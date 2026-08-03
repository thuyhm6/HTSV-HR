<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackUpdateAssistInfo(form, callback) {


	var $form = $("#updateAssistInfo");
	 
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("ASSIST_NO");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var title = "#TITLE_" + i;
			var content = "#CONTENT_" + i;
			if($(title).val() == "" || $(content).val() == ""){
				alertMsg.info("<spring:message code='hr.alert.message.assist'/>");
				return false;
			}
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	

	
	var AssistInfoListnum= document.getElementById("AssistInfoListSize").value;
	
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
function setCheckboxChecked(index){
  var tld="ASSIST_NO"+index
    document.getElementById(tld).checked=true;
}
//-->
</script>


<div class="pageContent">
<form id="updateAssistInfo" method="post"
	action="/hrm/empinfo/editAssistInfo"
	class="pageForm required-validate"
	onsubmit="return validateCallbackUpdateAssistInfo(this, dialogAjaxDone);">

<div class="panelBar">
<ul class="toolBar">
	<li id="addLi"><span>&nbsp;</span></li>
</ul>
</div>

<input type="hidden" id="AssistInfoListSize"
	name="AssistInfoListSize" value="${fn:length(AssistList)}" />
<table class="table" width="103%" layoutH="150">
	<thead>
		<tr>
			<th width="10"><input type="hidden" name="PERSON_ID"
				class="textInput" value="${PERSON_ID }" /></th>
			<th width="80"><spring:message
				code="hr.assist.title.title" /> <!--标题--></th>
			<th width="80"><spring:message
				code="hr.assist.title.content" /> <!--内容--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${AssistList}" var="item" varStatus="i">
			<tr target="PERSON_ID" rel="${item.ASSIST_NO}&PERSON_ID=${item.PERSON_ID }">
				<td><input type="checkbox" id="ASSIST_NO${i.index}" name="ASSIST_NO"
					value="${item.ASSIST_NO}" /></td>
				<td><input type="text" id="TITLE_${i.index}"
					name="TITLE_${item.ASSIST_NO}" value="${item.TITLE}"
					class="textInput" maxlength="60" onclick="setCheckboxChecked(${i.index})"/></td>
				<td><input type="text" id="CONTENT_${i.index}"
					name="CONTENT_${item.ASSIST_NO}" value="${item.CONTENT}" class="textInput"
					maxlength="60"  onclick="setCheckboxChecked(${i.index})"/></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div class="formBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.submit" /><!-- 保存 --></button>
	</div>
	</div>
	</li>
	<li>
	<div class="button">
	<div class="buttonContent">
	<button type="button" class="close"><spring:message
		code="public.title.cancle" /><!-- 取消 --></button>
	</div>
	</div>
	</li>
</ul>
</div>
</form>
</div>