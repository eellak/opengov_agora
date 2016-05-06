<?php
/**
 * The API of fetching a decision with a specific ADA. 
 * @author themiszamani
 *
 */
class DecisionApiData{

   /** Version.*/
  const VERSION = '1.1.0';
  
  /**
   * The Schema Url of Decisions 
   * @var string
   */
 	var $ModelUrl;
 	/**
   * The API Url of Decisions 
   * @var string
   */
  	var $APIUrl;
  	/**
  	 * A table with the allowed types of decision 
  	 * @var unknown_type
  	 */
  	var $AllowedValues;
  	
	public function __construct(){
		$this->ModelUrl  = 'http://diavgeia.gov.gr/schema/model/diavgeia-decision-0.1';
		$this->APIUrl = 'http://diavgeia.gov.gr/schema/api/diavgeia-decision-0.1';
		$this->AllowedValues[0]=7;
		$this->AllowedValues[1]=54; //ΠΡΟΚΗΡΥΞΗ ΠΛΗΡΩΣΗΣ ΘΕΣΕΩΝ 
	}
  
  /**
   * Analyzes the output xml data fetched by the curlRequest.
   * With SimplXMLElement and xpath we parse the xml and 
   * get the data of the contract item.
   * 
   * Some rules taken under consideration
   * a) If no results found check the count parameter. 
   * b) if the Organization Id is different for the Organization Id of the logged in user
   * c) If the type of decision is different than the one required
   * @param xml $output the output of the curl request
   */
   public function getItemData($output){
  		$test="";
  		$Result=array();
 		if($output!=-1){
	   		$xml = new SimpleXMLElement($output);
		
			$xml->registerXPathNamespace('a',$this->ModelUrl);
			$xml->registerXPathNamespace('b',$this->APIUrl);
			
			$queryInfo=$xml->xpath('//count');
			if($queryInfo[0][0]>0){
				//DECISION TYPE
				
			//	$decisionType = $xml->xpath('//a:decisionTypeId');
			//	if(!in_array((int)$decisionType[0][0],$this->AllowedValues)) return -1;
						
				$organizationid = $xml->xpath('//a:organizationId');
			//	print_r($organizationid);
			//	if((int)$organizationid[0][0]!=Yii::app()->user->RefId) return -1;
		
				$submissiontimestamp = $xml->xpath('//a:submissionTimestamp');
				$protocolnumber = $xml->xpath('//a:protocolNumber');
				$subject = $xml->xpath('//a:subject');
				$organizationunitid = $xml->xpath('//a:organizationUnitId');
				$signerid = $xml->xpath('//a:signerId');
				$documentUrl = $xml->xpath('//a:documentUrl');
				$ari8mosKAE = $xml->xpath('//a:extraFields/a:extraField[@name="arithmos_kae"]/a:value');
				
				$fdate = new DateTime($submissiontimestamp[0][0]);
				$Result[0]['submissionTimestamp']=$fdate->format('d/m/Y');
				$Result[0]['protocolNumber'] = isset($protocolnumber[0][0])?(string)$protocolnumber[0][0]:'';
				$Result[0]['title'] = isset($subject[0][0])?(string)$subject[0][0]:'';
				$Result[0]['organizationId'] = isset($organizationid[0][0])?(int)$organizationid[0][0]:'';
				$Result[0]['organizationUnitId'] = isset($organizationunitid[0][0])?(int)$organizationunitid[0][0]:'';
				$Result[0]['signerId'] = isset($signerid[0][0])?(int)$signerid[0][0]:'';
				$Result[0]['documentUrl'] = isset($documentUrl[0][0])?(string)$documentUrl[0][0]:'';
				$Result[0]['ari8mosKAE'] = isset($ari8mosKAE[0][0])?(string)$ari8mosKAE[0][0]:'';
				
				return $Result;
			}else return -1;
  		}else
  			return -1;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
   }
}