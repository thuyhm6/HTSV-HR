 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--签订合同信息表--%>
	<!-- 签订合同信息表 --><spring:message code="hrm.contractInfo.CONTRACT_NEWS_TABLE" />
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=contractByInsertInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="8" >
	    			<b><font size="+2"><%--签订合同信息表--%>
	    				<!-- 签订合同信息表 --><spring:message code="hrm.contractInfo.CONTRACT_NEWS_TABLE" />
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="70"><!-- 社号-->
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</th>
				<th width="60"><!-- 姓名-->
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</th>
				<th width="150"><!-- 部门-->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th width="80"><!-- 详细人力区分-->
					<%--<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>--%>
					<spring:message code="liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH"/>
				</th>
				<th width="60"><!-- 试用与否-->
					<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/>
				</th>
				<th width="100"><!--预转正日期-->
					<spring:message code="hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE"/>
				</th>
				<th width="100"><!-- 职位-->
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
				</th>
				<th width="100"><!-- 职级名称-->
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td>${item.EMPID}   </td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.DEPARTMENT_NAME}</td>
					<td>${item.EMP_TYPE_CODE }</td>
					<td>${item.IN_THE_DIFFERENCE }</td>
					<td>${item.BEFORE_END_PROBATION_DATE }</td>
					<td>${item.POSITION_NAME}</td>
					<td>${item.POST_NAME}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>