 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--闸北店、嘉兴店非转账人员信息--%>
	闸北店、嘉兴店非转账人员信息
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=zhaBeiAndJiaXingEmpInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="45%" border="1" align="center" cellpadding="0" cellspacing="0"
	    		style="padding: 2px 2px 2px 2px;border-collapse:collapse">
				<tr>
					<th width="10%" align="center"><%--工资月--%>
						<spring:message code="ar.viewarprogress.title.gongziyue"/>
					</th>
					<th width="15%" align="center"><%--工号--%>
						<spring:message code="public.title.empId"/>
					</th>
					<th width="20%" align="center"><%--姓名--%>
						姓名
					</th>
					<th width="15%" align="center"><%--工资发放类型--%>
						工资发放类型
					</th>
				</tr>
				<c:forEach items="${sapErrorEmpList}" var="item" varStatus="i">
				<tr>
					<td width="10%" align="center">${item.PA_MONTH }</td>
					<td width="15%" align="center">${item.EMPID }</td>
					<td width="20%" align="center">${item.LOCAL_NAME }</td>
					<td width="15%" align="center">${item.SEND_TYPE }</td>
				</tr>
			</c:forEach>
	      </table>  
		</td>
	  </tr>
	</table>
</body>
</html>