<?php
?>
<tr>    
    <td>
	<div class="clonedInput">
     <div class="row">
				<?php echo $form->labelEx($model,"[$id]quantity"); ?>
				<?php echo $form->textField($model,"[$id]quantity",array('size'=>12,'maxlength'=>12,'class'=>'quantity')); ?>
				<?php echo $form->error($model,"quantity"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,"[$id]units_of_measure"); ?>
				<?php echo $form->dropDownList($model,"[$id]units_of_measure",Lookup::items('units_of_measure'),array('class'=>'currency')); ?>
				<?php echo $form->error($model,"units_of_measure"); ?>
			</div>
	
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]cost'); ?>
				<?php echo $form->textField($model,"[$id]cost",array('size'=>12,'maxlength'=>12)); ?>
				<?php echo"<i>(π.χ.10000.00)</i>";?>
				<?php echo $form->error($model,"cost"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]vatid'); ?>
				<?php echo $form->dropDownList($model,"[$id]vatid",Lookup::items('FPA')); ?>	

				<?php echo $form->error($model,"vatid"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]currencyid'); ?>
				<?php echo $form->dropDownList($model,"[$id]currencyid",Lookup::items('Currency'),array('class'=>'currency')); ?>	
				<?php echo $form->error($model,"currencyid"); ?>
			</div>
		
			
			<?php 
				if($modelname!='procurement' && $modelname!='rfp'){
					
			?>
			<div class="clear"></div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]address'); ?>
				<?php
					if(isset($model->address) && $model->address!='')
						echo $form->textField($model,"[$id]address",array('size'=>120,'maxlength'=>120,'class'=>'address','value'=>$model->address)); 
					else if(isset($_POST['Contractitem'][$id]['address']))
						echo $form->textField($model,"[$id]address",array('size'=>120,'maxlength'=>120,'class'=>'address','value'=>$_POST['Contractitem'][$id]['address'])); 
					else{
						$add = isset(Yii::app()->user->OrganisationAddress)?Yii::app()->user->OrganisationAddress:'';
						echo $form->textField($model,"[$id]address",array('size'=>120,'maxlength'=>120,'class'=>'address','value'=>$add)); 
					}
					?>
				<?php echo $form->error($model,"address"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]addressNo'); ?>
				<?php 
				if(isset($model->addressNo) && $model->addressNo!='')
					echo $form->textField($model,"[$id]addressNo",array('size'=>5,'maxlength'=>12,'value'=>$model->addressNo),array('class'=>'addressNo')); 
				
				else if(isset($_POST['Contractitem'][$id]['addressNo'])) 
					echo $form->textField($model,"[$id]addressNo",array('size'=>5,'maxlength'=>12,'value'=>$_POST['Contractitem'][$id]['addressNo']),array('class'=>'addressNo')); 
				else{
					$addNo= isset(Yii::app()->user->OrganisationAddressNo)?Yii::app()->user->OrganisationAddressNo:'';
					echo $form->textField($model,"[$id]addressNo",array('size'=>5,'maxlength'=>12,'value'=>$addNo,'class'=>'addressNo')); 
					
				} 
						
				?>
				<?php echo $form->error($model,"addressNo"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]addressPostal'); ?>
				<?php 
				if(isset($model->addressPostal) && $model->addressPostal!='') 
					echo $form->textField($model,"[$id]addressPostal",array('size'=>7,'maxlength'=>12,'class'=>'postal','value'=>$model->addressPostal));
				else if(isset($_POST['Contractitem'][$id]['addressPostal'])) 
					echo $form->textField($model,"[$id]addressPostal",array('size'=>7,'maxlength'=>12,'class'=>'postal','value'=>$_POST['Contractitem'][$id]['addressPostal']));
				else {
					$addressPostal=(isset(Yii::app()->user->OrganisationAddressPostal)?Yii::app()->user->OrganisationAddressPostal:'');
					
					echo $form->textField($model,"[$id]addressPostal",array('size'=>7,'maxlength'=>12,'class'=>'postal','value'=>$addressPostal));
					
				}
					?>
				<?php echo $form->error($model,"addressPostal"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]city'); ?>
				<?php 
				if(isset($model->city))
					echo $form->textField($model,"[$id]city",array('size'=>12,'maxlength'=>100,'class'=>'city','value'=>$model->city));
				else if(isset($_POST['Contractitem'][$id]['city'])) {
					echo $form->textField($model,"[$id]city",array('size'=>12,'maxlength'=>100,'class'=>'city','value'=>$_POST['Contractitem'][$id]['city']));
				}else
					echo $form->textField($model,"[$id]city",array('size'=>12,'maxlength'=>100,'class'=>'city',));
				
					?>
				<?php echo $form->error($model,"city"); ?>
			</div>
		
     		<div class="clear"></div>
			<?php 
				}
			?>
			<?php 
				if($modelname!='procurement' && $modelname!='rfp'){
			?>
			<div class="clear"></div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]countryProduced'); ?>
				<?php 
					if(isset($model->countryProduced))
						echo $form->dropDownList($model,"[$id]countryProduced",Lookup::items('country',$model->countryProduced),array('class'=>'country'));
					else if(isset($_POST['Contractitem'][$id]['countryProduced']) && $_POST['Contractitem'][$id]['countryProduced']!='') 
						echo $form->dropDownList($model,"[$id]countryProduced",Lookup::items('country',$_POST['Contractitem'][$id]['countryProduced']),array('class'=>'country'));
					else
						echo $form->dropDownList($model,"[$id]countryProduced",Lookup::items('country'),array('class'=>'country'));
				?>	

				<?php echo $form->error($model,"countryProduced"); ?>
			</div>
				<div class="row">
				<?php echo $form->labelEx($model,'[$id]countryOfDelivery'); ?>
				<?php 
				if(isset($model->countryOfDelivery))
						echo $form->dropDownList($model,"[$id]countryOfDelivery",Lookup::items('country',$model->countryOfDelivery),array('class'=>'country'));
					else if(isset($_POST['Contractitem'][$id]['countryOfDelivery'])) 
						echo $form->dropDownList($model,"[$id]countryOfDelivery",Lookup::items('country',$_POST['Contractitem'][$id]['countryOfDelivery']),array('class'=>'country'));
					else	
				echo $form->dropDownList($model,"[$id]countryOfDelivery",Lookup::items('country'),array('class'=>'country'));?>	

				<?php echo $form->error($model,"countryOfDelivery"); ?>
			</div>
			<?php 
				}
			?>
			
			
			<div class="clear"></div>
			<div class="row-line">
				<?php echo $form->labelEx($model,'description'); ?>
				<?php 
				if(isset($Mainaction) &&  ($Mainaction=='extend' /*|| $Mainaction=='approverequest'*/)){
					echo $form->textArea($model,"[$id]description",array('rows'=>6, 'cols'=>80,'disabled'=>true));
					echo $form->hiddenField($model,"[$id]description");
				}else
					echo $form->textArea($model,"[$id]description",array('rows'=>6, 'cols'=>80));
				?>	
				<?php echo $form->error($model,"description"); ?>
			</div>
			<div class="clear"></div>
			
			<div class="row-line">
				<?php echo $form->labelEx($model,'cpvsid'); ?>
				<?php echo "<span class='note' style='font-size:11px;'>".Yii::t('form','cpvDescription')."</span><br/>";?>
				
				
				<?php 
				if(isset($Mainaction) &&   ($Mainaction=='extend' /*|| $Mainaction=='approverequest'*/)){
					echo $form->textArea($model,"[$id]cpvsid",array('rows'=>2,'cols'=>80, 'width'=>'500px','class'=>'user_selector','disabled'=>true)); 
					echo $form->hiddenField($model,"[$id]cpvsid");
					
				}else
					echo $form->textArea($model,"[$id]cpvsid",array('rows'=>2,'cols'=>80, 'width'=>'500px','class'=>'user_selector')); ?>
				<?php echo "<span class='note' style='font-size:10px;float:right;margin-right:140px;'>".Yii::t('form','cpvsdetails')."</span><br/>"; ?>
				<div class="clear"></div>
				<?php echo $form->error($model,"cpvsid"); ?>

			</div>
						<div class="clear"></div>
			<div style="" class="clear"></div>

			<div class="row-line">
			<?php if(($type=='procurement' && $Mainaction!='create' ) || $type=='contract' || $type=='rfp'){ //KAE should not be present on protogenes aithma 
					 echo $form->labelEx($model,'KAE'); 
				
				if(isset($Mainaction) &&  ($Mainaction=='extend' /*| $Mainaction=='approverequest'*/)){
					echo $form->textField($model,"[$id]KAE",array('size'=>80,'maxlength'=>1024, 'width'=>'500px','disabled'=>true)); 
					echo $form->hiddenField($model,"[$id]KAE");
					
				}else
					echo $form->textField($model,"[$id]KAE",array('size'=>80,'maxlength'=>1024, 'width'=>'500px')); 
				?>
		
				<?php echo $form->error($model,'KAE'); ?>
			</div>
			<div style="" class="clear"></div>
							
			</div>
			<?php } ?>
			<?php 					
			echo $form->hiddenField($model,"[$id]documentUrl");
			echo $form->hiddenField($model,"[$id]notice");
			
			?>
</td>
 
    <td>
        <?php
        //if($modelname!='rfp') 
        if((isset($Mainaction) && $Mainaction!='extend'  && $Mainaction!='approverequest') || ($type=='procurement'  && $Mainaction!='approverequest')) {
        	echo "<span style=\"cursor:pointer\">";
        	echo CHtml::link(
                Yii::t('form','delete'), 
        		'', 
                array(
                    'class'=>'delete',
                    'onClick'=>'deleteStudent($(this))',/*
                    'submit'=>'', 
                    'params'=>array(
                        'Student[command]'=>'delete', 
                        'Student[id]'=>$id, 
                        'noValidate'=>true)/**/
               ));
              echo"</span>";
        }
           ?>
    </td>
</tr>

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
		multiple: true,
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
