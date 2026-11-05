<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%-- Quản lý phê duyệt dữ liệu quẹt thẻ nhập tay (INSERT_BY: 'P' = Nhập tay - chưa phê duyệt -> 'H') --%>
<script type="text/javascript">
$(document).ready(function(){
	$("#viewArCardRecordApprove_search", navTab.getCurrentPanel()).click(function(){
		$("#viewArCardRecordApproveForm", navTab.getCurrentPanel()).submit();
	});

	$(".list", navTab.getCurrentPanel()).dataTable({"bPaginate": false,
		"bAutoWidth": false,
		"bProcessing": false,
		"bLengthChange": false,
		"bFilter": true,
		"bSort": true,
		"bInfo": false,
		"bScrollInfinite": true,
		"scrollY": $(document.body).height() - 310,
		"scrollX": true,
		"orderClasses": false,
		"oLanguage": {
			"sProcessing": "<spring:message code='ess.message.loading' />",
			"sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
			"sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
			"sSearch": "<spring:message code='ess.message.rapid_screening' />"
		}
	});
});

function f_approve_viewArCardRecordApprove(callback) {
	var jsonData = '[';
	$.each($("input[name='approve_c1']", navTab.getCurrentPanel()), function(i, obj) {
		if (obj.checked) {
			if (jsonData.length > 1) {
				jsonData += ',';
			}
			jsonData += '{ "RECORD_NO": "' + obj.value + '" }';
		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		// Vui lòng chọn dữ liệu cần phê duyệt
		alertMsg.error("<spring:message code='ar.viewArCardRecordApprove.alert.choose'/>");
		return;
	}
	// Xác nhận phê duyệt
	alertMsg.confirm("<spring:message code='ar.viewArCardRecordApprove.alert.confirm'/>", {
		okCall: function() {
			$.ajax({
				type: 'POST',
				url: '/ar/attendanceMintenance/approveArCardRecordInfo',
				data: [{ name: 'jsonData', value: jsonData }],
				dataType: "json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});
		}
	});
}
</script>
<div class="pageHeader">
	<form id="viewArCardRecordApproveForm" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArCardRecordApprove" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
					</td>
					<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="ar" id="viewArCardRecordApprove_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewArCardRecordApprove_seachDept" selected="${DEPTNO}"/>
					</td>
				</tr>
				<tr>
					<td><!-- 期间 --><spring:message code="ess.workgroup.title.duration" text="期间"/></td>
					<td>
						<input type="text" id="seach_STIME" name="seach_STIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${STIME}"/>~
						<input type="text" id="seach_RTIME" name="seach_RTIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${RTIME}"/>
					</td>
					<td><!-- Trạng thái --><spring:message code="ar.viewArCardRecordApprove.title.status"/></td>
					<td>
						<select name="seach_APPROVE_STATUS" id="seach_APPROVE_STATUS">
							<option value="PENDING" <c:if test="${APPROVE_STATUS ne 'APPROVED'}">selected="selected"</c:if>><spring:message code="ar.viewArCardRecordApprove.title.pending"/></option>
							<option value="APPROVED" <c:if test="${APPROVE_STATUS eq 'APPROVED'}">selected="selected"</c:if>><spring:message code="ar.viewArCardRecordApprove.title.approved"/></option>
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
			<li>
				<a class="buttonActive" id="viewArCardRecordApprove_search" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a>
			</li>
			<c:if test="${APPROVE_STATUS ne 'APPROVED'}">
			<li>
				<a class="buttonActive" href="#" onclick="javascript:f_approve_viewArCardRecordApprove(navTabAjaxDoneWithForm);"><span><!-- Phê duyệt --><spring:message code="ar.viewArCardRecordApprove.button.approve"/></span></a>
			</li>
			</c:if>
		</ul>
	</div>
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(approveList)}</div>
	<table class="list" width="99%">
		<thead>
			<tr>
				<th width="3%" align="center">No.</th>
				<th width="2%" align="center"><input type="checkbox" class="checkboxCtrl" group="approve_c1"></th>
				<th width="7%"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="12%"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="14%"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="6%"><!-- 职级 --><spring:message code="ess.infoApply.Rank"/></th>
				<th width="9%"><!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/></th>
				<th width="7%"><!-- 考勤日期 --><spring:message code="ess.infoApply.attendance_date"/></th>
				<th width="7%"><!-- 打卡日期 --><spring:message code="ar.viewArCardRecord.DAKARIQI.b"/></th>
				<th width="6%"><!-- 打卡时间 --><spring:message code="ess.infoApply.card_clock_time"/></th>
				<th width="4%"><!-- 类型 --><spring:message code="ar.viewarcardrecord.title.leixing"/></th>
				<th width="8%"><!-- 备注 --><spring:message code="ar.viewarcardrecord.title.beizhu"/></th>
				<th width="8%"><!-- Trạng thái --><spring:message code="ar.viewArCardRecordApprove.title.status"/></th>
				<th width="9%"><!-- Người nhập / ngày nhập --><spring:message code="ar.viewArCardRecordApprove.title.createdBy"/></th>
				<th width="9%"><!-- Người cập nhật / phê duyệt --><spring:message code="ar.viewArCardRecordApprove.title.updatedBy"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${approveList}" var="list" varStatus="i">
				<tr target="RECORD_NO" rel="${list.RECORD_NO}">
					<td>${i.count}</td>
					<td>
						<c:if test="${list.INSERT_BY eq INSERT_BY_PENDING}">
							<input type="checkbox" name="approve_c1" value="${list.RECORD_NO}">
						</c:if>
					</td>
					<td>${list.EMPID}</td>
					<td>${list.LOCAL_NAME}</td>
					<td>${list.DEPTNAME}</td>
					<td>${list.POST_GRADE_NAME}</td>
					<td>${list.SHIFT_NAME} (${list.SHIFT_TIME})</td>
					<td>${list.AR_DATE_STR}</td>
					<td>${fn:substring(list.R_TIME,0,10)}</td>
					<td>${fn:substring(list.R_TIME,10,19)}</td>
					<td>${list.DOOR_TYPE}</td>
					<td>${list.REMARK}</td>
					<td>
						<c:choose>
							<c:when test="${list.INSERT_BY eq INSERT_BY_PENDING}"><span style="color:red;"><spring:message code="ar.viewArCardRecordApprove.title.pending"/></span></c:when>
							<c:otherwise><span style="color:green;"><spring:message code="ar.viewArCardRecordApprove.title.approved"/></span></c:otherwise>
						</c:choose>
					</td>
					<td>${list.CREATED_BY_NAME}<c:if test="${not empty list.CREATE_DATE}"><br/>${list.CREATE_DATE}</c:if></td>
					<td>${list.UPDATED_BY_NAME}<c:if test="${not empty list.UPDATE_DATE}"><br/>${list.UPDATE_DATE}</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
