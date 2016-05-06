<?php 
class SCompareCost extends CCompareValidator{
	/**
	 * @var string the name of the attribute to be compared with
	 */
	public $compareAttribute;
	/**
	 * @var string the constant value to be compared with
	 */
	public $compareValue;
	/**
	 * @var boolean whether the attribute value can be null or empty. Defaults to true,
	 * meaning that if the attribute is empty, it is considered valid.
	 */
	public $allowEmpty=false;
	
	
	protected function validateAttribute($object,$attribute)
    {
	    $stringValue=$object->$attribute;
        if(!$this->allowEmpty && $this->isEmpty($stringValue))
        return;
	
        if(isset($_POST['Payments']['contract']) && $_POST['Payments']['contract']!=''){
	        $arrayKeys = array_keys($_POST['Contractitempayments']);
	        for($i=0;$i<count($arrayKeys);$i++)
	    		if( $_POST['Contractitempayments'][$arrayKeys[$i]]['cost'] ==$stringValue) break;
	    	
	    
	        $documentUrl = $_POST['Payments']['contract'];
	       	$contractItem = new Contractitempayments();
			$contractitemmanager=new ContractItempaymentsManager();
			$contractModel = $contractItem->findByPk($documentUrl,"contractsitem");
			$contractitemmanager=ContractItempaymentsManager::load($contractModel);
					
			if($stringValue >1.04*($contractitemmanager->_items[$arrayKeys[$i]]->cost)){
				$message=$this->message!==null?$this->message:Yii::t('yii','The {attribute} with value {compareAttribute} &euro; must not be greater than {compareValue} &euro;.');
	           	$this->addError($object,$attribute,$message,array('{compareAttribute}'=>$stringValue,'{compareValue}'=>$contractitemmanager->_items[$arrayKeys[$i]]->cost));			
			}
    	}
  	 	
       }
	
}
?>