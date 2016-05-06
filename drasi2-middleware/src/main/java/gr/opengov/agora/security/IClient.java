package gr.opengov.agora.security;

import java.util.Set;

import javax.servlet.ServletRequest;

public interface IClient {
	public abstract Integer getUserId();
	public abstract Boolean hasRole(String role);
	public abstract Boolean isAdminForOrg(Integer orgId);
	public abstract Boolean isUserForOrg(Integer orgId);
	public abstract Boolean isAdmin();
	public abstract Boolean isAnonymous();	
	public abstract Set<Integer> getOrgs();
	public abstract String getName();
	public abstract String getPassword();
	public abstract String getUsername();
	public abstract void setPassword(String password);
	public abstract void setUsername(String username);	
	public abstract String getEmail();
	public abstract String getAddress();
	public abstract Integer getMaximumDecisionsRead();
	public abstract Integer getMaximumDecisionsWrite();	
	public abstract void addServletRequestInfo( ServletRequest request );
}