package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.IDecisionUtilsService;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.validation.Validation;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class DecisionUtilsServiceTest {
	private static XMLUtils utils;
	private static final Logger logger = LoggerFactory.getLogger(DecisionUtilsServiceTest.class);
	@Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	@Resource( name="contractService" )
	private IDecisionGenericService<IContract> contractService;
	@Resource( name="decisionService" )
	private IDecisionUtilsService decisionService;
	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter converter;
	
	@BeforeClass
	public static void init() {		
		utils = XMLUtils.newInstance( );		
	}
	
	private IContract loadContract( String xmlFile ) throws Exception {
		ContractsOXM contracts = utils.unmarshal( xmlFile, ContractsOXM.class );
		return converter.toObject( contracts.getContract().get( 0 ) );
	}	
	
	@Test
	public void testGetNameFromAfm() throws Exception {	
		logger.debug( "Testing saving a single contract, no access control" );
		dbHandler.clearDb();
		IContract contract = loadContract( "sample-contract-1.xml" );
		contractService.save( Arrays.asList( contract ), new Validation() );
		List<IContract> saved = contractService.getAll();
		assertTrue( saved.size() == 1 );
		
		assertTrue(decisionService.getNameFromAfm(contract.getSecondaryParties().get(0).getAfm()).equals(contract.getSecondaryParties().get(0).getName()));
		
	}
}
