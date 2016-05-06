package gr.opengov.agora.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Cpv implements Serializable, ICpv {
	private static final long serialVersionUID = 1L;

	private Long id;
//	private IContractItem contractItem;
	private String cpvCode;
	
	public Cpv() { }
	
	public Cpv(String cpvCode) {
		this.cpvCode = cpvCode;
	}

	@Override
	public String getCpvCode() {
		return cpvCode;
	}

	@Override
	public void setCpvCode(String cpvCode) {
		this.cpvCode = cpvCode;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

//	@Override
//	public IContractItem getContractItem() {
//		return contractItem;
//	}
//
//	@Override
//	public void setContractItem(IContractItem contractItem) {
//		this.contractItem = contractItem;
//	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		if ( ! ( obj instanceof ICpv ) ) return false;
		ICpv rhs = (ICpv)obj;
		return new EqualsBuilder()
//			.append( contractItem, rhs.getContractItem() )
			.append( cpvCode, rhs.getCpvCode() )
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
//			.append( contractItem )
			.append( cpvCode )
			.toHashCode();
	}

	@Override
	public void finalizeEntity() {
//		contractItem.finalizeEntity();
	}
}
