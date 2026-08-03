<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
function validateAddOrgExperirnceInsideInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	//确定要保存吗？
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

</script>
<div class="pageContent" style="padding:0;">
     <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;"><!-- 个人基本事项 --><spring:message code="org.title.personBasic" /></div>
	<table width="100%" class="user_table margin_b">
		<tbody>
			<tr>
				<td rowspan="6" width="10%" class="td_type"><img src="${arBaseEmpInfoDetail.PHOTO_PATH}" width="115" height="150"></td>
				<td class="td_title" width='20%'><!-- 姓名 --><spring:message code="ess.infoApply.NAME" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.LOCAL_NAME}</td>
				<td class="td_title" width='20%'><!-- 工号 --><spring:message code="ess.infoApply.EMP_ID" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.EMPID}</td>
			</tr>
			<tr>
				<td class="td_title" width='20%'><!-- 部门 --><spring:message code="org.title.dept" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.DEPTNAME}</td>
				<td class="td_title" width='20%'><!-- 职位 --><spring:message code="hrm.recruitManage.DUTY_NO" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.DUTY_NAME}</td>
			</tr>
			<tr>
				<td class="td_title" width='20%'><!-- 员工状态 --><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.EMP_OFFICE_NAME} &nbsp;&nbsp;&nbsp;&nbsp;
					<c:if test="${arBaseEmpInfoDetail.EMP_OFFICE eq '1375' or arBaseEmpInfoDetail.EMP_OFFICE eq '15120'}"><!-- 离职日期 --><spring:message code="ess.empInfo.leaveDate" />:&nbsp;${arBaseEmpInfoDetail.DATE_LEFT}</c:if>
				</td>
				<td class="td_title" width='20%'><!-- 员工类型 --><spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.EMP_TYPE}</td>
			</tr>
			<tr>
				<td class="td_title" width='20%'><!-- 入社日期 --><spring:message code="ess.empInfo.date_of_agency" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.DATE_STARTED}</td>
				<td class="td_title" width='20%'><!-- 在职时间 --><spring:message code="ess.empInfo.in_service_time" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.WORK_TIME}</td>
			</tr>
			<tr>
				<td class="td_title" width='20%'><!-- 试用期结束日 --><spring:message code="hr.enpinfo.title.EMP.PROBATION_END_DATE" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.END_PROBATION_DATE}</td>
				<td class="td_title" width='20%'><!-- 工作地 --><spring:message code="org.title.WORD_AREA_NAME" /></td>
				<td class="td_type" width='30%'>${arBaseEmpInfoDetail.WORK_AREA_NAME}</td>
			</tr>
			<tr>
				
				
			</tr>
		</tbody>
	</table>
    <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 20px; line-height: 20px;"><!-- 考勤基本事项 --><spring:message code="ar.viewArBaseEmpInfoList.KAOQINJIBENSHIXIANG.b" /></div>
	<table width="100%" class="user_table margin_b">
		<tbody>
			<tr>
				<td class="td_title" width='20%'><!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu" /></td>
				<td class="td_type" width='30%'>${arEmpShiftGroupFinalInfo.SHIFT_NAME}</td>
				<td class="td_title" width='20%'><!-- 开始时间 --><spring:message code="ess.infoApply.title.startTime" /></td>
				<td class="td_type" width='30%'>${arEmpShiftGroupFinalInfo.START_DATE}</td>
			</tr>
			<tr>
				<td class="td_title" width='20%'><!-- 变更者 --><spring:message code="org.title.UPDATED_IP" /></td>
				<td class="td_type" width='30%'>${arEmpShiftGroupFinalInfo.CREATED_BY}</td>
				<td class="td_title" width='20%'><!-- 变更时间 --><spring:message code="org.title.UPDATE_DATE" /></td>
				<td class="td_type" width='30%'>${arEmpShiftGroupFinalInfo.CREATE_DATE}</td>
			</tr>
		</tbody>
	</table>
		
<%-- <c:if test="${LoginUser.cpnyId eq 'SST'}">
	<table width="100%" class="user_table margin_b">
		<tbody>
			<tr>
				<td class="td_title" width='20%'>工时类型</td>
				<td class="td_type" width='30%'>
				   <ait:SelectSyCodeByCpnyID name="WORK_HOUR_TYPE"
						parentNo="14014299" cnpyID="${LoginUser.cpnyId}" limit="all"
						selected="${arBaseEmpInfoDetail.WORK_HOUR_TYPE}" />
				</td>
				<td class="td_title" width='20%'>ID卡号</td>
				<td class="td_type" width='30%'>
				    <input type="text" id="ID_CARD_NO" name="ID_CARD_NO" value="${arBaseEmpInfoDetail.ID_CARD_NO}"/>
				    <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${arBaseEmpInfoDetail.PERSON_ID}"/>
				</td>
			</tr>			
		</tbody>
	</table>
	</c:if> --%>
</div>