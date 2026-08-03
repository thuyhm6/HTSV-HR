<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
   //excel导出
	function exportDeptInfoList() {
		var sform = document.getElementById("searchForm_org_dept");
		alertMsg.confirm('<spring:message code="org.title.IS_IMPUT_DATE" />', {
			okCall : function() {
				//用于excel导出的表单参数处理
				var eForm = document.getElementById("excelExportForm_org"); 
				document.getElementById("orgLink").innerHTML = '<spring:message code="org.title.EXCEL_SETPWD" />';
				eForm.dept_id.value 			= sform.seach_dept_id.value;
				eForm.DEPTNO_TYPE.value 		= sform.seach_DEPTNO_TYPE.value;
				$("#importExcelDialog_org").attr('href', "/sys/encryptExcel"
						+"?exportFunName=/org/orgManage/viewDeptInfoListExcel"
						+"&navTabId=org0103"
						+"&formId=excelExportForm_org");
				$("#importExcelDialog_org").attr('width', "300");
				$("#importExcelDialog_org").attr('height', "150");
				$("#importExcelDialog_org").click();
			}
		});
	}
</script>
<div class="pageHeader">
   <a id="importExcelDialog_org" href="#" target="dialog" mask="true"><span
		id="orgLink" style="display: none"></span></a> 
	<form onsubmit="return navTabSearch(this);" id = "searchForm_org_dept"
	action="/org/orgManage/viewOrganizationInfo" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
	              <td>
	               	<spring:message code="org.orgManage.title.deptId"/><!--部门ID-->/
                       <spring:message code="org.orgManage.title.deptName"/><!--部门名称-->(	<spring:message code="org.title.LIKE"/>)
                       <input type="text" name="seach_dept_id" value="${dept_id}"/>
	              </td> 
	              <td><!-- 部门： --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />(<spring:message code="org.title.INCLUDE_CHILDDEPT"/>)
			      <ait:deptList name="seach_DEPTNO_TYPE" cpnyId="${defaultCpny}" limit="hr" id="viewEmpInfoList_seachDept"/>
			      <ait:deptTreeIcon name="seach_DEPTNO_TYPE" cpnyId="${defaultCpny}" limit="hr" id="viewEmpInfoList_seachDept" selected="${DEPTNO_TYPE}"/></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="button.search"/><!--查询--></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">

	<c:set
		value="javascript:exportDeptInfoList();"
		var="excelD_Url" />
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="500" var="edit_height"/>
	<c:set value="/org/orgManage/updateOrganizationView?DEPTNO={paramno}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>
	<table class="table" width="130%" layoutH="236">
		<thead>
			<tr>
				<th><spring:message code="org.orgManage.title.deptName"/><!--部门名称--></th>
				<th><spring:message code="org.title.DEPT_CODE"/><!-- 部门代码 --></th>
				<th><spring:message code="org.title.DEPTTYPE"/><!-- 部门类型 --></th>
				<th><spring:message code="org.title.MANAGER_EMP_NAME"/><!-- 部门领导 --></th>
				<th><spring:message code="org.orgManage.title.deptBeginTime"/><!--部门成立时间--></th>
				<th><spring:message code="org.orgManage.title.parentDept"/><!--上级部门--></th>
				<th><spring:message code="org.title.ORG_TYPE"/><!-- ORG_TYPE --></th>
				<th><spring:message code="org.title.en.DEPT_LEVEL"/><!-- DEPT_LEVEL --></th>
				<th><spring:message code="org.title.en.AU_CODE"/><!-- AU_CODE --></th>
				<th><spring:message code="org.title.en.DEPT_CODE"/><!-- DEPT_CODE --></th>
				<th><spring:message code="org.title.FOR_PACAL"/><!-- 工资所属 --></th>
				<th><spring:message code="org.title.DEPT_PAYAREA"/><!-- 大区属性 --></th>
				<th><spring:message code="org.title.DEPT_BRANCH"/><!-- 支社属性 --></th>
				<th><spring:message code="org.title.en.DEPT_FUNCTION"/><!-- DEPT_FUNCTION --></th>
				<th><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
				<th><spring:message code="org.orgManage.title.deptEndTime"/><!--部门结束时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${deptInfoList}" var="parameter" varStatus="i">
				<tr target="paramno" rel="${parameter.DEPTNO}">
					<td>
						<c:if test="${parameter.DEPT_LEVEL==1}">
								${parameter.CONTENT}
						</c:if>
						<c:if test="${parameter.DEPT_LEVEL>1}">
							<c:forEach begin="2" end="${parameter.DEPT_LEVEL}" step="1" >
								&nbsp;&nbsp;&nbsp;
							</c:forEach>${parameter.CONTENT}
						</c:if>
					</td>
					<td>${parameter.DEPTNO}</td>
					
					<td><c:if test="${parameter.DEPT_TYPE eq 'payarea'}"><spring:message code="org.title.BIGAREA"/><!-- 大区 --></c:if>
					    <c:if test="${parameter.DEPT_TYPE eq 'branch'}"><spring:message code="org.title.OFFICE_BRANCH"/><!-- 支社 --></c:if>
					</td>
					<td>${parameter.MANAGER_EMP_NAME}</td>
					<td>${parameter.DATE_CREATED}</td>
					<td>
						${parameter.PARENT_DEPT_NAME_ZH}
					</td>
					<td>${parameter.ORG_TYPE}</td>
					<td>${parameter.DEPT_LEVEL}</td>
					
					<td>${parameter.ACC_DIV_CODE}</td>
					<td>${parameter.ACC_ORG_CODE}</td>
					<td>${parameter.FOR_PACAL}
					</td>
					<td>${parameter.DEPT_PAYAREA}</td>
					<td>${parameter.DEPT_BRANCH}</td>
					<td>${parameter.DEPT_FUNCTION_NAME}</td>
					<td>${parameter.LOCAL_NAME}</td>
					<td>${parameter.DATE_ENDED}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/org/orgManage/viewOrganizationInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	<form id="excelExportForm_org" name="excelExportForm_org" method="post">
	<input type="hidden" id="password" 			name="password" 		value="" />
	<input type="hidden" id="dept_id" 				name="dept_id" 			value="" />
	<input type="hidden" id="DEPTNO_TYPE" 			name="DEPTNO_TYPE" 			value="" />
</form>
</div>
