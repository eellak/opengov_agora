<?php

class PaymentsWriteApi{
	
	var $SchemaPaymentRequest = 'http://agora.opengov.gr/schema/payment-0.1';
	var $SchemaInstance = 'http://www.w3.org/2001/XMLSchema-instance';
	var $SchemaCommon ='http://agora.opengov.gr/schema/common-0.1';
	
	
	public function PaymentsWrite($status){
		$xml_writer_object=new xml_writer_class;	
		$noattributes=array();
		$PaymentsAttr['xmlns']=$this->SchemaPaymentRequest;
		$PaymentsAttr['xmlns:xsi']=$this->SchemaInstance;
		$PaymentsAttr['xmlns:c']=$this->SchemaCommon;
		$attributes=array();	
				
		if($status=='create'){
			$xml_writer_object->addtag('payments',$PaymentsAttr,'',$main,1);
			$attributes=array();
			$xml_writer_object->addtag('payment',$attributes,$main,$root,1);
			$attributes=array();
		}else{
			$xml_writer_object->addtag('singlePayment',$PaymentsAttr,'',$root,1);
		}
		
		//the Payments Title
		$xml_writer_object->addtag('c:title',$attributes,$root,$toptag,0);
		$xml_writer_object->adddata($_POST['Payments']['title'],$toptag,$path);
		
		//i send one Payments so the unique Document Code is always 1
		$xml_writer_object->addtag('c:uniqueDocumentCode',$noattributes,$root,$toptag,0);
		$xml_writer_object->adddata('1',$toptag,$path);	
		
		
		//Protocol Number Code
		$xml_writer_object->addtag('c:protocolNumberCode',$attributes,$root,$toptag,0);
		$xml_writer_object->adddata($_POST['Payments']['protocolNumberCode'],$toptag,$path);
				
		//Organization Refid
		$attributes['idRef']=Yii::app()->user->RefId;
		$xml_writer_object->addtag('c:organization',$attributes,$root,$toptag,0);
		$subattr['idRef'] = $_POST['Payments']['OrganizationIdRefUnits'];
		$xml_writer_object->adddatatag('c:unit',$subattr,'',$toptag,$datatag);
					
		//Signers
		$attributes=array();
		$xml_writer_object->addtag('c:signers',$noattributes,$root,$toptag,2);
		//Count Signers Table
		//for($i=0;$i<count($_POST['Payments']['signers']);$i++){
			$subattr['idRef']=$_POST['Payments']['signers'];
			$xml_writer_object->adddatatag('c:signer',$subattr,'',$toptag,$datatag);
		//}
				
		//diavgeia aware value
		$xml_writer_object->addtag('c:diavgeiaPublished',$noattributes,$root,$toptag,0);
		$xml_writer_object->adddata('false',$toptag,$path);
				
		//Signed Date
		$xml_writer_object->addtag('c:dateSigned',$noattributes,$root,$toptag,0);
		$date = DateTime::createFromFormat('d/m/Y', $_POST['Payments']['dateSigned']);              
		$date = $date->format('Y-m-d');
		$xml_writer_object->adddata($date,$toptag,$path);
		
		$xml_writer_object->addtag('c:relatedAdas',$noattributes,$root,$toptag,0);
		if(isset($_POST['Payments']['RelatedADA']) && $_POST['Payments']['RelatedADA']!=''){
			$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
			$subattr = array();
			$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Payments']['RelatedADA'],$toptag2,$datatag);
			$xml_writer_object->adddatatag('c:adaType',$subattr,'PaymentDeclaration',$toptag2,$datatag);	
		}
		/*
		if(isset($_POST['Contractitempayments'])){
			$kk=$ll=0;
			$PaymentsKeys = array_keys($_POST['Contractitempayments']);
			for($i=0;$i<count($PaymentsKeys);$i++){
				if(isset($_POST['Contractitempayments'][$PaymentsKeys[$i]]['notice'])){
					$notices[$kk] = $_POST['Contractitempayments'][$PaymentsKeys[$i]]['notice'];
					$kk++;
				}
				if(isset($_POST['Contractitempayments'][$PaymentsKeys[$i]]['documentUrl'])){
					$procurementR[$ll] = $_POST['Contractitempayments'][$PaymentsKeys[$i]]['documentUrl'];
					$ll++;
				}
			}
			
				$result = array_unique($notices);
				foreach ($result as $nums => $value){
					$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
					$subattr = array();
					$xml_writer_object->adddatatag('c:adaCode',$subattr,$result[$nums],$toptag2,$datatag);
					$xml_writer_object->adddatatag('c:adaType',$subattr,'Notice',$toptag2,$datatag);	
				}	
		
				$result2 = array_unique($procurementR);
				foreach ($result2 as $nums => $value){
					$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
					$subattr = array();
					$xml_writer_object->adddatatag('c:adaCode',$subattr,$result2[$nums],$toptag2,$datatag);
					$xml_writer_object->adddatatag('c:adaType',$subattr,'ProcurementRequest',$toptag2,$datatag);	
				}	
			
		}
		*/
	
	/*	if(isset($_POST['Payments']['contract'])){
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Payments']['contract'],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'Contract',$toptag2,$datatag);	
		}*/
		
		
		//else
		//$xml_writer_object->adddata("",$toptag,$path);
				
		//issuerEmail
		if(isset($_POST['Payments']['issuerEmail'])){
			$xml_writer_object->addtag('c:issuerEmail',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Payments']['issuerEmail'],$toptag,$path);
		}
		//the Document code	
		if(isset($_FILES['Payments']['name']['document']) && $_FILES['Payments']['name']['document']!='' && $_POST['Payments']['documentURlFromDiavgeia']==''
				 ){ 
			$the_temp_file = $_FILES['Payments']['tmp_name']['document'];
			$the_file = str_replace(" ","_",$_FILES['Payments']['name']['document']);
			$http_error = $_FILES['Payments']['error']['document'];
			$replace=""; //what you want to replace the bad characters with
			$pattern="/([[:alnum:]_\.-]*)/"; //basically all the filename-safe characters
			$bad_chars=preg_replace($pattern,$replace,$the_file); //leaves only the "bad" characters
			$bad_arr=str_split($bad_chars); //split them up into an array for the str_replace() func.
			$the_file=str_replace($bad_arr,$replace,$the_file);
					
			if($http_error==0){
				$target_path =str_replace("index.php", "", Yii::app()->request->scriptFile);
					$target_path .="/protected/data/files/".  $the_file;
				move_uploaded_file($the_temp_file, $target_path);
			}else echo "There was an error uploading the file, please try again!";
				$fh = fopen($target_path, 'r+');
				$contents = fread($fh, filesize($target_path));
				fclose($fh);
				$fileEncoded = base64_encode($contents);
		}else if ($_POST['Payments']['documentURlFromDiavgeia']!=''){
					/*			$opts = array(
				  
				);
					$context = stream_context_create($opts);
					// Open the file using the HTTP headers set above
					$file = file_get_contents($_POST['Procurement']['documentURlFromDiavgeia']);
				//$result = file_get_contents($fn,false,$_POST['Procurement']['documentURlFromDiavgeia']); 
				//print_r($file);*/
					// Open the file to get existing content
					$file = file_get_contents($_POST['Payments']['documentURlFromDiavgeia']);
					$target_path =str_replace("index.php", "", Yii::app()->request->scriptFile);
					$target_path .="protected/data/files/test.pdf";	
					file_put_contents($target_path,$file);
					$fh = fopen($target_path, 'r+');
					$contents = fread($fh, filesize($target_path));
					fclose($fh);
					$fileEncoded =base64_encode($contents);
					
			}else
			$fileEncoded ="";
	
	//		$fileEncoded ="";
	
		/* Add Document*/
		if((isset($_FILES['Payments']['name']['document']) && $_FILES['Payments']['name']['document']!='')
		|| ($_POST['Payments']['documentURlFromDiavgeia']!=''))
		{
			$attributes=array();
			$xml_writer_object->addtag('c:document',$noattributes,$root,$toptag1,1);
			$xml_writer_object->adddata($fileEncoded,$toptag1,$path);
		}
		
		if(isset($_POST['Payments']['contract']) && $_POST['Payments']['contract']!='' ){
			$subattr = array();
			$xml_writer_object->addtag('contractId',$subattr,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Payments']['contract'],$toptag,$path);	
		}
		//ContractItem payments DATA*/
		if(isset($_POST['Contractitempayments'])){
			$xml_writer_object->addtag('paymentItems',$noattributes,$root,$top,3);
			$PaymentsKeys = array_keys($_POST['Contractitempayments']);
			
			for($i=0;$i<count($PaymentsKeys);$i++){
				$xml_writer_object->addtag('paymentItem',$noattributes,$top,$top1,3);
				
				$xml_writer_object->addtag('contractItem',$noattributes,$top1,$top2,4);	
				$xml_writer_object->adddatatag('c:quantity',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['quantity'],$top2,$datatag);
				$attrUnit['idRef']=$_POST['Contractitempayments'][$PaymentsKeys[$i]]['units_of_measure'];
				$xml_writer_object->adddatatag('c:unitOfMeasure',$attrUnit,'',$top2,$datatag);
				$xml_writer_object->addtag('c:cost',$noattributes,$top2,$top3,4);
				$xml_writer_object->adddatatag('c:costBeforeVat',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['cost'],$top3,$datatag);
				$xml_writer_object->adddatatag('c:vatPercentage',$noattributes,Lookup::item('FPA',$_POST['Contractitempayments'][$PaymentsKeys[$i]]['vatid']),$top3,$datatag);
				
				$attrCur['idRef']=$_POST['Contractitempayments'][$PaymentsKeys[$i]]['currencyid'];
				$xml_writer_object->adddatatag('c:currency',$attrCur,'',$top3,$datatag);
			
				$attributes=array();
				if(isset($_POST['Contractitempayments'][$PaymentsKeys[$i]]['cpvsid'])){
					$xml_writer_object->addtag('c:cpvCodes',$attributes,$top2,$top4,4);
					$Cpvs = explode(";", $_POST['Contractitempayments'][$PaymentsKeys[$i]]['cpvsid']);
					for($j=0;$j<count($Cpvs) && $Cpvs[$j]!='';$j++){
						$newItem = explode("[", $Cpvs[$j]);
						if(count($newItem)>1){
							$finalItem = explode("]",$newItem[1]);
							$xml_writer_object->adddatatag('c:cpv',$noattributes,$finalItem[0],$top4,$datatag);
						}else continue;
					}
				}
				
				if(isset($_POST['Contractitempayments'][$PaymentsKeys[$i]]['KAE'])){
					$xml_writer_object->addtag('c:kaeCodes',$attributes,$top2,$top4,4);
					$KAE = explode(",", $_POST['Contractitempayments'][$PaymentsKeys[$i]]['KAE']);
					for($j=0;$j<count($KAE);$j++){
						if($KAE[$j]=='') continue;
					
						$xml_writer_object->adddatatag('c:kae',$noattributes,$KAE[$j],$top4,$datatag);
					}
				}
				$subattr=array();
				$xml_writer_object->adddatatag('c:description',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['description'],$top2,$datatag);	

				if(isset($_POST['Contractitempayments'][$PaymentsKeys[$i]]['documentUrl']) && $_POST['Contractitempayments'][$PaymentsKeys[$i]]['documentUrl']!='')
					$xml_writer_object->adddatatag('c:procurementRequest',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['documentUrl'],$top2,$datatag);	
				
				if(isset($_POST['Contractitempayments'][$PaymentsKeys[$i]]['notice']) && $_POST['Contractitempayments'][$PaymentsKeys[$i]]['notice']!='')
					$xml_writer_object->adddatatag('c:notice',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['notice'],$top2,$datatag);	
				
				if(isset($_POST['Payments']['contract']) && $_POST['Payments']['contract']!='')
					$xml_writer_object->adddatatag('c:contract',$noattributes,$_POST['Payments']['contract'],$top2,$datatag);	
				
				$xml_writer_object->adddatatag('c:address',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['address'],$top2,$datatag);	
				$xml_writer_object->adddatatag('c:addressNo',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['addressNo'],$top2,$datatag);	
				$xml_writer_object->adddatatag('c:addressPostal',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['addressPostal'],$top2,$datatag);	
				$xml_writer_object->adddatatag('c:nuts',$noattributes,'',$top2,$datatag);	
				$xml_writer_object->adddatatag('c:city',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['city'],$top2,$datatag);	
				
				$subattr['idRef']=$_POST['Contractitempayments'][$PaymentsKeys[$i]]['countryOfDelivery'];
				$xml_writer_object->adddatatag('c:countryOfDelivery',$subattr,'',$top2,$datatag);	
				$subattr['idRef']=$_POST['Contractitempayments'][$PaymentsKeys[$i]]['countryProduced'];
				$xml_writer_object->adddatatag('c:countryProduced',$subattr,'',$top2,$datatag);	
				$xml_writer_object->adddatatag('c:invoiceNumber',$noattributes,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['invoice'],$top2,$datatag);
				//print_r($_POST['Contractitempayments']);
			//Secondary Party
			if(isset($_POST['Contractitempayments'][$PaymentsKeys[$i]]['Secafm'])){
				$subattr=array();
				$xml_writer_object->addtag('contractParty',$attributes,$top1,$toptag1,3);
				$xml_writer_object->adddatatag('c:name',$subattr,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['Secname'],
					$toptag1,$datatag);
				$xml_writer_object->adddatatag('c:afm',$subattr,$_POST['Contractitempayments'][$PaymentsKeys[$i]]['Secafm'],$toptag1,$datatag);
				
				$attrCountry['idRef']=$_POST['Contractitempayments'][$PaymentsKeys[$i]]['Seccountry'];
				$xml_writer_object->adddatatag('c:country',$attrCountry,'',$toptag1,$datatag);	
			}
			if(isset($_POST['Contractitempayments'][$PaymentsKeys[$i]]['responsibilityAssumptionCode'])){
				$subattr=array();
				$xml_writer_object->adddatatag('responsibilityAssumptionCode',$subattr,
								$_POST['Contractitempayments'][$PaymentsKeys[$i]]['responsibilityAssumptionCode'],
								$top1,$toptag1);
			}
			}
		}

		if(isset($_POST['Payments']['responsibilityAssumptionCode'])){
			$xml_writer_object->addtag('responsibilityAssumptionCode',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Payments']['responsibilityAssumptionCode'],$toptag,$path);
		}
		if(isset($_POST['Payments']['invoice'])){
			$xml_writer_object->addtag('invoiceNumber',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Payments']['invoice'],$toptag,$path);
		}
		//Primary Party Starts
		$attributes=array();
		$xml_writer_object->addtag('primaryParty',$attributes,$root,$toptag,1);
		$subattr = array();

		//Take care.. we want a person and the name of him .
		//So.. the idea is to take the Signers[0]
		$xml_writer_object->adddatatag('c:name',$subattr,Units::item('Signers',$_POST['Payments']['signers'],Yii::app()->user->RefId),$toptag,$datatag);
		$xml_writer_object->adddatatag('c:afm',$subattr,Yii::app()->user->OrganisationAfm,$toptag,$datatag);
		$attrCountry['idRef']="GR";
		$xml_writer_object->adddatatag('c:country',$attrCountry,'',$toptag,$datatag);	
		$attributes=array();
		
		if(!$xml_writer_object->write($final_xml))
			echo ('Error: '.$xml_writer_object->error);
		
			$config['username']=Yii::app()->user->UserName;
			$config['password']=Yii::app()->user->password;
			$config['data'] =$final_xml;
			$config['filesize'] =strlen($final_xml);
			
			/*echo $final_xml;
			$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
			$WriteToFile = $Path."protected/data/files/final.xml";
			$fh = fopen($WriteToFile, 'w') or die("can't open file");
			file_put_contents($WriteToFile, $final_xml);*/
			if($status=='create' || $status=='extend'){
				$config['apirequest'] ='Paymentscreate';
				//send a curl request for the document 
				$api = new Apiconnector($config);
				$output = $api->create('payment');
			}else if($status=='update'){
				$config['apirequest'] ='PaymentsUpdate';
				$config['documentid'] = $_GET['documentUrl'];
				
				$api = new Apiconnector($config);
				$output = $api->update('payment');				
			}
			
		//print_r($output);
			//print_r($config);
			//echo $final_xml;
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
		 * xmlns:ns3="http://agora.opengov.gr/schema/Payments-0.1" 
		 * xmlns:ns4="http://agora.opengov.gr/schema/auth-0.1" 
		 * xmlns:ns5="http://agora.opengov.gr/schema/payment-0.1">
		 * <storagereferences>
		 * <validationerrors><error><field>adaCode</field><xpath>Paymentss/Payments[0]/adaCode</xpath><msg>Invalid ADA code!.</msg></error></validationerrors></storagereferences></storedecisionresponse>*/
		//$ReqTitle = $xml->xpath('//a:field');
		//print_r($ReqTitle);
	}
}