package gr.opengov.agora.bridges.diavgeia;

import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaOdeMember;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaOrganization;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaUnit;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaUser;
import gr.opengov.agora.domain.AuthenticationProfile;
import gr.opengov.agora.domain.IOrganization;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.InvalidDiavgeiaDecisionTypeException;
import gr.opengov.agora.model.OrganizationOXM;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IAgoraDiavgeiaBridge {
	boolean isValidOrganization(Integer organizationId);
		
	public abstract boolean isValidUnitForOrganization(Integer unitId, Integer organizationId);
	
	boolean areValidSignersForOrganization(List<Integer> signersId, Integer organizationId );
	boolean areValidSignersForOrganization(List<Integer> signersId, Integer organizationId, Set<Integer> invalidIndex );
	
	String getAdaOfDocument(String documentId);
	AuthenticationProfile getAuthenticationProfile(Integer userId);
	public boolean isValidAda(String ada);	
	public boolean isValidAda(String ada, Integer decisionType);
	
	public void saveDecision( IPublicOrganizationDecision decision ) throws InvalidDiavgeiaDecisionTypeException;
	public void updateDecision( IPublicOrganizationDecision decision ) throws InvalidDiavgeiaDecisionTypeException;
	public void deleteDecision( String ada );
	public DiavgeiaUser getDiavgeiaUser(Integer userId);

	DiavgeiaOrganization getOdeWithUnits(String itemId, Integer status);

	DiavgeiaOdeMember getOde(String itemId);

	List<DiavgeiaOdeMember> getOdeMembers(String orgId, boolean includeSupervisedMembers, boolean getUnits, boolean getSigners, String unitId);

}