package gr.opengov.agora.web;

import gr.opengov.agora.converters.ICommonsOXMConverter;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IDecisionGenericConverter;
import gr.opengov.agora.converters.IPublicOrganizationDecisionOXMConverter;
import gr.opengov.agora.domain.DecisionStorageReference;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.exceptions.InternalErrorException;
import gr.opengov.agora.exceptions.InvalidIdSpecifiedException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.model.ArrayOfDecisionIds;
import gr.opengov.agora.model.GetDecisionIdsResponse;
import gr.opengov.agora.model.PaginationInfoOXM;
import gr.opengov.agora.model.StoreDecisionResponse;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.SearchParameterFactory;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.util.IPaginationInfo;
import gr.opengov.agora.util.PaginationInfo;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.Validation;

//import gr.opengov.agora.web.ProcurementRequestController;
//import gr.opengov.agora.web.PaymentController;
//import gr.opengov.agora.web.ContractController;
//import gr.opengov.agora.web.NoticeController;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;

import org.hibernate.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
/**
 * Base for all entity controllers
 */

abstract public class DecisionGenericController<D extends IPublicOrganizationDecision,X,XA,XASHORT> {
	@Resource(name = "commonsOxmConverter")
	private ICommonsOXMConverter commonsConverter;
	
	@Resource(name = "commonsOxmConverterReadOnly")
	private ICommonsOXMConverter commonsConverterReadOnly;	
	
	@Resource(name = "podOxmConverter")
	private IPublicOrganizationDecisionOXMConverter podConverter;		
	
	@Resource(name = "podOxmConverterReadOnly")
	private IPublicOrganizationDecisionOXMConverter podConverterReadOnly;		
	
	@Autowired
	private IAccessControl accessControl;

	//static final private Logger logger = LoggerFactory.getLogger(DecisionGenericController.class);
	private Logger logger;
	
	private Class cl;

	/* Abstract methods, to be overridden by subclasses */
	abstract protected Object getResponseFull( XA data, PaginationInfoOXM pagination );
	abstract protected Object getResponseShort( XASHORT data, PaginationInfoOXM pagination );
	abstract protected String getName();
	abstract protected String getLabel();
	abstract protected IDecisionGenericService<D> getService();
	abstract protected IDecisionGenericConverter<D, X, XA, XASHORT> getConverter();
		
	protected DecisionGenericController( Class cl ) {
		logger = LoggerFactory.getLogger(cl);
		this.cl = cl;
	}
	
	protected Logger getLogger() {
		return logger;
	}
	
	protected IAccessControl getAccessControl() {
		return accessControl;
	}	
	
	private ICommonsOXMConverter getCommonsConverter(){
		if (accessControl.getClient().isAnonymous()){
			return commonsConverterReadOnly;
		}
		else{
			return commonsConverter;
		}
	}
	
	private IPublicOrganizationDecisionOXMConverter getPodConverter(){
		if (accessControl.getClient().isAnonymous()){
			return podConverterReadOnly;
		}
		else{
			return podConverter;
		}
	}	
	
	protected Object getResponseIdsOnly( ArrayOfDecisionIds ids, PaginationInfoOXM pagination ) {
		GetDecisionIdsResponse response = new GetDecisionIdsResponse();
		response.setDecisions( ids );
		response.setPagination( pagination );
		return response;
	}
	
	
	/**
	 * Gets a list of all decisions
	 * 
	 * @param model
	 * @return
	 * @throws InvalidSearchParameterException 
	 */
	public String getAll( HttpServletRequest request, Model model, 
			String fromParam, String limitParam, String output	)
	throws InvalidSearchParameterException
	{
		logger.debug("Retrieving " + getName() + ", output: " + output );		
		List<ISearchParameter> searchParameters = SearchParameterFactory.fromServletRequest( request );	

		IPaginationInfo pagination = new PaginationInfo( fromParam, limitParam );
		List<D> decisions = getService().getAll(pagination, searchParameters);
		if ( output != null && output.equalsIgnoreCase( "short" ) ) {
			logger.debug( "Returning short response" );
			Object response = getResponseShort( getConverter().toXMLShort( decisions ),
												getCommonsConverter().getPagination( pagination ) );			
			model.addAttribute(response);
		}
		else if ( output != null && output.equalsIgnoreCase( "id" ) ) {
			//TODO: optimize this. Objects should not be finalized.
			logger.debug( "Returning only ids" );
			List<IPublicOrganizationDecision> list = new ArrayList<IPublicOrganizationDecision>();
			for ( D decision: decisions ) {
				list.add( decision );
			}
			Object response = getResponseIdsOnly( getPodConverter().getDecisionIds(list),
					getCommonsConverter().getPagination( pagination ) );			
			model.addAttribute( response );
		}
		else {
			logger.debug( "Returning full response" );
			Object response = getResponseFull( getConverter().toXMLList(decisions),
					getCommonsConverter().getPagination( pagination ) );
			model.addAttribute(response);
		}
		return "allView";
	}

	/**
	 * Retrieves a decision specified by id
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	public String get(String id, Model model) {
		logger.debug("Requesting single " + getName() + ": " + id);
		D decision = getService().get(id);		
		List<D> list = new ArrayList<D>();
		list.add( decision );		
		Object response = getResponseFull( getConverter().toXMLList( list ), null );
		model.addAttribute(response);
		return "singleView";
	}

	private void getPDFDocument( IDocument doc, boolean useStamped, HttpServletResponse response )
	throws Exception
	{
		InputStream is;
		if ( useStamped ) {
			is = getService().getDocumentData( doc );
			response.setContentLength( doc.getStampedSize());	
		}
		else {
			is = getService().getDocumentOriginalData( doc );
			response.setContentLength( doc.getSize());	
		}
		response.setContentType( doc.getContentType());				
		FileCopyUtils.copy(new BufferedInputStream( is ),
				new BufferedOutputStream( response.getOutputStream()) );			
	}

	public void getStampedPDFDocument(String id, HttpServletResponse response) {
		try {
			logger.debug("Requesting stamped PDF: " + id);		
			IDocument document = getService().getDocument(id);			
			getPDFDocument( document, true, response );
		}
		catch ( Exception e ) {
			e.printStackTrace();
			throw new InternalErrorException();
		}
	}

	public void getOriginalPDFDocument(String id, HttpServletResponse response) {
		try {
			logger.debug("Requesting original PDF: " + id);		
			IDocument document = getService().getDocument(id);			
			getPDFDocument( document, false, response );
		}
		catch ( Exception e ) {
			e.printStackTrace();
			throw new InternalErrorException();
		}
	}
	
	/**
	 * Stores a list of new decisions. The operation is atomic and fails if any
	 * decision fails to get stored in the database.
	 * 
	 * @param xmlList
	 */
	public String put( XA xmlList, Model model) {
		List<D> decisions = getConverter().toObjectList(xmlList);		
		IValidation validation = new Validation();
		List<IDecisionStorageReference> references = getService().save(decisions, validation);
		StoreDecisionResponse response = new StoreDecisionResponse();
		if ( validation.isValid() ) {
			response.setStorageReferences(getPodConverter().getDecisionReferences(references));
		}
		else
		{
			response.setValidationErrors( getCommonsConverter().getValidationErrors( validation.getErrors() ) );
		}
		model.addAttribute( response );
		return "storeView";
	}

	/**
	 * Updates an existing decision.
	 * 
	 * @param xml
	 * @param id
	 */
	public String update( X xml, String id, Model model ) throws ForbiddenOperationException {	
		D decision = getConverter().toObject(xml);
		IValidation validation = new Validation();
		logger.debug("Update single " + getName() );
		if (decision.getId() == null) decision.setId( id );
		if ( decision.getId() != id ) throw new InvalidIdSpecifiedException();
		getService().update( decision, validation );
		StoreDecisionResponse response = new StoreDecisionResponse();
		if ( validation.isValid() ) {
			IDecisionStorageReference ref = decision.getStorageReference( accessControl.getClient() );		
			response.setStorageReferences(getPodConverter().getDecisionReference( ref ) );
		}
		else
		{
			response.setValidationErrors( getCommonsConverter().getValidationErrors( validation.getErrors() ) );
		}
		model.addAttribute( response );
		return "updateView";
	}

	public void delete( String id, String reason, String deletionType, HttpServletRequest request) throws ForbiddenOperationException{
		boolean purge = (request.getHeader("X-PURGE") != null);
		boolean cancel = (request.getHeader("X-CANCEL") != null);
		IValidation validation = new Validation();
		if (purge) {
			logger.debug("Purge single " + getName() );
			getService().purge(id);
		}
		else
		{ 
			if (cancel){
				getService().cancel(id, reason, deletionType);
			}
			else 
			{
				logger.debug("Delete single " + getName() );
				getService().delete(id, reason, deletionType);
			}
		}
		
	}	
			
	public ModelAndView debugView( HttpServletRequest request ) throws InvalidSearchParameterException {		
		List<ISearchParameter> searchParameters = SearchParameterFactory.fromServletRequest( request );			
		IPaginationInfo pagination = new PaginationInfo( );
		List<D> decisions = getService().getAll( pagination );		
		Map<String,Object> map = new HashMap<String, Object>();
		List<String> ids = new ArrayList<String>();
		for ( D decision: decisions ) {
			ids.add( decision.getId() );						
		}
		map.put( "ids", ids );
		map.put( "label", getLabel() );
		return new ModelAndView( "view_decisions", map );
	}	
 }