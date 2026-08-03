<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">


<div class="pageFormContent" layoutH="56">
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="user_table">


	<tr>

		<td class="td_title" width="25%">NAME</td>

		<td class="td_type" width="25%">${LOCAL_NAME}</td>

		<td class="td_title" width="25%">GEN</td>

		<td class="td_type" width="25%">${EMP_ID}</td>
	</tr>
	<tr>

		<td class="td_title" width="25%">主要业务</td>

		<td class="td_type" width="25%">${personInfo.MAIN_BUSINESS}</td>

		<td class="td_title" width="25%">等级</td>

		<td class="td_type" width="25%">${personInfo.POST_GRADE_NO}</td>
	</tr>
	<tr>

		<td class="td_title" width="25%">部门</td>

		<td class="td_type" width="25%">${personInfo.DEPT_NAME}</td>

		<td class="td_title" width="25%">状态</td>

		<td class="td_type" width="25%">${personInfo.EMP_OFFICE}</td>
	</tr>
	<tr>

		<td class="td_title" width="25%">员工类型</td>

		<td class="td_type" width="25%">${personInfo.EMP_TYPE_CODE}</td>

		<td class="td_title" width="25%">部门长</td>

		<td class="td_type" width="25%">${personInfo.HEAD_DEPARTMENT}</td>
	</tr>
	<tr>

		<td class="td_title" width="25%">工作地</td>

		<td class="td_type" width="25%">${personInfo.WORK_AREA}</td>

		<td class="td_title" width="25%">最初入社日</td>

		<td class="td_type" width="25%">${personInfo.TIME_STARTED}</td>
	</tr>
	<tr>

		<td class="td_title" width="25%">联系电话</td>

		<td class="td_type" width="25%"></td>

		<td class="td_title" width="25%">移动电话</td>

		<td class="td_type" width="25%"></td>
	</tr>
	<tr>

		<td class="td_title" width="25%">E-mile</td>

		<td class="td_type" width="25%"></td>

		<td class="td_title" width="25%">EagleM ID</td>

		<td class="td_type" width="25%"></td>
	</tr>

</table>

<div id="createTable" width="100%"></div>

<input type="hidden" name="count" id="count" value="1"></div>


</div>