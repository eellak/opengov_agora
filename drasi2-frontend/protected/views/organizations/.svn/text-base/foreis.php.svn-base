<?php
$this->breadcrumbs=array(
	'Εποπτευόμενοι Φορείς'
);
$this->menu=array(
		array('label'=>Yii::t('procurement','List Procurements'), 'url'=>array('indexprocperorg','org'=>Yii::app()->request->getParam('org'))),
		array('label'=>Yii::t('rfp','List'), 'url'=>array('indexrfpperorg','org'=>Yii::app()->request->getParam('org'))),
		array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('indexcontractsperorg','org'=>Yii::app()->request->getParam('org'))),
		array('label'=>Yii::t('payments','List Payments'), 'url'=>array('indexpaymentsperorg','org'=>Yii::app()->request->getParam('org'))),
);
	
?>

<div class="item">
	<a name="top"></a>
	<div class="contract">
        <!-- title bar -->
	 		<div class="title">
	        	<?php echo "Εποπτευόμενοι Φορείς" ?>   
	        </div>
	       
	</div>
	 <?php 
	        $org = Yii::app()->request->getParam('org');
			$Organizations = new Organizations();
			$org = $Organizations->getOrgId($org);
		
			if( isset($org) && $org!=''){
				$test= ODE::items('test',$org);
				
				if(count($test)>1){
					echo"<div style='margin-left:10px;'>";
					foreach ($test as $k => $v) {	
						if($v=='----') continue;
						echo CHtml::link($v, 
						array('organizations/startprocperorg','org'=>$k),
						array('target'=>'_blank','class'=>'blueBgBig'));
						echo"<br/>";
				     }
				     echo"</div>";
				  
				}else 
					echo"Δεν έχει εποπτευόμενους φορείς";
			}else 
			echo"Δεν έχει εποπτευόμενους φορείς";
			
	?>
</div>
