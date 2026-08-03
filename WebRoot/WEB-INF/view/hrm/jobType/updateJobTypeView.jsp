<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback_updateJobTypeview(form, callback) {
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
	<form method="post" action="/hrm/jobType/updateJobTypeInfo" class="pageForm required-validate" onsubmit="return validateCallback_updateJobTypeview(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<input type="hidden" name="NO" value="${jobTypeInfo.JOB_TYPE_SETUP_NO}"/>
			<dl>
				<dt><!-- 公司 --><spring:message code="ar.viewcycleparameter.title.gongsi"/></dt>
				<dd>
					<select name="CPNY_ID">
						<c:forEach items="${companyList}" var="cpny">
							<option value="${cpny.CPNY_ID}" <c:if test="${cpny.CPNY_ID eq jobTypeInfo.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!--人员类型组--><spring:message code="hrm.jobType.title.JOBTYPE_GROUP_NAME" /></dt>
				<dd>
					<select class="combox" name="JOBTYPE_GROUP_NO">
						<c:forEach items="${jobTypeGroupNameList}" var="jobType">
							<option value="${jobType.JOBTYPE_GROUP_NO}" <c:if test="${jobType.JOBTYPE_GROUP_NO eq jobTypeInfo.JOBTYPE_GROUP_NO}">selected</c:if>>${jobType.JOBTYPE_GROUP_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!--人员类型-->
						<spring:message code="is.company.title.PERSON_TYPE" /></dt>
				<dd>
					<select class="combox" name="JOBTYPE_NO">
						<c:forEach items="${jobTypeNameList}" var="jobType">
							<option value="${jobType.JOBTYPE_NO}" <c:if test="${jobType.JOBTYPE_NO eq jobTypeInfo.JOBTYPE_NO}">selected</c:if>>${jobType.JOBTYPE_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 是否活跃 --><spring:message code="ar.viewcycle.title.shifouhuoyue"/></dt>
				<dd>
					<input id="ACTIVITY" type="radio" name="ACTIVITY" value="1" <c:if test="${jobTypeInfo.ACTIVITY eq 1}">checked</c:if>/><!-- 是 --><spring:message code="ar.viewcycle.content.yes"/>
					<input id="ACTIVITY" type="radio" name="ACTIVITY" value="0" <c:if test="${jobTypeInfo.ACTIVITY eq 0}">checked</c:if>/><!-- 否 --><spring:message code="ar.viewcycle.content.no"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
