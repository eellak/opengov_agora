<?php $this->pageTitle=Yii::app()->name; 
Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/profile.css');
?>


<h1> <?php echo Yii::t('yii','Training');?> </h1>
<div id="view_header" class="clearfix user_header">
    <img src="images/documentss.jpg" class="user_img_medium" alt="Training Material" />    
    <span class="big museo"> <?php echo "Χρήσιμο Υλικό";?></span>
    
    <span class="block">
        <?php echo"<br/>Από εδώ μπορείτε να βρείτε όλο το σχετικό υλικό με το Πρόγραμμα Αγορά. Πιο συγκεκριμένα μπορείτε να βρείτε το υλικό που χρησιμοποιήθηκε στις εκπαιδεύσεις που έχουν πραγματοποιηθεί μέχρι σήμερα.";?>   </span>
  
   <ul id="view_nav">
        <li class="active">
            <a href="#first"><?php echo Yii::t('yii','Training Stuff');?></a><div class="arrow"></div>
        </li>
       
    </ul>
</div>
<div class="clear20"></div>
<div class="clear20"></div>
<div class="clear20"></div>

<?php 

echo"<h1><a name='first'>";
echo Yii::t('yii','Training Stuff');
echo"</a></h1>";
	echo"<h3>Τελικά Manuals</h3>";
	echo"<ul>";
	echo"<li style='height:30px;'><a href='documents/documents/2/Manual-Agora-user-v.2.pdf'><img src='images/pdf.gif' align='left' alt='pdf'/>&nbsp;";
	echo "Manual Χρήστη";
	echo"</a><span class='small'>&nbsp;[1.3 MB]</span></li>";
	echo"<li style='height:30px;'><a href='documents/documents/2/Manual-Agora-v3.pdf'><img src='images/pdf.gif' align='left' alt='pdf'/>&nbsp;";
	echo "Manual Διαχειριστή";
	echo"</a><span class='small'>&nbsp;[1.6 MB]</span></li>";
	echo"<li style='height:30px;'><a href='documents/documents/2/metatrophSePDF.pdf'><img src='images/pdf.gif' align='left' alt='pdf'/>&nbsp;";
	echo "Οδηγίες Μετατροπής σε PDF";
	echo"</a><span class='small'>&nbsp;[161 KB]</span></li>";
	echo"</ul>";
	
	
?>
