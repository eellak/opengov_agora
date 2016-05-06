package gr.opengov.agora.domain;

import gr.opengov.agora.exceptions.DocumentStamperNotFoundException;
import gr.opengov.agora.util.IDocumentStamper;
import gr.opengov.agora.util.PdfDocumentStamper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Document implements IDocument, Serializable {
	private static final Logger logger = LoggerFactory.getLogger(Document.class);

	private Long id;
	private byte[] data;
	private String key;
	private String type;
	private String contentType;
	private Integer size;
	private Integer stampedSize;
	private String hash;
	private String stampedHash;
	private String hashAlgorithm = "SHA-256";
	private String stamp;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public byte[] getData() {
		return data;
	}
	@Override
	public void setData(byte[] data) {
		this.data = data;
		try {
			MimeType mime = MimeTypes.getDefaultMimeTypes().getMimeType( new ByteArrayInputStream( data ) );
			setContentType( mime.getName() );
			calculateHash();
		}
		catch ( IOException e ) { }
		catch ( NoSuchAlgorithmException e ) { 
			logger.error( "Setting data with invalid hash algorithm: " + hashAlgorithm );
		}
	}
	@Override
	public String getStamp() {
		return stamp;
	}
	// Hibernate setter
	private void setStamp(String stamp) {
		this.stamp = stamp;
	}	
		
	@Override
	public IDocumentStamper getStamper() {
		if ( contentType.equals( "application/pdf" ) ) return new PdfDocumentStamper();
		throw new DocumentStamperNotFoundException( "No stamper found for content type: " + contentType );
	}
	@Override
	public IDocument getStampedDocument(String stamp) {
		Document doc;
		assert( this.data != null );
		synchronized(this) {
			// Temporarily set data to null, so clone will not have to copy it.
			byte[] data = this.data;
			this.data = null;
			doc = (Document)SerializationUtils.clone( this );
			this.stamp = stamp;
			this.data = data;			
			doc.stamp = stamp;
			doc.data = this.getStamper().stamp( this.data, stamp );
			doc.size = doc.data.length;
			try {
				doc.calculateHash();
				this.stampedHash = doc.getHash();
				this.stampedSize = doc.getSize();
			} catch (NoSuchAlgorithmException e) { }
		}	
		return doc;
	}
	@Override
	public String getType() {
		return type;
	}
	@Override
	public void setType(String type) {
		this.type = type;
	}	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDocument#getPath()
	 */
	@Override
	public String getKey() {
		return key;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDocument#setPath(java.lang.String)
	 */
	// Hibernate setter
	private void setKey(String key) {
		this.key = key;
	}
	@Override
	public void copyKeyFrom( IDocument document ) {
		this.key = document.getKey();
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDocument#getSize()
	 */
	@Override
	public Integer getSize() {
		return size;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDocument#setSize(java.lang.Long)
	 */
	@Override
	public void setSize(Integer size) {
		this.size = size;
	}
	@Override
	public Integer getStampedSize() {
		return stampedSize;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDocument#setSize(java.lang.Long)
	 */
	@Override
	public void setStampedSize(Integer size) {
		this.stampedSize = size;
	}

	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDocument#getHashOriginal()
	 */
	@Override
	public String getHash() {
		return hash;
	}
	// Hibernate setter
	private void setHash(String hash) {
		this.hash = hash;
	}	
	@Override
	public String getStampedHash() {
		return stampedHash;
	}
	// Hibernate setter
	private void setStampedHash(String hash) {
		this.stampedHash = hash;
	}	
	@Override
	public String getHashAlgorithm() {
		return hashAlgorithm;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDocument#setAlgorithm(java.lang.String)
	 */
	@Override
	public void setHashAlgorithm(String algorithm) throws NoSuchAlgorithmException {		
		boolean calculateHash = ( this.data != null && !algorithm.equals( this.hashAlgorithm ) );
		this.hashAlgorithm = algorithm;
		if ( calculateHash ) calculateHash();
	}
	
	private String getHash( byte[] data, String algorithm ) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance( algorithm );
		byte[] digest = md.digest( data );
		return new String( Hex.encodeHex( digest ) );		
	}
	
	private void calculateHash( ) throws NoSuchAlgorithmException {
		this.hash = getHash( this.data, getHashAlgorithm() );
	}
	
	@Override
	public String getContentType() {
		return contentType;
	}
	
	// Hibernate setter
	private void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public void initKey( String decisionId ) {
		try {
			this.key = getHash( decisionId.getBytes(), "SHA-256" );
		}
		catch ( NoSuchAlgorithmException e ) { }
	}

	/* Object overrides */
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IDocument ) ) return false;
		IDocument rhs = (IDocument)obj;		
		return new EqualsBuilder()
			.append( hashAlgorithm, rhs.getHashAlgorithm() )
			.append( contentType, rhs.getContentType() )
			// data is skipped
			.append( type, rhs.getType() )
			.append( hash, rhs.getHash() )
			// id is skipped
			.append( key, rhs.getKey() )
			.append( size, rhs.getSize() )
			.isEquals();		
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.appendSuper( 7 )
			.append( hashAlgorithm )
			.append( contentType )
			.append( type )
			.append( key )
			.append( size )
			.toHashCode();
	}	
	
	@Override
	public void finalizeEntity() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public IDocument copy() {			
		Document document = (Document)SerializationUtils.clone( this );		
		document.setId( null );
		return document;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append( "Id: " + id + "\n" );
		buffer.append( "Filename: " + type + "\n" );
		buffer.append( "Content Type: " + contentType + "\n" );
		buffer.append( "Data: " + ( data == null ? "null" : "not null" ) + "\n" );
		if ( data != null ) {
			buffer.append( "Data Size: " + data.length + "\n" );
		}
		buffer.append( "Size: " + size + "\n" );
		return buffer.toString();
	}

}
