<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddFileRoomInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",// 确定要保存吗？
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

function validateDeleteFileRoomInfoCallback(form,callback) {
	var ACTIVITY = $("#ACTIVITY",navTab.getCurrentPanel()).val();
	if(ACTIVITY == 4){
		alertMsg.warn("<spring:message code='sys.viewfileRoomList.PINGJIAJIESHUBUNENGSHANCHU.b' />");//评价已经结束，不能删除
		return false;
	}
	var $form = $("#" + form,navTab.getCurrentPanel());	
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/evs/manage/deleteFileRoomInfo',
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
		<form id="viewAddFileRoomInfo" method="post" action="/evs/manage/addFileRoomInfo" class="pageForm required-validate" 
			onsubmit="return validateAddFileRoomInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">
										<!-- 标题 --><spring:message code="ess.title.Title" />
									</td>
									<td width="85%" class="td_type">
										<input type="hidden" id="SEQ" name="SEQ" value="${fileRoomInfo.SEQ}"/>
										<input type="text" name="TITLE" value="${fileRoomInfo.TITLE}" size="112" class="required"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<!-- 备注 --><spring:message code="ess.trans.title.remark" />
									</td>
									<td width="85%" class="td_type">
										<textarea name="REMARK"  style="width:650px;height:80px">${fileRoomInfo.REMARK}</textarea>
									</td>
								</tr>
							</table>	
						</td>
					</tr>
				</table>
			</div>
			
			<c:if test="${empty fileRoomInfo.SEQ}">
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><!-- 附加文件 --><spring:message code="org.title.Attached_File" /></span></a>
						<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');"><span><!-- 删除 --><spring:message code="org.title.DELETE" /></span></a>
					</div>
					<table id="fileTable" class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><!-- 附件 --><spring:message code="ess.empInfo.enclosure" /></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</c:if>
			<c:if test="${not empty fileRoomInfo.SEQ}">
				<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialog_new('viewFileRoomList_unit','/evs/manage/viewAddFileRoomInfo?SEQ=${fileRoomInfo.SEQ}','${fileRoomInfo.SEQ}','FILE_ROOM','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><!-- 附加文件 --><spring:message code="org.title.Attached_File" /></span></a>
						<a class="w_button" href="#" onclick="deleteAttList_new('viewFileRoomList_unit','/evs/manage/viewAddFileRoomInfo?SEQ=${fileRoomInfo.SEQ}',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')"><span><!-- 删除 --><spring:message code="org.title.DELETE" /></span></a>
					</div>
					<table class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><!-- 附件 --><spring:message code="ess.empInfo.enclosure" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${fileRoomInfo.fileList}" var="item" varStatus="i">
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
