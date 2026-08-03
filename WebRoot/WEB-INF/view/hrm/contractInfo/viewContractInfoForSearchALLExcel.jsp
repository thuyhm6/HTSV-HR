 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--合同信息表--%>
	<!-- 合同信息表 --><spring:message code="hrm.contractInfo.HETONGXINXIBIAO"/>
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
	    		<td align="center" colspan="9" >
	    			<b><font size="+2"><%--合同信息表--%>
	    				<!-- 到期合同信息表 --><spring:message code="hrm.contractInfo.EXPIRE_CONTRACT_NEWS_TABLE" />
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
				<th width="120"><!--合同开始时间:-->
					<%--<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>--%>
					<spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/>
				</th>
				<th width="120"><!--合同结束时间:-->
					<%--<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>--%>
					<spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/>
				</th>
				<th width="100"><!-- 倒计天数 --><spring:message code="main.home.message.daojitianshu"/>
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
					<td>${item.START_CONTRACT_DATE}</td>
					<td>${item.END_CONTRACT_DATE}</td>
					<td>${item.DAYS}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>