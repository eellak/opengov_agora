package gr.opengov.agora.converters;

import gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge;
import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaSigner;
import gr.opengov.agora.bridges.diavgeia.domain.OdeMemberTaxonomyItem;
import gr.opengov.agora.bridges.diavgeia.domain.UnitTaxonomyItem;
import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.cms.ITaxonomyItem;
import gr.opengov.agora.cms.Taxonomy;
import gr.opengov.agora.cms.TaxonomyItem;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.AuthenticationProfile;
import gr.opengov.agora.domain.ContractItem;
import gr.opengov.agora.domain.ContractParty;
import gr.opengov.agora.domain.Cost;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IContractParty;
import gr.opengov.agora.domain.ICost;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IOrganization;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.model.AdaOXM;
import gr.opengov.agora.model.ArrayOfCpvCodes;
import gr.opengov.agora.model.ArrayOfKaeCodes;
import gr.opengov.agora.model.ArrayOfOrganizations;
import gr.opengov.agora.model.ArrayOfSigners;
import gr.opengov.agora.model.ArrayOfSupervisedOda;
import gr.opengov.agora.model.ArrayOfUnitReferences;
import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.AuthenticationProfileOXM;
import gr.opengov.agora.model.ContractItemOXM;
import gr.opengov.agora.model.ContractPartyOXM;
import gr.opengov.agora.model.CostOXM;
import gr.opengov.agora.model.OdaTaxonomyItemOXM;
import gr.opengov.agora.model.OrganizationOXM;
import gr.opengov.agora.model.PaginationInfoOXM;
import gr.opengov.agora.model.PaymentItemOXM;
import gr.opengov.agora.model.SignerOXM;
import gr.opengov.agora.model.TaxonomyItemOXM;
import gr.opengov.agora.model.TaxonomyOXM;
import gr.opengov.agora.model.TaxonomyReferenceOXM;
import gr.opengov.agora.model.UnitReference;
import gr.opengov.agora.bridges.diavgeia.domain.IUnitTaxonomyItem;
import gr.opengov.agora.model.ValidationErrorOXM;
import gr.opengov.agora.security.AnonymousUser;
import gr.opengov.agora.security.IClient;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.util.IPaginationInfo;
import gr.opengov.agora.validation.IValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonsOXMConverter implements ICommonsOXMConverter {
	private static final Logger logger = LoggerFactory.getLogger(CommonsOXMConverter.class);
	private IDecisionGenericService<IProcurementRequest> procurementRequestService;
	private IDecisionGenericService<INotice> noticeService;
	private IDecisionGenericService<IContract> contractService;
	private IAgoraDiavgeiaBridge diavgeiaBridge;
	
	public void setProcurementRequestService( IDecisionGenericService<IProcurementRequest> procurementRequestService ) {
		this.procurementRequestService = procurementRequestService;
	}	
	
	public void setNoticeService( IDecisionGenericService<INotice> noticeService ) {
		this.noticeService = noticeService;
	}	
	
	public void setContractService( IDecisionGenericService<IContract> contractService ) {
		this.contractService = contractService;
	}		
	
	public void setDiavgeiaBridge(IAgoraDiavgeiaBridge diavgeiaBridge) {
		this.diavgeiaBridge = diavgeiaBridge;
	}
		
	@Override
	public Ada convertAda(AdaOXM oxm, Ada obj){
//		obj.setId( oxm.getId() );
		obj.setAdaCode( oxm.getAdaCode() );
		obj.setAdaType( oxm.getAdaType() );
		return obj;
	}
	
	@Override
	public AdaOXM convertAda(Ada obj, AdaOXM oxm){
//		oxm.setId( obj.getId() );
		oxm.setAdaCode( obj.getAdaCode() );
		oxm.setAdaType( obj.getAdaType() );
		return oxm;
	}		
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#convertCost(gr.opengov.agora.model.Cost, gr.opengov.agora.domain.CostTO)
	 */
	@Override
	public ICost convertCost( CostOXM oxm, ICost obj ) {
		obj.setCostBeforeVat( oxm.getCostBeforeVat() );
		obj.setVatPercentage( oxm.getVatPercentage() );
		if (oxm.getCurrency() != null)
			obj.setCurrencyIdRef(oxm.getCurrency().getIdRef());
		return obj;
	}
	
	@Override
	public CostOXM convertCost( ICost obj, CostOXM oxm ) {
		oxm.setCostBeforeVat( obj.getCostBeforeVat() );
		oxm.setVatPercentage( obj.getVatPercentage() );
		if (obj.getCurrencyIdRef() != null){
			TaxonomyReferenceOXM ref = new TaxonomyReferenceOXM();
			ref.setIdRef(obj.getCurrencyIdRef());
			oxm.setCurrency(ref);		
		}		
		
		return oxm;
	}
	
	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#convertContractParty(gr.opengov.agora.model.ContractParty, gr.opengov.agora.domain.ContractPartyTO)
	 */
	@Override
	public IContractParty convertContractParty( ContractPartyOXM oxm, IContractParty obj ) {
		obj.setAfm( oxm.getAfm() );
		if (oxm.getCountry() != null)
			obj.setCountryIdRef(oxm.getCountry().getIdRef());
		obj.setName( oxm.getName() );
		//to.setId(...)
		return obj;		
	}
	
	@Override
	public ContractPartyOXM convertContractParty( IContractParty obj, ContractPartyOXM oxm ) {
		oxm.setAfm( obj.getAfm() );
		if (obj.getCountryIdRef() != null){
			TaxonomyReferenceOXM ref = new TaxonomyReferenceOXM();
			ref.setIdRef(obj.getCountryIdRef());
			oxm.setCountry(ref);		
		}
		oxm.setName( obj.getName() );
		return oxm;		
	}

	/* (non-Javadoc)
	 * @see gr.opengov.agora.domain.IContractDTOFactory#convertContractItem(gr.opengov.agora.model.ContractItem, gr.opengov.agora.domain.ContractItemTO)
	 */
	@Override
	public IContractItem convertContractItem( ContractItemOXM oxm, IContractItem obj ) {
		obj.setQuantity(oxm.getQuantity());
		obj.setUnitOfMeasureIdRef( oxm.getUnitOfMeasure().getIdRef());
		obj.setAddress(oxm.getAddress());
		obj.setAddressNo(oxm.getAddressNo());
		obj.setAddressPostal(oxm.getAddressPostal());
		obj.setNuts( oxm.getNuts() );
		obj.setCity(oxm.getCity());
		
		if (oxm.getCountryOfDelivery() != null)
			obj.setCountryIdRef(oxm.getCountryOfDelivery().getIdRef());
		if (oxm.getCountryProduced() != null)
			obj.setCountryProducedIdRef(oxm.getCountryProduced().getIdRef());
		obj.setDescription( oxm.getDescription() );
		
		if (oxm.getProcurementRequest() != null){
			obj.setProcurementRequest( procurementRequestService.get(oxm.getProcurementRequest()) );
		}

		if (oxm.getNotice() != null){
			obj.setNotice( noticeService.get(oxm.getNotice()) );
		}		
		
		if (oxm.getContract() != null){
			obj.setContract( contractService.get(oxm.getContract()) );
		}
		
		obj.setCost( convertCost( oxm.getCost(), new Cost() ) );
//		Set<ICpv> cpvCodes = new LinkedHashSet<ICpv>();
		if ( oxm.getCpvCodes() != null ) {
			for ( String cpv: oxm.getCpvCodes().getCpv() ) {
//				ICpv cpvItem = procurementRequestService.getCpv(cpv);
//				if (cpvItem == null)
//					cpvItem = new Cpv(cpv);
				
//				cpvItem.setContractItem(obj);
//				cpvCodes.add( new Cpv(cpv) );
				obj.addCpv(cpv);
			}
//			obj.setCpvCodes( cpvCodes );
		}
		
		obj.setInvoiceNumber(oxm.getInvoiceNumber());
		List<String> kaeCodes = new ArrayList<String>();
		if ( oxm.getKaeCodes() != null ) {
			for ( String kae: oxm.getKaeCodes().getKae() ) {
				kaeCodes.add( kae );
			}
			obj.setKaeCodes(kaeCodes);
		}
		
		
		//to.setId(...)
		return obj;
	}

	@Override
	public ContractItemOXM convertContractItem( IContractItem obj, ContractItemOXM oxm ) {
		oxm.setQuantity(obj.getQuantity());
		oxm.setDescription( obj.getDescription() );
		TaxonomyReferenceOXM uomRef = new TaxonomyReferenceOXM();
		uomRef.setIdRef( obj.getUnitOfMeasureIdRef() );
		oxm.setUnitOfMeasure(uomRef);
		oxm.setAddress(obj.getAddress());
		oxm.setAddressNo(obj.getAddressNo());
		oxm.setAddressPostal(obj.getAddressPostal());
		oxm.setNuts( obj.getNuts() );
		oxm.setCity(obj.getCity());

		if (obj.getCountryIdRef() != null){
			TaxonomyReferenceOXM ref = new TaxonomyReferenceOXM();
			ref.setIdRef(obj.getCountryIdRef());
			oxm.setCountryOfDelivery(ref);		
		}
		
		if ( obj.getCountryProducedIdRef() != null){
			TaxonomyReferenceOXM ref2 = new TaxonomyReferenceOXM();
			ref2.setIdRef(obj.getCountryProducedIdRef());
			oxm.setCountryProduced(ref2);
		}
		
		if (obj.getProcurementRequest() != null){
			oxm.setProcurementRequest( obj.getProcurementRequest().getId() );
		}
		
		if (obj.getNotice() != null){
			oxm.setNotice( obj.getNotice().getId() );
		}
		
		if (obj.getContract() != null){
			oxm.setContract( obj.getContract().getId() );
		}
		
		oxm.setCost( convertCost( obj.getCost(), new CostOXM() ) );
		
		ArrayOfCpvCodes cpvCodes = new ArrayOfCpvCodes();
		for (ICpv cpv:obj.getCpvCodes()){
			cpvCodes.getCpv().add(cpv.getCpvCode());
		}
		if ((cpvCodes != null) && (cpvCodes.getCpv() != null) && (cpvCodes.getCpv().size() > 0))
			oxm.setCpvCodes( cpvCodes );
		
		oxm.setInvoiceNumber(obj.getInvoiceNumber());
		
		ArrayOfKaeCodes kaeCodes = new ArrayOfKaeCodes();
		kaeCodes.getKae().addAll(obj.getKaeCodes());
		if ((kaeCodes != null) && (kaeCodes.getKae() != null) && (kaeCodes.getKae().size() > 0))
			oxm.setKaeCodes(kaeCodes);
		else
			oxm.setKaeCodes(new ArrayOfKaeCodes());
		return oxm;
	}
	
	@Override
	public IPaymentItem convertPaymentItem(PaymentItemOXM item, IPaymentItem paymentItem) {
		paymentItem.setContractParty(convertContractParty(item.getContractParty(), new ContractParty()));
		paymentItem.setContractItem(convertContractItem(item.getContractItem(), new ContractItem()));
		paymentItem.setResponsibilityAssumptionCode(item.getResponsibilityAssumptionCode());
		paymentItem.setPayed( item.isPayed() );
		return paymentItem;
	}	
	
	@Override
	public PaymentItemOXM convertPaymentItem(IPaymentItem paymentItem, PaymentItemOXM item) {		
		item.setContractParty(convertContractParty(paymentItem.getContractParty(), new ContractPartyOXM()));
		item.setContractItem(convertContractItem(paymentItem.getContractItem(), new ContractItemOXM()));
		item.setResponsibilityAssumptionCode(paymentItem.getResponsibilityAssumptionCode());
		item.setPayed( paymentItem.getPayed() );
		return item;
	}		
	
	@Override
	public ArrayOfValidationErrors getValidationErrors( List<IValidationError> errors) {
		ArrayOfValidationErrors list = new ArrayOfValidationErrors();
		for ( IValidationError error: errors ) {
			ValidationErrorOXM oxm = new ValidationErrorOXM();
			oxm.setField( error.getCode() );
			oxm.setMsg( error.getMessage() );
			oxm.setXpath( error.getLocation() );
			list.getError().add( oxm );
		}
		return list;
	}

	@Override
	public PaginationInfoOXM getPagination(IPaginationInfo obj) {
		PaginationInfoOXM oxm = new PaginationInfoOXM();
		oxm.setFrom( obj.getFrom() );
		oxm.setReturned( obj.getLimit() );
		oxm.setTotal( obj.getTotal() );
		return oxm;
	}

	@Override
	public AuthenticationProfileOXM getAuthenticationProfile(IClient obj) {
		AuthenticationProfileOXM profileOxm = new AuthenticationProfileOXM();
		if (obj instanceof AnonymousUser) {
			profileOxm.setRole(((AnonymousUser)obj).getRealm());
		}
		else
		{
			profileOxm.setOdeMember(new ArrayOfOrganizations());
			
			AuthenticationProfile profile = diavgeiaBridge.getAuthenticationProfile(obj.getUserId());
			profileOxm = convertAuthenticationProfileOXM( profile, profileOxm );
	
			logger.debug("got auth profile with " + profileOxm.getOdeMember() + " organizations");
		}
		return profileOxm;
	}	
	
	@Override
	public OrganizationOXM convertOrganizationOXM(IOrganization obj, OrganizationOXM oxm ){
		oxm.setAddress(obj.getAddress());
		oxm.setAddressNo(obj.getAddressNo());
		oxm.setAddressPostal(obj.getAddressPostal());
		oxm.setNuts( obj.getNuts() );
		oxm.setAfm(obj.getAfm());
		oxm.setCity(obj.getCity());
		oxm.setCountry(obj.getCountry());
		oxm.setFormalName(obj.getFormalName());
		oxm.setIdRef(obj.getIdRef());
		oxm.setName(obj.getName());
		oxm.setOrganizationType( obj.getOrganizationType() );
		return oxm;		
	}
	
	@Override
	public AuthenticationProfileOXM convertAuthenticationProfileOXM( AuthenticationProfile obj, AuthenticationProfileOXM oxm ){
		oxm.setFirstName(obj.getFirstName());
		oxm.setLastName(obj.getLastName());
		oxm.setEmail( obj.getEmail() );
		oxm.setRole(obj.getRole());
		oxm.setUserId(obj.getUserId());
		oxm.setUserName(obj.getUserName());

		for (IOrganization organization:(obj.getOdeMember())){
			oxm.getOdeMember().getOrganizations().add(convertOrganizationOXM(organization, new OrganizationOXM() ));
		}
		return oxm;		
	}


	@Override
	public ITaxonomy getTaxonomy(TaxonomyOXM oxm) {
		ITaxonomy obj = new Taxonomy();
		obj.setName( oxm.getName() );
		for ( TaxonomyItemOXM item: oxm.getItem() ) {
			TaxonomyItem objItem = new TaxonomyItem( item.getId(), item.getLabel() );
			if ( item.getTaxonomy() != null ) {
				objItem.setTaxonomy( getTaxonomy( item.getTaxonomy() ) );
			}			
			obj.addItem( objItem );
		}
		return obj;
	}
	


	public TaxonomyOXM convertTaxonomy( ITaxonomy obj, TaxonomyOXM oxm ) {
		oxm.setName( obj.getName() );
		for ( ITaxonomyItem item: obj.getItems() ) {
			TaxonomyItemOXM itemOxm = new TaxonomyItemOXM();
			itemOxm.setId( item.getId() );
			itemOxm.setLabel( item.getLabel() );
			if ( item.getTaxonomy() != null ) {
				itemOxm.setTaxonomy( convertTaxonomy( item.getTaxonomy(), new TaxonomyOXM() ) );				
			}
			oxm.getItem().add( itemOxm );
		}
		return oxm;		
	}
	
	private OdaTaxonomyItemOXM convertOdaItem(OdeMemberTaxonomyItem odaItem){
		OdaTaxonomyItemOXM itemOxm = new OdaTaxonomyItemOXM();
		itemOxm.setId( odaItem.getId() );
		itemOxm.setLabel( odaItem.getLabel() );
		itemOxm.setLatinName(odaItem.getLatinName());
		if ( (odaItem.getTaxonomy() != null) && (odaItem.getTaxonomy().getItems().size() > 0)) {
			itemOxm.setOda( convertSupervisedOda(odaItem.getTaxonomy()));
		}
		
		if ( (odaItem.getSigners() != null) && (odaItem.getSigners().size()>0)){
			itemOxm.setSigners(convertSigners( odaItem.getSigners() ));
		}
		
		if ( (odaItem.getUnits() != null) && (odaItem.getUnits().size()>0)){
			itemOxm.setUnits(convertUnits( odaItem.getUnits() ));
		}
		return itemOxm;				
	}
	
	public TaxonomyOXM convertOdaTaxonomy( ITaxonomy obj, TaxonomyOXM oxm ) {
		oxm.setName( obj.getName() );
		for ( ITaxonomyItem item: obj.getItems() ) {	
			oxm.getItem().add( convertOdaItem((OdeMemberTaxonomyItem)item) );
		}
		return oxm;
	}	
	
	@Override
	public TaxonomyOXM getTaxonomy(ITaxonomy obj) {
		return convertTaxonomy( obj, new TaxonomyOXM() );
	}

	@Override
	public TaxonomyOXM getOdaTaxonomy(ITaxonomy obj) {
		return convertOdaTaxonomy( obj, new TaxonomyOXM() );
	}	

	private ArrayOfSupervisedOda convertSupervisedOda(ITaxonomy taxonomy) {
		ArrayOfSupervisedOda supervisedOda = new ArrayOfSupervisedOda();
		for (ITaxonomyItem taxonomyItem:taxonomy.getItems()){
			supervisedOda.getItem().add( convertOdaItem((OdeMemberTaxonomyItem)taxonomyItem) );
		}
		return supervisedOda;
	}	
	
	private ArrayOfUnitReferences convertUnits(Set<IUnitTaxonomyItem> units) {
		if ( (units == null) || (units.size() == 0) ){ return new ArrayOfUnitReferences(); }
		ArrayOfUnitReferences unitReferences = new ArrayOfUnitReferences();
		UnitReference unitReference = new UnitReference();
		
		for (IUnitTaxonomyItem unit:units){
			unitReference = new UnitReference();
			unitReference.setIdRef( Integer.valueOf(unit.getId()) );
			unitReference.setLabel( unit.getLabel() );
			unitReference.setSigners( convertSigners(unit.getSigners()) );
			unitReferences.getUnit().add(unitReference);
		}
		return unitReferences;
	}

	private ArrayOfSigners convertSigners(Set<DiavgeiaSigner> signers) {
		if ((signers == null) || (signers.size() == 0) ) {return new ArrayOfSigners();}
		ArrayOfSigners signersList = new ArrayOfSigners();
		SignerOXM signer = new SignerOXM();
		
		for (DiavgeiaSigner diavgeiaSigner:signers){
			signer = new SignerOXM();
			
			signer.setUid(diavgeiaSigner.getId());
			signer.setActive(diavgeiaSigner.getActive());
			signer.setFirstName(diavgeiaSigner.getFirstName());
			signer.setLastName(diavgeiaSigner.getLastName());
			signer.setTitle(diavgeiaSigner.getTitle());
			signer.setPosition(diavgeiaSigner.getPosition());

			signersList.getSigner().add(signer);
		}
		return signersList;
	}


}