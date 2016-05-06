<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $data['uniqueDocumentCode']; ?>">
	<?php echo "<div class=\"documentUrl\"> ". $data['uniqueDocumentCode'] ."</div>";?>
        <!-- title bar -->
	 		<div class="title">
	        	<?php 
	        	echo CHtml::link($data['title'], array('organizations/viewrfp',
	                 			'uniqueDocumentCode'=>$data['uniqueDocumentCode'],
	                            'org'=>Yii::app()->request->getParam('org')),
	                 
	            			array('class'=>'title'));
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
	        	<?php CHtml::link('Update',array('rfp/viewrfp','uniqueDocumentCode'=>$data['uniqueDocumentCode'])); ?>   
	                 <?php echo CHtml::link(Yii::t('rfp','View Rfp'), array('organizations/viewrfp',
	                 			'uniqueDocumentCode'=>$data['uniqueDocumentCode'],
	                            'org'=>Yii::app()->request->getParam('org')),
	                 
	            			array('class'=>'view'));
	          
	        	?>
	    
	        <!-- Last update bar -->
	        <?php 
	        	$this->renderPartial('/commons/labelsList',array('data'=>$data,'model'=>'rfp','organizations'=>true));
	        	$this->renderPartial('/commons/updateBar',array('data'=>$data,'model'=>'rfp','organizations'=>true));
	        ?>
	        
	 </div><!-- contract -->

</div><!-- item -->
