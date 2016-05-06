package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.cms.ITaxonomyItem;
import gr.opengov.agora.cms.Taxonomy;
import gr.opengov.agora.cms.TaxonomyItem;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.model.TaxonomyOXM;
import gr.opengov.agora.test.util.XMLUtils;

import javax.annotation.Resource;

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
public class ContractOXMConverterTest {
	private static final Logger logger = LoggerFactory.getLogger(ContractOXMConverterTest.class);
	@Resource( name="sampleContract1" )
	private IContract sampleContract1;
	@Autowired
	IContractOXMConverter converter;	
	
	// Should fix this to take care of assymetry in documents conversion
	@Ignore
	@Test
	public void testReflex() {
		logger.debug( "Testing convert-uncovert-convert equality." );
		assertNotNull( converter );
		ContractOXM oxm = converter.toXML( sampleContract1 );
		IContract newContract = converter.toObject( oxm );
		assertEquals( sampleContract1, newContract );
	}	
}
