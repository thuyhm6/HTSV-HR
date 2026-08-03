<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function navTabSearchPaBankInfo(form,callback) {	
	var $form = $("#updatePaObjectCtrollerView");
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
		$("#paPaYear").val($("#seach_paYear").val());
		$("#paPaMonth").val($("#seach_paMonth").val());
	});
</script>
<div class="pageContent">
	<form id="updatePaObjectCtrollerView" method="post" action="/pa/wagebase/updatePaObjectInfo" class="pageForm required-validate" enctype="multipart/form-data" onsubmit="return navTabSearchPaBankInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="paPageNum" value="${pageNum }"/>
			<input type="hidden" name="paPaYear" id="paPaYear" value=""/>
			<input type="hidden" name="paPaMonth" id="paPaMonth" value=""/>
			<p>
				<label><spring:message code="pa.insurance.title.salaryMonth"/><!--工资月--></label>
				<label>${paObjectCtroller.PA_MONTH }</label>
			</p>
			<p>
				<label><spring:message code="pa.salary.title.salaryProvideDate"/>：<!-- 工资发放日 --></label>
				<label>${paObjectCtroller.GIVE_DATE }</label>
				<input type="hidden" name="GIVE_DATE" value="${paObjectCtroller.GIVE_DATE }"/>
			</p>
			<p>
				<label><spring:message code="public.title.empId"/><!--工号-->:</label>
				<label>${paObjectCtroller.EMPID }</label>
				<input type="hidden" name="PERSON_ID" value="${paObjectCtroller.PERSON_ID }" />
			</p>
			<p>
				<label><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--姓名--></label>
				<label>${paObjectCtroller.CHINESE_NAME }</label>
			</p>
			<p>
				<label><spring:message code="public.title.deptName"/><!--部门-->:</label>
				<label>${paObjectCtroller.DEPTNAME }</label>
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.postGrade"/><!--职级-->:</label>
				<label>${paObjectCtroller.POST_GRADE_NAME }</label>
			</p>
			<p>
				<label><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->:</label>
				<label>${paObjectCtroller.EMP_STATUS }</label>
			</p>
			<p>
				<label><spring:message code="pa.wagebase.title.caculateFlag1"/><!--计算标识-->:</label>
				<select name="CALC_FLAG" id="CALC_FLAG" style="width:70px; position: static; visibility: inherit;">
					<option value="Y" <c:if test="${paObjectCtroller.CALC_FLAG eq 'Y' }" >selected</c:if>>Y</option>
					<option value="N" <c:if test="${paObjectCtroller.CALC_FLAG eq 'N' }" >selected</c:if>>N</option>
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