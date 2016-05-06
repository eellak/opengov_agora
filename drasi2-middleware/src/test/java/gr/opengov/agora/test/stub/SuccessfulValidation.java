package gr.opengov.agora.test.stub;

import java.util.ArrayList;
import java.util.List;

import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.IValidationError;

public class SuccessfulValidation implements IValidation {

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void addValidationError(IValidationError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addValidationErrors(IValidation validation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IValidationError> getErrors() {
		return new ArrayList<IValidationError>();
	}

	@Override
	public void addLocationPrefix(String root) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasErrorCode(String code) {
		// TODO Auto-generated method stub
		return false;
	}

}
