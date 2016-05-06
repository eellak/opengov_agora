<?php

/**
 * This class supports the Contracts procedure.
 * It is the main model Class. 
 * In this class we define or support 
 * a) define the attributes of the Contract
 * b) define the validation rules for each attributes of the Contract
 * c) define the labels for each attribute of the Contract
 * d) the load function that returns the attributes values.
 * e) the findByPk function that returns the of the requested by uniquedocumentUrl of the Contract
 * f) the Save function that saves or updates the Contract.
 * g) The onBeforeValidation for specific scenarios
 * h) CheckDateWithModel function to check new with old date values
 * i) the isPermitted function that checks if the user has the permission to make the action. 
 * j) the GetNumberOfContracts function that connects to the API and gets the number of Contracts.
 * @author themiszamani
 * @version 1.0
 * @package agora 
 */

class Contract extends CFormModel{

	/**
     * The unique Document Code of the Contract
     * @var string
     */
	var $uniqueDocumentCode;
	/**
     * The unique id for each Contract
     * @var string
     */
	var $documentUrl;
	/**
     * The Contract title
     * @var string
     */
	var $title;
	/**
     * The date the Contract was signed
     * @var date
     */
	var $dateSigned;
	/**
     * The Contracts  start Date
     * @var date
     */
	var $since;
	/**
     * The Contracts  end Date
     * @var date
     */	
	var $until;
	
	/**
     * The Contracts doesnt always have an end date
     * This is a way to define it with a checkbox. 
     * @var integer
     */	
	var $untilexists;
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
     * The type of the contracting authority. Based
     * on the Lookup function
     * @var integer
     */	
	var $contractingAuthority;
	/**
     * The Award procedure of the call followed. Based
     * on the Lookup function
     * @var integer
     */	
	var $awardProcedure;
	/**
     * The Contracts Type. Based on the Lookup function
     * @var integer
     */	
	var $contractType;
	/**
     * The Contracts Commision Criteria. Based
     * on the Lookup function
     * @var integer
     */	
	var $commissionCriteria;
	/**
	 * The ADA of the call 
	 * @var string
	 */
	var $ADAdiakiriksis;
	/**
	 * The ADA of the results  
	 * @var string
	 */
	var $ADAkatakurosis;
	/**
	 * The ADAanathesis  
	 * @var string
	 */	
	var $ADAanathesis;
	/**
	 * The ADAtropopoihshs  
	 * @var string
	 */	
	var $ADAtropopoihshs;
	/**
	 * The ADAextends  
	 * @var string
	 */	
	var $ADAextends;
	/**
     * The Organization Unit id the payments belongs to. 
     * @var integer
     */
	var $OrganizationIdRefUnits;
	/**
     * The Organization name the contract belongs to. 
     * @var integer
     */
	var $OrganisationName;
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
	 * the place of the Organization that signed 
	 * the contract.
	 * @var string
	 */
	var $contractPlace;
	/**
	 * we need this variable in order to count the number of Signers
	 * @var integer
	 */
	var $numberofsigners;
	
	/**
	 * A checkbox if the contract uses Public Funds
	 * @var boolean 
	 */
	var $PublicFundsCheckBox;
	/**
	 * The SAE codes if PublicFunds is Checked
	 * @var string
	 */
	var $PublicFundsSAE;
	/**
	 * A checkbox if the contract is Cofinanced
	 * @var boolean 
	 */
	var $CofinancedCheckBox;
	/**
	 * The OPS code if CofinancedCheckBox is checked
	 * @var string
	 */ 
	var $CofinancedOPS;
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
	 * This defines if the payment is a new one.
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
     * The date the Contract was submitted to the api
     * @var date
     */
	var $submissionTime;
	/**
     * The date the Contract was last modified
     * @var date
     */
	var $lastModifiedTime;
	/**
	 * The id of the Organization the payment belongs to
	 * @var integer
	 */
	var $OrganizationIdRef;
	/**
	 * If we are refering to a deleted contract
	 * @var boolean
	 */
	var $deleted;
	/**
	 * If the contract is based on a procurement request
	 * in the procurement variable we keep the ADAs of the 
	 * procurements seperated by ;.
	 * @var string
	 */
	var $procurements;
	/**
	 * If we are refering to a new contract that changes
	 * the contractitem data of an existing one. THe ADA
	 * of the changed contract. 
	 * @var string
	 */	
	var $changesContract;
	/**
	 * If we are refering to a new contract that extends 
	 * the contractitem data  (quantity, of an existing one. THe ADA
	 * of the changed contract. 
	 * @var string
	 */	
	var $extendsContract;
	
	/**
	 * The notice ADA the contract is based on. 
	 * @var string
	 */	
	var $noticeADA;
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
 	 * - awardProcedure,contractingAuthority,contractType,commissionCriteria should be safe. After you post the form the selected item is shown. Otherwise the first value is shown. 
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
		array('title, dateSigned, since,OrganizationIdRefUnits,signers',
			 'required','on'=>'createcontract,extend,createcontractbyproc,createcontractbynotice,updatecontract'),
		array('Deldescription','required','on'=>'delete,cancelcontract'),
		array('dateSigned','date','format'=>'dd/MM/yyyy'),
		//Rule dateSigned must not be > from today
		//array('dateSigned','SCompareDates','compareValue'=>date('d/m/Y'),'operator'=>'<', 'allowEmpty'=>false ,'message'=>Yii::t('contract','DateSignedNotToday')),
		array('dateSigned','SComparePaymentDates','compareValue'=>date('d/m/Y'),'operator'=>'>', 'allowEmpty'=>false,'on'=>'createcontractbyproc'),
		array('dateSigned','SCompareDates','compareValue'=>date('d/m/Y'),'operator'=>'<=', 'allowEmpty'=>false ,'message'=>Yii::t('contract','DateSignedNotToday')),
		array('since','date','format'=>'dd/MM/yyyy'),
		array('until','date','format'=>'dd/MM/yyyy'),
		array('since','SCompareDates','compareAttribute'=>'dateSigned','operator'=>'>=','allowEmpty'=>false,'on'=>'createcontract,update,createcontractbyproc,createcontractbynotice'),
		array('until','SCompareDates','compareAttribute'=>'since','operator'=>'>','allowEmpty'=>false ,'on'=>'createcontract,update,createcontractbyproc'),
		array('document', 'file', 'types'=>'pdf,zip','allowEmpty'=>false,
			 'on'=>'createcontract,extend,createcontractbyproc,createcontractbyproc'),
		array('issuerEmail','email','allowName'=>false),
		array('PublicFundsSAE','SCompareRequiredCheckBoxInput','compareAttribute'=>'PublicFundsCheckBox'),
		array('CofinancedOPS','SCompareRequiredCheckBoxInput','compareAttribute'=>'CofinancedCheckBox'),
		//array('CofinancedOPS','SCompareRequiredValidator','compareAttribute'=>'PublicFundsSAE','compareValue'=>'someValue','message'=>Yii::t('contract','CofincancedOPSORPublicFundsSAE')),
		//array('PublicFundsSAE','SCompareRequiredValidator','compareAttribute'=>'CofinancedOPS','compareValue'=>'someValue','message'=>Yii::t('contract','PublicFundsSAEORCofincancedOPS')),
		array('awardProcedure,contractingAuthority,contractType,commissionCriteria,until','safe'),
		array('document','safe'),
		array('ADAdiakiriksis,ADAkatakurosis,ADAanathesis,untilexists,protocolNumberCode,ADAtropopoihshs,ADAextends','safe'),
		//$procurements Required on createcontractbyproc scenario
		array('procurements','required','on'=>'createcontractbyproc'),
		array('noticeADA','required','on'=>'createcontractbynotice'),
		array('documentUrl,cpvsid,title,foreis,ypoforeis,changesContract,noticeADA','safe'),
		array('Deldescription,deldescription,ADACancel,cancellationType','safe'),
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
			'since'=>Yii::t('contract','since'),
			'until'=>Yii::t('contract','until'),
			'untilexists'=>Yii::t('contract','untilexists'),
			'protocolNumberCode'=>Yii::t('contract','protocolNumberCode'),
			'diavgeiaPublished'=>Yii::t('contract','diavgeiaPublished'),
			'OrganizationIdRefUnits'=>Yii::t('contract','OrganizationIdRefUnits'),
			'signers'=>Yii::t('contract','signers'),
			'contractingAuthority'=>Yii::t('contract','contractingAuthority'),
			'awardProcedure'=>Yii::t('contract','awardProcedure'),
			'document'=>Yii::t('contract','document'),
			'contractType'=>Yii::t('contract','Contract Type'),
			'commissionCriteria'=>Yii::t('contract','Commission Criteria'),
			'uniqueDocumentCode'=>Yii::t('contract','uniqueDocumentCode'),
			'issuerEmail'=>Yii::t('form','issuerEmail'),
			'procurements'=>Yii::t('contract','Procurements'),
			'ADAdiakiriksis'=>Yii::t('contract','ADAdiakiriksis'),
			'ADAkatakurosis'=>Yii::t('contract','ADAkatakurosis'),
			'ADAanathesis'=>Yii::t('contract','ADAanathesis'),
			'ADAtropopoihshs'=>Yii::t('contract','ADAtropopoihshs'),
			'ADAextends'=>Yii::t('contract','ADAextends'),
			'Deldescription'=>Yii::t('form','Deldescription'),
			'PublicFundsCheckBox'=>Yii::t('contract','PublicFundsCheckBox'),
			'PublicFundsSAE'=>Yii::t('contract','PublicFundsSAE'),
			'CofinancedCheckBox'=>Yii::t('contract','CofinancedCheckBox'),
			'CofinancedOPS'=>Yii::t('contract','CofinancedOPS'),
			'noticeADA'=>Yii::t('rfp','RFP one'),
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
		$data = self::getContractItem($id);
		return $this;
	}

	/**
	 * Fetches the data of a contract in an array
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the contartapi data and the data are stored
	 * in a table (component Contractapidata.php)
	 * @return an array with the contract data
	 */
	public function getContractItem($id){

		//Connect to ApiConnector
		//sets the config table for the http request.
		$config['apirequest'] ='contractsitem';
		$config['documentid'] = $id;

		$api = new Apiconnector($config);
		$output = $api->makeRequest();

		$Results = new Contractapidata();
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
		$data = new ContractWriteApi();
		$data = $data->contractWrite($status);
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

		$config['apirequest'] ='contractsitem';
		$config['documentid'] =$uniqueDocumentCode;

		
		//send a curl request for the document
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
	
		//If no result return 404
		if($output==404) return $output;

		$Contract = new Contractapidata();
		if($output!=-1){
			$ContractData = $Contract->getItemData($output);
			$this->documentUrl = $uniqueDocumentCode;
			$this->submissionTime = $ContractData[0]['submissionTime'];
			$this->lastModifiedTime = $ContractData[0]['lastModifiedTime'];
			$this->deleted = $ContractData[0]['deleted'];
			$this->protocolNumberCode = $ContractData[0]['protocolNumberCode'];
			$this->title =(string)$ContractData[0]['title'];
			$this->diavgeiaPublished=  $ContractData[0]['diavgeiaPublished'];
			$this->dateSigned = $ContractData[0]['dateSigned'];
			$this->issuerEmail = $ContractData[0]['issuerEmail'];
			$this->OrganizationIdRef=$ContractData[0]['OrganizationIdRef'];
			
			$this->OrganizationIdRefUnits = $ContractData[0]['OrganizationIdRefUnits'];
			$this->OrganisationName =Units::item("Organization", $this->OrganizationIdRef,$this->OrganizationIdRef);
			
			//this is an array
			$this->signers = $ContractData[0]['signers'];
				
			$this->contractingAuthority = $ContractData[0]['contractingAuthority'];
			$this->awardProcedure = $ContractData[0]['awardProcedure'];
			$this->contractType = $ContractData[0]['contractType'];
			$this->commissionCriteria = $ContractData[0]['commissionCriteria'];
			$this->since = $ContractData[0]['since'];
			$this->until = $ContractData[0]['until'];
			$this->untilexists = $ContractData[0]['untilexists'];
			$this->contractPlace = $ContractData[0]['contractPlace'];
			$this->documentUrl = $ContractData[0]['documentUrl'];
			$this->uniqueDocumentCode = $ContractData[0]['documentUrl'];
			if(isset($ContractData[0]['changesContract']))
			$this->changesContract = $ContractData[0]['changesContract'];
			$this->extendsContract = $ContractData[0]['extendsContract'];
			
			//$this->OrganizationIdRef=Yii::app()->user->RefId;
			$this->relatedADAs=$ContractData[0]['relatedADAs'];
			$this->ADAdiakiriksis = (isset($ContractData[0]['ADAdiakiriksis']))?$ContractData[0]['ADAdiakiriksis']:'';
			$this->ADAkatakurosis = (isset($ContractData[0]['ADAkatakurosis']))?$ContractData[0]['ADAkatakurosis']:'';
			$this->ADAanathesis = (isset($ContractData[0]['ADAanathesis']))?$ContractData[0]['ADAanathesis']:'';
			$this->procurements = (isset($ContractData[0]['procurements']))?$ContractData[0]['procurements']:'';
			$this->PublicFundsCheckBox = (isset($ContractData[0]['PublicFundsCheckBox']))?$ContractData[0]['PublicFundsCheckBox']:'';
			$this->PublicFundsSAE = (isset($ContractData[0]['PublicFundsSAE']))?$ContractData[0]['PublicFundsSAE']:'';
			$this->CofinancedCheckBox = (isset($ContractData[0]['CofinancedCheckBox']))?$ContractData[0]['CofinancedCheckBox']:'';
			$this->CofinancedOPS = (isset($ContractData[0]['CofinancedOPS']))?$ContractData[0]['CofinancedOPS']:'';
			$this->cancelled = $ContractData[0]['cancelled'];
			$this->cancelledTime = $ContractData[0]['cancelledTime'];
			$this->cancelledReason = $ContractData[0]['cancelledReason'];
			$this->cancellationType = $ContractData[0]['cancellationType'];
			$this->cancelledFromUserIdRef = $ContractData[0]['cancelledFromUserIdRef'];
			$this->actionPermitted = $this->isPermitted($ContractData[0]['actionPermitted']);
		}
					
		return $this;

	}
	
/**
	 * Fetches the list of contracts in an array
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the contartapi data and are stored 
	 * in a table (component Contractapidata.php)
	 * @param string the search term
	 * @param integer the id of the selected organization 
	 * @return an array with the short contract list data 
	 */
	public function getContractListTable($search="",$org='',$limit=''){
		
		$model=new Contract();
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$Results = array();
		$config['apirequest'] ='contract';
		if($search!='')$config['search'] =$search;
		if($org!='') $config['org'] =$org;
		if($limit!='') $config['limit'] =$limit;
		
		$api = new Apiconnector($config);
		$output = $api->short();
		$Results=array();
		if($output==404 || $output==403) return $Results;		
		
		$Results = new Contractapidata();
		//if($search=='')
			$Results = $Results->getShortListTable($output);
		//else 
			//$Results = $Results->getItemData($output);
	
		//set actions if permitted
		if(count($Results)>1){		
		for($i=0;$i<count($Results) && $Results!=404 && $Results!=403;$i++)
			$Results[$i]['actionPermitted']=$model->isPermitted($Results[$i]['actionPermitted']);
		}
			//Fetch Data to Array
		return $Results;
		
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

		if($this->getScenario()=='createcontractbynotice'){
			$documentUrl = $_POST['Contractitem']['n0']['notice'];
			$tempData = new Rfp();
			$tempData = $tempData->findByPk($documentUrl);
			//check newDateSigned is > than oldDateSigned
			$this->checkDateWithModel('dateSigned',$tempData->dateSigned,$_POST['Contract']['dateSigned'],'<=','ContractdateSignedNewestThanRfp');
		}
		
		if(isset($_POST['Contract']['until']) && $_POST['Contract']['until']=='' && $_POST['Contract']['untilexists']==0){
			$message = Yii::t('form','untilOrUntilexists');
			//$message .=$oldUntil;
			$this->addError('until',$message);
		}
		if( $this->getScenario()=='extend'){
			$documentUrl = $_POST['Contract']['documentUrl'];
			$tempData = new Contract();
			$tempData = $this->findByPk($documentUrl);
			
			//check newDateSigned is not > than today
			$this->checkDateWithModel('dateSigned',$_POST['Contract']['dateSigned'],date('d/m/Y'),'<','DateSignedNotToday');
			//check newDateSigned is > than oldDateSigned
			$this->checkDateWithModel('dateSigned',$tempData->dateSigned,$_POST['Contract']['dateSigned'],'<=','OlddateSigned');
			//check Newsince is > than oldsince
			$this->checkDateWithModel('since',$tempData->since,$_POST['Contract']['since'],'<=','OldSince');
			//check Newuntil is > than olduntil
			if($_POST['Contract']['untilexists']!=1 && $tempData->untilexists!=1)
			$this->checkDateWithModel('until',$tempData->until,$_POST['Contract']['until'],'<=','OldUntil');
		}
		if($this->getScenario()=='changes'){
			$documentUrl = $_POST['Contract']['documentUrl'];
			$tempData = new Contract();
			$tempData = $this->findByPk($documentUrl);
			//check newDateSigned is not > than today
			$this->checkDateWithModel('dateSigned',$_POST['Contract']['dateSigned'],date('d/m/Y'),'<','DateSignedNotToday');
			//check newDateSigned is > than oldDateSigned
			$this->checkDateWithModel('dateSigned',$tempData->dateSigned,$_POST['Contract']['dateSigned'],'<=','OlddateSigned');
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

		$Olddate = DateTime::createFromFormat('d/m/Y', $oldUntil);
		$value = $Olddate->format('Y-m-d');
		$value = strtotime($value);
			
		$Newdate = DateTime::createFromFormat('d/m/Y', $newUntil);
		$valueNew = $Newdate->format('Y-m-d');
		$valueNew = strtotime($valueNew);
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
	
	/**
	 * This function gets the number of Contracts.
	 * It makes a request to the Apiconnector and 
	 * it makes the appropriate request to the middleware.
	 * @return integer the number of Contracts
	 * */
	public function GetNumberOfContracts(){

		$config['apirequest'] ='contractsnumber';
		if(isset($_GET['org'])){ 
			$Organizations = new Organizations();
			$org = $Organizations->getOrgId($_GET['org']);
			$config['org']=$org;
		}
		//send a curl request for the document
		$api = new Apiconnector($config);
		$output = $api->number('contract');
		$Proc = new Contractapidata();
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
	
public function getRfMultipleListTable($documentUrl='',$title='',$cpvsid='',$org=''){
		
		$model=new Contract();
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$Results = array();

		$config['apirequest'] ='contract';
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
		$modelR = new Contractapidata();
		$Results = $modelR->getShortListTable($output);
		return $Results;
		
		
	}
}
?>
