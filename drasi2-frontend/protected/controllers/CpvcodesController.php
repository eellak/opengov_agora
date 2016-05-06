<?php

class CpvcodesController extends Controller
{	

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
	/**
	 * This function is responsible for searching 
	 * the requested param in the list of cpvs.
	 * It uses the multibyte extension php-mbstring
	 * in order to make the necessary search.
	 * 
	 */
	private function processParams(){
		if(isset($_GET['q'])) $input = Yii::app()->request->getParam('q');
		if(isset($_GET['input'])) $input = Yii::app()->request->getParam('input');
		
		$len = strlen($input);
		$limit = Yii::app()->request->getParam('limit');
		$limit = $limit ? $limit : 0;
	
		$aResults = array();
		/** Way to parse the file*/
		$config['apirequest'] ='cpvcodes';
		$count=0;
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		$xml = new SimpleXMLElement($output);
		$i=0;
		foreach ($xml->children() as $child) {
	  		//$Xml2Array["[".$child->attributes()."] -".$child[0]->label.""] = "".$child->attributes()."" ;
		 	$Xml2Array["[".$child->attributes()."]-".$child[0]->label.""] = "".$child->attributes()."" ;
			$i++; 
		 }
		
    	$i=0;
    	foreach ($Xml2Array as $key=>$value) {
			if (mb_stripos(mb_strtoupper($key,"utf-8"), mb_strtoupper($input,"utf-8")) !== false ) {
				$count++;
				$aResults[] = array( "id"=>($i) ,"value"=>htmlspecialchars($key), "info"=>htmlspecialchars($value));
				$i++;
			}
			if ($limit && $count==$limit)
				break;
		}
	
			return $aResults;
	}
	
	/**
	 * This function renders the autocomplete data.
	 */
	public function actionAutocomplete(){
				
		$this->render('autocomplete');
		Yii::app()->end();
	}
	
	/**
	 * For the ajax action we use json resulrts.
	 * This function renders the necessary headers
	 * and the array with the results found from processParams.
	 */
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
	
	/**
	 * Ready function to create xml results 
	 * for the request.This function renders the necessary headers
	 * and the array with the results found from processParams.
	 **/
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