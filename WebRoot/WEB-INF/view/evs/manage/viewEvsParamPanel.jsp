<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsParamPanel_search",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsParamPanelForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewEvsParamPanelResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsParamPanelForm",navTab.getCurrentPanel()).submit();
	});
});
</script>
<div class="pageHeader">
	<form id="viewEvsParamPanelForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsParamPanel" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsParamPanelResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" id="viewEvsParamPanel_currentIndex" name="currentIndex" value="${currentIndex }">
						<input type="hidden" name="evsType" value="${evsType }">
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewEvsParamPanel_search" href="#">
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
					<li><a href="/evs/manage/viewEvsParamInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&currentIndex=0&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsParamPanel.KAOHEDENGJISHUXINGDINGYI.a"/><!--考核等级 属性 定义--></span></a></li>
					<li><a href="/evs/manage/viewEvsParamInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_PARAM_TYPE=ITEM&currentIndex=1&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsParamPanel.KAOHEXIANGMUDENGJIDINGYI.a"/><!--考核项目等级 定义--></span></a></li>
					<li><a href="/evs/manage/viewEvsParamInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_PARAM_TYPE=LIST&currentIndex=2&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsParamPanel.KAOHEDUIXIANGLEIXINGDINGYI.a"/><!--考核对象 类型 定义--></span></a></li>
					<li><a href="/evs/manage/viewEvsParamInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_PARAM_TYPE=LIST&currentIndex=3&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsParamPanel.KAOHEBIAOLEIXINGDINGYI.a"/><!--考核表 类型 定义--></span></a></li>
					<li><a href="/evs/manage/viewEvsParamInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_PARAM_TYPE=GROUP&currentIndex=4&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsParamPanel.KAOHEQUNDINGYI.a"/><!--考核群定义--></span></a></li>
					<li><a href="/evs/manage/viewEvsParamInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_PARAM_TYPE=FAMILY&currentIndex=5&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsParamPanel.KAOHEZHIYEQUNDINGYI.a"/><!--考核职业 群 定义--></span></a></li>
					<%-- <li><a href="/evs/manage/viewEvsParamInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_PARAM_TYPE=TARGET&currentIndex=6&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsParamPanel.KAOHEZHIBIAOQUNDINGYI.a"/><!--考核指标 群 定义--></span></a></li> --%>
					<li><a href="/evs/manage/viewEvsParamInfoList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&currentIndex=7&seach_ACTIVITY=${ACTIVITY}" class="j-ajax"><span><spring:message code="evs.viewEvsParamPanel.KAOHEZHEJIZHUNDINGYI.a"/><!--考核者 基准 定义--></span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div id="viewEvsParamInfoList_0"></div>
			<div id="viewEvsParamInfoList_1"></div>
			<div id="viewEvsParamInfoList_2"></div>
			<div id="viewEvsParamInfoList_3"></div>
			<div id="viewEvsParamInfoList_4"></div>
			<div id="viewEvsParamInfoList_5"></div>
			<!-- <div id="viewEvsParamInfoList_6"></div> -->
			<div id="viewEvsParamInfoList_7"></div>
		</div>
	</div>
</div>
