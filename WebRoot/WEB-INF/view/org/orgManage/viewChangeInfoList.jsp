<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<input type="hidden" id="viewChangeInfoList_currentIndex" value="${currentIndex }">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a id="viewChangeInfoList_panel1" href="/org/orgManage/viewChangeDetailInfoList?DEPTNO=D11EV&RESUME_NO=${RESUME_NO }&currentIndex=0" class="j-ajax"><span><spring:message code="org.title.ORG_INFO" /><!-- 组织信息 --></span></a></li>
					<li><a id="viewChangeInfoList_panel2" href="/org/orgManage/viewChangeDetailInfoList?DEPTNO=D11EV&RESUME_NO=${RESUME_NO }&currentIndex=1" class="j-ajax"><span><spring:message code="org.title.PERSON_INFO" /><!-- 个人信息 --></span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:560px;">
			<div></div>
			<div></div>
		</div>
	</div>
</div>
