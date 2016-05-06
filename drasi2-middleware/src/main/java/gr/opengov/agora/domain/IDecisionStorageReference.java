package gr.opengov.agora.domain;

import java.util.Calendar;
import java.util.List;

public interface IDecisionStorageReference {
	public abstract String getId();
	public abstract void setId(String id);
	public abstract String getUniqueDocumentId();
	public abstract void setUniqueDocumentId(String uniqueDocumentId);
	public abstract DecisionType getType();
	public abstract void setType(DecisionType type);
	public abstract String getHashOriginal();
	public abstract void setHashOriginal(String hashOriginal);
	public abstract String getHashStamped();
	public abstract void setHashStamped(String hashStamped);
	public abstract List<String> getEmails();
	public abstract void setEmails( List<String> emails );
	public abstract Calendar getSubmissionTimestamp();
	public abstract void setSubmissionTimestamp(Calendar timestamp);
}