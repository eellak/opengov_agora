package gr.opengov.agora.validation;

import java.util.Map;

import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.security.IAccessControl;

public interface IValidator<T extends IPublicOrganizationDecision> {
	public IValidation validateCreate( T decision, Map<IAda, IPublicOrganizationDecision> relatedDecisions);
	public IValidation validateUpdate( T decision, Map<IAda, IPublicOrganizationDecision> relatedDecisions);
}