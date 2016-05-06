package gr.opengov.agora.test.util;

import org.apache.http.HttpResponse;

public class HttpStatusException extends Exception {
	private HttpResponse response;
	
	public HttpStatusException( HttpResponse response ) {
		super( "Http Code: " + response.getStatusLine().getStatusCode() );
		this.response = response;
	}
	
	public HttpResponse getResponse() {
		return response;
	}
}
