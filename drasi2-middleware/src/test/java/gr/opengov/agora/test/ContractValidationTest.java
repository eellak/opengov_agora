package gr.opengov.agora.test;

import static org.junit.Assert.assertTrue;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.InvalidPublicOrganizationDecisionReference;
import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.ValidationErrorOXM;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.IValidationError;
import gr.opengov.agora.validation.IValidator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
public class ContractValidationTest {
	private static final Logger logger = LoggerFactory.getLogger(ContractValidationTest.class);
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;	
	private static XMLUtils xmlUtils;
	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter converter;
	@Resource( name="contractValidator" )
	private IValidator<IContract> validator;
	
	@BeforeClass
	public static void initStatic() {
		xmlUtils = XMLUtils.newInstance();
	}
		
	private IContract loadContract( String xmlFile ) throws Exception {
		ContractsOXM contracts = xmlUtils.unmarshal( xmlFile, ContractsOXM.class );
		return converter.toObject( contracts.getContract().get( 0 ) );
	}
	
	private void printValidationErrors( IValidation	validation ) {
		logger.debug( "\nValidation Errors:\n" );
		
		for ( IValidationError validationError: validation.getErrors() ) { 
			logger.debug( validationError.getCode() + " - " + validationError.getLocation()  + " - " + validationError.getMessage() );
		}
	}	
	
	@Test
	public void testExtensionDateFail() throws Exception {		
		logger.debug( "Testing contract extension w/o date extension" );
		dbHandler.clearDb();		
		IContract contract = loadContract( "sample-contract-1.xml" );
		IContract extendedContract = loadContract( "sample-contract-1.xml" );
		extendedContract.setExtendsContract( contract );
		IValidation validation = validator.validateCreate( extendedContract, null );
		assertTrue( validation.hasErrorCode( "until" ) );			
	}
	
	@Test
	public void testCmsMetadataExists() throws Exception {
		logger.debug( "Testing metadata exists" );		
		IContract contract = loadContract( "sample-contract-1.xml" );		
		contract.setCmsMetadata( new CmsMetadata() );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "cmsMetadata" ) );		
	}
	
	
	@Test
	public void testDatesUntilBeforeSince() throws Exception {		
		logger.debug( "Testing 'until' date before 'since'" );		
		IContract contract = loadContract( "sample-contract-1.xml" );		
		Calendar date = GregorianCalendar.getInstance();
		date.setTimeInMillis( contract.getSince().getTimeInMillis() );
		date.add( Calendar.YEAR, -1 );
		contract.setUntil( date );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "since" ) );
	}
	
	@Test
	public void testOrganizationNegativeCost() throws Exception {
		logger.debug( "Testing negative cost" );		
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCost().setCostBeforeVat( new Double( -1.0 ) );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "costBeforeVat" ) );
	}
	
	@Test
	public void testOrganizationInvalidVat() throws Exception {
		logger.debug( "Testing invalid VAT" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCost().setVatPercentage( new Double( -1.0 ) );
		IValidation validation = validator.validateCreate( contract, null );		
		assertTrue( validation.hasErrorCode( "vatPercentage" ) );
		contract.getContractItems().get(0).getCost().setVatPercentage( new Double( 100 ) );
		validation = validator.validateCreate( contract, null );		
		assertTrue( validation.hasErrorCode( "vatPercentage" ) );		
	}
	
	@Test
	public void testNoItems() throws Exception {
		logger.debug( "Testing contract with no items" );		
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().clear();
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "contractItems" ) );		
	}
	
	@Test
	public void testInvalidContractingAuthority() throws Exception {
		logger.debug( "Testing invalid contracting authority" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		logger.debug( "...Null ref, null other" );
		contract.setContractingAuthorityIdRef( null );
		contract.setContractingAuthorityOther( null );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "contractingAuthority" ) );
		
		logger.debug( "...Non-null ref, non-null other" );
		contract.setContractingAuthorityIdRef( "1" );
		contract.setContractingAuthorityOther( "test" );
		validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "contractingAuthority" ) );

		logger.debug( "...Invalid ref" );
		contract.setContractingAuthorityIdRef( "invalid" );
		contract.setContractingAuthorityOther( null );
		validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "contractingAuthority" ) );			
	}
	
	@Test
	public void testValidContractingAuthority() throws Exception {
		logger.debug( "Testing invalid contracting authority" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		Map<IAda,IPublicOrganizationDecision> relatedDecisions = new Hashtable<IAda, IPublicOrganizationDecision>();
		IAda ada = new Ada();
		ada.setAdaCode(contract.getRelatedAdas().get(0).getAdaCode());
		ada.setAdaType(contract.getRelatedAdas().get(0).getAdaType());
		relatedDecisions.put(ada, new InvalidPublicOrganizationDecisionReference());
		
		IValidation validation = validator.validateCreate( contract, relatedDecisions );
		printValidationErrors(validation);
		assertTrue( validation.isValid() );
		
		logger.debug( "...Null ref, non-null other" );
		contract.setContractingAuthorityIdRef( null );
		contract.setContractingAuthorityOther( "This is something else." );
		validation = validator.validateCreate( contract, relatedDecisions );
		if (!validation.isValid()){
			logger.debug("authority errors\n");
			printValidationErrors(validation);
		}
		assertTrue( validation.isValid() );
	}
	
	@Test
	public void testInvalidCurrency() throws Exception {
		logger.debug( "Testing invalid currency" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCost().setCurrencyIdRef( "test" );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "currency" ) );				
	}
	
	@Test
	public void testInvalidAfm() throws Exception {
		logger.debug( "Testing invalid Afm" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getSecondaryParties().get(0).setAfm("123");
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "invalidAfm" ) );				
	}
	
	@Test
	public void testInvalidQuantity() throws Exception {
		logger.debug( "Testing invalid quantity" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).setQuantity( new Double (0) );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "quantity" ) );				
	}
	
	@Test
	public void testValidCurrency() throws Exception {
		logger.debug( "Testing valid currency" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getContractItems().get(0).getCost().setCurrencyIdRef( "EUR" );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( !validation.hasErrorCode( "currency" ) );				
		contract.getContractItems().get(0).getCost().setCurrencyIdRef( "USD" );
		validation = validator.validateCreate( contract, null );
		assertTrue( !validation.hasErrorCode( "currency" ) );			
	}
	
	@Test
	public void testInvalidCpvCode() throws Exception {
		logger.debug( "Testing invalid CPV" );
		IContract contract = loadContract( "sample-contract-1.xml" );		
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( !validation.hasErrorCode( "currency" ) );				
		contract.getContractItems().get(0).getCpvCodes().add( new Cpv("invalid") );
		validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "cpvInvalid" ) );			
	}
	
	@Ignore //cpvCodes is now a Set, so there are not duplicates
	@Test
	public void testDuplicateCpvCode() throws Exception {
		logger.debug( "Testing invalid CPV" );
		IContract contract = loadContract( "sample-contract-1.xml" );		
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( !validation.hasErrorCode( "currency" ) );				
		List<ICpv> cpv = new ArrayList<ICpv>( contract.getContractItems().get(0).getCpvCodes() );
		contract.getContractItems().get(0).getCpvCodes().addAll( cpv );
		validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "cpvDuplicate" ) );			
	}
				
	@Test
	public void testInvalidOrganization() throws Exception {
		logger.debug( "Testing invalid organization" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.setOrganizationDiavgeiaId( -1 );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "organizationDiavgeiaId" ) );
	}
	
	@Test
	public void testInvalidOrganizationUnit() throws Exception {
		logger.debug( "Testing invalid organization" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.setUnitDiavgeiaId( -355 );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "unitDiavgeiaId" ) );
	}
	
	@Test
	public void testInvalidSigner() throws Exception {
		logger.debug( "Testing invalid organization" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.getSignersDiavgeiaIds().add( -1 );
		IValidation validation = validator.validateCreate( contract, null );
		assertTrue( validation.hasErrorCode( "signer" ) );
	}
}
