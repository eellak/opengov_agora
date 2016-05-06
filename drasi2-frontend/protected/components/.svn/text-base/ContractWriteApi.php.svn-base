<?php

class ContractWriteApi{
	
	var $SchemaContractRequest = 'http://agora.opengov.gr/schema/contract-0.1';
	var $SchemaInstance = 'http://www.w3.org/2001/XMLSchema-instance';
	var $SchemaCommon ='http://agora.opengov.gr/schema/common-0.1';
	

	public function contractWrite($status){
		$xml_writer_object=new xml_writer_class;	
		$noattributes=array();
		$contractAttr['xmlns']=$this->SchemaContractRequest;
		$contractAttr['xmlns:xsi']=$this->SchemaInstance;
		$contractAttr['xmlns:c']=$this->SchemaCommon;
		$attributes=array();	
				
		if($status=='create' || $status=='extend' || $status=='changes'){
			$xml_writer_object->addtag('contracts',$contractAttr,'',$main,1);
			$attributes=array();
			$xml_writer_object->addtag('contract',$attributes,$main,$root,1);
			$attributes=array();
		}else{
			$xml_writer_object->addtag('singleContract',$contractAttr,'',$root,1);
		}
		
		//the contract Title
		$xml_writer_object->addtag('c:title',$attributes,$root,$toptag,0);
		$xml_writer_object->adddata($_POST['Contract']['title'],$toptag,$path);
		
		//i send one contract so the unique Document Code is always 1
		$xml_writer_object->addtag('c:uniqueDocumentCode',$noattributes,$root,$toptag,0);
		$xml_writer_object->adddata('1',$toptag,$path);	
		
		
		//Protocol Number Code
		$xml_writer_object->addtag('c:protocolNumberCode',$attributes,$root,$toptag,0);
		$xml_writer_object->adddata($_POST['Contract']['protocolNumberCode'],$toptag,$path);
				
		//Organization Refid
		$attributes['idRef']=Yii::app()->user->RefId;
		$xml_writer_object->addtag('c:organization',$attributes,$root,$toptag,0);
		$subattr['idRef'] = $_POST['Contract']['OrganizationIdRefUnits'];
		$xml_writer_object->adddatatag('c:unit',$subattr,'',$toptag,$datatag);
					
		//Signers
		$attributes=array();
		$xml_writer_object->addtag('c:signers',$noattributes,$root,$toptag,2);
		//Count Signers Table
		//for($i=0;$i<count($_POST['Contract']['signers']);$i++){
			$subattr['idRef']=$_POST['Contract']['signers'];
			$xml_writer_object->adddatatag('c:signer',$subattr,'',$toptag,$datatag);
		//}
				
		//diavgeia aware value
		$xml_writer_object->addtag('c:diavgeiaPublished',$noattributes,$root,$toptag,0);
		//if($_POST['Contract']['diavgeiaPublished']=='on')
		//	$xml_writer_object->adddata('true',$toptag,$path);
		//else 
			$xml_writer_object->adddata('false',$toptag,$path);
				
		//adaCode
		//$xml_writer_object->addtag('c:adaCode',$noattributes,$root,$toptag,0);
		//$xml_writer_object->adddata($_POST['Contract']['adaCode'],$toptag,$path);
				
		//Signed Date
		$xml_writer_object->addtag('c:dateSigned',$noattributes,$root,$toptag,0);
		$date = DateTime::createFromFormat('d/m/Y', $_POST['Contract']['dateSigned']);              
		$date = $date->format('Y-m-d');
		$xml_writer_object->adddata($date,$toptag,$path);
		
		$xml_writer_object->addtag('c:relatedAdas',$noattributes,$root,$toptag,0);
		
		
		if(isset($_POST['Contract']['procurements']) && $_POST['Contract']['procurements']!='' ){
			$ProcIds = explode(";", $_POST['Contract']['procurements']);
			for($i=0;$i<count($ProcIds);$i++){
				if($ProcIds[$i]=='') continue;
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$ProcIds[$i],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'ProcurementRequest',$toptag2,$datatag);	
			}
		}else{
			
			if(isset($_POST['Contractitem'])){
				$kk=$ll=0;
				$procurementR=array();
				$PaymentsKeys = array_keys($_POST['Contractitem']);
				if($_POST['Contractitem'][$PaymentsKeys[0]]['documentUrl']!=''){
				for($i=0;$i<count($PaymentsKeys);$i++){
					if(isset($_POST['Contractitem'][$PaymentsKeys[$i]]['documentUrl']) && $_POST['Contractitem'][$PaymentsKeys[$i]]['documentUrl']!=''){
							$procurementR[$ll] = $_POST['Contractitem'][$PaymentsKeys[$i]]['documentUrl'];
							$ll++;
					}
				}
				$result2 = array_unique($procurementR);
					foreach ($result2 as $nums => $value){
					//for($nums=0;$nums<count($result2);$nums++){
						$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
						$subattr = array();
						$xml_writer_object->adddatatag('c:adaCode',$subattr,$result2[$nums],$toptag2,$datatag);
						$xml_writer_object->adddatatag('c:adaType',$subattr,'ProcurementRequest',$toptag2,$datatag);	
					}
				}	
			}
		}
		
		if(isset($_POST['Contractitem'])){
			$ContractKeys = array_keys($_POST['Contractitem']);
			if ($_POST['Contractitem'][$ContractKeys[0]]['notice']){
		 		
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Contractitem'][$ContractKeys[0]]['notice'],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'Notice',$toptag2,$datatag);	
		 	}
		}
		
		if(isset($_POST['Contract']['ADAdiakiriksis']) && $_POST['Contract']['ADAdiakiriksis']!=''){
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Contract']['ADAdiakiriksis'],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'ContractDeclaration',$toptag2,$datatag);	
		}
		if(isset($_POST['Contract']['ADAkatakurosis']) &&  $_POST['Contract']['ADAkatakurosis']!=''){
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Contract']['ADAkatakurosis'],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'ContractAward',$toptag2,$datatag);	
		}
		if(isset($_POST['Contract']['ADAanathesis']) && $_POST['Contract']['ADAanathesis']!=''){
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Contract']['ADAanathesis'],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'ContractCommission',$toptag2,$datatag);	
		}
		if(isset($_POST['Contract']['ADAtropopoihshs']) &&  $_POST['Contract']['ADAtropopoihshs']!=''){
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Contract']['ADAtropopoihshs'],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'ContractChanges',$toptag2,$datatag);	
		}
		if(isset($_POST['Contract']['ADAextends']) &&  $_POST['Contract']['ADAextends']!=''){
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Contract']['ADAextends'],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'ContractExtends',$toptag2,$datatag);	
		}
		
		//else
		//$xml_writer_object->adddata("",$toptag,$path);
		
		//issuerEmail
		if(isset($_POST['Contract']['issuerEmail'])){
			$xml_writer_object->addtag('c:issuerEmail',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Contract']['issuerEmail'],$toptag,$path);
		}
		//the Document code	
		if(isset($_FILES['Contract']['name']['document']) && $_FILES['Contract']['name']['document']!=''){ 
			$the_temp_file = $_FILES['Contract']['tmp_name']['document'];
			$the_file = str_replace(" ","_",$_FILES['Contract']['name']['document']);
			$http_error = $_FILES['Contract']['error']['document'];
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
		}else 
			$fileEncoded ="";
	
		/* Add Document*/
		if(isset($_FILES['Contract']['name']['document']) && $_FILES['Contract']['name']['document']!=''){
			$attributes=array();
			$xml_writer_object->addtag('c:document',$noattributes,$root,$toptag1,1);
			$xml_writer_object->adddata($fileEncoded,$toptag1,$path);
		}
		
		//the selected contractingAuthority
		$attributes['idRef']=$_POST['Contract']['contractingAuthority'];
		$xml_writer_object->addtag('contractingAuthority',$attributes,$root,$toptag,2);

		//the selected contractingAuthority
		$attributes['idRef']=$_POST['Contract']['awardProcedure'];
		$xml_writer_object->addtag('awardProcedure',$attributes,$root,$toptag,1);
		//the selected contractType
		//$attributes['idRef']=$_POST['Contract']['contractType'];
		$attributes['idRef']=$_POST['Contract']['contractType'];
		$xml_writer_object->addtag('contractType',$attributes,$root,$toptag4,0);
		//the selected c:commissionCriteria
		//$attributes['idRef']=$_POST['Contract']['commissionCriteria'];
		$attributes['idRef']=$_POST['Contract']['commissionCriteria'];
		$xml_writer_object->addtag('commissionCriteria',$attributes,$root,$toptag,0);
		
		
		//Secondary Party
		if(isset($_POST['Signers'])){
			$attributes=array();
			$xml_writer_object->addtag('secondaryParties',$attributes,$root,$toptag1,3);
			$SignersKeys = array_keys($_POST['Signers']);
		
		for($i=0;$i<count($SignersKeys);$i++){
		$xml_writer_object->addtag('c:party',$attributes,$toptag1,$toptag2,4);
			$subattr = array();
			$xml_writer_object->adddatatag('c:name',$subattr,$_POST['Signers'][$SignersKeys[$i]]['Secname'],$toptag2,$datatag);
			$xml_writer_object->adddatatag('c:afm',$subattr,$_POST['Signers'][$SignersKeys[$i]]['Secafm'],$toptag2,$datatag);
			$attrCountry['idRef']=$_POST['Signers'][$SignersKeys[$i]]['Seccountry'];
			$xml_writer_object->adddatatag('c:country',$attrCountry,'',$toptag2,$datatag);	
		}
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
							
				if(isset($_POST['Contractitem'][$ContractKeys[$i]]['documentUrl']) && $_POST['Contractitem'][$ContractKeys[$i]]['documentUrl']!='' && isset($_POST['Contract']['procurements']))
					$xml_writer_object->adddatatag('c:procurementRequest',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['documentUrl'],$top2,$datatag);	
				if((isset($_POST['Contract']['noticeADA']) && $_POST['Contract']['noticeADA']!='')
				 )
					$xml_writer_object->adddatatag('c:notice',$noattributes,$_POST['Contract']['noticeADA'],$top2,$datatag);	
				else if((isset($_POST['Contractitem'][$ContractKeys[$i]]['notice']) && $_POST['Contractitem'][$ContractKeys[$i]]['notice']!=''))
					$xml_writer_object->adddatatag('c:notice',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['notice'],$top2,$datatag);	
				
				$xml_writer_object->adddatatag('c:address',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['address'],$top2,$datatag);	
				$xml_writer_object->adddatatag('c:addressNo',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['addressNo'],$top2,$datatag);	
				$xml_writer_object->adddatatag('c:addressPostal',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['addressPostal'],$top2,$datatag);	
				$xml_writer_object->adddatatag('c:nuts',$noattributes,'',$top2,$datatag);
				$xml_writer_object->adddatatag('c:city',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['city'],$top2,$datatag);	
				$subattr['idRef']=$_POST['Contractitem'][$ContractKeys[$i]]['countryOfDelivery'];
				$xml_writer_object->adddatatag('c:countryOfDelivery',$subattr,'',$top2,$datatag);
				$subattr['idRef']=$_POST['Contractitem'][$ContractKeys[$i]]['countryProduced'];
				$xml_writer_object->adddatatag('c:countryProduced',$subattr,'',$top2,$datatag);	
			
			}
		
		}

		//Since Date
		$xml_writer_object->addtag('since',$noattributes,$root,$toptag,0);
		$date = date_create_from_format('d/m/Y',$_POST['Contract']['since']);
		$date = date_format($date, 'Y-m-d');
		$xml_writer_object->adddata($date,$toptag,$path);
		//print_r($_POST);
		if(isset($_POST['Contract']['until']) && $_POST['Contract']['until']!='' && $_POST['Contract']['untilexists']==0){
			$xml_writer_object->addtag('until',$noattributes,$root,$toptag,0);
			$date = date_create_from_format('d/m/Y',$_POST['Contract']['until']);
			$date = date_format($date, 'Y-m-d');
			$xml_writer_object->adddata($date,$toptag,$path);
			
		}
		$xml_writer_object->addtag('contractPlace',$noattributes,$root,$toptag,0);
		$xml_writer_object->adddata(Yii::app()->user->OrganisationAddressPostal,$toptag,$path);
		
		if( $status=='changes'){
			$xml_writer_object->addtag('changesContract',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Contract']['documentUrl'],$toptag,$path);
		}
		if(isset($_POST['Contract']['changesContract'])){
			$xml_writer_object->addtag('changesContract',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Contract']['documentUrl'],$toptag,$path);
		}
		if( $status=='extend'){
			$xml_writer_object->addtag('extendsContract',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Contract']['documentUrl'],$toptag,$path);
		}
		
		if(isset($_POST['Contract']['extendsContract'])){
			$xml_writer_object->addtag('extendsContract',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Contract']['extendsContract'],$toptag,$path);
		}
		
		/*CoFunded*/
		
	if(isset($_POST['Contract']['CofinancedCheckBox'])){
			if($_POST['Contract']['CofinancedCheckBox']==1){
				$xml_writer_object->addtag('coFunded',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata('true',$toptag,$path);
			}else {
				$xml_writer_object->addtag('coFunded',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata('false',$toptag,$path);
			
			}
		}
		if(isset($_POST['Contract']['PublicFundsCheckBox'])){
			if($_POST['Contract']['PublicFundsCheckBox']==1){
				$xml_writer_object->addtag('fundedFromPIP',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata('true',$toptag,$path);
			}else {
				$xml_writer_object->addtag('fundedFromPIP',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata('false',$toptag,$path);
			}
		}

		if(isset($_POST['Contract']['CofinancedCheckBox'])){
			if($_POST['Contract']['CofinancedCheckBox']==1){
				$xml_writer_object->addtag('codeCoFunded',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata($_POST['Contract']['CofinancedOPS'],$toptag,$path);
				
			}else {
				$xml_writer_object->addtag('codeCoFunded',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata('',$toptag,$path);
			}
		}
		if(isset($_POST['Contract']['PublicFundsCheckBox'])){
			if($_POST['Contract']['PublicFundsCheckBox']==1){
				$xml_writer_object->addtag('codePIP',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata($_POST['Contract']['PublicFundsSAE'],$toptag,$path);
				
			}else {
				$xml_writer_object->addtag('codePIP',$noattributes,$root,$toptag,0);
				$xml_writer_object->adddata('',$toptag,$path);
			}
		}
		
		if(!$xml_writer_object->write($final_xml))
			echo ('Error: '.$xml_writer_object->error);
			
			$config['username']=Yii::app()->user->UserName;
			$config['password']=Yii::app()->user->password;
			$config['data'] =$final_xml;
			$config['filesize'] =strlen($final_xml);
		//	echo $final_xml;
		/*	$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
			$WriteToFile = $Path."protected/data/files/final.xml";
			$fh = fopen($WriteToFile, 'w') or die("can't open file");
			file_put_contents($WriteToFile, $final_xml);
			*/
			if($status=='create' || $status=='extend' || $status=='changes'){
				$config['apirequest'] ='contractcreate';
				$api = new Apiconnector($config);
				$output = $api->create('contract');
			}else if($status=='update'){
				$config['apirequest'] ='contractUpdate';
				$config['documentid'] = $_GET['uniqueDocumentCode'];
				$api = new Apiconnector($config);
				$output = $api->update('contract');
			}
		
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
}