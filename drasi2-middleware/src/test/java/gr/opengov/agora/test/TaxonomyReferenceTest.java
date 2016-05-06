package gr.opengov.agora.test;

import static org.junit.Assert.*;
import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.Taxonomy;
import gr.opengov.agora.cms.TaxonomyItem;
import gr.opengov.agora.cms.TaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReferenceWithOther;
import gr.opengov.agora.test.stub.TaxonomyStubFactory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaxonomyReferenceTest {
	private static final Logger logger = LoggerFactory.getLogger(TaxonomyReferenceTest.class);
	
	@Test
	public void testTaxonomyReferenceWithOtherFail1() {
		logger.debug( "Testing invalid taxonomy reference (both refId and other non-null)" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		ITaxonomyReference ref = new TaxonomyReferenceWithOther() {			
			@Override
			public String getOther() throws UnsupportedOperationException {
				return "other";
			}
			
			@Override
			public String getIdRef() {
				return "3";
			}
		};
		assertFalse( ref.isValidForTaxonomy( taxonomy ) );
	}
	
	@Test
	public void testTaxonomyReferenceWithOtherFail2() {
		logger.debug( "Testing invalid taxonomy reference (both refId and other null)" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		ITaxonomyReference ref = new TaxonomyReferenceWithOther() {			
			@Override
			public String getOther() throws UnsupportedOperationException {
				return null;
			}
			
			@Override
			public String getIdRef() {
				return null;
			}
		};
		assertFalse( ref.isValidForTaxonomy( taxonomy ) );
	}
	
	@Test
	public void testTaxonomyReferenceWithOtherFail3() {
		logger.debug( "Testing invalid taxonomy reference (invalid refId)" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		ITaxonomyReference ref = new TaxonomyReferenceWithOther() {			
			@Override
			public String getOther() throws UnsupportedOperationException {
				return null;
			}
			
			@Override
			public String getIdRef() {
				return "nonexists";
			}
		};
		assertFalse( ref.isValidForTaxonomy( taxonomy ) );
	}
	
	@Test
	public void testTaxonomyReferenceWithOtherPass1() {
		logger.debug( "Testing valid taxonomy reference (valid idRef)" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		ITaxonomyReference ref = new TaxonomyReferenceWithOther() {			
			@Override
			public String getOther() throws UnsupportedOperationException {
				return null;
			}
			
			@Override
			public String getIdRef() {
				return "3";
			}
		};
		assertTrue( ref.isValidForTaxonomy( taxonomy ) );
	}
	
	@Test
	public void testTaxonomyReferenceWithOtherPass2() {
		logger.debug( "Testing valid taxonomy reference (using other)" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		ITaxonomyReference ref = new TaxonomyReferenceWithOther() {			
			@Override
			public String getOther() throws UnsupportedOperationException {
				return "other";
			}
			
			@Override
			public String getIdRef() {
				return null;
			}
		};
		assertTrue( ref.isValidForTaxonomy( taxonomy ) );
	}
	
	@Test
	public void testTaxonomyReferenceFail1() {
		logger.debug( "Testing invalid taxonomy reference (null refId)" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		ITaxonomyReference ref = new TaxonomyReference() {			
			@Override
			public String getIdRef() {
				return null;
			}
		};			
		assertFalse( ref.isValidForTaxonomy( taxonomy ) );
	}
	
	@Test
	public void testTaxonomyReferenceFail2() {
		logger.debug( "Testing invalid taxonomy reference (invalid refId)" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		ITaxonomyReference ref = new TaxonomyReference() {			
			@Override
			public String getIdRef() {
				return "none";
			}
		};			
		assertFalse( ref.isValidForTaxonomy( taxonomy ) );
	}
	
	@Test
	public void testTaxonomyReferencePass() {
		logger.debug( "Testing valid taxonomy reference" );
		ITaxonomy taxonomy = TaxonomyStubFactory.getTaxonomyWithChildrenStub();
		ITaxonomyReference ref = new TaxonomyReference() {			
			@Override
			public String getIdRef() {
				return "3";
			}
		};			
		assertTrue( ref.isValidForTaxonomy( taxonomy ) );
	}
}
