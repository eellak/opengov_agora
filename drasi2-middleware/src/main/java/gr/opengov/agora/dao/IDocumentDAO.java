package gr.opengov.agora.dao;

import gr.opengov.agora.domain.IDocument;

import java.io.InputStream;

public interface IDocumentDAO {
	//public abstract void save( IDocument document, IDocument stamped ) throws Exception;
	public abstract void stampAndSave( IDocument document, String stamp ) throws Exception;
	public abstract InputStream getInputStream( IDocument document, boolean useStamped ) throws Exception;
	public abstract void delete( IDocument document ) throws Exception;
}