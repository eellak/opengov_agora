package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.NOT_FOUND )
public class DecisionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DecisionNotFoundException() {
		super("Decision not found");
		// TODO Auto-generated constructor stub
	}

	public DecisionNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
