<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	$(document).ready(function() { 
		if('${totalCount}' == 1){
			$("#forDbClick",$.pdialog.getCurrent()).dblclick();
		}
	}); 
</script>
<div class="pageHeader">

	<form method="post" action="/pa/workManagement/viewEmpForArAddPopList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="PAY_SCHEDULE_NO" value="${PAY_SCHEDULE_NO}" />
		<input type="hidden" name="searchForFlag" value="${searchForFlag }"/>
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 部门： --> <spring:message
			code="hr.viewPersonalInfo.title.DEPTNAME" /> 
		</td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="${limit}" id="viewEmpCalendarList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" limit="${limit}" id="viewEmpCalendarList_seachDept" selected="${DEPTNO}"/>
		</td>
		<td><!-- 工号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td><input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
			<input type="hidden" name="limit" value="${limit}" />
			<input type="hidden" name="empidStr" value="${empidStr}"/>
			<input type="hidden" name="personidStr" value="${personidStr}"/>
			</td>
        <td>在职状态</td>
		<td>
			<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
		</td>
	</tr>
</table>
<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
</div>
</div>
</form>
</div>
<div class="pageContent">
	<table class="table" layoutH="130" targetType="dialog" width="100%">
				<thead>
					<tr>
						<th>No</th>
						<th orderfield="empName"><!-- 员工姓名 --><spring:message code="public.title.name"/></th>
						<th orderfield="empId"><!-- 员工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
						<th>职务</th>
						<th orderfield="empDept"><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
						<th>等级</th>
						<th orderfield="empOffice">任职状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${personList}" var="keeper" varStatus="i">
						<tr id='forDbClick' ondblclick="javascript:
										$.bringBackById({
										empid:'${keeper.EMPID}',
										personid:'${keeper.PERSON_ID}',
										deptno:'${keeper.DEPTNO}',
										${empidStr}:'${keeper.LOCAL_NAME}', 
										${personidStr}:'${keeper.PERSON_ID}'
										})">
							<td>${i.count}</td>
							<td>${keeper.LOCAL_NAME}</td>
							<td>${keeper.EMPID}</td>
							<td>${keeper.MAIN_BUSINESS}</td>
							<td>${keeper.DEPT_NAME}</td>
							<td>${keeper.POST_GRADE}</td>
							<td>${keeper.EMP_OFFICE_NAME}</td>
						</tr>
					</c:forEach>	
				<c:if test="${totalCount == 0 }">
					<tr>
						<td style="text-align: left"colspan="7">没有任何数据</td>
					</tr>
				</c:if>
				</tbody>
			</table>
	<%-- <c:set value="/pa/workManagement/viewEmpForPopList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%> --%>
	
	<form id="pagerForm" method="post" action="/pa/workManagement/viewEmpForPopList">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>