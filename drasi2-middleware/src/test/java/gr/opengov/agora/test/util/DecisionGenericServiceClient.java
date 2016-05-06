package gr.opengov.agora.test.util;

import static org.junit.Assert.*;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IDecisionGenericConverter;
import gr.opengov.agora.converters.IProcurementRequestOXMConverter;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.Contract;
import gr.opengov.agora.domain.ICmsMetadata;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.PublicOrganizationDecision;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.SearchParameterFactory;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.security.IClientFactory;
import gr.opengov.agora.service.DecisionGenericService;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.test.stub.AdminTestClientFactory;
import gr.opengov.agora.util.PaginationInfo;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.Validation;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtil;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

abstract public class DecisionGenericServiceClient<T extends IPublicOrganizationDecision, X, XA, XASHORT> {
	private static final Logger logger = LoggerFactory.getLogger(DecisionGenericServiceClient.class);	
	private IDecisionGenericService<T> service;
	private IDecisionGenericService<IContract> contractService;
	private IDecisionGenericService<IProcurementRequest> requestService;
	private IDecisionGenericService<IPayment> paymentService;
	private IDecisionGenericConverter<T, X, XA, XASHORT> converter;
	private Class<T> objClass;
	private Class<X> xmlClass;
	private Class<XA> xmlArrayClass;
	
	private String getName() {
		return objClass.getSimpleName();
	}
	
	protected abstract IDecisionGenericService<T> getService();
	protected abstract IDecisionGenericConverter<T, X, XA, XASHORT> getConverter();

	protected DecisionGenericServiceClient( Class<T> objClass, Class<X> xmlClass, Class<XA> xmlArrayClass ) {
		this.objClass = objClass;
		this.xmlClass = xmlClass;
		this.xmlArrayClass = xmlArrayClass;
	}
	
	private IDatabaseHandler dbHandler;
	
	private static XMLUtils xmlUtils;
	
	private IAccessControl accessControl;	
	
	public void setDatabaseHandler(IDatabaseHandler dbHandler){
		this.dbHandler = dbHandler;
	}
	
	public void setAccessControl(IAccessControl accessControl){
		this.accessControl = accessControl;
	}	
	
	public static void setXmlUtils(XMLUtils xmlUtilsInitialized) {
		xmlUtils = xmlUtilsInitialized;
	}
	
	public static XMLUtils getXmlUtils() {
		return xmlUtils;
	}

	private X getItemFromList( XA list, int index ) {
		if ( objClass.isAssignableFrom( IContract.class ) ) {
			return (X)((ContractsOXM)list).getContract().get(index);
		}
		if ( objClass.isAssignableFrom( IPayment.class ) ) {
			return (X)((PaymentsOXM)list).getPayment().get(index);
		}
		if ( objClass.isAssignableFrom( IProcurementRequest.class ) ) {
			return (X)((ProcurementRequestsOXM)list).getRequest().get(index);
		}		
		return null;
	}
	
	protected T load( String xmlFile ) throws Exception {
		XA list = xmlUtils.unmarshal( xmlFile, xmlArrayClass );
		return getConverter().toObject( getItemFromList( list, 0 ) );
	}	
	
//	protected T unmarshal( String xml ) throws Exception {
//		XA list = xmlUtils.unmarshalXml( xml, xmlArrayClass );
//		return getConverter().toObject( getItemFromList( list, 0 ) );
//	}
//	
	private List<T> loadList( String xmlFile ) throws Exception {
		XA list = xmlUtils.unmarshalXml( xmlFile, xmlArrayClass );
		List<T> decisions = new ArrayList<T>();
		X obj = getItemFromList( list, 0 );
		int i = 0;
		while ( obj != null){
			decisions.add(getConverter().toObject(obj));
			obj = getItemFromList( list, ++i );
		}
		return decisions;
	}	
	
//	private List<T> unmarshalList( String xml ) throws Exception {
//		XA list = xmlUtils.unmarshalXml( xml, xmlArrayClass );
//		List<T> decisions = new ArrayList<T>();
//		X obj = getItemFromList( list, 0 );
//		int i = 0;
//		while ( obj != null){
//			decisions.add(getConverter().toObject(obj));
//			obj = getItemFromList( list, ++i );
//		}
//		return decisions;
//	}	
	
	protected boolean saveSingleIgnoreAccess(String xmlFile, List<T> saved, boolean clearDb, String failMessage) throws Exception {
		return saveSingleIgnoreAccess( load( xmlFile ), saved, clearDb, failMessage);
	}
	
	protected boolean saveSingleIgnoreAccess(T decision, List<T> saved, boolean clearDb, String failMessage) throws Exception {
		boolean success = true;
		StringBuffer buffer = new StringBuffer();
		logger.debug( "Testing saving a single " + getName() + ", no access control" );
		Integer existedCnt = getService().getAll().size();
		if (clearDb){ 
			dbHandler.clearDb();
			existedCnt = 0;
		}
		IValidation validation = new Validation();
		getService().save( Arrays.asList( decision), validation );
		if ( !validation.isValid() ) {
			logger.error( "Validation Error:\n" + validation.toString() );
			buffer.append("Validation Error:\n" + validation.toString() );
			success = false;
		}
		else
		{
			List<T> savedList = getService().getAll();			
			success = success && (savedList.size() == (existedCnt + 1));
			if (success)
				saved.add(savedList.get((existedCnt)));				
		}
		failMessage = buffer.toString();
		return success;
	}	
	
	protected boolean saveListIgnoreAccess(String xmlFile, List<T> saved, boolean clearDb, boolean isAdminClient, String failMessage) throws Exception {
		boolean success = true;
		StringBuffer buffer = new StringBuffer();
		
		IClientFactory defaultFactory = accessControl.getClientFactory();
		if (isAdminClient)
			accessControl.setClientFactory( new AdminTestClientFactory() );

		logger.debug( "Testing saving list of " + getName() + ", no access control" );
		Integer existedCnt = getService().getAll().size();
		if (clearDb){ 
			dbHandler.clearDb();
			existedCnt = 0;
		}
		List<T> decisions = loadList( xmlFile );
		IValidation validation = new Validation();
		getService().save( decisions, validation );
		if ( !validation.isValid() ) {
			logger.error( "Validation Error:\n" + validation.toString() );
			buffer.append("Validation Error:\n" + validation.toString() );
			success = false;
		}		
		saved = getService().getAll();
		success = success && (saved.size() == (existedCnt + decisions.size()));
		if (!success) buffer.append("\nCould not save list of " + getName());
		accessControl.setClientFactory( defaultFactory );
		failMessage = buffer.toString();
		return success;
	}	
	
	protected boolean updateSingleIgnoreAccess(String xmlFileOriginal, String xmlFileChanged, boolean clearDb, String failMessage) throws Exception {
		boolean success = true;
		StringBuffer buffer = new StringBuffer();
		String errMsg = "";
		logger.debug( "Testing updating a single " + getName() + ", no access control" );
		if (clearDb) dbHandler.clearDb();
		
		List<T> saved = new ArrayList<T>();
		success = success && saveSingleIgnoreAccess(xmlFileOriginal, saved, true, errMsg);
		if (errMsg.length() > 0) buffer.append(errMsg);
		
		if (success)
		{
			String savedId = saved.get(0).getId();
		
			T changedDecision = load( xmlFileChanged );		
			changedDecision.setId(savedId);
		
			IValidation validation = new Validation();
			getService().update( changedDecision, validation );
			if ( !validation.isValid() ) {
				logger.error( "Validation Error:\n" + validation.toString() );
				buffer.append("\nValidation Error:\n" + validation.toString() );
				success = false;
			}				
		
			T decision = getService().get(savedId);
		
			success = success && (decision.equals(changedDecision));
			if (!success) buffer.append("\nCould not update " + getName());
		}
		failMessage = buffer.toString();
		return success;
	}	
	
	protected boolean saveAndGetDocument(String xmlFile, String failMessage) throws Exception {
		boolean success = true;
		StringBuffer buffer = new StringBuffer();
		String errMsg = "";		
		logger.debug( "Testing retrieving a " + getName() + "'s document" );
		dbHandler.clearDb();
		
		T decision = load( xmlFile );
		List<T> saved = new ArrayList<T>();
		
		success = success && saveSingleIgnoreAccess(decision, saved, false, errMsg);
		
		if (success){
			byte[] dataOriginal = decision.getDocument().getData().clone();
			logger.debug( "...Original data size: " + dataOriginal.length );
			
			IDocument documentRetrieved = getService().getDocument( saved.get(0).getId() );
			
			success = success && Arrays.equals( dataOriginal, IOUtil.toByteArray( getService().getDocumentOriginalData(documentRetrieved) ) );
		}
		if (!success) buffer.append("\nCould not document of " + getName());
		failMessage = buffer.toString();
		
		return success;
	}
		
	protected boolean purgeSingleStoredDecision(String xml, boolean clearDb, String failMessage) throws Exception {
		logger.debug( "Testing purging a single stored " + getName() );
		boolean success = true;
		StringBuffer buffer = new StringBuffer();
		String errMsg = "";
		
		List<T> saved = new ArrayList<T>();
		success = success && saveSingleIgnoreAccess(xml, saved, clearDb, errMsg);
		if (errMsg.length() > 0) buffer.append(errMsg);
		
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		getService().purge( saved.get(0).getId() );		
		accessControl.setClientFactory( defaultFactory );
		
		saved = getService().getAll();
		success = success && saved.isEmpty();
		if (!success) buffer.append("\nCould not purge " + getName());
		failMessage = buffer.toString();
		return success;
	}

	protected boolean deleteSingleStoredDecision(String xml, String reason, String deletionType, boolean clearDb, String failMessage) throws Exception {
		logger.debug( "Testing purging a single stored " + getName() );
		boolean success = true;
		StringBuffer buffer = new StringBuffer();
		String errMsg = "";
		List<T> saved = new ArrayList<T>();
		success = success && saveSingleIgnoreAccess(xml, saved, clearDb, errMsg);
		if (errMsg.length() > 0) buffer.append(errMsg);
		
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		getService().delete( saved.get(0).getId(), reason, deletionType );		
		accessControl.setClientFactory( defaultFactory );
		
		saved = getService().getAll();
		success = success && (saved.size() == 1);
		if (success){
			success = success && (saved.get(0).isDeleted());
			success = success && saved.get(0).getCmsMetadata().getDeletedReason().equals(reason);
		}
		if (!success) buffer.append("\nCould not delete " + getName());
		failMessage = buffer.toString();
		return success;
	}	
		
	protected List<T> searchDecisions(Map params) throws InvalidSearchParameterException{
		logger.debug( "Testing searching " + getName() +" using parameters" );
		MockHttpServletRequest request = new MockHttpServletRequest();
		Iterator it = params.entrySet().iterator();
		while (it.hasNext()){
			Map.Entry<String, String> param = (Map.Entry<String, String>)it.next();
			request.addParameter( (String)param.getKey(), (String)param.getValue() );
		}
		List<ISearchParameter> searchParams = SearchParameterFactory.fromServletRequest( request );
		return getService().getAll( new PaginationInfo(), searchParams );
	}
	
	// A simple test for manual debuggin
	@Test
	public void clearDb() {		
		dbHandler.clearDb();
	}
}
