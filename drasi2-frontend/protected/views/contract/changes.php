<?php

$this->breadcrumbs=array(
	Yii::t('contract','Contracts')=>array('index'),
	$model->title=>array('viewcontract','uniqueDocumentCode'=>$model->uniqueDocumentCode),
	Yii::t('contract','Changes Contract'),
);

$this->menu=array(
	array('label'=>Yii::t('contract','Create Contract'), 'url'=>array('createcontract')),
	array('label'=>Yii::t('contract','View Contract'), 'url'=>array('viewcontract', 'uniqueDocumentCode'=>$model->uniqueDocumentCode)),
	array('label'=>Yii::t('contract','Update Contract'), 'url'=>array('updatecontract', 'uniqueDocumentCode'=>$model->uniqueDocumentCode),
	'visible'=>$model->actionPermitted && Yii::app()->user->role!='user' && $model->cancelled=='false'),
	array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('index')),
	array('label'=>Yii::t('payments','Create Payment'), 'url'=>array('payments/createpaymentbycontract', 
		'documentUrl'=>$model->uniqueDocumentCode),'visible'=>!Yii::app()->user->isGuest && $model->cancelled=='false'),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchcontract'))
	
);
?>

<h1><?php echo Yii::t('contract','Changes Contract'); echo"&nbsp;"; echo $model->title; ?></h1>

<?php
echo $this->renderPartial('_form', array('model'=>$model,
						'contractitemmanager'=>$contractitemmanager,'signersmanager'=>$signersmanager,
						'modelname'=>$modelname,
						'Mainaction'=>'changes','type'=>'contract')); ?>
