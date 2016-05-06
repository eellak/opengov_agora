<?php
class RFPapidata{

   /** Version.*/
  const VERSION = '1.1.0';
  /**
	* The RFP Schema Url defines if the payment is a new one.
	* @var string
  */
  var $RFPUrlSchema;
  /**
	* The Common Schema Url defines if the payment is a new one.
	* @var string
  */
  var $UrlCommonSchema;
  /**
  	*A parameter whether an action is permitted or not (less tha 24 hours)
	* @var string
  */
  var $actionPermitted;
  /**
   * The Schema Procurement Url 
   * @var string
   */
  var $ProcurementSchema;
  
  
  /**
   * The construct function
   */
  public function __construct(){
 		$this->RFPUrlSchema  = Yii::app()->params['agora']['rfp'];
		$this->UrlCommonSchema = Yii::app()->params['agora']['common'];
		$this->actionPermitted = Yii::app()->params['parameters']['actionPermitted'];
		$this->ProcurementSchema = Yii::app()->params['agora']['procurement'];
		libxml_use_internal_errors ( true );
		
  }
  
	/**
	 * This function returns the number 
	 * of Contracts that belong to a user.
	 * @param string $output the xml output from middleware
	 * @return int the number of contracts.
	 */
	  public function getNumber($output){

	  	if($output==-1) return 0;
	  	try{
		  	$xml = new SimpleXMLElement($output);
			$xml->registerXPathNamespace('a',$this->RFPUrlSchema);
			$xml->registerXPathNamespace('b',$this->UrlCommonSchema);
			$queryResultReturned = $xml->xpath('//b:total');
	
		  	if($queryResultReturned[0]>0)  return (int)$queryResultReturned[0];
			else return 0;
	  	}catch (Exception $e){
  			return 0;
  		}
	  }
	 /**
	 * This function sets the values of the model
	 * @param string $output the xml output from middleware
	 * @return model the model with multiple values or -1 if nothing is found.
	 */
  	 public function getShortList($output){
  	 
  	 	try{
	  		$xml = new SimpleXMLElement($output);
			$xml->registerXPathNamespace('a',$this->RFPUrlSchema);
			$xml->registerXPathNamespace('b',$this->UrlCommonSchema);
			
			$k=0;
			$queryResultReturned = $xml->xpath('//b:returned');
			if($queryResultReturned[0]>0){
				$queryResultSubm = $xml->xpath('//b:submissionTime');	
				$queryResultModified = $xml->xpath('//b:lastModifiedTime');
				$queryResultDeleted = $xml->xpath('//b:deleted');
				$queryResultuniqueDocumentCode = $xml->xpath('//b:uniqueDocumentCode');
				$queryResultTitle = $xml->xpath('//b:title');
				$queryResultprotocolNumberCode = (string)$xml->xpath('//b:protocolNumberCode');
				$queryRFPdocumentUrl = $xml->xpath('//b:documentUrl');
				$queryResultRFPDate = $xml->xpath('//b:dateSigned');
				$queryResultSince = $xml->xpath('//a:since');
				$queryResultUntil = $xml->xpath('//a:until');
				$queryCancelled = $xml->xpath('//b:cancelled');
				$querycancelledReason = $xml->xpath('//b:cancelledReason');
				$querycancellationType = $xml->xpath('//b:cancellationType/attribute::idRef');
				$RFP = new Rfp();
				for($i=0;$i<$queryResultReturned[0];$i++){	
					$RFP->title[$i] = $queryResultTitle[$i][0] ;
					$RFP->uniqueDocumentCode[$i] = (string)$queryRFPdocumentUrl[$i][0];
					$fdate = new DateTime($queryResultRFPDate[$i][0]);
					$RFP->dateSigned[$i] = $fdate->format('d/m/Y');
					$RFP->protocolNumberCode[$i] = (string)$queryResultuniqueDocumentCode[$i][0];
					$RFP->deleted[$i] = (bool)$queryResultDeleted[$i][0];
					$RFP->actionPermitted[$i] = (strtotime("now") - strtotime($queryResultSubm[$i][0]) < $this->actionPermitted)?1:0;
					$id = $queryRFPdocumentUrl[$i][0];
					$fdate = new DateTime($queryResultModified[$i][0]);
					$RFP->lastModifiedTime[$i] = $fdate->format('d/m/Y');
					$fdate = new DateTime($queryResultSubm[$i][0]);
					$RFP->submissionTime[$i] = $fdate->format('d/m/Y');
					$fdate = new DateTime($queryResultSince[$i][0]);
					$RFP->since[$i] = $fdate->format('d/m/Y');
					$fdate = new DateTime($queryResultUntil[$i][0]);
					$RFP->until[$i] = $fdate->format('d/m/Y');
					
				$RFP->cancelled[$i] = isset($queryCancelled[$i][0])?(string)$queryCancelled[$i][0]:'false';
				if($RFP->cancelled[$i]=='true'){
					$RFP->cancelledReason[$i] = isset($querycancelledReason[$k][0])?(string)$querycancelledReason[$k][0]:'';
					$RFP->cancellationType[$i] = isset($querycancellationType[$k]['idRef'])?(int)$querycancellationType[$k]['idRef']:'';
					$k++;
				}else{
					$RFP->cancelledReason[$i] =$RFP->cancellationType[$i]='';
				}
					
				}	
				return $RFP;
				
			}else return -1;
  	 	}catch (Exception $e){
  			return -1;
  		}
  	}
 /**
	 * This function get the values of the model in a table
	 * @param string $output the xml output from middleware
	 * @return table a table with multiple values or -1 if nothing is found.
	 */
  	 public function getShortListTable($output){
  	 	
  	 	try{
	  		$xml = new SimpleXMLElement($output);
			$xml->registerXPathNamespace('a',$this->RFPUrlSchema);
			$xml->registerXPathNamespace('b',$this->UrlCommonSchema);
			$Results=array();
			$queryResultReturned = $xml->xpath('//b:returned');
			if($queryResultReturned[0]>0){
				$queryResultSubm = $xml->xpath('//b:submissionTime');	
				$queryResultModified = $xml->xpath('//b:lastModifiedTime');
				$queryResultDeleted = $xml->xpath('//b:deleted');
				$queryResultuniqueDocumentCode = $xml->xpath('//b:uniqueDocumentCode');
				$queryResultTitle = $xml->xpath('//b:title');
				$queryResultprotocolNumberCode = (string)$xml->xpath('//b:protocolNumberCode');
				$queryRFPdocumentUrl = $xml->xpath('//b:documentUrl');
				$queryResultRFPDate = $xml->xpath('//b:dateSigned');
				$queryResultSince = $xml->xpath('//a:since');
				$queryResultUntil = $xml->xpath('//a:until');
				$queryResultCost = $xml->xpath('//a:totalCostBeforeVAT');
				$queryCancelled = $xml->xpath('//b:cancelled');
				$querycancelledReason = $xml->xpath('//b:cancelledReason');
				$querycancellationType = $xml->xpath('//b:cancellationType/attribute::idRef');
				$k=0;
				$RFP = new Rfp();
				for($i=0;$i<$queryResultReturned[0];$i++){	
					$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
					$Results[$i]['OrganizationIdRef'] = (int)$queryOrganizationIdRef[$i]['idRef'];
					$Results[$i]['OrganisationName'] =Units::item("Organization", $Results[$i]['OrganizationIdRef'],$Results[$i]['OrganizationIdRef']);
			
					$Results[$i]['title'] = $queryResultTitle[$i][0] ;
					$Results[$i]['uniqueDocumentCode'] = (string)$queryRFPdocumentUrl[$i][0];
					$fdate = new DateTime($queryResultRFPDate[$i][0]);
					$Results[$i]['dateSigned'] = $fdate->format('d/m/Y');
					$Results[$i]['protocolNumberCode'] = (string)$queryResultuniqueDocumentCode[$i][0];
					$Results[$i]['deleted'] = (bool)$queryResultDeleted[$i][0];
					$Results[$i]['actionPermitted'] = (strtotime("now") - strtotime($queryResultSubm[$i][0]) < $this->actionPermitted)?1:0;
					$id = $queryRFPdocumentUrl[$i][0];
					$fdate = new DateTime($queryResultModified[$i][0]);
					$Results[$i]['lastModifiedTime'] = $fdate->format('d/m/Y');
					$fdate = new DateTime($queryResultSubm[$i][0]);
					$Results[$i]['submissionTime'] = $fdate->format('d/m/Y');
					$fdate = new DateTime($queryResultSince[$i][0]);
					$Results[$i]['since'] = $fdate->format('d/m/Y');
					$fdate = new DateTime($queryResultUntil[$i][0]);
					$Results[$i]['until'] = $fdate->format('d/m/Y');
					$Results[$i]['totalCostBeforeVAT'] = isset($queryResultCost[$i][0])?doubleval($queryResultCost[$i][0]):'';
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
   		$Result = array();		
   		if(is_integer($output) && $output!=-1) return $output;
   	
  		if($output==-1 || is_integer($output)) return $Result;
  		try{
	  		$xml = new SimpleXMLElement($output);
		
			$xml->registerXPathNamespace('a',$this->RFPUrlSchema);
			$xml->registerXPathNamespace('b',$this->UrlCommonSchema);
			$xml->registerXPathNamespace('c', $this->ProcurementSchema);
		
				$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
				$Result[0]['OrganizationIdRef'] = (int)$queryOrganizationIdRef[0]['idRef'];
				$Result[0]['OrganisationName'] =Units::item("Organization", $Result[0]['OrganizationIdRef'],$Result[0]['OrganizationIdRef']);
				
				$queryResultSubm = $xml->xpath('//b:submissionTime');
				$Date = $queryResultSubm[0];
				$date = new DateTime($Date);
				$Result[0]['submissionTime'] =  $date->format('d/m/Y');
				$queryResult = $xml->xpath('//b:lastModifiedTime');
				$Date = $queryResult[0];
				$date = new DateTime($Date);
				$Result[0]['lastModifiedTime'] =  $date->format('d/m/Y');
				
				$queryResult = $xml->xpath('//b:deleted');
				$Result[0]['deleted'] = (string)$queryResult[0];
				
				$ReqTitle = $xml->xpath('//b:title');
				$Result[0]['title'] = (string)$ReqTitle[0];
				
				$queryResult = $xml->xpath('//b:documentUrl');
				$Result[0]['documentUrl'] =  (string)$queryResult[0];
				$Result[0]['uniqueDocumentCode'] = (string) $queryResult[0];
				
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
				
				$queryResult = $xml->xpath('//b:protocolNumberCode');
				$Result[0]['protocolNumberCode'] =  (string)$queryResult[0];
				
				$queryResult = $xml->xpath('//b:unit/attribute::idRef');
				if(isset($queryResult[0]))
					$Result[0]['OrganizationIdRefUnits'] =  (int)$queryResult[0];
				else 
					$Result[0]['OrganizationIdRefUnits'] =  Yii::app()->user->RefId;
				
				$queryResult = $xml->xpath('//b:signer/attribute::idRef');
				for($i=0;$i<count($queryResult);$i++)
				$Result[0]['signers'][$i] = (int)$queryResult[$i][0];
				
				//get Related ADAs
				$queryADAs = $xml->xpath('//b:relatedAdas/b:ada/b:adaCode');
				$queryADAsType = $xml->xpath('//b:relatedAdas/b:ada/b:adaType');
				$Result[0]['procurements'] ='';
				$Result[0]['relatedADAs']=0;
				if(isset($queryADAs[0])){
					for($i=0;$i<count($queryADAs);$i++){
						if((string)$queryADAsType[$i]=='ContractDeclaration') $Result[0]['ADAdiakiriksis']=(string)$queryADAs[$i];
						if((string)$queryADAsType[$i]=='ContractAward') $Result[0]['ADAkatakurosis']=(string)$queryADAs[$i];
						if((string)$queryADAsType[$i]=='ContractCommission') $Result[0]['ADAanathesis']=(string)$queryADAs[$i];
						//if((string)$queryADAsType[$i]=='ProcurementRequest') $Result[0]['procurements'] .=(string)$queryADAs[$i].";";
					}
					$Result[0]['relatedADAs']=1;
					
				}	
				//get contract signedDate
				$queryResult = $xml->xpath('//b:dateSigned');
				$date = new DateTime($queryResult[0]);
				$Result[0]['dateSigned'] =   $date->format('d/m/Y');
				
				//get contract issuerEmail
				$queryResult = $xml->xpath('//b:issuerEmail');
				$Result[0]['issuerEmail'] =   (isset($queryResult[0]))?(string)$queryResult[0]:"";
				
				//get contract DocumentUrl
				$queryResult = $xml->xpath('//b:documentUrl');
				$Result[0]['documentUrl'] =  $queryResult[0];
				
				//get contract awardProcedure
				$queryResult = $xml->xpath('//a:awardProcedure/attribute::idRef');
				$Result[0]['awardProcedure'] =  (int)$queryResult[0];
		
				$queryResultSince = $xml->xpath('//a:since');
				$fdate = new DateTime($queryResultSince[0]);
				$Result[0]['since'] = $fdate->format('d/m/Y');
				$queryResultUntil = $xml->xpath('//a:until');
				if(isset($queryResultUntil[0]) && $queryResultUntil[0]!=''){
					$fdate = new DateTime($queryResultUntil[0]);
					$Result[0]['until'] = $fdate->format('d/m/Y');
					$Result[0]['untilexists']=0;
					
				}else 
				{
					$Result[0]['until'] = '';	
					$Result[0]['untilexists']=1;
				}
				$Result[0]['actionPermitted'] = (strtotime("now") - strtotime($queryResultSubm[0]) < $this->actionPermitted)?1:0;
				$queryResult = $xml->xpath('//b:procurementRequest');
				for($i=0;$i<count($queryResult);$i++)
					$proc[$i] = (string)$queryResult[$i][0];
				$Result[0]['procurements']=isset($proc)?$proc:'';
				
				$queryResultCost = $xml->xpath('//a:totalCostBeforeVAT');
				$Result[0]['totalCostBeforeVAT'] = isset($queryResultCost[$i][0])?doubleval($queryResultCost[$i][0]):'';
					
				return $Result;
			
  		}catch (Exception $e){
  			return -1;
  		}
   }
	

}