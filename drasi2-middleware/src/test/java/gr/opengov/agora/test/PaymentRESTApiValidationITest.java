package gr.opengov.agora.test;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.annotation.Resource;

import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.StoreDecisionResponse;
import gr.opengov.agora.model.ValidationErrorOXM;
import gr.opengov.agora.test.util.WebException;
import gr.opengov.agora.test.util.WebUtils;
import gr.opengov.agora.test.util.XMLUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
public class PaymentRESTApiValidationITest {
	public static final Logger logger = LoggerFactory.getLogger(PaymentRESTApiValidationITest.class);
	@Resource( name="webUtils" )
	private WebUtils webUtils;
	private XMLUtils xmlUtils;
	
	@Before
	public void init() {
		webUtils.setLogger(logger);
		xmlUtils = XMLUtils.newInstance();
	}

	private void printValidationErrors( ArrayOfValidationErrors errors ) {
		logger.debug( "\nValidation Errors:\n" );
		for ( ValidationErrorOXM oxm: errors.getError() ) { 
			logger.debug( oxm.getField() + " - " + oxm.getXpath()  + " - " + oxm.getMsg() );
		}
	}
	
	@Test
	public void testInvalidContract() throws IOException, WebException, SAXException{
		logger.debug("Testing sending a single payment, invalid contract");
		PaymentsOXM payments = xmlUtils.unmarshal( "single-payment.xml", PaymentsOXM.class );
		payments.getPayment().get(0).setContractId("test");
		StoreDecisionResponse xmlResponse = webUtils.storeDecisions( xmlUtils.marshal( payments ), webUtils.getPaymentsUrl() );		
		assertNotNull(xmlResponse.getValidationErrors());
		printValidationErrors( xmlResponse.getValidationErrors() );
	}


}
