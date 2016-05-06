package gr.opengov.agora.cms;

public interface ITaxonomyReference {
	public String getIdRef();
	public String getOther() throws UnsupportedOperationException; 
	public boolean supportsOther();
	public boolean isValidForTaxonomy( ITaxonomy taxonomy );
}
