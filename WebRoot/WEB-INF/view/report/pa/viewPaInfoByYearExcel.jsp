 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%--员工年薪资信息--%>
			${PA_YEAR }年，员工年薪资信息
		</title>
	</head>                              
	<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=paInfoByYear.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="22" >
	    			<b><font size="+2">${PA_YEAR }<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--年-->，<spring:message code="liang.hr.viewWorkInfo.title.yuangongnianxinxi"/><!--员工年薪资信息--></font></b>
	    		</td>
	    	</tr>
			<tr>
				<th style="text-align: center"><spring:message code="ar.viewcycle.title.xuhao" /><!-- 序号 --></th>
				<th style="text-align: center"><spring:message code="liang.hr.viewWorkInfo.title.farenmingcheng"/><!-- 法人名称 --></th>
				<th style="text-align: center"><spring:message code="pa.payear.title.payear"/><!-- 年份 --></th>
				<th style="text-align: center"><spring:message code="public.title.empId"/><!-- 工号 --></th>
				<th style="text-align: center"><spring:message code="public.title.empName"/><!-- 姓名 --></th>
				<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/> <!-- 部门 --></th>
				<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.POST_NAME"/><!--职级名称 --></th>
				<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_GONGHUINEIBUZHIZE"/><!-- 职责 --></th>
				<th style="text-align: center"><spring:message code="main.home.message.ruzhiriqi"/><!-- 入职日期 --></th>
				
				<th style="text-align: center">01<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">02<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">03<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">04<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">05<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">06<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				
				<th style="text-align: center">07<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">08<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">09<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">10<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">11<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				<th style="text-align: center">12<spring:message code="liang.hr.viewWorkInfo.title.MONTH"/><!--月  --></th>
				
				<th style="text-align: center"><spring:message code="liang.hr.viewWorkInfo.title.zongji"/><!-- 总计 --></th>
			</tr>    
	       	<c:forEach items="${paInfoByYearList}" var="pa" varStatus="i">
				<tr target="EMPID" rel="${pa.EMPID}">
					<td style="text-align: center">${i.index+1 }</td>
					<td style="text-align: center">${pa.COMPANY_NAME}</td>
					
					<td style="text-align: center">${pa.PA_YEAR}</td>
					<td style="text-align: center">${pa.EMPID}</td>
					<td style="text-align: center">${pa.LOCAL_NAME}</td>
					<td style="text-align: center">${pa.DEPT_NAME}</td>
					<td style="text-align: center">${pa.POSITION_NAME}</td>
					<td style="text-align: center">${pa.DUTY_NAME}</td>
					<td style="text-align: center">${pa.DATE_STARTED}</td>
					
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M01_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M02_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M03_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M04_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M05_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M06_SALARY}</td>
					
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M07_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M08_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M09_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M10_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M11_SALARY}</td>
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.M12_SALARY}</td>
					
					<td style="text-align: right" style="vnd.ms-excel.numberformat:@">${pa.SUM_SALARY}</td>
				</tr>
			</c:forEach>
      </table>  
	</body>
</html>