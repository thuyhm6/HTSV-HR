 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	促销员实绩上报
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=_temp_Sellout.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="1" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
			<tr>
				<th>支社</th>
				<th>社号</th>
				<th>月份</th>
				<th>日期</th>
				<th>产品类型</th>
				<th>产品ID</th>
				<th>客户ID</th>
				<th>CHANNEL</th>
				<th>销售数量</th>
				<th>NOTICE_PRICE</th>
				<th>SELLOUT_PRICE</th>
				<th>SEQ</th>
				<th width="100"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td>${item.BRANCH}</td>
					<td>${item.EMP_NO}</td>
					<td>${item.SALE_MONTH}</td>
					<td><fmt:formatDate value="${item.SALE_DAY}" pattern="yyyy-MM-dd" /></td>
					<td>${item.MODEL_CATEGORY_CODE}</td>
					<td>${item.MODEL_CODE}</td>
					<td>${item.SHIP_TO_CODE}</td>
					<td>${item.CHANNEL_CODE}</td>
					<td>${item.SALE_QTY}</td>
					<td>${item.NOTICE_PRICE}</td>
					<td>${item.SELLOUT_PRICE}</td>
					<td>${item.IMP_SEQ}</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>