package gr.opengov.agora.cms;

import java.util.List;

public interface ITaxonomy {

	public abstract String getName();

	public abstract void setName(String name);

	public abstract List<ITaxonomyItem> getItems();
	public abstract List<String> getIds();

	public abstract void setItems(List<ITaxonomyItem> items);

	public abstract void addItem(ITaxonomyItem item);
	
	public abstract ITaxonomyItem findItemAtTopLevel( String id );
	public abstract ITaxonomyItem findItemAtAnyLevel( String id );
	public abstract boolean containsAtTopLevel( String id );
	public abstract boolean containsAtAnyLevel( String id );

}