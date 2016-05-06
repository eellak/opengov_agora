package gr.opengov.agora.domain;

import gr.opengov.agora.util.IDocumentStamper;
import gr.opengov.agora.util.LazyEntity;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

public interface IDocument extends LazyEntity {

	public abstract String getKey();
	public abstract void initKey( String decisionId );
	public abstract void copyKeyFrom( IDocument document );

	public abstract Integer getSize();
	public abstract void setSize(Integer size);

	public abstract Integer getStampedSize();
	public abstract void setStampedSize(Integer size);
	
	public abstract String getStampedHash();
	public abstract String getHash();	
	public abstract String getHashAlgorithm();
	public abstract void setHashAlgorithm(String algorithm) throws NoSuchAlgorithmException;
	
	public abstract String getType();
	
	public abstract void setType( String type );

	public abstract byte[] getData();
	
	public void setData( byte[] data );
	
	public abstract String getContentType();
		
	public abstract IDocument copy();

	public abstract IDocumentStamper getStamper();
	public abstract String getStamp();
	
	/**
	 * Return a stamped version of this document. Set the stamp in this document as 
	 * well, so it can be persisted.
	 */
	public abstract IDocument getStampedDocument( String stamp );
}