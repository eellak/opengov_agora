package gr.opengov.agora.search;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class IdSearchParameter implements ISearchParameter {
	private String id;
	
	public IdSearchParameter( String id ) throws InvalidSearchParameterException {
		this.id = id;
	}


	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.idEq(id);			
	}
	
	@Override
	public String toString() {
		return "id : " + id;
	}
}
