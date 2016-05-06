<?php $this->pageTitle=Yii::app()->name; 
Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/profile.css');
$this->menu=array(
	array('label'=>Yii::t('procurement','List Procurements'), 'url'=>array('procurement/indexproc'),'visible'=>!Yii::app()->user->isGuest),
	array('label'=>Yii::t('rfp','List'), 'url'=>array('index')),
	array('label'=>Yii::t('contract','List Contracts'), 'url'=>array('contract/index'),'visible'=>!Yii::app()->user->isGuest),
	array('label'=>Yii::t('payments','List Payments'), 'url'=>array('payments/indexpayments'),'visible'=>!Yii::app()->user->isGuest),
	
);
?>


<h1> Προφίλ </h1>

<div id="view_header" class="clearfix user_header">
 <?php 
		$this->renderPartial('stats',array('numOfProcurements'=>$numOfProcurements,
										'numOfContracts'=>$numOfContracts,
										'numOfRfp'=>$numOfRfp,
										'numOfPayments'=>$numOfPayments));  
    ?>
    <img src="http://www.gravatar.com/avatar/a134ad10df00b705d59553cde6c60bc0?s=100&amp;r=G&amp;d=mm" class="user_img_medium" alt="">    
    	<span class="big museo"> <?php echo "Όνομα&nbsp;";?></span>
    	
    <span class="small block">
        <?php echo Yii::app()->user->firstName."&nbsp;".Yii::app()->user->lastName;?>   </span>
    <p>
       <?php echo Yii::app()->user->OrganisationName;?></p>
    <ul id="view_nav">
        <li class="active">
            <a href="#">Στοιχεία</a><div class="arrow"></div>
        </li>
        <li>
            <a href="#">Ενέργειες</a><div class="arrow"></div>
        </li>
    </ul>

   

</div>
		<br/>
		
       <div class="download">
		<?php
		$role =  Yii::app()->user->role;
		if($role!='admin'){ 
			echo CHtml::link(CHtml::encode(Yii::t('procurement','Create Procurement')),array('procurement/createproc'))."&nbsp;";
			echo CHtml::link(CHtml::encode(Yii::t('rfp','Create Rfp')),array('rfp/createrfp'))."&nbsp;";
			echo CHtml::link(CHtml::encode(Yii::t('contract','Create Contract')),array('contract/createcontract'))."&nbsp;";
			echo CHtml::link(CHtml::encode(Yii::t('payments','Create Payment Small')),array('payments/createemptypayment'))."&nbsp;";
		}
			?>
		</div>
       <?php 



//echo Yii::app()->user->authenticated;

?>
<div class="clear20"></div>
<div class="clear20"></div>
<div class="clear20"></div>
<h2>Τα στοιχεία του Φορέα σας!</h2>
<?php 
	if(Yii::app()->user->OrganisationAfm!=''){
		echo "<strong>AFM</strong>=".Yii::app()->user->OrganisationAfm;
		echo"<br/>";
	}
	if(Yii::app()->user->OrganisationFullAddress!=''){
		echo "<strong>Διεύθυνση</strong>=".Yii::app()->user->OrganisationFullAddress;
		echo"<br/>";
	}
	if(Yii::app()->user->OrganisationType!=''){
		echo "<strong>Τύπος</strong>=".Yii::app()->user->OrganisationType;
		echo"<br/>";
	}
	if(Yii::app()->user->email!=''){
		echo "<strong>Email</strong>=".str_replace('@','  [AT]  ',Yii::app()->user->email)."" ;
		echo"<br/>";
	}
?>
<h2>Οι ενέργειες που μπορείτε να κάνετε </h2>
<?php 

	if($role=='user'){
		echo Yii::t('form','usersimple')."<br/><br/>";
		echo Yii::t('form','usersimpleActions');
	}
	else if($role=='admin'){
		echo Yii::t('form','useradmin')."<br/><br/>";
		echo Yii::t('form','useradminActions');
	}else if($role=='admin_foreas'){
		echo Yii::t('form','useradminforea')."<br/><br/>";
		echo Yii::t('form','useradminforeaActions');
	}
?>
