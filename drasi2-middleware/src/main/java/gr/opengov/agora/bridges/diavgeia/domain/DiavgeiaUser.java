package gr.opengov.agora.bridges.diavgeia.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name="auth" )
public class DiavgeiaUser {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String username;
	
	@Column
	private String firstname;
	
	@Column
	private String lastname;
	
	@Column
	private String realm;
	
	@Column
	private String email;	
	
	@ManyToOne
	@JoinColumn( name="start_pb_id" )
	private DiavgeiaOrganization organization;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public DiavgeiaOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(DiavgeiaOrganization organization) {
		this.organization = organization;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFistname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
}
