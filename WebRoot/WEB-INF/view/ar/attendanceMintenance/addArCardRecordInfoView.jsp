<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateCallback_addarcardrecordinfoview(form, callback) {

	document.addarcardrecordinfoview.PERSON_ID.value = document.addarcardrecordinfoview.personId.value;
	
	if(document.addarcardrecordinfoview.PERSON_ID.value == ''){
		//工号不能为空
		alertMsg.error("<spring:message code='ar.viewarcardrecord.title.empidnotnull'/>");
		return false;
	}

	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
<div class="pageContent">
	<form id="addarcardrecordinfoview" name="addarcardrecordinfoview" method="post" action="/ar/attendanceMintenance/addArCardRecordInfo" class="pageForm required-validate" onsubmit="return validateCallback_addarcardrecordinfoview(this,dialogAjaxDoneWithForm);">
			<div class="pageFormContent nowrap" layoutH="56">
				<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
				</ul>
			</div>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="lge_table">
			<tr>
				<td class="td_title">
					<!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</td>
				<td class="td_type">
					<input id="PERSON_ID" name="PERSON_ID" value="" type="hidden"/>
					<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
					<input name="dwz.person.empId" type="text" class="required" readOnly lookupGroup="person"/>
					<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?firstFlag=1&limit=ar&pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
				</td>
			</tr>
			<tr>
				<td class="td_title">
					<!-- 考勤日期 --><spring:message code="ess.infoApply.attendance_date"/>
				</td>
				<td class="td_type">
				    <input type="text" id="AR_DATE_STR" name="AR_DATE_STR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd',lang:'en'})"/>
					
				</td>
			</tr>
			<tr>
				
				<td class="td_title">
					<!-- 时间 --><spring:message code="ar.viewarcardrecord.title.shijian"/>
				</td>
				<td class="td_type">
				    <input type="text" id="R_DATE" name="R_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd.MM.yyyy',lang:'en'})"/>&nbsp;&nbsp;
				    
					<!--<input type="text" name="R_DATE" class="date required" value="" yearstart="-20" yearend="20" readonly="true" />-->&nbsp;&nbsp;
					<input id="R_HOUR" name="R_HOUR" type="text" class="digits required" size="3" maxlength="2"  min="0" max="23" />&nbsp;&nbsp;
					<input id="R_MINITE" name="R_MINITE" type="text" class="digits required" size="3" maxlength="2" min="0" max="59" />&nbsp;&nbsp;
					
				</td>
			</tr>
			<tr>
				<td class="td_title">
					<!-- 类型 --><spring:message code="ar.viewarcardrecord.title.leixing"/>
				</td>
				<td class="td_type">
					<select id="DoorType" name="DoorType">
						<option value="IN"><!-- 进门 --><spring:message code="ar.viewarcardrecord.title.jinmen"/></option>
						<option value="OUT"><!-- 出门 --><spring:message code="ar.viewarcardrecord.title.chumen"/></option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td_title">
					<!-- 备注 --><spring:message code="ar.viewarcardrecord.title.beizhu"/>
				</td>
				<td class="td_type">
					<textarea name="REMARK" cols="60" rows="4"></textarea>
				</td>
			</tr>
		</table>	
		</div>
		
	</form>
</div>
