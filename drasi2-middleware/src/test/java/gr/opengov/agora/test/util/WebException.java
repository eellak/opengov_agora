package gr.opengov.agora.test.util;

/* This is required because HttpException in httpunit has protected constructors... */
public class WebException extends Exception {
	private int responseCode;
	
	public WebException( int responseCode ) {
		this.responseCode = responseCode;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
}
