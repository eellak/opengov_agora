// initializiation of counters for new elements
var lastStudent=<?php echo $contractit->lastNew?>;
 
// the subviews rendered with placeholders
var trStudent=new String(<?php echo CJSON::encode($this->renderPartial('_formContractitem', array('id'=>'idRep', 'model'=>new Contractitem, 'form'=>$form), true));?>);
 
 
function addStudent(button)
{
    lastStudent++;
    button.parents('table').children('tbody').append(trStudent.replace(/idRep/g,'n'+lastStudent));
}
 
 
function deleteStudent(button)
{
    button.parents('tr').detach();
}
