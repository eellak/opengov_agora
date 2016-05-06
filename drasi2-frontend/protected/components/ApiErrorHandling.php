<?php
/**
 * This a class to print the errors from the middleware.
 * The names of the errors are changing so we have to 
 * make a map for the attribute name and a mapping function
 * for the errors to be presented. 
 * An example
 * 		$errors = new ApiErrorHandling('contract'); //the model name
 *		$model->addError($errors->NameError($data[0]['error'][$i]),$errors->DescriptionError($data[0]['error'][$i]));	
 * @author themiszamani
 *
 */
class ApiErrorHandling {
	
	/*the model name so as to print the error*/
	var $modelName; 
	
	public function __construct($name) {
		$this->modelName = strtolower($name);
	}

	/**
	 * This function is mainly used to select 
	 * the corresponding field to display the error.
	 * When it is a generic error we use the field name 'form'.
	 * 
	 * @param string $name the string from the middleware 
	 */
	public function NameError($name){
		
		switch ($name){
			case'documentNotPdf':
			case'documentNotSpecified':
				return 'document';
			case'since':
				return 'since';

			case 'invalidContractAwardRelatedAda':
				return 'ADAkatakurosis';
			case 'invalidContractCommissionRelatedAda':
				return 'ADAanathesis';
			case'500':
			case'401':
			case'403':
			case'approvedRequest':
			case'updateApprovedRequest':
			case'invalidAfmForea':
			case'dateSignedPaymentPriorToRelated':
			case'invalidProcurementRequestRelatedAda':
			case'paymentRelatedNorToContractOrRequest':
			case 'paymentCostOverrun':
			case'multipleProcurementRequestRelatedAda':
			case'paymentItemReferedToNotRelatedRequest':
			case'fulfilmentDatePriorToSignedDateerror':
				return 'form';
			case 'invalidContractDeclarationRelatedAda':
			case 'RelatedAdaerror':	
				return 'RelatedADA';
			case 'awardProcedureIsNull':
				return 'awradProcedure';
			case 'signer':
				return 'signers';
			default:
				return $name;
		}
	}
	/**
	 * This function is mainly used to display 
	 * the corresponding error message.
	 * We mainly use the name of the Model to select
	 * the file with the messages. 
	 * When it is a generic error we use the name 'form'.
	 * @param string $name the string from the middleware 
	 */
	public function DescriptionError($name){
		
		
		switch ($name){
			case'documentNotSpecified':
				$description = Yii::t('form','documentNotSpecified');
				break;
			case'documentNotPdf':
				$description = Yii::t('form','documentNotPdf');
				break;
			case'since':
				$description = Yii::t($this->modelName,'sinceError');
				break;
			case'400':
				$description = Yii::t('form','400');
				break;
			case'401':
				$description = Yii::t('form','401');
				break;
			case'403':
				$description = Yii::t('form','403');
				break;
			case'500':
				$description = Yii::t('form','500');
				break;
			case 'invalidAfmParty':
				$description = Yii::t('form','invalidAfmForea');
				break;
			case 'invalidAfmForea':
				$description = Yii::t('form','invalidAfmForea');
				break;
			case 'approvedRequest':
				$description = Yii::t('form','approvedRequest');
				break;
			case'dateSignedPaymentPriorToRelated':
				$description = Yii::t('form','dateSignedPaymentPriorToRelated');
				break;
			case'invalidProcurementRequestRelatedAda':
				$description = Yii::t('form','invalidProcurementRequestRelatedAda');
				break;
			
			case'paymentRelatedNorToContractOrRequest':
				$description = Yii::t('form','paymentRelatedNorToContractOrRequest');
				break;
			case 'paymentCostOverrun':
				$description = Yii::t('form','paymentCostOverrun');
				break;
			case 'multipleProcurementRequestRelatedAda':
				$description = Yii::t('form','multipleProcurementRequestRelatedAda');
				break;
			case 'awardProcedureIsNull':
				$description = Yii::t('form','awardProcedureIsNull');
				break;
			case 'signer':
				$description = Yii::t('form','singerserrorDetails');
				break;
			case 'invalidContractDeclarationRelatedAda':
				$description = Yii::t('form','invalidContractDeclarationRelatedAda');
				break;
			case 'paymentItemReferedToNotRelatedRequest':
				$description = Yii::t('form','paymentItemReferedToNotRelatedRequest');
				break;
			case 'fulfilmentDatePriorToSignedDate':
				$description = Yii::t('form','fulfilmentDatePriorToSignedDateerror');
				break;
			case 'RelatedAda':	
				$description = Yii::t('form','RelatedAdaerror');
				break;
			default:
				$description = Yii::t($this->modelName,$name.'error');
		}
		
		return $description;
	}
	 
}
