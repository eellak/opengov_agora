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
</head>

<body>

<div class="ext-wrapper"><!-- ends at footer.php -->
	<div class="top"></div>
	<div class="main" >
		<div class="content-container">
			<div class="content">

				<div style="margin-left:60px;">
					<div class='logoTitle'>Δράση 2</div></div>
				
				<?php 
					$org = Yii::app()->request->getParam('org');
					$Organizations = new Organizations();
					$org = $Organizations->getOrgId($org);
					$OrgName='';
					if( isset($org) && $org!=''){
					$OrgName = Units::item('OrganizationName',$org,$org);
					if($OrgName!=''){
						$linkName = CHtml::link($OrgName,array('organizations/startprocperorg','org'=>Yii::app()->request->getParam('org')));
						//echo "<div style='clear:both'></div>";
							echo"<div class='foreasName' >";
							echo"<div style='padding:10px;text-align:center;' >";
								echo $linkName;
							echo"</div>";
						echo"</div>";
					}
					}
				
				?>
				</div>
				
				
				<div class="header" style="float:left">
						<div style="color: #555;font-size: 12px;margin-top: -43px;padding-left: 650px;font-style:italic;font-weight:bold">						
						<a href="index.php?r=organizations/indexall"><?php echo Yii::t('yii','pilot');?></a>
						</div>
						<div class="clear"></div> 	
						<br/>			
 				</div><!--  header end -->
				<div class="clear"></div>
	
	
				<div id="mainmenu">
					<?php 
					if( isset($org) && $org!=''){
						$this->widget('zii.widgets.CMenu',array(
						'items'=>array(
							array('label'=>Yii::t('yii','Home'), 'url'=>array('organizations/startprocperorg','org'=>Yii::app()->request->getParam('org'))),
							array('label'=>Yii::t('procurement','Procurements'), 'url'=>array('/organizations/indexprocperorg','org'=>Yii::app()->request->getParam('org'))),//,'visible'=>!Yii::app()->user->isGuest),
							array('label'=>Yii::t('rfp','RFP'), 'url'=>array('/organizations/indexrfpperorg','org'=>Yii::app()->request->getParam('org'))),//,'visible'=>!Yii::app()->user->isGuest),
							array('label'=>Yii::t('contract','Contracts'), 'url'=>array('/organizations/indexcontractsperorg','org'=>Yii::app()->request->getParam('org'))),
							array('label'=>Yii::t('payments','Payments'), 'url'=>array('/organizations/indexpaymentsperorg','org'=>Yii::app()->request->getParam('org'))),
							array('label'=>Yii::t('yii','Login'), 'url'=>array('/site/login'),'visible'=>Yii::app()->user->isGuest),
									
							),
					)); 
					}else{
						$this->widget('zii.widgets.CMenu',array(
						'items'=>array(
							array('label'=>Yii::t('yii','Home'), 'url'=>array('organizations/indexall')),
								
							),
					)); 
					}
					?>
				</div><!-- mainmenu -->

				<?php 
				if(count($this->breadcrumbs)) 
        		$this->widget('zii.widgets.CBreadcrumbs', array(
                	'links'=>$this->breadcrumbs,
        			'homeLink'=>CHtml::link(Yii::t('zii','Home'),'index.php?r=organizations/startprocperorg&org='.Yii::app()->request->getParam('org'))
						        
       			)); 
				/*	$this->widget('zii.widgets.CBreadcrumbs', array(
					'links'=>$this->breadcrumbs,
				)); */?><!-- breadcrumbs -->
				<div class='TryMain'>
			
			
 					<?php
 					$Start =  Yii::app()->request->getParam('r');
 					if ($OrgName!='' || $Start=='organizations/indexall'  
 						|| $Start=='organizations/contactus'
 						|| $Start=='organizations/indexdetails'
 						||  $Start=='organizations/foreisEPOP')
 						echo $content;
 					else  				
 						$this->renderPartial('errorOrg',array('code'=>Yii::t('form','noOrg'),'message'=>Yii::t('form','noOrgmessage')));
 						
 					?>
				</div><!-- TryMain -->
	 		</div><!-- content-container end-->
		</div><!-- content end-->
	</div><!-- wrapper end-->
	<div class="clear"></div>
	<!-- footer -->
	<div class="bottom">
		<div class="footer">
		
			<div id="posts">
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
