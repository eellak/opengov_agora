<?php
Yii::import('ext.httpclient.*');
Yii::import('ext.httpclient.adapter.*');

/**
 * This is the class for getting the opendata data.
 * It uses the httpclient to connect to opendata.
 * It is mainly used for Units & Signers. 
 * It stores the fetched values to memory. 
 * @author themiszamani
 * @version 1.0
 * @package agora  
 */
class Units extends CActiveRecord
{
	
	const _URL_OPENDATA = 'http://opendata.diavgeia.gov.gr/api/';
	const _URL_OPENDATA_DESICIONS = 'http://opendata.diavgeia.gov.gr/api/desicions';
	const _URL_OPENDATA_ORGANIZATION = 'http://opendata.diavgeia.gov.gr/api/organizations';
	const _URL_OPENDATA_UNITS = 'http://opendata.diavgeia.gov.gr/api/units';
	const _URL_OPENDATA_TAGS = 'http://opendata.diavgeia.gov.gr/api/tags';
	const _URL_OPENDATA_TYPES = 'http://opendata.diavgeia.gov.gr/api/types';
	const _URL_OPENDATA_SIGNER = 'http://opendata.diavgeia.gov.gr/api/signers/';
	const _URL_OPENDATA_SCHEMA = 'http://diavgeia.gov.gr/schema/model/diavgeia-decision-0.1';
	const _URL_UNITS = 'http://83.212.121.173:8080/agora/taxonomy/units/';
	const _URL_SIGNERS = 'http://83.212.121.173:8080/agora/taxonomy/signers/';
	const _URL_ORGANIZATION = 'http://83.212.121.173:8080/agora/taxonomy/oda/';
	
	private static $_items=array();
 
	public function  init(){
        $this->layout = '//layouts/column2PerOrg';
        
         Yii::app()->setComponents(array(
            'errorHandler'=>array(
            'errorAction'=>'organizations/error',
        ),));
    }
	/**
	 * Load the full list of a selected type code organization id.
	 * It calls the oadItems and then select the value to 
	 * return. 
	 * @param string $type the type Units, Signers
	 * @param integer $refid the Organization id
	 */
   public static function items($type,$refid)
    {
    	
    	if(!isset(self::$_items[$type]) || !array_key_exists($type,self::$_items[$type]))
            self::loadItems($type,$refid);
            
        //return self::$_items[$type];
        return isset(self::$_items[$type]) ? self::$_items[$type] : false;
       }
    
    /**
	 * Load the full list of Signers of a unit.
	 * It calls the loadItemsSigners and then select the value to 
	 * return. 
	 * @param string $type the type Signers
	 * @param integer $refid the Unit id
	 */
 	public static function itemsSigners($type,$refid)
    {
    	if(!isset(self::$_items[$type]))
            self::loadItemsSigners($type,$refid);
        return self::$_items[$type];
    }
    /**
     * Returns the model values
     * @param class  $className the class values
     */
	public static function model($className=__CLASS__)
	{
	  return parent::model($className);
	}
  
	/**
	 * Load the item of a selected type code and id.
	 * It calls the loadItems and then select the value to 
	 * return. If no item found the false is returned.
	 * @param string $type the type Units, Signers
	 * @param integer $code the Organization id
	 * @param integer $refid the Unit id when refered to Signers type
	 */
  	public static function item($type,$code,$refid)
    {
        if(!isset(self::$_items[$type][$code])){
            self::loadItems($type,$refid);
        }
        
        return isset(self::$_items[$type][$code]) ? self::$_items[$type][$code] : false;
    }
    
  /**
   * This function is responsible for the Signers opendata model. 
   * It returns an array with a list of the data of signers of the selected
   * unit id , 
   * @param string $type the type Units, Signers
   * @param integer $refid the Unit id when refered to Signers type
   * @return array an array with the data of the signers.
   */
	private static function loadItemsSigners($type,$refid)
    {
    	//$Url =($refid!='')?self::_URL_OPENDATA_SIGNER."?org=".Yii::app()->user->RefId."&unit=".$refid:self::_URL_OPENDATA_SIGNER;  
    	$Url =($refid!='')?self::_URL_SIGNERS."?item=".Yii::app()->user->RefId."&unit=".$refid:self::_URL_SIGNERS;        
		//$SignersName = 	Yii::app()->request->hostInfo."".Yii::app()->request->baseUrl."/protected/data/ajax/signers.xml";
    	//$Url =($refid!='')?$SignersName."?org=".Yii::app()->user->RefId."&unit=".$refid:$SignersName;        
		
    	$client = new EHttpClient($Url, array(
			'maxredirects' => 1,
	      	'keepalive'       => true,
		    'timeout'      => 30));
	    $client->setConfig(array('strictredirects' => true));	
	      	
		$client->setHeaders("Content-Type:application/xml");
		$client->setHeaders("Accept:application/xml");
		$response = $client->request();	
		if($response->isSuccessful()){		
			$Status = $response->getStatus();
			$Message = $response->getMessage();				
			//successful request
			if($Status==200 && $Message=='OK'){
				$output =  $response->getBody();
				$xml = new SimpleXMLElement($output);
				//$xml->registerXPathNamespace('a',self::_URL_OPENDATA_SCHEMA);
					$xml->registerXPathNamespace('a','http://agora.opengov.gr/schema/common-0.1');
				
				$tags = $xml->xpath("//a:signer");
				$tagsLabelFirst = $xml->xpath("//a:signer/a:firstName");
				$tagsLabelLast = $xml->xpath("//a:signer/a:lastName");
				$tagsLabelTrue = $xml->xpath("//a:signer/a:active");
				$tagsLabelPosition = $xml->xpath("//a:signer/a:position");
				if(count($tags)>0){
					for($j=0;$j<count($tags);$j++){
						//there are null values in the database or only in the xml so i have to check for null values.
					    if($tagsLabelFirst[$j][0]=="" ||  $tagsLabelTrue[$j][0]=='false') continue;
					    
					    //check if the signer has a Position, else type the name
					    if($tagsLabelPosition[$j][0]!="")
							self::$_items[$type][(int)$tags[$j]['uid']]="".$tagsLabelPosition[$j][0].": ".$tagsLabelFirst[$j][0]." ".$tagsLabelLast[$j][0]." ";
					    else
							self::$_items[$type][(int)$tags[$j]['uid']]="".$tagsLabelFirst[$j][0]." ".$tagsLabelLast[$j][0]."";
					}
					       
				}else {
					$value='--'.YII::t('form','No Signers exist')."--";
					self::$_items[$type][0]=$value;	
							return false;
	  			}//end else there results
			}//end successful request
				
		}else //else not successful response
			throw new CHttpException(Yii::t('yii','timeout'),Yii::t('yii','timeoutDesc'));
		
    }
    
  /**
   * This function is responsible for the opendata model. 
   * It returns an array with a list of the data of the type selected, 
   * The type may be (Units, Signers)
   * @param string $type the type Units, Signers
   * @param integer $refid the Unit id when refered to Signers type
   * @return array an array with the data of the type selected.
   */
  private static function loadItems($type,$refid)
    {
    	
   		$filename =  Yii::app()->request->hostInfo."".Yii::app()->request->baseUrl."/documents/ajax/organizations.xml";
   		$Orgs =  Yii::app()->request->hostInfo."".Yii::app()->request->baseUrl."/documents/ajax/oda.xml";
   		$Unitsname =  Yii::app()->request->hostInfo."".Yii::app()->request->baseUrl."/documents/ajax/units.xml";
    	$SignersName = 	Yii::app()->request->hostInfo."".Yii::app()->request->baseUrl."/documents/ajax/signers.xml";
    	$Url='';
   		if($type=='Organization')
    		$Url = $Orgs;
    	if($type=='Units')
      		$Url =($refid!='')?self::_URL_UNITS."?item=".$refid:self::_URL_UNITS;       

      	if($type=='Signers')
      		$Url =($refid!='')?self::_URL_SIGNERS."?item=".$refid:self::_URL_SIGNERS;  
        
      	if( $type=='OrganizationLabel' || $type=='OrganizationFull' 
      		|| $type=='OrganizationYpText1' 
      		|| $type=='OrganizationYp' || $type=='OrganizationPer' || $type=='OrganizationYpTextDrop'
      		|| $type=='OrganizationDhmoi')
      		$Url =self::_URL_UNITS."?item=".$refid;
      	if ($type=='OrganizationName')   
      		$Url =self::_URL_UNITS."?item=".$refid;
		if($type=='OrganizationId')
    		$Url = $Orgs;
				
		if($type!='OrganizationYpText')	{
      		$client = new EHttpClient($Url, array(
		    'maxredirects' => 10,
	      	'keepalive'       => false,
		    'timeout'      => 30));
	      	$client->setConfig(array('strictredirects' => true));	
	      	
			$client->setHeaders("Content-Type:application/xml");
			$client->setHeaders("Accept:application/xml");
			$response = $client->request();	
			if($response->isSuccessful()){		
				$Status = $response->getStatus();
				$Message = $response->getMessage();				
				if($Status==200 && $Message=='OK'){
					$output =  $response->getBody();
		
					$xml = new SimpleXMLElement($output);
					$xml->registerXPathNamespace('a','http://agora.opengov.gr/schema/common-0.1');
					$xml->registerXPathNamespace('b',self::_URL_OPENDATA_SCHEMA);
						if($type=='Signers'){
							$tags = $xml->xpath("//a:signer");
							$tagsLabelFirst = $xml->xpath("//a:signer/a:firstName");
							$tagsLabelLast = $xml->xpath("//a:signer/a:lastName");
							$tagsLabelTrue = $xml->xpath("//a:signer/a:active");
							$tagsLabelPosition = $xml->xpath("//a:signer/a:position");
							if(count($tags)>0){
							for($j=0;$j<count($tags);$j++){
									//there are null values in the database or only in the xml so i have to check for null values.
						        	if($tagsLabelFirst[$j][0]=="" ||  $tagsLabelTrue[$j][0]=='false') continue;
						        	//check if the signer has a Position, else type the name
						        	if($tagsLabelPosition[$j][0]!="")
										self::$_items[$type][(int)$tags[$j]['uid']]="".$tagsLabelPosition[$j][0].": ".$tagsLabelFirst[$j][0]." ".$tagsLabelLast[$j][0]." ";
						        	else
										self::$_items[$type][(int)$tags[$j]['uid']]="".$tagsLabelFirst[$j][0]." ".$tagsLabelLast[$j][0]."";
						    }
						       
							}else {
								$value='--'.YII::t('form','No AFM exist')."--";
								self::$_items[$type][0]=$value;	
								return false;
		  					}
						}
						
						if( $type=='OrganizationName'){
							$tagsLabel = $xml->xpath("//a:item[@id=$refid]/a:label");
							if(isset($tagsLabel[0][0]) && $tagsLabel[0][0]!='')
							self::$_items[$type][$refid]=(string)$tagsLabel[0][0];
					        return self::$_items[$type][$refid]=(string)$tagsLabel[0][0];
						}
						
						if( $type=='Units'){
							$tags = $xml->xpath("//a:item[@id=$refid]/a:units/a:unit[@idRef]");
							$tagsLabel = $xml->xpath("//a:item[@id=$refid]/a:units/a:unit/a:label");
							if(count($tags)>0){
								for($j=0;$j<count($tags);$j++){
						        	if($tagsLabel[$j][0]!='')
						        	self::$_items[$type][(int)$tags[$j]['idRef']]=(string)$tagsLabel[$j][0];
							}
							
							}else return FALSE;
								
						}
						if( $type=='Organization'){
							$tagsLabel = $xml->xpath("//a:item[@id=$refid]/a:label");
							self::$_items[$type][$refid]=(string)$tagsLabel[0][0];
							
						}
					if( $type=='OrganizationId'){
							//$tagsLabel = $xml->xpath("//a:item[@id]/a:label");
							$tagsLabel = $xml->xpath("//a:item[a:latinName='$refid']");
							
							if(isset($tagsLabel[0]['id']) && $tagsLabel[0]['id']!='')
							self::$_items[$type][$refid]=(int)$tagsLabel[0]['id'];
					     /*   
							$tagsLabel = $xml->xpath("//b:organization[b:latinName='$refid']");
                            if(isset($tagsLabel[0]['uid']) && $tagsLabel[0]['uid']!='')
								self::$_items[$type][$refid]=(int)$tagsLabel[0]['uid'];
                          */ 
						
						}
					if( $type=='OrganizationLabel'){
							$tagsLabel = $xml->xpath("//a:item[@id=$refid]/a:latinName");
							if(isset($tagsLabel[0][0]) && $tagsLabel[0][0]!='')
							self::$_items[$type][$refid]=(string)$tagsLabel[0][0];
					        
						}
				
					if( $type=='OrganizationDhmoi'){
					 		$start=6001; $end=6325;
							$tags = $xml->xpath("//a:organization[@uid>$start and @uid<$end]");
							$tagsLatin = $xml->xpath("//a:organization[@uid>$start and @uid<$end]/a:latinName");
							$tagsLabel = $xml->xpath("//a:organization[@uid>$start and @uid<$end]/a:label");
							
							for($j=0;$j<count($tags);$j++){
					        	if($tagsLabel[$j][0]!=''){
					        	self::$_items[$type][(int)$tags[$j]['uid']]['title']=(string)$tagsLabel[$j][0];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['short']=(string)$tagsLatin[$j][0];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['id']=(int)$tags[$j]['uid'];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['class']='greyBg';
					        	
					        	}
					       	}
						}
					if( $type=='OrganizationPer'){
							$start=5001; $end=5013;
							$tags = $xml->xpath("//a:organization[@uid>$start and @uid<$end]");
							$tagsLatin = $xml->xpath("//a:organization[@uid>$start and @uid<$end]/a:latinName");
							$tagsLabel = $xml->xpath("//a:organization[@uid>$start and @uid<$end]/a:label");
							
							for($j=0;$j<count($tags);$j++){
					        	if($tagsLabel[$j][0]!=''){
					        	self::$_items[$type][(int)$tags[$j]['uid']]['title']=(string)$tagsLabel[$j][0];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['short']=(string)$tagsLatin[$j][0];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['id']=(int)$tags[$j]['uid'];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['class']='blueBg';
					        	
					        	}
					       	}
							
						}
					if( $type=='OrganizationYp'){
							$start=3; 
							$end=31;
							$tags = $xml->xpath("//a:organization[@uid>$start and @uid<$end]");
							$tagsLatin = $xml->xpath("//a:organization[@uid>$start and @uid<$end]/a:latinName");
							$tagsLabel = $xml->xpath("//a:organization[@uid>$start and @uid<$end]/a:label");
							
							for($j=0;$j<count($tags);$j++){
					        	if($tagsLabel[$j][0]!=''){
					        	self::$_items[$type][(int)$tags[$j]['uid']]['title']=(string)$tagsLabel[$j][0];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['short']=(string)$tagsLatin[$j][0];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['id']=(int)$tags[$j]['uid'];
					        	self::$_items[$type][(int)$tags[$j]['uid']]['class']='blueBg';
					        	
					        	}
					       	}
					       	
					   }
									
			}else //if($Status==200 && $Message=='OK')
				throw new CHttpException(Yii::t('yii','timeout'),$type ."::Not Status :: ".Yii::t('yii','timeoutDesc'));
			
	    }else //ifresponse->isSuccessful()
	    		throw new CHttpException(Yii::t('yii','timeout'),$type ."::Not Successful  :: ".Yii::t('yii','timeoutDesc'));
      	}//type!=OrganizationYpText 
      
      	else if( $type=='OrganizationYpText'){
							self::$_items[$type][0]['title']='Διοικητικής Μεταρρύθμισης και Ηλεκτρονικής Διακυβέρνησης';
				        	self::$_items[$type][0]['short']='min-reform-egov';
				        	self::$_items[$type][0]['id']=31;
				        	self::$_items[$type][0]['class']='blueBg';
				        	self::$_items[$type][1]['title']='Εσωτερικών';
				        	self::$_items[$type][1]['short']='min-interior';
				        	self::$_items[$type][1]['id']=30;
				        	self::$_items[$type][1]['class']='blueBg';
				        	self::$_items[$type][2]['title']='Οικονομικών';
				        	self::$_items[$type][2]['short']='minfin';
				        	self::$_items[$type][2]['id']=15;
				        	self::$_items[$type][2]['class']='blueBg';
				        	self::$_items[$type][3]['title']='Εξωτερικών';
				        	self::$_items[$type][3]['short']='mfa';
				        	self::$_items[$type][3]['id']=10;
				        	self::$_items[$type][3]['class']='blueBg';
				        	self::$_items[$type][4]['title']='Εθνικής Άμυνας';
				        	self::$_items[$type][4]['short']='ypetha';
				        	self::$_items[$type][4]['id']=6;
				        	self::$_items[$type][4]['class']='blueBg';
				        	self::$_items[$type][5]['title']='Ανάπτυξης, Ανταγωνιστικότητας και Ναυτιλίας';
				        	self::$_items[$type][5]['short']='ypean';
				        	self::$_items[$type][5]['id']=18;
				        	self::$_items[$type][5]['class']='blueBg';
				        	self::$_items[$type][6]['title']='Περιβάλλοντος, Ενέργειας και Κλιματικής Αλλαγής';
				        	self::$_items[$type][6]['short']='ypeka';
				        	self::$_items[$type][6]['id']=16;
				        	self::$_items[$type][6]['class']='blueBg';
				        	self::$_items[$type][7]['title']='Παιδείας, Δια Βίου Μάθησης και Θρησκευμάτων';
				        	self::$_items[$type][7]['short']='minedu';
				        	self::$_items[$type][7]['id']=8;
				        	self::$_items[$type][7]['class']='blueBg';
				        	self::$_items[$type][8]['title']='Υποδομών, Μεταφορών και Δικτύων';
				        	self::$_items[$type][8]['short']='ypeka';
				        	self::$_items[$type][8]['id']=14;
				        	self::$_items[$type][8]['class']='blueBg';
				        	self::$_items[$type][9]['title']='Εργασίας και Κοινωνικής Ασφάλισης';
				        	self::$_items[$type][9]['short']='ypakp';
				        	self::$_items[$type][9]['id']=11;
				        	self::$_items[$type][9]['class']='blueBg';
				        	self::$_items[$type][10]['title']='Υγείας και Κοινωνικής Αλληλεγγύης';
				        	self::$_items[$type][10]['short']='yyka';
				        	self::$_items[$type][10]['id']=20;
				        	self::$_items[$type][10]['class']='blueBg';
				       		self::$_items[$type][11]['title']='Αγροτικής Ανάπτυξης και Τροφίμων';
				        	self::$_items[$type][11]['short']='ypaat';
				        	self::$_items[$type][11]['id']=3;
				        	self::$_items[$type][11]['class']='blueBg';
				        	self::$_items[$type][12]['title']='Δικαιοσύνης';
				        	self::$_items[$type][12]['short']='ministryofjustice';
				        	self::$_items[$type][12]['id']=5;
				        	self::$_items[$type][12]['class']='blueBg';
       						self::$_items[$type][13]['title']='Προστασίας του Πολίτη';
				        	self::$_items[$type][13]['short']='yptp';
				        	self::$_items[$type][13]['id']=19;
				        	self::$_items[$type][13]['class']='blueBg';
				        	self::$_items[$type][14]['title']='Πολιτισμού και Τουρισμού';
				        	self::$_items[$type][14]['short']='yppot';
				        	self::$_items[$type][14]['id']=17;
				        	self::$_items[$type][14]['class']='blueBg';
     			}
     	
    }
}
