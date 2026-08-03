<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function iframeCallback_photochange(form, callback){

	var filePath = document.getElementById("file1").value;
	if(document.getElementById("file1").value == ''){
		alert('<spring:message code="ar.alert.message.excelimport.pash"/>');
		return false;
	}
//  判断照片路径
    var fileType = null;
	if(filePath.length <= 4){
		alert('<spring:message code="ar.alert.message.excelimport.photo"/>');
		return false;
	}else{
		fileType = filePath.substring(filePath.length - 4, filePath.length);
		if(fileType != '.jpg' && fileType != '.JPG'){
			alert('<spring:message code="ar.alert.message.excelimport.jpg"/>');
			return false;
		}
	}
	
	var $form = $(form), $iframe = $("#callbackframe");
	if(!$form.valid()) {return false;}

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>");
		$form.appendTo($iframe);
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}
	
	//form.target = "callbackframe";
	
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone);
}
</script>

<div class="pageContent">
	
	<form id="PhotoForm" method="post" action="/hrm/empinfo/uploadPhoto?EMPID=${EMPID}&PERSON_ID=${PERSON_ID}&CPNY_ID=${CPNY_ID}" 
	enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback_photochange(this,dialogAjaxDone);" target="callbackframe">
		
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>          	
				<td class="td_title">
				<spring:message code="hr.viewWorkInfo.title.pash"/>
				<!-- 路径 -->
				</td>
				<td class="td_type">
					<input id="file1" name="file" type="file" />
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
			</tr>
			</table>
			<br/>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
				<tr>
					<td colspan="2"><font color="red"><spring:message code="hr.viewWorkInfo.title.zhu"/></font></td>
					<!--  注：所选择的图片必须为JPG格式，图片大小不要超过2M -->
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

