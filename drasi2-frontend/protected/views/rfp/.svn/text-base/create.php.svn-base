<?php
$this->breadcrumbs=array(
	Yii::t('rfp','RFP')=>array('index'),
	Yii::t('rfp','Create Rfp'),
);

$this->menu=array(
	array('label'=>Yii::t('rfp','List'), 'url'=>array('index')),
);
?>

<h1><?php echo Yii::t('rfp','Create Rfp');?></h1>
<?php
if($numOfProcurements==0){
	echo $this->renderPartial('notice',array('code'=>Yii::t('contract','noProcurements'),'message'=>Yii::t('contract','noProcurementsmessage')));
	
}else
echo $this->renderPartial('_form', array('model'=>$model,
							'contractitemmanager'=>$contractitemmanager,
							'Mainaction'=>'createByProc',
							'step'=>$step,
							'numOfProcurements'=>$numOfProcurements,
							'type'=>'contract',
							'modelname'=>$modelname,
							)); ?>