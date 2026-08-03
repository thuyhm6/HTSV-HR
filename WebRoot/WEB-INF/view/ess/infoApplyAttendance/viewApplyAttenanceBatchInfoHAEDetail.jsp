<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
function delBatchLeaveApplyCallback(OP_FLAG,form,callback) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("SINGLE_LEAVE");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApplyAttendance/delAttedanceApplyInBatch");
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
						navTabSearch($("#viewApplyAttenanceBatchInfoHAEList"));
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
<form name="delBatchLeaveApplyAffirmForm" id="delBatchLeaveApplyAffirmForm" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delBatchLeaveApplyCallback(this, navTabAjaxDone);"> 
   <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;"></div>
	<table width="150%" class="list">
		<thead>
			<tr>
			        <th width="2%">No.</th>
			        <th>
				    	<input type="checkbox" class="checkboxCtrl" group="SINGLE_LEAVE" />
				    </th>
				    <th><spring:message code="ess.infoApply.EMP_ID" /><!--工号--></th>
				    <th><spring:message code="ess.infoApply.NAME" /><!--姓名--></th>
					<th><spring:message code="ess.infoApply.attendance_type" /><!--考勤类型--></th>
					<th><spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE" /><!--开始日期--></th>
					<th><spring:message code="ess.infoApply.title.startTime" /><!--开始时间--></th>
					<th><spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE" /><!--结束日期--></th>
					<th><spring:message code="ess.infoApply.end_time" /><!--结束时间--></th>
					<th><spring:message code="ar.viewArAdjustRest.title.APPLYLENGTH" /><!--申请时长--></th>
					<th><spring:message code="ess.infoApply.Reason" /><!--原因--></th>
					<th><spring:message code="ar.attendanceView.viewNoSwipingCard.status" /><!--审批状态--></th>
				</tr>
		</thead>
		<tbody>
		<c:forEach items="${viewApplyAttenanceBatchInfoHAEDetail}" var="item"  varStatus="i">
			<tr>
			    <td style="text-align:left">${i.count}</td>
			    <td>
			        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
			        <c:if test="${item.AFFIRM_FLAG eq '14014306' or item.AFFIRM_FLAG eq '14014308'}">
				   		 <input type="checkbox" id="SINGLE_LEAVE" name="SINGLE_LEAVE" value="${item.APPLY_NO}" />
				    </c:if>
			    </td>
			    <td class='td_center'>${item.EMPID}</td>
				<td class='td_center' >${item.LOCAL_NAME}</td>
				<td class='td_center'>${item.LEAVE_TYPE_CODE_NAME}</td>
				<td class='td_center' >${item.FROM_DATE}</td>
				<td class='td_center' >${item.FROM_TIME}</td>
				<td class='td_center' >${item.TO_DATE}</td>
				<td class='td_center' >${item.TO_TIME}</td>
				<td class='td_center' >
				<c:if test="${item.APPLY_LENGTH ge item.DAY_HOURS }"><fmt:formatNumber type="number"  value="${item.APPLY_LENGTH/item.DAY_HOURS + (item.APPLY_LENGTH%item.DAY_HOURS == 0 ? 0 : -0.5)}" pattern="#" maxFractionDigits="0"/>&nbsp;<spring:message code="ar.viewitemparameter.title.dayofunit" /><!-- 天 --></c:if>${item.APPLY_LENGTH%item.DAY_HOURS}&nbsp;<spring:message code="ar.viewitemparameter.title.xiaoshi" /><!-- 小时 -->
				</td>				
				<td class='td_center' >${item.LEAVE_REASON}</td>
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














