package gr.opengov.agora.validation;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.util.Constants.Action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NoticeValidator extends PublicOrganizationDecisionValidator implements IValidator<INotice> {
	private static final Logger logger = LoggerFactory.getLogger(NoticeValidator.class);
	private IContractItemValidator contractItemValidator;
	
	public void setContractItemValidator(IContractItemValidator contractItemValidator){
		this.contractItemValidator = contractItemValidator;
	}	
	
	@Override
	public IValidation validateCreate(INotice notice, Map<IAda, IPublicOrganizationDecision> relatedDecisions) {
		IValidation validation = super.validateCreateDecision( notice, relatedDecisions );
		validation.addValidationErrors( validateCore( notice, Action.CREATE ) );
		return validation;
	}		
	
	@Override
	public IValidation validateUpdate(INotice notice, Map<IAda, IPublicOrganizationDecision> relatedDecisions) {
		IValidation validation = super.validateUpdateDecision( notice, relatedDecisions );
		validation.addValidationErrors( validateCore( notice, Action.UPDATE ) );
		return validation;
	}	

	private IValidation validateCore( INotice notice, Action action ) {
		IValidation validation = new Validation();
		validateNoticeDates(notice, validation);
		contractItemValidator.validateContractItems(notice.getContractItems(), validation );
		validateTaxonomyReferences(notice, validation );
		return validation;
	}	
	
	
	private void validateTaxonomyReferences( INotice notice, IValidation validation ) {
		ITaxonomy taxonomy;
		taxonomy = getTaxonomyService().getTaxonomy( "award_procedure", null );
		if ( !notice.getAwardProcedureTaxonomyReference().isValidForTaxonomy( taxonomy ) ) {		
			validation.addValidationError( new ValidationError( "awardProcedure", "Invalid award procedure.", "awardProcedure" ) );
		}		
	}	
	
	/**
	 * Validate since and until.
	 * Rules:
	 * since must be less than or equal to until
	 * @param INotice
	 * @param validation
	 */
	private void validateNoticeDates( INotice notice, IValidation validation ) {
		Calendar dateSince = notice.getSince();
		Calendar dateUntil = notice.getUntil();
		validateValidDate( dateSince, "dateSince", "dateSince", validation );
		if (dateUntil != null)
			validateValidDate( dateUntil, "dateUntil", "dateUntil", validation );
		
		if ( (dateUntil != null) && ( dateSince.compareTo( dateUntil ) ) > 0 ) {
			validation.addValidationError( new ValidationError( "since", "'since' date exceeds 'until' one.", "since") );
		}
	}
	
	public void validateRelatedAdas( IPublicOrganizationDecision notice, Map<IAda, IPublicOrganizationDecision> relatedDecisions, IValidation validation ){
		super.validateRelatedAdas(notice, relatedDecisions, validation);
		if ( (relatedDecisions != null) && (relatedDecisions.size() > 0) ){
			Set<String> relatedAdas = new HashSet<String>();
			int i=0;
			for (Map.Entry<IAda, IPublicOrganizationDecision> entry : relatedDecisions.entrySet())
			{
				if ( !(entry.getKey().getAdaType().equals("ProcurementRequest")) ){
					
					//the logic of relating related ada of diavgeia, with certain types of diavgeia decisions has been disabled and replaced from a single check if the ada exists in diavgeia
//					//check for ContractDeclaration on Diavgeia
//					Integer decisionType =  ( entry.getKey().getAdaType().equals("ContractDeclaration")?54:-1  );
//					if (decisionType == -1)
//						validation.addValidationError( new ValidationError( "invalid" + entry.getKey().getAdaType() + "RelatedAda", "The specified related ada type is invalid.", "relatedAda[" + i + "]" ) );
//					else
//						if (!getDiavgeiaBridge().isValidAda(entry.getKey().getAdaCode(), decisionType))
//							validation.addValidationError( new ValidationError( "invalid" + entry.getKey().getAdaType() + "RelatedAda", "The specified related ada is invalid.", "relatedAda[" + i + "]" ) );
					
/*					if (!getDiavgeiaBridge().isValidAda(entry.getKey().getAdaCode()))
						validation.addValidationError( new ValidationError( "invalid" + entry.getKey().getAdaType() + "RelatedAda", "The specified related ada is invalid.", "relatedAda[" + i + "]" ) );					*/
				}
				else{
				    if ( !entry.getValue().isValid() ){
				    	validation.addValidationError( new ValidationError( "invalid" + entry.getKey().getAdaType() + "RelatedAda", "The specified related ada is invalid.", "relatedAda[" + i + "]" ) );
				    }
				    else{
				    	if ( entry.getValue() instanceof IProcurementRequest){
				    		if ( !((((IProcurementRequest)entry.getValue()).getApprovesRequest() != null ) &&
				    				(((IProcurementRequest)entry.getValue()).getApprovesRequest().isValid())) ){
				    			validation.addValidationError( new ValidationError( "notApprovalRequestAdded", "You cannot add notices for not approval requests", "relatedAda[" + i + "]" ) );
				    		}
				    	}
				    	if (!relatedAdas.add(entry.getKey().getAdaCode())){
				    		validation.addValidationError( new ValidationError( "requestAdaPresentTwice", "Each procurement request must be inserted one time in related adas", "relatedAda[" + i + "]" ) );
				    	}
				    		
				    }
				}		    
				i++;
			}
			
			int j=0;
			if ( relatedAdas.size() > 0 ){
				for (IContractItem contractItem:notice.getContractItems()){
						if (contractItem.getProcurementRequest() != null){
							if (!relatedAdas.contains(contractItem.getProcurementRequest().getId()) ){
								validation.addValidationError( new ValidationError( "contractItemReferedToNotRelatedRequest", "The contract item of the specified notice must refer to a procurement request existed in related adas", "contractItems[" + j + "]" ) );
							}
						}
					j++;
				}
			}
		}
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
	
}
