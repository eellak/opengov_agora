<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $data['documentUrl']; ?>">
	<?php echo "<div class=\"documentUrl\"> ". $data['documentUrl'] ."</div>";?>
        <!-- title bar -->
	 		<div class="title">
	        	<?php 
	        	echo CHtml::link($data['title'], array('payments/viewpayment','documentUrl'=>$data['documentUrl']),
	            			 array(
				                'class'=>'title',
				                'title'=>$data['title'],));
	        	  ?>
	            </div>
	           <?php 
	          if(Yii::app()->user->isGuest || Yii::app()->user->role=='admin'){
	          	echo"<div class=\"subtitle\">";
	        	echo $data['OrganisationName']; 
	          	echo"</div>";
	          }
	          ?>
	          
	        	<?php 
	        		 if(!Yii::app()->user->isGuest){
	        			CHtml::link('Update',array('payments/viewproc','documentUrl'=>$data['documentUrl'])); ?>   
	                	<?php echo CHtml::link(Yii::t('payments','View Payment'), array('payments/viewpayment','documentUrl'=>$data['documentUrl']),
	            			 array(
				                'class'=>'view',
				                'title'=>Yii::t('payments','View Procurement'),));
	            			 
	            			  if($data['actionPermitted']  && isset(Yii::app()->user->role) && Yii::app()->user->role!='user'  && $data['cancelled']=='false'){
	            			  	echo"&nbsp;&nbsp;";
		            			 echo CHtml::link(Yii::t('payments','Update Payment'), array('payments/updatepayment','documentUrl'=>$data['documentUrl']),
		            			 array(
					               'class'=>'update',
					              'title'=>Yii::t('payments','Update Payment'),));
								 echo"&nbsp;&nbsp;";
		            			 echo CHtml::link(Yii::t('payments','Delete Payment'), array('payments/deletepayment','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
		            			 array(
					                'class'=>'delete',
					                'title'=>Yii::t('payment','Delete Payment'),
		            				/*'confirm'=>Yii::t('contract','deleteProcQuestion')*/));
							 }
							 
							  if(($data['cancelled']=='false')){
								echo"&nbsp;&nbsp;";
		            		 	echo CHtml::link(Yii::t('form','Cancel'), array('payments/cancelpayment','documentUrl'=>$data['documentUrl']),
		            		 	array('class'=>'view'));
							 }
	        		 }
       			 
	        	
	     	
	          	 ?>
	                
	       <!-- Last update bar -->
	        <?php 
	        $this->renderPartial('/commons/labelsList',array('data'=>$data,'model'=>'payment','organizations'=>false));
	        $this->renderPartial('/commons/updateBar',array('data'=>$data,'model'=>'payment','organizations'=>false));
	        ?>
	 </div><!-- contract -->

</div><!-- item -->
