 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--续签合同信息表--%>
	<!-- 续签合同信息表 --><spring:message code="hrm.contractInfo.ADD_CONTRACT_NEWS_TABLE" />
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=expiredContract.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="11" >
	    			<b><font size="+2"><%--合同信息表--%>
	    	<!-- 续签合同信息表 --><spring:message code="hrm.contractInfo.ADD_CONTRACT_NEWS_TABLE" />
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="50"><!--契约次数-->
					<spring:message code="hr.viewPersonalInfo.title.TOTAL_PERIOD"/>
				</th>
				<th width="70"><!-- 社号-->
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</th>
				<th width="60"><!-- 姓名-->
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</th>
				<th width="150"><!-- 部门-->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th width="80"><!-- 员工状态-->
					<%--<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>--%>
					<spring:message code="liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH"/>
				</th>
				
				<th width="60"><!-- 职位-->
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
				</th>
				<th width="140"><!-- 职级名称-->
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
				<th width="130"><!--合同种类-->
					<%--<spring:message code="hr.viewPersonalInfo.title.CONTRACT_NAME"/>--%>
					<spring:message code="hr.viewContract.title.CONTRACT_TYPE_NAME"/>
				</th>
				<th width="120"><!--合同开始时间:-->
					<%--<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>--%>
					<spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/>
				</th>
				<th width="120"><!--合同结束时间:-->
					<%--<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>--%>
					<spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/>
				</th>
				<th width="100"><!--描述:-->
					<spring:message code="hr.viewPromote.title.REMARK"/>
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td>${item.TOTAL_PERIOD}</td>
					<td>${item.EMPID}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.DEPARTMENT_NAME}</td>
					<td>${item.EMP_TYPE_CODE}</td>
					<td>${item.POSITION_NAME}</td>
					<td>${item.POST_NAME}</td>
					<td>${item.CONTRACT_TYPE }</td>
					<td>${item.CONTRACTSTARTDATE}</td>
					<td>${item.CONTRACTENDDATE}</td>
					<td>${item.REMARK}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>