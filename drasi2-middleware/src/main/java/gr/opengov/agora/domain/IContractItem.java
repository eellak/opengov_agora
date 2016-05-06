package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.util.LazyEntity;

import java.util.List;
import java.util.Set;

public interface IContractItem extends LazyEntity {

	public abstract ICost getCost();

	public abstract void setCost(ICost cost);

	public abstract Set<ICpv> getCpvCodes();

	public abstract void setCpvCodes(Set<ICpv> cpvCodes);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract Double getQuantity();

	public abstract void setQuantity(Double quantity);

	public abstract List<String> getKaeCodes();

	public abstract void setKaeCodes(List<String> kaeCodes);

	public IProcurementRequest getProcurementRequest();

	public void setProcurementRequest(IProcurementRequest procurementRequest);

	public String getAddress();

	public void setAddress(String address);

	public String getAddressNo();

	public void setAddressNo(String addressNo);

	public String getAddressPostal();

	public void setAddressPostal(String addressPostal);

	public String getNuts();

	public void setNuts(String nuts);

	public String getCity();

	public void setCity(String city);

	public ITaxonomyReference getCountryTaxonomyReference();

	public String getCountryIdRef();

	public void setCountryIdRef(String countryIdRef);

	public void setCountryProducedIdRef(String countryProducedIdRef);

	public String getCountryProducedIdRef();

	public ITaxonomyReference getCountryProducedTaxonomyReference();
	
	public abstract String getInvoiceNumber();

	public abstract void setInvoiceNumber(String invoiceNumber);

	public boolean addCpv(ICpv cpv);

	public void clearCpvs();

	public boolean addCpv(String cpv);

	public INotice getNotice();

	public void setNotice(INotice notice);

	public IContract getContract();

	public void setContract(IContract contract);

	public String getUnitOfMeasureIdRef();

	public void setUnitOfMeasureIdRef(String unitOfMeasureIdRef);

	public ITaxonomyReference getUnitOfMeasureTaxonomyReference();	
}