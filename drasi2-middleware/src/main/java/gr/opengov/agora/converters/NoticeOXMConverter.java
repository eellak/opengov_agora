package gr.opengov.agora.converters;


import gr.opengov.agora.domain.ContractItem;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.Notice;
import gr.opengov.agora.model.ArrayOfContractItems;
import gr.opengov.agora.model.ArrayOfNotices;
import gr.opengov.agora.model.ArrayOfNoticesShort;
import gr.opengov.agora.model.ArrayOfProcurementRequestIds;
import gr.opengov.agora.model.ContractItemOXM;
import gr.opengov.agora.model.NoticeOXM;
import gr.opengov.agora.model.NoticeShortOXM;
import gr.opengov.agora.model.TaxonomyReferenceOXM;
import gr.opengov.agora.service.IDecisionGenericService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoticeOXMConverter implements INoticeOXMConverter {
	private static final Logger logger = LoggerFactory.getLogger(NoticeOXMConverter.class);
	
	private IDecisionGenericService<INotice> noticeService;	
	private IPublicOrganizationDecisionOXMConverter podConverter;
	private ICommonsOXMConverter commonsConverter;	
	
	private ProcurementRequestOXMConverter requestConverter;
		
	public void setNoticeService( IDecisionGenericService<INotice> noticeService ) {
		this.noticeService = noticeService;
	}
	
	public void setPodConverter(IPublicOrganizationDecisionOXMConverter podConverter) {
		this.podConverter = podConverter;
	}

	public void setCommonsConverter(ICommonsOXMConverter commonsConverter) {
		this.commonsConverter = commonsConverter;
	}

	public void setRequestConverter(ProcurementRequestOXMConverter requestConverter) {
		this.requestConverter = requestConverter;
	}	
	
	public INotice convertNotice( NoticeOXM oxm, INotice obj ) {
		podConverter.convertPublicOrganizationDecision(oxm, obj);

		obj.setSince( oxm.getSince() );
		obj.setUntil( oxm.getUntil() );
		obj.setAwardProcedureIdRef(oxm.getAwardProcedure().getIdRef());
		if (oxm.getContractItems() != null){
			for ( ContractItemOXM item: oxm.getContractItems().getItem() ) {
				IContractItem contractItem = commonsConverter.convertContractItem( item, new ContractItem() );
				if (oxm.getId() != null){
					contractItem.setNotice( noticeService.get(oxm.getId()) );
				}				
				obj.getContractItems().add( contractItem );
			}
		}
		return obj;
	}
	
	public NoticeOXM convertNotice( INotice obj, NoticeOXM oxm ) {
		podConverter.convertPublicOrganizationDecision(obj, oxm);
		
		oxm.setSince( obj.getSince() );
		oxm.setUntil( obj.getUntil() );	
		if (obj.getAwardProcedureIdRef() != null){
			TaxonomyReferenceOXM ref2 = new TaxonomyReferenceOXM();
			ref2.setIdRef(obj.getAwardProcedureIdRef());
			oxm.setAwardProcedure(ref2);
		}
		
		ArrayOfContractItems items = new ArrayOfContractItems();
		for ( IContractItem item: obj.getContractItems() ) {
			ContractItemOXM contractItem = commonsConverter.convertContractItem( item, new ContractItemOXM() );
			contractItem.setNotice(obj.getId());			
			
			items.getItem().add( contractItem );
		}		
		oxm.setContractItems(items);
		
		return oxm;
	}
	
	public NoticeShortOXM convertNotice( INotice obj, NoticeShortOXM oxm ) {
		podConverter.convertPublicOrganizationDecision(obj, oxm);
		oxm.setSince( obj.getSince() );
		oxm.setUntil( obj.getUntil() );	
		oxm.setTotalCostBeforeVAT(obj.getTotalCostBeforeVat());
		return oxm;
	}
	
	@Override
	public INotice toObject( NoticeOXM oxm ) {
		return convertNotice( oxm, new Notice() );
	}
	
	@Override
	public NoticeOXM toXML( INotice obj ) {
		return convertNotice( obj, new NoticeOXM() );
	}
	
	@Override
	public List<INotice> toObjectList( ArrayOfNotices notices ) {
		List<INotice> list = new ArrayList<INotice>();
		for ( NoticeOXM notice: notices.getNotice() ) {
			list.add( toObject( notice ) );
		}
		return list;
	}

	@Override
	public ArrayOfNotices toXMLList( List<INotice> notices ) {
		ArrayOfNotices list = new ArrayOfNotices();
		for ( INotice notice: notices ) {
			list.getNotice().add( toXML( notice ) );
		}
		return list;
	}

	@Override
	public ArrayOfNoticesShort toXMLShort( List<INotice> notices ) {
		ArrayOfNoticesShort list = new ArrayOfNoticesShort();
		for ( INotice notice: notices ) {			
			list.getNotice().add( convertNotice( notice, new NoticeShortOXM() ) );
		}
		return list;
	}
	
	@Override
	public INotice getNewInstance(){
		return new Notice();
	}
	
	@Override
	public NoticeOXM getNewInstanceOXM(){
		return new NoticeOXM();
	}
}