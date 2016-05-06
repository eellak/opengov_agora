<?php

class CPVs {
	
	var $Common = 'http://agora.opengov.gr/schema/common-0.1';
	
	public function lookupCPV($id){
		/*
		$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
		$filename = $Path."protected/data/ajax/cpv_codes.xml";
		
		$handle = fopen($filename, "r");
		$output = fread($handle, filesize($filename));
		*/
		
		/* Parse Xml from middleware*/
		$config['apirequest'] ='cpvcodes';
				
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		$xml = new SimpleXMLElement($output);
		$xml->registerXPathNamespace('a',$this->Common);
		$result = $xml->xpath("//a:item[@id='$id']/a:label");
		if($result[0])
			$FinalCpv = "[$id]-". (string)$result[0].";"; 
		else $FinalCpv=-1;
		return $FinalCpv;	
	}
}