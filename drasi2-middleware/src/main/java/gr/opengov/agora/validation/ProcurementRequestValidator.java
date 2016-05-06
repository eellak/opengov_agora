package gr.opengov.agora.validation;

import java.util.List;
import java.util.Map;

import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.IProcurementRequestService;
import gr.opengov.agora.util.Constants.Action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProcurementRequestValidator extends PublicOrganizationDecisionValidator implements IValidator<IProcurementRequest> {
	private static final Logger logger = LoggerFactory.getLogger(ProcurementRequestValidator.class);
	private IContractItemValidator contractItemValidator;
	private IProcurementRequestService requestService;
	
	public void setContractItemValidator(IContractItemValidator contractItemValidator){
		this.contractItemValidator = contractItemValidator;
	}		
	
	public void setRequestService( IProcurementRequestService requestService ) {
		this.requestService = requestService;
	}
	
	private IValidation validateCore( IProcurementRequest procurementRequest ) {
		IValidation validation = new Validation();
		contractItemValidator.validateContractItems(procurementRequest.getContractItems(), validation);
		validateProcurementRequestReferences(procurementRequest, validation);
		validateTaxonomyReferences(procurementRequest, validation );
		if ((procurementRequest.getApprovesRequest() != null) && (procurementRequest.getAwardProcedureIdRef() == null))
			validation.addValidationError( new ValidationError( "awardProcedureIsNull", "Award procedure cannot be null for procurement request approvals.", "awardProcedure" ) );
//		if ( (procurementRequest.getResponsibilityAssumptionCode() != null) && (procurementRequest.getApprovesRequest() == null) )
//			validation.addValidationError( new ValidationError( "approvesRequestIsNull", "Approves request cannot be null since responsibilityAssumptionCode is not empty (request approval).", "approvesRequest" ) );
		if ((procurementRequest.getFulfilmentDate() != null) &&(procurementRequest.getFulfilmentDate().before(procurementRequest.getDateSigned())))
			validation.addValidationError( new ValidationError( "fulfilmentDatePriorToSignedDate", "Fulfilment date cannot be prior to signed date.", "filmentDate" ) );
		return validation;
	}
	
	private void validateTaxonomyReferences( IProcurementRequest procurementRequest, IValidation validation ) {
		ITaxonomy taxonomy;

		if (procurementRequest.getAwardProcedureIdRef() != null)
		{
			taxonomy = getTaxonomyService().getTaxonomy( "award_procedure", null );
			if ( !procurementRequest.getAwardProcedureTaxonomyReference().isValidForTaxonomy( taxonomy ) ) {		
				validation.addValidationError( new ValidationError( "awardProcedure", "Invalid award procedure.", "awardProcedure" ) );
			}
		}
	}	
	
	@Override
	public IValidation validateCreate( IProcurementRequest procurementRequest, Map<IAda, IPublicOrganizationDecision> relatedDecisions ) {
		IValidation validation = super.validateCreateDecision( procurementRequest, relatedDecisions );
		validation.addValidationErrors( validateCore( procurementRequest ) );
		if (procurementRequest.getApprovesRequest() != null)
		{
			IProcurementRequest approval = requestService.getRequestApproval(procurementRequest.getApprovesRequest().getId());
			if (approval != null )
				validation.addValidationError( new ValidationError( "approvedRequest", "cannot insert current procurement request due to existing approval.", "approvesRequest" ) );
		}
		return validation;
	}
	
	@Override
	public IValidation validateUpdate( IProcurementRequest procurementRequest, Map<IAda, IPublicOrganizationDecision> relatedDecisions ) {
		IValidation validation = super.validateUpdateDecision( procurementRequest, relatedDecisions );
		IProcurementRequest approval = requestService.getRequestApproval(procurementRequest);
		
		if (approval != null ){
			validation.addValidationError( new ValidationError( "approvedRequest", "cannot update current procurement request due to existing approval.", "approvesRequest" ) );
			logger.debug("approval:"+approval.getId());
			logger.debug("requestApproved:"+approval.getApprovesRequest().getId());
			logger.debug("request:"+ procurementRequest.getId());
		}
		
		validation.addValidationErrors( validateCore( procurementRequest ) );
		return validation;
	}
	
	private void validateProcurementRequestReferences( IProcurementRequest procurementRequest, IValidation validation ) {
		if ( procurementRequest.getApprovesRequest() != null && !procurementRequest.getApprovesRequest().isValid() ) {		
			validation.addValidationError( new ValidationError( "approvesRequest", "Invalid reference.", "approvesRequest" ) );
		}
		else
		{
			if ( (procurementRequest.getApprovesRequest() != null) && (procurementRequest.getDateSigned().before( procurementRequest.getApprovesRequest().getDateSigned().getTime())) )
				validation.addValidationError( new ValidationError( "dateSignedRequestPriorToRelated", "Invalid date signed. Date signed must be greater than the date signed of the procurement request approved", "approvesRequest.dateSigned" ) );
		}
	}	
}
