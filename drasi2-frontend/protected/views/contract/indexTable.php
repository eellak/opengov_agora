<?php

$this->breadcrumbs=array(
	'Contracts',
);

$this->menu=array(
	array('label'=>'Create Contract', 'url'=>array('createcontract')),
	array('label'=>'Manage Contract', 'url'=>array('admin')),
);
?>


<h1>Contracts</h1>

<?php 
//http://www.yiiframework.com/wiki/106/using-cbuttoncolumn-to-customize-buttons-in-cgridview/
if($dataProvider->rawData==-1) 
	echo"No results";
else{
	$this->widget('zii.widgets.grid.CGridView', array(
			'dataProvider' => $dataProvider,
			'columns'=>array(
				'title:html:Τίτλος',          // display the 'title' attribute
			    'since:html:Έναρξη',
				'until:html:Λήξη',
				array(
					    'class'=>'CButtonColumn',
				       'template'=>'{download}{view}{update}{delete}',
				    //   	'download'=>'Yii::app()->createUrl("/contract/viewcontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
				       
						'viewButtonUrl'=>'Yii::app()->createUrl("/contract/viewcontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
						'deleteButtonUrl'=>'Yii::app()->createUrl("/contract/deletecontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
						'updateButtonUrl'=>'Yii::app()->createUrl("/contract/updatecontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
						//'downloadButtonUrl'=>'Yii::app()->createUrl("/contract/updatecontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
				       'buttons'=>array(
				       'download' => array(
				            'imageUrl'=>Yii::app()->request->hostInfo . Yii::app()->request->baseUrl.'/images/pdf.gif',
				            'url'=>'Yii::app()->createUrl("/contract/contractdownload",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
				            ),
				        'view' => array(
				            'imageUrl'=>Yii::app()->request->hostInfo . Yii::app()->request->baseUrl.'/images/view.png',
				            'url'=>'Yii::app()->createUrl("/contract/viewcontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
				            ),
				        'update' => array(
				            'imageUrl'=>Yii::app()->request->hostInfo . Yii::app()->request->baseUrl.'/images/update.png',
				            'url'=>'Yii::app()->createUrl("/contract/updatecontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
				            ),
				        'delete' => array(
				            'imageUrl'=>Yii::app()->request->hostInfo . Yii::app()->request->baseUrl.'/images/delete.png',
				            'url'=>'Yii::app()->createUrl("/contract/deletecontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
				            )        
				            
				       	),//buttons end

		        ),//CButtonColumn

		        )//columns
		    
                ));//end
}

?>
