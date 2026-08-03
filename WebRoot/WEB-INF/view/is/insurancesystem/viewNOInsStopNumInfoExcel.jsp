 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--合同信息表--%>
	停保人员信息表
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=instanceStopInsurenum1.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="12" >
	    			<b><font size="+2"><%--合同信息表--%>
	    				停保人员信息表
	    			</font></b>
	    		</td>
	    	</tr>
			<thead>
			<tr>
			    <th width="50">
					<input type="checkbox" name="c1_bx0104_c" id="c1_bx0104_c" class="checkboxCtrl" group="check" >
				</th>
				<th width="50"><!-- 序号-->
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM"/>
				</th>
				<th width="100" ><!-- 部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="100" ><!-- 职号-->
					<spring:message code="display.emp.statistics.mes209"/>
				</th>
				<th width="80"><!-- 姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="50"><!-- 性别-->
					<spring:message code="hr.viewPersonalInfo.title.SEX"/>
				</th>
				
				<th width="130"><!-- 身份证号 -->
					<spring:message code="ess.personalinfo.title.IDCardNo"/>
				</th>
				<th width="100"><!-- 公积金账号-->
					<spring:message code="display.emp.statistics.mes223" />
				</th>				
				<th width="100" ><!-- 在职状态-->
					<spring:message code="display.emp.statistics.mes204"/>
				</th>
				<th width="100" ><!-- 入社日期-->
					<spring:message code="display.emp.statistics.mes206"/>
				</th>
			
				<th width="100" ><!-- 离职日期-->
					<spring:message code="ess.trans.title.resignDate"/>
				</th>				
				<th width="100" ><!-- 终止缴纳月-->
					<spring:message code="display.emp.statistics.mes224" />
				</th>				
		</thead>
			<tbody>
			<c:forEach items="${itemList}" var="show" varStatus="i">
                         <tr align="center" onclick="band('#f4f7fa','black')">
							<td >
								${i.index + 1}&nbsp;
							</td>
							<td align="left">
								${show.DEPTNAME}&nbsp;
							</td>
							<td >
								${show.EMPID}&nbsp;
							</td>
							<td >
								${show.CHINESENAME}&nbsp;
							</td>
							<td >
								${show.SEX_NAME}&nbsp;
							</td>
							<td >
								${show.IDCARD_NO}&nbsp;
							</td>
							<td >
								${show.SOCIAL_NO}&nbsp;
							</td>
							<td >
								${show.EMP_OFFICE_NAME}&nbsp;
							</td>
							<td >
								${show.DATE_STARTED}&nbsp;
							</td>
							<td >
								<c:choose>
									<c:when test="${empty show.DATE_LEFT}">-</c:when>
									<c:otherwise>${show.DATE_LEFT}</c:otherwise>
								</c:choose>
							</td>
							<td >
								<input type="hidden" name="empID" value="${show.EMPID}">
								<input type="hidden" name="yearMonth" value="${show.END_DATE}">
								${show.END_DATE}
							</td>
						</tr>
						</c:forEach>
					</tbody>
</table>
</body>
</html>