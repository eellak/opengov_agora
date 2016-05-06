package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;
import gr.opengov.agora.exceptions.InternalErrorException;
import gr.opengov.agora.model.ArrayOfOrganizations;
import gr.opengov.agora.util.ApplicationContextProvider;

import java.io.InputStream;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtil;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthenticationProfile implements IAuthenticationProfile, Serializable {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationProfile.class);

    protected String role;
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected List<IOrganization> odeMember;
    protected int userId;

    @Override
	public String getRole() {
		return role;
	}

    @Override
	public void setRole(String role) {
		this.role = role;
	}

    @Override
	public String getUserName() {
		return userName;
	}

    @Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

    @Override
	public String getFirstName() {
		return firstName;
	}

    @Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    @Override
	public String getLastName() {
		return lastName;
	}

    @Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
    @Override
	public String getEmail() {
		return email;
	}

    @Override
	public void setEmail(String email) {
		this.email = email;
	}    

    @Override
	public List<IOrganization> getOdeMember() {
		return odeMember;
	}

    @Override
	public void setOdeMember(List<IOrganization> odeMember) {
		this.odeMember = odeMember;
	}

    @Override
	public int getUserId() {
		return userId;
	}

    @Override
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IAuthenticationProfile ) ) return false;
		IAuthenticationProfile rhs = (IAuthenticationProfile)obj;
		return new EqualsBuilder()
			.append( role, rhs.getRole() )
			.append( userName, rhs.getUserName() )
			.append( firstName, rhs.getFirstName() )
			.append( lastName, rhs.getLastName() )
			.append( email, rhs.getEmail() )
			.append( odeMember, rhs.getOdeMember() )
			// id is used only for persistence, ignore
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append( role )
		.append( userName )
		.append( firstName )
		.append( lastName )
		.append( email )
		.append( odeMember )
		.toHashCode();
	}
	
	@Override
	public void finalizeEntity() {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		final String sep = ", ";
		buffer.append( "Username: " + userName + sep );
		buffer.append( "Firstname: " + firstName + sep );
		buffer.append( "Lastname: " + lastName + sep );
		buffer.append( "email: " + email + sep );
		buffer.append( "Admin: " + role + sep );
		if ( odeMember.isEmpty() ) {
			buffer.append( " - Org --> NONE" + sep );
		}
		else {
			buffer.append( " - Org --> " );
			IOrganization org = odeMember.get(0);
			buffer.append( "Id: " + org.getIdRef() + sep );
			buffer.append( "Name: " + org.getName() + sep );
			buffer.append( "AFM: " + org.getAfm() + sep );
			buffer.append( "organization type: " + org.getOrganizationType());
			// TODO: Add more fields if more debugging is required
		}
		return buffer.toString();
	}
}
