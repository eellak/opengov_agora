<?php
Yii::import('ext.qtip.QTip');
//Yii::import('ext.jqAutocomplete');

//Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/iphone-style-checkboxes.js');
//Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/iphone-style-checkboxes.css');

Yii::app()->clientScript->registerScriptFile(Yii::app()->request->baseUrl.'/themes/classic/js/jquery.autocomplete.js',CClientScript::POS_END);
Yii::app()->clientScript->registerCssFile(Yii::app()->request->baseUrl.'/css/jquery.autocomplete.css');

Yii::app()->clientScript->registerCoreScript('jquery');

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
	

	if($step==1){
	
	//$form=$this->beginWidget('ext.EActiveForm', array(
	$form=$this->beginWidget('CActiveForm', array(
	 'htmlOptions'=>array('enctype'=>'multipart/form-data'),
	 'id'=>'payments-form',
  	'stateful'=>true,
	//'enableAjaxValidation'=>true,
     'clientOptions' => array('validateOnChange'=>true, 'validateOnType'=>true,),
    'focus'=>array($model,'title'),

)); 
	?>

<script type="text/javascript">
function setAutocomplete()
{
    $(".user_selector").autocomplete("index.php?r=procurmentids/json", {
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
        	   rows[i] = { data:data[i], value:data[i].info, result:data[i].info };
        	}
        	return rows;        	    
     	},
	   formatItem: function(row, i, max) {
		   return row.value; },
       focus: function() {
					// prevent value inserted on focus
					return false;
				},
		multiple: true,
	    multipleSeparator: ";",
	    matchContains: true,
	    minChars: 5,
	   	minLength: 5
	               
    });
}
      $(document).ready(function() {
        setAutocomplete();

    });

	</script>
	
		
	<div class="form-group">
	<fieldset>
	<legend><?php echo Yii::t('contract','ProcurementSelection');?></legend>
	<div class="row">
		<?php echo $form->labelEx($model,'procurements',array('title'=>Yii::t('contract','Contract'))); ?>
		<?php
			echo "<span class='note' style='font-size:11px;'>".Yii::t('contract','procurementAutoDescription')."</span><br/>";	
			echo $form->textField($model,'procurements',array('size'=>60,'maxlength'=>512,'width'=>'500px','class'=>'user_selector')); ?>
		<?php echo $form->error($model,'procurements'); ?>
	</div>
	
	<div class="row">
		<?php echo $form->labelEx($model,'RelatedADA',array('title'=>Yii::t('contract','Contract'))); ?>
		<?php
			echo "<span class='note' style='font-size:11px;'>".Yii::t('rfp','RelatedADADetails')."</span><br/>";	
			echo $form->textField($model,'RelatedADA',array('size'=>60,'maxlength'=>512,'width'=>'500px')); ?>
		<?php echo $form->error($model,'RelatedADA'); ?>
	</div>
	</fieldset>
	</div>
	<input type='hidden' name='step' value='2'/>
	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? Yii::t('form','Δημιουργία') : Yii::t('form','Επόμενο Βήμα')); ?>
	</div>
	
<?php 	

	 $this->endWidget(); 

}else{
?>
		<?php if($Mainaction=='createByProc'){?>
	<div class="form-group">
		<fieldset>
			<div class="row">
	                <?php echo $form->labelEx($model,'RelatedADA'); ?>
	                <?php echo $form->textField($model,'RelatedADA'); ?>
	                <?php echo $form->error($model,'RelatedADA'); ?>
	        </div>
	        <?php echo CHtml::button('Εισαγωγή στοιχείων από τη Διαύγεια', 
	        			array( 'onclick'=>"{hpiCheck();}",
	        				   'style'=>'float:left;margin-top:34px;' ) ); ?>
	       	<div id='hpistatus' style='float: left;padding-left:10px;padding-top:34px;'></div>
 		</fieldset>
      </div>
     
        <script type="text/javascript">
        function hpiCheck()
        {
           	var ADAVal = $('#Rfp_RelatedADA').val();
            <?php  echo CHtml::ajax(array(
            	  'url'=>CController::createUrl('rfp/Check'),
                  'data'=>array('RelatedADA'=>'js:$(\'#Rfp_RelatedADA\').val()'),
                            'type'=>'post',
                            'dataType'=>'json',
                            'success'=>"function(data)
                            {
                                $('#hpistatus').html(data.status);
                                $('#Rfp_dateSigned').val(data.dateSigned);
                                $('#Rfp_protocolNumberCode').val(data.protocolNumberCode);
                                $('#Rfp_title').val(data.title);
                                $('#OrgIdRef').val(data.OrgIdRef);
                                $('#signersname').val(data.signersname);
                                $('#Rfp_RelatedADA').val(data.ada);
                                
            				} ",
                                )) ?>
                return false;  
        } 
    
        </script>
        <!-- form -->
        <?php } ?>
	
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
		<?php echo $form->labelEx($model,'dateSigned',array('title'=>Yii::t('rfp','dateSignedDesc'))); ?>
		
		<?php 
		$form->widget('application.extensions.jui.EDatePicker',
              array(
                    'name'=>'Rfp_dateSigned',
                    'attribute'=>'dateSigned',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              		'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
                   )
             );
            ?> 
        <?php echo $form->error($model,'dateSigned'); ?>
		   
	</div>
	<div class="row">
		<?php echo $form->labelEx($model,'since',array('title'=>Yii::t('rfp','sinceDesc'))); ?>
		
		<?php 
		$form->widget('application.extensions.jui.EDatePicker',
              array(
                    'name'=>'Rfp_since',
                    'attribute'=>'since',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              		'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
//                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('contract','sinceDesc'))
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
                    'name'=>'Rfp_until',
                    'attribute'=>'until',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              		'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
                    //'htmlOptions'=>array('size'=>10,'title'=>Yii::t('contract','untilDesc'))
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
	
	<div class="clear"></div>

	</fieldset>
	</div>
	
	
	
	<div class="form-group">
	<fieldset>
		<legend><?php echo Yii::t('contract','contractParties');?></legend>
		<div class="row">
			<?php 
				 $userRefId = isset($model->OrganizationIdRef)?$model->OrganizationIdRef:Yii::app()->user->RefId;
				echo $form->labelEx($model,'OrganizationIdRefUnits'); 
				$Units = Units::items('Units',$userRefId);
				if($Units){
			 	echo $form->dropDownList($model,'OrganizationIdRefUnits',
			 	$Units,
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
					<?php echo $form->dropDownList($model,'signers',Units::items('Signers',$userRefId),array('id'=>'signersname')); ?>	
					<?php echo $form->error($model,'signers'); ?>
		</div>
<br/><br/><br/>		<br/>	
<div class="clear"></div>

<div style="height: 10px;" class="clear"></div>
	</fieldset>
	

<div class="form-group">
	<fieldset>
	
	<legend><?php echo Yii::t('form','Contract Data');?></legend>
	
<table id="students">
    <tbody>
    <?php 
    
    	foreach($contractitemmanager->items as $id=>$contractitem): 
        		$this->renderPartial('/contractitem/_formContractitem', array('id'=>$id, 'model'=>$contractitem, 
        		'modelname'=>$modelname,'form'=>$form,'Mainaction'=>$Mainaction,'type'=>$type));?>
    <?php endforeach;?>
    </tbody>
</table>


    <?php  $this->renderPartial('/contractitem/contractitemjs', array('contractitems'=>$contractitemmanager, 
    							'form'=>$form, 'type'=>$type,
								'modelname'=>$modelname,
        						'Mainaction'=>'none'));?>
</fieldset>
</div>



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
		if($step!=3)
		echo $form->hiddenField($model,'RelatedADA', array('RelatedADA',$model->RelatedADA));
		
	?>
	<input type='hidden' name='step' value='3'/>

	
	<div class="row buttons">
		<?php echo CHtml::submitButton(Yii::t('form','Δημιουργία')); ?>
	</div>
	
</div>
<?php }?>
<?php $this->endWidget(); ?>


</div><!-- form -->