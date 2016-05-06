package gr.opengov.agora.converters;

import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.model.ArrayOfPayments;
import gr.opengov.agora.model.ArrayOfPaymentsShort;
import gr.opengov.agora.model.PaymentOXM;

public interface IPaymentOXMConverter extends IDecisionGenericConverter< IPayment, PaymentOXM, ArrayOfPayments, ArrayOfPaymentsShort >{

}
