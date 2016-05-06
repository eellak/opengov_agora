<?php

$this->breadcrumbs=array(
	Yii::t('rfp','RFP'),
);

$this->menu=array(
	array('label'=>Yii::t('rfp','Create Rfp'), 'url'=>array('createrfp'),
		'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchrfp'),
	),
);
?>


<h1><?php echo Yii::t('rfp','List RFP');?></h1>
<div style='float:right;padding-right:20px;'><img src='images/rss-s.jpeg' align='left'/> &nbsp;  
		 			<?php 
		 				  echo CHtml::link('RSS Feed',array('/rfp/rss2'),array('target'=>'blank'));		
	        		?><br/></div>
	        		<div class='clear'></div>
<?php 
//http://www.yiiframework.com/forum/index.php?/topic/18391-ajax-search-in-clistview/
?>

<?php Yii::app()->clientScript->registerCoreScript('jquery'); ?>

<?php 
Yii::app()->clientScript->registerScript('search',
 "var ajaxUpdateTimeout;
    var ajaxRequest;
    $('input#go').click(function(){
     	ajaxRequest = $('input#search').serialize();
            $.fn.yiiListView.update(
                'yw0',
                {data: ajaxRequest}
            )
        
    });
    
    $('input#clear').click(function(){
     	$('input#search').val('');
    	ajaxRequest = $('input#search').serialize();
        
            $.fn.yiiListView.update(
                'yw0',
                {data: ajaxRequest}
            )
    });
    "
);


?>

<div class="search" id="1">
	<div class="title">
		<?php echo Yii::t('form','SearchRfp'); ?>
	</div>
	<span class="note"><?php echo Yii::t('form','SearchRfpDetails'); ?></span><br/>
	<input type="text" id="search" name="search" />
	<input type="button" name="go" id="go" value="<?php echo Yii::t('form','go'); ?>"/>
	<input type="button" name="clear" id="clear" value="<?php echo Yii::t('form','clear'); ?>"/>
</div>
	
<?php 
if(is_array($dataProvider->rawData)){

	$dataProvider->pagination->pageSize = 5;

	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProvider,
        'itemView'=>'_view',
));
}else 
echo Yii::t('zii','No results found');

?>

