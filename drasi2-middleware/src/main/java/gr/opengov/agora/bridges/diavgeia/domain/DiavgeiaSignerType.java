package gr.opengov.agora.bridges.diavgeia.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table( name="ypografontes_types" )
public class DiavgeiaSignerType {
	@Id
	@GeneratedValue
	private Integer id;

	@Column( name="type" )
	private String type;
	
	@Column( name="name" )
	private String name;
	
	@Column( name="importance" )
	private Integer importancy;
	
	@Column( name="serviceType" )
	
	private String serviceType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getImportancy() {
		return importancy;
	}

	public void setImportancy(Integer importancy) {
		this.importancy = importancy;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

}
