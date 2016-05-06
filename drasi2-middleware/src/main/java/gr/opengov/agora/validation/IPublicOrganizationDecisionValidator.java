package gr.opengov.agora.validation;

import java.util.Map;

import gr.opengov.agora.bridges.diavgeia.IAgoraDiavgeiaBridge;
import gr.opengov.agora.domain.IAda;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.IPublicOrganizationDecision;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.IProcurementRequestService;
import gr.opengov.agora.service.ITaxonomyService;

public interface IPublicOrganizationDecisionValidator {

	public abstract void setDiavgeiaBridge(IAgoraDiavgeiaBridge bridge);

	public abstract IAgoraDiavgeiaBridge getDiavgeiaBridge();

	public abstract void setTaxonomyService(ITaxonomyService taxonomyService);

	public abstract ITaxonomyService getTaxonomyService();

	public abstract IValidation validateCreateDecision( IPublicOrganizationDecision decision, Map<IAda, IPublicOrganizationDecision> relatedDecisions );

	public abstract IValidation validateUpdateDecision( IPublicOrganizationDecision decision, Map<IAda, IPublicOrganizationDecision> relatedDecisions );

	public void setProcurementRequestService( IProcurementRequestService procurementRequestService );

	public void setContractService( IDecisionGenericService<IContract> contractService );

	public void setNoticeService(IDecisionGenericService<INotice> noticeService);

}