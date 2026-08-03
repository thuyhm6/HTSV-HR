 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--SAP工资报表--%>
	<spring:message code="pa.title.pa.excel.sappainfo"/>
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=sappainfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="${paItemListCnt+2 }" >
	    			<b><font size="+2"><%--XX年XX月  SAP工资报表--%>
	    				${YEAR}<spring:message code="rp.report.title.year"/>${MONTH}<spring:message code="rp.report.title.month"/>&nbsp;&nbsp;
	    				<spring:message code="pa.title.pa.excel.sappainfo"/></font>
	    			</b>
	    		</td>
	    	</tr>
	    	<br/>
			<tr>
				<th style="text-align: center" nowrap="nowrap"><%--部门--%>
					<spring:message code="public.title.deptName"/>
				</th>
				<th style="text-align: center" nowrap="nowrap"><%--人数--%>
					<spring:message code="pa.title.pa.excel.thecountnumberofemployee"/>
				</th>
				<c:forEach items="${paItemList}" var="paitem" varStatus="i">
					<th style="text-align: center" nowrap="nowrap">${paitem.ITEM }</th>
				</c:forEach>
			</tr>
			<c:forEach items="${departList}" var="depart" varStatus="j">
				<tr>
					<td style="text-align: center" nowrap="nowrap">${depart.DEPARTMENT}</td>
					<td style="text-align: center" nowrap="nowrap">
						<c:forEach items="${hrmCountList}" var="hrm" varStatus="y">
							<c:if test="${depart.DEPTNO eq hrm.DEPTNO }">
								<c:forEach items="${hrm.empTypeHrmList}" var="hrmCount" varStatus="x">
									<dt>${hrmCount.EMP_TYPE_NAME }:${hrmCount.PERSONCNT }</dt>
								</c:forEach>
							</c:if>
						</c:forEach>
					</td>						
					<c:forEach items="${paItemList}" var="head" varStatus="n">
						<td style="text-align: center" nowrap="nowrap">
							<c:forEach items="${sapPaList}" var="pa" varStatus="m">
								<c:if test="${pa.DEPTNO eq depart.DEPTNO && pa.ITEM_ID eq head.FIELD_ID}">
									${pa.ITEM_VALUE }
								</c:if>
							</c:forEach>&nbsp;
						</td>
					</c:forEach>							
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>