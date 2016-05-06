package gr.opengov.agora.bridges.diavgeia.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table( name="monades" )
public class DiavgeiaUnit {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn( name="parent_pb_id" )
	private DiavgeiaOrganization organization;
	
	@OneToMany( mappedBy="unitId" )
	@javax.persistence.OrderBy("id")
	private Set<DiavgeiaSigner> signers;
	
	@Column
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	

	public Set<DiavgeiaSigner> getSigners() {
		return signers;
	}

	public void setSigners(Set<DiavgeiaSigner> signers) {
		this.signers = signers;
	}
	
	
	
}
