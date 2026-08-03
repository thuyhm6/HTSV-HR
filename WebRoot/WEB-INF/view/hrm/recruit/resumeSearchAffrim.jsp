<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#essAddRewardInfo");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : callback || DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;
}
 

</script>

<div class="pageContent">
<h1><spring:message code="hr.hrm.empinfo.miashiqingkuang"/><!-- 面试情况 --></h1>

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="1" cellpadding="0" cellspacing="0"
				class="orderList">
				
				<tr>

					<th width="40px;" >
						<spring:message code="pa.salary.title.order"/><!-- 顺序 -->
					</th>
					<th width="40px;">
					<!-- 工号 -->
					<spring:message code="display.emp.ben.serviceno" />
					</th>
					<th width="40px;">
						<!-- 姓名 -->
						<spring:message code="inct.salesman.Name" />
					</th>
					
					<th width="60px;">
						<!-- 部门 -->
						<spring:message code="public.title.deptName" />
					</th>
					<th width="45px;">
					<!-- 职级 --><spring:message code="ess.trans.title.postGradeName" />
					</th>
					<th width="45px;">
					<!-- 状态 --><spring:message code="public.title.empStatus" />
					</th>
					<th width="45px;">
					<!-- 面试意见 --><spring:message code="hr.hrm.empinfo.Interview.opinion" />
					</th>
				</tr>
				
				
				
					<c:forEach items="${itemList}" var="resumeAffrimInfo" varStatus="i">
					<tr>
					<td style="text-align: center">
						${i.count}
					</td>
					<td style="text-align: center">
						${resumeAffrimInfo.AFFIRMOR_ID}
					</td>
					<td style="text-align: center">
						${resumeAffrimInfo.LOCAL_NAME}
					</td>
					<td style="text-align: center">
						${resumeAffrimInfo.DEPARTMENT_NAME}
					</td>
					<td style="text-align: center">
						${resumeAffrimInfo.POST_GRADE}
					</td>
					<td style="text-align: center">
					<c:choose>
						<c:when test="${resumeAffrimInfo.AFFIRM_FLAG==0}">
						<spring:message code = "hrm.resumeSearch.weimianshi.k"/>
						</c:when>
						<c:when test="${resumeAffrimInfo.AFFIRM_FLAG==1}">
						<spring:message code = "ess.viewApply.title.pass"/>
						</c:when>
						<c:when test="${resumeAffrimInfo.AFFIRM_FLAG==2}">
						<spring:message code = "ess.viewApply.title.reject"/>
						</c:when>
						</c:choose>
						
					</td>
					<td style="text-align: center">
						${resumeAffrimInfo.AFFIRM_REMARK}
					</td>
					</tr>
					</c:forEach>
				

			</table>

			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		</div>