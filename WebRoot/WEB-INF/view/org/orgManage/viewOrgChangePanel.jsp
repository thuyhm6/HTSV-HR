<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<input type="hidden" id="viewOrgChangePanel_currentIndex" value="${currentIndex }">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="/org/orgManage/viewOrgChangeList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&currentIndex=0" class="j-ajax"><span><spring:message code="org.title.dept" /><!-- 部门 --></span></a></li>
					<li><a href="/org/orgManage/viewOrgChangeList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&currentIndex=1" class="j-ajax"><span><spring:message code="org.title.DEPT_MANAGEE" /><!-- 部门经理 --></span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:560px;">
			<div></div>
			<div></div>
		</div>
	</div>
</div>
