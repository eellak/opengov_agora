<?php
$this->breadcrumbs=array(
	Yii::t('payments','Payments')=>array('indexpayments'),
	Yii::t('payments','Create Empty Payment'),
);

$this->menu=array(
	array('label'=>Yii::t('payments','Payments'), 'url'=>array('indexpayments')),
);
?>

<h1><?php echo Yii::t('payments','Create Empty Payment');?></h1>
<?php 

echo $this->renderPartial('_formempty', array('model'=>$model,'contractitemmanager'=>$contractitemmanager,
	'action'=>'createempty')); 
//'contractItem'=>$contractItem,
?>