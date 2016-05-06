<?php

/**
 * This is the main class for the procurements.
 * In this class we 
 * a) define the attributes of the Procurement
 * b) define the validation rules for each attributes of the Procurement
 * c) define the labels for each attribute of the Procurement
 * d) the findByPk function that returns the of the requested by uniquedocumentUrl of the procurement
 * e) the Save function that saves or updates the procurement.
 * @author themiszamani
 *
 */
class Procurement extends CFormModel{
	
	 /** The followings are the available properties of the procurement*/
	 
	var $ProcurementId;
	/* the unique Document URI created from the api*/
	var $documentUrl;
	
	/* the procurement title*/
	var $title;
	/*the procurement id */
	var $uniqueDocumentCode;
	/* the time the procurmenet was submitted*/
	var $submissionTime;
	/* the time the procurmenet was last modified*/
	var $lastModifiedTime;
	/*the procurement protocol number*/
	var $protocolNumberCode;
	/* the  Organization id the procurement belongs to*/
	var $OrganizationIdRef;
	/*the selected Units*/
	var $OrganizationIdRefUnits=array();
	/* the signers of the procurement */
	var $signers=array();
	/* the user may select to add but not to publish the 
	 * procurement at Diavgeia
	 */
	var $diavgeiaPublished = false;
	/* the ADA the procurement obtained from Diavgeia*/
	//var $adaCode;
	/* the datee the procurement request was signed*/
	var $dateSigned;
	/*the responsibily Assumption Code the procurement has 
	 * according to PD113 (Κωδικός Ανάληψης Υποχρεωσης)
	 */
	var $responsibilityAssumptionCode;
	/* the requested procurement document*/
	public $document;
	/* the contract items (quantity,cost,vat,currency...)*/
	var $contractItems = array();
	var $isNewRecord;
	var $issuerEmail;
	
	//if the user deletes a procurement a description must 
	//be provided
	var $Deldescription;
	//if the update, delete action is permitted <24Hours
	var $actionPermitted;
	/**
	 * This request is approved
	 * @var string
	 */
	var $approvesRequest;
	var $ContractUrlCommon  = 'http://agora.opengov.gr/schema/common-0.1'; 
	var $isApproved;
	
	/**
	 * When a procurement request is approved , the user has to select the awrd procedure he will follow.
	 * @var string
	 */
	var $awardProcedure;
	
	/**
	 * the day they want the procurement
	 * @var string
	 */
	var $fulfilmentDate;
	var $cpvsid;
	var $foreis;
	var $ypoforeis;
	var $totalCostBeforeVAT;
	var $ADA;
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
	
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('title, OrganizationIdRefUnits,signers,dateSigned', 'required','on'=>'createproc,update,approverequest'),
			array('Deldescription','required','on'=>'deleteproc,cancelproc'),
			//responsibilityAssumptionCode, protocolNumberCode
			array('signers,OrganizationIdRefUnits', 'numerical', 'integerOnly'=>true),
			array('issuerEmail','required'),
			array('title', 'length', 'max'=>512),
			//array('adaCode', 'length', 'max'=>24),
			array('protocolNumberCode', 'length', 'max'=>128),
			array('dateSigned','date','format'=>'dd/MM/yyyy'),
			array('dateSigned','SCompareDates','compareValue'=>date('d/m/Y'),'operator'=>'<='),
			array('fulfilmentDate','date','format'=>'dd/MM/yyyy'),
			
			//array('document','safe'),
            array('document', 'file', 'types'=>'pdf,zip','allowEmpty'=>true,'on'=>'createproc,approverequest'),	
    		array('issuerEmail','email','allowName'=>false),
    		array('title, dateSigned,protocolNumberCode,OrganizationIdRefUnits,signers,
    			  awardProcedure,responsibilityAssumptionCode,fulfilmentDate','safe'),
    		array('documentUrl,cpvsid,title,foreis,ypoforeis,ADA,documentURlFromDiavgeia,
    			  deldescription,ADACancel,cancellationType','safe'),
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
			'title' => Yii::t('procurement','title'),
			'documentUrl' => Yii::t('procurement','uniqueDocumentCode'),
			'protocolNumberCode' => Yii::t('procurement','protocolNumberCode'),
			'OrganizationIdRefUnits' => Yii::t('procurement','OrganizationIdRefUnits'),
			'signers' => Yii::t('procurement','signers'),
			'diavgeiaPublished' => Yii::t('procurement','diavgeiaPublished'),
			//'adaCode' => Yii::t('procurement','adaCode'),
			'dateSigned' => Yii::t('procurement','dateSigned'),
			'document' =>Yii::t('procurement','document'),
			'responsibilityAssumptionCode' => Yii::t('procurement','responsibilityAssumptionCode'),
			'issuerEmail'=>Yii::t('form','issuerEmail'),
			'Deldescription'=>Yii::t('form','Deldescription'),
			'awardProcedure'=>Yii::t('contract','awardProcedure'),	
			'fulfilmentDate'=>Yii::t('procurement','fulfilmentDate'),		
			'cpvsid' => Yii::t('item','CPVS'),
			'documentUrl' => Yii::t('contract','uniqueDocumentCode'),
			'foreis' => Yii::t('form','foreis'),
			'ypoforeis' => Yii::t('form','ypoforeis'),	
			'ADA'=>'ΑΔΑ απόφασης έγκρισης - Διαύγεια', 
			'cancellationType'=>Yii::t('form','cancellationType'),
			'ADACancel'=>Yii::t('form','ADACancel'),
		);
	}
	
	/**
	 * Finds a single procurement that has the specified attribute values. 
	 * It makes the request to the middleware and sets the 
	 * data of the specified procurement.
	 *  @param string $uniqueDocumentCode
	 */
	
	public function findByPk($uniqueDocumentCode){

		$config['apirequest'] ='procurement';
		$config['documentid'] =$uniqueDocumentCode;
		$procData=array();
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
	
		if($output==404) return $output;
		$Proc = new ProcurementApiDAta();
		$procData = $Proc->getItemData($output);
		if($procData!=-1){
			if(isset($procData[0]['title'])){
				$this->ProcurementId = $uniqueDocumentCode;
				$this->title =$procData[0]['title'];
				$this->submissionTime = $procData[0]['submissionTime'];
				$this->lastModifiedTime = $procData[0]['lastModifiedTime'];
				$this->uniqueDocumentCode =$procData[0]['uniqueDocumentCode'];
				$this->protocolNumberCode =$procData[0]['protocolNumberCode'];
				$this->dateSigned = $procData[0]['dateSigned'];
				$this->OrganizationIdRef =$procData[0]['OrganizationIdRef'];
				$this->OrganizationIdRefUnits = $procData[0]['OrganizationIdRefUnits'];
				$this->signers = $procData[0]['signer'];
				$this->documentUrl = $procData[0]['documentUrl'];
				//$this->OrganizationIdRef=(isset(Yii::app()->user->RefId))?Yii::app()->user->RefId:$procData[0]['OrganizationIdRef'];
				
				$this->actionPermitted =$this->isPermitted($procData[0]['actionPermitted']);
				$this->issuerEmail =$procData[0]['issuerEmail'];
				$this->approvesRequest =$procData[0]['approvesRequest'];
				$this->isApproved = $this->isApproved($this->ProcurementId);
				$this->awardProcedure = $procData[0]['awardProcedure'];
				$this->responsibilityAssumptionCode = $procData[0]['responsibilityAssumptionCode'];
				$this->fulfilmentDate = $procData[0]['fulfilmentDate'];
				$this->cancelled = $procData[0]['cancelled'];
				$this->cancelledReason = $procData[0]['cancelledReason'];
				$this->cancellationType = $procData[0]['cancellationType'];
				$this->cancelledFromUserIdRef = $procData[0]['cancelledFromUserIdRef'];
				$this->cancelledTime = $procData[0]['cancelledTime'];
			}
			
		}
		return $this;	
		
		
		
	}
	
	/**
	 * It saves the new procurement, or updates
	 * the procurement data. It makes a request
	 * to the ProcurementWriteApi and according
	 * to the status it makes the appropriate request 
	 * to the middleware.
	 * @param string $status either create or update
	 */
	public function save($status='create'){
		$data = new ProcurementWriteApi();
		if($status=='create')
			$data = $data->procurementWrite('createProcurement');
		else if ($status=='update')
			$data = $data->procurementWrite('updateProcurement');
		else 
			$data = $data->procurementWrite('approveRequest');
		return $data;
			
	}
	
	/**
	 * This function gets the number of procurements.
	 * It makes a request to the Apiconnector and 
	 * it makes the appropriate request to the middleware.
	 * @return integer the number of procurements
	 * */
	public function GetNumberOfProcurement(){

		$config['apirequest'] ='procurementApproved';
		
		if(isset($_GET['org'])){ 
			$Organizations = new Organizations();
			$org = $Organizations->getOrgId($_GET['org']);
			$config['org']=$org;
		}
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->number('procurement');
		$Proc = new ProcurementApiDAta();
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

	
	function CheckForErrors($data){
	
		$xml = new SimpleXMLElement($data);
		$xml->registerXPathNamespace('a',$this->ContractUrlCommon);
		
		$result = $xml->xpath('//a:error/a:field');
		if(isset($result[0]))
		$error = (string)$result[0];
		else $error="";
		return $error;
	}

	/**
	 * This function shows the ADa number of the procurement approved
	 * by this procurement. 
	 * @param string $documentUrl the procurement ada 
	 */
	public function isApproved($documentUrl){
		$config['apirequest'] ='procurementApprove';
		$config['documentid'] = $documentUrl;
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		return $output;
	}
	
/**
	 * Fetches the list of procurements in an array
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the contartapi data and are stored 
	 * in a table (component Contractapidata.php)
	 * Before the Results are Send we check the permissions of the action (isPermitted).
	 * @return an array with the short contract list data 
	 */
	public function getProcurementList($search='',$name='',$org='',$limit=''){
		$model = new Procurement();
		$config['apirequest'] ='procurement';
		if($search!='') $config['search'] =$search;
		if($name!='') $config['name'] =$name;
		if(isset($_GET['org'])) $config['org']=$org;
		if($limit!='')	$config['limit'] =$limit;
		
		
		$Results=array();
		$api = new Apiconnector($config);
		$output = $api->short();
		///echo $output;
		if($output==404 || $output==403 || $output==500) return $Results;		
		$Results = new ProcurementApiDAta();
	//	if($search!='' && $name==''){
			//$Results = $Results->getItemData($output);
		//}else
			$Results = $Results->getShortListOutput($output);
		
		$i=0;	
		$checkApproval = new Procurement();
		;
		if(count($Results)>1){	
			for($i=0;$i<count($Results) && $Results!=404 && $Results!=403;$i++){
				$Results[$i]['actionPermitted'] =$model->isPermitted($Results[$i]['actionPermitted']);
				if($search=='approved')
					$Results[$i]['isApproved'] = $checkApproval->isApproved($Results[$i]['uniqueDocumentCode']);
				else $Results[$i]['isApproved']=''; 
				$Results[$i]['OrganisationName'] = Units::item("Organization", $Results[$i]['OrganizationIdRef'],$Results[$i]['OrganizationIdRef']);
			}
		}else{
			if(count($Results)==1){	
				if(isset($Results[$i]['actionPermitted'])){
					$Results[$i]['actionPermitted'] =$model->isPermitted($Results[$i]['actionPermitted']);
					$Results[$i]['isApproved'] = $checkApproval->isApproved($Results[$i]['uniqueDocumentCode']);		
					$Results[$i]['OrganisationName'] = Units::item("Organization", $Results[$i]['OrganizationIdRef'],$Results[$i]['OrganizationIdRef']);
				}
			}
		}
		return $Results;
		
	}
	
public function getRfMultipleListTable($documentUrl='',$title='',$cpvsid='',$org=''){
		
		$model=new Procurement();
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$Results = array();

		$config['apirequest'] ='procurement';
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
		$modelR = new ProcurementApiDAta();
		$Results = $modelR->getShortList($output);
		
		return $Results;
		
		
	}

}