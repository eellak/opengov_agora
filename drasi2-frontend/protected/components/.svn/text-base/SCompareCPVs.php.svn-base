<?php 
class SCompareCPVs extends CValidator{
	/**
	 * @var string the name of the attribute to be compared with
	 */
	public $compareAttribute;
	
	public $allowEmpty;
	/**
	 * Validates the cpv value of the object.
	 * First of all we check if the cpv follows the regular expression
	 * [[0-9]{8}-[0-9]\]-.*$/ . I created the regular expression according to the 
	 * http://simap.europa.eu/codes-and-nomenclatures/codes-cpv/codes-cpv_el.htm
	 * If it follows the regular expression we then check if it is a valid 
	 * cpv. We compare the value with the list of cpvs. 
	 * If there is any error, the error message is added to the object.
	 * @param CModel the object being validated
	 * @param string the name of the attribute being validated. 
	 */
	protected function validateAttribute($object,$attribute)
        {
			$k=0;
			$CPVS = array();
        	$stringValue=$object->$attribute;
        	if(!$this->allowEmpty && $this->isEmpty($stringValue))
            return;
         
			if($object->cpvsid){
				$NewItem = explode(";", $stringValue);
				for($i=0;$i<count($NewItem);$i++){
					if($NewItem[$i]=='') continue;
				//	$result = preg_match('/^\[[0-9]{8}-[0-9]\]-.*$/', $NewItem[$i], $matches,PREG_OFFSET_CAPTURE);
					$result = preg_match('/^\[[a-zA-Z0-9]{4,}-[0-9]\]-.*$/', $NewItem[$i], $matches,PREG_OFFSET_CAPTURE);
					
					//check if the cpv follows the regular expression 
					if($result==0){
						$message=$this->message!==null?$this->message:Yii::t('item','{attribute}  with value <i><u>\'{compareValue}\'</u></i> is not accepted.');
                    	$this->addError($object,$attribute,$message,array('{compareValue}'=>$NewItem[$i]));
                    }
                    
                    
                    
                    //check if it is a valid cpv 
					$CpvToCheck = explode("[", $NewItem[$i]);
					if(count($CpvToCheck)>1){
						$finalItem = explode("]",$CpvToCheck[1]);
						$cpv = new CPVs();
						$value = $cpv->lookupCpv($finalItem[0]);
						if($value==-1){
							$message=$this->message!==null?$this->message:Yii::t('item','{attribute}  with value <i><u>\'{compareValue}\'</u></i> is not accepted.');
							$this->addError($object,$attribute,$message,array('{compareValue}'=>$NewItem[$i]));
						}
						
						if(in_array($value,$CPVS)){
							$message=$this->message!==null?$this->message:Yii::t('item','{attribute}  with value <i><u>\'{compareValue}\'</u></i> id double.');
							$this->addError($object,$attribute,$message,array('{compareValue}'=>$NewItem[$i]));
						}
						$CPVS[$k] = $value;
						$k++;
						
					}
                    
			
				}
			}
     
        }
	
}
?>