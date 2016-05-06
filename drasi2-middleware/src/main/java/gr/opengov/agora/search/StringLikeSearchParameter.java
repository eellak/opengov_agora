package gr.opengov.agora.search;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class StringLikeSearchParameter implements ISearchParameter {
	private String field;
	private String value;
	
	public StringLikeSearchParameter( String field, String value ){
		this.field = field;
		this.value = value;
	}

	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.like( field, "%" + value + "%" );			
	}	
	
	@Override
	public String toString() {
		return field + " contains: " + value;
	}
}
