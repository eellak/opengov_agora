package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IPaymentOXMConverter;
import gr.opengov.agora.domain.Contract;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.Payment;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.test.util.XMLUtils;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class PaymentTest {
	private static final Logger logger = LoggerFactory.getLogger(PaymentTest.class);
	@Autowired
	IPaymentOXMConverter paymentOxmConverter;
	private IPayment samplePayment1;	
	private IPayment samplePayment1Clone;	
	private IPayment samplePayment1Different;	
	
	@Before
	public void init() throws Exception {
		XMLUtils xmlUtils = XMLUtils.newInstance();
		samplePayment1 = paymentOxmConverter.toObject( xmlUtils.unmarshal( "single-payment.xml", PaymentsOXM.class ).getPayment().get( 0 ) );
		samplePayment1Clone = paymentOxmConverter.toObject( xmlUtils.unmarshal( "single-payment.xml", PaymentsOXM.class ).getPayment().get( 0 ) );
		samplePayment1Different = paymentOxmConverter.toObject( xmlUtils.unmarshal( "single-payment.xml", PaymentsOXM.class ).getPayment().get( 0 ) );
		samplePayment1Different.getPaymentItems().get(0).getContractItem().getCpvCodes().add( new Cpv("TEST-CPV") );
	}
	
	
	@Test
	public void testEqualsEmpty() {
		logger.debug( "Testing equality of empty payments" );
		IPayment payment = new Payment();
		assertEquals( payment, payment );
		assertEquals( payment, new Payment() );		
	}
	
	@Test	
	public void testEqualsSampleSelf() {
		logger.debug( "Testing self-equality for sample payment" );
		assertNotNull( samplePayment1 );
		assertEquals( samplePayment1, samplePayment1 );
	}
	
	@Test
	public void testEqualsSampleClones() {
		logger.debug( "Testing equality between clone samples" );
		assertNotNull( samplePayment1 );
		assertNotNull( samplePayment1Clone );
		assertEquals( samplePayment1, samplePayment1Clone );
		assertEquals( samplePayment1Clone, samplePayment1 );
		assertTrue( samplePayment1.equals( samplePayment1Clone ) );
	}
	
	@Test
	public void testUnequalSamples() {
		logger.debug( "Testing non-equality between different samples" );
		assertNotNull( samplePayment1 );
		assertNotNull( samplePayment1Different );
		assertFalse( samplePayment1.equals( samplePayment1Different ) ); 
		assertFalse( samplePayment1Different.equals( samplePayment1 ) );		
	}
}
