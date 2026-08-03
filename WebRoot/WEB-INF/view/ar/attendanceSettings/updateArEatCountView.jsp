<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function saveEatCount(form) {
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
		success: navTabAjaxDone || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	return false;
}
</script>
<div class="pageContent">
	<form id="updateArEatCountView" method="post" action="/ar/attendanceSettings/updateArEatCount" 
		class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>工号：</label>
				<input type="text" size="30" value="${eatCountData.EMPID}" disabled="true"/>
			</p>
			<p>
				<label>姓名:</label>
				<input type="text" size="30" value="${eatCountData.LOCAL_NAME}" disabled="true"/>
			</p>
			<p>
				<label>部门:</label>
				<input type="text" size="30" value="${eatCountData.DEPARTMENT}" disabled="true"/>
			</p>
			<p>
				<label>日期:</label>
				<input type="text" size="30" value="${eatCountData.AR_DATE_STR}" disabled="true"/>
			</p>
			<p>
				<label>食堂打卡次数:</label>
				<select name="seach_MEAL_NUM" id="seach_MEAL_NUM">
					<option value="1" <c:if test="${eatCountData.MEAL_NUM eq '1' }"> selected="true"</c:if>>1</option>
					<option value="2" <c:if test="${eatCountData.MEAL_NUM eq '2' }"> selected="true"</c:if>>2</option>
					<option value="3" <c:if test="${eatCountData.MEAL_NUM eq '3' }"> selected="true"</c:if>>3</option>
					<option value="4" <c:if test="${eatCountData.MEAL_NUM eq '4' }"> selected="true"</c:if>>4</option>
				</select>
			</p>
		    <input type="hidden" name="seach_AR_EAT_COUNT_NO" id="seach_AR_EAT_COUNT_NO" value="${eatCountData.AR_EAT_COUNT_NO }">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><button type="submit"><!-- 提交 -->
							<spring:message code="public.title.submit"/></button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><button type="button" class="close"><!-- 取消 -->
							<spring:message code="public.title.cancle"/></button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>	
</div>