<?php
$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')=>array('indexproc'),
	Yii::t('procurement','Create Procurement'),
);

$this->menu=array(
	array('label'=>Yii::t('procurement','List'), 'url'=>array('indexproc')),
);
?>

<h1><?php echo Yii::t('procurement','Create Procurement');?></h1>
<?php 
echo $this->renderPartial('_form', array('model'=>$model,
							'contractitemmanager'=>$contractitemmanager,
							'modelname'=>'procurement','type'=>'procurement',
							'Mainaction'=>'create')); 
//'contractItem'=>$contractItem,
?>