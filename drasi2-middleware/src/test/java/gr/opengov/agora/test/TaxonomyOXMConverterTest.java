package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.cms.ITaxonomyItem;
import gr.opengov.agora.cms.Taxonomy;
import gr.opengov.agora.cms.TaxonomyItem;
import gr.opengov.agora.converters.ICommonsOXMConverter;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.TaxonomyOXM;
import gr.opengov.agora.test.util.XMLUtils;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtil;
import org.custommonkey.xmlunit.XMLAssert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class TaxonomyOXMConverterTest {
	private static final Logger logger = LoggerFactory.getLogger(TaxonomyOXMConverterTest.class);
	@Autowired
	ICommonsOXMConverter commonsOxmConverter;
	
	@Test
	public void testTaxonomy() {
		logger.debug( "Testing taxonomy conversion." );
		ITaxonomy taxonomy = new Taxonomy();
		taxonomy.setName( "testCurrencyTaxonomy" );
		taxonomy.addItem( new TaxonomyItem( "EUR", "Euro" ) );
		taxonomy.addItem( new TaxonomyItem( "USD", "US Dollar" ) );
		TaxonomyOXM oxm = commonsOxmConverter.getTaxonomy( taxonomy );
		ITaxonomy obj = commonsOxmConverter.getTaxonomy( oxm );
		assertEquals( obj.getName(), taxonomy.getName() );
		assertEquals( obj.getItems().size(), taxonomy.getItems().size() );
		for ( ITaxonomyItem item: taxonomy.getItems() ) {
			assert( obj.getItems().contains( item ) );
		}
	}
	
	@Test
	public void testRecursiveTaxonomy() throws Exception {
		logger.debug( "Testing taxonomy conversion." );
		XMLUtils xmlUtils = XMLUtils.newInstance();
		xmlUtils.setXmlPathPrefix( "taxonomies/" );		
		TaxonomyOXM oxm = xmlUtils.unmarshal( "recursive.xml", TaxonomyOXM.class );
		ITaxonomy obj = commonsOxmConverter.getTaxonomy( oxm );
		TaxonomyOXM restored = commonsOxmConverter.getTaxonomy( obj );
		String xmlOriginal = IOUtil.toString( xmlUtils.marshal( oxm ) );
		String xmlRestored = IOUtil.toString( xmlUtils.marshal( restored ) );
		XMLAssert.assertXMLEqual( xmlOriginal, xmlRestored );
	}
	
	@Test
	public void testTaxonomyOxm() throws Exception {
		logger.debug( "Testing taxonomy conversion using XML file." );
		XMLUtils xmlUtils = XMLUtils.newInstance();
		xmlUtils.setXmlPathPrefix( "taxonomies/" );		
		ITaxonomy taxonomy = new Taxonomy();
		taxonomy.setName( "currency" );
		taxonomy.addItem( new TaxonomyItem( "EUR", "Euro" ) );
		taxonomy.addItem( new TaxonomyItem( "USD", "US Dollar" ) );	
		TaxonomyOXM oxm = xmlUtils.unmarshal( "currency.xml", TaxonomyOXM.class );
		ITaxonomy obj = commonsOxmConverter.getTaxonomy( oxm );
		assertEquals( obj.getName(), taxonomy.getName() );
//		assertEquals( obj.getItems().size(), taxonomy.getItems().size() );
		for ( ITaxonomyItem item: taxonomy.getItems() ) {
			assert( obj.getItems().contains( item ) );
		}
	}	
}
