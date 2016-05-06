<?php

$this->breadcrumbs=array(
	Yii::t('rfp','RFP')=>array('index'),
	$model->title=>array('viewrfp','uniqueDocumentCode'=>$model->uniqueDocumentCode),
	Yii::t('contract','Update Contract'),
);

$this->menu=array(
	array('label'=>Yii::t('rfp','Create Rfp'), 'url'=>array('createrfp'),'visible'=>Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('rfp','View Rfp'), 'url'=>array('viewrfp', 
		'uniqueDocumentCode'=>$model->uniqueDocumentCode)),
	array('label'=>Yii::t('rfp','List RFP'), 'url'=>array('index')),
	
);
?>

<h1><?php echo Yii::t('contract','Update Contract'); echo"&nbsp;"; echo $model->title; ?></h1>

<?php

echo $this->renderPartial('_formUpdate', array('model'=>$model,
						 'contractitemmanager'=>$contractitemmanager,
						 'type'=>$type,
						'modelname'=>$modelname,
						 'Mainaction'=>'update')); ?>
