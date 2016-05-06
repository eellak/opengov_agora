package gr.opengov.agora.cms;

public abstract class TaxonomyReference implements ITaxonomyReference {
	
	@Override
	public String getOther() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean supportsOther() {		
		return false;
	}

	@Override
	public boolean isValidForTaxonomy(ITaxonomy taxonomy) {
		if ( getIdRef() == null ) return false;
		if ( !taxonomy.containsAtAnyLevel( getIdRef() ) ) return false;
		return true;
	}

}
