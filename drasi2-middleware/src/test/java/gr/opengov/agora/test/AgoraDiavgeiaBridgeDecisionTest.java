package gr.opengov.agora.test;

import static org.junit.Assert.*;
import gr.opengov.agora.bridges.diavgeia.AgoraDiavgeiaBridge;
import gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge;
import gr.opengov.agora.bridges.diavgeia.IDiavgeiaConverter;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaDecision;
import gr.opengov.agora.bridges.diavgeia.domain.IDiavgeiaDecision;
import gr.opengov.agora.cms.ICms;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IPaymentOXMConverter;
import gr.opengov.agora.converters.IProcurementRequestOXMConverter;
import gr.opengov.agora.domain.AuthenticationProfile;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.exceptions.InvalidDiavgeiaDecisionTypeException;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.security.IClient;
import gr.opengov.agora.test.stub.OdeOrgAdminUser;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.util.IAdaGenerator;

import java.io.IOException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class AgoraDiavgeiaBridgeDecisionTest {
	private static final Logger logger = LoggerFactory.getLogger(AgoraDiavgeiaBridgeDecisionTest.class);
//	@Resource( name="dataSourceDiavgeia")
//	private DataSource dataSourceDiavgeia;
	@Resource( name="diavgeiaBridge")
	private IAgoraDiavgeiaBridge bridge;
	private static int UNIT_DISA = 173;
	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter contractConverter;
	@Resource( name="procurementRequestOxmConverter" )
	private IProcurementRequestOXMConverter procurementRequestConverter;
	@Resource( name="paymentOxmConverter" )
	private IPaymentOXMConverter paymentConverter;
	@Resource( name="diavgeiaConverter")
	private IDiavgeiaConverter diavgeiaConverter;
	@Resource( name="cmsManager")
	private ICms cms;
	
	private XMLUtils xmlUtils;
	private IClient client;

	@Before
	public void init() {
		xmlUtils = XMLUtils.newInstance();
		client = new OdeOrgAdminUser();
	}
	
	private IContract getContract() throws IOException {
		ContractsOXM oxm = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );
		return contractConverter.toObject( oxm.getContract().get(0) );		
	}
	
	private IProcurementRequest getProcurementRequest() throws IOException {
		ProcurementRequestsOXM oxm = xmlUtils.unmarshal( "single-request.xml", ProcurementRequestsOXM.class );
		return procurementRequestConverter.toObject( oxm.getRequest().get(0) );
	}
	
	private IPayment getPayment() throws IOException {
		PaymentsOXM oxm = xmlUtils.unmarshal( "single-payment.xml", PaymentsOXM.class );
		return paymentConverter.toObject( oxm.getPayment().get(0) );
	}
	
	@Test
	public void testSaveContract() throws IOException {
		IContract obj = getContract();
		try {
			cms.initialize( obj );
			logger.debug( "Generated ada: " + obj.getId() );
			logger.debug( "Converting to DiavgeiaDecision" );
			IDiavgeiaDecision decision = diavgeiaConverter.getDiavgeiaDecision( obj, client );
			fail();
		}
		catch ( InvalidDiavgeiaDecisionTypeException e ) {
			
		}
	}

	@Test
	public void testSaveProcurementRequest() throws IOException {
		logger.debug( "Testing saving a procurement request to Diavgeia");
		IProcurementRequest obj = getProcurementRequest();
		try {
			cms.initialize( obj );
			logger.debug( "Generated ada: " + obj.getId() );			
			logger.debug( "Converting to DiavgeiaDecision" );
			IDiavgeiaDecision decision = diavgeiaConverter.getDiavgeiaDecision( obj, client );
			fail();
		}
		catch ( InvalidDiavgeiaDecisionTypeException e ) {
			
		}
	}
	
	@Test
	public void testSavePayment() throws IOException {
		logger.debug( "Testing saving a payment to Diavgeia");
		IPayment obj = getPayment();
		// TODO: XSD should change to reflect that unit is required
		obj.setUnitDiavgeiaId( UNIT_DISA );
		try {
			cms.initialize( obj );
			logger.debug( "Generated ada: " + obj.getId() );
			logger.debug( "Saving..." );
			bridge.saveDecision( obj );		
			// TODO: Add more tests
		}
		catch ( InvalidDiavgeiaDecisionTypeException e ) {
			fail();
		}
	}
	
	@Test
	public void testUpdatePayment() throws IOException {
		logger.debug( "Testing updating a payment to Diavgeia");
		IPayment obj = getPayment();
		// TODO: XSD should change to reflect that unit is required
		obj.setUnitDiavgeiaId( UNIT_DISA );
		try {
			cms.initialize( obj );
			logger.debug( "Generated ada: " + obj.getId() );
			logger.debug( "Saving..." );
			bridge.saveDecision( obj );			
			logger.debug( "Changing subect" );
			obj.setTitle( "NEW SUBJECT" );
			bridge.updateDecision( obj );
			// TODO: Add more tests
		}
		catch ( InvalidDiavgeiaDecisionTypeException e ) {
			fail();
		}
	}
	
	@Test
	public void testDeletePayment() throws IOException {
		logger.debug( "Testing deleting a payment from Diavgeia");
		IPayment obj = getPayment();
		// TODO: XSD should change to reflect that unit is required
		obj.setUnitDiavgeiaId( UNIT_DISA );
		try {
			cms.initialize( obj );
			logger.debug( "Generated ada: " + obj.getId() );
			logger.debug( "Saving..." );
			bridge.saveDecision( obj );
			logger.debug( "Deleting..." );
			bridge.deleteDecision( obj.getId() );
			// TODO: Add more tests
		}
		catch ( InvalidDiavgeiaDecisionTypeException e ) {
			fail();
		}
	}

}
