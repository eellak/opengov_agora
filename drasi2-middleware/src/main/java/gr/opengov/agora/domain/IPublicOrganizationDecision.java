package gr.opengov.agora.domain;

import gr.opengov.agora.security.IClient;
import gr.opengov.agora.util.LazyEntity;

import java.util.Calendar;
import java.util.List;

public interface IPublicOrganizationDecision extends ICmsEntity, LazyEntity {
	
	/* Functions */
	public abstract DecisionType getDecisionType();
	public abstract Integer getDiavgeiaType();
	public abstract IDecisionStorageReference getStorageReference( IClient client );	
	
	/* Properties getters and setters */
	public abstract Boolean isDiavgeiaPublished();
	public abstract void setDiavgeiaPublished(Boolean diavgeiaPublished);
	
	public abstract String getUniqueDocumentCode();

	public abstract void setUniqueDocumentCode(String uniqueDocumentCode);
	
	public abstract String getProtocolNumberCode();
	
	public abstract void setProtocolNumberCode(String protocolNumberCode);

	public abstract Integer getOrganizationDiavgeiaId();

	public abstract void setOrganizationDiavgeiaId(Integer organizationDiavgeiaId);

	public abstract Integer getUnitDiavgeiaId();

	public abstract void setUnitDiavgeiaId(Integer unitDiavgeiaId);

	public abstract List<Integer> getSignersDiavgeiaIds();

	public abstract void setSignersDiavgeiaIds(List<Integer> signersDiavgeiaIds);

	public abstract IDocument getDocument();
	
	public abstract void setDocument( IDocument document );

	public abstract String getTitle();
	
	public abstract void setTitle(String title);

	public abstract Calendar getDateSigned();

	public abstract void setDateSigned(Calendar dateSigned);

	public abstract List<Ada> getRelatedAdas();

	public abstract void setRelatedAdas(List<Ada> relatedAdas);
	
	public abstract String getIssuerEmail();
	
	public abstract void setIssuerEmail( String email );
	
	public List<IContractItem> getContractItems();

}