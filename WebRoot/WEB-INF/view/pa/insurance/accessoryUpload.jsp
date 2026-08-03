<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function iframeCallback_photochange(form, callback){
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

<div id="aaaa">	
<form id="PhotoForm" method="post" action="/pa/insurance/upload" 
	enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback_photochange(this,dialogAjaxDone);" target="callbackframe">
		
		<div >
			<table >
			<tr>          	
				<td>
					<input id="file1" name="file" type="file" />
					
					<button type="submit">上传附件</button>
				</td>
			</tr>
			</table>
			
		</div>
		
			
		
	</form>
</div>