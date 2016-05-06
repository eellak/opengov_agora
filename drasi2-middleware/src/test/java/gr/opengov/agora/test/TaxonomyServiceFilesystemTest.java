package gr.opengov.agora.test;

import static org.junit.Assert.*;
import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.converters.ICommonsOXMConverter;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.exceptions.DuplicateTaxonomyFoundException;
import gr.opengov.agora.exceptions.InvalidTaxonomyException;
import gr.opengov.agora.exceptions.TaxonomyPathNotFoundException;
import gr.opengov.agora.service.ITaxonomyService;
import gr.opengov.agora.service.TaxonomyServiceFilesystemImpl;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.web.TaxonomyController;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class TaxonomyServiceFilesystemTest {
	private static final Logger logger = LoggerFactory.getLogger(TaxonomyServiceFilesystemTest.class);
	@Resource( name="taxonomyService" )
	private ITaxonomyService service;	
	@Resource( name="taxonomyMarshaller" )
	private Jaxb2Marshaller taxonomyMarshaller;
	@Resource( name="commonsOxmConverter" )
	private ICommonsOXMConverter commonsConverter;	
	@Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	private static XMLUtils xmlUtils;	
	
	@BeforeClass
	public static void init() {
		xmlUtils = XMLUtils.newInstance();
		xmlUtils.setXmlPathPrefix( "taxonomies/" );
	}
	
	@Test
	public void testGetTaxonomyNames() {
		logger.debug( "Testing retrieving all taxonomy names" );
		List<String> names = service.getTaxonomyNames();
		logger.debug( "Names: " + names );
		assertTrue( names.size() > 0 );
		assertTrue( names.contains( "currency" ) );
	}
	
	@Test
	public void testGetInvalidTaxonomy() {
		logger.debug( "Testing retrieving an invalid taxonomy" );
		ITaxonomy taxonomy = service.getTaxonomy( "notexists", null );
		assertNull( taxonomy );		
	}
	
	/*
	 * Duplicate taxonomy definition should prevent deployment, it usually indicates a
	 * configuration error that may lead to unexpected behavior 
	 */
	@Test
	public void testDuplicateTaxonomy() throws Exception {
		logger.debug( "Testing using the same taxonomy twice in config" );
		File taxonomiesPath = new ClassPathResource( "taxonomies/tests/duplicate" ).getFile();
		assertNotNull( taxonomiesPath );
		TaxonomyServiceFilesystemImpl service = new TaxonomyServiceFilesystemImpl();
		service.setCommonsConverter ( commonsConverter );
		service.setMarshaller( taxonomyMarshaller );
		service.setTaxonomyPath( taxonomiesPath );
		try {
			service.getTaxonomyNames();
			fail();
		}
		catch ( DuplicateTaxonomyFoundException e ) {
			// SUCCESS!
		}
	}
	
	@Test
	public void testNoNameTaxonomy() throws Exception {
		logger.debug( "Testing using a taxonomy with no 'name' attribute. " );
		File taxonomiesPath = new ClassPathResource( "taxonomies/tests/noname" ).getFile();
		assertNotNull( taxonomiesPath );
		TaxonomyServiceFilesystemImpl service = new TaxonomyServiceFilesystemImpl();
		service.setCommonsConverter( commonsConverter );
		service.setMarshaller( taxonomyMarshaller );
		service.setTaxonomyPath( taxonomiesPath );
		try {
			service.getTaxonomyNames();
			fail();
		}
		catch ( InvalidTaxonomyException e ) {
			// SUCCESS!
		}		
	}
	
	@Test
	public void testTaxonomyPathNotFound() throws Exception {
		logger.debug( "Testing using invalid path to lad a taxonomy. " );
		File taxonomiesPath = null;
//		assertNotNull( taxonomiesPath );
		TaxonomyServiceFilesystemImpl service = new TaxonomyServiceFilesystemImpl();
		service.setCommonsConverter( commonsConverter );
		service.setMarshaller( taxonomyMarshaller );
		service.setTaxonomyPath( taxonomiesPath );
		try {
			service.getTaxonomyNames();
			fail();
		}
		catch ( TaxonomyPathNotFoundException e ) {
			// SUCCESS!
		}		
	}	
	
	@Test
	public void testIgnoreInvalidTaxonomy() throws Exception {
		logger.debug( "Testing ignoring an invalid taxonomy. " );
		File taxonomiesPath = new ClassPathResource( "taxonomies/tests/invalid" ).getFile();
		assertNotNull( taxonomiesPath );
		TaxonomyServiceFilesystemImpl service = new TaxonomyServiceFilesystemImpl();
		service.setCommonsConverter( commonsConverter );
		service.setMarshaller( taxonomyMarshaller );
		service.setTaxonomyPath( taxonomiesPath );
		assertEquals( service.getTaxonomyNames().size(), 1 );
	}
}
