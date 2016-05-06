<?php
class SignersApiData{

   /** Version.*/
  const VERSION = '1.1.0';
  	
   /**
   * Default options for curl.
   */
  
 	var $ProcurementUrlSchema;
  	var $CommonSchema;
  	var $ContractSchema;
  	
	public function __construct(){
		$this->ProcurementUrlSchema  = Yii::app()->params['agora']['procurement'];
		$this->CommonSchema = Yii::app()->params['agora']['common'];
		$this->ContractSchema = Yii::app()->params['agora']['contract'];
	}
  	/**
   * Analyzes the output xml data fetched by the curlRequest.
   * With SimplXMLElement and xpath we parse the xml and 
   * get the data of the contract item.
   * 
   * @param xml $output the output of the curl request
   */
   public function getItemData($output){
  		
   		if($output==404) return -1;
   	
   		$test="";
  		$Result=array();
   		$xml = new SimpleXMLElement($output);
		
		$xml->registerXPathNamespace('a',$this->ProcurementUrlSchema);
		$xml->registerXPathNamespace('b',$this->CommonSchema);
		$xml->registerXPathNamespace('c', $this->ContractSchema);
		

		$Name = $xml->xpath('//c:secondaryParties/b:party/b:name');
		$Afm = $xml->xpath('//c:secondaryParties/b:party/b:afm');
		$Country = $xml->xpath('//c:secondaryParties/b:party/b:country/attribute::idRef');
		
		for($i=0;$i<count($Name);$i++){
			$Result[$i]['Secname'] = (string)$Name[$i][0];
			$Result[$i]['Secafm'] = (string)$Afm[$i][0];
			$Result[$i]['Seccountry'] = (string)$Country[$i][0];
		}
		
		return $Result;
		
   }
   
	/**
   * Analyzes the output xml data fetched by the curlRequest.
   * With SimplXMLElement and xpath we parse the xml and 
   * get the data of the contract item.
   * 
   * @param xml $output the output of the curl request
   */
   public function getAFMData($output){
  		
   		if($output==404) return -1;
   	
   		$test="";
  		$Result=array();
   		$xml = new SimpleXMLElement($output);
		
		$xml->registerXPathNamespace('a',$this->ProcurementUrlSchema);
		$xml->registerXPathNamespace('b',$this->CommonSchema);
		$xml->registerXPathNamespace('c', $this->ContractSchema);

		$Name = $xml->xpath('//c:secondaryParties/b:party/b:name');
		$Afm = $xml->xpath('//c:secondaryParties/b:party/b:afm');
		
		for($i=0;$i<count($Name);$i++){
			$Result[$i]['Secname'] = (string)$Name[$i][0];
			$Result[$i]['Secafm'] = (string)$Afm[$i][0];
		}

		$Result = $this->multi_unique($Result);

		return $Result;
		
   }
   /**
    * We want a function to check multiple values of the same afm / name. 
    * This function uses the serialize, array_unique, and unserialize functions to do the work.
    * It checks if both afm,name are equal in order to create a new unique multidimensional table.
    * @param unknown_type $array
    */
	 function multi_unique($array) {
        foreach ($array as $k=>$na)
            $new[$k] = serialize($na);
        $uniq = array_unique($new);
        foreach($uniq as $k=>$ser)
            $new1[$k] = unserialize($ser);
        return ($new1);
    }
}