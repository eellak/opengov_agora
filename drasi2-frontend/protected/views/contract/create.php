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

<h1><?php echo Yii::t('contract','Create Contract');?></h1>
<?php 
echo $this->renderPartial('_form', array('model'=>$model,'contractitemmanager'=>$contractitemmanager,
						'modelname'=>$modelname,
						'signersmanager'=>$signersmanager,'Mainaction'=>'create','type'=>'contract')); ?>