<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddHrResumeInfoCallback(form,callback) {
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

function validateDeleteHrResumeInfoCallback(form,callback) {
	var $form = $("#" + form,navTab.getCurrentPanel());	
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete"/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deleteResumeInfo',
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
		<form id="viewAddHrResumeInfo" method="post" action="/hrm/empinfo/addResumeInfo" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">
										 <spring:message code="hrm.empinfo.name"/><!-- 姓名 -->
									</td>
									<td width="35%" class="td_type">
										<input type="hidden" id="SEQ" name="SEQ" value="${resumeInfo.SEQ}"/>
										<input type="text" id="TITLE" name="TITLE" value="${resumeInfo.TITLE}" size="60" class="required"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.SEXCODE"/><!-- 性别 --> 
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="SEX" selected="${resumeInfo.SEX}" parentNo="1324" limit="all"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.AGE"/><!-- 年龄 --> 
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="AGE" name="AGE" value="${resumeInfo.AGE}" size="60"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.FAM_PHONE"/><!-- 联系电话 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="PHONE" name="PHONE" value="${resumeInfo.PHONE}" size="60" class="required"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/><!-- 部门 -->
									</td>
									<td width="35%" class="td_type">
										<ait:deptList name="DEPT" limit="super" id="viewAddRecruitInfo_deptList"/>
										<ait:deptTreeIcon name="DEPT" limit="super" id="viewAddRecruitInfo_deptList" selected="${resumeInfo.DEPT}"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.LANGUAGE"/><!-- 语言 -->
									</td>
									<td width="35%" class="td_type">
										<ait:SelectSyCodeByCpnyID name="LANGUAGE_TYPE" selected="${resumeInfo.LANGUAGE_TYPE}" parentNo="1703" limit="all"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.REMARK"/><!-- 备注 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<textarea name="REMARK"  style="width:650px;height:120px">${resumeInfo.REMARK}</textarea>
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
						<span><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');">
						<span><spring:message code="button.delete"/><!-- 删除 --></span></a>
					</div>
					<table id="fileTable" class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE"/><!-- 附件 --></th>
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
						<a class="w_button" href="#" onclick="uploadAttDialog_new('viewHrResumeList_unit','/hrm/empinfo/viewAddResumeInfo?SEQ=${resumeInfo.SEQ}','${resumeInfo.SEQ}','HR_RESUME','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')">
						<span><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttList_new('viewHrResumeList_unit','/hrm/empinfo/viewAddResumeInfo?SEQ=${resumeInfo.SEQ}',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')">
						<span><spring:message code="button.delete"/><!-- 删除 --></span></a>
					</div>
					<table class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE"/><!-- 附件 --></th>
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
