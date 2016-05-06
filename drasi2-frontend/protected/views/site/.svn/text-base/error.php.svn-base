<?php
$this->pageTitle=Yii::app()->name . ' -'. Yii::t('yii','Error');
$this->breadcrumbs=array(
	Yii::t('yii','Error'),
);
?>

<div class="item">
	<a name="top"></a>
	<div class="contract">


<div class="title" style="color:red">
 <?php echo Yii::t('form','error'); echo ": ". $code; ?>
</div>
<img src="images/error.png" align="left" style="margin-right:10px;">
	<?php 
	echo "<br/>";
	echo "<br/>";
	
	?>
<div class="error">
<?php echo $message; 
	echo CHtml::encode($message); ?>
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
		
		echo "<a href='Javascript:history.go(-1)'>".CHtml::encode(Yii::t('yii','Back to Index'))."</a>";
		//echo CHtml::link(CHtml::encode(Yii::t('yii','Back to Index')),array('site/index'));
		?>
	</div>
	</center>

