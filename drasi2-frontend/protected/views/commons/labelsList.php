<?php
 /*
 This is the file used at the list of each model.
 It shows the labels for each item of the model
 a) data: all the necessary data
 b) model: the model name
 c) organizations: if it used at the organizations model or not 
 */
 
if($data['cancelled']=='true'){
	echo"<div class=\"cancelled\">";
	echo Lookup::item('cancellationType', $data['cancellationType']);
	echo"</div>";
}

if($model=='contract'){
	if($data['extendsContract']!=''){
		echo"<div class=\"approved\">";
		if($organizations==1){
			echo CHtml::link(Yii::t('form','extends') . $data['extendsContract'],
			array('organizations/viewcontract','uniqueDocumentCode'=>$data['extendsContract'],'org'=>Yii::app()->request->getParam('org')));
		}else{
			echo CHtml::link(Yii::t('form','extends') . $data['extendsContract'],
			array('contract/viewcontract','uniqueDocumentCode'=>$data['extendsContract']));
		}
		echo"</div>";
	}
	if($data['changesContract']!=''){
		echo"<div class=\"approved\">";
		if($organizations==1){
			echo CHtml::link(Yii::t('form','changes') . $data['changesContract'],
			array('organizations/viewcontract','uniqueDocumentCode'=>$data['changesContract'],'org'=>Yii::app()->request->getParam('org')));
		}else{
			echo CHtml::link(Yii::t('form','changes') . $data['changesContract'],
			array('contract/viewcontract','uniqueDocumentCode'=>$data['changesContract']));
		}
		echo"</div>";
	}
}
if($model=='procurement'){
	if(isset($data['approvesRequest'])&& $data['approvesRequest']!=''){
	   echo"<div class=\"approved\">";
	   if($organizations==1){
	  		echo CHtml::link(Yii::t('form','approves') . $data['approvesRequest'],
	   		array('organizations/viewproc','documentUrl'=>$data['approvesRequest'],'org'=>Yii::app()->request->getParam('org')));
		}else{
	  		echo CHtml::link(Yii::t('form','approves') . $data['approvesRequest'],
	   	 	array('procurement/viewproc','documentUrl'=>$data['approvesRequest']));
		}
	   echo"</div>";
	 }
	        
	 if(isset($data['isApproved']) && $data['isApproved']!=''){
	 	echo"<div class=\"approved\">";
	 	 if($organizations==1){
	  		echo CHtml::link(Yii::t('form','isApproved') . $data['isApproved'],
	   		array('organizations/viewproc','documentUrl'=>$data['isApproved'],'org'=>Yii::app()->request->getParam('org')));
		}else{
	   		echo CHtml::link(Yii::t('form','isApproved') . $data['isApproved'],
	    	array('procurement/viewproc','documentUrl'=>$data['isApproved']));
		}
	    echo"</div>";
	}
}
	        	