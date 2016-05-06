<?php
$this->breadcrumbs=array(
	Yii::t('payments','Payments')=>array('indexpaymentsperorg','org'=>Yii::app()->request->getParam('org')),
	$model->title,
);
?>


<?php

$this->menu=array(
array('label'=>Yii::t('payments','All Payments'), 'url'=>array('indexpaymentsperorg','org'=>Yii::app()->request->getParam('org'))),
	);
	 $this->renderPartial('/commons/TabHeader',
				array('model'=>'payment','organizations'=>true,'title'=>$model->title,
					'documentUrl'=>$model->documentUrl,'submissionTime'=>$model->submissionTime,
					'lastModifiedTime'=>$model->lastModifiedTime,'relatedADAs'=>$model->relatedADAs,
					'OrganisationName'=>$model->OrganisationName));
			
		$this->renderPartial('/commons/TabCanceled',
				array('model'=>'payment','organizations'=>true,'cancelled'=>$model->cancelled,
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
		 if($model->contract){
		                echo"<div class='extends'>";
		               
		                echo CHtml::link('Αφορά την Σύμβαση με Μοναδικό αριθμό '.$model->contract.'',array('contract/viewcontract','uniqueDocumentCode'=>$model->contract),array('target'=>'_blank'));     
		                echo"</div>";
	        } 
	     if($model->procurements){
		                echo"<div class='extendsProc'>";
		                 echo "Αφορά τα Αιτήματα με Μοναδικό Αριθμό<br/>";
		                 $ProcIds = explode(";", $model->procurements);
							for($i=0;$i<count($ProcIds);$i++){
								if($ProcIds[$i]=='') continue;
		               			echo CHtml::link($ProcIds[$i],array('procurement/viewproc','documentUrl'=>$ProcIds[$i]),array('target'=>'_blank'));  
		               			echo "<br/>";   
							}
		               	echo"</div>";
	        } 
	      
	       
	      if($ContractData->notice[0]){
	      	echo"<div class='extendsProc' style='margin-top:10px;clear:both'>";
		    echo CHtml::link('Αφορά την Προκήρυξη με Μοναδικό αριθμό '.$ContractData->notice[0].'',array('rfp/viewrfp','uniqueDocumentCode'=>$ContractData->notice[0]), array('target'=>'_blank'));     
		    echo"</div>";
	      }
		
			echo"<div class=\"row\">";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','documentUrl')."</span>";
			echo "<span class='data'>".$model->documentUrl ."</span>";
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','dateSigned')."</span>";
			echo "<span class='data'>".$model->dateSigned ."</span>";
			echo"</div>";
			
			if(isset($model->responsibilityAssumptionCode) && $model->responsibilityAssumptionCode!=''){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('procurement','responsibilityAssumptionCode')."</span>";
				echo "<span class='data'>". $model->responsibilityAssumptionCode."</span>";
				echo"</div>";
			}
			if(isset($model->protocolNumberCode) && $model->protocolNumberCode!=''){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('contract','protocolNumberCode')."</span>";
				echo "<span class='data'>". $model->protocolNumberCode."</span>";
				echo"</div>";
			}
		//	echo"<div class='row-grey'>";
		//	echo "<span class='title'>". Yii::t('contract','diavgeiaPublished')."</span>";
		//	echo "<span class='data'>". $model->diavgeiaPublished."</span>";
		//	echo"</div>";
			echo"</div>";
			if($model->issuerEmail){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('form','issuerEmail')."</span>";
				echo "<span class='data'>".str_replace('@','  [AT]  ',$model->issuerEmail) ."</span>";
				echo"</div>";
			}
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
		if($ContractData->documentUrl[$i]){
		                echo"<div class='extendsProc'>";
		                 echo "Αφορά το Αίτημα με Μοναδικό Αριθμό<br/>";
		                 
		               			echo CHtml::link($ContractData->documentUrl[$i],
		               			array('organizations/viewproc','documentUrl'=>$ContractData->documentUrl[$i],
		               				 'org'=>Yii::app()->request->getParam('org')));  
		               			echo "<br/>";   
						
		               	echo"</div>";
	     } 
		
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
		echo"<br/>";
		echo "<span class='title'>".Yii::t('item','currency')."</span>";
		echo "<span class='data'>".Lookup::item('Currency',$ContractData->currencyid[$i])."</span>";
		echo "<span class='title'>".Yii::t('item','invoice')."</span>";
		echo "<span class='data'>".$ContractData->invoice[$i]."</span>";
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
		if(isset($ContractData->Secafm[$i])){
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','Secafm')."</span>";
			echo "<span class='data'>".$ContractData->Secafm[$i] ."</span>";
		
			echo "<span class='title'>".Yii::t('contract','Secname')."</span>";
			echo "<span class='data'>".$ContractData->Secname[$i] ."</span>";
			
			echo "<span class='title'>".Yii::t('contract','Seccountry')."</span>";
			echo "<span class='data'>".Lookup::item('country',$ContractData->Seccountry[$i]) ."</span>";
			echo"</div>";
		}
			echo"</div>";
		
		echo"<div class='row-grey'>";
	
			if(isset($ContractData->countryProduced[$i]) && $ContractData->countryProduced[$i]!=''){
				echo "<span class='title'>".Yii::t('payments','countryProduced')."</span>";
				echo "<span class='data'>".Lookup::item('country',$ContractData->countryProduced[$i]) ."</span>";
			}
			if(isset($ContractData->countryOfDelivery[$i]) && $ContractData->countryOfDelivery[$i]!=''){
				echo "<span class='title'>".Yii::t('payments','countryOfDelivery')."</span>";
				echo "<span class='data'>".Lookup::item('country',$ContractData->countryOfDelivery[$i]) ."</span>";
			}
		echo"</div>";
		echo"<div class='row-grey'>";
			if(isset($ContractData->address[$i]) && $ContractData->address[$i]!=''){
				echo "<span class='title'>".Yii::t('payments','address')."</span>";
				echo "<span class='data'>".$ContractData->address[$i] ."</span>";
			}
			if(isset($ContractData->addressNo[$i]) && $ContractData->addressNo[$i]!=''){
				echo "<span class='title'>".Yii::t('payments','addressNo')."</span>";
				echo "<span class='data'>".$ContractData->addressNo[$i] ."</span>";
			}
			if(isset($ContractData->addressPostal[$i]) && $ContractData->addressPostal[$i]!=''){
				echo "<span class='title'>".Yii::t('payments','addressPostal')."</span>";
				echo "<span class='data'>".$ContractData->addressPostal[$i] ."</span>";
			}
		//	echo "<span class='title'>".Yii::t('payments','nuts')."</span>";
		//	echo "<span class='data'>".$ContractData->nuts[$i] ."</span>";
			if(isset($ContractData->city[$i]) && $ContractData->city[$i]!=''){
				echo "<span class='title'>".Yii::t('payments','city')."</span>";
				echo "<span class='data'>".$ContractData->city[$i] ."</span>";
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
		echo CHtml::link(CHtml::encode(Yii::t('form','downloadFile')),array('payments/downloadpayment','documentUrl'=>$model->documentUrl));
		?>
	</div>
	</center>

