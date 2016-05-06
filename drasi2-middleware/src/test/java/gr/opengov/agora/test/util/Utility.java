package gr.opengov.agora.test.util;

import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.ProcurementRequestOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.model.PublicOrganizationDecisionOXM;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utility {
	XMLUtils xmlUtils;
	
	public Utility( XMLUtils xmlUtils ) {
		this.xmlUtils = xmlUtils; 
	}
	
	public List<String> getUniqueDocumentCodes(List<?> decisions) throws IOException {
		List<String> list = new ArrayList<String>();
		for (Object decision : decisions) {
			list.add(((PublicOrganizationDecisionOXM)decision).getUniqueDocumentCode());
		}
		return list;
	}
	
	public List<String> getIds(List<?> decisions) throws IOException {
		List<String> list = new ArrayList<String>();
		for (Object decision : decisions) {
			list.add(((PublicOrganizationDecisionOXM)decision).getId());
		}
		return list;
	}	
	
	public List<String> getUniqueDocumentCodesFromContract(String xmlFile) throws IOException {
		return getUniqueDocumentCodes(xmlUtils.unmarshal(xmlFile, ContractsOXM.class).getContract());		
	}	
	
	public List<String> getUniqueDocumentCodesFromDecision(String xmlFile, Class<?> cl) throws IOException {
		if (cl.isAssignableFrom(IContract.class))
			return getUniqueDocumentCodesFromContract(xmlFile);
		if (cl.isAssignableFrom(IPayment.class))
			return getUniqueDocumentCodesFromPayment(xmlFile);
		if (cl.isAssignableFrom(IProcurementRequest.class))
			return getUniqueDocumentCodesFromProcurementRequest(xmlFile);
		return null;				
	}		
	
	public List<String> getIdsFromContract(String xmlFile) throws IOException {
		return getIds(xmlUtils.unmarshal(xmlFile, ContractsOXM.class).getContract());		
	}		
	
	
	
//	public List<String> getUniqueDocumentCodes(String xmlFile)
//			throws IOException {
//		List<String> list = new ArrayList<String>();
//		ContractsOXM contracts = xmlUtils
//				.unmarshal(xmlFile, ContractsOXM.class);
//		for (ContractOXM contract : contracts.getContract()) {
//			list.add(contract.getUniqueDocumentCode());
//		}
//		return list;
//	}
	
//	public List<String> getUniqueDocumentCodesFromProcurementRequest(String xmlFile) throws IOException {
//		List<String> list = new ArrayList<String>();
//		ProcurementRequestsOXM procurementRequests = xmlUtils.unmarshal(xmlFile, ProcurementRequestsOXM.class);
//		for (ProcurementRequestOXM procurementRequestOXM : procurementRequests.getRequest() ) {
//			list.add(procurementRequestOXM.getUniqueDocumentCode());
//		}
//		return list;
//	}	
	
	public List<String> getUniqueDocumentCodesFromProcurementRequest(String xmlFile) throws IOException {
		return getUniqueDocumentCodes(xmlUtils.unmarshal(xmlFile, ProcurementRequestsOXM.class).getRequest());
	}
	
	public List<String> getUniqueDocumentCodesFromPayment(String xmlFile) throws IOException {
		return getUniqueDocumentCodes(xmlUtils.unmarshal(xmlFile, PaymentsOXM.class).getPayment());
	}
	
	public void saveFile( String output, String filename ) throws IOException {
		FileWriter writer = new FileWriter( filename );
		writer.write( output );
		writer.close();		
	}

}
