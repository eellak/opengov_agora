package gr.opengov.agora.mail;

import gr.opengov.agora.domain.IDecisionStorageReference;

import java.util.List;

public interface IDecisionMailer {

	/**
	 * Send a single mail for all email generating events
	 * @param generators
	 * @return 
	 */
	
	public abstract void sendMailSaved( List<IDecisionStorageReference> storedDecisions );
	public abstract void sendMailDeleted( IDecisionStorageReference deletedDecision );
	public abstract void sendMailUpdated(IDecisionStorageReference deletedDecision);
}