<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitAppFile(form) {
	if(checkForm()){
		var URL = $('#fileApp').val();
		if(URL.length>0){
			//var fileName = URL.substring(URL.lastIndexOf('\\')+1);
			var suffix =   URL.substring(URL.lastIndexOf('.'));
			var nowDate = new Date().getTime()+''+Math.floor(Math.random()*10);
			//$('#oldAppName').attr('value',fileName);
			//$('#newAppName').attr('value',nowDate+suffix);
	
			$("#PhotoForm").attr("action","/ess/wageApplication/submitApplicationFile?name="+(nowDate+suffix));
			$("#PhotoForm").attr("enctype","multipart/form-data");
			$("#PhotoForm").attr("target","callbackframe");
			$("#PhotoForm").attr("class","pageForm");
			$("#PhotoForm").submit();
			//$("#fileAppUrl").attr('value',URL);
			//$("#PhotoForm").attr("onsubmit","return iframeCallback_uploadfiles(this,attUploadApplyLeave);");
			$.pdialog.closeCurrent();
			navTabSearch($("#reloadAddNewWage"));
		}
	}
}

function iframeCallback_uploadfiles(form, callback){alert(12);
	alert(form);
	alert(callback);
	/*var $form = $(form), $iframe = $("#callbackframe");
	var $iframe = $("#callbackframe");

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>");
		$form.appendTo($iframe);
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}*/
	alert(ss);
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone);
}
//附件上传成功回调函数
function attUploadApplyLeave(json){
	alert(json);
	DWZ.ajaxDone(json);
}
function checkForm() {
	var name1 = '';
	name1 = document.getElementById("fileApp").value;
	var names;
	if (name1 != "") {
		names = name1.split(".");
		var length = names.length;
		if (names[length - 1] != "docx" && names[length - 1] != "pptx"
				&& names[length - 1] != "xlsx" && names[length - 1] != "xls"
				&& names[length - 1] != "ppt" && names[length - 1] != "doc"
				&& names[length - 1] != "txt" && names[length - 1] != "pdf"
				&& names[length - 1] != "jpg") {
			alertMsg.info("上传文件类型不符合要求！");
			return false;
		}
	} else {
		alertMsg.info("请选择文件！");
		return false;
	}
	return true;
}
function validateApplyLeaveCallback(form,callback) {
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
	<form id="PhotoForm" method="post" action="/ess/wageApplication/submitApplicationFile" enctype="multipart/form-data" class="pageForm" 
			>
		<div class="pageFormContent nowrap">
			<table>	
				<tr>			
					<td style="width: 500px;padding-top:5px;">
						<input id="fileApp" name="file" type="file" size="50"/>
						<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
					</td>
				</tr>
				<tr>	
					<td style="width: 500px;padding-top:5px;">
					<font color="red"><b>上传文件类型：.xls .xlsx .txt .doc .docx .ppt .pptx .pdf .jpg<br>上传文件不能大于10M</b></font>
					</td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onClick="submitAppFile('PhotoForm');">
								<!--提交 --><spring:message code="public.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<!--取消 --><spring:message code="public.title.cancle"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
