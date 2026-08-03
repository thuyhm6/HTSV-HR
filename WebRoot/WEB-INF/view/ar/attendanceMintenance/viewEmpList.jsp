<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form method="post" action="/ar/attendanceMintenance/viewEmpList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>关键字:</td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td>部门:</td>
				<td>
					<ait:deptTree name="seach_DEPTNO_TREE" limit="ar" selected="${DEPTNO_TREE}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" multLookup="orgId" >选择带回</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="orgId" /></th>
				<th orderfield="empId">员工号</th>
				<th orderfield="empName">员工姓名</th>
				<th orderfield="empDept">部门</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper">
			<tr>
				<td><input type="checkbox" name="orgId" 
						value="{personId:'${keeper.PERSON_ID}', 
								empName:'${keeper.LOCAL_NAME}', 
								empId:'${keeper.EMPID}',
								deptName:'${keeper.DEPT_NAME}'}"/></td>
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceMintenance/viewEmpList">
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span>条，共${totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>