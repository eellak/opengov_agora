<?php 
class SComparePaymentDates extends CCompareValidator{
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
	 * Validates the date of a payment.
	 * The date the payment is paied must always be greater than the 
	 * date the contract or procurement is signed. 
	 * If there is any error, the error message is added to the object.
	 * Note that you need to validate if the values are dates prior to call
	 * this function as it doesn't validate them.
	 * @param CModel the object being validated
	 * @param string the name of the attribute being validated. 
	 */
	protected function validateAttribute($object,$attribute)
        {
        	
        	//this is mainly used for payments. 
        	//if the payment is based on a contract
        	$stringValue=$object->$attribute;
            if(!$this->allowEmpty && $this->isEmpty($stringValue))
            return;
                        
            
            if(isset($_POST['Contract']['procurements'])){
            	$procurements = $_POST['Contract']['procurements'];
            	
            	$ProcIds = explode(";", $procurements);
            	
            	$Procurement = new Procurement();
            	
            	for($i=0;$i<count($ProcIds) && $ProcIds[$i]!='';$i++){
	            	$Procurement = $Procurement->findByPk($ProcIds[$i]);
	            	
	            	$compareDate = DateTime::createFromFormat('d/m/Y', $Procurement->dateSigned);              
					$compareValue = $compareDate->format('Y-m-d');
	                $compareValue=strtotime($compareValue);
	            	
	                $stringValue=$object->$attribute;     
	                $date = DateTime::createFromFormat('d/m/Y', $stringValue);              
	                $value = $date->format('Y-m-d');
	                $value = strtotime($value);
	       			
	        		if($value<$compareValue){
	                	$message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must be greater than {compareValue} the procurement request was signed');
	                    $this->addError($object,$attribute,$message,array('{compareAttribute}'=>'dateSigned','{compareValue}'=>$Procurement->dateSigned));
	               }
            	}
            	
            }
            
            
        	if(isset($_POST['Payments']['contract']) && $_POST['Payments']['contract']!=''){
        	
        		$documentUrl = $_POST['Payments']['contract'];
       			$contract = new Contract();
       			$contract = $contract->findByPk($documentUrl);
       			$compareDate = DateTime::createFromFormat('d/m/Y', $contract->dateSigned);              
				$compareValue = $compareDate->format('Y-m-d');
                $compareValue=strtotime($compareValue);
                
                $stringValue=$object->$attribute;     
                $date = DateTime::createFromFormat('d/m/Y', $stringValue);              
                $value = $date->format('Y-m-d');
                $value = strtotime($value);
        		if($value<$compareValue){
                	$message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must be greater than {compareValue} the contract was signed');
                    $this->addError($object,$attribute,$message,array('{compareAttribute}'=>'dateSigned','{compareValue}'=>$contract->dateSigned));
               }
        	}
        	
        if(isset($_POST['Payments']['procurements']) && $_POST['Payments']['procurements']!=''){
            	$procurements = $_POST['Payments']['procurements'];
            	
            	$ProcIds = explode(";", $procurements);
            	
            	$Procurement = new Procurement();
            	
            	for($i=0;$i<count($ProcIds) && $ProcIds[$i]!='';$i++){
	            	$Procurement = $Procurement->findByPk($ProcIds[$i]);
	            	
	            	$compareDate = DateTime::createFromFormat('d/m/Y', $Procurement->dateSigned);              
					$compareValue = $compareDate->format('Y-m-d');
	                $compareValue=strtotime($compareValue);
	            	
	                $stringValue=$object->$attribute;     
	                $date = DateTime::createFromFormat('d/m/Y', $stringValue);              
	                $value = $date->format('Y-m-d');
	                $value = strtotime($value);
	       			
	        		if($value<$compareValue){
	                	$message=$this->message!==null?$this->message:Yii::t('yii','{attribute} must be greater than {compareValue} the procurement request was signed');
	                    $this->addError($object,$attribute,$message,array('{compareAttribute}'=>'dateSigned','{compareValue}'=>$Procurement->dateSigned));
	               }
            	}
            	
            }
				
        }
	
}
?>