<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateCallback_addTempEmpInfoList(form, callback) {

	document.addTempEmpInfoList.PERSON_ID.value = document.addTempEmpInfoList.personId.value;
	
	if(document.addTempEmpInfoList.PERSON_ID.value == ''){
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

function fangdajing13(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY13').val()));
	$('#fangda13').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?searchChange=addTempEmpInfoList&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda13').click();
}
</script>
<div class="pageContent">
	<form id="addTempEmpInfoList" name="addTempEmpInfoList" method="post" action="/hrm/empinfo/addTempEmpInfo" class="pageForm required-validate" onsubmit="return validateCallback_addTempEmpInfoList(this,dialogAjaxDoneWithForm);">
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
					<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?firstFlag=1&limit=ar&pageNum=1" lookupGroup="person"><!-- 查找带回 -->
					<spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					<!-- <a class="btnLook" id="fangda13" onclick="fangdajing13()" href="/hrm/empinfo/viewEmpInfoListTanchu?searchChange=addTempEmpInfoList" lookupGroup="person"></a> -->
				</td>
			</tr>
			<tr>
				<td class="td_title"><spring:message code="inct.salesman.classify"/><!-- 信息区分 --></td>
				<td class="td_type">
				    <ait:SelectSyCodeByCpnyID name="INFOR_DIS_CODE" id="INFOR_DIS_CODE" parentNo="14014361" cnpyID="${defaultCpny}" selected="${personInfo.INFOR_DIS_CODE}" limit="all" />
					
				</td>
			</tr>
			<tr>
				<td class="td_title" >
					<spring:message code="ar.viewcycle.title.kaishiri"/><!-- 开始日期 --></th>
				</td>
                <td class="td_type">
					<input name="START_DATE"  id="START_DATE" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${personInfo.START_DATE}" />
				</td>
		   </tr>
		   <tr>
		   	<td class="td_title" >
					<spring:message code="ar.viewcycle.title.jieshuri"/><!-- 结束日期 -->
				</td>
                <td class="td_type">
					<input name="END_DATE"  id="END_DATE" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${personInfo.END_DATE}" />
				</td>
		   </tr>
		   <tr>
		   	<td class="td_title" > <spring:message code="ess.attendance.ot"/><!-- Overtime --> </td>
               <td class="td_type">
				<select id="OT_FLAG" name="OT_FLAG" >
						<option value="1" ><!--Yes --> <spring:message code="ar.viewcycle.content.yes" /></option>
						<option value="0" ><!-- No --> <spring:message code="ar.viewcycle.content.no" /></option>
					</select>
			</td>
		   </tr>
			<tr>
				<td class="td_title">
					<spring:message code="hrm.contract.content"/><!-- 内容 -->
				</td>
				<td class="td_type" colspan="3">
					<textarea name="SPECIAL_CONTENT"  id="SPECIAL_CONTENT" style="width:500px;height:80px">${personInfo.SPECIAL_CONTENT}</textarea>
				</td>
			</tr>
			<tr>
				<td class="td_title" width="1%">
					<spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!--附加文件-->
				</td>
				<td class="td_type" width="20%">
					<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message code="button.add"/><!--添加--></span>
					</a>
					<a class="w_button" href="#" onclick="deleteAttListInsertPlan();"><span><spring:message code="button.delete"/><!--删除--></span>
					</a>
					<table id="fileTable" class="list" width="100%">
						<thead>
						</thead>
						<tbody>
						</tbody>
					</table>
				</td>
			</tr>
		</table>	
		</div>
		
		
	</form>
</div>
