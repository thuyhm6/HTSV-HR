<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	function f_save(){
		var jsonData = '[' ;
	    	//$(":checkbox").each(function (index)
	    	$("input[name='day']:checkbox:checked").each(function()
   	        {
   	            if(this.checked){
   	            	if (jsonData.length > 1){
	   	             	jsonData += ',{'
	   	            }
	   	            else{
	   	             	jsonData += '{'
	   	            }
   	           /// 	jsonData += ' "DDATE_STR": "${request_year}-${request_month}-' + this.value + '", ' ;
   	            	jsonData += ' "DDATE_STR": "'  + this.title +  '", ' ;
   	             	jsonData += ' "SHIFT_NO": "' + $("#WORK_SHIFT").val() + '", ' ;
   	                jsonData += ' "REMARK": "' + $("#REMARK").val() + '", ' ;
   	                jsonData += ' "WORKDAYFLAG": "' + $("#work").val() + '", ' ;
   	                jsonData += ' "CPNY_ID": "${LoginUser.cpnyId}", ' ;
   	             	jsonData += ' "TYPEID": "' + $("#type").val() + '"' ;
   	             	jsonData += '}' ;
   	            }
   	            	
   	        });
	    	jsonData += ']' ;	

	    	if (jsonData.length == 2){
	    		alertMsg.error('<spring:message code="ar.alert.message.viewCompanyCalendar.choosedate"/>');
	    		return ;
	    	}
	    	var year = $("#year").val() ;
			var month = $("#month").val() ;
			$.ajax({
				type: 'POST',
				url:'/ar/attendanceSettings/updateCompanyCalendarInfo?year=' + year + "&month=" + month,
				data:[{ name: 'jsonData', value: jsonData }],
				dataType:"json",
				cache: false,
				success: navTabAjaxDone || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});
	}
</script>
<div class="pageContent">
	<div class="day">
		<div class="day_border">
		<div class="day_left_top">
		</div>
		<div class="day_left_bottom">
		</div>
		<div class="day_right_top">
		</div>
		<div class="day_right_bottom">
		</div>
	<form method="post" action="#" class="pageForm">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="day_nav_table">
		<tr>
			<th class="day_table_left_th">
				<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						${reqest_year}-${request_month}
						<input type="hidden" name="year" id="year" value="${reqest_year}"/>
						<input type="hidden" name="month" id="month" value="${request_month}"/>
					</td>
					<td>
						<!-- 是否工作--><spring:message code="ar.viewCompanyCalendar.title.shifougongzuo"/>:
					</td>
					<td>
						<select id="work" name="work">
							<option value="1"><!-- 工作 --><spring:message code="ar.viewCompanyCalendar.title.work"/></option>
							<option value="0"><!-- 休息 --><spring:message code="ar.viewCompanyCalendar.title.rest"/></option>
						</select>
					</td>
					<td>
						<!-- 日期性质--><spring:message code="ar.viewCompanyCalendar.title.riqixingzhi"/>:
					</td>
					<td>
						<select id="type" name="type">
							<option value="1440"><!-- 平日--><spring:message code="ar.viewitemparameter.title.pingshi"/></option>
							<option value="1441"><!-- 周末--><spring:message code="ar.viewitemparameter.title.zhoumo"/></option>
							<option value="1442"><!-- 节假日--><spring:message code="ar.viewitemparameter.title.jiejiari"/></option>
							<option value="90000425"><!-- 待薪假--><spring:message code="ar.viewComanyCalendar.DAIXINJIA.b"/></option>
						</select>
					</td>
					<td>
					<!-- 班次--><spring:message code="ar.viewCompanyCalendar.title.banci"/>:
					</td>
					<td>
						<select name="WORK_SHIFT" id="WORK_SHIFT" class="combox required">
							<c:forEach items="${shifts}" var="shift">
								<option value="${shift.SHIFT_NO}">${shift.SHIFT_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>
					<!-- Remark--><spring:message code="hrm.empinfo.REMARK"/>:
					</td>
					<td>
						<input type="text" name="REMARK" id="REMARK" size="50"/>
					</td>
				</tr>
				</table>	
			</th>
			<th class="day_table_right_th">
				<table align="right" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td class="day_table_right_line">
					<a class="bottona_a" onclick="f_save()"><span class="icon save_a"></span><span class="bottona_a_r"><!-- 提交 --><spring:message code="public.title.submit"/></span></a>
				</td>
				</tr>
				</table>				
			</th>
			</tr>
		</table>
	
	<table width="100%" border="0"  cellpadding="0" cellspacing="0" class="day_table">			
		<tr>
			<th><b>Sun</b></th>
			<th>Mon</th>
			<th>Tues</th>
			<th>Wed</th>
			<th>Thur</th>
			<th>Fri</th>
			<th><b>Sat</b></th>
		</tr>
		${calendarHtml} 
	</table>
	</form>
	</div>
	</div>
</div>