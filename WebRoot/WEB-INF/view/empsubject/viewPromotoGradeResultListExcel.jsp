 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
	教育实绩导入结果<!--教育实绩导入结果-->
</title>
</head>                              
<body>
   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=importPromotoGradeData.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>
    	<table class="table" width="100%" layoutH="150">
			<tr>
				<th width="5%"><spring:message code="ar.viewcycleparameter.title.gongsi"/><!-- 公司 -->*</th>
				<th width="5%"><spring:message code="inct.salesman.daqu"/><!-- 大区-->*</th>
				<th width="5%"><spring:message code="empsubject.branch"/><!-- 支社-->*</th>
				<th width="5%"><spring:message code="empsubject.empno"/><!-- 社号-->*</th>
				<th width="5%"><spring:message code="empsubject.subjectGrID"/><!-- 课程组ID -->*</th>
				<th width="5%"><spring:message code="empsubject.subjectID"/><!-- 课程名称ID-->*</th>
				<th width="2%"><spring:message code="empsubject.gradePoint"/><!-- 成绩-->*</th>
				<th width="2%"><spring:message code="empsubject.courseS"/><!-- 课程满意度-->*</th>
				<th width="5%"><spring:message code="empsubject.lecturerS"/><!-- 讲师满意度-->*</th>
				<th width="5%"><spring:message code="empsubject.nps"/><!-- NPS-->*</th>
				<th width="5%"><spring:message code="empsubject.tcrNm"/><!-- 讲师姓名 -->*</th>
				<th width="6%"><spring:message code="empsubject.eduTime"/><!-- 培训开始日期-->*</th>
				<th width="5%"><spring:message code="empsubject.eduRm"/><!-- 培训地点-->*</th>
				<th width="7%"><spring:message code="empsubject.useYn"/><!-- 状态 -->*</th>	
				<th width="5%"><spring:message code="empsubject.subjtTime"/><!-- 课时-->*</th>
				<th width="7%"><spring:message code="empsubject.npsReason"/><!-- NPS推荐理由-->*</th>
				<th width="5%"><spring:message code="empsubject.npnNoReason"/><!-- NPS不推荐理由-->*</th>
				<th width="5%"><spring:message code="empsubject.salesTalk"/><!-- Sales Talk内容-->*</th>
				<th width="5%"><spring:message code="empsubject.comClubInfo"/><!-- 竞争社信息-->*</th>
				<th width="5%"><spring:message code="empsubject.otherFeedback"/><!-- 促销员其他反馈-->*</th>	
				<th width="10%"><spring:message code="inct.salesman.validateMessage"/><!--验证结果-->*</th>
				<th width="10%"><spring:message code="inct.salesman.updateBy"/><!--更新人-->*</th>
				<th width="10%"><spring:message code="inct.salesman.updateTime"/><!--更新时间-->*</th>		
			</tr>
		<tbody>
			<c:forEach items="${MDATA}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.SUBSD_CD}</td>
					<td class='td_right'>${item.PAY_AREA_CD}</td>
					<td class='td_right'>${item.BRANCH_CD}</td>
					<td class='td_center'>${item.EMPNO}</td>
					<td class='td_center'>${item.SUBJT_GR}</td>
					<td class='td_center'>${item.SUBJT_ID}</td>
					<td class='td_right'>${item.GRADE_POINT}</td>
					<td class='td_right'>${item.COURSE_S}</td>
					<td class='td_right'>${item.LECTURER_S}</td>
					<td class='td_right'>${item.NPS}</td>
					<td class='td_center'>${item.TCR_NM}</td>
					<td class='td_center'>${item.EDU_TIME}</td>
					<td class='td_center'>${item.EDU_RM}</td>
					<td class='td_center'>${item.USE_YN}</td>	
					<td class='td_right'>${item.SUBJT_TIME}</td>
					<td class='td_center'>${item.NPS_REASON}</td>
					<td class='td_center'>${item.NPS_NO_REASON}</td>
					<td class='td_center'>${item.SALES_TALK}</td>
					<td class='td_center'>${item.COM_CLUB_INFO}</td>
					<td class='td_center'>${item.OTHER_FEEDBACK}</td>	
					<td>${item.ERROR_INFO}</td>
					<td class='td_center'>${item.UPDT_USER}</td>
					<td class='td_center'>${item.UPDT_DTIME}</td>		
				</tr>			
			</c:forEach>			
		</tbody>
	</table>  
	</td>
  </tr>
</table>
</body>
</html>