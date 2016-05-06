<?php 

Yii::import('ext.jqAutocomplete');

	
	$json_options = array(
    'script'=> $this->createUrl('cpvcodes/json',array('json'=>'true','limit'=>6)) . '&',
    'varName'=>'input',
    'shownoresults'=>true,
    'maxresults'=>16,
    'callback'=>'js:function(obj){ $(\'#json_info\').html(\'you have selected: \'+obj.id + \' \' + obj.value + \' (\' + obj.info + \')\'); }'   
);
 
// #test_json is your text field id 
// (please refer to the example view page for the field wrap)
jqAutocomplete::addAutocomplete('#Contractitem_cpvsid',$json_options);

?>

<div class="form">


<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'contractitem-form',
	'enableAjaxValidation'=>false,
)); ?>

	<p class="note">Fields with <span class="required">*</span> are required.</p>

	<?php echo $form->errorSummary($model); ?>
	
	<div style="margin-bottom: 10px;">
		<div class="clonedInput">
			<div id="test1"></div>
			<div class="clear"></div>
			<div class="row">
				<?php echo $form->labelEx($model,'quantity'); ?>
				<?php echo $form->textField($model,'quantity',array('size'=>24,'maxlength'=>24)); ?>
				<?php echo $form->error($model,'quantity'); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'cost'); ?>
				<?php echo $form->textField($model,'cost',array('size'=>24,'maxlength'=>24)); ?>
				<?php echo $form->error($model,'cost'); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'vatid'); ?>
				<?php //echo $form->dropDownList($model,'vatid',Lookup::items('FPA'),array('name'=>'vatid[]')); ?>	
				<?php echo $form->dropDownList($model,'vatid',Lookup::items('FPA')); ?>	

				<?php echo $form->error($model,'vatid'); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'currencyid'); ?>
				<?php echo $form->dropDownList($model,'currencyid',Lookup::items('Currency')); ?>	
				<?php //echo $form->dropDownList($model,'currencyid',Lookup::items('Currency'),array('name'=>'currencyid[]')); ?>	
			
				<?php echo $form->error($model,'currencyid'); ?>
			</div>
			
			
			<div class="clear"></div>
			<div class="row">
				<?php echo $form->labelEx($model,'description'); ?>
				<?php //echo $form->textArea($model,'description',array('name'=>'description[]','rows'=>6, 'cols'=>80));?>	
				<?php echo $form->textArea($model,'description',array('rows'=>6, 'cols'=>80));?>	

				<?php echo $form->error($model,'description'); ?>
			</div>
			
			<div class="row">
				<?php echo $form->labelEx($model,'cpvsid'); ?>
				<?php echo $form->textField($model,'cpvsid',array('size'=>80,'maxlength'=>1024, 'width'=>'500px')); ?>
				<?php echo $form->error($model,'cpvsid'); ?>

			</div>
			<div style="" class="clear"></div>
		</div>
	</div>
	
	<div class="clear10"></div>
	<div class="row buttons">
			<?php echo CHtml::submitButton('Create'); ?>
	
		<?php //echo CHtml::submitButton($model->isNewRecord ? 'Create' : 'Save'); ?>
	</div>

<?php $this->endWidget(); ?>

</div><!-- form -->