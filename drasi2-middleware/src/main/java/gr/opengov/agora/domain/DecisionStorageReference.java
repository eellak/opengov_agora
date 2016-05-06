package gr.opengov.agora.domain;

import java.util.Calendar;
import java.util.List;

public class DecisionStorageReference implements IDecisionStorageReference {
	private String id;
	private String uniqueDocumentId;
	private DecisionType type;
	private String hashOriginal;
	private String hashStamped;
	private Calendar submissionTimestamp;
	private List<String> emails;
	
	public DecisionStorageReference( String id, String uniqueDocumentId, DecisionType type ) {
		this.id = id;
		this.uniqueDocumentId = uniqueDocumentId;
		this.type = type;
	}
		
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDecisionStorageReference#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDecisionStorageReference#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDecisionStorageReference#getUniqueDocumentId()
	 */
	@Override
	public String getUniqueDocumentId() {
		return uniqueDocumentId;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IDecisionStorageReference#setUniqueDocumentId(java.lang.String)
	 */
	@Override
	public void setUniqueDocumentId(String uniqueDocumentId) {
		this.uniqueDocumentId = uniqueDocumentId;
	}

	@Override
	public DecisionType getType() {
		return type;
	}

	@Override
	public void setType(DecisionType type) {
		this.type = type;
	}

	@Override
	public String getHashOriginal() {
		return hashOriginal;
	}

	@Override
	public void setHashOriginal(String hashOriginal) {
		this.hashOriginal = hashOriginal;
	}

	@Override
	public String getHashStamped() {
		return hashStamped;
	}

	@Override
	public void setHashStamped(String hashStamped) {
		this.hashStamped = hashStamped;
	}

	@Override
	public List<String> getEmails() {
		return emails;
	}

	@Override
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	@Override
	public Calendar getSubmissionTimestamp() {
		return submissionTimestamp;
	}

	@Override
	public void setSubmissionTimestamp(Calendar timestamp) {
		this.submissionTimestamp = (Calendar)timestamp.clone();
	}		
}
