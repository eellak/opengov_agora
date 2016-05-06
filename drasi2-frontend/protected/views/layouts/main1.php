<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="en" />
	
	<!-- blueprint CSS framework -->
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/screen.css" media="screen, projection" />
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/print.css" media="print" />
	<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/ie.css" media="screen, projection" />
	<![endif]-->

	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/main.css" />
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/form.css" />
	<link rel="stylesheet" type="text/css" href="<?php echo Yii::app()->request->baseUrl; ?>/css/template.css" />
	
	
	<title><?php echo CHtml::encode($this->pageTitle); ?></title>
<style  type="css">

th.ui-datepicker-week-end,
td.ui-datepicker-week-end {
    display: none;
}
</style>
</head>

<body>


<div class="ext-wrapper"><!-- ends at footer.php -->
	<div class="top"></div>
	<div class="main" >
		<div class="content-container">
			<div class="content">

				<div style="margin-left:60px;">
					<div class='logoTitle'>Δράση 2</div></div>
				<div class="clear"></div>
				
				<div class="header">
					<div class="logo"><a href="index.php">Αγορά</a></div>
						<br/>
							<div style="color: #555;font-size: 12px;margin-top: -12px;padding-left: 630px;font-style:italic;font-weight:bold">						
						 <a href="index.php"><?php echo Yii::t('yii','pilot');?></a>
						 </div>
						<div class="clear"></div>
 					<br/>
 					<?php if( !Yii::app()->user->isGuest ) {?>
					<div class="grid_16">
	                    <div class="log_links">
	                        <div>
								 καλωσήρθατε, <a href="?r=site/userprofile"><?php echo Yii::app()->user->firstName."&nbsp;". Yii::app()->user->lastName;?></a>                         
								 | <a href="?r=site/logout">Έξοδος</a>                        
							 </div>
	                    </div><!-- log_links -->
               		 </div>	<!-- grid_16 -->
					<?php }?>
 				</div><!--  header end -->
				<div class="clear"></div>
	
	
				<div id="mainmenu">
					<?php $this->widget('zii.widgets.CMenu',array(
						'items'=>array(
							array('label'=>Yii::t('yii','Home'), 'url'=>array('/site/index')),
						//	array('label'=>'About', 'url'=>array('/site/page', 'view'=>'about')),
							array('label'=>Yii::t('procurement','Procurements'), 'url'=>array('/procurement/indexproc')),//,'visible'=>!Yii::app()->user->isGuest),
							array('label'=>Yii::t('rfp','RFP'), 'url'=>array('/rfp/index')),//,'visible'=>!Yii::app()->user->isGuest),
							array('label'=>Yii::t('contract','Contracts'), 'url'=>array('/contract/index')),
							array('label'=>Yii::t('payments','Payments'), 'url'=>array('/payments/indexpayments')),
							array('label'=>Yii::t('yii','Training'), 'url'=>array('/site/trainingMaterial')),
							array('label'=>Yii::t('yii','FAQ'), 'url'=>array('/site/faq')),
							array('label'=>Yii::t('yii','Login'), 'url'=>array('/site/login'),'visible'=>Yii::app()->user->isGuest),
							
							),
					)); ?>
				</div><!-- mainmenu -->

				<?php $this->widget('zii.widgets.CBreadcrumbs', array(
					'links'=>$this->breadcrumbs,
				)); ?><!-- breadcrumbs -->
				<div class='TryMain'>
					<?php echo $content;?>
				</div><!-- TryMain -->
	 		</div><!-- content-container end-->
		</div><!-- content end-->
	</div><!-- wrapper end-->
	<div class="clear"></div>
	<!-- footer -->
	<div class="bottom">
		<div class="footer">		<div id="posts">
    			<div id="left"><a href="http://www.europa.eu" target="_blank"><img src="<?php echo Yii::app()->baseUrl;?>/images/eu.jpg" border="0"></a>
  <img src="<?php echo Yii::app()->baseUrl;?>/images/gr.jpg" border="0">
  <a href="http://www.epdm.gr" target="_blank"><img src="<?php echo Yii::app()->baseUrl;?>/images/dioikhtikh.jpg" border="0"></a>
  <a href="http://www.espa.gr" target="_blank"><img src="<?php echo Yii::app()->baseUrl;?>/images/espa.jpg" border="0"></a>
  </div>
    			<div id="right">  <span class="small">Η Δράση 2 -  Πρότυπη Ηλεκτρονική Πλατφόρμα για την παρακολούθηση δημόσιων συμβάσεων, αναπτύχθηκε 
στο πλαίσιο του έργου «Υλοποίηση προτύπων εφαρμογών και παροχή τεχνογνωσίας για την υλοποίηση
δράσεων διαφάνειας και συμμετοχής» στο πλαίσιο του άξονα προτεραιότητας 11 του Ε.Π. «Διοικητική 
Μεταρρύθμιση 2007-2013» . Έργο συγχρηματοδοτούμενο από ΕΥΡΩΠΑΪΚΟ ΚΟΙΝΩΝΙΚΟ ΤΑΜΕΙΟ (ΕΚΤ)</span>
.</span>
 
</div>
    			<div style="clear: both;"></div>
			</div>
		
		</div><!-- footer -->
		<div class="footer-copyright"><img src="<?php echo Yii::app()->baseUrl;?>/images/cc.png" alt="CC Copyright" title="CC Copyright" /> &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<span style="padding-left:160px;width:340px;">&nbsp;</span>
					
   
					
					<?php echo CHtml::link(Yii::t('yii','policy'),array('site/page','view'=>'policy'))."&nbsp;--&nbsp;";?>
					<?php echo "<a href='".Yii::app()->baseUrl."/index.php?r=site/page&amp;view=policy#cookies'>".Yii::t('yii','cookies')."</a>";?>
					<br/>
		</div><!-- footer-copyright -->
	</div><!-- bottom -->
    <!-- footer end-->
  

</div><!-- ext-wrapper ends -->

</body>
</html>