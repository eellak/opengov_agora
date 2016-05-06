package gr.opengov.agora.cms;

import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.security.IClient;
import gr.opengov.agora.util.IAdaGenerator;

import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CmsManager implements ICms {
	private static final Logger logger = LoggerFactory
			.getLogger(CmsManager.class);
	private IClient client = null;
	private IAdaGenerator adaGenerator = null;

	public IClient getClient() {
		if (client != null)
			return client;
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		return (IClient) auth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gr.opengov.agora.util.ICms#update(gr.opengov.agora.model.CmsEntity)
	 */
	@Override
	public void update( IPublicOrganizationDecision entity) {
		GregorianCalendar cal = new GregorianCalendar();
		entity.getCmsMetadata().setLastModifiedTime(cal);
	}

	@Override
	public void initialize( IPublicOrganizationDecision entity) {
		/* Initialize metadata */
		GregorianCalendar cal = new GregorianCalendar();
		entity.setId(adaGenerator.getNewId( entity ));
		if (entity.getCmsMetadata() == null)
			entity.setCmsMetadata(new CmsMetadata());
		entity.getCmsMetadata().setSubmissionTime(cal);
		entity.getCmsMetadata().setLastModifiedTime(cal);

		/* Setup some default fields */
		entity.getCmsMetadata().setDeleted(false);
		
		/* Setup some default fields */
		entity.getCmsMetadata().setCancelled(false);		

		/* User management */
		entity.getCmsMetadata().setUserDiavgeiaId(getClient().getUserId());
	}

	@Override
	public void setClient(IClient client) {
		this.client = client;
	}
	
	@Override
	public void setAdaGenerator(IAdaGenerator adaGenerator) {
		this.adaGenerator = adaGenerator;
	}
}
