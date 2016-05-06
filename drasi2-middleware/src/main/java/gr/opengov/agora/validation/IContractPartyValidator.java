package gr.opengov.agora.validation;

import gr.opengov.agora.domain.IContractParty;

public interface IContractPartyValidator {

	IValidation validateAfm(String afm, IValidation validation, String validationXpath);

	IValidation validateSecondaryParty(IContractParty contractParty, String validationXpath);

}
