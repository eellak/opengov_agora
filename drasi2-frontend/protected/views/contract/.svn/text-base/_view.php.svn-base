<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $data['uniqueDocumentCode']; ?>">
	<?php echo "<div class=\"documentUrl\"> ". $data['uniqueDocumentCode'] ."</div>";?>
        <!-- title bar -->
	 		<div class="title">
	        	<?php echo CHtml::link($data['title'], array('contract/viewcontract','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
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
	        	<?php CHtml::link('Update',array('contract/viewcontract','uniqueDocumentCode'=>$data['uniqueDocumentCode'])); ?>   
	                 <?php echo CHtml::link(Yii::t('contract','View Contract'), array('contract/viewcontract','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
	            			 array(
				                'class'=>'view',
				                'title'=>Yii::t('contract','View Contract'),));
	            			
	            			if(!Yii::app()->user->isGuest ){
	            			 echo"&nbsp;&nbsp;";
	            			 if($data['actionPermitted'] && Yii::app()->user->role!='user' && $data['cancelled']=='false'){
							 	 echo CHtml::link(Yii::t('contract','Update Contract'), array('contract/updatecontract','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
		            			 array(
					                'class'=>'update'));
		            			 echo"&nbsp;&nbsp;";
		            			 echo CHtml::link(Yii::t('contract','Delete Contract'), array('contract/deletecontract','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
		            			 array(
					                'class'=>'delete',
		            				/*'confirm'=>Yii::t('contract','deleteProcQuestion')*/));
							 }
							 echo"&nbsp;&nbsp;<br/>";
	            			if(Yii::app()->user->role!='admin'){
							  echo CHtml::link(Yii::t('contract','Extend Contract'), array('contract/extendcontract','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
	            			 array(
				                'class'=>'update',
				                'title'=>Yii::t('contract','Extend Contract')));
							 echo"&nbsp;&nbsp;";
							 echo"&nbsp;&nbsp;";
							  echo CHtml::link(Yii::t('contract','Changes Contract'), array('contract/changescontract','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
	            			 array(
				                'class'=>'update',
				                'title'=>Yii::t('contract','Changes Contract')));
							 echo"&nbsp;&nbsp;";
	            			if($data['cancelled']=='false'){
		            			echo"&nbsp;&nbsp;";
		            			echo CHtml::link(Yii::t('form','Cancel'), array('contract/cancelcontract','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
		            			array('class'=>'view',
					       				));
					       			
		            			}
	            			}
	            			if( Yii::app()->user->role!='admin'){
	            				echo"<br/>";
							  echo CHtml::link(Yii::t('payments','Create Payment'), array('payments/createpaymentbycontract','documentUrl'=>$data['uniqueDocumentCode']),
	            			 	array(
				                'class'=>'update'));
							 echo"&nbsp;&nbsp;";
							 echo"&nbsp;&nbsp;";
							 
	            			}
							  echo CHtml::link(Yii::t('contract','View Payments'), array('contract/viewcontractpayments','uniqueDocumentCode'=>$data['uniqueDocumentCode']),
	            			 array(
				                'class'=>'update'));
							 echo"&nbsp;&nbsp;";
	            			}
       			  
	              ?>
	    
	        <!-- Last update bar -->
	        <?php 
	        	$this->renderPartial('/commons/labelsList',array('data'=>$data,'model'=>'contract','organizations'=>false));
	        	$this->renderPartial('/commons/updateBar',array('data'=>$data,'model'=>'contract','organizations'=>false));
	        ?>
	        
	 </div><!-- contract -->

</div><!-- item -->
