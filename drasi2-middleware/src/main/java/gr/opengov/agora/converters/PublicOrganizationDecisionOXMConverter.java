package gr.opengov.agora.converters;

import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.CmsEntity;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.Document;
import gr.opengov.agora.domain.ICmsEntity;
import gr.opengov.agora.domain.ICmsMetadata;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.InvalidCmsEntityReference;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.exceptions.InternalErrorException;
import gr.opengov.agora.exceptions.OperationNotYetSupportedException;
import gr.opengov.agora.model.AdaOXM;
import gr.opengov.agora.model.ArrayOfAdas;
import gr.opengov.agora.model.ArrayOfDecisionIds;
import gr.opengov.agora.model.ArrayOfDecisionStorageReferences;
import gr.opengov.agora.model.ArrayOfSigners;
import gr.opengov.agora.model.ArrayOfSignersReferences;
import gr.opengov.agora.model.CmsEntityOXM;
import gr.opengov.agora.model.CmsMetadataOXM;
import gr.opengov.agora.model.DecisionStorageReferenceOXM;
import gr.opengov.agora.model.OrganizationReference;
import gr.opengov.agora.model.PublicOrganizationDecisionOXM;
import gr.opengov.agora.model.SignerReference;
import gr.opengov.agora.model.TaxonomyReferenceOXM;
import gr.opengov.agora.model.UnitReference;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.IDecisionUtilsService;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicOrganizationDecisionOXMConverter implements IPublicOrganizationDecisionOXMConverter {
	private static final Logger logger = LoggerFactory.getLogger(PublicOrganizationDecisionOXMConverter.class);
	
	private IDecisionUtilsService decisionService;
	private Properties configProperties;	
	private ICommonsOXMConverter commonsConverter;
	
	public void setDecisionService( IDecisionUtilsService decisionService ) {
		this.decisionService = decisionService;
	}
	
	public void setConfigProperties( Properties configProperties ) {
		this.configProperties = configProperties;
	}

	public void setCommonsConverter(ICommonsOXMConverter commonsConverter) {
		this.commonsConverter = commonsConverter;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#convertCmsMetadata(gr.opengov.agora.model.CmsMetadata, gr.opengov.agora.domain.CmsMetadataTO)
	 */
	@Override
	public ICmsMetadata convertCmsMetadata( CmsMetadataOXM oxm, ICmsMetadata obj ) {
		if ( oxm == null ) return null;
		obj.setUserDiavgeiaId( oxm.getUserIdRef() );
		
		obj.setDeleted( oxm.isDeleted() == null ? false : oxm.isDeleted() );
		obj.setLastModifiedTime( oxm.getLastModifiedTime() );
		obj.setSubmissionTime( oxm.getSubmissionTime() );
		obj.setDeletedReason( oxm.getDeletedReason() );
		if (oxm.getDeletionType() != null){
			obj.setDeletionTypeIdRef(oxm.getDeletionType().getIdRef());
		}
		obj.setDeletedFromUserId(oxm.getDeletedFromUserIdRef() );
		obj.setDeletedTime( oxm.getDeletedTime() );
		
		obj.setCancelled( oxm.isCancelled() == null ? false : oxm.isCancelled() );
		obj.setCancelledReason( oxm.getCancelledReason() );
		if (oxm.getCancellationType() != null){
			obj.setCancellationTypeIdRef(oxm.getCancellationType().getIdRef());
		}
		obj.setCancelledFromUserId(oxm.getCancelledFromUserIdRef() );
		obj.setCancelledTime( oxm.getCancelledTime() );		
		
		return obj;
	}
	
	@Override
	public CmsMetadataOXM convertCmsMetadata( ICmsMetadata obj, CmsMetadataOXM oxm ) {
		if ( obj == null ) return null;
		//logger.debug( "\n\n\nCONVERTING: obj: " + obj + ", oxm: " + oxm + "\n\n\n" );		
		oxm.setUserIdRef( obj.getUserDiavgeiaId() );		
		oxm.setDeleted( obj.getDeleted() );
		oxm.setLastModifiedTime( obj.getLastModifiedTime() );
		oxm.setSubmissionTime( obj.getSubmissionTime() );	
		oxm.setDeletedReason( obj.getDeletedReason() );
		
		if (obj.getDeletionTypeIdRef() != null){		
			TaxonomyReferenceOXM deletionTypeTaxonomyReferenceOXM = new TaxonomyReferenceOXM();
			deletionTypeTaxonomyReferenceOXM.setIdRef(obj.getDeletionTypeIdRef());
			oxm.setDeletionType(deletionTypeTaxonomyReferenceOXM);
		}		
		
		oxm.setDeletedFromUserIdRef( obj.getDeletedFromUserId() );
		oxm.setDeletedTime( obj.getDeletedTime() );
		
		oxm.setCancelled( obj.getCancelled() );
		oxm.setCancelledReason( obj.getCancelledReason() );
		
		if (obj.getCancellationTypeIdRef() != null){		
			TaxonomyReferenceOXM cancellationTypeTaxonomyReferenceOXM = new TaxonomyReferenceOXM();
			cancellationTypeTaxonomyReferenceOXM.setIdRef(obj.getCancellationTypeIdRef());
			oxm.setCancellationType(cancellationTypeTaxonomyReferenceOXM);
		}		
		
		oxm.setCancelledFromUserIdRef( obj.getCancelledFromUserId() );
		oxm.setCancelledTime( obj.getCancelledTime() );		
		return oxm;		
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#convertCmsEntity(gr.opengov.agora.model.CmsEntity, gr.opengov.agora.domain.CmsEntityTO)
	 */
	@Override
	public ICmsEntity convertCmsEntity( CmsEntityOXM oxm, ICmsEntity obj ) {				
		obj.setId( oxm.getId() );
		obj.setCmsMetadata( convertCmsMetadata( oxm.getCmsMetadata(), new CmsMetadata() ) );
		if ( oxm.getReplaces() != null ) {
			ICmsEntity ref = null;
			try{
				ref = decisionService.get( oxm.getReplaces() );
				if ( ref == null ) {
					obj.setReplaces( new InvalidCmsEntityReference() );
				}
				else {
					obj.setReplaces( ref );
				}				
			}
			catch (DecisionNotFoundException e){
				obj.setReplaces( new InvalidCmsEntityReference() );
			}

		}		
		return obj;		
	}
	
	@Override
	public CmsEntityOXM convertCmsEntity( ICmsEntity obj, CmsEntityOXM oxm ) {				
		oxm.setId( obj.getId() );
		oxm.setCmsMetadata( convertCmsMetadata( obj.getCmsMetadata(), new CmsMetadataOXM() ) );
		oxm.setReplaces( obj.getReplaces() != null ? obj.getReplaces().getId() : null );
		return oxm;		
	}	
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#convertPublicOrganizationDecision(gr.opengov.agora.model.PublicOrganizationDecision, gr.opengov.agora.domain.PublicOrganizationDecisionTO)
	 */
	@Override
	public IPublicOrganizationDecision convertPublicOrganizationDecision( PublicOrganizationDecisionOXM oxm, IPublicOrganizationDecision obj ) {
		convertCmsEntity( oxm, obj );
		obj.setTitle( oxm.getTitle() );
		obj.setUniqueDocumentCode( oxm.getUniqueDocumentCode() );
		obj.setProtocolNumberCode(oxm.getProtocolNumberCode());
		obj.setOrganizationDiavgeiaId( oxm.getOrganization().getIdRef() );
		obj.setUnitDiavgeiaId( oxm.getOrganization().getUnit() == null ? null : oxm.getOrganization().getUnit().getIdRef() );
		obj.setIssuerEmail( oxm.getIssuerEmail() );
		List<Integer> signers = new ArrayList<Integer>();
		for ( SignerReference ref: oxm.getSigners().getSigner() ) {
			signers.add( ref.getIdRef() );
		}
		obj.setSignersDiavgeiaIds( signers );
		
		obj.setDiavgeiaPublished(oxm.isDiavgeiaPublished());
		obj.setDateSigned( oxm.getDateSigned() );
		if ((oxm.getRelatedAdas() != null) && (oxm.getRelatedAdas().getAda() != null) && (oxm.getRelatedAdas().getAda().size() > 0)){
			List<Ada> adas = new ArrayList<Ada>();		
			for (AdaOXM adaOxm: oxm.getRelatedAdas().getAda() ){
				adas.add(commonsConverter.convertAda(adaOxm, new Ada()));
			}
			obj.setRelatedAdas(adas);			
		}
				
		/*
		 * Document can be null in the case of updates. We ignore this here, and handle it at the service/dao layer.
		 */
		IDocument document = null;
		if ( oxm.getDocumentUrl() != null ) {
			throw new OperationNotYetSupportedException( "Document URLs are not yet supported, please use Base64 data." );
		}
		if ( oxm.getDocument() != null ) {
			document = new Document();
			document.setData( oxm.getDocument() );
			document.setSize( document.getData().length );
			logger.debug( "Binary document length: " + document.getSize() );
			document.setType( "document" );	
			try {
				document.setHashAlgorithm( configProperties.getProperty( "document.hashing.algorithm" ) );
			}
			catch ( NoSuchAlgorithmException e ) {
				throw new InternalErrorException();
			}
		}
		obj.setDocument( document );
		
		return obj;
	}
	
	@Override
	public PublicOrganizationDecisionOXM convertPublicOrganizationDecision( IPublicOrganizationDecision obj, PublicOrganizationDecisionOXM oxm ) {
		convertCmsEntity( obj, oxm );
		oxm.setTitle( obj.getTitle() );
		oxm.setUniqueDocumentCode( obj.getUniqueDocumentCode() );
		oxm.setProtocolNumberCode(obj.getProtocolNumberCode());
		oxm.setIssuerEmail( obj.getIssuerEmail() );
		OrganizationReference orgref = new OrganizationReference();
		orgref.setIdRef( obj.getOrganizationDiavgeiaId() );
		if ( obj.getUnitDiavgeiaId() != null ) {
			UnitReference uref = new UnitReference();
			uref.setIdRef( obj.getUnitDiavgeiaId() );
			orgref.setUnit( uref );
		}
		oxm.setOrganization( orgref );
		ArrayOfSignersReferences signersReferences = new ArrayOfSignersReferences();
		for ( Integer signerId: obj.getSignersDiavgeiaIds() ) {
			SignerReference ref = new SignerReference();
			ref.setIdRef( signerId );
			signersReferences.getSigner().add( ref );
		}
		oxm.setSigners( signersReferences );
		
		oxm.setDiavgeiaPublished(obj.isDiavgeiaPublished());
		oxm.setDateSigned( obj.getDateSigned() );

		if ((obj.getRelatedAdas() != null) && (obj.getRelatedAdas().size() > 0)){
			ArrayOfAdas adas = new ArrayOfAdas();
			for (Ada ada: obj.getRelatedAdas()){
				adas.getAda().add(commonsConverter.convertAda(ada, new AdaOXM()));
			}
			oxm.setRelatedAdas(adas);
		}		
		
		
		
		// Documents
		/* We never return binary data in a response object, we specify a URL instead */
		oxm.setDocumentUrl( obj.getId() );		
		return oxm;
	}
	
	@Override
	public ArrayOfDecisionStorageReferences getDecisionReferences(	List<IDecisionStorageReference> refs) {
		ArrayOfDecisionStorageReferences list = new ArrayOfDecisionStorageReferences();
		for ( IDecisionStorageReference ref: refs ) {
			DecisionStorageReferenceOXM oxm = new DecisionStorageReferenceOXM();
			oxm.setId( ref.getId() );
			oxm.setUniqueDocumentCodeRef( ref.getUniqueDocumentId() );
			list.getStorageReference().add( oxm );
		}
		return list;
	}

	@Override
	public ArrayOfDecisionStorageReferences getDecisionReference( IDecisionStorageReference ref) {
		List<IDecisionStorageReference> list = new ArrayList<IDecisionStorageReference>();
		list.add( ref );
		return getDecisionReferences( list );
	}

	@Override
	public ArrayOfDecisionIds getDecisionIds( List<IPublicOrganizationDecision> decisions) {
		ArrayOfDecisionIds ids = new ArrayOfDecisionIds();
		for ( IPublicOrganizationDecision decision: decisions ) {
			ids.getDecision().add( decision.getId() );
		}
		return ids;
	}	
}