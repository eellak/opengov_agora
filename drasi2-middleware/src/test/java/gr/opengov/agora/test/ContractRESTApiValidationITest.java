package gr.opengov.agora.test;

import static org.junit.Assert.*;
import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;
import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.StoreDecisionResponse;
import gr.opengov.agora.model.TaxonomyReferenceOXM;
import gr.opengov.agora.model.ValidationErrorOXM;
import gr.opengov.agora.test.util.Utility;
import gr.opengov.agora.test.util.WebUtils;
import gr.opengov.agora.test.util.XMLUtils;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.meterware.httpunit.HttpInternalErrorException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/root-context.xml")
public class ContractRESTApiValidationITest {
	public static final Logger logger = LoggerFactory
			.getLogger(ContractRESTApiValidationITest.class);
	@Resource( name="webUtils" )
	private WebUtils webUtils;
	private XMLUtils xmlUtils;
	private Utility utils;
	
	@Before
	public void init() {
		webUtils.setLogger(logger);
		xmlUtils = XMLUtils.newInstance();
		utils = new Utility(xmlUtils);
	}

	private void printValidationErrors( ArrayOfValidationErrors errors ) {
		logger.debug( "\nValidation Errors:\n" );
		for ( ValidationErrorOXM oxm: errors.getError() ) { 
			logger.debug( oxm.getField() + " - " + oxm.getXpath()  + " - " + oxm.getMsg() );
		}
	}
	
	@Test
	public void testInvalidCurrency() throws Exception {
		logger.debug("Testing sending a single contract, invalid currency");
		List<String> uCodes = utils.getUniqueDocumentCodesFromContract("single-contract.xml");
		ContractsOXM contracts = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );
		TaxonomyReferenceOXM ref = new TaxonomyReferenceOXM();
		ref.setIdRef("test");
		contracts.getContract().get(0).getContractItems().getItem().get(0).getCost().setCurrency(ref);
		StoreDecisionResponse xmlResponse = webUtils.storeDecisions( xmlUtils.marshal( contracts ), webUtils.getContractsUrl() );		
		assertNotNull(xmlResponse.getValidationErrors());
		printValidationErrors( xmlResponse.getValidationErrors() );
	}
	
	@Test
	public void testExtendInvalidContract() throws Exception {
		logger.debug( "Testing sending invalid extension");
		ContractsOXM contracts = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );
		contracts.getContract().get(0).setExtendsContract( "test" );		
		StoreDecisionResponse xmlResponse = webUtils.storeDecisions( xmlUtils.marshal( contracts ), webUtils.getContractsUrl() );
		assertNotNull(xmlResponse.getValidationErrors());
		printValidationErrors( xmlResponse.getValidationErrors() );
	}

	@Test
	public void testChangeInvalidContract() throws Exception {
		logger.debug( "Testing sending invalid extension");
		ContractsOXM contracts = xmlUtils.unmarshal( "single-contract.xml", ContractsOXM.class );
		contracts.getContract().get(0).setChangesContract( "test" );
		StoreDecisionResponse xmlResponse = webUtils.storeDecisions( xmlUtils.marshal( contracts ), webUtils.getContractsUrl() );
		assertNotNull(xmlResponse.getValidationErrors());
		printValidationErrors( xmlResponse.getValidationErrors() );
	}

}
