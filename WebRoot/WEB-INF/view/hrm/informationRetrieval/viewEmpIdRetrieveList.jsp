<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageHeader">

	<form method="post" action="/hrm/informationRetrieval/viewEmpIdRetrieveList?navTabId=${request_navTabId}" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
	
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
		
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					 <spring:message code="hr.viewPersonalInfo.title.EMPID"/><!--社号--><input type="text" name="seach_EMPID" value="${EMPID}"/>
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名--><input type="text" name="seach_LOCAL_NAME" value="${LOCAL_NAME}"/>
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门--><ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	

	
	<table class="table" width="100%" layoutH="188">
		<thead>
			<tr>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/><!--社号-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.AFFIRM"/>
					<!--确认-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${EmpIdRetrieveList}" var="item">
			
				<tr target="EMPID" rel="${item.EMPID}" ondblclick="empid_personel(${item.PERSON_ID},'${item.EMPID}','${item.LOCAL_NAME}','${item.DEPTNAME}')">
					<td>${item.EMPID}</td>
					<td>${item.LOCAL_NAME}</td>
					
					<td>${item.DEPTNAME}</td>
					
					<td>
						<img src="/resources/images/1.gif" onclick="empid_personel(${item.PERSON_ID},'${item.EMPID}','${item.LOCAL_NAME}','${item.DEPTNAME}')" style="cursor:hand">
					</td>
				</tr>

			</c:forEach>
			
		</tbody>
	</table>
	
	<form id="pagerForm" method="post" action="/hrm/informationRetrieval/viewEmpIdRetrieveList?navTabId=${request_navTabId}">
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!--显示--></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->，<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
