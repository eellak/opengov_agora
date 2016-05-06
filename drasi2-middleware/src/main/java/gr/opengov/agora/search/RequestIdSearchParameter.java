package gr.opengov.agora.search;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class RequestIdSearchParameter extends NumericSearchParameter implements ISearchParameter {
	
	public RequestIdSearchParameter( String requestId ) throws InvalidSearchParameterException {
		super( requestId );
	}

	//TODO: change restriction field
	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.eq( "relatedAdas",  getIntValue() );		
	}
	
	@Override
	public String toString() {
		return "contract.id: " + getIntValue();
	}	
}
