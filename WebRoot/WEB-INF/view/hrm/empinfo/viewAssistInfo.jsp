<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.assist.title.title" /><!--标题--></td>';
	   		htm+='<td  class="td_type"><input type="text" id="TITLE' + i + '" name="TITLE' + i + '" class="textInput"   maxlength="60" size="60" /></td>';

	   		htm+='<td  class="td_title"><spring:message code="hr.assist.title.content" /><!--内容--></td>';
	   		htm+='<td  class="td_type" colspan="5"><input type="text" id="CONTENT' + i + '" name="CONTENT' + i + '" class="textInput" maxlength="60" size="60"/></td>';
	   		
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		
	   	$("#createTable").append(htm) ;
	   	count++;  
	    $("#count").attr("value",count) ;
    }


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewAssistInfo(form, callback) {
	
		var $form = $("#viewAssistInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		var count = parseInt($("#count").val());

		for (var i = 0 ; i< count ; i++){
			var title = "#TITLE" + i;
			var content = "#CONTENT" + i;
			if($(title).val() == "" || $(content).val() == ""){
				alertMsg.info("<spring:message code='hr.alert.message.assist'/>");
				return false;
			}
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
	<form id="viewAssistInfo" method="post" action="/hrm/empinfo/addAssistInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewAssistInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title">
						<spring:message code="hr.assist.title.title" />
						<!--标题-->
					</td>
					<td class="td_type" colspan="5">
						<input type="hidden" name="PERSON_ID" value="${PERSON_ID }" />
						<input type="text" name="TITLE0" id="TITLE0" class="textInput" maxlength="60" size="60"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.assist.title.content" />
						<!--内容-->
					</td>
					<td class="td_type">
						<input type="text" name="CONTENT0" id="CONTENT0" class="textInput"  maxlength="60" size="60" />
					</td>
					
					<td><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
			</table>
			
			<div id="createTable" width="100%"></div>
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
		
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>	
</div>