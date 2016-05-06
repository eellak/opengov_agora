<?php $this->beginContent('//layouts/main'); ?>
<div class="container">
	<div class="span-19">
		<div id="content">
			<?php echo $content; ?>
		</div><!-- content -->
	</div>
	<div class="span-5 last">
		<div id="sidebar">
		<?php
			$this->beginWidget('zii.widgets.CPortlet', array(
				'title'=>'Operations',
			));
			$this->widget('zii.widgets.CMenu', array(
				'items'=>$this->menu,
				'htmlOptions'=>array('class'=>'operations'),
			));
			$this->endWidget();
		?>
		
		
		<?php 
			if(isset($this->action->id))
			if($this->action->id=='searchrfp' || $this->action->id=='searchcontract' ||  $this->action->id=='searchpayments'
				||  $this->action->id=='searchproc'){
				echo"<div class=\"foreisSubTitle\" style='width:158px;margin-left:-9px;'>".Yii::t('form','Help')."</div>";
				echo"<div class='foreisTitle' style='width:158px;margin-left:-9px;'>";
				if(Yii::app()->user->isGuest)
					echo Yii::t('form','SearchRightUser');
				else
					echo Yii::t('form','SearchRight');
				echo"</div>";
				
			}
			if(isset($this->action->id))
			if($this->action->id!='rss' && $this->action->id!='index'){
			   echo"<br/>";
			   echo CHtml::link('<img src="images/logo_feed.png" alt="RSS Feed"/>',array('/site/rss'));	
			}
		?>
		</div><!-- sidebar -->
	</div>
</div>
	
<?php $this->endContent(); ?>