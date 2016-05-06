package gr.opengov.agora.search;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IdLikeSearchParameter implements ISearchParameter {
	private String id;
	
	public IdLikeSearchParameter( String id ) throws InvalidSearchParameterException {
		this.id = id;
	}

	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.like( "id", "%" + id + "%" );			
	}
	
	@Override
	public String toString() {
		return "id contains: " + id;
	}
}
