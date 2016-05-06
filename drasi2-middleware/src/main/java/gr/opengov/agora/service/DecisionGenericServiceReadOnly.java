package gr.opengov.agora.service;

import gr.opengov.agora.dao.IDecisionGenericDAO;
import gr.opengov.agora.dao.IDocumentDAO;
import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.InvalidPublicOrganizationDecisionReference;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.exceptions.TooManyDecisionsToStoreException;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.OrganizationSearchParameter;
import gr.opengov.agora.search.StringSearchParameter;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.security.OdeAccessControl;
import gr.opengov.agora.util.IPaginationInfo;
import gr.opengov.agora.util.PaginationInfo;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.IValidator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;

public abstract class DecisionGenericServiceReadOnly<T extends IPublicOrganizationDecision> implements IDecisionGenericService<T> {
	private static final Logger logger = LoggerFactory.getLogger(DecisionGenericServiceReadOnly.class);
	private IDecisionGenericDAO<T> dao;
	private IAccessControl accessControl;
	private IValidator<T> validator;	
	private IDocumentDAO documentDao;
	
	public void setDocumentDAO(IDocumentDAO documentDAO) {
		this.documentDao = documentDAO;
	}
		
	public void setDao(IDecisionGenericDAO<T> dao) {
		this.dao = dao;		
	}	
	
	protected IDecisionGenericDAO<T> getDao() {
		return dao;
	}
		
	public void setAccessControl(IAccessControl accessControl) {
		this.accessControl = accessControl;
	}

	protected IAccessControl getAccessControl(){
		return accessControl;
	}
	
	public void setValidator( IValidator<T> validator ) {
		this.validator = validator;
	}
	
//	public abstract String getXmlPath();
	
	/**
	 * Checks if all references made by the specified contract exist in the database
	 * If they do not, they are replaced by InvalidReference which will be handled
	 * by the validation code. This is placed here rather than in the validation code
	 * to make use of the transactional context.
	 * @param contract
	 */
	protected void checkReferences( T decision ) {
		
	}
	
	/** 
	 *  Adds extra requirements to the query to take care of access control
	 * @param searchParams
	 */
	protected void addGetDecisionsFilters(List<ISearchParameter> searchParams) {	
		if ( accessControl.getClient().isAdmin() || accessControl.getClient().isAnonymous() ) return;
		for (Integer orgId:accessControl.getClient().getOrgs())
		{
			try {
				searchParams.add(new OrganizationSearchParameter(orgId.toString()));
			} catch (InvalidSearchParameterException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}		
	}
	
	private Map<IAda,IPublicOrganizationDecision> getRelatedDecisions(IPublicOrganizationDecision decision){
		Map<IAda,IPublicOrganizationDecision> relatedDecisions = null;
		
		if ( (decision.getRelatedAdas() != null) && (decision.getRelatedAdas().size() > 0) ){
			relatedDecisions = new Hashtable<IAda, IPublicOrganizationDecision>();
			IPublicOrganizationDecision relatedDecision = null;
			for (IAda ada:decision.getRelatedAdas()){
				if (ada.getAdaType().equals("ProcurementRequest"))
				{
					relatedDecision = getProcurementRequestService().get(ada.getAdaCode());
				}
				else{
					if (ada.getAdaType().equals("Notice")){
						relatedDecision = getNoticeService().get(ada.getAdaCode());
					}
					else{
						if (ada.getAdaType().equals("Notice")){
							relatedDecision = getContractService().get(ada.getAdaCode());
						}
						else{
							relatedDecision = new InvalidPublicOrganizationDecisionReference();
						}
					}
				}
				
				relatedDecisions.put(ada, ( relatedDecision != null) ? relatedDecision : new InvalidPublicOrganizationDecisionReference() );
			}
		}
		
		return relatedDecisions;
	}
	
	@Override
	public List<IDecisionStorageReference> save(List<T> decisions, IValidation validation ){
		throw new ForbiddenOperationException();
	}
	
	public IProcurementRequestService getProcurementRequestService() {
		return null; //getProcurementRequest of the child class is called
	}
	
	public IDecisionGenericService getNoticeService() {
		return null; //getNotice of the child class is called
	}	
	
	public IDecisionGenericService getContractService() {
		return null; //getContract of the child class is called
	}	
	
	public IPaymentService getPaymentService() {
		return null; //getPayment of the child class is called
	}	
	@Override
	public List<T> getAll( IPaginationInfo pagination, List<ISearchParameter> searchParams ) {
		pagination.setLimit( Math.min( pagination.getLimit(), accessControl.getClient().getMaximumDecisionsRead() ) );
		addGetDecisionsFilters(searchParams);
		List<T> decisions = dao.getAll( pagination, searchParams );
		logger.debug( "RETRIEVED DECISIONS: " + decisions.size() );
		for ( T c: decisions ) {
			c.finalizeEntity();
		}
		pagination.setLimit( decisions.size() );
		return decisions;
	}
	
	@Override
	public List<T> getAll( IPaginationInfo pagination ) {
		List<ISearchParameter> searchParams = new ArrayList<ISearchParameter>();
		addGetDecisionsFilters(searchParams);
		return getAll( pagination, searchParams );
	}
	
	@Override
	public List<T> getAll() {
		List<ISearchParameter> searchParams = new ArrayList<ISearchParameter>();
		addGetDecisionsFilters(searchParams);		
		return getAll( new PaginationInfo(), searchParams);
	}

	@Override
	public T get(String id) throws DecisionNotFoundException {
		List<ISearchParameter> searchParams = new ArrayList<ISearchParameter>();
		searchParams.add(new StringSearchParameter("id", id ));
		List<T> decisions = getAll( new PaginationInfo(), searchParams);
		if ( decisions.size() == 0 ) {
			logger.debug("decision with id=" + id + " not found");
			throw new DecisionNotFoundException( id );
		}
		T decision = decisions.get(0);
		decision.finalizeEntity();
		return 	decision;	
	}

	@Override
	public IDecisionStorageReference delete( String id, String reason, String deletionType ) {
		throw new ForbiddenOperationException();		
	}
	
	
	@Override
	public IDecisionStorageReference cancel( String id, String reason, String cancellationType ) {
		logger.debug( "Cancelling decision: " + id );
		accessControl.checkAccess( OdeAccessControl.Operation.CANCEL, get(id) );		
		logger.debug( "Validation was ok." );		
		return dao.cancel(id, reason, cancellationType, accessControl.getClient().getUserId() );		
	}		

	@Override
	public void purge( String id ) {
		throw new ForbiddenOperationException();
	}

	@Override
	public IDecisionStorageReference update(T decision, IValidation validation ) {
		throw new ForbiddenOperationException();
	}
	
	@Override
	public IDocument getDocument(String id) throws DecisionNotFoundException {
		List<ISearchParameter> searchParams = new ArrayList<ISearchParameter>();
		searchParams.add(new StringSearchParameter("id", id));
		addGetDecisionsFilters(searchParams);
		List<T> decisions = getAll( new PaginationInfo(), searchParams);
		if ( decisions.size() == 0 ) throw new DecisionNotFoundException( id );
		T decision = decisions.get( 0 );
		return decision.getDocument();
	}
	
	@Override
	public InputStream getDocumentData(IDocument document) throws DecisionNotFoundException, Exception {		
		return documentDao.getInputStream(document, true);
	}
	
	@Override
	public InputStream getDocumentOriginalData(IDocument document) throws DecisionNotFoundException, Exception {		
		return documentDao.getInputStream(document, false);
	}

	@Override
	public boolean exists(String id) {
		return dao.exists(id);
	}
	
	@Override
	public boolean exist( List<ISearchParameter> searchParams ) {
		addGetDecisionsFilters(searchParams);
		return dao.exist(searchParams);
	}	
	
	@Override
	public T getUnmanaged(String id){
		T obj = dao.getUnmanaged(id, false);
//		obj.finalizeEntity();
		return obj;
	}
	
	@Override
	public ICpv getCpv(String cpvCode){
		return dao.getCpv(cpvCode);
	}
}
