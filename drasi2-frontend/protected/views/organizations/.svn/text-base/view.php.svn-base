<?php
$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')=>array('indexprocperorg','org'=>Yii::app()->request->getParam('org')),
	$model->title,
);

$this->menu=array(
	array('label'=>Yii::t('procurement','List'), 'url'=>array('indexprocperorg','org'=>Yii::app()->request->getParam('org'))),
	);

		$organisationName =  Units::item("Organization", $model->OrganizationIdRef,$model->OrganizationIdRef);
		$this->renderPartial('/commons/TabHeader',
				array('model'=>'procurement','organizations'=>true,'title'=>$model->title,
					'documentUrl'=>$model->documentUrl,'submissionTime'=>$model->submissionTime,
					'lastModifiedTime'=>$model->lastModifiedTime,'relatedADAs'=>'',
					'OrganisationName'=>$organisationName));
		$this->renderPartial('/commons/TabCanceled',
				array('model'=>'procurement','organizations'=>true,'cancelled'=>$model->cancelled,
					'cancellationType'=>$model->cancellationType,'cancelledTime'=>$model->cancelledTime,
					'cancelledReason'=>$model->cancelledReason));
	 ?>
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
			if(isset($model->fulfilmentDate) && $model->fulfilmentDate!=''){
				echo"<div class='row-grey'>";
				echo "<span class='title'>".Yii::t('procurement','fulfilmentDate')."</span>";
				echo "<span class='data'>".$model->fulfilmentDate ."</span>";
				echo"</div>";
			}
			if(isset($model->responsibilityAssumptionCode) && $model->responsibilityAssumptionCode!=''){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('procurement','responsibilityAssumptionCode')."</span>";
				echo "<span class='data'>". $model->responsibilityAssumptionCode."</span>";
				echo"</div>";
			}
			if(isset($model->protocolNumberCode) && $model->protocolNumberCode!=''){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('rfp','protocolNumberCode')."</span>";
				echo "<span class='data'>". $model->protocolNumberCode."</span>";
				echo"</div>";
			}
			if(isset($model->awardProcedure) && $model->awardProcedure!=''){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('contract','awardProcedure')."</span>";
				echo "<span class='data'>". $model->awardProcedure."</span>";
				echo"</div>";
			}
		/*	echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','diavgeiaPublished')."</span>";
			echo "<span class='data'>". $model->diavgeiaPublished."</span>";
			echo"</div>";
			*/
			
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
			if($model->issuerEmail){
				echo"<div class='row-grey'>";
				echo "<span class='title'>".Yii::t('form','issuerEmail')."</span>";
				echo "<span class='data'>".str_replace('@','  [AT]  ',$model->issuerEmail) ."</span>";
				echo"</div>";
			}
			
		?>
		</div>
				<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
		
	</div>
	<div class="contract-content">
	<?php 
		echo"<div class=\"subtitle\"><a name=\"ContractItems\">";
		echo Yii::t('form','Contract Data');
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
		echo "<span class='title'>".Yii::t('item','units_of_measure')."</span>";
		echo "<span class='data'>".Lookup::item('units_of_measure',$ContractData->units_of_measure[$i])."</span>";
		
		echo "<span class='title'>".Yii::t('item','cost')."</span>";
		$fm = new CNumberFormatter(Yii::app()->getLocale());
		$ContractData->cost[$i] =$fm->formatCurrency($ContractData->cost[$i],$ContractData->currencyid[$i]);
		echo "<span class='data'>".$ContractData->cost[$i] ."</span>";
		
		echo "<span class='title'>".Yii::t('item','vat')."</span>";
		echo "<span class='data'>". Lookup::item('FPA',$ContractData->vatid[$i]) ."</span>";
		
		echo "<span class='title'>".Yii::t('item','currency')."</span>";
		echo "<span class='data'>".Lookup::item('Currency',$ContractData->currencyid[$i])."</span>";
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
		if(isset($ContractData->KAE[$i]) && $ContractData->KAE[$i]!=''){
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
		echo CHtml::link(CHtml::encode(Yii::t('form','downloadFile')),array('procurement/downloadproc','documentUrl'=>$model->documentUrl));
		?>
	</div>
	</center>
