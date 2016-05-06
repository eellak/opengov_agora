<?php
Yii::import('ext.qtip.QTip');
Yii::import('ext.jqAutocomplete');

Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/iphone-style-checkboxes.js');
Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/iphone-style-checkboxes.css');
Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/addDeleteSigners.js');
Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/addDeleteContractors.js');
Yii::app()->clientScript->registerScriptFile(Yii::app()->baseUrl.'/themes/classic/js/adddeleteProcurement.js');

//jquery iphone checkbox -->
//http://awardwinningfjords.com/2009/06/16/iphone-style-checkboxes.html -->
?>
<script type="text/javascript" charset="utf-8">
$(window).load(function() {
 $('#Contract_diavgeiaPublished:checkbox').iphoneStyle({
	  checkedLabel: 'NAI',
	  uncheckedLabel: 'OXI'
	});

});
</script>

<div class="form">

<?php 

$form=$this->beginWidget('CActiveForm', array(
	 'htmlOptions'=>array('enctype'=>'multipart/form-data'),
	 'id'=>'contract-form',
     'stateful'=>true,
	// 'enableAjaxValidation'=>true,
     'clientOptions' => array('validateOnChange'=>true, 'validateOnType'=>true,'validateOnSubmit'=>true),
     'focus'=>array($model,'title'),

)); 


if(isset($_POST['Contract'])){ //&& $_POST['Contract']['yt0']=='Δημιουργία'){
	$model->title = $_POST['Contract']['title'];
	$model->protocolNumberCode = $_POST['Contract']['protocolNumberCode'];
	
	$model->diavgeiaPublished = $_POST['Contract']['diavgeiaPublished'];
	$model->adaCode = $_POST['Contract']['adaCode'];
	$model->contractingAuthority = $_POST['Contract']['contractingAuthority'];
	$model->awardProcedure = $_POST['Contract']['awardProcedure'];
	$model->OrganizationIdRefUnits = $_POST['Contract']['OrganizationIdRefUnits'];
	for($i=0;$i<count($_POST['Contract']['signers']);$i++)
		$model->signers[$i]=$_POST['Contract']['signers'][$i];
	for($i=0;$i<count($_POST['Contract']['Secname']);$i++){
		$model->Secname[$i]=$_POST['Contract']['Secname'][$i];
		$model->Secafm[$i]=$_POST['Contract']['Secafm'][$i];
		$model->Seccountry[$i]=$_POST['Contract']['Seccountry'][$i];
	
	}
}

?>
 	<p class="note">Fields with <span class="required">*</span> are required.</p>

	<?php 

if(isset($_POST['Contractitem'])){
foreach ($contractitemmanager as $PMap){
    echo '<p>'.$form->errorSummary(array_merge(array($model),$PMap)).'</p>';
} 
}else 
	echo '<p>'.$form->errorSummary(array($model)).'</p>'; 

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
	<legend><?php echo Yii::t('form','Basic');?></legend>
	<div class="row">
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
                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('contract','dateSignedDesc'))
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
                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('contract','sinceDesc'))
                   )
             );
            ?> 
        <?php echo $form->error($model,'since'); ?>
		   
	</div>
	<div class="row">
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
                    'htmlOptions'=>array('size'=>10,'title'=>Yii::t('contract','untilDesc'))
                   )
             );
            ?> 
        <?php echo $form->error($model,'until'); ?>
		   
	</div>
	
	<div class="row" id="marginleft">
		<?php echo $form->labelEx($model,'protocolNumberCode'); ?>
		<?php echo $form->textField($model,'protocolNumberCode',array('size'=>60,'maxlength'=>128)); ?>
		<?php echo $form->error($model,'protocolNumberCode'); ?>
	</div>
	<div class="clear"></div>
	
	<div class="row">
		<?php echo $form->labelEx($model,'diavgeiaPublished'); ?>
		<?php echo $form->checkBox($model,'diavgeiaPublished',array('class'=>'checkbox','value'=>'on','selected'=>true));?>
		<?php echo $form->error($model,'diavgeiaPublished'); ?>
	</div>
	<div class="row">
		<?php //echo $form->labelEx($model,'adaCode'); ?>
		<?php //echo $form->textField($model,'adaCode',array('size'=>60,'maxlength'=>128)); ?>
		<?php //echo $form->error($model,'adaCode'); ?>
	</div>
	<div class="clear"></div>
	
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
	<legend><?php echo Yii::t('contract','contractParties');?></legend>
	<div class="row">
		<?php echo $form->labelEx($model,'OrganizationIdRefUnits'); ?>
		<?php echo $form->dropDownList($model,'OrganizationIdRefUnits',Units::items('Units')); ?>
		<?php echo $form->error($model,'OrganizationIdRefUnits'); ?>
	</div>
	<div class="clear"></div>
	<div style="height: 2px;" class="clear"></div>

	<div class="clonedInputSigners">
			<div class="row">
				<?php echo $form->labelEx($model,'signers'); ?>
				<?php echo $form->dropDownList($model,'signers[]',Units::items('Signers')); ?>	
				<?php echo $form->error($model,'signers'); ?>
			</div>
		<div class="row">
			<input type="button" class="btnDelSigners" id="btnDelSigners" value="Διαγραφή ">
		</div>
		<div style="height: 2px;" class="clear"></div>
		
	</div>
		<div class="clear"></div>
	
	<input type="button" class="btnAddSigners" id="btnAddSigners" value="Προσθήκη νέου αντικειμένου ">
	
	<div style="height: 10px;" class="clear"></div>
	<div class="clonedInputContractors">
		<div class="row">
			<?php echo $form->labelEx($model,'Secname'); ?>
			<?php echo $form->textField($model,'Secname[]',array('size'=>60,'maxlength'=>128)); ?>
			<?php echo $form->error($model,'Secname'); ?>
		</div>
		<div class="row">
			<?php echo $form->labelEx($model,'Secafm'); ?>
			<?php echo $form->textField($model,'Secafm[]'); ?>
			<?php echo $form->error($model,'Secafm'); ?>
		</div>
			
		<div class="row">
			<?php echo $form->labelEx($model,'Seccountry'); ?>
			<?php echo $form->dropDownList($model,'Seccountry[]',Lookup::items('country')); ?>	
			<?php echo $form->error($model,'Seccountry'); ?>
		</div>
		<div class="row">
			<input type="button" class="btnDelContractors" id="btnDelContractors" value="Διαγραφή ">
		</div>
		<div style="height: 10px;" class="clear"></div>
	</div>
		<input type="button" class="btnAddContractors" id="btnAddContractors" value="Προσθήκη νέου αντικειμένου ">
	</fieldset>
	
	
	<div class="form-group">
	<fieldset>
	
	<legend><?php echo Yii::t('form','Related File');?></legend>
	
	
			<?php echo $form->labelEx($model,'document'); ?>
			<?php echo $form->fileField($model,'document'); ?>
			<?php echo $form->error($model,'document'); ?>
	
	</fieldset>
	</div>
	<?php //echo CHtml::link('add', '#', array('submit'=>'', 'params'=>array('Contractitem[command]'=>'add', 'noValidate'=>true)));?>
<!--  <input type="button" id="btnAdd" value="add another form" />
-->
	<?php //foreach($contractitemmanager->items as $id=>$contractitem):?>
	<?php //$this->renderPartial('_formContractitem', array('id'=>$id, 'model'=>$contractitem, 'form'=>$form));?>

	<?php //endforeach;?>
<div class="form-group">
	<fieldset>
	
	<legend><?php echo Yii::t('form','Basic Data');?></legend>
	
<table id="students">
    <thead>
        <tr>
            <td>
                <?php echo CHtml::link('add', '', array('onClick'=>'addStudent($(this))', 'class'=>'add',/* 'submit'=>'', 'params'=>array('Student[command]'=>'add', 'noValidate'=>true)/**/));?>
            </td>
        </tr>
    </thead>
    <tbody>
    <?php foreach($contractitemmanager->items as $id=>$contractitem): ?> 
        <?php $this->renderPartial('_formContractitem', array('id'=>$id, 'model'=>$contractitem, 'form'=>$form));?>
    <?php endforeach;?>
    </tbody>
</table>

</fieldset>
</div>
<?php $this->renderPartial('contractitemjs', array('contractitems'=>$contractitemmanager, 'form'=>$form));?>
	<?php 
		if(isset($_GET['uniqueDocumentCode'])){
			echo $form->hiddenField($model,'documentUrl', array('documentUrl',$model->documentUrl));
		}
	?>

	<input type="hidden" value="1" name="numberofsubs" id="numberofsubs"/>
	<input type="hidden" value="1" name="numberofSigners" id="numberofSigners"/>
	<input type="hidden" value="1" name="numberofcontractors" id="numberofcontractors"/>
	
	<div class="row buttons">
		<?php echo CHtml::submitButton(Yii::t('form','Δημιουργία')); ?>
	</div>
	
</div>
<?php $this->endWidget(); ?>


</div><!-- form -->