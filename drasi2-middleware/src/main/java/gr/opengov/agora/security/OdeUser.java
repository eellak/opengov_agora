package gr.opengov.agora.security;

import gr.opengov.agora.dao.IDecisionGenericDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class OdeUser implements Authentication, IClient {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(OdeUser.class);
	private static final long serialVersionUID = 3851318332034877865L;
	private Integer userId;
	private String username;
	private String password;
	private String email = "test@localhost";
	private String address = "unknown";
	private Collection<GrantedAuthority> authorities;
	private boolean authenticated;
	private Set<Integer> orgs = new HashSet<Integer>();
	private Properties configProperties;
	private Set<Integer> units = new HashSet<Integer>();
	
	private IDecisionGenericDAO decisionDao;

	@Override
	public void addServletRequestInfo(ServletRequest request) {
		address = request.getRemoteAddr();
	}
	
	public void authenticate() {
		this.authenticated = true;
	}
	
	@Override
	public String getAddress() {
		return address;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#getAuthorities()
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#getCredentials()
	 */
	@Override
	public Object getCredentials() { 		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#getDetails()
	 */
	@Override
	public Object getDetails() {
		return null;
	}


	@Override
	public String getEmail() {
		return email;
	}


	@Override
	public Integer getMaximumDecisionsRead() {
		return Integer.parseInt( (String)configProperties.get( "client.ode.maxDecisionsRead" ) );
	}


	@Override
	public Integer getMaximumDecisionsWrite() {			
		return Integer.parseInt( (String)configProperties.get( "client.ode.maxDecisionsWrite" ) );		
	}


	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#getName()
	 */
	@Override
	public String getName() {
		return username;
	}


	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#getOrgs()
	 */
	@Override
	public Set<Integer> getOrgs() {
		return this.orgs;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#getPrincipal()
	 */
	@Override
	public Object getPrincipal() {
		return username;
	}
	
	@Override
	public Integer getUserId() {
		return userId;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}	
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#hasRole(java.lang.String)
	 */
	@Override
	public Boolean hasRole( String role ) {
		for ( GrantedAuthority auth: authorities ) {
			if ( auth.getAuthority().equals( role ) ) return true;
		}
		return false;
	}
		
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#isAdmin()
	 */
	@Override
	public Boolean isAdmin( ) {
		return hasRole( "ROLE_ADMIN" );
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#isAdminForOrg(java.lang.Integer)
	 */
	@Override
	public Boolean isAdminForOrg( Integer orgId ) {
		if ( orgId == null ) return false;
		if ( !hasRole( "ROLE_ORG_ADMIN" ) ) return false;
		return orgs.contains( orgId );
	}

	@Override
	public Boolean isAnonymous() {
		return false;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#isAuthenticated()
	 */
	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	public OdeUser() {
		authorities = new ArrayList<GrantedAuthority>();	
		authorities.add(new GrantedAuthorityImpl( "ROLE_ORG_USER" ) );
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IClient#setAuthenticated(boolean)
	 */
	@Override
	public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
		this.authenticated = authenticated;
		
	}

	public void setDecisionDao(IDecisionGenericDAO decisionDao) {
		this.decisionDao = decisionDao;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setExtraRoles( String... extraRoles ) {
		for ( String role: extraRoles ) {
			authorities.add( new GrantedAuthorityImpl( role ) );
		}	
	}

	public void setOrganizationId( Integer orgId ) {
		this.orgs.add( orgId );
	}
	
	public void setProperties( Properties properties ) {
		this.configProperties = properties;
	}
	
	public void setRealm( String realm ) {
		if ( realm == null ) return;
		if ( realm.equals( "admin_foreas" ) ) {			
			authorities.add( new GrantedAuthorityImpl( "ROLE_ORG_ADMIN" ) );
		}
		if ( realm.equals( "admin" ) ) {
			authorities.add( new GrantedAuthorityImpl( "ROLE_ADMIN" ) );
			authorities.add( new GrantedAuthorityImpl( "ROLE_ORG_ADMIN" ) );
		}		
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
	}	
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		if ( isAnonymous() ) buf.append( "Anonymous User" );
		else {
			buf.append( "User: " + username + "\n" );
			for ( GrantedAuthority auth: authorities ) {
				buf.append( "Role: " + auth.getAuthority() + "\n" );
			}
			for ( Integer id: orgs ) {
				buf.append( "Org: " + id + "\n" );		
			}
			for ( Integer id: units ) {
				buf.append( "Unit: " + id + "\n" );
			}
		}
		return buf.toString();
	}

	@Override
	public Boolean isUserForOrg(Integer orgId) {
		boolean found = false;
		for (Integer org:orgs){
			found = found || (org.intValue() == orgId.intValue());
		}
		return found;
	}
}