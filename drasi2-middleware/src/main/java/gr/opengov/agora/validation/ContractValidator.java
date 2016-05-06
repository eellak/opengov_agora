package gr.opengov.agora.validation;

import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IContractParty;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.util.Constants;
import gr.opengov.agora.util.Constants.Action;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContractValidator extends PublicOrganizationDecisionValidator implements IValidator<IContract>, IContractValidator {
	private static final Logger logger = LoggerFactory.getLogger(ContractValidator.class);

	private IContractItemValidator contractItemValidator;
	private IContractPartyValidator contractPartyValidator;
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IContractValidator#setContractItemValidator(gr.opengov.agora.validation.IContractItemValidator)
	 */
	public void setContractItemValidator(IContractItemValidator contractItemValidator){
		this.contractItemValidator = contractItemValidator;
	}	
	
	public void setContractPartyValidator(IContractPartyValidator contractPartyValidator){
		this.contractPartyValidator = contractPartyValidator;
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
	 * Validate contractDate, since and until.
	 * Rules:
	 * contractDate must be less than or equal to today
	 * since must be less than or equal to until
	 * @param contract
	 * @param validation
	 */
	private void validateContractDates( IContract contract, IValidation validation ) {
		Calendar now = GregorianCalendar.getInstance();
		Calendar dateSince = contract.getSince();
		Calendar dateUntil = contract.getUntil();
		validateValidDate( dateSince, "dateSince", "dateSince", validation );
		if (dateUntil != null)
			validateValidDate( dateUntil, "dateUntil", "dateUntil", validation );
		
		if ( (dateUntil != null) && ( dateSince.compareTo( dateUntil ) ) > 0 ) {
			validation.addValidationError( new ValidationError( "since", "'since' date exceeds 'until' one.", "since") );
		}
		
		IContract extendsContract = contract.getExtendsContract();
		if ( extendsContract != null && extendsContract.isValid() ) {
			if ((dateUntil != null) && (extendsContract.getUntil() != null))
				if (dateUntil.compareTo( extendsContract.getUntil() ) <= 0 ) {
					validation.addValidationError( new ValidationError( "until", "'until' date of extendsContract preceeds this one.", "until" ));
				}
		}
	}
	
	public void validateRelatedAdas( IPublicOrganizationDecision contract, Map<IAda, IPublicOrganizationDecision> relatedDecisions, IValidation validation ){
		super.validateRelatedAdas(contract, relatedDecisions, validation);
		if ( (relatedDecisions != null) && (relatedDecisions.size() > 0) ){
			Set<String> relatedAdas = new HashSet<String>();
			int i=0;
			for (Map.Entry<IAda, IPublicOrganizationDecision> entry : relatedDecisions.entrySet())
			{
				if ( !((entry.getKey().getAdaType().equals("ProcurementRequest")) || (entry.getKey().getAdaType().equals("Notice"))) ){
					
					//the logic of relating related ada of diavgeia, with certain types of diavgeia decisions has been disabled and replaced from a single check if the ada exists in diavgeia
//					//check for ContractCommission, ContractAward on Diavgeia
//					Integer decisionType = ( entry.getKey().getAdaType().equals("ContractAward")?55:entry.getKey().getAdaType().equals("ContractCommission")?56:-1  );
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
				    			validation.addValidationError( new ValidationError( "notApprovalRequestAdded", "You cannot add contracts for not approval requests", "relatedAda[" + i + "]" ) );
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
				for (IContractItem contractItem:contract.getContractItems()){
						if (contractItem.getNotice() != null){
							if (!relatedAdas.contains(contractItem.getNotice().getId()) ){
								validation.addValidationError( new ValidationError( "contractItemReferedToNotRelatedNotice", "The contract item of the specified contract must refer to a notice existed in related adas", "contractItems[" + j + "]" ) );
							}
						}
						else{
							if (contractItem.getProcurementRequest() != null){
								if (!relatedAdas.contains(contractItem.getProcurementRequest().getId()) ){
									validation.addValidationError( new ValidationError( "contractItemReferedToNotRelatedRequest", "The contract item of the specified contract must refer to a procurement request existed in related adas", "contractItems[" + j + "]" ) );
								}
							}
						}
					j++;
				}
			}
		}
	}
	
	private void validateContractReferences( IContract contract, IValidation validation ) {
		if ( contract.getExtendsContract() != null && !contract.getExtendsContract().isValid() ) {		
			validation.addValidationError( new ValidationError( "extendsContract", "Invalid reference.", "extendsContract" ) );
		}
		if ( contract.getChangesContract() != null && !contract.getChangesContract().isValid() ) {		
			validation.addValidationError( new ValidationError( "changesContract", "Invalid reference.", "changesContract" ) );
		}
	}
	
	private void validateTaxonomyReferences( IContract contract, IValidation validation ) {
		ITaxonomy taxonomy;
		taxonomy = getTaxonomyService().getTaxonomy( "contracting_authority", null );
		if ( !contract.getContractingAuthorityTaxonomyReference().isValidForTaxonomy( taxonomy ) ) {		
			validation.addValidationError( new ValidationError( "contractingAuthority", "Invalid taxonomy reference.", "contractingAuthority" ) );
		}
		
		taxonomy = getTaxonomyService().getTaxonomy( "award_procedure", null );
		if ( !contract.getAwardProcedureTaxonomyReference().isValidForTaxonomy( taxonomy ) ) {		
			validation.addValidationError( new ValidationError( "awardProcedure", "Invalid award procedure.", "awardProcedure" ) );
		}		
		
		taxonomy = getTaxonomyService().getTaxonomy( "contract_type", null );
		if ( !contract.getContractTypeTaxonomyReference().isValidForTaxonomy( taxonomy ) ) {		
			validation.addValidationError( new ValidationError( "invalidContractType", "Invalid contract type.", "contractType" ) );
		}
		
		taxonomy = getTaxonomyService().getTaxonomy( "commission_criteria", null );
		if ( !contract.getCommissionCriteriaTaxonomyReference().isValidForTaxonomy( taxonomy ) ) {		
			validation.addValidationError( new ValidationError( "invalidCommissionCriteria", "Invalid commission criteria.", "commissionCriteria" ) );
		}		
		
	}
	
	private IValidation validateCore( IContract contract, Action action ) {
		IValidation validation = new Validation();
		
		if (( Constants.CONTRACTTYPES_PROJECT.contains(Integer.parseInt(contract.getContractTypeIdRef())) ) && (contract.getProjectCode() != null))
			validation.addValidationError( new ValidationError( "projectCodeForNonProject", "invalid contract type or project code must be empty.", "projectCode" ) );
		if ((contract.isCoFunded()) && (((contract.getCodeCoFunded() == null)) || (contract.getCodeCoFunded().trim().length() == 0)))
			validation.addValidationError( new ValidationError( "emptyCodeCoFunded", "contract is coFunded and coFuded code is empty.", "codeCoFunded" ) );
		if ((contract.isFundedFromPIP()) && (((contract.getCodePIP() == null)) || (contract.getCodePIP().trim().length() == 0)))
			validation.addValidationError( new ValidationError( "emptyCodeCoFunded", "contract is funded from PIP and codePIP code is empty.", "codePIP" ) );
		
		validateContractDates( contract, validation );		
		contractItemValidator.validateContractItems(contract.getContractItems(), validation );
		validateContractReferences(contract, validation);
		validateTaxonomyReferences(contract, validation );
		return validation;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IContractValidator#validateCreate(gr.opengov.agora.domain.IContract)
	 */
	@Override
	public IValidation validateCreate( IContract contract, Map<IAda, IPublicOrganizationDecision> relatedDecisions ) {
		IValidation validation = super.validateCreateDecision( contract, relatedDecisions );
		validation.addValidationErrors( validateCore( contract, Action.CREATE ) );	
		for (int i=0; i < contract.getSecondaryParties().size(); i++){
			validation.addValidationErrors( contractPartyValidator.validateSecondaryParty(contract.getSecondaryParties().get(i), "SecondaryParties[" + i + "]"));
		}		
		return validation;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.validation.IContractValidator#validateUpdate(gr.opengov.agora.domain.IContract)
	 */
	@Override
	public IValidation validateUpdate( IContract contract, Map<IAda, IPublicOrganizationDecision> relatedDecisions ) {
		IValidation validation = super.validateUpdateDecision( contract, relatedDecisions );
		validation.addValidationErrors( validateCore( contract, Action.UPDATE ) );
		for (int i=0; i < contract.getSecondaryParties().size(); i++){
			validation.addValidationErrors( contractPartyValidator.validateSecondaryParty(contract.getSecondaryParties().get(i), "SecondaryParties[" + i + "]"));
		}
		return validation;
	}
}
