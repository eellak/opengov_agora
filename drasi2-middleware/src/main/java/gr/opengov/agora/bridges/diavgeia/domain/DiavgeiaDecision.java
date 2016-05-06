package gr.opengov.agora.bridges.diavgeia.domain;


import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This is a flat DTO because the Diavgeia database is not normalized.
 * @author xenofon
 *
 */
@Entity
@Table( name = "apofaseis" )
public class DiavgeiaDecision implements IDiavgeiaDecision {
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
	
	@Column( name="is_orthi_epanalipsi" )
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
	
	private String notNull( String str ) {
		return str == null ? "" : str;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override	
	public String getAda() {
		return ada;
	}
	
	@Override
	public void setAda(String ada) {
		this.ada = ada;
	}
	
	@Override
	public String getProtocolNumber() {
		return protocolNumber;
	}

	@Override
	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = notNull( protocolNumber );
	}
	
	@Override
	public Calendar getDecisionDate() {
		return decisionDate;
	}
	
	@Override
	public void setDecisionDate(Calendar decisionDate) {
		this.decisionDate = decisionDate;
	}
	
	@Override
	public Integer getTypeId() {
		return typeId;
	}
	
	@Override
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	@Override
	public String getThematicIds() {
		return thematicIds;
	}
	
	@Override
	public void setThematicIds(String thematicIds) {
		this.thematicIds = thematicIds;
	}

	@Override
	public Calendar getSubmissionTimestamp() {
		return submissionTimestamp;
	}
	
	@Override
	public void setSubmissionTimestamp(Calendar submissionTimestamp) {
		this.submissionTimestamp = submissionTimestamp;
	}
	
	@Override
	public String getSubject() {
		return subject;
	}
	
	@Override
	public void setSubject(String subject) {
		this.subject = notNull( subject );
	}
	
	@Override
	public Integer getUnitId() {
		return unitId;
	}
		
	@Override
	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}
	
	@Override
	public Integer getOrganizationId() {
		return organizationId;
	}
	
	@Override
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	
	@Override
	public Integer getSignerId() {
		return signerId;
	}

	@Override
	public void setSignerId(Integer signerId) {
		this.signerId = signerId;
	}
	
	@Override
	public boolean isPublishingHouseDecision() {
		return isPublishingHouseDecision;
	}
	
	@Override
	public void setPublishingHouseDecision(boolean isPublishingHouseDecision) {
		this.isPublishingHouseDecision = isPublishingHouseDecision;
	}	
	
	@Override
	public String getIsCorrectionOf() {
		return isCorrectionOf;
	}
	
	@Override
	public void setIsCorrectionOf(String isCorrectionOf) {
		this.isCorrectionOf = notNull( isCorrectionOf );
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = notNull( username );
	}
	
	@Override
	public String getIssuerEmail() {
		return issuerEmail;
	}

	@Override
	public void setIssuerEmail(String issuerEmail) {
		this.issuerEmail = notNull( issuerEmail );
	}

	@Override
	public String getRelatedAdas() {
		return relatedAdas;
	}

	@Override
	public void setRelatedAdas(String relatedAdas) {
		this.relatedAdas = notNull( relatedAdas );
	}

	@Override
	public String getFekNumber() {
		return fekNumber;
	}

	@Override
	public void setFekNumber(String fekNumber) {
		this.fekNumber = notNull( fekNumber );
	}

	@Override
	public String getFekIssue() {
		return fekIssue;
	}

	@Override
	public void setFekIssue(String fekIssue) {
		this.fekIssue = notNull( fekIssue );
	}


	@Override
	public String getFekYear() {
		return fekYear;
	}

	@Override
	public void setFekYear(String fekYear) {
		this.fekYear = notNull( fekYear );
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = notNull( status );
	}
	
	@Override
	public String getCc() {
		return cc;
	}

	@Override
	public void setCc(String cc) {
		this.cc = notNull( cc );
	}

	@Override
	public String getComments() {
		return comments;
	}

	@Override
	public void setComments(String comments) {
		this.comments = notNull( comments );
	}

	@Override
	public String getLevelsCSV() {
		return levelsCSV;
	}

	@Override
	public void setLevelsCSV(String levelsCSV) {
		this.levelsCSV = notNull( levelsCSV );
	}

	@Override
	public String getPublishingHouseUrl() {
		return publishingHouseUrl;
	}

	@Override
	public void setPublishingHouseUrl(String publishingHouseUrl) {
		this.publishingHouseUrl = notNull( publishingHouseUrl );
	}

	@Override
	public String getTags() {
		return tags;
	}

	@Override
	public void setTags(String tags) {
		this.tags = notNull( tags );
	}
	
}
