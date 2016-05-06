<?php

$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')=>array('indexproc'),
	$model->title=>array('viewproc','documentUrl'=>$model->documentUrl),
	Yii::t('procurement','Update Procurement'),
);

$this->menu=array(
	array('label'=>Yii::t('procurement','Create Procurement'), 'url'=>array('createproc'),'visible'=>Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('procurement','View Procurement'), 'url'=>array('viewproc', 'documentUrl'=>$model->documentUrl)),
	array('label'=>Yii::t('procurement','Approve Procurement'),  
		 'url'=>'#', 'visible'=>$model->actionPermitted && !Yii::app()->user->isGuest && Yii::app()->user->role!='user' && Yii::app()->user->role!='admin'
		  && $model->approvesRequest=='',
		 'linkOptions'=>array('submit'=>array('approverequest','documentUrl'=>$model->documentUrl))),
	array('label'=>Yii::t('procurement','List'), 'url'=>array('indexproc')),
);
?>

<h1><?php echo Yii::t('procurement','Update Procurement'); echo"&nbsp;"; echo $model->title; ?></h1>

<?php

echo $this->renderPartial('_form', array('model'=>$model,
									'modelname'=>$modelname,'type'=>'procurement',
									'contractitemmanager'=>$contractitemmanager,'Mainaction'=>'update'));
//'contractItem'=>$contractItem)); ?>