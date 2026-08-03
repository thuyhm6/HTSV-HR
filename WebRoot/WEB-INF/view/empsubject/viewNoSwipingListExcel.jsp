 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--教育实绩--%>
	教育实绩
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=NoSwiping.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="8" >
	    			<b><font size="+2">
	    				未刷卡信息
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="60">日期</th>
				<th width="60">社号/姓名</th>
				<th width="60">部门 </th>
				<th width="60">班次</th>
				<th width="60">状态</th>
				<th width="60">上班打卡时间</th>
				<th width="60">下班打卡时间</th>
				<th width="60">长度</th>
			</tr> 
	       	<c:forEach items="${noSwipingCardList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${item.ARDATESTR}</td>
					<td class='td_center' >${item.EMPIDANDNAME}</td>
					<td class='td_center'>${item.DEPTNAME}</td>
					<td class='td_center'>${item.SHIFTNAME}</td>
					<td class='td_center'>${item.ITEMNAME}</td>
					<td class='td_right'>${item.FROMTIME}</td>
					<td class='td_center'>${item.TOTIME}</td>
					<td class='td_center'>${item.LENGTHQ}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>
