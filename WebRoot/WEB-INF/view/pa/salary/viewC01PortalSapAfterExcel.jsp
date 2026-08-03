 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--江苏玛特SAP薪资信息(后)--%>
	<spring:message code="pa.salary.title.sapinfoafter"/>
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=PaSapInfoAfter.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
	    		<tr>
		    		<td align="center" colspan="13" >
		    			<b>
		    				<font size="+2"><%--江苏玛特SAP薪资信息(后)--%>
		    	     			<spring:message code="pa.salary.title.sapinfoafter"/>
		    				</font>
		    			</b>
		    		</td>
		    	</tr>
				<tr>
					<th width="5%" align="center"><!--序号-->
						<spring:message code="pa.insurance.title.orderNo"/>
					</th>
					<th width="15%" align="center"><%--当日发送次数--%>
						<spring:message code="pa.title.message.sendCount"/>
					</th>
					<th width="5%" align="center"><%--公司ID--%>
						<spring:message code="pa.insurance.title.companyID"/>
					</th>
					<th width="5%" align="center"><%--工资月--%>
						<spring:message code="ar.viewarprogress.title.gongziyue"/>
					</th>
					<th width="8%" align="center"><%--工资发放日--%>
						<spring:message code="pa.salary.title.salaryProvideDate"/>
					</th>
					
					<th width="8%" align="center"><%--发送日期--%>
					<spring:message code="pa.salary.title.senddate"/>
					</th>
					<th width="10%" align="center"><!--发送时间-->
						<spring:message code="pa.salary.title.sendtime"/>
					</th>
					<th width="5%" align="center"><!--发送人-->
						<spring:message code="pa.salary.title.sender"/>
					</th><%--
					<th width="7%" align="center">部门区分NO
						<spring:message code="ess.trans.title.distinctDeptName"/>NO
					</th>--%>
					<th width="10%" align="center"><%--部门区分名称--%>
						<spring:message code="ess.trans.title.distinctDeptName"/>
					</th>
					<th width="7%" align="center"><%--工号--%>
						<spring:message code="public.title.empId"/>
					</th>
					
					<th width="8%" align="center"><%--姓名--%>
						<spring:message code="public.title.name"/>
					</th>
					<th width="10%" align="center"><%--实发工资--%>
						<spring:message code="ess.viewpersonalpainfo.shifagongzi"/>
					</th>
					<th width="5%" align="center"><%--银行ID--%>
						<spring:message code="rp.report.title.bankname"/>ID
					</th>
					<th width="8%" align="center"><%--银行名称--%>
						<spring:message code="hr.viewCondSql.title.YINHANGMINGCHENG"/>
					</th>
					<th width="15%" align="center"><%--银行账号--%>
						<spring:message code="rp.report.title.bankcardno"/>
					</th>
					
				</tr>
				<c:forEach items="${sapPaAfterList}" var="item" varStatus="i">
					<tr target="sid" rel="${item.SEQ}">
						<td width="5%" align="center">${i.index+1}</td>
						<td width="15%" align="center">${item.SEND_COUNT }</td>
						<td width="5%" align="center">${item.CPNY_ID }</td>
						<td width="5%" align="center">${item.PA_MONTH }</td>
						<td width="8%" align="center">${item.GIVE_DATE }</td>
						
						<td width="8%" align="center">${item.SEND_DATE }</td>
						<td width="5%" align="center">${item.CREATED_BY}</td>
						<td width="5%" align="center">${item.CREATED_DATE}</td>
						<%--<td width="7%" align="center">${item.DEPT_DISTINGUISH_NO }</td>--%>
						<td width="10%" align="center">${item.DEPT_DISTINGUISH_NAME }</td>
						<td width="7%" align="center">${item.EMPID }</td>
						
						<td width="8%" align="center">${item.EMP_NAME }</td>
						<td width="10%" align="center">${item.ACTUAL_RELEASE_SALARY }</td>
						<td width="5%" align="center">${item.BANK_ID }</td>
						<td width="10%" align="center">${item.BANK_NAME }</td>
						<td width="15%" align="center" style="vnd.ms-excel.numberformat:@">${item.CARD_NO }</td>
						
					</tr>
				</c:forEach>
	      </table>  
		</td>
	  </tr>
	</table>
</body>
</html>