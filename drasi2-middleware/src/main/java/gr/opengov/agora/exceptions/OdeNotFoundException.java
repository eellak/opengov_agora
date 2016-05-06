package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.UNAUTHORIZED )
public class OdeNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public OdeNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OdeNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
