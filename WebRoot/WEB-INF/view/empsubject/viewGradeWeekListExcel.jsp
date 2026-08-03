 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--课程周别汇总--%>
	课程周别汇总
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=educationGradeWeekList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="10" >
	    			<b><font size="+2">
	    				课程周别汇总
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>			
			    <th width="60">
					大区
				</th>
				<th width="60"><!-- 组织名称  -->
					部门
				</th>
				<th width="60"><!-- 课程组-->
					课程组
				</th>
				<th width="60"><!-- 课程名称 -->
					课程名称
				</th>
				<th width="60"><!-- 渠道-->
					渠道
				</th>
				<th width="60"><!--前周累积 -->
					前周累积
				</th>
				<th width="60"><!-- 今周-->
					今周
				</th>
				<th width="60"><!-- 现在累积-->
					现在累积
				</th>
				<th width="60"><!-- 总人数	-->
					总人数	
				</th>
				<th width="60"><!-- 比率-->
					比率
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${item.PAY_AREA_CD}</td>
					<td class='td_center' >${item.ORG_NM}</td>
					<td class='td_center'>${item.SUBJT_GR_NM}</td>
					<td class='td_right'>${item.SUBJT_NM}</td>
					<td class='td_center'>${item.CHANNEL_NM}</td>
					<td class='td_center'>${item.PRE_WEEK_ACCOUNT}</td>
					<td class='td_center'>${item.CURRENT_WEEK}</td>
					<td class='td_center'>${item.NOW_ACCOUNT}</td>
					<td class='td_center'>${item.TOTAL_COUNT}</td>
					<td class='td_center' >${item.RATE}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>
