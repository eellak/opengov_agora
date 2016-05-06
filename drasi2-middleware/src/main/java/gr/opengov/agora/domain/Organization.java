package gr.opengov.agora.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Organization implements IOrganization, Serializable {
	private static final Logger logger = LoggerFactory.getLogger(Organization.class);

    protected String name;
    protected String formalName;
    protected String afm;
    protected String address;
    protected String addressNo;
    protected String addressPostal;
    protected String nuts;
    protected String city;
    protected String country;
    protected String organizationType;
    protected int idRef;

    @Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getFormalName() {
		return formalName;
	}

	@Override
	public void setFormalName(String formalName) {
		this.formalName = formalName;
	}

	@Override
	public String getAfm() {
		return afm;
	}

	@Override
	public void setAfm(String afm) {
		this.afm = afm;
	}
	
	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getAddressNo() {
		return addressNo;
	}

	@Override
	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}

	@Override
	public String getAddressPostal() {
		return addressPostal;
	}

	@Override
	public void setAddressPostal(String addressPostal) {
		this.addressPostal = addressPostal;
	}

	@Override
	public String getNuts() {
		return nuts;
	}
	
	@Override
	public void setNuts(String nuts) {
		this.nuts = nuts;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String getOrganizationType() {
		return organizationType;
	}

	@Override
	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	@Override
	public int getIdRef() {
		return idRef;
	}

	@Override
	public void setIdRef(int idRef) {
		this.idRef = idRef;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IOrganization ) ) return false;
		IOrganization rhs = (IOrganization)obj;
		return new EqualsBuilder()
			.append( name, rhs.getName() )
			.append( formalName, rhs.getFormalName() )
			.append( afm, rhs.getAfm() )
			.append( address, rhs.getAddress() )
			.append( addressNo, rhs.getAddressNo() )
			.append( addressPostal, rhs.getAddressPostal() )
			.append( nuts, rhs.getNuts() )
			.append( city, rhs.getCity() )
			.append( country, rhs.getCountry() )
			.append( organizationType, rhs.getOrganizationType() )
			// id is used only for persistence, ignore
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append( name )
		.append( formalName )
		.append( afm )
		.append( address )
		.append( addressNo )
		.append( addressPostal )
		.append( nuts )
		.append( city )
		.append( country )
		.append( organizationType )
		.toHashCode();
	}
	
	@Override
	public void finalizeEntity() {
		// TODO Auto-generated method stub		
	}
}
