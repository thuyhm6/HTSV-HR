<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form method="post" action="/pa/wagebase/viewWageBearList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><spring:message code="ess.infoApply.title.kewWord"/><!--关键字--></td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td><spring:message code="public.title.deptName"/><!--部门--></td>
				<td>
			<ait:deptList name="seach_DEPTNO_TREE" cpnyId="${defaultCpny}" limit="hr" id="viewWageBearList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO_TREE" cpnyId="${defaultCpny}" limit="hr" id="viewWageBearList_seachDept" selected="${DEPTNO_TREE}"/>
				</td>
				<td>在职状态
				</td>
				<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
			</tr>
			<tr>
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="viewWageBearList_limit" name="limit" value="hr">
						<input type="hidden" id="viewWageBearList_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="viewWageBearList_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="hr" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,viewWageBearList_seach_JobTypeGroupNo,viewWageBearList_seach_EmpTypeCodeNo,viewWageBearList_seach_CPNY,viewWageBearList_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="viewWageBearList_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="hr"/>
						</td>
						
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!--查询--></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="130" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="empId"><spring:message code="public.title.empId"/><!--工号--></th>
				<th orderfield="empName"><spring:message code="public.title.name"/><!--姓名--></th>
				<th orderfield="empDept"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="80"><spring:message code="pa.insurance.title.confirm"/><!--确认--></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper">
			<tr>
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>
					<a class="btnSelect" 
						href="javascript:
							$.bringBack({
								personId:'${keeper.PERSON_ID}',
								empDept:'${keeper.DEPT_NAME}',
								empName:'${keeper.LOCAL_NAME}',
								empId:'${keeper.EMPID}'
							})" title='<spring:message code="pa.insurance.title.lookUpAndBack"/>'>
							<spring:message code="pa.insurance.title.choose"/><!--选择--></a>
				</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/pa/wagebase/viewWageBearList">
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
            <span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>			
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>