<?php
Yii::app()->clientScript->registerScriptFile(Yii::app()->request->baseUrl.'/themes/classic/js/jquery.autocomplete.js',CClientScript::POS_HEAD);
Yii::app()->clientScript->registerCssFile(Yii::app()->request->baseUrl.'/css/jquery.autocomplete.css');
Yii::app()->clientScript->registerCoreScript('jquery'); 

$this->breadcrumbs=array(
	Yii::t('form','SearchPayments'),
);
?>
<script>
function setAutocomplete()
{
    $(".user_selector").autocomplete("index.php?r=cpvcodes/json", {
        width: 320,
        dataType: 'json',
        highlight: false,
        scroll: true,
        scrollHeight: 300,
        parse: function(data) {
            data = data.results;
        	var returnedObjects = eval(data);
        	var i = 0;
        	var rows = new Array();
        	for (i = 0; i < returnedObjects.length; i++){
        	   rows[i] = { data:data[i], value:data[i].value, result:data[i].value };
        	}
        	return rows;        	    
     	},
	   formatItem: function(row, i, max) {
		   return row.value; },
       focus: function() {
					// prevent value inserted on focus
					return false;
				},
		multiple: false,
	    multipleSeparator: ";",
	    matchContains: true,
	    minChars: 4,
	   	minLength: 4
	               
    });
}
      $(document).ready(function() {
        setAutocomplete();
    //    $('#addParticipant').live('click', addParticipantFields);

    });
</script>



<h1><?php echo Yii::t('form','SearchPayments');?></h1>


<div class="search" id="1">
<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'contractitem-form',
)); ?>

	<?php echo $form->errorSummary($model); ?>

	<div style="margin-bottom: 10px;">
			<div id="test1"></div>
			<div class="clear"></div>
			
			<div class="row" style='width:460px;'>
				<?php echo $form->labelEx($model,'cpvsid'); ?>
				<?php echo $form->textField($model,'cpvsid',array('size'=>80,'maxlength'=>1024, 'width'=>'460px','class'=>'user_selector')); ?>
				<?php echo "<br/><span class='note' style='font-size:11px;padding-left:0px;'>".Yii::t('form','cpvDescription')."</span><br/>";?>
				<?php echo $form->error($model,'cpvsid'); ?>
			</div>
			<div class="row" style='width:200px;'>
				<?php echo $form->labelEx($model,'documentUrl'); ?>
				<?php echo $form->textField($model,'documentUrl',array('size'=>80,'maxlength'=>1024, 'width'=>'300px')); ?>
				<?php echo "<span class='note' style='font-size:11px;padding-left:0px;'>".Yii::t('form','adaDescription')."</span><br/>";?>
				<?php echo $form->error($model,'documentUrl'); ?>

			</div>
			<div class="clear"></div>
			<?php if (Yii::app()->user->isGuest){?>
				<div class="row">
					<?php echo $form->labelEx($model,'foreis'); ?>
					<?php 
					$userRefId=0;
					echo $form->dropDownList($model,"foreis",Lookup::items('OrganizationYpTextDrop',$userRefId),
					array(
						'ajax' => array(
						'type'=>'POST', //request type
						'url'=>CController::createUrl('Payments/Dynamicforeis'), //url to call.
						'update'=>'#ypoforeis', //selector to update
						),'id'=>'OrgIdRef'
						)); 
					?>	
					<?php echo $form->error($model,"foreis"); ?>
				</div>
				<div class="row">
						<?php echo $form->labelEx($model,'ypoforeis'); ?>
						<?php echo $form->dropDownList($model,'ypoforeis',$test= ODE::items('withid',$userRefId),array('id'=>'ypoforeis')); ?>	
						<?php echo $form->error($model,'ypoforeis'); ?>
				</div>
			<?php }else ?>
			<div  class="clear"></div>
			
			<div class="row">
				<?php echo $form->labelEx($model,'title'); ?>
				<?php echo $form->textField($model,'title',array('size'=>80,'maxlength'=>1024, 'width'=>'500px')); ?>
				<?php echo "<br/><span class='note' style='font-size:11px;padding-left:0px;'>".Yii::t('form','SearchTitleDescription')."</span><br/>";?>
				<?php echo $form->error($model,'title'); ?>

			</div>
			
			<div class="clear10"></div>
			<div class="row buttons" style="float:right;">
			<?php echo CHtml::submitButton(Yii::t('form','search')); ?>
			<input style="margin-left:10px;" type="button" name="clear" id="clear" value="<?php echo Yii::t('form','clear');?>" onClick="$(':input','#contractitem-form')
				 .not(':button, :submit, :reset, :hidden')
				 .val('')
				 .removeAttr('checked')
				 .removeAttr('selected');
							" />
			</div>
			
			<div class="clear"></div>
			</div><!--  general div -->
</div><!-- form -->
			
<?php $this->endWidget(); ?>
<?php 
	if(isset($dataProvider->rawData) && is_array($dataProvider->rawData)){
	$dataProvider->pagination->pageSize = 5;
	if($cost>0){
?>
	<div class="contract-content" id="1"><div class="title1">Συνολικό Κοστος όλων των αποτελεσμάτων: <?php echo $cost;?></div>
		<span class="note">το κόστος δεν συμπεριλαμβάνει ΦΠΑ</span><br> 
	</div>
<?php 
	}
	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProvider,
        'itemView'=>'_view',
));
	}else 
	echo Yii::t('zii','No results found');
	
	

?>

