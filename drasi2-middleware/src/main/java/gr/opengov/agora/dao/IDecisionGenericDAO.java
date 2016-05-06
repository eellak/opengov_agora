package gr.opengov.agora.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;

import gr.opengov.agora.cms.ICms;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractParty;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.exceptions.DecisionReferenceNotFoundException;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.util.IPaginationInfo;

public interface IDecisionGenericDAO<T extends IPublicOrganizationDecision> {
	public T get( String id, boolean includeDeleted );
	public T get( List<ISearchParameter> searchParams );
	public List<T> getAll( IPaginationInfo pagination, List<ISearchParameter> searchParams );

	public boolean exists( String id );
//	public boolean exists( List<ISearchParameter> searchParams );
	
	public List<IDecisionStorageReference> save( List<T> decisions ) throws DecisionReferenceNotFoundException;
	public IDecisionStorageReference delete( String id, String reason, String deletionType, Integer deletedFromUserId ) throws DecisionNotFoundException;
	public IDecisionStorageReference purge( String id ) throws DecisionNotFoundException;
	public IDecisionStorageReference update( T decision );
	
	public IDocumentDAO getDocumentDao();

	public void setDocumentDao(IDocumentDAO documentDao);
	public void setCms(ICms cms);
	public IContractParty getContractParty(String afm);
	public T getUnmanaged(String id, boolean includeDeleted);
	public T getUnmanaged(List<ISearchParameter> searchParams, boolean includeDeleted);
	public List<IPayment> getPaymentsForRequest(IPaginationInfo pagination, List<ISearchParameter> searchParams, IProcurementRequest request);
	public List<IPayment> getPaymentsForContract(IPaginationInfo pagination, List<ISearchParameter> searchParams, IContract contract);
	public ICpv getCpv(String cpvCode);
	public boolean exist( List<ISearchParameter> searchParams);
	IDecisionStorageReference cancel(String id, String reason, String cancellationType, Integer cancelledFromUserId);
	
}