package gr.opengov.agora.test.stub;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;

import gr.opengov.agora.security.IClient;

public class AdminUser implements IClient {

	private final String ROLE_ADMIN = "ROLE_ADMIN";
	@Override
	public Integer getUserId() {
		return null;
	}

	@Override
	public Boolean hasRole(String role) {
		return (role.equals(ROLE_ADMIN));
	}

	@Override
	public Boolean isAdminForOrg(Integer orgId) {
		return true;
	}

	@Override
	public Boolean isAdmin() {
		return true;
	}

	@Override
	public Boolean isAnonymous() {
		return false;
	}
	
	@Override
	public Boolean isUserForOrg(Integer orgId) {
		return true;
	}	

	@Override
	public Set<Integer> getOrgs() {
		return new HashSet<Integer>();
	}

	@Override
	public String getName() {
		return "adminstub";		
	}

	@Override
	public String getEmail() {
		return "admin366@localhost";
	}

	@Override
	public String getAddress() {
		return null;
	}

	@Override
	public Integer getMaximumDecisionsRead() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Integer getMaximumDecisionsWrite() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void addServletRequestInfo(ServletRequest request) {
		
	}

	@Override
	public String getPassword() {
		return "adminstub";
	}

	@Override
	public String getUsername() {
		return "adminstub";
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUsername(String username) {
		// TODO Auto-generated method stub
		
	}
}
