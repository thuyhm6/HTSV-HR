<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<table class="user_table"  width="400px;" border="1" cellpadding="2" cellspacing="1" sysLong="${DEPTNO }">
	<tr>
		<td class="tb_title" width="30px" style="text-align:center;">No.</td>
		<td class="tb_title" width="30px" style="text-align:center;"><input type="checkbox" class="checkboxCtrl" group="viewOrgSplitTemp_empid_${FLAG }" /></td>
		<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.EMPID" /><!-- 工号 --></td>
		<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></td>
		<td class="tb_title" width="100px" style="text-align:center;"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职级 --></td>
		<!--<td class="tb_title" width="60px" style="text-align:center;"><spring:message code="org.title.DUTY_NO" /> 岗位 </td>-->
		<td class="tb_title" width="80px" style="text-align:center;"><spring:message code="org.title.EMP_OFFICE_NAME" /><!-- 员工状态 --></td>
	</tr>
	<c:forEach items="${viewOrgSplitTemp}" var="item" varStatus="i">
		<tr onclick="changeColorEmp(this)">
			<td class="tb_type" width="30px"  style="text-align:center;">${i.count}</td>
			<td class="tb_type" width="30px"  style="text-align:center;"><input type="checkbox" name="viewOrgSplitTemp_empid_${FLAG }" value="${item.EMPID}"/></td>
			<td class="tb_type" width="60px"  style="text-align:center;">${item.EMPID}</td>
			<td class="tb_type" width="60px" style="text-align:center;">${item.LOCAL_NAME}</td>
			<td class="tb_type" width="100px" style="text-align:center;">${item.POST_GRADE_NAME}</td>
			<!--<td class="tb_type" width="60px" style="text-align:center;">${item.DUTY_NAME}</td>-->
			<td class="tb_type" width="80px" style="text-align:center;">${item.EMP_OFFICE}</td>
		</tr>
	</c:forEach>
</table>