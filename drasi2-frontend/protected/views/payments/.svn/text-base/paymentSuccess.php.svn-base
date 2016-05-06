<div class="item">
	<a name="top"></a>
	<div class="contract">


<div class="title">
<?php
	if($data['action']=='create')
		echo Yii::t('form','successCreatePayment');
	else if($data['action']=='update')
		echo Yii::t('form','successUpdatePayment');
	else if($data['action']=='delete')
		echo Yii::t('form','successdeletePayment');
	
	?>
	</div>
	<img src="images/successB.png" align="left" style="margin-right:10px;">
	<?php 
	echo "<br/>";
	if($data['action']=='create')
		echo Yii::t('payments','paymentDetails');
	else if($data['action']=='update')
		echo Yii::t('payments','paymentUpdateDetails');
	else if($data['action']=='delete')
		echo Yii::t('payments','paymentDeleteDetails');
		
	echo "<br/>";
	?>
	
		<!-- Basic Data bar -->
	<?php 
		echo"<div class=\"row\">";
		if(isset($data['title']) && $data['title']!=''){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('procurement','title')."</span>";
			echo "<span class='data'>".$data['title'] ."</span>";
			echo"</div>";
		}
		if(isset($data['protocolNumberCode']) && $data['protocolNumberCode']!=''){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('procurement','protocolNumberCode')."</span>";
			echo "<span class='data'>".$data['protocolNumberCode'] ."</span>";
			echo"</div>";
		}
		if(isset($data['documentUrl']) && $data['documentUrl']!=''){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('procurement','documentUrl')."</span>";
			echo "<span class='data'>".$data['documentUrl'] ."</span>";
			echo"</div>";
		}
		if(isset($data['action']) && $data['action']=='delete'){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('form','Deldescription')."</span>";
			echo "<span class='data'>".$data['deldescription'] ."</span>";
			echo"</div>";
		}
		echo"</div>";
	
	
?>
	
</div>
</div>

<center>
	<div class="listButton">
		<?php 
		echo CHtml::link(CHtml::encode(Yii::t('payments','Back to List Payments')),array('payments/indexpayments'));
		?>
	</div>
	</center>
	