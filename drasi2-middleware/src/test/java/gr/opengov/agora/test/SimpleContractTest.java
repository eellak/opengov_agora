package gr.opengov.agora.test;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IDecisionGenericConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.model.ArrayOfContracts;
import gr.opengov.agora.model.ArrayOfContractsShort;
import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.test.util.GenericDomainTest;

import javax.annotation.Resource;

public class SimpleContractTest extends GenericDomainTest< IContract, ContractOXM, ArrayOfContracts, ArrayOfContractsShort>{
	
	public SimpleContractTest() {
		super( IContract.class, ContractOXM.class, ArrayOfContracts.class );
	}

	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter converter;

	@Override
	protected String getSingleEntityXml() {
		return "single-contract.xml";
	}

	@Override
	protected IDecisionGenericConverter<IContract, ContractOXM, ArrayOfContracts, ArrayOfContractsShort> getConverter() {
		return converter;
	}
}
