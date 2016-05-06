package gr.opengov.agora.test;

import gr.opengov.agora.converters.IPaymentOXMConverter;
import gr.opengov.agora.converters.IDecisionGenericConverter;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.model.ArrayOfPayments;
import gr.opengov.agora.model.ArrayOfPaymentsShort;
import gr.opengov.agora.model.PaymentOXM;
import gr.opengov.agora.test.util.GenericDomainTest;

import javax.annotation.Resource;

public class SimplePaymentTest extends GenericDomainTest< IPayment, PaymentOXM, ArrayOfPayments, ArrayOfPaymentsShort>{
	
	public SimplePaymentTest() {
		super( IPayment.class, PaymentOXM.class, ArrayOfPayments.class );
	}

	@Resource( name="paymentOxmConverter" )
	private IPaymentOXMConverter converter;

	@Override
	protected String getSingleEntityXml() {
		return "single-payment.xml";
	}

	@Override
	protected IDecisionGenericConverter<IPayment, PaymentOXM, ArrayOfPayments, ArrayOfPaymentsShort> getConverter() {
		return converter;
	}
}
