package gr.opengov.agora.domain;

public interface ICpv {

	public abstract String getCpvCode();

	public abstract void setCpvCode(String cpvCode);

	public void finalizeEntity();
	
	public abstract boolean equals(Object obj);

	public abstract int hashCode();

	public Long getId();

	public void setId(Long id);

//	public IContractItem getContractItem();
//
//	public void setContractItem(IContractItem contractItem);	

}