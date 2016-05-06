<?php
$this->pageTitle=Yii::app()->name . ' - About';
$this->breadcrumbs=array(
	'About',
);
echo "isAdmin=";
if(isset(Yii::app()->user->isAdmin))
echo Yii::app()->user->isAdmin;
echo "isGuest =";
echo Yii::app()->user->isGuest;
var_dump(Yii::app()->user->checkAccess('admin')); //returns bool(true) on var_dump

?>
<h1>About</h1>

<p>This is a "static" page. You may change the content of this page
by updating the file <tt><?php echo __FILE__; ?></tt>.</p>