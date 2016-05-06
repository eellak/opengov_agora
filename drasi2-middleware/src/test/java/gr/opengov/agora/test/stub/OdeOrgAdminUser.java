package gr.opengov.agora.test.stub;

import gr.opengov.agora.security.IClient;
//import gr.opengov.agora.test.ContractServiceTest;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OdeOrgAdminUser implements IClient {
	private static final Logger logger = LoggerFactory.getLogger(OdeOrgAdminUser.class);
	private Integer userId = 17;
	private Integer orgId = 366;
	private Integer maximumDecisionsRead = 10;
	private Integer maximumDecisionsWrite = 3;
	private String username = "366_admin";
	private String password = "366";

	@Override
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	@Override
	public Boolean hasRole(String role) {
		return true;
	}

	@Override
	public Boolean isAdminForOrg(Integer orgId) {
		return orgId.equals( this.orgId );
	}

	@Override
	public Boolean isAdmin() {
		return false;
	}

	@Override
	public Boolean isAnonymous() {
		return false;
	}
	
	@Override
	public Boolean isUserForOrg(Integer orgId) {
		return orgId.equals( this.orgId );
	}
	
	@Override
	public Set<Integer> getOrgs() {
		Set<Integer> ret = new HashSet<Integer>();
		ret.add( orgId );
		return ret;
	}

	@Override
	public Integer getMaximumDecisionsRead() {
		return maximumDecisionsRead;
	}

	public void setMaximumDecisionsRead(Integer maximumDecisionsRead) {
		this.maximumDecisionsRead = maximumDecisionsRead;
	}

	@Override
	public Integer getMaximumDecisionsWrite() {
		return maximumDecisionsWrite;
	}

	public void setMaximumDecisionsWrite(Integer maximumDecisionsWrite) {
		this.maximumDecisionsWrite = maximumDecisionsWrite;
	}

	@Override
	public String getEmail() {
		return "xkorakidis@localhost";
	}
	
	@Override
	public String getAddress() {
		return "unknown";
	}

	@Override
	public void addServletRequestInfo(ServletRequest request) {
		
	}

	@Override
	public String getName() {
		return "stubuser";
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
