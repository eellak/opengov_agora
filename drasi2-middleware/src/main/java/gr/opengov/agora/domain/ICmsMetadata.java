package gr.opengov.agora.domain;

import gr.opengov.agora.cms.ITaxonomyReference;
import gr.opengov.agora.util.LazyEntity;

import java.util.Calendar;

public interface ICmsMetadata extends LazyEntity {

	public abstract Integer getUserDiavgeiaId();

	public abstract void setUserDiavgeiaId(Integer userId);

	public abstract Calendar getSubmissionTime();

	public abstract void setSubmissionTime(Calendar submissionTime);

	public abstract Calendar getLastModifiedTime();

	public abstract void setLastModifiedTime(Calendar lastModifiedTime);

	public abstract Boolean getDeleted();

	public abstract void setDeleted(Boolean deleted);

	public abstract ICmsMetadata copy();

	public abstract String getDeletedReason();

	public abstract void setDeletedReason(String deletedReason);

	public abstract Integer getDeletedFromUserId();

	public abstract void setDeletedFromUserId(Integer deletedFromUserId);

	public abstract Calendar getDeletedTime();

	public abstract void setDeletedTime(Calendar deletedTime);

	public ITaxonomyReference getDeletionTypeTaxonomyReference();

	public String getDeletionTypeIdRef();

	public void setDeletionTypeIdRef(String deletionTypeIdRef);

	void setCancelled(Boolean cancelled);

	String getCancelledReason();

	void setCancelledReason(String cancelledReason);

	ITaxonomyReference getCancellationTypeTaxonomyReference();

	String getCancellationTypeIdRef();

	void setCancellationTypeIdRef(String cancellationTypeIdRef);

	Integer getCancelledFromUserId();

	void setCancelledFromUserId(Integer cancelledFromUserId);

	Calendar getCancelledTime();

	void setCancelledTime(Calendar cancelledTime);

	Boolean getCancelled();

}