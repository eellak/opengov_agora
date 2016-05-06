package gr.opengov.agora.domain;

import java.util.Calendar;
import java.util.List;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.util.LazyEntity;

public interface IProcurementRequest extends IPublicOrganizationDecision, LazyEntity {
	
	public abstract List<IContractItem> getContractItems();
	
	public abstract void setContractItems(List<IContractItem> contractItems);

	public abstract IProcurementRequest getApprovesRequest();

	public abstract void setApprovesRequest(IProcurementRequest approvesRequest);

	public abstract Double getTotalCostBeforeVat();

	public abstract ITaxonomyReference getAwardProcedureTaxonomyReference();

	public abstract String getAwardProcedureIdRef();

	public abstract void setAwardProcedureIdRef(String awardProcedureIdRef);

	public abstract String getResponsibilityAssumptionCode();

	public abstract void setResponsibilityAssumptionCode(String responsibilityAssumptionCode);

	public abstract Calendar getFulfilmentDate();

	public abstract void setFulfilmentDate(Calendar fulfilmentDate);
	
	public Boolean isEppApproved();
	
	public void setEppApproved(Boolean eppApproved);
	
	public String getCode();
	
	public void setCode(String code);

}
