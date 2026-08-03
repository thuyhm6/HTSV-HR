<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
   //excel导出
	function exportDeptInfoList() {
		var sform = document.getElementById("searchForm_org_dept");
		alertMsg.confirm('<spring:message code="org.title.IS_IMPUT_DATE" />',{
			okCall : function() {
				//用于excel导出的表单参数处理
				var eForm = document.getElementById("excelExportForm_org"); 
				document.getElementById("orgLink").innerHTML = '<spring:message code="org.title.EXCEL_SETPWD" />';
				eForm.dept_id.value 			= sform.seach_dept_id.value;
				eForm.DEPTNO_TYPE.value 		= sform.seach_DEPTNO_TYPE.value;
				$("#importExcelDialog_org11order").attr('href', "/sys/encryptExcel"
						+"?exportFunName=/org/orgManage/viewOrgInfoListExcel"
						+"&navTabId=org01order"
						+"&formId=excelExportForm_org");
				$("#importExcelDialog_org11order").attr('width', "300");
				$("#importExcelDialog_org11order").attr('height', "150");
				$("#importExcelDialog_org11order").click();
			}
		});
	}
	
function importDeptOrderData(){
	$("#importExcelDialog_org11order").attr('href','/pa/excelImport/importExcelData?importFunName=/importDeptOrderData');
	$("#importExcelDialog_org11order").click();
}

$(document).ready(function() {
	var message = "${message}";
	if (message != '') {
		alertMsg.info("${message}");
	}
});
function downloadImportTemplate_dept_order() {//下载导入模板
        var form = document.getElementById("searchForm_org_dept");
        form.action="/org/orgManage/downloadTempleteDeptOrder";
        form.submit();
}
</script>
<div style="display: none">
<a id="importExcelDialog_org11order" href="#" width="400"
	height="200" target="dialog" mask="true"><span
		id="orgLink" style="display: none"></span>
</a>
</a>
<a id="importExcel_org11order" href="#" target="navTab" mask="true"><spring:message code="org.title.SELECT_INPUT" /><!-- 查看导入 --></a>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" id="searchForm_org_dept"
		action="/org/orgManage/viewOrgInfoOrderNoList" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="org.orgManage.title.deptId" />
						<!--部门ID-->/ <spring:message code="org.orgManage.title.deptName" />
						<!--部门名称-->(<spring:message code="org.title.LIKE" /><!-- 模糊 -->) <input type="text" name="seach_dept_id"
						value="${dept_id}" /></td>
					<td>
						<!-- 部门： --> <spring:message
							code="hr.viewPersonalInfo.title.DEPTNAME" />(<spring:message code="org.title.INCLUDE_CHILDDEPT" /><!-- 含子部门 -->) <ait:deptList
							name="seach_DEPTNO_TYPE" cpnyId="${defaultCpny}" limit="hr"
							id="viewEmpInfoList_seachDept" /> <ait:deptTreeIcon
							name="seach_DEPTNO_TYPE" cpnyId="${defaultCpny}" limit="hr"
							id="viewEmpInfoList_seachDept" selected="${DEPTNO_TYPE}" />
					</td>
					<td><spring:message code="org.title.IS_USE" />： <select name="seach_USE_YN">
							<option value=""><spring:message code="org.title.ALL" /><!-- 全部 --></option>
							<option value="Y"
								<c:if test="${USE_YN eq 'Y'}">selected="selected"</c:if>><spring:message code="org.title.IN_USE" /><!-- 使用中 --></option>
							<option value="N"
								<c:if test="${USE_YN eq 'N'}">selected="selected"</c:if>><spring:message code="org.title.USED" /><!-- 已停用 --></option>
					</select></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="button.search" />
									<!--查询-->
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
	<c:set value="dialog" var="edit_tab" />
	<c:set value="600" var="edit_width" />
	<c:set value="500" var="edit_height" />
	<c:set value="/org/orgManage/updateOrgInfoView?DEPTNO={paramno}"
		var="edit_Url" />
	<div class="formBar">
		<ul class="toolBar">
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="exportExcel"
							onclick="downloadImportTemplate_dept_order();">
							<spring:message code="pa.insurance.title.downloadImportTemplate" />
							<!--下载导入模板-->
						</button>
					</div>
				</div>
			</li>
			<li><a class="buttonActive" onclick="importDeptOrderData()"> <span><spring:message
							code="ar.addempshift.title.excelimport" /> <!-- EXCEL导入 --> </span> </a>
			</li>
			<li><a class="buttonActive" onclick="javascript:exportDeptInfoList();"> <span><spring:message code="org.title.INPUT" /><!-- 导出 --></span> </a>
			</li>
			<c:if test="${toolbarInfo.INSERTR == '1'}">
				<c:if test="${init_Url ne '' && init_Url ne null}">
					<li id="addLi"><a class="add" href="${init_Url}"
						<c:if test="${init_target_exit eq '' || init_target_exit eq null }">
							target="${init_tab eq '' || init_tab eq null ? 'ajaxTodo' : init_tab}"
						</c:if>
						<c:if test="${init_range ne '' && init_range ne null }">
							width="${init_width eq '' || init_width eq null ? '500' : init_width}" 
							height="${init_height eq '' || init_height eq null ? '400' : init_height}"
						</c:if>
						title="
							<c:choose>
							   <c:when test="${init_title eq '' || init_title eq null}">
							     	<spring:message code="button.init.sure" />
							   </c:when>
							   <c:otherwise>
							   		${init_title}
							   </c:otherwise>
							</c:choose>
						">
							<span> <c:choose>
									<c:when test="${init_name eq '' || init_name eq null}">
										<spring:message code="button.init" />
									</c:when>
									<c:otherwise>
							   		${init_name}
							   </c:otherwise>
								</c:choose> </span> </a>
					</li>
				</c:if>
			</c:if>

			<c:if test="${toolbarInfo.UPDATER == '1'}">
				<c:if test="${edit_Url ne '' && edit_Url ne null}">
					<li id="editLi"><a class="edit" href="${edit_Url}"
						<c:if test="${edit_target_exit eq '' || edit_target_exit eq null }">
							target="${edit_tab eq '' || edit_tab eq null ? 'dialog' : edit_tab}"
						</c:if>
						mask="${edit_mask eq '' || edit_mask eq null ? 'true' : edit_mask }"
						width="${edit_width eq '' || edit_width eq null ? '800' : edit_width}"
						height="${edit_height eq '' || edit_height eq null ? '400' : edit_height}"
						<c:if test="${edit_rel ne '' && edit_rel ne null}">
								rel="${edit_rel}"
							</c:if>><span>
								<c:choose>
									<c:when test="${edit_name eq '' || edit_name eq null}">
										<spring:message code="button.update" />
									</c:when>
									<c:otherwise>
								   		${edit_name}
								   </c:otherwise>
								</c:choose> </span> </a>
					</li>
				</c:if>
			</c:if>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="236">
		<thead>
			<tr>
				<th><spring:message code="org.orgManage.title.deptName" />
					<!--部门名称-->
				</th>
				<th><spring:message code="org.title.DEPT_CODE"/><!-- 部门代码 --></th>
				<th><spring:message code="org.title.DEPTTYPE"/><!-- 部门类型 --></th>
				<th><spring:message code="org.orgManage.title.parentDept" />
					<!--上级部门-->
				</th>
				<th><spring:message code="org.title.ORG_TYPE"/><!-- ORG_TYPE --></th>
				<th><spring:message code="org.title.en.AU_CODE"/><!-- AU_CODE --></th>
				<th><spring:message code="org.title.en.DEPT_CODE"/><!-- DEPT_CODE --></th>
				<th><spring:message code="org.title.FOR_PACAL"/><!-- 工资所属 --></th>
				<th><spring:message code="org.title.DEPT_PAYAREA"/><!-- 大区属性 --></th>
				<th><spring:message code="org.title.DEPT_BRANCH"/><!-- 支社属性 --></th>
				<th><spring:message code="org.title.ORG_SORT_NO"/><!-- 组织排序No --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${deptInfoList}" var="parameter" varStatus="i">
				<tr target="paramno" rel="${parameter.DEPTNO}">
					<td><c:if test="${parameter.DEPT_LEVEL==1}">
								${parameter.CONTENT}
						</c:if> <c:if test="${parameter.DEPT_LEVEL>1}">
							<c:forEach begin="2" end="${parameter.DEPT_LEVEL}" step="1">
								&nbsp;&nbsp;&nbsp;
							</c:forEach>${parameter.CONTENT}
						</c:if></td>
					<td>${parameter.DEPTNO}</td>

					<td><c:if test="${parameter.DEPT_TYPE eq 'payarea'}"><spring:message code="org.title.BIGAREA"/><!-- 大区 --></c:if>
						<c:if test="${parameter.DEPT_TYPE eq 'branch'}"><spring:message code="org.title.OFFICE_BRANCH"/><!-- 支社 --></c:if></td>
					<td>${parameter.PARENT_DEPT_NAME_ZH}</td>
					<td>${parameter.ORG_TYPE}</td>

					<td>${parameter.ACC_DIV_CODE}</td>
					<td>${parameter.ACC_ORG_CODE}</td>
					<td>${parameter.FOR_PACAL}</td>
					<td>${parameter.DEPT_PAYAREA}</td>
					<td>${parameter.DEPT_BRANCH}</td>
					<td>${parameter.ORDERNO}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/org/orgManage/viewOrgInfoOrderNoList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	<form id="excelExportForm_org" name="excelExportForm_org" method="post">
		<input type="hidden" id="password" name="password" value="" /> <input
			type="hidden" id="dept_id" name="dept_id" value="" /> <input
			type="hidden" id="DEPTNO_TYPE" name="DEPTNO_TYPE" value="" />
	</form>
</div>
