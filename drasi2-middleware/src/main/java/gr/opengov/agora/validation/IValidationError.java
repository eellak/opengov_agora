package gr.opengov.agora.validation;

public interface IValidationError {

	public abstract String getCode();

	public abstract void setCode(String code);

	public abstract String getMessage();

	public abstract void setMessage(String message);

	public abstract String getLocation();

	public abstract void setLocation(String location);
	
	public void addLocationPrefix( String prefix );

}