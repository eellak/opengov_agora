package gr.opengov.agora.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.opengov.agora.dao.IDecisionGenericDAO;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.OrganizationSearchParameter;
import gr.opengov.agora.search.StringSearchParameter;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.util.IPaginationInfo;
import gr.opengov.agora.util.PaginationInfo;

public class DecisionUtilsServiceImplReadOnly implements IDecisionUtilsService {
	private static final Logger logger = LoggerFactory.getLogger(DecisionUtilsServiceImplReadOnly.class);
	private IAccessControl accessControl;
	private IDecisionGenericDAO<IPublicOrganizationDecision> dao;
	
	public void setAccessControl(IAccessControl accessControl) {
		this.accessControl = accessControl;
	}
	
	public void setDao(IDecisionGenericDAO<IPublicOrganizationDecision> dao) {
		this.dao = dao;		
	}		

	/* (non-Javadoc)
	 * @see gr.opengov.agora.service.IDecisionUtilsService#getSubmissionTimestamp(java.lang.String)
	 */
	@Override
	public Calendar getSubmissionTimestamp( String id ) throws DecisionNotFoundException {
		return get(id).getCmsMetadata().getSubmissionTime();
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.service.IDecisionUtilsService#get(java.lang.String)
	 */
	@Override
	public IPublicOrganizationDecision get(String id) throws DecisionNotFoundException {
		List<ISearchParameter> searchParams = new ArrayList<ISearchParameter>();
		searchParams.add(new StringSearchParameter("id", id ));
		addGetDecisionsFilters(searchParams);
		List<IPublicOrganizationDecision> decisions = getAll( new PaginationInfo(), searchParams);
		if ( decisions.size() == 0 ) throw new DecisionNotFoundException( id );
		decisions.get(0).finalizeEntity();
		return decisions.get(0);		
	}	
	
	@Override
	public String getNameFromAfm(String afm){
		if (accessControl.getClient().isAnonymous()) return null;
		else return dao.getContractParty(afm).getName();
	}
	
	private List<IPublicOrganizationDecision> getAll( IPaginationInfo pagination, List<ISearchParameter> searchParams ) {
		pagination.setLimit( Math.min( pagination.getLimit(), accessControl.getClient().getMaximumDecisionsRead() ) );
		addGetDecisionsFilters(searchParams);
		List<IPublicOrganizationDecision> decisions = dao.getAll( pagination, searchParams );
		for ( IPublicOrganizationDecision d: decisions ) {
			d.finalizeEntity();
		}
		pagination.setLimit( decisions.size() );
		return decisions;
	}	
	
	/** 
	 *  Adds extra requirements to the query to take care of access control
	 * @param searchParams
	 */
	private void addGetDecisionsFilters(List<ISearchParameter> searchParams) {	
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

}
