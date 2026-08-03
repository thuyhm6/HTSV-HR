<script type="text/javascript">
	jQuery.extend(jQuery.validator.messages, {
	    required: '<spring:message code="input.required"/>',
	    email: '<spring:message code="input.email"/>',		
		date: '<spring:message code="input.date"/>',
		number: '<spring:message code="input.number"/>',
		digits: '<spring:message code="input.digits"/>'
		});
	
	function getMessage(a){	
		var temp = new Array(); 
		temp[0]='<spring:message code="tab.close"/>';
		temp[1]='<spring:message code="tab.closeOther"/>';
		temp[2]='<spring:message code="tab.closeAll"/>';
		temp[3]='<spring:message code="tab.reload"/>';	
		
		return temp[a];
	}
</script>