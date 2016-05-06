package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.TaxonomyReferenceOXM;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.Validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class ContractServiceValidationTest {
	private static final Logger logger = LoggerFactory.getLogger(ContractServiceValidationTest.class);
	@Resource( name="contractService" )
	private IDecisionGenericService<IContract> service;
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	private static XMLUtils xmlUtils;
	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter converter;
	
	@BeforeClass
	public static void initStatic() {
		xmlUtils = XMLUtils.newInstance();
	}
	
	private IContract loadContract( String xmlFile ) throws Exception {
		ContractsOXM contracts = xmlUtils.unmarshal( xmlFile, ContractsOXM.class );
		return converter.toObject( contracts.getContract().get( 0 ) );
	}
	
	private List<IContract> getList( IContract contract ) {
		List<IContract> list = new ArrayList<IContract>();		
		list.add( contract );
		return list;
	}
	
	@Test
	public void testExtensionDateFail() throws Exception {
		IValidation validation = new Validation();
		logger.debug( "Testing contract extension w/o date extension" );
		logger.debug( "...Saving contract" );
		dbHandler.clearDb();
		
		IContract contracta = loadContract( "sample-contract-1.xml" );
		service.save( Arrays.asList( contracta ), new Validation() );
		logger.debug( "...Creating new contract, extending previous one" );
		IContract saved = service.getAll().get( 0 );
		
		IContract contractb = loadContract( "sample-contract-1.xml" );
		contractb.getUntil().add( Calendar.YEAR, -1 );
		contractb.setExtendsContract( saved );
		List<IDecisionStorageReference> ret = service.save( Arrays.asList( contractb ), validation );
		
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "until" ) );			
	}
	
	@Test
	public void testExtensionDatePass() throws Exception {
		logger.debug( "Testing contract extension with date extension" );
		IValidation validation = new Validation();
		logger.debug( "...Saving contract" );
		dbHandler.clearDb();
		
		IContract contract = loadContract( "sample-contract-1.xml" );		
		service.save( Arrays.asList( contract ), new Validation() );
		logger.debug( "...Creating new contract, extending previous one" );
		IContract saved = service.getAll().get( 0 );
		
		IContract contractb = loadContract( "sample-contract-1.xml" );
		contractb.getUntil().add( Calendar.YEAR, 1 );
		contractb.setExtendsContract( saved );
		List<IDecisionStorageReference> ret = service.save( Arrays.asList( contractb ), validation );
		
		assertEquals( ret.size(), 1 );				
	}
	
	@Test
	public void testCmsMetadataExists() throws Exception {
		logger.debug( "Testing metadata exists" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.setCmsMetadata( new CmsMetadata() );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "cmsMetadata" ) );		
	}
	
	@Test
	public void testExtensionNotExists() throws Exception {
		logger.debug( "Testing extension of non-existing contract" );
		dbHandler.clearDb();
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		logger.debug( "...Saving first contract" );
		service.save( getList( contract ), new Validation() );
		IContract saved = service.getAll().get( 0 );
		contract = loadContract( "sample-contract-1.xml" );
		saved.setId( "test" );
		contract.getUntil().add( Calendar.YEAR, 1 );
		contract.setExtendsContract( saved );
		logger.debug( "...Saving extension" );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "extendsContract" ) );
	}
	
	@Test
	public void testChangedNotExists() throws Exception {
		logger.debug( "Testing changing of non-existing contract" );
		dbHandler.clearDb();
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		logger.debug( "...Saving first contract" );
		service.save( getList( contract ), new Validation() );
		IContract saved = service.getAll().get( 0 );
		contract = loadContract( "sample-contract-1.xml" );
		saved.setId( "test" );
		contract.setChangesContract( saved );
		logger.debug( "...Saving change" );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "changesContract" ) );
	}
	
	@Test
	public void testReplacesNotExists() throws Exception {
		logger.debug( "Testing replacing a non-existing contract" );
		dbHandler.clearDb();
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		logger.debug( "...Saving first contract" );
		service.save( getList( contract ), new Validation() );
		IContract saved = service.getAll().get( 0 );
		contract = loadContract( "sample-contract-1.xml" );
		saved.setId( "test" );
		contract.setReplaces( saved );
		logger.debug( "...Saving change" );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "replacesInvalid" ) );
	}
		
	@Test
	public void testDatesUntilBeforeSince() throws Exception {		
		logger.debug( "Testing 'until' date before 'since'" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );		
		Calendar date = GregorianCalendar.getInstance();
		date.setTimeInMillis( contract.getSince().getTimeInMillis() );
		date.add( Calendar.YEAR, -1 );
		contract.setUntil( date );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "since" ) );
	}
	
	@Test
	public void testSignedDateInvalid() throws Exception {		
		logger.debug( "Testing signed date" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );		
		Calendar date = GregorianCalendar.getInstance();
		date.add( Calendar.DAY_OF_YEAR, 1 );
		contract.setDateSigned( date );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "futureDateSigned" ) );
	}	
	
	@Test
	public void testOrganizationNegativeCost() throws Exception {
		logger.debug( "Testing negative cost" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCost().setCostBeforeVat( new Double( -1.0 ) );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "costBeforeVat" ) );
	}
	
	@Test
	public void testOrganizationInvalidVat() throws Exception {
		logger.debug( "Testing invalid VAT" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCost().setVatPercentage( new Double( -1.0 ) );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "vatPercentage" ) );
		contract.getContractItems().get(0).getCost().setVatPercentage( new Double( 100 ) );
		ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "vatPercentage" ) );		
	}
	
	@Test
	public void testNoItems() throws Exception {
		logger.debug( "Testing contract with no items" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().clear();
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "contractItems" ) );		
	}
	
	@Test
	public void testInvalidCurrency() throws Exception {
		logger.debug( "Testing invalid currency" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCost().setCurrencyIdRef( "test" );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "currency" ) );				
	}
	
	@Test
	public void testInvalidCpvCode() throws Exception {
		logger.debug( "Testing invalid CPV" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCpvCodes().add( new Cpv("invalid") );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "cpvInvalid" ) );				
	}
	
	@Test
	public void testNoCpvCode() throws Exception {
		logger.debug( "Testing no CPV" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCpvCodes().clear();
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "cpvCodes" ) );				
	}
	
	@Test
	public void testInvalidAwardProcedure() throws Exception {
		logger.debug( "Testing invalid award procedure" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.setAwardProcedureIdRef( "invalid" );
		List<IDecisionStorageReference> ret = service.save( getList( contract ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "awardProcedure" ) );				
	}
	
	@Test
	public void testExtendsNotExists() throws Exception {
		logger.debug( "Testing extendsContract does not exist" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		IContract contractExtends = loadContract( "sample-contract-1.xml" );
		contract.setId( "invalid" );
		contractExtends.setExtendsContract( contract );		
		List<IDecisionStorageReference> ret = service.save( getList( contractExtends ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "extendsContract" ) );
	}

	@Test
	public void testChangesNotExists() throws Exception {
		logger.debug( "Testing changesContract does not exist" );
		IValidation validation = new Validation();
		IContract contract = loadContract( "sample-contract-1.xml" );
		IContract contractExtends = loadContract( "sample-contract-1.xml" );
		contract.setId( "invalid" );
		contractExtends.setChangesContract( contract );		
		List<IDecisionStorageReference> ret = service.save( getList( contractExtends ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "changesContract" ) );
	}						
}
