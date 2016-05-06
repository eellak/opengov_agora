package gr.opengov.agora.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.PARTIAL_CONTENT )
public class ProcurementRequestNullAprovalException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ProcurementRequestNullAprovalException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProcurementRequestNullAprovalException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
