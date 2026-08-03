 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%--课程别实绩汇总--%>
	课程别实绩汇总
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=educationGradeStatisticsList.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table width="60%" border="2" align="center" cellpadding="0" cellspacing="0" 
    			style="padding: 2px 2px 2px 2px;border-collapse:collapse">
    		<tr>
	    		<td align="center" colspan="20" >
	    			<b><font size="+2">
	    				课程别实绩汇总
	    			</font></b>
	    		</td>
	    	</tr>
			<tr>
			    <th width="60"><!-- 分公司	  -->
					大区
				</th>
				<th width="60"><!-- 组织名称  -->
					部门
				</th>
				<th width="60"><!-- 产品   -->
					产品
				</th>
				<th width="60"><!-- 课程组名称-->
					课程组名称
				</th>
				<th width="60"><!-- 课程名称 -->
					课程名称
				</th>
				<th width="60"><!-- 总人数-->
					总人数
				</th>
				<th width="60"><!--参加人员数量 -->
					参加人员数量
				</th>
				<th width="60"><!-- 覆盖率	-->
					覆盖率	
				</th>
				<th width="60"><!-- 成绩-->
					成绩
				</th>
				<th width="60"><!-- 课程满意度-->
					课程满意度
				</th>
				<th width="60"><!-- 讲师满意度-->
					讲师满意度
				</th>
				<th width="60"><!-- NPS-->
					NPS
				</th>
				<th width="60"><!-- 课时数-->
					课时数
				</th>
				<th width="60"><!-- 培训日期-->
					培训日期
				</th>
				<th width="60"><!-- 培训地点-->
					培训地点
				</th>
				<th width="60"><!-- NPS推荐理由-->
					NPS推荐理由
				</th>
				<th width="60"><!-- NPS不推荐理由-->
					NPS不推荐理由
				</th>
				<th width="60"><!-- Sales Talk内容-->
					Sales Talk内容
				</th>
				<th width="60"><!-- 竞争社信息-->
					竞争社信息
				</th>
				<th width="60"><!-- 促销员其他反馈-->
					促销员其他反馈
				</th>
			</tr> 
	       	<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr>
					<td class="td_center">${item.PAY_AREA_CD}</td>
					<td class='td_center' >${item.ORG_NM}</td>
					<td class='td_center'>${item.PROD_TP}</td>
					<td class='td_center'>${item.SUBJT_GR_NM}</td>
					<td class='td_right'>${item.SUBJT_NM}</td>
					<td class='td_center'>${item.TOTAL_COUNT}</td>
					<td class='td_center'>${item.STUDY_COUNT}</td>
					<td class='td_center'>${item.RATE}</td>
					<td class='td_center'>${item.GRADE_POINT}</td>
					<td class='td_center' >${item.COURSE_S}</td>
					<td class='td_center'>${item.LECTURER_S}</td>
					<td class='td_center'>${item.NPS}</td>
					<td class='td_right'>${item.SUBJT_TIME}</td>
					<td class='td_center'>${item.EDU_TIME}</td>
					<td class='td_center'>${item.EDU_RM}</td>
					<td class='td_center'>${item.NPS_REASON}</td>
					<td class='td_center'>${item.NPS_NO_REASON}</td>
					<td class='td_center'>${item.SALES_TALK}</td>
					<td class='td_center'>${item.COM_CLUB_INFO}</td>
					<td class='td_center'>${item.OTHER_FEEDBACK}</td>
				</tr>
			</c:forEach>
      </table>  
	</td>
  </tr>
</table>
</body>
</html>
