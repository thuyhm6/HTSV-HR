 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--刷卡机刷卡数据信息--%>
	刷卡机刷卡数据信息
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=ArCardRecordInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
   	<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" 
   			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
   		<tr>
    		<td align="center" colspan="16">
    			<b>
    				<font size="+2"><%--刷卡机刷卡数据信息--%>
    	     			刷卡机刷卡数据信息
    				</font>
    			</b>
    		</td>
    	</tr>
		<tr>
			<th width="5%" style="text-align: right">序号</th>
		
			<th width="15%" style="text-align: center">法人名</th>
			<th width="5%" style="text-align: center">工号</th>
			<th width="5%" style="text-align: center">姓名</th>
			<th width="10%" style="text-align: center">部门</th>
			<th width="5%" style="text-align: center">分店名</th>
			
			<th width="5%" style="text-align: center">上/下班</th>
			<th width="5%" style="text-align: center">刷卡日期</th>
			<th width="5%" style="text-align: center">刷卡时间</th>
			<th width="5%" style="text-align: center">考勤机号</th>
			<th width="5%" style="text-align: center">考勤卡号</th>
			
			<th width="5%" style="text-align: center">传送状态</th>
			<th width="5%" style="text-align: center">传送日</th>
			<th width="5%" style="text-align: center">传送者</th>
			<th width="5%" style="text-align: center">修改日</th>
			<th width="5%" style="text-align: center">修改者</th>
		</tr>
		<c:forEach items="${cardRecordList}" var="card" varStatus="i">
			<tr target="sid" rel="${card.AR_MAC_DATA_NO}">
				<td style="text-align: center">${i.index+1}</td>
				
				<td style="text-align: center">${card.COMPANY_NAME }</td>
				<td style="text-align: center">${card.EMPID }</td>
				<td style="text-align: center">${card.LOCAL_NAME }</td>
				<td style="text-align: center">${card.DEPT_NAME }</td>
				<td style="text-align: center">${card.STORE_NAME }</td>
				
				<td style="text-align: center">
					<c:if test="${card.DOOR_TYPE eq 'IN'}">
						<b><font color="green">进门</font></b>
					</c:if>
					<c:if test="${card.DOOR_TYPE eq 'OUT'}">
						<b><font color="blue">出门</font></b>
					</c:if>
					<c:if test="${card.DOOR_TYPE ne 'IN' && card.DOOR_TYPE ne 'OUT'}">
						<b><font color="red">异常</font></b>
					</c:if>
				</td>
				<td style="text-align: center">${card.R_DATE }</td>
				<td style="text-align: center">${card.R_TIME }</td>
				<td style="text-align: center">${card.INTERFACE_RECORD_ID }</td>
				<td style="text-align: center">${card.CARD_NO }</td>
				
				<td style="text-align: center">
					<c:if test="${card.SEND_STATUS eq 'S'}">
						<b><font color="green">已读取</font></b>
					</c:if>
					<c:if test="${card.SEND_STATUS ne 'S'}">
						<b><font color="red">未读取</font></b>
					</c:if>
				</td>
				<td style="text-align: center">${card.SEND_DATE }</td>
				<td style="text-align: center">${card.SEND_BY }</td>
				<td style="text-align: center">${card.UPDATE_DATE }</td>
				<td style="text-align: center">${card.UPDATED_BY }</td>
			</tr>
		</c:forEach>
    </table> 