<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteAdditionalInfo(form, callback) {


	var $form = $("#deleteAdditionalInfo");
	
	if (!$form.valid()) {
		return false;
	}
   var checked=false;
	var ids= document.getElementsByName("QN");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	var ids1= document.getElementsByName("LN");
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
	<form id="deleteAdditionalInfo" method="post" action="/hrm/empinfo/deleteLanguageInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteAdditionalInfo(this, dialogAjaxDone);">
		<input type="hidden" name="PERSON_ID" id="PERSON_ID" value="${PERSON_ID}"/>
		<div class="panelBar">
			<ul class="toolBar">
				<li id="addLi">
					<span>&nbsp;</span>
				</li>
			</ul>
		</div>
	
	<table class="table" width="101.7%"  layoutH="150" >
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
				<th width="100">
				 	<spring:message code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME"/>
				 	<!--等级-->
				 </th>
				 <th width="100">
				 	<spring:message code="hr.viewCompetence.title.MARK"/>
				 	<!--分数-->
				 </th>
				 <th width="100">
				 	<spring:message code="hr.viewPersonalInfo.title.jintiebiaozhun"/>
				 	<!--津贴标准(金额)-->
				 </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${languageLevelList}" var="item" varStatus="i">
				<tr>
					<td><input type="checkbox" id="LN" name="LN" value="${item.LANGUAGE_NO}" /></td>
					<td>
														
						${fn:substring(item.KAOSHIDATE,0, 10)}
													</td>
					<%--<td>
														${item.LANGUAGE_TYPE_NAME}
													</td>
													--%><td>
														${item.EXAM_NAME}
													</td>
													<%--<td>
														${item.QUALIFICATION_NAME}
													</td>
													--%><td>
														${item.LANGUAGE_LEVEL_NAME}
													</td>
													<td>
														${item.MARK}
													</td>
													<td>
														${item.ALLWANCE}
													</td>
													
					
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