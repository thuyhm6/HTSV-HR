
<script src="/resources/js/jquery/jquery.validate.min.js" type="text/javascript"></script> 
<script src="/resources/js/jquery/jquery.metadata.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/view/inc/initJsMessages.jsp"%>

<script type="text/javascript">
// 

 	$(document).ready(function(){
    	$("form").ligerForm();
        $.metadata.setType("attr", "validate");
        var v = $("form").validate({
            debug: true,
            errorPlacement: function(lable, element) {
                if (element.hasClass("l-textarea")) element.ligerTip({ content: lable.html(), appendIdTo: lable });
                else if (element.hasClass("l-text-field")) element.parent().ligerTip({ content: lable.html(), appendIdTo: lable });
                else lable.appendTo(element.parents("td:first").next("td"));
            },
            success: function(lable) {
                lable.ligerHideTip();
            },
            submitHandler: function() {
                $("form .l-text,.l-textarea").ligerHideTip() ;
                
               try {  
            	   if(typeof(eval(f_save))=="function")  
                   {
            		   f_save();
                   }
               }
               catch(e){
            	   alert(e); 
               }  

            }
        });
    }); 
</script>