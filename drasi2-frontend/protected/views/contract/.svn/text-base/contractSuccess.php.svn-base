
<div class="item">
	<a name="top"></a>
	<div class="contract">


<div class="title">
<?php
	if($data['action']=='cancel')
		echo Yii::t('form','successCancelContract');
	else if($data['action']=='delete')
		echo Yii::t('form','successDeleteContract');
	else if($data['action']=='update')
		echo Yii::t('contract','successUpdateContract');
	else if($data['action']=='extend')
		echo Yii::t('contract','successExtendContract');
	else if($data['action']=='changes')
		echo Yii::t('contract','successChangesContract');
	else
	echo Yii::t('form','successCreateContract');

	?>
	</div>
	<img src="images/successB.png" align="left" style="margin-right:10px;">
	<?php 
	echo "<br/>";
	if($data['action']=='cancel')
		echo Yii::t('form','contractCancelDetails');
	else if($data['action']=='update')
		echo Yii::t('contract','contractUpdateDetails');
	else if($data['action']=='extend')
		echo Yii::t('contract','contractExtendDetails');
	else if($data['action']=='delete')
		echo Yii::t('contract','contractDeleteDetails');
	else if($data['action']=='changes')
		echo Yii::t('contract','contractChangesDetails');
	else
		echo Yii::t('contract','contractDetails');
	
	echo "<br/>";
	?>
	
		<!-- Basic Data bar -->
	<?php 
		echo"<div class=\"row\">";
		if(isset($data['title']) && $data['title']!='') {
				
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('contract','title')."</span>";
			echo "<span class='data'>".$data['title'] ."</span>";
			echo"</div>";
		}
		if(isset($data['protocolNumberCode']) && $data['protocolNumberCode']!=''){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('contract','protocolNumberCode')."</span>";
			echo "<span class='data'>".$data['protocolNumberCode'] ."</span>";
			echo"</div>";
		}
		if(isset($data['documentUrl']) && $data['documentUrl']!=''){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('contract','documentUrl')."</span>";
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
		echo CHtml::link(CHtml::encode(Yii::t('contract','Back to List Contracts')),array('contract/index'));
		?>
	</div>
	</center>