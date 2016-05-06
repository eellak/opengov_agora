package gr.opengov.agora.test;

import static org.junit.Assert.assertTrue;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IPaymentOXMConverter;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.IValidator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
public class PaymentValidationTest {
	private static final Logger logger = LoggerFactory.getLogger(PaymentValidationTest.class);
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;	
	private static XMLUtils xmlUtils;
	@Resource( name="paymentOxmConverter" )
	private IPaymentOXMConverter converter;
	@Resource( name="paymentValidator" )
	private IValidator<IPayment> validator;
	
	@BeforeClass
	public static void initStatic() {
		xmlUtils = XMLUtils.newInstance();
	}
		
	private IPayment loadPayment( String xmlFile ) throws Exception {
		PaymentsOXM payments = xmlUtils.unmarshal( xmlFile, PaymentsOXM.class );
		return converter.toObject( payments.getPayment().get( 0 ) );
	}
	
	@Test
	public void testCmsMetadataExists() throws Exception {
		logger.debug( "Testing metadata exists" );		
		IPayment payment = loadPayment( "single-payment.xml" );		
		payment.setCmsMetadata( new CmsMetadata() );
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "cmsMetadata" ) );		
	}
		
	@Test
	public void testOrganizationNegativeCost() throws Exception {
		logger.debug( "Testing negative cost" );		
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCost().setCostBeforeVat( new Double( -1.0 ) );
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "costBeforeVat" ) );
	}
	
	@Test
	public void testOrganizationInvalidVat() throws Exception {
		logger.debug( "Testing invalid VAT" );
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCost().setVatPercentage( new Double( -1.0 ) );
		IValidation validation = validator.validateCreate( payment, null );		
		assertTrue( validation.hasErrorCode( "vatPercentage" ) );
		payment.getPaymentItems().get(0).getContractItem().getCost().setVatPercentage( new Double( 100 ) );
		validation = validator.validateCreate( payment, null );		
		assertTrue( validation.hasErrorCode( "vatPercentage" ) );		
	}
	
	@Test
	public void testNoItems() throws Exception {
		logger.debug( "Testing payment with no items" );		
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().clear();
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "paymentItems" ) );		
	}
	
	@Test
	public void testInvalidCurrency() throws Exception {
		logger.debug( "Testing invalid currency" );
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCost().setCurrencyIdRef( "test" );
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "currency" ) );				
	}
	
	@Test
	public void testInvalidQuantity() throws Exception {
		logger.debug( "Testing invalid quantity" );
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().setQuantity( new Double (0) );
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "quantity" ) );				
	}
	
	@Test
	public void testValidCurrency() throws Exception {
		logger.debug( "Testing valid currency" );
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCost().setCurrencyIdRef( "EUR" );
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( !validation.hasErrorCode( "currency" ) );				
		payment.getPaymentItems().get(0).getContractItem().getCost().setCurrencyIdRef( "USD" );
		validation = validator.validateCreate( payment, null );
		assertTrue( !validation.hasErrorCode( "currency" ) );			
	}
	
	@Test
	public void testInvalidCpvCode() throws Exception {
		logger.debug( "Testing invalid CPV" );
		IPayment payment = loadPayment( "single-payment.xml" );		
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( !validation.hasErrorCode( "currency" ) );				
		payment.getPaymentItems().get(0).getContractItem().getCpvCodes().add( new Cpv("invalid") );
		validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "cpvInvalid" ) );			
	}
	
	@Ignore //cpvCodes is now a Set, so there are not duplicates
	@Test
	public void testDuplicateCpvCode() throws Exception {
		logger.debug( "Testing invalid CPV" );
		IPayment payment = loadPayment( "single-payment.xml" );		
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( !validation.hasErrorCode( "currency" ) );				
		List<ICpv> cpv = new ArrayList<ICpv>( payment.getPaymentItems().get(0).getContractItem().getCpvCodes() );
		payment.getPaymentItems().get(0).getContractItem().getCpvCodes().addAll( cpv );
		validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "cpvDuplicate" ) );			
	}
				
	@Test
	public void testInvalidOrganization() throws Exception {
		logger.debug( "Testing invalid organization" );
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.setOrganizationDiavgeiaId( -1 );
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "organizationDiavgeiaId" ) );
	}
	
	@Test
	public void testInvalidOrganizationUnit() throws Exception {
		logger.debug( "Testing invalid organization" );
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.setUnitDiavgeiaId( -355 );
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "unitDiavgeiaId" ) );
	}
	
	@Test
	public void testInvalidSigner() throws Exception {
		logger.debug( "Testing invalid organization" );
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getSignersDiavgeiaIds().add( -1 );
		IValidation validation = validator.validateCreate( payment, null );
		assertTrue( validation.hasErrorCode( "signer" ) );
	}
}
