<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<div class="pageHeader">

	<form method="post" action="/hrm/empinfo/viewEmpIdList?navTabId=${request_navTabId}" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
	<input type="hidden" value="${viewEmpIdListColnum}" id="viewEmpIdListColnum" name="viewEmpIdListColnum"/>
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
							  <input type="hidden" name="seach_IDCARD_NO" value="${IDCARD_NO}"/>
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门--><ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" />
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.ORDERTYPE"/>
					<!-- 排序 -->
						<select id="seach_orderType" name="seach_orderType">
							
							<option value="">请选择</option>
							
							<c:forEach items="${sortNameNoList}" var="sort">
								<option value="${sort.SORT_COLUMN_ID }" <c:if test="${sort.SORT_COLUMN_ID==orderType }">selected</c:if>>${sort.SORT_NAME }</option>
							</c:forEach>
							
						 </select>
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME"/>
					<!--在职区分-->
					<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" parentNo="15118" cnpyID="${defaultCpny}"limit="all" selected="${EMP_OFFICE}"/>
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.ASCORDESC"/>
					<!--升/降序-->
						<input id="sort"  name="sort" <c:if test="${sortOrGradeDown=='sort'}">checked="checked"</c:if> type="checkbox" onclick="document.getElementById('gradeDown').checked=this.checked?false:true;document.getElementById('seach_sortOrGradeDown').value='sort';" /> 
						<input id="gradeDown" name="gradeDown" <c:if test="${sortOrGradeDown=='gradeDown'}">checked="checked"</c:if> type="checkbox" onclick="document.getElementById('sort').checked=this.checked?false:true;document.getElementById('seach_sortOrGradeDown').value='gradeDown';" />
					    <input id="seach_sortOrGradeDown" name="seach_sortOrGradeDown" type="hidden" value="${sortOrGradeDown }">
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
	

	
	<table class="table" width="100%" layoutH="138">
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
					<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
					<!--身份证号-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.DEPT_DISTINGUISH_NO"/>
					<!--部门区分-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.AFFIRM"/>
					<!--确认-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${empList}" var="item">
			
				<tr target="EMPID" rel="${item.EMPID}" ondblclick="F_HR_ShowMore(${item.PERSON_ID},'${request_navTabId}')">
					<td>${item.EMPID}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.IDCARD_NO}</td>
					<td>${item.DEPTNAME}</td>
					<td>${item.DEPT_DISTINGUISH_NO}</td>
					<td>
						<img src="/resources/images/1.gif" onclick="F_HR_ShowMore(${item.PERSON_ID},'${request_navTabId}')" style="cursor:hand">
					</td>
				</tr>

			</c:forEach>
			
		</tbody>
	</table>
	
	<form id="pagerForm" method="post" action="/hrm/empinfo/viewEmpIdList?navTabId=${request_navTabId}">
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
	</form>
	
	
</div>
