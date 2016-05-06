package gr.opengov.agora.validation;

import java.util.ArrayList;
import java.util.List;

public class Validation implements IValidation {
	private List<IValidationError> errors = new ArrayList<IValidationError>();	
	
	@Override
	public boolean isValid() {	
		return this.errors.isEmpty();
	}

	@Override
	public void addValidationError( IValidationError error ) {
		this.errors.add( error );
	}
	
	@Override
	public void addValidationErrors( IValidation validation ) {
		this.errors.addAll( validation.getErrors() );
	}
		
	@Override
	public List<IValidationError> getErrors() {
		return this.errors;
	}
	
	@Override
	public void addLocationPrefix( String prefix ) {
		for ( IValidationError error: errors ) {
			error.addLocationPrefix( prefix );
		}
	}
	
	@Override
	public boolean hasErrorCode( String code ) {
		for ( IValidationError error: errors ) {
			if ( error.getCode().equals( code ) ) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for ( IValidationError error: errors ) {
			buffer.append( error.getCode() + ":" + error.getMessage() );
		}
		return buffer.toString();
	}

}
