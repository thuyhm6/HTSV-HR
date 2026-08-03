<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salary/viewPaInputItemDataPersonList" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!--部门-->
				     <spring:message code="public.title.deptName"/>：
				     <ait:deptTree name="seach_DEPTNO" limit="pa" selected="${DEPTNO}"/>
				</td>
				<td><!--工号/姓名-->
					<spring:message code="public.title.empId"/><!--工号-->/
					<spring:message code="public.title.name"/><!--姓名-->：
					<input type="text" name="seach_KEY" value="${KEY}" />
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
				<th width="10%"><!--序号-->
					<spring:message code="ar.viewcycle.title.xuhao"/>
				</th>
				<th width="25%"><!--姓名-->
					<spring:message code="public.title.name"/><!--点击名字进行添加-->
					<font color="red">(<spring:message code="alert.message.pa.insurance.onclickNameForAdd"/>)</font>
				</th>
				<th width="30%"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="35%"><!--部门-->
					<spring:message code="public.title.deptName"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paInputItemPersonList}" var="paPerson" varStatus="ix">
				<tr target="PERSON_ID" rel="${paPerson.PERSON_ID}">
					<td>${ix.index+1 }</td>
					<td style="padding-top:5px;">
						<a class="update" href="/pa/salary/addPaInsPersonalInputView?pageNum=1&seach_PERSON_ID=${paPerson.PERSON_ID}
							&seach_CPNY_ID=${paPerson.CPNY_ID}" target="dialog" mask="true" width="800" height="600" style="overflow:auto;"><span>${paPerson.LOCAL_NAME}</span>
						</a>
					</td>
					<td>${paPerson.EMPID}</td>
					<td>${paPerson.DEPT_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewPaInputItemDataPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>