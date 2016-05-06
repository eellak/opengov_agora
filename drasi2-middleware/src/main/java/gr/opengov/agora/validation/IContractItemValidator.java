package gr.opengov.agora.validation;

import java.util.List;

import gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.util.Constants.Action;

public interface IContractItemValidator {

	public IValidation validateContractItems(List<IContractItem> contractItems, IValidation validation);
	
	public IValidation validateContractItem(IContractItem contractItem, IValidation validation, int index);

}