package gr.opengov.agora.security;

import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.security.OdeAccessControl.Operation;

import java.util.Collection;

import org.springframework.security.core.Authentication;

public interface IAccessControl {
	public abstract boolean canViewDeleted();
	public abstract void checkAccess(Operation op, IPublicOrganizationDecision... decisions);
	public abstract IClient getClient();
	public abstract void setClientFactory( IClientFactory clientFactory );
	public abstract IClientFactory getClientFactory();
}