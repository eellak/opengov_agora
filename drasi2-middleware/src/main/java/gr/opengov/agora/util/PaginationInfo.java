package gr.opengov.agora.util;

import gr.opengov.agora.exceptions.InvalidRangeSpecifiedException;

public class PaginationInfo implements IPaginationInfo {
	private Integer from;
	private Integer limit;
	private Integer total;
	
	public PaginationInfo() {
		setFrom( 0 );
		setLimit( Integer.MAX_VALUE );
		setTotal( null );
	}
	
	public PaginationInfo( Integer from, Integer limit ) {
		setFrom( from );
		setLimit( limit );
		setTotal( null );
	}
	
	public PaginationInfo( String from, String limit ) {
		setFrom( from == null ? null : Integer.parseInt( from ) );
		setLimit( limit == null ? null : Integer.parseInt( limit ) );
		setTotal( null );
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.util.IPaginationInfo#getFrom()
	 */
	@Override
	public Integer getFrom() {
		return from;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.util.IPaginationInfo#setFrom(java.lang.Integer)
	 */
	@Override
	public void setFrom(Integer from) {
		if ( from == null ) from = 0;
		else if ( from < 0 ) throw new InvalidRangeSpecifiedException( from.toString() ); 
		this.from = from;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.util.IPaginationInfo#getLimit()
	 */
	@Override
	public Integer getLimit() {
		return limit;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.util.IPaginationInfo#setLimit(java.lang.Integer)
	 */
	@Override
	public void setLimit(Integer limit) {
		if ( limit == null ) limit = Integer.MAX_VALUE;
		else if ( limit < 0 ) throw new InvalidRangeSpecifiedException( limit.toString() );
		this.limit = limit;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.util.IPaginationInfo#getTotal()
	 */
	@Override
	public Integer getTotal() {
		return total == null ? -1 : total;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.util.IPaginationInfo#setTotal(java.lang.Integer)
	 */
	@Override
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
