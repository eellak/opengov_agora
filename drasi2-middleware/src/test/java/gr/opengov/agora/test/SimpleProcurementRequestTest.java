package gr.opengov.agora.test;

import gr.opengov.agora.converters.IProcurementRequestOXMConverter;
import gr.opengov.agora.converters.IDecisionGenericConverter;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.model.ArrayOfProcurementRequests;
import gr.opengov.agora.model.ArrayOfProcurementRequestsShort;
import gr.opengov.agora.model.ProcurementRequestOXM;
import gr.opengov.agora.test.util.GenericDomainTest;

import javax.annotation.Resource;

public class SimpleProcurementRequestTest extends GenericDomainTest< IProcurementRequest, ProcurementRequestOXM, ArrayOfProcurementRequests, ArrayOfProcurementRequestsShort>{
	
	public SimpleProcurementRequestTest() {
		super( IProcurementRequest.class, ProcurementRequestOXM.class, ArrayOfProcurementRequests.class );
	}

	@Resource( name="procurementRequestOxmConverter" )
	private IProcurementRequestOXMConverter converter;

	@Override
	protected String getSingleEntityXml() {
		return "single-request.xml";
	}

	@Override
	protected IDecisionGenericConverter<IProcurementRequest, ProcurementRequestOXM, ArrayOfProcurementRequests, ArrayOfProcurementRequestsShort> getConverter() {
		return converter;
	}
}
