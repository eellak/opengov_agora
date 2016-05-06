<?php
/**
 * The cancelation Tab
 */
if($cancelled=='true'){
	echo"<div class=\"contract-content\" style='background-color:#F5A9A9'>";
		echo"<div class=\"data\">";
			echo"<div class=\"subtitle\">";
				echo Lookup::item("cancellationType", $cancellationType);
			echo"</a></div>";
			echo"<div class=\"row\">";
				echo"<div class='row-grey'>";
					echo "<span class='title'>".Yii::t('contract','dateSigned')."</span>";
					echo "<span class='data'>".$cancelledTime ."</span>";
				echo"</div>";
				echo"<div class='row-grey'>";
					echo "<span class='title'>".Yii::t('form','Deldescription')."</span>";
					$reason = explode("---", $cancelledReason);
					echo "<span class='data'>".$reason[0] ."</span>";
				echo"</div>";
				if(isset($reason[1]) && $reason[1]!=$reason[0]){
					echo"<div class='row-grey'>";
						echo "<span class='title'>".Yii::t('form','ADACancel')."</span>";
						echo "<span class='data'>".$reason[1] ."</span>";
					echo"</div>";
				}
			echo"</div>";//end row
		echo"</div>";
		echo"<div align=\"right\"><a href=\"#top\">";
			echo Yii::t('yii','top');
		echo"</a></div>";
	echo"</div>";

}
		
		?>