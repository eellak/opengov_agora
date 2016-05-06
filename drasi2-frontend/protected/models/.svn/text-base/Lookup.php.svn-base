<?php

/**
 * This is the model class for table "tbl_lookup".
 *
 * The followings are the available columns in table 'tbl_lookup':
 * @property integer $id
 * @property string $name
 * @property integer $code
 * @property string $type
 * @property integer $position
 */

class Lookup extends CActiveRecord
{
    private static $_items=array();
	
    public static function model($className=__CLASS__)
    {
            return parent::model($className);
    }

    public static function items($type)
    {
        if(!isset(self::$_items[$type]))
            self::loadItems($type);
        return self::$_items[$type];
    }
 
    public static function item($type,$code)
    {
        if(!isset(self::$_items[$type]))
            self::loadItems($type);
        return isset(self::$_items[$type][$code]) ? self::$_items[$type][$code] : false;
    }
 
    private static function loadItems($type)
  {
	if($type=='contractType')
		$Url =Yii::app()->params['taxonomy']['contract_type'];
	if($type=='Currency')
		$Url =Yii::app()->params['taxonomy']['currency'];
	if($type=='country' || $type=='Country')
		$Url =Yii::app()->params['taxonomy']['country'];
	if($type=='commissionCriteria')
		$Url =Yii::app()->params['taxonomy']['commision_criteria'];
	if($type=='contracting_authority')
		$Url =Yii::app()->params['taxonomy']['contracting_authority'];
	if($type=='FPA' || $type=='OrganizationYpTextDrop') $Url=-1;
	if($type=='award_procedure')
		$Url =Yii::app()->params['taxonomy']['award_procedure'];
	if($type=='units_of_measure')
		$Url =Yii::app()->params['taxonomy']['units_of_measure'];
	if($type=='cancellationType')
		$Url =Yii::app()->params['taxonomy']['cancellationType'];
	
    	if(isset($Url) && $Url!=-1){
	      	$client = new EHttpClient($Url, array(
		    'maxredirects' => 3,
	      	'keepalive'       => true,
		    'timeout'      => 130));
	      	$client->setConfig(array('strictredirects' => true));	
	      	
			$client->setHeaders("Content-Type:application/xml");
			$client->setHeaders("Accept:application/xml");
			$response = $client->request();	
			
			if($response->isSuccessful() ){		
				$Status = $response->getStatus();
				$Message = $response->getMessage();	
				
				if($Status==200 && $Message=='OK' ){
					$output =  $response->getBody();
					$xml = new SimpleXMLElement($output);
					$xml->registerXPathNamespace('a','http://agora.opengov.gr/schema/common-0.1');
					
					$item = $xml->xpath("//a:item[@id]");
					if(count($item)>0)
					for($i=0;$i<count($item);$i++){
						$id=$item[$i]['id'];
						$label=$xml->xpath("//a:item[@id='$id']/a:label");
						self::$_items[$type][(string)$item[$i]['id']]="".$label[0][0]."";
			
					}				
			}else 
				throw new CHttpException(Yii::t('yii','timeout'),Yii::t('yii','timeoutDesc'));
			
	    }
    }else{
      if($type=='FPA'){
		self::$_items['FPA'][23]=23;
      	self::$_items['FPA'][0]=0;
      	self::$_items['FPA'][5]=5;
      	self::$_items['FPA'][9]=9;
      	self::$_items['FPA'][10]=10;
      	self::$_items['FPA'][13]=13;
      	self::$_items['FPA'][15]=15;
      	self::$_items['FPA'][16]=16;
      }	else if( $type=='OrganizationYpTextDrop'){
      		//self::$_items[$type][1111]='Διοικητικής Μεταρρύθμισης και Ηλεκτρονικής Διακυβέρνησης';
      		//self::$_items[$type][30]='Εσωτερικών';
      			self::$_items[$type][0]='----';
      			self::$_items[$type][31]='Διοικητικής Μεταρρύθμισης και Ηλεκτρονικής Διακυβέρνησης';
				self::$_items[$type][30]='Εσωτερικών';
				self::$_items[$type][15]='Οικονομικών';
				self::$_items[$type][10]='Εξωτερικών';
				self::$_items[$type][6]='Εθνικής Άμυνας';
				self::$_items[$type][18]='Ανάπτυξης, Ανταγωνιστικότητας και Ναυτιλίας';
				self::$_items[$type][16]='Περιβάλλοντος, Ενέργειας και Κλιματικής Αλλαγής';
				self::$_items[$type][8]='Παιδείας, Δια Βίου Μάθησης και Θρησκευμάτων';
				self::$_items[$type][14]='Υποδομών, Μεταφορών και Δικτύων';
				self::$_items[$type][11]='Εργασίας και Κοινωνικής Ασφάλισης';
				self::$_items[$type][20]='Υγείας και Κοινωνικής Αλληλεγγύης';
				self::$_items[$type][3]='Αγροτικής Ανάπτυξης και Τροφίμων';
				self::$_items[$type][5]='Δικαιοσύνης';
				self::$_items[$type][19]='Προστασίας του Πολίτη';
				self::$_items[$type][17]='Πολιτισμού και Τουρισμού';
				        	
				        	
      	}
      /*  self::$_items[$type]=array();
        $models=self::model()->findAll(array(
            'condition'=>'type=:type',
            'params'=>array(':type'=>$type),
            'order'=>'position',
        ));
        
        foreach($models as $model)
            self::$_items[$type][$model->code]=$model->name;*/
    
    }
  }
}
