<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form method="post" action="/ar/attendanceMintenance/viewAddAffirmList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 关键字 --><spring:message code="ar.viewkeeperlist.title.keyword"/></td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/>
				<input type="hidden" name="limit" value="${limit}"/></td>
				<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></td>
				<td>
					<ait:deptList name="seach_DEPTNO_TREE" limit="${limit}" id="viewAddAffirmList_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO_TREE" limit="${limit}" id="viewAddAffirmList_seachDept" selected="${DEPTNO_TREE}"/>
					<input type="hidden" name="empidStr" value="${empidStr}"/>
					<input type="hidden" name="personidStr" value="${personidStr}"/>
				</td>
		        <td>在职状态</td>
				<td>
					<c:if test="${isEmployeement eq 1}">
						<input type="hidden" name="isEmployeement" value="${isEmployeement}"/>在职
					</c:if>
					<c:if test="${isEmployeement ne 1}">
		 				<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</c:if>	
				</td>
			</tr>
		<tr>
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="viewAddAffirmList_limit" name="limit" value="${limit}">
						<input type="hidden" id="viewAddAffirmList_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="viewAddAffirmList_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="${limit}" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,viewAddAffirmList_seach_JobTypeGroupNo,viewAddAffirmList_seach_EmpTypeCodeNo,viewAddAffirmList_seach_CPNY,viewAddAffirmList_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="viewAddAffirmList_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="${limit}"/>
						</td>
		          </tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="130" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="empId"><!-- 员工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th orderfield="empName"><!-- 员工姓名 --><spring:message code="public.title.name"/></th>
				<th orderfield="empDept"><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
				<th orderfield="empDept">人员类型</th>
				<th orderfield="empDept">在职区分</th>
				<th width="80"><!-- 确认 --><spring:message code="hr.viewPersonalInfo.title.AFFIRM"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper">
			<tr>
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>${keeper.EMP_TYPE_NAME}</td>
				<td>${keeper.EMP_OFFICE_NAME}</td>
				<td>
					<a class="btnSelect" 
						href="javascript:
							$.bringBackById({
							    ${personInfoStr}:'${keeper.LOCAL_NAME}/${keeper.PostGradeBNo}/${keeper.DEPTNONAME}',
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								STAT_NO:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}',
								${empidStr}:'[${keeper.EMPID}]-${keeper.LOCAL_NAME}', 
								${personidStr}:'${keeper.PERSON_ID}'
							})" title="<spring:message code='ar.alert.message.viewattendencekeeper.chazhaodaihui'/>"><!-- 选择 --><spring:message code="public.title.choose"/></a>
				</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceMintenance/viewAddAffirmList">
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