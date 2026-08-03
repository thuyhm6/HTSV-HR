<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
var affirmLevel=1;
function validateCallbacksy0483(form, callback) {
	
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
	<form method="post" action="/sys/hrmAffirm/saveHrmAffirmInfo" class="pageForm required-validate" onsubmit="return validateCallbacksy0483(this,navTabAjaxDone);">
	<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>		
		
	<div style="clear: both;"></div>
		<div class="panel">
			<h1>
				<spring:message code="sys.affirm.title.projectParam" />
				<!--项目参数-->
			</h1>
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="user_table">
					<tr>
						<td class="td_title">
							<spring:message code="sys.affirm.title.applyType" />
							<!--申请类型-->
						</td>
						<td class="td_type" width="15%">
							<select name="APPLY_TYPE" id="APPLY_TYPE">
								<option value="">
									--
									<spring:message code="sys.affirm.title.choose" />
									--
								</option>
								<c:forEach items="${applyList}" var="result">
									<option value="${result.CODE_NO}">
										${result.CODENAME}
									</option>
								</c:forEach>
							</select>
							<input type="hidden" name="TYPE" value="pa" />
							<input name="REFERENCN_FROM_FLAG" type="hidden"
								id="REFERENCN_FROM_FLAG" value="1">
							<input name="REFERENCN_FROM_RELATION" type="hidden"
								id="REFERENCN_FROM_RELATION" value=">=">
							<input name="REFERENCN_FROM_OFFSET" type="hidden"
								id="REFERENCN_FROM_OFFSET" value="0">
								
								
							<input name="REFERENCN_TO_OFFSET" type="hidden"
								id="REFERENCN_TO_OFFSET" value="99999">
						</td>
						
						<td class="td_title">
							人员类型
						</td>
						<td class="td_type">
							<select name="EMP_TYPE">
								<option value="Q">全部</option>
								<option value="G">管理职</option>
								<option value="O">其他</option>
							</select>
						</td>
						<td class="td_title">
							职责
						</td>
						<td class="td_type">
						    <select name="DUTY_NO">
								<option value="Q">全部</option>
								<c:forEach items="${applyDutyList}" var="item" varStatus="i">
									<option value="${item.CODE_NO }">${item.CODE_NAME }</option>
								</c:forEach>
							</select>
						</td>
						<td class="td_title">
							裁决者等级长度
						</td>
						<td class="td_type">
							<input name="AFFIRM_LEVEL" type="text"
								id="AFFIRM_LEVEL" size="5" maxlength="5" value="0">
						</td>
						<td class="td_title">
							最低审批等级
						</td>
						<td class="td_type">
							<select name="LOW_LEVEL" id="LOW_LEVEL">
								<c:forEach items="${affirmPostList}" var="result">
									<option value="${result.AFFIRM_LEVEL}">
										${result.DUTY_NAME}
									</option>
								</c:forEach>
							</select>
						</td>
						<td class="td_title">
							最高审批等级
						</td>
						<td class="td_type" colspan="4">
							<select name="HIGH_LEVEL" id="HIGH_LEVEL">
								<c:forEach items="${affirmPostList}" var="result" varStatus="i">
									<option value="${result.AFFIRM_LEVEL}" <c:if test="${fn:length(affirmPostList) eq i.count}">selected</c:if>>
										${result.DUTY_NAME}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div style="clear: both;"></div>
	</form>
</div>