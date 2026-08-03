 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--门店基本信息--%>
	门店基本信息
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=custInfoList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="15" >
	    			<b><font size="+2"><%--城市等级--%>
	    				门店基本信息
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="60"><!-- 年份  -->
					年份
				</th>
				<th width="60"><!-- 大区   -->
					大区
				</th>
				<th width="60"><!-- 大区名称-->
					大区名称
				</th>
				<th width="60"><!-- 门店地区 -->
					门店地区
				</th>
				<th width="60"><!-- 门店代码-->
					门店代码
				</th>
				<th width="60"><!--门店名称 -->
					门店名称
				</th>
				<th width="60"><!-- 门店等级-->
					门店等级
				</th>
				<th width="60"><!-- GoldenShop-->
					GoldenShop
				</th>
				<th width="60"><!-- 渠道-->
					渠道
				</th>
				<th width="60"><!-- 省名称-->
					省名称
				</th>
				<th width="60"><!-- 城市名称-->
					城市名称
				</th>
				<th width="60"><!-- 地区名称-->
					地区名称
				</th>
				<th width="60"><!-- 开店时间-->
					开店时间
				</th>
				<th width="60"><!-- 状态-->
					状态
				</th>
				<th width="60"><!-- 门店提成率-->
					门店提成率
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${item.YYYY}</td>
					<td class="td_center">${item.DIV_CD}</td>
					<td class="td_center">${item.DIV_CD_NM}</td>
					<td class="td_center">${item.CITY_LEVEL_NM}</td>
					<td class="td_center">${item.SHOP_CD}</td>
					<td class="td_left">${item.SHOP_NAME}</td>
					<td class="td_center">${item.SHOP_LEVEL}</td>
					<td class="td_center">${item.GOLDEN_SHOP}</td>
					<td class="td_center">${item.CHANNEL2_NAME}</td>
					<td class="td_center">${item.STATE_NAME}</td>
					<td class="td_center">${item.CITY_NAME}</td>
					<td class="td_center">${item.AREA_NAME}</td>
					<td class="td_center">${item.OPEN_DATE}</td>
					<td class="td_center">${item.USE_YN}</td>
					<td class="td_center"></td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>