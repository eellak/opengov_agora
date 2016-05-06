<?php
class SCompareRequiredValidator extends CValidator{
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
	public $allowEmpty=true;

	/**
	 * Validates the attribute of the object.
	 * If there is any error, the error message is added to the object.
	 * @param CModel the object being validated
	 * @param string the attribute being validated
	 */
	protected function validateAttribute($object,$attribute){
		$value=$object->$attribute;
		$compareAttribute=$this->compareAttribute;
		$valueAttribute=$object->$compareAttribute;
		if($this->allowEmpty && ($valueAttribute===null || trim($valueAttribute)==='')){
			if($value===null || trim($value)===''){
				$message=$this->message!==null?$this->message:Yii::t('yii','{attribute} cannot be blank.');
				$this->addError($object,$attribute,$message);
			}
		}
		if($valueAttribute==$this->compareValue){
			if($value===null || trim($value)===''){
				$message=$this->message!==null?$this->message:Yii::t('yii','{attribute} cannot be blank.');
				$this->addError($object,$attribute,$message);
			}
		}
	}
}
?>