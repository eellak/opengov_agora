<?php
$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')=>array('indexproc'),
	$model->title,
);

$this->menu=array(

	array('label'=>Yii::t('procurement','Update Procurement'), 
		  'url'=>array('updateproc', 'documentUrl'=>$model->documentUrl),
		  'visible'=>$model->actionPermitted && !Yii::app()->user->isGuest && Yii::app()->user->role!='user' 
		   && $model->cancelled=='false' && $model->isApproved=='') ,
	array('label'=>Yii::t('form','Cancel'),  
		 'url'=>'#', 'visible'=> !Yii::app()->user->isGuest 
		&& $model->cancelled=='false' && Yii::app()->user->role!='user',
		 'linkOptions'=>array('submit'=>array('cancelproc','documentUrl'=>$model->documentUrl))),
	array('label'=>Yii::t('procurement','Delete Procurement'),  
		 'url'=>'#', 'visible'=>$model->actionPermitted && !Yii::app()->user->isGuest 
		&& $model->cancelled=='false'
		 && Yii::app()->user->role!='user',
		 'linkOptions'=>array('submit'=>array('deleteproc','documentUrl'=>$model->documentUrl))),
	array('label'=>Yii::t('procurement','Approve Procurement'),  
		 'url'=>array('approverequest','documentUrl'=>$model->documentUrl),
		  'visible'=> !Yii::app()->user->isGuest && Yii::app()->user->role!='user' && $model->cancelled=='false'
		  && $model->approvesRequest=='' && $model->isApproved=='' && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('procurement','List'), 
		  'url'=>array('indexproc')),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchproc'))
	
	
	);
		$organisationName =  Units::item("Organization", $model->OrganizationIdRef,$model->OrganizationIdRef);
		$this->renderPartial('/commons/TabHeader',
				array('model'=>'procurement','organizations'=>false,'title'=>$model->title,
					'documentUrl'=>$model->documentUrl,'submissionTime'=>$model->submissionTime,
					'lastModifiedTime'=>$model->lastModifiedTime,'relatedADAs'=>'',
					'OrganisationName'=>$organisationName));
		$this->renderPartial('/commons/TabCanceled',
				array('model'=>'procurement','organizations'=>false,'cancelled'=>$model->cancelled,
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
			if(isset($oldData->documentUrl)) echo "&nbsp;<i>($oldData->documentUrl)</i>&nbsp;";
			
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','dateSigned')."</span>";
			echo "<span class='data'>".$model->dateSigned ."</span>";
			if(isset($oldData->dateSigned)) echo "&nbsp;<i>($oldData->dateSigned)</i>&nbsp;";
			
			echo"</div>";
			if(isset($model->fulfilmentDate) && $model->fulfilmentDate!=''){
				echo"<div class='row-grey'>";
				echo "<span class='title'>".Yii::t('procurement','fulfilmentDate')."</span>";
				echo "<span class='data'>".$model->fulfilmentDate ."</span>";
				if(isset($oldData->fulfilmentDate)) echo "&nbsp;<i>($oldData->fulfilmentDate)</i>&nbsp;";
				
				echo"</div>";
			}
			
			if((isset($model->responsibilityAssumptionCode) && $model->responsibilityAssumptionCode!='') ||
				(isset($oldData->responsibilityAssumptionCode) && $oldData->responsibilityAssumptionCode!='')){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('procurement','responsibilityAssumptionCode')."</span>";
				echo "<span class='data'>". $model->responsibilityAssumptionCode."</span>";
				if(isset($oldData->responsibilityAssumptionCode)) echo "&nbsp;<i>($oldData->responsibilityAssumptionCode)</i>&nbsp;";
				
				echo"</div>";
			}
			if((isset($model->protocolNumberCode) && $model->protocolNumberCode!='')){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('procurement','protocolNumberCode')."</span>";
				echo "<span class='data'>". $model->protocolNumberCode."</span>";
				if(isset($oldData->protocolNumberCode)) echo "&nbsp;<i>($oldData->protocolNumberCode)</i>&nbsp;";
				
				echo"</div>";
			}
			if(isset($model->awardProcedure) && $model->awardProcedure!=''){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('contract','awardProcedure')."</span>";
				echo "<span class='data'>". $model->awardProcedure."</span>";
				if(isset($oldData->awardProcedure)) echo "&nbsp;<i>($oldData->awardProcedure)</i>&nbsp;";
				
				echo"</div>";
			}
			
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
		
			if(isset($oldData->OrganizationIdRefUnits) && $oldData->title!='') 
				echo "&nbsp;<i>($oldData->OrganizationIdRefUnits)</i>&nbsp;";
			
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','signers')."</span>";
			echo "<span class='data'>".$model->signers ."</span>";
			if(isset($oldData->signers) && $oldData->title!='') echo "&nbsp;<i>($oldData->signers)</i>&nbsp;";
			
			echo"</div>";
			if($model->issuerEmail){
				echo"<div class='row-grey'>";
				echo "<span class='title'>".Yii::t('form','issuerEmail')."</span>";
				echo "<span class='data'>".str_replace('@','  [AT]  ',$model->issuerEmail) ."</span>";
				if(isset($oldData->issuerEmail)) echo "&nbsp;<i>(".str_replace('@','  [AT]  ',$oldData->issuerEmail).")</i>&nbsp;";
				
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
		
		$numOfItems = count($ContractData->quantity);
		$num = $numOfItems-1;
		
		echo"<div class='totalcost'>";
		if(isset($ContractData->TotalItemcost[$num])){
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$ContractData->TotalItemcost[$num] =$fm->formatCurrency($ContractData->TotalItemcost[$num],$ContractData->currencyid[$num]);
			echo "". Yii::t('item','TotalItemcost') .": &nbsp;".$ContractData->TotalItemcost[$num]."&nbsp;";
		}
		if(isset($ContractItemData->TotalItemcost[$num])){
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$ContractItemData->TotalItemcost[$num] =$fm->formatCurrency($ContractItemData->TotalItemcost[$num],$ContractItemData->currencyid[$num]);
			echo "&nbsp;<i>(".$ContractItemData->TotalItemcost[$num].")</i>&nbsp;";
		}
		echo"</div>";
		for($i=0;$i<count($ContractData->quantity);$i++){
			$kk=$i+1;
		echo"<div class=\"row\">";
		echo"<div class=\"subcontract\">";
		echo"<div class=\"title\">". Yii::t('form','ContractItem') ."&nbsp;".$kk."</div>";
		echo"</div>";
		echo"<div class='row-grey'>";
		echo "<span class='title'>".Yii::t('item','quantity')."</span>";
		echo "<span class='data'>".$ContractData->quantity[$i] ."</span>";
		if(isset($ContractItemData->quantity[$i])) echo "&nbsp;<i>(".$ContractItemData->quantity[$i].")</i>&nbsp;";
		
		echo "<span class='title'>".Yii::t('item','units_of_measure')."</span>";
		echo "<span class='data'>".Lookup::item('units_of_measure',$ContractData->units_of_measure[$i])."</span>";
		if(isset($ContractItemData->units_of_measure[$i])) echo "&nbsp;<i>(". Lookup::item('units_of_measure',$ContractItemData->units_of_measure[$i]) .")</i>&nbsp;";
		
		
		echo "<span class='title'>".Yii::t('item','cost')."</span>";
		$fm = new CNumberFormatter(Yii::app()->getLocale());
		$ContractData->cost[$i] =$fm->formatCurrency($ContractData->cost[$i],$ContractData->currencyid[$i]);
		echo "<span class='data'>".$ContractData->cost[$i] ."</span>";
		if(isset($ContractItemData->cost[$i])){
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$ContractItemData->cost[$i] =$fm->formatCurrency($ContractItemData->cost[$i],$ContractItemData->currencyid[$i]);
			echo "&nbsp;<i>(".$ContractItemData->cost[$i].")</i>&nbsp;";
		}
		
		
		
		echo "<span class='title'>".Yii::t('item','vat')."</span>";
		echo "<span class='data'>". Lookup::item('FPA',$ContractData->vatid[$i]) ."</span>";
		if(isset($ContractItemData->vatid[$i])) echo "&nbsp;<i>(". Lookup::item('FPA',$ContractItemData->vatid[$i]) .")</i>&nbsp;";
		
		echo "<span class='title'>".Yii::t('item','currency')."</span>";
		echo "<span class='data'>".Lookup::item('Currency',$ContractData->currencyid[$i])."</span>";
		if(isset($ContractItemData->currencyid[$i])) echo "&nbsp;<i>(". Lookup::item('Currency',$ContractItemData->currencyid[$i]) .")</i>&nbsp;";
		
		echo"</div>";
		echo"<div class='row-grey'>";
		echo "<span class='title'>".Yii::t('item','description')."</span>";
		echo "<span class='data'>".$ContractData->description[$i] ."</span>";
		if(isset($ContractItemData->description[$i])) echo "&nbsp;<i>(". $ContractItemData->description[$i].")</i>&nbsp;";
		
		echo"</div>";

		if(isset($ContractData->cpvsid[$i])){
		echo"<div class='row-grey'>";
		echo "<span class='title'>".Yii::t('item','CPVS')."</span>";
		echo "<span class='data'>".$ContractData->cpvsid[$i] ."</span>";
		if(isset($ContractItemData->cpvsid[$i])) echo "&nbsp;<i>(". $ContractItemData->cpvsid[$i].")</i>&nbsp;";
		
		echo"</div>";
		}
		if(isset($ContractData->KAE[$i]) && $ContractData->KAE[$i]!=''){
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('item','KAE')."</span>";
			echo "<span class='data'>".$ContractData->KAE[$i] ."</span>";
			if(isset($ContractItemData->KAE[$i])) echo "&nbsp;<i>(". $ContractItemData->KAE[$i].")</i>&nbsp;";
			
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
