package gr.opengov.agora.bridges.diavgeia;

import gr.opengov.agora.bridges.diavgeia.domain.IDiavgeiaDecision;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.InvalidDiavgeiaDecisionTypeException;
import gr.opengov.agora.security.IClient;

public interface IDiavgeiaConverter {

	public abstract IDiavgeiaDecision getDiavgeiaDecision(
			IPublicOrganizationDecision decision, IClient client)
			throws InvalidDiavgeiaDecisionTypeException;
	
	public abstract IDiavgeiaDecision convertDiavgeiaDecision( IPublicOrganizationDecision decision, IClient client, IDiavgeiaDecision target )
		throws InvalidDiavgeiaDecisionTypeException;
}