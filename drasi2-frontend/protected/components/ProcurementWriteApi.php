<?php

class ProcurementWriteApi{
	
	var $SchemaProcurementRequest;
	var $SchemaInstance;
	var $SchemaCommon;
	
	public function __construct(){
		$this->SchemaProcurementRequest  = Yii::app()->params['agora']['procurement'];
		$this->SchemaCommon = Yii::app()->params['agora']['common'];
		$this->SchemaInstance = Yii::app()->params['agora']['xmlSchema'];
	}
	public function procurementWrite($status){
		
				$xml_writer_object=new xml_writer_class;	
				$noattributes=array();
				$procAttr['xmlns']=$this->SchemaProcurementRequest;
				$procAttr['xmlns:xsi']=$this->SchemaInstance;
				$procAttr['xmlns:c']=$this->SchemaCommon;
				$attributes=array();	
				
				if($status!='updateProcurement'){
					$xml_writer_object->addtag('procurementRequests',$procAttr,'',$main,1);
					$xml_writer_object->addtag('request',$attributes,$main,$root,1);
					
				}else{
					$xml_writer_object->addtag('singleProcurementRequest',$procAttr,'',$root,1);
					
				}
				//title
				$attributes=array();	
				$xml_writer_object->addtag('c:title',$attributes,$root,$toptag,0);
				
				$xml_writer_object->adddata($_POST['Procurement']['title'],$toptag,$path);
				//c:uniqueDocumentCode 
				$xml_writer_object->addtag('c:uniqueDocumentCode',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata('1',$toptag,$path);	
				
				//Protocol Number Code
				$xml_writer_object->addtag('c:protocolNumberCode',$attributes,$root,$toptag,0);
				$xml_writer_object->adddata($_POST['Procurement']['protocolNumberCode'],$toptag,$path);
				
				//Organization Refid
				$attributes['idRef']=Yii::app()->user->RefId;
				$xml_writer_object->addtag('c:organization',$attributes,$root,$toptag,0);
				$subattr['idRef'] = $_POST['Procurement']['OrganizationIdRefUnits'];
				$xml_writer_object->adddatatag('c:unit',$subattr,'',$toptag,$datatag);
				
			
				//Signers
				$attributes=array();
				$xml_writer_object->addtag('c:signers',$noattributes,$root,$toptag,2);
				$subattr['idRef']=$_POST['Procurement']['signers'];
				
				$xml_writer_object->adddatatag('c:signer',$subattr,'',$toptag,$datatag);
				
				//diavgeia aware value
				$xml_writer_object->addtag('c:diavgeiaPublished',$noattributes,$root,$toptag,0);
				//if($_POST['Procurement']['diavgeiaPublished']=='on')
				//	$xml_writer_object->adddata('true',$toptag,$path);
				//else 
					$xml_writer_object->adddata('false',$toptag,$path);
				
				//Signed Date
				$xml_writer_object->addtag('c:dateSigned',$noattributes,$root,$toptag,0);
				$date = date_create_from_format('d/m/Y',$_POST['Procurement']['dateSigned']);
				$Newdate = date_format($date, 'Y-m-d');
				//$date = new DateTime($_POST['Procurement']['dateSigned']);
				//$date = $date->format('Y-m-d');
				$xml_writer_object->adddata($Newdate,$toptag,$path);
				
				$xml_writer_object->addtag('c:relatedAdas',$noattributes,$root,$toptag,0);
		
		
				if(isset($_POST['Procurement']['ADA']) && $_POST['Procurement']['ADA']!='' && $status=='approveRequest'){
					$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
					$subattr = array();
					$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Procurement']['ADA'],$toptag2,$datatag);
					$xml_writer_object->adddatatag('c:adaType',$subattr,'ProcurementDeclaration',$toptag2,$datatag);
				}
				
				if(isset($_FILES['Procurement']['name']['document']) &&  $_FILES['Procurement']['name']['document']!=''
					&& $_POST['Procurement']['documentURlFromDiavgeia']==''
				){ 
					$the_temp_file = $_FILES['Procurement']['tmp_name']['document'];
					$the_file = str_replace(' ', '', $_FILES['Procurement']['name']['document']);
					$http_error = $_FILES['Procurement']['error']['document'];
					$filesize = $_FILES['Procurement']['size']['document'];
					
					$replace=""; //what you want to replace the bad characters with
					$pattern="/([[:alnum:]_\.-]*)/"; //basically all the filename-safe characters
					$bad_chars=preg_replace($pattern,$replace,$the_file); //leaves only the "bad" characters
					$bad_arr=str_split($bad_chars); //split them up into an array for the str_replace() func.
					$the_file=str_replace($bad_arr,$replace,$the_file);
					
					if($http_error==0){
						$target_path =str_replace("index.php", "", Yii::app()->request->scriptFile);
						$target_path .="protected/data/files/".$the_file;						
						move_uploaded_file($the_temp_file, $target_path);
						$fh = fopen($target_path, 'r+');
						$contents = fread($fh, $filesize);
						fclose($fh);
						$fileEncoded = base64_encode($contents);
					}else echo "There was an error uploading the file, please try again!";
					
					
				
			}else if ($_POST['Procurement']['documentURlFromDiavgeia']!=''){
					/*			$opts = array(
				  
				);
					$context = stream_context_create($opts);
					// Open the file using the HTTP headers set above
					$file = file_get_contents($_POST['Procurement']['documentURlFromDiavgeia']);
				//$result = file_get_contents($fn,false,$_POST['Procurement']['documentURlFromDiavgeia']); 
				//print_r($file);*/
					// Open the file to get existing content
					$file = file_get_contents($_POST['Procurement']['documentURlFromDiavgeia']);
					$target_path =str_replace("index.php", "", Yii::app()->request->scriptFile);
					$target_path .="protected/data/files/test.pdf";	
					file_put_contents($target_path,$file);
					$fh = fopen($target_path, 'r+');
					$contents = fread($fh, filesize($target_path));
					fclose($fh);
					$fileEncoded =base64_encode($contents);
			}else
			$fileEncoded ="";
	
			//issuerEmail
			if(isset($_POST['Procurement']['issuerEmail'])){
				$xml_writer_object->addtag('c:issuerEmail',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata($_POST['Procurement']['issuerEmail'],$toptag,$path);
			}
			/* Add Document*/
			if((isset($_FILES['Procurement']['name']['document']) && $_FILES['Procurement']['tmp_name']['document']!='') 
				|| ($_POST['Procurement']['documentURlFromDiavgeia']!=''))
			{
				$attributes=array();
				$xml_writer_object->addtag('c:document',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata($fileEncoded,$toptag,$path);
			}
			
		
			
		//Contract Imtes	
		if(isset($_POST['Contractitem'])){
			$xml_writer_object->addtag('contractItems',$noattributes,$root,$top1,3);
			$ContractKeys = array_keys($_POST['Contractitem']);
			
			for($i=0;$i<count($ContractKeys);$i++){
				$xml_writer_object->addtag('c:item',$noattributes,$top1,$top2,4);	
				$xml_writer_object->adddatatag('c:quantity',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['quantity'],$top2,$datatag);
				$attrUnit['idRef']=$_POST['Contractitem'][$ContractKeys[$i]]['units_of_measure'];
				$xml_writer_object->adddatatag('c:unitOfMeasure',$attrUnit,'',$top2,$datatag);
				$xml_writer_object->addtag('c:cost',$noattributes,$top2,$top3,4);
				$xml_writer_object->adddatatag('c:costBeforeVat',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['cost'],$top3,$datatag);
				$xml_writer_object->adddatatag('c:vatPercentage',$noattributes,Lookup::item('FPA',$_POST['Contractitem'][$ContractKeys[$i]]['vatid']),$top3,$datatag);
				
				$attrCur['idRef']=$_POST['Contractitem'][$ContractKeys[$i]]['currencyid'];
				$xml_writer_object->adddatatag('c:currency',$attrCur,'',$top3,$datatag);
			
				
				$attributes=array();
				if(isset($_POST['Contractitem'][$ContractKeys[$i]]['cpvsid'])){
					$xml_writer_object->addtag('c:cpvCodes',$attributes,$top2,$top4,4);
					$Cpvs = explode(";", $_POST['Contractitem'][$ContractKeys[$i]]['cpvsid']);
					for($j=0;$j<count($Cpvs) && $Cpvs[$j]!='';$j++){
						$newItem = explode("[", $Cpvs[$j]);
						if(count($newItem)>1){
							$finalItem = explode("]",$newItem[1]);
							$xml_writer_object->adddatatag('c:cpv',$noattributes,$finalItem[0],$top4,$datatag);
						}else continue;
					}
				}
				
				if(isset($_POST['Contractitem'][$ContractKeys[$i]]['KAE'])){
					$xml_writer_object->addtag('c:kaeCodes',$attributes,$top2,$top4,4);
					$KAE = explode(",", $_POST['Contractitem'][$ContractKeys[$i]]['KAE']);
					for($j=0;$j<count($KAE);$j++){
						if($KAE[$j]=='') continue;
					
						$xml_writer_object->adddatatag('c:kae',$noattributes,$KAE[$j],$top4,$datatag);
					}
				}
				$subattr=array();
				$xml_writer_object->adddatatag('c:description',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['description'],$top2,$datatag);	
				
				
			}
			if($status=='approveRequest'){
				$xml_writer_object->addtag('approvesRequest',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata($_GET['documentUrl'],$toptag,$path);
				//the selected contractingAuthority
				$attributes['idRef']=$_POST['Procurement']['awardProcedure'];
				$xml_writer_object->addtag('awardProcedure',$attributes,$root,$toptag,0);
			}
			if($status=='updateProcurement' && isset($_POST['Procurement']['approvesRequest']) && $_POST['Procurement']['approvesRequest']!=''){
				$xml_writer_object->addtag('approvesRequest',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata($_POST['Procurement']['approvesRequest'],$toptag,$path);
				
			}
			if(($status=='updateProcurement' && isset($_POST['Procurement']['awardProcedure']) && $_POST['Procurement']['awardProcedure']!='')){
				$attributes['idRef']=$_POST['Procurement']['awardProcedure'];
				$xml_writer_object->addtag('awardProcedure',$attributes,$root,$toptag,0);
			}
			
			if(isset($_POST['Procurement']['responsibilityAssumptionCode'])){
				$xml_writer_object->addtag('responsibilityAssumptionCode',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata($_POST['Procurement']['responsibilityAssumptionCode'],$toptag,$path);
			}
			if(isset($_POST['Procurement']['fulfilmentDate']) && $_POST['Procurement']['fulfilmentDate']!=''){
					$xml_writer_object->addtag('fulfilmentDate',$noattributes,$root,$toptag,0);
					$date = date_create_from_format('d/m/Y',$_POST['Procurement']['fulfilmentDate']);
					$Newdate = date_format($date, 'Y-m-d');
					$xml_writer_object->adddata($Newdate,$toptag,$path);
			}
		}
		
		
			if(!$xml_writer_object->write($final_xml))
				echo ('Error: '.$xml_writer_object->error);
				//return 1;
			//echo $final_xml ."<br/>";
			//Connect to ApiConnector
			//sets the config table for the http request. 
//<singleProcurementRequest  
			
		
			$config['username']=Yii::app()->user->UserName;
			$config['password']=Yii::app()->user->password;
			$config['data'] =$final_xml;
			$config['filesize'] =strlen($final_xml);
			//echo $final_xml;	
			$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
			$WriteToFile = $Path."protected/data/files/final.xml";
			$fh = fopen($WriteToFile, 'w') or die("can't open file");
			file_put_contents($WriteToFile, $final_xml);
			
			if($status=='createProcurement'){
				$config['apirequest'] ='procurementCreate';
				//send a curl request for the document 
				$api = new Apiconnector($config);
				$output = $api->create('procurement');
			}else if($status=='updateProcurement'){
				//print_r($_POST);
				$config['apirequest'] ='procurementUpdate';
				$config['documentid'] = $_GET['documentUrl'];
				$api = new Apiconnector($config);
				//$output = $api->makeRequest();
				$output = $api->update('procurement');
			}else if($status=='approveRequest'){
				//print_r($_POST);
				$config['apirequest'] ='approveRequest';
				$config['documentid'] = $_GET['documentUrl'];
				$api = new Apiconnector($config);
				//$output = $api->makeRequest();
				$output = $api->create('procurement');
			}
		
			//print_r($output);
			//$result = $this->parseErrors($output);
			return $output;
	}
	
	/**
	 * 
	 * This function parses the result of the request
	 * and creates the error response or the success report ...
	 * @param unknown_type $output
	 */
	public function parseErrors($output){
		
		
		
		$success = "";
		$xml = new SimpleXMLElement($output);
		$xml->registerXPathNamespace('a',$this->SchemaCommon);
		
		$ReqTitle = $xml->xpath('//a:id');
		$succes = (string)$ReqTitle['0'];
		echo "<br/>to mhnyma = $success<br/>";
		$this->addError('title','Incorrect username or password.');
		
		/*<storedecisionresponse 
		 * xmlns="http://agora.opengov.gr/schema/common-0.1" 
		 * xmlns:ns2="http://agora.opengov.gr/schema/procurement-request-0.1" 
		 * xmlns:ns3="http://agora.opengov.gr/schema/contract-0.1" 
		 * xmlns:ns4="http://agora.opengov.gr/schema/auth-0.1" 
		 * xmlns:ns5="http://agora.opengov.gr/schema/payment-0.1">
		 * <storagereferences>
		 * <validationerrors><error><field>adaCode</field><xpath>contracts/contract[0]/adaCode</xpath><msg>Invalid ADA code!.</msg></error></validationerrors></storagereferences></storedecisionresponse>*/
		//$ReqTitle = $xml->xpath('//a:field');
		//print_r($ReqTitle);
	}
	
	function formatDate($date){
		
	}
}
