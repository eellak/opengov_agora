<?php 
class PaymentsApiData{

   /** Version.*/
  const VERSION = '1.1.0';
  	
   /**
   * Default options for curl.
   */
  
 	var $PaymentsUrlSchema;
  	var $CommonSchema;
  //a parameter whether an action is permitted or not (less tha 24 hours)
  var $actionPermitted;
  
	public function __construct(){
		$this->PaymentsUrlSchema  = Yii::app()->params['agora']['payment'];
		$this->CommonSchema = Yii::app()->params['agora']['common'];
		$this->actionPermitted = Yii::app()->params['parameters']['actionPermitted'];
		libxml_use_internal_errors ( true );
		
	}
	/**
	 * This function returns the number 
	 * of Payments that belong to a user.
	 * @param string $output the xml output from middleware
	 * @return int the number of payments.
	 */
	  public function getNumber($output){
	  	
	  	if($output==-1 || $output==404) return 0;
	  	try{
		  	$xml = new SimpleXMLElement($output);
		  	$xml->registerXPathNamespace('a',$this->PaymentsUrlSchema);
			$xml->registerXPathNamespace('b',$this->CommonSchema);
			$queryResultReturned = $xml->xpath('//b:total');
			if($queryResultReturned[0]>0)  return (int)$queryResultReturned[0];
			else return 0;
	  	}catch (Exception $e){
  			return 0;
  		}
	  }
	  
  	public function getShortList($output){

  	$Results = array();
  	if($output==-1) return $Results;
  	
  	try{
  		$xml = new SimpleXMLElement($output);
  		
		$xml->registerXPathNamespace('a',$this->PaymentsUrlSchema);
		$xml->registerXPathNamespace('b',$this->CommonSchema);
		$queryResultReturned = $xml->xpath('//b:returned');
		if($queryResultReturned[0]>0){
			$queryResultSubm = $xml->xpath('//b:submissionTime');	
			$queryResultModified = $xml->xpath('//b:lastModifiedTime');
			$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
			
			$queryResultDeleted = $xml->xpath('//b:deleted');
			$queryResultuniqueDocumentCode = $xml->xpath('//b:uniqueDocumentCode');
			$queryResultTitle = $xml->xpath('//b:title');
			$queryResultprotocolNumberCode = $xml->xpath('//b:protocolNumberCode');
		//	$queryADA = $xml->xpath('//b:adaCode');
			$queryContractdocumentUrl = $xml->xpath('//b:documentUrl');
			
			$queryResultContractDate = $xml->xpath('//b:dateSigned');
			$queryDescrption = $xml->xpath('//b:description');
			$querycostBeforeVat = $xml->xpath('//b:costBeforeVat');
			$queryTotalcostBeforeVat = $xml->xpath('//a:totalCostBeforeVAT');
			$queryCancelled = $xml->xpath('//b:cancelled');
			$querycancelledReason = $xml->xpath('//b:cancelledReason');
			$querycancellationType = $xml->xpath('//b:cancellationType/attribute::idRef');
			$k=0;
			for($i=0;$i<$queryResultReturned[0];$i++){				
				$Results[$i]['title'] = $queryResultTitle[$i][0] ;
				$Results[$i]['titleF'] = $queryResultTitle[$i][0] ."<br/><b>Μοναδικό ID:</b> ". $queryContractdocumentUrl[$i][0];
				
				$Results[$i]['uniqueDocumentCode'] = (string)$queryContractdocumentUrl[$i][0];
				
				$fdate = new DateTime($queryResultContractDate[$i][0]);
				$Results[$i]['dateSigned'] = $fdate->format('d/m/Y');
				$Results[$i]['OrganizationIdRef'] = (int)$queryOrganizationIdRef[$i]['idRef'];
				$Results[$i]['OrganisationName'] =Units::item("Organization", $Results[$i]['OrganizationIdRef'],$Results[$i]['OrganizationIdRef']);
				
				$fdate = new DateTime($queryResultSubm[$i][0]);
				$Results[$i]['submissionTime'] = $fdate->format('d/m/Y');
				$fdate = new DateTime($queryResultModified[$i][0]);
				$Results[$i]['lastModifiedTime'] = $fdate->format('d/m/Y');
				$Results[$i]['protocolNumberCode'] = (string)$queryResultuniqueDocumentCode[$i][0];
				$Results[$i]['deleted'] = (bool)$queryResultDeleted[$i][0];
				$Results[$i]['description'] = isset($queryDescrption[$i][0])?$queryDescrption[$i][0]:'';
				$Results[$i]['costBeforeVat'] =  isset($querycostBeforeVat[$i][0])?$querycostBeforeVat[$i][0]:'';
				$Results[$i]['documentUrl'] = (string)$queryContractdocumentUrl[$i][0];
				$Results[$i]['actionPermitted'] = (strtotime("now") - strtotime($queryResultSubm[$i][0]) < $this->actionPermitted)?1:0;
				$Results[$i]['totalCostBeforeVAT'] =  isset($queryTotalcostBeforeVat[$i][0])?$queryTotalcostBeforeVat[$i][0]:0;
				$Results[$i]['cancelled'] = isset($queryCancelled[$i][0])?(string)$queryCancelled[$i][0]:'false';
					if($Results[$i]['cancelled']=='true'){
						$Results[$i]['cancelledReason'] = isset($querycancelledReason[$k][0])?(string)$querycancelledReason[$k][0]:'';
						$Results[$i]['cancellationType'] = isset($querycancellationType[$k]['idRef'])?(int)$querycancellationType[$k]['idRef']:'';
						$k++;
					}else{
						$Results[$i]['cancelledReason'] =$Results[$i]['cancellationType']='';
					}	
		}			
		return $Results;
		
		}else return $Results;
  		}catch (Exception $e){
  			return -1;
  		}
  	
  }
  
  /**
   * Analyzes the output xml data fetched by the curlRequest.
   * With SimplXMLElement and xpath we parse the xml and  
   * get the data of the contract item.
   * 
   * @param the output of the curl request $output
   */
   public function getItemData($output){
   		
   		$Result=array();
   		if(is_integer($output) && $output!=-1) return $output;
   		if($output !=-1){
   			
   			try{
		   		$xml = new SimpleXMLElement($output);
		  		
				$xml->registerXPathNamespace('a',$this->PaymentsUrlSchema);
				$xml->registerXPathNamespace('b',$this->CommonSchema);
				
				$ReqTitle = $xml->xpath('//b:title');
				$Result[0]['title'] = (string)$ReqTitle[0];
				$queryResultSubm = $xml->xpath('///b:submissionTime');
				$Date = $queryResultSubm[0];
				$date = new DateTime($Date);
				$Result[0]['submissionTime'] =  $date->format('d/m/Y');
				$queryResult = $xml->xpath('///b:lastModifiedTime');
				$Date = $queryResult[0];
				$date = new DateTime($Date);
				$Result[0]['lastModifiedTime'] =  $date->format('d/m/Y');
				
				$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
				$Result[0]['OrganizationIdRef'] = (int)$queryOrganizationIdRef[0]['idRef'];
				$Result[0]['OrganisationName'] =Units::item("Organization", $Result[0]['OrganizationIdRef'],$Result[0]['OrganizationIdRef']);
				
				//get payment title
				$queryResult = $xml->xpath('//b:title');
				$Result[0]['title'] =  $queryResult[0];
				//get payment uniqueDocumentCode
				$queryResult = $xml->xpath('//b:uniqueDocumentCode');
				$Result[0]['uniqueDocumentCode'] = (string) $queryResult[0];
				
				//get payment DocumentUrl
				$queryResult = $xml->xpath('//b:documentUrl');
				$Result[0]['documentUrl'] =  (string)$queryResult[0];
				//get payment uniqueDocumentCode
				$queryResult = $xml->xpath('//b:protocolNumberCode');
				$Result[0]['protocolNumberCode'] =  (string)$queryResult[0];
				
				//get payment diavgeiaPublished
				$queryResult = $xml->xpath('//b:diavgeiaPublished');
				$Result[0]['diavgeiaPublished'] =  (string)$queryResult[0];
				
				$queryResult = $xml->xpath('//b:invoiceNumber');
				$Result[0]['invoice'] =(isset($queryResult[0]))?(string)$queryResult[0]:'';
				$queryCancelled = $xml->xpath('//b:cancelled');
				$Result[0]['cancelled'] = isset($queryCancelled[0][0])?(string)$queryCancelled[0][0]:'false';
				$querycancelledReason = $xml->xpath('//b:cancelledReason');
				$Result[0]['cancelledReason'] = isset($querycancelledReason[0][0])?(string)$querycancelledReason[0][0]:'';
				$querycancellationType = $xml->xpath('//b:cancellationType/attribute::idRef');
				$Result[0]['cancellationType'] = isset($querycancellationType[0]['idRef'])?(int)$querycancellationType[0]['idRef']:'';
				$querycancelledFromUserIdRef = $xml->xpath('//b:cancelledFromUserIdRef');	
				$Result[0]['cancelledFromUserIdRef'] = isset($querycancelledFromUserIdRef[0][0])?(int)$querycancelledFromUserIdRef[0][0]:'';
				$querycancelledTime = $xml->xpath('//b:cancelledTime');
				if(isset($querycancelledTime[0])){
					$Date = $querycancelledTime[0];
					$date = new DateTime($Date);
					$Result[0]['cancelledTime'] =  $date->format('d/m/Y');
				}else $Result[0]['cancelledTime']='';
				//get payment diavgeiaPublished
				//$queryResult = $xml->xpath('//b:adaCode');
				//$Result[0]['adaCode'] =  $queryResult[0];
				
				//get payment signedDate
				$queryResult = $xml->xpath('//b:dateSigned');
				$date = new DateTime($queryResult[0]);
				$Result[0]['dateSigned'] = $date->format('d/m/Y');
				
				$queryResult = $xml->xpath('//a:contractId');
				$Result[0]['contract'] =(isset($queryResult[0]))?(string)$queryResult[0]:'';
		
				$queryADAs = $xml->xpath('//b:relatedAdas/b:ada/b:adaCode');
				$queryADAsType = $xml->xpath('//b:relatedAdas/b:ada/b:adaType');
				
				$Result[0]['procurement']='';
				$Result[0]['relatedADAs']=0;
				if(isset($queryADAs[0])){
					for($i=0;$i<count($queryADAs);$i++){
						if((string)$queryADAsType[$i]=='ProcurementRequest') $Result[0]['procurement'].=(string)$queryADAs[$i].";";
						//if((string)$queryADAsType[$i]=='Contract') $Result[0]['contract']=(string)$queryADAs[$i];
					}
					$Result[0]['relatedADAs']=1;
					$Result[0]['procurements']=$Result[0]['procurement'];
				
				}
				
				//get payment issuerEmail
				$queryResult = $xml->xpath('//b:issuerEmail');
				$Result[0]['issuerEmail'] =   (string)$queryResult[0];
				
				
				
				//get payment diavgeiaPublished
				$queryResult = $xml->xpath('//a:responsibilityAssumptionCode');
				$Result[0]['responsibilityAssumptionCode'] =  $queryResult[0];
				$queryResult =$xml->xpath('//b:unit/attribute::idRef');
				$Result[0]['OrganizationIdRefUnits'] =  (int)$queryResult[0]['idRef'];
				$queryResult = $xml->xpath('//b:signer/attribute::idRef');
				$Result[0]['signer'] =  (int)$queryResult[0];
				$Result[0]['actionPermitted'] = (strtotime("now") - strtotime($queryResultSubm[0]) < $this->actionPermitted)?1:0;
   		}catch (Exception $e){
  			return -1;
  		}
				} 
				
		   		return $Result;
   			
   }
}