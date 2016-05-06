package gr.opengov.agora.bridges.diavgeia.domain;

import java.util.Calendar;

public interface IDiavgeiaDeletedDecision {

	public abstract Integer getId();

	public abstract void setId(Integer id);

	public abstract String getAda();

	public abstract void setAda(String ada);

	public abstract String getProtocolNumber();

	public abstract void setProtocolNumber(String protocolNumber);

	public abstract Calendar getDecisionDate();

	public abstract void setDecisionDate(Calendar decisionDate);

	public abstract Integer getTypeId();

	public abstract void setTypeId(Integer typeId);

	public abstract String getThematicIds();

	public abstract void setThematicIds(String thematicIds);

	public abstract Calendar getSubmissionTimestamp();

	public abstract void setSubmissionTimestamp(Calendar submissionTimestamp);

	public abstract String getSubject();

	public abstract void setSubject(String subject);

	public abstract Integer getUnitId();

	public abstract void setUnitId(Integer unitId);

	public abstract Integer getOrganizationId();

	public abstract void setOrganizationId(Integer organizationId);

	public abstract Integer getSignerId();

	public abstract void setSignerId(Integer signerId);

	public abstract boolean isPublishingHouseDecision();

	public abstract void setPublishingHouseDecision(
			boolean isPublishingHouseDecision);

	public abstract String getIsCorrectionOf();

	public abstract void setIsCorrectionOf(String isCorrectionOf);

	public abstract String getUsername();

	public abstract void setUsername(String username);

	public abstract String getIssuerEmail();

	public abstract void setIssuerEmail(String issuerEmail);

	public abstract String getRelatedAdas();

	public abstract void setRelatedAdas(String relatedAdas);

	public abstract String getFekNumber();

	public abstract void setFekNumber(String fekNumber);

	public abstract String getFekIssue();

	public abstract void setFekIssue(String fekIssue);

	public abstract String getFekYear();

	public abstract void setFekYear(String fekYear);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract String getCc();

	public abstract void setCc(String cc);

	public abstract String getComments();

	public abstract void setComments(String comments);

	public abstract String getLevelsCSV();

	public abstract void setLevelsCSV(String levelsCSV);

	public abstract String getPublishingHouseUrl();

	public abstract void setPublishingHouseUrl(String publishingHouseUrl);

	public abstract String getTags();

	public abstract void setTags(String tags);
	
	public abstract String getDeletionReason();

	public abstract void setDeletionReason(String deletionReason);

	public abstract Calendar getDeletionTimestamp();

	public abstract void setDeletionTimestamp(Calendar deletionTimestamp);

	public abstract String getDeletionUser();

	public abstract void setDeletionUser(String deletionUser);

}