<?php

$this->breadcrumbs=array(
	Yii::t('contract','Contracts')=>array('index'),
	$model->title=>array('viewcontract','uniqueDocumentCode'=>$model->uniqueDocumentCode),
	Yii::t('contract','Update Contract'),
);

$this->menu=array(
	array('label'=>Yii::t('contract','Create Contract'), 'url'=>array('createcontract'),
		 'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin',
		 ),
	array('label'=>Yii::t('contract','View Contract'), 'url'=>array('viewcontract', 
		'uniqueDocumentCode'=>$model->uniqueDocumentCode)),
	array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('index')),
		array('label'=>Yii::t('payments','Create Payment'), 'url'=>array('payments/createpaymentbycontract', 
		'documentUrl'=>$model->uniqueDocumentCode),
		'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin' && $model->cancelled=='false'),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchcontract'))
		
);
?>

<h1><?php echo Yii::t('contract','Update Contract'); echo"&nbsp;"; echo $model->title; ?></h1>

<?php

echo $this->renderPartial('_form', array('model'=>$model,
						 'contractitemmanager'=>$contractitemmanager,'type'=>$type,
						'modelname'=>$modelname,
						 'signersmanager'=>$signersmanager,'Mainaction'=>'update')); ?>
