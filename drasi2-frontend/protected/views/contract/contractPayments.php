<?php

$this->breadcrumbs=array(
	Yii::t('contract','Contracts')=>array('index'),
	$model->title,
);

$this->menu=array(


	array('label'=>Yii::t('contract','Update Contract'), 
	'url'=>array('updatecontract', 'uniqueDocumentCode'=>$model->uniqueDocumentCode),
	'visible'=>$model->actionPermitted && Yii::app()->user->role!='user' ),
	array('label'=>Yii::t('contract','Extend Contract'), 'url'=>array('extendcontract', 
		'uniqueDocumentCode'=>$model->uniqueDocumentCode),'visible'=>!Yii::app()->user->isGuest),
	
	array('label'=>Yii::t('contract','Delete Contract'), 
		 'visible'=>$model->actionPermitted && Yii::app()->user->role!='user' , 
		'url'=>'#', 'linkOptions'=>array('submit'=>array('contract/deletecontract',
		'uniqueDocumentCode'=>$model->uniqueDocumentCode),
		)),
	array('label'=>Yii::t('payments','Create Payment'), 'url'=>array('payments/createpaymentbycontract', 
		'documentUrl'=>$model->uniqueDocumentCode),
		'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('contract','list'), 'url'=>array('index')),
	//array('label'=>Yii::t('contract','Create Contract'), 'url'=>array('createcontract')),

	            			
);

?>

<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $model->uniqueDocumentCode; ?>">
	<?php echo "<div class=\"documentUrl\">#$model->documentUrl</div>";?>
        <!-- title bar -->
	 		<div class="title">
	        	<?php echo $model->title; ?>   
	        </div>
	         <?php  if(Yii::app()->user->isGuest){?>
	        <div class="subtitle">
	        	<?php 
	        	if(!isset(Yii::app()->user->OrganisationName))
	        		echo Units::item("Organization", $model->OrganizationIdRef,$model->OrganizationIdRef);
				else 
					echo Yii::app()->user->OrganisationName;
	        ?>   
	        </div>
	        <?php }?>
	        <!-- Last update bar -->
	         <div class="time">
	        	  <?php 
	        	  		if($model->actionPermitted)
	       		  		echo CHtml::link(Yii::t('contract','Update Contract'),array('contract/updatecontract','uniqueDocumentCode'=>$model->uniqueDocumentCode))." |"; ?>     
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
					 if($model->relatedADAs==1){
		                echo"<a href=\"#ADAs\">::";
	                	echo Yii::t('contract','ADAS');
                    	echo"</a>&nbsp;&nbsp;&nbsp;";
	                }
	                echo"<a href=\"#contractParties\">::";
	                echo Yii::t('contract','contractParties');
	                echo"</a>&nbsp;&nbsp;&nbsp;";
	                echo"<a href=\"#ContractItems\">::";
	                echo Yii::t('form','ContractItems');
	                echo"</a>";
	              	echo"&nbsp;&nbsp;&nbsp;";
	                echo"<a href=\"#Payments\">::";
	                echo Yii::t('payments','Payments');
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

			if($model->extendsContract){
		        echo"<div class='extends'>";
		        echo CHtml::link('Επεκτείνει την Σύμβαση με Μοναδικό αριθμό '.$model->extendsContract.'',array('contract/viewcontract','uniqueDocumentCode'=>$model->extendsContract));     
		        echo"</div>";
	        }
	        if($ContractData->notice[0]){
		      	echo"<div class='extendsProc' style='margin-top:10px;clear:both'>";
			    echo CHtml::link('Αφορά την Προκήρυξη με Μοναδικό αριθμό '.$ContractData->notice[0].'',array('rfp/viewrfp','uniqueDocumentCode'=>$ContractData->notice[0]), array('target'=>'_blank'));     
			    echo"</div>";
	      	}
			if($model->changesContract){
		                echo"<div class='extends'>";
		                echo CHtml::link('Επεκτείνει την Σύμβαση με Μοναδικό αριθμό '.$model->changesContract.'',array('contract/viewcontract','uniqueDocumentCode'=>$model->changesContract));     
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
			echo"<div class='row-grey'>";
			echo "<span class='title'>".Yii::t('contract','since')."</span>";
			echo "<span class='data'>".$model->since ."</span>";
			
			echo "<span class='title'>". Yii::t('contract','until')."</span>";
			echo "<span class='data'>". $model->until."</span>";
			echo"</div>";
			
			echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','protocolNumberCode')."</span>";
			echo "<span class='data'>". $model->protocolNumberCode."</span>";
			echo"</div>";
			if($model->issuerEmail){
				echo"<div class='row-grey'>";
				echo "<span class='title'>". Yii::t('form','issuerEmail')."</span>";
				echo "<span class='data'>". $model->issuerEmail."</span>";
				echo"</div>";
			}
			/*echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','diavgeiaPublished')."</span>";
			echo "<span class='data'>". $model->diavgeiaPublished."</span>";
			echo"</div>";
			*/
			echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','contractingAuthority')."</span>";
			echo "<span class='data'>". $model->contractingAuthority."</span>";
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','awardProcedure')."</span>";
			echo "<span class='data'>". $model->awardProcedure."</span>";
			echo"</div>";
			echo"<div class='row-grey'>";
			echo "<span class='title'>". Yii::t('contract','Contract Type')."</span>";
			echo "<span class='data'>". $model->contractType."</span>";
			echo "<span class='title'>". Yii::t('contract','Commission Criteria')."</span>";
			echo "<span class='data'>". $model->commissionCriteria."</span>";
			echo"</div>";
			echo"</div>";
			
	?>
		</div>
		<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
	</div>
	
	
	<?php 
		if($model->relatedADAs==1){
	?>
	
	 <div class="contract-content">
	<!-- Basic Data bar -->
		<div class="data">
	   	<?php 
	   	echo"<div class=\"subtitle\"><a name=\"ADAs\">";
		echo Yii::t('contract','ADAS');
		echo"</a></div>";
		?>
		</div>
		<?php 
		echo"<div class=\"row\">";
			echo"<div class='row-grey'>";
			if($model->ADAdiakiriksis!=''){
				echo "<span class='title'>".Yii::t('contract','ADAdiakiriksis')."</span>";
				echo "<span class='data'>";
				echo CHtml::link($model->ADAdiakiriksis,'http://et.diavgeia.gov.gr/f/all/ada/'.$model->ADAdiakiriksis,array('target'=>'_blank'))."&nbsp;&nbsp;";     
				echo"</span>";
			}
			if($model->ADAkatakurosis!=''){
				echo "<span class='title'>".Yii::t('contract','ADAkatakurosis')."</span>";
				echo "<span class='data'>";
				echo CHtml::link($model->ADAkatakurosis,'http://et.diavgeia.gov.gr/f/all/ada/'.$model->ADAkatakurosis,array('target'=>'_blank'))."&nbsp;&nbsp;";     
				echo"</span>";
			}
			if( $model->ADAkatakurosis!='' ){
				echo "<span class='title'>". Yii::t('contract','ADAanathesis')."</span>";
				echo "<span class='data'>";
				echo CHtml::link($model->ADAanathesis,'http://et.diavgeia.gov.gr/f/all/ada/'.$model->ADAanathesis,array('target'=>'_blank'))."&nbsp;&nbsp;";     
				echo"</span>";
			}
			if($model->procurements!=''){
				echo "<span class='title'>". Yii::t('procurement','approval')."</span>";
				$procurements = explode(";", $model->procurements);
				echo "<span class='data'>";
				for($j=0;$j<count($procurements) && $procurements[$j]!='';$j++){
					echo CHtml::link($procurements[$j],array('procurement/viewproc','documentUrl'=>$procurements[$j]),array('target'=>'_blank'))."&nbsp;&nbsp;";     
				}
				echo"</span>";
			}
			echo"</div>";
		echo"</div>";	
		?>
		<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
		
		</div>
	<?php } ?>
	
	<?php 
	if((isset($model->PublicFundsCheckBox) && $model->PublicFundsCheckBox=='true')
	|| (isset($model->CofinancedCheckBox) && $model->CofinancedCheckBox=='true'))
	{
	?>
	 <div class="contract-content">
	<!-- Basic Data bar -->
		<div class="data">
		 	<?php 
	   	echo"<div class=\"subtitle\"><a name=\"funds\">";
		echo Yii::t('contract','ContractFunds');
		echo"</a></div>";
		
		
		echo"<div class=\"row\">";
			echo"<div class='row-grey'>";
			if($model->PublicFundsCheckBox!='false'){
				echo "<span class='title'>".Yii::t('contract','PublicFundsSAE')."</span>";
				echo "<span class='data'>";
				echo $model->PublicFundsSAE;
				echo"</span>";
			}
			
			if($model->CofinancedCheckBox!='false'){
				echo "<span class='title'>".Yii::t('contract','CofinancedOPS')."</span>";
				echo "<span class='data'>";
				echo $model->CofinancedOPS;
				echo"</span>";
			}
		echo"</div>";
		echo"</div>";
	?>
		
		</div>
		</div>
	<?php 
	}
	?>
		
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
				echo "<span class='data'>".Lookup::item("country",$SignersData->Seccountry[$sign]) ."</span>";
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
		$kk=$ItemCost=0;
		$numOfItems = count($ContractData->quantity);
		$num = $numOfItems-1;
		if(isset($ContractData->TotalItemcost[$num])){
			$ItemCost = $ContractData->TotalItemcost[$num];
			echo"<div class='totalcost'>";
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$ContractData->TotalItemcost[$num] =$fm->formatCurrency($ContractData->TotalItemcost[$num],$ContractData->currencyid[$num]);
			echo "". Yii::t('item','TotalItemcost') .": &nbsp;".$ContractData->TotalItemcost[$num]."&nbsp;";
			echo"</div>";
		}
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
		
		echo "<span class='title'>".Yii::t('item','cost')."</span>";
		echo "<span class='data'>".$fm->formatCurrency($ContractData->cost[$i],$ContractData->currencyid[$i]) ."</span>";
		
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
	
		echo"<div class='row-grey'>";
		//echo $ContractData->countryProduced[$i];
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
		if(isset($ContractData->city[$i]) && $ContractData->city[$i]!=''){
				echo "<span class='title'>".Yii::t('payments','city')."</span>";
				echo "<span class='data'>".$ContractData->city[$i] ."</span>";
			}
		echo"</div>";
			
		echo"</div>";
		}
		?>
				<div align="right"><a href="#top"><?php echo Yii::t('yii','top');?></a></div>
		
	</div>
	
	
	<div class="contract-content">
	<?php 
		echo"<div class=\"subtitle\"><a name=\"Payments\">";
		echo Yii::t('payments','Payments');
		echo"</a></div>";
		$Payment=0;
		if(count($ContractItemPayments->ContractItemId)>0){
			$numOfItems1 = count($ContractItemPayments->quantity);
			$num1 = $numOfItems1-1;
			if(isset($ContractItemPayments->TotalItemcost[$num1])){
				$Payment = $ContractItemPayments->TotalItemcost[$num1];
				echo"<div class='totalcost'>";
				$fm = new CNumberFormatter(Yii::app()->getLocale());
				$ContractItemPayments->TotalItemcost[$num1] =$fm->formatCurrency($ContractItemPayments->TotalItemcost[$num1],$ContractItemPayments->currencyid[$num1]);
				echo "". Yii::t('item','TotalItempayment') .": &nbsp;".$ContractItemPayments->TotalItemcost[$num1]."&nbsp;";
				echo"</div>";
			}	
		echo"<br/><br/>";
		for($i=0;$i<count($ContractItemPayments->quantity);$i++){
			
		
		echo"<div class=\"row\">";
		if($i<count($ContractItemPayments->ContractItemId)-1)
			if($ContractItemPayments->ContractItemId[$i]==$ContractItemPayments->ContractItemId[$i+1]){
			echo"<div class=\"subcontract\">";
			echo"<div class=\"title\">". Yii::t('payments','Payment') ."&nbsp; (".$ContractItemPayments->dateSigned[$i].")</div>";
			echo"</div>";
		
		}
			
	
		
		echo"<div class='row-grey'>";
		echo "<span class='title'>".Yii::t('item','quantity')."</span>";
		echo "<span class='data'>".$ContractItemPayments->quantity[$i] ."</span>";
		
		echo "<span class='title'>".Yii::t('item','cost')."</span>";
		echo "<span class='data'>".$fm->formatCurrency($ContractItemPayments->cost[$i],$ContractItemPayments->currencyid[$i]) ."</span>";
		
		echo "<span class='title'>".Yii::t('item','vat')."</span>";
		echo "<span class='data'>". Lookup::item('FPA',$ContractItemPayments->vatid[$i]) ."</span>";
		
		echo "<span class='title'>".Yii::t('item','currency')."</span>";
		echo "<span class='data'>".Lookup::item("Currency",$ContractItemPayments->currencyid[$i])."</span>";
		echo "<span class='title'>".Yii::t('item','invoice')."</span>";
		echo "<span class='data'>".$ContractItemPayments->invoice[$i] ."</span>";
		
		echo"</div>";
		echo"<div class='row-grey'>";
		echo "<span class='title'>".Yii::t('item','description')."</span>";
		echo "<span class='data'>".$ContractItemPayments->description[$i] ."</span>";
		echo"</div>";

		echo"</div>";
		
		
		}
		if(isset($ContractItemPayments->TotalItemcost[$num])){
			$Rest=0;
			echo"<div class='totalcost'>";
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$Rest = $ItemCost- $Payment;
			$Rest = $fm->formatCurrency($Rest,$ContractItemPayments->currencyid[$num1]);
			echo "". Yii::t('item','Rest') .": &nbsp;".$Rest."&nbsp;";
			echo"</div>";
		}
		echo"<br/><br/>";
		?>
		<?php }else{
		
			echo"<span class='data'>Δεν έχετε κάνει κάποια πληρωμή</span>";
		}?>
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
