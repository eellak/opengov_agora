package gr.opengov.agora.test;

import static org.junit.Assert.*;

import gr.opengov.agora.converters.IProcurementRequestOXMConverter;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.ICmsMetadata;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.ProcurementRequest;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.model.ValidationErrorOXM;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.SearchParameterFactory;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.security.IClientFactory;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.test.stub.AdminTestClientFactory;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.util.PaginationInfo;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.IValidationError;
import gr.opengov.agora.validation.Validation;
import gr.opengov.agora.web.ContractController;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class ProcurementRequestServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ProcurementRequestServiceTest.class);
	@Resource( name="procurementRequestService" )
	private IDecisionGenericService<IProcurementRequest> service;
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	private static XMLUtils xmlUtils;
	@Resource( name="procurementRequestOxmConverter" )
	private IProcurementRequestOXMConverter procurementRequestOxmConverter;
	@Resource( name="accessControl" )
	private IAccessControl accessControl;
	
	@BeforeClass
	public static void init() {
		xmlUtils = XMLUtils.newInstance( XMLUtils.PROCUREMENT_REQUEST );
	}
		
	private IProcurementRequest loadRequest( String xmlFile ) throws Exception {
		ProcurementRequestsOXM requests = xmlUtils.unmarshal( xmlFile, ProcurementRequestsOXM.class );
		return procurementRequestOxmConverter.toObject( requests.getRequest().get( 0 ) );
	}
	
	private void printValidationErrors( Validation errors ) {
		logger.debug( "\nValidation Errors:\n" );
		for ( IValidationError error:errors.getErrors() ) { 
			logger.debug( error.getCode() + " - " + error.getLocation()  + " - " + error.getMessage() );
		}
	}
	
	@Test
	public void testSaveSingleProcurementRequestIgnoreAccess() throws Exception {
		logger.debug( "Testing saving a single procurement request, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		Validation validation = new Validation();
		service.save( Arrays.asList( request ), validation );
		if (!validation.isValid())
			printValidationErrors(validation);
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 1 );
	}
	
	//procurement request with responsibilityAssumptionCode != null and approvesRequest == null
	@Ignore //this rule has been rejected
	@Test
	public void testSaveSinglePRwithResponsibilityCode() throws Exception {
		logger.debug( "Testing saving a single procurement request, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		request.setResponsibilityAssumptionCode("testCode");
		service.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 0 );
	}
	
	@Test
	public void testSaveSingleProcurementRequestApprovesIgnoreAccess() throws Exception {
		logger.debug( "Testing saving a single procurement request extending an existing one, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		Validation validation = new Validation();
		service.save( Arrays.asList( request ), validation );
		if (!validation.isValid())
			printValidationErrors(validation);
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 1 );
		
		IProcurementRequest approvalRequest = loadRequest( "single-request.xml" );
		approvalRequest.setProtocolNumberCode("test");
		approvalRequest.setApprovesRequest(saved.get(0));
		approvalRequest.setResponsibilityAssumptionCode("testCode");
		approvalRequest.setAwardProcedureIdRef("1");
		validation = new Validation();
		service.save( Arrays.asList( approvalRequest ), validation );
		printValidationErrors(validation);
		List<IProcurementRequest> savedFinal = service.getAll();
		assertTrue( savedFinal.size() == 2 );		
	}	
	
	@Test
	public void testSearchRequestsByCpv() throws Exception {
		logger.debug( "Testing saving a single procurement request extending an existing one, no access control" );
		dbHandler.clearDb();
		Validation validation = new Validation();
		logger.debug("storing 3 requests\n");
		int firstRequestContractItems=0;
		String firstRequestAda="";
		for (int i=0;i<3;i++)
		{		
			IProcurementRequest request = loadRequest( "single-request-three-items.xml" );
			if (i<2){
				request.getContractItems().get(0).addCpv("AA07-9");
			}

			if (i == 0){
				firstRequestContractItems = request.getContractItems().size();
				firstRequestAda = request.getId();
			}			
			
			validation = new Validation();
			service.save( Arrays.asList( request ), validation );
			if (!validation.isValid()){
				logger.debug("saving requests errors:\n");
				printValidationErrors(validation);
			}
		}
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 3 );
		
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();		
		mockRequest.addParameter( "cpv", "AA07-9" );
		List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( mockRequest );
		List<IProcurementRequest> found = service.getAll( new PaginationInfo(), params );
		assertEquals( found.size(), 2 );
		for (IProcurementRequest request:found){
			if (request.getId().equals(firstRequestAda)){
				assertTrue(request.getContractItems().size() == firstRequestContractItems);
			}
		}
	
	}
	
	@Test
	public void testSearchRequestsByCpvB() throws Exception {
		logger.debug( "Testing saving a single procurement request extending an existing one, no access control" );
		dbHandler.clearDb();
		Validation validation = new Validation();
		logger.debug("storing 3 requests\n");
		int firstRequestContractItems=0;
		String firstRequestAda="";
		for (int i=0;i<3;i++)
		{		
			IProcurementRequest request = loadRequest( "single-request-three-items.xml" );
			if (i<2){
				request.getContractItems().get(0).addCpv("AA07-9");
				request.getContractItems().get(1).addCpv("AA07-9");
			}

			if (i == 0){
				firstRequestContractItems = request.getContractItems().size();
				firstRequestAda = request.getId();
			}			
			
			validation = new Validation();
			service.save( Arrays.asList( request ), validation );
			if (!validation.isValid()){
				logger.debug("saving requests errors:\n");
				printValidationErrors(validation);
			}
		}
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 3 );
		
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();		
		mockRequest.addParameter( "cpv", "AA07-9" );
		List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( mockRequest );
		List<IProcurementRequest> found = service.getAll( new PaginationInfo(), params );
		assertEquals( found.size(), 2 );
		for (IProcurementRequest request:found){
			if (request.getId().equals(firstRequestAda)){
				assertTrue(request.getContractItems().size() == firstRequestContractItems);
			}
		}
	
	}		
	
	@Test
	public void testSaveSingleProcurementRequestApprovesTwiceIgnoreAccess() throws Exception {
		IValidation validation = new Validation();
		
		logger.debug( "Testing saving a single procurement request extending twice an existing one, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		service.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 1 );
		
		IProcurementRequest approvalRequest = loadRequest( "single-request.xml" );
		approvalRequest.setProtocolNumberCode("test");
		approvalRequest.setApprovesRequest(saved.get(0));
		approvalRequest.setAwardProcedureIdRef("2");
		service.save( Arrays.asList( approvalRequest ), new Validation() );	
		List<IProcurementRequest> savedFinal = service.getAll();
		assertTrue( savedFinal.size() == 2 );
		
		IProcurementRequest approvalRequestb = loadRequest( "single-request.xml" );
		approvalRequestb.setProtocolNumberCode("testb");
		approvalRequestb.setApprovesRequest(saved.get(0));
		
		service.save( Arrays.asList( approvalRequestb ), validation );	
		savedFinal = service.getAll();
		assertTrue( savedFinal.size() == 2 );
		assertFalse( validation.isValid());
		assertTrue( validation.hasErrorCode("approvedRequest"));
	}		

	@Test
	public void testSaveSingleProcurementRequestApprovesAndUpdateApprovalIgnoreAccess() throws Exception {
		IValidation validation = new Validation();
		
		logger.debug( "Testing saving a single procurement request extending an existing one and updating aproval, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		List<IDecisionStorageReference> firstRequest = service.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 1 );
		
		IProcurementRequest approvalRequest = loadRequest( "single-request.xml" );
		approvalRequest.setProtocolNumberCode("test");
		approvalRequest.setApprovesRequest(service.getUnmanaged(firstRequest.get(0).getId()));
		approvalRequest.setAwardProcedureIdRef("3");
		List<IDecisionStorageReference> secondRequest = service.save( Arrays.asList( approvalRequest ), new Validation() );	
		List<IProcurementRequest> savedFinal = service.getAll();
		assertTrue( savedFinal.size() == 2 );
		
		
		IProcurementRequest requestApproval = loadRequest( "single-request.xml" );
		requestApproval.setTitle("newTitle");
		requestApproval.setUniqueDocumentCode( "newDocumentCode" );
		String id = secondRequest.get(0).getId();
		requestApproval.setId(id);
		requestApproval.setApprovesRequest(service.get(id).getApprovesRequest());
		requestApproval.setAwardProcedureIdRef("2");
		
		logger.debug("requestApproval:"+requestApproval.getId());
		logger.debug("approves request:"+requestApproval.getApprovesRequest().getId());
		
		
		service.update(requestApproval, validation );
		if ( !validation.isValid() ) {
			logger.error( "Validation Error:\n" + validation.toString() );
			fail();
		}		
		IProcurementRequest savedRequest = service.get( requestApproval.getId() );
		assertEquals( savedRequest.getUniqueDocumentCode(), "newDocumentCode" );
		assertEquals( savedRequest.getTitle(), "newTitle" );
		assertEquals( savedRequest.getId(), requestApproval.getId() );
		assertEquals( firstRequest.get(0).getId(), service.getUnmanaged(secondRequest.get(0).getId()).getApprovesRequest().getId());
		
		
	}		
	@Test
	public void testSaveSingleProcurementRequestPriorDateSignedApprovesIgnoreAccess() throws Exception {
		logger.debug( "Testing saving a single procurement request extending an existing one, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		service.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 1 );
		
		IProcurementRequest approvalRequest = loadRequest( "single-request.xml" );
		
		approvalRequest.getDateSigned().add(  Calendar.YEAR, -1 );
		approvalRequest.setProtocolNumberCode("test");
		approvalRequest.setApprovesRequest(saved.get(0));
		approvalRequest.setAwardProcedureIdRef("2");
		service.save( Arrays.asList( approvalRequest ), new Validation() );	
		List<IProcurementRequest> savedFinal = service.getAll();
		assertTrue( savedFinal.size() == 2 );		
	}	
	@Test
	public void testSaveSingleProcurementRequestApprovesFailIgnoreAccess() throws Exception {
		logger.debug( "Testing saving a single procurement request approves not existed request, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		IProcurementRequest newRequest = new ProcurementRequest();
		newRequest.setId("testApproves");
		request.setApprovesRequest(newRequest);
		service.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 0 );
	}	
	@Test
	public void testgetDocument() throws Exception {
		logger.debug( "Testing retrieving a procurement request's document" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );		
		byte[] dataOriginal = request.getDocument().getData().clone();
		logger.debug( "...Original data size: " + dataOriginal.length );
		service.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = service.getAll();
		String id = saved.get(0).getId();		
		IDocument documentRetrieved = service.getDocument( id );
		assertTrue( Arrays.equals( dataOriginal, IOUtil.toByteArray( service.getDocumentOriginalData(documentRetrieved) ) ) );				
	}

	private void updateSingleProcurementRequest( boolean emptyDocument ) throws Exception {
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );		
		byte[] dataOriginal = request.getDocument().getData().clone();
		service.save( Arrays.asList( request ), new Validation() );				
		String savedId = service.getAll().get(0).getId();		
		request = loadRequest( "single-request.xml" );
		if ( emptyDocument ) {
			request.setDocument( null );
		}
		request.setId( savedId );
		request.setUniqueDocumentCode( "newDocumentCode" );
		request.setDocument( null );
		Validation validation = new Validation();
		service.update(request, validation );
		if ( !validation.isValid() ) {
			logger.error( "Validation Error:\n" + validation.toString() );
			fail();
		}		
		request = service.get( savedId );
		assertEquals( request.getUniqueDocumentCode(), "newDocumentCode" );
		assertEquals( request.getId(), savedId );
		IDocument document = service.getDocument( savedId );
		assertTrue( Arrays.equals( dataOriginal, IOUtil.toByteArray( service.getDocumentOriginalData(document) ) ) );
	}	
	@Test
	public void testUpdateSingleProcurementRequestWithoutDocument() throws Exception {
		logger.debug( "Testing updating a single procurement request without document" );
		updateSingleProcurementRequest( true );
	}
	@Test
	public void testUpdateSingleProcurementRequestWithDocument() throws Exception {
		logger.debug( "Testing updating a single procurement request without document" );
		updateSingleProcurementRequest( false );
	}	
	@Test	
	public void testPurgeSingleStoredProcurementRequest() throws Exception {
		logger.debug( "Testing purging a single stored procurement request" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		
		service.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 1 );
		request = saved.get(0);
		Validation validation = new Validation();
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		service.purge( request.getId() );
		accessControl.setClientFactory( defaultFactory );
		saved = service.getAll();
		assertTrue( saved.isEmpty() );
		assertTrue(validation.isValid());
	}
	
	@Ignore
	@Test
	public void testPurgeSingleStoredProcurementOldRequest() throws Exception {
		logger.debug( "Testing purging a single stored procurement request" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		
//		Calendar submited = Calendar.getInstance();
//		submited.add(Calendar.DAY_OF_MONTH, -3);			
//		ICmsMetadata metadata = new CmsMetadata();
//		metadata.setSubmissionTime( submited );
//		request.setCmsMetadata(metadata);
		
		Validation validation = new Validation();
		service.save( Arrays.asList( request ), validation );
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 1 );
		request = saved.get(0);
	
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		try{
			service.purge( request.getId() );
		}
		catch (Exception ex){
			assertTrue(ex instanceof ForbiddenOperationException);
		}
//		
		accessControl.setClientFactory(defaultFactory);
		saved = service.getAll();
		assertTrue( saved.isEmpty() );
//		assertTrue(validation.hasErrorCode("endOfChangeTimeSlot") );
	}	
	@Test
	public void testDeleteSingleStoredProcurementRequest() throws Exception {
		logger.debug( "Testing deleting a single stored procurement request" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		
//		ICmsMetadata metadata = new CmsMetadata();
//		metadata.setSubmissionTime(Calendar.getInstance());		
//		request.setCmsMetadata(metadata);
		
		Validation validation = new Validation();
		service.save( Arrays.asList( request ), validation );
		List<IProcurementRequest> saved = service.getAll();
		if (!validation.isValid())
		{
			logger.debug("store request errors:\n");
			printValidationErrors(validation);
		}
		assertTrue( saved.size() == 1 );
		request = saved.get(0);
		validation = new Validation();
		service.delete( request.getId(), "testReason", "3" );
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );		
		saved = service.getAll();
		accessControl.setClientFactory( defaultFactory );
		assertEquals( saved.size(), 1 );
		request = saved.get( 0 );
		assertTrue( request.isDeleted() );
		assertTrue( validation.isValid() );
		assertTrue( request.getCmsMetadata().getDeletedReason().equals("testReason") );
		// TODO: Test that user was saved
		//assertTrue( request.getCmsMetadata().getDeletedFromUserId().intValue() == fullAccess.getClient().getUserId().intValue() );		
	}
	
	@Ignore //it fails if user cannot see deleted
	@Test
	public void testDeleteSingleStoredProcurementOldRequest() throws Exception {
		logger.debug( "Testing deleting a single stored procurement request" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		
//		ICmsMetadata metadata = new CmsMetadata();
//		Calendar submited = Calendar.getInstance();
//		submited.add(Calendar.DAY_OF_MONTH, -3);
//		metadata.setSubmissionTime( submited );
//		request.setCmsMetadata(metadata);
		
		service.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = service.getAll();
		assertTrue( saved.size() == 1 );
		request = saved.get(0);
		Validation validation = new Validation();
		service.delete( request.getId(), "testReason", "1" );		
		saved = service.getAll();
		assertEquals( saved.size(), 1 );
		request = saved.get( 0 );
		assertFalse( request.isDeleted() );
		assertTrue( validation.hasErrorCode("endOfChangeTimeSlot") );
	}
}
