package gr.opengov.agora.cms;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TaxonomyItem implements ITaxonomyItem {
	private String id;
	private String label;
	private ITaxonomy taxonomy;
	
	public TaxonomyItem() {
		
	}
	
	public TaxonomyItem(String id, String label) {
		super();
		this.id = id;
		this.label = label;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ITaxonomyItem#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ITaxonomyItem#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ITaxonomyItem#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ITaxonomyItem#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String label) {
		this.label = label;
	}
		
	@Override
	public ITaxonomy getTaxonomy() {
		return taxonomy;
	}

	@Override
	public void setTaxonomy(ITaxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof ITaxonomyItem ) ) return false;
		ITaxonomyItem rhs = (ITaxonomyItem)obj;
		return new EqualsBuilder()
			.append( id, rhs.getId() )
			.append( label, rhs.getLabel() )
			.append( taxonomy, rhs.getTaxonomy() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()			
			.append( id )
			.append( label )
			.append( taxonomy )
			.hashCode();
	}
	
}
