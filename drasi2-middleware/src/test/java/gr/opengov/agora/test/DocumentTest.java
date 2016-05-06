package gr.opengov.agora.test;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;

import gr.opengov.agora.domain.Document;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.exceptions.DocumentStamperNotFoundException;
import gr.opengov.agora.exceptions.InternalErrorException;
import gr.opengov.agora.exceptions.PdfPasswordException;

import org.apache.commons.io.IOUtil;
import org.apache.tika.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration("classpath:spring/root-context.xml")
public class DocumentTest {
	
	@Test
	public void testHash() throws Exception {
		Document doc = new Document();
		Resource resource = new ClassPathResource( "files/hashtest.txt" );
		String dataHashSha256 = "91751cee0a1ab8414400238a761411daa29643ab4b8243e9a91649e25be53ada";
		doc.setHashAlgorithm( "SHA-256" );
		doc.setData( IOUtil.toByteArray( resource.getInputStream() ) );
		assertEquals( doc.getHash(), dataHashSha256 );		
	}
	
	@Test
	public void testChangeHashAlgorithmAfterDataIsSet() throws Exception {
		Document doc = new Document();
		Resource resource = new ClassPathResource( "files/hashtest.txt" );
		String dataHashMD5 = "e19c1283c925b3206685ff522acfe3e6";
		doc.setData( IOUtil.toByteArray( resource.getInputStream() ) );
		doc.setHashAlgorithm( "MD5" );		
		assertEquals( doc.getHash(), dataHashMD5 );				
	}
	
	@Test
	public void testStampInvalidContentType() throws Exception {
		Document doc = new Document();
		Resource resource = new ClassPathResource( "files/hashtest.txt" );
		doc.setData( IOUtil.toByteArray( resource.getInputStream() ) );
		try {
			IDocument signed = doc.getStampedDocument( "ΔΟΚΙΜΑΣΤΙΚΟ" );
			fail();
		}
		catch ( DocumentStamperNotFoundException e ) {
			
		}		
	}
	
	@Test
	public void testStampPasswordProtectedPdf() throws Exception {
		Document doc = new Document();
		Resource resource = new ClassPathResource( "files/pdf-passwd.pdf" );
		doc.setData( IOUtil.toByteArray( resource.getInputStream() ) );
		try {
			IDocument signed = doc.getStampedDocument( "ΔΟΚΙΜΑΣΤΙΚΟ" );
			fail();
		}
		catch ( PdfPasswordException e ) {
			
		}		
	}
		
	@Ignore
	@Test
	public void testStampPdf() throws Exception {
		String hashSha256Unstamped = "9996615c959d7f4645ad4921fb9a3211addc01565d21f2ad79fd56d962071ced";
		String hashSha256Stamped = "048808e7fec1d4c4c83ff5cb0887745541f981494b0a1254451defc0bc400778";		
		Document doc = new Document();
		doc.setHashAlgorithm( "SHA-256" );
		Resource resource = new ClassPathResource( "files/pdf-unstamped.pdf" );		
		doc.setData( IOUtil.toByteArray( resource.getInputStream() ) );
		IDocument signed = doc.getStampedDocument( "ΔΟΚΙΜΑΣΤΙΚΟ" );
		BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream( "/tmp/signed.pdf" ) );
		IOUtil.copy( signed.getData(), bos );
		assertEquals( doc.getHash(), hashSha256Unstamped );
		assertEquals( signed.getHash(), hashSha256Stamped );
	}
	
}
