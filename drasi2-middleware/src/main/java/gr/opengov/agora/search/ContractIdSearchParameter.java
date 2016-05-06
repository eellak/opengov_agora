package gr.opengov.agora.search;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class ContractIdSearchParameter extends NumericSearchParameter implements ISearchParameter {
	
	public ContractIdSearchParameter( String contractId ) throws InvalidSearchParameterException {
		super( contractId );
	}

	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.eq( "contract.id",  getIntValue() );		
	}
	
	@Override
	public String toString() {
		return "contract.id: " + getIntValue();
	}	
}
