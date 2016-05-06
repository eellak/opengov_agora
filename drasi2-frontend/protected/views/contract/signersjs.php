<script type="text/javascript">
// initializiation of counters for new elements
var lastSigner=<?php echo $signers->lastNew?>;
 
// the subviews rendered with placeholders
var trSigner=new String(<?php echo CJSON::encode($this->renderPartial('_formSigners', array('id'=>'idRep', 'model'=>new Signers, 'form'=>$form,'Mainaction'=>$Mainaction), true));?>);
 
 
function addSigners(button)
{
    lastSigner++;
    button.parents('table').children('tbody').append(trSigner.replace(/idRep/g,'n'+lastSigner));
}
 
 
function deleteSigners(button)
{
	if(lastSigner>0)
    button.parents('tr').detach();
    lastSigner--;
}
 
</script>
