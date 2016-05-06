package gr.opengov.agora.converters;


import gr.opengov.agora.domain.ContractParty;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.InvalidContractReference;
import gr.opengov.agora.domain.Payment;
import gr.opengov.agora.domain.PaymentItem;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.model.ArrayOfContractsShort;
import gr.opengov.agora.model.ArrayOfPaymentItems;
import gr.opengov.agora.model.ArrayOfPayments;
import gr.opengov.agora.model.ArrayOfPaymentsShort;
import gr.opengov.agora.model.ContractPartyOXM;
import gr.opengov.agora.model.ContractShortOXM;
import gr.opengov.agora.model.PaymentItemOXM;
import gr.opengov.agora.model.PaymentOXM;
import gr.opengov.agora.model.PaymentShortOXM;
import gr.opengov.agora.service.IDecisionGenericService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentOXMConverter implements IPaymentOXMConverter {
	private static final Logger logger = LoggerFactory.getLogger(PaymentOXMConverter.class);
	
	private IDecisionGenericService<IContract> contractService;	
	private IPublicOrganizationDecisionOXMConverter podConverter;
	private ICommonsOXMConverter commonsConverter;
		
	public void setContractService( IDecisionGenericService<IContract> contractService ) {
		this.contractService = contractService;
	}
	
	public void setPodConverter(IPublicOrganizationDecisionOXMConverter podConverter) {
		this.podConverter = podConverter;
	}

	public void setCommonsConverter(ICommonsOXMConverter commonsConverter) {
		this.commonsConverter = commonsConverter;
	}

	public IPayment convertPayment( PaymentOXM oxm, IPayment obj ) {
		podConverter.convertPublicOrganizationDecision(oxm, obj);
		
		if (oxm.getContractId() != null)
			try {
					IContract ref = contractService.get(oxm.getContractId());
					obj.setContract((ref != null)?ref:new InvalidContractReference());
			} catch (DecisionNotFoundException e) {
				logger.info( "INVALID REFERENCE" );
				obj.setContract( new InvalidContractReference() );
			}
		
		for ( PaymentItemOXM item: oxm.getPaymentItems().getPaymentItem() ) {
			obj.getPaymentItems().add( commonsConverter.convertPaymentItem( item, new PaymentItem() ) );
		}
		
		obj.setResponsibilityAssumptionCode(oxm.getResponsibilityAssumptionCode());
		obj.setPrimaryParty(commonsConverter.convertContractParty(oxm.getPrimaryParty(), new ContractParty()));

		return obj;
	}
	
	public PaymentOXM convertPayment( IPayment obj, PaymentOXM oxm ) {
		podConverter.convertPublicOrganizationDecision(obj, oxm);
		if ( obj.getContract() != null ) {
			oxm.setContractId(obj.getContract().getId());
		}
		else {
			oxm.setContractId( null );
		}
		ArrayOfPaymentItems items = new ArrayOfPaymentItems();		
		for ( IPaymentItem item: obj.getPaymentItems() ) {
			items.getPaymentItem().add(commonsConverter.convertPaymentItem(item, new PaymentItemOXM()));			
		}
		oxm.setPaymentItems( items );
		oxm.setResponsibilityAssumptionCode(obj.getResponsibilityAssumptionCode());
		oxm.setPrimaryParty(commonsConverter.convertContractParty(obj.getPrimaryParty(), new ContractPartyOXM()));
		return oxm;
	}	

	public PaymentShortOXM convertPayment( IPayment obj, PaymentShortOXM oxm ) {
		podConverter.convertPublicOrganizationDecision(obj, oxm);
		if (obj.getContract() != null)
			oxm.setContractId(obj.getContract().getId());
		oxm.setTotalCostBeforeVAT(obj.getTotalCostBeforeVat());
		return oxm;
	}	
	
	@Override
	public IPayment toObject( PaymentOXM oxm ) {
		return convertPayment( oxm, new Payment() );
	}
	
	@Override
	public PaymentOXM toXML( IPayment obj ) {
		return convertPayment( obj, new PaymentOXM() );
	}
	
	@Override
	public List<IPayment> toObjectList( ArrayOfPayments payments ) {
		List<IPayment> list = new ArrayList<IPayment>();
		for ( PaymentOXM payment: payments.getPayment() ) {
			list.add( toObject( payment ) );
		}
		return list;
	}

	@Override
	public ArrayOfPayments toXMLList( List<IPayment> payments ) {
		ArrayOfPayments list = new ArrayOfPayments();
		for ( IPayment payment: payments ) {
			list.getPayment().add( toXML( payment ) );
		}
		return list;
	}

	@Override
	public ArrayOfPaymentsShort toXMLShort(List<IPayment> payments) {
		ArrayOfPaymentsShort list = new ArrayOfPaymentsShort();
		for ( IPayment payment: payments ) {			
			list.getPayment().add( convertPayment( payment, new PaymentShortOXM() ) );
		}
		return list;
	}

	@Override
	public IPayment getNewInstance() {
		return new Payment();
	}

	@Override
	public PaymentOXM getNewInstanceOXM() {
		return new PaymentOXM();
	}
}