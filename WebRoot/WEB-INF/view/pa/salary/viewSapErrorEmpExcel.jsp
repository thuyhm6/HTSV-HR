 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--江苏玛特发送SAP，信息欠缺人员信息--%>
	<spring:message code="pa.title.message.sapEmpInfoErrorTable"/>
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=sapErrorEmpInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
	    		<tr>
		    		<td align="center" colspan="13">
		    			<b>
		    				<font size="+2"><%--江苏玛特发送SAP，信息欠缺人员信息--%>
		    	     			<spring:message code="pa.title.message.sapEmpInfoErrorTable"/>
		    				</font>
		    			</b>
		    		</td>
		    	</tr>
				<tr>
					<th width="5%" align="center"><!--序号-->
						<spring:message code="pa.insurance.title.orderNo"/>
					</th>
					<th width="5%" align="center"><%--公司ID--%>
						<spring:message code="pa.insurance.title.companyID"/>
					</th>
					<th width="5%" align="center"><%--工资月--%>
						<spring:message code="ar.viewarprogress.title.gongziyue"/>
					</th>
					<th width="10%" align="center"><%--门店名称--%>
						<spring:message code="pa.title.message.mendianName"/>
					</th>
					<th width="10%" align="center"><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>
					</th>
					
					<th width="7%" align="center"><%--工号--%>
						<spring:message code="public.title.empId"/>
					</th>
					<th width="8%" align="center"><%--人事姓名--%>
						<spring:message code="pa.title.message.empHrmName"/>
					</th>
					<th width="8%" align="center"><%--账号名称--%>
						<spring:message code="pa.title.message.empPaName"/>
					</th>
					<th width="5%" align="center"><%--银行ID--%>
						<spring:message code="rp.report.title.bankname"/>ID
					</th>
					<th width="10%" align="center"><%--银行名称--%>
						<spring:message code="hr.viewCondSql.title.YINHANGMINGCHENG"/>
					</th>
					
					<th width="15%" align="center"><%--银行账号--%>
						<spring:message code="rp.report.title.bankcardno"/>
					</th>
					<th width="15%" align="center"><%--银行支行NO--%>
						<spring:message code="pa.title.message.bankBranchNoInfo"/>
					</th>
					<th width="15%" align="center"><%--银行支行名称--%>
						<spring:message code="pa.title.message.bankBranchNameInfo"/>
					</th>
				</tr>
				<c:forEach items="${sapErrorEmpList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.SEQ}">
					<td width="5%" align="center">${i.index+1}</td>
					<td width="5%" align="center">${item.CPNY_ID }</td>
					<td width="5%" align="center">${item.PA_MONTH }</td>
					<td width="8%" align="center">${item.DEPT_DISTINGUISH_NAME }</td>
					<td width="8%" align="center">${item.DEPARTMENT }</td>
					
					<td width="7%" align="center">${item.EMPID }</td>
					<td width="10%" align="center">${item.LOCAL_NAME }</td>
					<td width="10%" align="center">${item.CARD_NAME }</td>
					<td width="5%" align="center">${item.BANK_ID }</td>
					<td width="10%" align="center">${item.BANK_NAME }</td>
					
					<td width="15%" align="center" style="vnd.ms-excel.numberformat:@">${item.CARD_NO }</td>
					<td width="10%" align="center">${item.BANK_BRANCH_CD }</td>
					<td width="10%" align="center">${item.BANK_BRANCH_NAME_CN }</td>
				</tr>
			</c:forEach>
	      </table>  
		</td>
	  </tr>
	</table>
</body>
</html>