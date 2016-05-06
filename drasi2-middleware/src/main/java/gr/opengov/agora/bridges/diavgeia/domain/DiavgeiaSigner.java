package gr.opengov.agora.bridges.diavgeia.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table( name="ypografontes" )
public class DiavgeiaSigner {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn( name="pb_id" )
	private DiavgeiaOrganization organization;
	
	@ManyToOne
	@JoinColumn( name="type_id" )
	private DiavgeiaSignerType signerType;	
	
	@Column( name="monada_id" )
	private Integer unitId;
	
	@Column( name="firstname")
	private String firstName;
	
	@Column( name="lastname")
	private String lastName;
	
	@Column( name="en_energeia")
	private Boolean active;	

	@Column( name="title_name" )
	private String position;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DiavgeiaOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(DiavgeiaOrganization organization) {
		this.organization = organization;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	
	public String getTitle(){
		if (signerType != null){
			return signerType.getName();
		}
		else{
			return "";
		}
	}

	public DiavgeiaSignerType getSignerType() {
		return signerType;
	}

	public void setSignerType(DiavgeiaSignerType signerType) {
		this.signerType = signerType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
}
