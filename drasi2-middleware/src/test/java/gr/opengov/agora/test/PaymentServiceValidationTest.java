package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IPaymentOXMConverter;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.PaymentsOXM;
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
public class PaymentServiceValidationTest {
	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceValidationTest.class);
	@Resource( name="paymentService" )
	private IDecisionGenericService<IPayment> service;
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	private static XMLUtils xmlUtils;
	@Resource( name="paymentOxmConverter" )
	private IPaymentOXMConverter converter;
	
	@BeforeClass
	public static void initStatic() {
		xmlUtils = XMLUtils.newInstance();
	}
	
	private IPayment loadPayment( String xmlFile ) throws Exception {
		PaymentsOXM payments = xmlUtils.unmarshal( xmlFile, PaymentsOXM.class );
		return converter.toObject( payments.getPayment().get( 0 ) );
	}
	
	private List<IPayment> getList( IPayment payment ) {
		List<IPayment> list = new ArrayList<IPayment>();		
		list.add( payment );
		return list;
	}
	
	@Test
	public void testCmsMetadataExists() throws Exception {
		logger.debug( "Testing metadata exists" );
		IValidation validation = new Validation();
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.setCmsMetadata( new CmsMetadata() );
		List<IDecisionStorageReference> ret = service.save( getList( payment ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "cmsMetadata" ) );		
	}

	@Test
	public void testOrganizationNegativeCost() throws Exception {
		logger.debug( "Testing negative cost" );
		IValidation validation = new Validation();
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCost().setCostBeforeVat( new Double( -1.0 ) );
		List<IDecisionStorageReference> ret = service.save( getList( payment ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "costBeforeVat" ) );
	}
	
	@Test
	public void testOrganizationInvalidVat() throws Exception {
		logger.debug( "Testing invalid VAT" );
		IValidation validation = new Validation();
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCost().setVatPercentage( new Double( -1.0 ) );
		List<IDecisionStorageReference> ret = service.save( getList( payment ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "vatPercentage" ) );
		payment.getPaymentItems().get(0).getContractItem().getCost().setVatPercentage( new Double( 100 ) );
		ret = service.save( getList( payment ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "vatPercentage" ) );		
	}
	
	@Test
	public void testNoItems() throws Exception {
		logger.debug( "Testing payment with no items" );
		IValidation validation = new Validation();
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().clear();
		List<IDecisionStorageReference> ret = service.save( getList( payment ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "paymentItems" ) );		
	}
	
	@Test
	public void testInvalidCurrency() throws Exception {
		logger.debug( "Testing invalid currency" );
		IValidation validation = new Validation();
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCost().setCurrencyIdRef( "test" );
		List<IDecisionStorageReference> ret = service.save( getList( payment ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "currency" ) );				
	}
	
	@Test
	public void testInvalidCpvCode() throws Exception {
		logger.debug( "Testing invalid CPV" );
		IValidation validation = new Validation();
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCpvCodes().add( new Cpv("invalid") );
		List<IDecisionStorageReference> ret = service.save( getList( payment ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "cpvInvalid" ) );				
	}
	
	@Test
	public void testNoCpvCode() throws Exception {
		logger.debug( "Testing no CPV" );
		IValidation validation = new Validation();
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().get(0).getContractItem().getCpvCodes().clear();
		List<IDecisionStorageReference> ret = service.save( getList( payment ), validation );
		assertEquals( ret.size(), 0 );
		assertTrue( validation.hasErrorCode( "cpvCodes" ) );				
	}
}
