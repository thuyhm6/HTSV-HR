 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--合同信息表--%>
	<!-- 合同信息表 --><spring:message code="hrm.contractInfo.HETONGXINXIBIAO" />
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=contractInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="7" >
	    			<b><font size="+2"><%--合同信息表--%>
	    		<!-- 未签合同信息表 --><spring:message code="hrm.contractInfo.NO_CONTRACT_NEWS_TABLES" />
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="50">NO
				</th>
				<th><!-- 法人 --><spring:message code="sys.essParam.title.legalPerson"/></th>
				<th width="70"><!-- 社号-->
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</th>
				<th width="60"><!-- 姓名-->
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</th>
				<th width="150"><!-- 部门-->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th width="60"><!-- 职位-->
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
				</th>
				<th width="140"><!-- 职级名称-->
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
				<th width="130"><spring:message code="main.home.message.ruzhiriqi"/><!-- 入职日期 -->
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td style="text-align: center">${item.CPNY_NAME}</td>
					<td>${item.EMPID}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.DEPARTMENT_NAME}</td>
					<td>${item.POSITION_NAME}</td>
					<td>${item.POST_NAME}</td>
					<td>${item.DATE_STARTED}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>