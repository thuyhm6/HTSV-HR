 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	大区单台提成调整
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=incAdjustList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="1" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
			<tr>
				<th width="100"><!-- 大区   -->
					大区
				</th>
				<th width="100"><!-- 产品ID  -->
					产品ID
				</th>
				<th width="100"><!-- 调整比率  -->
					调整比率
				</th>
				<th width="100"><!-- 固定提成-->
					固定提成
				</th>
				<th width="100"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th width="100"><spring:message code="inct.salesman.updateTime"/><!--更新时间--></th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td>${item.PAY_AREA_CD}</td>
	                <td>${item.PROD_ID}</td>
	                <td>${item.DIFF_RAT}</td>
	                <td>${item.FXD_AMT}</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
					<td>${item.UPDT_DTIME}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>