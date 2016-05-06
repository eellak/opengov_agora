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
if(isset($_POST['Contractitem'])){
foreach ($contractitemmanager as $PMap){
   $error .= $form->errorSummary(array_merge(array($model),$PMap)).'</p>';
   	$arrayErrors = array_merge(array($model),$PMap);
	} 
}	
if(isset($_POST['Signers'])){
	foreach ($signersmanager as $PMap){
		 $error1 .= $form->errorSummary($PMap);
	}
}


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
	 'enableAjaxValidation'=>true,
     'clientOptions' => array('validateOnChange'=>true, 'validateOnType'=>true,),
    'focus'=>array($model,'title'),

)); 
	?>


	<div class="form-group">
	<fieldset>
	<?php 

	if($Mainaction=='createByProc'){
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
	<legend><?php echo Yii::t('contract','ProcurementSelection');?></legend>
	<div class="row">
		<?php echo $form->labelEx($model,'procurements',array('title'=>Yii::t('contract','Contract'))); ?>
		<?php
			echo "<span class='note' style='font-size:11px;'>".Yii::t('contract','procurementAutoDescription')."</span><br/>";	
			echo $form->textField($model,'procurements',array('size'=>60,'maxlength'=>512,'width'=>'500px','class'=>'user_selector')); ?>
		<?php echo $form->error($model,'procurements'); ?>
	</div>
	<?php } ?>
	<?php if($Mainaction=='createByNotice'){ ?>
	<script type="text/javascript">
function setAutocomplete()
{
    $(".user_selector").autocomplete("index.php?r=noticeids/json", {
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
		multiple: false,
	    matchContains: true,
	    minChars: 2,
	   	minLength: 2
	               
    });
}
      $(document).ready(function() {
        setAutocomplete();

    });

	</script>
	<div class="row">
		<?php echo $form->labelEx($model,'noticeADA',array('title'=>Yii::t('rfp','RFP one'))); ?>
		<?php
			echo "<span class='note' style='font-size:11px;'>".Yii::t('rfp','noticeADA')."</span><br/>";	
			echo $form->textField($model,'noticeADA',array('size'=>60,'maxlength'=>512,'width'=>'500px','class'=>'user_selector')); ?>
		<?php echo $form->error($model,'noticeADA'); ?>
	</div>
	<?php }?>
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
	<?php 
		if(isset($_POST['Contract']['untilexists']) && $_POST['Contract']['untilexists']!=0) $style='display:none';
		else $style='';
		?>
	<div class="row" id="untildiv" style='<?php echo $style;?>'>
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
		<?php 
		echo $form->labelEx($model,'untilexists');
		if(isset($_POST['Contract']['untilexists']) && $_POST['Contract']['untilexists']!=0) 
				$value = $_POST['Contract']['untilexists'];
			else 
				$value='';
		 echo $form->checkBox($model,'untilexists',
			   array('checked'=>$value,
			 'onclick'=>'$(this).is(":checked")?$("#untildiv").hide():$("#untildiv").show();'));
		
		?>
		<?php echo $form->error($model,'untilexists'); ?>
	</div>
	<div class="clear"></div>
	
	<div class="row">
		<?php echo $form->labelEx($model,'protocolNumberCode'); ?>
		<?php echo $form->textField($model,'protocolNumberCode',array('size'=>60,'maxlength'=>128)); ?>
		<?php echo $form->error($model,'protocolNumberCode'); ?>
	</div>
	<div class="row" id="marginleft editorEmail">
		<?php echo $form->labelEx($model,'issuerEmail'); ?>
		<?php echo $form->textField($model,'issuerEmail',array('size'=>60,'maxlength'=>128)); ?>
		<?php echo $form->error($model,'issuerEmail'); ?>
	</div>
	<div class="clear"></div>
	
	<!-- <div class="row">
		<?php //echo $form->labelEx($model,'diavgeiaPublished'); ?>
		<?php //echo $form->checkBox($model,'diavgeiaPublished',array('class'=>'checkbox','value'=>'on','selected'=>true));?>
		<?php //echo $form->error($model,'diavgeiaPublished'); ?>
	</div> --> 
	
	<div class="row">
				<?php echo $form->labelEx($model,'contractType'); ?>
				<?php echo $form->dropDownList($model,'contractType',Lookup::items('contractType')); ?>				
				<?php echo $form->error($model,'contractType'); ?>
	</div>
	<div class="row">
				<?php echo $form->labelEx($model,'commissionCriteria'); ?>
				<?php echo $form->dropDownList($model,'commissionCriteria',Lookup::items('commissionCriteria')); ?>				
				<?php echo $form->error($model,'commissionCriteria'); ?>
	</div>
	
	<div class="clear"></div>
	<div class="row">
		<?php echo $form->labelEx($model,'contractingAuthority'); ?>
		<?php echo $form->dropDownList($model,'contractingAuthority',Lookup::items('contracting_authority')); ?>
		<?php echo $form->error($model,'contractingAuthority'); ?>
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
		<legend><?php echo Yii::t('contract','ADAS');?></legend>
		<?php echo "<div class='note'>". Yii::t('contract','DiavgeiaADAlist');
		echo"</div>\n";?>
		<div class="row">
			<?php
				if($model->noticeADA){ 
					echo $form->labelEx($model,'noticeADA'); 
					echo CHtml::link('Δείτε την Προκήρυξη ',array('rfp/viewrfp','uniqueDocumentCode'=>$model->noticeADA), array('target'=>'_blank'));      
					
				}
				?>
		</div>
		<div class="row">
			<?php echo $form->labelEx($model,'ADAkatakurosis'); ?>
			<?php echo $form->textField($model,'ADAkatakurosis',array('size'=>60,'maxlength'=>128)); ?>
			<?php echo $form->error($model,'ADAkatakurosis'); ?>
		</div>
		<div class="row">
			<?php echo $form->labelEx($model,'ADAanathesis'); ?>
			<?php echo $form->textField($model,'ADAanathesis',array('size'=>60,'maxlength'=>128)); ?>
			<?php echo $form->error($model,'ADAanathesis'); ?>
		</div>
	</fieldset>
	</div>	
	
	<div class="form-group">
	<fieldset>
		<legend><?php echo Yii::t('contract','ContractFunds');?></legend>
	<?php 
	
	?>	
		<div class="row">
			<?php echo $form->labelEx($model,'PublicFundsCheckBox'); 
			if(isset($_POST['Contract']['PublicFundsCheckBox']) && $_POST['Contract']['PublicFundsCheckBox']!=0) 
				$value = $_POST['Contract']['PublicFundsCheckBox'];
			else 
				$value='';
			 echo $form->checkBox($model,'PublicFundsCheckBox',array('checked'=>$value,
			 'onclick'=>'$(this).is(":checked")?$("#Contract_PublicFundsSAEdiv").show():$("#Contract_PublicFundsSAEdiv").hide();'));
			 echo $form->error($model,'PublicFundsCheckBox');
			 ?>
			<?php // ?>
		</div>
		<?php 
			if(isset($_POST['Contract']['PublicFundsCheckBox']) && $_POST['Contract']['PublicFundsCheckBox']!=0) $style='';
			else 
				$style='display:none';
		?>
		
		<div class="row" id="Contract_PublicFundsSAEdiv" style='<?php echo $style;?>'>
			<?php echo $form->labelEx($model,'PublicFundsSAE'); ?>
			<?php echo $form->textField($model,'PublicFundsSAE',array('size'=>60,'maxlength'=>128)); ?>
			<?php echo $form->error($model,'PublicFundsSAE'); ?>
		</div>
		<div class="clear"></div>	
		<div class="row">
			<?php echo $form->labelEx($model,'CofinancedCheckBox'); 
			if(isset($_POST['Contract']['CofinancedCheckBox']) && $_POST['Contract']['CofinancedCheckBox']!=0) 
				$value = $_POST['Contract']['CofinancedCheckBox'];
			else 
				$value='';
			 echo $form->checkBox($model,'CofinancedCheckBox',array('checked'=>$value,
			 'onclick'=>'$(this).is(":checked")?$("#Contract_CofinancedOPSdiv").show():$("#Contract_CofinancedOPSdiv").hide();'));
			 echo $form->error($model,'CofinancedCheckBox');
			 ?>
		</div>
		<?php 
			if(isset($_POST['Contract']['CofinancedCheckBox']) && $_POST['Contract']['CofinancedCheckBox']!=0) $style='';
			else 
				$style='display:none';
		?>
		
		<div class="row" id="Contract_CofinancedOPSdiv" style='<?php echo $style;?>'>
			<?php echo $form->labelEx($model,'CofinancedOPS'); ?>
			<?php echo $form->textField($model,'CofinancedOPS',array('size'=>60,'maxlength'=>128)); ?>
			<?php echo $form->error($model,'CofinancedOPS'); ?>
		</div>
	</fieldset>
	</div>	
	<div class="form-group">
	<fieldset>
		<legend><?php echo Yii::t('contract','contractParties');?></legend>
		<div class="row">
			<?php 
			$IdRef = isset($model->OrganizationIdRef)?$model->OrganizationIdRef:Yii::app()->user->RefId;
			echo $form->labelEx($model,'OrganizationIdRefUnits');
			$Units =  Units::items('Units',$IdRef);
			if($Units){
			echo $form->dropDownList($model,'OrganizationIdRefUnits',
				  $Units,
		array(
			'ajax' => array(
			'type'=>'POST', //request type
			'url'=>CController::createUrl('Contract/dynamicsigners'), //url to call.
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
					<?php echo $form->dropDownList($model,'signers',Units::items('Signers',$IdRef),array('id'=>'signersname')); ?>	
					<?php echo $form->error($model,'signers'); ?>
		</div>
<br/><br/><br/>		<br/>	
<div class="clear"></div>
<div><h3><?php echo Yii::t('form','Signers-data');?></h3></div>
<div class="clear"></div>
<table id="signer">
    <thead>
        <tr>
            <td>
                <?php 
                	if($Mainaction!='extend'){
                		echo "<span style=\"cursor:pointer\">";
                		echo CHtml::link(Yii::t('form','add'), '', array('onClick'=>'addSigners($(this))', 'class'=>'add',/* 'submit'=>'', 'params'=>array('Student[command]'=>'add', 'noValidate'=>true)/**/));
                		echo"</span>";
                	}
                ?>
            </td>
        </tr>
    </thead>
    <tbody>
	 	<?php foreach($signersmanager->items as $id=>$signeritem): ?> 
        	<?php $this->renderPartial('_formSigners', array('id'=>$id, 'model'=>$signeritem, 'form'=>$form,'Mainaction'=>$Mainaction));?>
    	<?php endforeach;?>
    
		 </tbody>
</table>
		
		<div style="height: 10px;" class="clear"></div>
	</fieldset>
	

<div class="form-group">
	<fieldset>
	
	<legend><?php echo Yii::t('form','Contract Data');?></legend>
	
<table id="students">
    <thead>
        <tr>
            <td>
                <?php
                	if($Mainaction!='extend'){
                		echo "<span style=\"cursor:pointer\">"; 
                		echo CHtml::link(Yii::t('form','add'), '', array('onClick'=>'addStudent($(this))', 'class'=>'add',/* 'submit'=>'', 'params'=>array('Student[command]'=>'add', 'noValidate'=>true)/**/));
                		echo"</span>";
                	}	
                ?>
            </td>
        </tr>
    </thead>
    <tbody>
    <?php 
   
    	foreach($contractitemmanager->items as $id=>$contractitem): ?> 
        <?php 
        //print_r($contractitem);
        	$this->renderPartial('/contractitem/_formContractitem', array('id'=>$id, 'model'=>$contractitem, 
        		'modelname'=>$modelname,'form'=>$form,'Mainaction'=>$Mainaction,'type'=>$type));?>
    <?php endforeach;?>
    </tbody>
</table>

</fieldset>
</div>
	<?php $this->renderPartial('signersjs', array('signers'=>$signersmanager, 'form'=>$form,'Mainaction'=>$Mainaction));?>

    <?php $this->renderPartial('/contractitem/contractitemjs', array('contractitems'=>$contractitemmanager, 
						    'modelname'=>$modelname,
						    'form'=>$form,'Mainaction'=>$Mainaction,'type'=>$type));?>

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
		if(isset($_GET['uniqueDocumentCode']) && $Mainaction=='extend'){
			echo $form->hiddenField($model,'documentUrl', array('documentUrl',$model->documentUrl));
		}
		echo $form->hiddenField($model,'noticeADA', array('noticeADA',$model->noticeADA));
		
		echo $form->hiddenField($model,'procurements', array('contract',$model->procurements));
	?>
	<input type='hidden' name='step' value='3'/>

	<input type="hidden" value="1" name="numberofsubs" id="numberofsubs"/>
	<input type="hidden" value="1" name="numberofSigners" id="numberofSigners"/>
	<input type="hidden" value="1" name="numberofcontractors" id="numberofcontractors"/>
	
	<div class="row buttons">
		<?php echo CHtml::submitButton(Yii::t('form','Δημιουργία')); ?>
	</div>
	
</div>
<?php }?>
<?php $this->endWidget(); ?>


</div><!-- form -->