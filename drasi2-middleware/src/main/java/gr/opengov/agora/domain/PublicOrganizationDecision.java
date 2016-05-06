package gr.opengov.agora.domain;

import gr.opengov.agora.security.IClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicOrganizationDecision extends CmsEntity implements Serializable, IPublicOrganizationDecision {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String uniqueDocumentCode;
	private String protocolNumberCode;
	private Integer organizationDiavgeiaId;
	private Integer unitDiavgeiaId;
	private List<Integer> signersDiavgeiaIds;
	private IDocument document;
	private Boolean diavgeiaPublished;
	private Calendar dateSigned;
	private List<Ada> relatedAdas;
	private String issuerEmail;
	private static final Logger logger = LoggerFactory.getLogger(PublicOrganizationDecision.class);
	
	@Override
	public DecisionType getDecisionType() {
		return DecisionType.NONE;
	}
	
	@Override
	public Integer getDiavgeiaType() {
		return null;
	}
	
	@Override
	public IDecisionStorageReference getStorageReference( IClient client ) {
		IDecisionStorageReference ref = new DecisionStorageReference( getId(), getUniqueDocumentCode(), getDecisionType() );
		ref.setHashOriginal( getDocument().getHash() );
		ref.setHashStamped( getDocument().getStampedHash() );
		ref.setSubmissionTimestamp( getCmsMetadata().getSubmissionTime() );
		List<String> emails = new ArrayList<String>();
		if ( getIssuerEmail() != null && getIssuerEmail().length() > 0 ) {
			emails.add( getIssuerEmail() );
		}
		ref.setEmails( emails );
		if ( client != null ) {
			if ( client.getEmail() != null && client.getEmail().length() > 0 ) {
				emails.add( client.getEmail() );
			}
		}
		return ref;
	}
	
	@Override
	public List<IContractItem> getContractItems(){
		//do nothing. overriden by Contract, Notice, ProcurementRequest
		logger.debug("show contract items");
		System.out.println("showing contract items");
		return Collections.emptyList();
	}
	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public IDocument getDocument() {
		return document;
	}
	
	@Override
	public void setDocument(IDocument document) {
		this.document = document;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IPublicOrganizationDecision#getUniqueDocumentCode()
	 */
	@Override
	public String getUniqueDocumentCode() {
		return uniqueDocumentCode;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IPublicOrganizationDecision#setUniqueDocumentCode(java.lang.String)
	 */
	@Override
	public void setUniqueDocumentCode(String uniqueDocumentCode) {
		this.uniqueDocumentCode = uniqueDocumentCode;
	}
	
	@Override
	public String getProtocolNumberCode() {
		return protocolNumberCode;
	}

	@Override
	public void setProtocolNumberCode(String protocolNumberCode) {
		this.protocolNumberCode = protocolNumberCode;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IPublicOrganizationDecision#getOrganizationDiavgeiaId()
	 */
	@Override
	public Integer getOrganizationDiavgeiaId() {
		return organizationDiavgeiaId;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IPublicOrganizationDecision#setOrganizationDiavgeiaId(java.lang.Integer)
	 */
	@Override
	public void setOrganizationDiavgeiaId(Integer organizationDiavgeiaId) {
		this.organizationDiavgeiaId = organizationDiavgeiaId;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IPublicOrganizationDecision#getUnitDiavgeiaId()
	 */
	@Override
	public Integer getUnitDiavgeiaId() {
		return unitDiavgeiaId;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IPublicOrganizationDecision#setUnitDiavgeiaId(java.lang.Integer)
	 */
	@Override
	public void setUnitDiavgeiaId(Integer unitDiavgeiaId) {
		this.unitDiavgeiaId = unitDiavgeiaId;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IPublicOrganizationDecision#getSignersDiavgeiaIds()
	 */
	@Override
	public List<Integer> getSignersDiavgeiaIds() {
		return signersDiavgeiaIds;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IPublicOrganizationDecision#setSignersDiavgeiaIds(java.util.List)
	 */
	@Override
	public void setSignersDiavgeiaIds(List<Integer> signersDiavgeiaIds) {
		this.signersDiavgeiaIds = signersDiavgeiaIds;
	}
	
	@Override
	public Boolean isDiavgeiaPublished() {
		return diavgeiaPublished;
	}

	@Override
	public void setDiavgeiaPublished(Boolean diavgeiaPublished) {
		this.diavgeiaPublished = diavgeiaPublished;		
	}

	@Override
	public Calendar getDateSigned() {
		return dateSigned;
	}

	@Override
	public void setDateSigned(Calendar dateSigned) {
		this.dateSigned = dateSigned;
	}

	@Override
	public List<Ada> getRelatedAdas() {
		return relatedAdas;
	}

	@Override
	public void setRelatedAdas(List<Ada> relatedAdas) {
		this.relatedAdas = relatedAdas;
	}

	@Override
	public String getIssuerEmail() {
		return issuerEmail;
	}

	@Override
	public void setIssuerEmail(String issuerEmail) {
		this.issuerEmail = issuerEmail;
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IPublicOrganizationDecision ) ) return false;
		IPublicOrganizationDecision rhs = (IPublicOrganizationDecision)obj;
		return new EqualsBuilder()
			.appendSuper( super.equals( obj ) )
			.append( title, rhs.getTitle() )
			.append( organizationDiavgeiaId, rhs.getOrganizationDiavgeiaId() )
			.append( signersDiavgeiaIds, rhs.getSignersDiavgeiaIds() )
			.append( uniqueDocumentCode, rhs.getUniqueDocumentCode() )
			.append( protocolNumberCode, rhs.getProtocolNumberCode() )
			.append( unitDiavgeiaId, rhs.getUnitDiavgeiaId() )
			.append( document, rhs.getDocument() )
			.append( diavgeiaPublished, rhs.isDiavgeiaPublished() )
			.append( dateSigned, rhs.getDateSigned() )
			.append( relatedAdas, rhs.getRelatedAdas() )
			.append( issuerEmail, rhs.getIssuerEmail() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.appendSuper( 17 )
			.append( title )
			.append( organizationDiavgeiaId )
			.append( signersDiavgeiaIds )
			.append( uniqueDocumentCode )
			.append( protocolNumberCode )
			.append( unitDiavgeiaId )
			.append( document )
			.append( diavgeiaPublished )
			.append( dateSigned )
			.append( relatedAdas )
			.append( issuerEmail )
			.toHashCode();
	}
	
	@Override
	public void finalizeEntity() {
//		logger.debug( "Public organization decision, finalizing..." );
//		logger.debug(((Integer)System.identityHashCode(this)).toString());
		super.finalizeEntity();
		if (getDocument() != null)
			getDocument().finalizeEntity();
		if (getSignersDiavgeiaIds() != null)
			for ( Integer id: getSignersDiavgeiaIds() ) { id.byteValue();}	
		if (getRelatedAdas() != null)
			for ( Ada adaCode: getRelatedAdas() ) { adaCode.finalizeEntity();}
	}

}