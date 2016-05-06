package gr.opengov.agora.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.fail;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IDecisionGenericConverter;
import gr.opengov.agora.domain.Contract;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.Payment;
import gr.opengov.agora.domain.ProcurementRequest;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.test.ContractTest;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
abstract public class GenericDomainTest <T extends IPublicOrganizationDecision, X, XA, XASHORT> {
	private static final Logger logger = LoggerFactory.getLogger(GenericDomainTest.class);
	
	private Class<T> objClass;
	private Class<X> xmlClass;
	private Class<XA> xmlArrayClass;
	
	private IPublicOrganizationDecision sampleDecision1;	
	private IPublicOrganizationDecision sampleDecision1Clone;	
	private IPublicOrganizationDecision sampleDecisionDifferent;
	
	private static XMLUtils xmlUtils;
	
	protected abstract String getSingleEntityXml();
	protected abstract IDecisionGenericConverter<T, X, XA, XASHORT> getConverter();
	
	protected GenericDomainTest( Class<T> objClass, Class<X> xmlClass, Class<XA> xmlArrayClass ) {
		this.objClass = objClass;
		this.xmlClass = xmlClass;
		this.xmlArrayClass = xmlArrayClass;
	}
	
	@BeforeClass
	public static void init() {
		xmlUtils = XMLUtils.newInstance();		
	}	
	
	@Before
	public void initializeSampleObjects() throws Exception {
		sampleDecision1 = load(getSingleEntityXml());
		sampleDecision1Clone = load(getSingleEntityXml());
		sampleDecisionDifferent = load(getSingleEntityXml());
		sampleDecisionDifferent.setTitle(sampleDecisionDifferent.getTitle() + "test");
	}
	
	private X getItemFromList( XA list, int index ) {
		//If we change the xsd of XA from getContract, getPayment etc, to getItem, maybe we can change these 3 different cases with the one of return (X)((XA)list).getItem().get(index);
		if ( objClass.isAssignableFrom( IContract.class ) ) {
			return (X)((ContractsOXM)list).getContract().get(index);
		}
		if ( objClass.isAssignableFrom( IPayment.class ) ) {
			return (X)((PaymentsOXM)list).getPayment().get(index);
		}
		if ( objClass.isAssignableFrom( IProcurementRequest.class ) ) {
			return (X)((ProcurementRequestsOXM)list).getRequest().get(index);
		}		
		return null;
	}	
	
	private T load( String xmlFile ) throws Exception {
		XA list = xmlUtils.unmarshal( xmlFile, xmlArrayClass );
		return getConverter().toObject( getItemFromList( list, 0 ) );
	}
	
	@Test
	public void testEqualsEmpty() {
		logger.debug( "Testing equality of empty decisions" );
		IPublicOrganizationDecision decision = getConverter().getNewInstance();
		IPublicOrganizationDecision decisionb = getConverter().getNewInstance();

		if (decision != null)
			assertEquals( decision, decision );
		else
			fail();
		
		if ((decision != null) && (decisionb != null))
			assertEquals( decision, decisionb );
		else
			fail();		
	}
	
	@Test	
	public void testEqualsSampleSelf() {
		logger.debug( "Testing self-equality for sample decision" );
		assertNotNull( sampleDecision1 );
		assertEquals( sampleDecision1, sampleDecision1 );
	}
	
	@Test
	public void testEqualsSampleClones() {
		logger.debug( "Testing equality between clone samples" );
		assertNotNull( sampleDecision1 );
		assertNotNull( sampleDecision1Clone );
		assertEquals( sampleDecision1, sampleDecision1Clone );
		assertEquals( sampleDecision1Clone, sampleDecision1 );
		assertTrue( sampleDecision1.equals( sampleDecision1Clone ) );
	}
	
	@Test
	public void testUnequalSamples() {
		logger.debug( "Testing non-equality between different samples" );
		assertNotNull( sampleDecision1 );
		assertNotNull( sampleDecisionDifferent );
		assertFalse( sampleDecision1.equals( sampleDecisionDifferent ) ); 
		assertFalse( sampleDecisionDifferent.equals( sampleDecision1 ) );		
	}
}
