package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ContractParty implements Serializable, IContractParty {
	private Long id;
	private String name;
	private String afm;
	private String countryIdRef;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractParty#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractParty#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractParty#getAfm()
	 */
	@Override
	public String getAfm() {
		return afm;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractParty#setAfm(java.lang.String)
	 */
	@Override
	public void setAfm(String afm) {
		this.afm = afm;
	}
	
	@Override
	public ITaxonomyReference getCountryTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getCountryIdRef();
			}
		};		
	}

	@Override
	public String getCountryIdRef() {
		return countryIdRef;
	}
	
	@Override
	public void setCountryIdRef(String countryIdRef) {
		this.countryIdRef = countryIdRef;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IContractParty ) ) return false;
		IContractParty rhs = (IContractParty)obj;
		return new EqualsBuilder()
			.append( afm, rhs.getAfm() )
			.append( countryIdRef, rhs.getCountryIdRef() )
			// id is used only for persistence, ignore
			.append( name, rhs.getName() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append( afm )
			.append( countryIdRef )
			.append( name )
			.toHashCode();
	}

	@Override
	public void finalizeEntity() {
		// TODO Auto-generated method stub
		
	}
}
