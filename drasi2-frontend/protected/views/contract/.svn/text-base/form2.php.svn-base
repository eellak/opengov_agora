<?php
?>




<div style="margin-bottom: 10px;">
		<div class="clonedInput">
			<div id="test1"></div>
			<div class="clear"></div>
			<div class="row">
				<?php echo $form->labelEx($model,'quantity'); ?>
				<?php echo $form->textField($model,"quantity[$id]",array('size'=>24,'maxlength'=>24)); ?>
				<?php echo $form->error($model,"quantity[$id]"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'cost'); ?>
				<?php echo $form->textField($model,"cost[$id]",array('size'=>24,'maxlength'=>24)); ?>
				<?php echo $form->error($model,"cost[$id]"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'vatid'); ?>
				<?php //echo $form->dropDownList($model,'vatid',Lookup::items('FPA'),array('name'=>'vatid[]')); ?>	
				<?php echo $form->dropDownList($model,"vatid[$id]",Lookup::items('FPA')); ?>	

				<?php echo $form->error($model,"vatid[$id]"); ?>
			</div>
			<div class="row">
				<?php echo $form->labelEx($model,'currencyid'); ?>
				<?php echo $form->dropDownList($model,"currencyid[$id]",Lookup::items('Currency')); ?>	
				<?php //echo $form->dropDownList($model,'currencyid',Lookup::items('Currency'),array('name'=>'currencyid[]')); ?>	
			
				<?php echo $form->error($model,"currencyid[$id]"); ?>
			</div>
			
			
			<div class="clear"></div>
			<div class="row">
				<?php echo $form->labelEx($model,'description'); ?>
				<?php //echo $form->textArea($model,'description',array('name'=>'description[]','rows'=>6, 'cols'=>80));?>	
				<?php echo $form->textArea($model,"description[$id]",array('rows'=>6, 'cols'=>80));?>	

				<?php echo $form->error($model,"description[$id]"); ?>
			</div>
			
			<div class="row">
				<?php echo $form->labelEx($model,'cpvsid'); ?>
				<?php //echo $form->textField($model,'cpvsid',array('name'=>'cpvsid[]','size'=>80,'maxlength'=>1024, 'width'=>'500px')); ?>
				<?php echo $form->textField($model,"cpvsid[$id]",array('size'=>80,'maxlength'=>1024, 'width'=>'500px')); ?>
				<?php echo $form->error($model,"cpvsid[$id]"); ?>

			</div>
			<div style="" class="clear"></div>
				<input type="button" id="btnDel" class="btnDel" value="remove previous form" />
			
		</div>
	</div>
	<?php echo CHtml::link(
                'delete', 
                '', 
                array(
                    'class'=>'delete',
                    'onClick'=>'btnDel($(this))', /*
                    'submit'=>'', 
                    'params'=>array(
                        'Student[command]'=>'delete', 
                        'Student[id]'=>$id, 
                        'noValidate'=>true)/**/
                    ));?>
	
	<?php /*echo CHtml::link(
        'delete', 
        '#', 
        array(
            'submit'=>'', 
            'params'=>array(
                'Contraitem[command]'=>'delete', 
                'Contraitem[id]'=>$id, 
                'noValidate'=>true)
            ));
            */
            ?>
    </td>
</tr>