<?php

$this->breadcrumbs=array(
	Yii::t('rfp','RFP'),
);

$this->menu=array(
	array('label'=>Yii::t('procurement','List Procurements'), 'url'=>array('indexprocperorg','org'=>Yii::app()->request->getParam('org'))),
	array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('indexcontractsperorg','org'=>Yii::app()->request->getParam('org'))),
	array('label'=>Yii::t('payments','List Payments'), 'url'=>array('indexpaymentsperorg','org'=>Yii::app()->request->getParam('org'))),
);
?>
<h1>
<?php echo Yii::t('rfp','List RFP');?>
</h1>
<div style='float:right;padding-right:20px;'><img src='images/rss-s.jpeg' align='left'/> &nbsp; 
		 <?php echo CHtml::link('RSS feed',array('/organizations/rss2rfp','org'=>Yii::app()->request->getParam('org')),array('target'=>'blank'));?><br/></div>
	        <div class='clear'></div>
<?php 

if(is_array($dataProvider->rawData)){
echo"<div id='arxes'>";
	
	$dataProvider->pagination->pageSize = 5;
	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProvider,
        'itemView'=>'_viewrfp',
));
echo"</div>";
}else 
echo Yii::t('zii','No results found');

?>

