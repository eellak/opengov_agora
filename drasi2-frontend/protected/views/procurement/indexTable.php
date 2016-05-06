<?php
$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')
);

$this->menu=array(
	array('label'=>'Create Procurement', 'url'=>array('createproc')),
);
?>

<h1><?php echo Yii::t('procurement','Procurements');?></h1>

<?php if(Yii::app()->user->hasFlash('updateSuccess')): ?>
<?php echo Yii::app()->user->getFlash('updateSuccess'); ?>
<?php endif; ?>
<?php if(Yii::app()->user->hasFlash('deleteSuccess')): ?>
<?php echo Yii::app()->user->getFlash('deleteSuccess'); ?>
<?php endif; ?>


<div id="#flashSuccess" style="display:none;"></div>

<script>
    function setFlash(type, message)
{
    alert(message);
    $('#flashSuccess').html(message);
    $('#flashSuccess').fadeIn();
}
</script>

<?php
if($dataProvider->rawData==-1) 
	echo"No results";
else{
$this->widget('zii.widgets.grid.CGridView', array(
			'dataProvider' => $dataProvider,
			//'afterAjaxUpdate'=>'function(id, data){ $("#info").html(data);}',
			//'afterAjaxUpdate'=>"function setFlash(id, data) {
		//		$('.flash-error').html(data.message).fadeIn().animate({opacity: 1.0}, 3000).fadeOut('slow');
		//	}",
		//	'afterDelete'=>'function(link,success){ if(success) alert("Delete completed successfuly"); }',

			'columns'=>array(
       			 'title:html:'.Yii::t('procurement','title'),          // display the 'title' attribute
				 'dateSigned:html:'.Yii::t('procurement','dateSigned'),
				 'description:html:'.Yii::t('item','descriptionS'),  // display the 'name' attribute of the 'category' relation
				 'costBeforeVat:html:'.Yii::t('item','cost'),
					array(
						'class'=>'CButtonColumn',
				       'template'=>'{download}{view}{update}{delete}',
				    //   	'download'=>'Yii::app()->createUrl("/contract/viewcontract",array("uniqueDocumentCode"=>$data["uniqueDocumentCode"]))',
				       
						'viewButtonUrl'=>'Yii::app()->createUrl("/procurement/viewproc",array("documentUrl"=>$data["documentUrl"]))',
						//'deleteButtonUrl'=>'Yii::app()->createUrl("/procurement/deleteproc",array("documentUrl"=>$data["documentUrl"]))',
						'updateButtonUrl'=>'Yii::app()->createUrl("/procurement/updateproc",array("documentUrl"=>$data["documentUrl"]))',
						//'downloadButtonUrl'=>'Yii::app()->createUrl("/contract/updatecontract",array("documentUrl"=>$data["documentUrl"]))',
				       'buttons'=>array(
				       'download' => array(
				            'imageUrl'=>Yii::app()->request->hostInfo . Yii::app()->request->baseUrl.'/images/pdf.gif',
				            'url'=>'Yii::app()->createUrl("/procurement/downloadproc",array("documentUrl"=>$data["documentUrl"]))',
				            ),
				            'view' => array(
				            'imageUrl'=>Yii::app()->request->hostInfo . Yii::app()->request->baseUrl.'/images/view.png',
				            'url'=>'Yii::app()->createUrl("/procurement/viewproc",array("documentUrl"=>$data["documentUrl"]))',
				            ),
				         'update' => array(
				            'imageUrl'=>Yii::app()->request->hostInfo . Yii::app()->request->baseUrl.'/images/update.png',
				            'url'=>'Yii::app()->createUrl("/procurement/updateproc",array("documentUrl"=>$data["documentUrl"]))',
				            ),
				         'delete' => array(
				            'label'=>'delete the procurement',
				            'imageUrl'=>Yii::app()->request->hostInfo . Yii::app()->request->baseUrl.'/images/delete.png',
				            'url'=>'Yii::app()->createUrl("/procurement/deleteproc",array("documentUrl"=>$data["documentUrl"]))',
							//'click'=>'function(link,success){ if(success) alert("Delete completed successfuly");',
							'deleteConfirmation'=>"js:'Do you really want to delete record with ID '+$(this).parent().parent().children(':nth-child(2)').text()+'?'",
				            
				            )        
				            
				       ),

		        ),

		    )));
}

?>

