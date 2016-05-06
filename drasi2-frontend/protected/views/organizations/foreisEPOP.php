<div style="padding-left:20px;width:600px;">
<div class="item">
	<a name="top"></a>
	<div class="contract">
        <!-- title bar -->
	 		<div class="title">
	        	<?php echo "Ανεξάρτητες Αρχές"; ?>   
	        </div>
	       
	</div>
	<?php 
 	$arxes = Organizations::getArxes();
 	for($i=0;$i<count($arxes);$i++){
 		if($arxes[$i]['short']!=''){
 		echo CHtml::link($arxes[$i]['title'], 
		array('organizations/startprocperorg','org'=>$arxes[$i]['short']),
		array('target'=>'_blank','class'=>'greyBgBig'));
		echo"<br/>";
 		}	
 	}
	?>
</div>
</div>