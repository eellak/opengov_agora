package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR )
public class DocumentStamperNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DocumentStamperNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DocumentStamperNotFoundException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}