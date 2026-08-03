<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div class="tabs" eventType="click" currentIndex="${currentIndex }">
		<input type="hidden" id="viewHistoryOrgPanel_currentIndex" value="${currentIndex }">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="/org/orgManage/viewHistoryDetailInfoList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=0" class="j-ajax"><span><spring:message code="org.title.DEPT_INFO" /><!-- 部门信息 --></span></a></li>
					<li><a href="/org/orgManage/viewHistoryDetailInfoList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=1" class="j-ajax"><span><spring:message code="org.title.DEPT_EMP" /><!-- 部门员工 --></span></a></li>
					<li><a href="/org/orgManage/viewHistoryDetailInfoList?DEPTNO=${DEPTNO }&RESUME_NO=${RESUME_NO }&SON_FLAG=${SON_FLAG }&currentIndex=2" class="j-ajax"><span><spring:message code="org.title.DEPT_LIST" /><!-- 部门列表 --></span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:565px;">
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>
</div>
