package gr.opengov.agora.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Ada implements Serializable, IAda {
	private Long id;
	private String adaCode;
	private String adaType;
	
	public Ada() { }
	
	public Ada(String adaCode, String adaType) {
		this.adaCode = adaCode;
		this.adaType = adaType;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IAdaCode#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IAdaCode#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getAdaCode() {
		return adaCode;
	}
	
	@Override
	public void setAdaCode(String adaCode) {
		this.adaCode = adaCode;
	}
	
	@Override
	public String getAdaType() {
		return adaType;
	}
	
	@Override
	public void setAdaType(String adaType) {
		this.adaType = adaType;			
	}
	
	@Override
	// TODO: Change this to return a valid result depending on type.
	public boolean isDiavgeiaAda() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IAdaCode#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof IAda ) ) return false;
		IAda rhs = (IAda)obj;
		return new EqualsBuilder()
			.append( adaCode, rhs.getAdaCode() )
			.append( adaType, rhs.getAdaType())
			// id is used only for persistence, ignore
			.isEquals();
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IAdaCode#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append( adaCode )
			.append( adaType )
			.toHashCode();
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IAdaCode#finalizeEntity()
	 */
	@Override
	public void finalizeEntity() {		
	
	}
}
