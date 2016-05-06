package gr.opengov.agora.test;

import static org.junit.Assert.*;
import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.Taxonomy;
import gr.opengov.agora.cms.TaxonomyItem;
import gr.opengov.agora.cms.TaxonomyReferenceWithOther;
import gr.opengov.agora.test.stub.TaxonomyStubFactory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaxonomyTest {
	private static final Logger logger = LoggerFactory.getLogger(TaxonomyTest.class);
	
	@Test
	public void testCheckExists() {
		logger.debug( "Testing taxonomy" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithoutChildrenStub();
		assertTrue( taxonomy.containsAtTopLevel( "1" ) );
		assertFalse( taxonomy.containsAtTopLevel( "3" ) );
		assertFalse( taxonomy.containsAtAnyLevel( "3" ) );
	}		
	
	@Test
	public void testCheckExistsInRecursive() {
		logger.debug( "Testing recursive taxonomy" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		assertTrue( taxonomy.containsAtTopLevel( "1" ) );
		assertFalse( taxonomy.containsAtTopLevel( "3" ) );
		assertTrue( taxonomy.containsAtAnyLevel( "3" ) );
	}
}
