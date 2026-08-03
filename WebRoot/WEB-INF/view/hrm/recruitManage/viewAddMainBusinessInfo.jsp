<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddMainBusinessInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("确定要保存吗？",
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
		<form id="viewAddMainBusinessInfo" method="post" action="/hrm/recruitManage/addMainBusinessInfo" class="pageForm required-validate" >
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">
										主要业务
									</td>
									<td width="85%" class="td_type">
										${resumeInfo.DESCRIPTION} -> ${resumeInfo.CODE_NAME}
										<input type="hidden" id="CODE_NO" name="CODE_NO" value="${resumeInfo.CODE_NO}"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										说明
									</td>
									<td width="85%" class="td_type">
										<textarea name="REMARK"  style="width:650px;height:80px">${resumeInfo.REMARK}</textarea>
									</td>
								</tr>
							</table>	
						</td>
					</tr>
				</table>
			</div>
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialog_new('viewMainBusinessList_unit','/hrm/recruitManage/viewAddMainBusinessInfo?CODE_NO=${resumeInfo.CODE_NO}','${resumeInfo.CODE_NO}','MAIN_BUSINESS','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span>附加文件</span></a>
						<a class="w_button" href="#" onclick="deleteAttList_new('viewMainBusinessList_unit','/hrm/recruitManage/viewAddMainBusinessInfo?CODE_NO=${resumeInfo.CODE_NO}',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')"><span>删除</span></a>
					</div>
					<table class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%">附件</th>
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
	  	</form>	
