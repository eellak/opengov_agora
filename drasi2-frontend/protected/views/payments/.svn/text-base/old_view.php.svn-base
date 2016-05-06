<?php
$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')=>array('indexproc'),
	$model->title,
);


$this->menu=array(
	array('label'=>'List Procurement', 'url'=>array('indexproc')),
	array('label'=>'Create Procurement', 'url'=>array('createproc')),
	array('label'=>'Update Procurement', 'url'=>array('updateproc', 'documentUrl'=>$model->documentUrl)),
	array('label'=>'Delete Procurement', 'url'=>'#', 'linkOptions'=>array('submit'=>array('deleteproc','documentUrl'=>$model->documentUrl),'confirm'=>'Are you sure you want to delete this item?')),
);
?>


<?php

$this->menu=array(

	array('label'=>Yii::t('procurement','Update Procurement'), 'url'=>array('updateproc', 'documentUrl'=>$model->documentUrl)),
	array('label'=>Yii::t('procurement','Delete Procurement'),  'url'=>'#', 'linkOptions'=>array('submit'=>array('deleteproc','documentUrl'=>$model->documentUrl),'confirm'=>'Are you sure you want to delete this item?')),
	);

?>

<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $model->uniqueDocumentCode; ?>">
	<?php echo CHtml::link("#{$model->uniqueDocumentCode}", $model->uniqueDocumentCode, array(
	                'class'=>'cid',
	                'title'=>'Permalink to this comment',
	        )); ?>
	        <!-- title bar -->
	 		<div class="title">
	        	<?php echo $model->title; ?>   
	        </div>
	        <!-- Last update bar -->
	         <div class="time">
	        	  <?php 
	       		  		echo CHtml::link('Update',array('contract/updatecontract','uniqueDocumentCode'=>$model->uniqueDocumentCode)); ?> |    
	                <?php 
	              	echo"<span class=\"lastUpdated\">";
	                echo Yii::t('form','Created');
	                echo"</span>";
	                echo $model->submissionTime."&nbsp;&nbsp;"; 
	                echo"<span class=\"lastUpdated\">";
	                echo Yii::t('form','LastUpdated');
	                echo"</span>";
	                echo $model->lastModifiedTime;
	                echo"<br/><br/>";
	                echo"<a href=\"#BasicData\">:: ";
					echo Yii::t('form','Basic Data');
					echo"</a>&nbsp;&nbsp;&nbsp;";
	                echo"<a href=\"#contractParties\">::";
	                echo Yii::t('contract','contractParties');
	                echo"</a>&nbsp;&nbsp;&nbsp;";
	                echo"<a href=\"#ContractItems\">::";
	                echo Yii::t('form','ContractItems');
	                echo"</a>";
	              
	                ?>
	        </div>
	        
	 </div>
	 <div class="contract-content">
	        
		<!-- Basic Data bar -->
		<div class="data">
	   	<?php 
	   	echo"<div class=\"subtitle\"><a name=\"BasicData\">";
		echo Yii::t('form','Basic Data');
		echo"</a></div>";
		?>
		<?php 
			echo"<div class=\"row\">";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','documentUrl')."</span>";
			echo "<span class='data'>".$model->documentUrl ."</span>";
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','dateSigned')."</span>";
			echo "<span class='data'>".$model->dateSigned ."</span>";
			echo"</div>";
			
			
			echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','protocolNumberCode')."</span>";
			echo "<span class='data'>". $model->protocolNumberCode."</span>";
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','diavgeiaPublished')."</span>";
			echo "<span class='data'>". $model->diavgeiaPublished."</span>";
			echo"</div>";
			
			
			echo"</div>";
			
	?>
		</div>
		<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
	</div>
	 <div class="contract-content">
	 <!-- Basic Data bar -->
		<div class="data">
		<?php 
		    echo"<div class='subtitle'><a name=\"contractParties\">";
			echo Yii::t('contract','contractParties');
			echo"</a></div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','OrganizationIdRefUnits')."</span>";
			echo "<span class='data'>".$model->OrganizationIdRefUnits ."</span>";
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','signers')."</span>";
			echo "<span class='data'>".$model->signers ."</span>";
			echo"</div>";
			for($sign=0;$sign<count($SignersData->Secname);$sign++){
				echo"<div class='row-grey'>";
				echo "<span class='title'>".Yii::t('contract','Secname')."</span>";
				echo "<span class='data'>".$SignersData->Secname[$sign] ."</span>";
				
				echo "<span class='title'>".Yii::t('contract','Secafm')."</span>";
				echo "<span class='data'>".$SignersData->Secafm[$sign] ."</span>";
				
				echo "<span class='title'>".Yii::t('contract','Seccountry')."</span>";
				echo "<span class='data'>".$SignersData->Seccountry[$sign] ."</span>";
				echo"</div>";
			}
		?>
		</div>
				<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
		
	</div>
	<div class="contract-content">
	<?php 
		echo"<div class=\"subtitle\"><a name=\"ContractItems\">";
		echo Yii::t('form','ContractItems');
		echo"</a></div>";
		$kk=0;
		for($i=0;$i<count($ContractData->quantity);$i++){
			$kk=$i+1;
		echo"<div class=\"row\">";
		echo"<div class=\"subcontract\">";
		echo"<div class=\"title\">". Yii::t('form','ContractItem') ."&nbsp;".$kk."</div>";
		echo"</div>";
		echo"<div class='row-grey'>";
		echo "<span class='title'>".Yii::t('item','quantity')."</span>";
		echo "<span class='data'>".$ContractData->quantity[$i] ."</span>";
		
		echo "<span class='title'>".Yii::t('item','cost')."</span>";
		echo "<span class='data'>".$ContractData->cost[$i] ."</span>";
		
		echo "<span class='title'>".Yii::t('item','vat')."</span>";
		echo "<span class='data'>". Lookup::item('FPA',$ContractData->vatid[$i]) ."</span>";
		
		echo "<span class='title'>".Yii::t('item','currency')."</span>";
		echo "<span class='data'>".$ContractData->currencyid[$i]."</span>";
		echo"</div>";
		echo"<div class='row-grey'>";
		echo "<span class='title'>".Yii::t('item','description')."</span>";
		echo "<span class='data'>".$ContractData->description[$i] ."</span>";
		echo"</div>";

		if(isset($ContractData->cpvsid[$i])){
		echo"<div class='row-grey'>";
		echo "<span class='title'>".Yii::t('item','CPVS')."</span>";
		echo "<span class='data'>".$ContractData->cpvsid[$i] ."</span>";
		echo"</div>";
		}
		if(isset($ContractData->KAE[$i])){
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('item','KAE')."</span>";
			echo "<span class='data'>".$ContractData->KAE[$i] ."</span>";
			echo"</div>";
		}
	
			echo"</div>";
		}
		?>
				<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
		
	</div>
	
	<!-- Basic Data bar end-->

	<center>
	<div class="download">
		<?php 
		echo CHtml::link(CHtml::encode(Yii::t('form','downloadFile')),array('contract/contractdownload','uniqueDocumentCode'=>$model->uniqueDocumentCode));
		?>
	</div>
	</center>
</div>

<?
/*</div>

$this->menu=array(
	array('label'=>'List Contracts', 'url'=>array('index')),
	array('label'=>'Create Contract', 'url'=>array('createcontract')),
	array('label'=>'Update Contract', 'url'=>array('updatecontract', 'id'=>$model->uniqueDocumentCode)),
	array('label'=>'Delete Contract', 'url'=>'#', 'linkOptions'=>array('submit'=>array('delete','uniqueDocumentCode'=>$model->uniqueDocumentCode),'confirm'=>'Are you sure you want to delete this item?')),
);

*/?>



<?php /*$this->widget('zii.widgets.CDetailView', array(
	'data'=>$model,
	'attributes'=>array(
		'title',
		'uniqueDocumentCode' ,
		'protocolNumberCode',
		'OrganizationIdRefUnits',
		'signers',
		'diavgeiaPublished' ,
		'dateSigned',
		'responsibilityAssumptionCode',
	
	 array(               // related city displayed as a link
            'label'=>Yii::t('procurement','document'),
            'type'=>'raw',
            'value'=>CHtml::link(CHtml::encode($model->title),
                                 array('procurement/downloadproc','documentUrl'=>$model->documentUrl)),
        ),),
        
        
)); */
?>
<?php /*$this->widget('zii.widgets.CDetailView', array(
	'data'=>$contractItem,
	'attributes'=>array(
		'cost',
		'quantity' ,
		'currencyid',
		'description',
		'cpvsid',
	),
	
)); */

?>
