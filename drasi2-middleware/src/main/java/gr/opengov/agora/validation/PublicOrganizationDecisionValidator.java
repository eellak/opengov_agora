package gr.opengov.agora.validation;

import gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge;
import gr.opengov.agora.domain.Contract;
import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.Notice;
import gr.opengov.agora.domain.Payment;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.IProcurementRequestService;
import gr.opengov.agora.service.ITaxonomyService;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublicOrganizationDecisionValidator implements IPublicOrganizationDecisionValidator {
	private static final Logger logger = LoggerFactory.getLogger(PublicOrganizationDecisionValidator.class);
	
	private IAgoraDiavgeiaBridge diavgeiaBridge = null;	
	private ITaxonomyService taxonomyService = null;		
	private IProcurementRequestService procurementRequestService;
	private IDecisionGenericService<IContract> contractService;
	private IDecisionGenericService<INotice> noticeService;
		
	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IPublicOrganizationDecisionValidator#setDiavgeiaBridge(gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge)
	 */
	@Override
	public void setDiavgeiaBridge( IAgoraDiavgeiaBridge bridge ) {
		this.diavgeiaBridge = bridge;
	}
			
	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IPublicOrganizationDecisionValidator#getDiavgeiaBridge()
	 */
	@Override
	public IAgoraDiavgeiaBridge getDiavgeiaBridge() {
		return this.diavgeiaBridge;
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IPublicOrganizationDecisionValidator#setTaxonomyService(gr.opengov.agora.service.ITaxonomyService)
	 */
	@Override
	public void setTaxonomyService(ITaxonomyService taxonomyService) {
		this.taxonomyService = taxonomyService;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IPublicOrganizationDecisionValidator#getTaxonomyService()
	 */
	@Override
	public ITaxonomyService getTaxonomyService() {
		return taxonomyService;
	}
	
	@Override
	public void setProcurementRequestService(IProcurementRequestService procurementRequestService) {
		this.procurementRequestService = procurementRequestService;
	}
	
	@Override
	public void setContractService(IDecisionGenericService<IContract> contractService) {
		this.contractService = contractService;
	}	

	@Override
	public void setNoticeService(IDecisionGenericService<INotice> noticeService) {
		this.noticeService = noticeService;
	}	
	
	private boolean isValidContentType( String contentType ) {
		if ( contentType.equals( "application/pdf" ) ) return true;
		return false;
	}
	
	/**
	 * Checks whether a date is valid
	 * @param date
	 * @param field
	 * @param xpath
	 * @param validation
	 */
	private void validateValidDate(	Calendar date, String field, String xpath, IValidation validation ) {
		//logger.debug( "Checking valid date: " + date.toString() );
		date.setLenient( false );
		try {
			date.getTime();
		}
		catch ( Exception e ) {
			validation.addValidationError( new ValidationError( field, "Invalid date", xpath ) );
		}
	}
	/**
	 * Validate signedDate, since and until.
	 * Rules:
	 * signedDate must be less than or equal to today
	 * @param relatedDecisions 
	 * @param publicOrganizationDecision
	 * @param validation
	 */
	protected void validateDateSigned( IPublicOrganizationDecision publicOrganizationDecision, Map<IAda, IPublicOrganizationDecision> relatedDecisions, IValidation validation ) {
		Calendar now = GregorianCalendar.getInstance();
		Calendar dateSigned = publicOrganizationDecision.getDateSigned();	
		
		logger.debug("validating decision date signed");
		validateValidDate( dateSigned, "dateSigned", "dateSigned", validation );
		if ( (dateSigned != null) && ( dateSigned.compareTo( now ) ) > 0 ) {
			logger.debug("validation error: The specified date is in the future : " + dateSigned.toString() );
			validation.addValidationError( new ValidationError( "futureDateSigned", "The specified date is in the future.", "dateSigned" ) );
		}
		
		if ( (relatedDecisions != null) && (relatedDecisions.size() > 0) ){
			for (Map.Entry<IAda, IPublicOrganizationDecision> entry : relatedDecisions.entrySet())
			{
			    if ( entry.getValue().isValid() ){
			    	if ( entry.getValue().getDateSigned().compareTo(publicOrganizationDecision.getDateSigned()) > 0 ){
			    		validation.addValidationError( new ValidationError( "dateSignedPaymentPriorToRelated", "Invalid date signed. Date signed must be greater than the date signed of the related decision", "dateSigned" ) );
			    	}
			    }
			    
			}
		}
	}
	
	private boolean hasValidContentType( IDocument document ) {
		try {						
			if ( !isValidContentType( document.getContentType() ) ) {
				logger.error( "Received invalid mime type: " + document.getContentType() );
				return false;
			}
			return true;
		}
		catch ( Exception e ) {
			logger.error( "Exception while validating content type: " + e.getMessage() );
			return false;
		}
	}
	
	public void validateRelatedAdas( IPublicOrganizationDecision publicOrganizationDecision, Map<IAda, IPublicOrganizationDecision> relatedDecisions, IValidation validation ) {
		if ( (publicOrganizationDecision.getRelatedAdas() != null) && (publicOrganizationDecision.getRelatedAdas().size() > 0) ){
			if (relatedDecisions == null){
				validation.addValidationError( new ValidationError( "RelatedAda", "One or more invalid related ada found.", "relatedAda" ) );
			}
			else{
				if ( relatedDecisions.size() != publicOrganizationDecision.getRelatedAdas().size() ){
					validation.addValidationError( new ValidationError( "RelatedAda", "One or more invalid related ada found.", "relatedAda" ) );
				}
			}
		}
		int i=0;
		if ((relatedDecisions != null) && (relatedDecisions.size() > 0) ){
			for (Map.Entry<IAda, IPublicOrganizationDecision> entry : relatedDecisions.entrySet()){
				//disabled decision type check from diavgeia.
//				if ( (!entry.getValue().isValid()) && ( !entry.getKey().getAdaType().equals("ContractCommission")) && ( !entry.getKey().getAdaType().equals("ContractDeclaration")) && ( !entry.getKey().getAdaType().equals("ContractAward")) ){
				if ( !entry.getValue().isValid()){
					if (!getDiavgeiaBridge().isValidAda(entry.getKey().getAdaCode())){
						validation.addValidationError( new ValidationError( "RelatedAda", "One or more invalid related ada found.", "relatedAdas["+i+"]" ) );
					}
				}
				i++;
			}
		}
	}
	

	private IValidation validateCore( IPublicOrganizationDecision decision, Map<IAda, IPublicOrganizationDecision> relatedDecisions ) {	
		Set<Integer> invalidIndex = new HashSet<Integer>();
		IValidation validation = new Validation();		
		/* Validate organization and unit ids */
		if ( !getDiavgeiaBridge().isValidOrganization( decision.getOrganizationDiavgeiaId() ) ) {
			validation.addValidationError( new ValidationError( "organizationDiavgeiaId", "The organization ID does not exist.", "organizationDiavgeiaId" ) );
		}
		if ( decision.getCmsMetadata() != null ) {
			validation.addValidationError( new ValidationError( "cmsMetadata", "Submitted data cannot contain metadata.", "cmsMetadata" ) );
		}
		if ( !getDiavgeiaBridge().isValidUnitForOrganization( decision.getUnitDiavgeiaId(), decision.getOrganizationDiavgeiaId() ) ) {
			validation.addValidationError( new ValidationError( "unitDiavgeiaId", "The unid ID does not belong to the specified organization.", "organization/unit/idRef" ) );
		}
		
		/* Validate signer ids for organization */
		if ( !getDiavgeiaBridge().areValidSignersForOrganization( decision.getSignersDiavgeiaIds(), decision.getOrganizationDiavgeiaId(), invalidIndex ) ) {
			for ( Integer index: invalidIndex ) {
				validation.addValidationError( new ValidationError( "signer", "The specified signer ID does not belong to an authorized signer for this organization.", "signers/signer[" + index + "]/idRef" ) );
			}
		}
		
		if ( decision.getReplaces() != null && !decision.getReplaces().isValid() ) {
			validation.addValidationError( new ValidationError( "replacesInvalid", "Invalid reference.", "replaces" ) );
		}
		
		validateDateSigned( decision, relatedDecisions, validation );
		validateRelatedAdas(decision, relatedDecisions, validation);
//		validateUniqueDocumentCode(decision, validation); //there are problems with already inserted contracts from other tests. Enabling this, all tests which insert contracts, should also delete those.
		return validation;
	}
	
	private void validateUniqueDocumentCode( IPublicOrganizationDecision decision, IValidation validation ) {
		// TODO: Fix this check
		return;
		/*
		if (decisionDao.exists("uniqueDocumentCode", decision.getUniqueDocumentCode(), accessControl.getClient().getOrgs())){
			validation.addValidationError( new ValidationError( "uniqueDocumentCode", "Invalid Unique Document Code.", "uniqueDocumentCode" ) );
		}
		*/		
	}	
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IPublicOrganizationDecisionValidator#validateCreateDecision(gr.opengov.agora.domain.IPublicOrganizationDecision)
	 */
	@Override
	public IValidation validateCreateDecision( IPublicOrganizationDecision decision, Map<IAda, IPublicOrganizationDecision> relatedDecisions ) {
		IValidation validation = new Validation();
		validation.addValidationErrors( validateCore( decision, relatedDecisions ) );
		/*
		 * For new entries, documents cannot be empty
		 */
		if ( decision.getDocument() == null ) {
			validation.addValidationError( new ValidationError( "documentNotSpecified", "Document not specified", "document" ) );
		}			
		else if ( !hasValidContentType( decision.getDocument() ) ) {
			validation.addValidationError( new ValidationError( "documentNotPdf", "The specified document is not in PDF format", "document") );
		}		
		return validation;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IPublicOrganizationDecisionValidator#validateUpdateDecision(gr.opengov.agora.domain.IPublicOrganizationDecision)
	 */
	@Override
	public IValidation validateUpdateDecision( IPublicOrganizationDecision decision, Map<IAda, IPublicOrganizationDecision> relatedDecisions ) {
		IValidation validation = new Validation();
		validation.addValidationErrors( validateCore( decision, relatedDecisions ) );
		/*
		 * For updates, document can be empty. In this case the old document will be used by the service/dao layers
		 */
		if ( decision.getDocument() != null && !hasValidContentType( decision.getDocument() ) ) {
			validation.addValidationError( new ValidationError( "documentNotPdf", "The specified document is not in PDF format", "document") );			
		}
		
		return validation;		
	}
}
