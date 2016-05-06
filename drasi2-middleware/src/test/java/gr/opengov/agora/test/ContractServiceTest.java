package gr.opengov.agora.test;

import static org.junit.Assert.*;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IProcurementRequestOXMConverter;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.ICmsMetadata;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.model.ValidationErrorOXM;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.SearchParameterFactory;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.security.IClientFactory;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.test.stub.AdminTestClientFactory;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.util.PaginationInfo;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.IValidationError;
import gr.opengov.agora.validation.Validation;
import gr.opengov.agora.web.ContractController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtil;
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
public class ContractServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceTest.class);
	@Resource( name="contractService" )
	private IDecisionGenericService<IContract> service;
	@Resource( name="procurementRequestService" )
	private IDecisionGenericService<IProcurementRequest> requestService;	
	@Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	private static XMLUtils xmlUtils;
	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter converter;
	@Resource( name="procurementRequestOxmConverter" )
	private IProcurementRequestOXMConverter procurementRequestOxmConverter;	
	@Resource( name="accessControl" )
	private IAccessControl accessControl;	
	
	@BeforeClass
	public static void init() {
		xmlUtils = XMLUtils.newInstance();		
	}
	
	private IContract loadContract( String xmlFile ) throws Exception {
		ContractsOXM contracts = xmlUtils.unmarshal( xmlFile, ContractsOXM.class );
		return converter.toObject( contracts.getContract().get( 0 ) );
	}
	
	@Test
	public void testSaveSingleContractIgnoreAccess() throws Exception {
		logger.debug( "Testing saving a single contract, no access control" );
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-1.xml" );
		service.save( Arrays.asList( contract ), new Validation() );
		List<IContract> saved = service.getAll();
		assertTrue( saved.size() == 1 );
	}
	
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
	
	@Test
	public void testgetDocument() throws Exception {
		logger.debug( "Testing retrieving a contract's document" );
		dbHandler.clearDb();
		IContract contract = loadContract( "single-contract.xml" );
		byte[] dataOriginal = contract.getDocument().getData().clone();
		logger.debug( "...Original data size: " + dataOriginal.length );
		service.save( Arrays.asList( contract ), new Validation() );
		List<IContract> saved = service.getAll();
		String id = saved.get(0).getId();		
		IDocument documentRetrieved = service.getDocument( id );
		assertTrue( Arrays.equals( dataOriginal, IOUtil.toByteArray( service.getDocumentOriginalData(documentRetrieved) ) ) );				
	}
	
	private IProcurementRequest loadRequest( String xmlFile ) throws Exception {
		ProcurementRequestsOXM requests = xmlUtils.unmarshal( xmlFile, ProcurementRequestsOXM.class );
		return procurementRequestOxmConverter.toObject( requests.getRequest().get( 0 ) );
	}	
	
	private void updateSingleContract( boolean emptyDocument ) throws Exception {
		IValidation validation = new Validation();
		
		logger.debug( "Testing saving a single procurement request, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request.xml" );
		requestService.save( Arrays.asList( request ), validation );
		logger.debug("request 1 saving errors:\n");
		printValidationErrors(validation);
		List<IProcurementRequest> saved = requestService.getAll();
		assertTrue( saved.size() == 1 );		
		IProcurementRequest tmpRequest = requestService.getAll().get(0);
		
		
		IProcurementRequest requestApproval = loadRequest( "single-request.xml" );
		requestApproval.setApprovesRequest(tmpRequest);
		validation = new Validation();
		List<IDecisionStorageReference> approvalsRef = requestService.save( Arrays.asList( requestApproval ), validation );
		logger.debug("request 2 saving errors:\n");
		printValidationErrors(validation);		
		saved = requestService.getAll();
		assertTrue( saved.size() == 2 );		
		IProcurementRequest finalRequest = requestService.get(approvalsRef.get(0).getId());		
		
		
		logger.debug( "Testing saving a single contract, no access control" );
		
		IContract contract = loadContract( "sample-contract-1.xml" );
		byte[] dataOriginal = contract.getDocument().getData().clone();		
		List<Ada> adas = new ArrayList<Ada>();
		List<String> kaes = new ArrayList<String>();		
		adas.add( new Ada( finalRequest.getId(), "ProcurementRequest" ) );
//		adas.add( new Ada( "4ΙΙ6Η-4", "ContractAward" ) );
		
		
		contract.setRelatedAdas( adas );		
		
		for ( int i = 0; i < contract.getContractItems().size(); i++ ) {
			contract.getContractItems().get(i).setKaeCodes( kaes );
		}	
		
		validation = new Validation();
		service.save( Arrays.asList( contract ), validation );
		
		printValidationErrors(validation);
		logger.debug( "Saved, validation: " + validation );	
		String savedId = service.getAll().get(0).getId();			
		IContract contractUpdate = loadContract( "sample-contract-1.xml" );
		if ( emptyDocument ) {
			contractUpdate.setDocument( null );
		}
		
		List<Ada> adasUpdate = new ArrayList<Ada>();
		List<String> kaesUpdate = new ArrayList<String>();
		
		adasUpdate.clear();
		adasUpdate.add( new Ada( finalRequest.getId(), "ProcurementRequest" ) );
//		adasUpdate( new Ada( "4ΙΙ64691ΩΓ-Α", "ContractCommission" ) );
		contractUpdate.setRelatedAdas( adasUpdate );
		
		kaesUpdate.clear();
		kaesUpdate.add( "KAE-UPDATED-1" );		
		
		contractUpdate.setId( savedId );
		contractUpdate.setUniqueDocumentCode( "newDocumentCode" );
		for ( int i = 0; i < contractUpdate.getContractItems().size(); i++ ) {
			logger.debug( "Updating cost for item: " + i );
			double cost = (i+1) * 50;
			contractUpdate.getContractItems().get(i).getCost().setCostBeforeVat( cost );
			contractUpdate.getContractItems().get(i).setKaeCodes( kaesUpdate );
		}		
		
		for ( int i = 0; i < contractUpdate.getSecondaryParties().size(); i++ ) {
			contractUpdate.getSecondaryParties().get(0).setName( "Updated Secondary Party " + i );
		}
		
		if (emptyDocument)
			contract.setDocument( null ); // it is set null above only in case if emptyDocument
		validation = new Validation();
		IDecisionStorageReference ref = service.update(contractUpdate, validation );
		logger.debug("updating contract\n");
		printValidationErrors(validation);
		if ( !validation.isValid() ) {
			logger.error( "Validation Error:\n" + validation.toString() );
			fail();
		}		
		contract = service.get( savedId );
		assertEquals( contract.getUniqueDocumentCode(), "newDocumentCode" );
		assertEquals( contract.getId(), savedId );
		IDocument document = service.getDocument( savedId );
		assertTrue( Arrays.equals( dataOriginal, IOUtil.toByteArray( service.getDocumentOriginalData(document) ) ) );
		assertTrue(ref.getEmails().size() == 4);
	}	
	
	@Test
	public void updateSingleContractPreviousDateFromRequest( ) throws Exception {
		logger.debug( "Testing saving a single procurement request, no access control" );
		dbHandler.clearDb();
		IProcurementRequest request = loadRequest( "single-request-2.xml" );
		requestService.save( Arrays.asList( request ), new Validation() );
		List<IProcurementRequest> saved = requestService.getAll();
		assertTrue( saved.size() == 1 );		
		IProcurementRequest tmpRequest = requestService.getAll().get(0);
		
		logger.debug( "Testing saving a single contract, no access control" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		byte[] dataOriginal = contract.getDocument().getData().clone();		
		List<Ada> adas = new ArrayList<Ada>();
		List<String> kaes = new ArrayList<String>();		
		adas.add( new Ada( tmpRequest.getId(), "ProcurementRequest" ) );
		adas.add( new Ada( "4ΙΙ6Η-4", "ContractAward" ) );
		
		
		contract.setRelatedAdas( adas );		
		
//		for ( int i = 0; i < contract.getContractItems().size(); i++ ) {
//			contract.getContractItems().get(i).setKaeCodes( kaes );
//		}	
		
		service.save( Arrays.asList( contract ), validation );
		assertFalse(validation.isValid());
		assertTrue(service.getAll().size() == 0);
	}	
	
	@Test
	public void testUpdateSingleContractEmptyCpvList() throws Exception {
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-1.xml" );
		byte[] dataOriginal = contract.getDocument().getData().clone();		
		service.save( Arrays.asList( contract ), new Validation() );

		contract = loadContract( "sample-contract-1.xml" );
		String savedId = service.getAll().get(0).getId();
		contract.setId( savedId );
		for (IContractItem contractItem:contract.getContractItems()){
			contractItem.setCpvCodes(new LinkedHashSet<ICpv>());
		}		
		Validation validation = new Validation();
		service.update(contract, validation );
		if ( !validation.isValid() ) {
			logger.error( "Validation Error:\n" + validation.toString() );
			
		}
		else
			fail();
	}	
	
	@Test
	public void testUpdateSingleContractWithoutDocument() throws Exception {
		logger.debug( "Testing updating a single contract without document" );
		updateSingleContract( true );
	}
	
	@Test
	public void testUpdateSingleContractWithDocument() throws Exception {
		logger.debug( "Testing updating a single contract without document" );
		updateSingleContract( false );
	}
		
	@Test	
	public void testPurgeSingleStoredContract() throws Exception {
		logger.debug( "Testing purging a single stored contract" );
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-1.xml" );
		
		service.save( Arrays.asList( contract ), new Validation() );		
		List<IContract> saved = service.getAll();		
		assertTrue( saved.size() == 1 );
		contract = saved.get(0);
		Validation validation = new Validation();
				
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		service.purge( contract.getId() );
		accessControl.setClientFactory( defaultFactory );
		saved = service.getAll();		
		assertTrue( saved.isEmpty() );
		assertTrue(validation.isValid());
	}
	
	@Ignore
	@Test
	public void testPurgeSingleStoredOldContract() throws Exception {
		logger.debug( "Testing purging a single stored contract" );
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-1.xml" );
		
//		Calendar submited = Calendar.getInstance();
//		submited.add(Calendar.DAY_OF_MONTH, -3);			
//		ICmsMetadata metadata = new CmsMetadata();
//		metadata.setSubmissionTime( submited );
//		contract.setCmsMetadata(metadata);		
		
		service.save( Arrays.asList( contract ), new Validation() );		
		List<IContract> saved = service.getAll();		
		assertTrue( saved.size() == 1 );
		contract = saved.get(0);
		Validation validation = new Validation();
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		service.purge( contract.getId() );		
		accessControl.setClientFactory(defaultFactory);
		saved = service.getAll();
		assertFalse( saved.isEmpty() );
		assertTrue( validation.hasErrorCode("endOfChangeTimeSlot"));
	}	
	
	@Test
	public void testDeleteSingleStoredContract() throws Exception {
		logger.debug( "Testing deleting a single stored contract" );
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-1.xml" );	
		
		service.save( Arrays.asList( contract ), new Validation() );		
		List<IContract> saved = service.getAll();		
		assertTrue( saved.size() == 1 );
		contract = saved.get(0);		
		service.delete( contract.getId(), "testReason", "1" );
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		saved = service.getAll();
		accessControl.setClientFactory( defaultFactory );
		assertEquals( saved.size(), 1 );
		contract = saved.get( 0 );		
		assertTrue( contract.isDeleted() );
		assertTrue( contract.getCmsMetadata().getDeletedReason().equals("testReason") );		
	}
	
	@Ignore
	@Test
	public void testDeleteSingleStoredOldContract() throws Exception {
		logger.debug( "Testing deleting a single stored contract" );
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-1.xml" );
		
//		ICmsMetadata metadata = new CmsMetadata();
//		Calendar submited = Calendar.getInstance();
//		submited.add(Calendar.DAY_OF_MONTH, -3);
//		metadata.setSubmissionTime( submited );
//		contract.setCmsMetadata(metadata);		
		
		service.save( Arrays.asList( contract ), new Validation() );		
		List<IContract> saved = service.getAll();		
		assertTrue( saved.size() == 1 );
		contract = saved.get(0);
		Validation validation = new Validation();
		service.delete( contract.getId(), "testReason", "2" );		
		saved = service.getAll();
		assertEquals( saved.size(), 1 );
		contract = saved.get( 0 );
		assertTrue( validation.hasErrorCode("endOfChangeTimeSlot") );
		assertFalse( contract.isDeleted() );
	}	
	
	@Test
	public void testSaveTwoContractsTwoCalls() throws Exception {
		logger.debug( "Testing saving two different contracts in two calls" );
		dbHandler.clearDb();
		List<IContract> saved = service.getAll();
		assertTrue( saved.isEmpty() );
		IContract contract1 = loadContract( "sample-contract-1.xml" );
		IContract contract2 = loadContract( "single-contract.xml" );
		logger.debug( "...Saving 1st contract" );
		Validation validation = new Validation();
		service.save( Arrays.asList( contract1 ), validation );
		if (!validation.isValid())
			printValidationErrors(validation);
		logger.debug( "...Retrieving list" );
		saved = service.getAll();
		assertTrue( saved.size() == 1 );
		logger.debug( "...Saving 2nd contract" );
		validation = new Validation();
		service.save( Arrays.asList( contract2 ), validation );
		if (!validation.isValid())
			printValidationErrors(validation);		
		saved = service.getAll();
		assertTrue( saved.size() == 2 );	
	}
	
	@Test
	public void testSaveTwoContractsSingleCall() throws Exception {
		logger.debug( "Testing saving two different contracts in a single call" );		
		dbHandler.clearDb();		
		List<IContract> saved = service.getAll();		
		assertTrue( saved.isEmpty() );				
		IContract contract1 = loadContract( "sample-contract-1.xml" );
		IContract contract2 = loadContract( "single-contract.xml" );
		Validation validation = new Validation();
		service.save( Arrays.asList( contract1, contract2 ), validation );
		if (!validation.isValid())
			printValidationErrors(validation);
		saved = service.getAll();
		assertTrue( saved.size() == 2 );
	}
	
	@Test
	public void testContractsDifferentOrganization() throws Exception {
		logger.debug( "Testing storing decisions of two different organizations" );
		dbHandler.clearDb();		
		IContract contract1 = loadContract( "single-contract.xml" );
		IContract contract2 = loadContract( "single-contract.xml" );
		contract2.setOrganizationDiavgeiaId( 15 );
		contract2.setUnitDiavgeiaId( 117 );
		contract2.setSignersDiavgeiaIds( Arrays.asList( 24 ) );		
		Validation validation = new Validation();
		try {
			List<IDecisionStorageReference> stored = service.save( Arrays.asList( contract1, contract2 ), validation );
			fail();
		}
		catch (ForbiddenOperationException e) {
			
		}
	}
	
	@Test
	public void testSearchContractsByOrganization() throws Exception {
		logger.debug( "Testing searching contract by organization" );
		dbHandler.clearDb();		
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		IContract contract1 = loadContract( "single-contract.xml" );
		IContract contract2 = loadContract( "single-contract.xml" );
		contract2.setOrganizationDiavgeiaId( 15 );
		contract2.setUnitDiavgeiaId( 117 );
		contract2.setSignersDiavgeiaIds( Arrays.asList( 24 ) );		
		service.save( Arrays.asList( contract1, contract2 ), new Validation() );						
		MockHttpServletRequest request = new MockHttpServletRequest();		
		request.addParameter( "org", "366" );
		List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( request );
		List<IContract> found = service.getAll( new PaginationInfo(), params );
		assertEquals( found.size(), 1 );
		assertEquals( found.get(0).getOrganizationDiavgeiaId().intValue(), 366 );
		accessControl.setClientFactory( defaultFactory );
	}
	
	@Test
	public void testSearchContractByCpv() throws Exception {
		logger.debug( "Testing saving a single contract, no access control" );
		dbHandler.clearDb();
		Validation validation = new Validation();
		logger.debug("storing 3 contracts\n");
		int firstContractContractItems=0;
		String firstContractAda="";
		for (int i=0;i<3;i++)
		{		
			IContract contract = loadContract( "single-contract.xml" );
			if (i<2){
				contract.getContractItems().get(0).addCpv("AA07-9");
			}

			if (i == 0){
				firstContractContractItems = contract.getContractItems().size();
				firstContractAda = contract.getId();
			}			
			
			validation = new Validation();
			service.save( Arrays.asList( contract ), validation );
			if (!validation.isValid()){
				logger.debug("saving contracts errors:\n");
				printValidationErrors(validation);
			}
		}
		List<IContract> saved = service.getAll();
		assertTrue( saved.size() == 3 );
		
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();		
		mockRequest.addParameter( "cpv", "AA07-9" );
		List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( mockRequest );
		List<IContract> found = service.getAll( new PaginationInfo(), params );
		assertEquals( found.size(), 2 );
		for (IContract contract:found){
			if (contract.getId().equals(firstContractAda)){
				assertTrue(contract.getContractItems().size() == firstContractContractItems);
			}
		}
	
	}	
	
	@Test
	public void testExtendContract() throws Exception {
		logger.debug( "Testing extending a contract" );
		dbHandler.clearDb();
		IContract contract = loadContract( "single-contract.xml" );
		service.save( Arrays.asList( contract ), new Validation() );
		IContract contractExtends = loadContract( "single-contract.xml" );
		contractExtends.getUntil().add( Calendar.YEAR, 1 );
		contractExtends.setExtendsContract( contract );
		Validation validation = new Validation();
		service.save( Arrays.asList( contractExtends ), validation );
		assertTrue( validation.isValid() );
		assertEquals( service.getAll().size(), 2 );
	}
	
	@Test
	public void testChangeContract() throws Exception {
		logger.debug( "Testing changing a contract" );
		dbHandler.clearDb();
		IContract contract = loadContract( "single-contract.xml" );
		service.save( Arrays.asList( contract ), new Validation() );
		IContract contractExtends = loadContract( "single-contract.xml" );		
		contractExtends.setChangesContract( contract );
		Validation validation = new Validation();
		service.save( Arrays.asList( contractExtends ), validation );
		assertTrue( validation.isValid() );
		assertEquals( service.getAll().size(), 2 );
	}
	
	@Test
	public void testReplaceContract() throws Exception {
		logger.debug( "Testing replacing a contract" );
		dbHandler.clearDb();
		IContract contract = loadContract( "single-contract.xml" );
		service.save( Arrays.asList( contract ), new Validation() );
		IContract contractExtends = loadContract( "single-contract.xml" );		
		contractExtends.setReplaces( contract );
		Validation validation = new Validation();
		service.save( Arrays.asList( contractExtends ), validation );
		assertTrue( validation.isValid() );
		assertEquals( service.getAll().size(), 2 );
	}
	
	// A simple test for manual debuggin
	@Test
	public void clearDb() {		
		dbHandler.clearDb();
	}
	
	private void printValidationErrors( IValidation	validation ) {
		logger.debug( "\nValidation Errors:\n" );
		
		for ( IValidationError validationError: validation.getErrors() ) { 
			logger.debug( validationError.getCode() + " - " + validationError.getLocation()  + " - " + validationError.getMessage() );
		}
	}	
}
