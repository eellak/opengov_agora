package gr.opengov.agora.validation;

import gr.opengov.agora.cms.ITaxonomy;

import gr.opengov.agora.domain.IContractParty;

import gr.opengov.agora.service.ITaxonomyService;
import gr.opengov.agora.util.Constants;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContractPartyValidator implements IContractPartyValidator {
	private static final Logger logger = LoggerFactory.getLogger(ContractPartyValidator.class);
	
	private ICommonsValidator commonsValidator;
	private ITaxonomyService taxonomyService = null;

	public void setTaxonomyService(ITaxonomyService taxonomyService) {
		this.taxonomyService = taxonomyService;
	}
	
	public void setCommonsValidator(ICommonsValidator commonsValidator){
		this.commonsValidator = commonsValidator;
	}		
	
//	private ITaxonomyService taxonomyService = null;
//
//	public void setTaxonomyService(ITaxonomyService taxonomyService) {
//		this.taxonomyService = taxonomyService;
//	}	
	
	@Override
	public IValidation validateAfm( String afm, IValidation validation, String validationXpath ) {
		if ( !commonsValidator.isOkAfm(afm) ) {
			validation.addValidationError( new ValidationError( "invalidAfm", "Afm is invalid.", validationXpath + "/afm" ) );
		}
		return validation;
	}
	
	@Override
	public IValidation validateSecondaryParty( IContractParty contractParty, String validationXpath ) {
		IValidation validation = new Validation();
		
		if (contractParty.getCountryIdRef() != null)
		{
			if ((contractParty.getCountryIdRef().equalsIgnoreCase(Constants.COUNTRYCODE_GREECE)))
				validateAfm(contractParty.getAfm(), validation, validationXpath);
			
			ITaxonomy countryTaxonomy = taxonomyService.getTaxonomy( "country", null );
			if ( !contractParty.getCountryTaxonomyReference().isValidForTaxonomy( countryTaxonomy ) ) {			
				validation.addValidationError( new ValidationError( "country", "Invalid country specified.", "contractParty/country" ) );
			}			
		}
		return validation;
	}	
	
}
