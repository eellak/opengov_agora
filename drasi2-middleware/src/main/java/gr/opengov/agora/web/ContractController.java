package gr.opengov.agora.web;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.model.ArrayOfContracts;
import gr.opengov.agora.model.ArrayOfContractsShort;
import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.GetContractsResponse;
import gr.opengov.agora.model.GetContractsShortResponse;
import gr.opengov.agora.model.PaginationInfoOXM;
import gr.opengov.agora.model.SingleContractOXM;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.service.IDecisionGenericService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/contracts")
public class ContractController extends DecisionGenericController<IContract, ContractOXM, ArrayOfContracts, ArrayOfContractsShort>{	
	@Resource(name = "contractService")
	private IDecisionGenericService<IContract> contractService;
	
	@Resource(name = "contractServiceReadOnly")
	private IDecisionGenericService<IContract> contractServiceReadOnly;
	
	@Resource(name = "contractOxmConverter")
	private IContractOXMConverter contractConverter;
	
	@Resource(name = "contractOxmConverterReadOnly")
	private IContractOXMConverter contractConverterReadOnly;
	
	public ContractController() {
		super( ContractController.class );
	}
	
	private IDecisionGenericService<IContract> getContractService(){
		if (getAccessControl().getClient().isAnonymous()){
			return contractServiceReadOnly;
		}
		else{
			return contractService;
		}
	}
	
	private IContractOXMConverter getContractConverter(){
		if (getAccessControl().getClient().isAnonymous()){
			return contractConverterReadOnly;
		}
		else{
			return contractConverter;
		}
	}	
	
	@Override
	protected Object getResponseFull( ArrayOfContracts data, PaginationInfoOXM pagination ) {
		GetContractsResponse response = new GetContractsResponse();		
		response.setContracts( data );
		response.setPagination( pagination );
		return response;
	}
	
	@Override
	protected Object getResponseShort( ArrayOfContractsShort data, PaginationInfoOXM pagination ) {
		GetContractsShortResponse response = new GetContractsShortResponse();
		response.setContracts( data );
		response.setPagination( pagination );			
		return response;
	}
	
	@Override
	protected String getName() {
		return "contract";
	}
	
	@Override
	protected String getLabel() {
		return "Contracts";
	}
	
	@Override
	protected IDecisionGenericService<IContract> getService() {
		return getContractService();
	}
	
	@Override
	protected IContractOXMConverter getConverter() {
		return getContractConverter();
	}	
		
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getContracts( HttpServletRequest request, Model model, 
			@RequestParam(value = "from",  required = false) String fromParam,
			@RequestParam(value = "limit", required = false) String limitParam,
			@RequestParam(value = "output", required = false) String output	)
	throws InvalidSearchParameterException
	{
		return getAll( request, model, fromParam, limitParam, output );
	}

	@RequestMapping(value = "/{contractId}", method = RequestMethod.GET)
	public String getContract(@PathVariable String contractId, Model model) {
		return get( contractId, model );
	}

	/* This is horrible performance-wise, but fixes the problem with UTF8 filenames */
	@RequestMapping(value = "/documents/{contractId}", method = RequestMethod.GET)
	public View getDocument(@PathVariable String contractId) {
		return new RedirectView( contractId + ".pdf" ); 
	}
	@RequestMapping(value = "/documents/original/{contractId}", method = RequestMethod.GET)
	public View getOriginalDocument(@PathVariable String contractId) {
		return new RedirectView( "../" + contractId + "-original.pdf" ); 
	}
	
	@RequestMapping( value="/documents/{contractId}.pdf", method = RequestMethod.GET)
	public void getStampedPDFDocument(@PathVariable String contractId,
			HttpServletResponse response) {
		super.getStampedPDFDocument( contractId, response );
	}

	@RequestMapping( value="/documents/{contractId}-original.pdf", method = RequestMethod.GET)
	public void getOriginalPDFDocument(@PathVariable String contractId,
			HttpServletResponse response) {
		super.getOriginalPDFDocument( contractId, response);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String putContracts(@RequestBody ContractsOXM contractsOxm, Model model){
		return put( contractsOxm, model );
	}

	@RequestMapping(value = "/{contractId}", method = RequestMethod.PUT)	
	public String updateContract(@RequestBody SingleContractOXM contractOxm,
			@PathVariable String contractId, Model model ) {
		return update( contractOxm, contractId, model );
	}

	@RequestMapping(value = "/{contractId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteContract(@PathVariable String contractId, @RequestParam String reason, @RequestParam String deletionType, HttpServletRequest request)  throws ForbiddenOperationException{
		delete( contractId, reason, deletionType, request );
	}	
		
	@RequestMapping( value = "/debug", method = RequestMethod.GET )
	public ModelAndView getContractsDebugView( HttpServletRequest request ) throws InvalidSearchParameterException {
		return debugView(request);
	}
}