package gr.opengov.agora.bridges.diavgeia.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="oda_members_master")
public class DiavgeiaOdeMember {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn( name="foreas_pb_id", insertable=false, updatable=false )
	private DiavgeiaOrganization organization;
	
	@Column( name="foreas_pb_id" )
	private Integer organizationId;
	
	@Column( name="ypourgeio_to_check" )
	private Integer parentOrganizationId;	
	
	@Column( name="foreas_latin_name" )
	private String organizationLatinName;
	
	@Column( name="foreas_afm")
	private String organizationAfm;
	
	@Column( name="diefthinsi")
	private String organizationAddressRoad;
	
	@Column( name="arithmos" )
	private String organizationAddressNumber;
	
	@Column( name="TK" )
	private String organizationAddressPostalCode;
	
	@Column( name="status" )
	private String status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	public Integer getParentOrganizationId() {
		return parentOrganizationId;
	}
	public void setParentOrganizationId(Integer parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}
	public DiavgeiaOrganization getOrganization() {
		return organization;
	}
	public void setOrganization(DiavgeiaOrganization organization) {
		this.organization = organization;
	}
	public String getOrganizationLatinName() {
		return organizationLatinName;
	}
	public void setOrganizationLatinName(String organizationLatinName) {
		this.organizationLatinName = organizationLatinName;
	}
	
	public String getOrganizationAfm() {
		return organizationAfm;
	}
	public void setOrganizationAfm(String organizationAfm) {
		this.organizationAfm = organizationAfm;
	}
	public String getOrganizationAddressRoad() {
		return organizationAddressRoad;
	}
	public void setOrganizationAddressRoad(String organizationAddressRoad) {
		this.organizationAddressRoad = organizationAddressRoad;
	}
	public String getOrganizationAddressNumber() {
		return organizationAddressNumber;
	}
	public void setOrganizationAddressNumber(String organizationAddressNumber) {
		this.organizationAddressNumber = organizationAddressNumber;
	}
	public String getOrganizationAddressPostalCode() {
		return organizationAddressPostalCode;
	}
	public void setOrganizationAddressPostalCode(
			String organizationAddressPostalCode) {
		this.organizationAddressPostalCode = organizationAddressPostalCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
