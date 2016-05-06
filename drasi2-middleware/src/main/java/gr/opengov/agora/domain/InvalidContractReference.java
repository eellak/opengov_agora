package gr.opengov.agora.domain;

public class InvalidContractReference extends Contract {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isValid() {
		return false;
	}
}
