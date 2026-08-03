 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--合同信息表--%>
	基数信息表
</title>
</head> 
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=instancebasenum1.xls");
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
	    				保险基数信息表
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
			     <th width="100">
					<!-- 序号--> <spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
				</th>
				<th width="70">
				   <!-- 部门--> <spring:message code="public.title.deptName" />
				</th>
				<th width="60">
					<!-- 职号--> <spring:message code="display.emp.statistics.mes209" />
				</th>
				<th width="150">
					<!-- 姓名--> <spring:message code="public.title.name" />
				</th>
				<th width="60">
				 <!-- 身份证号 --> <spring:message
						code="ess.personalinfo.title.IDCardNo" />
				</th>
				<th width="140">
				 <!-- 职系--> <spring:message code="display.emp.statistics.mes210" />
				</th>
				<th width="140">
				 <!-- 户口性质 --> <spring:message
						code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
				</th>
				<th width="140">
				 <!-- 在职状态-->
					<spring:message code="display.emp.statistics.mes204"/>
				</th>
				<th width="140">
				<!-- 入社日期--> <spring:message code="display.emp.statistics.mes206" />
				</th>
				<th width="140">
				<!-- 离职日期--> <spring:message code="ess.trans.title.resignDate" />
				</th>
				<th width="140">
				<!-- 平均扣税工资--> <spring:message code="display.emp.statistics.mes198" />
				</th>
				<th width="140">
				<!-- 年度基数--> <spring:message code="display.emp.statistics.mes208" />
				</th>
			
			</tr> 
			
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
	       	<tr>
				    <td>${i.count}</td>
					<td>${item.DEPTNAME}</td>
					<td>${item.EMPID}</td>
					<td>${item.CHINESENAME}</td>
					<td>${item.IDCARD_NO}</td>
					<td>${item.REG_TYPE_CODE_NAME}</td>
					<td>${item.POST_COEF_NAME}</td>
					<td>${item.STATUS_CODE_NAME}</td>
					<td><c:choose>
							<c:when test="${empty show.DATE_STARTED}">-</c:when>
							<c:otherwise>${show.DATE_STARTED}</c:otherwise>
						</c:choose>
					</td>
					<td><c:choose>
							<c:when test="${empty show.DATE_LEFT}">-</c:when>
							<c:otherwise>${show.DATE_LEFT}</c:otherwise>
						</c:choose>
					</td>
					<td>${item.PAY_SALARY}</td>
					<td>${item.AVG_SALARY}</td>
					</tr>
			</c:forEach>
			
      </table>  
	</td>
  </tr>
</table>
</body>
</html>