<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteFileInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("FEN");
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
	<form method="post" action="/hrm/empinfo/deleteFileInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteFileInfo(this, dialogAjaxDone);">
		
		
			<table class="table" width="101.7%" layoutH="60">
					<thead>
						<tr>
							<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
							<th width="100">
								<spring:message code="hr.viewContract.title.FILE_NO"/>
								<!--档案号-->
							</th>
							<th width="100">
								<spring:message code="hr.viewContract.title.FILE_TYPE_NAME"/>
								<!--档案类型-->
							</th>
							<th width="100">
								<spring:message code="hr.viewContract.title.FILE_RELATION_NAME"/>
								<!--档案关系-->
							</th>
							<th width="100">
								<spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME"/>
								<!--档案转入-->
							</th>
							<th width="100">
								<spring:message code="hr.viewContract.title.FILE_DATE"/>
								<!--转入日期-->
							</th>
							<th width="100">
								<spring:message code="hr.viewContract.title.FILE_CONTENT"/>
								<!--档案内容-->
							</th>
							<th width="100">
								<spring:message code="hr.viewContract.title.FILE_AREA_NAME"/>
								<!--存档归属地-->
							</th>
							<th width="100">
								<spring:message code="hr.viewContract.title.COST_END_DATE"/>
								<!--存档费截至日-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${fileList}" var="item" >
						
							<tr>
								<td>
									<input type="checkbox" id="FEN" name="FEN" value="${item.FILE_EMP_NO}" />
								</td>
								<td>${item.FILE_NO}</td>
								<td>${item.FILE_TYPE_NAME}</td>
								<td>${item.FILE_RELATION_NAME}</td>
								<td>${item.FILE_INTO_YN_NAME}</td>
								<td>${item.FILE_DATE}</td>
								<td>${item.FILE_CONTENT}</td>
								<td>${item.FILE_AREA_NAME}</td>
								<td>${item.COST_END_DATE}</td>
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