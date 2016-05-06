package gr.opengov.agora.test;

import static org.junit.Assert.*;

import gr.opengov.agora.converters.IContractOXMConverter;
import gr.opengov.agora.converters.IPaymentOXMConverter;
import gr.opengov.agora.converters.IProcurementRequestOXMConverter;
import gr.opengov.agora.domain.Ada;
import gr.opengov.agora.domain.CmsMetadata;
import gr.opengov.agora.domain.ContractItem;
import gr.opengov.agora.domain.ContractParty;
import gr.opengov.agora.domain.Cost;
import gr.opengov.agora.domain.ICmsMetadata;
import gr.opengov.agora.domain.IContract;
import gr.opengov.agora.domain.IContractItem;
import gr.opengov.agora.domain.ICost;
import gr.opengov.agora.domain.ICpv;
import gr.opengov.agora.domain.IDecisionStorageReference;
import gr.opengov.agora.domain.IDocument;
import gr.opengov.agora.domain.IPayment;
import gr.opengov.agora.domain.IProcurementRequest;
import gr.opengov.agora.domain.PaymentItem;
import gr.opengov.agora.exceptions.ForbiddenOperationException;
import gr.opengov.agora.model.ContractsOXM;
import gr.opengov.agora.model.PaymentsOXM;
import gr.opengov.agora.model.ProcurementRequestsOXM;
import gr.opengov.agora.search.ISearchParameter;
import gr.opengov.agora.search.SearchParameterFactory;
import gr.opengov.agora.security.IAccessControl;
import gr.opengov.agora.security.IClientFactory;
import gr.opengov.agora.service.IDecisionGenericService;
import gr.opengov.agora.test.stub.AdminTestClientFactory;
import gr.opengov.agora.test.util.IDatabaseHandler;
import gr.opengov.agora.test.util.XMLUtils;
import gr.opengov.agora.util.PaginationInfo;
import gr.opengov.agora.validation.IValidation;
import gr.opengov.agora.validation.IValidationError;
import gr.opengov.agora.validation.Validation;
import gr.opengov.agora.web.PaymentController;
import gr.opengov.agora.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtil;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( "classpath:spring/root-context.xml" )
public class PaymentServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceTest.class);
	@Resource( name="paymentService" )
	private IDecisionGenericService<IPayment> service;
	@Resource( name="contractService" )
	private IDecisionGenericService<IContract> contractService;	
	@Resource( name="procurementRequestService" )
	private IDecisionGenericService<IProcurementRequest> requestService;	
	@javax.annotation.Resource( name="databaseHandler" )
	private IDatabaseHandler dbHandler;
	private static XMLUtils xmlUtils;
	@Resource( name="contractOxmConverter" )
	private IContractOXMConverter contractConverter;
	@Resource( name="paymentOxmConverter" )
	private IPaymentOXMConverter paymentConverter;	
	@Resource( name="procurementRequestOxmConverter" )
	private IProcurementRequestOXMConverter procurementRequestOxmConverter;	
	@Resource( name="accessControl" )
	private IAccessControl accessControl;
	
	@BeforeClass
	public static void init() {
		xmlUtils = XMLUtils.newInstance();
	}
	
	private IPayment loadPayment( String xmlFile ) throws IOException{
		PaymentsOXM payments = xmlUtils.unmarshal( xmlFile, PaymentsOXM.class );
		return paymentConverter.toObject( payments.getPayment().get( 0 ) );
	}
	
	private IContract loadContract( String xmlFile ) throws Exception {
		ContractsOXM contracts = xmlUtils.unmarshal( xmlFile, ContractsOXM.class );
		return contractConverter.toObject( contracts.getContract().get( 0 ) );
	}
	
	private IProcurementRequest loadRequest( String xmlFile ) throws Exception {
		ProcurementRequestsOXM requests = xmlUtils.unmarshal( xmlFile, ProcurementRequestsOXM.class );
		return procurementRequestOxmConverter.toObject( requests.getRequest().get( 0 ) );
	}	
//@Ignore	
	@Test
	public void testSaveSinglePaymentIgnoreAccess() throws Exception {
		logger.debug( "Testing saving a single payment, no access control" );
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );
		IValidation validation = new Validation();
		service.save( Arrays.asList( payment), validation );
		printValidationErrors(validation);
		List<IPayment> saved = service.getAll();
		assertTrue( saved.size() == 1 );
	}
	
//@Ignore
	@Test
	public void testGetDocument() throws Exception {
		logger.debug( "Testing retrieving a payments's document" );
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );
		byte[] dataOriginal = payment.getDocument().getData().clone();
		logger.debug( "...Original data size: " + dataOriginal.length );
		service.save( Arrays.asList( payment ), new Validation() );
		List<IPayment> saved = service.getAll();
		String id = saved.get(0).getId();		
		IDocument documentRetrieved = service.getDocument( id );
		assertTrue( Arrays.equals( dataOriginal, IOUtil.toByteArray( service.getDocumentOriginalData(documentRetrieved) ) ) );				
	}
	

	private void updateSinglePayment( boolean emptyDocument ) throws Exception {
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );
		byte[] dataOriginal = payment.getDocument().getData().clone();		
		service.save( Arrays.asList( payment ), new Validation() );
		String savedId = service.getAll().get(0).getId();		
		payment = loadPayment( "single-payment.xml" );
		if ( emptyDocument ) {
			payment.setDocument( null );
		}
		payment.setId( savedId );
		payment.setUniqueDocumentCode( "newDocumentCode" );
		Validation validation = new Validation();
		service.update(payment, validation );
		if ( !validation.isValid() ) {
			logger.error( "Validation Error:\n" + validation.toString() );
			fail();
		}		
		payment = service.get( savedId );
		assertEquals( payment.getUniqueDocumentCode(), "newDocumentCode" );
		assertEquals( payment.getId(), savedId );
		IDocument document = service.getDocument( savedId );
		assertTrue( Arrays.equals( dataOriginal, IOUtil.toByteArray( service.getDocumentOriginalData(document) ) ) );
	}	

//@Ignore
	@Test
	public void updateSinglePaymentInvalidDates() throws Exception {
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );
		byte[] dataOriginal = payment.getDocument().getData().clone();		
		service.save( Arrays.asList( payment ), new Validation() );
		String savedId = service.getAll().get(0).getId();		
		payment = loadPayment( "single-payment.xml" );

		payment.setId( savedId );
		payment.setUniqueDocumentCode( "newDocumentCode" );
		Validation validation = new Validation();
		service.update(payment, validation );
		if ( !validation.isValid() ) {
			logger.error( "Validation Error:\n" + validation.toString() );
			fail();
		}		
		payment = service.get( savedId );
		assertEquals( payment.getUniqueDocumentCode(), "newDocumentCode" );
		assertEquals( payment.getId(), savedId );
		IDocument document = service.getDocument( savedId );
		assertTrue( Arrays.equals( dataOriginal, IOUtil.toByteArray( service.getDocumentOriginalData(document) ) ) );
	}
//@Ignore
	@Test
	public void testUpdateSinglePaymentWithoutDocument() throws Exception{
		logger.debug( "Testing updating a single contract without document" );
		updateSinglePayment( true );
	}
	
//@Ignore
	@Test
	public void testUpdateSinglePaymentWithDocument() throws Exception {
		logger.debug( "Testing updating a single payment without document" );
		updateSinglePayment( false );
	}
		
//@Ignore
	@Test
	public void testPurgeSingleStoredPayment() throws Exception {
		logger.debug( "Testing purging a single stored payment" );
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );	
		
		service.save( Arrays.asList( payment ), new Validation() );		
		List<IPayment> saved = service.getAll();		
		assertTrue( saved.size() == 1 );
		payment = saved.get(0);
		Validation validation = new Validation();
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		service.purge( payment.getId() );		
		accessControl.setClientFactory(defaultFactory);
		saved = service.getAll();
		assertTrue( saved.isEmpty() );
		assertTrue( validation.isValid() );
	}
	
//	@Ignore
//	@Test
//	public void testPurgeSingleStoredOldPayment() throws Exception {
//		logger.debug( "Testing purging a single stored old payment" );
//		dbHandler.clearDb();
//		IPayment payment = loadPayment( "single-payment.xml" );	
//		
////		ICmsMetadata metadata = new CmsMetadata();
////		Calendar submited = Calendar.getInstance();
////		submited.add(Calendar.DAY_OF_MONTH, -3);
////		metadata.setSubmissionTime( submited );
////		payment.setCmsMetadata(metadata);
//		
//		service.save( Arrays.asList( payment ), new Validation() );		
//		List<IPayment> saved = service.getAll();		
//		assertTrue( saved.size() == 1 );
//		payment = saved.get(0);
//		Validation validation = new Validation();
//		IClientFactory defaultFactory = accessControl.getClientFactory();
//		accessControl.setClientFactory( new AdminTestClientFactory() );
//		service.purge( payment.getId() );		
//		accessControl.setClientFactory(defaultFactory);
//		saved = service.getAll();
//		assertTrue( saved.isEmpty() );
//		assertTrue( validation.hasErrorCode("endOfChangeTimeSlot") );
//	}	
//@Ignore	
 	@Test
	public void testDeleteSingleStoredPayment() throws Exception {
 		logger.debug( "Testing deleting a single stored payment" );
 		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );	
 		
		service.save( Arrays.asList( payment ), new Validation() );		
		List<IPayment> saved = service.getAll();		
		assertTrue( saved.size() == 1 );
		payment = saved.get(0);
		Validation validation = new Validation();
		service.delete( payment.getId(), "testReason", "1" );
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );		
		saved = service.getAll();
		accessControl.setClientFactory( defaultFactory );
		assertEquals( saved.size(), 1 );
		payment = saved.get( 0 );
		assertTrue( validation.isValid() );
		assertTrue( payment.isDeleted() );
		assertTrue( payment.getCmsMetadata().getDeletedReason().equals("testReason") );			
 	}
//@Ignore 	
	@Test
	public void testSavePaymentWithInvalidDate() throws Exception {
		logger.debug( "Testing saving a single payment with invalid dateSubmitted" );
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );
		
		//related contract with dateSigned > than payment.dateSigned
		logger.debug( "Testing saving a single contract, no access control" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		contract.setDateSigned(Calendar.getInstance());
		contractService.save( Arrays.asList( contract ), new Validation() );
		List<IContract> savedContracts = contractService.getAll();
		assertTrue( savedContracts.size() == 1 );
		payment.setContract(contract);
		service.save( Arrays.asList( payment ), new Validation());		
		List<IPayment> savedPayments = service.getAll();		
		assertTrue( savedPayments.size() == 0 );		
		
		//related request with dateSigned > than payment.dateSigned
		logger.debug( "Testing saving a single procurement request, no access control" );
		IProcurementRequest request = loadRequest( "single-request.xml" );
		request.setDateSigned(Calendar.getInstance());
		IValidation validation = new Validation();
		requestService.save( Arrays.asList( request ), validation );
		if (!validation.isValid()){
			printValidationErrors(validation);
		}
		List<IProcurementRequest> requestsSaved = requestService.getAll();
		assertTrue( requestsSaved.size() == 1 );
		Ada ada = new Ada();
		ada.setAdaCode(requestsSaved.get(0).getId());
		ada.setAdaType("ProcurementRequest");
		payment.setRelatedAdas(Arrays.asList(ada));
		service.save( Arrays.asList( payment ), new Validation());		
		savedPayments = service.getAll();		
		assertTrue( savedPayments.size() == 0 );
	}
//@Ignore	
//	@Test
//	public void testSavePaymentWithInvalidAmount() throws Exception {	
//		logger.debug( "Testing saving a single payment with invalid totalAmount" );
//		dbHandler.clearDb();
//		IPayment payment = loadPayment( "single-payment.xml" );
//		
//		//related contract totalAmount > than payment.totalAmount * 1.04
//		logger.debug( "Testing saving a single contract, no access control" );
//		IContract contract = loadContract( "sample-contract-1.xml" );
//		ICost cost = new Cost(); 
//		cost.setCurrencyIdRef(contract.getContractItems().get(0).getCost().getCurrencyIdRef());
//		cost.setVatPercentage(contract.getContractItems().get(0).getCost().getVatPercentage());
//			
//		cost.setCostBeforeVat(contract.getTotalCostBeforeVat()*(Constants.COSTOVERRUN + 1.01) );
//		payment.getPaymentItems().get(0).getContractItem().setCost(cost);
//		contractService.save( Arrays.asList( contract ), new Validation() );
//		List<IContract> savedContracts = contractService.getAll();
//		assertTrue( savedContracts.size() == 1 );
//		payment.setContract(contract);
//		Validation validation = new Validation();
//		service.save( Arrays.asList( payment ), validation);		
//		List<IPayment> savedPayments = service.getAll();		
//		assertTrue( savedPayments.size() == 0 );		
//		
//		//related request with totalAmount > than payment.totalAmount
//		logger.debug( "Testing saving a single procurement request, no access control" );
//		IProcurementRequest request = loadRequest( "single-request.xml" );
//		cost.setCostBeforeVat(request.getTotalCostBeforeVat()*(Constants.COSTOVERRUN + 1.01) );
//		payment.getPaymentItems().get(0).getContractItem().setCost(cost);
//		validation = new Validation();
//		requestService.save( Arrays.asList( request ), validation );
//		if (!validation.isValid()){
//			printValidationErrors(validation);
//		}
//		List<IProcurementRequest> requestsSaved = requestService.getAll();
//		assertTrue( requestsSaved.size() == 1 );
//		Ada ada = new Ada();
//		ada.setAdaCode(requestsSaved.get(0).getId());
//		ada.setAdaType("ProcurementRequest");
//		payment.setRelatedAdas(Arrays.asList(ada));
//		service.save( Arrays.asList( payment ), new Validation());		
//		savedPayments = service.getAll();		
//		assertTrue( savedPayments.size() == 0 );		
//		
//	}
//@Ignore	
	@Test
	public void testSavePaymentsOfContract() throws Exception {	
		logger.debug( "Testing saving two payments for a contract. Second payment exeeds the approved cost overrun" );
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );
		
		//related contract totalAmount > than payment.totalAmount * 1.04
		logger.debug( "Testing saving a single contract, no access control" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		ICost cost = new Cost(); 
		cost.setCurrencyIdRef(contract.getContractItems().get(0).getCost().getCurrencyIdRef());
		cost.setVatPercentage(contract.getContractItems().get(0).getCost().getVatPercentage());
			
		cost.setCostBeforeVat(contract.getTotalCostBeforeVat()*(1.01) );
		payment.getPaymentItems().get(0).getContractItem().setCost(cost);
		Validation validation = new Validation();
		contractService.save( Arrays.asList( contract ), validation );
		logger.debug("contract currency errors\n");
		printValidationErrors(validation);
		logger.debug("/contract currency errors\n");
		List<IContract> savedContracts = contractService.getAll();
		assertTrue( savedContracts.size() == 1 );
		payment.getPaymentItems().get(0).getContractItem().setContract(contract);
		payment.setContract(contract);
		validation = new Validation();
		logger.debug("payment currency errors\n");
		service.save( Arrays.asList( payment ), validation);
		printValidationErrors(validation);
		logger.debug("/payment currency errors\n");
		List<IPayment> savedPayments = service.getAll();		
		assertTrue( savedPayments.size() == 1 );		
		
	
		IPayment paymentb = loadPayment( "single-payment.xml" );				
		ICost costb = new Cost(); 
		costb.setCurrencyIdRef(contract.getContractItems().get(0).getCost().getCurrencyIdRef());
		costb.setVatPercentage(contract.getContractItems().get(0).getCost().getVatPercentage());
			
		costb.setCostBeforeVat(contract.getTotalCostBeforeVat()*(0.99) );
		paymentb.getPaymentItems().get(0).getContractItem().setCost(costb);
		
		paymentb.setContract(contract);
		service.save( Arrays.asList( paymentb ), validation);		
		savedPayments = service.getAll();		
		assertTrue( savedPayments.size() == 1 );		
		
	}	
	
	private IContractItem getCopy(IContractItem contractItem){
		IContractItem result = new ContractItem();
		result.setAddress(contractItem.getAddress());
		result.setAddressNo(contractItem.getAddressNo());
		result.setAddressPostal(contractItem.getAddressPostal());
		result.setCity(contractItem.getCity());
		ICost cost = new Cost();
		cost.setCostBeforeVat(contractItem.getCost().getCostBeforeVat());
		cost.setCurrencyIdRef(contractItem.getCost().getCurrencyIdRef());
		cost.setVatPercentage(contractItem.getCost().getVatPercentage());
		result.setCost(cost);
		result.setCountryIdRef(contractItem.getCountryIdRef());
		result.setCountryProducedIdRef(contractItem.getCountryProducedIdRef());
		result.setCpvCodes(new LinkedHashSet<ICpv>());
		result.getCpvCodes().addAll(contractItem.getCpvCodes());
		result.setDescription(contractItem.getDescription());
		result.setInvoiceNumber(contractItem.getInvoiceNumber());
		result.setKaeCodes(new ArrayList<String>());
		result.getKaeCodes().addAll(contractItem.getKaeCodes());
		result.setNuts(contractItem.getNuts());
		if (contractItem.getProcurementRequest() != null){
			result.setProcurementRequest(contractItem.getProcurementRequest());
		}
		if (contractItem.getContract() != null){
			result.setContract(contractItem.getContract());
		}
		if (contractItem.getNotice() != null){
			result.setNotice(contractItem.getNotice());
		}		
		result.setQuantity(contractItem.getQuantity());
		result.setUnitOfMeasureIdRef( contractItem.getCountryProducedIdRef());
		
		return result;
	}
//@Ignore
	@Test
	public void testSavePaymentForRequest() throws Exception {	
		logger.debug( "Testing saving payments for a procurement request" );
		dbHandler.clearDb();
		IValidation validation = new Validation();
		
		logger.debug( "Testing saving a single procurement request, no access control" );
		IProcurementRequest request = loadRequest( "single-request.xml" );
		requestService.save( Arrays.asList( request ), validation );
		if (!validation.isValid()){
			printValidationErrors(validation);
		}
		List<IProcurementRequest> saved = requestService.getAll();
		assertTrue( saved.size() == 1 );		
		
		IProcurementRequest requestApproval = loadRequest( "single-request.xml" );
		requestApproval.setApprovesRequest(saved.get(0));
		requestApproval.setAwardProcedureIdRef("3");
		requestApproval.getContractItems().get(0).setDescription("new description");
		validation = new Validation();
		List<IDecisionStorageReference> approvalsRef = requestService.save( Arrays.asList( requestApproval ), validation );
		logger.debug("request 2 saving errors:\n");
		printValidationErrors(validation);		
		saved = requestService.getAll();
		assertTrue( saved.size() == 2 );		
		IProcurementRequest finalRequest = requestService.getUnmanaged(approvalsRef.get(0).getId());				
		
		logger.debug("saving one more payment on saved procurement request. Total payed for this request equals 100% of the request");
		IPayment payment = loadPayment( "single-payment.xml" );
		payment.getPaymentItems().clear();
		Ada ada = new Ada();
		ada.setAdaCode(finalRequest.getId());
		ada.setAdaType("ProcurementRequest");
		payment.setRelatedAdas(Arrays.asList(ada));		

		IContractItem contractItem = getCopy(finalRequest.getContractItems().get(0));
		contractItem.setProcurementRequest(finalRequest);
		contractItem.getCost().setCostBeforeVat(finalRequest.getTotalCostBeforeVat());
		contractItem.setDescription("description for CI of PI");
		PaymentItem paymentItem = new PaymentItem();
		paymentItem.setContractItem(contractItem);
		ContractParty party = new ContractParty();
		party.setAfm("023928277");
		party.setName("Δημήτρης Κολιός");
		party.setCountryIdRef("GR");
		paymentItem.setContractParty(party);
		payment.getPaymentItems().add(paymentItem);
		
		validation = new Validation();
		service.save( Arrays.asList( payment ), validation);
		logger.debug("saving payment for request\n");
		printValidationErrors(validation);
		
		List<IPayment> savedPayments = service.getAll();		
		assertTrue( savedPayments.size() == 1 );		
		
		
		logger.debug("saving one more payment on saved procurement request. Total payed for this request equals 100% of the request + costOverrun - 0.0001");
		IPayment paymentb = loadPayment( "single-payment.xml" );
		IProcurementRequest finalRequestb = requestService.getUnmanaged(approvalsRef.get(0).getId());
		paymentb.getPaymentItems().clear();
		Ada adab = new Ada();
		adab.setAdaCode(finalRequestb.getId());
		adab.setAdaType("ProcurementRequest");
		paymentb.setRelatedAdas(Arrays.asList(adab));		

		IContractItem contractItemb = getCopy(finalRequestb.getContractItems().get(0));
		contractItemb.setProcurementRequest(finalRequestb);
		contractItemb.getCost().setCostBeforeVat(finalRequestb.getTotalCostBeforeVat()*(Constants.COSTOVERRUN-0.0001));
		PaymentItem paymentItemb = new PaymentItem();
		paymentItemb.setContractItem(contractItemb);
		ContractParty partyb = new ContractParty();
		partyb.setAfm("023928277");
		partyb.setName("Δημήτρης Κολιός");
		partyb.setCountryIdRef("GR");
		paymentItemb.setContractParty(partyb);
		paymentb.getPaymentItems().add(paymentItemb);
		
		service.save( Arrays.asList( paymentb ), validation);		
		List<IPayment> savedPaymentsb = service.getAll();		
		assertTrue( savedPaymentsb.size() == 2 );
		
		
		logger.debug("saving one more payment on saved procurement request exeeding the costOverrun by 0.0001");
		IPayment paymentc = loadPayment( "single-payment.xml" );
		paymentc.getPaymentItems().clear();
		Ada adac = new Ada();
		adac.setAdaCode(finalRequestb.getId());
		adac.setAdaType("ProcurementRequest");
		paymentc.setRelatedAdas(Arrays.asList(adac));		

		IContractItem contractItemc = getCopy(finalRequestb.getContractItems().get(0));
		contractItemc.setProcurementRequest(finalRequestb);
		contractItemc.getCost().setCostBeforeVat(finalRequestb.getTotalCostBeforeVat()*(0.0002));
		PaymentItem paymentItemc = new PaymentItem();
		paymentItemc.setContractItem(contractItemc);
		ContractParty partyc = new ContractParty();
		partyc.setAfm("023928277");
		partyc.setName("Δημήτρης Κολιός");
		partyc.setCountryIdRef("GR");
		paymentItemc.setContractParty(partyc);
		paymentc.getPaymentItems().add(paymentItemc);
		
		service.save( Arrays.asList( paymentc ), validation);		
		List<IPayment> savedPaymentsc = service.getAll();		
		assertTrue( savedPaymentsc.size() == 3 );
		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		payment = loadPayment( "single-payment.xml" );
//		Ada adab = new Ada();
//		adab.setAdaCode(saved.get(0).getId());
//		adab.setAdaType("ProcurementRequest");
//		payment.setRelatedAdas(Arrays.asList(adab));
//		validation = new Validation();
//		IContractItem contractItemb = getCopy(contractItem);
//		contractItemb.setQuantity(4);
//		PaymentItem paymentItemB = new PaymentItem();
//		paymentItemB.setContractItem(contractItemb);
//		ContractParty partyb = new ContractParty();
//		partyb.setAfm("023928277");
//		partyb.setName("Δημήτρης Κολιός");
//		partyb.setCountry("gr");		
//		paymentItemB.setContractParty(partyb);
//		payment.getPaymentItems().clear();
//		payment.getPaymentItems().add(paymentItemB);
//		service.save( Arrays.asList( payment ), validation);		
//		savedPayments = service.getAll();	
//		if (!validation.isValid())
//			printValidationErrors(validation);
//		logger.debug("end of errors ------");
//		assertTrue( savedPayments.size() == 2 );
//		
//		payment = loadPayment( "single-payment.xml" );
//		Ada adac = new Ada();
//		adac.setAdaCode(saved.get(0).getId());
//		adac.setAdaType("ProcurementRequest");	
//		payment.setRelatedAdas(Arrays.asList(adac));
//		validation = new Validation();
//		IContractItem contractItemc = getCopy(contractItem);
//		contractItemc.setQuantity(1);
//		contractItemc.getCost().setCostBeforeVat(1.0);
//		PaymentItem paymentItemC = new PaymentItem();
//		paymentItemC.setContractItem(contractItemc);
//		ContractParty partyc = new ContractParty();
//		partyc.setAfm("023928277");
//		partyc.setName("Δημήτρης Κολιός");
//		partyc.setCountry("gr");
//		paymentItemC.setContractParty(partyc);
//		payment.getPaymentItems().clear();
//		payment.getPaymentItems().add(paymentItemC);
//		service.save( Arrays.asList( payment ), validation);
//		if (!validation.isValid())
//			printValidationErrors(validation);		
//		savedPayments = service.getAll();		
//		assertTrue( savedPayments.size() == 3 );		
//		
//		payment = loadPayment( "single-payment.xml" );
//		Ada adad = new Ada();
//		adad.setAdaCode(saved.get(0).getId());
//		adad.setAdaType("ProcurementRequest");	
//		payment.setRelatedAdas(Arrays.asList(adad));
//		validation = new Validation();
//		IContractItem contractItemd = getCopy(contractItem);
//		contractItemd.setQuantity(2);
//		contractItemd.getCost().setCostBeforeVat(0.10004);
//		PaymentItem paymentItemD = new PaymentItem();
//		paymentItemD.setContractItem(contractItemd);
//		ContractParty partyd = new ContractParty();
//		partyd.setAfm("023928277");
//		partyd.setName("Δημήτρης Κολιός");
//		partyd.setCountry("gr");
//		paymentItemD.setContractParty(partyd);
//		payment.getPaymentItems().clear();
//		payment.getPaymentItems().add(paymentItemD);
//		
//		service.save( Arrays.asList( payment ), validation);
//		if (!validation.isValid())
//			printValidationErrors(validation);
//		savedPayments = service.getAll();	
//		logger.debug("payments:"+savedPayments.size());
//		assertTrue( savedPayments.size() == 3 );			
	}

//@Ignore
//@Test
//public void testRequestsOfPayment() throws Exception {	
//	logger.debug( "Testing saving payments for a procurement request" );
//	List<IProcurementRequest> saved = requestService.getAll();
//	assertTrue(saved.size()>0);
//	
//	IProcurementRequest finalRequest = requestService.getUnmanaged("6ΚΩΨΕΝ-Γ-Α");
//	assertNotNull(finalRequest);
//	
//	IProcurementRequest finalRequestb = requestService.getUnmanaged("6ΚΩΨΕΝ-Δ-Α");
//	assertNotNull(finalRequestb);
//	
//	List<IPayment> savedPaymentsc = service.getAll();
//	assertTrue(savedPaymentsc.size() > 0);
//
//}
//@Ignore	
	@Test
	public void testSavePaymentWithContractAndRequest() throws Exception {	
		logger.debug( "Testing saving a single payment with contract and related procurementRequest" );
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );
		
		logger.debug( "Testing saving a single contract, no access control" );
		IContract contract = loadContract( "sample-contract-1.xml" );
		IValidation validation = new Validation();
		contractService.save( Arrays.asList( contract ), validation );
		if (!validation.isValid()){
			logger.debug("saving contract for payment errors");
			printValidationErrors(validation);
		}		
		List<IContract> savedContracts = contractService.getAll();
		assertTrue( savedContracts.size() == 1 );
		payment.setContract(contract);		
		
		logger.debug( "Testing saving a single procurement request, no access control" );
		IProcurementRequest request = loadRequest( "single-request.xml" );
		validation = new Validation();
		requestService.save( Arrays.asList( request ), validation );
		if (!validation.isValid()){
			printValidationErrors(validation);
		}
		List<IProcurementRequest> requestsSaved = requestService.getAll();
		assertTrue( requestsSaved.size() == 1 );
		Ada ada = new Ada();
		ada.setAdaCode(requestsSaved.get(0).getId());
		ada.setAdaType("ProcurementRequest");
		payment.setRelatedAdas(Arrays.asList(ada));
		validation = new Validation();
		service.save( Arrays.asList( payment ), new Validation());
		if (!validation.isValid()){
			printValidationErrors(validation);
		}
		List<IPayment> savedPayments = service.getAll();		
		assertTrue( savedPayments.size() == 0 );		
		
	}
//	@Ignore
	@Test
	public void testSavePaymentWithOtherAda() throws Exception {	
		logger.debug( "Testing saving a single stored payment with other related ada than procurementRequest" );
		dbHandler.clearDb();
		IPayment payment = loadPayment( "single-payment.xml" );	
		
		Ada ada = new Ada( "4ΙΙ64691ΩΓ-Α", "ContractCommission" );
		payment.setRelatedAdas(Arrays.asList(ada));
		
		service.save( Arrays.asList( payment ), new Validation());		
		List<IPayment> savedPayments = service.getAll();		
		assertTrue( savedPayments.size() == 0 );		
		
	}	
	
//	@Ignore
//	@Test
//	public void testDeleteSingleStoredOldPayment() throws Exception {
//		logger.debug( "Testing deleting a single stored payment" );
//		dbHandler.clearDb();
//		IPayment payment = loadPayment( "single-payment.xml" );
//		
////		ICmsMetadata metadata = new CmsMetadata();
////		Calendar submited = Calendar.getInstance();
////		submited.add(Calendar.DAY_OF_MONTH, -3);
////		metadata.setSubmissionTime( submited );
////		payment.setCmsMetadata(metadata);		
//		
//		service.save( Arrays.asList( payment ), new Validation() );		
//		List<IPayment> saved = service.getAll();		
//		assertTrue( saved.size() == 1 );
//		payment = saved.get(0);
//		Validation validation = new Validation();
//		service.delete( payment.getId(), "testReason" );		
//		saved = service.getAll();
//		assertEquals( saved.size(), 1 );
//		payment = saved.get( 0 );
//		assertTrue( validation.hasErrorCode("endOfChangeTimeSlot") );
//		assertFalse( payment.isDeleted() );
//	}	
//@Ignore	
	@Test
	public void testSaveTwoPaymentsTwoCalls() throws Exception {
		logger.debug( "Testing saving two different contracts in two calls" );
		dbHandler.clearDb();
		List<IPayment> saved = service.getAll();
		assertTrue( saved.isEmpty() );
		IPayment payment1 = loadPayment( "single-payment.xml" );
		IPayment payment2 = loadPayment( "single-payment2.xml" );
		logger.debug( "...Saving 1st payment" );
		service.save( Arrays.asList( payment1 ), new Validation() );
		logger.debug( "...Retrieving list" );
		saved = service.getAll();
		assertTrue( saved.size() == 1 );
		logger.debug( "...Saving 2nd payment" );
		service.save( Arrays.asList( payment2 ), new Validation() );
		saved = service.getAll();
		assertTrue( saved.size() == 2 );	
	}
//@Ignore	
	@Test
	public void testSaveTwoPaymentsSingleCall() throws Exception {
		logger.debug( "Testing saving two different payments in a single call" );		
		dbHandler.clearDb();		
		List<IPayment> saved = service.getAll();
		assertTrue( saved.isEmpty() );				
		IPayment payment1 = loadPayment( "single-payment.xml" );
		IPayment payment2 = loadPayment( "single-payment2.xml" );
		service.save( Arrays.asList( payment1, payment2 ), new Validation() );		
		saved = service.getAll();
		assertTrue( saved.size() == 2 );
	}
//@Ignore	
	@Test
	public void testPaymentsDifferentOrganization() throws Exception {
		logger.debug( "Testing storing decisions of two different organizations" );
		dbHandler.clearDb();		
		IPayment payment1 = loadPayment( "single-payment.xml" );
		IPayment payment2 = loadPayment( "single-payment.xml" );
		payment2.setOrganizationDiavgeiaId( 15 );
		payment2.setUnitDiavgeiaId( 117 );
		payment2.setSignersDiavgeiaIds( Arrays.asList( 24 ) );		
		Validation validation = new Validation();
		try {
			List<IDecisionStorageReference> stored = service.save( Arrays.asList( payment1, payment2 ), validation );
			fail();
		}
		catch (ForbiddenOperationException e) {
			
		}
	}
//@Ignore	
	@Test
	public void testSearchPaymentsByOrganization() throws Exception {
		logger.debug( "Testing searching payment by organization" );
		IClientFactory defaultFactory = accessControl.getClientFactory();
		accessControl.setClientFactory( new AdminTestClientFactory() );
		dbHandler.clearDb();		
		IPayment payment1 = loadPayment( "single-payment.xml" );
		IPayment payment2 = loadPayment( "single-payment.xml" );
		payment2.setOrganizationDiavgeiaId( 15 );
		payment2.setUnitDiavgeiaId( 117 );
		payment2.setSignersDiavgeiaIds( Arrays.asList( 24 ) );		
		service.save( Arrays.asList( payment1, payment2 ), new Validation() );						
		MockHttpServletRequest request = new MockHttpServletRequest();		
		request.addParameter( "org", "366" );
		List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( request );
		List<IPayment> found = service.getAll( new PaginationInfo(), params );
		assertEquals( found.size(), 1 );
		assertEquals( found.get(0).getOrganizationDiavgeiaId().intValue(), 366 );
		accessControl.setClientFactory(defaultFactory);
	}
	
	private void printValidationErrors( IValidation	validation ) {
		logger.debug( "\nValidation Errors:\n" );
		
		for ( IValidationError validationError: validation.getErrors() ) { 
			logger.debug( validationError.getCode() + " - " + validationError.getLocation()  + " - " + validationError.getMessage() );
		}
	}		
	
	@Test
	public void testSearchContractByCpv() throws Exception {
		logger.debug( "Testing saving a single payment, no access control" );
		dbHandler.clearDb();
		Validation validation = new Validation();
		logger.debug("storing 3 payments\n");
		int firstPaymentContractItems=0;
		String firstPaymentAda="";
		for (int i=0;i<3;i++)
		{		
			IPayment payment = loadPayment( "single-payment.xml" );
			if (i<2){
				payment.getPaymentItems().get(0).getContractItem().addCpv("AA07-9");
			}

			if (i == 0){
				firstPaymentContractItems = payment.getPaymentItems().size();
				firstPaymentAda = payment.getId();
			}			
			
			validation = new Validation();
			service.save( Arrays.asList( payment ), validation );
			if (!validation.isValid()){
				logger.debug("saving payments errors:\n");
				printValidationErrors(validation);
			}
		}
		List<IPayment> saved = service.getAll();
		assertTrue( saved.size() == 3 );
		
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();		
		mockRequest.addParameter( "cpv", "AA07-9" );
		List<ISearchParameter> params = SearchParameterFactory.fromServletRequest( mockRequest );
		List<IPayment> found = service.getAll( new PaginationInfo(), params );
		assertEquals( found.size(), 2 );
		for (IPayment payment:found){
			if (payment.getId().equals(firstPaymentAda)){
				assertTrue(payment.getContractItems().size() == firstPaymentContractItems);
			}
		}
	
	}	
}
