<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddResumeInfoCallback(form,callback) {
	var ids= document.getElementsByName("ACTIVITY");
	 for(var i=0;i<ids.length-1;i++){
		 var index = ids[i].id.substring(9);
			if($("#ACTIVITY_"+index,navTab.getCurrentPanel()).val()!='14013947'){
				alertMsg.error('<spring:message code="org.orgManage.COMPLETE_DELETE_THEN_ADD.Z" />');//"请先把未完成的组织完成或删除，再进行添加"
				return false;
			}
	} 
			
	var $form = $("#" + form);	
	if($("#EXPERIENCE_TYPE",navTab.getCurrentPanel()).val()==''){
		alertMsg.error('<spring:message code="org.title.EXPERIENCE_TYPE_ISNOTNULL" />');
		return false;
	}
	if (!$form.valid()) {
		return false;
	}
	if($("#ACTIVITY_YN",navTab.getCurrentPanel()).val()=='14013947'){
		alertMsg.error('<spring:message code="org.orgManage.ADD_NEW_ORGANIZATION.Z" />');//'请添加一个新的组织'
		return false;
	}
	alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
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
	var isCurrent = $('#IS_CURRENT_ORG_IN').val();
	if(isCurrent=='Y'){
		alertMsg.error('<spring:message code="org.orgManage.ORGANIZATION.NOT.DELETE.Z" />');//'现组织不可删除'
		return false;
	}
	alertMsg.confirm('<spring:message code="button.delete.sure" />',
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/org/orgManage/deleteResumeInfo',
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
		<form id="viewAddResumeInfo" method="post" action="/org/orgManage/addResumeInfo" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title"><spring:message code="org.title.ORG_CHANGE_NO" /><!-- 组织变更代码 --></td>
									<td width="35%" class="td_type">
										<input type="text" id="NO" name="NO" value="${resumeInfo.NO}" class="required"  size="35"/>
										<input type="hidden" id="SEQ" name="SEQ" value="${resumeInfo.SEQ}"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.DATE" /><!-- 日期 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CHANGE_DATE" name="CHANGE_DATE" class="Wdate required" readonly="true" value="${resumeInfo.CHANGE_DATE}" size="35"/>
									</td>
								</tr>
								<tr>
									<td  width="15%" class="td_title">
									   <spring:message code="org.title.EXPERIENCE_TYPE" /><!-- 变更类型 --> 
									</td>
									<td  width="35%" class="td_type">
									 	<ait:SelectSyCodeByCpnyID id="EXPERIENCE_TYPE" name="EXPERIENCE_TYPE" parentNo="14013935" selected="${resumeInfo.EXPERIENCE_TYPE}" limit="all"/><span class="text_red" style="color:red; align=center">*</span>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.orgManage.GAIBIAN_NAME.Z" /><!-- 组织改编名称 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="RESUME_NAME" name="RESUME_NAME" value="${resumeInfo.RESUME_NAME}" size="60"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.IS_CURRENT_ORG" /><!-- 现组织与否 -->
									</td>
									<td width="35%" class="td_type">
										<input type="checkbox" <c:if test="${resumeInfo.IS_CURRENT_ORG eq 'Y'}">checked</c:if> DISABLED>
										<input type="hidden" id="IS_CURRENT_ORG_IN" value='${resumeInfo.IS_CURRENT_ORG}'>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.status" /><!-- 状态 -->
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="ACTIVITY_display" selected="${resumeInfo.ACTIVITY}" parentNo="14013946" disabled="true" cnpyID="${LoginUser.cpnyId}" />
									   	<input type="hidden" id="ACTIVITY_YN" name="ACTIVITY" value="${resumeInfo.ACTIVITY}">
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.orgManage.GAIBIAN_YUANYIN.Z" /><!-- 改编原因 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<input type="text" id="CHANGE_REASON" name="CHANGE_REASON" value="${resumeInfo.CHANGE_REASON}" size="100"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.REMARK" /><!-- 备注 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<textarea name="REMARK"  style="width:600px;height:100px"  <c:if test="${resumeInfo.ACTIVITY eq '14013947'}">readonly="readonly"</c:if> >${resumeInfo.REMARK}</textarea>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.UPDATED_IP" /><!-- 变更者 -->
									</td>
									<td width="35%" class="td_type">
										${resumeInfo.LOCAL_NAME} ${resumeInfo.UPDATED_IP}
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 -->
									</td>
									<td width="35%" class="td_type">
									   	${resumeInfo.UPDATE_DATE}
									</td> 
								</tr>
							</table>	
						</td>
					</tr>
				</table>
			</div>
			
			<c:if test="${empty resumeInfo.SEQ}">
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message code="org.title.Attached_File" /><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
					</div>
					<table id="fileTable" class="list" width="100%">
						<thead>
							<tr>
								<th width="10%"><spring:message code="org.title.V" /><!-- V --></th>
								<th width="90%"><spring:message code="org.title.enclosure" /><!-- 附件 --></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</c:if>
			<c:if test="${not empty resumeInfo.SEQ}">
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialog_new('viewResumeList_unit','/org/orgManage/viewAddResumeInfo?SEQ=${resumeInfo.SEQ}','${resumeInfo.SEQ}','RESUME','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message code="org.title.Attached_File" /><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttList_new('viewResumeList_unit','/org/orgManage/viewAddResumeInfo?SEQ=${resumeInfo.SEQ}',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
					</div>
					<table class="table" width="100%" layoutH="500">
						<thead>
							<tr>
								<th width="10%"><spring:message code="org.title.V" /></th>
								<th width="90%"><spring:message code="org.title.enclosure" /><!-- 附件 --></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${resumeInfo.fileList}" var="item" varStatus="i">
								<tr>
									<td class='td_center'><input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/></td>
									<td><a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
	  	</form>	
