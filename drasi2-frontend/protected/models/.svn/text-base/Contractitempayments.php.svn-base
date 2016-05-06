<?php

/**
 * This is the model class for the contractitem for payments.
 * This class supports the Contractitempayment procedure.
 * It is the main model Class. 
 * In this class we define or support 
 * a) the attributes of a Contractitempayment
 * b) the validation rules for each attributes of a Contractitempayment.
 * c) the labels for each attribute of a Contractitempayment.
 * d) The findByPk function of the model.
 * e) The dindByMultiplePk is used by Payment Controller
 * The followings are the available properties:
 * @property integer $id
 * @property integer $quantity
 * @property string $cost
 * @property string $description
 * @property integer $vatid
 * @property integer $currencyid
 * @property string $cpvsid
 * @property string $procurementid
 * @property string $invoice
 * @property string $Secafm
 * @property string $Secname
 * @property string $Seccountry 
 * @author themiszamani
 * @version 1.0
 * @package agora 
 */

class Contractitempayments extends CFormModel{
	/**
     * The unique id in order to take the data of the contractItem
     * @var string
     */
	var $ContractItemId;
	/** 
	 * the quantity of the contract item for payment
	 * @var integer
	 */
	var $quantity;
	/** 
	 * the cost of the contract item for payment
	 * @var integer
	 */
	var $cost;
	/** 
	 * the total cost of the contract item
	 * @var integer
	 */
	var $TotalItemcost;
	/** 
	 * the vatid of the contract item for payment
	 * @var integer
	 */	
	var $vatid;
	/** 
	 * the currencyid of the contract item for payment
	 * @var integer
	 */		
	var $currencyid;
	/** 
	 * the description of the contract item for payment
	 * @var string
	 */	
	var $description;
	/** 
	* the cpv of the item for payment, drawn from the autocomplete procedure.
	* the cpvs are stored in an xml.
	* @var sting
	*/
	var $cpvsid;
	/** 
	 * the KAE of the contract item for payment. It supports multiple KAE separated by , 
	 * @var string
	 */	
	var $KAE;
	/** 
	 * the final cost of the contract item for payment.
	 * @var string
	 */	
	var $finalcost;
	/** 
	 * the  invoice number of the contract item for payment. 
	 * @var string
	 */	
	var $invoice;
	/** 
	 * the  signers Name of the contract item for payment.
	 * @var string
	 */	
	var $Secname;
	/** 
	 * the  signers AFM of the contract item for payment. 
	 * @var string
	 */	
	var $Secafm;
	/** 
	 * the  signers Country of the contract item for payment. 
	 * @var string
	 */	
	 var $Seccountry;
	 
	 /**
	  * the country the item is produced 
	  * @var integer 
	  */
	 var $countryProduced;
	 
	/**
	  * the address name the item will be delivered 
	  * @var string 
	  */
	var $address;
	/**
	  * the address no the item will be delivered 
	  * @var string 
	  */
	var $addressNo;
	/**
	  * the address Postal Code the item will be delivered 
	  * @var integer 
	  */
	var $addressPostal;
	/**
	 * The city the item will be delivered
	 * @var string
	 */
	var $city;
	/**
	  * the country the item will be delivered 
	  * @var integer 
	  */
	var $countryOfDelivery;
	/**
	 * if the procurement is approved
	 * @var string
	 */
	var $approvesRequest;
	
	/**
	 * documentUrl , the procurement ADA when the payment is from a pcocurement
	 */
	var $documentUrl;
	/**
	 * the date the payment was signed.
	 * @var date
	 */
	var $dateSigned;
	/**
	 * If the payment is based on a contract which is based on a notice  
	 * in this variable we keep the ADA of the selected notice.
	 * @var string
	 */
	var $notice;
	/**
	 * the type of measure
	 * @var string
	 */
	var $units_of_measure;
	
	/**
	 * the kwdikos analhpshs upoxrewshs
	 * @var string 
	 */
	var $responsibilityAssumptionCode;
	
	/**
	 * The main function that validates the attributes - properties of a contractitem for payment. 
 	 * The purpose is to have all the information gathered in one place instead of scattered.
 	 * See the following link on how to add a new rule to payment. 
	 * The validation rules are as follows:
	 * - quantity, vatid, cost,description,invoice are required
	 * - cpvsid uses the SCompareCPVs to check if it valid or a duplicate value
	 * - KAE shouldnt be required if CPV value is used
	 * - quantity, vatid should be numerical 
	 * - costs length should be less than 20 characters 
	 * - cost type should be float
	 * - description length should be less than 512 characters
	 * - description,invoice,finalcost should be safe. 
	 * - Secname,Secafm,Seccountry,countryProduced are required
	 * - address,addressNo,addressPostal,city,countryOfDelivery are required 
	 * - addressPostal must be 5 characters long 
	 * - Secafm should be valid . It uses SCompareAFM
	 * - currencyid should be safe. After you post the form the selected item is shown. Otherwise the first value is shown. 
	 * */
	public function rules()
	{
		// NOTE: you should only define rules for those attributes that
		// will receive user inputs.
		return array(
			array('quantity, vatid, cost,description,invoice,units_of_measure', 'required',
			      'message'=>Yii::t('item','Valrequired')
			),
			//array('finalcost,invoice', 'required'),
			//the following 2 rules specifies that . It uses SCompareRequiredValidator (components/SCompareRequiredValidator.php)
			//a) if cpvs has values then KAE may be empty 
			//b) if KAE has values then cpvs may be empty 
			//http://www.yiiframework.com/extension/required-compare-true-validator
			//array('cost','SCompareCost','compareValue'=>'someValue'),
			array('cpvsid','SCompareRequiredValidator','compareAttribute'=>'KAE','compareValue'=>'someValue'),
			array('KAE','SCompareRequiredValidator','compareAttribute'=>'cpvsid','compareValue'=>'someValue'),
			array('quantity, vatid,addressPostal', 'numerical', 'integerOnly'=>true),
			array('cost', 'length', 'max'=>20),
			array('cost','type','type'=>'float','message'=>Yii::t('item','CostRequired')),
			array('description', 'length', 'max'=>512),
			array('description,invoice,finalcost','safe'),
			array('Secname,Secafm,Seccountry,countryProduced', 'required'),
			array('address,addressNo,addressPostal,city,countryOfDelivery','required'),
			array('addressPostal','length','max'=>5),
			array('Secafm','SCompareAFM','compareAttribute'=>'Seccountry','operator'=>'=','allowEmpty'=>false ),
			array('currencyid,documentUrl,notice,contract,units_of_measure,responsibilityAssumptionCode,description,cpvsid','safe')
						
		);
	}

	/**
	 * The corresponding attribute labels. The labels are stored in 
	 * the message/el/item.php 
	 * Declares the model attribute labels. 
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'quantity' => Yii::t('item','quantity'),
			'cost' => Yii::t('item','cost'),
			'TotalItemcost' => Yii::t('item','TotalItempayment'),
			'description' => Yii::t('item','description'),
			'vatid' => Yii::t('item','vat'),
			'currencyid' => Yii::t('item','currency'),
			'cpvsid' => Yii::t('item','CPVS'),
			'KAE'=>Yii::t('item','KAE'),
			'finalcost'=>Yii::t('item','finalcost'),
			'invoice'=>Yii::t('item','invoice'),
			'Secname'=>Yii::t('contract','Secname'),
			'Secafm'=>Yii::t('contract','Secafm'),
			'Seccountry'=>Yii::t('contract','Seccountry'),
			'countryProduced'=>Yii::t('payments','countryProduced'),
			'address'=>Yii::t('payments','address'),
			'addressNo'=>Yii::t('payments','addressNo'),
			'addressPostal'=>Yii::t('payments','addressPostal'),
			'city'=>Yii::t('payments','city'),
			'countryOfDelivery'=>Yii::t('payments','countryOfDelivery'),
			'units_of_measure'=>Yii::t('item','units_of_measure'),
			'responsibilityAssumptionCode' => Yii::t('procurement','responsibilityAssumptionCodeSmall'),
		
		
			);
	}
	/**
	 * 
	 * The findByPk function of the model.
	 * It sends a request to the API with the 
	 * documentUrl either of the procurement or
	 * of the contract. This means it returns the
	 * contractItems of the procurement or the contract.
	 * @param string $documentUrl the documentUrl of the item (procurement,contract) 
	 */
	public function findByPk($documentUrl,$apirequest="paymentsitem"){
		
		$config['apirequest'] =$apirequest;
		$config['documentid'] =$documentUrl;
		
		//send a curl request for the document 
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		
		if($output==404) return 404;
		//Read the xml output and returns an array with 
		//the attribute values.
		$ContractItem = new ContractItemPaymentsApiData();
		$procData = $ContractItem->getItemData($output);
		$TotalItemcost=0;
		for($i=0;$i<count($procData);$i++){
			$this->ContractItemId[$i] = $documentUrl;	
			$this->quantity[$i] =(string)$procData[$i]['quantity'];
			$this->units_of_measure[$i]= $procData[$i]['units_of_measure'];
			$this->cost[$i] = $procData[$i]['cost'];
			$TotalItemcost = $TotalItemcost + $procData[$i]['cost']+($procData[$i]['cost']*$procData[$i]['vatid']/100);
			$this->TotalItemcost[$i] = $TotalItemcost;
			
			$this->description[$i] =$procData[$i]['description'];
			$this->vatid[$i]= $procData[$i]['vatid'];
			$this->currencyid[$i] = $procData[$i]['currency'];
			$cpvNew="";
			if(isset($procData[$i]['cpvsid'])){
				$CpvsToDisplay = explode(",",$procData[$i]['cpvsid']);
				for($j=0;$j<count($CpvsToDisplay) && $CpvsToDisplay[$j]!='';$j++){
					$FCpv =new CPVs();
					$cpvNew .=  $FCpv->lookupCPV($CpvsToDisplay[$j]);
				}
				$this->cpvsid[$i] = $cpvNew;
				
				$cpvNew="";
				
			}
			if(isset($procData[$i]['kae']))
				$this->KAE[$i] = $procData[$i]['kae'];
				
			$this->Secname[$i] = (isset($procData[$i]['Secname']))?$procData[$i]['Secname']:"";	
			$this->Secafm[$i] = (isset($procData[$i]['Secafm']))?$procData[$i]['Secafm']:"";	
			$this->Seccountry[$i] = (isset($procData[$i]['Seccountry']))?$procData[$i]['Seccountry']:"";	
			$this->countryProduced[$i] = (isset($procData[$i]['countryProduced']))?$procData[$i]['countryProduced']:"";
			$this->countryOfDelivery[$i] = (isset($procData[$i]['countryOfDelivery']))?$procData[$i]['countryOfDelivery']:"";	
			$this->address[$i] = (isset($procData[$i]['address']))?$procData[$i]['address']:"";	
			$this->addressNo[$i] = (isset($procData[$i]['addressNo']))?$procData[$i]['addressNo']:"";	
			$this->addressPostal[$i] = (isset($procData[$i]['addressPostal']))?$procData[$i]['addressPostal']:"";	
			$this->city[$i] = (isset($procData[$i]['city']))?$procData[$i]['city']:"";			
			$this->approvesRequest[$i] =(isset($procData[$i]['approvesRequest']))?$procData[$i]['approvesRequest']:"";
			$this->invoice[$i] =(isset($procData[$i]['invoice']))?$procData[$i]['invoice']:"";
			$this->documentUrl[$i] =(isset($procData[$i]['documentUrl']))?$procData[$i]['documentUrl']:"";
			$this->notice[$i] =(isset($procData[$i]['notice']))?$procData[$i]['notice']:"";
			$this->responsibilityAssumptionCode[$i] =(isset($procData[$i]['responsibilityAssumptionCode']))?$procData[$i]['responsibilityAssumptionCode']:"";
			
			
		}

		return $this;
	}
	
	/**
	 * This function is used by ContractController createcontractbyproc
	 * to check if the multiple contractids exists. If they exist it 
	 * returns the manager of contractids. Otherwise it returns a 
	 * table with the documentId and the specific error.
	 * @param array $documentUrl an array of documentUrls
	 * @param string $apirequest the string of the apirequest
	 */
	public function findByMultiplePk($documentUrl,$apirequest="contractsitem"){		
		$error=array();
		$count=$r=0;
		
		for($k=0;$k<count($documentUrl);$k++){
			//if empty continue
			if($documentUrl[$k]=='') continue;
			$config['username']=Yii::app()->user->UserName;
			$config['password']=Yii::app()->user->password;
			$config['apirequest'] =$apirequest;
			//$config['apirequest'] ='contractsitem';
			$config['documentid'] =str_replace(' ','',$documentUrl[$k]);
			
			//send a curl request for the document 
			$api = new Apiconnector($config);
			$output = $api->makeRequest();
			//echo $output;
			if($output==404) {
				$error[$r]['documentUrl']=$documentUrl[$k];
				$error[$r]['error']=404;
				$r++;
				continue;
			}
		
		if($output==-1) continue;
		//Read the xml output and returns an array with 
		//the attribute values.
		$ContractItem = new ContractItemPaymentsApiData();
		$procData = $ContractItem->getItemData($output);
		$TotalItemcost=0;
		for($i=0;$i<count($procData);$i++){
			
			$this->ContractItemId[$count] = $documentUrl[$k];	
			$this->quantity[$count] =(string)$procData[$i]['quantity'];
			$this->units_of_measure[$count]= $procData[$i]['units_of_measure'];
			
			$this->cost[$count] =$procData[$i]['cost'];
			$TotalItemcost = $TotalItemcost + $procData[$i]['cost']+($procData[$i]['cost']*$procData[$i]['vatid']/100);
			$this->TotalItemcost[$count] = $TotalItemcost;
			
			$this->description[$count] =$procData[$i]['description'];
			$this->vatid[$count]= $procData[$i]['vatid'];
			$this->currencyid[$count] = $procData[$i]['currency'];
			$this->approvesRequest[$count] = $procData[$i]['approvesRequest'];
						
			$cpvNew="";
			if(isset($procData[$i]['cpvsid'])){
				$CpvsToDisplay = explode(",",$procData[$i]['cpvsid']);
				for($j=0;$j<count($CpvsToDisplay) && $CpvsToDisplay[$j]!='';$j++){
					$FCpv =new CPVs();
					$cpvNew .=  $FCpv->lookupCPV($CpvsToDisplay[$j]);
				}
				$this->cpvsid[$count] = $cpvNew;
				
				$cpvNew="";
				
			}
			if(isset($procData[$i]['kae']))
				$this->KAE[$count] = $procData[$i]['kae'];
			$this->countryProduced[$count] = (isset($procData[$i]['countryProduced']))?$procData[$i]['countryProduced']:"";
			$this->countryOfDelivery[$count] = (isset($procData[$i]['countryOfDelivery']))?$procData[$i]['countryOfDelivery']:"";	
			$this->address[$count] = (isset($procData[$i]['address']))?$procData[$i]['address']:"";	
			$this->addressNo[$count] = (isset($procData[$i]['addressNo']))?$procData[$i]['addressNo']:"";	
			$this->addressPostal[$count] = (isset($procData[$i]['addressPostal']))?$procData[$i]['addressPostal']:"";	
			$this->city[$count] = (isset($procData[$i]['city']))?$procData[$i]['city']:"";			
			$this->invoice[$count] =(isset($procData[$i]['invoice']))?$procData[$i]['invoice']:"";
			$this->documentUrl[$count] =$documentUrl[$k];
			$this->notice[$count] =(isset($procData[$i]['notice']))?$procData[$i]['notice']:"";
			$this->responsibilityAssumptionCode[$count] =(isset($procData[$i]['responsibilityAssumptionCode']))?$procData[$i]['responsibilityAssumptionCode']:"";
			
			$count++;
		}

		}			
		if(isset($error[0]['error']))
			return $error;
		else 
			return $this;			
	}
	
/**
	 * This function is used by ContractController createcontractbyproc
	 * to check if the multiple contractids exists. If they exist it 
	 * returns the manager of contractids. Otherwise it returns a 
	 * table with the documentId and the specific error.
	 * @param array $documentUrl an array of documentUrls
	 * @param string $apirequest the string of the apirequest
	 */
	public function findByMultiplePkPayments($documentUrl,$apirequest="paymentsitem"){
		
		$error=array();
		$count=$r=0;
		$Payment = new Payments();
		
		for($k=0;$k<count($documentUrl);$k++){
			//if empty continue
			if($documentUrl[$k]=='') continue;
			//$config['username']=Yii::app()->user->UserName;
			//$config['password']=Yii::app()->user->password;
			$config['apirequest'] =$apirequest;
			//$config['apirequest'] ='contractsitem';
			$config['documentid'] =str_replace(' ','',$documentUrl[$k]);
			
			//send a curl request for the document 
			$api = new Apiconnector($config);
			$output = $api->makeRequest();
			if($output==404) {
				$error[$r]['documentUrl']=$documentUrl[$k];
				$error[$r]['error']=404;
				$r++;
				continue;
			}
		
		if($output==-1) continue;
		//Read the xml output and returns an array with 
		//the attribute values.
		$ContractItem = new ContractItemPaymentsApiData();
		$procData = $ContractItem->getItemData($output);
		$dateSigned = $Payment->getPaymentDate($documentUrl[$k]);
		$TotalItemcost=0;
		
		for($i=0;$i<count($procData);$i++){
			
			$this->ContractItemId[$count] = $documentUrl[$k];	
			$this->quantity[$count] =(string)$procData[$i]['quantity'];
			$this->units_of_measure[$count]= $procData[$i]['units_of_measure'];
			$this->cost[$count] =$procData[$i]['cost'];
			$TotalItemcost = $TotalItemcost + $procData[$i]['cost']+($procData[$i]['cost']*$procData[$i]['vatid']/100);
			$this->TotalItemcost[$count] = $TotalItemcost;
			
			$this->description[$count] =$procData[$i]['description'];
			$this->vatid[$count]= $procData[$i]['vatid'];
			$this->currencyid[$count] = $procData[$i]['currency'];
			$this->approvesRequest[$count] = $procData[$i]['approvesRequest'];
			$this->invoice[$count] = $procData[$i]['invoice'];
			$this->notice[$count] = $procData[$i]['notice'];
			$this->responsibilityAssumptionCode[$count] = $procData[$i]['responsibilityAssumptionCode'];
			
			
			
			$cpvNew="";
			if(isset($procData[$i]['cpvsid'])){
				$CpvsToDisplay = explode(",",$procData[$i]['cpvsid']);
				for($j=0;$j<count($CpvsToDisplay) && $CpvsToDisplay[$j]!='';$j++){
					$FCpv =new CPVs();
					$cpvNew .=  $FCpv->lookupCPV($CpvsToDisplay[$j]);
				}
				$this->cpvsid[$count] = $cpvNew;
				
				$cpvNew="";
				
			}
			if(isset($procData[$i]['kae']))
				$this->KAE[$count] = $procData[$i]['kae'];
			$this->countryProduced[$count] = (isset($procData[$i]['countryProduced']))?$procData[$i]['countryProduced']:"";
			$this->countryOfDelivery[$count] = (isset($procData[$i]['countryOfDelivery']))?$procData[$i]['countryOfDelivery']:"";	
			$this->address[$count] = (isset($procData[$i]['address']))?$procData[$i]['address']:"";	
			$this->addressNo[$count] = (isset($procData[$i]['addressNo']))?$procData[$i]['addressNo']:"";	
			$this->addressPostal[$count] = (isset($procData[$i]['addressPostal']))?$procData[$i]['addressPostal']:"";	
			$this->city[$count] = (isset($procData[$i]['city']))?$procData[$i]['city']:"";	
			$this->documentUrl[$count] = (isset($procData[$i]['documentUrl']))?$procData[$i]['documentUrl']:"";			
			$this->dateSigned[$count]= (isset($dateSigned))?$dateSigned:"";
			
			$count++;
		}
	
		}
	
		if(isset($error[0]['error']))
			return $error;
		else 
			return $this;			
	}
	
}