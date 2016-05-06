package gr.opengov.agora.domain;

public interface IAda {

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract String getAdaCode();

	public abstract void setAdaCode(String adaCode);

	public abstract String getAdaType();

	public abstract void setAdaType(String adaType);
	
	/* Used to generated related ADAs when copying to Diavgeia */
	public abstract boolean isDiavgeiaAda();
	
	public abstract boolean equals(Object obj);

	public abstract int hashCode();

	public abstract void finalizeEntity();
	

}