$(document).ready(function() {   
	var formName=$('#contract-form');
   	var numContractors = formName.find('input[id^="numberofcontractors"]').val();
    var inputsContractors = numContractors;

    $('.btnDelContractors:disabled').removeAttr('disabled');
    $('#btnAddContractors').click(function() {
       
        $('.btnDelContractors:disabled').removeAttr('disabled');

		var counterContractors = ++inputsContractors;
      	var c = $('.clonedInputContractors:first').clone();
		c.find('input[id^="Contract_Secname"]').attr("value","");
		c.find('input[id^="Contract_Secafm"]').attr("value","");

		
	       	var formName=$('#contract-form');
	       	formName.find('input[id^="numberofcontractors"]').attr("value",counterContractors);	       	
	       	
   			$('.clonedInputContractors:last').after(c);

   	    	
    });

    $('.btnDelContractors').click(function() {
    	var num    = $('.clonedInputContractors').length;  

			var counterContractors=inputsContractors;
	       	var formName=$('#contract-form');
    	    if(num<2)  {
    		    $('.btnDelContractors').attr('disabled'); 
				$('#btnDelContractors').attr('disabled','disabled');       	    
        	}else{     
        		counterContractors = --inputsContractors;   
    	    	$(this).closest('.clonedInputContractors').remove();
        	}
    	    if(counterContractors==1){	
    	    	$('#btnDelContractors').attr('disabled','disabled');       	    
    	    }

    	    formName.find('input[id^="numberofcontractors"]').attr("value",counterContractors);
    });
    if(inputsContractors<2)	
    $('.btnDelContractors').attr('disabled','disabled');
    
});