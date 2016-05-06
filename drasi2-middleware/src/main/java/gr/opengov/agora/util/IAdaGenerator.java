package gr.opengov.agora.util;

import gr.opengov.agora.domain.IPublicOrganizationDecision;

public interface IAdaGenerator {
	public String getNewId( IPublicOrganizationDecision entity );
}
