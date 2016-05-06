<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $data['documentUrl']; ?>">
	<?php echo "<div class=\"documentUrl\">". $data['documentUrl'] ."</div>";?>
        <!-- title bar -->
	 		<div class="title">
	        	<?php 
	        	echo CHtml::link($data['title'], 
	                 array('organizations/viewpayment','documentUrl'=>$data['documentUrl'],
	                 	'org'=>Yii::app()->request->getParam('org')),
	            			 array(
				                'class'=>'title',
				                'title'=>$data['title'],));
	        	
	        	 ?>
	            </div>
	             <?php  if(Yii::app()->user->isGuest){?>
	         <div class="subtitle">
	        	<?php 
	        	if(!isset(Yii::app()->user->OrganisationName))
	        		echo Units::item("Organization", $data['OrganizationIdRef'],$data['OrganizationIdRef']);
				else 
					echo Yii::app()->user->OrganisationName;
	        ?>   
	        
	        </div> 
	        <?php }?>
	                 <?php echo CHtml::link(Yii::t('payments','View Payment'), 
	                 array('organizations/viewpayment','documentUrl'=>$data['documentUrl'],
	                 	'org'=>Yii::app()->request->getParam('org')),
	            			 array(
				                'class'=>'view',
				                'title'=>Yii::t('payments','View Procurement'),));
	            
       			  ?>
	                
	        	
	    
	         <!-- Last update bar-->
	        <?php 
	            $this->renderPartial('/commons/labelsList',array('data'=>$data,'model'=>'payment','organizations'=>true));
	        	$this->renderPartial('/commons/updateBar',array('data'=>$data,'model'=>'payment','organizations'=>true));
	        ?>
	        
	        
	 </div><!-- contract -->

</div><!-- item -->
