package gr.opengov.agora.service;

import gr.opengov.agora.domain.INotice;

public class NoticeServiceImplReadOnly extends DecisionGenericService<INotice> {

	private IProcurementRequestService procurementRequestService;
	
	public IProcurementRequestService getProcurementRequestService() {
		return procurementRequestService;
	}

	public void setProcurementRequestService(
			IProcurementRequestService procurementRequestService) {
		this.procurementRequestService = procurementRequestService;
	}
	
	@Override
	public String getXmlPath(){
		return "notices/notice";
	}

}
