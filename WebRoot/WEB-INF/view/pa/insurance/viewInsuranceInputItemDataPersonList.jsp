<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/insurance/viewInsuranceInputItemDataPersonList" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!--部门-->
				     <spring:message code="public.title.deptName"/>：
				     <ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
				</td>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->/
					<spring:message code="public.title.name"/><!--姓名-->：
					<input type="text" name="seach_KEY" value="${KEY}"/>
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
	<table class="table" width="99%" layoutH="145">
		<thead>
			<tr>
				<th width="10%"><!--序号-->
					<spring:message code="ar.viewcycle.title.xuhao"/>
				</th>
				<th width="25%"><!--姓名-->
					<spring:message code="public.title.name"/><!--点击名字进行添加-->
					<font color="red">(<spring:message code="alert.message.pa.insurance.onclickNameForAdd"/>)</font>
				</th>
				<th width="30%"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="35%"><spring:message code="public.title.deptName"/><!--部门--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${insuranceInputItemPersonList}" var="insurancePerson" varStatus="ix">
				<tr target="PERSON_ID" rel="${insurancePerson.PERSON_ID}">
					<td>${ix.index+1 }</td>
					<td><a class="update" href="/pa/insurance/addInsurancePersonalInputView?pageNum=1&seach_PERSON_ID=${insurancePerson.PERSON_ID}&seach_CPNY_ID=${insurancePerson.CPNY_ID}"
						   target="dialog" width="700" height="400" mask="true"><span>${insurancePerson.LOCAL_NAME}</span>
						</a>
					</td>
					<td>${insurancePerson.EMPID}</td>
					<td>${insurancePerson.DEPT_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/insurance/viewInsuranceInputItemDataPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>

</div>