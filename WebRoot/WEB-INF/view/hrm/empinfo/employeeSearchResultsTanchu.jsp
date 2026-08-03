<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	$.pdialog.reload('/hrm/empinfo/employeeSearchResults', {data: $('#employeeSearchResults').serializeArray()});
});
</script>

<div  class="pageContent" style="overflow-x: auto;overflow-y: auto">
           
</div>
