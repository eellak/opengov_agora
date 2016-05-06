package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gr.opengov.agora.model.TaxonomyOXM;
import gr.opengov.agora.test.util.Utility;
import gr.opengov.agora.test.util.WebException;
import gr.opengov.agora.test.util.WebUtils;
import gr.opengov.agora.test.util.XMLUtils;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.meterware.httpunit.WebResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
public class TaxonomyRESTApiITest {
	private static final Logger logger = LoggerFactory
			.getLogger(TaxonomyRESTApiITest.class);
	@Resource( name="webUtils" )
	private WebUtils webUtils;
	private XMLUtils xmlUtils;
	private Utility utils;
	
	@Before
	public void init() {
		webUtils.setLogger(logger);		
		xmlUtils = XMLUtils.newInstance();
		xmlUtils.setXmlPathPrefix( "taxonomies/" );
		utils = new Utility(xmlUtils);
	}
	
	@Test
	public void testGetRequiredTaxonomies() throws Exception {
		logger.debug( "Testing required taxonomies" );
		String[] taxonomies = new String[] { "currency", "contracting_authority", "award_procedure", "cpv_codes", "test", "contract_type", "deletion_type", "commission_criteria" };
		for ( String taxonomy: taxonomies ) {
			logger.debug( "..." + taxonomy );
			WebResponse response = webUtils.getResponse( webUtils.getBaseUrl() + "taxonomy/" + taxonomy );
			TaxonomyOXM oxm = xmlUtils.unmarshal( response.getInputStream(), TaxonomyOXM.class );
			assertEquals( oxm.getName(), taxonomy );
			assertTrue( oxm.getItem().size() > 0 );
		}
	}
			
	@Test
	public void testGetInvalidTaxonomy( ) throws Exception {
		logger.debug( "Testing retrieving invalid taxonomy" );
		try {
			WebResponse response = webUtils.getResponse( webUtils.getBaseUrl() + "taxonomy/none" );
			fail();
		}
		catch ( WebException e ) {	
			assertEquals( e.getResponseCode(), HttpStatus.SC_NOT_FOUND );
		}
	}	
}
