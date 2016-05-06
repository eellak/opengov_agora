package gr.opengov.agora.bridges.diavgeia.domain;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import gr.opengov.agora.cms.TaxonomyItem;

public class OdeMemberTaxonomyItem extends TaxonomyItem implements IOdeMemberTaxonomyItem {
	private String latinName;
	private Set<DiavgeiaSigner> signers;
	private Set<IUnitTaxonomyItem> units;

	public OdeMemberTaxonomyItem(String id, String label){
		super(id, label);
	}
	
	@Override
	public String getLatinName() {
		return latinName;
	}
	@Override
	public void setLatinName(String latinName) {
		this.latinName = latinName;
	}
	@Override
	public Set<DiavgeiaSigner> getSigners() {
		return signers;
	}
	@Override
	public void addSigner(DiavgeiaSigner signer) {
		if (this.signers == null) { signers = new LinkedHashSet<DiavgeiaSigner>();}
		this.signers.add(signer);
	}	

	@Override
	public void setSigners(Set<DiavgeiaSigner> signers) {
		this.signers = signers;
	}

	@Override
	public Set<IUnitTaxonomyItem> getUnits() {
		return units;
	}
	@Override
	public void addUnit(UnitTaxonomyItem unit) {
		if (this.units == null) { units = new LinkedHashSet<IUnitTaxonomyItem>();}
		this.units.add(unit);
	}	

	@Override
	public void setUnits(Set<IUnitTaxonomyItem> units) {
		this.units = units;
	}	

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IOdeMemberTaxonomyItem ) ) return false;
		IOdeMemberTaxonomyItem rhs = (IOdeMemberTaxonomyItem)obj;
		return new EqualsBuilder()
			.appendSuper( super.equals( obj ) )
			.append( latinName, rhs.getLatinName() )
			.append( signers, rhs.getSigners() )
			.append( units, rhs.getUnits() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()			
			.appendSuper( 23 )
			.append( latinName )
			.append( signers )
			.append( units )
			.toHashCode();
	}
		
	
}
