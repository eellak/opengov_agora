package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.FORBIDDEN )
public class AccessToOrganizationDenied extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public AccessToOrganizationDenied() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccessToOrganizationDenied(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
