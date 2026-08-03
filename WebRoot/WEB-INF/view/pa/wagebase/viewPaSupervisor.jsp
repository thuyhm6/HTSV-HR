<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/wagebase/viewPaSupervisor" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td><spring:message code="public.title.empId"/><!--工号-->
				/<spring:message code="public.title.name"/><!--姓名--></td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td><spring:message code="public.title.deptName"/><!--部门--></td>
				<td>
					<ait:deptList name="seach_DEPTNO" limit="pa"  id="viewPaSupervisor_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" limit="pa" id="viewPaSupervisor_seachDept" selected="${DEPTNO}"/>
				</td>
						<td><spring:message code="org.title.OFFICE_NAME"/><!--在职状态-->
						</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
				</tr>
				<%-- <tr>
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="viewPaSupervisor_limit" name="limit" value="pa">
						<input type="hidden" id="viewPaSupervisor_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="viewPaSupervisor_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,viewPaSupervisor_seach_JobTypeGroupNo,viewPaSupervisor_seach_EmpTypeCodeNo,viewPaSupervisor_seach_CPNY,viewPaSupervisor_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="viewPaSupervisor_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="pa"/>
						</td>
			</tr> --%>
			
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
<div class="pageContent">
	
	<c:set value="navTab" var="add_tab"/>
	<c:set value="/pa/wagebase/addPaSupervisorView" var="add_Url"/>
	<c:set value="/pa/wagebase/deletePaSupervisorInfo?PERSON_ID={personID}" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="/pa/wagebase/updatePaSupervisorView?PERSON_ID={personID}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="100"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="80"><spring:message code="sys.basic.title.createDate"/><!--创建日期--></th>
				<th width="80"><spring:message code="sys.basic.title.createBy"/><!--创建者--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paSupervisorList}" var="keepers">
			
				<tr target="personID" rel="${keepers.PERSON_ID}">
					<td>${keepers.EMPID}</td>
					<td>${keepers.HRNAME}</td>
					<td>${keepers.DEPT_NAME}</td>
					<td>${keepers.CREATE_DATE}</td>
					<td>${keepers.CREATED_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/wagebase/viewPaSupervisor" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
