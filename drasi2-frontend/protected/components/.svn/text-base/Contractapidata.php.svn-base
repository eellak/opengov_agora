<?php
class Contractapidata{

   /** Version.*/
  const VERSION = '1.1.0';
  	
   /**
   * Default options for curl.
   */
  
  var $ContractUrlSchema;
  var $ContractUrlCommonSchema;
  //a parameter whether an action is permitted or not (less tha 24 hours)
  var $actionPermitted;
  
  
  public function __construct(){
 		$this->ContractUrlSchema  = Yii::app()->params['agora']['contract'];
		$this->ContractUrlCommonSchema = Yii::app()->params['agora']['common'];
		$this->actionPermitted = Yii::app()->params['parameters']['actionPermitted'];
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
			$xml->registerXPathNamespace('a',$this->ContractUrlSchema);
			$xml->registerXPathNamespace('b',$this->ContractUrlCommonSchema);
			$queryResultReturned = $xml->xpath('//b:total');
		
		  	if($queryResultReturned[0]>0)  return (int)$queryResultReturned[0];
			else return 0;
	  	}catch (Exception $e){
	  		return 0;
	  	}
	  }
	  
  		public function getShortList($output){
  		
  			try{
	  			$xml = new SimpleXMLElement($output);
			
				$xml->registerXPathNamespace('a',$this->ContractUrlSchema);
				$xml->registerXPathNamespace('b',$this->ContractUrlCommonSchema);
				
				$queryResultReturned = $xml->xpath('//b:returned');
				if($queryResultReturned[0]>0){
					$queryResultSubm = $xml->xpath('//b:submissionTime');	
					$queryResultModified = $xml->xpath('//b:lastModifiedTime');
					$queryResultDeleted = $xml->xpath('//b:deleted');
					$queryResultuniqueDocumentCode = $xml->xpath('//b:uniqueDocumentCode');
					$queryResultTitle = $xml->xpath('//b:title');
					$queryResultprotocolNumberCode = (string)$xml->xpath('//b:protocolNumberCode');
					//$queryADA = $xml->xpath('//b:adaCode');
					$queryContractdocumentUrl = $xml->xpath('//b:documentUrl');
					$queryResultContractDate = $xml->xpath('//b:dateSigned');
					$queryResultSince = $xml->xpath('//a:since');
					$queryResultUntil = $xml->xpath('//a:until');
					$queryCancelled = $xml->xpath('//b:cancelled');
					$querycancelledReason = $xml->xpath('//b:cancelledReason');
					$querycancellationType = $xml->xpath('//b:cancellationType/attribute::idRef');
					$contract = new Contract();
					$k=0;
					for($i=0;$i<$queryResultReturned[0];$i++){	
						$contract->title[$i] = (string)$queryResultTitle[$i][0] ;
						$contract->uniqueDocumentCode[$i] = (string)$queryContractdocumentUrl[$i][0];
						$fdate = new DateTime($queryResultContractDate[$i][0]);
						$contract->dateSigned[$i] = $fdate->format('d/m/Y');
						$contract->protocolNumberCode[$i] = (string)$queryResultuniqueDocumentCode[$i][0];
						$contract->deleted[$i] = (bool)$queryResultDeleted[$i][0];
				
						$contract->actionPermitted[$i] = (strtotime("now") - strtotime($queryResultSubm[$i][0]) < $this->actionPermitted)?1:0;
						$id = $queryContractdocumentUrl[$i][0];
						$queryResultApproved = $xml->xpath("//a:contract[@id='$id']/a:changesContract");
						$contract->changesContract[$i] = isset($queryResultApproved[0])?(string)$queryResultApproved[0]:'';
						$queryResultApproved = $xml->xpath("//a:contract[@id='$id']/a:extendsContract");
						$contract->extendsContract[$i] = isset($queryResultApproved[0])?(string)$queryResultApproved[0]:'';
						$contract->cancelled[$i] = isset($queryCancelled[$i][0])?(string)$queryCancelled[$i][0]:'false';
						if($contract->cancelled[$i]=='true'){
							$contract->cancelledReason[$i] = isset($querycancelledReason[$k][0])?(string)$querycancelledReason[$k][0]:'';
							$contract->cancellationType[$i] = isset($querycancellationType[$k]['idRef'])?(int)$querycancellationType[$k]['idRef']:'';
							$k++;
						}else{
								$contract->cancelledReason[$i] =$contract->cancellationType[$i]='';
						}
					}
					return $contract;
				
				}else return -1;
  			}catch (Exception $e){
  				return -1;
  			}
	  	
  }
 public function getShortListTable($output){
 		
 	  	$Results = array();
 	  	
  		if($output==-1) return $Results;
  		try{
	  		$xml = new SimpleXMLElement($output);
			$xml->registerXPathNamespace('a',$this->ContractUrlSchema);
			$xml->registerXPathNamespace('b',$this->ContractUrlCommonSchema);
			
			$queryResultReturned = $xml->xpath('//b:returned');
			if($queryResultReturned[0]>0){
				$queryResultSubm = $xml->xpath('//b:submissionTime');	
				$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
				
				$queryResultModified = $xml->xpath('//b:lastModifiedTime');
				$queryResultDeleted = $xml->xpath('//b:deleted');
				$queryResultuniqueDocumentCode = $xml->xpath('//b:uniqueDocumentCode');
				$queryResultTitle = $xml->xpath('//b:title');
				$queryResultprotocolNumberCode = (string)$xml->xpath('//b:protocolNumberCode');
				//$queryADA = $xml->xpath('//b:adaCode');
				$queryContractdocumentUrl = $xml->xpath('//b:documentUrl');
				$queryResultContractDate = $xml->xpath('//b:dateSigned');
				$queryResultSince = $xml->xpath('//a:since');
				$queryResultUntil = $xml->xpath('//a:until');
				$queryResultChanges = $xml->xpath('//a:changesContract');
				$queryCancelled = $xml->xpath('//b:cancelled');
				$querycancelledReason = $xml->xpath('//b:cancelledReason');
				$querycancellationType = $xml->xpath('//b:cancellationType/attribute::idRef');
				$queryTotalcostBeforeVat = $xml->xpath('//a:totalCostBeforeVAT');
				
				$k=0;
				for($i=0;$i<$queryResultReturned[0];$i++){				
					$Results[$i]['title'] = $queryResultTitle[$i][0] ;
					$Results[$i]['titleF'] = $queryResultTitle[$i][0] ."<br/><b>Μοναδικό ID:</b> ". $queryContractdocumentUrl[$i][0];
					$Results[$i]['OrganizationIdRef'] = (int)$queryOrganizationIdRef[$i]['idRef'];
					$Results[$i]['OrganisationName'] =Units::item("Organization", $Results[$i]['OrganizationIdRef'],$Results[$i]['OrganizationIdRef']);
					
					$Results[$i]['uniqueDocumentCode'] = (string)$queryContractdocumentUrl[$i][0];
					$fdate = new DateTime($queryResultContractDate[$i][0]);
					$Results[$i]['dateSigned'] = $fdate->format('d/m/Y');
					$fdate = new DateTime($queryResultModified[$i][0]);
					$Results[$i]['lastModifiedTime'] = $fdate->format('d/m/Y');
					$fdate = new DateTime($queryResultSubm[$i][0]);
					$Results[$i]['submissionTime'] = $fdate->format('d/m/Y');
					$Results[$i]['totalCostBeforeVAT'] =  isset($queryTotalcostBeforeVat[$i][0])?$queryTotalcostBeforeVat[$i][0]:0;
					$Results[$i]['protocolNumberCode'] = (string)$queryResultuniqueDocumentCode[$i][0];
					$Results[$i]['deleted'] = (bool)$queryResultDeleted[$i][0];
					$fdate = new DateTime($queryResultSince[$i][0]);
					$Results[$i]['since'] = $fdate->format('d/m/Y');
					if(isset($queryResultUntil[$i][0]) && $queryResultUntil[$i][0]!=''){
						$fdate = new DateTime($queryResultUntil[$i][0]);
						$Results[$i]['until'] = $fdate->format('d/m/Y');
					}else $Results[$i]['until']='';
					
					if(isset($queryResultChanges[$i][0])){
						$Results[$i]['changesContract'] = (string)$queryResultChanges[$i][0];
					}
					$Results[$i]['actionPermitted'] = (strtotime("now") - strtotime($queryResultSubm[$i][0]) < $this->actionPermitted)?1:0;
					$id = $Results[$i]['uniqueDocumentCode'] ;
					$queryResultApproved = $xml->xpath("//a:contract[@id='$id']/a:changesContract");
					$Results[$i]['changesContract'] = isset($queryResultApproved[0])?(string)$queryResultApproved[0]:'';
					$queryResultApproved = $xml->xpath("//a:contract[@id='$id']/a:extendsContract");
					$Results[$i]['extendsContract'] = isset($queryResultApproved[0])?(string)$queryResultApproved[0]:'';
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
  		if($output==-1) return $Result;
   		
  		try{
	  		$xml = new SimpleXMLElement($output);
		
			$xml->registerXPathNamespace('a',$this->ContractUrlSchema);
			$xml->registerXPathNamespace('b',$this->ContractUrlCommonSchema);
			
			$queryOrganizationIdRef = $xml->xpath('//b:organization/attribute::idRef');
			$Result[0]['OrganizationIdRef'] = (int)$queryOrganizationIdRef[0]['idRef'];
			$Results[0]['OrganisationName'] =Units::item("Organization", $Result[0]['OrganizationIdRef'],$Result[0]['OrganizationIdRef']);
			
		
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
		    $Result[0]['OrganizationIdRefUnits'] =  (int)$queryResult[0];
		
			$queryResult = $xml->xpath('//b:signer/attribute::idRef');
			for($i=0;$i<count($queryResult);$i++)
			$Result[0]['signers'][$i] = (int)$queryResult[$i][0];
					
			//get contract diavgeiaPublished
			$queryResult = $xml->xpath('//b:diavgeiaPublished');
			$Result[0]['diavgeiaPublished'] = (string)$queryResult[0];
			
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
					if((string)$queryADAsType[$i]=='ProcurementRequest') $Result[0]['procurements'] .=(string)$queryADAs[$i].";";
					if((string)$queryADAsType[$i]=='ContractChanges') $Result[0]['ADAtropopoihshs'] .=(string)$queryADAs[$i].";";
					if((string)$queryADAsType[$i]=='ContractExtends') $Result[0]['ADAextends'] .=(string)$queryADAs[$i].";";
					
				}
				$Result[0]['relatedADAs']=1;
				
			}	
			
				
			//get contract diavgeiaPublished
			//$queryResult = $xml->xpath('//b:adaCode');
			//$Result[0]['adaCode'] =  $queryResult[0];
			
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
			
			//get contract contractingAuthority
			$queryResult = $xml->xpath('//a:contractingAuthority/attribute::idRef');
			$Result[0]['contractingAuthority'] =  (int)$queryResult[0];
			//get contract awardProcedure
			$queryResult = $xml->xpath('//a:awardProcedure/attribute::idRef');
			$Result[0]['awardProcedure'] =  (int)$queryResult[0];
			//get contract contractType
			$queryResult = $xml->xpath('//a:contractType/attribute::idRef');
			$Result[0]['contractType'] =  (int)$queryResult[0];
			//get contract commissionCriteria
			$queryResult = $xml->xpath('//a:commissionCriteria/attribute::idRef');
			$Result[0]['commissionCriteria'] =  (int)$queryResult[0];
		
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
			//contractPlace
			$queryResult = $xml->xpath('//a:contractPlace');
			$Result[0]['contractPlace'] = isset($queryResult[0])?(int)$queryResult[0]:'';
			
			//Funds
			$queryResult = $xml->xpath('//a:coFunded');
			$Result[0]['CofinancedCheckBox'] = isset($queryResult[0])?(string)$queryResult[0]:'';
			$queryResult = $xml->xpath('//a:codeCoFunded');
			$Result[0]['CofinancedOPS'] = isset($queryResult[0])?(string)$queryResult[0]:'';
			$queryResult = $xml->xpath('//a:fundedFromPIP');
			$Result[0]['PublicFundsCheckBox'] = isset($queryResult[0])?(string)$queryResult[0]:'';
			$queryResult = $xml->xpath('//a:codePIP');
			$Result[0]['PublicFundsSAE'] = isset($queryResult[0])?(string)$queryResult[0]:'';
			
			$queryResultChanges = $xml->xpath('//a:extendsContract');
	   		$Result[0]['extendsContract'] = isset($queryResultChanges[0])?(string)$queryResultChanges[0]:'';
	   		$queryResultChanges = $xml->xpath('//a:changesContract');
	   		$Result[0]['changesContract'] = isset($queryResultChanges[0])?(string)$queryResultChanges[0]:'';
			$Result[0]['actionPermitted'] = (strtotime("now") - strtotime($queryResultSubm[0]) < $this->actionPermitted)?1:0;
			
				
			return $Result;
  		}catch (Exception $e){
  			return -1;
  		}
		
   }
	

}