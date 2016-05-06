package gr.opengov.agora.domain;


import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcurementRequest extends PublicOrganizationDecision implements Serializable, IProcurementRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<IContractItem> contractItems;
	private IProcurementRequest approvesRequest;
	private String awardProcedureIdRef;
	private String responsibilityAssumptionCode;
	private Calendar fulfilmentDate;
	private Boolean eppApproved;
	private String code;
	
	private static final Logger logger = LoggerFactory.getLogger(ProcurementRequest.class);
	
	@Override
	public DecisionType getDecisionType() {
		return DecisionType.PROCUREMENT_REQUEST;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IProcurementRequest#getContractItems()
	 */
	@Override
	public List<IContractItem> getContractItems() {
		return contractItems;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IProcurementRequest#setContractItems(java.util.List)
	 */
	@Override
	public void setContractItems(List<IContractItem> contractItems) {
		this.contractItems = contractItems;
	}
	
	@Override
	public Double getTotalCostBeforeVat(){
		BigDecimal cost = new BigDecimal(0.0);
		for (IContractItem item:contractItems){
			cost = cost.add(new BigDecimal(item.getCost().getCostBeforeVat().toString()));
		}
		return cost.doubleValue();		
	}
	
	@Override
	public IProcurementRequest getApprovesRequest() {
		return approvesRequest;
	}

	@Override
	public void setApprovesRequest(IProcurementRequest approvesRequest ) {
		this.approvesRequest = approvesRequest;
	}

	@Override
	public boolean isValid() {
		return true;
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
	public String getResponsibilityAssumptionCode() {
		return responsibilityAssumptionCode;
	}
	@Override
	public void setResponsibilityAssumptionCode(String responsibilityAssumptionCode) {
		this.responsibilityAssumptionCode = responsibilityAssumptionCode;
	}	
	@Override
	public Calendar getFulfilmentDate() {
		return fulfilmentDate;
	}
	@Override
	public void setFulfilmentDate(Calendar fulfilmentDate) {
		this.fulfilmentDate = fulfilmentDate;
	}
	@Override
	public Boolean isEppApproved() {
		return eppApproved;
	}
	@Override
	public void setEppApproved(Boolean eppApproved) {
		this.eppApproved = eppApproved;
	}
	@Override
	public String getCode() {
		return code;
	}
	@Override
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	@Override	
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IProcurementRequest ) ) return false;
		IProcurementRequest rhs = (IProcurementRequest)obj;
		return new EqualsBuilder()
			.appendSuper( super.equals( obj ) )
			.append( contractItems, rhs.getContractItems() )
			.append( approvesRequest, rhs.getApprovesRequest() )
			.append( awardProcedureIdRef, rhs.getAwardProcedureIdRef() )
			.append( responsibilityAssumptionCode, rhs.getResponsibilityAssumptionCode() )
			.append( fulfilmentDate, rhs.getFulfilmentDate() )
			.append( eppApproved, rhs.isEppApproved() )
			.append( code, rhs.getCode() )
			.isEquals();				
	}

	@Override	
	public int hashCode() {
		return new HashCodeBuilder( )
				.appendSuper( 23 )
				.append( contractItems )
				.append( approvesRequest )
				.append( awardProcedureIdRef )
				.append( responsibilityAssumptionCode )
				.append( fulfilmentDate )
				.append( eppApproved )
				.append( code )
				.toHashCode();				
	}	
	
	@Override
	public void finalizeEntity() {
		logger.debug( "procurement request, finalizing..." );
		logger.debug(((Integer)System.identityHashCode(this)).toString());
		super.finalizeEntity();
		
		for ( IContractItem item: getContractItems() ) {
			item.finalizeEntity();
		}
		
		if ( this.approvesRequest != null ) this.approvesRequest.finalizeEntity();
	}
}