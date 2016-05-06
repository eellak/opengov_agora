package gr.opengov.agora.validation;

import java.util.Map;

import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPublicOrganizationDecision;

public interface IContractValidator extends IPublicOrganizationDecisionValidator{

	public abstract IValidation validateCreate(IContract contract, Map<IAda, IPublicOrganizationDecision> relatedDecisions);

	public abstract IValidation validateUpdate(IContract contract, Map<IAda, IPublicOrganizationDecision> relatedDecisions);

}