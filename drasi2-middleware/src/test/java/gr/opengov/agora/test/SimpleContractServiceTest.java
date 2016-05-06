package gr.opengov.agora.test;

import static org.junit.Assert.*;

import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.model.ContractShortOXM;
import gr.opengov.agora.model.ArrayOfContracts;
import gr.opengov.agora.model.ArrayOfContractsShort;
import gr.opengov.agora.model.GetDecisionIdsResponse;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IDecisionGenericConverter;
import gr.opengov.agora.converters.IProcurementRequestOXMConverter;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.ICmsMetadata;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.SearchParameterFactory;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.security.IClientFactory;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.test.stub.AdminTestClientFactory;
import gr.opengov.agora.test.util.AgoraTestResource;
import gr.opengov.agora.test.util.AgoraTestResources;
import gr.opengov.agora.test.util.DecisionGenericServiceClient;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.util.PaginationInfo;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.Validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class SimpleContractServiceTest extends DecisionGenericServiceClient< IContract, ContractOXM, ArrayOfContracts, ArrayOfContractsShort> {
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceTest.class);
	@Resource( name="contractService" )
	private IDecisionGenericService<IContract> service;
	@Resource( name="procurementRequestService" )
	private IDecisionGenericService<IProcurementRequest> requestService;	
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	private static XMLUtils xmlUtils;
	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter converter;
	@Resource( name="procurementRequestOxmConverter" )
	private IProcurementRequestOXMConverter procurementRequestOxmConverter;	
	@Resource( name="accessControl" )
	private IAccessControl accessControl;
	@Resource( name = "hibernateSessionFactory")
	private SessionFactory sessionFactory;
	
	private List<AgoraTestResource> tests = null;
	private AgoraTestResource contractOkToSaveA = null;
	private AgoraTestResources testResources = null;
	
	public SimpleContractServiceTest() {
		super( IContract.class, ContractOXM.class, ArrayOfContracts.class );
//		testResources = new AgoraTestResources();
//		setAccessControl(accessControl);
//		logger.debug("accesscontrol:"+accessControl.toString());
//		setDatabaseHandler(dbHandler);
//		List<AgoraTestResource> tests = session().createQuery("from test where decision =: decision").setParameter( "decision", "Contract").list();
//		contractOkToSaveA = (AgoraTestResource)session().createQuery("from test where decision =: decision and getServiceSaveSingleIgnoreAccessA =: save").setParameter( "decision", "Contract").setParameter( "save", true).uniqueResult();
//		contractOkToSaveA = testResources.getTestResources().get(0);
	}	
	
	@BeforeClass
	public static void init() {
		setXmlUtils(XMLUtils.newInstance());
	}
	
	
	@Before	
	public void initialize(){
		testResources = new AgoraTestResources();
		setAccessControl(accessControl);
		logger.debug("accesscontrol:"+accessControl.toString());
		setDatabaseHandler(dbHandler);
		contractOkToSaveA = testResources.getTestResources().get(0);
	}

//	@Ignore
	@Test
	public void testExtensionDatePass() throws Exception {
		String failMessage = "";
		boolean success = false;
		List<IContract> savedContracts = new ArrayList<IContract>();
		
		success = saveSingleIgnoreAccess(contractOkToSaveA.getXml(), savedContracts, true, failMessage);		
		assertTrue(failMessage, success);
		
		if ((savedContracts != null) && (savedContracts.size() > 0))
		{
			IContract contract = load(contractOkToSaveA.getXml());
			contract.getUntil().add( Calendar.YEAR, 1 );
			contract.setExtendsContract( savedContracts.get(0) );
			success = saveSingleIgnoreAccess(contract, savedContracts, false, failMessage);
			assertTrue(failMessage, success);
		}
		else
			fail();		
	}
//	@Ignore
	@Test
	public void testExtensionDateFail() throws Exception {
		String failMessage = "";
		boolean success = false;
		List<IContract> savedContracts = new ArrayList<IContract>();
		
		success = saveSingleIgnoreAccess(contractOkToSaveA.getXml(), savedContracts, true, failMessage);
		assertTrue(failMessage, success);
		
		if ((savedContracts != null) && (savedContracts.size() > 0))
		{
			IContract contract = load(contractOkToSaveA.getXml());
			contract.getUntil().add( Calendar.YEAR, -1 );
			contract.setExtendsContract( savedContracts.get(0) );
			success = saveSingleIgnoreAccess(contract, savedContracts, false, failMessage);
			assertFalse(failMessage, success);
		}
		else
			fail();		
	}	
		
//	@Ignore
	@Test
	public void testContractIgnoreAccess() throws Exception {
		String failMessage = "";
		boolean success = false;
		
		for (AgoraTestResource test:testResources.getTestResources()){
			logger.debug("save " + test.getDecision() + " with id = " + test.getId());
			failMessage = "";
			
			if (test.getServiceSaveSingleIgnoreAccess() != null){
				success = saveSingleIgnoreAccess(test.getXml(), new ArrayList<IContract>(), true, failMessage);
				if (test.getServiceSaveSingleIgnoreAccess())
					assertTrue(failMessage, success);
				else
					assertFalse(failMessage, success);
			}
		}			
	}
	
//	@Ignore
	@Test
	public void testGetDocument() throws Exception {
		String failMessage = "";
		boolean success = saveAndGetDocument("sample-contract-1.xml", failMessage); 
		assertTrue(failMessage, true);
	}

	@Override
	protected IDecisionGenericService<IContract> getService() {
		return service;
	}

	@Override
	protected IDecisionGenericConverter<IContract, ContractOXM, ArrayOfContracts, ArrayOfContractsShort> getConverter() {
		return converter;
	}
}
