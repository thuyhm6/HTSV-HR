<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent" style="padding: 0px;">
<div class="tabs" eventType="click" currentIndex="${currentIndex }">
<input type="hidden" id="viewAddRecruitInfoPanel_currentIndex"
	value="${currentIndex }">
<div class="tabsHeader">
<div class="tabsHeaderContent">
<ul>
	<li><a
		href="/hrm/recruitManage/viewAddRecruitInfo?PERSON_ID=${PERSON_ID }&currentIndex=0"
		class="j-ajax"> <span><spring:message
		code="hrm.recruitManage.Basic_information" /><!-- 基本信息 --></span> </a></li>
	<li><a
		href="/hrm/recruitManage/viewAddRecruitInfo?PERSON_ID=${PERSON_ID }&currentIndex=1"
		class="j-ajax"><span><spring:message
		code="hrm.recruitManage.Additional_information" /><!-- 附加信息 --></span></a></li>
	<li><a
		href="/hrm/recruitManage/viewAddRecruitInfo?PERSON_ID=${PERSON_ID }&currentIndex=2&seq=${SEQ2}"
		class="j-ajax"><span><spring:message
		code="hrm.recruitManage.educational_information" /><!-- 教育信息 --></span></a></li>
	<li><a
		href="/hrm/recruitManage/viewAddRecruitInfo?PERSON_ID=${PERSON_ID }&currentIndex=3&seq=${SEQ3}"
		class="j-ajax"><span><spring:message
		code="hrm.recruitManage.Experience_information" /><!-- 经历信息 --></span></a></li>
	<li><a
		href="/hrm/recruitManage/viewAddRecruitInfo?PERSON_ID=${PERSON_ID }&currentIndex=4&seq=${SEQ4}"
		class="j-ajax"><span><spring:message
		code="hrm.recruitManage.Family_information" /><!-- 家庭信息 --></span></a></li>
	<li><a
		href="/hrm/recruitManage/viewAddRecruitInfo?PERSON_ID=${PERSON_ID }&currentIndex=5"
		class="j-ajax"><span><spring:message
		code="hrm.recruitManage.Photo_collection" /><!-- 照片采集 --></span></a></li>
</ul>
</div>
</div>
<div class="tabsContent" style="height: 565px;">
<div></div>
<div></div>
<div></div>
<div></div>
<div></div>
<div></div>
</div>
</div>
</div>
