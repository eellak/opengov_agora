<?php
class ContractItemApiData{

   /** Version.*/
  const VERSION = '1.1.0';
  	
   /**
   * Default options for curl.
   */
  
 	var $ProcurementUrlSchema;
  	var $CommonSchema;
  	
	public function __construct(){
		$this->ProcurementUrlSchema  = Yii::app()->params['agora']['procurement'];
		$this->CommonSchema = Yii::app()->params['agora']['common'];
		libxml_use_internal_errors ( true );
	}
  
  /**
   * Analyzes the output xml data fetched by the curlRequest.
   * With SimplXMLElement and xpath we parse the xml and 
   * get the data of the contract item.
   * 
   * @param xml $output the output of the curl request
   */
   public function getItemData($output){
   
   //	echo $output;
  		$test="";
  		$Result=array();
  		
  		if($output!=-1  && $output!=404){
  			try{
		   		$xml = new SimpleXMLElement($output);
				$xml->registerXPathNamespace('a',$this->ProcurementUrlSchema);
				$xml->registerXPathNamespace('b',$this->CommonSchema);
				
				$cost = $xml->xpath('//b:costBeforeVat');
				$documentUrl = $xml->xpath('//b:documentUrl');
				$docUrl = isset($documentUrl[0][0])?(string)$documentUrl[0][0]:'';
				$quantity = $xml->xpath('//b:quantity');
				$vatPercentage = $xml->xpath('//b:vatPercentage');
				$currency = $xml->xpath('//b:currency/attribute::idRef');
				$AllCpvs = $xml->xpath('//b:cpvCodes');
				$queryResultApproved = $xml->xpath('//a:approvesRequest');
				$description = $xml->xpath('//b:description');
				$procRequests = $xml->xpath('//b:procurementRequest');
				$Allkae = $xml->xpath('//b:kaeCodes');
				$address = $xml->xpath('//b:address');
				$addressNo = $xml->xpath('//b:addressNo');
				$addressPostal = $xml->xpath('//b:addressPostal');
				$Nuts = $xml->xpath('//b:nuts');
				$city = $xml->xpath('//b:city');
				$countryOfDelivery = $xml->xpath('//b:countryOfDelivery/attribute::idRef');
				$units_of_measure = $xml->xpath('//b:unitOfMeasure/attribute::idRef');
				$countryProduced = $xml->xpath('//b:countryProduced/attribute::idRef');		
				$awardProcedure = $xml->xpath('//a:awardProcedure/attribute::idRef');	
				$notice = $xml->xpath('//b:notice');
					
				for($i=0;$i<count($cost);$i++){
					$Result[$i]['cost'] = (float)$cost[$i][0];
					$Result[$i]['documentUrl'] = $docUrl;
					$Result[$i]['quantity'] = (int)$quantity[$i][0];
					$Result[$i]['units_of_measure']=(string)$units_of_measure[$i][0];
					$Result[$i]['vatid'] = (string)$vatPercentage[$i][0];
					$Result[$i]['currency'] = (string)$currency[$i][0];
					$Result[$i]['procurementRequest'] = isset($procRequests[$i][0])?(string)$procRequests[$i][0]:''; 
					$Result[$i]['awardProcedure'] = isset($awardProcedure[0][0])?(int)$awardProcedure[0][0]:''; 
					
					
					$test="";
					$cpv = (array)$AllCpvs[$i];		
					if(isset($cpv['cpv'])) {
						if(is_array($cpv['cpv'])) {
							for($ii=0;$ii<count($cpv['cpv']);$ii++){
								$test .= (string)$cpv['cpv'][$ii].",";
							}
							$Result[$i]['cpvsid'] = $test;
						}else 
						$Result[$i]['cpvsid'] = (string)$cpv['cpv'];
					
					}else 
					$Result[$i]['cpvsid']="";
					
					$Result[$i]['approvesRequest'] = (isset($queryResultApproved[0][0]))?(string)$queryResultApproved[0][0]:''; 
					$Result[$i]['description'] = (string)$description[$i][0];
					$Result[$i]['notice'] = (isset($notice[$i][0]))?(string)$notice[$i][0]:''; 
					//check if there is a kae code
					
					if(isset($Allkae[$i])){
					$testKae="";
					$kae = (array)$Allkae[$i];	
					if(isset($kae['kae'])) {		
						if(is_array($kae['kae'])) {
							for($kk=0;$kk<count($kae['kae']);$kk++){
								$testKae .= (string)$kae['kae'][$kk].",";
							}
							$Result[$i]['kae'] = $testKae;
							
						}else 
						$Result[$i]['kae'] = (string)$kae['kae'];
						
					}else 
					$Result[$i]['kae']="";
					}
					$Result[$i]['address'] = isset($address[$i][0])?(string)$address[$i][0]:'';
					$Result[$i]['addressNo'] = isset($addressNo[$i][0])?(string)$addressNo[$i][0]:'';
					$Result[$i]['addressPostal'] = isset($addressPostal[$i][0])?(string)$addressPostal[$i][0]:'';
					$Result[$i]['Nuts'] = (isset($Nuts[$i][0]))?(string)$Nuts[$i][0]:'';
					$Result[$i]['city'] = (isset($city[$i][0]))?(string)$city[$i][0]:'';
					$Result[$i]['countryOfDelivery'] = (isset($countryOfDelivery[$i][0]))?((string)$countryOfDelivery[$i][0]):"";
					$Result[$i]['countryProduced'] = (isset($countryProduced[$i][0]))?((string)$countryProduced[$i][0]):"";
			}
			
				return $Result;
	  		    
	  		}catch (Exception $_ex ){
	  			return -1; 
	  		}	
  		}else
	  			return -1; 	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
   }
}