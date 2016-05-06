package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gr.opengov.agora.model.ArrayOfContracts;
import gr.opengov.agora.model.ContractItemOXM;
import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.GetContractsShortResponse;
import gr.opengov.agora.model.SingleContractOXM;
import gr.opengov.agora.model.StoreDecisionResponse;
import gr.opengov.agora.test.util.IDatabaseHandler;
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
public class UtilsControllerRESTApiITest {
	private static final Logger logger = LoggerFactory
			.getLogger(UtilsControllerRESTApiITest.class);
	@Resource( name="webUtils" )
	private WebUtils webUtils;
	private XMLUtils xmlUtils;
	private Utility utils;
	
	@Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	
	@Before
	public void init() {
		webUtils.setLogger( logger);
		xmlUtils = XMLUtils.newInstance();
		utils = new Utility(xmlUtils);
	}
	
	@Test
	public void testGetContractsNoAuthentication() throws Exception {	
		WebResponse response = null;
		try {
			logger.debug("Testing connection without authentication");
			WebConversation wc = new WebConversation();
			WebRequest request = new GetMethodWebRequest( webUtils.getContractsUrl() );
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
	public void testGetContractsAuthSuccess() throws Exception {
		WebResponse response = null;
		try {
			logger.debug("Testing basic authentication, valid credentials");
			
			WebConversation wc = webUtils.authorizedWebConversation();	
			WebRequest request = new GetMethodWebRequest( webUtils.getContractsUrl() );
			webUtils.setupRequest( request );
			response = wc.getResponse( request );
			assertEquals( response.getResponseCode(), HttpStatus.SC_OK );
		}
		catch ( HttpInternalErrorException e ) {
			logger.error( e.getMessage() );
			logger.error( e.getResponseMessage() );	
			fail();
		}
	}


	@Test
	public void testGetContractsAuthFailed() throws Exception {
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
	public void testPostSingleContract() throws Exception {
		logger.debug("Testing sending a single contract");
		List<String> uCodes = utils.getUniqueDocumentCodesFromContract("single-contract.xml");			
		StoreDecisionResponse xmlResponse = webUtils.storeContracts("single-contract.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String uid = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
	}
	
	@Test
	public void testPostSingleContractSimpleUser() throws Exception {
		logger.debug("Testing sending a single contract");
		List<String> uCodes = utils.getUniqueDocumentCodesFromContract("single-contract.xml");			
		StoreDecisionResponse xmlResponse = webUtils.storeContracts("single-contract.xml", "366_1", "3661" );
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String uid = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
	}
	
	@Test
	public void testPostSingleContractSimpleIncorrectUser() throws Exception {
		logger.debug("Testing sending a single contract");
		List<String> uCodes = utils.getUniqueDocumentCodesFromContract("single-contract.xml");		
		try{
			StoreDecisionResponse xmlResponse = webUtils.storeContracts("single-contract.xml", "12_63", "1263" );
			fail();
		}
		catch ( WebException e ) {
			assertEquals( e.getResponseCode(), HttpStatus.SC_FORBIDDEN );			
		}

	}		
	
	@Test
	public void testSameContract() throws Exception {
		logger.debug( "Testing sending and receiving a contract" );		
		StoreDecisionResponse xmlResponse = webUtils.storeContracts( "single-contract.xml" );
		ContractsOXM contractOriginal = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );		
		assertTrue( xmlResponse.getStorageReferences() != null );
		assertEquals( xmlResponse.getStorageReferences().getStorageReference().size(), 1 );
		String savedId = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		ArrayOfContracts ret = webUtils.getContracts( webUtils.getContractsUrl() + savedId ).getContracts();
		ContractsOXM contractRetrieved = new ContractsOXM();
		contractRetrieved.getContract().addAll(  ret.getContract() );
						
		assertEquals( contractOriginal.getContract().size(), contractRetrieved.getContract().size() );
		contractOriginal.getContract().get(0).setId( savedId );
		contractOriginal.getContract().get(0).setDocument( null );
		contractOriginal.getContract().get(0).setDocumentUrl( null );
		contractRetrieved.getContract().get(0).setCmsMetadata( null );
		contractRetrieved.getContract().get(0).setDocument( null );
		contractRetrieved.getContract().get(0).setDocumentUrl( null );		
				
		for (ContractOXM contract:contractRetrieved.getContract()){
			for (ContractItemOXM contractItem:contract.getContractItems().getItem()){
				contractItem.setContract(null);
			}
		}
		
		String xmlOriginal = IOUtil.toString( xmlUtils.marshal(contractOriginal) );
		String xmlRetrieved = IOUtil.toString( xmlUtils.marshal(contractRetrieved ) );
		utils.saveFile( xmlOriginal, "contractoriginal.xml" );
		utils.saveFile( xmlRetrieved, "contractretrieved.xml" );
		XMLAssert.assertXMLEqual( xmlOriginal, xmlRetrieved );
	}
	
	@Test
	public void testGetShortContract() throws Exception {
		logger.debug( "Testing receiving short contract info a contract" );		
		logger.debug( "...Storing some contracts" );
		for ( int i = 0; i < 3; i++ ) { 
			webUtils.storeContracts( "single-contract.xml" );
		}
		WebResponse response = webUtils.getResponse( webUtils.getContractsUrl() + "?output=short" );
		GetContractsShortResponse contracts = xmlUtils.unmarshal( response.getInputStream(), GetContractsShortResponse.class );
		assertTrue( contracts.getContracts().getContract().size() > 0 );
	}

	@Test
	public void testExtendContract() throws Exception {
		logger.debug( "Testing extending a contract" );
		StoreDecisionResponse xmlResponse = webUtils.storeContracts( "single-contract.xml" );
		String id = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		logger.debug( "...ID: " + id );
		ContractsOXM contracts = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );
		contracts.getContract().get(0).setExtendsContract( id );
		contracts.getContract().get(0).getUntil().add( Calendar.YEAR, 1 );
		xmlResponse = webUtils.storeDecisions( xmlUtils.marshal( contracts ), webUtils.getContractsUrl() );
		assertEquals( xmlResponse.getStorageReferences().getStorageReference().size(), 1 );
		logger.debug( "...New ID: " + xmlResponse.getStorageReferences().getStorageReference().get(0).getId() );
	}

	@Test
	public void testExtendAndUpdateContract() throws Exception {
		logger.debug( "Testing extending a contract" );
		StoreDecisionResponse xmlResponse = webUtils.storeContracts( "single-contract.xml" );
		String id = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		logger.debug( "...ID: " + id );
		ContractsOXM contracts = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );
		contracts.getContract().get(0).setExtendsContract( id );
		contracts.getContract().get(0).getUntil().add( Calendar.YEAR, 1 );
		xmlResponse = webUtils.storeDecisions( xmlUtils.marshal( contracts ), webUtils.getContractsUrl() );
		assertEquals( xmlResponse.getStorageReferences().getStorageReference().size(), 1 );
		logger.debug( "...New ID: " + xmlResponse.getStorageReferences().getStorageReference().get(0).getId() );
		
		WebResponse response = null;
		logger.debug("Testing basic authentication, valid credentials");
		
		WebConversation wc = webUtils.authorizedWebConversation();	
		WebRequest request = new GetMethodWebRequest( webUtils.getContractsUrl() );
		webUtils.setupRequest( request );
		response = wc.getResponse( request );
		logger.debug( "getting contract result: " + IOUtils.toString( response.getInputStream() ) );
		assertEquals( response.getResponseCode(), HttpStatus.SC_OK );		
	}	
	
	@Test
	public void testUpdateContract() throws Exception {
		logger.debug("Testing updating a contract" );				
		StoreDecisionResponse xmlResponse = webUtils.storeContracts( "single-contract.xml" );
		String uid = xmlResponse.getStorageReferences().getStorageReference().get( 0 ).getId();
		webUtils.updateContract( "root-contract.xml", uid );
		// Test that document is ok
		SingleContractOXM oxm = xmlUtils.unmarshal( "root-contract.xml", SingleContractOXM.class );		
		WebResponse response = webUtils.getResponse( webUtils.getContractsUrl() + "documents/original/" + uid );
		byte[] b = IOUtils.toByteArray( response.getInputStream() );
		assertTrue( Arrays.equals( oxm.getDocument(), b ) );
	}
	
	@Test
	public void testDeleteContract() throws Exception {
		logger.debug( "Testing deleting a contract" );
		StoreDecisionResponse xmlResponse = webUtils.storeContracts( "single-contract.xml" );
		String uid = xmlResponse.getStorageReferences().getStorageReference().get( 0 ).getId();
		logger.debug( "...Deleting id: " + uid );
		webUtils.deleteContract( uid, "test reason", "2" );
	}

	@Test
	public void testPostMultipleContracts() throws Exception {
		logger.debug("Testing sending multiple contracts");
		StoreDecisionResponse xmlResponse = webUtils.storeContracts("contracts.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
	}

	@Test
	public void testMultipost() throws Exception {
		logger.debug("Testing multiple posts");
		List<String> uCodes = utils.getUniqueDocumentCodesFromContract("contracts.xml");
		assertTrue(uCodes.size() > 0);
		int repeats = 20;
		for (int i = 0; i < repeats; i++) {
			StoreDecisionResponse xmlResponse = webUtils.storeContracts("contracts.xml");
			assertEquals(xmlResponse.getStorageReferences()
					.getStorageReference().size(), uCodes.size());
		}	
	}

	static class TestingThreadGroup extends ThreadGroup {
		private static List<Throwable> list;

		public TestingThreadGroup(final List<Throwable> lst) {
			super("TestingThreadGroup");
			list = lst;
		}

		@Override
		public void uncaughtException(Thread thread, Throwable e) {
			list.add(e);
		}
	}

	@Ignore
	@Test
	public void testMultiThread() throws Exception {
		List<Throwable> exceptions = new ArrayList<Throwable>();				
		int REPEATS = 50;		
		Thread[] threads = new Thread[ REPEATS ];
		logger.debug( "Spawning threads" );
		for (int i = 0; i < REPEATS; i++) {
			Thread t = new Thread(new TestingThreadGroup(exceptions), new Runnable() {
				@Override
				public void run() {
					try {					
						int INNER_REPEATS = 1;
						for ( int j = 0; j < INNER_REPEATS; j++ ) {
							doTestGetSpecificContract( "single-contract.xml", false );
							//doTestGetSpecificContract( "single-contract-450k.xml", false );
						}
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}

			});
			threads[ i ] = t;
			t.start();			
		}
		/* Wait for death */
		for ( int i = 0; i < REPEATS; i++ ) {
			if ( threads[ i ].isAlive() ) {
				logger.debug( "Threads are still alive, waiting..." );
				i = -1;				
				Thread.sleep( 1000 );
			}
		}
		logger.debug( "...Failed threads: " + exceptions.size() );
		for ( Throwable t: exceptions ) {
			//t.printStackTrace();
		}
		assertEquals( exceptions.size(), 0 );
	}

	@Test
	public void testPostTooMany() throws Exception {
		logger.debug("Testing posting too many contracts in one request");
		try {
			webUtils.storeContracts("contracts6.xml");
			fail("Succeeded in storing too many contracts");
		} catch (WebException e) {
			assertEquals( e.getResponseCode(), HttpStatus.SC_FORBIDDEN);
		}
	}

	private void doTestGetSpecificContract( String xmlFile, boolean log ) throws Exception {
		StoreDecisionResponse xmlResponse = webUtils.storeContracts( xmlFile );
		assertEquals(xmlResponse.getStorageReferences().getStorageReference()
				.size(), 1);
		String id = xmlResponse.getStorageReferences().getStorageReference()
				.get(0).getId();
		if ( log ) logger.debug("...Id: " + id);
		ArrayOfContracts contracts = webUtils.getContracts( webUtils.getContractsUrl() + id ).getContracts();
		assertEquals( contracts.getContract().get( 0 ).getId(), id );
		if ( log ) logger.debug("...Retrieving document");
		WebResponse response = webUtils.getResponse( webUtils.getContractsUrl() + "documents/original/" + id );
		assertEquals(response.getContentType(), "application/pdf");
		byte[] b = IOUtils.toByteArray( response.getInputStream() );
		long contentLength = response.getContentLength();
		if ( log ) {
			logger.debug( "...Content length: " + contentLength );			
			logger.debug( "...Actual length: " + b.length );			
		}
		assertEquals( contentLength, b.length );
	}
	
	@Test
	public void testGetSpecificContract( ) throws Exception {
		logger.debug( "Testing retrieving contract: 25k" );
		doTestGetSpecificContract( "single-contract.xml", true );
	}
	
	@Test
	public void testGetSpecificContractMedium( ) throws Exception {
		logger.debug( "Testing retrieving contract: 450k" );
		doTestGetSpecificContract( "single-contract-450k.xml", true );
	}
		
	@Test
	public void testGetSpecificContractLarge( ) throws Exception {
		logger.debug( "Testing retrieving contract: 1.5M" );
		doTestGetSpecificContract( "single-contract-1500k.xml", true );
	}
	
	@Ignore
	@Test
	public void testGetSpecificContractVeryLarge( ) throws Exception {
		logger.debug( "Testing retrieving contract: 3.1M" );
		doTestGetSpecificContract( "single-contract-3100k.xml", true );
	}	

	@Ignore
	@Test
	public void testGetSpecificContractHuge( ) throws Exception {
		logger.debug( "Testing retrieving contract: 16M" );
		doTestGetSpecificContract( "single-contract-16m.xml", true );
	}	

	@Ignore	
	@Test
	public void testGetSpecificContractGargantuan( ) throws Exception {
		logger.debug( "Testing retrieving contract: 46M" );
		doTestGetSpecificContract( "single-contract-46m.xml", true );
	}
	
	@Ignore
	@Test
	public void testBenchmarkHuge( ) throws Exception {
		logger.debug( "Testing benchmark for retrieving contract: 16M" );
		int REPEATS = 100;
		long average = 0;
		for ( int i = 1; i <= REPEATS; i++ ) {
			long start = System.currentTimeMillis();
			doTestGetSpecificContract( "single-contract-16m.xml", false );
			long end = System.currentTimeMillis();
			long duration = ( end - start );
			average += duration;
			logger.debug( "Test: " + i + ", Time: " + duration + " msec" );
		}
		average /= REPEATS;
		logger.debug( "\nAverage: " + average + " msec " );		
	}		
}
