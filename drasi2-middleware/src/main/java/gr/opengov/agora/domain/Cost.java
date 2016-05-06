package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Cost implements Serializable, ICost {
	private Long id;
	private Double costBeforeVat;
	private Double vatPercentage;
	private String currencyIdRef;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICost#getCostBeforeVat()
	 */
	@Override
	public Double getCostBeforeVat() {
		return costBeforeVat;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICost#setCostBeforeVat(java.lang.Float)
	 */
	@Override
	public void setCostBeforeVat(Double costBeforeVat) {
		this.costBeforeVat = costBeforeVat;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICost#getvatPercentage()
	 */
	@Override
	public Double getVatPercentage() {
		return vatPercentage;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICost#setvatPercentage(int)
	 */
	@Override
	public void setVatPercentage(Double vatPercentage) {
		this.vatPercentage = vatPercentage;
	}
	
	@Override
	public ITaxonomyReference getCurrencyTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getCurrencyIdRef();
			}
		};		
	}

	@Override
	public String getCurrencyIdRef() {
		return currencyIdRef;
	}
	
	@Override
	public void setCurrencyIdRef(String currencyIdRef) {
		this.currencyIdRef = currencyIdRef;
	}		
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof ICost ) ) return false;
		ICost rhs = (ICost)obj;
		return new EqualsBuilder()
			.append( costBeforeVat, rhs.getCostBeforeVat() )
			.append( currencyIdRef, rhs.getCurrencyIdRef() )
			// id is used only for persistence, ignore
			.append( vatPercentage, rhs.getVatPercentage() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append( costBeforeVat )
			.append( currencyIdRef )
			.append( vatPercentage )
			.toHashCode();
	}
	
	@Override
	public void finalizeEntity() {
		// TODO Auto-generated method stub		
	}
}
