package gr.opengov.agora.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.InvalidProcurementRequestReference;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.search.ApprovedFilterSearchParameter;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.IdSearchParameter;
import gr.opengov.agora.search.StringSearchParameter;
import gr.opengov.agora.util.Constants;
import gr.opengov.agora.util.PaginationInfo;

public class ProcurementRequestServiceImpl extends DecisionGenericService<IProcurementRequest> implements IProcurementRequestService{
//	private IDecisionGenericDAO<IProcurementRequest> procurementRequestDao;
//	
//	private IDecisionGenericDAO<IProcurementRequest> getProcurementRequestDao() {
//		return procurementRequestDao;
//	}
//	
//	public void setProcurementRequesttDao(IDecisionGenericDAO<IProcurementRequest> procurementRequestDao) {
//		this.procurementRequestDao = procurementRequestDao;
//	}
	private static final Logger logger = LoggerFactory.getLogger(ProcurementRequestServiceImpl.class);
	
	@Override
	public String getXmlPath(){
		return "procurementRequests/procurementRequest";
	}
	
	@Override
	public IProcurementRequest getRequestApproval(IProcurementRequest request){
		return getRequestApproval(request.getId());
	}
	
	@Override
	public IProcurementRequest getRequestApproval(String requestId){
		List<ISearchParameter> searchParams = new ArrayList<ISearchParameter>();
		searchParams.add(new StringSearchParameter("approvesRequest.id", requestId ));
		addGetDecisionsFilters(searchParams);
//		IProcurementRequest approvalRequest = getDao().getUnmanaged(searchParams, false);
//		if (approvalRequest != null)
//			approvalRequest.finalizeEntity();
//		return approvalRequest;
		List<IProcurementRequest> approvalRequests = getAll(new PaginationInfo(), searchParams);
		if ((approvalRequests != null) && (approvalRequests.size() > 0) )
		{
			IProcurementRequest approvalRequest = approvalRequests.get(0);
			approvalRequest.finalizeEntity();
			return approvalRequest;
		}
		return null;		
	}	
	
	@Override
	public boolean isRequestApproval(String requestId){
		List<ISearchParameter> searchParams = new ArrayList<ISearchParameter>();
		try {
			searchParams.add(new IdSearchParameter(requestId ));
		} catch (InvalidSearchParameterException e) {
			logger.error("Invalid search parameter exception: " + e.toString());
			e.printStackTrace();
			return false;
		}
		searchParams.add(new ApprovedFilterSearchParameter( "approvedfilter", Constants.APPROVAL_REQUESTS_FILTER_KEY ));
		
		addGetDecisionsFilters(searchParams);
		
		return exist(searchParams);
	}	
	
	@Override
	protected void checkReferences( IProcurementRequest procurementRequest ) {
		IProcurementRequest approvesRequest = procurementRequest.getApprovesRequest();
		if (approvesRequest != null)
			if ( (!approvesRequest.isValid()) || (!exists(approvesRequest.getId())) ) {
				procurementRequest.setApprovesRequest(new InvalidProcurementRequestReference());
		}
	}
	
	@Override
	public IProcurementRequest get(String id) throws DecisionNotFoundException {
		return super.get(id);
	}

}
