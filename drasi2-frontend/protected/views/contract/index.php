<?php

$this->breadcrumbs=array(
	Yii::t('contract','Contracts'),
);

$this->menu=array(
	array('label'=>Yii::t('contract','Create Contract'), 'url'=>array('createcontract'),
		'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('contract','Create Contract by procs'),
		 'url'=>array('createContractByProc'),
		 'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('contract','Create Contract by notice'),
		 'url'=>array('createContractByNotice'),
		 'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchcontract'))
		
);
?>


<h1><?php echo Yii::t('contract','List Contracts');?></h1>
<div style='float:right;padding-right:20px;'><img src='images/rss-s.jpeg' align='left'/> &nbsp; 
		 			<?php 
		 				  echo CHtml::link('RSS Feed',array('/contract/rss2'),array('target'=>'blank'));		
	        		?><br/></div>
	        		<div class='clear'></div>


<?php 
//http://www.yiiframework.com/forum/index.php?/topic/18391-ajax-search-in-clistview/
?>
<?php if(Yii::app()->user->hasFlash('deleteSuccess')): ?>
   <div class="flash">
	<div class="flash-success">
	<?php echo Yii::app()->user->getFlash('deleteSuccess'); ?>
</div>
</div>
<?php endif; ?>
<?php if(Yii::app()->user->hasFlash('deleteFail')): ?>
   <div class="flash">

    <div class="flash-error">
	<?php echo Yii::app()->user->getFlash('deleteFail'); ?>
</div>
</div>

	<?php endif; ?>


<?php Yii::app()->clientScript->registerCoreScript('jquery'); ?>

<?php

Yii::app()->clientScript->registerScript(
   'myHideEffect',
   '$(".flash").animate({opacity: 1.0}, 3000).fadeOut("slow");',
   CClientScript::POS_READY
);
?>

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

<div class="search">
	<div class="title">
		<?php echo Yii::t('form','SearchContract'); ?>
	</div>
	<span class="note"><?php echo Yii::t('form','SearchContractDetails'); ?></span><br/>
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

