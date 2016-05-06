package gr.opengov.agora.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tools {
	private static final Logger logger = LoggerFactory.getLogger(Tools.class);

	private final static SimpleDateFormat dateLocaleFormatter = new SimpleDateFormat( "dd/MM/yyyy kk:mm:ss" );
	
	/* (non-Javadoc)
	 * @see gr.gov.diavgeia.util.ITools#parseDbList(java.lang.String)
	 */
	public static List<Integer> parseDbList( String lst ) {
		List<Integer> items = new ArrayList<Integer>();
		StringTokenizer tok = new StringTokenizer( lst, ",");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			token = token.replaceAll("#", "");
			try {
				Integer id = Integer.parseInt(token);
				items.add( id );
			} catch (Exception e) { }
		}
		return items;		
	}
	
	public static String hex( byte[] msg ) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < msg.length; i++) {
			String str = Integer.toHexString(0xFF & msg[i]);
			if ( str.length() == 1 ) str = "0" + str;
			sb.append( str );
		}
		return sb.toString();		
	}

	public static String makeDbList(List<Integer> lst) {
		if ( lst == null ) return "";
		String ret = "";
		for ( Integer id: lst ) {
			if ( ret.length() > 0 ) ret += ",";
			ret += "#" + id + "#";			
		}
		return ret;
	}
	
	public static String formatDateToLocale( Date date ) {
		return dateLocaleFormatter.format( date );		
	}

	public static String getQueryUrl( HttpServletRequest request ) {
		String url = request.getRequestURL().toString();
		String query = request.getQueryString();
		if ( query != null ) {
			url += "?" + query;
		}
		return query;
	}

	public static String normalizeXMLString(String str) {
		StringBuilder out = new StringBuilder();                // Used to hold the output.
    	int codePoint;                                          // Used to reference the current character.
		int i=0;
    	while( i < str.length()) {
    		codePoint = str.codePointAt(i);                       // This is the unicode code of the character.
			if ((codePoint == 0x9) ||          				    // Consider testing larger ranges first to improve speed. 
					(codePoint == 0xA) ||
					(codePoint == 0xD) ||
					((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
					((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
					((codePoint >= 0x10000) && (codePoint <= 0x10FFFF))) 
			{
				out.append(Character.toChars(codePoint));

			}				
			i+= Character.charCount(codePoint);                 // Increment with the number of code units(java chars) needed to represent a Unicode char.  
    	}
    	return out.toString();
	}
}
