package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gr.opengov.agora.model.ArrayOfProcurementRequests;
import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.AuthenticationProfileOXM;
import gr.opengov.agora.model.GetContractsResponse;
import gr.opengov.agora.model.GetProcurementRequestsResponse;
import gr.opengov.agora.model.ProcurementRequestOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.model.StoreDecisionResponse;
import gr.opengov.agora.model.TaxonomyReferenceOXM;
import gr.opengov.agora.model.ValidationErrorOXM;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.Utility;
import gr.opengov.agora.test.util.WebException;
import gr.opengov.agora.test.util.WebUtils;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.HttpInternalErrorException;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
public class ProcurementRequestRESTApiITest {
	private static final Logger logger = LoggerFactory
			.getLogger(ProcurementRequestRESTApiITest.class);
	@Resource( name="webUtils" )
	private WebUtils webUtils;
	private XMLUtils xmlUtils;
	private Utility utils;
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	
	@Before
	public void init() {
		webUtils.setLogger(logger);
		xmlUtils = XMLUtils.newInstance();
		utils = new Utility(xmlUtils);
	}
	
	private void printValidationErrors( ArrayOfValidationErrors errors ) {
		logger.debug( "\nValidation Errors:\n" );
		if (errors != null)
			for ( ValidationErrorOXM oxm: errors.getError() ) { 
				logger.debug( oxm.getField() + " - " + oxm.getXpath()  + " - " + oxm.getMsg() );
			}
	}	
	
@Ignore	
	@Test
	public void testGetProcurementRequestsNoAuthentication() throws Exception {	
		WebResponse response = null;
		try {
			logger.debug("Testing connection without authentication");
			WebConversation wc = new WebConversation();
			WebRequest request = new GetMethodWebRequest( webUtils.getProcurementRequestsUrl() );
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

@Ignore
	@Test
	public void testGetProcurementRequestsAuthSuccess() throws Exception {
		WebResponse response = null;
		try {
			logger.debug("Testing basic authentication, valid credentials");
			WebConversation wc = webUtils.authorizedWebConversation();
			WebRequest request = new GetMethodWebRequest( webUtils.getProcurementRequestsUrl() );
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

@Ignore
	@Test
	public void testGetProcurementRequestsAuthFailed() throws Exception {
		try {
			logger.debug("Testing basic authentication, invalid credentials");
			WebConversation wc = webUtils.authorizedWebConversation( "", "366_admin", "3661" );		
			WebRequest request = new GetMethodWebRequest( webUtils.getProcurementRequestsUrl() );
			webUtils.setupRequest( request );
			wc.getResponse( request );			
			fail();
		}
		catch ( AuthorizationRequiredException e ) { }
	}

@Ignore
	@Test
	public void testPostProcurementRequest() throws Exception {
		logger.debug("Testing sending a procurement request");
		List<String> uCodes = utils.getUniqueDocumentCodesFromProcurementRequest("single-request.xml");
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests("single-request.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String uid = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
	}
	
	@Test
	public void testPostProcurementRequestAndSearchCpv() throws Exception {
		logger.debug( "Testing receiving Procurement Request" );		
		logger.debug( "...Storing some Procurement Requests" );
		
		WebResponse response = webUtils.getResponse( webUtils.getProcurementRequestsUrl() + "?cpv=AA07-9");
		GetProcurementRequestsResponse procurementRequests = xmlUtils.unmarshal( response.getInputStream(), GetProcurementRequestsResponse.class );
		int requestsSearchNum = 0;
		requestsSearchNum = procurementRequests.getProcurementRequests().getRequest().size(); 
		
		for ( int i = 0; i < 3; i++ ) { 
			ProcurementRequestsOXM procurementRequestOriginal = xmlUtils.unmarshal( "single-request.xml", ProcurementRequestsOXM.class );
			procurementRequestOriginal.getRequest().get(0).getContractItems().getItem().get(0).getCpvCodes().getCpv().add("AA07-9");
			StoreDecisionResponse xmlResponsed = webUtils.storeProcurementRequests(xmlUtils.marshal(procurementRequestOriginal));
			assertNotNull(xmlResponsed);
			assertNull(xmlResponsed.getValidationErrors());
			assertEquals(xmlResponsed.getStorageReferences().getStorageReference().size(), 1);
		}
		
		WebResponse responseAfter = webUtils.getResponse( webUtils.getProcurementRequestsUrl() + "?cpv=AA07-9");
		GetProcurementRequestsResponse procurementRequestsAfter = xmlUtils.unmarshal( responseAfter.getInputStream(), GetProcurementRequestsResponse.class );
		int requestsSearchNumAfter = 0;
		requestsSearchNumAfter = procurementRequestsAfter.getProcurementRequests().getRequest().size(); 		
		
		assertTrue(requestsSearchNum+3 == requestsSearchNumAfter);
		
	}	
	
	//posts two procurement requests, second approves first.
@Ignore
	@Test
	public void testPostAndapproveProcurementRequest() throws Exception {
		logger.debug("Testing sending a procurement request");
		List<String> uCodes = utils.getUniqueDocumentCodesFromProcurementRequest("single-request.xml");
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests("single-request.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String uid = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
		
		ProcurementRequestsOXM procurementRequestOriginal = xmlUtils.unmarshal( "single-request.xml", ProcurementRequestsOXM.class );
		procurementRequestOriginal.getRequest().get(0).setApprovesRequest(uid);
		TaxonomyReferenceOXM ref = new TaxonomyReferenceOXM();
		ref.setIdRef("2");
		procurementRequestOriginal.getRequest().get(0).setAwardProcedure(ref);
		StoreDecisionResponse xmlResponseB = webUtils.storeProcurementRequests(xmlUtils.marshal(procurementRequestOriginal));
		assertNotNull(xmlResponseB);
		assertNull(xmlResponseB.getValidationErrors());
		assertEquals(xmlResponseB.getStorageReferences().getStorageReference().size(), 1);
		String uidB = xmlResponseB.getStorageReferences().getStorageReference().get(0).getId();
		
		WebResponse response = webUtils.getResponse( webUtils.getProcurementRequestsUrl()+"approval/"+uid );
		logger.debug("approval id:"+IOUtils.toString( response.getInputStream() ));
	}	
	
@Ignore
	@Test
	public void insertRequestsAndGetLists() throws Exception{
		String xml;
		dbHandler.clearDb();
		
		logger.debug("Testing sending a procurement request");
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests("single-request.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String notApprovedAId = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		
		logger.debug("Testing sending a procurement request");
		StoreDecisionResponse xmlResponseb = webUtils.storeProcurementRequests("single-request.xml");
		assertNotNull(xmlResponseb);
		assertNull(xmlResponseb.getValidationErrors());
		assertEquals(xmlResponseb.getStorageReferences().getStorageReference().size(), 1);		
		String notApprovedBId = xmlResponseb.getStorageReferences().getStorageReference().get(0).getId();
		
		logger.debug("Testing sending a procurement request");
		StoreDecisionResponse xmlResponsec = webUtils.storeProcurementRequests("single-request.xml");
		assertNotNull(xmlResponsec);
		assertNull(xmlResponsec.getValidationErrors());
		assertEquals(xmlResponsec.getStorageReferences().getStorageReference().size(), 1);
		String approvedId = xmlResponsec.getStorageReferences().getStorageReference().get(0).getId();
		
		ProcurementRequestsOXM procurementRequestOriginal = xmlUtils.unmarshal( "single-request.xml", ProcurementRequestsOXM.class );
		procurementRequestOriginal.getRequest().get(0).setApprovesRequest(approvedId);
		TaxonomyReferenceOXM ref = new TaxonomyReferenceOXM();
		ref.setIdRef("2");
		procurementRequestOriginal.getRequest().get(0).setAwardProcedure(ref);
		StoreDecisionResponse xmlResponsed = webUtils.storeProcurementRequests(xmlUtils.marshal(procurementRequestOriginal));
		assertNotNull(xmlResponsed);
		assertNull(xmlResponsed.getValidationErrors());
		assertEquals(xmlResponsed.getStorageReferences().getStorageReference().size(), 1);
		String approvalId = xmlResponsed.getStorageReferences().getStorageReference().get(0).getId();		
		
		logger.debug("getting requests: approval");
		WebResponse response = webUtils.getResponse( webUtils.getProcurementRequestsUrl()+"?approvedfilter="+Constants.APPROVAL_REQUESTS_FILTER_KEY );
		GetProcurementRequestsResponse requestsResponse = xmlUtils.unmarshal( response.getInputStream(), GetProcurementRequestsResponse.class );
		assertTrue(requestsResponse.getProcurementRequests().getRequest().size() == 1);
		
		logger.debug("getting requests: approved");	
		WebResponse responseb = webUtils.getResponse( webUtils.getProcurementRequestsUrl()+"?approvedfilter="+Constants.APPROVED_REQUESTS_FILTER_KEY );		
		GetProcurementRequestsResponse requestsResponseb = xmlUtils.unmarshal( responseb.getInputStream(), GetProcurementRequestsResponse.class );
		assertTrue(requestsResponseb.getProcurementRequests().getRequest().size() == 1);		
		
		logger.debug("getting requests: not approved");
		WebResponse responsec = webUtils.getResponse( webUtils.getProcurementRequestsUrl()+"?approvedfilter="+Constants.NOTAPPROVED_REQUESTS_FILTER_KEY );		
		GetProcurementRequestsResponse requestsResponsec = xmlUtils.unmarshal( responsec.getInputStream(), GetProcurementRequestsResponse.class );
		assertTrue(requestsResponsec.getProcurementRequests().getRequest().size() == 2);						
	}

	@Test
	public void testApproveAndUpdateProcurementRequest() throws Exception {
		dbHandler.clearDb();
		logger.debug("Testing storing a Procurement Request" );				
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests( "single-request.xml" );
		String uid = xmlResponse.getStorageReferences().getStorageReference().get( 0 ).getId();
		
		ProcurementRequestsOXM oxm = xmlUtils.unmarshal( "single-request-b.xml", ProcurementRequestsOXM.class );
		oxm.getRequest().get(0).setApprovesRequest(uid);
		oxm.getRequest().get(0).setTitle("testTitle");
		TaxonomyReferenceOXM refa = new TaxonomyReferenceOXM();
		refa.setIdRef("2");
		oxm.getRequest().get(0).setAwardProcedure(refa);
//		oxm.getRequest().get(0).getContractItems().getItem().get(0).setCpvCodes(null);
//		oxm.getRequest().get(0).getContractItems().getItem().get(0).getCpvCodes().getCpv().add("98393000-4");
		
		
		StoreDecisionResponse xmlResponseb = webUtils.storeProcurementRequests( xmlUtils.marshal(oxm) );
		
		ProcurementRequestOXM oxmb = xmlUtils.unmarshal( "root-request.xml", ProcurementRequestOXM.class );
		oxmb.setTitle("newTestTitle");
		oxmb.setApprovesRequest(uid);
		TaxonomyReferenceOXM ref = new TaxonomyReferenceOXM();
		ref.setIdRef("3");
		oxmb.setAwardProcedure(ref);
		
		String uidb = xmlResponseb.getStorageReferences().getStorageReference().get( 0 ).getId();
		webUtils.updateProcurementRequest( xmlUtils.marshal(oxmb), uidb );
		
		WebResponse response = webUtils.getResponse( webUtils.getProcurementRequestsUrl() );
		GetProcurementRequestsResponse procurementRequests = xmlUtils.unmarshal( response.getInputStream(), GetProcurementRequestsResponse.class );
		assertTrue( procurementRequests.getProcurementRequests().getRequest().size() > 0 );		
		
		String approvalId = null;
		String approvedId = null;
		
		for (ProcurementRequestOXM request:procurementRequests.getProcurementRequests().getRequest()){
			if (request.getId().equals(uidb)) {
				approvalId = request.getId();
				approvedId = request.getApprovesRequest();
			}
		}
		
		assertTrue(approvalId != null);
		assertTrue(uidb.equals(approvalId));
		assertTrue(approvedId != null);
		assertTrue(uid.equals(approvedId));	
	}	

@Ignore
	@Test
	public void testSameProcurementRequest() throws Exception {
		logger.debug( "Testing sending and receiving a procurement request" );		
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests( "single-request.xml" );
		ProcurementRequestsOXM procurementRequestOriginal = xmlUtils.unmarshal( "single-request.xml", ProcurementRequestsOXM.class );		
		String savedId = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		ArrayOfProcurementRequests ret = webUtils.getProcurementRequests( webUtils.getProcurementRequestsUrl() + savedId ).getProcurementRequests();
		ProcurementRequestsOXM procurementRequestRetrieved = new ProcurementRequestsOXM();
		procurementRequestRetrieved.getRequest().addAll(  ret.getRequest() );
						
		assertEquals( procurementRequestOriginal.getRequest().size(), procurementRequestRetrieved.getRequest().size() );
		procurementRequestOriginal.getRequest().get(0).setId( savedId );
		procurementRequestOriginal.getRequest().get(0).setDocument( null );
		procurementRequestOriginal.getRequest().get(0).setDocumentUrl( null );
		ret.getRequest().get(0).setCmsMetadata( null );
		ret.getRequest().get(0).setDocument( null );
		ret.getRequest().get(0).setDocumentUrl( null );		
				
		String xmlOriginal = IOUtil.toString( xmlUtils.marshal(procurementRequestOriginal) );
		String xmlRetrieved = IOUtil.toString( xmlUtils.marshal(procurementRequestRetrieved ) );
		utils.saveFile( xmlOriginal, "original.xml" );
		utils.saveFile( xmlRetrieved, "retrieved.xml" );
		XMLAssert.assertXMLEqual( xmlOriginal, xmlRetrieved );
	}
	
@Ignore
	@Test
	public void testGetProcurementRequest() throws Exception {
		logger.debug( "Testing receiving Procurement Request" );		
		logger.debug( "...Storing some Procurement Requests" );
		for ( int i = 0; i < 3; i++ ) { 
			webUtils.storeProcurementRequests( "single-request.xml" );
		}
		WebResponse response = webUtils.getResponse( webUtils.getProcurementRequestsUrl() );
		GetProcurementRequestsResponse procurementRequests = xmlUtils.unmarshal( response.getInputStream(), GetProcurementRequestsResponse.class );
		assertTrue( procurementRequests.getProcurementRequests().getRequest().size() > 0 );
	}
	
//@Ignore
	@Test
	public void testUpdateProcurementRequest() throws Exception {
		logger.debug("Testing updating a Procurement Request" );				
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests( "single-request.xml" );
		String uid = xmlResponse.getStorageReferences().getStorageReference().get( 0 ).getId();
		webUtils.updateProcurementRequest( "root-request.xml", uid );
		// Test that document is ok
		ProcurementRequestOXM oxm = xmlUtils.unmarshal( "root-request.xml", ProcurementRequestOXM.class );		
		WebResponse response = webUtils.getResponse( webUtils.getProcurementRequestsUrl() + "documents/original/" + uid );
		byte[] b = IOUtils.toByteArray( response.getInputStream() );
		assertTrue( Arrays.equals( oxm.getDocument(), b ) );
	}

	
@Ignore
	@Test
	public void testPostMultipleProcurementRequests() throws Exception {
		logger.debug("Testing sending multiple Procurement Requests");
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests("procurement-requests.xml");
		assertNotNull(xmlResponse);
		printValidationErrors( xmlResponse.getValidationErrors() );
		assertNull(xmlResponse.getValidationErrors());
	}
	
@Ignore
	@Test
	public void testMultipostProcurementRequests() throws Exception {
		logger.debug("Testing multiple procurement requests posts");
		List<String> uCodes = utils.getUniqueDocumentCodesFromProcurementRequest("procurement-requests.xml");
		assertTrue(uCodes.size() > 0);
		int repeats = 20;
		for (int i = 0; i < repeats; i++) {
			StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests("procurement-requests.xml");
			assertEquals(xmlResponse.getStorageReferences()
					.getStorageReference().size(), uCodes.size());
		}	
	}

	static class TestingThreadGroupProcurementRequests extends ThreadGroup {
		private static List<Throwable> list;

		public TestingThreadGroupProcurementRequests(final List<Throwable> lst) {
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
	public void testMultiThreadProcurementRequests() throws Exception {
		List<Throwable> exceptions = new ArrayList<Throwable>();				
		int REPEATS = 50;		
		Thread[] threads = new Thread[ REPEATS ];
		logger.debug( "Spawning threads (procurement requests)" );
		for (int i = 0; i < REPEATS; i++) {
			Thread t = new Thread(new TestingThreadGroupProcurementRequests(exceptions), new Runnable() {
				@Override
				public void run() {
					try {					
						int INNER_REPEATS = 1;
						for ( int j = 0; j < INNER_REPEATS; j++ ) {
							doTestGetSpecificProcurementRequest( "single-request.xml", false );
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

@Ignore	
	@Test
	public void testPostTooManyProcurementRequests() throws Exception {
		logger.debug("Testing posting too many Procurement Requests in one request");
		try {
			webUtils.storeProcurementRequests("procurement-requests6.xml");
			fail("Succeeded in storing too many procurement requests");
		} catch (WebException e) {
			assertEquals( e.getResponseCode(), HttpStatus.SC_FORBIDDEN);
		}
	}

	private void doTestGetSpecificProcurementRequest( String xmlFile, boolean log ) throws Exception {
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests( xmlFile );
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String id = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		if ( log ) logger.debug("...Id: " + id);
		ArrayOfProcurementRequests procurementRequests = webUtils.getProcurementRequests( webUtils.getProcurementRequestsUrl() + id ).getProcurementRequests();
		assertEquals( procurementRequests.getRequest().get( 0 ).getId(), id );
		if ( log ) logger.debug("...Retrieving document");
		WebResponse response = webUtils.getResponse( webUtils.getProcurementRequestsUrl() + "documents/original/" + id );
		assertEquals(response.getContentType(), "application/pdf");
		byte[] b = IOUtils.toByteArray( response.getInputStream() );
		long contentLength = response.getContentLength();
		if ( log ) {
			logger.debug( "...Content length: " + contentLength );			
			logger.debug( "...Actual length: " + b.length );			
		}
		assertEquals( contentLength, b.length );
	}
	
@Ignore	
	@Test
	public void testGetSpecificProcurementRequest( ) throws Exception {
		logger.debug( "Testing retrieving request: 25k" );
		doTestGetSpecificProcurementRequest( "single-request.xml", true );
	}
		
	/*@Ignore
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
	}		*/
}
