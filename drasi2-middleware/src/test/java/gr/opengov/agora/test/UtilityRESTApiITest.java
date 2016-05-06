package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gr.opengov.agora.model.ArrayOfContracts;
import gr.opengov.agora.model.ArrayOfContractsShort;
import gr.opengov.agora.model.AuthenticationProfileOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.GetContractsShortResponse;
import gr.opengov.agora.model.SingleContractOXM;
import gr.opengov.agora.model.StoreDecisionResponse;
import gr.opengov.agora.test.util.Utility;
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
import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.HttpInternalErrorException;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
public class UtilityRESTApiITest {
	private static final Logger logger = LoggerFactory
			.getLogger(UtilityRESTApiITest.class);
	@Resource( name="webUtils" )
	private WebUtils webUtils;
	private XMLUtils xmlUtils;
	
	@Before
	public void init() {
		webUtils.setLogger( logger );
		xmlUtils = XMLUtils.newInstance();
	}
	
	@Test
	public void testIsLive() throws Exception {
		WebResponse response = null;

		logger.debug( "Testing isLive" );
		
		WebConversation wc = new WebConversation();
		WebRequest request = new GetMethodWebRequest( webUtils.getBaseUrl() + "islive/" );
		webUtils.setupRequest( request );
		
		response = wc.getResponse( request );
		assertTrue( response.getResponseCode() == HttpStatus.SC_OK );
	}

	@Test
	public void testGetAfmAnonymous() throws Exception {
		logger.debug( "Testing sending and receiving a contract" );		
		StoreDecisionResponse xmlResponse = webUtils.storeContracts( "single-contract.xml" );
		ContractsOXM contractOriginal = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );		
		assertTrue( xmlResponse.getStorageReferences() != null );
		assertEquals( xmlResponse.getStorageReferences().getStorageReference().size(), 1 );
		String savedId = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		ArrayOfContracts ret = webUtils.getContracts( webUtils.getContractsUrl() + savedId ).getContracts();
		ContractsOXM contractRetrieved = new ContractsOXM();
		contractRetrieved.getContract().addAll(  ret.getContract() );
		
		String afm = contractRetrieved.getContract().get(0).getSecondaryParties().getParty().get(0).getAfm();
		
		assertTrue( webUtils.getNameFromAfm(afm, true) == null );
	}	
	
	@Test
	public void testGetAfmAuthorized() throws Exception {
		logger.debug( "Testing sending and receiving a contract" );		
		StoreDecisionResponse xmlResponse = webUtils.storeContracts( "single-contract.xml" );
		ContractsOXM contractOriginal = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );		
		assertTrue( xmlResponse.getStorageReferences() != null );
		assertEquals( xmlResponse.getStorageReferences().getStorageReference().size(), 1 );
		String savedId = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		ArrayOfContracts ret = webUtils.getContracts( webUtils.getContractsUrl() + savedId ).getContracts();
		ContractsOXM contractRetrieved = new ContractsOXM();
		contractRetrieved.getContract().addAll(  ret.getContract() );
		
		String afm = contractRetrieved.getContract().get(0).getSecondaryParties().getParty().get(0).getAfm();
		String name = contractRetrieved.getContract().get(0).getSecondaryParties().getParty().get(0).getName();
		
		assertFalse( webUtils.getNameFromAfm(afm, false).equals(name) );
	}		
	
}
