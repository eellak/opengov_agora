 <?php 
 /*
 This is the file used at the list of each model.
 It shows the basic data for each item of the model
 a) data: all the necessary data
 b) model: the model name
 c) organizations: if it used at the organizations model or not 
 */
 ?>
 <div class="time">
 	<?php 
	echo"<span class=\"lastUpdated\">";
	echo Yii::t('form','Created');
	echo"</span>";
	echo $data['submissionTime']."&nbsp;&nbsp;"; 
	echo"<span class=\"lastUpdated\">";
	echo Yii::t('form','LastUpdated');
	echo"</span>";
	echo $data['lastModifiedTime'];
	if(isset($data['since'])){
		echo"<span class=\"lastUpdated\">&nbsp;&nbsp;";
 		if($model=='rfp')
			echo Yii::t('rfp','since');
		if($model=='contract')
		    echo Yii::t('contract','since');		                
		echo"</span>";
		echo $data['since']."&nbsp;&nbsp;"; 
	}
	if(isset($data['since'])){
		echo"<span class=\"lastUpdated\">";
		if($model=='rfp')
			echo Yii::t('rfp','until');
		if($model=='contract')
			echo Yii::t('contract','until');
		echo"</span>";
		echo $data['until'];
	 }
	 if(isset($data['totalCostBeforeVAT'])){
		echo"<span class=\"lastUpdated\">&nbsp;&nbsp;";
		echo Yii::t('item','cost');
		echo"</span>";
		$fm = new CNumberFormatter(Yii::app()->getLocale());
		$data['totalCostBeforeVAT'] =$fm->formatCurrency($data['totalCostBeforeVAT'],'€');
		echo $data['totalCostBeforeVAT']."&nbsp;&nbsp;";
	 }
	echo"<br/><br/>";
?>
</div><!-- time -->
