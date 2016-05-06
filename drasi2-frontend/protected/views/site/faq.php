<?php $this->pageTitle=Yii::app()->name; 
Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/profile.css');
Yii::app()->clientScript->registerCoreScript('jquery'); 

?>
<div>   
<h1> <?php echo Yii::t('yii','FAQ');?> </h1>
<div id="view_header" class="clearfix user_header">
    <img src="images/faq.jpg" class="user_img_medium" alt="Training Material" />    
    <span class="big museo"> <?php echo "Ερωτο - Απαντήσεις";?></span>
    
    <span class="block">
        <?php echo"<br/>Από εδώ μπορείτε να βρείτε έτοιμες ερωτοαπαντήσεις για θέματα που αφορούν το Μητρωό. Μπορείτε να επιλέξετε 
        μία υποκατηγορία ανάλογα με το θέμα που σας αποσχολεί.";?>   </span>
  
		   <ul id="view_nav">
		        <li >
		            <a href="#first" onclick="javascript: $('#first').show(); $('#second').hide();$('#third').hide();$('#fourth').hide();$('#fifth').hide();"><?php echo Yii::t('yii','General');?></a><div class="arrow"></div>
		        </li>
		         <li >
		            <a href="#second" onclick="javascript:$('#first').hide(); $('#second').show();$('#third').hide();$('#fourth').hide();$('#fifth').hide();"><?php echo Yii::t('procurement','Procurements');?></a><div class="arrow"></div>
		        </li>
		         <li>
		            <a href="#third" onclick="javascript:$('#first').hide(); $('#second').hide();$('#third').show();$('#fourth').hide();$('#fifth').hide();"><?php echo Yii::t('rfp','RFP');?></a><div class="arrow"></div>
		        </li>
		        <li>
		            <a href="#fourth" onclick="javascript:$('#first').hide(); $('#second').hide();$('#third').hide();$('#fourth').show();$('#fifth').hide();">
		            	<?php echo Yii::t('contract','Contracts');?></a><div class="arrow"></div>
		            
		        </li>
		       <li>
		            <a href="#fifth" onclick="javascript:$('#first').hide(); $('#second').hide();$('#third').hide();$('#fourth').hide();$('#fifth').show();">
		            	<?php echo Yii::t('payments','Payments');?></a><div class="arrow"></div>
		            
		        </li>
		    </ul>
</div>
<div class="clear20"></div>
<div class="clear20"></div>
<div class="clear20"></div>

<?php 


echo"<div id='content'>";
echo"<div id='first'>";
	echo"<h1><a name='first'>";
	echo Yii::t('yii','General');
	echo"</a></h1>";
?>

	<div class="item">
		<div class="contract" style="float:left; width:650px;">
	        <!-- title bar -->
		 	<div class="title">
				<a onclick="javascript: $('#g1').toggle('slow'); return false;" href="javascript:void(0);" class="open">
					Ποιες είναι οι συνέπειες αν δεν αναρτηθεί μια πράξη;</a>	
			</div>
			<div id="g1" style="display:none">Οι αναρτητέες πράξεις που προβλέπονται από το Νόμο,  
					δεν εκτελούνται εάν δεν έχει προηγηθεί η ανάρτησή τους στον διαδικτυακό τόπο του Μητρώου. 
					Επιπλέον η μη ανάρτηση μιας πράξης επισύρει πειθαρχικές ευθύνες για τους υπόχρεους.
			</div>
		</div><!-- contract end -->
	</div><!-- item end -->
	
<?php 
echo"</div>";
?>
<?php 
echo"<div id='second' style='display:none'>";
	echo"<h1><a name='first'>";
	echo Yii::t('procurement','Procurements');
	echo"</a></h1>";
?>

	<div class="item">
		<div class="contract" style="float:left; width:650px;">
	        <!-- title bar -->
		 	<div class="title">
				<a onclick="javascript: $('#p1').toggle('slow'); return false;" href="javascript:void(0);" class="open">
					Όταν λέμε Αιτήματα τι εννοούμε;</a>	
			</div>
			<div id="p1" style="display:none">
			Όλα τα αιτήματα μιας υπηρεσίας για προμήθεια αγαθών και υπηρεσιών των φορέων του Δημοσίου Τομέα, μαζί με το εκτιμούμενο κόστος. 
			</div>
		</div><!-- contract end -->
	</div><!-- item end -->
	
<?php 
echo"</div>";
?>

<?php 
echo"<div id='third' style='display:none'>";
	echo"<h1><a name='first'>";
	echo Yii::t('rfp','RFP');
	echo"</a></h1>";
?>

	<div class="item">
		<div class="contract" style="float:left; width:650px;">
	        <!-- title bar -->
		 	<div class="title">
				<a onclick="javascript: $('#r1').toggle('slow'); return false;" href="javascript:void(0);" class="open">
					Όταν λέμε Προκηρύξη τι εννοούμε;</a>	
			</div>
			<div id="r1" style="display:none">Όλες οι προκηρύξεις ή προσκλήσεις που αφορούν εγκεκριμένα αιτήματα 
			σε συνδυασμό με την αγωνιστική διαδικασία που θα ακολουθηθεί. 

			</div>
		</div><!-- contract end -->
	</div><!-- item end -->
	
<?php 
echo"</div>";
?>

<?php 
echo"<div id='fourth' style='display:none'>";
	echo"<h1><a name='fourth'>";
	echo Yii::t('contract','Contracts');
	echo"</a></h1>";
?>

	<div class="item">
		<div class="contract" style="float:left; width:650px;">
	        <!-- title bar -->
		 	<div class="title">
				<a onclick="javascript: $('#c1').toggle('slow'); return false;" href="javascript:void(0);" class="open">
					Όταν λέμε Σύμβαση τι εννοούμε;</a>	
			</div>
			<div id="c1" style="display:none">Όλες οι συμβάσεις υπό την έννοια των παραγράφων 2-5 του άρθρου 2 του π.δ. 59/2007 
			(Α΄ 63) και των παραγράφων 2-6 του άρθρου 2 του π.δ. 60/2007 (Α΄ 64), ανεξαρτήτως προϋπολογισμού και διαδικασίας ανάθεσης, 
			μαζί με τα κείμενα της σύμβασης, τα ποσά, τους αναδόχους και πληροφορίες για τον τρόπο επιλογής τους. 
			Όλες οι συμβάσεις καταχωρίζονται υποχρεωτικά με ευθύνη του κατά περίπτωση αρμόδιου οργάνου μετά τη υπογραφή τους, 
			και πάντως πριν την εκτέλεση οποιασδήποτε σχετικής δαπάνης Παράλληλα με την καταχώριση των νέων συμβάσεων, προβλέπεται 
			μεταβατική περίοδος για την απογραφή των ενεργών συμβάσεων και του ανεκτέλεστου υπολοίπου αυτών όλων των φορέων του Δημοσίου..
			</div>
		</div><!-- contract end -->
	</div><!-- item end -->
	
<?php 
echo"</div>";
?>

<?php 
echo"<div id='fifth' style='display:none'>";
	echo"<h1><a name='fifth'>";
	echo Yii::t('payments','Payments');
	echo"</a></h1>";
?>

	<div class="item">
		<div class="contract" style="float:left; width:650px;">
	        <!-- title bar -->
		 	<div class="title">
				<a onclick="javascript: $('#pa1').toggle('slow'); return false;" href="javascript:void(0);" class="open">
					Όταν λέμε Εντολή Πληρωμής τι εννοούμε;</a>	
			</div>
			<div id="pa1" style="display:none">Όλες οι Εντολές Πληρωμών έναντι των καταχωρημένων συμβάσεων. 
			Αφορά τα Εντάλματα Πληρωμής, μαζί με τα στοιχεία των σχετικών τιμολογίων.</div>
		</div><!-- contract end -->
	</div><!-- item end -->
	
<?php 
echo"</div>";
echo"</div>";
?>

</div>
