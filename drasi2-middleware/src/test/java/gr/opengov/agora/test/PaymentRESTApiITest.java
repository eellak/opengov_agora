package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gr.opengov.agora.model.ArrayOfContracts;
import gr.opengov.agora.model.ArrayOfContractsShort;
import gr.opengov.agora.model.ArrayOfPayments;
import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.AuthenticationProfileOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.GetContractsShortResponse;
import gr.opengov.agora.model.GetPaymentsResponse;
import gr.opengov.agora.model.GetPaymentsShortResponse;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.SingleContractOXM;
import gr.opengov.agora.model.SinglePaymentOXM;
import gr.opengov.agora.model.StoreDecisionResponse;
import gr.opengov.agora.model.ValidationErrorOXM;
import gr.opengov.agora.test.util.Utility;
import gr.opengov.agora.test.util.WebException;
import gr.opengov.agora.test.util.WebUtils;
import gr.opengov.agora.test.util.XMLUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtil;
import org.apache.tika.io.IOUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.meterware.httpunit.AuthorizationRequiredException;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpInternalErrorException;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
public class PaymentRESTApiITest {
	private static final Logger logger = LoggerFactory
			.getLogger(PaymentRESTApiITest.class);
	private XMLUtils xmlUtils;
	private Utility utils;
	@Resource( name="webUtils" )
	private WebUtils webUtils;
	
	@Before
	public void init() {
		webUtils.setLogger(logger);
		xmlUtils = XMLUtils.newInstance();
		utils = new Utility(xmlUtils);
	}
	
	private void printValidationErrors( ArrayOfValidationErrors errors ) {
		logger.debug( "\nValidation Errors:\n" );
		for ( ValidationErrorOXM oxm: errors.getError() ) { 
			logger.debug( oxm.getField() + " - " + oxm.getXpath()  + " - " + oxm.getMsg() );
		}
	}	
	
	@Test
	public void testGetPaymentsNoAuthentication() throws Exception {	
		WebResponse response = null;
		try {
			logger.debug("Testing connection without authentication");
			WebConversation wc = new WebConversation();
			WebRequest request = new GetMethodWebRequest( webUtils.getPaymentsUrl() );
			webUtils.setupRequest( request );
			
			response = wc.getResponse( request );
			if ( response.getResponseCode() != HttpStatus.SC_OK ) {
				logger.error( "Server Error: " + IOUtils.toString( response.getInputStream() ) );
			}
			assertEquals( response.getResponseCode(), HttpStatus.SC_OK );
		}
		catch ( AuthorizationRequiredException e ) { 
			logger.error( e.getMessage() );
			fail();			
		}
		catch ( HttpInternalErrorException e ) {
			logger.error( e.getMessage() );
			logger.error( e.getResponseMessage() );	
			fail();
		}
	}

	@Test
	public void testGetPaymentsAuthSuccess() throws Exception {
		WebResponse response = null;
		try {
			logger.debug("Testing basic authentication, valid credentials");
			
			WebConversation wc = webUtils.authorizedWebConversation();
			wc.setExceptionsThrownOnErrorStatus( false );
			WebRequest request = new GetMethodWebRequest( webUtils.getPaymentsUrl() );
			webUtils.setupRequest( request );
			response = wc.getResponse( request );
			if ( response.getResponseCode() != HttpStatus.SC_OK ) {
				logger.error( "Server Error: " + IOUtils.toString( response.getInputStream() ) );
			}
			assertEquals( response.getResponseCode(), HttpStatus.SC_OK );
		}
		catch ( HttpInternalErrorException e ) {
			logger.error( e.getMessage() );
			logger.error( e.getResponseMessage() );	
			fail();
		}
	}

	@Test
	public void testGetPaymentsOfContract() throws Exception {
		WebResponse response = null;
		try {
			logger.debug("Testing basic authentication, valid credentials");
			logger.debug("Testing getting of payments of specific contract");
			
			logger.debug("Testing sending a single contract");
			List<String> uCodes = utils.getUniqueDocumentCodesFromContract("single-contract.xml");			
			StoreDecisionResponse xmlResponse = webUtils.storeContracts("single-contract.xml");
			assertNotNull(xmlResponse);
			assertNull(xmlResponse.getValidationErrors());
			assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
			String contractId = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
			assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
		
			logger.debug("Testing sending a single payment");
			uCodes = utils.getUniqueDocumentCodesFromPayment("single-payment.xml");	
			webUtils.storePayments("single-payment.xml");
			for ( int i = 0; i < 3; i++ ) { 
				xmlResponse = webUtils.storePayment("single-payment.xml", contractId);
				assertNotNull(xmlResponse);
				
				if (xmlResponse.getValidationErrors() != null){
					logger.debug("----------there are errors----------");
					printValidationErrors(xmlResponse.getValidationErrors());
					logger.debug("----------end of errors----------");
				}
				assertNull(xmlResponse.getValidationErrors());
				assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
			}
			

			WebResponse paymentResponse = webUtils.getResponse( webUtils.getPaymentsUrl() + "?output=short&contractid="+contractId );
			GetPaymentsShortResponse payments = xmlUtils.unmarshal( paymentResponse.getInputStream(), GetPaymentsShortResponse.class );
			logger.debug("payments cnt:"+ payments.getPayments().getPayment().size());
			logger.debug("payments for contract="+contractId);
			assertTrue( payments.getPayments().getPayment().size() == 3 );	
			
			if ( paymentResponse.getResponseCode() != HttpStatus.SC_OK ) {
				logger.error( "Server Error: " + IOUtils.toString( paymentResponse.getInputStream() ) );
			}
			assertEquals( paymentResponse.getResponseCode(), HttpStatus.SC_OK );
	
		}
		catch ( HttpInternalErrorException e ) {
			logger.error( e.getMessage() );
			logger.error( e.getResponseMessage() );	
			fail();
		}
	}	
	
	@Test
	public void testGetPaymentsAuthFailed() throws Exception {
		try {
			logger.debug("Testing basic authentication, invalid credentials");
			WebConversation wc = webUtils.authorizedWebConversation( "", "366_admin", "3661" );		
			WebRequest request = new GetMethodWebRequest( webUtils.getContractsUrl() );
			webUtils.setupRequest( request );
			wc.getResponse( request );			
			fail();
		}
		catch ( AuthorizationRequiredException e ) { }
	}
		
	@Test
	public void testPostSinglePayment() throws Exception {
		logger.debug("Testing sending a single payment");
		List<String> uCodes = utils.getUniqueDocumentCodesFromPayment("single-payment.xml");			
		StoreDecisionResponse xmlResponse = webUtils.storePayments("single-payment.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String uid = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
	}
	
	@Test
	public void testPostSinglePaymentWithContract() throws Exception {
		logger.debug("Testing sending a single contract");
		List<String> uCodes = utils.getUniqueDocumentCodesFromContract("single-contract.xml");			
		StoreDecisionResponse xmlResponse = webUtils.storeContracts("single-contract.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String contractId = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
	
		logger.debug("Testing sending a single payment");
		uCodes = utils.getUniqueDocumentCodesFromPayment("single-payment.xml");			
		xmlResponse = webUtils.storePayment("single-payment.xml", contractId);
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
	}
	
//	@Ignore
	@Test
	public void testSamePayment() throws Exception {
		logger.debug( "Testing sending and receiving a payment" );		
		StoreDecisionResponse xmlResponse = webUtils.storePayments( "single-payment.xml" );
		PaymentsOXM paymentsOriginal = xmlUtils.unmarshal( "single-payment.xml", PaymentsOXM.class );		
		assertTrue( xmlResponse.getStorageReferences() != null );
		assertEquals( xmlResponse.getStorageReferences().getStorageReference().size(), 1 );
		String savedId = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		ArrayOfPayments ret = webUtils.getPayments( webUtils.getPaymentsUrl() + savedId ).getPayments();
		PaymentsOXM paymentsRetrieved = new PaymentsOXM();
		paymentsRetrieved.getPayment().addAll(  ret.getPayment() );
						
		assertEquals( paymentsOriginal.getPayment().size(), paymentsRetrieved.getPayment().size() );
		
		paymentsOriginal.getPayment().get(0).setId( savedId );
		paymentsOriginal.getPayment().get(0).setDocument( null );
		paymentsOriginal.getPayment().get(0).setDocumentUrl( null );
		paymentsRetrieved.getPayment().get(0).setCmsMetadata( null );
		paymentsRetrieved.getPayment().get(0).setDocument( null );
		paymentsRetrieved.getPayment().get(0).setDocumentUrl( null );		
				
		String xmlOriginal = IOUtil.toString( xmlUtils.marshal(paymentsOriginal) );
		String xmlRetrieved = IOUtil.toString( xmlUtils.marshal(paymentsRetrieved ) );
		utils.saveFile( xmlOriginal, "original.xml" );
		utils.saveFile( xmlRetrieved, "retrieved.xml" );
		XMLAssert.assertXMLEqual( xmlOriginal, xmlRetrieved );
	}
	
	@Test
	public void testUpdatePayment() throws Exception {
		logger.debug("Testing updating a payment" );				
		StoreDecisionResponse xmlResponse = webUtils.storePayments( "single-payment.xml" );
		String uid = xmlResponse.getStorageReferences().getStorageReference().get( 0 ).getId();
		logger.debug( "Updating saved id: " + uid );
		webUtils.updatePayment( "root-payment.xml", uid );
		// Test that document is ok
		logger.debug( "Update ok" );
		SinglePaymentOXM oxm = xmlUtils.unmarshal( "root-payment.xml", SinglePaymentOXM.class );
		logger.debug( "Getting response" );
		WebResponse response = webUtils.getResponse( webUtils.getPaymentsUrl() + "documents/original/" + uid );
		logger.debug( "Response: ok" );
		
		byte[] b = IOUtils.toByteArray( response.getInputStream() );
		assertTrue( Arrays.equals( oxm.getDocument(), b ) );
	}

	@Test
	public void testPostMultiplePayments() throws Exception {
		logger.debug("Testing sending multiple payments");
		StoreDecisionResponse xmlResponse = webUtils.storePayments("payments.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
	}
	
	@Test
	public void testMultipost() throws Exception {
		logger.debug("Testing multiple posts");
		List<String> uCodes = utils.getUniqueDocumentCodesFromPayment("payments.xml");
		assertTrue(uCodes.size() > 0);
		int repeats = 20;
		for (int i = 0; i < repeats; i++) {
			StoreDecisionResponse xmlResponse = webUtils.storePayments("payments.xml");
			assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), uCodes.size());
		}	
	}

	@Test
	public void testPostTooMany() throws Exception {
		logger.debug("Testing posting too many payments in one request");
		try {
			webUtils.storePayments("payments6.xml");
			fail("Succeeded in storing too many payments");
		} catch (WebException e) {
			assertEquals( e.getResponseCode(), HttpStatus.SC_FORBIDDEN);
		}
	}

	private void doTestGetSpecificPayment( String xmlFile, boolean log ) throws Exception {
		StoreDecisionResponse xmlResponse = webUtils.storePayments( xmlFile );
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String id = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		if ( log ) logger.debug("...Id: " + id);
		ArrayOfPayments payments = webUtils.getPayments( webUtils.getPaymentsUrl() + id ).getPayments();
		assertEquals( payments.getPayment().get( 0 ).getId(), id );
		if ( log ) logger.debug("...Retrieving document");
		WebResponse response = webUtils.getResponse( webUtils.getPaymentsUrl() + "documents/original/" + id );
		assertEquals(response.getContentType(), "application/pdf");
		byte[] b = IOUtils.toByteArray( response.getInputStream() );
		long contentLength = response.getContentLength();
		if ( log ) {
			logger.debug( "...Content length: " + contentLength );			
			logger.debug( "...Actual length: " + b.length );			
		}
		assertEquals( contentLength, b.length );
	}
	
//	@Test
//	public void testGetSpecificContract( ) throws Exception {
//		logger.debug( "Testing retrieving contract: 25k" );
//		doTestGetSpecificContract( "single-contract.xml", true );
//	}
//	
//	@Test
//	public void testGetSpecificContractMedium( ) throws Exception {
//		logger.debug( "Testing retrieving contract: 450k" );
//		doTestGetSpecificContract( "single-contract-450k.xml", true );
//	}
//		
//	@Test
//	public void testGetSpecificContractLarge( ) throws Exception {
//		logger.debug( "Testing retrieving contract: 1.5M" );
//		doTestGetSpecificContract( "single-contract-1500k.xml", true );
//	}
//	
//	@Ignore
//	@Test
//	public void testGetSpecificContractVeryLarge( ) throws Exception {
//		logger.debug( "Testing retrieving contract: 3.1M" );
//		doTestGetSpecificContract( "single-contract-3100k.xml", true );
//	}	
//
//	@Ignore
//	@Test
//	public void testGetSpecificContractHuge( ) throws Exception {
//		logger.debug( "Testing retrieving contract: 16M" );
//		doTestGetSpecificContract( "single-contract-16m.xml", true );
//	}	
//
//	@Ignore	
//	@Test
//	public void testGetSpecificContractGargantuan( ) throws Exception {
//		logger.debug( "Testing retrieving contract: 46M" );
//		doTestGetSpecificContract( "single-contract-46m.xml", true );
//	}
//	
//	@Ignore
//	@Test
//	public void testBenchmarkHuge( ) throws Exception {
//		logger.debug( "Testing benchmark for retrieving contract: 16M" );
//		int REPEATS = 100;
//		long average = 0;
//		for ( int i = 1; i <= REPEATS; i++ ) {
//			long start = System.currentTimeMillis();
//			doTestGetSpecificContract( "single-contract-16m.xml", false );
//			long end = System.currentTimeMillis();
//			long duration = ( end - start );
//			average += duration;
//			logger.debug( "Test: " + i + ", Time: " + duration + " msec" );
//		}
//		average /= REPEATS;
//		logger.debug( "\nAverage: " + average + " msec " );		
//	}		
}
