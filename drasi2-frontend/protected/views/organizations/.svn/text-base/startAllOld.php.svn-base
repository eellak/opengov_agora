<?php $this->pageTitle=Yii::app()->name; ?>

<?


?>
<?php Yii::app()->clientScript->registerCoreScript('jquery'); ?>

<div style="padding-left:20px;width:700px;">
	<h1><?php echo Yii::app()->name;?></h1>
	<div style="padding:10px;"><?php echo Yii::t('yii','SiteDescription1');?></div>

	<div class="item">
		<div class="contract" style="float:left; width:300px;">
	        <!-- title bar -->
		 	<div class="title">
			 	<a onclick="javascript: $('#ypourgeia').toggle('slow'); return false;" href="javascript:void(0);" class="open">
			 		<?php echo"Υπουργεία" ?>
			 	</a>
		    </div>
		    <div id="ypourgeia" style="display:none">
		    	<?php 
		    
		    		$dataProvider->pagination->pageSize = 5;
					$this->widget('zii.widgets.CListView', array(
        				 'dataProvider'=>$dataProvider,
        				 'itemView'=>'_viewforeis',
					  	 'summaryText' => '',
					));
		    	
		    	?>
		    </div>
		 </div><!-- contract end -->
	 </div><!-- item end -->
	  <div class="search" style="float:right; width:300px;">
		<div class="title">
				 	<a onclick="javascript: $('#arxes').toggle('slow'); return false;" href="javascript:void(0);" class="open">
		
			<?php echo"Ανεξάρτητες Αρχές" ?>
		</a>
		</div>
		<div id='arxes' style='display:none;'>
		<?php 
		
			$dataProviderAr->pagination->pageSize = 5;
					$this->widget('zii.widgets.CListView', array(
        				 'dataProvider'=>$dataProviderAr,
        				 'itemView'=>'_viewforeis',
					  	 'summaryText' => '',
					));
		
		?>		    
	</div>
	</div><!-- end search -->
	 <div class="clear"></div>
	 <div class="item">
<div class="contract" style="float:left; width:300px;">
	        <!-- title bar -->
		 	<div class="title">
		 	<a onclick="javascript: $('#per').toggle('slow'); return false;" href="javascript:void(0);" class="open">
		 		<?php echo"Περιφέρειες" ?>
		 	</a>
		    </div>
		    <div id="per" style="display:none">
		    	<?php 
		    
		    		$dataProviderPer->pagination->pageSize = 5;
					$this->widget('zii.widgets.CListView', array(
        				 'dataProvider'=>$dataProviderPer,
        				 'itemView'=>'_viewforeis',
					  	 'summaryText' => '',
					));
		    	
		    	?>
		    </div>
		 </div><!-- contract end -->
	 </div><!-- item end -->
	  <div class="search" style="float:right; width:300px;">
		<div class="title">
			 	<a onclick="javascript: $('#dhmoi').toggle('slow'); return false;" href="javascript:void(0);" class="open">
					<?php echo"Δήμοι" ?>
				</a>
		</div>
		<div id='dhmoi' style='display:none;'>
			<?php 
			
				$dataProviderDhmoi->pagination->pageSize = 5;
						$this->widget('zii.widgets.CListView', array(
	        				 'dataProvider'=>$dataProviderDhmoi,
	        				 'itemView'=>'_viewforeis',
						  	 'summaryText' => '',
						));
			
			?>		    
		</div><!--  end Dhmoi -->
	</div><!-- end search -->
	 <div class="clear"></div>

</div>