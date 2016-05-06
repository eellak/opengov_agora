<?php $this->beginContent('//layouts/mainPerOrg'); ?>
<div class="container">
	<div class="span-19">
		<div id="content">
			<?php echo $content; ?>
		</div><!-- content -->
	</div>
	<div class="span-5 last">
	
	<?php if($this->action->id=='indexall' || $this->action->id=='foreisEPOP')
		echo"<div id='sidebarBig'>";
	else
		echo"<div id='sidebar'>";
		?>
		<?php
			$this->beginWidget('zii.widgets.CPortlet', array(
				'title'=>'Operations',
			));
			$this->widget('zii.widgets.CMenu', array(
				'items'=>$this->menu,
				'htmlOptions'=>array('class'=>'operations'),
			));
			$this->endWidget();
			
		
			$org = Yii::app()->request->getParam('org');
			if($org!='' && $this->action->id!='foreis'){
				$Organizations = new Organizations();
				$org1 = $Organizations->getOrgId($org);
				if( $org1){
					$test= ODE::items('test',$org1);
					if(count($test)>1){
						echo"<div class='buttonorange'>";
						echo CHtml::link('Εποπτευόμενοι Φορείς', 
								array('organizations/foreis','org'=>$org),
								array('class'=>'buttonorange'));
						echo"</div>";
					}
			}
		}
			
		if($this->action->id=='indexall' || $this->action->id=='foreisEPOP'){
			echo "<div class='foreisTitle'>".Yii::t('yii','startPageForeis')."</div>";
			echo"<div class='foreisSubTitle'>Υπουργεία</div>";
			$test = Organizations::getYpourgeiaStatic();
			if(count($test)){
			$Data = array_keys($test);
			foreach ($Data as $value)
				echo CHtml::link($test[$value]['title'], 
							array('organizations/startprocperorg','org'=>$test[$value]['short']),
							array('class'=>'blueBg1','target'=>'_blank'));
			}
			echo"<div class='foreisSubTitle'>Περιφέρειες & Τοπική Αυτοδιοίκηση</div>";
			echo "<div class='foreisTitle'>".Yii::t('yii','startDetailsOTA')."</div>";
			echo"<div class='foreisSubTitle'>Εποπτευόμενοι Φορείς  / Νομικά Πρόσωπα</div>";
			echo "<div class='foreisTitle'>";
			echo CHtml::link('Δείτε τα στοιχεία τους', 
				 array('organizations/foreisEPOP'));
			echo"</div>";
			
		}
		if(isset($this->action->id))
		if($this->action->id!='rss'){
		   echo"<br/>";
		   echo CHtml::link('<img src="images/logo_feed.png" alt="RSS Feed"/>',array('/organizations/rss',
		   						'org'=>Yii::app()->request->getParam('org')));	
		}
		?>
		</div><!-- sidebar -->
	</div>
</div>
</div>

		<?php $this->endContent(); ?>