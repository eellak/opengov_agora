<?php
class EAutoCompleteAction extends CAction
{
    public $model;
    public $attribute;
    private $results = array();
 
    public function run()
    {
        /*if(isset($this->model) && isset($this->attribute)) {
            $criteria = new CDbCriteria();
            $criteria->compare($this->attribute, $_GET['term'], true);
            $model = new $this->model;
            foreach($model->findAll($criteria) as $m)
            {
                $this->results[] = $m->{$this->attribute};
            }
 
        }*/
    	
    	$input = strtolower(Yii::app()->request->getParam('term'));
		$len = strlen($input);
		$limit = Yii::app()->request->getParam('limit');
		
		$limit = $limit ? $limit : 0;
	
	
		$aResults = array();
		$count = 0;
		$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
		$filename = $Path."protected/data/ajax/cpv_codes.xml";
		
		 	//echo $filename ."<br/>";
			$handle = fopen($filename, "r");
			$output = fread($handle, filesize($filename));
		 	$xml = new SimpleXMLElement($output);
		 	$i=0;
		 	foreach ($xml->children() as $child) {
	  			$Xml2Array["[".$child->attributes()."]-".$child[0]->label.""] = "".$child->attributes()."" ;
		 		$i++; 
		 	}
		 	
		 	foreach ($Xml2Array as $key=>$value) {
					if (strpos(strtolower($key), $input) !== false) {
						$count++;
						$aResults[] = array( "id"=>($i+1) ,"value"=>htmlspecialchars($key), "info"=>htmlspecialchars($value));
						
					}
					if ($limit && $count==$limit)
					break;
			}
		
		//return $aResults;
		$this->results = $aResults;	
        echo CJSON::encode($this->results);
    }
}
?>