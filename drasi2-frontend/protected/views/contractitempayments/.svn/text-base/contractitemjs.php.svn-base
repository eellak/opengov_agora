<script type="text/javascript">
// initializiation of counters for new elements
var lastStudent=<?php echo $contractitems->lastNew?>;
 
// the subviews rendered with placeholders
var trStudent=new String(<?php echo CJSON::encode($this->renderPartial('/contractitempayments/_formContractitem', array('id'=>'idRep', 'model'=>new Contractitempayments(),
												'form'=>$form,'action'=>$action,
												'contractid'=>$contractid
												), true));?>);
 
 
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

function copyItem(button,number)
{
	alert(button);
    lastStudent++;
    button.parents('table').children('tbody').append(trStudent.replace(/idRep/g,'n'+lastStudent));
    var y=document.getElementById('Contractitempayments_'+number+'_description').value;
		document.getElementById('Contractitempayments_n'+lastStudent+'_description').value=y; 
		document.getElementById('Contractitempayments_n'+lastStudent+'_description').title=y; 

		document.getElementById('Contractitempayments_n'+lastStudent+'_description').innerHTML=y; 
   //	var oldNode = document.getElementById(number);
   //	oldNode.replace(/idRep/g,'n'+lastStudent);
   	//var oldNode = button.parents('table').children('tbody').replace(/idRep/g,'n'+lastStudent);
   	//var NewNode = oldNode.cloneNode(true);
   	//NewNode.id='n'+lastStudent;
   	//var table = document.getElementById('Bodystudents');
   	//button.parents('table').children('tbody').append(NewNode);
	//var str = oldNode.innerHTML;
   	//str.replace(/idRep/g,'n'+lastStudent);
	
    }


 
</script>