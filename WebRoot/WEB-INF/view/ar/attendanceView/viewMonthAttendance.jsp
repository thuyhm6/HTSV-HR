<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 <script type="text/javascript">
	function validateMonthAttendanceCallback(form,callback){ 
		var $form = $("#form");
		var checked = true ; 
		var dept = $('#deptNO').val(); 
		if(dept=='' ){ 
			alertMsg.error('<spring:message code="hr.alert.message.viewMonthAttendance.checkDepartmentNotNull"/>');	
			 checked = false ; 
			return false; 
		 }
	 if(checked){ 
	    //提交表单 
			$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action") ,
		data:$form.serializeArray(), 
    cache: false ,
    success: function(data){ //请求成功后处理函数。
      $('.pageContent').html('');
		  $('.searchBar').html('');// 
		  $('.pageHeader').html(data);
	 }  , 
		 error: DWZ.ajaxError 
	 }); 
			return false;
		}
	} 
 
</script>
 
<div class="pageHeader"> 
	<form onsubmit="return validateMonthAttendanceCallback(this );" id = "form" action="/ar/attendanceView/viewMonthAttendance" method="post">
		<div class="searchBar">
			<table class="searchContent" >
				<tr>
					<td><!-- 部门 --><spring:message code='public.title.deptName'/>:</td>
					<td>
						<ait:deptTree name="deptNO" limit="ar" selected="${deptNO}" />
					</td>
					<td>
						<!-- 考勤月 --><spring:message code="ar.excelexport.title.armonth"/>
					</td>
					<td>
						<ait:date yearName="arYear" yearSelected="${arYear}" monthName="arMonth" monthSelected="${arMonth}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="button.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent" name = 'pageContent' id = 'pageContent' >${dataTable}</div>