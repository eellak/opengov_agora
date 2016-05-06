package gr.opengov.agora.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PaymentItem implements Serializable, IPaymentItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private IContractItem contractItem;
	private IContractParty contractParty;
	private String responsibilityAssumptionCode;
	private Boolean payed;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public IContractItem getContractItem() {
		return contractItem;
	}

	@Override
	public void setContractItem(IContractItem contractItem) {
		this.contractItem = contractItem;
	}

	@Override
	public IContractParty getContractParty() {
		return contractParty;
	}

	@Override
	public void setContractParty(IContractParty contractParty) {
		this.contractParty = contractParty;
	}
	
	@Override
	public String getResponsibilityAssumptionCode() {
		return responsibilityAssumptionCode;
	}
	
	@Override
	public void setResponsibilityAssumptionCode(String responsibilityAssumptionCode) {
		this.responsibilityAssumptionCode = responsibilityAssumptionCode;
	}
	
	@Override
	public Boolean getPayed() {
		return payed;
	}

	@Override
	public void setPayed(Boolean payed) {
		this.payed = payed;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IPaymentItem ) ) return false;
		IPaymentItem rhs = (IPaymentItem)obj;
		return new EqualsBuilder()
			.append( contractItem, rhs.getContractItem() )
			.append( contractParty, rhs.getContractParty() )
			.append( responsibilityAssumptionCode, rhs.getResponsibilityAssumptionCode() )
			.append( payed, rhs.getPayed() )
			// id is used only for persistence, ignore
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append( contractItem )
			.append( contractParty )
			.append( responsibilityAssumptionCode )
			.append( payed )
			.toHashCode();
	}

	@Override
	public void finalizeEntity() {
		getContractParty().finalizeEntity();
		getContractItem().finalizeEntity();
	}
}
