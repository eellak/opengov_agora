<?php
$this->breadcrumbs=array(
	'Contractitem'=>array('index'),
	'Create',
);

?>

<h1>Create Contractitem</h1>
<?php 
echo $this->renderPartial('_form', array('model'=>$model)); ?>