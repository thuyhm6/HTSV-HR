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
		response.setHeader("Content-Disposition", "attachment; filename=officeIncAdjustList.xls");
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
	    				大区单台提成调整
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="100"><!-- 大区   -->
					大区
				</th>
				<th width="100"><!-- 产品类型   -->
					产品类型
				</th>
				<th width="100"><!-- 产品ID  -->
					产品ID
				</th>
				<th width="100"><!-- 单价 -->
					单价
				</th>
				<th width="100"><!-- 总部单价-->
					总部单价
				</th>
				<th width="100"><!-- 标准提成-->
					标准提成
				</th>
				<th width="100"><!-- 调整比率  -->
					调整比率
				</th>
				<th width="100"><!--基本提成-->
					基本提成
				</th>
				<th width="100"><!-- 固定提成-->
					固定提成
				</th>
				<th width="100"><!-- 修改人 -->
					修改人
				</th>
				<th width="100"><!-- 使用标记 -->
					使用标记
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td>${item.PAY_AREA_NM}</td>
	                <td>${item.PROD_TP}</td>
	                <td>${item.PROD_ID}</td>
	                <td>${item.UNIT_PRC}</td>
	                <td>${item.HEAD_UNIT_PRC}</td>
	                <td>${item.SUBSD_INCTV_AMT}</td>
	                <td>${item.DIFF_RAT}</td>
	                <td>${item.BASE_AMT}</td>
	                <td>${item.FXD_AMT}</td>
	                <td>${item.UPDT_USER}</td>
	                <td>${item.USE_YN}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>