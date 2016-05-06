<!-- http://www.yiiframework.com/forum/index.php?/topic/14520-more-than-one-jquery-ui-dialog/-->
<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $data['documentUrl']; ?>">
	<?php echo "<div class=\"documentUrl\">". $data['documentUrl'] ."</div>";?>
        <!-- title bar -->
	 		<div class="title">
	        	<?php 
	        	echo CHtml::link($data['title'], array('procurement/viewproc','documentUrl'=>$data['documentUrl']),
	            	 array('class'=>'title',
				           'title'=>$data['title'],));	        
	        	?>
	        </div>

	        <?php  
            $organisationName =  Units::item("Organization", $data['OrganizationIdRef'],$data['OrganizationIdRef']);
	        if(Yii::app()->user->isGuest || ( Yii::app()->user->role=='admin')){
	        	echo"<div class=\"subtitle\">\n";
                echo $organisationName;
	       		echo"</div>\n"; 
	        }
	        CHtml::link('Update',array('procurement/viewproc','documentUrl'=>$data['documentUrl']));
	        ?>   
	        <?php echo CHtml::link(Yii::t('procurement','View Procurement'), array('procurement/viewproc','documentUrl'=>$data['documentUrl']),
	        		array(
				    'class'=>'view',
				    'title'=>Yii::t('procurement','View Procurement'),));
	            	echo"&nbsp;&nbsp;";
	            	if($data['cancelled']=='false' && $data['actionPermitted']==1 && (isset(Yii::app()->user->role) && Yii::app()->user->role!='user') && (isset($data['isApproved']) && $data['isApproved']=='') ) {
		            	echo CHtml::link(Yii::t('procurement','Update Procurement'), array('procurement/updateproc','documentUrl'=>$data['documentUrl']),
		            		array('class'=>'view',));
	            		echo"&nbsp;&nbsp;";
		            	echo CHtml::link(Yii::t('procurement','Delete Procurement'), array('procurement/deleteproc','documentUrl'=>$data['documentUrl']),
		            		array('class'=>'view',
					              'title'=>Yii::t('procurement','Delete Procurement'),
								 ));
		            }
	            	if(($data['cancelled']=='false' && $data['approvesRequest']=='' && (isset($data['isApproved']) && $data['isApproved']=='') &&   (isset(Yii::app()->user->role) && Yii::app()->user->role!='admin') )){		            			
		            	echo"&nbsp;&nbsp;<br/>";
		            	echo CHtml::link(Yii::t('procurement','Approve Procurement'), array('procurement/approverequest','documentUrl'=>$data['documentUrl']),
		            	array('class'=>'view',
					          'title'=>Yii::t('procurement','Approve Procurement'),
							));
						echo"&nbsp;&nbsp;";
		            	echo CHtml::link(Yii::t('form','Cancel'), array('procurement/cancelProc','documentUrl'=>$data['documentUrl']),
		            	array('class'=>'view',
					          'title'=>Yii::t('procurement','Approve Procurement'),
							));
		            }
		            
		            if($data['cancelled']=='false' && $data['approvesRequest']!=''){
		            	echo"&nbsp;&nbsp;";
		            	echo CHtml::link(Yii::t('form','Cancel'), array('procurement/cancelProc','documentUrl'=>$data['documentUrl']),
		            	array('class'=>'view',
					          'title'=>Yii::t('procurement','Approve Procurement'),
							));
		            }
		            
       		  ?>
	        
	         <!-- Last update bar -->
	        <?php 
	       	$this->renderPartial('/commons/labelsList',array('data'=>$data,'model'=>'procurement','organizations'=>false));
	        $this->renderPartial('/commons/updateBar',array('data'=>$data,'model'=>'procurement','organizations'=>false));
	        ?>
	 </div><!-- contract -->
</div><!-- item -->
