<div class="item">
	<a name="top"></a>
	<div class="contract">


<div class="title">
<?php
	if($data['action']=='cancel')
		echo Yii::t('form','successCancelProc');
	else if($data['action']=='delete')
		echo Yii::t('form','successDeleteProc');
	else if($data['action']=='update')
		echo Yii::t('form','successUpdateProc');
	else if($data['action']=='approverequest')
		echo Yii::t('form','successApproveRequest');
	else
		echo Yii::t('form','successCreateProc');

	?>
	</div>
	<img src="images/successB.png" align="left" style="margin-right:10px;">
	<?php 
	echo "<br/>";
	echo Yii::t('procurement','procDetails');
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
				echo "<span class='statustitle'>".Yii::t('procurement','Deldescription')."</span>";
				echo "<span class='data'>".$data['deldescription'] ."</span>";
				echo"</div>";
			}
		echo"</div>";
		echo"<br/><br/><br/><br/><br/>";
	
?>
	
</div>
</div>

<center>
	<div class="listButton">
		<?php 
		echo CHtml::link(CHtml::encode(Yii::t('procurement','Back to List procurement')),array('procurement/indexproc'));
		?>
	</div>
	</center>
	