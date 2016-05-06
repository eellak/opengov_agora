<?php 
echo CHtml::link($data['title'],
	array('organizations/startprocperorg','org'=>$data['short']),array('class'=>$data['class'],))."<br/>";

?>