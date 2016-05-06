package gr.opengov.agora.domain;

public interface IPaymentItem {

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract IContractItem getContractItem();

	public abstract void setContractItem(IContractItem contractItem);

	public abstract IContractParty getContractParty();

	public abstract void setContractParty(IContractParty contractParty);

	public abstract void finalizeEntity();

	public String getResponsibilityAssumptionCode();

	public void setResponsibilityAssumptionCode(String responsibilityAssumptionCode);

	public Boolean getPayed();

	public void setPayed(Boolean payed);

}
