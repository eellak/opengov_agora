package gr.opengov.agora.domain;

public class InvalidPublicOrganizationDecisionReference extends PublicOrganizationDecision {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isValid() {
		return false;
	}
}
