<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsSchedulePanel_search",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsSchedulePanelForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewEvsSchedulePanelResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsSchedulePanelForm",navTab.getCurrentPanel()).submit();
	});
});
</script>
<div class="pageHeader">
	<form id="viewEvsSchedulePanelForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsSchedulePanel" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsSchedulePanelResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" id="viewEvsSchedulePanel_currentIndex" name="currentIndex" value="${currentIndex }">
						<input type="hidden" name="evsType" value="${evsType }">
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewEvsSchedulePanel_search" href="#">
							<span><spring:message code="hrm.empinfo.QUERY"/><!--查询--></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="/evs/manage/viewEvsScheduleInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_SCHEDULE_TYPE=CPNY&currentIndex=0&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsSchedulePanel.GONGSIRICHENG.a"/><!--公司日程--></span></a></li>
					<li><a href="/evs/manage/viewEvsScheduleInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_SCHEDULE_TYPE=DEPT&currentIndex=1&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsSchedulePanel.BUMENRICHENG.a"/><!--部门日程--></span></a></li>
					<li><a href="/evs/manage/viewEvsScheduleInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_SCHEDULE_TYPE=EMP&currentIndex=2&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsSchedulePanel.GERENRICHENG.a"/><!--个人日程--></span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div id="viewEvsScheduleInfoList_0"></div>
			<div id="viewEvsScheduleInfoList_1"></div>
			<div id="viewEvsScheduleInfoList_2"></div>
		</div>
	</div>
</div>
