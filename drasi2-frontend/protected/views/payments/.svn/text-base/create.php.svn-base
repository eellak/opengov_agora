<?php
$this->breadcrumbs=array(
	Yii::t('payments','Payments')=>array('indexpayments'),
	Yii::t('payments','Create Payment'),
);

$this->menu=array(
	array('label'=>Yii::t('payments','Payments'), 'url'=>array('indexpayments')),
);
?>

<h1><?php 
	if($scenario=='createpaymentbyproc')
		echo Yii::t('payments','Create Payment Procurement');
	else
		echo Yii::t('payments','Create Payment');
?></h1>
<?php 
if(isset($numOfContracts) && $numOfContracts==0 && $scenario=='create'){
	echo $this->renderPartial('notice',array('code'=>Yii::t('payments','noContract'),'message'=>Yii::t('payments','noContractsmessage')));
	
}if(isset($numOfProc) && $numOfProc==0 && $scenario=='createpaymentbyproc'){
	echo $this->renderPartial('notice',array('code'=>Yii::t('payments','noProc'),'message'=>Yii::t('payments','noProcmessage')));
}else if($numOfProc>0 || $numOfContracts>0)
echo $this->renderPartial('_form', array('model'=>$model,'contractitemmanager'=>$contractitemmanager,'step'=>$step,
	'action'=>$action,'contract'=>$contract,'numOfContracts'=>$numOfContracts,'scenario'=>$scenario,
	'procurements'=>$procurements,'numOfProc'=>$numOfProc)); 
//'contractItem'=>$contractItem,
?>