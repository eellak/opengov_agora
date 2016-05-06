package gr.opengov.agora.search;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class CpvSearchParameter implements ISearchParameter {
	private String value;
	
	public CpvSearchParameter( String cpvValue ) throws InvalidSearchParameterException {
		this.value = cpvValue;		
	}

	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.eq( "cpvCodes.cpv",  value );		
	}
	
	@Override
	public String toString() {
		return "cpvCodes.cpv: " + value;
	}	
}
