<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewApprovalBatchEmail_seach",navTab.getCurrentPanel()).click(function(){
		$("#viewApprovalBatchEmailForm",navTab.getCurrentPanel()).submit();
	});
});

function executeAffirmByBatchNO(flag){
	//获取页面的值
	var jsonData = '[';
	$("input[name='viewApprovalEmailByBatchNo_checkbox']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.id.substring(9);
			var person_id = $("#PERSON_ID_"+index,navTab.getCurrentPanel()).val();
			//jsonData += obj.value;
			jsonData += ' "BATCH_NO": "' + obj.value + '" ,';
			jsonData += ' "AFFIRM_FLAG": "' + flag + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '", ';
			jsonData += ' "interLanguage": "' + '${LoginUser.language}' + '", ';
			jsonData += ' "PERSON_ID": "' + person_id + '" ';
			jsonData += '}';
		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请先选择要操作的数据
		alertMsg.info("<spring:message code='ess.infoApply.select_data_want_operate'/>");
		return;
	}
    //确定要执行此操作吗？
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/infoApply/executeAffirmBatchByBatchNo',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  		});
  	}});
}
function changeColor_ess0603(obj){
	$(obj).css("color","black").css("font-weight","normal");
}
</script>

<div class="pageHeader">
<form id="viewApprovalBatchEmailForm" onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewApprovalBatchEmail" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<!--<td>标题 <spring:message code="ess.title.Title"/></td>
		<td><input type="text" name="seach_TITLE" value="${TITLE}" /></td>-->
		<td><!--考勤区分 --><spring:message code="ess.title.attendance_distinction"/></td>
		<td>
			<select name="seach_APPLY_TYPE_CODE">
				<option value=""><!--请选择 --><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
				<option value="31" <c:if test="${APPLY_TYPE_CODE eq '31' }">selected</c:if>><!--加班 --><spring:message code="ess.attendance.ot"/></option>
				<option value="21" <c:if test="${APPLY_TYPE_CODE eq '21' }">selected</c:if>><!--考勤 --><spring:message code="ess.attendance.state"/></option>
				<!--<option value="218197" <c:if test="${APPLY_TYPE_CODE eq '218197' }">selected</c:if>>考勤异常 <spring:message code="ess.title.attendance_anomaly"/></option>-->
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
				<a class="buttonActive" id="viewApprovalBatchEmail_seach" href="#">
					<span><!--查询 --><spring:message code="ess.infoApply.SELECT"/></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" onclick="executeAffirmByBatchNO(1)" href="#"><span><!--统一通过 --><spring:message code="ess.attendance.toyitongguo"/></span></a>
				<a class="buttonActive" onclick="executeAffirmByBatchNO(2)" href="#"><span><!--统一否决 --><spring:message code="ess.attendance.toyifoujue"/></span></a>
			</li>
	</ul>
</div>
<div class="pageContent" style="padding-left:10px;padding-right:10px;">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewApprovalEmailSize}</div>
		<table class="table" width="100%" layoutH="200">
			<thead>
				<tr>
				    <th width="5%">
				    <!--NO--><spring:message code="sys.homeParam.title.NO"/>
				    </th>
					<th width="5%">
				    	<input type="checkbox" class="checkboxCtrl" group="viewApprovalEmailByBatchNo_checkbox" />
				    </th>
				    <th width="10%" style="text-align: center">
				        <!--考勤区分--><spring:message code="ess.title.attendance_distinction"/>
					</th>
				    <th width="10%" style="text-align: center">
				        <!--人数--><spring:message code="pa.title.pa.excel.thecountnumberofemployee"/>
					</th>
				    <th width="20%" style="text-align: center">
				        <!-- 代申请人--><spring:message code="ar.viewLeaveConfirmList.DAISHENQINGREN.b"/>
					</th>
				    <th width="30%" style="text-align: center">
				        <!-- 部门--><spring:message code="ess.infoApply.DEPT"/>               
					</th>
					<th width="20%" style="text-align: center">
					    <!--批量申请日期--><spring:message code="ess.empInfo.date_application"/>
					</th>
					<!--<th width="80" style="text-align: center">开始时间
					    审批状态<spring:message code="ess.affirmApply.title.remark.shenpizhuangtai"/>
					</th>-->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewApprovalBatchEmail}" var="item" varStatus="i">			
					<tr>
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					        <input type="checkbox" name="viewApprovalEmailByBatchNo_checkbox" id="checkbox_${i.index }" value="${item.BATCH_NO}" />
					    </td>
					    <td style="text-align: center">${item.APPLY_TYPE_NAME}</td>
					    <td style="text-align: center"><a href="<c:if test="${item.APPLY_TYPE_CODE eq '21'}">/ess/infoApply/viewBatchApprovaledLeave</c:if><c:if test="${item.APPLY_TYPE_CODE eq '31'}">/ess/infoApply/viewBatchApprovaledOT</c:if>?&seach_BATCH_NO=${item.BATCH_NO}&READ_FLAG=1&APPLY_FLAG=${item.APPLY_FLAG}&seach_ACTIVITY=${item.ACTIVITY}" target="dialog" title="<spring:message code="ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b"/>" mask="true" width="1000" height="600" rel="applyInfo"<c:if test="${item.READ_FLAG eq '0'}">style="color:blue;font-weight:bold;"</c:if> onclick="changeColor_ess0603(this)">${item.BATCH_NUM }</a></td>
					    <td style="text-align: center">${item.CREATED_BY}</td>
					    <td style="text-align: center">${item.DEPT_NAME}</td>
					    <td style="text-align: center">${item.APPLY_TIME}</td>
					    <input type="hidden" name="PERSON_ID" id="PERSON_ID_${i.index }" value="${item.APPLY_PERSON_ID }">
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>