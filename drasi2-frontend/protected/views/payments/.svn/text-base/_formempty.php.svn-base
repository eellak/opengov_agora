<?php
Yii::import('ext.qtip.QTip');
//Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/iphone-style-checkboxes.js',CClientScript::POS_END);
//Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/iphone-style-checkboxes.css');
Yii::app()->clientScript->registerScriptFile(Yii::app()->request->baseUrl.'/themes/classic/js/jquery.autocomplete.js');
Yii::app()->clientScript->registerCssFile(Yii::app()->request->baseUrl.'/css/jquery.autocomplete.css');

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
QTip::qtip('.row input[invoice]', $opts);

?>
<?php if(Yii::app()->user->hasFlash('createError')): ?>
<div class="errorSummary" id="payments-form_es_"><p>Παρακαλώ διορθώστε τα παρακάτω σφάλματα:</p>
<?php echo Yii::app()->user->getFlash('createError'); 
	
?>
</div>
<?php endif;
//this function draws an information
//about the contract to users. 
//$model->displayContractInfo();
 ?>
<div class="form">



<?php 
//jquery iphone checkbox -->
//http://awardwinningfjords.com/2009/06/16/iphone-style-checkboxes.html -->
?>
<!-- <script type="text/javascript" charset="utf-8">
$(window).load(function() {
 $('#Payments_diavgeiaPublished:checkbox').iphoneStyle({
	  checkedLabel: 'NAI',
	  uncheckedLabel: 'OXI'
	});

});
</script>
 -->
<?php 
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
<p class="note">
 		<?php echo Yii::t('form','requiredStart');?> 
 		<span class="required">*</span> 
 		<?php echo Yii::t('form','requiredEnd');?>
 	</p>
	<?php 
	//print_r($_POST);
	
$arrayErrors = $model;
$error="";
if(isset($_POST['Contractitempayments'])){
foreach ($contractitemmanager as $PMap){
   $error .= $form->errorSummary(array_merge(array($model),$PMap)).'</p>';
   	$arrayErrors = array_merge(array($model),$PMap);
	} 
}	


							
echo '<p>'.$form->errorSummary($arrayErrors).'</p>'; 
?>	
		<div class="form-group">
		<fieldset>
			<div class="row">
	                <?php echo $form->labelEx($model,'Related_ADA'); ?>
	                <?php echo $form->textField($model,'Related_ADA'); ?>
	                <?php echo $form->error($model,'Related_ADA'); ?>
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
           	var ADAVal = $('#Payments_Related_ADA').val();
            <?php  echo CHtml::ajax(array(
            	  'url'=>CController::createUrl('payments/Check'),
                  'data'=>array('Related_ADA'=>'js:$(\'#Payments_Related_ADA\').val()'),
                            'type'=>'post',
                            'dataType'=>'json',
                            'success'=>"function(data)
                            {
                                $('#hpistatus').html(data.status);
                                $('#payments_dateSigned').val(data.dateSigned);
                                $('#Payments_protocolNumberCode').val(data.protocolNumberCode);
                                $('#Payments_title').val(data.title);
                                $('#OrgIdRef').val(data.OrgIdRef);
                                $('#signersname').val(data.signersname);
                                $('#Payments_documentURlFromDiavgeia').val(data.RelatedDocument);
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
        <!-- form -->
        <?php //} 
        $cs = Yii::app()->getClientScript();  
	$cs->registerScript(
	  'hide-file-link',
	  '$(document).ready(function(){
	  var test=1;
	  if(jQuery("a#alink[href]").attr("href")){
	  	test = jQuery("a#alink[href]").attr("href"); 
	  	if(test!=1 && test.length>1)  $("#documentValue").css("display","none");
	  }
	  })',
	  CClientScript::POS_END
	);
	
	
        
        ?>
        
        
	
	
	<div class="form-group">
	<fieldset>
	<legend><?php echo Yii::t('form','Basic');?></legend>
	<div class="row">
		<?php echo $form->labelEx($model,'title',array('title'=>Yii::t('form','ProcTitleDesc'))); ?>
		<?php
			echo $form->textField($model,'title',array('size'=>60,'maxlength'=>512,'title'=>Yii::t('form','ProcTitleDesc'),'width'=>'500px')); ?>
		<?php echo $form->error($model,'title'); ?>
	</div>
	<div class="clear"></div>
	<div class="row">
		<?php echo $form->labelEx($model,'dateSigned',array('title'=>Yii::t('payments','dateSignedDesc'))); ?>
		
		<?php 
		$form->widget('application.extensions.jui.EDatePicker',
              array(
                    'name'=>'payments_dateSigned',
                    'attribute'=>'dateSigned',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              		'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
//                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('payments','dateSignedDesc'))
                   )
             );
            ?> 
        <?php echo $form->error($model,'dateSigned'); ?>
		   
	</div>
	<div class="row" id="marginleft">
		<?php echo $form->labelEx($model,'responsibilityAssumptionCode'); ?>
		<?php echo $form->textField($model,'responsibilityAssumptionCode',array('size'=>56,'maxlength'=>56,'width'=>'100px','title'=>'Είναι η διοικητική πράξη με την οποία γεννάται ή βεβαιώνεται υποχρέωση του Δημοσίου και των λοιπών φορέων της Γενικής Κυβέρνησης έναντι τρίτων')); ?>
		<?php echo $form->error($model,'responsibilityAssumptionCode'); ?>
	</div>

	
	<div class="row" id="marginleft">
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
		<?php echo $form->labelEx($model,'OrganizationIdRefUnits'); ?>
		<?php
		$Units = Units::items('Units',$model->OrganizationIdRef);
		if($Units){
		 echo $form->dropDownList($model,'OrganizationIdRefUnits',$Units,
		array(
			'ajax' => array(
			'type'=>'POST', //request type
			'url'=>CController::createUrl('Payments/dynamicsigners'), //url to call.
			'update'=>'#signersname', //selector to update
			),'id'=>'OrgIdRef'
			)); 
		//http://www.yiiframework.com/wiki/24/creating-a-dependent-dropdown/
		}else echo "<font color='red'>PROBLEM</font>";
		 ?>
		<?php echo $form->error($model,'OrganizationIdRefUnits'); ?>
	</div>
	<div class="clear"></div>

	<div class="row">
		<?php echo $form->labelEx($model,'signers'); ?>
		<?php 
			echo $form->dropDownList($model,'signers',Units::items('Signers',$model->OrganizationIdRef),array('id'=>'signersname'));
		?>	
		<?php echo $form->error($model,'signers'); ?>
	</div>

	
	</fieldset>
	</div>
	<div class="form-group">
	<fieldset>
	
	<legend><?php echo Yii::t('form','Basic Data');?></legend>
	<table id="students">
	
   <thead>
        <tr>
            <td>
                <?php
                		echo "<span style=\"cursor:pointer\">";
                		echo CHtml::link(Yii::t('form','add'), '', array('onClick'=>'addStudent($(this))', 'class'=>'add',/* 'submit'=>'', 'params'=>array('Student[command]'=>'add', 'noValidate'=>true)/**/));
            			echo"</span>";
            			
            		?>
            </td>
        </tr>
    </thead>
    <tbody>
    <?php 
    
    	foreach($contractitemmanager->items as $id=>$contractitem): ?> 
        <?php $this->renderPartial('/contractitempayments/_formContractitem', array('id'=>$id, 'model'=>$contractitem, 
        				'form'=>$form,'action'=>'createempty'));?>
    <?php endforeach;?>
    </tbody>
</table>
    <?php $this->renderPartial('/contractitempayments/contractitemjs', array('contractitems'=>$contractitemmanager, 
    							'contractid'=>0,'form'=>$form,'action'=>'createempty'));?>

</fieldset>
</div>

 
	
	<div class="form-group">
	<fieldset>
	<legend><?php echo Yii::t('form','Related File');?></legend>
	
	<?php
		echo"<div id='documentValue'>";
			echo $form->labelEx($model,'document'); 
			echo $form->fileField($model,'document'); 
			echo $form->error($model,'document'); 
			echo "<div class='note'>". Yii::t('form','documentDetails');
			echo"</div>\n";
		echo"</div>\n";
	?>
	
			
	
	<?php 
	echo"<div id='documentUrlValue'>";
			if($model->documentURlFromDiavgeia!=''){
				echo"<center>\n";
				echo"<div class=\"download\" id=\"download\">\n";
				echo CHtml::link(CHtml::encode(Yii::t('form','downloadFile')),$model->documentURlFromDiavgeia,array('id'=>'alink'));
				echo"</div>\n";
				echo"</center>\n";
			}
	echo"</div>";
			?>
</fieldset>
	</div>
	<?php 
		if(isset($_GET['uniqueDocumentCode'])){
			echo $form->hiddenField($model,'documentUrl', array('documentUrl',$model->documentUrl));
		}
		echo $form->hiddenField($model,'contract', array('contract',$model->contract));
		echo $form->hiddenField($model,'documentURlFromDiavgeia');
		
	?>
	<input type='hidden' name='step' value='3'/>
	
	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? Yii::t('form','Δημιουργία') : Yii::t('form','Save')); ?>
	</div>
<?php $this->endWidget(); ?>


</div><!-- form -->

