<?php
$this->breadcrumbs=array(
	Yii::t('contract','Contracts')=>array('index'),
	Yii::t('contract','Create Contract'),
);

$this->menu=array(
	array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('index')),
);
?>

<h1><?php echo Yii::t('contract','Create Contract by procs');?></h1>
<?php
if($numOfProcurements==0){
	echo $this->renderPartial('notice',array('code'=>Yii::t('contract','noProcurements'),'message'=>Yii::t('contract','noProcurementsmessage')));
	
}else
echo $this->renderPartial('_formProc', array('model'=>$model,
							'contractitemmanager'=>$contractitemmanager,
							'signersmanager'=>$signersmanager,
							'Mainaction'=>'createByProc',
							'step'=>$step,
							'numOfProcurements'=>$numOfProcurements,
							'type'=>'contract',
							'modelname'=>$modelname,
							)); ?>