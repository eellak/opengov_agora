package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.FORBIDDEN )
public class ForbiddenOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ForbiddenOperationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForbiddenOperationException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
