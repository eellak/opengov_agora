<?php
/**
 * This class supports the Payments procedure.
 * It is the main model Class. 
 * In this class we define or support 
 * a) the attributes of a Payment
 * b) the validation rules for each attributes of a Payment.
 * c) the labels for each attribute of a Payment.
 * d) the findByPk function that returns the of the requested by uniqueDocumentCode of a Payment.
 * e) the SaveToApi function that saves or updates a payment.
 * f) the GetNumberOfPaymets function that connects to the API and gets the number of Payments.
 * g) the isPermitted function that checks if the user has the permission to make the action. 
 * h) the displayContractInfo function that is used for paying a contract. It just prompts an informational block
 * that informs the user that the contract has expired. It just compares the until date of a contract to now and 
 * suggest to the user to extend the contract if necessary. 
 * @author themiszamani
 * @version 1.0
 * @package agora 
 */

class Payments extends CFormModel{
	/**
     * The unique id for each Payment
     * @var string
     */
	var $uniqueDocumentCode;
	/**
     * The unique id for each Payment
     * @var string
     */
	var $documentUrl;
	/**
     * The Payment title
     * @var string
     */
	var $title;
	/**
     * The date the Payment was signed
     * @var date
     */
	var $dateSigned;
	/**
     * The Responsibility Assumption Code. Each payment is authorized by 
     * GLK and get a unique code called Responsibility Assumption Code. 
     * @var string
     */
	var $responsibilityAssumptionCode;
	/**
     * The protocol number Code or a specific number for Contracts. 
     * @var string
     */
	var $protocolNumberCode;
	/**
     * The email of the person issued the Payment 
     * @var string
     */
	var $issuerEmail;
	/**
     * The Organization Unit id the payments belongs to. 
     * @var integer
     */
	var $OrganizationIdRefUnits;
	/**
     * The id of the Signer of the payment
     * @var integer
     */
	var $signers;
	/**
	 * An option than define if the user wants to add
	 * the payment to Diavgeia.
	 * @var integer
	 */
	var $diavgeiaPublished;
	/**
	 * The payment document.
	 * @var string
	 */
	var $document;
	/**
	 * If the user made a mistake to a new payment, we let him delete it. 
	 * In order to delete it the user must add a description.
	 * @var string
	 */
	var $Deldescription;
	/**
	 * Not all users all allowed to make an action. Only admins are allowed
	 * to update or delete a payment for the next 24 hours. In order to 
	 * check if is permitted we define this variable.
	 * @var integer
	 */
	var $actionPermitted;
	/**
	 * This defines if the payment is a new one.
	 * @var string
	 */
	var $isNewRecord;
	/**
	 * Related adas of a payment. Mainly strings of 
	 * procurementRequests. 
	 * @var array
	 */
	var $relatedADAs;
	/**
	 * If the payment is based on a procurement request
	 * in the procurement variable we keep the ADAs of the 
	 * procurements seperated by ;.
	 * @var string
	 */
	var $procurement;
	/**
	 * If the payment is based on a procurement request
	 * in the procurements variable we keep the ADAs of the 
	 * procurements seperated by ;.
	 * @var string
	 */
	var $procurements;
	/**
	 * If the payment is based on a contract in the contract 
	 * variable we keep the ADA of the selected contract.
	 * @var string
	 */
	var $contract;	
		
	/**
     * The date the Payment was submitted to the api
     * @var date
     */
	var $submissionTime;
	/**
     * The date the Payment was last modified
     * @var date
     */
	var $lastModifiedTime;
	/**
	 * The id of the Organization the payment belongs to
	 * @var integer
	 */
	var $OrganizationIdRef;
	/**
	 * The name of the Organization the payment belongs to
	 * @var string
	 */
	var $OrganisationName;
	var $cpvsid;
	var $foreis;
	var $ypoforeis;
	var $totalCostBeforeVAT;
	var $Related_ADA;
	var $documentURlFromDiavgeia;
		/**
	 * The model is cancelled
	 * @var boolean
	 */
	var $cancelled;
	/**
	 * The cancellation reason
	 * @var string
	 */
	var $cancelledReason;
	/**
	 * The cancellation type (from taxonomy)
	 * @var integer
	 */
	var $cancellationType;
	/**
	 * The cancellation user (from taxonomy)
	 * @var integer
	 */
	var $cancelledFromUserIdRef;
	/**
	 * The cancellation time
	 * @var date
	 */
	var $cancelledTime;
	/**
	 * The cancellation ADA from Diauvgeia
	 * @var integer
	 */
	var $ADACancel;
	
	/**
	 * The main function that validates the attributes - properties of a payment. 
 	 * The purpose is to have all the information gathered in one place instead of scattered.
 	 * See the following link on how to add a new rule to payment. 
	 * The validation rules are as follows:
	 * - title, dateSigned, protocolNumberCode,OrganizationIdRefUnits,signers are required
	 * - a contract Ada is required on create and createpaymentbycontract action 
	 * - one or multiples procurement  Ada is required on createpaymentbyproc action 
	 * - Deldescription is required on delete
	 * - dateSigned must have a dd/MM/yyyy date format
	 * - dateSigned and SComparePaymentDates: a)for a contract the dateSigned of a payment must be greater that the dateSigned of the Contract
	 * - dateSigned and SComparePaymentDates: a)for multiple procurements the dateSigned of a payment must be greater than the dateSigned of each procurement
	 * - dateSignedS and CompareDates should be older than today 
	 * - the document should be safe and the files should be either pdf or zip 
	 * - the issuerEmail must have an email format
	 * @link http://www.yiiframework.com/wiki/56/reference-model-rules-validation/#hh4
	 * @return array with the errors
	 */
	public function rules()
	{
		
		return array(
		
			//the required variables 
			array('title, dateSigned, protocolNumberCode,OrganizationIdRefUnits,signers,issuerEmail', 'required'),
			array('contract','required','on'=>'create,createpaymentbycontract'),
			array('title, dateSigned, protocolNumberCode,OrganizationIdRefUnits,signers,issuerEmail,contract,procurements,document', 'safe'),
			
			array('procurements','required','on'=>'createpaymentbyproc'),
			array('Deldescription','required','on'=>'deletepayment,cancelpayment'),
			array('dateSigned','date','format'=>'dd/MM/yyyy'),
			//dateSigned must be greater than the datesigned the contractr or the procurement was signed
			array('dateSigned','SComparePaymentDates','compareValue'=>date('d/m/Y'),'operator'=>'>', 'allowEmpty'=>false),
			//Rule dateSigned must not be > from today
			array('dateSigned','SCompareDates','compareValue'=>date('d/m/Y'),'operator'=>'<=', 'allowEmpty'=>false ,'message'=>Yii::t('contract','DateSignedNotToday')),
			//the document types
			array('document,responsibilityAssumptionCode','safe'),
            array('document', 'file', 'types'=>'pdf,zip','allowEmpty'=>true,'on'=> 'createpayment,
            	createemptypayment,createpaymentbyproc,createpaymentbycontract',
            ),
            array('documentUrl,cpvsid,title,foreis,ypoforeis,Related_ADA,documentURlFromDiavgeia,deldescription,ADACancel,cancellationType,RelatedADA','safe'),
            //issuerEmail must have an email format
            array('issuerEmail','email','allowName'=>false),
            array('document','SCompareRequiredDocument','compareAttribute'=>'documentURlFromDiavgeia'),
            
            
		);
	}
	
	/**
	 * Declares the model attribute labels. 
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			
			'title' => Yii::t('contract','title'),
			'dateSigned'=>Yii::t('contract','dateSigned'),
			'protocolNumberCode'=>Yii::t('payments','protocolNumberCode'),
			'diavgeiaPublished'=>Yii::t('contract','diavgeiaPublished'),
			'OrganizationIdRefUnits'=>Yii::t('contract','OrganizationIdRefUnits'),
			'signers'=>Yii::t('contract','signers'),
			'responsibilityAssumptionCode' => Yii::t('procurement','responsibilityAssumptionCode'),
			'document' => Yii::t('procurement','document'),
			'contract'=>Yii::t('payments','Contractid'),
			'Deldescription'=>Yii::t('form','Deldescription'),
			'issuerEmail'=>Yii::t('form','issuerEmail'),
			'cpvsid' => Yii::t('item','CPVS'),
			'documentUrl' => Yii::t('contract','uniqueDocumentCode'),
			'foreis' => Yii::t('form','foreis'),
			'ypoforeis' => Yii::t('form','ypoforeis'),
			'Related_ADA'=>Yii::t('payments','Related_ADA'),
			'cancellationType'=>Yii::t('form','cancellationType'),
			'ADACancel'=>Yii::t('form','ADACancel'),	
		);
	}
	
	/**
	 * Finds a single payment Finds a single active record with the specified uniqueDocumentCode.
	 * It makes the request to the middleware and sets the 
	 * data of the specified procurement. 
	 * If the api responds with 404 then 404 is returned.
	 *  @param string $uniqueDocumentCode the unique id of the payment
	 *  @return the record found. Null if none is found.
	 */
	public function findByPk($uniqueDocumentCode){

		$config['apirequest'] ='paymentsitem';
		$config['documentid'] =$uniqueDocumentCode;
		
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		
		//If no result return 404
		if($output==404) return $output;
		
		$Proc = new PaymentsApiData();
		$procData = $Proc->getItemData($output);
		
		//Set the data
		if(isset($procData[0]['title'])){
			$this->title =(string)$procData[0]['title'];
			$this->submissionTime = $procData[0]['submissionTime'];
			$this->lastModifiedTime = $procData[0]['lastModifiedTime'];
			$this->uniqueDocumentCode =$procData[0]['uniqueDocumentCode'];
			$this->protocolNumberCode =$procData[0]['protocolNumberCode'];
			$this->diavgeiaPublished= (bool) $procData[0]['diavgeiaPublished'];
			$this->dateSigned = $procData[0]['dateSigned'];
			$this->responsibilityAssumptionCode = $procData[0]['responsibilityAssumptionCode'];
			$this->OrganizationIdRefUnits = $procData[0]['OrganizationIdRefUnits'];
			$this->signers = $procData[0]['signer'];
			$this->documentUrl = $procData[0]['documentUrl'];
			$this->issuerEmail = $procData[0]['issuerEmail'];
			$this->OrganizationIdRef = $procData[0]['OrganizationIdRef'];
			$this->OrganisationName = Units::item("Organization", $this->OrganizationIdRef,$this->OrganizationIdRef);
			$this->OrganizationIdRefUnits = $procData[0]['OrganizationIdRefUnits'];
			$this->relatedADAs=$procData[0]['relatedADAs'];
			$this->procurements=$this->procurement = (isset($procData[0]['procurement']))?$procData[0]['procurement']:'';
			$this->contract = (isset($procData[0]['contract']))?$procData[0]['contract']:'';
			$this->actionPermitted = (isset($procData[0]['actionPermitted']))?$this->isPermitted($procData[0]['actionPermitted']):'';
			$this->cancelled = $procData[0]['cancelled'];
			$this->cancelledTime = $procData[0]['cancelledTime'];
			$this->cancelledReason = $procData[0]['cancelledReason'];
			$this->cancellationType = $procData[0]['cancellationType'];
			$this->cancelledFromUserIdRef = $procData[0]['cancelledFromUserIdRef'];
		}
		
		return $this;	
		
	}
	/**
	 * Fetches the list of procurements in an array
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the contartapi data and are stored 
	 * in a table (component Contractapidata.php)
	 * @param string search the search term 
	 * @param integet the organization id
	 * @return an array with the short contract list data 
	 */
	public function getPaymentList($search='',$org='',$limit=''){
		
		//Connect to ApiConnector
		//sets the config table for the http request. 
		
		$config['apirequest'] ='payments';
		if($search!='')$config['search'] =$search;
		if($org!='') $config['org'] =$org;
		if($limit!='') $config['limit'] =$limit;
		
		
		$model = new Payments();
		//print_r($config);
		$api = new Apiconnector($config);
		$output = $api->short();
		$Results=array();
		if($output==404 || $output==403) return $Results;		
		
		$Results = new PaymentsApiData();
		$Results = $Results->getShortList($output);
		//Fetch Data to Array
		//$Results = $Results->getShortList($output);
		if(count($Results)>1)	
		for($i=0;$i<count($Results) && $Results!=404 && $Results!=403;$i++){
			$Results[$i]['actionPermitted'] =$model->isPermitted($Results[$i]['actionPermitted']);
		}
		return $Results;
		
	}
	
	public function getRfMultipleListTable($documentUrl='',$title='',$cpvsid='',$org=''){
		
		$model=new Payments();
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$Results = array();

		$config['apirequest'] ='payments';
		$config['title'] = $title;
		$config['ada']= $documentUrl;
		$config['org'] =$org;
		$finalData='';
		if (isset($cpvsid) && $cpvsid!=''){
			$newItem = explode("[", $cpvsid);
				if(count($newItem)>1){
					$finalItem = explode("]",$newItem[1]);
					$finalData = $finalItem[0];
				}
		}else $finalData='';
		$config['cpv']=$finalData;
		
		$api = new Apiconnector($config);
		$output = $api->search();
		if($output==500) return -1;
		$modelR = new PaymentsApiData();
		$Results = $modelR->getShortList($output);
		
		return $Results;
		
		
	}
/**
	 * Finds a single payment Finds a single active record with the specified uniqueDocumentCode.
	 * It makes the request to the middleware and sets the 
	 * data of the specified procurement. 
	 * If the api responds with 404 then 404 is returned.
	 *  @param string $uniqueDocumentCode the unique id of the payment
	 *  @return the record found. Null if none is found.
	 */
	public function findByPkByContract($uniqueDocumentCode){

		$config['apirequest'] ='paymentsitem';
		$config['contractid'] =$uniqueDocumentCode;
		
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		
		//If no result return 404
		if($output==404) return $output;
		
		$Proc = new PaymentsApiData();
		$procData = $Proc->getShortList($output);
		//Set the data
		if(count($procData)>1){
			for($i=0;$i<count($procData);$i++)
				$data[$i]['documentUrl'] =$procData[$i]['documentUrl'];
			return $data;	
		}else return -1;
		
	}
	/**
	 * This function creates or updates a payment.
	 * It makes a request to the PaymentsWriteApi and according
	 * to the status it makes the appropriate request 
	 * to the middleware.
	 * @param string $status either create or update
	 * @return the result of the action. 200 if success and 400, 403, 404 and 500 when an error is occured
	 */
	public function saveToApi($status='create'){
		$data = new PaymentsWriteApi();
		$data = $data->PaymentsWrite($status);		
		return $data;
	}
	
	/**
	 * This function gets the number of payments.
	 * It makes a request to the Apiconnector and 
	 * it makes the appropriate request to the middleware.
	 * @return integer the number of payments
	 * */
	public function GetNumberOfPayments(){

		$config['apirequest'] ='paymentsshort';
		//send a curl request for the document 
		if(isset($_GET['org'])){ 
			$Organizations = new Organizations();
			$org = $Organizations->getOrgId($_GET['org']);
			if($org!='')
			$config['org']=$org;
		}
		
		$api = new Apiconnector($config);
		$output = $api->number('payment');
		$Proc = new PaymentsApiData();
		return $Proc->getNumber($output);
		
		
	}
	/**
	 * Check if the user has the permission 
	 * to permit an action. The values we check are
	 * a) if isPermitted is true less than 24 hours
	 * b) if the user is admin 
	 * @param integer $isPermitted if is permitted to make an action <24h
	 */
	public function isPermitted($isPermitted){
		
		if(isset(Yii::app()->user->isAdmin))
			if($isPermitted && Yii::app()->user->isAdmin=='true') return 1;
			else return 0;
		else return 0;	
	}	
	
	
	
	/**
	 * This function is an informational function.
	 * It checks the contract until date and informs 
	 * the user that the selected contract has ended.
	 * He also encourages the user to extend the 
	 * selected contract. The information is displayed
	 * to user form.
	 */
	public function displayContractInfo(){
		
		if(!isset($_POST['Payments']['contract']) || $_POST['Payments']['contract']=='') return;
		
        	$documentUrl = $_POST['Payments']['contract'];
       		$contract = new Contract();
       		$contract = $contract->findByPk($documentUrl);
       		 if( $contract==404) return;
            if(isset($contract->title) && $contract->title!=''){
       		//the contract doesnt have an until date
       		if($contract->untilexists==1) return;
       		if(isset($contract->until) && $contract->until=='') return;
       		$compareDate = DateTime::createFromFormat('d/m/Y', $contract->until);              
			$compareValue = $compareDate->format('Y-m-d');
			$compareValue=strtotime($compareValue);
                
            $stringValue=date('d/m/Y');
            //echo $stringValue."<br/>";     
            $date = DateTime::createFromFormat('d/m/Y', $stringValue);              
            $value = $date->format('Y-m-d');
            $value = strtotime($value);
       		//echo $value."<br/>";
        	if($value> $compareValue) {
        		echo"<div class=\"item\">";
					echo"<div class=\"contract\">";
						echo"<img src=\"images/info.png\" align=\"left\" style=\"margin-right:10px;\">";
						echo"<div class=\"row\">";
							echo"<div class='row-grey'>";
							echo "<span class='statustitle'>".Yii::t('payments','ContractEnded')."</span>";
							echo CHtml::link(Yii::t('contract','Extend Contract'), array("contract/extendcontract",'uniqueDocumentCode'=>$documentUrl));
							echo"</div>";
						echo"</div>";/*--end div row--*/
					echo"</div>";/*--end div contract--*/
				echo"</div>";/*--end div item--*/
        		}
        		else return 0;
            }
         
	}
	
	public function getPaymentDate($documentUrl){
		if(Yii::app()->user->isGuest) return 0; 
		if(Yii::app()->user->role=='admin') return 1;
		$Payment = new Payments();
		$Payment->findByPk($documentUrl);
		return $Payment->dateSigned;
	}
	
}