package gr.opengov.agora.bridges.diavgeia.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import gr.opengov.agora.cms.TaxonomyItem;

public class UnitTaxonomyItem extends TaxonomyItem implements IUnitTaxonomyItem {
	private Set<DiavgeiaSigner> signers;

	public UnitTaxonomyItem(String id, String label){
		super(id, label);
	}
	
	@Override
	public Set<DiavgeiaSigner> getSigners() {
		return signers;
	}
	@Override
	public void setSigners(Set<DiavgeiaSigner> signers) {
		this.signers = signers;
	}
	
	@Override
	public void addSigner(DiavgeiaSigner signer) {
		if (this.signers == null) { signers = new HashSet<DiavgeiaSigner>();}
		this.signers.add(signer);
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IUnitTaxonomyItem ) ) return false;
		IUnitTaxonomyItem rhs = (IUnitTaxonomyItem)obj;
		return new EqualsBuilder()
			.appendSuper( super.equals( obj ) )
			.append( signers, rhs.getSigners() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()			
			.appendSuper( 23 )
			.append( signers )
			.toHashCode();
	}
		
	
}
