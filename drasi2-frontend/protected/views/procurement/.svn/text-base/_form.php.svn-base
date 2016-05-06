<?php
Yii::import('ext.qtip.QTip');
//Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/iphone-style-checkboxes.js',CClientScript::POS_END);
//Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/iphone-style-checkboxes.css');
Yii::app()->clientScript->registerScriptFile(Yii::app()->request->baseUrl.'/themes/classic/js/jquery.autocomplete.js',CClientScript::POS_END);
Yii::app()->clientScript->registerCssFile(Yii::app()->request->baseUrl.'/css/jquery.autocomplete.css');





//jquery iphone checkbox -->
//http://awardwinningfjords.com/2009/06/16/iphone-style-checkboxes.html -->
?>
<!-- 
<script type="text/javascript" charset="utf-8">
$(window).load(function() {
 $('#Procurement_diavgeiaPublished:checkbox').iphoneStyle({
	  checkedLabel: 'NAI',
	  uncheckedLabel: 'OXI'
	});

});
</script>
 -->
 
<div class="form">

<?php if(Yii::app()->user->hasFlash('createError')): ?>
<div class="errorSummary" id="procurement-form_es_"><p>Παρακαλώ διορθώστε τα παρακάτω σφάλματα:</p>
<?php echo Yii::app()->user->getFlash('createError'); 
	
?>
</div>
<?php endif; ?>

<?php

//$form=$this->beginWidget('ext.EActiveForm', array(
$form=$this->beginWidget('CActiveForm', array(
	 'htmlOptions'=>array('enctype'=>'multipart/form-data'),
	 'id'=>'procurement-form',
  	'stateful'=>true,
	 'enableAjaxValidation'=>false,
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
$arrayErrors = $model;
$error="";
if(isset($_POST['Contractitem'])){
foreach ($contractitemmanager as $PMap){
   $error .= $form->errorSummary(array_merge(array($model),$PMap)).'</p>';
   	$arrayErrors = array_merge(array($model),$PMap);
	} 
}	

echo '<p>'.$form->errorSummary($arrayErrors).'</p>'; 
?>
	<?php //echo $form->errorSummary(array($model,$contractitemmanager));
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
	QTip::qtip('.row input[responsibilityAssumptionCode]', $opts);
	
	?>
	
	<?php if($Mainaction=='approverequest'){?>
	<div class="form-group">
		<fieldset>
			<div class="row">
	                <?php echo $form->labelEx($model,'ADA'); ?>
	                <?php echo $form->textField($model,'ADA'); ?>
	                <?php echo $form->error($model,'ADA'); ?>
	        </div>
	        <?php echo CHtml::button('Εισαγωγή στοιχείων από τη Διαύγεια', array( 'onclick'=>"{hpiCheck();}",'style'=>'float:left;margin-top:34px;' ) ); ?>
	       	<div id='hpistatus' style='float: left;padding-left:10px;padding-top:34px;'></div>
 		</fieldset>
      </div>
     
        <script type="text/javascript">
        function hpiCheck()
        {
           	var ADAVal = $('#Procurement_ADA').val();
            <?php  echo CHtml::ajax(array(
            	  'url'=>CController::createUrl('procurement/Check'),
                  'data'=>array('ADA'=>'js:$(\'#Procurement_ADA\').val()'),
                            'type'=>'post',
                            'dataType'=>'json',
                            'success'=>"function(data)
                            {
                                $('#hpistatus').html(data.status);
                                $('#Procurement_dateSigned').val(data.dateSigned);
                                $('#Procurement_protocolNumberCode').val(data.protocolNumberCode);
                                $('#Procurement_title').val(data.title);
                                $('#OrgIdRef').val(data.OrgIdRef);
                                $('#signersname').val(data.signersname);
                                $('#Contractitem_n0_KAE').val(data.ari8mosKAE);
 								
                                $('#Procurement_documentURlFromDiavgeia').val(data.RelatedDocument);
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

	<?php 
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
        
	
	
	
 
	<?php }?>
<!--    
                                     -->
	<div class="form-group">
	<fieldset>
	<legend><?php echo Yii::t('form','Basic');?></legend>
	<div class="row">
		<?php echo $form->labelEx($model,'title',array('title'=>Yii::t('form','ProcTitleDesc'))); ?>
		<?php
			if(isset($modelAll->title))
				echo $form->labelEx($modelAll,$modelAll->title,array('title'=>$modelAll->title ." ". Yii::t('procurement','ReadyToApproveData'),'class'=>'ToApprove')); 
			echo $form->textField($model,'title',array('size'=>60,'maxlength'=>512,'title'=>Yii::t('form','ProcTitleDesc'),'width'=>'500px')); ?>
		<?php echo $form->error($model,'title'); ?>
	</div>
	<div class="clear"></div>
	<div class="row">
		<?php echo $form->labelEx($model,'dateSigned',array('title'=>Yii::t('procurement','dateSignedDesc')));
		if(isset($modelAll->dateSigned))
				echo $form->labelEx($modelAll,$modelAll->dateSigned,
					array('title'=>$modelAll->dateSigned ." ". Yii::t('procurement','ReadyToApproveData'),
						'class'=>'ToApprove')); 

		
		$form->widget('application.extensions.jui.EDatePicker',
              array(
                    'name'=>'Procurement_dateSigned',
                    'attribute'=>'dateSigned',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              		'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
              
//                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('procurement','dateSignedDesc'))
                   )
             );
            ?> 
        <?php echo $form->error($model,'dateSigned'); ?>
		   
	</div>

	
	<div class="row" >
		<?php echo $form->labelEx($model,'protocolNumberCode'); 
		if(isset($modelAll->protocolNumberCode) &&  $modelAll->protocolNumberCode!='')
			echo $form->labelEx($modelAll,$modelAll->protocolNumberCode,
						array('title'=>$modelAll->protocolNumberCode ." ". Yii::t('procurement','ReadyToApproveData'),
						'class'=>'ToApprove')); 
		else if(isset($modelAll->title)) echo "<br/>";
		
		 echo $form->textField($model,'protocolNumberCode',array('size'=>60,'maxlength'=>128)); ?>
		<?php echo $form->error($model,'protocolNumberCode'); ?>
	</div>
	<div class="row" id="marginleft editorEmail">
			<?php echo $form->labelEx($model,'issuerEmail'); 
				if(isset($modelAll->issuerEmail) && $modelAll->issuerEmail!=''){
					echo $form->labelEx($modelAll,$modelAll->issuerEmail,
						 array('title'=>$modelAll->issuerEmail ." ". Yii::t('procurement','ReadyToApproveData'),
						 'class'=>'ToApprove')); 
				}else if(isset($modelAll->title)) echo "<br/>";
				echo $form->textField($model,'issuerEmail',array('size'=>60,'maxlength'=>128));
				
	 ?>
			<?php echo $form->error($model,'issuerEmail'); ?>
		</div>
		<div class="row">
		<?php echo $form->labelEx($model,'fulfilmentDate');
			if(isset($modelAll->fulfilmentDate) && $modelAll->fulfilmentDate!=''){
					echo $form->labelEx($modelAll,$modelAll->fulfilmentDate,
						 array('title'=>$modelAll->fulfilmentDate ." ". Yii::t('procurement','fulfilmentDate'),
						 'class'=>'ToApprove')); 
				}else if(isset($modelAll->title)) echo "<br/>";
			
		$form->widget('application.extensions.jui.EDatePicker',
              array(
                    'name'=>'Procurement_fulfilmentDate',
                    'attribute'=>'fulfilmentDate',
                    'language'=>'el',
                    'mode'=>'imagebutton',
              		'model'=>$model,
              		'theme'=>'humanity',
                    'value'=>date('d/m/Y'),
                    'dateFormat'=>'dd/mm/yy',
              		'options'=>array("changeMonth"=>true, "changeYear"=>true, "showButtonPanel"=>true), 
//                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('procurement','dateSignedDesc'))
                   )
             );
            ?> 
        <?php echo $form->error($model,'fulfilmentDate'); ?>
		   
	</div>
		
		<div class="clear"></div>
		
		<div class="row">
			<?php  
				if(isset($modelAll->title) || (isset($model->approvesRequest) && $model->approvesRequest!='')){
					 echo $form->labelEx($model,'awardProcedure');
					 echo $form->dropDownList($model,'awardProcedure',Lookup::items('award_procedure'));
		 			 echo $form->error($model,'awardProcedure');
				}
					?>
		</div>
		
		
		<div class="row">
			<?php  
				if(isset($modelAll->title) || (isset($model->approvesRequest) && $model->approvesRequest!='')){
					 echo $form->labelEx($model,'responsibilityAssumptionCode');
					 $this->widget('CMaskedTextField',
                   	 array(
                         'mask'=>'9999/*?************',
                         'model'=>$model,
                   	 	 'placeholder' => '_',
                         'attribute'=>'responsibilityAssumptionCode',
                         'htmlOptions' => array('size' => 20,'title'=>Yii::t('form','responsibilityAssumptionCodeDesc')),
                   	 	 
                   	 ));
				
					// echo $form->textField($model,'responsibilityAssumptionCode',array('size'=>60,'maxlength'=>128));
					 echo $form->error($model,'responsibilityAssumptionCode');
				}
					?>
		</div>
		<div class="clear"></div>
		
	<div class="row">
		<?php echo $form->labelEx($model,'OrganizationIdRefUnits'); 
			if(isset($modelAll->OrganizationIdRefUnits)){
				$UnitName = Units::item('Units',$modelAll->OrganizationIdRefUnits,$modelAll->OrganizationIdRef);
				if($UnitName){
					echo $form->labelEx($modelAll,Units::item('Units',$modelAll->OrganizationIdRefUnits,$modelAll->OrganizationIdRef),
						array('title'=>$UnitName ." ". Yii::t('procurement','ReadyToApproveData'),'class'=>'ToApprove')); 
				}
			}
		 $IdRef = isset($model->OrganizationIdRef)?$model->OrganizationIdRef:Yii::app()->user->RefId;
		
		 Yii::app()->user->RefId = isset($model->OrganizationIdRef)?$model->OrganizationIdRef:Yii::app()->user->RefId;
		$Units = Units::items('Units',Yii::app()->user->RefId);
		if($Units){
		echo $form->dropDownList($model,'OrganizationIdRefUnits',$Units,
		array(
			'ajax' => array(
			'type'=>'POST', //request type
			'url'=>CController::createUrl('Procurement/dynamicsigners'), //url to call.
			'update'=>'#signersname', //selector to update
			),'id'=>'OrgIdRef'
			)); 
		//http://www.yiiframework.com/wiki/24/creating-a-dependent-dropdown/
		}else echo "<font color='red'>PROBLEM</font>";
		 ?>
		<?php echo $form->error($model,'OrganizationIdRefUnits'); ?>
	</div>

	<div class="row">
		<?php echo $form->labelEx($model,'signers'); 	
			if(isset($modelAll->signers))
				echo $form->labelEx($modelAll,Units::item('Signers',$modelAll->signers,$modelAll->OrganizationIdRef),array('title'=>Units::item('Signers',$modelAll->signers,$modelAll->OrganizationIdRef),'class'=>'ToApprove')); 
		 echo $form->dropDownList($model,'signers',Units::items('Signers',Yii::app()->user->RefId),array('id'=>'signersname')); ?>	
		<?php echo $form->error($model,'signers'); ?>
	</div>
	
	
	</fieldset>
	</div>
	<div class="form-group">
	<fieldset>
	
	<legend><?php echo Yii::t('form','Contract Data');?></legend>
	
<table id="students">
    <thead>
        <tr>
            <td>
            
                <?php
                	if($type=='procurement'  && $Mainaction!='approverequest'){
                	echo "<span style=\"cursor:pointer\">"; 
                	echo CHtml::link(Yii::t('form','add'), '', array('onClick'=>'addStudent($(this))', 'class'=>'add',/* 'submit'=>'', 'params'=>array('Student[command]'=>'add', 'noValidate'=>true)/**/));
                	echo "</span>";	
                	}
                ?>
            </td>
        </tr>
    </thead>
    <tbody>
    <?php 
    	
    	foreach($contractitemmanager->items as $id=>$contractitem): ?> 
        <?php 
        		$this->renderPartial('/contractitem/_formContractitem', array('id'=>$id, 'model'=>$contractitem, 
        							'modelname'=>$modelname, 'Mainaction'=>$Mainaction,
        							'form'=>$form, 'type'=>$type));?>
    <?php endforeach;?>
    </tbody>
</table>

</fieldset>
</div>
	
    <?php $this->renderPartial('/contractitem/contractitemjs', array('contractitems'=>$contractitemmanager, 
    							'form'=>$form, 'type'=>'procurement',
								'modelname'=>$modelname,
        						'Mainaction'=>'none'));?>
	<div class="form-group">
		<fieldset>
		<legend><?php echo Yii::t('form','Related File');?></legend>
		
	<?php 
		//if it is an update action 
		//we dont need to upload a file

		
		if(isset($Mainaction) && $Mainaction!='update'){
			echo"<div id='documentValue'>";
				echo $form->labelEx($model,'document'); 
				echo $form->fileField($model,'document'); 
				echo $form->error($model,'document');
				echo "<div class='note'>". Yii::t('form','documentDetails');
				echo"</div>\n";
			echo"</div>\n";
		
		}else {
			echo"<center>\n";
			echo"<div class=\"download\">\n";
			echo CHtml::link(CHtml::encode(Yii::t('form','downloadFile')),array('procurement/downloadproc','documentUrl'=>$model->documentUrl));
			echo"</div>\n";
			
			echo"</center>\n";
		}
		
			echo"<div id='documentUrlValue'>";
			if($model->documentURlFromDiavgeia!=''){
				echo"<center>\n";
				echo"<div class=\"download\" id=\"download\">\n";
				echo CHtml::link(CHtml::encode(Yii::t('form','downloadFile')),$model->documentURlFromDiavgeia,array('id'=>'alink'));
				echo"</div>\n";
				echo"</center>\n";
			}
			echo"</div>";
			
			
	//}*/
	

		?>
		
		</fieldset>
		</div>

	<?php 
		if(isset($_GET['uniqueDocumentCode'])){
			echo $form->hiddenField($model,'documentUrl', array('documentUrl',$model->documentUrl));
		}
		if(isset($model->approvesRequest))
			echo $form->hiddenField($model,'approvesRequest', array('approvesRequest',$model->approvesRequest));
			
		echo $form->hiddenField($model,'documentURlFromDiavgeia');
			
	?>
	<div class="row buttons">
		<?php 
				if($Mainaction=='approverequest')
				echo CHtml::submitButton(Yii::t('procurement','Approve Procurement'));
				else
				echo CHtml::submitButton($model->isNewRecord ? Yii::t('form','Δημιουργία') : Yii::t('form','Save')); ?>
	</div>
<?php $this->endWidget(); ?>

</div>
