<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddresumeInfoCallback(form,callback) {

	<c:if test="${ resumeInfo.ACTIVITY eq 1 }">
	 	alertMsg.info("<spring:message code='hrm.empinfo.ORDER_CONFIRMED_CONNOT_UPDATE.Z' />");//发令已经确认，不能修改。
	 	return false;
	</c:if>
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function validateDeleteresumeInfoCallback(form,callback) {

	<c:if test="${ resumeInfo.ACTIVITY eq 1 }">
	 	alertMsg.info("<spring:message code='hrm.empinfo.ORDER_CONFIRMED_CONNOT_DELETE.Z' />");//发令已经确认，不能删除。
	 	return false;
	</c:if>
	var $form = $("#" + form);	
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/recruitManage/deleteResumeInfo',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
		<form id="viewAddresumeInfo" method="post" action="/hrm/recruitManage/addResumeInfo" class="pageForm required-validate" 
			onsubmit="return validateAddresumeInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title"><spring:message code="hrm.empinfo.AUTOMATIC_PROCESSINF_CODE.Z"/><!-- 自动处理代码 --></td>
									<td width="35%" class="td_type">
										<input type="text" id="REGISTER_CODE" name="REGISTER_CODE" value="${resumeInfo.REGISTER_CODE}" readonly="true" size="20"/>
										<input type="hidden" id="SEQ" name="SEQ" value="${resumeInfo.SEQ}"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.REGISTRATION_DATE"/><!-- 注册日 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="REGISTER_INFO" name="REGISTER_INFO" value="${resumeInfo.REGISTER_INFO}" readonly="true" size="20"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hr.assignment.type"/><!-- 统一发令类型 -->
									</td>
									<td width="35%" class="td_type">
										<ait:SelectSyCodeByCpnyID id="REGISTER_TYPE" name="REGISTER_TYPE" parentNo="14013956" selected="${resumeInfo.REGISTER_TYPE}" limit="all"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="display.emp.ben.effectivedate"/><!-- 生效日期 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="REGISTER_DATE" name="REGISTER_DATE" value="${resumeInfo.REGISTER_DATE}" class="Wdate required" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
											readonly="true"  />
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="ar.viewcycle.title.zhuangtai"/><!-- 状态 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
											<c:if test="${not empty resumeInfo.ACTIVITY_NAME }">
					                    	 	${resumeInfo.ACTIVITY_NAME}
											</c:if>
											<c:if test="${empty resumeInfo.ACTIVITY_NAME }">
					                    		In Progress
											</c:if>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.ORDER_OUTLINE.Z"/><!-- 发令概要 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<textarea name="REMARK"  style="width:400px;height:80px">${resumeInfo.REMARK}</textarea>
									</td>
								</tr>
							</table>
				</table>
			</div>
	  	</form>	
