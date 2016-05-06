package gr.opengov.agora.search;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class ApprovedFilterSearchParameter implements ISearchParameter {
	private String field;
	private String value;
	
	public ApprovedFilterSearchParameter( String field, String value ){
		this.field = field;
		this.value = value;
	}

	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.eq( field,  value );	
	}
	
	@Override
	public String toString() {
		return field + ": " + value;
	}
}
