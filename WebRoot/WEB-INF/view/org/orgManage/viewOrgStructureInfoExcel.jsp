 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--组织结构信息表--%>
	<spring:message code="org.title.ORGINFO_TABLE"/><!-- 组织结构信息表 -->
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=orgStructureInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="45%" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="45%" border="1" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="15" >
	    			<b><font size="+2"><%--组织结构信息表--%>
	    				<spring:message code="org.title.ORGINFO_TABLE"/><!-- 组织结构信息表 -->
	    			</font></b>
	    		</td>
	    	</tr>
	    	
			<tr>
				<th style="text-align: center"><spring:message code="org.title.NO"/><!-- 序号 --></th>
				<th style="text-align: center"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>NO<!--部门NO--></th>
				<th><spring:message code="org.orgManage.title.deptName"/><!--部门名称--></th>
				<th style="text-align: center"><spring:message code="org.title.DEPT_LV"/><!-- 部门等级 --></th>
				<th style="text-align: center"><spring:message code="org.orgManage.title.parentDept"/><!--上级部门--></th>
				<th style="text-align: center"><spring:message code="org.orgManage.title.deptBeginTime"/><!--部门成立时间--></th>
				<th style="text-align: center"><spring:message code="org.orgManage.title.deptEndTime"/><!--部门结束时间--></th>
				
				<th style="text-align: center"><spring:message code="org.orgManage.title.deptDistinct"/><!--部门区分--></th>
				<th style="text-align: center"><spring:message code="org.orgManage.title.deptType"/><!--部门类型--></th>
				<th style="text-align: center"><spring:message code="org.orgManage.title.ownArea"/><!--所属地区--></th>
				
				<th style="text-align: center"><spring:message code="org.orgManage.title.createdBy"/><!--添加者--></th>
				<th style="text-align: center"><spring:message code="org.title.ADD_DATE"/><!-- 添加日期 --></th>
				<th style="text-align: center"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
				<th style="text-align: center"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
				<th style="text-align: center"><spring:message code="org.title.IS_USE" /><!-- 是否使用 --></th>
			</tr>
	       	<c:forEach items="${orgStructureInfoList}" var="org" varStatus="i">
				<tr>
					<td style="text-align: center">${i.index+1}</td>
					<td style="text-align: center">${org.DEPTNO}</td>
					<td>
						<c:if test="${org.DEPT_LEVEL==1}">
								${org.DEPATMENT}
						</c:if>
						<c:if test="${org.DEPT_LEVEL>1}">
							<c:forEach begin="2" end="${org.DEPT_LEVEL}" step="1" >
								&nbsp;&nbsp;&nbsp;
							</c:forEach>${org.DEPATMENT}
						</c:if>
					</td>
					<td style="text-align: center">${org.DEPT_LEVEL}</td>
					<td style="text-align: center">${org.PARENT_DEPT_NAME}</td>
					<td style="text-align: center">${org.DATE_CREATED}</td>
					<td style="text-align: center">${org.DATE_ENDED}</td>
					
					<td style="text-align: center">${org.DEPT_DISTINGUISH_NAME}</td>
					<td style="text-align: center">${org.DEPT_TYPE_NAME}</td>
					<td style="text-align: center">${org.WORK_AREA}</td>
					
					<td style="text-align: center">${org.CREATED_BY}</td>
					<td style="text-align: center">${org.CREATE_DATE}</td>
					<td style="text-align: center">${org.UPDATED_BY}</td>
					<td style="text-align: center">${org.UPDATE_DATE}</td>
					<td style="text-align: center">
						<c:if test="${org.ACTIVITY==1}"><!-- 使用中 -->
							<font color="green"><spring:message code="org.title.IN_USE" /><!-- 使用中 --></font>
						</c:if>
						<c:if test="${org.ACTIVITY==0}"><!-- 已停用 -->
							<font color="red"><spring:message code="org.title.USED" /><!-- 已使用 --></font>
						</c:if>
					</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>