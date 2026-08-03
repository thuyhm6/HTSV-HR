<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageContent" layoutH="10" >
		<div class="pageFormContent nowrap">
		<span style="font-size: 15px;">Total:${teacherInformationListCount }</span>
		<table  class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%">NO.</td>
		<td class="td_title" width="5%"><spring:message code="edu.teacherManager.JIANGSHIXINGMING.a"/><!--讲师姓名--></td>
		<td class="td_title" width="5%"><spring:message code="ar.attendanceView.viewNoSwipingCard.status"/><!--状态--></td>
		<td class="td_title" width="5%"><spring:message code="edu.teacherManager.JIANGSHIJIBIE.a"/><!--讲师级别--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.title.startTime"/><!--开始时间--></td>
		<td class="td_title" width="5%"><spring:message code="ess.infoApply.end_time"/><!--结束时间--></td>
		</tr>
		<c:forEach items="${teacherInformationList}" var="t" varStatus="i">
		<tr>
		<td class="td_type" width="1%" style="text-align:center;">${i.count }.</td>
		<td class="td_type" width="5%">${t.TEACHER_NAME }</td>
		<td class="td_type" width="5%">${t.TEACH_STATUS_CODE_NAME }</td>
		<td class="td_type" width="5%">${t.TEACH_LEVEL_CODE_NAME }</td>
		<td class="td_type" width="5%">${t.START_DATE }</td>
		<td class="td_type" width="5%">${t.END_DATE }</td>
		</tr>
		</c:forEach>
		</table>
		</div>
</div>
