package gr.opengov.agora.cms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Taxonomy implements ITaxonomy {
	private String name;
	private List<ITaxonomyItem> items;
	
	public Taxonomy() {
		this.items = new LinkedList<ITaxonomyItem>();
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ITaxonomy#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ITaxonomy#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ITaxonomy#getItems()
	 */
	@Override
	public List<ITaxonomyItem> getItems() {
		return items;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ITaxonomy#setItems(java.util.List)
	 */
	@Override
	public void setItems(List<ITaxonomyItem> items) {
		this.items.clear();
		this.items.addAll(items);
	}	
	
	@Override
	public void addItem( ITaxonomyItem item ) {
		items.add( item );
	}
		
	@Override
	public ITaxonomyItem findItemAtTopLevel( String id ) {
		for ( ITaxonomyItem item: items ) {
			if ( item.getId().equalsIgnoreCase( id ) ) return item;
		}
		return null;
	}
	
	@Override
	public ITaxonomyItem findItemAtAnyLevel( String id ) {
		for ( ITaxonomyItem item: items ) {
			if ( item.getId().equalsIgnoreCase( id ) ) return item;
			if ( item.getTaxonomy() != null ) {
				ITaxonomyItem child = item.getTaxonomy().findItemAtAnyLevel( id );
				if ( child != null ) return child;
			}
		}
		return null;
	}

	@Override
	public boolean containsAtTopLevel(String id) {
		return findItemAtTopLevel( id ) != null;
	}
	
	@Override
	public boolean containsAtAnyLevel(String id) {
		return findItemAtAnyLevel( id ) != null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof ITaxonomy ) ) return false;
		ITaxonomy rhs = (ITaxonomy)obj;
		return new EqualsBuilder()			
			.append( name, rhs.getName() )
			.append( items, rhs.getItems() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()			
			.append( name )
			.append( items )
			.hashCode();
	}
	
	@Override
	public List<String> getIds() {
		List<String> ids = new ArrayList<String>();
		getIdsOfItems(items, ids);
		return ids;
	}
	
	private void getIdsOfItems(List<ITaxonomyItem> iTaxonomyItemList, List<String> itemIds){
		for (ITaxonomyItem iTaxonomyItem:iTaxonomyItemList){
			getIdsOfItem(iTaxonomyItem, itemIds);
		}		
	}
	
	private void getIdsOfItem(ITaxonomyItem iTaxonomyItem, List<String> itemIds){
		itemIds.add(iTaxonomyItem.getId());
		
		if ((iTaxonomyItem.getTaxonomy() != null) && (iTaxonomyItem.getTaxonomy().getItems() != null) && (iTaxonomyItem.getTaxonomy().getItems().size() > 0)){
			getIdsOfItems(iTaxonomyItem.getTaxonomy().getItems(), itemIds);
		}			
	}	
}
