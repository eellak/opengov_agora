package gr.opengov.agora.domain;

public class InvalidProcurementRequestReference extends ProcurementRequest {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isValid() {
		return false;
	}
}
