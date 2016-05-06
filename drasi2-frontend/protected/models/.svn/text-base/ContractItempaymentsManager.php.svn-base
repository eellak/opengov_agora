<?php

/**
 * This is the class for creating multiple contract Items.
 * It extends TabularInputManager which is the main component
 * for creating and using multiple inputs.
 * In Contracts & Payments We want to collect user input in a batch mode. 
 * That is, the user can enter the information for multiple model instances 
 * and submit them all at once. We call this tabular input because the input 
 * fields are often presented in an HTML table.
 * @author themiszamani
 *
 */
class ContractitempaymentsManager extends TabularInputManager
{
 
    protected $class='Contractitempayments';
    
    /**
     * (non-PHPdoc)
     * @see TabularInputManager::getItems()
     */
    public function getItems()
    {
        if (is_array($this->_items))
            return ($this->_items);
        else 
            return array(
                'n0'=>new Contractitempayments,
            );
    }
 
 	 /**
     * Load the multiple ContractItem model for payments.
     * @param model $model
     */
    public static function load($model)
    {
    	$items = new ContractitempaymentsManager();
    	
    	for($i=0;$i<count($model['quantity']);$i++){
    		$data['n'.$i] = new Contractitempayments();
    		$data['n'.$i]->ContractItemId = $model['ContractItemId'][$i];
    		$data['n'.$i]->quantity=$model['quantity'][$i];
    		$data['n'.$i]->units_of_measure=$model['units_of_measure'][$i];
    		$data['n'.$i]->cost=$model['cost'][$i];
    		$data['n'.$i]->TotalItemcost=$model['TotalItemcost'][$i];
    		
    		$data['n'.$i]->vatid=$model['vatid'][$i];
    		$data['n'.$i]->currencyid=$model['currencyid'][$i];
    		$data['n'.$i]->description=$model['description'][$i];
    		$data['n'.$i]->cpvsid=$model['cpvsid'][$i];
    		$data['n'.$i]->KAE=$model['KAE'][$i];
    		$data['n'.$i]->Secname=(isset($model['Secname'][$i]))?$model['Secname'][$i]:"";
    		$data['n'.$i]->Secafm=(isset($model['Secafm'][$i]))?$model['Secafm'][$i]:"";
    		$data['n'.$i]->Seccountry=(isset($model['Seccountry'][$i]))?$model['Seccountry'][$i]:"";
    		//where to send the items 
    		$data['n'.$i]->address=(isset($model['address'][$i]))?$model['address'][$i]:
    								(isset(Yii::app()->user->OrganisationAddress)?Yii::app()->user->OrganisationAddress:'');
    		$data['n'.$i]->addressNo=(isset($model['addressNo'][$i]))?$model['addressNo'][$i]:
    								(isset(Yii::app()->user->OrganisationAddressNo)?Yii::app()->user->OrganisationAddressNo:'');
    		$data['n'.$i]->addressPostal=(isset($model['addressPostal'][$i]) && $model['addressPostal'][$i]!='')?$model['addressPostal'][$i]:
    								(isset(Yii::app()->user->OrganisationAddressPostal)?Yii::app()->user->OrganisationAddressPostal:'');
    		
			$data['n'.$i]->city=(isset($model['city'][$i]))?$model['city'][$i]:'';
			$data['n'.$i]->countryProduced=(isset($model['countryProduced'][$i]))?$model['countryProduced'][$i]:'';
    		$data['n'.$i]->countryOfDelivery=(isset($model['countryOfDelivery'][$i]))?$model['countryOfDelivery'][$i]:'';
    		$data['n'.$i]->invoice=(isset($model['invoice'][$i]))?$model['invoice'][$i]:'';
    		$data['n'.$i]->documentUrl=(isset($model['documentUrl'][$i]))?$model['documentUrl'][$i]:'';
    		$data['n'.$i]->notice=(isset($model['notice'][$i]))?$model['notice'][$i]:'';
    		$data['n'.$i]->responsibilityAssumptionCode=(isset($model['responsibilityAssumptionCode'][$i]))?$model['responsibilityAssumptionCode'][$i]:'';
    		
    		
    		}
    		$items->_items = $data;
     	    $items->_lastNew=$i-1;
    		
    
    	return $items;
    }
 
  /**
  * (non-PHPdoc)
  * @see TabularInputManager::setUnsafeAttribute()
  */
    public function setUnsafeAttribute($item, $model)
    {
        $item->class_id=$model->primaryKey;
 
    }
 	/**
 	 * (non-PHPdoc)
 	 * @see TabularInputManager::deleteOldItems()
 	 */
 	public function deleteOldItems($model, $itemsPk)
    {
        //$criteria=new CDbCriteria;
        //$criteria->addNotInCondition('id', $itemsPk);
        //$criteria->addCondition("class_id= {$model->primaryKey}");
 
        //Student::model()->deleteAll($criteria); 
    }
    
  
    
}