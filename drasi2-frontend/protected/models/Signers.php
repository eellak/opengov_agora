<?php
/**
 * For the agora module we want multiple signers.
 * This is why we create a different model to manage
 * the signers. We use the Signers with Tabular Input
 * to let the user add multiple signers and manage 
 * the data through tabularInputManager Component.
 * @author themiszamani
 *
 */
class Signers extends CFormModel{
	/*secondary party*/
	//the signers Name
	var $Secname;
	//the signers AFM
	var $Secafm;
	//the country of the signer
	var $Seccountry;
	
	/**
	 * With this function we define the rules of
	 * the secondary party.
	 */
	public function rules()
	{
	return array(
			array('Secname,Secafm,Seccountry', 'required'),
			array('Secname,Secafm,Seccountry', 'safe'),
			array('Secafm','SCompareAFM','compareAttribute'=>'Seccountry','operator'=>'=','allowEmpty'=>false ),
			
			);
	}
	
	/**
	 * The customized attibute labels.
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'Secname'=>Yii::t('contract','Secname'),
			'Secafm'=>Yii::t('contract','Secafm'),
			'Seccountry'=>Yii::t('contract','Seccountry'),
		);
	}
	
	/**
	 * 
	 * The findByPk function of the model.
	 * It sends a request to the API with the 
	 * documentUrl either of the procurement or
	 * of the contract. This means it returns the
	 * signers of the procurement or the contract.
	 * @param string $documentUrl the documentUrl of the item (procurement,contract) 
	 */
	public function findByPk($documentUrl,$apirequest="contractsitem"){

		$config['apirequest'] =$apirequest;
	//	$config['apirequest'] ='contractsitem';
		$config['documentid'] =$documentUrl;
		
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		
		//Read the xml output and returns an array with 
		//the attribute values.
		$Signers = new SignersApiData();
		$signersData = $Signers->getItemData($output);
		
		for($i=0;$i<count($signersData);$i++){
			$this->Secname[$i] = $signersData[$i]['Secname'];	
			$this->Secafm[$i] =(string)$signersData[$i]['Secafm'];
			$this->Seccountry[$i] =$signersData[$i]['Seccountry'];
		}
		
		return $this;
	}
	
	
}
?>