package gr.opengov.agora.search;

import gr.opengov.agora.exceptions.InvalidSearchParameterException;

public abstract class NumericSearchParameter implements ISearchParameter {
	private Integer intValue;
	public NumericSearchParameter( String value ) throws InvalidSearchParameterException {
		try {
			intValue = Integer.parseInt( value );
		}
		catch ( NumberFormatException e ) {
			throw new InvalidSearchParameterException( e.getMessage() );
		}
	}
	
	public Integer getIntValue() {
		return intValue;
	}
}
