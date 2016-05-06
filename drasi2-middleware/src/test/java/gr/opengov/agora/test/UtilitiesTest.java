package gr.opengov.agora.test;

import static org.junit.Assert.assertEquals;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.test.util.XMLUtils;

import org.junit.BeforeClass;
import org.junit.Test;

public class UtilitiesTest {
	private static XMLUtils utils;
	
	@BeforeClass
	public static void init() {		
		utils = XMLUtils.newInstance( );		
	}
	
	@Test
	public void testXmlParser() throws Exception {	
		ContractsOXM contracts = utils.unmarshal( "single-contract.xml", ContractsOXM.class );
		assertEquals( contracts.getContract().size(), 1 );
	}
}
