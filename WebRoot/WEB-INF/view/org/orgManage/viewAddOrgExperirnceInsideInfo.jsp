<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddOrgExperirnceInsideInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM"/>',
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
		<form id="viewAddOrgExperirnceInsideInfoForm" method="post" action="/org/orgManage/addOrgExperirnceInsideInfo" class="pageForm required-validate" 
			onsubmit="return validateAddOrgExperirnceInsideInfoCallback(this,navTabAjaxDone);">
				<div style="font:bold 12px/20px arial,sans-serif;"><spring:message code="org.title.personBasic" /><!-- 个人基本事项 --></div>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></td>
									<td width="85%" class="td_type" colspan="3">${OrgExperirnceInsideInfo.LOCAL_NAME}</td>
								</tr>
								<tr>
									<td width="15%" class="td_title"><spring:message code="org.title.IS_CONFIRM" /><!-- 确认 --></td>
									<td width="85%" class="td_type" colspan="3"><input type="checkbox" <c:if test="${OrgExperirnceInsideInfo.IS_CONFIRM eq 1}">checked</c:if> disabled/></td>
								</tr>
								<tr>
								
									<td width="15%" class="td_title"><spring:message code="org.title.DATE_STARTED" /><!-- 入职日期 --></td>
									<td width="85%" class="td_type" colspan="3">
										${OrgExperirnceInsideInfo.DATE_STARTED}
									</td>
								</tr>
							</table>
				<div style="font:bold 12px/20px arial,sans-serif;"><spring:message code="org.title.before" /><!-- 以前 --></div>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.dept" /><!-- 部门 -->
									</td>
									<td width="35%" class="td_type">${OrgExperirnceInsideInfo.OLD_DEPTNAME}</td>
									<td width="15%" class="td_title"><spring:message code="org.title.COST_CENTER" /><!-- 成本中心 --></td>
									<td width="35%" class="td_type">${OrgExperirnceInsideInfo.OLD_COST_CENTER_NAME}</td>
								</tr>
								<tr>
									<td width="15%" class="td_title"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职务 --></td>
									<td width="35%" class="td_type">${OrgExperirnceInsideInfo.POST_GRADE_NAME}</td>
									<td width="15%" class="td_title"><spring:message code="org.title.EMP_OFFICE_NAME" /><!-- 员工状态 --></td>
									<td width="35%" class="td_type">${OrgExperirnceInsideInfo.EMP_OFFICE_NAME}</td> 
								</tr>
							</table>
				<div style="font:bold 12px/20px arial,sans-serif;"><spring:message code="org.title.after" /><!-- 后 --></div>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.ORSER_START_DATE" /><!-- 命令日期 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<input type="text" id="START_DATE" name="START_DATE" value="${OrgExperirnceInsideInfo.START_DATE}" readonly="readonly" size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.EXPERIENCE_TYPE_NAME" /><!-- 发令区分 -->
									</td>
									<td width="35%" class="td_type">
									   <ait:SelectSyCodeByCpnyID id="EXPERIENCE_TYPE" name="EXPERIENCE_TYPE" selected="${OrgExperirnceInsideInfo.EXPERIENCE_TYPE}" 
									   		include="400428,400430,400432,400431,400433" parentNo="14013956" limit="all"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.reason" /><!-- 发令原因 -->
									</td>
									<td width="35%" class="td_type">
									   	<input type="text" name="REMARK" value="${OrgExperirnceInsideInfo.REMARK}" size="35"/>
									   	<input type="hidden" name="SEQ" value="${OrgExperirnceInsideInfo.SEQ}" size="35"/>
									</td> 
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.RESUME_NO" /><!-- 发令部门 -->
									</td>
									<td width="35%" class="td_type">
										<c:if test="${OrgExperirnceInsideInfo.RESUME_NO ne null}">
											<ait:resumeDeptList name="DEPTNO" resumeNo="${OrgExperirnceInsideInfo.RESUME_NO}" id="viewAddOrgExperirnceInsideInfo" />
											<ait:resumeDeptTreeIcon name="DEPTNO" resumeNo="${OrgExperirnceInsideInfo.RESUME_NO}" id="viewAddOrgExperirnceInsideInfo" selected="${OrgExperirnceInsideInfo.DEPTNO}"/>
										</c:if>
										<c:if test="${OrgExperirnceInsideInfo.RESUME_NO eq null}">
											<ait:deptList name="DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="super" id="viewAddOrgExperirnceInsideInfo"/>
											<ait:deptTreeIcon name="DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="super" id="viewAddOrgExperirnceInsideInfo"/>
										</c:if>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.COST_CENTER" /><!-- 成本中心 -->
									</td>
									<td width="35%" class="td_type">
										<ait:SelectOrgInfo name="COST_CENTER" orgType="COST_CENTER" selected="${OrgExperirnceInsideInfo.COST_CENTER}"/>
									</td>
								</tr>
							</table>	
</form>	
