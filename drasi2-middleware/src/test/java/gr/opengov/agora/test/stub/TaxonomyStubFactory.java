package gr.opengov.agora.test.stub;

import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.cms.Taxonomy;
import gr.opengov.agora.cms.TaxonomyItem;

public class TaxonomyStubFactory {
	public static ITaxonomy getTaxonomyWithoutChildrenStub() {
		ITaxonomy taxonomy = new Taxonomy();
		taxonomy.setName( "testTaxonomy" );
		taxonomy.addItem( new TaxonomyItem( "1", "item 1" ) );
		taxonomy.addItem( new TaxonomyItem( "2", "item 2" ) );
		return taxonomy;
	}
	
	public static ITaxonomy getTaxonomyWithChildrenStub() {
		ITaxonomy taxonomy = new Taxonomy();
		taxonomy.setName( "testTaxonomy" );
		taxonomy.addItem( new TaxonomyItem( "1", "item 1" ) );
		taxonomy.addItem( new TaxonomyItem( "2", "item 2" ) );
		ITaxonomy child = new Taxonomy();
		child.addItem( new TaxonomyItem( "3", "item 3 " ) );
		taxonomy.getItems().get(1).setTaxonomy( child );
		return taxonomy;
	}
}
