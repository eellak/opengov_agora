package gr.opengov.agora.search;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.web.ContractController;
import gr.opengov.agora.web.NoticeController;
import gr.opengov.agora.web.PaymentController;
import gr.opengov.agora.web.ProcurementRequestController;
import gr.opengov.agora.util.Constants;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchParameterFactory {
	private static final Logger logger = LoggerFactory.getLogger(SearchParameterFactory.class);

	private SearchParameterFactory() { }

	/**
	 * Constructs a list of search parameters from the given http request parameters.
	 * Throws InvalidSearchParameterException if a parameter has an invalid format.
	 * Silently ignores unsupported parameters.
	 * @param request
	 * @return
	 * @throws InvalidSearchParameterException
	 */
	public static List<ISearchParameter> fromServletRequest( HttpServletRequest request ) throws InvalidSearchParameterException {
		List<ISearchParameter> ret = new ArrayList<ISearchParameter>();
		Enumeration names = request.getParameterNames();
		while ( names.hasMoreElements() ) {
			String name = (String)names.nextElement();
			String value = request.getParameter( name );
			name = name.toLowerCase();
			logger.debug( "Parsing search criterion: " + name + ", value: " + value );
			if ( name.equals( "org" ) ) {				
				ret.add( new OrganizationSearchParameter( value ) );
			}
			else if ( name.equals( "idlike" ) ) {					
				ret.add( new IdLikeSearchParameter( value ) );
			}
			else if ( name.equals( "contractid" ) ) {					
				ret.add( new StringSearchParameter( "contract.id", value ) );
			}
			else if ( name.equals("titlelike")){
				ret.add(new StringLikeSearchParameter("title", value));
			}
			else if ( name.equals("titleoridlike")){
				ret.add(new StringLikeTwoOrFieldsSearchParameter("id", "title", value));
			}
			else 
				if ( name.equals("approvedfilter")){
					ret.add(new ApprovedFilterSearchParameter("approvedfilter", value));
			}			
			else 
				if ( name.equals("cpv") ){
					ret.add(new CpvSearchParameter(value));
//					if (clazz.isAssignableFrom(ContractController.class))
//						logger.debug("isAssignableFrom ContractRequestController");
//					if (clazz.isAssignableFrom(PaymentController.class))
//						logger.debug("isAssignableFrom PaymentController");
//					if (clazz.isAssignableFrom(NoticeController.class))
//						logger.debug("isAssignableFrom NoticeController");					
					
					
			}				
			
		}		
		return ret;
	}
}