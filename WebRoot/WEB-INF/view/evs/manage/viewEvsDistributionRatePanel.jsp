<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsDistributionRateInfoPanel_search",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsDistributionRateInfoPanelForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewEvsDistributionRateInfoPanelResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsDistributionRateInfoPanelForm",navTab.getCurrentPanel()).submit();
	});
});
</script>
<div class="pageHeader">
	<form id="viewEvsDistributionRateInfoPanelForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsDistributionRatePanel" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsDistributionRateInfoPanelResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" id="viewEvsDistributionRateInfoPanel_currentIndex" name="currentIndex" value="${currentIndex }">
						<input type="hidden" name="evsType" value="${evsType }">
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewEvsDistributionRateInfoPanel_search" href="#">
							<span><spring:message code="button.search"/><!--查询--></span>
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
					<li><a href="/evs/manage/viewEvsDistributionRateInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_IS_INCLUDE=1&currentIndex=0&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsDistributionRatePanel.FENPEILVJIZHUNGUANLI.a"/><!--分配率 基准 管理--></span></a></li>
					<li style="display:none;"><a href="/evs/manage/viewEvsDistributionRateInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&currentIndex=1&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsDistributionRatePanel.FENPEILVPINGJIAQUNGUANLI.a"/><!--分配率 评价群 管理--></span></a></li>
					<li style="display:none;"><a href="/evs/manage/viewEvsDistributionRateInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&currentIndex=2&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsDistributionRatePanel.FENPEILVLIWAIGUANLI.a"/><!--分配率 例外 管理--></span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div id="viewEvsDistributionRateInfoList_0"></div>
			<div id="viewEvsDistributionRateInfoList_1" style="display:none;"></div>
			<div id="viewEvsDistributionRateInfoList_2" style="display:none;"></div>
		</div>
	</div>
</div>
