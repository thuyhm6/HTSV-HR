 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--城市等级--%>
	城市等级
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=cityLevelList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="7" >
	    			<b><font size="+2"><%--城市等级--%>
	    				城市等级
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="50"><!-- 省编号  -->
					省编号
				</th>
				<th width="60"><!-- 省名称   -->
					省名称
				</th>
				<th width="50"><!-- 城市编号-->
					城市编号
				</th>
				<th width="60"><!-- 城市名称 -->
					城市名称
				</th>
				<th width="50"><!-- 地区编号-->
					地区编号
				</th>
				<th width="60"><!--地区名称 -->
					地区名称
				</th>
				<th width="60"><!-- 城市等级-->
					城市等级
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${item.STATE_CD}   </td>
					<td class="td_center">${item.STATE_NM}</td>
					<td class="td_center">${item.CITY_CD}</td>
					<td class="td_center">${item.CITY_NM }</td>
					<td class="td_center">${item.REGION_CD }</td>
					<td class="td_center">${item.REGION_NM }</td>
					<td class="td_center">${item.CITY_LEVEL}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>