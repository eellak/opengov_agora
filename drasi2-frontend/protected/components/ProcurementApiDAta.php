<?php 
class ProcurementApiData{

   /** Version.*/
  const VERSION = '1.1.0';
  	
   /**
   * Default options for curl.
   */
  
 	var $ProcurementUrlSchema;
  	var $CommonSchema;
  	//a parameter whether an action is permitted or not (less tha 24 hours)
  	var $actionPermitted;
  
	public function __construct(){
		$this->ProcurementUrlSchema  = Yii::app()->params['agora']['procurement'];
		$this->CommonSchema = Yii::app()->params['agora']['common'];
		$this->actionPermitted = Yii::app()->params['parameters']['actionPermitted'];
		libxml_use_internal_errors ( true );
	}
	
	/**
	 * This function returns the number 
	 * of Procurements that belong to a user.
	 * @param string $output the xml output from middleware
	 * @return int the number of procurements.
	 */
	  public function getNumber($output){
	
	  	if($output==-1 || $output==404) return 0;
	  	try{
		  	$xml = new SimpleXMLElement($output);
		  	$xml->registerXPathNamespace('a',$this->ProcurementUrlSchema);
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
  	if($output==-1 || $output==500) return $Results;
  	
  	try{
  		$xml = new SimpleXMLElement($output);
		$xml->registerXPathNamespace('a',$this->ProcurementUrlSchema);
		$xml->registerXPathNamespace('b',$this->CommonSchema);
		
		$queryResultReturned = $xml->xpath('//b:returned');
		if($queryResultReturned[0]>0){
			$queryResult = $xml->xpath('//b:submissionTime');	
			$queryResultidRef = $xml->xpath('//b:userIdRef');	
			$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
			
			$queryResultModified = $xml->xpath('//b:lastModifiedTime');
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
				$Results[$i]['title'] = isset($queryResultTitle[$i][0])?(string)$queryResultTitle[$i][0]:'' ;
				$Results[$i]['OrganizationIdRef'] = isset($queryOrganizationIdRef[$i]['idRef'])?(int)$queryOrganizationIdRef[$i]['idRef']:'';
				$Results[$i]['uniqueDocumentCode'] = isset($queryContractdocumentUrl[$i][0])?(string)$queryContractdocumentUrl[$i][0]:'';
				$fdate = new DateTime($queryResultContractDate[$i][0]);
				$Results[$i]['dateSigned'] = $fdate->format('d/m/Y');
				$Results[$i]['userIdRef'] = isset($queryResultidRef[0])?(int)$queryResultidRef[0]:'';
				
				$Results[$i]['cancelled'] = isset($queryCancelled[$i][0])?(string)$queryCancelled[$i][0]:'false';
				if($Results[$i]['cancelled']=='true'){
					$Results[$i]['cancelledReason'] = isset($querycancelledReason[$k][0])?(string)$querycancelledReason[$k][0]:'';
					$Results[$i]['cancellationType'] = isset($querycancellationType[$k]['idRef'])?(int)$querycancellationType[$k]['idRef']:'';
					$k++;
				}else{
					$Results[$i]['cancelledReason'] =$Results[$i]['cancellationType']='';
				}
				if(isset($queryResult[$i][0])){
					$fdate = new DateTime($queryResult[$i][0]);
					$Results[$i]['submissionTime'] = $fdate->format('d/m/Y');
				}else
					$Results[$i]['submissionTime']='';
				
				if(isset($queryResultModified[$i][0])){
					$fdate = new DateTime($queryResultModified[$i][0]);
					$Results[$i]['lastModifiedTime'] = $fdate->format('d/m/Y');
				}else $Results[$i]['lastModifiedTime']='';
				
				$Results[$i]['protocolNumberCode'] = isset($queryResultuniqueDocumentCode[$i][0])?(string)$queryResultuniqueDocumentCode[$i][0]:'';
				$Results[$i]['deleted'] = isset($queryResultDeleted[$i][0])?(string) $queryResultDeleted[$i][0]:"false";
				$Results[$i]['description'] = isset($queryDescrption[$i][0])?(string)$queryDescrption[$i][0]:'';
				$Results[$i]['costBeforeVat'] =  isset($querycostBeforeVat[$i][0])?$querycostBeforeVat[$i][0]:'';
				$Results[$i]['documentUrl'] = isset($queryContractdocumentUrl[$i][0])?(string)$queryContractdocumentUrl[$i][0]:'';
				$id = $Results[$i]['documentUrl'];
				
				//set actionPermitted
				$Results[$i]['actionPermitted'] = (strtotime("now") - strtotime($queryResult[$i][0]) < $this->actionPermitted)?1:0;
				//the approves Reqiest
				$queryResultApproved = $xml->xpath("//a:getProcurementRequestsResponse/a:procurementRequests/a:request[@id='$id']/a:approvesRequest");
				$Results[$i]['approvesRequest'] =  isset($queryResultApproved[0][0])?(string) $queryResultApproved[0][0]:'';
				$Results[$i]['totalCostBeforeVAT'] =  isset($queryTotalcostBeforeVat[$i][0])?$queryTotalcostBeforeVat[$i][0]:0;
				
				
		}		
		return $Results;
		
		}else return -1;
  	}catch (Exception $e){
  			return -1;
  	}
  	
  }
  	/**
  	 * It calls the short list function from the api 
  	 * and prints the basic data such as title, ada, dates, cost.
  	 * @param string $output the middleware output
  	 */
 	public function getShortListOutput($output){
  	   
  	$Results = array();
  	if($output==-1 || $output==500) return $Results;
  	
  	try{
  		$xml = new SimpleXMLElement($output);
		$xml->registerXPathNamespace('a',$this->ProcurementUrlSchema);
		$xml->registerXPathNamespace('b',$this->CommonSchema);
		
		$queryResultReturned = $xml->xpath('//b:returned');
		if($queryResultReturned[0]>0){
			$queryResult = $xml->xpath('//b:submissionTime');	
			$queryResultidRef = $xml->xpath('//b:userIdRef');	
			$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
			$queryResultModified = $xml->xpath('//b:lastModifiedTime');
			$queryResultuniqueDocumentCode = $xml->xpath('//b:uniqueDocumentCode');
			$queryResultTitle = $xml->xpath('//b:title');
			$queryResultprotocolNumberCode = $xml->xpath('//b:protocolNumberCode');
		//	$queryADA = $xml->xpath('//b:adaCode');
			$queryContractdocumentUrl = $xml->xpath('//b:documentUrl');
			$queryResultContractDate = $xml->xpath('//b:dateSigned');
			$queryTotalcostBeforeVat = $xml->xpath('//a:totalCostBeforeVAT');

			$querycancellationType = $xml->xpath('//b:cancellationType/attribute::idRef');
			$querycancelledReason = $xml->xpath('//b:cancelledReason');
			$queryCancelled = $xml->xpath('//b:cancelled');
			$queryTotalcostBeforeVat = $xml->xpath('//a:totalCostBeforeVAT');
			
			$k=0;
			for($i=0;$i<$queryResultReturned[0];$i++){				
				$Results[$i]['title'] = isset($queryResultTitle[$i][0])?(string)$queryResultTitle[$i][0]:'' ;
				$Results[$i]['OrganizationIdRef'] = isset($queryOrganizationIdRef[$i]['idRef'])?(int)$queryOrganizationIdRef[$i]['idRef']:'';
				$Results[$i]['uniqueDocumentCode'] = isset($queryContractdocumentUrl[$i][0])?(string)$queryContractdocumentUrl[$i][0]:'';
				$fdate = new DateTime($queryResultContractDate[$i][0]);
				$Results[$i]['dateSigned'] = $fdate->format('d/m/Y');
				
				$fdate = new DateTime($queryResult[$i][0]);
				$Results[$i]['submissionTime'] = $fdate->format('d/m/Y');
				$fdate = new DateTime($queryResultModified[$i][0]);
				$Results[$i]['lastModifiedTime'] = $fdate->format('d/m/Y');
				$Results[$i]['deleted'] = isset($queryResultDeleted[$i][0])?(string) $queryResultDeleted[$i][0]:"false";
				$Results[$i]['documentUrl'] = isset($queryContractdocumentUrl[$i][0])?(string)$queryContractdocumentUrl[$i][0]:'';
				$id = $Results[$i]['documentUrl'];
				
				$Results[$i]['cancelled'] = isset($queryCancelled[$i][0])?(string)$queryCancelled[$i][0]:'false';
				if($Results[$i]['cancelled']=='true'){
					$Results[$i]['cancelledReason'] = isset($querycancelledReason[$k][0])?(string)$querycancelledReason[$k][0]:'';
					$Results[$i]['cancellationType'] = isset($querycancellationType[$k]['idRef'])?(int)$querycancellationType[$k]['idRef']:'';
					$k++;
				}else{
					$Results[$i]['cancelledReason'] =$Results[$i]['cancellationType']='';
				}
				
				//set actionPermitted
				$Results[$i]['actionPermitted'] = (strtotime("now") - strtotime($queryResult[$i][0]) < $this->actionPermitted)?1:0;
				//the approves Reqiest
				$queryResultApproved = $xml->xpath("//a:getProcurementRequestsResponse/a:procurementRequests/a:request[@id='$id']/a:approvesRequest");
				$Results[$i]['approvesRequest'] =  isset($queryResultApproved[0][0])?(string) $queryResultApproved[0][0]:'';
				$Results[$i]['totalCostBeforeVAT'] =  isset($queryTotalcostBeforeVat[$i][0])?$queryTotalcostBeforeVat[$i][0]:0;
				
		}
		//print_r($Results);		
		return $Results;
		
		}else return $Results;
  	}catch (Exception $e){
  			return $Results;
  	}
  	
  }
  
 // public functio getApprovesRequest($documentUrl)
  
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
   		if($output!=-1){
   		
	   		try{
		   		$xml = new SimpleXMLElement($output);
			
				$xml->registerXPathNamespace('a',$this->ProcurementUrlSchema);
				$xml->registerXPathNamespace('b',$this->CommonSchema);
			
					$ReqTitle = $xml->xpath('//b:title');
					$Result[0]['title'] = (string)$ReqTitle[0];
					$queryResultidRef = $xml->xpath('//b:userIdRef');	
					$Results[0]['userIdRef'] = isset($queryResultidRef[0])?(int)$queryResultidRef[0]:'';
					
					$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
					$Result[0]['OrganizationIdRef'] = isset($queryOrganizationIdRef[0]['idRef'])?(int)$queryOrganizationIdRef[0]['idRef']:'';
					
					$queryCancelled = $xml->xpath('//b:cancelled');
					$Result[0]['cancelled'] = isset($queryCancelled[0])?(string)$queryCancelled[0]:'false';
					$querycancelledReason = $xml->xpath('//b:cancelledReason');
					$Result[0]['cancelledReason'] = isset($querycancelledReason[0])?(string)$querycancelledReason[0]:'';
					$querycancellationType = $xml->xpath('//b:cancellationType/attribute::idRef');
					$Result[0]['cancellationType'] = isset($querycancellationType[0]['idRef'])?(int)$querycancellationType[0]['idRef']:'';
					$querycancelledFromUserIdRef = $xml->xpath('//b:cancelledFromUserIdRef');	
					$Result[0]['cancelledFromUserIdRef'] = isset($querycancelledFromUserIdRef[0])?(int)$querycancelledFromUserIdRef[0]:'';
					$querycancelledTime = $xml->xpath('//b:cancelledTime');
					if(isset($querycancelledTime[0])){
						$Date = $querycancelledTime[0];
						$date = new DateTime($Date);
						$Result[0]['cancelledTime'] =  $date->format('d/m/Y');
					}else $Result[0]['cancelledTime']='';
			
					$queryResultSubm = $xml->xpath('//b:submissionTime');
					if(isset($queryResultSubm[0])){
						$Date = $queryResultSubm[0];
						$date = new DateTime($Date);
						$Result[0]['submissionTime'] =  $date->format('d/m/Y');
					}else $Result[0]['submissionTime']='';
			
					$queryResult = $xml->xpath('//b:lastModifiedTime');
					if(isset($queryResult[0])){
						$Date = $queryResult[0];
						$date = new DateTime($Date);
						$Result[0]['lastModifiedTime'] =  $date->format('d/m/Y');
			   		}else 
			   			$Result[0]['lastModifiedTime'] ='';
			   			
					//get procurement title
					
					$queryResult = $xml->xpath('//b:title');
					
					$Result[0]['title'] =  isset($queryResult[0])?(string)$queryResult[0]:'';
					//get procurement uniqueDocumentCode
					$queryResult = $xml->xpath('//b:uniqueDocumentCode');
					$Result[0]['uniqueDocumentCode'] =  isset($queryResult[0])?(string)$queryResult[0]:'';
					
					$queryDescrption = $xml->xpath('//b:description');
					$Result[0]['description'] =  isset($queryDescrption[0])?(string)$queryDescrption[0]:'';
					$querycostBeforeVat = $xml->xpath('//b:costBeforeVat');
					$Result[0]['costBeforeVat'] =  isset($querycostBeforeVat[0])?$querycostBeforeVat[0]:'';
					
					
					//get procurement DocumentUrl
					$queryResult = $xml->xpath('//b:documentUrl');
					$Result[0]['documentUrl'] =  isset($queryResult[0])?(string)$queryResult[0]:'';
					//get procurement uniqueDocumentCode
					$queryResult = $xml->xpath('//b:protocolNumberCode');
					$Result[0]['protocolNumberCode'] =  isset($queryResult[0])?(string)$queryResult[0]:'';
					
					//get procurement diavgeiaPublished
					$queryResult = $xml->xpath('//b:diavgeiaPublished');
					$Result[0]['diavgeiaPublished'] = isset($queryResult[0])?(string)$queryResult[0]:false;
					//get issuerEmail 
					$queryResult = $xml->xpath('//b:issuerEmail');
					$Result[0]['issuerEmail'] =  isset($queryResult[0])?(string) $queryResult[0]:'';
					
					//get procurement signedDate
					$queryResult = $xml->xpath('//b:dateSigned');
					$date = new DateTime($queryResult[0]);
					$Result[0]['dateSigned'] =   $date->format('d/m/Y');
					
					$queryResult = $xml->xpath('//a:approvesRequest');
					$Result[0]['approvesRequest'] =   isset($queryResult[0])?(string) $queryResult[0]:'';
					
					$queryResult = $xml->xpath('//a:awardProcedure/attribute::idRef');
					$Result[0]['awardProcedure'] =  isset($queryResult[0]['idRef'])?(int) $queryResult[0]['idRef']:'';
					
					$queryResult = $xml->xpath('//a:responsibilityAssumptionCode');
					$Result[0]['responsibilityAssumptionCode'] =  isset($queryResult[0][0])?(string) $queryResult[0][0]:'';
					
					$queryResult = $xml->xpath('//a:fulfilmentDate');
					if(isset($queryResult[0][0]) && $queryResult[0][0]!='')
					$date = new DateTime($queryResult[0]);
					$Result[0]['fulfilmentDate'] =  isset($queryResult[0][0])? $date->format('d/m/Y'):'';
			
					$queryResult = $xml->xpath('//b:unit/attribute::idRef');
					$Result[0]['OrganizationIdRefUnits'] =  (int)$queryResult[0]['idRef'];
					
					$queryResult = $xml->xpath('//b:signer/attribute::idRef');
					$Result[0]['signer'] =  isset($queryResult[0])?(int)$queryResult[0]:'';
					
					$Result[0]['actionPermitted'] = (strtotime("now") - strtotime($queryResultSubm[0]) < $this->actionPermitted)?1:0;
					return $Result;
	  		 }catch (Exception $e){
	  			return -1;
	  		 }
	  		}else 
	  		return $Result;
   }
   
   function isApproved($uniqueDocumentCode){
   		$config['apirequest'] ='procurementapproved';
		$config['documentid'] =$uniqueDocumentCode;
		//print_r($config);
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		 if(simplexml_load_string($output)) return $output;
		
		//print_r($output);
		
		
   }
}