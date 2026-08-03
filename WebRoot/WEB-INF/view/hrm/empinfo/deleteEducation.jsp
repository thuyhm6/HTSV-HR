<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>





<script type="text/javascript">
<!--
function validateCallback(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("DNO");
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
	<form method="post" action="/hrm/empinfo/deleteEducationInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		
			<table class="table" width="102.6%" layoutH="60">
					<thead>
						<tr>
							<th width="10"><input name="PERSON_ID" type="hidden" value="${PERSON_ID }"></th>
							<th width="50"  >
								<spring:message
									code="hr.viewPersonalInfo.title.INSTITUTION_NAME" />
								<!--学校名-->
							</th>
							<th width="50">
								<spring:message
									code="liang.hr.viewPersonalInfo.title.SUBJECT_CLASSIFY" />
								<!--专业分类-->
							</th>
							<th width="50">
								<spring:message
									code="hr.viewPersonalInfo.title.SUBJECTNAME" />
								<!--专业-->
							</th>
							<th width="50">
								<spring:message
									code="liang.hr.viewPersonalInfo.title.SUBJECT_CLASSIFY_TWO" />
								<!--第二专业分类-->
							</th>
							
							<th width="50">
								<spring:message
									code="hr.viewPersonalInfo.title.SUBJECT_SECOND_NAME" />
								<!--第二专业-->
							</th>
							<th width="50">
								<spring:message
									code="liang.hr.viewPersonalInfo.title.START_DATE" />
								<!--入学年月-->
							</th>
							<th width="50">
								<spring:message code="liang.hr.viewPersonalInfo.title.END_DATE" />
								<!--毕业年月-->
							</th>
							<th width="50">
								<spring:message
									code="hr.viewPersonalInfo.title.DEGREE_NAME" />
								<!--学历-->
							</th>
							
							<th width="50">
								<spring:message
									code="hr.viewPersonalInfo.title.SCHOOL_ADDRESS" />
								<!--所在地-->
							</th>
							<th width="50">
								<spring:message
									code="liang.hr.viewPersonalInfo.title.FINAL_DEGREE_WHETHER" />
								<!--最终学历与否-->
							</th>
							<th width="50">
								<spring:message
									code="liang.hr.viewPersonalInfo.title.REMARKS" />
								<!--备注-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${educationList}" var="item" >
						
							<tr target="sid" rel="${item.EDUC_NO}">
								<td>
									<c:if test="${item.FINAL_DEGREE_WHETHER ne 'Y'}"><input type="checkbox" id="DNO" name="DNO" value="${item.EDUC_NO}" /></c:if>
								</td>
								<td>${item.INSTITUTION_NAME}</td>
								<td>${item.SUBJECT_CLASSIFY_NAME}</td>
								<td>${item.SUBJECT_NAME}</td>
								<td>${item.CLASSIFY_TWO_NAME}</td>
								<td>${item.SUBJECT_SECOND_NAME}</td>
								<td>${item.START_DATE}</td>
								<td>${item.END_DATE}</td>
								<td>${item.DEGREE_NAME}</td>
								<td>${item.SITE_PROVINCE_NAME}<c:if test="${item.SITE_PROVINCE_NAME ne item.SITE_CITY_NAME}" >${item.SITE_CITY_NAME}</c:if></td>
								<td>${item.FINAL_DEGREE_WHETHER}</td>
								<td>${item.REMARKS}</td>
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