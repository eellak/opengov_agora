package gr.opengov.agora.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IProcurementRequestOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.model.ArrayOfProcurementRequests;
import gr.opengov.agora.model.ArrayOfProcurementRequestsShort;
import gr.opengov.agora.model.GetProcurementRequestsResponse;
import gr.opengov.agora.model.GetProcurementRequestsShortResponse;
import gr.opengov.agora.model.PaginationInfoOXM;
import gr.opengov.agora.model.ProcurementRequestOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.model.SingleProcurementRequestOXM;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.service.IProcurementRequestService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/procurementRequests")
public class ProcurementRequestController  extends DecisionGenericController<IProcurementRequest, ProcurementRequestOXM, ArrayOfProcurementRequests, ArrayOfProcurementRequestsShort> {
	
	@Resource(name = "procurementRequestService")
	private IProcurementRequestService procurementRequestService;
	
	@Resource(name = "procurementRequestServiceReadOnly")
	private IProcurementRequestService procurementRequestServiceReadOnly;	
	
	@Resource(name = "procurementRequestOxmConverter")
	private IProcurementRequestOXMConverter procurementRequestConverter;
	
	@Resource(name = "procurementRequestOxmConverterReadOnly")
	private IProcurementRequestOXMConverter procurementRequestConverterReadOnly;	

	public ProcurementRequestController() {
		super( ProcurementRequestController.class );
	}
	
	private IProcurementRequestService getProcurementRequestService(){
		if (getAccessControl().getClient().isAnonymous()){
			return procurementRequestServiceReadOnly;
		}
		else{
			return procurementRequestService;
		}
	}
	
	private IProcurementRequestOXMConverter getProcurementRequestConverter(){
		if (getAccessControl().getClient().isAnonymous()){
			return procurementRequestConverterReadOnly;
		}
		else{
			return procurementRequestConverter;
		}
	}		
	
	@Override
	protected Object getResponseFull( ArrayOfProcurementRequests data, PaginationInfoOXM pagination ) {
		GetProcurementRequestsResponse response = new GetProcurementRequestsResponse();		
		response.setProcurementRequests( data );
		response.setPagination( pagination );
		return response;
	}
	
	@Override
	protected Object getResponseShort( ArrayOfProcurementRequestsShort data, PaginationInfoOXM pagination ) {
		GetProcurementRequestsShortResponse response = new GetProcurementRequestsShortResponse();		
		response.setProcurementRequests( data );
		response.setPagination( pagination );			
		return response;
	}
	
	@Override
	protected String getName() {
		return "procurementRequest";
	}
	
	@Override
	protected String getLabel() {
		return "Procurement Requests";
	}
	
	@Override
	protected IProcurementRequestService getService() {
		return getProcurementRequestService();
	}
	
	@Override
	protected IProcurementRequestOXMConverter getConverter() {
		return getProcurementRequestConverter();
	}	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getProcurementRequests( HttpServletRequest request, Model model, 
			@RequestParam(value = "from",  required = false) String fromParam,
			@RequestParam(value = "limit", required = false) String limitParam,
			@RequestParam(value = "output", required = false) String output)
	throws InvalidSearchParameterException
	{
		return getAll( request, model, fromParam, limitParam, output );
	}

	@RequestMapping(value = "/{procurementRequestId}", method = RequestMethod.GET)
	public String getProcurementRequest(@PathVariable String procurementRequestId, Model model) {
		return get( procurementRequestId, model );
	}
	
	@RequestMapping(value = "/approval/{procurementRequestId}", method = RequestMethod.GET)
	public void getProcurementRequestApproval(HttpServletResponse response, @PathVariable String procurementRequestId, Model model) throws IOException {
		IProcurementRequest approval = getService().getRequestApproval(procurementRequestId);
		if (approval != null){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
			out.write( approval.getId() );	
			out.flush();
		}	
	}
	
	/* This is horrible performance-wise, but fixes the problem with UTF8 filenames */
	@RequestMapping(value = "/documents/{procurementRequestId}", method = RequestMethod.GET)
	public View getDocument(@PathVariable String procurementRequestId) {
		return new RedirectView( procurementRequestId + ".pdf" ); 
	}
	@RequestMapping(value = "/documents/original/{procurementRequestId}", method = RequestMethod.GET)
	public View getOriginalDocument(@PathVariable String procurementRequestId) {
		return new RedirectView( "../" + procurementRequestId + "-original.pdf" ); 
	}
	
	@RequestMapping(value = "/documents/{procurementRequestId}.pdf", method = RequestMethod.GET)
	public void getStampedPDFDocument(@PathVariable String procurementRequestId, HttpServletResponse response) {
		super.getStampedPDFDocument( procurementRequestId, response);
	}
	
	@RequestMapping(value = "/documents/{procurementRequestId}-original.pdf", method = RequestMethod.GET)
	public void getOriginalPDFDocument(@PathVariable String procurementRequestId, HttpServletResponse response) {
		super.getOriginalPDFDocument( procurementRequestId, response);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String putProcurementRequests(@RequestBody ProcurementRequestsOXM procurementRequestsOxm, Model model) {
		return put( procurementRequestsOxm, model);
	}

	@RequestMapping(value = "/{procurementRequestId}", method = RequestMethod.PUT)	
	public String updateProcurementRequest(@RequestBody SingleProcurementRequestOXM procurementRequestOXM, @PathVariable String procurementRequestId, Model model ) {
		return update( procurementRequestOXM, procurementRequestId, model );
	}	
	
	@RequestMapping(value = "/{procurementRequestId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteProcurementRequest(@PathVariable String procurementRequestId, @RequestParam String reason, @RequestParam String deletionType, HttpServletRequest request)  throws ForbiddenOperationException{
		delete( procurementRequestId, reason, deletionType, request);
	}
	
	@RequestMapping( value = "/debug", method = RequestMethod.GET )
	public ModelAndView getContractsDebugView( HttpServletRequest request ) throws InvalidSearchParameterException {
		return debugView( request );
	}
}