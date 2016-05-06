<?php
$this->breadcrumbs=array(
	Yii::t('procurement','Procurements')
);

$this->menu=array(
	array('label'=>Yii::t('procurement','Create Procurement'), 'url'=>array('createproc'),
			'visible'=>!Yii::app()->user->isGuest && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchproc'))
	
);
?>



<h1><?php echo Yii::t('procurement','Procurements');?></h1>
<div style='float:right;padding-right:20px;'><img src='images/rss-s.jpeg' align='left' alt='Rss Feed'/> &nbsp;  
		 			<?php 
		 				  echo CHtml::link('RSS Feed',array('/procurement/rss2'),array('target'=>'blank'));		
	        		?><br/></div>
	        		<div class='clear'></div>
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
echo"<div class='submenuproc' style='float:left;'>";
echo CHtml::link(Yii::t('procurement','notapproved'), array('procurement/indexproc','search'=>'notapproved','name'=>'type'));
echo"</div>";
echo"<div class='submenuproc' style='float:left; margin-left:20px;margin-right:20px;'>";
echo CHtml::link(Yii::t('procurement','approval'), array('procurement/indexproc','search'=>'approval','name'=>'type'));
echo"</div>";
echo"<div class='submenuproc' style='margin-left:20px;'>";
echo CHtml::link(Yii::t('procurement','approved'), array('procurement/indexproc','search'=>'approved','name'=>'type'));
echo"</div>";


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

 		     
if  ( $dataProvider===null) 	echo Yii::t('zii','No results found');



?>

<div class="search">
	<div class="title">
		<?php echo Yii::t('form','SearchProc'); ?>
	</div>
	<span class="note"><?php echo Yii::t('form','SearchProcDetails'); ?></span><br/>
	<input type="text" id="search" name="search" />
	<input type="button" name="go" id="go" value="<?php echo Yii::t('form','go'); ?>"/>
	<input type="button" name="clear" id="clear" value="<?php echo Yii::t('form','clear'); ?>"/>
</div>
<?php

if(is_array($dataProvider->rawData) && $dataProvider->rawData!=-1 ){	
 
	//$dataProvider->pagination->pageSize = 5;
	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProvider,
        'itemView'=>'_view',
	
));
}else 
	echo Yii::t('zii','No results found');
?>