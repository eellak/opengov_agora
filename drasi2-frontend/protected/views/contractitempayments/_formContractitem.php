<?php
//print_r($model);

?>
<tr id=<?php echo $id;?>>    
    <td>
	<div class="clonedInput" ><!--  id=<?php //echo $id;?>-->
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
			<div class="clear"></div>
						
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]Secafm'); ?>
				<?php 
						if($action=='createempty' || $action=='createpaymentbyproc' || $contractid=='' )
							echo $form->textField($model,"[$id]Secafm",array('size'=>12,'maxlength'=>12));
						else
							echo $form->dropDownList($model,"[$id]Secafm",SignersLookup::items('contractSigners',$contractid),
				array(
					'ajax' => array(
					// 'type'=>'GET', 
       			 	//'data'=>'js:this.value',
					'type'=>'POST', //request type
					'url'=>CController::createUrl('payments/dynamicsecname&number='.$id), //url to call.
					'update'=>'#'.$id.'secname','#'.$id.'seccountry', //selector to update				
					),
					)); 
					
///http://www.yiiframework.com/forum/index.php?/topic/18517-ajax-update-a-textfield-based-on-a-dropdownlist-value/page__p__91164__hl__ajax+update+textField#entry91164
//http://www.yiiframework.com/forum/index.php?/topic/12471-form-field-value-ajax/page__p__61048__hl__ajax++get+value#entry61048			
					?>	
				<?php echo $form->error($model,"Secafm"); ?>
			</div>
			<div id="<?php echo $id; ?>secname">
				
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]Secname'); ?>
				 <?php echo $form->textField($model,"[$id]Secname",array('size'=>120,'maxlength'=>120,'class'=>'Secname')); ?>
				<?php echo $form->error($model,"Secname"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]Seccountry'); ?>
				<?php echo $form->dropDownList($model,"[$id]Seccountry",Lookup::items('country',$model->Seccountry),array('class'=>'country1')); ?>
				
				<?php echo $form->error($model,"Seccountry"); ?>
			</div>
		</div>
			<div class="clear"></div>
			<div class="row">
				<?php 
					/*echo $form->labelEx($model,'[$id]invoice'); 
					echo $form->textField($model,"[$id]invoice",array('size'=>12,'title'=>Yii::t('form','InvoiceDetails'),
					'class'=>'country1')); 
					 echo $form->error($model,"invoice"); 
			*/
			 echo $form->labelEx($model,'[$id]invoice');
					 $this->widget('CMaskedTextField',
                   	 array(
						 'mask'=>'99-99-9999/*?**/*?************',
                   	     'model'=>$model,
                   	 	 'placeholder' => '_',
                         'attribute'=>"[$id]invoice",
                         'htmlOptions' => array('size' => 20,'title'=>Yii::t('form','responsibilityAssumptionCodeDesc')),
                   	 	 
                   	 ));
				
				//	 echo $form->textField($model,"[$id]responsibilityAssumptionCode",array('size'=>60,'maxlength'=>128));
					 echo $form->error($model,'invoice');
			?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]address'); ?>
				<?php
					if(isset($model->address) && $model->address!='')
						echo $form->textField($model,"[$id]address",array('size'=>12,'maxlength'=>12,'class'=>'address','value'=>$model->address)); 
					else if(isset($_POST['Contractitem'][$id]['address'])) 
						echo $form->textField($model,"[$id]address",array('size'=>12,'maxlength'=>12,'class'=>'address','value'=>$_POST['Contractitem'][$id]['address'])); 
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
					echo $form->textField($model,"[$id]addressNo",array('size'=>5,'maxlength'=>12,'value'=>$model->addressNo)); 
				else if(isset($_POST['Contractitem'][$id]['addressNo'])) 
					echo $form->textField($model,"[$id]addressNo",array('size'=>5,'maxlength'=>12,'value'=>$_POST['Contractitem'][$id]['addressNo'])); 
				else{
					$addNo= isset(Yii::app()->user->OrganisationAddressNo)?Yii::app()->user->OrganisationAddressNo:'';
					echo $form->textField($model,"[$id]addressNo",array('size'=>5,'maxlength'=>12,'value'=>$addNo)); 
					
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
		
			<div class="clear"></div>
		
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]city'); ?>
				<?php 
				if(isset($model->city))
					echo $form->textField($model,"[$id]city",array('size'=>12,'maxlength'=>100,'class'=>'city','value'=>$model->city)); 
				else
					echo $form->textField($model,"[$id]city",array('size'=>12,'maxlength'=>100,'class'=>'city')); ?>
				<?php echo $form->error($model,"city"); ?>
			</div>
			
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]countryOfDelivery'); ?>
				<?php 
				if(isset($model->countryOfDelivery))
						echo $form->dropDownList($model,"[$id]countryOfDelivery",Lookup::items('country',$model->countryOfDelivery),array('class'=>'country'));
					else if(isset($_POST['Contractitem'][$id]['countryOfDelivery'])) 
						echo $form->dropDownList($model,"[$id]countryOfDelivery",Lookup::items('country',$_POST['Contractitem'][$id]['countryOfDelivery']),array('class'=>'country')); 
					else	
				echo $form->dropDownList($model,"[$id]countryOfDelivery",Lookup::items('country'),array('class'=>'country')); ?>	

				<?php echo $form->error($model,"countryOfDelivery"); ?>
			</div>
			
						<div class="row">
				<?php echo $form->labelEx($model,'[$id]countryProduced'); ?>
				<?php 
				
					if(isset($model->countryProduced))
						echo $form->dropDownList($model,"[$id]countryProduced",Lookup::items('country',$model->countryProduced),array('class'=>'country1'));
					else if(isset($_POST['Contractitem'][$id]['countryProduced'])) 
						echo $form->dropDownList($model,"[$id]countryProduced",Lookup::items('country',$_POST['Contractitem'][$id]['countryProduced']),array('class'=>'country')); 
					else
						echo $form->dropDownList($model,"[$id]countryProduced",Lookup::items('country'),array('class'=>'country1')); 
				?>	

				<?php echo $form->error($model,"countryProduced"); ?>
			</div>
			<div class="row">
			
			<?php 
			
			 echo $form->labelEx($model,'responsibilityAssumptionCode');
					 $this->widget('CMaskedTextField',
                   	 array(
                         'mask'=>'9999/*?************',
                         'model'=>$model,
                   	 	 'placeholder' => '_',
                         'attribute'=>"[$id]responsibilityAssumptionCode",
                         'htmlOptions' => array('size' => 20,'title'=>Yii::t('form','responsibilityAssumptionCodeDesc')),
                   	 	 
                   	 ));
				
				//	 echo $form->textField($model,"[$id]responsibilityAssumptionCode",array('size'=>60,'maxlength'=>128));
					 echo $form->error($model,'responsibilityAssumptionCode');
			?>
			</div>
			 <div id="<?php echo $id; ?>seccountry"></div>
			 
			 
     		<div class="clear"></div>
			
			<div class="row-line">
				<?php echo $form->labelEx($model,'description'); 
					if($action=='createempty')
						echo $form->textArea($model,"[$id]description",array('rows'=>6, 'cols'=>80));
					else{
						echo $form->textArea($model,"[$id]description",array('rows'=>6, 'cols'=>80,'disabled'=>true));
						echo $form->hiddenField($model,"[$id]description");
					}
				?>
				<?php echo $form->error($model,"description"); ?>
			</div>
			<div class="clear"></div>
			
			<div class="row-line">
			
				<?php echo $form->labelEx($model,'cpvsid'); ?>
				<?php 
				echo "<span class='note' style='font-size:11px;'>".Yii::t('form','cpvDescription')."</span><br/>";	
				if($action=='createempty')
				echo $form->textArea($model,"[$id]cpvsid",array('rows'=>2,'cols'=>80, 'width'=>'500px','class'=>'user_selector')); 
				else{
					echo $form->textArea($model,"[$id]cpvsid",array('rows'=>2,'cols'=>80, 'width'=>'500px','class'=>'user_selector','disabled'=>true)); 
					echo $form->hiddenField($model,"[$id]cpvsid");
				}
				?>
				<?php echo "<span class='note' style='font-size:10px;float:right;margin-right:140px;'>".Yii::t('form','cpvsdetails')."</span><br/>"; ?>
				<div class="clear"></div>
				<?php echo $form->error($model,"cpvsid"); ?>
			</div>
			<div class="clear"></div>
			<div style="" class="clear"></div>
			
			<?php if($model->KAE || $action=='createempty') echo $form->labelEx($model,'KAE'); ?>
			
			<?php if($action=='createempty')
				echo $form->textField($model,"[$id]KAE",array('size'=>80,'maxlength'=>1024, 'width'=>'500px')); 
				else
			if($model->KAE){?>
			<div class="row-line">
				<?php echo $form->textField($model,"[$id]KAE",array('size'=>80,'maxlength'=>1024, 'width'=>'500px','disabled'=>true)); 
					echo $form->hiddenField($model,"[$id]KAE"); ?>
			</div>
			<div style="" class="clear"></div>
			
			<?php }?>
				<?php echo $form->error($model,'KAE'); ?>
			
			<?php 
					echo $form->hiddenField($model,"[$id]documentUrl",array('value'=>$model->documentUrl));
					echo $form->hiddenField($model,"[$id]notice");
			?>
			</div>
		</td>
 
    <td>
      <?php 
      
        /*	echo CHtml::link(
                Yii::t('form','copy'), 
        		'', 
                array(
                    'class'=>'copy',
                	'onClick'=>'copyItem($(this),'."'$id'".')',
           
               ));
              echo"</span>";*/
      
           ?>
    
        <?php 
      //  if($action=='createempty') {
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
       // }
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
	   	minLength: 4,
	               
    });
}
      $(document).ready(function() {
        setAutocomplete();
    //    $('#addParticipant').live('click', addParticipantFields);

    });
</script>
			
