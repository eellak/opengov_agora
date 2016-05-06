package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.opengov.agora.converters.ICommonsOXMConverter;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.Contract;
import gr.opengov.agora.domain.ContractItem;
import gr.opengov.agora.domain.Cpv;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
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
public class ContractItemTest {
	private static final Logger logger = LoggerFactory.getLogger(ContractItemTest.class);
	@Autowired
	ICommonsOXMConverter commonsOxmConverter;
	private IContractItem sampleContractItem1;	
	private IContractItem sampleContractItem1Clone;	
	private IContractItem sampleContractItem1Different;	
	
	@Before
	public void init() throws Exception {
		XMLUtils xmlUtils = XMLUtils.newInstance();
		sampleContractItem1 = commonsOxmConverter.convertContractItem( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ).getContractItems().getItem().get(0), new ContractItem() );
		sampleContractItem1Clone = commonsOxmConverter.convertContractItem( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ).getContractItems().getItem().get(0), new ContractItem() );
		sampleContractItem1Different = commonsOxmConverter.convertContractItem( xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class ).getContract().get( 0 ).getContractItems().getItem().get(0), new ContractItem() );
		sampleContractItem1Different.addCpv("TEST-CPV");
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
		assertNotNull( sampleContractItem1 );
		assertEquals( sampleContractItem1, sampleContractItem1 );
	}
	
	@Test
	public void testEqualsSampleClones() {
		logger.debug( "Testing equality between clone samples" );
		assertNotNull( sampleContractItem1 );
		assertNotNull( sampleContractItem1Clone );
		assertEquals( sampleContractItem1, sampleContractItem1Clone );
		assertEquals( sampleContractItem1Clone, sampleContractItem1 );
		assertTrue( sampleContractItem1.equals( sampleContractItem1Clone ) );
	}
	
	@Test
	public void testUnequalSamples() {
		logger.debug( "Testing non-equality between different samples" );
		assertNotNull( sampleContractItem1 );
		assertNotNull( sampleContractItem1Different );
		assertFalse( sampleContractItem1.equals( sampleContractItem1Different ) ); 
		assertFalse( sampleContractItem1Different.equals( sampleContractItem1 ) );		
	}
}
