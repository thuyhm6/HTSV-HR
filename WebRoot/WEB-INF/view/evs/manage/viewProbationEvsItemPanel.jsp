<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewProbationEvsItemPanel_search",navTab.getCurrentPanel()).click(function(){
		$("#viewProbationEvsItemPanelForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewProbationEvsItemPanelResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewProbationEvsItemPanelForm",navTab.getCurrentPanel()).submit();
	});
});
</script>
<div class="pageHeader">
	<form id="viewProbationEvsItemPanelForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewProbationEvsItemPanel" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewProbationEvsItemPanelResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" id="viewProbationEvsItemPanel_currentIndex" name="currentIndex" value="${currentIndex }">
						<input type="hidden" name="evsType" value="${evsType }">
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewProbationEvsItemPanel_search" href="#">
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
					<li><a href="/evs/manage/viewProbationEvsItemList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&currentIndex=0" class="j-ajax"><span>力量项目定义</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div id="viewProbationEvsItemList_0"></div>
		</div>
	</div>
</div>
