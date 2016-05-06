<?php
$this->pageTitle=Yii::app()->name . ' - Error';
$this->breadcrumbs=array(
	Yii::t('form','error'),
);
?>
<div class="item">
	<a name="top"></a>
	<div class="contract">


<div class="title" style="color:red">
 <?php echo Yii::t('form','error'); echo ":: ". $code; ?>
</div>
<img src="images/error.png" align="left" style="margin-right:10px;">
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
		if(isset($type)){
			if ($type=='paymentsByContract')
					echo CHtml::link(CHtml::encode(Yii::t('contract','Back to List Contracts')),array('contract/index'));
		}else 
		echo CHtml::link(CHtml::encode(Yii::t('procurement','Back to List procurement')),array('procurement/indexproc'));
		?>
	</div>
	</center>