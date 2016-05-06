<?php
?>
<tr>    
    <td>
	<div class="clonedInput">
	<?php 
			echo"<div class='row-grey'>";
		//	echo "<span class='title'>".Yii::t('contract','documentUrl')."</span>";
		//	echo "<span class='data'>$otherData->documentUrl</span>";
			echo $form->hiddenField($otherData,"[$id]documentUrl");
			
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('item','quantity')."</span>";
			echo "<span class='data'>$otherData->quantity</span>";
			echo $form->hiddenField($otherData,"[$id]quantity");
			
			echo "<span class='title'>".Yii::t('item','cost')."</span>";
			echo "<span class='data'>$otherData->cost</span>";
			echo $form->hiddenField($otherData,"[$id]cost");
			
			echo "<span class='title'>".Yii::t('item','vat')."</span>";
			echo "<span class='data'>".Lookup::item('FPA',$otherData->vatid)."</span>";
			echo $form->hiddenField($otherData,"[$id]vatid");
			
			echo "<span class='title'>".Yii::t('item','currency')."</span>";
			echo "<span class='data'>".Lookup::item('Currency',$otherData->currencyid)."</span>";
			echo $form->hiddenField($otherData,"[$id]currencyid");
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('item','description')."</span>";
			echo "<span class='data'>".$otherData->description ."</span>";
			echo $form->hiddenField($otherData,"[$id]description");
			
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('item','CPVS')."</span>";
			echo "<span class='data'>".$otherData->cpvsid ."</span>";
			echo $form->hiddenField($otherData,"[$id]cpvsid");
			
			echo"</div>";
			if($otherData->KAE!=''){
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('item','KAE')."</span>";
			echo "<span class='data'>".$otherData->KAE ."</span>";
			echo $form->hiddenField($otherData,"[$id]KAE");
			
			echo"</div>";
			
			}
			
	
	?>
</div>	
			
</td>
 
    
</tr>
