package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.test.util.XMLUtils;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class CpvTest {
	private static final Logger logger = LoggerFactory.getLogger(CpvTest.class);

	private ICpv sampleCpv1;	
	private ICpv sampleCpv1Clone;	
	private ICpv sampleCpv1Different;	
	
	@Before
	public void init() throws Exception {
		XMLUtils xmlUtils = XMLUtils.newInstance();
		sampleCpv1 = new Cpv("test");
		sampleCpv1Clone = new Cpv("test");
		sampleCpv1Clone.setId(new Long(1));
		sampleCpv1Different = new Cpv("test1");
		
//		sampleContract1 = contractOxmConverter.toObject( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ) );
//		sampleContract1Clone = contractOxmConverter.toObject( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ) );
//		sampleContract1Different = contractOxmConverter.toObject( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ) );
//		sampleContract1Different.getContractItems().get(0).getCpvCodes().add( new Cpv("TEST-CPV") );
	}
	
	
	@Test
	public void testEqualsEmpty() {
		logger.debug( "Testing equality of empty cpvs" );
		ICpv cpv = new Cpv();
		assertEquals( cpv, cpv );
		assertEquals( cpv, new Cpv() );		
	}
	
	@Test	
	public void testEqualsSampleSelf() {
		logger.debug( "Testing self-equality for sample cpv" );
		assertNotNull( sampleCpv1 );
		assertEquals( sampleCpv1, sampleCpv1 );
	}
	
	@Test
	public void testEqualsSampleClones() {
		logger.debug( "Testing equality between clone samples" );
		assertNotNull( sampleCpv1 );
		assertNotNull( sampleCpv1Clone );
		assertEquals( sampleCpv1, sampleCpv1Clone );
		assertEquals( sampleCpv1Clone, sampleCpv1 );
		assertTrue( sampleCpv1.equals( sampleCpv1Clone ) );
	}
	
	@Test
	public void testUnequalSamples() {
		logger.debug( "Testing non-equality between different samples" );
		assertNotNull( sampleCpv1 );
		assertNotNull( sampleCpv1Different );
		assertFalse( sampleCpv1.equals( sampleCpv1Different ) ); 
		assertFalse( sampleCpv1Different.equals( sampleCpv1 ) );		
	}
}
