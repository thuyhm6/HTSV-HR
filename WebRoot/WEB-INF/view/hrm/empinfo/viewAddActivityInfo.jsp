<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddActivityInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM" />",//确定要保存吗？
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

function validateDeleteActivityInfoCallback(form,callback) {
	var $form = $("#" + form,navTab.getCurrentPanel());	
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete" />",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deleteActivityInfo',
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
		<form id="viewAddActivityInfo" method="post" action="/hrm/empinfo/addActivityInfo" class="pageForm required-validate" 
			onsubmit="return validateAddActivityInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.title" /><!-- 标题 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<input type="hidden" id="SEQ" name="SEQ" value="${resumeInfo.SEQ}"/>
										<input type="text" id="NAME" name="NAME" value="${resumeInfo.NAME}" size="60" />
									</td>
								</tr>
								<tr style="display:none">
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.PARTICIPANTS" /><!-- 参加人 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="PARTICIPANTS" name="PARTICIPANTS" value="${resumeInfo.PARTICIPANTS}" />
									</td>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.PERSON_NUMBER" /><!-- 人数 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="PERSON_NUMBER" name="PERSON_NUMBER" value="${resumeInfo.PERSON_NUMBER}" />
									</td>
									
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.activite_time" /><!-- 活动时间 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<input type="text" id="START_DATE" name="START_DATE" class="Wdate" size="35" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${resumeInfo.START_DATE}" />
										~<input type="text" id="END_DATE" name="END_DATE" class="Wdate" size="35" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${resumeInfo.END_DATE}" />
									</td>
								</tr>
								<c:if test="${ not empty resumeInfo.SEQ}">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.Active_object" /><!-- 活动对象 -->
									</td>
									<td width="85%" class="td_type" colspan="3">${activityEmpListSize }<spring:message code="hrm.empinfo.people" /><!-- 人 -->
										<a href="/hrm/empinfo/viewActivityEmpList?seach_ACTIVITY_SEQ=${resumeInfo.SEQ}" target="dialog" mask="true" width="700" height="500">
										<spring:message code="hrm.empinfo.View_details" /><!-- 查看详情 --></a>
									</td>
								</tr>
								</c:if>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.Active_emile" /><!-- 活动邮件 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<textarea name="EMAIL_CONTENT"  style="width:650px;height:120px" class="editor" tools="Cut,Copy,Paste,|,Fullscreen">${resumeInfo.EMAIL_CONTENT}</textarea>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.Activity_summary" /><!-- 活动总结 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<textarea name="REMARK"  style="width:650px;height:120px" class="editor" tools="Cut,Copy,Paste,|,Fullscreen">${resumeInfo.REMARK}</textarea>
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
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');">
						<span><spring:message code="hrm.recruitManage.ATTACHED_FILE" /><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');">
						<span><spring:message code="button.delete" /><!-- 删除 --></span></a>
					</div>
					<table id="fileTable" class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE" /><!-- 附件 --></th>
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
						<a class="w_button" href="#" onclick="uploadAttDialog_new('viewActivityList_unit','/hrm/empinfo/viewAddActivityInfo?SEQ=${resumeInfo.SEQ}','${resumeInfo.SEQ}','HR_ACTIVITY','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')">
						<span><spring:message code="hrm.recruitManage.ATTACHED_FILE" /><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttList_new('viewActivityList_unit','/hrm/empinfo/viewAddActivityInfo?SEQ=${resumeInfo.SEQ}',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')">
						<span><spring:message code="button.delete" /><!-- 删除 --></span></a>
					</div>
					<table class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE" /><!-- 附件 --></th>
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
