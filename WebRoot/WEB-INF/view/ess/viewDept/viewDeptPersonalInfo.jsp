<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div style="background-color: #fff;">
	<div class="pageFormContent">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<td class="td_title" width="25%"><!-- 姓名 --> <spring:message code="ess.infoApply.NAME" /></td>
				<td class="td_type" width="25%">${LOCAL_NAME}</td>
				<td class="td_title" width="25%"><!-- 工号 --> <spring:message code="ess.infoApply.EMPID" /></td>
				<td class="td_type" width="25%">${personInfo.EMPID}</td>
			</tr>
			<tr>
				<td class="td_title" width="25%"><!-- 部门 --> <spring:message code="ess.infoApply.DEPT" /></td>
				<td class="td_type" width="25%">
					${personInfo.DEPT_NAME}[${personInfo.DEPTNO}]
				</td>
				<td class="td_title" width="25%"><!-- 职级 --> <spring:message code="ess.infoApply.Rank" /></td>
				<td class="td_type" width="25%">${personInfo.POST_GRADE_NO}</td>
			</tr>
			<tr>
				<td class="td_title" width="25%"><!-- 主要业务 --> <spring:message code="org.title.MAIN_BUSINESS" /></td>
				<td class="td_type" width="25%">${personInfo.MAIN_BUSINESS}</td>
				<td class="td_title" width="25%"><!-- 职责 --> <spring:message code="org.title.POSITION_NO" /></td>
				<td class="td_type" width="25%">${personInfo.POSITION_NAME}</td>
			</tr>
			<tr>
				<td class="td_title" width="25%"><!-- 成本中心 --> <spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL" /></td>
				<td class="td_type" width="25%">${personInfo.COST_CENTER_NAME}</td>
				<td class="td_title" width="25%"><!-- 班组类型 --> <spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
				<td class="td_type" width="25%">${personInfo.SHIFT_NO_NAME}</td>
			</tr>
			<tr>
				<td class="td_title" width="25%"><!-- 员工类型 --> <spring:message code="org.title.EMP_TYPE" /></td>
				<td class="td_type" width="25%">${personInfo.EMP_TYPE_CODE}</td>
				<td class="td_title" width="25%"><!-- 部门长 --> <spring:message code="org.title.MINISTER" /></td>
				<td class="td_type" width="25%">${personInfo.MANAGER_NAME}</td>
			</tr>
			<tr>
				<td class="td_title" width="25%"><!-- 入社日期 --> <spring:message code="display.emp.statistics.mes206" /></td>
				<td class="td_type" width="25%">${personInfo.DATE_STARTED}</td>
				<td class="td_title" width="25%"><!-- 任职状态 --> <spring:message code="ess.infoApply.renzhizhuangtai" /></td>
				<td class="td_type" width="25%">${personInfo.EMP_OFFICE}</td>
			</tr>
		</table>
		<div id="createTable" width="100%"></div>
		<input type="hidden" name="count" id="count" value="1">
	</div>
</div>