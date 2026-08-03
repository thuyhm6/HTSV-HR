 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--工资转账表格--%>
	<spring:message code="rp.report.title.wagetransferform"/>
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=paTranser.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		
    		<tr>
	    		<td align="center" colspan="5" >
	    			<b><font size="+2"><%--工资转账表格--%><spring:message code="rp.report.title.wagetransferform"/></font></b>
	    		</td>
	    	</tr>
	    	<br/>
			<tr>
				<th width="10%" align="center"><%--区分--%>
					<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
				</th>
				<th width="10%" align="center"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				<th width="25%" align="center"><%--金额--%>
					<spring:message code="rp.report.title.amount"/>
				</th>
				<th width="10%" align="center"><%--银行--%>
					<spring:message code="rp.report.title.bankname"/>
				</th>
				<th width="35%" align="center"><%--银行账号--%>
					<spring:message code="rp.report.title.bankcardno"/>
				</th>
			</tr>    
			<c:set value="0" var="sum" /> 
	       	<c:forEach items="${paTranserList}" var="item" varStatus="i">
				<tr target="PERSON_ID" rel="${item.PERSON_ID}">
					<td width="10%" align="center">${i.index + 1}</td>
					<td width="10%" align="center">${item.LOCAL_NAME }</td>
					<td width="25%" align="right"><fmt:formatNumber value="${item.ACTUAL_RELEASE_SALARY }" pattern="#,###.00" type="number"/>
						<c:set value="${sum+item.ACTUAL_RELEASE_SALARY }" var="sum" /> 
					</td>
					<td width="15%" align="center">${item.BANK }</td>
					<td width="35%" align="center" style="vnd.ms-excel.numberformat:@">${item.CARD_NO }</td>
				</tr>
			</c:forEach>
			<tr>
			<td colspan="2" align="center">TOTAL</td>
			<td  align="right"><fmt:formatNumber value="${sum }" pattern="#,###.00" type="number"/></td>
			<td colspan="2" ></td>
			</tr>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>