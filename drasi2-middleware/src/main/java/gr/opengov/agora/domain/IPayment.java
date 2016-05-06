package gr.opengov.agora.domain;

import gr.opengov.agora.util.LazyEntity;

import java.util.List;

public interface IPayment extends IPublicOrganizationDecision, LazyEntity {

	public abstract IContract getContract();

	public abstract void setContract(IContract contract);

	public abstract List<IPaymentItem> getPaymentItems();

	public abstract void setPaymentItems(List<IPaymentItem> paymentItems);

	public abstract String getResponsibilityAssumptionCode();

	public abstract void setResponsibilityAssumptionCode(String responsibilityAssumptionCode);

	public abstract IContractParty getPrimaryParty();

	public abstract void setPrimaryParty(IContractParty primaryParty);

	public abstract Double getTotalCostBeforeVat();

}
