<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEmpChangeInfoList_Serch").click(function(){
		$("#viewEmpChangeInfoListForm").submit();
	});
	$("#viewEmpChangeInfoListResumeNo").change(function(){
		$("#viewEmpChangeInfoListForm").submit();
	});
});
</script>
<div class="pageHeader">
<form id="viewEmpChangeInfoListForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewEmpChangeInfoList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
		<td>
			<select id="viewEmpChangeInfoListResumeNo" name="seach_RESUME_NO">
				<c:forEach items="${orgResumeList}" var="result">
					<option value="${result.NO}" <c:if test="${result.NO eq RESUME_NO}">selected</c:if>>${result.NO }&nbsp;&nbsp;&nbsp;${result.RESUME_NAME}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li>
			<a class="buttonActive" id="viewEmpChangeInfoList_Serch" href="#">
				<span><spring:message code="org.title.SELECT" /><!-- 查询 --></span>
			</a>
		</li>
		<li>
			<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=31&RESUME_NO=${ RESUME_NO}"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>					
		</li>
		<li>
			<a href="#" onclick="navTabNum('/org/orgManage/viewOrgChangeInfoList','pageNum=1&amp;menuNo=14013681&amp;navTabId=org0208','org0208','<spring:message code="org.title.UPDATE_RESUME_DEPT" />');"><span><spring:message code="org.title.NEXT_STAGE" /><!-- 下阶段 --></span></a>				
		</li>
	</ul>
</div>
<div class="pageContent">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${empChangeInfoListSize}</div>
	<table class="list" width="100%" layooutH="120">
		<thead>
			<tr>
				<th width="670px" colspan="7"></th>
				<th width="160px" colspan="2"><spring:message code="org.title.dept" /><!-- 部门 --></th>
				<th width="160px" colspan="2"><spring:message code="org.title.COST_CENTER" /><!-- 成本中心 --></th>
				<th width="160px" colspan="2"></th>
			</tr>
			<tr>
				<th width="30px" rowspan="2">No.</th>
				<th width="80px" rowspan="2"><spring:message code="org.title.LOCAL_NAME" /><!-- 姓名 --></th>
				<th width="80px" rowspan="2"><spring:message code="org.title.EMPID" /><!-- 工号 --></th>
				<th width="80px" rowspan="2"><spring:message code="org.title.POST_GRADE_NAME" /><!-- 职务 --></th>
				<th width="80px" rowspan="2"><spring:message code="org.title.ORSER_START_DATE" /><!-- 命令日期 --></th>
				<th width="80px" rowspan="2"><spring:message code="org.title.EXPERIENCE_TYPE_NAME" /><!-- 发令区分 --></th>
				<th width="80px" rowspan="2"><spring:message code="org.title.TRANS_REASON_NAME" /><!-- 发令原因 --></th>
				<th width="80px"><spring:message code="org.title.before" /><!-- 以前 --></th>
				<th width="80px"><spring:message code="org.title.NOW" /><!-- 现在 --></th>
				<th width="80px"><spring:message code="org.title.before" /><!-- 以前 --></th>
				<th width="80px"><spring:message code="org.title.NOW" /><!-- 现在 --></th>
				<th width="80px" rowspan="2"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
				<th width="80px" rowspan="2"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${empChangeInfoList}" var="item" varStatus="i">
				<tr>
					<td class='td_center'>${i.count}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.POST_GRADE_NAME}</td>
					<td class='td_center'>${item.START_DATE}</td>
					<td class='td_center'>${item.EXPERIENCE_TYPE_NAME}</td>
					<td class='td_center'>${item.REMARK}</td>
					<td class='td_center'>${item.OLD_DEPTNAME}</td>
					<td class='td_center'>${item.DEPTNAME}</td>
					<td class='td_center'>${item.OLD_COST_CENTER_NAME}</td>
					<td class='td_center'>${item.COST_CENTER_NAME}</td>
					<td class='td_center'>${item.UPDATED_BY}</td>
					<td class='td_center'>${item.UPDATE_DATE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>