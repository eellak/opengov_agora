<?php $this->pageTitle=Yii::app()->name; ?>

<div style="padding-left:20px;width:600px;">
	<h1><?php echo Yii::app()->name;?></h1>
		<div style="padding:10px;"><?php echo Yii::t('yii','SiteDescription');?></div>
	
	 <div class="clear"></div>
	 <div class="search" style="float:right; width:600px;">
		<div class="title">
			<?php echo Yii::t('procurement','Procurements'); ?>
		</div>
		<?php echo Yii::t('procurement','ProcurementsDescription'); ?>		    
	</div><!-- end search -->
	<div class="clear"></div>
	 <div class="contract-content" style="float:left; margin-left:8px;width:600px;">
		<div class="data">
	   	<?php 
	   	echo"<div class=\"subtitle\">";
		echo Yii::t('rfp','RFP');
		echo"</div>";
		?>
		<?php 
			echo"<div class=\"row\">";
			echo"<div class='row-grey'>";
				echo Yii::t('rfp','RFPDescription');
			echo"</div>";
			echo"</div>";
		?>
		</div><!-- data end -->
	</div>
	<div class="item">
		<div class="contract" style="float:right; width:500px;">
	        <!-- title bar -->
		 	<div class="title">
		 		<?php echo Yii::t('contract','Contracts'); ?>
		    </div>
		    <div>
		 		<?php echo Yii::t('contract','ContractsDescription'); ?>		    
		    </div>
		 </div><!-- contract end -->
	 </div><!-- item end -->
	
	 <div class="clear"></div>
	 <div class="contract-content" style="float:left; margin-left:8px;width:500px;">
		<div class="data">
	   	<?php 
	   	echo"<div class=\"subtitle\">";
		echo Yii::t('payments','Payments');
		echo"</div>";
		?>
		<?php 
			echo"<div class=\"row\">";
			echo"<div class='row-grey'>";
				echo Yii::t('payments','PaymentsDescription');
			echo"</div>";
			echo"</div>";
		?>
		</div><!-- data end -->
	</div><!--contract-content-->
	
	<div class="clear"></div>
	
	 <div class="contract-content" style="float:right; margin-left:8px;">
		<div style="padding:10px;"><?php echo Yii::t('yii','SiteDesc2');?></div>
	 </div> <!-- contract content -->	
</div>
	