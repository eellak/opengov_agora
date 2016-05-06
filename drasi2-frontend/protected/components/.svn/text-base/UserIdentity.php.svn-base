<?php

/**
 * UserIdentity represents the data needed to identity a user.
 * It contains the authentication method that checks if the provided
 * data can identity the user.
 */
class UserIdentity extends CUserIdentity
{
	CONST ERROR_CONNECTION='Δεν είναι δυνατή η σύνδεση';
	private $_id;
	/**
	 * Authenticates a user.
	 * It is based on the Apiconnector. It makes an http request to 
	 * the api with Apiconnector and get the result either an xml with 
	 * the users data or -1 if the authentication fails.
	 * When the xml is fetched the Accountapidata parses it and gets 
	 * the necessary values.
	 * The values are set to session with the setState function. 
	 * @return boolean whether authentication succeeds.
	 */
	public function authenticate()
	{
		
		$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
		include($Path.'/protected/commands/RbacCommand.php');
		$auth = Yii::app()->authManager;
	//	$script = new RbacCommand();
	//	$script->run();
		if(!isset($this->username))
			$this->errorCode=self::ERROR_USERNAME_INVALID;
		if(!isset($this->password))
			$this->errorCode=self::ERROR_PASSWORD_INVALID;
		
		//sets the config table for the http request. 
		$config['username']=$this->username;
		$config['password']=$this->password;
		$config['apirequest'] ='authentication';
		//calls the connector in order to get 
		//the users data. 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
			
		if ($output=='401') $this->errorCode=$output;
      	else if ($output=='404' || $output=='504' ||  $output=='500' || $output=='502' || $output=='501' || $output==-1) $this->errorCode=$output;
		else{
			//gets the xml and by parsing with xpath the xml it sets 
			//the necessary values.
			$UserAccount= new Accountapidata();
			$UserAccount->setCurlData($output);
			//echo $output;
			   	$this->_id = $UserAccount->UserName;
				
			   	$this->setState('RefId',$UserAccount->RefId);
				$this->setState('validUser',$UserAccount->validUser);
				$this->setState('UserName',$UserAccount->UserName);
				$this->setState('email',$UserAccount->email);
				$this->setState('role',$UserAccount->role);
				$UserAccount->isAdmin = (strcmp($UserAccount->isAdmin,"true")==-1)?'false':'true';
				
				$auth=Yii::app()->authManager;
				if($UserAccount->role=='admin'){
					$UserAccount->isAdmin='true';
					$this->setState('isAdmin',true);
			        if(!$auth->isAssigned('superadmin',$this->_id))
			        {
			            if($auth->assign('superadmin',$this->_id))
			               Yii::app()->authManager->save();
			           
			        }
				}else if($UserAccount->role=='admin_foreas'){
					$UserAccount->isAdmin='true';
					$this->setState('isAdmin',true);
			        if(!$auth->isAssigned('admin',$this->_id))
			        {
			            if($auth->assign('admin',$this->_id))
			               Yii::app()->authManager->save();
			           
			        }
				}else if($UserAccount->role=='user'){ 
		
					$this->setState('isAdmin',true);
					if(!$auth->isAssigned('authenticated',$this->_id))
			        {
			            if($auth->assign('authenticated',$this->_id))
			            {
			                Yii::app()->authManager->save();
			            }
			     }
				}
				$this->setState('firstName',$UserAccount->firstName);
				$this->setState('password',$this->password);
				$this->setState('lastName',$UserAccount->lastName);
				$this->setState('OrganisationName',$UserAccount->OrganisationName);
				
				$this->setState('OrganisationAfm',$UserAccount->OrganisationAfm);
				$this->setState('OrganisationAddress',$UserAccount->OrganisationAddress);
				$this->setState('OrganisationAddressNo',$UserAccount->OrganisationAddressNo);
				$this->setState('OrganisationAddressPostal',$UserAccount->OrganisationAddressPostal);
				$this->setState('OrganisationFullAddress',$UserAccount->OrganisationFullAddress);
				$this->setState('OrganisationType',$UserAccount->OrganisationType);
				
				//$this->__admin = 'true';
	//			echo $this->getState('isAdmin');
	
				$this->errorCode=self::ERROR_NONE;
			
		} 
	return !$this->errorCode;
	
	}
	
	
	/*
	 *override the cUserIdentity::getId in order to send 
	 *the username as id
	 * @return string the username of the authenticated user.
	 */
	public function getId(){
		return $this->_id;
	}
		
}
