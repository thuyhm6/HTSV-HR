<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackUpdateBadArchivesInfo(form, callback) {
	var $form = $("#updateBadArchivesInfo");
	 
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
	var badArchivesInfoListnum= document.getElementById("badArchivesInfoListSize").value;
	
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
  var tld="BAID"+index
    document.getElementById(tld).checked=true;
}
</script>
<div class="pageContent">
<form id="updateBadArchivesInfo" method="post"
	action="/hrm/empinfo/editBadArchivesInfo"
	class="pageForm required-validate"
	onsubmit="return validateCallbackUpdateBadArchivesInfo(this, dialogAjaxDone);">
<div class="panelBar">
<ul class="toolBar">
	<li id="addLi"><span>&nbsp;</span></li>
</ul>
</div>

<input type="hidden" id="badArchivesInfoListSize"
	name="badArchivesInfoListSize" value="${fn:length(badArchivesList)}" />
<table class="table" width="103%" layoutH="150">
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
		<c:forEach items="${badArchivesList}" var="item" varStatus="i">
			<tr target="PERSON_ID" rel="${item.ID}&PERSON_ID=${item.PERSON_ID }">
				<td><input type="checkbox" id="BAID${i.index}" name="BAID"
					value="${item.ID}" /></td>
				<td><input type="text" id="HAPPEN_DATE_${i.index}"
					name="HAPPEN_DATE_${item.ID}" value="${item.HAPPEN_DATE}"
					class="date required" readonly="true" format="yyyy-MM-dd"
					yearstart="-50" yearend="5" onClick="setdate(this);" /></td>
				<td><select name="ARCHIVES_TYPE_${item.ID}"
					id="ARCHIVES_TYPE_${i.index}"
					onclick="setCheckboxChecked(${i.index})">
					<option value=""><spring:message
						code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /> <!-- 请选择 --></option>
					<c:forEach items="${codeList}" var="recsource">
						<option value="${recsource.CODE_NO}"
							<c:if test="${recsource.CODE_NO==item.ARCHIVES_TYPE_CODE}">selected</c:if>>
						${recsource.CODENAME}</option>
					</c:forEach>
				</select></td>
				<td><input type="text" id="DETAIL_DESCRIPT_${i.index}"
					name="DETAIL_DESCRIPT_${item.ID}" value="${item.DETAIL_DESCRIPT}"
					class="textInput" maxlength="60" /></td>
				<!--<td><input type="text" id="FILE_URL_${i.index}"
					name="FILE_URL_${item.ID}" value="${item.FILE_URL}"
					class="textInput" maxlength="60" />
				</td>
				--><td>${item.LOCAL_NAME}</td>
				<td><input type="text" id="REMARK_${i.index}"
					name="REMARK_${item.ID}" value="${item.REMARK}" class="textInput"
					maxlength="60" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="formBar">
<ul>
	<li>
	<div class="buttonActive">
		<div class="buttonContent">
			<button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button>
		</div>
	</div>
	</li>
	<li>
	<div class="button">
		<div class="buttonContent">
			<button type="button" class="close"><spring:message code="public.title.cancle" /><!-- 取消 --></button>
		</div>
	</div>
	</li>
</ul>
</div>
</form>
</div>