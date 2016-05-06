package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.NOT_FOUND )
public class DecisionReferenceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DecisionReferenceNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DecisionReferenceNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
