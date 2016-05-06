<?php

$this->breadcrumbs=array(
	Yii::t('payments','Payments')=>array('indexpayments'),
	$model->title=>array('viewpayment','documentUrl'=>$model->documentUrl),
	Yii::t('payments','Update Payment'),
);

$this->menu=array(
	array('label'=>Yii::t('payments','Create Payment'), 'url'=>array('createpayment'),
	 'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin',
	),
	array('label'=>Yii::t('payments','View Payment'), 'url'=>array('viewpayment', 'documentUrl'=>$model->documentUrl)),
	array('label'=>Yii::t('payments','All Payments'), 'url'=>array('indexpayments')),
);
?>

<h1><?php echo Yii::t('payments','Update Payment'); echo"&nbsp;"; echo $model->title; ?></h1>

<?php

echo $this->renderPartial('_form', array('model'=>$model,'action'=>'update','step'=>0,
							'contractitemmanager'=>$contractitemmanager,'action'=>$action,'scenario'=>$action,
							'procurements'=>$procurements,
));
//'contractItem'=>$contractItem)); ?>