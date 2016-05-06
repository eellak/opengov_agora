package gr.opengov.agora.dao;

import gr.opengov.agora.domain.IDocument;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentFilesystemDAO implements IDocumentDAO {
	private static final Logger logger = LoggerFactory.getLogger(DocumentFilesystemDAO.class);
	
	private String rootPath = "/tmp/files";
	private int chunkSize = 2;
	private int pathDepth = 3;

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	
	private String getRootPath() {
		return rootPath;
	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	public void setPathDepth(int pathDepth) {
		this.pathDepth = pathDepth;
	}

	private String getDirectory( IDocument document ) {
		String key = document.getKey();
		int keyLength = key.length();		
		StringBuilder builder = new StringBuilder( getRootPath() );
		for ( int i = 0; i < this.pathDepth; i++ ) {
			if ( i + this.chunkSize > keyLength ) break;
			String chunk = key.substring( i, i + this.chunkSize );
			builder.append( "/" + chunk );			
		}
		return builder.toString();
	}
	
	private void saveFile( String filename, byte[] data ) throws IOException {
		File file = new File( filename );
		if ( file.isFile() ) file.delete();
		BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream( filename ) );
		IOUtil.copy( data, bos );
		bos.close();
	}
	
	@Override
	public void stampAndSave( IDocument document, String stamp ) throws Exception { 
		String directory = getDirectory( document );		
		try {			
			FileUtils.forceMkdir( new File( directory ) );			
		}
		catch ( Exception e ) { }
		String pathOriginal = directory + "/" + document.getKey() + ".original";		
		IDocument signed = document.getStampedDocument( stamp );
		String pathSigned = directory + "/" + document.getKey() + ".stamped";
		saveFile( pathOriginal, document.getData() );
		saveFile( pathSigned, signed.getData() );
	}		

	@Override
	public InputStream getInputStream(IDocument document, boolean useStamped) throws FileNotFoundException {
		String suffix = useStamped ? ".stamped" : ".original";
		String path = getDirectory( document ) + "/" + document.getKey() + suffix;
		logger.debug( "Retrieving file: " + path );
		return new FileInputStream( path );		
	}

	@Override
	public void delete(IDocument document) throws Exception {
		String pathOriginal = getDirectory( document ) + "/" + document.getKey() + ".original";
		String pathSigned = getDirectory( document ) + "/" + document.getKey() + ".stamped";
		File file = new File( pathOriginal );
		if ( file.exists() ) file.delete();
		file = new File( pathSigned );
		if ( file.exists() ) file.delete();
	}

}
