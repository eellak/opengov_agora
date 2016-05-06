package gr.opengov.agora.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gr.opengov.agora.converters.ICommonsOXMConverter;
import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.model.ApiInfo;
import gr.opengov.agora.model.AuthenticationProfileOXM;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.IDecisionUtilsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Handles requests for the application home page.
 */
@Controller

public class UtilityController {
	
	@Resource(name = "commonsOxmConverter")
	private ICommonsOXMConverter commonsConverter;
	
	@Resource(name = "commonsOxmConverterReadOnly")
	private ICommonsOXMConverter commonsConverterReadOnly;	
	
	
	@Autowired
	private IAccessControl accessControl;
	
	@Resource( name="configProperties" )
	private Properties configProperties;

	@Resource( name="decisionService" )
	private IDecisionUtilsService decisionService;
	
	@Resource( name="decisionServiceReadOnly" )
	private IDecisionUtilsService decisionServiceReadOnly;	
	
	private static final Logger logger = LoggerFactory
			.getLogger(UtilityController.class);

	
	private IDecisionUtilsService getDecisionService(){
		if (accessControl.getClient().isAnonymous()){
			return decisionServiceReadOnly;
		}
		else{
			return decisionService;
		}
	}
	
	private ICommonsOXMConverter getCommonsConverter(){
		if (accessControl.getClient().isAnonymous()){
			return commonsConverterReadOnly;
		}
		else{
			return commonsConverter;
		}
	}		
	
	/**
	 * Provide authentication information
	 * @param model
	 * @return
	 */
	@RequestMapping( value="/authenticate", method = RequestMethod.GET )	
	public String authentication( Model model ) {
		AuthenticationProfileOXM profile = getCommonsConverter().getAuthenticationProfile( accessControl.getClient() );
		model.addAttribute( profile );
		return "authenticationProfile";
	}
	
	@RequestMapping( value="/schema", method = RequestMethod.GET )
	public String listSchemas() {
		return "schema";
	}
	
	@RequestMapping( value="/islive", method = RequestMethod.GET )
	public void isLive(HttpServletResponse response) throws IOException {
		response.getOutputStream().println("1");
	}
	
	@RequestMapping( value="/afm", method = RequestMethod.GET )
	public void getNameFromAfm(
								HttpServletResponse response,
								@RequestParam(value = "afm",  required = true) String afm
								) throws IOException {

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8"); 
		PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
		out.println( getDecisionService().getNameFromAfm(afm) );	
		
		//response.getOutputStream().println(getDecisionService().getNameFromAfm(afm));
	}	
	
	@RequestMapping( value="/api-info", method = RequestMethod.GET )
	public String apiInfo( Model model ) {
		ApiInfo info = new ApiInfo();
		String buildNumber = configProperties.getProperty( "build.number" );
		String buildTimestamp = configProperties.getProperty( "build.timestamp" );
		info.setBuild( buildNumber + "-" + buildTimestamp );
		Calendar cal = GregorianCalendar.getInstance();		
		cal.setTimeInMillis( Long.parseLong( buildTimestamp ) );
		info.setBuildTime( cal );
		model.addAttribute( info );
		return "apiInfo";
	}
}