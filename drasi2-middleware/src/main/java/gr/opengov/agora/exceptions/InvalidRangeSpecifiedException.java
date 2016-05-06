package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.BAD_REQUEST )
public class InvalidRangeSpecifiedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public InvalidRangeSpecifiedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidRangeSpecifiedException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
