<?php

?>
<tr>    
    <td>
	<div class="clonedInput">
     <div class="row">
				<?php echo $form->labelEx($model,"[$id]quantity"); ?>
				<?php echo $form->textField($model,"[$id]quantity",array('size'=>12,'maxlength'=>12)); ?>
				<?php echo $form->error($model,"quantity"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]cost'); ?>
				<?php echo $form->textField($model,"[$id]cost",array('size'=>12,'maxlength'=>12)); ?>
				<?php echo $form->error($model,"cost"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]vatid'); ?>
				<?php echo $form->dropDownList($model,"[$id]vatid",Lookup::items('FPA')); ?>	

				<?php echo $form->error($model,"vatid"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'[$id]currencyid'); ?>
				<?php echo $form->dropDownList($model,"[$id]currencyid",Lookup::items('Currency')); ?>	
				<?php echo $form->error($model,"currencyid"); ?>
			</div>
			
			
			<div class="clear"></div>
			<div class="row-line">
				<?php echo $form->labelEx($model,'description'); ?>
				<?php 
				if($Mainaction=='extend'){
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
				if($Mainaction=='extend'){
					echo $form->textArea($model,"[$id]cpvsid",array('rows'=>2,'cols'=>80, 'width'=>'500px','class'=>'user_selector','disabled'=>true)); 
					echo $form->hiddenField($model,"[$id]cpvsid");
					
				}else
					echo $form->textArea($model,"[$id]cpvsid",array('rows'=>2,'cols'=>80, 'width'=>'500px','class'=>'user_selector')); ?>
				<?php echo $form->error($model,"cpvsid"); ?>

			</div>
						<div class="clear"></div>
			<div style="" class="clear"></div>
			<div class="row-line">
				<?php echo $form->labelEx($model,'KAE'); ?>
				<?php
				if($Mainaction=='extend'){
					echo $form->textField($model,"[$id]KAE",array('size'=>80,'maxlength'=>1024, 'width'=>'500px','disabled'=>true)); 
					echo $form->hiddenField($model,"[$id]KAE");
					
				}else
					echo $form->textField($model,"[$id]KAE",array('size'=>80,'maxlength'=>1024, 'width'=>'500px')); 
				?>
				<?php echo $form->error($model,'KAE'); ?>
			</div>
			<div style="" class="clear"></div>
			
			</div>
			
</td>
 
    <td>
        <?php 
        if($Mainaction!='extend') {
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
	    minChars: 5,
	   	minLength: 5,
	               
    });
}
      $(document).ready(function() {
        setAutocomplete();
    //    $('#addParticipant').live('click', addParticipantFields);

    });
</script>
