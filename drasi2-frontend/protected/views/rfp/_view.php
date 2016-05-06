<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $data['uniqueDocumentCode']; ?>">
	<?php echo "<div class=\"documentUrl\"> ". $data['uniqueDocumentCode'] ."</div>";?>
        <!-- title bar -->
	 		<div class="title">
	 		<?php echo CHtml::link($data['title'], array('rfp/viewrfp','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
	     			array('class'=>'title'));
	 		
	        	?>
	            </div>
	         
	    
	          <?php  
	          if(Yii::app()->user->isGuest || Yii::app()->user->role=='admin'){
	          	echo"<div class=\"subtitle\">";
	        	echo $data['OrganisationName']; 
	          	echo"</div>";
	          }
	    	  ?>    
	        
	        	<?php CHtml::link('Update',array('rfp/viewrfp','uniqueDocumentCode'=>$data['uniqueDocumentCode'])); ?>   
	                 <?php echo CHtml::link(Yii::t('rfp','View Rfp'), array('rfp/viewrfp','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
	            			array('class'=>'view'));
	            			
	            			if(!Yii::app()->user->isGuest){
	            			 if($data['actionPermitted'] && Yii::app()->user->role!='user' && $data['cancelled']=='false'){
							 	 echo"&nbsp;&nbsp;";
		            			 echo CHtml::link(Yii::t('contract','Update Contract'), array('rfp/updaterfp','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
		            			 array('class'=>'view'));
		            			 echo"&nbsp;&nbsp;";
		            			 echo CHtml::link(Yii::t('contract','Delete Contract'), array('rfp/deleterfp','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
		            			 array('class'=>'view'));
							 }
							 if(($data['cancelled']=='false')){
								echo"&nbsp;&nbsp;";
		            		 	echo CHtml::link(Yii::t('form','Cancel'), array('rfp/cancelrfp','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
		            		 	array('class'=>'view'));
							 }
	            			}
       			
	          	?>
	        <!-- Last update bar -->
	        <?php 
	       	$this->renderPartial('/commons/labelsList',array('data'=>$data,'model'=>'rfp','organizations'=>false));
	        $this->renderPartial('/commons/updateBar',array('data'=>$data,'model'=>'rfp','organizations'=>false));
	        ?>
	        
	 </div><!-- contract -->
</div><!-- item -->
