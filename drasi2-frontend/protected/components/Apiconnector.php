<?php
class Apiconnector{
	/**
     * the users username
     * @var string
     */	
	var $username;
	/**
     * the users password
     * @var string
     */	
	var $password;
	/**
     * the API Type request
     * @var string
     */	
	var $apirequest;
	/**
     * the document id when a single item is requested
     * @var string
     */	
	var $documentid;
	var $docdocumentid;
	/**
     * the xml data to send
     * @var string
     */	

	var $data;
	/**
     * the size of the xml we want to send via put (ex update procurement )
     * @var integer
     */	
	var $filesize;
	/**
     * the search parameter 
     * @var string
     */	
	var $search;
	/**
     * the deldescription send to explain the reason of deletion 
     * @var string
     */
	var $reason;
	/**
	 * the contractid related document url
	 * @var string
	 */
	var $contractid;
	
	/**
	 * the Authentication Url
	 * @var string
	 */
	var $UrlAuthenticate;
	/**
	 * the Authentication Url
	 * @var string
	 */
	var $ContractUrlAuthSchema;
	/**
	 * the Common schema Url
	 * @var string
	 */
	var $SchemaCommon;
	/**
	 * the Payment schema Url
	 * @var string
	 */
	var $PaymentSchema;
	/**
	 * the Short Contract Url
	 * @var string
	 */
	var $UrlContractsShort;
	/**
	 * the Contract Item Url
	 * @var string
	 */
	var $UrlContractsItem ;
	/**
	 * the Url from where  to download the Contract file 
	 * @var string
	 */
	var $UrlContractDown;
	/**
	 * the Procurment Item Url
	 * @var string
	 */
	var $UrlProcurement;
	/**
	 * the Url from where  to download the Procurment file 
	 * @var string
	 */
	var $UrlProcurementDown;
	/**
	* The RFP Schema Url defines if the payment is a new one.
	* @var string
  	*/
  	var $RFPUrlSchema;
  	/*
  	 * THe RFP list
  	 * @var string
  	 */
  	var $UrlRfpList;
  /**
	 * the Short RFP Url
	 * @var string
	 */
	var $UrlRfpShort;
	/**
	 * the RFP Item Url
	 * @var string
	 */
	var $UrlRfpItem;
	/**
	 * the Url from where  to download the RFP file 
	 * @var string
	 */
	var $UrlRfpDownload;
	/**
	 * the Short Payments Url
	 * @var string
	 */
	var $UrlPaymentsShort;
	/**
	 * the Payments Item Url
	 * @var string
	 */
	var $UrlPaymentsItem;
	/**
	 * the Url from where  to download the Payments file 
	 * @var string
	 */
	var $UrlPaymentsDownload;
	/**
	 * the time of connection timeout of the request
	 * @var string
	 */
	var $ConnectionTimeOut;
	/**
	 * the name of the prcurement request (type, searcxh)
	 * @var string
	 */
	var $name;
	/**
	 * the CPVS URL 
	 * @var string
	 */
	var $UrlCpvs;
	
	/**
	 * THe Opendata Url
	 * @var string
	 */
	var $Opendata;
	/**
	 * The organization Id 
	 * @var integer
	 */
	var $org;
	/**
	 * The limit of results
	 * @var integer
	 */
	var $limit;
	/**
	 * The title value for search
	 * @var string
	 */
	var $title;
	/**
	 * The ADA to search
	 * @var string
	 */
	
	var $ada;
	/**
	 * The CPV value for search
	 * @var string
	 */
	var $cpv;
	
	/**
	 * The type of cancelation
	 * @var integer
	 */
	var $cancellationType;
	/**
   * Initialize an apiconnector request 
   *
   * The configuration:
   * - username: the users username 
   * - password : the users password
   * - apirequest: the type of request 
   *
   * @param Array $config the application configuration
   */
  public function __construct($config="") {
  		$this->UrlAuthenticate = Yii::app()->params['agora']['authenticate'];
  		$this->ContractUrlAuthSchema = Yii::app()->params['agora']['auth'];
  		$this->SchemaCommon = Yii::app()->params['agora']['common'];
  		$this->PaymentSchema = Yii::app()->params['agora']['payment'];
  		$this->RFPUrlSchema = Yii::app()->params['agora']['rfp'];
  		$this->UrlRfpShort = Yii::app()->params['rfp']['short'];
  		$this->UrlRfpList = Yii::app()->params['rfp']['list'];
  		
  		
  		$this->UrlRfpItem = Yii::app()->params['rfp']['item'];
  		$this->UrlRfpDown = Yii::app()->params['rfp']['download'];
  		$this->UrlContractsShort = Yii::app()->params['contract']['short'];
  		$this->UrlContractsItem = Yii::app()->params['contract']['item'];
  		$this->UrlContractsDown = Yii::app()->params['contract']['download'];
  		$this->UrlProcurement = Yii::app()->params['procurement']['list'];
  		$this->UrlProcurementDown = Yii::app()->params['procurement']['download'];
  		$this->UrlPaymentsShort = Yii::app()->params['payments']['list'];
  		$this->UrlPaymentsItem=Yii::app()->params['payments']['item'];
  		$this->UrlPaymentsDownload=Yii::app()->params['payments']['download'];
  		$this->ConnectionTimeOut = Yii::app()->params['agora']['ConnectionTimeout'];
  		$this->UrlCpvs=Yii::app()->params['taxonomy']['cpv_codes'];
		$this->UrlProcurementApprove = Yii::app()->params['procurement']['approve'];
		$this->Opendata = Yii::app()->params['agora']['opendata'];
  		if(isset($config['username']))
	  		$this->username = $config['username'];
	  	else if(isset(Yii::app()->user->UserName))
	  		$this->username=Yii::app()->user->UserName;

	  	if(isset($config['password']))
	  		$this->password = $config['password'];
	  	else if(isset(Yii::app()->user->password))
	  		$this->password=Yii::app()->user->password;

	  	if(isset($config['apirequest']))
	  		$this->apirequest = $config['apirequest'];
	  	if(isset($config['documentid']))
	  		$this->documentid = urlencode($config['documentid']);
	  	if(isset($config['docdocumentid']))
	  		$this->docdocumentid = urlencode($config['docdocumentid']);
	  	
	  	if(isset($config['data']))
	  		$this->data = $config['data'];
	  		
	  	if(isset($config['name']))
	  		$this->name = $config['name'];
	  	if(isset($config['filesize']))
	  		$this->filesize = $config['filesize'];
	  		
	  	if(isset($config['search']))
	  		$this->search = urlencode($config['search']);
	  	
	  	if(isset($config['reason']))
	  		$this->reason = $config['reason'];
	  	if(isset($config['cpv']) && $config['cpv']!=''){
	  		$this->cpv = $config['cpv'];
	  	}
	  		
	  	if(isset($config['contractid'])) $this->contractid = urlencode($config['contractid']);
	  	if(isset($config['org'])) $this->org = $config['org'];
	  	if(isset($config['limit'])) $this->limit = $config['limit'];
	  	if(isset($config['title'])) $this->title = urlencode($config['title']);
	  	if(isset($config['ada'])) $this->ada = urlencode($config['ada']);
	  	if(isset($config['cancellationType'])) $this->cancellationType = $config['cancellationType'];
	  	
  }
  
   /**
   * Makes an HTTP request to the api. The HTTP request is based on the 
   * apirequest type. 
   *
   * @return String the response text or -1 if the autherisation fails
   */
  	public function makeRequest() {
  		
  		Yii::log('makeRequest',$this->apirequest);
  		switch($this->apirequest){
			case "decisions":
  				$url = $this->Opendata."decisions?ada=".$this->documentid;
  			
  				$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => 300));
	  			$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  		
  			case "procurementApprove":
  				$url = $this->UrlProcurement."approval/".$this->documentid;
  				$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => $this->ConnectionTimeOut));
	  			$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");

				break;
  			case "cpvcodes":
  				$url = $this->UrlCpvs;
  				$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => $this->ConnectionTimeOut));
	  			$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			case "authentication":{
		    	$client = new EHttpClient($this->UrlAuthenticate, array(
			    'maxredirects' => 0,
			    'timeout'      =>  $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			}
  			case "paymentsshort":
  			case "paymentshort":{
  				if($this->search)
  					$url = $this->UrlPaymentsShort.$this->search;
  				else 
  					$url = $this->UrlPaymentsShort;
  					
  				if(isset($this->org) && $this->search)
  					$url .="&org=".$this->org;
  				else if(isset($this->org))
  					$url .="?org=".$this->org;
  					
  				if(isset($this->limit))
  					$url .="&limit=".$this->limit;
  			 		
  					$client = new EHttpClient($url, array(
				    'maxredirects' => 0,
				    'timeout'      =>  $this->ConnectionTimeOut));
  				
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			}
  			case "contracts": {	
  				if($this->search){
  					$url = $this->UrlContractsItem.$this->search;
  					$client = new EHttpClient($url, array(
			  	  	'maxredirects' => 0,
			    	'timeout'      =>  $this->ConnectionTimeOut));
  				}else{
	  				$client = new EHttpClient($this->UrlContractsItem, array(
				    'maxredirects' => 0,
				    'timeout'      =>  $this->ConnectionTimeOut));
  				}
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			}
  			case "contractsshort": {	
  				if($this->search)
  					$url = $this->UrlContractsItem.$this->search;
  				else 	
  					$url = $this->UrlContractsShort;
  				if(isset($this->org) && $this->org!='')
  					$url .="&org=".$this->org;
  				if(isset($this->limit))
  					$url .="&limit=".$this->limit;
 				$client = new EHttpClient($url, array(
			  	  				'maxredirects' => 0,
			    				'timeout' =>  $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			}
  			case "contractsearch": {		
  				$url = $this->UrlContractsShort."&idlike=".$this->search;
  				$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => $this->ConnectionTimeOut));
	  			$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  					
  			}
  			case "contractsitem":
  				$url= $this->UrlContractsItem.$this->documentid;
  				$client = new EHttpClient($url, array(
			    'maxredirects' => 0,
			    'timeout'      =>  $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			case "contractcreate":
  				$url= $this->UrlContractsItem;
  				$client = new EHttpClient($url, array(
			    'maxredirects' => 0,
			    'timeout'      =>  $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			
  			case "procurementApproved":
  				{
  				$url = $this->UrlProcurement."?approvedfilter=approval";
  				if(isset($this->org) && $this->org!='')
  				$url .="&org=".$this->org;	
  				$client = new EHttpClient($url, array(
				  				'maxredirects' => 0,
				    			'timeout'=> $this->ConnectionTimeOut));
	  			
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  				}
  			case "procurementsearch": {		
  				$url = $this->UrlProcurement."?output=short&idlike=".$this->search;
  				$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => $this->ConnectionTimeOut));
	  			$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				//echo $url;
				break;
  					
  			}
  			case "procurementDownload":
  				$url= $this->UrlProcurementDown.$this->documentid;
  				$client = new EHttpClient($url, array(
			    'maxredirects' => 2,
			    'timeout'      => $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			case "procurement":	
  				$url= $this->UrlProcurement.$this->documentid;
  				$client = new EHttpClient($url, array(
  				'maxredirects' => 0,
			    'timeout'      => $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			case "procurementapproved":
  				$url= $this->UrlProcurementApprove.$this->documentid;
  				$client = new EHttpClient($url, array(
  				'maxredirects' => 0,
			    'timeout'      => $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
			
				break;
	  		case "contractDownload":
	  				$url= $this->UrlContractsDown.$this->documentid;
	  				$client = new EHttpClient($url, array(
				    'maxredirects' => 2,
				    'timeout'      => $this->ConnectionTimeOut));
					$client->setHeaders("Content-Type:application/xml");
					$client->setHeaders("Accept:application/xml");
					break;
		
  			case "paymentsitem":
  				//echo /contractId/6%CE%9A%CE%9D%CE%93%CE%95%CE%9D-5-%CE%A3404
  				
  				if(isset($this->contractid))
  				$url = $this->UrlPaymentsItem."?contractId=".$this->contractid;
  				else 
  				$url= $this->UrlPaymentsItem.$this->documentid;
  				$client = new EHttpClient($url, array(
			    'maxredirects' => 0,
			    'timeout'      => $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			case "paymentsDownload":
  				$url= $this->UrlPaymentsDownload.$this->documentid;
  				$client = new EHttpClient($url, array(
				    'maxredirects' => 2,
				    'timeout'      =>  $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
				
			/** RFPS **/
				case "rfp": {	
  				if($this->search){
  					$url = $this->UrlRfpItem.$this->search;
  					$client = new EHttpClient($url, array(
			  	  	'maxredirects' => 0,
			    	'timeout'      =>  $this->ConnectionTimeOut));
  				}else{
  					$url = $this->UrlRfpItem;
  					$client = new EHttpClient($url, array(
				    'maxredirects' => 0,
				    'timeout'      =>  $this->ConnectionTimeOut));
  				}
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			}
  			case "rfpshort": {	
  				if($this->search)
  					$url = $this->UrlRfpList."?idlike=".$this->search;
  				else 
  					$url = $this->UrlRfpShort;
  				if(isset($this->org))
  					$url .="&org=".$this->org;
  				if(isset($this->limit))
  					$url .="&limit=".$this->limit;
   				$client = new EHttpClient($url, array(
				    'maxredirects' => 0,
				    'timeout'      => $this->ConnectionTimeOut));
	  			
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;	
  				
	  		}
  			case "rfpsearch": {		
  				$url = $this->UrlRfpShort."&idlike=".$this->search;
  				$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => $this->ConnectionTimeOut));
	  			$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  					
  			}
  			case "RFPitem":
  			case "rfpitem":
  				$url= $this->UrlRfpItem.$this->documentid;
  				
  				$client = new EHttpClient($url, array(
			    'maxredirects' => 0,
			    'timeout'      =>  $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
  			case "rfpcreate":
  				$url= $this->UrlRfpItem;
  				$client = new EHttpClient($url, array(
			    'maxredirects' => 0,
			    'timeout'      =>  $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
			case "rfpdownload":
  				$url= $this->UrlRfpDown.$this->documentid;
  				$client = new EHttpClient($url, array(
			    'maxredirects' => 2,
			    'timeout'      => $this->ConnectionTimeOut));
				$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				break;
	  		
  		}

  		if(!isset($client)) return -1;
  	
  		if($this->apirequest!='decisions')
  		if(isset($this->username) && isset($this->password))
  			$client->setAuth($this->username, $this->password);
		$response = $client->request();	
  		$Status = $response->getStatus();
		$Message = $response->getMessage();
		
		//Yii::log('makeRequest output',$response->getBody());
		Yii::log('makeRequest info',$Status );
		if($Status==200 && $Message=='OK'){
			$output =  $response->getBody();	
			return $output;
		}
		
 				
 				return $Status;

  }
  
  /**
   * This api function is responsible for the api search request.
   * The search parameters are ada, title, cpv.
   * It searchs with string like ada and title
   * and for a specific cpv. 
   */
   function search(){
   	
   
   		if($this->ada=='' && $this->title=='' && $this->cpv=='') return;
   		switch($this->apirequest){
   			case "rfp":
				$url = $this->UrlRfpShort;   			
   				break;
   			case "procurement":
				$url = $this->UrlProcurement."?output=short";   			
   				break;
   			case "contract":
   				$url = $this->UrlContractsShort;
   				break;
   			case "payments":
				$url = $this->UrlPaymentsShort."?output=short";   			
   				break;
   		}
   			if(isset($this->ada) && $this->ada!='')
   				$url.="&idlike=".$this->ada;
   			if(isset($this->title) && $this->title!='')
   				$url.="&titlelike=".$this->title;
   			if(isset($this->cpv) && $this->cpv!='')
   				$url.="&cpv=".$this->cpv."&";
   			if(isset($this->org) && $this->org!='')
   				$url.="&org=".$this->org."&";
   				Yii::log('search url',$url );

   			
  				$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => 300));
	  			$client->setHeaders("Content-Type:application/xml");
				$client->setHeaders("Accept:application/xml");
				
   		
   		//if(isset($this->ada) || isset($this->title) || isset($this->cpv)){
	   		if(isset($this->username) && isset($this->password))
	  			$client->setAuth($this->username, $this->password);
			$response = $client->request();	
	  		$Status = $response->getStatus();
			$Message = $response->getMessage();
			Yii::log('search info',$Status );
			
			if($Status==200 && $Message=='OK'){
				$output =  $response->getBody();
				Yii::log('search output',$output);
				
				return $output;
			}
   		//}
		
 				
 				return $Status;
   		
   }
  	
  	/**
  	 * This fuction creates an item.
  	 * The items are 
  	 * a) procurement, b) contract, c) payment.
  	 * The necessary data are defined in the config file with 
  	 * a) username, b)password, c)data d)type of item
   	 * @param string $type the item type
  	 */
  	public function create($type){

		if($type=='procurement')
			$URL = $this->UrlProcurement;
		if($type=='contract')
			$URL = $this->UrlContractsItem;
		if($type=='payment')
			$URL = $this->UrlPaymentsItem;
		if($type=='rfp')
			$URL = $this->UrlRfpItem;
	
		
		/*$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
		$WriteToFile = $Path."protected/data/files/final.xml";
		$fh = fopen($WriteToFile, 'w') or die("can't open file");
		file_put_contents($WriteToFile, $this->data);
	*/
			
		Yii::log('create',$type);
		$putData = tmpfile(); 
		$putString = $this->data;
		fwrite($putData, $putString); 
		fseek($putData, 0); 
		$ch = curl_init();
		//Yii::log('create data',$this->data);
		
		//echo $this->data;
		$ch = curl_init($URL);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_URL, $URL);
		curl_setopt($ch, CURLOPT_VERBOSE, 1); // set url to post to 
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/xml','Accept:application/xml'));
		curl_setopt($ch, CURLOPT_USERPWD, Yii::app()->user->UserName.":".Yii::app()->user->password);
		curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
		curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $this->data);
		$output = curl_exec($ch);
		$info = curl_getinfo($ch);
		
		//print_r($output);
		print_r($info);
		Yii::log('create output',$output);
		Yii::log('create info',$info['http_code'] );
		if($info['http_code']=='200')
			$output = $this->Success($output);

		if($info['http_code']=='403'){
			$data[0]['error'][0] = '403';
			return $data;
		} 
  		if($info['http_code']=='400'){
			$data[0]['error'][0] = '400';
			return $data;
		}
  		if($info['http_code']=='404'){
			$data[0]['error'][0] = '404';
			return $data;
		}
		if($info['http_code']=='500'){
			$data[0]['error'][0] = '500';
			return $data;
		}
		return $output;
				
  	}
  	/**
	 * This is the update function of procurement, contract
	 * and payment. The user has to insert a reason for deleting
	 * a file.  
	 * @param string $type the type of the item 
 	 */
	function update($type){
		
		if($type=='procurement')
			$URL = $this->UrlProcurement.$this->documentid;
		if($type=='rfp')
			$URL = $this->UrlRfpItem.$this->documentid;
		if($type=='contract')
			$URL = $this->UrlContractsItem.$this->documentid;
		if($type=='payment')
			$URL = $this->UrlPaymentsItem.$this->documentid;

		
		Yii::log('update',$type);
		//echo $this->documentid;
		$putData = tmpfile(); 
		$putString = $this->data;
		fwrite($putData, $putString); 
		fseek($putData, 0); 
		$ch = curl_init();
		$CURLOPT_USERPWD = $this->username.":".$this->password;
		//Yii::log('update data',$this->data);
		Yii::log('update user',$CURLOPT_USERPWD);
		//echo $this->data;

		/*$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
		$WriteToFile = $Path."protected/data/files/final.xml";
		$fh = fopen($WriteToFile, 'w') or die("can't open file");
		file_put_contents($WriteToFile, $this->data);*/
		
		curl_setopt($ch, CURLOPT_URL, $URL);
		curl_setopt($ch, CURLOPT_INFILE, $putData); 
		curl_setopt($ch, CURLOPT_INFILESIZE, $this->filesize); 
		curl_setopt($ch, CURLOPT_PUT, TRUE);
			
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/xml','Accept:application/xml'));
	    curl_setopt($ch, CURLOPT_USERPWD, $this->username.":".$this->password);
		curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
		$output = curl_exec($ch);	
		$info = curl_getinfo($ch);
		//print_r($output);
		//print_r($info);
		Yii::log('update output',$output);
		Yii::log('update info',$info['http_code']);
	
		if($info['http_code']=='200')
			$output = $this->Success($output);
		if($info['http_code']=='403'){
			$data[0]['error'][0] = '403';
			return $data;
		}
		if($info['http_code']=='404'){
			$data[0]['error'][0] = '404';
			return $data;
		}
		if($info['http_code']=='400'){
			$data[0]['error'][0] = '400';
			return $data;
		}
  		if($info['http_code']=='500'){
			$data[0]['error'][0] = '500';
			return $data;
		}
		//else 
		//	return -1;
		curl_close($ch);
		fclose($putData);
		return $output;
	}
	
	/*
	 * Function responsible for getting the short list of each type
	 * Unfortunately i cant use the short type for procurement because
	 * i need the approvesRequest tag for approval type procurements.
	 */
	
	public function short(){

			if($this->apirequest=='procurement'){
				if($this->search=='approval')
				$url = $this->UrlProcurement."?output=short1";
				else 
					$url = $this->UrlProcurement."?output=short";
			}
			if($this->apirequest=='rfp')
				$url = $this->UrlRfpShort;
			if($this->apirequest=='contract')
				$url = $this->UrlContractsShort;
			if($this->apirequest=='payments')
				$url = $this->UrlPaymentsShort."?output=short";
			
			if($this->apirequest=='procurement'){
				if($this->search && $this->name=='search')
	  				$url .="&idlike=".$this->search;
	  			else if($this->search && $this->search!='' && $this->name!='search')
	  				$url .="&approvedfilter=".$this->search;
	  			else if($this->apirequest=='procurement')
	  				$url .="&approvedfilter=notapproved";
			}else{
				if($this->search)
					$url.="&idlike=".$this->search;	
			}
				
			if(isset($this->org))
			$url = $url.'&org='.$this->org;
			if(isset($this->limit))
			$url = $url.'&limit='.$this->limit;
			
			
				$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => $this->ConnectionTimeOut));
	  		$client->setHeaders("Content-Type:application/xml");
			$client->setHeaders("Accept:application/xml");
			
			if(isset($this->username) && isset($this->password))
  				$client->setAuth($this->username, $this->password);
			$response = $client->request();	
  			$Status = $response->getStatus();
			$Message = $response->getMessage();
			if($Status==200 && $Message=='OK'){
				$output =  $response->getBody();
				return $output;
			}else 
				return $Status;
		
			
	}
	/**
	 * This is a function for getting the numner of procurement, contract,rfp
	 * and payment. 
	 * @param string $type the type 
 	 */
	function number($type){
			if($type=='procurement')
				$url = $this->UrlProcurement.'?limit=0&approvedfilter=approval';
			if($type=='rfp')
				$url = $this->UrlRfpItem.'?limit=0';
			if($type=='contract')
				$url = $this->UrlContractsItem.'?limit=0';
			if($type=='payment')
				$url = $this->UrlPaymentsItem.'?limit=0';

			if(isset($this->org))
			$url = $url.'&org='.$this->org;
			$client = new EHttpClient($url, array(
				'maxredirects' => 0,
				'timeout'      => $this->ConnectionTimeOut));
	  		$client->setHeaders("Content-Type:application/xml");
			$client->setHeaders("Accept:application/xml");
			
			if(isset($this->username) && isset($this->password))
  				$client->setAuth($this->username, $this->password);
			$response = $client->request();	
  			$Status = $response->getStatus();
			$Message = $response->getMessage();
			if($Status==200 && $Message=='OK'){
				$output =  $response->getBody();
				return $output;
			}else 
				return 1;	
		
			
			
	}
	/**
	 * This is the delete function of procurement, rfp,contract
	 * and payment. The user has to insert a reason for deleting
	 * a file.  
	 * @param string $documentid the documentid,
 	 */
	function delete($documentid,$type){
			if($type=='procurement')
				$url = $this->UrlProcurement.$documentid.'?reason='.urlencode($this->reason);
			if($type=='rfp')
				$url = $this->UrlRfpItem.$documentid.'?reason='.urlencode($this->reason);
			if($type=='contract')
				$url = $this->UrlContractsItem.$documentid.'?reason='.urlencode($this->reason);
			if($type=='payment')
				$url = $this->UrlPaymentsItem.$documentid.'?reason='.urlencode($this->reason);
			Yii::log('delete type',$type);
				
			$url.="&deletionType=1";
			
			$ch = curl_init($url);
			curl_setopt($ch, CURLOPT_URL, $url);
			$CURLOPT_USERPWD = $this->username.":".$this->password;
			Yii::log('delete user',$CURLOPT_USERPWD);
			curl_setopt($ch, CURLOPT_USERPWD, $CURLOPT_USERPWD);
			curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			//curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/xml','Accept:application/xml'));
			//curl_setopt ($ch, CURLOPT_USERAGENT, "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)");
			curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "DELETE");
			curl_setopt ($ch, CURLOPT_HEADER, 1);
			//curl_setopt ($ch, CURLOPT_FAILONERROR, 1);
			//curl_setopt ($ch, CURLOPT_SSL_VERIFYPEER, 0);
			curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
     	   	//curl_setopt($ch, CURLOPT_HEADER, 0);
        	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
			$output = curl_exec($ch);
			//print_r($output);
			//echo"<br/>";
			$info = curl_getinfo($ch);
			//print_r($info);
			Yii::log('delete output',$output);
			Yii::log('delete info',$info['http_code']);
			//echo $output;
			if($info['http_code']===200) return 1;
			else 
			return $info['http_code'];
			//else return 1;
			
	}
	
/**
	 * This is the delete function of procurement, rfp,contract
	 * and payment. The user has to insert a reason for deleting
	 * a file.  
	 * @param string $documentid the documentid,
 	 */
	function cancel($documentid,$type){
			if($type=='procurement')
				$url = $this->UrlProcurement.$documentid.'?reason='.urlencode($this->reason);
			if($type=='rfp')
				$url = $this->UrlRfpItem.$documentid.'?reason='.urlencode($this->reason);
			if($type=='contract')
				$url = $this->UrlContractsItem.$documentid.'?reason='.urlencode($this->reason);
			if($type=='payment')
				$url = $this->UrlPaymentsItem.$documentid.'?reason='.urlencode($this->reason);
			Yii::log('cancel type',$type);
				
			$url.="&deletionType=".$this->cancellationType;
			
			$ch = curl_init($url);
			curl_setopt($ch, CURLOPT_URL, $url);
			$CURLOPT_USERPWD = $this->username.":".$this->password;
			Yii::log('cancel user',$CURLOPT_USERPWD);
			curl_setopt($ch, CURLOPT_USERPWD, $CURLOPT_USERPWD);
			curl_setopt($ch, CURLOPT_HTTPAUTH, CURLAUTH_BASIC);
			curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "DELETE");
			curl_setopt($ch, CURLOPT_HTTPHEADER, array('X-CANCEL: 1'));
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
			$output = curl_exec($ch);
			$info = curl_getinfo($ch);
			
			Yii::log('cancel info',$info['http_code']);
			if($info['http_code']===200) return 1;
			else return $info['http_code'];

	}
	
	public function Success($output){
		
		$success = "";
		$xml = new SimpleXMLElement($output);
		
		$xml->registerXPathNamespace('a',$this->SchemaCommon);
		
		$ReqTitle = $xml->xpath('//a:id');
		if(isset($ReqTitle[0]))
			$success[0]['id'] = (string)$ReqTitle[0];
		else 
			$success[0]['id'] =-1;
		
		$Error = $xml->xpath('//a:field');
		$Xpath = $xml->xpath('//a:xpath');
		
		//print_r($Xpath);
		if(isset($Error[0])){
			$numOfErrors = count($Error);
			
				for($i=0;$i<$numOfErrors;$i++){
					if((string)$Error[$i][0]=='invalidAfm'){
						if(strstr((string)$Xpath[$i][0],'PrimaryParty'))
							$success[0]['error'][$i] ='invalidAfmForea';
							if(strstr((string)$Xpath[$i][0],'contractParty'))
							$success[0]['error'][$i] ='invalidAfmParty';	
						if(strstr((string)$Xpath[$i][0],'SecondaryParties'))
							$success[0]['error'][$i] ='invalidAfmParty';
						
			/*		if((string)$Error[$i][0]=='invalidRelatedAda'){
						if(strstr((string)$Xpath[$i][0],'relatedAda[1]'))
							$success[0]['error'][$i] ='ADAkatakurosis';
						else if (strstr((string)$Xpath[$i][0],'relatedAda[0]'))
							$success[0]['error'][$i] ='ADAdiakiriksis';
						else if (strstr((string)$Xpath[$i][0],'relatedAda[2]'))
							$success[0]['error'][$i] ='ADAanathesis';*/
					}else
					$success[0]['error'][$i] = (string)$Error[$i][0];
					
				}
			//}else
			//	$success[0]['error'][0] = (string)$Error[0];
		}
		return $success;
	}
  
}
