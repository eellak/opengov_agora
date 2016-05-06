package gr.opengov.agora.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gr.opengov.agora.dao.IDecisionGenericDAO;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.InvalidContractReference;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.StringSearchParameter;
import gr.opengov.agora.util.PaginationInfo;

public class PaymentServiceImplReadOnly extends DecisionGenericService<IPayment> implements IPaymentService{
	private static final Logger logger = LoggerFactory.getLogger(DecisionGenericService.class);
	private IDecisionGenericDAO<IContract> contractDao;
	
	private IProcurementRequestService procurementRequestService;
	private IDecisionGenericService<INotice> noticeService;
	private IDecisionGenericService<IContract> contractService;
		
	public IProcurementRequestService getProcurementRequestService() {
		return procurementRequestService;
	}

	public void setProcurementRequestService(
			IProcurementRequestService procurementRequestService) {
		this.procurementRequestService = procurementRequestService;
	}

	public IDecisionGenericService<INotice> getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(IDecisionGenericService<INotice> noticeService) {
		this.noticeService = noticeService;
	}
	
	public IDecisionGenericService<IContract> getContractService() {
		return contractService;
	}

	public void setContractService(IDecisionGenericService<IContract> contractService) {
		this.contractService = contractService;
	}	

	private IDecisionGenericDAO<IContract> getContractDao() {
		return contractDao;
	}

	public void setContractDao(IDecisionGenericDAO<IContract> contractDao) {
		this.contractDao = contractDao;
	}

	@Override
	protected void checkReferences( IPayment payment ) {
		IContract paymentContract = payment.getContract();
		if ( paymentContract != null && paymentContract.isValid() && (paymentContract.getId() != null) && !getContractDao().exists( paymentContract.getId() ) ) {
			payment.setContract( new InvalidContractReference() );
		}		
	}	
	
	
	@Override
	public String getXmlPath(){
		return "payments/payment";
	}

	@Override
	public double getTotalPayedFor(IContract contract) {

		List<ISearchParameter> params = new ArrayList<ISearchParameter>();
		PaginationInfo pagination = new PaginationInfo();
		pagination.setLimit( Math.min( pagination.getLimit(), getAccessControl().getClient().getMaximumDecisionsRead() ) );
		addGetDecisionsFilters(params);
		List<IPayment> paymentsOfContract = getDao().getPaymentsForContract( pagination, params, contract );
//		for ( IPayment c: paymentsOfContract ) {
//			c.finalizeEntity();
//		}
		pagination.setLimit( paymentsOfContract.size() );		
		
		BigDecimal amount = new BigDecimal(0.0);
		for (IPayment payment:paymentsOfContract){
			for (IPaymentItem paymentItem:payment.getPaymentItems()){
				if (paymentItem.getContractItem().getContract() != null &&
//					paymentItem.getContractItem().getContract().equals(contract)) //TODO: check why it doesn't equal even when both sides' ids are equal// contractItem of paymentItem of payment doesn't finalize it's Contract in order to avoid cyclic references. This results in not equals(contract)
					paymentItem.getContractItem().getContract().getId().equals(contract.getId()) )
				{
					amount = amount.add(new BigDecimal(paymentItem.getContractItem().getCost().getCostBeforeVat().toString()));
				}
			}
		}
		return amount.doubleValue();
	}	
	
	@Override
	public double getTotalPayedFor(IProcurementRequest request) {

		List<ISearchParameter> params = new ArrayList<ISearchParameter>();
		PaginationInfo pagination = new PaginationInfo();
		pagination.setLimit( Math.min( pagination.getLimit(), getAccessControl().getClient().getMaximumDecisionsRead() ) );
		addGetDecisionsFilters(params);
		List<IPayment> paymentsOfRequest = getDao().getPaymentsForRequest( pagination, params, request );
//		for ( IPayment c: paymentsOfRequest ) {
//			c.finalizeEntity();
//		}
		pagination.setLimit( paymentsOfRequest.size() );		
		
		BigDecimal amount = new BigDecimal(0.0);
		for (IPayment payment:paymentsOfRequest){
			for (IPaymentItem paymentItem:payment.getPaymentItems()){
				if (paymentItem.getContractItem().getProcurementRequest() != null &&
					paymentItem.getContractItem().getProcurementRequest().equals(request))
				{
					amount = amount.add(new BigDecimal(paymentItem.getContractItem().getCost().getCostBeforeVat().toString()));
				}
			}
		}
		return amount.doubleValue();
	}	
}
