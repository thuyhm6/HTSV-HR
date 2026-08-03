<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackUpdateAdditionalInfo(form, callback) {


	var $form = $("#updateAdditionalInfo");
	
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
	
	var credentialListSizenum= document.getElementById("credentialListSize").value;
	
	for (i=0;i<credentialListSizenum;i++){
		if(document.getElementById("CN"+i).checked && document.getElementById("CREDENTIAL_BEGIN_DATE_"+i) !=null && document.getElementById("CREDENTIAL_END_DATE_"+i) != null){
			
			var sd=document.getElementById("CREDENTIAL_BEGIN_DATE_"+i).value;
			var ed=document.getElementById("CREDENTIAL_END_DATE_"+i).value;
			
			var date1 = sd.replaceAll("-","");
			var date2 = ed.replaceAll("-","");
			
			if (date1 - date2 > 0) {
				//alert("取证日期不能晚于有效期");
				alertMsg.error('<spring:message code="hr.alert.message.viewCompetence.addNull"/>');
				document.getElementById("CREDENTIAL_BEGIN_DATE_"+i).focus();
				return false;
			}
		}
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
	<form id="updateAdditionalInfo" method="post" action="/hrm/empinfo/editCredentialInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateAdditionalInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="credentialListSize" name="credentialListSize" value="${fn:length(credentialList)}" />
	<table class="table" width="102%" layoutH="150">
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
			<c:forEach items="${credentialList}" var="item" varStatus="i">
			
				<tr>
					<td><input type="checkbox" id="CN${i.index}" name="CN" value="${item.CRED_NO}" /></td>
					
					<td><ait:SelectSyCodeByCpnyID name="CREDENTIAL_TYPE_${item.CRED_NO}" parentNo="4297" cnpyID="${defaultCpny}" selected="${item.CREDENTIAL_TYPE }" /></td>
					
					<td><input type="text" name="CREDENTIAL_NO_${item.CRED_NO}" value="${item.CREDENTIAL_NO}" class="textInput alphanumeric required" maxlength="50"/></td>
					
					<td><input type="text" name="CREDENTIAL_SOURCE_${item.CRED_NO}" value="${item.CREDENTIAL_SOURCE}" class="textInput" maxlength="30" /></td>
					
					<td>
						<input type="text" id="CREDENTIAL_BEGIN_DATE_${i.index}" name="CREDENTIAL_BEGIN_DATE_${item.CRED_NO}" class="date required" value="${item.CREDENTIAL_BEGIN_DATE }" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					
					<td>
						<input type="text" id="CREDENTIAL_END_DATE_${i.index}" name="CREDENTIAL_END_DATE_${item.CRED_NO}" value="${item.CREDENTIAL_END_DATE }" class="date required" value="${item.CREDENTIAL_BEGIN_DATE }" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					
					<td><input type="text" name="REMARK_${item.CRED_NO}"  class="textInput" maxlength="30" value="${item.REMARK}"/></td>	
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