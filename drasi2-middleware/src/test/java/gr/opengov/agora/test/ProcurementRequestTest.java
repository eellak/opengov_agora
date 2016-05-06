package gr.opengov.agora.test;

import static org.junit.Assert.*;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.test.util.XMLUtils;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class ProcurementRequestTest {
	private static final Logger logger = LoggerFactory.getLogger(ProcurementRequestTest.class);
	private XMLUtils xmlUtils;
	
	@Before
	public void init() throws Exception {
		xmlUtils = XMLUtils.newInstance( XMLUtils.PROCUREMENT_REQUEST );
	}

	@Test
	public void testTestUnmarshall() throws Exception {
		logger.debug( "Testing valid XML test case" );
		ProcurementRequestsOXM oxm = xmlUtils.unmarshal( "single-request.xml", ProcurementRequestsOXM.class );
		assertNotNull( oxm );	
	}	
	
}
