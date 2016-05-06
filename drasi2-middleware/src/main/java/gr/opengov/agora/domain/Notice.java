package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Notice extends PublicOrganizationDecision implements Serializable, INotice {
	private static final long serialVersionUID = 1L;
	
	private Calendar since;
	private Calendar until;
	private String awardProcedureIdRef;
	private List<IContractItem> contractItems;
	
	public Notice() {
		contractItems = new ArrayList<IContractItem>();
	}
		
	@Override
	public Double getTotalCostBeforeVat(){
		BigDecimal cost = new BigDecimal(0.0);
		for (IContractItem contractItem:contractItems){
			cost = cost.add(new BigDecimal(contractItem.getCost().getCostBeforeVat().toString()));
		}
		return cost.doubleValue();
	}

	@Override
	public Calendar getSince() {
		return since;
	}

	@Override
	public void setSince(Calendar since) {
		this.since = since;
	}

	@Override
	public Calendar getUntil() {
		return until;
	}

	@Override
	public void setUntil(Calendar until) {
		this.until = until;
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
	public List<IContractItem> getContractItems() {
		return contractItems;
	}

	@Override
	public void setContractItems(List<IContractItem> contractItems) {
		this.contractItems = contractItems;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}	
	
	@Override	
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof INotice ) ) return false;
		INotice rhs = (INotice)obj;
		return new EqualsBuilder()
			.appendSuper( super.equals( obj ) )
			.append( since, rhs.getSince() )
			.append( until, rhs.getUntil() )
			.append( awardProcedureIdRef, rhs.getAwardProcedureIdRef() )
			.append( contractItems, rhs.getContractItems() )
			.isEquals();				
	}
	
	@Override	
	public int hashCode() {
		return new HashCodeBuilder( )
				.appendSuper( 23 )
				.append( since )
				.append( until )
				.append( awardProcedureIdRef )
				.append( contractItems )
				.toHashCode();				
	}	
	
	@Override
	public void finalizeEntity() {
		super.finalizeEntity();
		for ( IContractItem contractItem: getContractItems() ) {
			contractItem.finalizeEntity();
		}
	}

	
}