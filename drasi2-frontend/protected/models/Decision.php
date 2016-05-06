<?php

/**
 * This is the model class for the decision
 * This class supports the Contractitem procedure.
 * It is the main model Class. 
 * In this class we define or support 
 * a) the attributes of a Contractitem
 * b) the validation rules for each attributes of a Contractitem.
 * c) the labels for each attribute of a Contractitem.
 * d) The findByPk function of the model.
 * e) The dindByMultiplePk is used by ContractController createcontractbyproc
 * The followings are the available properties:
 * @property integer $id
 * @property integer $quantity
 * @property string $cost
 * @property string $description
 * @property integer $vatid
 * @property integer $currencyid
 * @property string $cpvsid
 * @property string $procurementid
 */
class Decision extends CFormModel{
	/**
     * The unique id in order to take the data of the Decision
     * @var string
     */
	var $DecisionADA;
	/** 
	 * the protocolNumber of the Decision
	 * @var string
	 */
	var $protocolNumber;
	/** 
	 * the title of the Decision
	 * @var string
	 */
	var $title;
	/** 
	 * the organizationId of the Decision
	 * @var integer
	 */	
	var $organizationId;
	/** 
	 * the organizationUnitId of the decision
	 * @var integer
	 */		
	var $organizationUnitId;
	/** 
	 * the signerId of the decision
	 * @var string
	 */	
	var $signerId;
	/** 
	* the submissionTimestamp of the decision
	* @var sting
	*/
	var $submissionTimestamp;
	/** 
	* the submissionTimestamp of the decision
	* @var sting
	*/
	var $documentUrl;
	/**
	 * The main function that validates the attributes - properties of a payment. 
 	 * The purpose is to have all the information gathered in one place instead of scattered.
 	 * See the following link on how to add a new rule to payment. 
	 * The validation rules are as follows:
	 * - quantity, vatid, cost,description,cpvsid are required
	 * - cpvsid uses the SCompareCPVs to check if it valid or a duplicate value
	 * - KAE shouldnt be required if CPV value is used
	 * - quantity, vatid should be numerical 
	 * - costs length should be less than 20 characters 
	 * - cost type should be float
	 * - description, datesigned should be safe. 
 	 * - countryProduced,countryOfDelivery,currencyid should be safe. After you post the form the selected item is shown. Otherwise the first value is shown. 
	 * @link http://www.yiiframework.com/wiki/56/reference-model-rules-validation/#hh4
	 * @return array with the errors
	 */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('protocolNumber, title, cost,organizationId,organizationUnitId,signerId,submissionTimestamp,documentUrl', 'safe')
			
		);
	}


	/**
	 * Declares the model attribute labels. 
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'title' => Yii::t('contract','title'),
			
		);
	}
	/**
	 * The findByPk function of the model.
	 * It sends a request to the API with the 
	 * documentUrl either of the procurement or
	 * of the contract. This means it returns the
	 * contractItems of the procurement or the contract.
	 * @param string $documentUrl the documentUrl of the item (procurement,contract) 
	 * @param string $apirequest the type of request
	 */
	public function findByPk($documentUrl,$apirequest="decisions"){
		$config['apirequest'] =$apirequest;
		//$config['apirequest'] ='contractsitem';
		$config['documentid'] =$documentUrl;
		
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();

		//if($output==500 || $output==401) return -1;
			//Read the xml output and returns an array with 
			//the attribute values.
			$Decision = new DecisionApiData();
			$Data = $Decision->getItemData($output);
			
			return $Data;
		
		
	}

	
	
}