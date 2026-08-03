<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackUpdateCompetenceInfo(form, callback) {


	var $form = $("#updateCompetenceInfo");
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids1= document.getElementsByName("LNO");
	
	for(var i=0;i<ids1.length;i++){
		if(ids1[i].checked){
			checked=true;
		}
	}
	
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	
	var qualificationListSizenum= document.getElementById("languageLevelList").value;
	
	
	
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
  //var ckElems = document.getElementsByName(elemName);
  	var tld="LNO"+index
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}
	//根据考试名称的不同 显示不同的等级
function getExamNameU(id,sourceId){
	
	
	id=id.replace("EXAM_NAME_CODE_","");
	
	if(sourceId == null || sourceId.length == 0){
		return ;
	}
	var sel = $("#LANGUAGE_LEVEL_CODE_"+id);
	
	sel.empty();
	
	$.ajax({
		 cache: false,
		 type: 'post',
		 url: "/hrm/transferOrder/getRecSourceDetailByRecSource?",
		 data: 'PARENT_CODE_NO=' + sourceId,
		 dataType:"json",
		 success: function(data) {
			$.each(data, function(key,value){
					if($(data).size() > 0){
						
							sel.append('<option value='+value+'>'+key+'</option>'); 
						
					}
			});
		 }
	});
}
//-->
</script>


<div class="pageContent">
	<form id="updateCompetenceInfo" method="post" action="/hrm/empinfo/editLanguageInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateCompetenceInfo(this, dialogAjaxDone);">

	
	<input type="hidden" id="languageLevelList" name="languageLevelList" value="${fn:length(languageLevelList)}" />
	<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
	
	
	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<table class="table" width="101.7%" layoutH="150">
		<thead>
			<tr>
				 <th width="10"></th>
				 <th width="100">
				 	<spring:message code="hr.viewLanguage.KAOSHIDATE"/>
				 	<!--考试日期-->
				 </th>
				 <%--
				 <th width="100">
				 	<spring:message code="hr.viewCompetence.title.LANGUAGE_TYPE_NAME"/>
				 	<!--语言类型-->
				 </th>
				 --%><th width="100">
				 	<spring:message code="hr.viewCompetence.title.EXAM_NAME"/>
				 	<!--考试名-->
				 </th>
				 <%--<th width="100">
				 	<spring:message code="hr.viewCompetence.title.QUALIFICATION_NAME"/>
				 	<!--证书名称-->
				 </th>
				 --%><th width="100">
				 	<spring:message code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME"/>
				 	<!--等级-->
				 </th>
				 <th width="100">
				 	<spring:message code="hr.viewCompetence.title.MARK"/>
				 	<!--分数-->
				 </th>
				 <th width="100">
				 	<spring:message code="hr.viewPersonalInfo.title.jintiebiaozhun"/>
				 	<!--津贴-->
				 </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${languageLevelList}" var="item" varStatus="i">
				<tr>
					
					<td><input type="checkbox" id="LNO${i.count}" name="LNO" value="${item.LANGUAGE_NO}" /></td>
						<td>
						<input onclick="setCheckboxChecked(${i.count})" type="text"  name="KAOSHIDATE_${item.LANGUAGE_NO}"  value="${fn:substring(item.KAOSHIDATE,0, 10)}" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>	
					</td>
					<td>
					<select name="EXAM_NAME_CODE_${item.LANGUAGE_NO}" id="EXAM_NAME_CODE_${item.LANGUAGE_NO}" onChange="setCheckboxChecked(${i.count});">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /><!-- 请选择 --></option>
							<c:forEach items="${examNameCodeList}" var="itemM" >
	   						<option value="${itemM.CODE_NO}" <c:if test="${itemM.CODE_NO==item.EXAM_NAME_CODE}">selected</c:if>>${itemM.CODENAME}</option>
							</c:forEach>
					</select>
					<td>
					<select name="LANGUAGE_LEVEL_CODE_${item.LANGUAGE_NO}" id="LANGUAGE_LEVEL_CODE_${item.LANGUAGE_NO}" onChange="setCheckboxChecked(${i.count});">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /><!-- 请选择 --></option>
							<c:forEach items="${languageLevelCodeList}" var="itemM" >
	   						<option value="${itemM.CODE_NO}" <c:if test="${itemM.CODE_NO==item.LANGUAGE_LEVEL_CODE}">selected</c:if>>${itemM.CODENAME}</option>
							</c:forEach>
					</select>
					</td>
					<td><input onclick="setCheckboxChecked(${i.count})" name="MARK_${item.LANGUAGE_NO}" type="text" maxlength="3" size="15"	value="${item.MARK}" /></td>
					<td><input onclick="setCheckboxChecked(${i.count})" name="ALLWANCE_${item.LANGUAGE_NO}" type="text" maxlength="5" size="15"	value="${item.ALLWANCE}" /></td>
					
				
					
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	
	
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