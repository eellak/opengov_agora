<?php
$this->pageTitle=Yii::app()->name . ' - Error';
$this->breadcrumbs=array(
	Yii::t('form','error'),
);
?>
<div class="item">
	<a name="top"></a>
	<div class="contract">


<div class="title" style="color:orange">
 <?php echo Yii::t('form','notice'); echo ":: ". $code; ?>
</div>
<img src="images/notice.png" align="left" style="margin-right:10px;">
	<?php 
	echo "<br/>";
	echo "<br/>";
	
	?>
<div class="error">
<?php echo CHtml::encode($message); ?>
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
		echo CHtml::link(CHtml::encode(Yii::t('contract','Back to List Contracts')),array('contract/index'));
		?>
	</div>
	</center>