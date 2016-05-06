package gr.opengov.agora.converters;

import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.model.ContractOXM;

import java.util.List;

public interface IDecisionGenericConverter<D extends IPublicOrganizationDecision,X,XA,XASHORT> {
	public abstract D toObject( X oxm );
	public abstract X toXML( D obj );

	public abstract List<D> toObjectList( XA payments);
	public abstract XA toXMLList(List<D> payments);
	public abstract XASHORT toXMLShort( List<D> payments );
	public abstract D getNewInstance();
	public abstract X getNewInstanceOXM();	
}
