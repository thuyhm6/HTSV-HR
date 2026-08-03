<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>





<script type="text/javascript">
<!--
function validateCallbackDeleteHealthInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("HN");
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
	<form method="post" action="/hrm/empinfo/deleteHealthInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteHealthInfo(this, dialogAjaxDone);">
		
		
			<table class="table" width="102.6%" layoutH="60">
					<thead>
						<tr>
							<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.PHYSICAL_DATE"/>
								<!--检查日期-->
							</th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.PHYSICAL_TYPE_NAME"/>
								<!--检查类型-->
							</th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
								<!--区分-->
							</th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.EFFECTIVE_DATE"/>
								<!--有效期-->
							</th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.CHECK_YN_NAME"/>
								<!--检查与否-->
							</th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.GENRAL_HEALTH_NAME"/>
								<!--健康情况-->
							</th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.BLOOD_TYPE_NAME"/>
								<!--血型-->
							</th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.HEALTH_CERTIFICATE_YN_NAME"/>
								<!--是否提交健康证-->
							</th>
							<th width="80">
								<spring:message code="hr.viewHealth.title.SPECIAL_MATTERS"/>
								<!--特殊事项-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${healthList}" var="item" >
						
							<tr>
								<td>
									<input type="checkbox" id="HN" name="HN" value="${item.HEALTH_NO}" />
								</td>
								<td>${item.PHYSICAL_DATE}</td>
								<td>${item.PHYSICAL_TYPE_NAME}</td>
								<td>${item.INDUSTRY_DISTINGUISH_NAME}</td>
								<td>${item.EFFECTIVE_DATE}</td>
								<td>${item.CHECK_YN}</td>
								<td>${item.GENRAL_HEALTH_NAME}</td>
								<td>${item.BLOOD_TYPE_NAME}</td>
								<td>${item.HEALTH_CERTIFICATE_YN}</td>
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