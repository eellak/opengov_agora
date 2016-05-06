package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.util.LazyEntity;

import java.util.Calendar;
import java.util.List;

public interface IContract extends IPublicOrganizationDecision, LazyEntity {

	public abstract List<IContractParty> getSecondaryParties();
	public abstract void setSecondaryParties(List<IContractParty> secondaryParties);

	public abstract List<IContractItem> getContractItems();
	public abstract void setContractItems(List<IContractItem> contractItems);

	public abstract Calendar getSince();
	public abstract void setSince(Calendar since);

	public abstract Calendar getUntil();
	public abstract void setUntil(Calendar until);

	public abstract IContract getChangesContract();
	public abstract void setChangesContract(IContract changesContract);

	public abstract IContract getExtendsContract();
	public abstract void setExtendsContract(IContract extendsContract);

	public abstract ITaxonomyReference getContractingAuthorityTaxonomyReference();
	public abstract String getContractingAuthorityIdRef();
	public abstract void setContractingAuthorityIdRef(String contractingAuthorityIdRef);
	
	public abstract String getContractingAuthorityOther();
	public abstract void setContractingAuthorityOther(String contractingAuthorityOther);
	
	public abstract ITaxonomyReference getAwardProcedureTaxonomyReference();
	public abstract String getAwardProcedureIdRef();
	public abstract void setAwardProcedureIdRef(String awardProcedureIdRef);
	
	public abstract ITaxonomyReference getCommissionCriteriaTaxonomyReference();
	public abstract String getCommissionCriteriaIdRef();
	public abstract void setCommissionCriteriaIdRef(String commissionCriteriaIdRef);
	
	public abstract ITaxonomyReference getContractTypeTaxonomyReference();
	public abstract String getContractTypeIdRef();
	public abstract void setContractTypeIdRef(String contractTypeIdRef);
	
	public abstract String getContractPlace();
	public abstract void setContractPlace(String contractPlace);
	public abstract String getProjectCode();
	public abstract void setProjectCode(String projectCode);
	public abstract Double getTotalCostBeforeVat();
	public abstract void setCodePIP(String codePIP);
	public abstract String getCodePIP();
	public abstract void setCodeCoFunded(String codeCoFunded);
	public abstract String getCodeCoFunded();
	public abstract void setFundedFromPIP(Boolean fundedFromPIP);
	public abstract Boolean isFundedFromPIP();
	public abstract void setCoFunded(Boolean coFunded);
	public abstract Boolean isCoFunded();	
}