 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	促销员担当产品
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=IncProdTpByPromoter.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="9" >
	    			<b><font size="+2">
	    				促销员担当产品
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="100"><!-- 社号  -->
					社号
				</th>
				<th width="100"><!-- 员工姓名   -->
					员工姓名
				</th>
				<th width="100"><!-- 大区  -->
					大区
				</th>
				<th width="100"><!-- 产品类型 -->
					产品类型
				</th>
				<th width="100"><!-- 地区区分-->
					地区区分
				</th>
				<th width="100"><!-- 促销员所属 -->
					促销员所属
				</th>
				<th width="100"><!-- 工作类型 -->
					工作类型
				</th>
				<th width="100"><!-- 是否在职-->
					是否在职
				</th>
				<th width="100"><!-- 是否使用 -->
					是否使用
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td>${item.EMPNO}</td>
	                <td>${item.EMP_NM}</td>
	                <td>${item.PAY_AREA_NM}</td>
	                <td>${item.PROD_TP_NM}</td>
	                <td>${item.OFICE_AREA_NM}</td>
	                <td>${item.PROMTR_PAYMNT_TP_NM}</td>
	                <td>${item.PROMTR_WORK_TP_NM}</td>
	                <td>${item.TENU_STAT}</td>
	                <td>${item.USE_YN}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>