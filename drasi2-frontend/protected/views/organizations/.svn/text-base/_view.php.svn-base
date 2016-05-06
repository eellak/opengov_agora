<!-- http://www.yiiframework.com/forum/index.php?/topic/14520-more-than-one-jquery-ui-dialog/-->
<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $data['documentUrl']; ?>">
	<?php echo "<div class=\"documentUrl\"> ". $data['documentUrl'] ."</div>";?>
        <!-- title bar -->
	 		<div class="title">
	        	<?php echo CHtml::link($data['title'], array('organizations/viewproc',
	                 'documentUrl'=>$data['documentUrl'],'org'=>Yii::app()->request->getParam('org')),
	            			 array(
				                'class'=>'title',
				                'title'=>$data['title']));
	            			 echo"&nbsp;&nbsp;";
	        	?>
	        </div>
	        <?php  if(Yii::app()->user->isGuest){?>
	         <div class="subtitle">
	        	<?php 
	        		echo $data['OrganisationName']; ?>
	        
	        </div> 
	        <?php
	        }?>
	        <?php echo CHtml::link(Yii::t('procurement','View Procurement'), array('organizations/viewproc',
	                 'documentUrl'=>$data['documentUrl'],'org'=>Yii::app()->request->getParam('org')),
	            			 array(
				                'class'=>'view',
				                'title'=>Yii::t('procurement','View Procurement'),));
	            			 echo"&nbsp;&nbsp;";
	            		
       			  ?>
	               

	      
	          <!-- Last update bar -->
	        <?php 
	        	$this->renderPartial('/commons/labelsList',array('data'=>$data,'model'=>'procurement','organizations'=>true));
	        	$this->renderPartial('/commons/updateBar',array('data'=>$data,'model'=>'procurement','organizations'=>true));
	        ?>
	        
	 </div><!-- contract -->

</div><!-- item -->
