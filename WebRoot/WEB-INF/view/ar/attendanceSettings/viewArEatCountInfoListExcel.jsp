 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--员工食堂就餐可打卡次数信息--%>
	员工食堂就餐可打卡次数信息
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=EmpEatMealCountInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" 
   			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
   		<tr>
    		<td align="center" colspan="7">
    			<b>
    				<font size="+2"><%--员工食堂就餐可打卡次数信息--%>
    	     			员工食堂就餐可打卡次数信息
    				</font>
    			</b>
    		</td>
    	</tr>
		<tr>
			<th width="5%" style="text-align: center">序号</th>
			<th width="10%" style="text-align: center">工号</th>
			<th width="10%" style="text-align: center">姓名</th>
			<th width="10%" style="text-align: center">员工状态</th>
			<th width="15%" style="text-align: center">日期</th>
			<th width="10%" style="text-align: center">可刷卡次数</th>
			<th width="10%" style="text-align: center">是否锁定</th>
			<th width="15%" style="text-align: center">部门</th>
			<th width="15%" style="text-align: center">分店名</th>
		</tr>
		<c:forEach items="${eatMealCountList}" var="meal" varStatus="i">
			<tr target="sid" rel="${meal.AR_EAT_COUNT_NO}">
				<td style="text-align: center">${i.index+1}</td>
				<td style="text-align: center">${meal.EMPID }</td>
				<td style="text-align: center">${meal.LOCAL_NAME }</td>
				<td style="text-align: center">${meal.EMP_OFFICE }</td>
				<td style="text-align: center">${meal.AR_DATE_STR }</td>
				
				<td style="text-align: center">${meal.MEAL_NUM }</td>
				<td style="text-align: center">
						<c:if test="${meal.LOCK_FLAG eq 'Y'}">
							<font color="red">已锁定</font>
						</c:if>
						<c:if test="${meal.LOCK_FLAG eq 'N'}">
							<font color="blue">未锁定</font>
						</c:if>
					</td>
				<td style="text-align: center">${meal.DEPARTMENT }</td>
				<td style="text-align: center">${meal.DISTINGUISH_NAME }</td>
			</tr>
		</c:forEach>
    </table> 