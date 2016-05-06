$(document).ready(function() {   
	var formName=$('#contract-form');
   	var numsubs = formName.find('input[id^="numberofsubs"]').val();
    var inputssubs = numsubs;

    $('.btnDel:disabled').removeAttr('disabled');
    $('#btnAdd').click(function() {
       
        $('.btnDel:disabled').removeAttr('disabled');

		var countersubs = ++inputssubs;
      	var c = $('.clonedInput:first').clone();


		
	       	var formName=$('#contract-form');
	       	formName.find('input[id^="numberofsubs"]').attr("value",countersubs);	       	
	       	
   			$('.clonedInput:last').after(c);
    });

$('.btnDel').click(function() {
    	var num    = $('.clonedInput').length;  
    	var countersubs=inputssubs;
	   	var formName=$('#contract-form');
	    if(num<2)  {
		    $('.btnDel').attr('disabled'); 
			$('#btnDel').attr('disabled','disabled');       	    
		}else{     
			countersubs = --inputssubs;   
	    	$(this).closest('.clonedInput').remove();
		}
	    if(countersubs==1){	
	    	$('#btnDel').attr('disabled','disabled');       	    
	    }
	
	    formName.find('input[id^="numberofsubs"]').attr("value",countersubs);
	});
	if(inputssubs<2)	
	$('.btnDel').attr('disabled','disabled');
    
 
	
});
