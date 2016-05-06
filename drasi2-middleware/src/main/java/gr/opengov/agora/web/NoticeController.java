package gr.opengov.agora.web;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.INoticeOXMConverter;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.INotice;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.exceptions.InvalidSearchParameterException;
import gr.opengov.agora.model.ArrayOfNotices;
import gr.opengov.agora.model.ArrayOfNoticesShort;
import gr.opengov.agora.model.GetNoticesResponse;
import gr.opengov.agora.model.GetNoticesShortResponse;
import gr.opengov.agora.model.NoticeOXM;
import gr.opengov.agora.model.NoticesOXM;
import gr.opengov.agora.model.PaginationInfoOXM;
import gr.opengov.agora.model.SingleNoticeOXM;
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
@RequestMapping(value = "/notices")
public class NoticeController extends DecisionGenericController<INotice, NoticeOXM, ArrayOfNotices, ArrayOfNoticesShort>{
	
	@Resource(name = "noticeService")
	private IDecisionGenericService<INotice> noticeService;
	
	@Resource(name = "noticeServiceReadOnly")
	private IDecisionGenericService<INotice> noticeServiceReadOnly;	
	
	@Resource(name = "noticeOxmConverter")
	private INoticeOXMConverter noticeConverter;
	
	@Resource(name = "noticeOxmConverterReadOnly")
	private INoticeOXMConverter noticeConverterReadOnly;	
	
	public NoticeController() {
		super( NoticeController.class );
	}
	
	private IDecisionGenericService<INotice> getNoticeService(){
		if (getAccessControl().getClient().isAnonymous()){
			return noticeServiceReadOnly;
		}
		else{
			return noticeService;
		}
	}
	
	private INoticeOXMConverter getNoticeConverter(){
		if (getAccessControl().getClient().isAnonymous()){
			return noticeConverterReadOnly;
		}
		else{
			return noticeConverter;
		}
	}	
	
	@Override
	protected Object getResponseFull( ArrayOfNotices data, PaginationInfoOXM pagination ) {
		GetNoticesResponse response = new GetNoticesResponse();		
		response.setNotices( data );
		response.setPagination( pagination );
		return response;
	}
	
	@Override
	protected Object getResponseShort( ArrayOfNoticesShort data, PaginationInfoOXM pagination ) {
		GetNoticesShortResponse response = new GetNoticesShortResponse();
		response.setNotices( data );
		response.setPagination( pagination );			
		return response;
	}
	
	@Override
	protected String getName() {
		return "notice";
	}
	
	@Override
	protected String getLabel() {
		return "Notices";
	}
	
	@Override
	protected IDecisionGenericService<INotice> getService() {
		return getNoticeService();
	}
	
	@Override
	protected INoticeOXMConverter getConverter() {
		return getNoticeConverter();
	}	
		
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getNotices( HttpServletRequest request, Model model, 
			@RequestParam(value = "from",  required = false) String fromParam,
			@RequestParam(value = "limit", required = false) String limitParam,
			@RequestParam(value = "output", required = false) String output	)
	throws InvalidSearchParameterException
	{
		return getAll( request, model, fromParam, limitParam, output );
	}

	@RequestMapping(value = "/{noticeId}", method = RequestMethod.GET)
	public String getNotice(@PathVariable String noticeId, Model model) {
		return get( noticeId, model );
	}

	/* This is horrible performance-wise, but fixes the problem with UTF8 filenames */
	@RequestMapping(value = "/documents/{noticeId}", method = RequestMethod.GET)
	public View getDocument(@PathVariable String noticeId) {
		return new RedirectView( noticeId + ".pdf" ); 
	}
	@RequestMapping(value = "/documents/original/{noticeId}", method = RequestMethod.GET)
	public View getOriginalDocument(@PathVariable String noticeId) {
		return new RedirectView( "../" + noticeId + "-original.pdf" ); 
	}
	
	@RequestMapping( value="/documents/{noticeId}.pdf", method = RequestMethod.GET)
	public void getStampedPDFDocument(@PathVariable String noticeId,
			HttpServletResponse response) {
		super.getStampedPDFDocument( noticeId, response );
	}

	@RequestMapping( value="/documents/{noticeId}-original.pdf", method = RequestMethod.GET)
	public void getOriginalPDFDocument(@PathVariable String noticeId,
			HttpServletResponse response) {
		super.getOriginalPDFDocument( noticeId, response);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public String putNotices(@RequestBody NoticesOXM noticesOxm, Model model){
		return put( noticesOxm, model );
	}

	@RequestMapping(value = "/{noticeId}", method = RequestMethod.PUT)	
	public String updateNotice(@RequestBody SingleNoticeOXM noticeOxm,
			@PathVariable String noticeId, Model model ) {
		return update( noticeOxm, noticeId, model );
	}

	@RequestMapping(value = "/{noticeId}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteNotice(@PathVariable String noticeId, @RequestParam String reason, @RequestParam String deletionType, HttpServletRequest request)  throws ForbiddenOperationException{
		delete( noticeId, reason, deletionType, request );
	}	
		
	@RequestMapping( value = "/debug", method = RequestMethod.GET )
	public ModelAndView getNoticesDebugView( HttpServletRequest request ) throws InvalidSearchParameterException {
		return debugView(request);
	}
	
}