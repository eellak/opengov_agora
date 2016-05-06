<tr>    
    <td>
	<div class="clonedInput">
		<div class="row" style="float:left;">
			<?php echo $form->labelEx($model,"[$id]Seccountry"); ?>
			<?php 
			if($Mainaction =='extend'){
				echo $form->dropDownList($model,"[$id]Seccountry",Lookup::items('country'),array('disabled'=>true)); 
				echo $form->hiddenField($model,"[$id]Seccountry");
				
			}else 	
				echo $form->dropDownList($model,"[$id]Seccountry",Lookup::items('country'),array('class'=>'country')); 
			?>	
			<?php echo $form->error($model,'Seccountry'); ?>
		</div>	
		<div class="row">
			<?php echo $form->labelEx($model,"[$id]Secafm"); ?>
			
			<?php 
			if($Mainaction =='extend'){
				echo $form->textField($model,"[$id]Secafm",array('disabled'=>true,'class'=>'afm')); 
				echo $form->hiddenField($model,"[$id]Secafm");
				
			}else
				echo $form->textField($model,"[$id]Secafm",array('class'=>'afm')); 
			
			?>
			<?php echo $form->error($model,'Secafm'); ?>
		</div>
			

		<div class="row">
			<?php echo $form->labelEx($model,"[$id]Secname"); ?>
			
			<?php 
			if($Mainaction =='extend'){
				echo $form->textField($model,"[$id]Secname",array('size'=>30,'maxlength'=>128,'disabled'=>true));
				echo $form->hiddenField($model,"[$id]Secname");
			}else
				echo $form->textField($model,"[$id]Secname",array('size'=>30,'maxlength'=>128)); ?>
			<?php echo $form->error($model,'Secname'); ?>
		</div>
     		<div class="clear"></div>
			
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
	                    'onClick'=>'deleteSigners($(this))',/*
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
