package gr.opengov.agora.bridges.diavgeia.domain;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table( name="foreis" )
public class DiavgeiaOrganization {
	
	@Transient
	private final String MINISTRY = "ministry";
	@Transient
	private final String INDEPENDENT_AUTH = "independentAuthority";
	@Transient
	private final String COURT = "court";
	@Transient
	private final String PERIPHERY = "periphery";
	@Transient
	private final String MUNICIPALITY = "municipality";	
	@Transient
	private final String OTHER = "other";
	
	@Id
	@Column( name="pb_id" )
	private Integer id;
	
	@Column
	private String name;
	
	@Column(name = "pb_supervisor_id")
	private Integer supervisorId;
	
	@OneToMany( mappedBy="organization" )
	@javax.persistence.OrderBy("id")
	private Set<DiavgeiaOdeMember> odeMembers;
	
	@OneToMany( mappedBy="organization" )
	@javax.persistence.OrderBy("id")
	private Set<DiavgeiaUnit> units;
	
	@OneToMany( mappedBy="organization" )
	@javax.persistence.OrderBy("id")
	private Set<DiavgeiaSigner> signers;
	
	@OneToMany( mappedBy="organization" )
	@javax.persistence.OrderBy("id")
	private Set<DiavgeiaUser> users;
		

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public Set<DiavgeiaUnit> getUnits() {
		return units;
	}

	public void setUnits(Set<DiavgeiaUnit> units) {
		this.units = units;
	}

	public Set<DiavgeiaSigner> getSigners() {
		return signers;
	}

	public void setSigners(Set<DiavgeiaSigner> signers) {
		if (this.signers == null) {
			this.signers = new LinkedHashSet<DiavgeiaSigner>();
		}
		this.signers.clear();
		this.signers.addAll(signers);
	}

	public Set<DiavgeiaUser> getUsers() {
		return users;
	}

	public void setUsers(Set<DiavgeiaUser> users) {
		this.users = users;
	}

	public Set<DiavgeiaOdeMember> getOdeMembers() {
		return odeMembers;
	}

	public void setOdeMembers(Set<DiavgeiaOdeMember> odeMembers) {
		this.odeMembers = odeMembers;
	}
	
	@Transient
	public String getType() {
		if ( supervisorId == 324 ) return MINISTRY;
		else if ( supervisorId == 323 ) return INDEPENDENT_AUTH;
		else if ( supervisorId == 322 ) return COURT;
		else if ( supervisorId == 5000 ) return PERIPHERY;
		else if ( supervisorId >= 5001 && supervisorId <= 5013 ) return MUNICIPALITY;
		else return OTHER;
	}
	
}
