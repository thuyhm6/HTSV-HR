<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
		function viewPaiQianDiInfoModule(){
			$("#importExcelDialog_ar0117").attr('href','/pa/excelImport/importArExceptionAffirmInfoData?importFunName=/importArExceptionAffirmInfoDataModule');
			$("#importExcelDialog_ar0117").click();			
		}
		function CheckForm(form,navTabId){
			var $form=$(form);

			return true;
		}
		

</script>
<a id="importExcelDialog_ar0117"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ar0117"  href="#" target="navTab" mask="true">
<span style="display:none;">考勤决裁导入结果</span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceView/viewArAffirmExceptionList" method="post" rel="pagerForm">
	<div class="searchBar">
	   
		<table class="searchContent">
			<tr>
					<td><%--工号/姓名--%>
						<spring:message code="public.title.empIdAndName"/>
					</td>
					<td>
					    <input type="text" id="seach_KEY" name="seach_KEY" maxlength="25" value="${KEY }">
					</td>
				     <td><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" limit="ar" id="viewAnnualUsage_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" limit="ar" id="viewAnnualUsage_seachDept" selected="${DEPT_NO}"/>
					</td>
			</tr>
		</table>
		
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	
	<div class="formBar">
	<ul class="toolBar">
		
		<li><a href="#" onclick="viewPaiQianDiInfoModule()">
				<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
	 		</a>
	 	</li>
	 	<li><a id="exportExcel" href="/pa/excelExport/exportArExceptionAffirmModle">
				<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
			</a>
		</li>
	</ul>
</div>
			
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.salary.canShu.xianSHiShunXu"/><!--NO.--></th>
				<th width="120">工号</th>
				<th width="120">姓名</th>
				<th width="120">部门</th>
				<th width="120">开始时间</th>
				<th width="120">结束时间</th>
				<th width="120">最终修改人</th>
				<th width="120">最终修改时间</th>
				<th width="50">状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arException}" var="EXC" varStatus="i">
				<tr target="PQD_NO" rel="">
				    <td class="td_center">${i.index+1}</td>
					<td class="td_center">${EXC.EMPID}</td>
					<td class="td_center">${EXC.LOCAL_NAME}</td>
					<td class="td_center">${EXC.DEPTNAME}</td>
					<td class="td_center">${EXC.START_DATE}</td>
					<td class="td_center">${EXC.END_DATE}</td>
					<td class="td_center">${EXC.UPLOAD_BY}</td>
					<td class="td_center">${EXC.UPLOAD_DATE}</td>
					<c:if test="${EXC.ACTIVITY eq 1}">
					<td class="td_center">启用</td>
					</c:if>
					<c:if test="${EXC.ACTIVITY eq 0}">
					<td class="td_center">废弃</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>		
	<c:set value="/ar/attendanceView/viewArAffirmExceptionList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>