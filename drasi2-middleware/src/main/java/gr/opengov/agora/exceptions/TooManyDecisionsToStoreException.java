package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.FORBIDDEN )
public class TooManyDecisionsToStoreException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public TooManyDecisionsToStoreException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TooManyDecisionsToStoreException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
