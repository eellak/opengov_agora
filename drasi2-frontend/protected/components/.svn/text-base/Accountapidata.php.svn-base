<?php
class Accountapidata{

   /** Version.*/
  const VERSION = '1.1.0';
  	
   /**
   * Default options for curl.
   */
 
	
  	public $userName;
  	public $RefId;
  	public $validUser=false;
  	public $isAdmin = false;
  	public $UserName;
	public $lastName,$firstName,$OrganisationName ;
	public $OrganisationAfm;
	public $OrganisationFullAddress;
	/*the organisation address number */
	public $OrganisationAddress;
	/*the organisation address number */
	public $OrganisationAddressNo;
	/*the organisation postal code */
	public $OrganisationAddressPostal;
	public $OrganisationType;
	
	var $ContractUrlAuthSchema;
	var $ContractUrlCommon;
	/**
	 * the users email 
	 * @var string
	 */
	var $email;
		
	
	CONST ERROR_PASSWORD_INVALID='Δεν είναι δυνατή η σύνδεση';
	
	/**
	 * The construction function of the account data 
	 * it just calls the setCurlData and sets the data from the xml Request
	 * @param the xml output $output
	 */
	public function __construct(){
	}
		//$this->setCurlData($output);
	//	$this->ContractUrlAuthSchema  = Yii::app()->params['agora']['auth'];
	//	$this->ContractUrlCommon = Yii::app()->params['agora']['auth'];
//	}
    /**
    * Set current user password (if is)
    * @return string 
    */
 	public function setCurlData($output)
    {
    	
    	$this->ContractUrlAuthSchema  = Yii::app()->params['agora']['auth'];
		$this->ContractUrlCommon = Yii::app()->params['agora']['common'];
		
		$xml = new SimpleXMLElement($output);
       	$xml->registerXPathNamespace('b',$this->ContractUrlAuthSchema);
		$xml->registerXPathNamespace('a',$this->ContractUrlCommon);
		
		$result = $xml->xpath('//b:organizations/attribute::idRef');



		$isAd = $xml->xpath('//b:admin');
		$role = $xml->xpath('//b:role');
		$userName = $xml->xpath ('//b:userName');
		$email = $xml->xpath ('//b:email');
		$firstName = $xml->xpath ('//b:firstName');
		$lastName = $xml->xpath ('//b:lastName');
		$name = $xml->xpath('//b:odeMember/b:organizations/a:name');			
		$afm = $xml->xpath('//b:odeMember/b:organizations/a:afm');			
		$address = $xml->xpath('//b:odeMember/b:organizations/a:address');			
		$addressNo = $xml->xpath('//b:odeMember/b:organizations/a:addressNo');			
		$addressPostal = $xml->xpath('//b:odeMember/b:organizations/a:addressPostal');			
		$OrganizationType = $xml->xpath('//b:odeMember/b:organizations/a:organizationType');	
		$authenticated = $xml->xpath('//b:authenticated');		
		$this->RefId = (int)$result[0];
		$this->validUser= isset($authenticated[0])?(string) $authenticated[0]:"false";
		//(string)$authenticated[0];
		$this->role = isset($role[0])?(string)$role[0]:"";
		$this->UserName =isset($userName[0])?(string)$userName[0]:"";
		$this->email =isset($email[0])?(string)$email[0]:"";		
		$this->isAdmin = isset($isAd[0])?(string) $isAd[0]:"false";		
		$this->firstName = isset($firstName[0])?(string) $firstName[0]:"";
		$this->lastName = isset($lastName[0])?(string) $lastName[0]:"";
		$this->OrganisationName = isset($name[0])?(string)$name[0]:"";
		$this->OrganisationAfm = isset($afm[0])?(string) $afm[0]:"";
		$this->OrganisationAddress = isset($address[0])?(string) $address[0]:"";
		$this->OrganisationAddressNo = isset($addressNo[0])?(string) $addressNo[0]:"";
		$this->OrganisationAddressPostal = isset($addressPostal[0])?(string) $addressPostal[0]:"";
		$this->OrganisationAddressPostal = str_replace(' ', '', $this->OrganisationAddressPostal);
		$this->OrganisationFullAddress = $this->OrganisationAddress ."&nbsp;". $this->OrganisationAddressNo ."&nbsp;-&nbsp;".$this->OrganisationAddressPostal;		Yii::app()->session->add('OrganisationFullAddress', $this->OrganisationFullAddress);
   		$this->OrganisationType = isset($OrganizationType[0])?(string) $OrganizationType[0]:"";
    }
    

}

?>
