 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--乐天玛特异常明细-社保情况--%>
	乐天玛特异常明细-社保情况
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=LotteMart-shebaoqingkuang.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="45%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" 
	    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
	    		<tr>
		    		<td align="center" colspan="7" >
		    			<b><font size="+2">乐天玛特${YEAR }年${MONTH }月,异常明细-社保情况</font></b>
		    		</td>
		    	</tr>
				<tr>
					<td align="center"><%--险种--%>险种</td>
					<td align="center"><%--上月参保人数--%>上月参保人数</td>
					<td align="center"><%--本月参保人数--%>本月参保人数</td>
					<td align="center"><%--本月增加人数--%>本月增加人数</td>
					<td align="center"><%--上月参保金额（公出）--%>上月参保金额（公出）</td>
					<td align="center"><%--本月参保金额（公出）--%>本月参保金额（公出）</td>
					<td align="center"><%--本月增加金额（公出）--%>本月增加金额（公出）</td>
				</tr>        
		       	<c:forEach items="${abnormalInsList}" var="ins" varStatus="i">
					<tr>
						<td style="text-align: center">
							${ins.SUM_NAME }
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${ins.LAST_CNT }" pattern="#,##0"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${ins.SYS_CNT }" pattern="#,##0"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${ins.ADD_CNT }" pattern="#,##0"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${ins.LAST_SUM }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${ins.SYS_SUM }" pattern="#,##0.00#"/>
						</td>
						<td style="text-align: right">
							<fmt:formatNumber value="${ins.ADD_SUM }" pattern="#,##0.00#"/>
						</td>
					</tr>
				</c:forEach>
	      </table>  
		</td>
	  </tr>
	</table>
</body>
</html>