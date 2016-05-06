package gr.opengov.agora.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.SearchParameterFactory;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;

public class SearchParameterTest {
	private static final Logger logger = LoggerFactory.getLogger(SearchParameterTest.class);
	@Test
	public void testNonExistingParameter() throws Exception {	
		logger.debug( "Testing non-existing parameter" );
		MockHttpServletRequest request = new MockHttpServletRequest();		
		request.addParameter( "invalidParam", "366" );
		List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( request );
		assertEquals( params.size(), 0 );		
	}
	
	@Test
	public void testValidParameter() throws Exception {
		logger.debug( "Testing valid parameter" );
		MockHttpServletRequest request = new MockHttpServletRequest();		
		request.addParameter( "org", "366" );
		List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( request );
		assertEquals( params.size(), 1 );		
	}
	
	@Test
	public void testInvalidParameter() throws Exception {
		logger.debug( "Testing invalid parameter" );
		MockHttpServletRequest request = new MockHttpServletRequest();		
		request.addParameter( "org", "noorg" );
		try {
			List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( request );
			fail();
		}
		catch ( InvalidSearchParameterException e ) { }		
	}
}
