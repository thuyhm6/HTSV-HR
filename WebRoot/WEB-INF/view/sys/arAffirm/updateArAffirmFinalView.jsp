<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
function validateCallbackSy0486_update(form, callback) {
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	if(document.getElementById("APPLY_TYPE")){
		var typeVar=document.getElementById("APPLY_TYPE").value;
		if(typeVar==""){
		    alertMsg.error('<spring:message code="alert.message.sys.arAffirm.pleaseChooseApplyType"/>');
			return false;
		}
	} 

	if(document.getElementById("AFFIRM_LEVEL")){
		var levelVar=document.getElementById("AFFIRM_LEVEL").value;
		if(levelVar==""){
		    alertMsg.error("裁决者等级长度不能为空");
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
</SCRIPT>
<div class="pageContent">
	<form method="post" action="/sys/arAffirm/updateArAffirmFinalInfo" class="pageForm required-validate" 
	      onsubmit="return validateCallbackSy0486_update(this,navTabAjaxDone);">
		<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
		</div>	
		
		<div style="clear:both;"></div>
		<div class="panel">
			<h1><spring:message code="sys.affirm.title.projectParam"/><!--项目参数-->	
			    <input type="hidden" name="APPLY_PARAM_NO" value="${leaveApply.APPLY_PARAM_NO}"/></h1>
				<div>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
					<tr>
					    <td class="td_title"><spring:message code="sys.affirm.title.applyType"/><!--申请类型--></td>
					    <td class="td_type" width="15%">
						     <select name="APPLY_TYPE" id="APPLY_TYPE">
							       	<option value="">--<spring:message code="sys.affirm.title.choose"/>--</option>
							         <c:forEach items="${applyList}" var="result" >
							         <option  value="${result.CODE_NO}" 
							         <c:if test="${leaveApply.APPLY_TYPE eq result.CODE_NO}">selected="selected"
							         </c:if>>${result.CODENAME}</option>
							         </c:forEach>
							 </select>
					    </td>
					    <td class="td_title"><spring:message code="sys.affirm.title.startFlag"/><!--开始标志--></td>
					    <td class="td_type" width="15%"> 
						    <select name="REFERENCN_FROM_FLAG" id="REFERENCN_FROM_FLAG">
						      <option value="1" <c:if test="${leaveApply.REFERENCN_FROM_FLAG eq '1'}">selected="selected"
						      </c:if>>
						      <spring:message code="sys.affirm.title.yes"/><!--是--></option>
						      <option value="0" <c:if test="${leaveApply.REFERENCN_FROM_FLAG eq '0'}">selected="selected"
						      </c:if>> 
						      <spring:message code="sys.affirm.title.no"/><!--否--></option>
						    </select>
					    </td>
					    <td class="td_title"><spring:message code="sys.affirm.title.startMigrationDirection"/><!--开始偏移方向--></td>
					    <td class="td_type" width="15%">      
						    <select name="REFERENCN_FROM_RELATION" id="REFERENCN_FROM_RELATION">
							      <option value=">" <c:if test="${leaveApply.REFERENCN_FROM_RELATION eq '>'}">selected="selected"</c:if>> 
							      <spring:message code="sys.affirm.title.more"/><!--大于--></option>
							      <option value="<" <c:if test="${leaveApply.REFERENCN_FROM_RELATION eq '<'}">selected="selected"</c:if>> 
							      <spring:message code="sys.affirm.title.less"/><!--小于--></option>
							      <option value="=" <c:if test="${leaveApply.REFERENCN_FROM_RELATION eq '='}">selected="selected"</c:if>> 
							      <spring:message code="sys.affirm.title.euqal"/><!--等于--></option>
								  <option value=">="  <c:if test="${leaveApply.REFERENCN_FROM_RELATION eq '>='}">selected="selected"</c:if>>
								  <spring:message code="sys.affirm.title.moreThanEqualTo"/><!--大于等于--></option>
								  <option value="<=" <c:if test="${leaveApply.REFERENCN_FROM_RELATION eq '<='}">selected="selected"</c:if>>
								  <spring:message code="sys.affirm.title.lessThanEqualTo"/><!--小于等于--></option>
						    </select>
					    </td>
					    <td class="td_title"><spring:message code="sys.affirm.title.startLength"/><!--开始长度--></td>
					    <td class="td_type">
					    	<input name="REFERENCN_FROM_OFFSET" type="text" id="REFERENCN_FROM_OFFSET" size="5" maxlength="5" value="${leaveApply.REFERENCN_FROM_OFFSET}">
					    </td>
				 	 </tr>
				 	 <tr>
				    	 <td class="td_title"><spring:message code="sys.affirm.title.ifReference"/><!--是否参考--></td>
				    	 <td class="td_type">
					    	 <select name="REFERENCN_FLAG" id="REFERENCN_FLAG">
							      <option value="1" <c:if test="${leaveApply.REFERENCN_FLAG eq '1'}">selected="selected"</c:if>>
							     <spring:message code="sys.affirm.title.yes"/><!--是--></option>
							      <option value="0" <c:if test="${leaveApply.REFERENCN_FLAG eq '0'}">selected="selected"</c:if>>
							      <spring:message code="sys.affirm.title.no"/><!--否--></option>
					  		  </select>
				  		  </td>   
					      <td class="td_title"><spring:message code="sys.affirm.title.endFlag"/><!--结束标志--></td>
					      <td class="td_type" width="15%">
						    <select name="REFERENCN_TO_FLAG" id="REFERENCN_TO_FLAG">
						      <option value="1"  <c:if test="${leaveApply.REFERENCN_TO_FLAG eq '1'}">selected="selected"</c:if>>
						      <spring:message code="sys.affirm.title.yes"/><!--是--></option>
						      <option value="0"  <c:if test="${leaveApply.REFERENCN_TO_FLAG eq '0'}">selected="selected"</c:if>>
						      <spring:message code="sys.affirm.title.no"/><!--否--></option>
						    </select>
					      </td>
				  		  <td class="td_title"><spring:message code="sys.affirm.title.endMigrationDirection"/><!--结束偏移方向--></td>
						   <td class="td_type" width="15%">      
							    <select name="REFERENCN_TO_RELATION" id="REFERENCN_TO_RELATION">
								      <option value=">" <c:if test="${leaveApply.REFERENCN_TO_RELATION eq '>'}">selected="selected"</c:if>> 
								      <spring:message code="sys.affirm.title.more"/><!--大于--></option>
								      <option value="<" <c:if test="${leaveApply.REFERENCN_TO_RELATION eq '<'}">selected="selected"</c:if>>
								      <spring:message code="sys.affirm.title.less"/><!--小于--></option>
								      <option value="=" <c:if test="${leaveApply.REFERENCN_TO_RELATION eq '='}">selected="selected"</c:if>> 
								      <spring:message code="sys.affirm.title.euqal"/><!--等于--></option>
									  <option value=">=" <c:if test="${leaveApply.REFERENCN_TO_RELATION eq '>='}">selected="selected"</c:if>>
									  <spring:message code="sys.affirm.title.moreThanEqualTo"/><!--大于等于--></option>
									  <option value="<="  <c:if test="${leaveApply.REFERENCN_TO_RELATION eq '<='}">selected="selected"</c:if>>
									  <spring:message code="sys.affirm.title.lessThanEqualTo"/><!--小于等于--></option>
							      </select>
						    </td>
						    <td class="td_title"><spring:message code="sys.affirm.title.endLength"/><!--结束长度--></td>
						    <td class="td_type"><input name="REFERENCN_TO_OFFSET" type="text" id="REFERENCN_TO_OFFSET" size="5" maxlength="5" value="${leaveApply.REFERENCN_TO_OFFSET}"></td>
				  	 </tr>
				  	 <tr>
						<td class="td_title">
							裁决者等级长度
						</td>
						<td class="td_type">
							<input name="AFFIRM_LEVEL" type="text" id="AFFIRM_LEVEL" size="5" maxlength="5" value="${leaveApply.AFFIRM_LEVEL}">
						</td>
					</tr>
				 </table>
				</div>
		</div>
		<div style="clear:both;"></div>		
	</form>	
</div>
