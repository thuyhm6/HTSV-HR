<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateCallback_addcompanycalendarview(form, callback) {
    
	if($("#FROM_DATE").val() == '' || $("#TO_DATE").val() == ''){
		//开始或结束时间不能为空！
		alertMsg.error("<spring:message code='ar.alert.message.viewCompanyCalendar.datenull'/>");
		return false;
	}

	var fromDate = $("#FROM_DATE").val();
	var toDate = $('#TO_DATE').val();
    var formatFormDate = fromDate.substring(6,10)+fromDate.substring(3,5)+fromDate.substring(0,2);
    var formatToDate = toDate.substring(6,10)+toDate.substring(3,5)+toDate.substring(0,2);
    
	if (formatFormDate > formatToDate){
		//结束时间应大于等于起始时间
		alert("<spring:message code='ar.alert.message.viewCompanyCalendar.endafterstart'/>");
		return false;
	}
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	//开始日期之后的旧数据将被全部删除,确认操作?
	alertMsg.confirm("<spring:message code='ar.alert.message.viewCompanyCalendar.deleteall'/>", {
		okCall: function(){
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});
		}
	});
	
	return false;
}
</script>
<div class="pageContent">                              
	<form method="post" action="/ar/attendanceSettings/addCompanyCalendarInfoBanCi" class="pageForm required-validate" onsubmit="return validateCallback_addcompanycalendarview(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt style="width:150px;"><!-- 开始日期 --><spring:message code="public.title.startDate"/>: </dt>
				<dd>
					<input id="FROM_DATE" type="text" name="FROM_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
					<!--<input id="FROM_DATE" type="text" name="FROM_DATE" class="date required" readonly="true"/>
					<a class="inputDateButton"> 选择 <spring:message code="public.title.choose"/></a>-->
				</dd>
			</dl>
			<dl>
				<dt style="width:150px;"><!-- 结束日期 --><spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>：</dt>
				<dd>
					<input id="TO_DATE" type="text" name=TO_DATE class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
					<!--<input id="TO_DATE" type="text" name="TO_DATE" class="date required" readonly="true"/>
					<a class="inputDateButton"> 选择 <spring:message code="public.title.choose"/></a>-->
				</dd>
			</dl>
			<dl>
				<dt style="width:150px;"><!-- 选择班次 --><spring:message code="ar.viewClassCalendar.XUANZEBANCI.b"/>:</dt>
				<dd>
				<ait:SelectSyCodeByCpnyID name="GROUP" id="GROUP" parentNo="400223"
							cnpyID="${LoginUser.cpnyId}"  selected="${GROUP}"/>
				</dd>
			</dl>
			<dl>
				<dt style="width:150px;"><!-- 工作状态班次 --><spring:message code="ar.viewCompanyCalendar.title.gongzuozhuangtaibanci"/>:</dt>
				<dd>
					<select name="WORK_SHIFT" class="combox required">
						<c:forEach items="${shifts}" var="shift">
							<option value="${shift.SHIFT_NO}">${shift.SHIFT_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt style="width:150px;"><!-- 非工作状态班次 --><spring:message code="ar.viewCompanyCalendar.title.feigongzuozhuangtaibanci"/>:</dt>
				<dd>
					<select name="REST_SHIFT" class="combox required">
						<c:forEach items="${shifts}" var="shift">
							<option value="${shift.SHIFT_NO}">${shift.SHIFT_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
