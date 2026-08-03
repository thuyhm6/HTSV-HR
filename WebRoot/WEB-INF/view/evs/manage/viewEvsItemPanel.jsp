<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsItemPanel_search",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsItemPanelForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewEvsItemPanelResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsItemPanelForm",navTab.getCurrentPanel()).submit();
	});
});
</script>
<div class="pageHeader">
	<form id="viewEvsItemPanelForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsItemPanel" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsItemPanelResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" id="viewEvsItemPanel_currentIndex" name="currentIndex" value="${currentIndex }">
						<input type="hidden" name="evsType" value="${evsType }">
					</td>
					<td><spring:message code="evs.viewEvsItemPanel.LIDUQUN.a"/><!--力度群--></td>
					<td>
		 				<ait:SelectSyCodeByCpnyID id="seach_GROUP_NO" name="seach_GROUP_NO" parentNo="14015376" selected="${GROUP_NO}" limit="all"/>
					</td>
					<td><spring:message code="evs.viewEvsAffirmorSetup.PINGJIAQUN.a"/><!--评价群--></td>
					<td>
						<ait:evsCodeMulti id="seach_EVS_GROUP" name="seach_EVS_GROUP_NAME" resumeSeq="${RESUME_SEQ}" limit="EVS_GROUP" selected="${EVS_GROUP}" selectedNm="${EVS_GROUP_NAME}"/>
					</td>
					<td><spring:message code="evs.viewEvsParamInfoList.PINGJIAZHIYEQUN.a"/><!--评价职业群--></td>
					<td>
						<ait:evsCodeMulti id="seach_EVS_OCC_GROUP" name="seach_EVS_OCC_GROUP_NAME" resumeSeq="${RESUME_SEQ}" limit="EVS_OCC_GROUP" selected="${EVS_OCC_GROUP}" selectedNm="${EVS_OCC_GROUP_NAME}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewEvsItemPanel_search" href="#">
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
					<li><a href="/evs/manage/viewEvsItemList?seach_evsType=${evsType }&seach_RESUME_SEQ=${RESUME_SEQ }&seach_GROUP_NO=${GROUP_NO }&seach_EVS_GROUP=${EVS_GROUP }&seach_EVS_OCC_GROUP=${EVS_OCC_GROUP }&seach_ITEM_TYPE=CPNY&currentIndex=0" class="j-ajax"><span><spring:message code="evs.viewEvsIndex.LILIANGXIANGMUDINGYI.a"/><!--力量项目定义--></span></a></li>
					<li><a href="/evs/manage/viewEvsItemList?seach_evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&seach_GROUP_NO=${GROUP_NO }&seach_EVS_GROUP=${EVS_GROUP }&seach_EVS_OCC_GROUP=${EVS_OCC_GROUP }&seach_ITEM_TYPE=DEPT&currentIndex=1" class="j-ajax"><span><spring:message code="evs.viewEvsItemPanel.ZHIDINGLILIANGXIANGMU.a"/><!--指定力量项目--></span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div id="viewEvsItemList_0"></div>
			<div id="viewEvsItemList_1"></div>
		</div>
	</div>
</div>
