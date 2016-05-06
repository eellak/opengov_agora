<?php 
class SCompareAFM extends CCompareValidator{
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
	
	/* AFM rules 
	 * 1) Να είναι 9ψήφιος
	 * 2) Απομονώνοντας το τελευταίο ψηφίο του αριθμού και βρίσκοντας το άθροισμα 
	 * των ψηφίων του πολλαπλασιαζόμενο το κάθε ένα με το δύο υψωμένο στην δύναμη που ισούται 
	 * με την θέση του αντίστροφα παίρνουμε ένα αποτέλεσμα.
	 * 3) Στο αποτέλεσμα αυτό το υπόλοιπο της ακέραιας διαίρεσης με το 11 και το υπόλοιπο 
	 * αυτό διαδοχικά με το 10 δίνει ένα νέο υπόλοιπο.Ο αριθμός αυτός πρέπει  
	 * να είναι ίσος με το ψηφίο που απομονώσαμε.
	 * http://roboday.wordpress.com/2011/04/02/έλεγχος-ορθότητας-αφμ/
	 * Validates the afm of the object, only if seccountry==gr.
	 * If there is any error, the error message is added to the object.
	 * Note that you need to validate if the values are dates prior to call
	 * this function as it doesn't validate them.
	 * @param CModel the object being validated
	 * @param string the name of the attribute being validated. 
	 */
	protected function validateAttribute($object,$attribute)
    {
	    $stringValue=$object->$attribute;
        if(!$this->allowEmpty && $this->isEmpty($stringValue))
        return;

  	 	 //Check AFM only when Seccountry is gr
      	 if($object->Seccountry=='GR'){
 
      	 	$result = preg_match('/^[0-9]{9}$/', $stringValue, $matches,PREG_OFFSET_CAPTURE);
	      	 if($result==0){
				$message= Yii::t('form','afm small');
	      	 	$this->addError($object,$attribute,$message);
	      	}
	        $fvalue=0;
	      	$count=0;
	      	  
	        for($i=strlen($stringValue);$i>=1;$i--){
	        	if($count!=0)
	      	  		$fvalue = $fvalue + $stringValue[$i-1]*pow(2,$count);
	          	$count++;
	      	}
	      	$ValueToCheck = (($fvalue%11)%10);
	      	  
	      	if($ValueToCheck!=$stringValue[strlen($stringValue)-1]) {
	      	 	$message= Yii::t('form','afm not valid');
	      		$this->addError($object,$attribute,$message);
	      	}
      	 }//if Seccountry == gr ends
      
       }
	
}
?>