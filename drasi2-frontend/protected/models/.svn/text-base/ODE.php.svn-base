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
class ODE extends CActiveRecord
{
	
	const _URL_COMMON_SCHEMA = 'http://agora.opengov.gr/schema/common-0.1';
	var $URLODE;
	private static $_items=array();
 
	public function  init(){
        $this->layout = '//layouts/column2PerOrg';
		$this->URLODE= Yii::app()->params['agora']['authenticate'];
        
    }
	/**
	 * Load the full list of a selected type code organization id.
	 * It calls the loadItems and then select the value to 
	 * return. 
	 * @param string $type the type Units, Signers
	 * @param integer $refid the Organization id
	 */
   public static function items($type,$refid)
    {
    	if(!isset(self::$_items[$type]))
            self::loadItems($type,$refid);
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
   * This function is responsible for the ode model. 
   * It returns an array with a list of the data of the type selected, 
   * @param string $type the type Units, Signers
   * @param integer $refid the Unit id when refered to Signers type
   * @return array an array with the data of the type selected.
   */
  private static function loadItems($type,$refid)
    {
     		$Url = Yii::app()->params['ode']['list'];
			$Url = $Url."?item=".$refid;
		
			$client = new EHttpClient($Url, array(
		    'maxredirects' => 0,
	      	'keepalive'       => false,
		    'timeout'      => 30));
	    	$client->setHeaders("Content-Type:application/xml");
			$client->setHeaders("Accept:application/xml");
			$response = $client->request();	
				
			if($response->isSuccessful()){	
				$Status = $response->getStatus();
				$Message = $response->getMessage();				
				if($Status==200 && $Message=='OK'){
					$output =  $response->getBody();
					
					$xml = new SimpleXMLElement($output);
					$xml->registerXPathNamespace('a',self::_URL_COMMON_SCHEMA);
					
					if($type=='withid'){
						$tagsLabel = $xml->xpath("a:item[@id=$refid]/a:oda/a:item/a:label");
						$tagsLatin = $xml->xpath("a:item[@id=$refid]/a:oda/a:item/attribute::id");
						
						if(count($tagsLabel)>0){
							self::$_items[$type]['----']='----';
						echo count($tagsLabel);
							for($i=0;$i<count($tagsLabel) ;$i++) {
								self::$_items[$type][(int)$tagsLatin[$i][0]]=(string)$tagsLabel[$i][0];
							}
						}else
							self::$_items[$type]['----']='----';
					}else{
						$tagsLabel = $xml->xpath("a:item[@id=$refid]/a:oda/a:item/a:label");
						$tagsLatin = $xml->xpath("a:item[@id=$refid]/a:oda/a:item/a:latinName");
								
						if(count($tagsLabel)>0){
							self::$_items[$type]['----']='----';
							for($i=0;$i<count($tagsLabel) ;$i++) {
								self::$_items[$type][(string)$tagsLatin[$i][0]]=(string)$tagsLabel[$i][0];
							}
						}else
							self::$_items[$type]['----']='----';
					}
						
			}else 
			//self::$_items[$type][$refid]=-1;
			self::$_items[$type]['----']='----';			
			
	    }else //ifresponse->isSuccessful()
	    self::$_items[$type][$refid]='----';
	    		//throw new CHttpException(Yii::t('yii','timeout'),Yii::t('yii','timeoutDesc'));
	   
    }
}