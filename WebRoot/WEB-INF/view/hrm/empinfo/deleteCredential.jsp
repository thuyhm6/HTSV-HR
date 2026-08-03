<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteCredentialInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("CN");
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
	<form method="post" action="/hrm/empinfo/deleteCredentialInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteCredentialInfo(this, dialogAjaxDone);">
		
		
			<table class="table" width="102.5%" layoutH="60">
					<thead>
						<tr>
							<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
							<th width="80">
								<spring:message code="hr.viewCredential.title.CREDENTIAL_TYPE_NAME"/>
								<!--证照类型-->
							</th>
							<th width="80">
								<spring:message code="hr.viewCredential.title.CREDENTIAL_NO"/>
								<!--证照号码-->
							</th>
							<th width="80">
								<spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE"/>
								<!--签发地-->
							</th>
							<th width="80">
								<spring:message code="hr.viewCompetence.title.DATE_OBTAINED"/>
								<!--取证日期-->
							</th>
							<th width="80">
								<spring:message code="hr.viewCredential.title.CREDENTIAL_END_DATE"/>
								<!--到期日-->
							</th>
							<th width="80">
								<spring:message code="hr.viewPromote.title.REMARK"/>
								<!--备注-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${credentialList}" var="item" >
						
							<tr>
								<td>
									<input type="checkbox" id="CN" name="CN" value="${item.CRED_NO}" />
								</td>
								<td>${item.CREDENTIAL_TYPE_NAME}</td>
								<td>${item.CREDENTIAL_NO}</td>
								<td>${item.CREDENTIAL_SOURCE}</td>
								<td>${item.CREDENTIAL_BEGIN_DATE}</td>
								<td>${item.CREDENTIAL_END_DATE}</td>
								<td>${item.REMARK}</td>
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