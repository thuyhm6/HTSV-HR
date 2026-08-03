<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js">
</script>
<script type="text/javascript" src="script/jquery.easydrag.js">
</script>
<script>
function downloadImportTemplate_viweapplyleavebatchess0240() {
	var url = "/ess/infoApplyLeave/exportBatchLeaveModule?navTabId=ess0240";
	document.getElementById("exportExcel_viweapplyleavebatchess0240").href = encodeURI(url);
}
function excelimport_viewapplyleavebatchess0240() {
	$("#importExcelDialog_ess0240")
			.attr(
					'href',
					'/pa/excelImport/importExcelData?importFunName=/importLeaveTempess0240&LEAVE_TYPE=ess0240');
	$("#importExcelDialog_ess0240").click();
}
//-->

function downloadExl(url) {
	$('#viewDeptPersonalInfoList').attr("action", url);
	$('#viewDeptPersonalInfoList').attr("onsubmit", '');
	$('#viewDeptPersonalInfoList').submit();
	$('#viewDeptPersonalInfoList').attr("action",
			'/ess/viewDept/viewDeptPersonalInfoList');
	$('#viewDeptPersonalInfoList')
			.attr("onsubmit", 'return navTabSearch(this)');
}
</script>
<div class="panel">
	<h1>
		部门员工职务经历
	</h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewDeptPersonalInfoList" rel="pagerForm"
		method="post" id="viewDeptPersonalInfoList"
		name="viewDeptPersonalInfoList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>




					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="viewDeptPersonalInfoList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="viewDeptPersonalInfoList_seachDept"
							selected="${DEPTNO}" />
					</td>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>

						<input type="text" name="seach_KEY" value="${KEY}" />




					</td>




				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>

					<li>
						<a class="buttonActive"
							onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=74')"
							href="#"> <span>导出到Excel</span> </a>
					</li>
				</ul>


			</div>
		</div>
	</form>
</div>

<div class="pageContent">

	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm"
		method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch"
		onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);">
		<table class="table" width="100%" layoutH="235" nowrapTD="false">
			<thead>
				<tr>
					<th>
						NO
					</th>
					<th>
						<!--姓名 -->
						姓名
					</th>
					<th>
						<!--部门 -->
						部门
					</th>
					<th>
						<!--等级名-->
						等级名
					</th>
					<th>
						<!--GEN -->
						GEN
					</th>
					<th>
						<!--主要业务 -->
						主要业务
					</th>
					<th>
						<!--部门长姓名 -->
						部门长姓名
					</th>
					<th>
						<!--标准职务 -->
						标准职务
					</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${personList}" var="personList" varStatus="i">
					<tr target="sid" rel="${personList.PERSON_ID_ID}">
						<td style="text-align: center">

							${i.count}
						</td>
						<td style="text-align: center">

							<a
								href="/ess/viewDept/viewDeptPersonalInfo?EMPID=${personList.EMPID_ID}&LOCAL_NAME=${personList.LOCAL_NAME}"
								target="dialog" style="color: blue;">
								${personList.LOCAL_NAME} </a>
						</td>
						<td style="text-align: center">

							${personList.DEPT_NAME}
						</td>
						<td style="text-align: center">

							${personList.POST_GRADE_NO}
						</td>
						<td style="text-align: center">

							${personList.EMPID_ID}
						</td>
						<td style="text-align: center">

							${personList.MAIN_BUSINESS}
						</td>

						<td style="text-align: center">

							${personList.HEAD_DEPARTMENT}
						</td>
						<td style="text-align: center">

							${personList.POSITION_NO}
						</td>



					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<c:set value="/ess/viewDept/viewDeptPersonalInfoList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>