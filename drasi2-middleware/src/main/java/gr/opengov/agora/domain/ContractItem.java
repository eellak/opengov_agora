package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;
import gr.opengov.agora.dao.DecisionGenericHibernateDAO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContractItem implements Serializable, IContractItem {
	private static final Logger logger = LoggerFactory.getLogger(ContractItem.class);

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double quantity;
	private String unitOfMeasureIdRef;
	private ICost cost;
	private Set<ICpv> cpvCodes = new LinkedHashSet<ICpv>(0);
	private List<String> kaeCodes;
	private String description;
	private IProcurementRequest procurementRequest; //Procurement request source where from this contract item is retrieved
	private INotice notice; //notice source where from this contract item is retrieved
	private IContract contract; //contract source where from this contract item is retrieved
	private String address;
	private String addressNo;
	private String addressPostal;
	private String nuts;
	private String city;
	private String countryIdRef;
	private String countryProducedIdRef;
	private String invoiceNumber;	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractItem#getCost()
	 */
	@Override
	public ICost getCost() {
		return cost;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractItem#setCost(gr.opengov.agora.domain.ICost)
	 */
	@Override
	public void setCost(ICost cost) {
		this.cost = cost;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractItem#getCpvCodes()
	 */
	@Override
	public Set<ICpv> getCpvCodes() {
		return cpvCodes;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractItem#setCpvCodes(java.util.Set)
	 */
	@Override
	public void setCpvCodes(Set<ICpv> cpvCodes) {
		this.cpvCodes = cpvCodes;
	}
	
	@Override
	public List<String> getKaeCodes() {
		return kaeCodes;
	}
	
	@Override
	public void setKaeCodes(List<String> kaeCodes) {
		this.kaeCodes = kaeCodes;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractItem#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractItem#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}	
	
	@Override
	public ITaxonomyReference getUnitOfMeasureTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getUnitOfMeasureIdRef();
			}
		};		
	}
	
	@Override
	public String getUnitOfMeasureIdRef() {
		return unitOfMeasureIdRef;
	}
	
	@Override
	public void setUnitOfMeasureIdRef( String unitOfMeasureIdRef ) {
		this.unitOfMeasureIdRef = unitOfMeasureIdRef;
	}	
	
	@Override
	public Double getQuantity() {
		return quantity;
	}
	
	@Override
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public IProcurementRequest getProcurementRequest() {
		return procurementRequest;
	}
	
	@Override
	public void setProcurementRequest(IProcurementRequest procurementRequest) {
		this.procurementRequest = procurementRequest;
	}
	
	@Override
	public INotice getNotice() {
		return notice;
	}
	
	@Override
	public void setNotice(INotice notice) {
		this.notice = notice;
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
	public String getAddress() {
		return address;
	}
	
	@Override
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String getAddressNo() {
		return addressNo;
	}
	
	@Override
	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}
	
	@Override
	public String getAddressPostal() {
		return addressPostal;
	}
	
	@Override
	public void setAddressPostal(String addressPostal) {
		this.addressPostal = addressPostal;
	}
	
	@Override
	public String getNuts() {
		return nuts;
	}
	
	@Override
	public void setNuts(String nuts) {
		this.nuts = nuts;
	}
	
	@Override
	public String getCity() {
		return city;
	}
	
	@Override
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public ITaxonomyReference getCountryTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getCountryIdRef();
			}
		};		
	}

	@Override
	public String getCountryIdRef() {
		return countryIdRef;
	}
	
	@Override
	public void setCountryIdRef(String countryIdRef) {
		this.countryIdRef = countryIdRef;
	}
	
	@Override
	public ITaxonomyReference getCountryProducedTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getCountryProducedIdRef();
			}
		};		
	}

	@Override
	public String getCountryProducedIdRef() {
		return countryProducedIdRef;
	}
	
	@Override
	public void setCountryProducedIdRef(String countryProducedIdRef) {
		this.countryProducedIdRef = countryProducedIdRef;
	}	
	
	@Override
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	@Override
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}	
	
	@Override
	public boolean addCpv(ICpv cpv){
		return cpvCodes.add(cpv);
	}
	
	@Override
	public boolean addCpv(String cpv){
		return cpvCodes.add(new Cpv(cpv));
	}	
	
	@Override
	public void clearCpvs(){
		cpvCodes.clear();
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IContractItem ) ) return false;
		IContractItem rhs = (IContractItem)obj;
		return new EqualsBuilder()
			.append( cost, rhs.getCost() )
			.append( quantity, rhs.getQuantity())
			.append( unitOfMeasureIdRef, rhs.getUnitOfMeasureIdRef())
			.append( cpvCodes, rhs.getCpvCodes() )
			.append( kaeCodes, rhs.getKaeCodes())
			.append( description, rhs.getDescription() )
//			.append( procurementRequest, rhs.getProcurementRequest() )
//			.append( notice, rhs.getNotice())
//			.append( contract, rhs.getContract())
			.append( address, rhs.getAddress() )
			.append( addressNo, rhs.getAddressNo() )
			.append( addressPostal, rhs.getAddressPostal() )
			.append( nuts, rhs.getNuts() )
			.append( city, rhs.getCity() )
			.append( countryIdRef, rhs.getCountryIdRef() )
			.append( countryProducedIdRef, rhs.getCountryProducedIdRef() )
			.append( invoiceNumber, rhs.getInvoiceNumber() )			
			// id is used only for persistence, ignore
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
//			.appendSuper( 24 )
			.append( cost )
			.append( quantity )
			.append( unitOfMeasureIdRef )
			.append( cpvCodes )  // Do NOT add cpvCodes to the hash, so that DAO hack works
			.append( kaeCodes )
			.append( description )
//			.append( procurementRequest )
//			.append( notice )
//			.append( contract )
			.append( address )
			.append( addressNo )
			.append( addressPostal )
			.append( nuts )
			.append( city )
			.append( countryIdRef )
			.append( countryProducedIdRef )
			.append( invoiceNumber )					
			.toHashCode();			
	}
	
	@Override
	public void finalizeEntity() {		
		for ( ICpv cpv: cpvCodes ) { cpv.finalizeEntity();}
		for ( String kae: kaeCodes ) { };
		cost.finalizeEntity();
//Don't enable that! it runs finalize of the decision containing this contract Item. 
//On the other hand, decision containing this contractItem, runs this contractItem's finalize(). 
//Finally, it results in a StackOverFlowError
//		if (procurementRequest != null){
//			procurementRequest.finalizeEntity();
//		}
//		if (notice != null){
//			notice.finalizeEntity();
//		}
//		if (contract != null){
//			contract.finalizeEntity();
//		}
	}
}
