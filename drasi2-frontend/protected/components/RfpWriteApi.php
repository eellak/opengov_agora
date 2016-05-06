<?php

class RfpWriteApi{
	
	const VERSION = '1.1.0';
  	
	 /**
	   * Default options for curl.
	   */
	  
	  var $SchemaRfpRequest;
	  var $SchemaInstance;
	  //a parameter whether an action is permitted or not (less tha 24 hours)
	  var $SchemaCommon;
	  
  
  	public function __construct(){
 			$this->SchemaRfpRequest  = Yii::app()->params['agora']['rfp'];
			$this->SchemaInstance = Yii::app()->params['agora']['xmlSchema'];
 			$this->SchemaCommon = Yii::app()->params['agora']['common'];
  	}
	public function RfpWrite($status){
		
		$xml_writer_object=new xml_writer_class;	
		$noattributes=array();
		$contractAttr['xmlns']=$this->SchemaRfpRequest;
		$contractAttr['xmlns:xsi']=$this->SchemaInstance;
		$contractAttr['xmlns:c']=$this->SchemaCommon;
		$attributes=array();	
				
		if($status=='create'){
			$xml_writer_object->addtag('notices',$contractAttr,'',$main,1);
			$attributes=array();
			$xml_writer_object->addtag('notice',$attributes,$main,$root,1);
			$attributes=array();
		}else{
			$xml_writer_object->addtag('singleNotice',$contractAttr,'',$root,1);
		}
		
		//the contract Title
		$xml_writer_object->addtag('c:title',$attributes,$root,$toptag,0);
		$xml_writer_object->adddata($_POST['Rfp']['title'],$toptag,$path);
		
		//i send one contract so the unique Document Code is always 1
		$xml_writer_object->addtag('c:uniqueDocumentCode',$noattributes,$root,$toptag,0);
		$xml_writer_object->adddata('1',$toptag,$path);	
		
		
		//Protocol Number Code
		$xml_writer_object->addtag('c:protocolNumberCode',$attributes,$root,$toptag,0);
		$xml_writer_object->adddata($_POST['Rfp']['protocolNumberCode'],$toptag,$path);
				
		//Organization Refid
		$attributes['idRef']=Yii::app()->user->RefId;
		$xml_writer_object->addtag('c:organization',$attributes,$root,$toptag,0);
		$subattr['idRef'] = $_POST['Rfp']['OrganizationIdRefUnits'];
		$xml_writer_object->adddatatag('c:unit',$subattr,'',$toptag,$datatag);
					
		//Signers
		$attributes=array();
		$xml_writer_object->addtag('c:signers',$noattributes,$root,$toptag,2);
		//Count Signers Table
		//for($i=0;$i<count($_POST['Rfp']['signers']);$i++){
			$subattr['idRef']=$_POST['Rfp']['signers'];
			$xml_writer_object->adddatatag('c:signer',$subattr,'',$toptag,$datatag);
		//}
				
		//diavgeia aware value
		$xml_writer_object->addtag('c:diavgeiaPublished',$noattributes,$root,$toptag,0);
		//if($_POST['Contract']['diavgeiaPublished']=='on')
		//	$xml_writer_object->adddata('true',$toptag,$path);
		//else 
			$xml_writer_object->adddata('false',$toptag,$path);
				
				
		//Signed Date
		$xml_writer_object->addtag('c:dateSigned',$noattributes,$root,$toptag,0);
		$date = DateTime::createFromFormat('d/m/Y', $_POST['Rfp']['dateSigned']);              
		$date = $date->format('Y-m-d');
		$xml_writer_object->adddata($date,$toptag,$path);
		
		
		if(isset($_POST['Rfp']['RelatedADA']) && $_POST['Rfp']['RelatedADA']!=''){
			$xml_writer_object->addtag('c:relatedAdas',$noattributes,$root,$toptag,0);
			$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
			$subattr = array();
			$xml_writer_object->adddatatag('c:adaCode',$subattr,$_POST['Rfp']['RelatedADA'],$toptag2,$datatag);
			$xml_writer_object->adddatatag('c:adaType',$subattr,'ContractDeclaration',$toptag2,$datatag);
		}
		/*
		$xml_writer_object->addtag('c:relatedAdas',$noattributes,$root,$toptag,0);
		
		
		if(isset($_POST['Rfp']['procurements'])){
			$ProcIds = explode(";", $_POST['Rfp']['procurements']);
			for($i=0;$i<count($ProcIds);$i++){
				if($ProcIds[$i]=='') continue;
				$xml_writer_object->addtag('c:ada',$attributes,$toptag,$toptag2,4);
				$subattr = array();
				$xml_writer_object->adddatatag('c:adaCode',$subattr,$ProcIds[$i],$toptag2,$datatag);
				$xml_writer_object->adddatatag('c:adaType',$subattr,'ProcurementRequest',$toptag2,$datatag);	
			}
		}*/
		
		//issuerEmail
		
		if(isset($_POST['Rfp']['issuerEmail'])){
			$xml_writer_object->addtag('c:issuerEmail',$noattributes,$root,$toptag,0);
			$xml_writer_object->adddata($_POST['Rfp']['issuerEmail'],$toptag,$path);
		}
		//the Document code	
		if(isset($_FILES['Rfp']['name']['document']) && $_FILES['Rfp']['name']['document']!=''){ 
			$the_temp_file = $_FILES['Rfp']['tmp_name']['document'];
			$the_file = str_replace(" ","_",$_FILES['Rfp']['name']['document']);
			$http_error = $_FILES['Rfp']['error']['document'];
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
		if(isset($_FILES['Rfp']['name']['document']) && $_FILES['Rfp']['name']['document']!=''){
			$attributes=array();
			$xml_writer_object->addtag('c:document',$noattributes,$root,$toptag1,1);
			$xml_writer_object->adddata($fileEncoded,$toptag1,$path);
		}
		$xml_writer_object->addtag('since',$noattributes,$root,$toptag,0);
		$date = date_create_from_format('d/m/Y',$_POST['Rfp']['since']);
		$date = date_format($date, 'Y-m-d');
		$xml_writer_object->adddata($date,$toptag,$path);
		//print_r($_POST);
	
		$xml_writer_object->addtag('until',$noattributes,$root,$toptag,0);
		$date = date_create_from_format('d/m/Y',$_POST['Rfp']['until']);
		$date = date_format($date, 'Y-m-d');
		$xml_writer_object->adddata($date,$toptag,$path);
			
		//the selected contractingAuthority
		$attributes['idRef']=$_POST['Rfp']['awardProcedure'];
		$xml_writer_object->addtag('awardProcedure',$attributes,$root,$toptag,1);
	
		//Contract Imtes	
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
				if(isset($_POST['Contractitem'][$ContractKeys[$i]]['documentUrl']) && $_POST['Contractitem'][$ContractKeys[$i]]['documentUrl']!='' && isset($_POST['Rfp']['procurements']))
					$xml_writer_object->adddatatag('c:procurementRequest',$noattributes,$_POST['Contractitem'][$ContractKeys[$i]]['documentUrl'],$top2,$datatag);	
				
			
			
			}
		
		}
		/*
		if(isset($_POST['Rfp']['procurements'])){
			$xml_writer_object->addtag('procurementRequests',$noattributes,$root,$top1,3);
		    $ids = explode(";", $_POST['Rfp']['procurements']);

			for($i=0;$i<count($ids);$i++){
				if($ids[$i]!='')
				$xml_writer_object->adddatatag('requestId',$noattributes, $ids[$i],$top1,$datatag);
	
			}
		
		}*/

		//Since Date
		
		if(!$xml_writer_object->write($final_xml))
			echo ('Error: '.$xml_writer_object->error);
		
			
			
			$config['username']=Yii::app()->user->UserName;
			$config['password']=Yii::app()->user->password;
			$config['data'] =$final_xml;
			$config['filesize'] =strlen($final_xml);
			//echo $final_xml;
			/*$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
			$WriteToFile = $Path."protected/data/files/final.xml";
			$fh = fopen($WriteToFile, 'w') or die("can't open file");
			file_put_contents($WriteToFile, $final_xml);*/
			if($status=='create'){
				$config['apirequest'] ='rfpcreate';
				$api = new Apiconnector($config);
				$output = $api->create('rfp');
			}else if($status=='update'){
				$config['apirequest'] ='rfpUpdate';
				$config['documentid'] = $_GET['uniqueDocumentCode'];
				$api = new Apiconnector($config);
				$output = $api->update('rfp');
					
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