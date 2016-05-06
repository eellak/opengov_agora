<?php

$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')=>array('indexproc'),
	$modelAll->title=>array('viewproc','documentUrl'=>$modelAll->documentUrl),
	Yii::t('procurement','Approve Procurement'),
);

$this->menu=array(
	array('label'=>Yii::t('procurement','Create Procurement'), 'url'=>array('createproc')),
	array('label'=>Yii::t('procurement','View Procurement'), 'url'=>array('viewproc', 'documentUrl'=>$model->documentUrl)),
	array('label'=>Yii::t('procurement','List'), 'url'=>array('indexproc')),
);
?>

<h1><?php echo Yii::t('procurement','Approve Procurement'); echo"&nbsp;"; echo $model->title; ?></h1>

<?php

echo $this->renderPartial('_form', array('model'=>$model,
									'modelname'=>$modelname,'type'=>'procurement',
									 'modelAll'=>$modelAll,
									'contractitemmanager'=>$contractitemmanager,'Mainaction'=>'approverequest'));
//'contractItem'=>$contractItem)); ?>