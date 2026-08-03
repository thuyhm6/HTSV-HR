<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function navTabSearchPaBankInfo(form,callback) {	
	var $form = $("#updateInsuranceObjectView");
	if (!$form.valid()) {
		return false;
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
	return false ;
}
//获取父页上的工资月选项
$(document).ready(function(){
		$("#insurancePaYear").val($("#seach_insYear").val());
		$("#insurancePaMonth").val($("#seach_insMonth").val());
	});
</script>
<div class="pageContent">
	<form id="updateInsuranceObjectView" method="post" action="/pa/insurance/updateInsuranceObjectInfo" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return navTabSearchPaBankInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="insurancePageNum" value="${pageNum }"/>
			<input type="hidden" name="insurancePaYear" id="insurancePaYear"/>
			<input type="hidden" name="insurancePaMonth" id="insurancePaMonth"/>
			<p>
				<label><spring:message code="pa.insurance.title.salaryMonth"/><!--工资月--></label>
				<label>${insuranceObject.PA_MONTH }</label>
			</p>
			<p>
				<label><spring:message code="pa.salary.title.salaryProvideDate"/>：<!-- 工资发放日 --></label>
				<label>${insuranceObject.GIVE_DATE }</label>
				<input type="hidden" name="INSURANCE_GIVE_DATE" value="${insuranceObject.GIVE_DATE }"/>
			</p>
			<p>
				<label><spring:message code="public.title.empId"/><!--工号-->:</label>
				<label>${insuranceObject.EMPID }</label>
				<input type="hidden" name="PERSON_ID" value="${insuranceObject.PERSON_ID }" />
			</p>
			<p>
				<label><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--姓名--></label>
				<label>${insuranceObject.CHINESE_NAME }</label>
			</p>
			<p>
				<label><spring:message code="public.title.deptName"/><!--部门-->:</label>
				<label>${insuranceObject.DEPTNAME }</label>
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.postGrade"/><!--职级-->:</label>
				<label>${insuranceObject.POST_GRADE_NAME }</label>
			</p>
			<p>
				<label><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->:</label>
				<label>${insuranceObject.EMP_STATUS }</label>
			</p>
			<p>
				<lable>&nbsp;&nbsp;<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 -->:</lable>
				<label>${insuranceObject.IN_THE_DIFFERENCE }</label>
			</p>
			<p>
				<label><spring:message code="pa.wagebase.title.caculateFlag1"/><!--计算标识-->:</label>
				<select name="CALC_FLAG" id="CALC_FLAG" style="width:70px; position: static; visibility: inherit;">
					<option value="Y" <c:if test="${insuranceObject.CALC_FLAG eq 'Y' }" >selected</c:if>>Y</option>
					<option value="N" <c:if test="${insuranceObject.CALC_FLAG eq 'N' }" >selected</c:if>>N</option>
				</select>			
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				     <spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>