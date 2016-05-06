<?php
Yii::import('ext.qtip.QTip');
//Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/iphone-style-checkboxes.js',CClientScript::POS_END);
//Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/iphone-style-checkboxes.css');
Yii::app()->clientScript->registerScriptFile(Yii::app()->request->baseUrl.'/themes/classic/js/jquery.autocomplete.js');
Yii::app()->clientScript->registerCssFile(Yii::app()->request->baseUrl.'/css/jquery.autocomplete.css');
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
if($step==1 && $action!='update'){
	
	//$form=$this->beginWidget('ext.EActiveForm', array(
	$form=$this->beginWidget('CActiveForm', array(
		 'htmlOptions'=>array('enctype'=>'multipart/form-data'),
		 'id'=>'payments-form',
	  	'stateful'=>true,
		 'enableAjaxValidation'=>true,
	     'clientOptions' => array('validateOnChange'=>true, 'validateOnType'=>true,),
	    'focus'=>array($model,'title'),
	
	)); 

	if($scenario=='createpaymentbyproc'){
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
				multiple:true,
			    multipleSeparator: ";",
			    matchContains: true,
			    minChars: 4,
			   	minLength: 4
			               
		    });
		}
		      $(document).ready(function() {
		        setAutocomplete();
		
		    });

	</script>
		<div class="form-group">
		<fieldset>
		<legend><?php echo Yii::t('payments','Create Payment Procurement');?></legend>
		<div class="row">
			<?php echo $form->labelEx($model,'procurements',array('title'=>Yii::t('contract','Contract'))); ?>
			<?php
				echo "<span class='note' style='font-size:11px;'>".Yii::t('contract','procurementAutoDescription')."</span><br/>";	
				echo $form->textField($model,'procurements',array('size'=>60,'maxlength'=>512,'width'=>'500px','class'=>'user_selector')); ?>
			<?php echo $form->error($model,'procurements'); ?>
		</div>
		</fieldset>
		</div>
		<input type='hidden' name='step' value='2'/>
		<div class="row buttons">
			<?php echo CHtml::submitButton($model->isNewRecord ? Yii::t('form','Δημιουργία') : Yii::t('form','Επόμενο Βήμα')); ?>
		</div>
	<?php 
	}else{
	
		?>


	<script type="text/javascript">
	
	function setAutocomplete()
	{
	    $(".user_selectorc").autocomplete("index.php?r=contractids/json", {
	        width: 320,
	        dataType: 'json',
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
		    matchContains: true,
		    mustMatch:true,
		    multiple:false,
		    minChars: 4,
		   	minLength: 4
		               
	    });
	}
	      $(document).ready(function() {
	        setAutocomplete();
	
	    });
		</script>
		<div class="form-group">
		<fieldset>
		<legend><?php echo Yii::t('form','ContractSelection');?></legend>
		<div class="row">
			<?php echo $form->labelEx($model,'contract',array('title'=>Yii::t('contract','Contract'))); ?>
			<?php
				echo "<span class='note' style='font-size:11px;'>".Yii::t('form','contractAutoDescription')."</span><br/>";	
				echo $form->textField($model,'contract',array('size'=>60,'maxlength'=>512,'width'=>'500px','class'=>'user_selectorc')); ?>
			<?php echo $form->error($model,'contract'); ?>
		</div>
		</fieldset>
		</div>
		<input type='hidden' name='step' value='2'/>
		<div class="row buttons">
			<?php echo CHtml::submitButton($model->isNewRecord ? Yii::t('form','Δημιουργία') : Yii::t('form','Επόμενο Βήμα')); ?>
		</div>
		<?php 	
		}
		 $this->endWidget(); 
		 

}else{
?>

<?php 

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
       
    </thead>
    <tbody id="Bodystudents">
    <?php 
    	foreach($contractitemmanager->items as $id=>$contractitem): ?> 
        <?php $this->renderPartial('/contractitempayments/_formContractitem', array('id'=>$id, 'model'=>$contractitem, 'form'=>$form,
        						'contractid'=>$model->contract,'action'=>$action,'procurements'=>$procurements));?>
    <?php endforeach;?>
    </tbody>
</table>

</fieldset>
</div>
 <?php		$this->renderPartial('/contractitempayments/contractitemjs', array('contractitems'=>$contractitemmanager, 
    							'form'=>$form, 'type'=>'payment',
								'modelname'=>'Payments',
        						'Mainaction'=>'none',
 								'contractid'=>$model->contract,'action'=>$action));?>
 
	
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
			echo"</div>\n";?>
	
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
		if($scenario=='createpaymentbyproc'){
			echo $form->hiddenField($model,'procurements', array('procurements',$model->procurements));
		}else
		echo $form->hiddenField($model,'contract', array('contract',$model->contract));
		echo $form->hiddenField($model,'documentURlFromDiavgeia');
		
	?>
	<input type='hidden' name='step' value='3'/>
	
	<div class="row buttons">
		<?php echo CHtml::submitButton($model->isNewRecord ? Yii::t('form','Δημιουργία') : Yii::t('form','Save')); ?>
	</div>
<?php $this->endWidget(); ?>
<?php 
}
?>

</div><!-- form -->

