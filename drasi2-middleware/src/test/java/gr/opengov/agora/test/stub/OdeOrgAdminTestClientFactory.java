package gr.opengov.agora.test.stub;

import gr.opengov.agora.security.IClient;
import gr.opengov.agora.security.IClientFactory;

public class OdeOrgAdminTestClientFactory implements IClientFactory {

	@Override
	public IClient getClient() {
		return new OdeOrgAdminUser();
	}
}
