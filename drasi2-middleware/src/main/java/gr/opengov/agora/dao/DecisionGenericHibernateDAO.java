package gr.opengov.agora.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge;
import gr.opengov.agora.cms.ICms;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IContractParty;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.ProcurementRequest;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.exceptions.DecisionReferenceNotFoundException;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.exceptions.InternalErrorException;
import gr.opengov.agora.search.ApprovedFilterSearchParameter;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.CpvSearchParameter;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.security.OdeAccessControl;
import gr.opengov.agora.util.Constants;
import gr.opengov.agora.util.IPaginationInfo;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some common functionality, mostly injection stuff
 * @author xenofon
 *
 */
public class DecisionGenericHibernateDAO<T extends IPublicOrganizationDecision> implements IDecisionGenericDAO<T> {
	private static final Logger logger = LoggerFactory.getLogger(DecisionGenericHibernateDAO.class);
	private ICms cms;
	private IAccessControl accessControl;
	private SessionFactory sessionFactory;
	private SessionFactory sessionFactoryReadOnly;
	private IDocumentDAO documentDao;
	private IAgoraDiavgeiaBridge bridge;
	private Class<T> type;	
	
	public DecisionGenericHibernateDAO( Class<T> type ) {
		this.type = type;
	}
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected SessionFactory getSessionFactoryReadOnly() {
		return sessionFactoryReadOnly;
	}
	public void setSessionFactoryReadOnly(SessionFactory sessionFactoryReadOnly) {
		this.sessionFactoryReadOnly = sessionFactoryReadOnly;
	}		
	private Session session() {
		return getSessionFactory().getCurrentSession();
	}
	private Session sessionReadOnly() {
		return getSessionFactoryReadOnly().getCurrentSession();
	}	
	
	public IAccessControl getAccessControl() {
		return accessControl;
	}
	
	public void setDiavgeiaBridge(IAgoraDiavgeiaBridge bridge) {
		this.bridge = bridge;
	}	
	
	public void setAccessControl(IAccessControl accessControl) {
		this.accessControl = accessControl;
	}
		
	private String entityName() {
		String name = type.getSimpleName();
		logger.debug( "ENTITY NAME FOR CLASS " + type + ": " + name );
		return name;
	}

	/**
	 * Parse searchParams arguments and return a list of all criteria
	 * required for a query
	 * @param searchParams
	 * @return
	 */
	private Criteria getSearchCriteria( List<ISearchParameter> searchParams ) {			
		Criteria criteria = session().createCriteria( type, "returnObj" ); //.createAlias( "cmsMetadata", "cmsMetadata" );
		logger.debug( "Client: " + getAccessControl().getClass() );
		logger.debug( "Can view deleted: " + getAccessControl().canViewDeleted() );
		if ( !getAccessControl().canViewDeleted() ) {
			criteria = criteria.add( Restrictions.eq( "cmsMetadata.deleted", false ) );
		}
		for ( ISearchParameter searchParam: searchParams ) {
			logger.debug( "Adding search criterion: " + searchParam.toString() );

			
			if (searchParam instanceof ApprovedFilterSearchParameter){
				String value = searchParam.toString().substring(searchParam.toString().indexOf(": ")+2);
				if (value.equals(Constants.APPROVAL_REQUESTS_FILTER_KEY)){
					criteria.add(Restrictions.isNotNull("approvesRequest"));
				}
				else
					if (value.equals(Constants.APPROVED_REQUESTS_FILTER_KEY)){
						DetachedCriteria approved = DetachedCriteria.forClass( ProcurementRequest.class, "approvedRequest" )
													.createAlias( "approvesRequest", "approves" )
													.add( Restrictions.isNotNull( "approves.id" ) )
													.setProjection( Property.forName( "approves.id" ) );
						criteria.add( Restrictions.isNull( "approvesRequest"  ) ).add( Subqueries.propertyIn( "id", approved ) );						
					}				
					else
						if (value.equals(Constants.NOTAPPROVED_REQUESTS_FILTER_KEY)){
							DetachedCriteria approved = DetachedCriteria.forClass( ProcurementRequest.class, "approvedRequest" ).createAlias( "approvesRequest", "approves" ).add( Restrictions.isNotNull( "approves.id" ) ).setProjection( Property.forName( "approves.id" ) );
							criteria.add( Restrictions.isNull( "approvesRequest" ) ).add( Subqueries.propertyNotIn( "id", approved ) );
						}									
				
			}
			else
				if (searchParam instanceof CpvSearchParameter){
					if ( type.isAssignableFrom( IProcurementRequest.class ) ){
						logger.debug("cpv code param:" + searchParam.toString());
						String value = searchParam.toString().substring(searchParam.toString().indexOf(": ")+2);
			
//						DetachedCriteria contractItemsCriteria = DetachedCriteria.forClass( ContractItem.class, "contractItem" )
//						.createAlias( "cpvCodes", "cpvCode" )
//						.add( Restrictions.eq("cpvCode.cpvCode", value))
//						.createAlias( "contractItem.procurementRequest", "procurementRequest" )
//						.add( Restrictions.eqProperty("procurementRequest.id", "returnObj.id") )
//						.setProjection( Property.forName( "procurementRequest.id"));
//						criteria.add(Subqueries.propertyIn("returnObj.id", contractItemsCriteria));
						
						criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.createCriteria( "contractItems", Criteria.LEFT_JOIN )
						.createCriteria( "cpvCodes", Criteria.LEFT_JOIN )
						.add( Restrictions.eq( "cpvCode", value ) );								
					}
					else
						if ( type.isAssignableFrom( IContract.class ) ){
							logger.debug("cpv code param:" + searchParam.toString());
							String value = searchParam.toString().substring(searchParam.toString().indexOf(": ")+2);

							criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
							.createCriteria( "contractItems", Criteria.LEFT_JOIN )
							.createCriteria( "cpvCodes", Criteria.LEFT_JOIN )
							.add( Restrictions.eq( "cpvCode", value ) );							
						}
						else
							if ( type.isAssignableFrom( IPayment.class ) ){
								logger.debug("cpv code param:" + searchParam.toString());
								String value = searchParam.toString().substring(searchParam.toString().indexOf(": ")+2);
								
								criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
								.createCriteria( "paymentItems", Criteria.LEFT_JOIN )
								.createCriteria( "contractItem", Criteria.LEFT_JOIN )
								.createCriteria( "cpvCodes", Criteria.LEFT_JOIN )
								.add( Restrictions.eq( "cpvCode", value ) );	
																
							}
							else
								if ( type.isAssignableFrom( INotice.class ) ){
									logger.debug("cpv code param:" + searchParam.toString());
									String value = searchParam.toString().substring(searchParam.toString().indexOf(": ")+2);
									
									criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
									.createCriteria( "contractItems", Criteria.LEFT_JOIN )
									.createCriteria( "cpvCodes", Criteria.LEFT_JOIN )
									.add( Restrictions.eq( "cpvCode", value ) );				
								}
				}
				else
				{					
					criteria = criteria.add( searchParam.toHibernateCriterion() );
				}
		}
		criteria = criteria.addOrder( Order.desc( "cmsMetadata.submissionTime" ) );
		return criteria;
	}

	/**
	 * Return a single entity, using search parameters
	 * 
	 */
	@Override
	public T get( List<ISearchParameter> searchParams ) {
		Criteria criteria = getSearchCriteria(searchParams);
		return (T)criteria.uniqueResult();		
	}

	/**
	 * Return a single entity by id
	 */
	@Override
	public T get( String id, boolean includeDeleted ) {
		Criteria criteria = getSearchCriteria( new ArrayList<ISearchParameter>() );
		criteria.add( Restrictions.eq( "id", id ) );
		if ( !includeDeleted ) criteria.add( Restrictions.eq( "cmsMetadata.deleted", false) );
		return (T)criteria.uniqueResult();
	}	
	
	@Override
	public T getUnmanaged( String id, boolean includeDeleted ) {
		Criteria criteria = getSearchCriteria( new ArrayList<ISearchParameter>() );
		criteria.add( Restrictions.eq( "id", id ) );
		if ( !includeDeleted ) criteria.add( Restrictions.eq( "cmsMetadata.deleted", false) );
		T obj = (T)criteria.uniqueResult();
		
		if (obj != null) {
			obj.finalizeEntity();
			session().evict(obj);
		}
		return obj;
	}	
	
	@Override
	public T getUnmanaged( List<ISearchParameter> searchParams, boolean includeDeleted ) {
		Criteria criteria = getSearchCriteria(searchParams);
		if ( !includeDeleted ) criteria.add( Restrictions.eq( "cmsMetadata.deleted", false) );
		T obj = (T)criteria.uniqueResult();
		if (obj != null) session().evict(obj); 
		return obj;		
	}	
	
	/**
	 * Return any number of items, limited by pagination and filtering options
	 */	
	@Override
	public List<T> getAll( IPaginationInfo pagination, List<ISearchParameter> searchParams ) {
		Criteria criteria = getSearchCriteria(searchParams);
		/*
		 * Uses projections to calculate maximum number of results before limiting them for pagination. This
		 * performs decently in non cursor-oriented databases, such as MySQL.
		 */
		criteria.setProjection( Projections.rowCount() );
		pagination.setTotal( (Integer)criteria.uniqueResult() );
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		logger.debug( "TOTAL DECISION:" + pagination.getTotal() );
		
		/*
		 * Application pagination restrictions. Limit = 0 is a special case. Clients may send this to retrieve 
		 * only pagination info w/o results.
		 * Hibernate silently ignores this criterion, so we handle it separately
		 */
		if ( pagination.getLimit() == 0 ) return new ArrayList<T>();
		
//		criteria = getSearchCriteria(searchParams);
//		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		
		criteria.setFirstResult( pagination.getFrom() );
		criteria.setMaxResults( pagination.getLimit() );
		return Collections.checkedList( criteria.list(), type );		
	}
	
	@Override
	public ICpv getCpv(String cpvCode){
		Criteria criteria = session().createCriteria( ICpv.class );
		criteria.add( Restrictions.eq("cpvCode", cpvCode) );
		ICpv cpv = (ICpv)criteria.uniqueResult();	
		
		
//		if (cpv != null) {
//			cpv.finalizeEntity();
//			session().evict(cpv);
//		}
		return cpv;
	}
	
	
	@Override
	public List<IPayment> getPaymentsForRequest(IPaginationInfo pagination, List<ISearchParameter> searchParams, IProcurementRequest request){
		Criteria criteria = session().createCriteria( IPayment.class );
		logger.debug( "Client: " + getAccessControl().getClass() );
		logger.debug( "Can view deleted payments: " + getAccessControl().canViewDeleted() );

		if ( !getAccessControl().canViewDeleted() ) {
			criteria = criteria.add( Restrictions.eq( "cmsMetadata.deleted", false ) );
		}
		for ( ISearchParameter searchParam: searchParams ) {
			logger.debug( "Adding search criterion: " + searchParam.toString() );
			criteria = criteria.add( searchParam.toHibernateCriterion() );
		}
		
		criteria.createAlias( "paymentItems", "paymentItems" );
		criteria.createAlias( "paymentItems.contractItem", "contractItem" );
		criteria.createAlias( "contractItem.procurementRequest", "request" );
		criteria.add( Restrictions.eq( "request.id", request.getId() ) );
		
		criteria = criteria.addOrder( Order.desc( "cmsMetadata.submissionTime" ) );
		
		/*
		 * Uses projections to calculate maximum number of results before limiting them for pagination. This
		 * performs decently in non cursor-oriented databases, such as MySQL.
		 */
		criteria.setProjection( Projections.rowCount() );
		pagination.setTotal( (Integer)criteria.uniqueResult() );
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		logger.debug( "TOTAL DECISION:" + pagination.getTotal() );
		
		/*
		 * Application pagination restrictions. Limit = 0 is a special case. Clients may send this to retrieve 
		 * only pagination info w/o results.
		 * Hibernate silently ignores this criterion, so we handle it seperately
		 */
		if ( pagination.getLimit() == 0 ) return new ArrayList<IPayment>();
		criteria.setFirstResult( pagination.getFrom() );
		criteria.setMaxResults( pagination.getLimit() );	

		List<IPayment> result = Collections.checkedList( criteria.list(), type );
		for (IPayment payment:result){
			payment.finalizeEntity();
//			for (IPaymentItem paymentItem:payment.getPaymentItems()){
//				if (paymentItem.getContractParty() != null)
//					session().evict(paymentItem.getContractParty());
//				if (paymentItem.getContractItem() != null){
//					if (paymentItem.getContractItem().getProcurementRequest() != null)
//						session().evict(paymentItem.getContractItem().getProcurementRequest());
//					session().evict(paymentItem.getContractItem());
//				}
//				session().evict(paymentItem);
//			}
			session().evict(payment);
		}
		return 	result;
	}

	@Override
	public List<IPayment> getPaymentsForContract(IPaginationInfo pagination, List<ISearchParameter> searchParams, IContract contract){
		Criteria criteria = session().createCriteria( IPayment.class );
		logger.debug( "Client: " + getAccessControl().getClass() );
		logger.debug( "Can view deleted payments: " + getAccessControl().canViewDeleted() );

		if ( !getAccessControl().canViewDeleted() ) {
			criteria = criteria.add( Restrictions.eq( "cmsMetadata.deleted", false ) );
		}
		for ( ISearchParameter searchParam: searchParams ) {
			logger.debug( "Adding search criterion: " + searchParam.toString() );
			criteria = criteria.add( searchParam.toHibernateCriterion() );
		}
		
		criteria.createAlias( "paymentItems", "paymentItems" );
		criteria.createAlias( "paymentItems.contractItem", "contractItem" );
		criteria.createAlias( "contractItem.contract", "contract" );
		criteria.add( Restrictions.eq( "contract.id", contract.getId() ) );
		
		criteria = criteria.addOrder( Order.desc( "cmsMetadata.submissionTime" ) );
		
		/*
		 * Uses projections to calculate maximum number of results before limiting them for pagination. This
		 * performs decently in non cursor-oriented databases, such as MySQL.
		 */
		criteria.setProjection( Projections.rowCount() );
		pagination.setTotal( (Integer)criteria.uniqueResult() );
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		logger.debug( "TOTAL DECISION:" + pagination.getTotal() );
		
		/*
		 * Application pagination restrictions. Limit = 0 is a special case. Clients may send this to retrieve 
		 * only pagination info w/o results.
		 * Hibernate silently ignores this criterion, so we handle it seperately
		 */
		if ( pagination.getLimit() == 0 ) return new ArrayList<IPayment>();
		criteria.setFirstResult( pagination.getFrom() );
		criteria.setMaxResults( pagination.getLimit() );	

		List<IPayment> result = Collections.checkedList( criteria.list(), type );
		for (IPayment payment:result){
			payment.finalizeEntity();
			session().evict(payment);
		}
		return 	result;
	}	
	
	/**
	 * Check if an entity with the specified id exists and is not deleted
	 */
	@Override
	public boolean exists( String id ) {
		T obj = get( id, false );
		if (obj != null) session().evict(obj); // remove that object from session in order to avoid conflicts with other functions loading it elsewhere.
		return (obj != null);
	}
	
	/**
	 * there exists at least one object matching these criteria
	 */
	@Override
	public boolean exist( List<ISearchParameter> searchParams ) {
		Criteria criteria = getSearchCriteria(searchParams);
		/*
		 * Uses projections to calculate maximum number of results before limiting them for pagination. This
		 * performs decently in non cursor-oriented databases, such as MySQL.
		 */
		criteria.setProjection( Projections.rowCount() );
		return (((Integer)criteria.uniqueResult()).intValue() > 0);
	}	
		
	/**
	 * Check if an entity that satisfies the specified criteria exists
	 */
//	@Override
//	public boolean exists( List<ISearchParameter> searchParams ) {
//		return get( searchParams ) != null;
//	}	
	
	private void deleteDocuments( List<IDocument> docs ) {		
		for ( IDocument doc: docs ) {
			try {
					getDocumentDao().delete(doc);
			}
			catch ( Exception e ) { }				
		}
	}
		
	
	private void saveCpvCodesOfcontractItem(IContractItem contractItem){
		Criteria cpvCriteria;
		ICpv cpvItem;
		ICpv cpv;
		Iterator<ICpv> iterator = contractItem.getCpvCodes().iterator();
		Set<ICpv> cpvCodes = new LinkedHashSet<ICpv>(contractItem.getCpvCodes().size());
		while (iterator.hasNext())
		{
			cpv = (ICpv)iterator.next();
			if (!session().contains(cpv)){
				cpvCriteria = session().createCriteria( ICpv.class );
				cpvCriteria.add( Restrictions.eq("cpvCode", cpv.getCpvCode()) );
				cpvItem = (ICpv)cpvCriteria.uniqueResult();

				iterator.remove();
				
				if ( cpvItem != null ) {
					cpvCodes.add(cpvItem);								
				}
				else {
					session().save(cpv);
					cpvCodes.add(cpv);
				}
			}
		}
		contractItem.getCpvCodes().addAll(cpvCodes);
	}
	
	/**
	 * Save all entities. This occurs as a single transaction.
	 * @param decisions
	 * @return
	 * @throws DecisionReferenceNotFoundException
	 */
	@Override
	public List<IDecisionStorageReference> save( List<T> decisions ) throws DecisionReferenceNotFoundException, ForbiddenOperationException {
		List<IDocument> savedDocuments = new ArrayList<IDocument>();
		List<IDecisionStorageReference> references = new ArrayList<IDecisionStorageReference>();
		getAccessControl().checkAccess( OdeAccessControl.Operation.WRITE, decisions.toArray(  new IPublicOrganizationDecision[0] ) ) ;

		try {
			for ( IPublicOrganizationDecision decision: decisions ) {
				logger.debug( "Initializing metadata" );
				getCms().initialize( decision );

				if (( decision instanceof IContract ) || ( decision instanceof INotice ) || ( decision instanceof IProcurementRequest )){
					for (IContractItem contractItem:decision.getContractItems()){
						saveCpvCodesOfcontractItem(contractItem);
						if ( decision instanceof IContract ){
							contractItem.setContract((IContract)decision);
						}
						else{
							if ( decision instanceof IProcurementRequest ){
								contractItem.setProcurementRequest((IProcurementRequest)decision);
							}
							else{
								if ( decision instanceof INotice ){
									contractItem.setNotice((INotice)decision);
								}
							}
								
						}
					}					
				}
				
				if ( decision instanceof IPayment ) {
					IPayment payment = (IPayment)decision;
			
					for (IPaymentItem paymentItem:payment.getPaymentItems()){
						saveCpvCodesOfcontractItem(paymentItem.getContractItem());
					}
				}		
				
				decision.getDocument().initKey( decision.getId() );
				getDocumentDao().stampAndSave(decision.getDocument(), decision.getId() );
				savedDocuments.add( decision.getDocument() );
				logger.debug( "Saving" );
				
				session().save( decision );
//				session().flush();		
				logger.debug( "Saved" );
				IDecisionStorageReference ref = decision.getStorageReference( accessControl.getClient() ); 
				references.add( ref );
			}
		}	
		catch ( Exception e ) {
			e.printStackTrace();
			deleteDocuments( savedDocuments );
			throw new InternalErrorException( e.getMessage() );
		}
		return references;	
	}
	
	/**
	 * Delete a decision. If purge is true, delete it from the database.
	 * Returns info about the deleted decision.
	 * @param <T>
	 * @param id
	 * @param purge
	 * @param cancel
	 * @param cl
	 * @throws DecisionNotFoundException
	 */	
	private IDecisionStorageReference deleteOrPurgeOrCancel( String id, boolean purge, boolean cancel, String reason, String deletionType, Integer deletedFromUserId ) throws DecisionNotFoundException {
		logger.debug( "Checking access" );
		T decision = get( id, getAccessControl().canViewDeleted() );
		logger.debug( "Decision: " + decision );
		if ( decision == null ) {
			throw new DecisionNotFoundException();
		}
		IDecisionStorageReference ref = decision.getStorageReference( accessControl.getClient() );
		if ( purge ) {
			logger.debug( "Purging" );
			getAccessControl().checkAccess( OdeAccessControl.Operation.PURGE, decision );
			try {
					getDocumentDao().delete(decision.getDocument());
			}
			catch ( Exception e ) {
				throw new InternalErrorException( "Failed to delete document:" + decision.getId() );
			}
			session().delete( decision );			
		}
		else
		{
			if (cancel){
				logger.debug( "Marking as cancelled" );
				getAccessControl().checkAccess( OdeAccessControl.Operation.CANCEL, decision );
				decision.markCancelled(reason, deletionType, deletedFromUserId);			
				session().save( decision );				
			}
			else {
				logger.debug( "Marking as deleted" );
				getAccessControl().checkAccess( OdeAccessControl.Operation.DELETE, decision );
				decision.markDeleted(reason, deletionType, deletedFromUserId);			
				session().save( decision );
			}			
		}

		return ref;
	}
	
	@Override
	public IDecisionStorageReference delete( String id, String reason, String deletionType, Integer deletedFromUserId ) {
		return deleteOrPurgeOrCancel( id, false, false, reason, deletionType, deletedFromUserId );
	}
	
	@Override
	public IDecisionStorageReference cancel( String id, String reason, String cancellationType, Integer cancelledFromUserId ) {
		return deleteOrPurgeOrCancel( id, false, true, reason, cancellationType, cancelledFromUserId );
	}	
	
	@Override
	public IDecisionStorageReference purge( String id ) {
		return deleteOrPurgeOrCancel( id, true, false, null, null, null );
	}
	
	private List<Object> getOrphanedNodes( T saved ) {
		 List<Object> orphaned = new ArrayList<Object>();
		 orphaned.add( saved.getDocument() );
//		 if ( type.isAssignableFrom( IContract.class ) ) {
//			 IContract contract = (IContract)saved;
//			 orphaned.add( contract.getPrimaryParty() );
//		 }
//		 else if ( type.isAssignableFrom( IPayment.class ) ) {
//			 IPayment payment = (IPayment)saved;
//			 orphaned.add( payment.getPrimaryParty() );
//		 }
		 return orphaned;
	}
	/**
	 * Updates a saved decision
	 * @param decision
	 */
//	@Override
//	public IDecisionStorageReference update( T decision ) throws DecisionNotFoundException{
//		if ( type.isAssignableFrom( IProcurementRequest.class ) ){
//			IPublicOrganizationDecision approvalDecision = ((IProcurementRequest)decision).getApprovalDecision();
//			T saved =  get( decision.getId(), false );
//			if ((((IProcurementRequest)saved).getApprovalDecision() != null) && (((IProcurementRequest)decision).getApprovalDecision() == null)){
//				throw new ProcurementRequestNullAprovalException();
//			}
//				
//			if (((IProcurementRequest)decision).getApprovalDecision() != null){
//				((IProcurementRequest)saved).setApprovalDecision(((IProcurementRequest)decision).getApprovalDecision());
//				return updateDecision(saved);
//			}
//			else
//				return updateDecision(decision);
//		}
//		else
//			return updateDecision(decision);
//	}
	
	@Override
	public IDecisionStorageReference update( T decision ) throws DecisionNotFoundException{
		boolean saveDocument = true;
		byte[] data = null;
		List<Object> orphaned;
		String oldUserEmail;
		String oldIssuerEmail;
		IDecisionStorageReference ref = null;
		logger.debug( "Loading old decision" );		
		T saved =  get( decision.getId(), false );		
		if ( saved == null ) throw new DecisionNotFoundException( decision.getId() );
		
		oldIssuerEmail = saved.getIssuerEmail();
		oldUserEmail = bridge.getDiavgeiaUser(saved.getCmsMetadata().getUserDiavgeiaId()).getEmail();
		
		getAccessControl().checkAccess( OdeAccessControl.Operation.UPDATE, decision );
		// TODO: Handle case of different user
		decision.setCmsMetadata( saved.getCmsMetadata().copy() );
		getCms().update( decision );
		/*
		 * Handle null document
		 */		
		if ( decision.getDocument() == null ) {
			logger.debug( "New decision has no document, keeping old one" );
			IDocument document = saved.getDocument().copy();			
			decision.setDocument( document );
			saveDocument = false;
		}		
		else {
			// Data will be lost in merge, so keep a backup here
			data = decision.getDocument().getData();
			logger.debug( "New decision has new document, data size: " + decision.getDocument().getData().length );
			decision.getDocument().copyKeyFrom( saved.getDocument() );
			saveDocument = true;
		}		
		// Set this due to all-delete-orphan cascade
		if ( decision.getRelatedAdas() == null ) decision.setRelatedAdas( new ArrayList<Ada>() );
		
		// Save soon-to-be-orphaned associations
		orphaned = getOrphanedNodes( saved );		
		
		if (( decision instanceof IContract ) || ( decision instanceof INotice ) || ( decision instanceof IProcurementRequest )){
			for (IContractItem contractItem:decision.getContractItems()){
				saveCpvCodesOfcontractItem(contractItem);
			}					
		}
		
		if ( decision instanceof IPayment ) {
			IPayment payment = (IPayment)decision;
	
			for (IPaymentItem paymentItem:payment.getPaymentItems()){
				saveCpvCodesOfcontractItem(paymentItem.getContractItem());
			}
		}
		
		logger.debug( "Document before merge: " + decision.getDocument() );
		logger.debug( "Related adas before merge: " + decision.getRelatedAdas() );
		logger.debug( "Saved related adas before merge: " + saved.getRelatedAdas() );
		logger.debug( "Merging decision" );		
		decision = (T)session().merge( decision );
		logger.debug( "Done" );
		logger.debug( "Document after merge: " + decision.getDocument() );
		logger.debug( "Related adas after merge: " + decision.getRelatedAdas() );
		/*
		session().delete( saved );		
		// Save here, to avoid file overwrite on failed transaction
		session().save( decision );
		*/
		if ( saveDocument ) {								
			try {				
					// Restore backup data
					decision.getDocument().setData( data );
					logger.debug( "Stamping new document" );
					logger.debug( "Document key: " + decision.getDocument().getKey() );
					getDocumentDao().stampAndSave(decision.getDocument(), decision.getId() );
					logger.debug( "Re-saving decision after document update" );
					session().update( decision ); // Save again to persist stamp

					logger.debug( "Done" );
			}
			catch ( Exception e ) {
				throw new InternalErrorException( "Failed to save document: " + decision.getId() );
			}
		}
		logger.debug( "Deleting orphaned nodes" );
		for ( Object obj: orphaned ) {
			session().delete( obj );
		}

		ref = decision.getStorageReference( accessControl.getClient() );
		ref.getEmails().add(oldUserEmail);
		ref.getEmails().add(oldIssuerEmail);		
		
		logger.debug( "Flushing session" );
//		session().flush();
		logger.debug( "Returning" );
		
		return ref;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.dao.IDecisionBaseDAO#getCms()
	 */
	public ICms getCms() {
		return cms;
	}

	@Override
	public void setCms(ICms cms) {
		this.cms = cms;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.dao.IDecisionBaseDAO#getDocumentDao()
	 */
	@Override
	public IDocumentDAO getDocumentDao() {
		return documentDao;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.dao.IDecisionBaseDAO#setDocumentDao(gr.opengov.agora.dao.IDocumentDAO)
	 */
	@Override
	public void setDocumentDao(IDocumentDAO documentDao) {
		this.documentDao = documentDao;
	}
	
	@Override
	public IContractParty getContractParty(String afm){
		Criteria criteria = session().createCriteria( IContractParty.class );
		logger.debug( "Client: " + getAccessControl().getClass() );
		criteria.add( Restrictions.eq( "afm", afm ) );
		return (IContractParty)criteria.list().get(0);				
	}
}
