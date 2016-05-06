package gr.opengov.agora.converters;

import gr.opengov.agora.domain.ICmsEntity;
import gr.opengov.agora.domain.ICmsMetadata;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.model.ArrayOfDecisionIds;
import gr.opengov.agora.model.ArrayOfDecisionStorageReferences;
import gr.opengov.agora.model.CmsEntityOXM;
import gr.opengov.agora.model.CmsMetadataOXM;
import gr.opengov.agora.model.PublicOrganizationDecisionOXM;


import java.util.List;

public interface IPublicOrganizationDecisionOXMConverter {

	public abstract ICmsEntity convertCmsEntity(CmsEntityOXM oxm, ICmsEntity obj);
	public abstract CmsEntityOXM convertCmsEntity(ICmsEntity obj, CmsEntityOXM oxm);

	public abstract ICmsMetadata convertCmsMetadata(CmsMetadataOXM oxm, ICmsMetadata obj );
	public abstract CmsMetadataOXM convertCmsMetadata( ICmsMetadata obj, CmsMetadataOXM oxm);	
	
	public abstract IPublicOrganizationDecision convertPublicOrganizationDecision(PublicOrganizationDecisionOXM oxm, IPublicOrganizationDecision obj);
	public abstract PublicOrganizationDecisionOXM convertPublicOrganizationDecision(IPublicOrganizationDecision obj, PublicOrganizationDecisionOXM oxm);
	
	public abstract ArrayOfDecisionStorageReferences getDecisionReferences( List<IDecisionStorageReference> refs );	
	public abstract ArrayOfDecisionStorageReferences getDecisionReference( IDecisionStorageReference ref );

	public abstract ArrayOfDecisionIds getDecisionIds( List<IPublicOrganizationDecision> decisions );
}