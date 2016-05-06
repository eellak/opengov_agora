<?php
$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')
);

$this->menu=array(
	array('label'=>Yii::t('rfp','List'), 'url'=>array('indexrfpperorg','org'=>Yii::app()->request->getParam('org'))),
	array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('indexcontractsperorg','org'=>Yii::app()->request->getParam('org'))),
	array('label'=>Yii::t('payments','List Payments'), 'url'=>array('indexpaymentsperorg','org'=>Yii::app()->request->getParam('org'))),
);
?>

<h1>
<?php echo Yii::t('procurement','Procurements');?>
</h1>
	<div style='float:right;padding-right:20px;'><img src='images/rss-s.jpeg' align='left'/> &nbsp; 
	<?php echo CHtml::link('RSS feed',array('/organizations/rss2proc','org'=>Yii::app()->request->getParam('org')),array('target'=>'blank'));?><br/></div>
	<div class='clear'></div>
<?php Yii::app()->clientScript->registerCoreScript('jquery'); ?>
<?php 
echo"<div class='submenuproc' style='float:left;'>";
echo CHtml::link(Yii::t('procurement','notapproved'), array('organizations/indexprocperorg','search'=>'notapproved','name'=>'type','org'=>$org));
echo"</div>";
echo"<div class='submenuproc' style='float:left; margin-left:20px;margin-right:20px;'>";
echo CHtml::link(Yii::t('procurement','approval'), array('organizations/indexprocperorg','search'=>'approval','name'=>'type','org'=>$org));
echo"</div>";
echo"<div class='submenuproc' style='margin-left:20px;'>";
echo CHtml::link(Yii::t('procurement','approved'), array('organizations/indexprocperorg','search'=>'approved','name'=>'type','org'=>$org));
echo"</div>";

if(is_array($dataProvider->rawData)){
	$dataProvider->pagination->pageSize = 5;

	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProvider,
        'itemView'=>'_view',
	
));
}else 
echo Yii::t('zii','No results found');
?>