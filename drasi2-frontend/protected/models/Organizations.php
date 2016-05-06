<?php

/**
 * This is the main class for the organization Views.
 * In this class we 
 * a) define the attributes of the Organization
 * b) define the validation rules for each attributes of the Organization
 * c) define the labels for each attribute of the Organization
 * @author themiszamani
 *
 */
class Organizations extends CFormModel{
	
	 /** The followings are the available properties of the organization*/
	 
	/**
	 * The Organization Id to See
	 * @var integer
	 */
	var $Org;
	private static $_itemsNames=array();
	
	
	
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			
		);
	}
	/**
	 * Declares the model attribute labels. 
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'Org' => Yii::t('form','organization'),		
			);
	}
	
	/**
	 * Finds a single Organization that has the specified attribute values. 
	 * It makes the request to the middleware and sets the 
	 * data of the specified Organization.
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
			$this->OrganizationIdRef=(isset(Yii::app()->user->RefId))?Yii::app()->user->RefId:$procData[0]['OrganizationIdRef'];
			$this->actionPermitted =$this->isPermitted($procData[0]['actionPermitted']);
			$this->issuerEmail =$procData[0]['issuerEmail'];
			$this->approvesRequest =$procData[0]['approvesRequest'];
			$this->isApproved = $this->isApproved($this->ProcurementId);
			$this->awardProcedure = $procData[0]['awardProcedure'];
			
		}
		
		return $this;	
		
	}
	
	public function getOrgId($org){
		return Units::item('OrganizationId', $org, $org);
	}
	
	public function getLabelName($org){
		return 	Units::item('OrganizationLabel', $org, $org);
		
	}
	public  function getDhmous(){
		return Units::items('OrganizationDhmoi',1);
	}
	public  function getPeriferies(){
		return Units::items('OrganizationPer',2);
	}
	
	public  function getYpourgeia(){
		
		return Units::items('OrganizationYp',3);
	}
	public  function getYpourgeiaStatic(){
		
		return Units::items('OrganizationYpText',3);
	}
	
	
	
	public static function getArxes(){
		$dataAr[0]['title']='ΑΝΩΤΑΤΟ ΣΥΜΒΟΥΛΙΟ ΕΠΙΛΟΓΗΣ ΠΡΟΣΩΠΙΚΟΥ (ΑΣΕΠ)';
		$dataAr[0]['id']=7452;
		$dataAr[0]['category']='arxes';
		$dataAr[0]['class']='greyBg';
		$dataAr[0]['short']=Organizations::getLabelName($dataAr[0]['title']);
		$dataAr[1]['title']='ΑΡΧΗ ΔΙΑΣΦΑΛΙΣΗΣ ΤΗΣ ΠΟΙΟΤΗΤΑΣ ΣΤΗΝ ΑΝΩΤΑΤΗ ΕΚΠΑΙΔΕΥΣΗ (ΑΔΙΠ)';
		$dataAr[1]['id']=22949;
		$dataAr[1]['category']='arxes';
		$dataAr[1]['class']='greyBg';
		$dataAr[1]['short']=Organizations::getLabelName($dataAr[1]['title']);
		$dataAr[2]['title']='ΑΡΧΗ ΔΙΑΣΦΑΛΙΣΗΣ ΤΟΥ ΑΠΟΡΡΗΤΟΥ ΤΩΝ ΕΠΙΚΟΙΝΩΝΙΩΝ (ΑΔΑΕ) (ΣΥΝΤΑΓΜΑΤΙΚΑ ΚΑΤΟΧΥΡΩΜΕΝΗ)';
		$dataAr[2]['id']=22794;
		$dataAr[2]['category']='arxes';
		$dataAr[2]['class']='greyBg';
		$dataAr[2]['short']=Organizations::getLabelName($dataAr[2]['title']);
		$dataAr[3]['title']='ΑΡΧΗ ΠΡΟΣΤΑΣΙΑΣ ΔΕΔΟΜΕΝΩΝ ΠΡΟΣΩΠΙΚΟΥ ΧΑΡΑΚΤΗΡΑ';
		$dataAr[3]['short']=Organizations::getLabelName($dataAr[3]['title']);
		$dataAr[3]['id']=22951;
		$dataAr[3]['category']='arxes';
		$dataAr[3]['class']='greyBg';
		$dataAr[4]['title']='ΕΘΝΙΚΗ ΑΝΑΛΟΓΙΣΤΙΚΗ ΑΡΧΗ';
		$dataAr[4]['id']=22953;
		$dataAr[4]['short']=Organizations::getLabelName($dataAr[4]['title']);
		$dataAr[4]['category']='arxes';
		$dataAr[4]['class']='greyBg';
		$dataAr[5]['title']='ΕΘΝΙΚΗ ΑΡΧΗ ΚΑΤΑΠΟΛΕΜΗΣΗΣ ΤΗΣ ΝΟΜΙΜΟΠΟΙΗΣΗΣ ΕΣΟΔΩΝ ΑΠΟ ΕΓΚΛΗΜΑΤΙΚΕΣ ΔΡΑΣΤΗΡΙΟΤΗΤΕΣ';
		$dataAr[5]['id']=22955;
		$dataAr[5]['category']='arxes';
		$dataAr[5]['class']='greyBg';
		$dataAr[5]['short']=Organizations::getLabelName($dataAr[5]['title']);
		$dataAr[6]['title']='ΕΘΝΙΚΗ ΕΠΙΤΡΟΠΗ ΗΛΕΚΤΡΟΝΙΚΩΝ ΜΕΣΩΝ ΕΠΙΚΟΙΝΩΝΙΑΣ';
		$dataAr[6]['short']=Organizations::getLabelName($dataAr[6]['title']);
		$dataAr[6]['id']=22957;
		$dataAr[6]['category']='arxes';
		$dataAr[6]['class']='greyBg';
		$dataAr[7]['title']='ΕΘΝΙΚΗ ΕΠΙΤΡΟΠΗ ΤΗΛΕΠΙΚΟΙΝΩΝΙΩΝ & ΤΑΧΥΔΡΟΜΕΙΩΝ (ΕΕΤΤ)';
		$dataAr[7]['id']=7726;
		$dataAr[7]['short']=Organizations::getLabelName($dataAr[7]['title']);
		$dataAr[7]['category']='arxes';
		$dataAr[7]['class']='greyBg';
		$dataAr[8]['title']='ΕΘΝΙΚΟ ΣΥΜΒΟΥΛΙΟ ΔΗΜΟΣΙΑΣ ΥΓΕΙΑΣ (ΕΣΥΔΥ)';
		$dataAr[8]['id']=22959;
		$dataAr[8]['category']='arxes';
		$dataAr[8]['class']='greyBg';
		$dataAr[8]['short']=Organizations::getLabelName($dataAr[8]['title']);
		$dataAr[9]['title']='ΕΘΝΙΚΟ ΣΥΜΒΟΥΛΙΟ ΡΑΔΙΟΤΗΛΕΟΡΑΣΗΣ (ΕΣΡ) (ΣΥΝΤΑΓΜΑΤΙΚΑ ΚΑΤΟΧΥΡΩΜΕΝΗ)';
		$dataAr[9]['id']=22960;
		$dataAr[9]['category']='arxes';
		$dataAr[9]['class']='greyBg';
		$dataAr[9]['short']='esr';
		$dataAr[10]['title']='ΕΛΛΗΝΙΚΗ ΣΤΑΤΙΣΤΙΚΗ ΑΡΧΗ (ΕΛ.ΣΤΑΤ.)';
		$dataAr[10]['short']=Organizations::getLabelName($dataAr[10]['title']);
		$dataAr[10]['id']=7839;
		$dataAr[10]['category']='arxes';
		$dataAr[10]['class']='greyBg';
		$dataAr[11]['title']='ΕΠΙΤΡΟΠΗ ΑΝΤΑΓΩΝΙΣΜΟΥ';
		$dataAr[11]['id']=22963;
		$dataAr[11]['short']=Organizations::getLabelName($dataAr[11]['title']);
		$dataAr[11]['category']='arxes';
		$dataAr[11]['class']='greyBg';
		$dataAr[12]['title']='ΕΠΙΤΡΟΠΗ ΕΠΟΠΤΕΙΑΣ & ΕΛΕΓΧΟΥ ΤΥΧΕΡΩΝ ΠΑΙΧΝΙΔΙΩΝ';
		$dataAr[12]['id']=22961;
		$dataAr[12]['category']='arxes';
		$dataAr[12]['class']='greyBg';
		$dataAr[12]['short']=Organizations::getLabelName($dataAr[12]['title']);
		$dataAr[13]['title']='ΡΥΘΜΙΣΤΙΚΗ ΑΡΧΗ ΕΝΕΡΓΕΙΑΣ (ΡΑΕ)';
		$dataAr[13]['short']=Organizations::getLabelName($dataAr[13]['title']);
		$dataAr[13]['id']=22793;
		$dataAr[13]['category']='arxes';
		$dataAr[13]['class']='greyBg';
		$dataAr[14]['title']='ΣΥΝΗΓΟΡΟΣ ΤΟΥ ΚΑΤΑΝΑΛΩΤΗ';
		$dataAr[14]['id']=22964;
		$dataAr[14]['short']=Organizations::getLabelName($dataAr[14]['title']);
		$dataAr[14]['category']='arxes';
		$dataAr[14]['class']='greyBg';
		$dataAr[15]['title']='ΣΥΝΗΓΟΡΟΣ ΤΟΥ ΠΟΛΙΤΗ';
		$dataAr[15]['id']=22966;
		$dataAr[15]['category']='arxes';
		$dataAr[15]['class']='greyBg';
		$dataAr[15]['short']=Organizations::getLabelName($dataAr[15]['title']);
		return $dataAr;
	}
	
}
