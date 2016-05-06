package gr.opengov.agora.service;

import gr.opengov.agora.domain.IProcurementRequest;

import java.util.List;

public interface IProcurementRequestService extends IDecisionGenericService<IProcurementRequest> {

	public IProcurementRequest getRequestApproval(IProcurementRequest request);

	public IProcurementRequest getRequestApproval(String requestId);

	public boolean isRequestApproval(String requestId);

}
