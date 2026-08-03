<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<table class="table"  id="daTable" width="100%" layoutH="60">
		<thead>
			<tr>
				<th>No.</th>
				<th><!-- 工号--><spring:message code="alert.pa.pasalarycanshu.shehao" /></th>
				<th><!-- 姓名 --><spring:message code="alert.pa.pasalarycanshu.xingming" /></th>
				<th><!-- 部门 --><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName" /></th>
				<th><!-- 职级 --><spring:message code="ess.trans.title.postGradeName" /></th>
				<th><!-- 状态 --><spring:message code="ar.attendanceView.viewNoSwipingCard.status" /></th>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<th><!-- 分数 --><spring:message code="hr.viewCompetence.title.MARK" /></th>
				</c:if>
				<th><!-- 面试意见 --><spring:message code="hr.hrm.empinfo.Interview.opinion" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${interviewAffirmList}" var="infoList"
			varStatus="i">
			<tr>
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${infoList.EMPID}</td>
				<td style="text-align: center">${infoList.LOCAL_NAME}</td>
				<td style="text-align: center">${infoList.DEPTNAME}</td>
				<td style="text-align: center">${infoList.POSTGRADENAME}</td>
				<td style="text-align: center">
					<c:choose>
						<c:when test="${infoList.AFFIRM_FLAG == 0}"><spring:message code="ess.affirmApply.title.remark.shenpizhong" /></c:when>
						<c:when test="${infoList.AFFIRM_FLAG == 1}"><spring:message code="ess.affirmApply.title.remark.tongguo" /></c:when>
						<c:when test="${infoList.AFFIRM_FLAG == 2}"><spring:message code="ess.affirmApply.title.remark.foujue" /></c:when>
					</c:choose>
				</td>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<td style="text-align: center">${infoList.SCORE}</td>
				</c:if>
				<td style="text-align: center">${infoList.AFFIRM_REMARK}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>