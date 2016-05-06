package gr.opengov.agora.service;

import java.util.List;

import gr.opengov.agora.cms.ITaxonomy;

public interface ITaxonomyService {
	public List<String> getTaxonomyNames();
	public ITaxonomy getTaxonomy( String name, String itemId );
//	public ITaxonomy getDiavgeiaOdeMembersTaxonomy(String itemId);
//	public ITaxonomy getDiavgeiaOdeUnitsTaxonomy(String itemId);
	ITaxonomy getDiavgeiaOdeMembersTaxonomy(String orgId, boolean getUnits, boolean getSigners, String unitId);
	ITaxonomy getSignersOfUnitTaxonomy(String name, String orgId, String unitId);
}
