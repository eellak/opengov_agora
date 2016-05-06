package gr.opengov.agora.domain;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CmsEntity implements Serializable, ICmsEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private ICmsEntity replaces;
	private ICmsMetadata cmsMetadata;
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsEntity#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsEntity#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsEntity#getCmsMetadata()
	 */
	@Override
	public ICmsMetadata getCmsMetadata() {
		return cmsMetadata;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.ICmsEntity#setCmsMetadata(gr.opengov.agora.domain.CmsMetadata)
	 */
	@Override
	public void setCmsMetadata(ICmsMetadata cmsMetadata) {
		this.cmsMetadata = cmsMetadata;
	}
	
	@Override
	public boolean isDeleted() {
		return cmsMetadata.getDeleted();
	}
	
	@Override
	public boolean isCancelled() {
		return cmsMetadata.getCancelled();
	}	
	
	@Override
	public void markDeleted(String reason, String deletionType, Integer deletedFromUserId) {
		cmsMetadata.setDeleted( true );
		cmsMetadata.setDeletedReason(reason);
		cmsMetadata.setDeletionTypeIdRef(deletionType);
		cmsMetadata.setDeletedFromUserId(deletedFromUserId);
		cmsMetadata.setDeletedTime(Calendar.getInstance());
	}
	
	@Override
	public void markCancelled(String reason, String cancellationType, Integer cancelledFromUserId) {
		cmsMetadata.setCancelled( true );
		cmsMetadata.setCancelledReason(reason);
		cmsMetadata.setCancellationTypeIdRef(cancellationType);
		cmsMetadata.setCancelledFromUserId(cancelledFromUserId);
		cmsMetadata.setCancelledTime(Calendar.getInstance());
	}	
	
	@Override
	public ICmsEntity getReplaces() {
		return replaces;
	}
	
	@Override
	public void setReplaces(ICmsEntity replaces) {
		this.replaces = replaces;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof ICmsEntity ) ) return false;
		ICmsEntity rhs = (ICmsEntity)obj;
		return new EqualsBuilder()
//			.append( id, rhs.getId() )
			.append( cmsMetadata, rhs.getCmsMetadata() )
			.append( replaces, rhs.getReplaces() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder( )
//				.append( id )
				.append( cmsMetadata )
				.append( replaces )
				.toHashCode();
	}
	@Override
	public void finalizeEntity() {
		if ( getCmsMetadata() != null)
			getCmsMetadata().finalizeEntity();
		if ( getReplaces() != null ) {
			getReplaces().getId();
			getReplaces().getCmsMetadata();			
		}
	}
}
