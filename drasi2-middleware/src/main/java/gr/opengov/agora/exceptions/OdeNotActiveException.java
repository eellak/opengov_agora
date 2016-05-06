package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.UNAUTHORIZED )
public class OdeNotActiveException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public OdeNotActiveException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OdeNotActiveException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
