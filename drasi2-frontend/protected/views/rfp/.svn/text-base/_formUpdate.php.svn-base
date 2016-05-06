<?php
Yii::import('ext.qtip.QTip');
Yii::import('ext.jqAutocomplete');

//Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/iphone-style-checkboxes.js');
//Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/iphone-style-checkboxes.css');
Yii::app()->clientScript->registerScriptFile(Yii::app()->request->baseUrl.'/themes/classic/js/jquery.autocomplete.js',CClientScript::POS_END);
Yii::app()->clientScript->registerCssFile(Yii::app()->request->baseUrl.'/css/jquery.autocomplete.css');


//jquery iphone checkbox -->
//http://awardwinningfjords.com/2009/06/16/iphone-style-checkboxes.html -->
?>
<!-- 
<script type="text/javascript" charset="utf-8">
$(window).load(function() {
 $('#Contract_diavgeiaPublished:checkbox').iphoneStyle({
	  checkedLabel: 'NAI',
	  uncheckedLabel: 'OXI'
	});

});
</script>
 -->
<div class="form">

<?php 

$form=$this->beginWidget('CActiveForm', array(
	 'htmlOptions'=>array('enctype'=>'multipart/form-data'),
	 'id'=>'contract-form',
     'stateful'=>true,
	//'enableAjaxValidation'=>true,
     'clientOptions' => array('validateOnChange'=>true, 'validateOnType'=>true,'validateOnSubmit'=>true),
     'focus'=>array($model,'title'),
)); 

?>

 	<p class="note">
 		<?php echo Yii::t('form','requiredStart');?> 
 		<span class="required">*</span> 
 		<?php echo Yii::t('form','requiredEnd');?>
 	</p>
	<?php 

$arrayErrors = $model;
$error="";
$error1='';

echo '<p>'.$form->errorSummary($arrayErrors).'</p>'; 
echo $error1;

		$opts = array(
	    'position' => array(
	        'corner' => array(
	            'target' => 'rightMiddle',
	            'tooltip' => 'leftMiddle'
	            )
	        ),
	    'show' => array(
	        'when' => array('event' => 'focus' ),
	        'effect' => array( 'length' => 300 )
	    ),
	    'hide' => array(
	        'when' => array('event' => 'blur' ),
	        'effect' => array( 'length' => 300 )
	    ),
	    'style' => array(
	        'color' => 'black',
	        'name' => 'blue',
	        'border' => array(
	            'width' => 7,
	            'radius' => 5,
	        ),
	    )
);
	
	QTip::qtip('.row input[title]', $opts);
		
	//$form=$this->beginWidget('ext.EActiveForm', array(
$form=$this->beginWidget('CActiveForm', array(
	 'htmlOptions'=>array('enctype'=>'multipart/form-data'),
	 'id'=>'payments-form',
  	'stateful'=>true,
	 'enableAjaxValidation'=>true,
     'clientOptions' => array('validateOnChange'=>true, 'validateOnType'=>true,),
    'focus'=>array($model,'title'),

)); 
	?>

	
	
	<div class="form-group">
	<fieldset>
	<legend><?php echo Yii::t('form','Basic');?></legend>
	<div class="row-line">
		<?php echo $form->labelEx($model,'title',array('title'=>Yii::t('form','ProcTitleDesc'))); ?>
		<?php
			echo $form->textField($model,'title',array('size'=>60,'maxlength'=>512,'title'=>Yii::t('form','ProcTitleDesc'),'width'=>'500px')); ?>
		<?php echo $form->error($model,'title'); ?>
	</div>
	<div class="clear"></div>
	<div class="row">
		<?php echo $form->labelEx($model,'dateSigned',array('title'=>Yii::t('contract','dateSignedDesc'))); ?>
		
		<?php 
		$form->widget('application.extensions.jui.EDatePicker',
              array(
                    'name'=>'Contract_dateSigned',
                    'attribute'=>'dateSigned',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              		'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
                   // 'htmlOptions'=>array('size'=>10)//'title'=>Yii::t('contract','dateSignedDesc'))
                   )
             );
            ?> 
        <?php echo $form->error($model,'dateSigned'); ?>
		   
	</div>
	<div class="row">
		<?php echo $form->labelEx($model,'since',array('title'=>Yii::t('contract','sinceDesc'))); ?>
		
		<?php 
		$form->widget('application.extensions.jui.EDatePicker',
              array(
                    'name'=>'Contract_since',
                    'attribute'=>'since',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('contract','sinceDesc'))
                   )
             );
            ?> 
        <?php echo $form->error($model,'since'); ?>
		   
	</div>
	
	<div class="row" id="untildiv" >
		<?php echo $form->labelEx($model,'until',array('title'=>Yii::t('contract','untilDesc'))); ?>
		
		<?php 
		$form->widget('application.extensions.jui.EDatePicker',
              array(
                    'name'=>'Contract_until',
                    'attribute'=>'until',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              		'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('contract','untilDesc'))
                   )
             );
            ?> 
        <?php echo $form->error($model,'until'); ?>
		   
	</div>
	
	<div class="row">
		<?php echo $form->labelEx($model,'protocolNumberCode'); ?>
		<?php echo $form->textField($model,'protocolNumberCode',array('size'=>60,'maxlength'=>128)); ?>
		<?php echo $form->error($model,'protocolNumberCode'); ?>
	</div>
	<div class="clear"></div>

	<div class="row" id="marginleft editorEmail">
		<?php echo $form->labelEx($model,'issuerEmail'); ?>
		<?php echo $form->textField($model,'issuerEmail',array('size'=>60,'maxlength'=>128)); ?>
		<?php echo $form->error($model,'issuerEmail'); ?>
	</div>
	<div class="row">
		<?php echo $form->labelEx($model,'awardProcedure'); ?>
		<?php echo $form->dropDownList($model,'awardProcedure',Lookup::items('award_procedure')); ?>
		<?php echo $form->error($model,'awardProcedure'); ?>
	</div>	
	<div class="row">
		<?php echo $form->labelEx($model,'RelatedADA',array('title'=>Yii::t('contract','Contract'))); ?>
		<?php
			echo $form->textField($model,'RelatedADA',array('size'=>60,'maxlength'=>512,'width'=>'500px')); ?>
		<?php echo $form->error($model,'RelatedADA'); ?>
	</div>
	<div class="clear"></div>

	</fieldset>
	</div>
	
	
	
	<div class="form-group">
	<fieldset>
		<legend><?php echo Yii::t('contract','contractParties');?></legend>
		<div class="row">
			<?php echo $form->labelEx($model,'OrganizationIdRefUnits'); ?>
			
			<?php 
			$Units = Units::items('Units',Yii::app()->user->RefId);
			if($Units){
			echo $form->dropDownList($model,'OrganizationIdRefUnits',Units::items('Units',Yii::app()->user->RefId),
		array(
			'ajax' => array(
			'type'=>'POST', //request type
			'url'=>CController::createUrl('Rfp/dynamicsigners'), //url to call.
			'update'=>'#signersname', //selector to update
			),'id'=>'OrgIdRef'
			)); 
			}else  echo "<font color='red'>PROBLEM</font>";
			//http://www.yiiframework.com/wiki/24/creating-a-dependent-dropdown/
		 ?>
			<?php echo $form->error($model,'OrganizationIdRefUnits'); ?>
		</div>

		<div class="row">
					<?php echo $form->labelEx($model,'signers'); ?>
					<?php echo $form->dropDownList($model,'signers',Units::items('Signers',Yii::app()->user->RefId),array('id'=>'signersname')); ?>	
					<?php echo $form->error($model,'signers'); ?>
		</div>
<br/><br/><br/>		<br/>	
<div class="clear"></div>

<div style="height: 10px;" class="clear"></div>
	</fieldset>
	

<?php 
	if($contractitemmanager){
?>
	<div class="form-group">
		<fieldset>
		
		<legend><?php echo Yii::t('form','Contract Data');?></legend>
		
	<table id="students">
	    <tbody>
	    <?php 
	    	foreach($contractitemmanager->items as $id=>$contractitem): ?> 
	        <?php $this->renderPartial('/contractitem/_formContractitem', array('id'=>$id, 'otherData'=>$contractitem, 
	        		'modelname'=>$modelname,'model'=>$contractitem,'form'=>$form,'Mainaction'=>$Mainaction,'type'=>$type));?>
	    <?php endforeach;?>
	    </tbody>
	</table>
	
	</fieldset>
	</div>
<?php 
	}
?>


	<div class="form-group">
	<fieldset>
	
	<legend><?php echo Yii::t('form','Related File');?></legend>
	<?php
		
			echo $form->labelEx($model,'document'); 
			echo $form->fileField($model,'document'); 
			echo $form->error($model,'document'); 
			echo "<div class='note'>". Yii::t('form','documentDetails');
			echo"</div>\n";?>
			
			
	
	</fieldset>
	</div>
	<?php 
		if(isset($_GET['uniqueDocumentCode'])){
			echo $form->hiddenField($model,'documentUrl', array('documentUrl',$model->documentUrl));
		}
		
		echo $form->hiddenField($model,'procurements', array('procurements',$model->procurements));
	?>
	<input type='hidden' name='step' value='3'/>

	
	<div class="row buttons">
		<?php echo CHtml::submitButton(Yii::t('form','Update')); ?>
	</div>
	
</div>

<?php $this->endWidget(); ?>

<?php $this->endWidget(); ?>


</div><!-- form -->