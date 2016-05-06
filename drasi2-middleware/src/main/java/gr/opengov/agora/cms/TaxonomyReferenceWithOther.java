package gr.opengov.agora.cms;

public abstract class TaxonomyReferenceWithOther implements ITaxonomyReference {	
	@Override
	public boolean supportsOther() {
		return true;
	}

	@Override
	public boolean isValidForTaxonomy(ITaxonomy taxonomy) {
		if ( getIdRef() == null && getOther() == null ) return false;
		if ( getIdRef() != null && getOther() != null ) return false;
		if ( getIdRef() != null ) {
			if ( !taxonomy.containsAtAnyLevel( getIdRef() ) ) return false;
		}
		return true;
	}

}
