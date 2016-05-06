<?php
$this->pageTitle=Yii::app()->name . ' - '.Yii::t('form','error');
$this->breadcrumbs=array(
	Yii::t('form','error'),
);
?>
<div class="item">
	<a name="top"></a>
	<div class="contract">


<div class="title" style="color:red">
 <?php echo Yii::t('form','error'); echo ":: ". $data['code']; ?>
</div>
<img src="images/error.png" align="left" style="margin-right:10px;">
	<?php 
	echo "<br/>";
	echo "<br/>";
	
	?>
<div class="error">
<?php echo CHtml::encode($data['message']); ?>
</div>
<?php 
	echo "<br/>";
	echo "<br/>";
	echo "<br/>";
	
	?>
</div><!-- contract -->
</div><!-- item -->

<center>
	<div class="listButton">
		<?php 
		echo CHtml::link(CHtml::encode(Yii::t('rfp','Back to List')),array('rfp/index'));
		?>
	</div>
	</center>