package gr.opengov.agora.bridges.diavgeia.domain;

import java.util.Set;

import gr.opengov.agora.cms.ITaxonomyItem;

public interface IOdeMemberTaxonomyItem extends ITaxonomyItem{

	public String getLatinName();

	public void setLatinName(String latinName);

	Set<DiavgeiaSigner> getSigners();

	void setSigners(Set<DiavgeiaSigner> signers);

	void addSigner(DiavgeiaSigner signer);

	Set<IUnitTaxonomyItem> getUnits();

	void addUnit(UnitTaxonomyItem unit);

	void setUnits(Set<IUnitTaxonomyItem> units);


}
