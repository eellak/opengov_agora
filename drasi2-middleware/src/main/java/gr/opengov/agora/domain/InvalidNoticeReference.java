package gr.opengov.agora.domain;

public class InvalidNoticeReference extends Notice {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isValid() {
		return false;
	}
}
