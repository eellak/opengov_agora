package gr.opengov.agora.cms;

import gr.opengov.agora.domain.ICmsEntity;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.security.IClient;
import gr.opengov.agora.util.IAdaGenerator;

public interface ICms {

	public abstract void update( IPublicOrganizationDecision entity);
	public abstract void initialize( IPublicOrganizationDecision contract);
	public abstract void setClient( IClient user );
	public abstract void setAdaGenerator(IAdaGenerator adaGenerator);

}