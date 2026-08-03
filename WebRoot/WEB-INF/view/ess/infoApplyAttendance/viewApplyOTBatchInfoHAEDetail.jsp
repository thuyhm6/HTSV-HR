<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

function delBatchOTApplyCallback(OP_FLAG,form,callback) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApply/delPOvertimeApplyInBatch");
    alertMsg.confirm ("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QUEDINGPILIANGQUXIAOMA.b' />",{//确定要批量取消吗?
        okCall:function(){
	    	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch($("#viewApplyOTBatchInfoHAEList"));
						alertMsg.correct(data.message);
					}else{
						if(data.result=="2"){
							alertMsg.info(data.message);
						}else{
							alertMsg.error(data.message);
						}
					}   
		   	 	}  ,
				error: DWZ.ajaxError
			});
        }});
	return false;
}
</script>
<div class="pageContent" style="padding:0;">
<form name="delBatchOTApplyAffirmForm" id="delBatchOTApplyAffirmForm" method="post" action="/ess/infoApply/delPOvertimeApplyInBatch" 
	  onsubmit="return delBatchOTApplyCallback(this, navTabAjaxDone);"> 
   <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;"></div>
	<table width="150%" class="list">
		<thead>
			<tr>
			        <th width="2%">No.</th>
			        <th>
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th><spring:message code="ess.infoApply.EMP_ID" /><!--工号--></th>
				    <th><spring:message code="ess.infoApply.NAME" /><!--姓名--></th>
					<th><spring:message code="ess.viewApply.title.overtimeApplyType" /><!--加班类型--></th>
					<th><spring:message code="ess.infoApply.title.overtimeTime" /><!--加班日期--></th>
					<th><spring:message code="ess.infoApply.title.startTime" /><!--开始时间--></th>
					<th><spring:message code="ess.infoApply.end_time" /><!--结束时间--></th>
					<th><spring:message code="ess.infoApply.overtime_hours" /><!--加班时长--></th>
					<th><spring:message code="ess.infoApply.Reason" /><!--原因--></th>
					<th><spring:message code="ar.attendanceView.viewNoSwipingCard.status" /><!--审批状态--></th>
				</tr>
		</thead>
		<tbody>
		<c:forEach items="${viewApplyOTBatchInfoHAEDetail}" var="item"  varStatus="i">
			<tr>
			    <td style="text-align:left">${i.count}</td>
			    <td>
			        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
			        <c:if test="${item.AFFIRM_FLAG eq '14014306' or item.AFFIRM_FLAG eq '14014308'}">
				   		 <input type="checkbox" id="c1${i.count }" name="c1" value="${item.APPLY_NO}" />
				    </c:if>
			    </td>
			    <td class='td_center'>${item.EMPID}</td>
			    <td class='td_center'>${item.LOCAL_NAME}</td>
				<td class='td_center'>${item.OT_TYPE_CODE_NAME}</td>
				<td class='td_center' >${item.APPLY_OT_DATE}</td>
				<td class='td_center' >${item.OT_FROM_TIME}</td>
				<td class='td_center' >${item.OT_TO_TIME}</td>
				<td class='td_center' >
				${item.OT_APPLY_HOUR}&nbsp;<spring:message code="ar.viewitemparameter.title.xiaoshi" /><!-- 小时 -->
				</td>				
				<td class='td_center' >${item.APPLY_OT_REMARK}</td>
				<td class='td_center' >${item.AFFIRM_FLAG_NAME}&nbsp;&nbsp;
				                       <c:if test="${item.CONFIRM_FLAG  eq 0}">
										    <spring:message code="ess.viewApply.title.notConfirmed" /><!-- 人事未确认 -->
									   </c:if>
									   <c:if test="${item.CONFIRM_FLAG ne 0}">
										    <spring:message code="ar.viewAttendanceManagentForSerchInfo.RENSHIYIQUEREN.b" /><!-- 人事已确认 -->
									   </c:if></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
</div>














