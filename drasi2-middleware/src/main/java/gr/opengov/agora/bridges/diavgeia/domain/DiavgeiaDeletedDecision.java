package gr.opengov.agora.bridges.diavgeia.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * This is awful but inheritance is messed up. Just keep a copy for now and refactor later.
 * @author xenofon
 *
 */
@Entity
@Table( name="apofaseis_deleted" )
public class DiavgeiaDeletedDecision implements IDiavgeiaDeletedDecision {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column
	private String ada;
	
	@Column( name="arithmos_protokolou")	
	private String protocolNumber;		
	
	@Column( name="apofasi_date" )
	@Temporal( TemporalType.DATE )
	private Calendar decisionDate = GregorianCalendar.getInstance();	
	
	@Column( name="eidos_apofasis" )
	private Integer typeId;
		
	@Column( name="koinopoiiseis" )
	@UnusedColumn
	private String cc = "";
	
	@Column( name="thematiki" )
	private String thematicIds;
	
	@Column( name="submission_timestamp")
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar submissionTimestamp = GregorianCalendar.getInstance();	
	
	@Column( name="comments" )
	@UnusedColumn
	private String comments = "";
	
	@Column( name="thema" )
	private String subject;
	
	@Column( name="monada" )
	private Integer unitId;
	
	@Column( name="lastlevel" )
	private Integer organizationId;
	
	@Column( name="levelsCSV" )
	@UnusedColumn
	private String levelsCSV = "";
	
	@Column( name="telikos_ypografwn" )		
	private Integer signerId;
	
	@Column( name="isET_Apofasi" )
	private boolean isPublishingHouseDecision = false;
	
	//@Column( name="is_orthi_epanalipsi" )
	@Transient
	private String isCorrectionOf = "";
	
	@Column( name="ETURL" )
	@UnusedColumn
	private String publishingHouseUrl = "";
	
	@Column( name="tags" )
	@UnusedColumn
	private String tags = "";
	
	@Column( name="user" )
	private String username;
	
	@Column( name="ET_FEK_tefxos" )
	private String fekNumber = "";
	
	@Column( name="ET_FEK" )
	private String fekIssue = "";
	
	@Column( name="ET_FEK_etos" )
	private String fekYear = "";
	
	@Column( name="status" )
	private String status;
	
	@Column( name="syntaktis_email" )
	private String issuerEmail = "";
	
	@Column( name="related_ADAs" )
	private String relatedAdas = "";	
	
	@Column( name="deletion_reason" )
	private String deletionReason;
	
	@Column( name="deletion_timestamp" )
	@Temporal( TemporalType.TIMESTAMP )
	private Calendar deletionTimestamp = GregorianCalendar.getInstance();
	
	@Column( name="deletion_user" )
	private String deletionUser;

	@Override
	public String getDeletionReason() {
		return deletionReason;
	}

	@Override
	public void setDeletionReason(String deletionReason) {
		this.deletionReason = deletionReason;
	}

	@Override
	public Calendar getDeletionTimestamp() {
		return deletionTimestamp;
	}

	@Override
	public void setDeletionTimestamp(Calendar deletionTimestamp) {
		this.deletionTimestamp = deletionTimestamp;
	}

	@Override
	public String getDeletionUser() {
		return deletionUser;
	}

	@Override
	public void setDeletionUser(String deletionUser) {
		this.deletionUser = deletionUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAda() {
		return ada;
	}

	public void setAda(String ada) {
		this.ada = ada;
	}

	public String getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public Calendar getDecisionDate() {
		return decisionDate;
	}

	public void setDecisionDate(Calendar decisionDate) {
		this.decisionDate = decisionDate;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getThematicIds() {
		return thematicIds;
	}

	public void setThematicIds(String thematicIds) {
		this.thematicIds = thematicIds;
	}

	public Calendar getSubmissionTimestamp() {
		return submissionTimestamp;
	}

	public void setSubmissionTimestamp(Calendar submissionTimestamp) {
		this.submissionTimestamp = submissionTimestamp;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getLevelsCSV() {
		return levelsCSV;
	}

	public void setLevelsCSV(String levelsCSV) {
		this.levelsCSV = levelsCSV;
	}

	public Integer getSignerId() {
		return signerId;
	}

	public void setSignerId(Integer signerId) {
		this.signerId = signerId;
	}

	public boolean isPublishingHouseDecision() {
		return isPublishingHouseDecision;
	}

	public void setPublishingHouseDecision(boolean isPublishingHouseDecision) {
		this.isPublishingHouseDecision = isPublishingHouseDecision;
	}

	public String getIsCorrectionOf() {
		return isCorrectionOf;
	}

	public void setIsCorrectionOf(String isCorrectionOf) {
		this.isCorrectionOf = isCorrectionOf;
	}

	public String getPublishingHouseUrl() {
		return publishingHouseUrl;
	}

	public void setPublishingHouseUrl(String publishingHouseUrl) {
		this.publishingHouseUrl = publishingHouseUrl;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFekNumber() {
		return fekNumber;
	}

	public void setFekNumber(String fekNumber) {
		this.fekNumber = fekNumber;
	}

	public String getFekIssue() {
		return fekIssue;
	}

	public void setFekIssue(String fekIssue) {
		this.fekIssue = fekIssue;
	}

	public String getFekYear() {
		return fekYear;
	}

	public void setFekYear(String fekYear) {
		this.fekYear = fekYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIssuerEmail() {
		return issuerEmail;
	}

	public void setIssuerEmail(String issuerEmail) {
		this.issuerEmail = issuerEmail;
	}

	public String getRelatedAdas() {
		return relatedAdas;
	}

	public void setRelatedAdas(String relatedAdas) {
		this.relatedAdas = relatedAdas;
	}
}
