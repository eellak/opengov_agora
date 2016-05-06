<?php
$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')
);

$this->menu=array(
	array('label'=>Yii::t('procurement','List Procurements'), 'url'=>array('indexprocperorg','org'=>Yii::app()->request->getParam('org'))),
	array('label'=>Yii::t('rfp','List'), 'url'=>array('indexrfpperorg','org'=>Yii::app()->request->getParam('org'))),
	array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('indexcontractsperorg','org'=>Yii::app()->request->getParam('org'))),
	array('label'=>Yii::t('payments','List Payments'), 'url'=>array('indexpaymentsperorg','org'=>Yii::app()->request->getParam('org'))),
);
?>
<div id="view_headerOrg">
 <?php 
 Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/profile.css');
 
 $Item = new Procurement();
				$numOfProcurements = $Item->GetNumberOfProcurement();
				$Contract = new Contract();
				$numOfContracts = $Contract->GetNumberOfContracts();
				$Payment = new Payments();
				$numOfPayments = $Payment->GetNumberOfPayments();
				$Rfp = new Rfp();
				$numOfRfp = $Rfp->GetNumberOfRfp();
				$this->renderPartial('/site/stats',array('numOfProcurements'=>$numOfProcurements,
										'numOfContracts'=>$numOfContracts,
										'numOfRfp'=>$numOfRfp,
										'numOfPayments'=>$numOfPayments));		
		
 ?>
 </div>
<h1><?php echo Yii::t('procurement','Latest Procurement');?></h1>

<?php 

if(is_array($dataProvider->rawData)){
	$dataProvider->pagination->pageSize = 5;

	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProvider,
        'itemView'=>'_view',
	 	'summaryText' => '',
	
));
}else 
echo Yii::t('zii','No results found');
?>

<h1><?php echo Yii::t('rfp','Latest RFP');?></h1>
<?php 
if(is_array($dataProviderRfp->rawData)){
	$dataProviderRfp->pagination->pageSize = 5;
	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProviderRfp,
        'itemView'=>'_viewrfp',
		'summaryText' => '',
	
));
}else 
echo Yii::t('zii','No results found');
?>

<h1><?php echo Yii::t('contract','Latest Contract');?></h1>

<?php 
if(is_array($dataProviderContract->rawData)){

?>

<?php 
	$dataProviderContract->pagination->pageSize = 5;

	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProviderContract,
        'itemView'=>'_viewcontracts',
		'summaryText' => '',
	
));
}else 
echo Yii::t('zii','No results found');
?>
<h1><?php echo Yii::t('payments','Latest Payment');?></h1>

<?php 
if(is_array($dataProviderPayment->rawData)){
$dataProviderPayment->pagination->pageSize = 5;

	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProviderPayment,
        'itemView'=>'_viewpayment',	
		'summaryText' => '',
));
}else 
echo Yii::t('zii','No results found');
?>
