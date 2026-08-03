<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/bonus/viewBonusInputItemDataPersonList" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				     <spring:message code="public.title.deptName"/><!--部门-->：
				     <div><ait:deptTree name="seach_DEPTNO" limit="ar"/></div>
				</td>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->/
					<spring:message code="public.title.name"/><!--姓名-->：
					<input type="text" name="seach_KEY" value="${KEY }" />
				</td>				
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent" >
	<div class="panelBar">
	</div>
	<table class="table" width="99%" layoutH="138">
		<thead>
			<tr>
				<th width="20%"><spring:message code="pa.bonus.title.chineseName"/><!--中文姓名--></th>
				<th width="20%"><spring:message code="pa.bonus.title.lineNumber"/><!--行号--></th>
				<th width="30%"><spring:message code="pa.bonus.title.chinesePinyin"/><!--姓名拼音--></th>
				<th width="30%"><spring:message code="public.title.deptName"/><!--部门--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bonusPersonList}" var="bonusPerson" varStatus="ix">
				<tr target="PERSON_ID" rel="${bonusPerson.PERSON_ID}">
					<td><a class="update" href="/pa/bonus/addBonusPersonalInputView?seach_PERSON_ID=${bonusPerson.PERSON_ID}&seach_CPNY_ID=${bonusPerson.CPNY_ID}"
						   target="navTab"><span>${bonusPerson.LOCAL_NAME}</span>
						</a>
					</td>
					<td>${bonusPerson.EMPID}</td>
					<td>${bonusPerson.LOCAL_NAME}</td>
					<td>${bonusPerson.DEPT_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/bonus/viewBonusInputItemDataPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>

</div>