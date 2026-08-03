 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--在离职人员信息--%>
	在离职人员信息
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=empOnStatus.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="22" >
	    			<b><font size="+2"><%--在离职人员信息--%>
	    				在离职人员信息
	    			</font></b>
	    		</td>
	    	</tr>
	    	
			<tr>
				<th width="2%" align="center"><!--序号-->
					<spring:message code="pa.insurance.title.orderNo"/>
				</th>
				<th width="4%" align="center"><%--部门区分--%>
					<spring:message code="ess.trans.title.distinctDeptName"/>
				</th>
				<th width="4%" align="center"><%--部门--%>
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="3%" align="center"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="3%" align="center"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				
				<th width="3%" align="center"><%--员工状态--%>
					<spring:message code="ess.trans.title.employeeStatus"/>
				</th>
				<th width="4%" align="center"><%--员工类型--%>
					<spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/>
				</th>
				<th width="3%" align="center"><%--在职区分--%>
					<spring:message code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME"/>
				</th>
				<th width="3%" align="center"><%--职级(GGS)--%>
					<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
				</th>
				<th width="3%" align="center"><%--职级名称(职务) --%>
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
				
				<th width="4%" align="center"><%--职责--%>
					<spring:message code="sys.affirm.title.duty"/>
				</th>
				<th width="4%" align="center"><%--职(岗)位--%>
					<spring:message code="public.title.positionName"/>
				</th>
				<th width="8%" align="center"><%--身份证号--%>
					<spring:message code="ess.personalinfo.title.IDCardNo"/>
				</th>
				<th width="5%" align="center"><%--出生日期--%>
					<spring:message code="main.home.message.chushengriqi"/>
				</th>
				<th width="3%" align="center"><%--性别--%>
					<spring:message code="hr.viewPersonalInfo.title.SEX"/>
				</th>
				
				<th width="5%" align="center"><%--集团入职日期--%>
					<spring:message code="hr.viewPersonalInfo.title.JOIN_BLOC_DATE"/>
				</th>
				<th width="5%" align="center"><%--子公司入司日期--%>
					<spring:message code="hr.viewPersonalInfo.title.JOIN_COMPANY_DATE"/>
				</th>
				<th width="5%" align="center"><%--离职日期--%>
					<spring:message code="hr.viewPromote.title.RESIGN_DATE"/>
				</th>
				<th width="5%" align="center"><%--现部门异动日期--%>
					<spring:message code="hr.viewPersonalInfo.title.NOW_DEPARTMENT_DATE"/>
				</th>
				<th width="5%" align="center"><%--试用结束日--%>
					<spring:message code="hr.viewPersonalInfo.title.PROBATION_FINISH_DATE"/>
				</th>
				
				<th width="6%" align="center"><%--采用路径--%>
					<spring:message code="hr.viewPersonalInfo.title.RECRUITMENT_SOURCE_NAME"/>
				</th>
				<th width="6%" align="center"><%--详细采用路径--%>
					<spring:message code="hr.viewPersonalInfo.title.REC_SOURCE_DETAIL"/>
				</th>
			</tr>
			
	       	<c:forEach items="${personStatusInfoList}" var="item" varStatus="i">
				<tr>
					<td width="5%" align="center">${i.index+1}</td>
					<td align="center">${item.DISTINGUISH_DEPT }</td>
					<td align="center">${item.DEPARTMENT }</td>
					<td align="center">${item.EMPID }</td>
					<td align="center">${item.LOCAL_NAME }</td>
					
					<td align="center">${item.STATUS }</td>
					<td align="center">${item.EMP_TYPE }</td>
					<td align="center">${item.EMP_OFFICE }</td>
					<td align="center">${item.POST_GRADE }</td>
					<td align="center">${item.POST }</td>
					
					<td align="center">${item.DUTY }</td>
					<td align="center">${item.POSITION }</td>
					<td align="center" style="vnd.ms-excel.numberformat:@">${item.IDCARD_NO }</td>
					<td align="center">${item.DOB }</td>
					<td align="center">${item.SEX }</td>
					
					<td align="center">${item.JOIN_BLOC_DATE }</td>
					<td align="center">${item.JOIN_COMPANY_DATE }</td>
					<td align="center">${item.DATE_LEFT }</td>
					<td align="center">${item.NOW_DEPARTMENT_DATE }</td>
					<td align="center">${item.BEFORE_END_PROBATION_DATE }</td>
					
					<td align="center">${item.RECRUITMENT_SOURCE_TYPE }</td>
					<td align="center">${item.REC_SOURCE_DETAIL_NO }</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>