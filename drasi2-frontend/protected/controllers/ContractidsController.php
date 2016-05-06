<?php

class ContractidsController extends Controller
{	
	var $ContractUrlSchema;
	var $CommonSchema;
	/**
	 * @return array action filters
	 */
	public function filters()
	{
		return array(
			'accessControl', // perform access control for CRUD operations
		);
	}
		
	/**
	 * Specifies the access control rules.
	 * This method is used by the 'accessControl' filter.
	 * @return array access control rules
	 */
	public function accessRules()
	{
		return array(
			array('allow', // allow all
				'users'=>array('*'),
			),
		);
	}

	private function processParams(){
		$this->ContractUrlSchema  = Yii::app()->params['agora']['contract'];
		$this->CommonSchema = Yii::app()->params['agora']['common'];
		
		$input = Yii::app()->request->getParam('q');
		$len = strlen($input);
		$limit = Yii::app()->request->getParam('limit');
		
		$limit = $limit ? $limit : 0;
	
		$aResults = array();
		$count = 0;
		$config['username']=Yii::app()->user->UserName;
		$config['password']=Yii::app()->user->password;
		$config['apirequest'] ='contractsearch';
		$config['search']=$input;
		
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		
		//print_r($output);
		
		$xml = new SimpleXMLElement($output);
		$xml->registerXPathNamespace('a',$this->ContractUrlSchema);
		$xml->registerXPathNamespace('b',$this->CommonSchema);
		$queryResultuniqueDocumentCode = $xml->xpath('//a:contract/attribute::id');
		$queryResultTitle = $xml->xpath('//b:title');
		$queryResultReturned = $xml->xpath('//b:returned');
		
		if($queryResultReturned[0]>0){
			for($i=0;$i<$queryResultReturned[0];$i++){
				$Xml2Array[(string)$queryResultuniqueDocumentCode[$i][0]]= "[".(string)$queryResultuniqueDocumentCode[$i][0]."]-".(string)$queryResultTitle[$i][0]."";
			}
		}
		foreach ($Xml2Array as $key=>$value) {
			if (strpos((mb_convert_case($key,MB_CASE_UPPER, 'UTF-8')),mb_convert_case($input,MB_CASE_UPPER, 'UTF-8')) !== false) {
				$count++;
				$aResults[] = array( "id"=>($i+1) ,"value"=>htmlspecialchars($key), "info"=>htmlspecialchars($value));
			}
			if ($limit && $count==$limit)
			break;
		}
		
		return $aResults;
	
	}
	
	
	public function actionAutocomplete(){
				
		$this->render('autocomplete');
		Yii::app()->end();
	}
	
	public function actionJson(){
		
		header ("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); // Date in the past
		header ("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT"); // always modified
		header ("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
		header ("Pragma: no-cache"); // HTTP/1.0
		
		header("Content-Type: application/json");
	
		echo "{\"results\": [";
		$arr = array();
		$matches = $this->processParams();
		
		for ($i=0;$i<count($matches);$i++)
		{
			$arr[] = "{\"id\": \"".$matches[$i]['id']."\", \"value\": \"".$matches[$i]['value']."\", \"info\":\"".$matches[$i]['info']."\"}";
		}
		echo implode(", ", $arr);
		echo "]}";
		
		Yii::app()->end();
	}
	
	public function actionXml(){
		header ("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); // Date in the past
		header ("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT"); // always modified
		header ("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
		header ("Pragma: no-cache"); // HTTP/1.0
		
		header("Content-Type: text/xml");

		$matches = $this->processParams();
		
		echo "<?xml version=\"1.0\" encoding=\"utf-8\" ?><results>";
		for ($i=0;$i<count($matches);$i++)
		{
			echo "<rs id=\"".$matches[$i]['id']."\" info=\"".$matches[$i]['info']."\">".$matches[$i]['value']."</rs>";
		}
		echo "</results>";
		
	}
	
}