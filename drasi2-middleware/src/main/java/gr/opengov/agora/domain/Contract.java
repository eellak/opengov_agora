package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReferenceWithOther;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Contract extends PublicOrganizationDecision implements Serializable, IContract {
	private static final long serialVersionUID = 1L;
	
	private List<IContractParty> secondaryParties;
	private List<IContractItem> contractItems;
	private Calendar since;
	private Calendar until;
	private String contractPlace;
	private String projectCode;
	private IContract changesContract;
	private IContract extendsContract;
	
	private String contractingAuthorityIdRef;
	private String contractingAuthorityOther;
	private String awardProcedureIdRef;
	private String commissionCriteriaIdRef;
	private String contractTypeIdRef;
		
	private Boolean coFunded;
	private Boolean fundedFromPIP;
	private String codeCoFunded;
	private String codePIP;	
	
	public Contract() {
		secondaryParties = new ArrayList<IContractParty>();
		contractItems = new ArrayList<IContractItem>();
	}
	
	@Override
	public DecisionType getDecisionType() {
		return DecisionType.CONTRACT;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContract#getSecondaryParties()
	 */
	@Override
	public List<IContractParty> getSecondaryParties() {
		return secondaryParties;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContract#setSecondaryParties(java.util.List)
	 */
	@Override
	public void setSecondaryParties(List<IContractParty> secondaryParties) {
		this.secondaryParties = secondaryParties;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContract#getContractItems()
	 */
	@Override
	public List<IContractItem> getContractItems() {
		return contractItems;
	}
	
	@Override
	public Double getTotalCostBeforeVat(){
		BigDecimal cost = new BigDecimal(0.0);
		for (IContractItem item:contractItems){
			cost = cost.add(new BigDecimal(item.getCost().getCostBeforeVat().toString()));
		}
		return cost.doubleValue();
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContract#setContractItems(java.util.List)
	 */
	@Override
	public void setContractItems(List<IContractItem> contractItems) {
		this.contractItems = contractItems;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContract#getSince()
	 */
	@Override
	public Calendar getSince() {
		return since;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContract#setSince(java.util.Calendar)
	 */
	@Override
	public void setSince(Calendar since) {
		this.since = since;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContract#getUntil()
	 */
	@Override
	public Calendar getUntil() {
		return until;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContract#setUntil(java.util.Calendar)
	 */
	@Override
	public void setUntil(Calendar until) {
		this.until = until;
	}
	@Override
	public String getContractPlace() {
		return contractPlace;
	}
	@Override
	public void setContractPlace(String contractPlace) {
		this.contractPlace = contractPlace;
	}
	@Override
	public String getProjectCode() {
		return projectCode;
	}

	@Override
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	@Override
	public IContract getChangesContract() {
		return changesContract;
	}
	@Override
	public void setChangesContract(IContract changesContract) {
		this.changesContract = changesContract;
	}
	@Override
	public IContract getExtendsContract() {
		return extendsContract;
	}
	@Override
	public void setExtendsContract(IContract extendsContract) {
		this.extendsContract = extendsContract;
	}
		
	@Override
	public String getContractingAuthorityIdRef() {
		return contractingAuthorityIdRef;
	}
	
	@Override
	public void setContractingAuthorityIdRef(String contractingAuthorityIdRef) {
		this.contractingAuthorityIdRef = contractingAuthorityIdRef;
	}
	
	@Override
	public String getContractingAuthorityOther() {
		return contractingAuthorityOther;
	}
	
	@Override
	public void setContractingAuthorityOther(String contractingAuthorityOther) {
		this.contractingAuthorityOther = contractingAuthorityOther;
	}
	
	@Override
	public ITaxonomyReference getContractingAuthorityTaxonomyReference() {
		return new TaxonomyReferenceWithOther() {
			@Override
			public String getIdRef() {
				return getContractingAuthorityIdRef();
			}
			@Override
			public String getOther() throws UnsupportedOperationException {
				return getContractingAuthorityOther();
			}			
		};
	}
	
	@Override
	public ITaxonomyReference getAwardProcedureTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getAwardProcedureIdRef();
			}
		};
	}	
	
	@Override
	public String getAwardProcedureIdRef() {
		return awardProcedureIdRef;
	}
	
	@Override
	public void setAwardProcedureIdRef(String awardProcedureIdRef) {
		this.awardProcedureIdRef = awardProcedureIdRef;
	}
		
	@Override
	public ITaxonomyReference getCommissionCriteriaTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getCommissionCriteriaIdRef();
			}
		};
	}		
	
	@Override
	public String getCommissionCriteriaIdRef() {
		return commissionCriteriaIdRef;
	}
	
	@Override
	public void setCommissionCriteriaIdRef(String commissionCriteriaIdRef) {
		this.commissionCriteriaIdRef = commissionCriteriaIdRef;
	}
	
	@Override
	public ITaxonomyReference getContractTypeTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getContractTypeIdRef();
			}
		};
	}			
	
	@Override
	public String getContractTypeIdRef() {
		return contractTypeIdRef;
	}
	
	@Override
	public void setContractTypeIdRef(String contractTypeIdRef) {
		this.contractTypeIdRef = contractTypeIdRef;
	}
	
	@Override
	public Boolean isCoFunded() {
		return coFunded;
	}

	@Override
	public void setCoFunded(Boolean coFunded) {
		this.coFunded = coFunded;
	}

	@Override
	public Boolean isFundedFromPIP() {
		return fundedFromPIP;
	}

	@Override
	public void setFundedFromPIP(Boolean fundedFromPIP) {
		this.fundedFromPIP = fundedFromPIP;
	}

	@Override
	public String getCodeCoFunded() {
		return codeCoFunded;
	}

	@Override
	public void setCodeCoFunded(String codeCoFunded) {
		this.codeCoFunded = codeCoFunded;
	}

	@Override
	public String getCodePIP() {
		return codePIP;
	}

	@Override
	public void setCodePIP(String codePIP) {
		this.codePIP = codePIP;
	}

	@Override
	public boolean isValid() {
		return true;
	}	
	
	@Override	
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IContract ) ) return false;
		IContract rhs = (IContract)obj;
		return new EqualsBuilder()
			.appendSuper( super.equals( obj ) )
			.append( changesContract, rhs.getChangesContract() )
			.append( contractItems, rhs.getContractItems() )
			.append( extendsContract, rhs.getExtendsContract() )
			.append( secondaryParties, rhs.getSecondaryParties() )
			.append( since, rhs.getSince() )
			.append( until, rhs.getUntil() )
			.append( contractPlace, rhs.getContractPlace() )
			.append( contractingAuthorityIdRef, rhs.getContractingAuthorityIdRef() )
			.append( contractingAuthorityOther, rhs.getContractingAuthorityOther() )
			.append( awardProcedureIdRef, rhs.getAwardProcedureIdRef() )
			.append( commissionCriteriaIdRef, rhs.getCommissionCriteriaIdRef() )
			.append( contractTypeIdRef, rhs.getContractTypeIdRef() )
			.append( coFunded, rhs.isCoFunded() )
			.append( fundedFromPIP, rhs.isFundedFromPIP() )
			.append( codeCoFunded, rhs.getCodeCoFunded() )
			.append( codePIP, rhs.getCodePIP() )
			.isEquals();				
	}
	
	@Override	
	public int hashCode() {
		return new HashCodeBuilder( )
				.appendSuper( 23 )
				.append( changesContract )
				.append( contractItems )
				.append( extendsContract )
				.append( secondaryParties )
				.append( since )
				.append( until )
				.append( contractPlace )
				.append( contractingAuthorityIdRef )
				.append( contractingAuthorityOther )
				.append( awardProcedureIdRef )
				.append( commissionCriteriaIdRef )
				.append( contractTypeIdRef )
				.append( coFunded )
				.append( fundedFromPIP )
				.append( codeCoFunded )
				.append( codePIP )
				.toHashCode();				
	}	
	
	@Override
	public void finalizeEntity() {
		super.finalizeEntity();
		for ( IContractItem item: getContractItems() ) {
			item.finalizeEntity();
		}
		for ( IContractParty party: getSecondaryParties() ) {
			party.finalizeEntity();
		}
		if ( changesContract != null ) {
			changesContract.getId();
			changesContract.getCmsMetadata();
		}
		if ( extendsContract != null ) {
			extendsContract.getId();
			extendsContract.getCmsMetadata();
		}					
	}

	
}