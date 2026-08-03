<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(function(){
    $('#demoTree').treeTable();
});
</script>
<a href="" id="reloadPage_viewEmpInfo" target="navTab" rel="hr2100" style="display:none;">员工信息查询</a>
<div class="pageHeader">
<form id="viewEmpInfo" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewEmpInfoList?firstFlag=N&defaultCpny=${defaultCpny }" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>法人
		</td>
		<td>
			<c:if test="${authority eq '1'}">
				<select id="hr2100_seach_CPNY_ID" name="seach_defaultCpny" onchange="reloadPage_viewEmpInfo();" disabled="disabled">
					<c:forEach items="${companyList}" var="item" varStatus="i">
						<option value="${item.CPNY_ID }" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
					</c:forEach>
				</select>
			</c:if>
			<c:if test="${authority eq '0'}">
				${defaultCpny}
				<input type="hidden" id="hr2100_seach_CPNY_ID" name="seach_defaultCpny" value="${defaultCpny}"/>
			</c:if>
	    </td>
		<td><!-- 部门： --> <spring:message
			code="hr.viewPersonalInfo.title.DEPTNAME" /> 
		</td>
		<td>
			 <c:if test="${authority eq '1'}">
			<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="super" id="viewEmpInfoList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="super" id="viewEmpInfoList_seachDept" selected="${DEPTNO}"/>
			</c:if>
			<c:if test="${authority ne '1'}">
			<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="hr" id="viewEmpInfoList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="hr" id="viewEmpInfoList_seachDept" selected="${DEPTNO}"/>
			</c:if> 
		</td>
		<td><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td><input type="text" name="seach_KEY" value="${KEY}" /></td>
		<td><!-- 职责： --> <spring:message
			code="ess.infoApply.title.dutyName" /> 
		</td>
		<td><select name="seach_POSITION"><option value="">请选择</option>
				<c:forEach items="${positionList}" var="position">
					<option value="${position.POSITION}" <c:if test="${position.POSITION eq POSITION}">selected</c:if>>${position.POSITION}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td>人员类型组
		</td>
		<td>
			<c:if test="${authority eq '1'}">
			<ait:SelectEmpTypeCode name="seach_EMP_TYPE_GROUP" selected="${EMP_TYPE_GROUP}" cnpyID="${defaultCpny}" limit="super" type="group" onChangeName="ajaxAdd_add_hr2100(-1)"/>
			</c:if>
			<c:if test="${authority ne '1'}">
			<ait:SelectEmpTypeCode name="seach_EMP_TYPE_GROUP" selected="${EMP_TYPE_GROUP}" cnpyID="${defaultCpny}" limit="hr" type="group" onChangeName="ajaxAdd_add_hr2100(-1)"/>
			</c:if>
		</td>
		<td><!-- 人员类型： --> <spring:message
			code="hr.enpinfo.title.EMP.TYPE" />
		</td>
		<td>
			<c:if test="${authority eq '1'}">
		 		<ait:SelectEmpTypeCode id="seach_EMP_TYPE_hr2100" name="seach_EMP_TYPE" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="super"/>
			</c:if>
			<c:if test="${authority ne '1'}">
		 		<ait:SelectEmpTypeCode id="seach_EMP_TYPE_hr2100" name="seach_EMP_TYPE" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="hr"/>
			</c:if>
		</td>
		<td><!-- 在职区分： --> <spring:message
			code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME" /> 
		</td>
		<td>
		 <ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE_NAME" name="seach_EMP_OFFICE_NAME" parentNo="15118" selected="${EMP_OFFICE_NAME}" cnpyID="${defaultCpny}" limit="all"/>
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
			<table class="list" width="100%" id="demoTree">
				<thead>
					<tr>
						<th width="17%" orderField="DEPTNO" class="${orderDirection}"><spring:message
							code="public.title.deptName" /> <!--部门--></th>
						<th width="17%" orderField="EMPID" class="${orderDirection}">
							部门等级</th>
						<th width="17%" orderField="LOCAL_NAME" class="${orderDirection}">
							部门下人数</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${empInfo}" var="item" varStatus="i">
						<tr id="${item.DEPTNO}" <c:if test="${item.PARENT_DEPT_NO ne 0}">pid="${item.PARENT_DEPT_NO}"</c:if>>
							<td controller="true">${item.ORG_NAME_LOCAL}</td>
							<td controller="true">${item.DEPT_LEVEL}</td>
							<td controller="true">${item.CNT}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
