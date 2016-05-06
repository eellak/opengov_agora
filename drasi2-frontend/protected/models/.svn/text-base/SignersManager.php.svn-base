<?php

/**
 * 
 * This is the class for creating multiple signers for contract.
 * It extends TabularInputManager which is the main component
 * for creating and using multiple inputs.
 * In Contracts & Payments We want to collect user input in a batch mode. 
 * That is, the user can enter the information for multiple model instances 
 * and submit them all at once. We call this tabular input because the input 
 * fields are often presented in an HTML table.
 * @author themiszamani
 *
 */
class SignersManager extends TabularInputManager
{
 
    protected $class='Signers';
 
    
    
    public function getItems()
    {
        if (is_array($this->_items))
            return ($this->_items);
        else 
            return array(
                'n0'=>new Signers,
            );
    }
 
 
    public static function load($model)
    {
    	$items = new SignersManager();
    	$item = new Signers();
    	for($i=0;$i<count($model['Secname']);$i++){
			$data['n'.$i] = new Signers();
    		$data['n'.$i]->Secname=$model['Secname'][$i];
    		$data['n'.$i]->Secafm=$model['Secafm'][$i];
    		$data['n'.$i]->Seccountry=$model['Seccountry'][$i];
    		
    	}
    	$items->_items = $data;
    	
    	return $items;
    
        $return= new SignersManager;
        foreach ($model->singers as $item)
            $return->_items[$item->primaryKey]=$item;
        return $return;
    }
 
 
    public function setUnsafeAttribute($item, $model)
    {
        $item->class_id=$model->primaryKey;
 
    }
 
 	public function deleteOldItems($model, $itemsPk)
    {
        //$criteria=new CDbCriteria;
        //$criteria->addNotInCondition('id', $itemsPk);
        //$criteria->addCondition("class_id= {$model->primaryKey}");
 
        //Student::model()->deleteAll($criteria); 
    }
    
  
    
}