 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	促销员实贩卖实绩
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=salesAchievementList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="11" >
	    			<b><font size="+2">
	    				促销员实贩卖实绩
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
	    		<th>大区</th>
	    		<th>支社</th>
	    		<th>姓名</th>
	    		<th>社号</th>
	    		<th>产品类型</th>
	    		<th>产品ID</th>
	    		<th>客户ID</th>
	    		<th>客户类型</th>
	    		<th>销售数量</th>
	    		<th>变动提成</th>
	    		<th>固定提成</th>
	    		<th>销售金额</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
			<tr>
				<td>${item.PAY_AREA_CD}</td>
				<td>${item.BRANCH}</td>
				<td>${item.EMP_NM}</td>
				<td>${item.EMPNO}</td>
				<td>${item.PROD_TP}</td>
				<td>${item.PROD_ID}</td>
				<td>${item.CUST_ID}</td>
				<td>${item.CUST_TP}</td>
				<td>${item.SALS_QTY}</td>
				<td>${item.VARB_INCTV_AMT}</td>
				<td>${item.FXD_INCTV_AMT}</td>
				<td>${item.SALS_AMT}</td>
			</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>