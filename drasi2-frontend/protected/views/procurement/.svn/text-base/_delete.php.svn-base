<?php 

$this->breadcrumbs=array(
		Yii::t('procurement','Procurements')=>array('indexproc'), 
	Yii::t('procurement','Delete Procurement'),$model->title,
);

$this->menu=array(
	array('label'=>Yii::t('procurement','List Procurements'), 'url'=>array('indexproc')),
);

?>
<h1><?php echo Yii::t('procurement','Delete Procurement');?></h1>
<div style="padding:10px;"> <?php echo Yii::t('procurement','Delete Procurement Details');?></div>

<div class="form">
<?php
$form=$this->beginWidget('CActiveForm', array(
	 'id'=>'procurement-form',
 	 'enableAjaxValidation'=>true,
)); 


$arrayErrors = $model;
echo '<p>'.$form->errorSummary($arrayErrors).'</p>'; 
?>
 	<p class="note">
 		<?php echo Yii::t('form','requiredStart');?> 
 		<span class="required">*</span> 
 		<?php echo Yii::t('form','requiredEnd');?>
 	</p>
	<div class="form-group">
		<fieldset>
		<legend><?php echo Yii::t('procurement','Delete Procurement')."-". Yii::t('form','Deldescription');?></legend>
		<div class="row-line">
					<?php echo $form->labelEx($model,'Deldescription'); ?>
					<?php echo $form->textArea($model,"Deldescription",array('rows'=>6, 'cols'=>80));?>	
					<?php echo $form->error($model,"Deldescription"); ?>
		</div>
		<div class="clear"></div>
	 	</fieldset>
 	</div>
 	 
 	
 		<div class="row buttons">
		<?php echo CHtml::submitButton(Yii::t('form','Διαγραφή')); ?>
	</div>
	<?php $this->endWidget(); ?>
</div> 	