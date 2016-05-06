package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.cms.TaxonomyReference;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CmsMetadata implements Serializable, ICmsMetadata {
	private Long id;	
	private Integer userDiavgeiaId;
	private Calendar submissionTime;
	private Calendar lastModifiedTime;
	private Boolean deleted = false;
	private String deletedReason;
	private String deletionTypeIdRef;
	private Integer deletedFromUserId;
	private Calendar deletedTime;
	
	private Boolean cancelled = false;
	private String cancelledReason;
	private String cancellationTypeIdRef;
	private Integer cancelledFromUserId;
	private Calendar cancelledTime;	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#getUserDiavgeiaId()
	 */
	@Override
	public Integer getUserDiavgeiaId() {
		return userDiavgeiaId;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#setUserDiavgeiaId(java.lang.Integer)
	 */
	@Override
	public void setUserDiavgeiaId(Integer userId) {
		this.userDiavgeiaId = userId;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#getReplaces()
	 */
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#getSubmissionTime()
	 */
	@Override
	public Calendar getSubmissionTime() {
		return submissionTime;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#setSubmissionTime(java.util.Calendar)
	 */
	@Override
	public void setSubmissionTime(Calendar submissionTime) {
		this.submissionTime = submissionTime;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#getLastModifiedTime()
	 */
	@Override
	public Calendar getLastModifiedTime() {
		return lastModifiedTime;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#setLastModifiedTime(java.util.Calendar)
	 */
	@Override
	public void setLastModifiedTime(Calendar lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#getDeleted()
	 */
	@Override
	public Boolean getDeleted() {
		return deleted;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsMetadata#setDeleted(java.lang.Boolean)
	 */
	@Override
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	@Override
	public String getDeletedReason() {
		return deletedReason;
	}
	
	@Override
	public void setDeletedReason(String deletedReason) {
		this.deletedReason = deletedReason;
	}
	
	@Override
	public ITaxonomyReference getDeletionTypeTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getDeletionTypeIdRef();
			}
		};
	}			
	
	@Override
	public String getDeletionTypeIdRef() {
		return deletionTypeIdRef;
	}
	
	@Override
	public void setDeletionTypeIdRef(String deletionTypeIdRef) {
		this.deletionTypeIdRef = deletionTypeIdRef;
	}	
	
	@Override
	public Integer getDeletedFromUserId() {
		return deletedFromUserId;
	}
	
	@Override
	public void setDeletedFromUserId(Integer deletedFromUserId) {
		this.deletedFromUserId = deletedFromUserId;
	}
	
	@Override
	public Calendar getDeletedTime() {
		return deletedTime;
	}
	
	@Override
	public void setDeletedTime(Calendar deletedTime) {
		this.deletedTime = deletedTime;
	}
	
	@Override
	public Boolean getCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	@Override
	public String getCancelledReason() {
		return cancelledReason;
	}
	
	@Override
	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}
	
	@Override
	public ITaxonomyReference getCancellationTypeTaxonomyReference() {
		return new TaxonomyReference() {
			@Override
			public String getIdRef() {
				return getCancellationTypeIdRef();
			}
		};
	}			
	
	@Override
	public String getCancellationTypeIdRef() {
		return cancellationTypeIdRef;
	}
	
	@Override
	public void setCancellationTypeIdRef(String cancellationTypeIdRef) {
		this.cancellationTypeIdRef = cancellationTypeIdRef;
	}	
	
	@Override
	public Integer getCancelledFromUserId() {
		return cancelledFromUserId;
	}
	
	@Override
	public void setCancelledFromUserId(Integer cancelledFromUserId) {
		this.cancelledFromUserId = cancelledFromUserId;
	}
	
	@Override
	public Calendar getCancelledTime() {
		return cancelledTime;
	}
	
	@Override
	public void setCancelledTime(Calendar cancelledTime) {
		this.cancelledTime = cancelledTime;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof ICmsMetadata ) ) return false;
		ICmsMetadata rhs = (ICmsMetadata)obj;
		return new EqualsBuilder()
			// id is used only for persistence, ignore
			.append( lastModifiedTime, rhs.getLastModifiedTime() )
			.append( submissionTime, rhs.getSubmissionTime() )
			.append( userDiavgeiaId, rhs.getUserDiavgeiaId() )
			.append( deleted, rhs.getDeleted() )
			.append( deletedReason, rhs.getDeletedReason() )
			.append( deletedFromUserId, rhs.getDeletedFromUserId() )
			.append( deletedTime, rhs.getDeletedTime() )
			.append( cancelled, rhs.getCancelled() )
			.append( cancelledReason, rhs.getCancelledReason() )
			.append( cancelledFromUserId, rhs.getCancelledFromUserId() )
			.append( cancelledTime, rhs.getCancelledTime() )			
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(23,43)
			.append( lastModifiedTime )
			.append( submissionTime )
			.append( userDiavgeiaId )
			.append( deleted )			
			.append( deletedReason )
			.append( deletedFromUserId )
			.append( deletedTime )			
			.append( cancelled )			
			.append( cancelledReason )
			.append( cancelledFromUserId )
			.append( cancelledTime )						
			.toHashCode();
	}
	/*
	@Override
	public ICmsMetadata copy() {
		ICmsMetadata copy = new CmsMetadata();
		copyTo( copy );
		return copy;
	}
	
	@Override
	public void copyTo(ICmsMetadata dest) {	
		dest.setDeleted( deleted );
		dest.setLastModifiedTime(lastModifiedTime);
		
	}	
	*/
	@Override
	public void finalizeEntity() {	
		
	}
	@Override
	public ICmsMetadata copy() {
		CmsMetadata obj = (CmsMetadata)SerializationUtils.clone( this );
		obj.setId( null );
		return obj;
	}	

}
