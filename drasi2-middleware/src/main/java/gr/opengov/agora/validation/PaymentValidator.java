package gr.opengov.agora.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.Payment;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.IPaymentService;
import gr.opengov.agora.service.IProcurementRequestService;
import gr.opengov.agora.util.Constants;
import gr.opengov.agora.util.Constants.Action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PaymentValidator extends PublicOrganizationDecisionValidator implements IValidator<IPayment> {
	private static final Logger logger = LoggerFactory.getLogger(PaymentValidator.class);
		
	private IPaymentItemValidator paymentItemValidator;
	private IValidator<IContract> contractValidator;
	
	private IPaymentService paymentService;
	private IDecisionGenericService<IProcurementRequest> procurementRequestService;
		
	public void setPaymentItemValidator(IPaymentItemValidator paymentItemValidator){
		this.paymentItemValidator = paymentItemValidator;
	}	
	
	public void setContractValidator(IValidator<IContract> contractValidator){
		this.contractValidator = contractValidator;
	}
	
	public void setProcurementRequestService(IProcurementRequestService procurementRequestService) {
		this.procurementRequestService = procurementRequestService;
		super.setProcurementRequestService(procurementRequestService);
	}	
	
	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}		
	
	private IValidation validateCore( IPayment payment, Map<IAda, IPublicOrganizationDecision> relatedDecisions, Action action ) {
		IValidation validation = new Validation();
		validateContractReference(payment, validation);
		paymentItemValidator.validatePaymentItems(payment.getPaymentItems(), validation);
		validateDateSigned(payment, relatedDecisions, validation);
		validateCost(payment, validation, relatedDecisions, action);
		//TODO: create contractPartyValidator and use it wherever (e.g. payment, contract) a contractParty is used
		return validation;
	}
	
	private void validateDateSigned(IPayment payment, Map<IAda, IPublicOrganizationDecision> relatedDecisions, IValidation validation){
		logger.debug("validating payment datesigned");
		if ( (payment.getContract() != null) && (payment.getContract().isValid())){
			if ( (payment.getContract().getDateSigned() == null) || ( payment.getContract().getDateSigned().compareTo( payment.getDateSigned() ) ) > 0 ) {
				validation.addValidationError( new ValidationError( "dateSignedPaymentPriorToRelated", "Invalid date signed. Date signed must be greater than the date signed of the related contract or procurement request", "payment.dateSigned" ) );
			}
		}
		super.validateDateSigned(payment, relatedDecisions, validation);
	}
	
	public void validateRelatedAdas( IPublicOrganizationDecision payment, Map<IAda, IPublicOrganizationDecision> relatedDecisions, IValidation validation ){
		super.validateRelatedAdas(payment, relatedDecisions, validation);
		if ( (relatedDecisions != null) && (relatedDecisions.size() > 0) ){
			Set<String> relatedAdas = new HashSet<String>();
			int i=0;
			for (Map.Entry<IAda, IPublicOrganizationDecision> entry : relatedDecisions.entrySet())
			{
				if ( !((entry.getKey().getAdaType().equals("ProcurementRequest")) || (entry.getKey().getAdaType().equals("Notice"))) ){
					validation.addValidationError( new ValidationError( "invalid" + entry.getKey().getAdaType() + "RelatedAda", "The specified related ada type is invalid.", "relatedAda[" + i + "]" ) );
				}
				else{
				    if ( !entry.getValue().isValid() ){
				    	validation.addValidationError( new ValidationError( "invalid" + entry.getKey().getAdaType() + "RelatedAda", "The specified related ada is invalid.", "relatedAda[" + i + "]" ) );
				    }
				    else{
				    	if ( entry.getValue() instanceof IProcurementRequest){
				    		if ( !((((IProcurementRequest)entry.getValue()).getApprovesRequest() != null ) &&
				    				(((IProcurementRequest)entry.getValue()).getApprovesRequest().isValid())) ){
				    			validation.addValidationError( new ValidationError( "notApprovalRequestPayed", "You cannot add payments for not approval requests", "relatedAda[" + i + "]" ) );
				    		}
				    	}
				    	if (!relatedAdas.add(entry.getKey().getAdaCode())){
				    		validation.addValidationError( new ValidationError( "requestAdaPresentTwice", "Each procurement request must be inserted one time in related adas", "relatedAda[" + i + "]" ) );
				    	}
				    		
				    }
				}		    
				i++;
			}
			
			if ((relatedAdas.size() > 0) &&  (((IPayment)payment).getContract() != null )){
				validation.addValidationError( new ValidationError( "paymentRelatedToContractAndRequest", "The specified payment cannot refer to both contract and procurement request", "relatedAda" ) );
			}
			
			int j=0;
			for (IPaymentItem paymentItem:((IPayment)payment).getPaymentItems()){
				if (paymentItem.getContractItem().getContract() != null) {
					if ( 
							( !paymentItem.getContractItem().getContract().isValid() ) ||
							( !paymentItem.getContractItem().getContract().equals(((IPayment)payment).getContract()) )
						){
						validation.addValidationError( new ValidationError( "paymentItemReferedToNotRelatedContract", "The contract item of the specified payment item must belong to the related contract", "paymentItem[" + j + "].contractItem" ) );
					}
				}
				j++;
			}
			j=0;
			if ( relatedAdas.size() > 0 ){
				for (IPaymentItem paymentItem:((IPayment)payment).getPaymentItems()){
					if (paymentItem.getContractItem().getNotice() != null){
						if (!relatedAdas.contains(paymentItem.getContractItem().getNotice().getId()) ){
							validation.addValidationError( new ValidationError( "paymentItemReferedToNotRelatedNotice", "The contract item of the specified payment item must refer to a notice existed in related adas", "paymentItem[" + j + "].contractItem" ) );
						}
					}
					else{
						if (paymentItem.getContractItem().getProcurementRequest() != null){
							if (!relatedAdas.contains(paymentItem.getContractItem().getProcurementRequest().getId()) ){
								validation.addValidationError( new ValidationError( "paymentItemReferedToNotRelatedRequest", "The contract item of the specified payment item must refer to a procurement request existed in related adas", "paymentItem[" + j + "].contractItem" ) );
							}
						}
					}
					j++;
				}
			}
		}
	}
	
	private void validateCost(IPayment payment, IValidation validation, Map<IAda, IPublicOrganizationDecision> relatedDecisions, Action action){
		if ( (payment.getContract() != null) && (payment.getContract().isValid())){
			double alreadyPayedAmount = 0.0;
			if (action == Action.CREATE){
				alreadyPayedAmount = paymentService.getTotalPayedFor(payment.getContract());
			}
			if ( (payment.getTotalCostBeforeVat() + alreadyPayedAmount) > (payment.getContract().getTotalCostBeforeVat() * (1+Constants.COSTOVERRUN)) )
				validation.addValidationError( new ValidationError( "paymentCostOverrun", "Payment cost cannot be greater than contract cost plus " + Double.valueOf(Constants.COSTOVERRUN*100).toString() + "%", "payment.totalCostBeforeVat" ) );				
		}
//		else // this check is ignored due to high complexity and redesing needed
//			if ( (payment.getRelatedAdas() != null) && (payment.getRelatedAdas().size() > 0))
//			{
//				for (Map.Entry<IAda, IPublicOrganizationDecision> entry : relatedDecisions.entrySet()){
//					BigDecimal payingAmount = new BigDecimal(0.0);
//					for (IPaymentItem paymentItem:payment.getPaymentItems()){
//						if (paymentItem.getContractItem().getProcurementRequest().getId().equals(entry.getValue().getId())){
//							payingAmount = payingAmount.add(new BigDecimal(paymentItem.getContractItem().getCost().getCostBeforeVat().toString()));
//						}
//					}					
//					double payedAmount = 0.0;
//					
//					if ((entry.getKey().equals("ProcurementRequest")) && (entry.getValue() instanceof IProcurementRequest) && (entry.getValue().isValid()) ){
//
//						
//						if (action == Action.CREATE){
//							payedAmount = paymentService.getTotalPayedFor((IProcurementRequest)entry.getValue());
//						}
//						BigDecimal toBePayedTotal = new BigDecimal(0.0);
//						for (IContractItem contractItem:entry.getValue().getContractItems()){
//							toBePayedTotal = toBePayedTotal.add(new BigDecimal(contractItem.getCost().getCostBeforeVat().toString()));
//						}
//						logger.debug("payed ammount for request " + entry.getValue().getId() + " : " + payedAmount);
//						logger.debug("paying ammount for request " + entry.getValue().getId() + " : " + payingAmount);
//						
//						if ((payingAmount.doubleValue() + payedAmount) > (toBePayedTotal.doubleValue()* (1+Constants.COSTOVERRUN) ))
//							validation.addValidationError( new ValidationError( "paymentCostOverrun", "Payment cost cannot be greater than procurement request cost plus " + Double.valueOf(Constants.COSTOVERRUN*100).toString() + "%", "payment.totalCostBeforeVat" ) );
//					}
//					
//					if ((entry.getKey().equals("Contract")) && (entry.getValue() instanceof IContract) && (entry.getValue().isValid()) ){
//
//						if (action == Action.CREATE){
//							payedAmount = paymentService.getTotalPayedFor((IContract)entry.getValue());
//						}
//						BigDecimal toBePayedTotal = new BigDecimal(0.0);
//						for (IContractItem contractItem:entry.getValue().getContractItems())
//							toBePayedTotal = toBePayedTotal.add(new BigDecimal(contractItem.getCost().getCostBeforeVat().toString()));
//						
//						if ((payingAmount.doubleValue() + payedAmount) > (toBePayedTotal.doubleValue()* (1+Constants.COSTOVERRUN) ))
//							validation.addValidationError( new ValidationError( "paymentCostOverrun", "Payment cost cannot be greater than contract cost plus " + Double.valueOf(Constants.COSTOVERRUN*100).toString() + "%", "payment.totalCostBeforeVat" ) );
//					}
//					
//				}
//
//			}
	}	
	
	private void validateContractReference( IPayment payment, IValidation validation ) {
		if ( payment.getContract() != null && !payment.getContract().isValid() ) {		
			validation.addValidationError( new ValidationError( "paymentContract", "Invalid Contract reference.", "contract" ) );
		}
	}
	
	@Override
	public IValidation validateCreate( IPayment payment, Map<IAda, IPublicOrganizationDecision> relatedDecisions ) {
		IValidation validation = super.validateCreateDecision( payment, relatedDecisions );
		validation.addValidationErrors( validateCore( payment, relatedDecisions, Action.CREATE ) );		
		return validation;
	}
	
	@Override
	public IValidation validateUpdate( IPayment payment, Map<IAda, IPublicOrganizationDecision> relatedDecisions  ) {
		IValidation validation = super.validateUpdateDecision( payment, relatedDecisions );
		validation.addValidationErrors( validateCore( payment, relatedDecisions, Action.UPDATE ) );
		return validation;
	}
}
