<?php
$this->breadcrumbs=array(
	Yii::t('contract','Contracts')=>array('index'),
	Yii::t('contract','Create Contract'),
);

$this->menu=array(
	array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('index')),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchcontract'))
	
);
?>

<h1><?php 
if($Mainaction=='createByProc')
echo Yii::t('contract','Create Contract by procs');
else
echo Yii::t('contract','Create Contract by notice');

?></h1>
<?php
if($Mainaction=='createByProc'){
if($numOfProcurements==0)
	echo $this->renderPartial('notice',array('code'=>Yii::t('contract','noProcurements'),'message'=>Yii::t('contract','noProcurementsmessage')));
}
if($Mainaction=='createByNotices'){
	if ($numOfNotices==0)
		echo $this->renderPartial('notice',array('code'=>Yii::t('contract','noNotices'),'message'=>Yii::t('contract','noNoticesmessage')));
	
}else 
echo $this->renderPartial('_formProc', array('model'=>$model,
							'contractitemmanager'=>$contractitemmanager,
							'signersmanager'=>$signersmanager,
							'Mainaction'=>$Mainaction,
							'step'=>$step,
							'numOfProcurements'=>$numOfProcurements,
							'type'=>'contract',
							'modelname'=>$modelname,
							'numOfNotices'=>$numOfNotices,
							)); ?>