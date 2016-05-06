<?php
$this->pageTitle=Yii::app()->name . ' - Login';
$this->breadcrumbs=array(
	Yii::t('yii','Login'),
);
?>
<div id="wrap">
<h1><?php echo Yii::t('form','logintitle');?></h1>

<p><?php echo Yii::t('form','logindesc');?></p>

<div class="formLogin" style="margin-left:20px;">
	<?php $form=$this->beginWidget('CActiveForm', array(
		'id'=>'login-form',
		'enableAjaxValidation'=>true,
	)); ?>
	
		<p class="note">
	 		<?php echo Yii::t('form','requiredStart');?> 
	 		<span class="required">*</span> 
	 		<?php echo Yii::t('form','requiredEnd');?>
	 	</p>
	 	<?php echo '<p>'.$form->errorSummary(array($model)).'</p>'; 
	 	?>
		<div class="row">
			<?php echo $form->labelEx($model,'username'); ?>
			<?php echo $form->textField($model,'username'); ?>
			<?php echo $form->error($model,'username'); ?>
		</div>
		<div class="clear"></div>
		<div class="row">
			<?php echo $form->labelEx($model,'password'); ?>
			<?php echo $form->passwordField($model,'password'); ?>
			<?php echo $form->error($model,'password'); ?>
		</div>
		<div class="clear"></div>
	
	<?php if(extension_loaded('gd')): ?>
	    <div class="row">
	        <?php echo $form->labelEx($model,'validacion'); ?>
	        <div>
	        <?php $this->widget('CCaptcha'); ?>
	        <div class="clear"></div>
	        <?php echo $form->textField($model,'validacion'); ?>
	        </div>
	
	    </div>
	    <?php endif; ?>
	    <div class="clear"></div>
	    
	<?php //echo $form->labelEx($model, 'validacion'); ?>
	<?php  /*$this->widget('application.extensions.recaptcha.EReCaptcha', 
	   array('model'=>$model, 
	   		 'attribute'=>'validacion',
	         'theme'=>'clean', 
	         'language'=>'en', 
	         'publicKey'=>Yii::app()->params['captchaPublicKey']))*/ ?>
	         
	<?php //$this->widget('CCaptcha'); ?>
	<?php //echo $form->error($model, 'validacion'); ?>	
	
		<div class="row buttons">
			<?php echo CHtml::submitButton('Login'); ?>
		</div>
	
	<?php $this->endWidget(); ?>



</div>
<div id="sidebarLogin">
<?php 
		echo Yii::t('form','logindiavgeia');?>
</div>
</div>
<!-- form -->
