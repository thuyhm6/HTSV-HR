<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewAttendanceKeeper" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td><!-- 工号/姓名--><spring:message code="public.title.empIdAndName"/></td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td><!-- 部门 --><spring:message code="public.title.deptName"/></td>
				<td>
					<ait:deptList name="seach_DEPTNO" limit="ar" id="viewAttendanceKeeper_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewAttendanceKeeper_seachDept" selected="${DEPTNO}"/>
				</td>
				<td><!-- 在职状态--><spring:message code="org.title.OFFICE_NAME"/></td>
				<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
				</td>
			</tr>
			<%-- <tr>
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="ar0202_limit" name="limit" value="ar">
						<input type="hidden" id="ar0202_seach_CPNY" name="seach_CPNY" value="${LoginUser.cpnyId}">
			<ait:SelectEmpTypeCode  id="ar0202_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,ar0202_seach_JobTypeGroupNo,ar0202_seach_EmpTypeCodeNo,ar0202_seach_CPNY,ar0202_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="ar0202_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>
			</tr>--%>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 检索 --><spring:message code="public.title.search"/></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	
	<c:set value="navTab" var="add_tab"/>
	<c:set value="/ar/attendanceSettings/addAttendanceKeeperView" var="add_Url"/>
	<c:set value="/ar/attendanceSettings/deleteAttendanceKeeperInfo?PERSON_ID={personID}" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="/ar/attendanceSettings/updateAttendanceKeeperView?PERSON_ID={personID}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<!-- orderField 与sql 的orderBy对应  orderDirection 为相反配置 class 控制图片-->
				<th width="80"><!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th width="80"><!-- 姓名 --><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
				<th width="100"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="80"><!-- 创建日期 --><spring:message code="ar.viewattendencekeeper.title.chuangjianriqi"/></th>
				<th width="80"><!-- 创建者 --><spring:message code="ar.viewattendencekeeper.title.chuangjianzhe"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${attendanceKeeperList}" var="keepers">
			
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
	<c:set value="/ar/attendanceSettings/viewAttendanceKeeper" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
