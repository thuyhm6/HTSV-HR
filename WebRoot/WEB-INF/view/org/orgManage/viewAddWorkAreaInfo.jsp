<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddWorkAreaInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
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

function validateDeleteWorkAreaInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm('<spring:message code="button.delete.sure" />',
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/org/orgManage/deleteWorkAreaInfo',
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
		<form id="viewAddWorkAreaInfo" method="post" action="/org/orgManage/addWorkAreaInfo" class="pageForm required-validate" 
			onsubmit="return validateAddWorkAreaInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title"><spring:message code="org.title.WorkAreaInfo_CODE_NO" /><!-- 工作区域代码 --></td>
									<td width="35%" class="td_type">
									 <input type="text" id="CODE_NO" name="CODE_NO" value="${WorkAreaInfo.CODE_NO}" class="required"
											<c:if test="${not empty WorkAreaInfo.CODE_NO }">
												 readonly="true"
											</c:if>  
											size="35"/>
										<input type="hidden" id="SEQ" name="SEQ" value="${WorkAreaInfo.SEQ}"/> 
								<%-- 		<ait:SelectSyCodeByCpnyID name="CODE_NO" parentNo="14015541" selected="${WorkAreaInfo.CODE_NO}"/>  --%>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.WorkAreaInfo_CODE_NAME" /><!-- 工作区域名 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_NAME" name="CODE_NAME" value="${WorkAreaInfo.CODE_NAME}" class="required"
											<c:if test="${not empty WorkAreaInfo.CODE_NAME }">
												readonly="true"
											</c:if> 
											size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.WorkAreaInfo_REMARK" /><!-- 工作区域说明 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<input type="text" id="REMARK" name="REMARK" value="${WorkAreaInfo.REMARK}" size="112"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.START_DATE" /><!-- 生成日期 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="START_DATE" name="START_DATE" class="required" readonly="true" 
											<c:if test="${not empty WorkAreaInfo.START_DATE }">
					                    	value="${WorkAreaInfo.START_DATE}"
											</c:if>
											<c:if test="${empty WorkAreaInfo.START_DATE }">
					                    	value="${START_DATE}"
											</c:if> size="35"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.status" /><!-- 状态 -->
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="ACTIVITY" selected="${WorkAreaInfo.ACTIVITY}" parentNo="14013911" cnpyID="${LoginUser.cpnyId}"/>
									</td> 
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.END_DATE" /><!-- 废弃日期 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="END_DATE" name="END_DATE" class="date required" format="yyyy.MM.dd" 
					                    	readonly="true" 
					                    	
											<c:if test="${not empty WorkAreaInfo.END_DATE }">
					                    	value="${WorkAreaInfo.END_DATE}"
											</c:if>
											<c:if test="${empty WorkAreaInfo.END_DATE }">
					                    	value="${END_DATE}"
											</c:if> size="35"/>
					                    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
									</td>
									<td width="15%" class="td_title">
									</td>
									<td width="35%" class="td_type">
									</td>
								</tr>
							</table>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.COUNTRY" /><!-- 国家 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
									   	<ait:SelectSyCodeByCpnyID name="COUNTRY" selected="${WorkAreaInfo.COUNTRY}" parentNo="870" cnpyID="${LoginUser.cpnyId}"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.ADDRESS" /><!-- 地址 -->
									</td>
									<td width="85%" class="td_type" colspan="3">
										<input type="text" id="ADDRESS" name="ADDRESS" value="${WorkAreaInfo.ADDRESS}" size="112"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.UPDATED_IP" /><!-- 变更者 -->
									</td>
									<td width="35%" class="td_type">
										[${WorkAreaInfo.EMPID}]${WorkAreaInfo.LOCAL_NAME} ${WorkAreaInfo.UPDATED_IP}
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 -->
									</td>
									<td width="35%" class="td_type">
									   	${WorkAreaInfo.UPDATE_DATE}
									</td> 
								</tr>
							</table>	
				</table>
			</div>
	  	</form>	
