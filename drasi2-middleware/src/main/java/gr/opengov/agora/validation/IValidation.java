package gr.opengov.agora.validation;

import java.util.List;

public interface IValidation {
	public boolean isValid();
	public void addValidationError( IValidationError error );
	public void addValidationErrors( IValidation validation );
	public List<IValidationError> getErrors();
	public void addLocationPrefix( String root );
	public boolean hasErrorCode( String code );
}