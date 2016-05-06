package gr.opengov.agora.search;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class OrganizationSearchParameter extends NumericSearchParameter implements ISearchParameter {
	public OrganizationSearchParameter( String organizationId ) throws InvalidSearchParameterException {
		super( organizationId );
	}

	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.eq( "organizationDiavgeiaId",  getIntValue() );		
	}
	
	@Override
	public String toString() {
		return "organizationDiavgeiaId: " + getIntValue();
	}
}
