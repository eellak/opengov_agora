package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.util.LazyEntity;

import java.util.Calendar;
import java.util.List;

public interface INotice extends IPublicOrganizationDecision, LazyEntity {

	public abstract Calendar getSince();
	public abstract void setSince(Calendar since);

	public abstract Calendar getUntil();
	public abstract void setUntil(Calendar until);

	public Double getTotalCostBeforeVat();
	public String getAwardProcedureIdRef();
	public void setAwardProcedureIdRef(String awardProcedureIdRef);
	public ITaxonomyReference getAwardProcedureTaxonomyReference();
	public List<IContractItem> getContractItems();
	public void setContractItems(List<IContractItem> contractItems);
	
}