package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.BAD_REQUEST )
public class InvalidIdSpecifiedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidIdSpecifiedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidIdSpecifiedException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
