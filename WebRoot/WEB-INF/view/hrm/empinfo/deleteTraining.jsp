<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteTrainingInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("TN");
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
	<form method="post" action="/hrm/empinfo/deleteTrainingInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteTrainingInfo(this, dialogAjaxDone);">
		
		
			<table class="table" width="103%" layoutH="60">
					<thead>
						<tr>
							<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
							<th width="80">
								<spring:message code="liang.hr.viewTraining.title.COURSE_NAME"/>
								<!--课程名-->
							</th>
							<th width="80">
								<spring:message code="liang.hr.viewTraining.title.SELECT_MUST"/>
								<!--选择/必选  -->
							</th>
							<th width="80">
								<spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/>
								<!--培训区分  -->
							</th>
							<th width="80">
								<spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/>
								<!--开始日期-->
							</th>
							<th width="80">
								<spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/>
								<!--结束日期-->
							</th>
							<th width="80">
								<spring:message code="liang.hr.viewTraining.title.INSTITUTION_NAME"/>
								<!--培训机关-->
							</th>
							<th width="80">
								<spring:message code="liang.hr.viewTraining.title.TRAINING_METHOD" />
								<!--培训方法-->
							</th>
							<th width="80">
								<spring:message code="liang.hr.viewTraining.title.TRAINING_TIME" />
								<!--培训时间-->
							</th>
							<td width="80">
								<spring:message code="liang.hr.viewTraining.title.TRAINING_RESULT" />
								<!--培训结果-->
							</td>
							<th width="80">
								<spring:message code="liang.hr.viewTraining.title.REMARKS" />
								<!--备注-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${trainingInfoList}" var="item" >
						
							<tr>
								<td>
									<input type="checkbox" id="TN" name="TN" value="${item.TRAIN_NO}" />
								</td>
								<td>${item.COURSE_NAME}</td>
								<td>${item.MUST_NAME}</td>
								<td>${item.TRAINING_DIFFERENTIATE_NAME}</td>
								<td>${item.START_DATE}</td>
								<td>${item.END_DATE}</td>
								<td>${item.INSTITUTION_NAME}</td>
								<td>${item.TRAINING_METHOD_NAME}</td>
								<td>${item.TRAINING_TIME}</td>
								<td>${item.TRAINING_RESULT}</td>
								<td>${item.REMARKS }</td>
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