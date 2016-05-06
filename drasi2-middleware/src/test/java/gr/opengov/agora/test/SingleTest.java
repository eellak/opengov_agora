package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.model.AuthenticationProfileOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.ProcurementRequestOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.model.StoreDecisionResponse;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.Utility;
import gr.opengov.agora.test.util.WebException;
import gr.opengov.agora.test.util.WebUtils;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.util.DiavgeiaAdaGenerator;
import gr.opengov.agora.util.IAdaGenerator;
import gr.opengov.agora.validation.Validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.tika.io.IOUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;


import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
public class SingleTest {
	private static final Logger logger = LoggerFactory
			.getLogger(SingleTest.class);
	@Resource( name="webUtils" )
	private WebUtils webUtils;
	private XMLUtils xmlUtils;
	private Utility utils;	
	
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	
	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter converter;
	
	@Resource( name="contractService" )
	private IDecisionGenericService<IContract> service;	
	
	@Resource( name="diavgeiaAdaGenerator" )
	private IAdaGenerator diavgeiaAdaGenerator;
	
	@Before
	public void init() {
		webUtils.setLogger( logger );
		xmlUtils = XMLUtils.newInstance();
		utils = new Utility(xmlUtils);
	}
	
	private IContract loadContract( String xmlFile ) throws Exception {
		ContractsOXM contracts = xmlUtils.unmarshal( xmlFile, ContractsOXM.class );
		return converter.toObject( contracts.getContract().get( 0 ) );
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
	
	@Ignore
	@Test
	public void testGetAda()
	{
		String ada = webUtils.getAda();
		logger.debug("webutils ada=" + ada);
		assertTrue(ada.trim().length() > 0);
	}
	
	@Ignore
	@Test
	public void testDiavgeiaAdaGenerator() throws IOException, WebException, SAXException
	{
		logger.debug( "Testing authentication" );
		
		WebResponse response = webUtils.getResponse( webUtils.getBaseUrl() + "authenticate" );
		AuthenticationProfileOXM profile = xmlUtils.unmarshal( response.getInputStream(), AuthenticationProfileOXM.class );
		logger.debug("profile authorized: " + profile.toString());
		logger.debug("profile organizations: " + profile.getOdeMember());
		assertFalse( profile.getRole().equals("admin") );
		assertEquals( profile.getOdeMember().getOrganizations().size(), 1 );		
		
		String ada = diavgeiaAdaGenerator.getNewId(null);
	
		logger.debug("diavgeia generator ada=" + ada);
		assertTrue(ada.trim().length() > 0);
		assertFalse(ada.contains("Error"));
	}	
	
	@Ignore
	@Test
	public void testSaveSingleContractIgnoreAccess() throws Exception {
		logger.debug( "Testing saving a single contract, no access control" );
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-temp.xml" );
		Validation validation = new Validation();
		service.save( Arrays.asList( contract ), validation );
		List<IContract> saved = service.getAll();
		logger.debug( "validation errors:\n");
		logger.debug( validation.toString());
		assertTrue( saved.size() == 1 );
	}
	
	@Ignore
	@Test
	public void testPostSingleContract() throws Exception {
		logger.debug("Testing sending a single contract");
		List<String> uCodes = utils.getUniqueDocumentCodesFromContract("sample-contract-temp.xml");			
		StoreDecisionResponse xmlResponse = webUtils.storeContracts("sample-contract-temp.xml");
		assertNotNull(xmlResponse);
		assertNull(xmlResponse.getValidationErrors());
		assertEquals(xmlResponse.getStorageReferences().getStorageReference().size(), 1);
		String uid = xmlResponse.getStorageReferences().getStorageReference().get(0).getId();
		assertTrue(uCodes.contains(xmlResponse.getStorageReferences().getStorageReference().get(0).getUniqueDocumentCodeRef()));
	}	
	
	@Ignore
	@Test
	public void testUpdateProcurementRequest() throws Exception {
		logger.debug("Testing updating a Procurement Request" );	
		ProcurementRequestsOXM oxmOriginal = xmlUtils.unmarshal( "single-request.xml", ProcurementRequestsOXM.class );
		int sizeOriginal = oxmOriginal.getRequest().get(0).getDocument().length;
		StoreDecisionResponse xmlResponse = webUtils.storeProcurementRequests( "single-request.xml" );
		String uid = xmlResponse.getStorageReferences().getStorageReference().get( 0 ).getId();
		webUtils.updateProcurementRequest( "root-request.xml", uid );
		
		// Test that document is ok
		ProcurementRequestOXM oxm = xmlUtils.unmarshal( "root-request.xml", ProcurementRequestOXM.class );		
		WebResponse response = webUtils.getResponse( webUtils.getProcurementRequestsUrl() + "documents/original/" + uid );
		byte[] b = IOUtils.toByteArray( response.getInputStream() );
		logger.debug( "Original OXM size: " + sizeOriginal );
		logger.debug( "Response size: " + b.length );
		logger.debug( "OXM size: " + oxm.getDocument().length );
		assertTrue( Arrays.equals( oxm.getDocument(), b ) );
	}
	
	@Ignore
	@Test
	public void testSaveSingleContractNoCpvsIgnoreAccess() throws Exception {
		logger.debug( "Testing saving a single contract with no cpvs, no access control" );
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-1.xml" );
		for (IContractItem contractItem:contract.getContractItems()){
			contractItem.setCpvCodes(new LinkedHashSet<ICpv>());
		}
		service.save( Arrays.asList( contract ), new Validation() );
		List<IContract> saved = service.getAll();
		assertTrue( saved.size() == 0 );
	}			
}
