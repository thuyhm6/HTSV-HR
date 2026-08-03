<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewApprovaledBatchEmail_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewApprovaledBatchEmailForm",navTab.getCurrentPanel()).submit();
	});
	//删除
	$("#viewApprovaledBatchEmail_delete",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var BATCH_NO = "";
		$("input[name='viewApprovaledBatchEmail_checkbox']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				BATCH_NO += ",'" + obj.value + "'";
			}
		});

		if (BATCH_NO.length < 1) {
			//请先选择要删除的数据
			alertMsg.info("<spring:message code='ess.infoApply.select_data_delete'/>");
			return;
		}
        //确定要删除吗？
		alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete'/>",
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/infoApply/deleteAffirmByBatchNo',
					data: [{ name: 'BATCH_NO', value: BATCH_NO.substr(1) },
					       { name: 'FORM_ID', value: "viewApprovaledBatchEmailForm" }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
	//标记为已读
	$("#viewApprovaledBatchEmail_readed",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var BATCH_NO = "";
		$("input[name='viewApprovaledBatchEmail_checkbox']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				BATCH_NO += ",'" + obj.value + "'";
			}
		});

		if (BATCH_NO.length < 1) {
			//请先选择要标记为已读的数据
			alertMsg.info("<spring:message code='ess.infoApply.select_data_want_mark_read'/>");
			return;
		}
        //确定要标记为已读吗？
		alertMsg.confirm("<spring:message code='ess.infoApply.confirm_mark_read'/>",
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/infoApply/readedAffirmByBatchNo',
					data: [{ name: 'BATCH_NO', value: BATCH_NO.substr(1) },
					       { name: 'FORM_ID', value: "viewApprovaledBatchEmailForm" }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
});
function changeColor_ess0604(obj){
	$(obj).css("color","black").css("font-weight","normal");
}
</script>

<div class="pageHeader">
<form id="viewApprovaledBatchEmailForm" onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewApprovaledBatchEmail" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!--期间 --><spring:message code="ess.infoApply.Period"/></td>
		<td colspan="3">
			<input type="text" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${START_DATE }"/>~
			<input type="text" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})" value="${END_DATE }"/>
		</td>
		<td><!--考勤区分 --><spring:message code="ess.title.attendance_distinction"/></td>
		<td>
			<select name="seach_APPLY_TYPE_CODE">
				<option value=""><!--请选择 --><spring:message code="org.title.PLEASE_SELECT"/></option>
				<option value="31" <c:if test="${APPLY_TYPE_CODE eq '31' }">selected</c:if>><!--加班 --><spring:message code="ess.attendance.ot"/></option>
				<option value="21" <c:if test="${APPLY_TYPE_CODE eq '21' }">selected</c:if>><!--考勤 --><spring:message code="ess.attendance.state"/></option>
				<!--<option value="218197" <c:if test="${APPLY_TYPE_CODE eq '218197' }">selected</c:if>>考勤异常 <spring:message code="ess.title.attendance_anomaly"/></option>-->
			</select>
		</td>
		<td><!--已读/未读 --><spring:message code="ess.infoApply.read_notRead"/></td>
		<td>
			<select name="seach_READ_FLAG">
				<option value="2" <c:if test="${READ_FLAG eq '2' }">selected</c:if>><!--全部 --><spring:message code="ess.infoApply.whole"/></option>
				<option value="0" <c:if test="${READ_FLAG eq '0' }">selected</c:if>><!--未读 --><spring:message code="hrm.ess.apply.weidu"/></option>
				<option value="1" <c:if test="${READ_FLAG eq '1' }">selected</c:if>><!--已读 --><spring:message code="hrm.ess.apply.yidu"/></option>
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
				<a class="buttonActive" id="viewApprovaledBatchEmail_Serch" href="#">
					<span><!--查询 --><spring:message code="ess.infoApply.SELECT"/></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" id="viewApprovaledBatchEmail_delete" href="#">
					<span><!--删除 --><spring:message code="org.title.DELETE"/></span>
				</a>
			</li>
			<li>
				<a class="buttonActive" id="viewApprovaledBatchEmail_readed" href="#">
					<span><!--标记为已读 --><spring:message code="hr.viewCondSql.title.BIAOJIYIDU"/></span>
				</a>
			</li>
	</ul>
</div>
<div class="pageContent" style="padding-left:10px;padding-right:10px;">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewApprovaledBatchEmailSize}</div>
		<table class="table" width="100%" layoutH="200">
			<thead>
				<tr>
				    <th width="5%">
				    	<!--NO--><spring:message code="sys.homeParam.title.NO"/>
				    </th>
					<th width="5%">
				    	<input type="checkbox" class="checkboxCtrl" group="viewApprovaledBatchEmail_checkbox" />
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
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewApprovaledBatchEmail}" var="item" varStatus="i">			
					<tr>
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					        <input type="checkbox" name="viewApprovaledBatchEmail_checkbox" id="checkbox_${i.index }" value="${item.BATCH_NO}" />
					    </td>
					    <td style="text-align: center">${item.APPLY_TYPE_NAME}</td>
					    <td style="text-align: center"><a href="<c:if test="${item.APPLY_TYPE_CODE eq '21'}">/ess/infoApply/viewBatchApprovaledLeave</c:if><c:if test="${item.APPLY_TYPE_CODE eq '31'}">/ess/infoApply/viewBatchApprovaledOT</c:if>?seach_BATCH_NO=${item.BATCH_NO}&READ_FLAG=1&APPLY_FLAG=${item.APPLY_FLAG}&seach_ACTIVITY=${item.ACTIVITY}" target="dialog" title="<spring:message code="ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b"/>" mask="true" width="1000" height="600" rel="applyInfo"<c:if test="${item.READ_FLAG eq '0'}">style="color:blue;font-weight:bold;"</c:if> onclick="changeColor_ess0604(this)">${item.BATCH_NUM }</a></td>
					    <td style="text-align: center">${item.CREATED_BY}</td>
					    <td style="text-align: center">${item.DEPT_NAME}</td>
					    <td style="text-align: center">${item.APPLY_TIME}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>