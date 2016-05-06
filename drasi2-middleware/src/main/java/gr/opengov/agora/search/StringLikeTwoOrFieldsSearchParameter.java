package gr.opengov.agora.search;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class StringLikeTwoOrFieldsSearchParameter implements ISearchParameter {
	private String fieldA;
	private String fieldB;
	private String value;
	
	public StringLikeTwoOrFieldsSearchParameter( String fieldA, String fieldB, String value ){
		this.fieldA = fieldA;
		this.fieldB = fieldB;
		this.value = value;
	}

	@Override
	public Criterion toHibernateCriterion() {
		return Restrictions.or( Restrictions.like( fieldA, "%" + value + "%" ), Restrictions.like( fieldB, "%" + value + "%" ) );
	}	
	
	@Override
	public String toString() {
		return fieldA + " contains: " + value +" or " + fieldB + " contains: " + value ;
	}
}
