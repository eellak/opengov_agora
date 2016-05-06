<?php

$this->breadcrumbs=array(
	Yii::t('rfp','RFP')=>array('index'),
	$model->title,
);

$this->menu=array(


	array('label'=>Yii::t('contract','Update Contract'), 
	'url'=>array('updaterfp', 'uniqueDocumentCode'=>$model->uniqueDocumentCode),
	'visible'=>$model->actionPermitted && Yii::app()->user->role!='user' && $model->cancelled=='false' ),
	array('label'=>Yii::t('form','Cancel'),  
		 'url'=>'#', 'visible'=> !Yii::app()->user->isGuest 
		&& $model->cancelled=='false' && Yii::app()->user->role!='user',
		 'linkOptions'=>array('submit'=>array('cancelrfp','uniqueDocumentCode'=>$model->uniqueDocumentCode))),
	array('label'=>Yii::t('contract','Delete Contract'), 
		 'visible'=>$model->actionPermitted && Yii::app()->user->role!='user' && $model->cancelled=='false', 
		 'url'=>array('deleterfp', 'uniqueDocumentCode'=>$model->uniqueDocumentCode )),
	array('label'=>Yii::t('rfp','Create Rfp'), 'url'=>array('rfp/createrfp', 
		'documentUrl'=>$model->uniqueDocumentCode),'visible'=>!Yii::app()->user->isGuest 
		&& Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('rfp','List'), 'url'=>array('index')),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchrfp'))
	            			
);
	   //header with dates and title and anchors 
	   $this->renderPartial('/commons/TabHeader',
				array('model'=>'payment','organizations'=>false,'title'=>$model->title,
					'documentUrl'=>$model->documentUrl,'submissionTime'=>$model->submissionTime,
					'lastModifiedTime'=>$model->lastModifiedTime,'relatedADAs'=>$model->relatedADAs,
					'OrganisationName'=>$model->OrganisationName));
		//the canceled tab		
		$this->renderPartial('/commons/TabCanceled',
				array('model'=>'rfp','organizations'=>false,'cancelled'=>$model->cancelled,
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
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('rfp','since')."</span>";
			echo "<span class='data'>".$model->since ."</span>";
			
			echo "<span class='title'>". Yii::t('rfp','until')."</span>";
			if($model->until!='')
				echo "<span class='data'>". $model->until."</span>";
			else
				echo "<span class='data'>". Yii::t('contract','nountil')."</span>";
				echo"</div>";
			
			
			echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('rfp','protocolNumberCode')."</span>";
			echo "<span class='data'>". $model->protocolNumberCode."</span>";
			echo"</div>";
			if($model->issuerEmail){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('form','issuerEmail')."</span>";
				echo "<span class='data'>". $model->issuerEmail."</span>";
				echo"</div>";
			}
			
			echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','awardProcedure')."</span>";
			echo "<span class='data'>". $model->awardProcedure."</span>";
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
			
		?>
		</div>
				<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
		
	</div>
	<div class="contract-content">
	<?php 
	if($ContractData){
		echo"<div class=\"subtitle\"><a name=\"ContractItems\">";
		echo Yii::t('form','Contract Data');
		echo"</a></div>";
		$kk=0;
		$numOfItems = count($ContractData->quantity);
		$num = $numOfItems-1;
		
		echo"<div class='totalcost'>";
		if(isset($ContractData->TotalItemcost[$num])){
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$ContractData->TotalItemcost[$num] =$fm->formatCurrency($ContractData->TotalItemcost[$num],$ContractData->currencyid[$num]);
			echo "". Yii::t('item','TotalItemcost') .": &nbsp;".$ContractData->TotalItemcost[$num]."&nbsp;";
		}
	
		echo"</div>";
		for($i=0;$i<count($ContractData->quantity);$i++){
			$kk=$i+1;
		echo"<div class=\"row\">";
		echo"<div class=\"subcontract\">";
		echo"<div class=\"title\">". Yii::t('form','ContractItem') ."&nbsp;".$kk."</div>";
		echo"</div>";
		if($ContractData->documentUrl[$i]){
			echo"<div class='extendsProc'>";
		    echo "Αφορά το Αίτημα με Μοναδικό Αριθμό<br/>";
		    echo CHtml::link($ContractData->documentUrl[$i],array('procurement/viewproc','documentUrl'=>$ContractData->documentUrl[$i]));  
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
		
		echo "<span class='title'>".Yii::t('item','currency')."</span>";
		echo "<span class='data'>".Lookup::item("Currency",$ContractData->currencyid[$i])."</span>";
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
		if(isset($ContractData->KAE[$i])  && $ContractData->KAE[$i]!=''){
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('item','KAE')."</span>";
			echo "<span class='data'>".$ContractData->KAE[$i] ."</span>";
			echo"</div>";
		}
	
		echo"</div>";
		}
	
		?>
				<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
		<?php 
	}
		?>
	</div>
	
	<!-- Basic Data bar end-->

	<center>
	<div class="download">
		<?php 
		echo CHtml::link(CHtml::encode(Yii::t('form','downloadFile')),array('rfp/rfpdownload','uniqueDocumentCode'=>$model->uniqueDocumentCode));
		?>
	</div>
	</center>

