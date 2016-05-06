package gr.opengov.agora.cms;

public interface ITaxonomyItem {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getLabel();

	public abstract void setLabel(String label);

	public abstract ITaxonomy getTaxonomy();

	public abstract void setTaxonomy(ITaxonomy taxonomy);

}