package gr.opengov.agora.converters;


import gr.opengov.agora.domain.ContractItem;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.domain.InvalidContractReference;
import gr.opengov.agora.domain.InvalidProcurementRequestReference;
import gr.opengov.agora.domain.ProcurementRequest;
import gr.opengov.agora.domain.PublicOrganizationDecision;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.model.ArrayOfContractItems;
import gr.opengov.agora.model.ArrayOfContractsShort;
import gr.opengov.agora.model.ArrayOfProcurementRequests;
import gr.opengov.agora.model.ArrayOfProcurementRequestsShort;
import gr.opengov.agora.model.ContractItemOXM;
import gr.opengov.agora.model.ContractShortOXM;
import gr.opengov.agora.model.ProcurementRequestOXM;
import gr.opengov.agora.model.ProcurementRequestShortOXM;
import gr.opengov.agora.model.PublicOrganizationDecisionOXM;
import gr.opengov.agora.model.TaxonomyReferenceOXM;
import gr.opengov.agora.service.IDecisionGenericService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcurementRequestOXMConverter implements IProcurementRequestOXMConverter {
	private static final Logger logger = LoggerFactory.getLogger(ProcurementRequestOXMConverter.class);
	private IPublicOrganizationDecisionOXMConverter podConverter;
	private ICommonsOXMConverter commonsConverter;
	private IDecisionGenericService<IProcurementRequest> requestService;
	
	public void setPodConverter(IPublicOrganizationDecisionOXMConverter podConverter) {
		this.podConverter = podConverter;
	}

	public void setCommonsConverter(ICommonsOXMConverter commonsConverter) {
		this.commonsConverter = commonsConverter;
	}
	
	public void setRequestService( IDecisionGenericService<IProcurementRequest> requestService ) {
		this.requestService = requestService;
	}	
	
	public IProcurementRequest convertProcurementRequest(ProcurementRequestOXM oxm, IProcurementRequest obj) {
		podConverter.convertPublicOrganizationDecision(oxm, obj);
		obj.setContractItems(new ArrayList<IContractItem>());

		if (oxm.getContractItems() != null){
			for ( ContractItemOXM item: oxm.getContractItems().getItem() ) {
				IContractItem contractItem = commonsConverter.convertContractItem( item, new ContractItem() );
				if (oxm.getId() != null){
					contractItem.setProcurementRequest( requestService.get(oxm.getId()) );
				}				
				obj.getContractItems().add( contractItem );
			}
		}		
		
		
		if ( oxm.getApprovesRequest() != null ) {
			try {
				IProcurementRequest ref = requestService.get( oxm.getApprovesRequest() );
				//logger.info( "REF: " + ref );
				obj.setApprovesRequest( (ref != null)?ref:new InvalidProcurementRequestReference() );
			}
			catch ( DecisionNotFoundException e ) {
				logger.info( "INVALID REFERENCE" );
				obj.setApprovesRequest( new InvalidProcurementRequestReference() );
			}
		}		
		
		if (oxm.getAwardProcedure() != null)
			obj.setAwardProcedureIdRef(oxm.getAwardProcedure().getIdRef());
		obj.setResponsibilityAssumptionCode(oxm.getResponsibilityAssumptionCode());
		obj.setFulfilmentDate(oxm.getFulfilmentDate());
		obj.setEppApproved(oxm.isEppApproved());
		obj.setCode(oxm.getCode());
		return obj;
	}

	public ProcurementRequestOXM convertProcurementRequest(IProcurementRequest obj, ProcurementRequestOXM oxm) {
		podConverter.convertPublicOrganizationDecision(obj, oxm);
		
		ArrayOfContractItems items = new ArrayOfContractItems();
		for ( IContractItem item: obj.getContractItems() ) {
			ContractItemOXM contractItem = commonsConverter.convertContractItem( item, new ContractItemOXM() );
			contractItem.setProcurementRequest(obj.getId());
			
			items.getItem().add( contractItem );			
			
		}		
		oxm.setContractItems(items);
		
		if ( obj.getApprovesRequest() != null && !( obj.getApprovesRequest() instanceof InvalidProcurementRequestReference ) ) oxm.setApprovesRequest( obj.getApprovesRequest().getId() );
		
		if (obj.getAwardProcedureIdRef() != null){
			TaxonomyReferenceOXM ref = new TaxonomyReferenceOXM();
			ref.setIdRef(obj.getAwardProcedureIdRef());
			oxm.setAwardProcedure(ref);
		}
		oxm.setResponsibilityAssumptionCode(obj.getResponsibilityAssumptionCode());
		oxm.setFulfilmentDate(obj.getFulfilmentDate());
		oxm.setEppApproved(obj.isEppApproved());
		oxm.setCode(obj.getCode());
		return oxm;
	}
	
	public ProcurementRequestShortOXM convertProcurementRequest( IProcurementRequest obj, ProcurementRequestShortOXM oxm ) {
		podConverter.convertPublicOrganizationDecision(obj, oxm);
		oxm.setTotalCostBeforeVAT(obj.getTotalCostBeforeVat());
		return oxm;
	}	

	@Override
	public IProcurementRequest toObject(ProcurementRequestOXM oxm) {
		return convertProcurementRequest( oxm, new ProcurementRequest() );
	}

	@Override
	public ProcurementRequestOXM toXML(IProcurementRequest procurementRequest) {
		return convertProcurementRequest( procurementRequest, new ProcurementRequestOXM() );
	}

	@Override
	public List<IProcurementRequest> toObjectList(ArrayOfProcurementRequests procurementRequests) {
		List<IProcurementRequest> list = new ArrayList<IProcurementRequest>();
		for ( ProcurementRequestOXM procurementRequest: procurementRequests.getRequest() ) {
			list.add( toObject( procurementRequest ) );
		}
		return list;
	}

	@Override
	public ArrayOfProcurementRequests toXMLList(List<IProcurementRequest> procurementRequests) {
		ArrayOfProcurementRequests list = new ArrayOfProcurementRequests();
		for ( IProcurementRequest procurementRequest: procurementRequests ) {
			list.getRequest().add(toXML( procurementRequest ));
		}
		return list;
	}

	@Override
	public ArrayOfProcurementRequestsShort toXMLShort( List<IProcurementRequest> procurementRequests) {
		ArrayOfProcurementRequestsShort list = new ArrayOfProcurementRequestsShort();
		for ( IProcurementRequest request: procurementRequests ) {			
			list.getProcurementRequest().add( convertProcurementRequest( request, new ProcurementRequestShortOXM() ) );
		}
		return list;
	}

	@Override
	public IProcurementRequest getNewInstance() {
		return new ProcurementRequest();
	}

	@Override
	public ProcurementRequestOXM getNewInstanceOXM() {
		return new ProcurementRequestOXM();
	}	
	
}