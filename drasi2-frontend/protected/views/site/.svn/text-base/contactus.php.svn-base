<?php $this->pageTitle=Yii::app()->name; 
Yii::app()->clientScript->registerCssFile(Yii::app()->baseUrl.'/css/profile.css');

?>


<h1> <?php echo Yii::t('form','Support');?> </h1>
<?php 
if(isset(Yii::app()->user->isAdmin) && Yii::app()->user->isAdmin){
?>
<div id="view_header" class="clearfix user_header">
    <img src="http://www.gravatar.com/avatar/a134ad10df00b705d59553cde6c60bc0?s=100&amp;r=G&amp;d=mm" class="user_img_medium" alt="">    
    	<span class="big museo"> <?php echo Yii::app()->user->firstName."&nbsp;".Yii::app()->user->lastName;?></span>
    <span class="small block">
        <?php echo Yii::app()->user->firstName."&nbsp;".Yii::app()->user->lastName;?>   </span>
    <p>
       <?php echo Yii::app()->user->OrganisationName;?></p>
  
</div>
<?php 
$role =  Yii::app()->user->role;
//echo Yii::app()->user->authenticated;
}
?>
<div class="clear20"></div>
<div class="clear20"></div>
<div class="clear20"></div>

