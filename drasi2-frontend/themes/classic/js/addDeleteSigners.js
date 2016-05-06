$(document).ready(function() {   
	var formName=$('#contract-form');
   	var numSigners = formName.find('input[id^="numberofSigners"]').val();
    var inputsSigners = numSigners;
    $('.btnDelSigners:disabled').removeAttr('disabled');
    $('#btnAddSigners').click(function() {
       
        $('.btnDelSigners:disabled').removeAttr('disabled');

		var counterSigners = ++inputsSigners;
      	var c = $('.clonedInputSigners:first').clone(true,true);
	      
	       	var formName=$('#formID');
	       	formName.find('input[id^="numberofSigners"]').attr("value",counterSigners);	       	
	       	
   			$('.clonedInputSigners:last').after(c);
   			jQuery("#formID").validationEngine();

   	    	
    });

    $('.btnDelSigners').click(function() {
    	var num    = $('.clonedInputSigners').length;  

			var counterSigners=inputsSigners;
	       	var formName=$('#contract-form');
    	    if(num<2)  {
    		    $('.btnDelSigners').attr('disabled'); 
				$('#btnDelSigners').attr('disabled','disabled');       	    
        	}else{     
        		counterSigners = --inputsSigners;   
    	    	$(this).closest('.clonedInputSigners').remove();
        	}
    	    if(counterSigners==1){	
    	    	$('#btnDelSigners').attr('disabled','disabled');       	    
    	    }

    });
    if(inputsSigners<2)	
    	$('.btnDelSigners').attr('disabled','disabled');
    
});