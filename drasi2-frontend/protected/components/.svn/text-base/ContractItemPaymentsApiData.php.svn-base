<?php
class ContractItemPaymentsApiData{

   /** Version.*/
  const VERSION = '1.1.0';
  	
   /**
   * Default options for curl.
   */
  
 	var $PaymentsUrl;
  	var $CommonSchema;
  	var $PaymentsSchema;
  	
	public function __construct(){
		$this->PaymentsUrl  = Yii::app()->params['payments']['item'];
		$this->CommonSchema = Yii::app()->params['agora']['common'];
		$this->PaymentsSchema = Yii::app()->params['agora']['payment'];
		$this->ProcurementUrlSchema  = Yii::app()->params['agora']['procurement'];
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

   	$test="";
  		$Result=array();
  		if($output!=-1){
  			try{
		   		$xml = new SimpleXMLElement($output);
				$xml->registerXPathNamespace('a',$this->PaymentsUrl);
				$xml->registerXPathNamespace('b',$this->CommonSchema);
				$xml->registerXPathNamespace('c',$this->PaymentsSchema);
				$xml->registerXPathNamespace('d',$this->ProcurementUrlSchema);
				
				//<ns2:contractItems><item><quantity>10</quantity><cost><costBeforeVat>121221.0</costBeforeVat><vatPercentage>13</vatPercentage><currency>euro</currency></cost><cpvCodes><cpv>AA01-1</cpv><cpv>98393000-4</cpv></cpvCodes><kaeCodes/><description>mpuxaxaa</description></item></ns2:contractItems></ns2:request></ns2:procurementRequests></ns2:getProcurementRequestsResponse>
				$cost = $xml->xpath('//b:costBeforeVat');
				$units_of_measure = $xml->xpath('//b:unitOfMeasure/attribute::idRef');
				
				for($i=0;$i<count($cost);$i++){
				
				$cost = $xml->xpath('//b:costBeforeVat');
				$Result[$i]['cost'] = (float)$cost[$i][0];
				
				$quantity = $xml->xpath('//b:quantity');
				$Result[$i]['quantity'] = (int)$quantity[$i][0];
				$Result[$i]['units_of_measure']=(string)$units_of_measure[$i][0];
				$vatPercentage = $xml->xpath('//b:vatPercentage');
				$Result[$i]['vatid'] = (string)$vatPercentage[$i][0];
				$currency = $xml->xpath('//b:currency/attribute::idRef');
				$Result[$i]['currency'] = (string)$currency[$i][0]; 
				
				$AllCpvs = $xml->xpath('//b:cpvCodes');
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
				
				
				$description = $xml->xpath('//b:description');
				$Result[$i]['description'] = (string)$description[$i][0];
				$queryResultApproved = $xml->xpath('//d:approvesRequest');
				$Result[$i]['approvesRequest'] = (isset($queryResultApproved[0][0]))?(string)$queryResultApproved[0][0]:''; 
				//echo $queryResultApproved[$i][0]."<br/>";
				$procRequest = $xml->xpath('//b:procurementRequest');
				$Result[$i]['documentUrl'] = (isset($procRequest[$i][0]))?(string)$procRequest[$i][0]:''; 
				
				$notice = $xml->xpath('//b:notice');
				
				$Result[$i]['notice'] = (isset($notice[$i][0]))?(string)$notice[$i][0]:''; 
				
				//check if there is a kae code
				//check if there is a kae code
				$Allkae = $xml->xpath('//b:kaeCodes');
				
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
				//}//end is_array($Allkae)
				$address = $xml->xpath('//b:address');
				$Result[$i]['address'] = (isset($address[$i]))?(string)$address[$i][0]:'';
				
				$addressNo = $xml->xpath('//b:addressNo');
				$Result[$i]['addressNo'] = (isset($addressNo[$i]))?(string)$addressNo[$i][0]:'';
				
				$addressPostal = $xml->xpath('//b:addressPostal');
				$Result[$i]['addressPostal'] = (isset($addressPostal[$i]))?(string)$addressPostal[$i][0]:'';
				
				$Nuts = $xml->xpath('//b:nuts');
				$Result[$i]['Nuts'] = (isset($nuts[$i]))?(string)$Nuts[$i][0]:'';
				
				$city = $xml->xpath('//b:city');
				$Result[$i]['city'] = (isset($city[$i]))?(string)$city[$i][0]:'';
				
				$invoice = $xml->xpath('//b:invoiceNumber');
				$Result[$i]['invoice'] = (isset($invoice[$i]))?(string)$invoice[$i][0]:'';
				
				$countryOfDelivery = $xml->xpath('//b:countryOfDelivery/attribute::idRef');
				$Result[$i]['countryOfDelivery'] = (isset($countryOfDelivery[$i][0]))?((string)$countryOfDelivery[$i][0]):"";
				$countryProduced = $xml->xpath('//b:countryProduced/attribute::idRef');
				$Result[$i]['countryProduced'] = (isset($countryProduced[$i][0]))?((string)$countryProduced[$i][0]):"";
				
				//$Name = $xml->xpath('//c:secondaryParties/b:party/b:name');
				//$Afm = $xml->xpath('//c:secondaryParties/b:party/b:afm');
				//$Country = $xml->xpath('//c:secondaryParties/b:party/b:country');
				$cParty = $xml->xpath('//c:contractParty/b:country/attribute::idRef');
				$Result[$i]['Seccountry'] = (isset($cParty[$i][0]))?((string)$cParty[$i][0]):"";
				$cPartyAfm = $xml->xpath('//c:contractParty/b:afm');
				$Result[$i]['Secafm'] = (isset($cPartyAfm[$i][0]))?(string)$cPartyAfm[$i][0]:"";
				$cPartyName = $xml->xpath('//c:contractParty/b:name');
				$Result[$i]['Secname'] = (isset($cPartyName[$i][0]))?(string)$cPartyName[$i][0]:"";
				$responsibilityAssumptionCode = $xml->xpath('//c:responsibilityAssumptionCode');
				$Result[$i]['responsibilityAssumptionCode'] = (isset($responsibilityAssumptionCode[$i][0]))?(string)$responsibilityAssumptionCode[$i][0]:"";
				
			}
			
			
				return $Result;
  			}catch (Exception $e){
  			return -1;
  		}
  		}else
  			return -1;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
   }
}