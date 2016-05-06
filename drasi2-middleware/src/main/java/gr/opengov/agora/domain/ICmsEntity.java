package gr.opengov.agora.domain;

import gr.opengov.agora.util.LazyEntity;

public interface ICmsEntity extends LazyEntity {

	public abstract String getId();
	public abstract void setId(String id);
	public abstract ICmsMetadata getCmsMetadata();
	public abstract void setCmsMetadata(ICmsMetadata cmsMetadata);
	public abstract boolean isDeleted();
	public abstract void markDeleted(String reason, String deletionType, Integer deletedFromUserId);
	public abstract ICmsEntity getReplaces();
	public abstract void setReplaces( ICmsEntity replaces );
	public abstract boolean isValid();
	boolean isCancelled();
	void markCancelled(String reason, String cancellationType, Integer cancelledFromUserId);
}