package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
public class InvalidDiavgeiaDecisionTypeException extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidDiavgeiaDecisionTypeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidDiavgeiaDecisionTypeException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
