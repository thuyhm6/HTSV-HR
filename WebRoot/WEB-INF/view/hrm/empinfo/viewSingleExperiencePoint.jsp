<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">

function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>",//确定要保存吗？
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

function validateDeleteResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete"/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deleteExperiencePoint',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
//function expDateSize(a){
//	var startdate=$('#exp_START_DATE').val();
//	var enddate=$('#exp_END_DATE').val();
//	if(startdate!=''&&enddate!=''){
//		sdate=parseInt(startdate.replace('-','').replace('-',''));
//		edate=parseInt(enddate.replace('-','').replace('-',''));
//		if(sdate>edate){
//			alert("<spring:message code="ar.attendanceView.viewNoSwipingCard.beginTimeDontendTime"/>");//开始时间不能大于结束时间!
//			if(a=='0'){
//				$('#exp_START_DATE').attr('value','');
//			}else if(a=='1'){
//				$('#exp_END_DATE').attr('value','');
//			}
//		}
//	}
//}
</script>
<div>
<form id="editExperiencePoint" method="post"
	action="/hrm/empinfo/editExperiencePoint"
	class="pageForm required-validate"
	onsubmit="return validateAddResumeInfoCallback(this,navTab);"><input
	TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}">
<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID"
	VALUE="${PERSON_ID}"> <input TYPE="hidden" NAME="isEssSystem"
	VALUE="${isEssSystem}"> <input type="hidden"
	name="WORK_EXPER_NO" id="WORK_EXPER_NO"
	value="${personInfo.WORK_EXPER_NO}">
<div>
<table class="user_table" width="100%" border="1" cellpadding="2"
	cellspacing="1">
	<tr>
		<td>
		<table class="user_table">
		  <tr>
		  	<td class="td_title" width="4%"><spring:message
					code="hrm.empinfo.COMPANY_NAME" /><!--公司--></td>
				<td class="td_type" width="65%" colspan="3"><input type="text"
					class="required" id="CPNY_NAME" name="CPNY_NAME"
					value="${personInfo.CPNY_NAME }" size="100px"></td>
		  </tr>
		  <tr>
				<td class="td_title" width="4%"><spring:message code="ess.infoApply.DEPT" /><!--部门--></td>
				<td class="td_type" width="25%"><input type="text" id="DEPT_NAME" name="DEPT_NAME" value="${personInfo.DEPT_NAME }"></td>
				<td class="td_title" width="4%"><spring:message code="hr.hrm.empinfo.MONTH_SALARY.Z" /><!--月薪--></td>
				<td class="td_type" width="25%"><input type="text" id="PAY_YEAR" name="PAY_YEAR" value="${personInfo.PAY_YEAR }"> </td>
			</tr> 
			<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<tr>
				<td class="td_title" width="4%"><spring:message code="rp.report.title.dutyinfo" /><!--岗位--></td>
				<td class="td_type" width="25%"><input type="text" id="DUTY" name="DUTY" value="${personInfo.DUTY }"></td>
				<td class="td_title" width="4%"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" /><!--主要业务--></td>
				<td class="td_type" width="25%"><input type="text" id="POSITION" name="POSITION" value="${personInfo.POSITION }"> </td>
			</tr>  
			</c:if>
			<tr>
				<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.DATE_STARTED" /><!-- 入职日期 --></td>
				<td class="td_type" width="25%"><input name="START_DATE" id="exp_START_DATE" onchange="expDateSize(0)" class="Wdate required"
					onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})" value="${personInfo.START_DATE}" /></td>
				<td class="td_title" width="4%"><spring:message code="hrm.recruitManage.LEAVE_DATE" /><!-- 离职日期 --></td>
				<td class="td_type" width="25%"><input name="END_DATE" id="exp_END_DATE" onchange="expDateSize(1)" class="Wdate required"
					onClick="WdatePicker({dateFmt:'MM/yyyy',lang:'en'})" value="${personInfo.END_DATE}" /></td>
			</tr>
			<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<tr>
				<td class="td_title" width="4%"><spring:message code="hr.viewCondSql.title.LIZHIYUANYIN" /><!--离职事由--></td>
				<td class="td_type" width="25%" colspan='3'><textarea id="RESIGN_REASON" name="RESIGN_REASON" style="width: 560px; height: 80px">${personInfo.RESIGN_REASON}</textarea>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="4%"><spring:message code="hrm.empinfo.REMARK" /><!--备注--></td>
				<td class="td_type" width="25%" colspan='3'><textarea
					id="REMARK" name="REMARK" style="width: 560px; height: 80px">${personInfo.REMARK}</textarea>
				</td>
			</tr>
			</c:if>
		</table>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			<tr>
				<c:if test="${not empty personInfo.UPDATED_BY}">
					<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者--></td>
					<td class="td_type" width="25%">${personInfo.UPDATED_BY
					}&nbsp&nbsp${personInfo.UPDATED_IP }</td>
					<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></td>
					<td class="td_type" width="25%">${personInfo.UPDATE_DATE }</td>
				</c:if>
				<c:if test="${empty personInfo.UPDATED_BY}">
					<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者--></td>
					<td class="td_type" width="25%">${personInfo.CREATED_BY
					}&nbsp&nbsp${personInfo.CREATED_IP }</td>
					<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></td>
					<td class="td_type" width="25%">${personInfo.CREATE_DATE }</td>
				</c:if>
			</tr>
		</table>
		</td>
	</tr>
</table>
</td>
</tr>
</table>
</div>
</form>
</div>
