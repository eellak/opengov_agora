
<div class="item">
	<a name="top"></a>
	<div class="contract">


<div class="title">
<?php
	if($data['action']=='cancel')
		echo Yii::t('form','successCancelRfp');
	else if($data['action']=='deleterfp')
		echo Yii::t('rfp','successDeleteRfp');
	else if($data['action']=='update')
		echo Yii::t('rfp','successUpdateRfp');
	else
	echo Yii::t('rfp','successCreateRfp');

	?>
	</div>
	<img src="images/successB.png" align="left" style="margin-right:10px;">
	<?php 
	echo "<br/>";
	if($data['action']=='update')
		echo Yii::t('rfp','RfpUpdateDetails');
	else
		echo Yii::t('rfp','RfpDetails');
	
	echo "<br/>";
	?>
	
		<!-- Basic Data bar -->
	<?php 
		echo"<div class=\"row\">";
		if(isset($data['title']) && $data['title']!=''){
				
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('contract','title')."</span>";
			echo "<span class='data'>".$data['title'] ."</span>";
			echo"</div>";
		}
		if(isset($data['protocolNumberCode']) && ($data['protocolNumberCode']!='')){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('rfp','protocolNumberCode')."</span>";
			echo "<span class='data'>".$data['protocolNumberCode'] ."</span>";
			echo"</div>";
		}
		if(isset($data['documentUrl']) && $data['documentUrl']!=''){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('contract','documentUrl')."</span>";
			echo "<span class='data'>".$data['documentUrl'] ."</span>";
			echo"</div>";
		}
		if(isset($data['action']) && $data['action']=='deleterfp'){
			echo"<div class='row-grey'>";
			echo "<span class='statustitle'>".Yii::t('form','Deldescription')."</span>";
			echo "<span class='data'>".$data['deldescription'] ."</span>";
			echo"</div>";
		}
		echo"<br/><br/>";
		echo"</div>";
	
	
?>
	
</div>
</div>

<center>
	<div class="listButton">
		<?php 
		echo CHtml::link(CHtml::encode(Yii::t('rfp','Back to List Rfp')),array('rfp/index'));
		?>
	</div>
	</center>