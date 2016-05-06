<?php 
class SCompareDates extends CCompareValidator{
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
	
	/**
	 * Validates the attribute of the object.
	 * If there is any error, the error message is added to the object.
	 * Note that you need to validate if the values are dates prior to call
	 * this function as it doesn't validate them.
	 * @param CModel the object being validated
	 * @param string the name of the attribute being validated. 
	 */
	protected function validateAttribute($object,$attribute)
        {
        	
        	//this is mainly used for contracts. Some contracts dont have 
        	// until date. So they have to check until check point.
        	if($attribute=='until')
        	if(isset($object->untilexists) && $object->untilexists==1 && $attribute=='until') 
        		return;
			else {
				if(!$this->allowEmpty && $object->until==''){
					$message=$this->message!==null?$this->message:Yii::t('yii','{attribute} cannot be blank');
					$this->addError($object,$attribute,$message);
				}
			}
        	
        	   $stringValue=$object->$attribute;
               if(!$this->allowEmpty && $this->isEmpty($stringValue))
                        return;
         
				$date = DateTime::createFromFormat('d/m/Y', $stringValue);              
                $value = $date->format('Y-m-d');
                $value = strtotime($value);
          		if($this->compareValue!==null){
					$compareDate = DateTime::createFromFormat('d/m/Y', $this->compareValue);              
                	$compareValue = $compareDate->format('Y-m-d');
                    $compareTo=$compareValue=strtotime($compareValue);                        
                		
                }
                else
                {
                    $compareAttribute=$this->compareAttribute===null ? $attribute.'_repeat' : $this->compareAttribute;
					$compareDate = DateTime::createFromFormat('d/m/Y', $object->$compareAttribute);              
					$compareValue = $compareDate->format('Y-m-d');
                    $compareValue=strtotime($compareValue);
                    $compareTo=strtotime($object->getAttributeLabel($compareAttribute));
                         
                }
                
                
               // echo "value: ".date('Y-m-d',$value)." compareValue: ".date('Y-m-d',$compareValue);
            
                switch($this->operator)
                {
                        case '=':
                        case '==':
                                if(($this->strict && $value!==$compareValue) || (!$this->strict && $value!=$compareValue))
                                {
                                        $message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must be repeated exactly.');
                                        $this->addError($object,$attribute,$message,array('{compareAttribute}'=>$compareTo));
                                }
                                break;
                        case '!=':
                                if(($this->strict && $value===$compareValue) || (!$this->strict && $value==$compareValue))
                                {
                                        $message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must not be equal to "{compareValue}".');
                                        $this->addError($object,$attribute,$message,array('{compareAttribute}'=>$compareTo,'{compareValue}'=>$object->$compareAttribute));
                                }
                                break;
                        case '>':
                                if($value<=$compareValue)
                                {
                                      $message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must be greater than "{compareValue}".');
                                      $this->addError($object,$attribute,$message,array('{compareAttribute}'=>$compareTo,'{compareValue}'=>$object->$compareAttribute));
                                }
                                break;
                        case '>=':
                                if($value<$compareValue)
                                {
                                	
                                        $message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must be greater than or equal to "{compareValue}".');
                                        $this->addError($object,$attribute,$message,array('{compareAttribute}'=>$compareTo,'{compareValue}'=>$object->$compareAttribute));
                                }
                                break;
                        case '<':
                                if($value>=$compareValue)
                                {
                                        $message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must be less than "{compareValue}".');
                                        $this->addError($object,$attribute,$message,array('{compareAttribute}'=>$compareTo,'{compareValue}'=>date('d/m/Y',$compareValue)));
                                }
                                break;
                        case '<=':
                                if($value>$compareValue)
                                {
                                        $message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must be less than or equal to "{compareValue}".');
                                        $this->addError($object,$attribute,$message,array('{compareAttribute}'=>$compareTo,'{compareValue}'=>date('d/m/Y',$compareValue)));
                                }
                                break;
                        default:
                                throw new CException(Yii::t('yii','Invalid operator "{operator}".',array('{operator}'=>$this->operator)));
                }
        }
	
}
?>