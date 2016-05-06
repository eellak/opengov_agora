package gr.opengov.agora.security;

import gr.opengov.agora.dao.IDecisionGenericDAO;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.PublicOrganizationDecision;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.service.IDecisionUtilsService;
import gr.opengov.agora.util.Constants;

import java.util.Calendar;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class OdeAccessControl implements IAccessControl {
	public enum Operation { READ, WRITE, DELETE, UPDATE, PURGE, CANCEL }
	private static final Logger logger = LoggerFactory.getLogger(OdeAccessControl.class);
	
	private IDecisionUtilsService decisionService;
	private IClientFactory clientFactory;

	public void setDecisionService(IDecisionUtilsService decisionService) {
		this.decisionService = decisionService;
	}
	
	@Override
	public void setClientFactory( IClientFactory clientFactory ) {
		this.clientFactory = clientFactory;		
	}
	
	@Override
	public IClientFactory getClientFactory() {
		return clientFactory;
	}

	@Override
	public IClient getClient() {
		return clientFactory.getClient();		
	}
	
	@Override
	public boolean canViewDeleted() {
		IClient user = getClient();
		if ( user == null ) return false;
		return user.isAdmin();
	}
	
	/**
	 * The main entry point, all other methods call this
	 * @see gr.opengov.agora.security.IAccessControl#hasAccess(gr.opengov.agora.domain.IPublicOrganizationDecision, gr.opengov.agora.security.AccessControl.Operation)
	 */
	private boolean hasAccess( Operation op, IPublicOrganizationDecision decision ) {	
		logger.error("ID: " + decision.getId());
		logger.error("op: " + op);
		IClient user = getClient();
		if ( user == null ) return false;
		if ( !user.isAdmin() && !user.isAdminForOrg( decision.getOrganizationDiavgeiaId() ) && !user.isUserForOrg( decision.getOrganizationDiavgeiaId() ) ) return false;
		if ((Operation.WRITE == op) && user.isUserForOrg( decision.getOrganizationDiavgeiaId() ) ) return true;
		
		switch ( op ) {
		case PURGE:
			if ( !user.isAdmin() ) return false;
			break;
		}
		
		if ((Operation.DELETE == op) || (Operation.UPDATE == op)){
			return ( user.isAdmin() || isTimeOkForChangeOrRemoveDecision(decisionService.get( decision.getId() ).getCmsMetadata().getSubmissionTime().getTimeInMillis()));
		}
		return true;
	}
	
	private boolean hasAccess( Operation op, IPublicOrganizationDecision... decisions ) {
		for ( IPublicOrganizationDecision decision: decisions ) {
			if ( !hasAccess( op, decision ) ) return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IAccessControl#checkAccess(gr.opengov.agora.model.PublicOrganizationDecision, gr.opengov.agora.security.AccessControl.Operation)
	 */
	@Override
	public void checkAccess( Operation op, IPublicOrganizationDecision... decision ) throws ForbiddenOperationException {		
		if ( !hasAccess( op, decision ) ) throw new ForbiddenOperationException();
	}
	
	
	private boolean isTimeOkForChangeOrRemoveDecision( long submissionTime ) {
		return !(Calendar.getInstance().getTimeInMillis() > (submissionTime + Constants.TIMESLOT_FOR_CHANGE_OR_REMOVE_DECISION) );
	}	
}
