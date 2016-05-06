package gr.opengov.agora.test.util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class AgoraTestResource {
	private Integer id;
	private String decision;
	private String xml;
	private String comments;
	private Boolean serviceSaveSingleIgnoreAccess;
	private Boolean serviceSaveListIgnoreAccess;
	private Boolean serviceUpdateSingleIgnoreAccess;
	private Boolean serviceSaveAndGetDocument;
	private Boolean servicePurgeSingleStoredDecision;
	private Boolean serviceDeleteSingleStoredDecision;	
	private Boolean serviceSearchDecisions;

	public AgoraTestResource(
								Integer id,
								String decision,
								String xml,
								String comments,
								Boolean serviceSaveSingleIgnoreAccess,
								Boolean serviceSaveListIgnoreAccess,
								Boolean serviceUpdateSingleIgnoreAccess,
								Boolean serviceSaveAndGetDocument,
								Boolean servicePurgeSingleStoredDecision,
								Boolean serviceDeleteSingleStoredDecision,	
								Boolean serviceSearchDecisions			
							){
		this.id = id;
		this.decision = decision;
		this.xml = xml;
		this.comments = comments;
		this.serviceSaveSingleIgnoreAccess = serviceSaveSingleIgnoreAccess;
		this.serviceSaveListIgnoreAccess = serviceSaveListIgnoreAccess;
		this.serviceUpdateSingleIgnoreAccess = serviceUpdateSingleIgnoreAccess;
		this.serviceSaveAndGetDocument = serviceSaveAndGetDocument;
		this.servicePurgeSingleStoredDecision = servicePurgeSingleStoredDecision;
		this.serviceDeleteSingleStoredDecision = serviceDeleteSingleStoredDecision;
		this.serviceSearchDecisions = serviceSearchDecisions;		
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getServiceSaveSingleIgnoreAccess() {
		return serviceSaveSingleIgnoreAccess;
	}

	public void setServiceSaveSingleIgnoreAccess(
			Boolean serviceSaveSingleIgnoreAccess) {
		this.serviceSaveSingleIgnoreAccess = serviceSaveSingleIgnoreAccess;
	}

	public Boolean getServiceSaveListIgnoreAccess() {
		return serviceSaveListIgnoreAccess;
	}

	public void setServiceSaveListIgnoreAccess(Boolean serviceSaveListIgnoreAccess) {
		this.serviceSaveListIgnoreAccess = serviceSaveListIgnoreAccess;
	}

	public Boolean getServiceUpdateSingleIgnoreAccess() {
		return serviceUpdateSingleIgnoreAccess;
	}

	public void setServiceUpdateSingleIgnoreAccess(
			Boolean serviceUpdateSingleIgnoreAccess) {
		this.serviceUpdateSingleIgnoreAccess = serviceUpdateSingleIgnoreAccess;
	}

	public Boolean getServiceSaveAndGetDocument() {
		return serviceSaveAndGetDocument;
	}

	public void setServiceSaveAndGetDocument(Boolean serviceSaveAndGetDocument) {
		this.serviceSaveAndGetDocument = serviceSaveAndGetDocument;
	}

	public Boolean getServicePurgeSingleStoredDecision() {
		return servicePurgeSingleStoredDecision;
	}

	public void setServicePurgeSingleStoredDecision(
			Boolean servicePurgeSingleStoredDecision) {
		this.servicePurgeSingleStoredDecision = servicePurgeSingleStoredDecision;
	}

	public Boolean getServiceDeleteSingleStoredDecision() {
		return serviceDeleteSingleStoredDecision;
	}

	public void setServiceDeleteSingleStoredDecision(
			Boolean serviceDeleteSingleStoredDecision) {
		this.serviceDeleteSingleStoredDecision = serviceDeleteSingleStoredDecision;
	}

	public Boolean getServiceSearchDecisions() {
		return serviceSearchDecisions;
	}

	public void setServiceSearchDecisions(Boolean serviceSearchDecisions) {
		this.serviceSearchDecisions = serviceSearchDecisions;
	}	
	

	
}
