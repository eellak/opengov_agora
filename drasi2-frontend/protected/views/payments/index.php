<?php
$this->breadcrumbs=array(
	Yii::t('payments','Payments')
);

$this->menu=array(
	array('label'=>Yii::t('payments','Create Payment Empty'), 'url'=>array('createemptypayment'),
			'visible'=>!Yii::app()->user->isGuest  && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('payments','Create Payment'), 'url'=>array('createpayment'),
			'visible'=>!Yii::app()->user->isGuest  && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('payments','Create Payment Procurement'), 'url'=>array('createpaymentbyproc'),
			'visible'=>!Yii::app()->user->isGuest  && Yii::app()->user->role!='admin'),
	array('label'=>Yii::t('form','SuperSearch'), 'url'=>array('searchpayments'))
		
);
?>

<h1><?php echo Yii::t('payments','Payments');?></h1>
<div style='float:right;padding-right:20px;'><img src='images/rss-s.jpeg' align='left'/> &nbsp;  
		 			<?php
		 				  echo CHtml::link('RSS Feed',array('/payments/rss2'),array('target'=>'blank'));		
	        		?><br/></div>
	        		<div class='clear'></div>
	
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

if(is_array($dataProvider->rawData)){
?>
<div class="search">
	<div class="title">
		<?php echo Yii::t('form','SearchPayments'); ?>
	</div>
	<span class="note"><?php echo Yii::t('form','SearchPaymentsDetails'); ?></span><br/>
	<input type="text" id="search" name="search" />
	<input type="button" name="go" id="go" value="<?php echo Yii::t('form','go'); ?>"/>
	<input type="button" name="clear" id="clear" value="<?php echo Yii::t('form','clear'); ?>"/>
</div>

<?php 
$dataProvider->pagination->pageSize = 5;

	$this->widget('zii.widgets.CListView', array(
        'dataProvider'=>$dataProvider,
        'itemView'=>'_view',	
		'sortableAttributes'=>array(
	      'title',
    	 ),
	
));
}else 
echo Yii::t('zii','No results found');
?>
