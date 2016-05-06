package gr.opengov.agora.test.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class XMLUtils {
	private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);
		private Jaxb2Marshaller marshaller;
		private String xmlPathPrefix = "/xml/";
		private XMLUtils() { }
		public final static int CONTRACT = 0;
		public final static int PROCUREMENT_REQUEST = 1;
				
		public void setXmlPathPrefix( String prefix ) {
			this.xmlPathPrefix = prefix;
		}
		
		public static XMLUtils newInstance( String[] xsdPaths, String context ) {
			XMLUtils utils = new XMLUtils();
			utils.marshaller = new Jaxb2Marshaller();
			List<Resource> resources = new ArrayList<Resource>();
			
			for ( String xsdPath: xsdPaths ) {
				Resource resource = new ClassPathResource( xsdPath );
				resources.add( resource );
			}
			if ( resources.size() > 0 ) utils.marshaller.setSchemas( resources.toArray( new Resource[0] ) );
			if ( context != null ) utils.marshaller.setContextPath( context );
			return utils;
		}
		
		public static XMLUtils newInstance( int type ) {
			String[] xsdPaths = null;
			switch ( type ) {
			case CONTRACT:
				xsdPaths = new String[] { "schemas/common-0.1.xsd", "schemas/payment-0.1.xsd", "schemas/contract-0.1.xsd", "schemas/auth-0.1.xsd" };
				break;
			case PROCUREMENT_REQUEST:
				xsdPaths = new String[] { "schemas/procurement-request-0.1.xsd", "schemas/payment-0.1.xsd", "schemas/contract-0.1.xsd", "schemas/auth-0.1.xsd" };
				break;
			default:
				throw new UnsupportedOperationException();
			}
			return newInstance( xsdPaths, "gr.opengov.agora.model" );
		}
		
		public static XMLUtils newInstance( ) {
			String[] xsdPaths = new String[] { "schemas/common-0.1.xsd", "schemas/payment-0.1.xsd", "schemas/contract-0.1.xsd", "schemas/auth-0.1.xsd", "schemas/procurement-request-0.1.xsd", "schemas/notice-0.1.xsd" };			
			return newInstance( xsdPaths, "gr.opengov.agora.model" );
		}		
		
		public static XMLUtils newInstance( boolean validate ) {
			if ( validate ) return newInstance();
			else return newInstance( new String[0], "gr.opengov.agora.model" );
		}

		public InputStream readXml(String xmlPath ) throws IOException {			
			Resource resource = new ClassPathResource( xmlPathPrefix + xmlPath );
			return resource.getInputStream();
		}
		
		public <T> T unmarshal( String xmlPath, Class<T> cl ) throws IOException {			
			Source source = new StreamSource( readXml( xmlPath ) );
			return (T)marshaller.unmarshal( source );			
		}
		
		public <T> T unmarshal( InputStream stream, Class<T> cl ) throws IOException {
			Source source = new StreamSource( stream );			
			return (T)marshaller.unmarshal( source );
		}
		
		public <T> T unmarshalXml( String xml, Class<T> cl ) throws IOException {
			ByteArrayInputStream input = new ByteArrayInputStream (xml.getBytes());
			Source source = new StreamSource( input );			
			return (T)marshaller.unmarshal( source );
		}		
		
		public InputStream marshal( Object obj ) throws IOException {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Result result = new StreamResult( stream );
			marshaller.marshal( obj, result );
			return new ByteArrayInputStream( stream.toString().getBytes() );			
		}
}
