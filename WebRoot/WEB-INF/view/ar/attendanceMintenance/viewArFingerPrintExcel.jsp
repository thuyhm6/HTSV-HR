 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--员工的指纹编号信息--%>
	员工的指纹编号信息
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=EmpFingerPrintInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" 
   			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
   		<tr>
    		<td align="center" colspan="7">
    			<b>
    				<font size="+2"><%--员工的指纹编号信息--%>
    	     			员工的指纹编号信息
    				</font>
    			</b>
    		</td>
    	</tr>
		<tr>
			<th width="5%" style="text-align: center">序号</th>
			<th width="10%" style="text-align: center">工号</th>
			<th width="10%" style="text-align: center">姓名</th>
			<th width="15%" style="text-align: center">指纹编号</th>
			<th width="10%" style="text-align: center">员工状态</th>
			<th width="15%" style="text-align: center">部门</th>
			<th width="15%" style="text-align: center">分店名</th>
		</tr>
		<c:forEach items="${fingerPringList}" var="finger" varStatus="i">
			<tr target="sid" rel="${finger.PERSON_ID}">
				<td style="text-align: center">${i.index+1}</td>
				<td style="text-align: center">${finger.EMPID }</td>
				<td style="text-align: center">${finger.LOCAL_NAME }</td>
				<td style="text-align: center">${finger.FINGER_ID }</td>
				<td style="text-align: center">${finger.STATUS }</td>
				<td style="text-align: center">${finger.DEPARTMENT }</td>
				<td style="text-align: center">${finger.DISTINGUISH_NAME }</td>
			</tr>
		</c:forEach>
    </table> 