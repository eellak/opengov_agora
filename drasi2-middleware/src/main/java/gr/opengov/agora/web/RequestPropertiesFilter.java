package gr.opengov.agora.web;

import gr.opengov.agora.security.IAccessControl;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter that saves all request properties in a 
 * @author xenofon
 *
 */
public class RequestPropertiesFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(RequestPropertiesFilter.class);
	private IAccessControl accessControl; 
	
	public IAccessControl getAccessControl() {
		return accessControl;
	}

	public void setAccessControl(IAccessControl accessControl) {
		this.accessControl = accessControl;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		accessControl.getClient().addServletRequestInfo( request );		
		chain.doFilter( request, response );
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}

}
