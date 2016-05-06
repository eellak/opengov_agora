package gr.opengov.agora.bridges.diavgeia.domain;

import gr.opengov.agora.cms.ITaxonomyItem;

import java.util.Set;

public interface IUnitTaxonomyItem extends ITaxonomyItem{

	Set<DiavgeiaSigner> getSigners();

	void setSigners(Set<DiavgeiaSigner> signers);

	void addSigner(DiavgeiaSigner signer);

}
