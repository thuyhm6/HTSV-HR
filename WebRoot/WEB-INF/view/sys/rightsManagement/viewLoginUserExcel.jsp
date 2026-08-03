 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--登陆用户信息表--%>
	HR系统登陆用户信息表
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=hrLoginUserInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="75%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="75%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="8" >
	    			<b><font size="+2"><%--HR系统登陆用户信息表--%>
	    				<!--HR系统登陆用户信息表-->
	    				HR system login in the user information table.
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th><spring:message code="pa.insurance.title.orderNo"/><!--序号--></th>
			    <th><spring:message code="sys.rights.title.employeeType"/><!--用户类型--></th>
				<th><spring:message code="public.title.empId"/><!--工号--></th>
				<th><spring:message code="public.title.name"/><!--姓名--></th>
				<th><spring:message code="public.title.deptName"/><!--部门--></th>
				<th><spring:message code="sys.rights.title.userName"/><!--用户名--></th>
				<th><spring:message code="sys.rights.title.password"/><!--密码--></th>
				<th><spring:message code="sys.rights.title.privilegeGroup"/><!--权限组--></th>
			</tr>
	       	<c:forEach items="${loginUserList}" var="loginUser" varStatus="i">
				<tr>
					<td style="text-align: center">${i.index+1 }</td>
				    <td style="text-align: center">
				    	<c:if test="${loginUser.SPECIAL_PARAM eq 'special'}">
				    		<spring:message code="sys.rights.title.specialEmployee"/><!--特殊用户-->
				    	</c:if>
				    	<c:if test="${loginUser.SPECIAL_PARAM eq 'manager'}">
				    		<spring:message code="sys.rights.title.adminstrator"/><!--管理者-->
				    	</c:if>
				     	<c:if test="${loginUser.SPECIAL_PARAM eq 'general'}">
				     		<spring:message code="sys.rights.title.commonEmployee"/><!--普通用户-->
				     	</c:if>
				    </td>
					<td style="text-align: center">${loginUser.EMPID}</td>
					<td style="text-align: center">${loginUser.LOCAL_NAME}</td>
					<td style="text-align: center">${loginUser.DEPT_NAME_ZH}</td>
					<td style="text-align: center">${loginUser.USER_NAME}</td>
					<td style="text-align: center">${loginUser.PASSWORD}</td>
					<td style="text-align: right">
						<c:forEach items="${loginUser.relationList}" var="relation">
							<c:if test="${relation.CHECKED eq '1'}">${relation.GROUPNAME}</c:if>
						</c:forEach>
					 &nbsp;
					</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>