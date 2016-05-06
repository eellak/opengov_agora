<?php

/**
 * This class supports the RFP procedure.
 * It is the main model Class. 
 * In this class we define or support 
 * a) define the attributes of the RFP
 * b) define the validation rules for each attributes of the RFP
 * c) define the labels for each attribute of the RFP
 * d) the load function that returns the attributes values.
 * e) the findByPk function that returns the of the requested by uniquedocumentUrl of the RFP
 * f) the Save function that saves or updates the RFP.
 * g) The onBeforeValidation for specific scenarios
 * h) CheckDateWithModel function to check new with old date values
 * i) the isPermitted function that checks if the user has the permission to make the action. 
 * j) the GetNumberOfRFP function that connects to the API and gets the number of RFP.
 * @author themiszamani
 * @version 1.0
 * @package agora 
 */

class Rfp extends CFormModel{

	/**
     * The unique Document Code of the RFP
     * @var string
     */
	var $uniqueDocumentCode;
	/**
     * The unique id for each RFP
     * @var string
     */
	var $documentUrl;
	/**
     * The RFP title
     * @var string
     */
	var $title;
	/**
     * The date the RFP was signed
     * @var date
     */
	var $dateSigned;
	/**
     * The RFP  start Date
     * @var date
     */
	var $since;
	/**
     * The RFP  end Date
     * @var date
     */	
	var $until;
	
	/**
     * The RFP doesnt always have an end date
     * This is a way to define it with a checkbox. 
     * @var integer
     */	
	var $untilexists;
	/**
     * The protocol number Code or a specific number for RFPs. 
     * @var string
     */
	var $protocolNumberCode;
	/**
     * The email of the person issued the RFP 
     * @var string
     */
	var $issuerEmail;

	/**
     * The Award procedure of the call followed. Based
     * on the Lookup function
     * @var integer
     */	
	var $awardProcedure;

	/**
     * The Organization Unit id the RFP belongs to. 
     * @var integer
     */
	var $OrganizationIdRefUnits;
	/**
     * The Organization Name id the RFP belongs to. 
     * @var string;
     */
	var $OrganisationName;
	/**
     * The id of the Signer of the RFP
     * @var integer
     */
	var $signers;
	
	/**
	 * The RFP document.
	 * @var string
	 */
	var $document;
	/**
	 * The total cost
	 * @var double
	 */
	var $totalCostBeforeVat;
	/**
	 * If the user made a mistake to a new contract, we let him delete it. 
	 * In order to delete it the user must add a description.
	 * @var string
	 */
	var $Deldescription;
	/**
	 * Not all users all allowed to make an action. Only admins are allowed
	 * to update or delete a contrct for the next 24 hours. In order to 
	 * check if is permitted we define this variable.
	 * @var integer
	 */
	var $actionPermitted;
	/**
	 * This defines if the rfp is a new one.
	 * @var string
	 */
	var $isNewRecord;
	/**
	 * Related adas of a contract. Mainly strings of 
	 * procurementRequests. Check what to use from wiki
	 * http://forge.opengov.gr/redmine/projects/agoramiddleware/wiki/%CE%A4%CF%8D%CF%80%CE%BF%CE%B9_%CE%91%CE%94%CE%91
	 * @var array
	 */
	var $relatedADAs;
	/**
     * The date the rfp was submitted to the api
     * @var date
     */
	var $submissionTime;
	/**
     * The date the rfp was last modified
     * @var date
     */
	var $lastModifiedTime;
	/**
	 * The id of the Organization the procurement belongs to
	 * @var integer
	 */
	var $OrganizationIdRef;
	/**
	 * The Name of the Organisation the procurmente belongs to
	 * @var integer
	 */
	var $OrganizationName;
	/**
	 * If we are refering to a deleted rfp
	 * @var boolean
	 */
	var $deleted;
	/**
	 * If the rfp is based on a procurement request
	 * in the procurement variable we keep the ADAs of the 
	 * procurements seperated by ;.
	 * @var string
	 */
	var $procurements;
	
	/**
	 * If the user puts the related ADA some data for openada 
	 * are fetched and are set to rfp model.
	 * @var string
	 */
	var $RelatedADA;
	var $cpvsid;
	var $foreis;
	var $ypoforeis;
	
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
	 * The main function that validates the attributes - properties of a contract. 
 	 * The purpose is to have all the information gathered in one place instead of scattered.
 	 * Apart from  the default rules of the yiiframework,
	 * it also uses some custom created for the contract Attributes.
 	 * The SCompareDates that compares dates (<.<=,>.>=).
 	 * See the following link on how to add a new rule to contract. 
	 * The validation rules are as follows:
	 * - title, dateSigned, since,protocolNumberCode,OrganizationIdRefUnits,signers are required
 	 * - Deldescription is required on delete
	 * - dateSigned must have a dd/MM/yyyy date format
 	 * - dateSigned and SComparePaymentDates: a)for multiple procurements the dateSigned of a payment must be greater than the dateSigned of each procurement
	 * - dateSigned and CompareDates should be older than today 
	 * - since must have a dd/MM/yyyy date format
	 * - until must have a dd/MM/yyyy date format
	 * - document is required on createcontract,extend,createcontractbyproc and must be either pdf or zip 
 	 * - awardProcedure should be safe. After you post the form the selected item is shown. Otherwise the first value is shown. 
	 * - the document should be safe 
	 * - the issuerEmail must have an email format
 	 * - one or multiples procurement  Ada is required on createcontractbyproc action 

	 * @link http://www.yiiframework.com/wiki/56/reference-model-rules-validation/#hh4
	 * @return array with the errors
	 */
	public function rules()
	{

		return array(

		//Secname,Secafm,Seccountry
		array('title, dateSigned, since,protocolNumberCode,OrganizationIdRefUnits,signers',
			 'required','on'=>'createrfp,updaterfp'),
		array('Deldescription','required','on'=>'deleterfp,delete,cancelrfp'),
		array('dateSigned','date','format'=>'dd/MM/yyyy'),
		//Rule dateSigned must not be > from today
		array('dateSigned','SCompareDates','compareValue'=>date('d/m/Y'),'operator'=>'<=', 
			 'allowEmpty'=>false ,'message'=>Yii::t('contract','DateSignedNotToday')),
		array('since','date','format'=>'dd/MM/yyyy'),
		array('until','date','format'=>'dd/MM/yyyy'),
		array('since','SCompareDates','compareAttribute'=>'dateSigned','operator'=>'>=',
			 'allowEmpty'=>false,'on'=>'createrfp,updaterfp'),
		array('until','SCompareDates','compareAttribute'=>'since','operator'=>'>','allowEmpty'=>false ,
			  'on'=>'createrfp,updaterfp'),
		array('document', 'file', 'types'=>'pdf,zip','allowEmpty'=>false,'on'=>'createrfp'),
		array('issuerEmail','email','allowName'=>false),
		array('title,issuerEmail, dateSigned, since,protocolNumberCode,OrganizationIdRefUnits,signers,
			 awardProcedure,procurements,cpvsid','safe'),
		array('document','safe'),
		array('documentUrl,cpvsid,title,foreis,ypoforeis,deldescription,ADACancel,cancellationType,RelatedADA','safe'),
		array('cpvsid','SCompareCPVs'),
		


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
			'since'=>Yii::t('rfp','since'),
			'until'=>Yii::t('rfp','until'),
			'untilexists'=>Yii::t('contract','untilexists'),
			'protocolNumberCode'=>Yii::t('rfp','protocolNumberCode'),
			'OrganizationIdRefUnits'=>Yii::t('contract','OrganizationIdRefUnits'),
			'signers'=>Yii::t('contract','signers'),
			'awardProcedure'=>Yii::t('contract','awardProcedure'),
			'document'=>Yii::t('contract','document'),
			'uniqueDocumentCode'=>Yii::t('contract','uniqueDocumentCode'),
			'issuerEmail'=>Yii::t('form','issuerEmail'),
			'procurements'=>Yii::t('contract','Procurements'),
			'Deldescription'=>Yii::t('form','Deldescription'),
			'RelatedADA'=>Yii::t('rfp','RelatedADA'),
			'cpvsid' => Yii::t('item','CPVS'),
			'documentUrl' => Yii::t('contract','uniqueDocumentCode'),
			'foreis' => Yii::t('form','foreis'),
			'ypoforeis' => Yii::t('form','ypoforeis'),
			'cancellationType'=>Yii::t('form','cancellationType'),
			'ADACancel'=>Yii::t('form','ADACancel'),		
		);
	}

	/**
	 * Loads the data of the element with the selected id
	 * @param the document id $id
 	 * @return the record found. Null if none is found.
	 */
	public function loadModel($id){
		$data = self::getRFPItem($id);
		return $this;
	}

	/**
	 * Fetches the data of a contract in an array
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the contartapi data and the data are stored
	 * in a table (component Contractapidata.php)
	 * @return an array with the contract data
	 */
	public function getRFPItem($id){

		//Connect to ApiConnector
		//sets the config table for the http request.
		$config['apirequest'] ='RFPitem';
		$config['documentid'] = $id;

		$api = new Apiconnector($config);
		$output = $api->makeRequest();

		$Results = new RFPapidata();
		$Results = $Results->getItemData($output);
		return $Results;
	}

	/**
	 * It saves the new contract, updates
	 * the contract data, or extends the contract.
	 * It makes a request to the ContractWriteApi and according
	 * to the status it makes the appropriate request
	 * to the middleware.
	 * @param string $status either create or update or extend
	 * @return the result of the action. 200 if success and 400, 403, 404 and 500 when an error is occured
	 */
	public function saveToApi($status='create'){
		$data = new RfpWriteApi();
		$data = $data->RfpWrite($status);
		return $data;
	}

	
	/**
	 * Finds a single contract Finds a single active record with the specified uniqueDocumentCode.
	 * It makes the request to the middleware and sets the 
	 * data of the specified procurement. 
	 * If the api responds with 404 then 404 is returned.
	 *  @param string $uniqueDocumentCode the unique id of the payment
	 *  @return the record found. Null if none is found.
	 */
	public function findByPk($uniqueDocumentCode){

		$config['apirequest'] ='rfpitem';
		$config['documentid'] =$uniqueDocumentCode;

		//send a curl request for the document
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		//If no result return 404
		if($output==404) return $output;

		$RFP = new RFPapidata();
		
		if($output!=-1){
			$RFPData = $RFP->getItemData($output);
			$this->documentUrl = $uniqueDocumentCode;
			$this->submissionTime = $RFPData[0]['submissionTime'];
			$this->lastModifiedTime = $RFPData[0]['lastModifiedTime'];
			$this->deleted = $RFPData[0]['deleted'];
			$this->protocolNumberCode = $RFPData[0]['protocolNumberCode'];
			$this->title =(string)$RFPData[0]['title'];
			$this->dateSigned = $RFPData[0]['dateSigned'];
			$this->issuerEmail = $RFPData[0]['issuerEmail'];
			$this->OrganizationIdRef = $RFPData[0]['OrganizationIdRef'];
			$this->OrganizationIdRefUnits = $RFPData[0]['OrganizationIdRefUnits'];
			$this->OrganisationName =Units::item("Organization", $this->OrganizationIdRef,$this->OrganizationIdRef);
			
			//this is an array

			$this->signers = $RFPData[0]['signers'];
				
			$this->awardProcedure = $RFPData[0]['awardProcedure'];
			$this->since = $RFPData[0]['since'];
			$this->until = $RFPData[0]['until'];
			$this->documentUrl = $RFPData[0]['documentUrl'];
			$this->uniqueDocumentCode = $RFPData[0]['documentUrl'];
			$this->relatedADAs=$RFPData[0]['relatedADAs'];
			$this->procurements = (isset($RFPData[0]['procurements']))?$RFPData[0]['procurements']:'';
			$this->actionPermitted = $this->isPermitted($RFPData[0]['actionPermitted']);
			$this->cancelled = $RFPData[0]['cancelled'];
			$this->cancelledTime = $RFPData[0]['cancelledTime'];
			$this->cancelledReason = $RFPData[0]['cancelledReason'];
			$this->cancellationType = $RFPData[0]['cancellationType'];
			$this->cancelledFromUserIdRef = $RFPData[0]['cancelledFromUserIdRef'];
		}
		return $this;

	}

/**
	 * Fetches the list of contracts in an array
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the contartapi data and are stored 
	 * in a table (component Contractapidata.php)
	 * @return an array with the short contract list data 
	 */
	public function getRfpListTable($search="",$org='',$limit=''){
		
		$model=new Rfp();
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$Results = array();
		$config['apirequest'] ='rfp';
		
		if($search!='') $config['search'] =$search;
		if($org!='') $config['org']=$org;
		if($limit!='') $config['limit']=$limit;
				
		$api = new Apiconnector($config);
		$output = $api->short();
		
		if($output==404 || $output==403 || $output==500 || $output=='') 
		 return $Results;		
	
		$Results = new RFPapidata();
		$Results = $Results->getShortListTable($output);
		
		if(count($Results)>1)		
		for($i=0;$i<count($Results) && $Results!=404 && $Results!=403;$i++){
			$Results[$i]['actionPermitted']=$model->isPermitted($Results[$i]['actionPermitted']);
			
			
		}
			//Fetch Data to Array
		
		return $Results;
		
		
	}
	public function getRfMultipleListTable($documentUrl='',$title='',$cpvsid='',$org=''){
		
		$model=new Rfp();
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$Results = array();

		$config['apirequest'] ='rfp';
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
		$modelR = new RFPapidata();
		$Results = $modelR->getShortListTable($output);
		
		return $Results;
		
		
	}
	/**
	 * This function gets the number of Rfp.
	 * It makes a request to the Apiconnector and 
	 * it makes the appropriate request to the middleware.
	 * @return integer the number of Contracts
	 * */
	public function GetNumberOfRfp(){

		$config['apirequest'] ='rfpshort';
		if(isset($_GET['org'])){ 
			$Organizations = new Organizations();
			$org = $Organizations->getOrgId($_GET['org']);
			if($org!='')
			$config['org']=$org;
		}

		//send a curl request for the document
		$api = new Apiconnector($config);
		$output = $api->number('rfp');
		$Proc = new RFPapidata();
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
		if(Yii::app()->user->isGuest) return 0; 
		if(Yii::app()->user->role=='admin') return 1;
		if(isset(Yii::app()->user->isAdmin))
			if($isPermitted && Yii::app()->user->isAdmin=='true') return 1;
			else return 0;
		else return 0;
	}
	
	public function getADAValues($RelatedADA){
		$ADAData = new Decision();
		$ADAResults = $ADAData->findByPK($RelatedADA);
		if($ADAResults!=-1){
			$this->dateSigned = $ADAResults[0]['submissionTimestamp'];
			$this->protocolNumberCode = $ADAResults[0]['protocolNumber'];
			$this->title = $ADAResults[0]['title'];
			$this->OrganizationIdRefUnits = $ADAResults[0]['organizationUnitId'];
			$this->signers = $ADAResults[0]['signerId'];
		}
		
	}
	
	/**
	 * This function is used to check some form values
	 * with old values from model.It is mainly used by
	 * the extendContract and changesContract scenario.
	 * It uses the functionallity of checkDateWithModel
	 * function.
 	 * @return null or the error found
	 */
	public function onBeforeValidate(){

		if( $this->getScenario()=='createrfp' || $this->getScenario()=='update'){			
			$documentUrl = $_POST['Rfp']['procurements'];
			$documentUrl=explode(';', $documentUrl);
			$tempData = new Procurement();
			$tempData = $tempData->findByPk($documentUrl[0]);
			//check newDateSigned is not > than today
			$this->checkDateWithModel('dateSigned',$_POST['Rfp']['dateSigned'],date('d/m/Y'),'<','RfpDateSignedNotToday');
			//check newDateSigned is > than oldDateSigned
			$this->checkDateWithModel('dateSigned',$tempData->dateSigned,$_POST['Rfp']['dateSigned'],'<=','RfpVsProcDateSigned');
		
		}
	}
	
	/**
	 * For the extend and changesContract scenario we have
	 * rules for dates. We have to compare the values of
	 * the existing contract with the new values inserted
	 * by the user. So we made a especially designed function.
	 * @param string  $type the attribute name
	 * @param date $oldUntil the model date
	 * @param date $newUntil the new date inserted by the user
	 * @param string $messageType the message to show. The message is saved to the messages file.
	 * @return null or the error found
	 */
	public function checkDateWithModel($type,$oldUntil,$newUntil,$operator,$messageType){

		if(isset($oldUntil) && $oldUntil!=''){
			$Olddate = DateTime::createFromFormat('d/m/Y', $oldUntil);
			$value = $Olddate->format('Y-m-d');
			$value = strtotime($value);
		}
		if(isset($newUntil) && $newUntil!=''){
			$Newdate = DateTime::createFromFormat('d/m/Y', $newUntil);
			$valueNew = $Newdate->format('Y-m-d');
			$valueNew = strtotime($valueNew);
		}
		
		if(isset($value) && $value!='' && isset($valueNew) && $valueNew!='' ){
			switch ($operator) {
				case '<':
					if($valueNew < $value){
						//check newUntilDate is > than oldDate
						$message = Yii::t('form',$messageType);
						$message .=$oldUntil;
						$this->addError($type,$message);
					}
					break;
				default:
					if($valueNew <= $value){
						//check newUntilDate is > than oldDate
						$message = Yii::t('form',$messageType);
						$message .=$oldUntil;
						$this->addError($type,$message);
					}
					break;
			}
		}
	}
						
}
?>
