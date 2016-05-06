package gr.opengov.agora.validation;

import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.ICost;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.service.ITaxonomyService;
import gr.opengov.agora.util.Constants.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContractItemValidator implements IContractItemValidator {
	private static final Logger logger = LoggerFactory.getLogger(ContractItemValidator.class);
	private boolean allowZeroCostPayments = false;
	private ITaxonomyService taxonomyService = null;
	private IValidator<IProcurementRequest> procurementRequestValidator;
	private IValidator<INotice> noticeValidator;
	private IValidator<IContract> contractValidator;

	public void setTaxonomyService(ITaxonomyService taxonomyService) {
		this.taxonomyService = taxonomyService;
	}
	
	public void setProcurementRequestValidator(IValidator<IProcurementRequest> procurementRequestValidator) {
		this.procurementRequestValidator = procurementRequestValidator;
	}		
	
	public void setNoticeValidator(IValidator<INotice> noticeValidator) {
		this.noticeValidator = noticeValidator;
	}		
	
	public void setContractValidator(IValidator<IContract> contractValidator) {
		this.contractValidator = contractValidator;
	}			
	
	@Override
	public IValidation validateContractItems( List<IContractItem> contractItems, IValidation validation) {
		if ( contractItems.size() == 0 ) {
			validation.addValidationError( new ValidationError( "contractItems", "No contract items.", "contractItems" ) );
		}
		
		for ( int i = 0; i < contractItems.size(); i++ ) {
			validateContractItem(contractItems.get(i), validation, i);
		}
		
		return validation;
	}

	@Override
	public IValidation validateContractItem( IContractItem contractItem, IValidation validation, int index) {
		validateQuantity(contractItem, validation );
		validatePaymentCost(contractItem.getCost(), validation, index);
		validateCPVs(contractItem.getCpvCodes(), taxonomyService.getTaxonomy( "cpv_codes", null ), validation );
		if (contractItem.getKaeCodes() != null){
			validateKAEs(contractItem.getKaeCodes(), validation );
		}
		
		ITaxonomy uomTaxonomy = taxonomyService.getTaxonomy("units_of_measure", null);
		
		if (contractItem.getUnitOfMeasureIdRef() != null){
			if ( !contractItem.getUnitOfMeasureTaxonomyReference().isValidForTaxonomy( uomTaxonomy ) ) {			
				validation.addValidationError( new ValidationError( "uom", "Invalid unit of measure specified.", "contractItems/item[" + index + "]/unitOfMeasure" ) );
			}
		}		
		
		ITaxonomy countryTaxonomy = taxonomyService.getTaxonomy( "country", null );
		
		if (contractItem.getCountryIdRef() != null){
			if ( !contractItem.getCountryTaxonomyReference().isValidForTaxonomy( countryTaxonomy ) ) {			
				validation.addValidationError( new ValidationError( "country", "Invalid country specified.", "contractItems/item[" + index + "]/country" ) );
			}
		}
		
		if (contractItem.getCountryProducedIdRef() != null){
			if ( !contractItem.getCountryProducedTaxonomyReference().isValidForTaxonomy( countryTaxonomy ) ) {			
				validation.addValidationError( new ValidationError( "country", "Invalid country specified.", "contractItems/item[" + index + "]/countryProduced" ) );
			}
		}
		return validation;
	}
	
	private void validateQuantity(IContractItem iContractItem, IValidation validation ){
		if (!(iContractItem.getQuantity() > 0)){
			validation.addValidationError( new ValidationError( "quantity", "Not greater than 0 quantity of cotractItems", "quantity" ) );
		}		
	}
	
	private void validateCPVs(Set<ICpv> cpvCodes, ITaxonomy taxonomy, IValidation validation) {		 		
		if ((cpvCodes == null) || (cpvCodes.size() == 0)){
			validation.addValidationError( new ValidationError( "cpvCodes", "no CPV code(s) for the current contract item.", "cpvCodes" ) );
		}
		else
		{
			List<String> cpvCodesList = new ArrayList<String>(); //Should I add cpv objects in the set?
			for (ICpv cpv:cpvCodes){
				cpvCodesList.add(cpv.getCpvCode());
			}
			Set<String> cpvCodesSet = new LinkedHashSet<String>(cpvCodesList);
			if (cpvCodesSet.size() != cpvCodes.size()){
				validation.addValidationError( new ValidationError( "cpvDuplicate", "Contract item found with duplicate CPV code values.", "cpv" ) );
			}
			if (!taxonomy.getIds().containsAll(cpvCodesList)){
				validation.addValidationError( new ValidationError( "cpvInvalid", "Invalid contract item CPV code.", "cpv" ) );
			}
		}
	}
	
	private void validateKAEs(List<String> kaeCodes, IValidation validation) {
		Set<String> kaeCodesSet = new HashSet<String>(kaeCodes);
		if (kaeCodesSet.size() != kaeCodes.size()){
			validation.addValidationError( new ValidationError( "kaeDuplicate", "Contract item found with duplicate KAE code values.", "cpv" ) );
		}
	}	

	/**
	 * Validate the cost of a contractItem
	 * Rules:
	 * costBeforeVat cannot be negative
	 * If allowZeroCostPayments == false, then costBeforeVat cannot be zero
	 * currency can be one of 'euro', 'usd', 'gbp'
	 * @param payments
	 * @param validation
	 */
	private void validatePaymentCost( ICost cost, IValidation validation, int index ) {
		

		if ( cost.getCostBeforeVat() < 0 || ( cost.getCostBeforeVat() == 0 && !this.allowZeroCostPayments ) )
		{
			validation.addValidationError( new ValidationError( "costBeforeVat", "Invalid cost specified." , "contractItems/item[" + index + "]/cost/costBeforeVat") );
		}
		if ( cost.getVatPercentage() < 0 || cost.getVatPercentage() >= 100 ) {
			validation.addValidationError( new ValidationError( "vatPercentage", "Invalid VAT percentage specified." , "contractItems/item[" + index + "]/cost/vatPercentage") );
		}						
//		if ( !cost.getCurrencyTaxonomyReference().isValidForTaxonomy( currencyTaxonomy ) ) {			
//			validation.addValidationError( new ValidationError( "currency", "Invalid currency specified.", "contractItems/item[" + index + "]/cost/currency" ) );
//		}
		
		if (cost.getCurrencyTaxonomyReference() == null)
			validation.addValidationError( new ValidationError( "currencyIsNull", "No currency.", "contractItems/item[" + index + "]/cost/currency" ) );
		else
		{
			ITaxonomy currencyTaxonomy = taxonomyService.getTaxonomy( "currency", null );
			if ( !cost.getCurrencyTaxonomyReference().isValidForTaxonomy( currencyTaxonomy ) ) 
				validation.addValidationError( new ValidationError( "currency", "Invalid currency specified.", "contractItems/item[" + index + "]/cost/currency" ) );
		}					
	}
}
