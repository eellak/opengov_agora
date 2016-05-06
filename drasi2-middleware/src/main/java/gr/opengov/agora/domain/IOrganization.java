package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.util.LazyEntity;

public interface IOrganization extends LazyEntity {

	String getName();

	void setName(String name);

	String getFormalName();

	void setFormalName(String formalName);

	String getAfm();

	void setAfm(String afm);

	String getAddress();

	void setAddress(String address);

	String getAddressNo();

	void setAddressNo(String addressNo);

	String getAddressPostal();

	void setAddressPostal(String addressPostal);

	String getCity();

	void setCity(String city);

	String getCountry();

	void setCountry(String country);

	int getIdRef();

	void setIdRef(int idRef);

	String getNuts();

	void setNuts(String nuts);

	String getOrganizationType();

	void setOrganizationType(String organizationType);

}
