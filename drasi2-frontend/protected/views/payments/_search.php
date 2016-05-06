<div class="wide form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'action'=>Yii::app()->createUrl($this->route),
	'method'=>'get',
)); ?>

	<div class="row">
		<?php echo $form->label($model,'ProcurementId'); ?>
		<?php echo $form->textField($model,'ProcurementId'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'ProcurementTitle'); ?>
		<?php echo $form->textField($model,'ProcurementTitle',array('size'=>60,'maxlength'=>512)); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'ProcurementDate'); ?>
		<?php echo $form->textField($model,'ProcurementDate'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'ProcurementKAD'); ?>
		<?php echo $form->textField($model,'ProcurementKAD',array('size'=>56,'maxlength'=>56)); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'ProcurementADA'); ?>
		<?php echo $form->textField($model,'ProcurementADA',array('size'=>24,'maxlength'=>24)); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'ProcurementUnit'); ?>
		<?php echo $form->textField($model,'ProcurementUnit'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'ProcurementSigner'); ?>
		<?php echo $form->textField($model,'ProcurementSigner'); ?>
	</div>

	<div class="row">
		<?php echo $form->label($model,'ProcurementArProt'); ?>
		<?php echo $form->textField($model,'ProcurementArProt',array('size'=>60,'maxlength'=>128)); ?>
	</div>

	<div class="row buttons">
		<?php echo CHtml::submitButton('Search'); ?>
	</div>

<?php $this->endWidget(); ?>

</div><!-- search-form -->