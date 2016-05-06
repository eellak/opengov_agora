package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.util.LazyEntity;

public interface IContractParty extends LazyEntity {

	public abstract String getName();
	public abstract void setName(String name);
	public abstract String getAfm();
	public abstract void setAfm(String afm);
	
	public abstract ITaxonomyReference getCountryTaxonomyReference();
	public abstract String getCountryIdRef();
	public abstract void setCountryIdRef(String countryIdRef);
}