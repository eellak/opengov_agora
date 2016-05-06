package gr.opengov.agora.test.util;

import com.meterware.httpunit.HeaderOnlyWebRequest;

public class DeleteMethodWebRequest extends HeaderOnlyWebRequest {

	protected DeleteMethodWebRequest(String urlString) {
		super(urlString);
		setMethod("DELETE");
	}
}
