$( document ).ready(function() {
	
	var successMessage = $('#alertSuccessMessage').val();
	var failureMessage = $('#alertFailureMessage').val();
	if(failureMessage){
		$.alert({
		    title: 'Sorry !',
		    icon: 'fa fa-ban',
		    content: failureMessage,
		    type: 'red',
		    boxWidth: '35%',
		    useBootstrap: false,
		    typeAnimated: true
		});
	}else if(successMessage){
		$.alert({
    	    title: 'Success !',
    	    icon: 'fa fa-check',
    	    content: successMessage,
    	    type: 'green',
    	    boxWidth: '35%',
    	    useBootstrap: false,
    	    typeAnimated: true
	   });
	}
	
});	
