package gr.opengov.agora.validation;

public class ValidationError implements IValidationError {
	private String code;
	private String message;
	private String location;
	
	public ValidationError( String code, String message, String location ) {
		this.code = code;
		this.message = message;
		this.location = location;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IValidationError#getCode()
	 */
	@Override
	public String getCode() {
		return code;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IValidationError#setCode(java.lang.String)
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IValidationError#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IValidationError#setMessage(java.lang.String)
	 */
	@Override
	public void setMessage(String message) {
		this.message = message;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IValidationError#getLocation()
	 */
	@Override
	public String getLocation() {
		return location;
	}
	/* (non-Javadoc)
	 * @see gr.opengov.agora.security.IValidationError#setLocation(java.lang.String)
	 */
	@Override
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public void addLocationPrefix(String prefix) {		
		location = prefix + location; 
	}
	
}
