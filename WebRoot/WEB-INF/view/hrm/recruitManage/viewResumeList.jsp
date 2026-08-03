<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	openOnRight('/hrm/recruitManage/viewAddResumeInfo?SEQ=${SEQ}','viewResumeManagerInfo_unit');
	$("#viewResumeManagerInfo_search").click(function(){
		$("#viewResumeManagerInfoForm").submit();
	});
});
</script>
<div class="pageHeader">
<form id="viewResumeManagerInfoForm" onsubmit="return navTabSearch(this);" action="/hrm/recruitManage/viewResumeList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hrm.empinfo.REGISTRATION_DATE"/><!-- 注册日 --></td>
		<td>
			<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE}" />
			~
			<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}" />
		</td>
		<td><spring:message code="hr.assignment.type"/><!-- 统一发令类型 --></td>
		<td><ait:SelectSyCodeByCpnyID id="seach_REGISTER_TYPE" name="seach_REGISTER_TYPE" parentNo="14013956" selected="${REGISTER_TYPE}" limit="all"/></td>
		<td><spring:message code="ar.viewcycle.title.zhuangtai"/><!-- 状态 --></td>
		<td>
			<select name="seach_ACTIVITY">
				<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!-- 请选择 --></option>
				<option value="0" <c:if test="${ACTIVITY eq '0' }">selected</c:if>>In Progress</option>
				<option value="1" <c:if test="${ACTIVITY eq '1' }">selected</c:if>>Action Completed</option>
			</select>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" id="viewResumeManagerInfo_search" href="#">
				<span><spring:message code="button.search"/><!-- 查询 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="openOnRight('/hrm/recruitManage/viewAddResumeInfo?SEQ=0','viewResumeManagerInfo_unit');" href="#">
					<span><spring:message code="button.add" /><!-- 添加 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateDeleteresumeInfoCallback('viewAddresumeInfo',navTabAjaxDone)" href="#">
				<span><spring:message code="button.delete" /><!-- 删除 --></span></a>
			</li>
			<li>
				<a class="buttonActive" onclick="validateAddresumeInfoCallback('viewAddresumeInfo',navTabAjaxDone)" href="#">
				<span><spring:message code="button.sys.affirm.save" /><!-- 保存 --></span></a>
			</li>
			<li>
				<a class="delete" onclick="downloadExcel('viewResumeManagerInfoForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=40','/hrm/recruitManage/viewResumeList')" href="#">
				<span><spring:message code="hrm.empinfo.EXPORT" /><!-- 导出到EXECL --></span></a>					
			</li>
	</ul>
</div>

	<div id="viewResumeManagerInfo_left" style="float:left; display:block; overflow:auto;width:800px; height:450px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${resumeSize}</div>
		<table class="list" width="800px;">
			<thead>
				<tr>
					<th width="25px;">No.</th>
					<th width="50px"><spring:message code="hrm.empinfo.AUTOMATIC_PROCESSINF_CODE.Z"/><!-- 自动处理代码 --></th>
					<th width="50px"><spring:message code="hrm.empinfo.REGISTRATION_DATE"/><!-- 注册日 --></th>
					<th width="50px"><spring:message code="hr.assignment.type"/><!-- 发令类型 --></th>
					<th width="50px"><spring:message code="display.emp.ben.effectivedate"/><!-- 生效日期 --></th>
					<th width="100px"><spring:message code="ar.viewcycle.title.zhuangtai"/><!-- 状态 --></th>
					<th width="100px"><spring:message code="hrm.empinfo.ORDER_OUTLINE.Z"/><!-- 发令概要 --></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${resumeList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/hrm/recruitManage/viewAddResumeInfo?SEQ=${item.SEQ}','viewResumeManagerInfo_unit');">
						<td width="25px;" class='td_center'>${i.count}</td>
						<td width="50px" style="text-align:left">${item.REGISTER_CODE}</td>
						<td width="50px" style="text-align:left">${item.REGISTER_INFO}</td>
						<td width="50px" style="text-align:left">${item.REGISTER_TYPE_NAME}</td>
						<td width="50px" style="text-align:left">${item.REGISTER_DATE}</td>
						<td width="50px" style="text-align:left">${item.ACTIVITY_NAME}</td>
						<td width="100px" style="text-align:left">${item.REMARK}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="viewResumeManagerInfo_unit">
	</div>
</div>