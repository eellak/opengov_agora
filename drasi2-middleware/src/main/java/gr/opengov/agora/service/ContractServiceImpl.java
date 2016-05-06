package gr.opengov.agora.service;

import gr.opengov.agora.domain.ICmsEntity;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.InvalidCmsEntityReference;
import gr.opengov.agora.domain.InvalidContractReference;

public class ContractServiceImpl extends DecisionGenericService<IContract> {

	private IProcurementRequestService procurementRequestService;
	private IDecisionGenericService<INotice> noticeService;	
	
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
	
	@Override
	protected void checkReferences(IContract contract) {
		IContract extendsContract = contract.getExtendsContract();
		IContract changesContract = contract.getChangesContract();
		ICmsEntity replacesEntity = contract.getReplaces();

		if (extendsContract != null && extendsContract.isValid()
				&& !exists(extendsContract.getId())) {
			contract.setExtendsContract(new InvalidContractReference());
		}
		if (changesContract != null && changesContract.isValid()
				&& !exists(changesContract.getId())) {
			contract.setChangesContract(new InvalidContractReference());
		}
		if (replacesEntity != null && replacesEntity.isValid()
				&& !exists(replacesEntity.getId())) {
			contract.setReplaces(new InvalidCmsEntityReference());
		}

	}
	
	@Override
	public String getXmlPath(){
		return "contracts/contract";
	}

}
