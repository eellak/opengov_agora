package gr.opengov.agora.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.DiavgeiaConnectionException;
import gr.opengov.agora.security.IAccessControl;

public class DiavgeiaAdaGenerator implements IAdaGenerator {
	private static final Logger logger = LoggerFactory.getLogger(DiavgeiaAdaGenerator.class);
	
	private IAccessControl accessControl;
	
	public void setAccessControl(IAccessControl accessControl) {
		this.accessControl = accessControl;
	}	

	@Override
	public String getNewId(IPublicOrganizationDecision entity) {
		WebConversation wc = new WebConversation();
		wc.setExceptionsThrownOnErrorStatus( false );
		WebRequest request = new PostMethodWebRequest( "http://test.diavgeia.gov.gr/diavgeia_service/index.php" );
		request.setHeaderField( "Content-Type", "application/x-www-form-urlencoded" );
		request.setHeaderField( "Accept", "text/html" );
		request.setParameter("function", "generateADA");
		request.setParameter("user", accessControl.getClient().getUsername());
		request.setParameter("pass", accessControl.getClient().getPassword());
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		request.setParameter("date", sdf.format(cal.getTime()));
		request.setParameter("orgID", ((Integer)accessControl.getClient().getOrgs().toArray()[0]).toString());
		
		logger.debug("[getting ada from diavgeia\n]");
		logger.debug("user= " + accessControl.getClient().getUsername() + "\n]");
		logger.debug("pass= " + accessControl.getClient().getPassword()  + "\n]");
		logger.debug("date= " + sdf.format(cal.getTime()));		
		logger.debug("orgID= " + ((Integer)accessControl.getClient().getOrgs().toArray()[0]).toString()  + "\n]");
		logger.debug("[/getting ada from diavgeia\n]");
		
		WebResponse response = null;
		try {
			response = wc.getResponse( request );
		} catch (IOException e) {
			logger.error("DiavgeiaAdaGenerator.getNewId():WebResponse.getResponce(WebRequest) IOException: " + e.getMessage());
			e.printStackTrace();
			throw new DiavgeiaConnectionException();
		} catch (SAXException e) {
			logger.error("DiavgeiaAdaGenerator.getNewId():WebResponse.getResponce(WebRequest) SAXException: " + e.getMessage());
			e.printStackTrace();
			throw new DiavgeiaConnectionException();
		}
		
		if ( (response != null) && response.getResponseCode() == HttpStatus.SC_OK ) {
			try {
				return (IOUtil.toString( response.getInputStream() ));
			} catch (IOException e) {
				logger.error("DiavgeiaAdaGenerator.getNewId():WebResponse.getInputStream() IOException: " + e.getMessage());
				e.printStackTrace();
				throw new DiavgeiaConnectionException();
			}
		}
		
		return null;		
	}

}
