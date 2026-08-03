<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewArDetailDataExcelImportResult" name="viewArDetailDataExcelImportResult"
			action="/ar/attendanceMintenance/viewImportArDetailTempList" 
			onsubmit="return navTabSearch(this);"
			method="post" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->
				</td>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<td>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->
				</td>
				<td>							
					${errCnt}
				</td>
				<td>
					<spring:message code="org.title.IS_ERROR"/><!-- 出错与否-->
				</td>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>>全部</option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>>是</option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>>否</option>
					</select>
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
	<div class="formBar">
		<ul>			
			<li>
				<a class="buttonActive" onclick="excelimport_org0203();">
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a></li>
			<li>
				<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
					href="/org/orgManage/submitImportExcelDeptData" title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="220" nowrapTD="false">
		<thead>
			<tr>
				<th>Line<!--excel行号--></th>
				<th><spring:message code="org.orgManage.title.deptId"/><!--部门ID--></th>
				<th><spring:message code="org.title.ORG_NAME_LOCAL"/><!--部门中文名--></th>
				<th><spring:message code="org.title.ORG_NAME_KO"/><!--部门韩文名--></th>
				<th><spring:message code="org.orgManage.SUPERIOR_DEPT_ID.Z"/><!--上级部门ID--></th>
				<th><spring:message code="org.orgManage.SUPERIOR_DEPT_NAME.Z"/><!--上级部门名称--></th>
				<th><spring:message code="org.title.DEPT_TYPE"/><!--组织类型--></th>
				<th><spring:message code="org.orgManage.ORGANIZATIONAL_LEVEL.Z"/><!--组织等级--></th>
				<th><spring:message code="org.orgManage.DEPARTMENT_EMPID.Z"/><!--部门长社号--></th>
				<th><spring:message code="org.title.IS_PART_TIME"/><!--兼职与否--></th>
				<th><spring:message code="ess.trans.title.workArea"/><!--工作地--></th>
				<th><spring:message code="org.orgManage.DEPT_START_DATE.Z"/><!--部门开始日期--></th>
				<th><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th><spring:message code="org.title.UPLOAD_BY"/><!--上传人--></th>
				<th><spring:message code="org.title.UPLOAD_DATE"/><!--上传时间--></th>			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paDeptTempList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.LINE_ID}</td>
					<td class='td_center'>${item.DEPTNO}</td>
					<td class='td_center'>${item.ORG_NAME_LOCAL}</td>
					<td class='td_center'>${item.ORG_NAME_KO}</td>
					<td class='td_center'>${item.PARENT_DEPT_NO}</td>
					<td class='td_center'>${item.PARENT_DEPT_NAME}</td>
					<td class='td_center'>${item.DEPT_TYPE_NAME}</td>
					<td class='td_center'>${item.ORG_LEVEL}</td>
					<td class='td_center'>${item.MANAGER_EMP_ID}</td>
					<td class='td_center'>${item.IS_PART_TIME}</td>
					<td class='td_center'>${item.WORK_AREA_NAME}</td>
					<td class='td_center'>${item.DATE_CREATED}</td>
					<td style="text-align:left">${item.UPLOAD_ERROR_MSG}</td>
					<td class='td_center'>${item.UPLOAD_BY}</td>
					<td class='td_center'>${item.UPLOAD_DATE}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</div>