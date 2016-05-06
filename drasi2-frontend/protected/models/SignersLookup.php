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

class SignersLookup extends CActiveRecord
{
    private static $_items=array();
 
  
    
    public static function items($type,$refid)
    {
        if(!isset(self::$_items[$type]))
            self::loadItems($type,$refid);
        return self::$_items[$type];
    }
 
    public static function item($type,$code,$refid)
    {
        if(!isset(self::$_items[$type]))
            self::loadItems($type,$refid);
        return isset(self::$_items[$type][$code]) ? self::$_items[$type][$code] : false;
    }
 
    private static function loadItems($type,$refid)
    {
    	if($type=='contractSigners'){
	    	$config['apirequest'] ='contractsitem';
			$config['documentid'] =$refid;
				
			//send a curl request for the document 
			$api = new Apiconnector($config);
			$output = $api->makeRequest();
    	}
    	
		$Signers = new SignersApiData();
		$signersData = $Signers->getAFMData($output);
		if($signersData!=-1){
			self::$_items[$type]['----']='-----';
			for($i=count($signersData)-1;$i>=0;$i--){
				$value = (string)$signersData[$i]['Secafm']."-".(string)$signersData[$i]['Secname'];
				self::$_items[$type][$signersData[$i]['Secafm']]=$value;
			}
		}else{
			$i=0;
			$value='--'.YII::t('form','No AFM exist')."--";
			self::$_items[$type][$signersData[$i]['Secafm']]=$value;	
  			return false;
		}
    }
}
