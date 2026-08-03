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
		response.setHeader("Content-Disposition", "attachment; filename=educationPromotoGradeList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="29" >
	    			<b><font size="+2">
	    				教育实绩
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
				<th width="60">大区 </th>
				<th width="60">支社</th>
				<th width="60">城市 </th>
				<th width="60">流通 </th>
				<th width="60">门店名称 </th>
				<th width="60">门店等级</th>
				<th width="60">社号</th>
				<th width="60">姓名</th>
				<th width="5%">性别</th>
				<th width="7%">电话</th>
				<th width="60">主责产品</th>
				<th width="60">课程组 </th>
				<th width="60">课程名称</th>
				<th width="60">成绩</th>
				<th width="60">课程满意度</th>
				<th width="60">讲师满意度</th>
				<th width="60">NPS</th>
				<th width="60">课时</th>
				<th width="60">讲师姓名 </th>
				<th width="60">培训开始日期</th>
				<th width="60">培训地点</th>
				<th width="60">NPS推荐理由</th>
				<th width="60">NPS不推荐理由</th>
				<th width="60">Sales Talk内容</th>
				<th width="60">竞争社信息</th>
				<th width="60">促销员其他反馈</th>
				<th width="60">修改时间</th>
				<th width="60">更新人</th>
				<th width="60">状态</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${item.PAY_AREA_CD}</td>
					<td class='td_center' >${item.ORG_NM}</td>
               		<input type="hidden" name="DEPTNO" value="${item.DEPTNO}"/>
					<td class='td_center'>${item.CITY_NAME}</td>
					<td class='td_center'>${item.CHANNEL1_NAME}</td>
					<td class='td_right'>${item.SHOP_NAME}</td>
					<td class='td_center'>${item.SHOP_LEVEL}</td>
					<td class='td_center'>${item.EMPNO}</td>
					<td class='td_center'>${item.EMP_NM}</td>
					<td class='td_center'>${item.SEX_NAME}</td>
					<td class='td_center'>${item.OFFICE_PHONE}</td>
					<td class='td_center'>${item.CN_CD_NM}</td>
					<td class='td_center' >${item.SUBJT_GR_NM}</td>
					<td class='td_center'>${item.SUBJT_NM}</td>
					<td class='td_center'>${item.GRADE_POINT}</td>
					<td class='td_right'>${item.COURSE_S}</td>
					<td class='td_center'>${item.LECTURER_S}</td>
					<td class='td_center'>${item.NPS}</td>
					<td class='td_center'>${item.SUBJT_TIME}</td>
					<td class='td_center'>${item.TCR_NM}</td>
					<td class='td_center'>${item.EDU_TIME}</td>
					<td class='td_center'>${item.EDU_RM}</td>
					<td class='td_center'>${item.NPS_REASON}</td>
					<td class='td_center'>${item.NPS_NO_REASON}</td>
					<td class='td_center'>${item.SALES_TALK}</td>
					<td class='td_center'>${item.COM_CLUB_INFO}</td>
					<td class='td_center'>${item.OTHER_FEEDBACK}</td>
					<td class='td_center'>${item.UPDT_DTIME}</td>
					<td class='td_center'>${item.UPDT_USER}</td>
					<td class='td_center'>${item.USE_YN}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>
