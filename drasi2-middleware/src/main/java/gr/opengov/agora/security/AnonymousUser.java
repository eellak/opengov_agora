package gr.opengov.agora.security;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;

public class AnonymousUser implements IClient {
	private String address = "";

	public String getRealm(){
		return "anonymous";
	}
	
	@Override
	public Integer getUserId() {
		return null;
	}

	@Override
	public Boolean hasRole(String role) {
		return role.equals("ROLE_ANONYMOUS");
	}

	@Override
	public Boolean isAdminForOrg(Integer orgId) {
		return false;
	}

	@Override
	public Boolean isAdmin() {
		return false;
	}

	@Override
	public Boolean isAnonymous() {
		return true;
	}

	@Override
	public Set<Integer> getOrgs() {
		return new HashSet<Integer>();
	}

	@Override
	public String getEmail() {
		return "anonymous";
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public Integer getMaximumDecisionsRead() {
		return 100;
	}

	@Override
	public Integer getMaximumDecisionsWrite() {
		return 0;
	}

	@Override
	public void addServletRequestInfo(ServletRequest request) {
		this.address = request.getRemoteAddr();
	}

	@Override
	public String getName() {
		return "anonymous";
	}

	@Override
	public Boolean isUserForOrg(Integer orgId) {
		return false;
	}

	@Override
	public String getPassword() {
		return "anonymous";
	}

	@Override
	public String getUsername() {
		return "anonymous";
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
