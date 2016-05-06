<? //Yii::app()->clientScript->registerCoreScript('jquery');?>
<script type="text/javascript">
// initializiation of counters for new elements
var lastStudent=<?php echo $contractitems->lastNew?>;
// the subviews rendered with placeholders
var trStudent=new String(<?php echo CJSON::encode($this->renderPartial('/contractitem/_formContractitem', 
				array('id'=>'idRep', 'model'=>new Contractitem,'Mainaction'=>$Mainaction, 
				'modelname'=>$modelname,
				'form'=>$form,'type'=>$type), true));?>);
 
 
function addStudent(button)
{
    lastStudent++;
    button.parents('table').children('tbody').append(trStudent.replace(/idRep/g,'n'+lastStudent));
}
 
 
function deleteStudent(button)
{
	
	if(lastStudent>0)
    button.parents('tr').detach();
    lastStudent--;
}
 
</script>