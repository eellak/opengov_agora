package gr.opengov.agora.converters;

import gr.opengov.agora.domain.Contract;
import gr.opengov.agora.domain.ContractItem;
import gr.opengov.agora.domain.ContractParty;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IContractParty;
import gr.opengov.agora.domain.InvalidContractReference;
import gr.opengov.agora.exceptions.DecisionNotFoundException;
import gr.opengov.agora.model.ArrayOfContractItems;
import gr.opengov.agora.model.ArrayOfContractParties;
import gr.opengov.agora.model.ArrayOfContracts;
import gr.opengov.agora.model.ArrayOfContractsShort;
import gr.opengov.agora.model.ContractItemOXM;
import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.model.ContractPartyOXM;
import gr.opengov.agora.model.ContractShortOXM;
import gr.opengov.agora.model.TaxonomyReferenceOXM;
import gr.opengov.agora.model.TaxonomyReferenceWithOtherOXM;
import gr.opengov.agora.service.IDecisionGenericService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContractOXMConverter implements IContractOXMConverter {
	private static final Logger logger = LoggerFactory.getLogger(ContractOXMConverter.class);
	
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

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#convertContract(gr.opengov.agora.model.Contract, gr.opengov.agora.domain.ContractTO)
	 */
	public IContract convertContract( ContractOXM oxm, IContract obj ) {
		podConverter.convertPublicOrganizationDecision(oxm, obj);
		obj.setContractingAuthorityIdRef( oxm.getContractingAuthority().getIdRef() );
		obj.setAwardProcedureIdRef(oxm.getAwardProcedure().getIdRef());
		obj.setContractTypeIdRef(oxm.getContractType().getIdRef());
		obj.setCommissionCriteriaIdRef(oxm.getCommissionCriteria().getIdRef());
		obj.setContractingAuthorityOther( oxm.getContractingAuthority().getOther() );
		for ( ContractPartyOXM party: oxm.getSecondaryParties().getParty() ) {
			obj.getSecondaryParties().add( commonsConverter.convertContractParty( party, new ContractParty() ) );
		}
		if (oxm.getContractItems() != null){
			for ( ContractItemOXM item: oxm.getContractItems().getItem() ) {
				IContractItem contractItem = commonsConverter.convertContractItem( item, new ContractItem() );
				if (oxm.getId() != null){
					contractItem.setContract( contractService.get(oxm.getId()) );
				}
				obj.getContractItems().add( contractItem );
			}
		}
		obj.setDateSigned( oxm.getDateSigned() );
		obj.setSince( oxm.getSince() );
		obj.setUntil( oxm.getUntil() );
		obj.setContractPlace( oxm.getContractPlace() );
		obj.setProjectCode(oxm.getProjectCode());
		obj.setCoFunded(oxm.isCoFunded());
		obj.setFundedFromPIP(oxm.isFundedFromPIP());
		obj.setCodeCoFunded(oxm.getCodeCoFunded());
		obj.setCodePIP(oxm.getCodePIP());
		
		if ( oxm.getExtendsContract() != null ) {
			try {
				IContract ref = contractService.get( oxm.getExtendsContract() );
				//logger.info( "REF: " + ref );
				obj.setExtendsContract( (ref != null)?ref:new InvalidContractReference() );
			}
			catch ( DecisionNotFoundException e ) {
				logger.info( "INVALID REFERENCE" );
				obj.setExtendsContract( new InvalidContractReference() );
			}
		}
		
		if ( oxm.getChangesContract() != null ) {
			try {
				IContract ref = contractService.get( oxm.getChangesContract() );
				obj.setChangesContract( (ref != null)?ref:new InvalidContractReference() );
			}
			catch ( DecisionNotFoundException e ) {
				obj.setChangesContract( new InvalidContractReference() );
			}
		}		
		return obj;
	}
	
	public ContractOXM convertContract( IContract obj, ContractOXM oxm ) {
		podConverter.convertPublicOrganizationDecision(obj, oxm);
		/* Add contracting authority reference */
		if (obj.getContractingAuthorityIdRef() != null){
			TaxonomyReferenceWithOtherOXM ref = new TaxonomyReferenceWithOtherOXM();
			ref.setIdRef( obj.getContractingAuthorityIdRef() );
			ref.setOther( obj.getContractingAuthorityOther() );
			oxm.setContractingAuthority( ref );
		}
		
		if (obj.getAwardProcedureIdRef() != null){
			TaxonomyReferenceOXM ref2 = new TaxonomyReferenceOXM();
			ref2.setIdRef(obj.getAwardProcedureIdRef());
			oxm.setAwardProcedure(ref2);
		}
		/* Add more elements */
		if (obj.getContractTypeIdRef() != null){		
			TaxonomyReferenceOXM contractTypeTaxonomyReferenceOXM = new TaxonomyReferenceOXM();
			contractTypeTaxonomyReferenceOXM.setIdRef(obj.getContractTypeIdRef());
			oxm.setContractType(contractTypeTaxonomyReferenceOXM);
		}
		
		if (obj.getCommissionCriteriaIdRef() != null){
			TaxonomyReferenceOXM commissionCriteriaTaxonomyReferenceOXM = new TaxonomyReferenceOXM();
			commissionCriteriaTaxonomyReferenceOXM.setIdRef(obj.getCommissionCriteriaIdRef());
			oxm.setCommissionCriteria(commissionCriteriaTaxonomyReferenceOXM);		
		}
		
		ArrayOfContractParties parties = new ArrayOfContractParties();
		for ( IContractParty party: obj.getSecondaryParties() ) {
			parties.getParty().add( commonsConverter.convertContractParty( party, new ContractPartyOXM() ) );			
		}
		oxm.setSecondaryParties( parties );
		ArrayOfContractItems items = new ArrayOfContractItems();
		for ( IContractItem item: obj.getContractItems() ) {
			ContractItemOXM contractItem = commonsConverter.convertContractItem( item, new ContractItemOXM() );
			contractItem.setContract(obj.getId());
			
			items.getItem().add( contractItem );
		}		
		oxm.setContractItems(items);
		oxm.setSince( obj.getSince() );
		oxm.setUntil( obj.getUntil() );		
		oxm.setContractPlace( obj.getContractPlace() );
		oxm.setProjectCode(obj.getProjectCode());
		
		oxm.setCoFunded(obj.isCoFunded());
		oxm.setFundedFromPIP(obj.isFundedFromPIP());
		oxm.setCodeCoFunded(obj.getCodeCoFunded());
		oxm.setCodePIP(obj.getCodePIP());
		
		if ( obj.getChangesContract() != null && !( obj.getChangesContract() instanceof InvalidContractReference ) ) oxm.setChangesContract( obj.getChangesContract().getId() );
		if ( obj.getExtendsContract() != null && !( obj.getExtendsContract() instanceof InvalidContractReference ) ) oxm.setExtendsContract( obj.getExtendsContract().getId() );
		return oxm;
	}
	
	public ContractShortOXM convertContract( IContract obj, ContractShortOXM oxm ) {
		podConverter.convertPublicOrganizationDecision(obj, oxm);
		oxm.setSince( obj.getSince() );
		oxm.setUntil( obj.getUntil() );		
		if ( obj.getChangesContract() != null && !( obj.getChangesContract() instanceof InvalidContractReference ) ) oxm.setChangesContract( obj.getChangesContract().getId() );
		if ( obj.getExtendsContract() != null && !( obj.getExtendsContract() instanceof InvalidContractReference ) ) oxm.setExtendsContract( obj.getExtendsContract().getId() );
		oxm.setTotalCostBeforeVAT( obj.getTotalCostBeforeVat() );
		return oxm;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#getContractTO(gr.opengov.agora.model.Contract)
	 */
	@Override
	public IContract toObject( ContractOXM oxm ) {
		return convertContract( oxm, new Contract() );
	}
	
	@Override
	public ContractOXM toXML( IContract obj ) {
		return convertContract( obj, new ContractOXM() );
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#getListOfContractTO(gr.opengov.agora.model.ArrayOfContracts)
	 */
	@Override
	public List<IContract> toObjectList( ArrayOfContracts contracts ) {
		List<IContract> list = new ArrayList<IContract>();
		for ( ContractOXM contract: contracts.getContract() ) {
			list.add( toObject( contract ) );
		}
		return list;
	}

	@Override
	public ArrayOfContracts toXMLList( List<IContract> contracts ) {
		ArrayOfContracts list = new ArrayOfContracts();
		for ( IContract contract: contracts ) {
			list.getContract().add( toXML( contract ) );
		}
		return list;
	}

	@Override
	public ArrayOfContractsShort toXMLShort( List<IContract> contracts ) {
		ArrayOfContractsShort list = new ArrayOfContractsShort();
		for ( IContract contract: contracts ) {			
			list.getContract().add( convertContract( contract, new ContractShortOXM() ) );
		}
		return list;
	}
	
	@Override
	public IContract getNewInstance(){
		return new Contract();
	}
	
	@Override
	public ContractOXM getNewInstanceOXM(){
		return new ContractOXM();
	}
}