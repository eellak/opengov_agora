<div class="item">
	<a name="top"></a>
	<div class="contract" id="c<?php echo $documentUrl; ?>">
		<?php
		echo "<div class=\"documentUrl\">#$documentUrl</div>";
		echo"<div class=\"title\">";
			echo $title;   
		echo"</div>\n";
		 if(Yii::app()->user->isGuest || Yii::app()->user->role=='admin'){
		 	echo"<div class=\"subtitle\">";
		    echo $OrganisationName; 
		    echo"</div>";
		 }
		 
		 echo"<div class=\"time\">";
		    echo"<span class=\"lastUpdated\">";
	        echo Yii::t('form','Created');
		    echo"</span>";
		    echo $submissionTime."&nbsp;&nbsp;"; 
		    echo"<span class=\"lastUpdated\">";
		    echo Yii::t('form','LastUpdated');
		    echo"</span>";
		    echo $lastModifiedTime;
		    echo"<br/><br/>";
		    echo"<a href=\"#BasicData\">:: ";
			echo Yii::t('form','Basic Data');
			echo"</a>&nbsp;&nbsp;&nbsp;";
			if(isset($relatedADAs) && $relatedADAs==1){
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
		echo"</div>\n";
	?>
	</div><!-- end contract div -->
</div><!-- end item div -->