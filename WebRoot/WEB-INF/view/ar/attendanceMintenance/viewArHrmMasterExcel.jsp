 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--考勤机系统HrmMaster信息--%>
	考勤机系统HrmMaster信息
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=ArHrmMasterInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" 
   			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
   		<tr>
    		<td align="center" colspan="13">
    			<b>
    				<font size="+2"><%--考勤机系统HrmMaster信息--%>
    	     			考勤机系统HrmMaster信息
    				</font>
    			</b>
    		</td>
    	</tr>
		<tr>
			<th width="5%" style="text-align: right">序号</th>
			<th width="15%" style="text-align: center">法人名</th>
			<th width="8%" style="text-align: center">工号</th>
			<th width="10%" style="text-align: center">姓名</th>
			<th width="12%" style="text-align: center">部门</th>
			<th width="12%" style="text-align: center">分店名</th>
			
			<th width="6%" style="text-align: center">离职日</th>
			<th width="5%" style="text-align: center">在职区分</th>
			<th width="5%" style="text-align: center">同步区分</th>
			<th width="6%" style="text-align: center">生产日</th>
			<th width="5%" style="text-align: center">生成者</th>
			
			<th width="6%" style="text-align: center">修改日</th>
			<th width="5%" style="text-align: center">修改人</th>
		</tr>
		<c:forEach items="${hrmMasterList}" var="master" varStatus="i">
		<tr target="sid" rel="${master.PERSON_ID}">
			<td style="text-align: center">${i.index+1}</td>
			<td style="text-align: center">${master.COMPANY_NAME }</td>
			<td style="text-align: center">${master.EMPID }</td>
			<td style="text-align: center">${master.LOCAL_NAME }</td>
			<td style="text-align: center">${master.DEPT_NAME }</td>
			<td style="text-align: center">${master.STORE_NAME }</td>
			
			<td style="text-align: center">${master.RESIGN_DATE }</td>
			<td style="text-align: center">${master.EMP_OFFICE_NAME }</td>
			<td style="text-align: center">${master.READ_FLAG }</td>
			<td style="text-align: center">${master.CREATE_DATE}</td>
			<td style="text-align: center">${master.CREATED_BY}</td>
			
			<td style="text-align: center">${master.UPDATE_DATE}</td>
			<td style="text-align: center">${master.UPDATED_BY}</td>
		</tr>
	</c:forEach>
     </table>  