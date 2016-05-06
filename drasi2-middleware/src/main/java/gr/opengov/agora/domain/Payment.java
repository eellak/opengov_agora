package gr.opengov.agora.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Payment extends PublicOrganizationDecision implements Serializable, IPayment {
	private static final long serialVersionUID = 1L;

	private IContract contract;	
	private List<IPaymentItem> paymentItems;
	private String responsibilityAssumptionCode;
	private IContractParty primaryParty;
	
	public Payment(){
		paymentItems = new ArrayList<IPaymentItem>();
	}
	
	@Override
	public DecisionType getDecisionType() {
		return DecisionType.PAYMENT;
	}
	
	@Override
	public Integer getDiavgeiaType() {
		return 27; // Τύπος: ΔΑΠΑΝΗ
	}
	
	@Override
	public IContract getContract() {
		return contract;
	}
	@Override
	public void setContract(IContract contract) {
		this.contract = contract;
	}
	@Override
	public List<IPaymentItem> getPaymentItems() {
		return paymentItems;
	}
	@Override
	public void setPaymentItems(List<IPaymentItem> paymentItems) {
		this.paymentItems = paymentItems;
	}
	
	@Override
	public Double getTotalCostBeforeVat(){
		BigDecimal cost = new BigDecimal(0.0);
		for (IPaymentItem item:paymentItems){
			cost = cost.add(new BigDecimal(item.getContractItem().getCost().getCostBeforeVat().toString()));
		}
		return cost.doubleValue();		
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
	public IContractParty getPrimaryParty() {
		return primaryParty;
	}
	@Override
	public void setPrimaryParty(IContractParty primaryParty) {
		this.primaryParty = primaryParty;
	}

	@Override
	public boolean isValid() {
		return true;
	}	

	@Override	
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IPayment ) ) return false;
		IPayment rhs = (IPayment)obj;
		return new EqualsBuilder()
			.appendSuper( super.equals( obj ) )
			.append( contract, rhs.getContract() )
			.append( paymentItems, rhs.getPaymentItems() )
			.append( responsibilityAssumptionCode, rhs.getResponsibilityAssumptionCode() )
			.append( primaryParty, rhs.getPrimaryParty() )
			.isEquals();				
	}
	
	@Override	
	public int hashCode() {
		return new HashCodeBuilder( )
				.appendSuper( 23 )
				.append( contract )
				.append( paymentItems )
				.append( primaryParty )
				.toHashCode();				
	}	
	
	@Override
	public void finalizeEntity() {
		super.finalizeEntity();
		for ( IPaymentItem item: getPaymentItems() ) {
			item.finalizeEntity();
		}
		getPrimaryParty().finalizeEntity();
		if (getContract() != null) getContract().finalizeEntity();
	}

	
}