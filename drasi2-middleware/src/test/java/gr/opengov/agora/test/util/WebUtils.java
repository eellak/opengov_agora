package gr.opengov.agora.test.util;

import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.model.ContractOXM;
import gr.opengov.agora.model.GetContractsResponse;
import gr.opengov.agora.model.GetPaymentsResponse;
import gr.opengov.agora.model.GetProcurementRequestsResponse;
import gr.opengov.agora.model.PaymentOXM;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.ProcurementRequestOXM;
import gr.opengov.agora.model.PublicOrganizationDecisionOXM;
import gr.opengov.agora.model.StoreDecisionResponse;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.PutMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public final class WebUtils {
	private Logger logger;
	private static XMLUtils xmlUtils;
	private static XMLUtils xmlNVUtils;
	private static Utility utils;
	@Value( "${baseUrl}" ) String baseUrl;
	
	public WebUtils() {		
		logger = LoggerFactory.getLogger(WebUtils.class);
		this.xmlUtils = XMLUtils.newInstance();
		this.xmlNVUtils = XMLUtils.newInstance( false );
		this.utils = new Utility( xmlUtils );		
	}
	
	public void setLogger( Logger logger ) {
		this.logger = logger;
	}
	
	public String getBaseUrl() {
		return baseUrl.trim() + "/";
	}
	
	public String getContractsUrl() {
		return getBaseUrl() + "contracts/";
	}
	
	public String getPaymentsUrl() {
		return getBaseUrl() + "payments/";
	}	
	
	public String getProcurementRequestsUrl() {
		return getBaseUrl() + "procurementRequests/";
	}
	
	public void setupRequest( WebRequest request ) {
		request.setHeaderField( "Content-Type", "application/xml" );
		request.setHeaderField( "Accept", "application/xml" );
	}
	
	public WebConversation authorizedWebConversation( String realm, String username, String password ) {
		WebConversation wc = new WebConversation();
		wc.setAuthorization(username, password);
		return wc;
	}
	
	public WebConversation authorizedWebConversation( ) {
		return authorizedWebConversation( "Basic", "366_admin", "366" );
	}
	
	public WebResponse getResponse( String url ) throws IOException, WebException, SAXException {
		WebConversation wc = authorizedWebConversation();
		wc.setExceptionsThrownOnErrorStatus( false );
		WebRequest request = new GetMethodWebRequest( url );
		setupRequest( request );
		WebResponse response = wc.getResponse( request );
		if ( response.getResponseCode() != HttpStatus.SC_OK ) {
			logger.debug("[ERROR RESPONSE]");
			logger.debug( "ERROR RESPONSE:\n\n\n" + IOUtil.toString( response.getInputStream() ) );
			logger.debug("[/ERROR RESPONSE]");
			throw new WebException( response.getResponseCode() );			
		}
		return response;
	}

	public String getAda(){
		WebConversation wc = authorizedWebConversation();
		wc.setExceptionsThrownOnErrorStatus( false );
		WebRequest request = new PostMethodWebRequest( "http://test.diavgeia.gov.gr/diavgeia_service/index.php" );
		request.setHeaderField( "Content-Type", "application/x-www-form-urlencoded" );
		request.setHeaderField( "Accept", "text/html" );
		request.setParameter("function", "generateADA");
		request.setParameter("user", "366_admin");
		request.setParameter("pass", "366");
		request.setParameter("date", "110530");
		request.setParameter("orgID", "366");
		
		WebResponse response = null;
		try {
			response = wc.getResponse( request );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ( (response != null) && response.getResponseCode() == HttpStatus.SC_OK ) {
			try {
//				logger.debug( "ERROR RESPONSE:\n\n\n" + IOUtil.toString( response.getInputStream() ) );
				return (IOUtil.toString( response.getInputStream() ));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public String getNameFromAfm(String afm, boolean anonymous){
		WebConversation wc = null;
		if (anonymous)
			wc = new WebConversation();
		else
			wc = authorizedWebConversation( "agora", "366_admin", "366" );
		wc.setExceptionsThrownOnErrorStatus( false );
		WebRequest request = new GetMethodWebRequest( getBaseUrl() + "afm/" );
		setupRequest( request );
		request.setParameter("afm", afm);
		
		
		WebResponse response = null;
		
		try {
			response = wc.getResponse( request );
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		}
		
		if ( (response != null) && response.getResponseCode() == HttpStatus.SC_OK ) {
			try {
				return (IOUtil.toString( response.getInputStream() ));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
		
	}
	
	public StoreDecisionResponse storeDecisions( InputStream stream, String decisionTypeUrl, String username, String password ) throws WebException, IOException, SAXException {
		WebConversation wc = authorizedWebConversation( "agora", username, password );
		wc.setExceptionsThrownOnErrorStatus( false );
		WebRequest request = new PostMethodWebRequest( decisionTypeUrl, stream, "application/xml" );		
		setupRequest( request );		
		WebResponse response = wc.getResponse( request );		
		if ( response.getResponseCode() != HttpStatus.SC_OK) {	
			logger.debug("[ERROR RESPONSE]");
			logger.debug( "ERROR RESPONSE:\n\n\n" + IOUtil.toString( response.getInputStream() ) );
			logger.debug("[/ERROR RESPONSE]");
			throw new WebException( response.getResponseCode() );
		}
		return xmlUtils.unmarshal(response.getInputStream(), StoreDecisionResponse.class);
	}			
	
	public StoreDecisionResponse storeDecisions( InputStream stream, String decisionTypeUrl ) throws WebException, IOException, SAXException {
		return storeDecisions( stream, decisionTypeUrl, "366_admin", "366" );
	}
	
	public void deleteDecision( String id, String decisionTypeUrl, String reason, String deletionType ) throws WebException, IOException, SAXException {
		WebConversation wc = authorizedWebConversation();
		wc.setExceptionsThrownOnErrorStatus( false );
		WebRequest request;
		
		String params = "?";
		if ( reason != null ) {
			params = params + "reason=" + reason;
		}
		if ( deletionType != null){
			if (params.length() > 1){
				params = params + "&deletionType="+deletionType;
			}
			else
			{
				params = params + "deletionType="+deletionType;
			}
		}
		
		if ( params.length() > 1 ) {
			request = new DeleteMethodWebRequest( decisionTypeUrl + id + params );
		}
		else {
			request = new DeleteMethodWebRequest( decisionTypeUrl + id );
		}
		setupRequest( request );
		WebResponse response = wc.getResponse( request );
		if ( response.getResponseCode() != HttpStatus.SC_OK) {			
			logger.debug( "DELETE RESPONSE:\n\n\n" + IOUtil.toString( response.getInputStream() ) );			
			throw new WebException( response.getResponseCode() );
		}				
	}

	public void updateDecision(String xmlFile, String id, String decisionTypeUrl ) throws WebException, IOException, SAXException	{
		updateDecision(xmlUtils.readXml( xmlFile ), id, decisionTypeUrl );
	}
	
	public void updateDecision(InputStream input, String id, String decisionTypeUrl ) throws WebException, IOException, SAXException	{
		WebConversation wc = authorizedWebConversation();
		wc.setExceptionsThrownOnErrorStatus( false );
		WebRequest request = new PutMethodWebRequest( decisionTypeUrl + id, input, "application/xml" );
		setupRequest( request );
		WebResponse response = wc.getResponse( request );
		if ( response.getResponseCode() != HttpStatus.SC_OK) {			
			logger.debug( "UPDATE RESPONSE:\n\n\n" + IOUtil.toString( response.getInputStream() ) );			
			throw new WebException( response.getResponseCode() );
		}		
	}	
	
	public GetContractsResponse getContracts( String url ) throws IOException, WebException, SAXException  {		
		WebResponse response = getResponse( url );
		return xmlUtils.unmarshal( response.getInputStream(), GetContractsResponse.class );		
	}
	
	public PublicOrganizationDecisionOXM getDecisionOXM( String url, Class<?> cl ) throws IOException, WebException, SAXException  {		
		WebResponse response = getResponse( url );
		PublicOrganizationDecisionOXM decision = null;
		if (cl.isAssignableFrom(ContractOXM.class)){
			GetContractsResponse contractResponse = xmlUtils.unmarshal( response.getInputStream(), GetContractsResponse.class );
			decision = contractResponse.getContracts().getContract().get(0);			
		}
		
		if (cl.isAssignableFrom(PaymentOXM.class)){
			GetPaymentsResponse paymentResponse = xmlUtils.unmarshal( response.getInputStream(), GetPaymentsResponse.class );
			decision = paymentResponse.getPayments().getPayment().get(0);			
		}
		
		if (cl.isAssignableFrom(ProcurementRequestOXM.class)){
			GetProcurementRequestsResponse procurementRequestResponse = xmlUtils.unmarshal( response.getInputStream(), GetProcurementRequestsResponse.class );
			decision = procurementRequestResponse.getProcurementRequests().getRequest().get(0);			
		}		
	
		return decision; 		
	}
	
	public GetPaymentsResponse getPayments( String url ) throws Exception {		
		WebResponse response = getResponse( url );		
		return xmlUtils.unmarshal( response.getInputStream(), GetPaymentsResponse.class );		
	}	
	
	public GetProcurementRequestsResponse getProcurementRequests( String url ) throws IOException, WebException, SAXException {		
		WebResponse response = getResponse( url );
		return xmlUtils.unmarshal( response.getInputStream(), GetProcurementRequestsResponse.class );		
	}	
	
	public StoreDecisionResponse storeDecisions(String xmlFile, String decisionsUrl) throws WebException, IOException, SAXException {
		return storeDecisions( xmlUtils.readXml( xmlFile ), decisionsUrl );
	}	
	
	public StoreDecisionResponse storeContracts(String xmlFile) throws WebException, IOException, SAXException {
		return storeDecisions( xmlUtils.readXml( xmlFile ), getContractsUrl());
	}
	
	public StoreDecisionResponse storeContracts(InputStream input) throws WebException, IOException, SAXException {
		return storeDecisions( input, getContractsUrl());
	}	
	
	public StoreDecisionResponse storeDecisions(String xmlFile, String decisionsUrl, String username, String password ) throws WebException, IOException, SAXException {
		return storeDecisions( xmlUtils.readXml( xmlFile ), decisionsUrl, username, password );
	}	
	
	public StoreDecisionResponse storeContracts(String xmlFile, String username, String password ) throws WebException, IOException, SAXException {
		return storeDecisions( xmlUtils.readXml( xmlFile ), getContractsUrl(), username, password );
	}
	
	public StoreDecisionResponse storeProcurementRequests(String xmlFile) throws WebException, IOException, SAXException {
		return storeDecisions( xmlUtils.readXml( xmlFile ), getProcurementRequestsUrl());
	}	
		
	public StoreDecisionResponse storeProcurementRequests(InputStream input) throws WebException, IOException, SAXException {
		return storeDecisions( input, getProcurementRequestsUrl());
	}		
	
	public StoreDecisionResponse storePayments(String xmlFile) throws Exception {
		return storeDecisions( xmlUtils.readXml( xmlFile ), getPaymentsUrl());
	}		
	
	public void updateContract(String xmlFile, String id) throws WebException, IOException, SAXException {
		updateDecision(xmlFile, id, getContractsUrl());
	}	
	
	public void updateProcurementRequest(String xmlFile, String id) throws WebException, IOException, SAXException {
		updateDecision(xmlFile, id, getProcurementRequestsUrl());
	}	
	
	public void updateProcurementRequest(InputStream input, String id) throws WebException, IOException, SAXException {
		updateDecision(input, id, getProcurementRequestsUrl());
	}
	
	public void updatePayment(String xmlFile, String id) throws Exception	{
		updateDecision(xmlFile, id, getPaymentsUrl());
	}
	
	public void deleteContract( String id, String reason, String deletionType ) throws WebException, IOException, SAXException {
		deleteDecision( id, getContractsUrl(), reason, deletionType );
	}

	public StoreDecisionResponse storePayment(String xmlFile, String contractId) throws Exception{
		if (contractId == null) return null;
		PaymentsOXM paymentsOxm = ((PaymentsOXM)xmlUtils.unmarshal(xmlUtils.readXml(xmlFile), PaymentsOXM.class));
		paymentsOxm.getPayment().get(0).setContractId(contractId);		
		return storeDecisions( xmlUtils.marshal(paymentsOxm), getPaymentsUrl());
	}
	
//	public void updateContract(String xmlFile, String id) throws Exception	{
//		WebConversation wc = authorizedWebConversation();
//		WebRequest request = new PutMethodWebRequest( getContractsUrl() + id, xmlUtils.readXml( xmlFile ), "application/xml" );
//		setupRequest( request );
//		WebResponse response = wc.getResponse( request );
//		if ( response.getResponseCode() != HttpStatus.SC_OK) {			
//			logger.debug( "UPDATE RESPONSE:\n\n\n" + IOUtil.toString( response.getInputStream() ) );			
//			fail();
//		}		
//	}		
//	
//	public void updateProcurementRequest(String xmlFile, String id) throws Exception	{
//		WebConversation wc = authorizedWebConversation();
//		WebRequest request = new PutMethodWebRequest( getProcurementRequestsUrl() + id, xmlUtils.readXml( xmlFile ), "application/xml" );
//		setupRequest( request );
//		WebResponse response = wc.getResponse( request );
//		if ( response.getResponseCode() != HttpStatus.SC_OK) {			
//			logger.debug( "UPDATE RESPONSE:\n\n\n" + IOUtil.toString( response.getInputStream() ) );			
//			fail();
//		}		
//	}	
	
	
}
