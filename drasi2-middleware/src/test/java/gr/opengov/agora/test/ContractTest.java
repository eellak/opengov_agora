package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.Contract;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.model.ContractsOXM;
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
public class ContractTest {
	private static final Logger logger = LoggerFactory.getLogger(ContractTest.class);
	@Autowired
	IContractOXMConverter contractOxmConverter;
	private IContract sampleContract1;	
	private IContract sampleContract1Clone;	
	private IContract sampleContract1Different;	
	
	@Before
	public void init() throws Exception {
		XMLUtils xmlUtils = XMLUtils.newInstance();
		sampleContract1 = contractOxmConverter.toObject( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ) );
		sampleContract1Clone = contractOxmConverter.toObject( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ) );
		sampleContract1Different = contractOxmConverter.toObject( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ) );
		sampleContract1Different.getContractItems().get(0).getCpvCodes().add( new Cpv("TEST-CPV") );
	}
	
	
	@Test
	public void testEqualsEmpty() {
		logger.debug( "Testing equality of empty contracts" );
		IContract contract = new Contract();
		assertEquals( contract, contract );
		assertEquals( contract, new Contract() );		
	}
	
	@Test	
	public void testEqualsSampleSelf() {
		logger.debug( "Testing self-equality for sample contract" );
		assertNotNull( sampleContract1 );
		assertEquals( sampleContract1, sampleContract1 );
	}
	
	@Test
	public void testEqualsSampleClones() {
		logger.debug( "Testing equality between clone samples" );
		assertNotNull( sampleContract1 );
		assertNotNull( sampleContract1Clone );
		assertEquals( sampleContract1, sampleContract1Clone );
		assertEquals( sampleContract1Clone, sampleContract1 );
		assertTrue( sampleContract1.equals( sampleContract1Clone ) );
	}
	
	@Test
	public void testUnequalSamples() {
		logger.debug( "Testing non-equality between different samples" );
		assertNotNull( sampleContract1 );
		assertNotNull( sampleContract1Different );
		assertFalse( sampleContract1.equals( sampleContract1Different ) ); 
		assertFalse( sampleContract1Different.equals( sampleContract1 ) );		
	}
}
