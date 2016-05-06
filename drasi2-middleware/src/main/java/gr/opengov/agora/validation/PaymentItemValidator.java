package gr.opengov.agora.validation;


import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.util.Constants.Action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PaymentItemValidator implements IPaymentItemValidator {
	private static final Logger logger = LoggerFactory.getLogger(PaymentItemValidator.class);
	
	private IContractItemValidator contractItemValidator;
	private IContractPartyValidator contractPartyValidator;
	
	public void setContractItemValidator(IContractItemValidator contractItemValidator){
		this.contractItemValidator = contractItemValidator;
	}		

	public void setContractPartyValidator(IContractPartyValidator contractPartyValidator){
		this.contractPartyValidator = contractPartyValidator;
	}	
	
	@Override
	public IValidation validatePaymentItems( List<IPaymentItem> paymentItems, IValidation validation) {
		if ( paymentItems.size() == 0 ) {
			validation.addValidationError( new ValidationError( "paymentItems", "No payment items.", "paymentItems" ) );
		}
		
		for ( int i = 0; i < paymentItems.size(); i++ ) {
			contractItemValidator.validateContractItem(paymentItems.get(i).getContractItem(), validation, i);
			if (paymentItems.get(i).getContractParty() == null){
				validation.addValidationError( new ValidationError( "paymentItemInvalidContractParty", "Invalid contract party. contract party cannot be empty", "paymentItems/item[" + i + "]/contractParty" ) );
			}
			else
				validation.addValidationErrors(contractPartyValidator.validateSecondaryParty(paymentItems.get(i).getContractParty(), "paymentItems/item[" + i + "]/contractParty"));
		}
		return validation;
	}


}
