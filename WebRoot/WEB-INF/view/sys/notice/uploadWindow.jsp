<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function validateCallbackuploadWindow(form,callback) {	
	var $form = $(form);	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){
		
			openOnRight('${val}','${id}');
		
			alertMsg.info("<spring:message code="hr.alert.message.upload_success"/>"); //上传成功
			
			$.pdialog.closeCurrent();
		},
		error: DWZ.ajaxError
	});	
	return false;
}
//文件上传的js方法
function uploadifySuccess_upload(file, data, response){
  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
  var files = $("#fileNmae1_upload").html();
  var fileUrl = $("#fileUrl_upload").val();
  var fileName = $("#fileName_upload").val();
  var fileResult = data.split(";");
  //第一个文件
  if(files=="" || files==null){
    files = fileResult[0];
    fileName = fileResult[0];
    fileUrl = fileResult[1];
  }else{
    files+=";"+fileResult[0];
    fileName+=";"+fileResult[0];
    fileUrl+=";"+fileResult[1];
  }
  $("#fileNmae1_upload").html(files);
  $("#fileUrl_upload").val(fileUrl);
  $("#fileName_upload").val(fileName);
}
</script>
<div class="pageContent" layoutH="10">
	<form id="uploadWindowForm" method="post" action="/sys/notice/uploadAtt" class="pageForm required-validate" onsubmit="return validateCallbackuploadWindow(this, dialogAjaxDone)">
		<div class="pageFormContent">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" >
						<spring:message code="hrm.recruitManage.ENCLOSURE"/><!-- 附件 -->
					</td>
					<td class="td_type" colspan="4">
						<span id="fileNmae1_upload"></span>
						<input type="hidden" name="APPLY_NO" value="${seq}"/>
						<input type="hidden" name="APPLY_TYPE" value="${applyType}"/>
					</td>
				</tr>
				<tr>
				<td width="20%"  class="td_title">
										<spring:message code="hrm.alert.empinfo.upload_Enclosure"/><!-- 附件上传 -->
									</td>
				 <td class="td_type" colspan="1">
				 <input id="testFileInput_upload"
					type="file" name="file"
					 uploaderOption="{
						swf:'/resources/js/uploadify/scripts/uploadify.swf',
					    uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${LoginUser.personId}',
						formData:{ajax:1},
						queueID:'fileQueue_upload',
						buttonText:'<spring:message code="hrm.empinfo.CHANGE"/>',//请选择
						height:25,
						width:100,
						auto:false,
						onUploadSuccess:uploadifySuccess_upload,
						removeTimeout:1
					    }" />
					<div id="fileQueue_upload" class="fileQueue"></div> 
					<input type="hidden" id="fileUrl_upload" name="fileUrl" value="" /> 
					<input type="hidden" id="fileName_upload" name="fileName" value="" />

					<div class="buttonActive">
						<div class="buttonContent">
							<!--保存-->
							<button type="button"
								onclick="$('#testFileInput_upload').uploadify('upload', '*');return false;">
								<spring:message code="hrm.empinfo.upload"/><!-- 上传 --></button>
						</div>
					</div>
					<div class="buttonActive">
						<div class="buttonContent">
							<!--提交-->
							<button type="button"
								onclick="$('#testFileInput_upload').uploadify('cancel', '*');return false;">
								<spring:message code="hrm.empinfo.cancel"/><!-- 取消 --></button>
						</div>
					</div></td>
			</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!-- 保存 --></button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="hrm.empinfo.close"/><!-- 关闭 --></button></div></div></li>
			</ul>
		</div>
	</form>	
</div>