package gr.opengov.agora.converters;

import gr.opengov.agora.bridges.diavgeia.domain.DiavgeiaOdeMember;
import gr.opengov.agora.cms.ITaxonomy;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.AuthenticationProfile;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.IContractParty;
import gr.opengov.agora.domain.ICost;
import gr.opengov.agora.domain.IOrganization;
import gr.opengov.agora.domain.IPaymentItem;
import gr.opengov.agora.domain.PaymentItem;
import gr.opengov.agora.model.AdaOXM;
import gr.opengov.agora.model.ArrayOfValidationErrors;
import gr.opengov.agora.model.AuthenticationProfileOXM;
import gr.opengov.agora.model.ContractItemOXM;
import gr.opengov.agora.model.ContractPartyOXM;
import gr.opengov.agora.model.CostOXM;
import gr.opengov.agora.model.OrganizationOXM;
import gr.opengov.agora.model.PaginationInfoOXM;
import gr.opengov.agora.model.PaymentItemOXM;
import gr.opengov.agora.model.TaxonomyOXM;
import gr.opengov.agora.security.IClient;
import gr.opengov.agora.util.IPaginationInfo;
import gr.opengov.agora.validation.IValidationError;

import java.util.List;

public interface ICommonsOXMConverter {

	public abstract ICost convertCost(CostOXM oxm, ICost obj);
	public abstract CostOXM convertCost(ICost obj, CostOXM oxm);

	public abstract IContractParty convertContractParty(ContractPartyOXM oxm, IContractParty obj);
	public abstract ContractPartyOXM convertContractParty(IContractParty obj, ContractPartyOXM oxm);

	public abstract IContractItem convertContractItem( ContractItemOXM oxm, IContractItem obj);
	public abstract ContractItemOXM convertContractItem( IContractItem obj, ContractItemOXM oxm );

	public abstract ArrayOfValidationErrors getValidationErrors( List<IValidationError> errors );
	public abstract PaginationInfoOXM getPagination( IPaginationInfo obj );
	
	public abstract ITaxonomy getTaxonomy( TaxonomyOXM oxm );
	public abstract TaxonomyOXM getTaxonomy( ITaxonomy obj );

	public abstract AuthenticationProfileOXM getAuthenticationProfile( IClient obj );
	public abstract Ada convertAda(AdaOXM oxm, Ada obj);
	public abstract AdaOXM convertAda(Ada obj, AdaOXM oxm);
	public abstract IPaymentItem convertPaymentItem(PaymentItemOXM item, IPaymentItem paymentItem);
	public abstract PaymentItemOXM convertPaymentItem(IPaymentItem paymentItem, PaymentItemOXM item);
	public abstract OrganizationOXM convertOrganizationOXM(IOrganization obj, OrganizationOXM oxm);
	public abstract TaxonomyOXM getOdaTaxonomy(ITaxonomy obj);
	public AuthenticationProfileOXM convertAuthenticationProfileOXM( AuthenticationProfile obj, AuthenticationProfileOXM oxm );
}