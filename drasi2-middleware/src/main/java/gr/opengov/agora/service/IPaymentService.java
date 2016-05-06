package gr.opengov.agora.service;

import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;

public interface IPaymentService extends IDecisionGenericService<IPayment> {
	public abstract double getTotalPayedFor(IContract contract);

	public abstract double getTotalPayedFor(IProcurementRequest request);
}
