<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function importFSESalaryData(){
	$("#importExcelDialog_fse").attr('href','/pa/excelImport/importExcelData?importFunName=/importFSESalaryData');
	$("#importExcelDialog_fse").click();
}

$(document).ready(function() {
		var message = "${message}";
		if (message != '') {
			alertMsg.info("${message}");
		}
	});
function downloadImportTemplate_fse() {//下载导入模板
        var form = document.getElementById("pageForm_fse");
        form.action="/pa/salarycode/downloadTempleteFSESalary";
        form.submit();
		//document.form.action = "/hrm/jobType/downloadTempleteJobType";
		//document.form.submit();
	}
</script>
<div style="display: none">
<a id="importExcelDialog_fse" rel="update" mask="true" width="400"
	height="200" target="dialog" />
<a id="importExcel_pa2011" href="#" target="navTab" mask="true">查看导入</a>
</div>
<form id="pageForm_fse" onsubmit="return navTabSearch(this);"
	action="/pa/salarycode/viewFSESalaryItemList" method="post" rel="pagerForm">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					 <td>法人:
				</td>
			<td>
				<c:if test="${authority eq '1'}">
				
				<select id="seach_CPNY_ID" name="defaultCpny" onchange="reloadPage();">
				    <option value="">全部</option>
					<c:forEach items="${companyList}" var="item" varStatus="i">
						<option value="${item.CPNY_ID }" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${authority eq '0'}">
					<input type="text" id="seach_CPNY_ID" name="seach_defaultCpny" value="${defaultCpny}" readonly="readonly"/>
						</c:if>
	   			 </td>
				</tr>
			</table>
			<div class="subBar">
				<ul>

					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!-- 查询 -->
									<spring:message code="button.search" />
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	<div class="formBar">
		<ul class="toolBar">
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="exportExcel"
							onclick="downloadImportTemplate_fse();">
							<spring:message code="pa.insurance.title.downloadImportTemplate" />
							<!--下载导入模板-->
						</button>
					</div>
				</div></li>
			<li><a class="buttonActive" onclick="importFSESalaryData()"> <span><spring:message
							code="ar.addempshift.title.excelimport" /> <!-- EXCEL导入 --> </span> </a></li>
		</ul>
	</div>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th>
					公司</th>
			    <th width="100">社号</th>
			    <th width="100">姓名</th>
			    <th width="100">支付月</th>
				<th>
					<!--人员类型--> <spring:message code="is.company.title.PERSON_TYPE" />
				</th>
				<th>
					PA_AREA_CD
				</th>
				<th>
					工资项目
				</th>
				<th width="100">项目金额</th>
				<th width="100">ADD_FLAG</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${jobTypeList}" var="parameter" varStatus="i">

				<tr align="center" onclick="band('#f4f7fa','black')" target="sid">
					<td>${parameter.CPNY_ID}</td>
					<td>${parameter.EMPID}</td>
					<td>${parameter.LOCAL_NAME}</td>
					<td>${parameter.PA_MONTH }</td>
					<td>${parameter.EMP_TYPE_CODE }</td>
					<td>${parameter.PA_AREA_CD }</td>
					<td>${parameter.SALARY_ITEM }</td>
					<td>${parameter.SALARY_ITEM_FEE }</td>
					<td>${parameter.ADD_FLAG }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
  </div>
</form>
<c:set value="/pa/fsesalarycode/viewFSESalaryList" var="pageUrl" />
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>


