package gr.opengov.agora.test.stub;

import gr.opengov.agora.security.IClient;
import gr.opengov.agora.security.IClientFactory;

public class AdminTestClientFactory implements IClientFactory {

	@Override
	public IClient getClient() {
		return new AdminUser();
	}
	
}
