<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteTrainingInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("BAID");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
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
//-->
</script>


<div class="pageContent">
<form method="post" action="/hrm/empinfo/deleteBadArchivesInfo"
	class="pageForm required-validate"
	onsubmit="return validateCallbackDeleteTrainingInfo(this, dialogAjaxDone);">


<table class="table" width="103%" layoutH="60">
	<thead>
		<tr>
			<th width="10"><input type="hidden" name="PERSON_ID"
				class="textInput" value="${PERSON_ID }" /></th>
			<th width="80"><spring:message
				code="hr.viewBadArchives.title.HAPPEN_DATE" /> <!--发生日期--></th>
			<th width="80"><spring:message
				code="hr.viewBadArchives.title.ARCHIVES_TYPE" /> <!--类型--></th>
			<th width="80"><spring:message
				code="hr.viewBadArchives.title.DETAIL_DESCRIPT" /> <!--详细描述--></th>
			<!--<th width="80"><spring:message
				code="hr.viewBadArchives.title.FILE" /> 附件</th>
			--><th width="80"><spring:message
				code="hr.viewBadArchives.title.OPTER" /> <!--操作者--></th>
			<th width="80"><spring:message
				code="hr.viewBadArchives.title.REMARK" /> <!--备注--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${badArchivesList}" var="item">

			<tr>
				<td><input type="checkbox" id="BAID" name="BAID"
					value="${item.ID}" /></td>
				<td class='td_center' width="80">${item.HAPPEN_DATE}</td>
				<td class='td_center' width="80">${item.ARCHIVES_TYPE}</td>
				<td class='td_center' width="80">${item.DETAIL_DESCRIPT}</td>
				<!--<td class='td_center' width="80">${item.FILE_URL}</td>
				--><td class='td_center' width="80">${item.LOCAL_NAME}</td>
				<td class='td_left'>${item.REMARK}</td>
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