package gr.opengov.agora.validation;

import java.util.List;

import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.util.Constants.Action;

public interface IPaymentItemValidator {

	public IValidation validatePaymentItems(List<IPaymentItem> paymentItems, IValidation validation);

}