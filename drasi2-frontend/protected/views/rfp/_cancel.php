<?php 

$this->breadcrumbs=array(
		Yii::t('rfp','RFP')=>array('indexproc'), 
	 Yii::t('form','Cancel') ." ".Yii::t('rfp','RFP'),$model->title,
);

$this->menu=array(
	array('label'=>Yii::t('rfp','List'), 'url'=>array('index')),
);


?>
<h1><?php echo Yii::t('form','Cancel') ." ".Yii::t('rfp','RFP');?></h1>
<div style="padding:10px;"> <?php echo Yii::t('form','Cancel Details');?></div>

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
		<legend><?php echo Yii::t('form','Cancel') ." ".Yii::t('rfp','RFP')."-". Yii::t('form','Canceldescription');?></legend>
		<div class="row-line" >
					<?php  echo $form->labelEx($model,'cancellationType');
					 echo $form->dropDownList($model,'cancellationType',Lookup::items('cancellationType'));
		 			 echo $form->error($model,'cancellationType'); ?>
		</div>
		<div class="row-line">
					<?php echo $form->labelEx($model,'ADACancel'); 
					echo $form->textField($model,'ADACancel',array('size'=>60,'maxlength'=>60,'width'=>'50px')); 
					echo $form->error($model,"ADACancel");
					echo CHtml::button('Εισαγωγή στοιχείων από τη Διαύγεια', array( 'onclick'=>"{hpiCheck();}",'style'=>'float:right;margin-left:20px;' ) ); ?>
	       			<div id='hpistatus' style='float: right;padding-left:10px;padding-top:4px;'></div>
		</div>
		<script type="text/javascript">
        function hpiCheck()
        {
           	var ADAVal = $('#Rfp_ADACancel').val();
            <?php  echo CHtml::ajax(array(
            	  'url'=>CController::createUrl('rfp/Check'),
                  'data'=>array('ADA'=>'js:$(\'#Rfp_ADACancel\').val()'),
                            'type'=>'post',
                            'dataType'=>'json',
                            'success'=>"function(data)
                            {
                                $('#hpistatus').html(data.status);
                                if(data.RelatedDocument !=''){
                                	 $('#documentValue').css('display','none');
                                	 $('#documentUrlValue').css('display','inline');
                                	 $('#documentUrlValue').html(data.documentURL);
                                }else{
                                	 $('#documentValue').css('display','inline');
                                	 $('#documentUrlValue').css('display','none');
                                }
                                
            				} ",
                                )) ?>
                return false;  
        } 
    
        </script>
    
		<div class="clear"></div>
		
		<div class="row-line">
					<?php echo $form->labelEx($model,'Deldescription'); ?>
					<?php echo $form->textArea($model,"Deldescription",array('rows'=>6, 'cols'=>80));?>	
					<?php echo $form->error($model,"Deldescription"); ?>
		</div>
		<div class="clear"></div>
	 	</fieldset>
 	</div>
 	 
 	
 		<div class="row buttons">
		<?php echo CHtml::submitButton(Yii::t('form','Cancel')); ?>
	</div>
	<?php $this->endWidget(); ?>
</div> 	