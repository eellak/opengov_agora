<?php $this->pageTitle=Yii::app()->name; 
Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/profile.css');
Yii::app()->clientScript->registerCoreScript('jquery'); 

$this->menu=array(
	array('label'=>'Κάντε την ερώτηση σας!', 
	'url'=>'http://ticketing.ktpae.gr/agora/Default.aspx',
	'linkOptions'=>array('target'=>'_BLANK'), //options to pass to the menu link tag
	)           			
);

?>
<div style="height:200px;">   
<h1> <?php echo Yii::t('yii','RSS Feeds');?> </h1>
<div id="view_header" class="clearfix user_header" style="height:220px;">
    <span class="big museo"> <?php echo "RSS Feeds";?></span>

    <img src="images/rss.jpg" class="user_img_medium" alt="RSS" />        
    <span class="block" style="height:200px;">
        <?php echo"<br/>Το ακρωνύμιο RSS, από τον αγγλικό όρο Really Simple Syndication (Πολύ Απλή Διανομή), αναφέρεται σε μία 
        				προτυποποιημένη μέθοδο ανταλλαγής ψηφιακού πληροφοριακού περιεχομένου διαμέσου του Διαδικτύου, στηριγμένη 
        				στην πρότυπη, καθιερωμένη και ευρέως υποστηριζόμενη γλώσσα σήμανσης XML. Ένας χρήστης του Διαδικτύου μπορεί 
        				έτσι να ενημερώνεται αυτομάτως για γεγονότα και νέα από όσες ιστοσελίδες υποστηρίζουν RSS, αρκεί να έχει 
        				εγγραφεί ο ίδιος συνδρομητής στην αντίστοιχη υπηρεσία της εκάστοτε ιστοσελίδας. Οι εν λόγω ενημερώσεις 
        				(«ροές RSS», αγγλ: «RSS feeds») περιέχουν τα πλήρη δεδομένα, σύνοψη των δεδομένων, σχετικά μεταδεδομένα, 
        				ημερομηνία έκδοσης κλπ, ενώ αποστέλλονται αυτομάτως στον συνδρομητή μέσω Διαδικτύου.";?>  <br/> </span>
  
		 
</div>
<div class="clear20"></div>
<div class="clear20"></div>
<div class="clear20"></div>

<?php 


echo"<div id='content'>";
echo"<div id='first'>";
	echo"<h1><a name='first'>";
	echo Yii::t('yii','RSS Feeds');
	echo"</a></h1>";
?>

	<div class="item">
		<div class="contract" style="float:left; width:650px;">
	        <!-- title bar -->
	        <div class="title">
					Διαθέσιμα κανάλια
			</div>
		 	<img src='images/rss-s.jpeg' align='left'/> &nbsp;  
		 			<?php echo CHtml::link(Yii::t('procurement','Procurements'),array('/organizations/rss2proc',
		 							'org'=>Yii::app()->request->getParam('org')),array('target'=>'_blank'));		
	        		?><br/><br/>
	       <img src='images/rss-s.jpeg' align='left'/> &nbsp;
		 			<?php 
		 				  echo CHtml::link(Yii::t('rfp','RFP'),array('/organizations/rss2rfp',
		 				  			'org'=>Yii::app()->request->getParam('org')),array('target'=>'_blank'));		
	        		?><br/><br/>
	        <img src='images/rss-s.jpeg' align='left'/> &nbsp; 
		 			<?php 
		 				  echo CHtml::link(Yii::t('contract','Contracts'),array('/organizations/rss2contract',
		 				  			'org'=>Yii::app()->request->getParam('org')),array('target'=>'_blank'));		
	        		?><br/><br/>
	         <img src='images/rss-s.jpeg' align='left'/> &nbsp; 
		 			<?php 
		 				  echo CHtml::link(Yii::t('payments','Payments'),array('/organizations/rss2payments',
		 				  					'org'=>Yii::app()->request->getParam('org')),array('target'=>'_blank'));		
	        		?><br/><br/>
		</div><!-- contract end -->
	</div><!-- item end -->
	<div class="item">
		<div class="contract" style="float:left; width:650px;">
	        <!-- title bar -->
	        <div class="title">
				Προγράμματα ανάγνωσης ειδήσεων			</div>
				<p>Υπάρχουν πολλά προγράμματα από τα οποία μπορείτε να επιλέξετε αυτό που σας ταιριάζει περισσότερο. Πιο συγκεκριμένα:</p>
				<ul><li><a href="http://www.deskshare.com/awr.aspx" target="_blank">Active Web Reader</a> - Windows</li><li><a href="http://www.feedreader.com/" target="_blank">FeedReader</a> - Windows</li><li><a href="http://www.cincomsmalltalk.com/BottomFeeder" target="_blank">BottomFeeder</a> - Windows | Mac | Unix | Linux</li><li><a href="http://www.benkazez.com/newsreader.php" target="_blank">NewsReader</a> – Mac</li><li><a href="http://www.newsgator.com/rss-readers.aspx">NewsGator</a>&nbsp;- Windows | Mac | iphone | ipad</li><li><a href="http://www.feedbucket.com/" target="_blank">FeedBucket</a>&nbsp;- Online</li></ul><p>&nbsp;</p>
</div></div>
	        		
	    <?php 
echo"</div>";
?>
</div></div>