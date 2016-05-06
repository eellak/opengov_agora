package gr.opengov.agora.web;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IPaymentOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.model.ArrayOfPayments;
import gr.opengov.agora.model.ArrayOfPaymentsShort;
import gr.opengov.agora.model.GetPaymentsResponse;
import gr.opengov.agora.model.GetPaymentsShortResponse;
import gr.opengov.agora.model.PaginationInfoOXM;
import gr.opengov.agora.model.PaymentOXM;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.SinglePaymentOXM;
import gr.opengov.agora.service.IDecisionGenericService;

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
@RequestMapping(value = "/payments")
public class PaymentController extends DecisionGenericController<IPayment, PaymentOXM, ArrayOfPayments, ArrayOfPaymentsShort> {
	
	@Resource(name = "paymentService")
	private IDecisionGenericService<IPayment> paymentService;
	
	@Resource(name = "paymentServiceReadOnly")
	private IDecisionGenericService<IPayment> paymentServiceReadOnly;	
	
	@Resource(name = "paymentOxmConverter")
	private IPaymentOXMConverter paymentConverter;
	
	@Resource(name = "paymentOxmConverterReadOnly")
	private IPaymentOXMConverter paymentConverterReadOnly;	
	
	public PaymentController() {
		super( PaymentController.class );
	}
	
	private IDecisionGenericService<IPayment> getPaymentService(){
		if (getAccessControl().getClient().isAnonymous()){
			return paymentServiceReadOnly;
		}
		else{
			return paymentService;
		}
	}
	
	private IPaymentOXMConverter getPaymentConverter(){
		if (getAccessControl().getClient().isAnonymous()){
			return paymentConverterReadOnly;
		}
		else{
			return paymentConverter;
		}
	}		
	
	@Override
	protected Object getResponseFull( ArrayOfPayments data, PaginationInfoOXM pagination ) {
		GetPaymentsResponse response = new GetPaymentsResponse();		
		response.setPayments( data );
		response.setPagination( pagination );
		return response;
	}
	
	@Override
	protected Object getResponseShort( ArrayOfPaymentsShort data, PaginationInfoOXM pagination ) {
		GetPaymentsShortResponse response = new GetPaymentsShortResponse();
		response.setPayments( data );
		response.setPagination( pagination );			
		return response;
	}
	
	@Override
	protected String getName() {
		return "payment";
	}
	
	@Override
	protected String getLabel() {
		return "Payments";
	}
	
	@Override
	protected IDecisionGenericService<IPayment> getService() {
		return getPaymentService();
	}
	
	@Override
	protected IPaymentOXMConverter getConverter() {
		return getPaymentConverter();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getPayments( HttpServletRequest request, Model model, 
			@RequestParam(value = "from",  required = false) String fromParam,
			@RequestParam(value = "limit", required = false) String limitParam,
			@RequestParam(value = "output", required = false) String output,
			@RequestParam(value = "contractid", required = false) String contractId,
			@RequestParam(value = "requestid", required = false) String requestId	)
	throws InvalidSearchParameterException
	{
		return getAll( request, model, fromParam, limitParam, output );
	}

	@RequestMapping(value = "/{paymentId}", method = RequestMethod.GET)
	public String getPayment(@PathVariable String paymentId, Model model) {
		return get( paymentId, model );
	}

	/* This is horrible performance-wise, but fixes the problem with UTF8 filenames */
	@RequestMapping(value = "/documents/{paymentId}", method = RequestMethod.GET)
	public View getDocument(@PathVariable String paymentId) {
		return new RedirectView( paymentId + ".pdf" ); 
	}
	@RequestMapping(value = "/documents/original/{paymentId}", method = RequestMethod.GET)
	public View getOriginalDocument(@PathVariable String paymentId) {
		return new RedirectView( "../" + paymentId + "-original.pdf" ); 
	}
	
	@RequestMapping( value="/documents/{paymentId}.pdf", method = RequestMethod.GET)
	public void getStampedPDFDocument(@PathVariable String paymentId,
			HttpServletResponse response) {
		super.getStampedPDFDocument( paymentId, response);
	}

	@RequestMapping( value="/documents/{paymentId}-original.pdf", method = RequestMethod.GET)
	public void getOriginalPDFDocument(@PathVariable String paymentId,
			HttpServletResponse response) {
		super.getOriginalPDFDocument( paymentId, response);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String putPayments(@RequestBody PaymentsOXM paymentsOxm, Model model) {
		return put( paymentsOxm, model );
	}
	
	@RequestMapping(value = "/{paymentId}", method = RequestMethod.PUT)	
	public String updatePayment(@RequestBody SinglePaymentOXM paymentOxm,
			@PathVariable String paymentId, Model model ) {	
		return update( paymentOxm, paymentId, model );
	}

	@RequestMapping(value = "/{paymentId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deletePayment(@PathVariable String paymentId, @RequestParam String reason, @RequestParam String deletionType, HttpServletRequest request)  throws ForbiddenOperationException{
		super.delete( paymentId, reason, deletionType, request );
	}	
		
	@RequestMapping( value = "/debug", method = RequestMethod.GET )
	public ModelAndView getPaymentsDebugView( HttpServletRequest request ) throws InvalidSearchParameterException {		
		return debugView(request);
	}
}