package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.util.LazyEntity;

public interface ICost extends LazyEntity {

	public abstract Double getCostBeforeVat();

	public abstract void setCostBeforeVat(Double costBeforeVat);

	public abstract Double getVatPercentage();

	public abstract void setVatPercentage(Double vatPercentage);
	
	public abstract ITaxonomyReference getCurrencyTaxonomyReference();

	public abstract String getCurrencyIdRef();

	public abstract void setCurrencyIdRef(String countryIdRef);
}