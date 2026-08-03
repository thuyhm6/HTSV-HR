<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateCallback_addcompanycalendarview(form, callback) {

	if($("#DDATE_STR").val() == '' ){
		//开始或结束时间不能为空！
		alertMsg.error("<spring:message code='ar.alert.message.viewCompanyCalendar.datenull'/>");
		return false;
	}
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
   $.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});
		
	
	return false;
}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/addStatutoryHolidaysInfo" class="pageForm required-validate" onsubmit="return validateCallback_addcompanycalendarview(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt><!-- 开始日期 --><spring:message code="public.title.startDate"/>: </dt>
				<dd>
					<input id="DDATE_STR" type="text" name="DDATE_STR" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 日期性质--><spring:message code="ar.viewCompanyCalendar.title.riqixingzhi"/>：</dt>
				<dd>
					<select id="type" name="type">
							<option value="1441"><!-- 周末--><spring:message code="ar.viewitemparameter.title.zhoumo"/></option>
							<option value="1442"><!-- 节假日--><spring:message code="ar.viewitemparameter.title.jiejiari"/></option>
						</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 班次--><spring:message code="ar.viewCompanyCalendar.title.banci"/>::</dt>
				<dd>
					<select id="type" name="WORK_SHIFT">
							<option value="147746" <c:if test="${statutoryHoliday.SHIFT_NO eq '147746'}">selected</c:if>><spring:message code="ess.message.rest"/><!-- 休息 --></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!-- 备注 --><spring:message code="ar.viewarcardrecord.title.beizhu"/>:</dt>
				<dd>
					<input id="REMARK" type="text" name="REMARK" size="50"/>
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
