<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
 

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewFamilyInfo(form, callback) {
	
		var $form = $("#addEmergencyAddressInfo");
		
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
		
		return false;
	}
 
	
    
</script>
<div style="background-color: #fff;">
<!-- <div class="pageContent"> -->
	<!-- 紧急联系地址   hr_emergency_address  没有更换名字 -->
	<form id="addEmergencyAddressInfo" method="post"
		action="/ess/empinfo/addEmergencyAddressInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">
	
		<div class="pageFormContent" layoutH="56">
					<input type="hidden" name="APPLY_TYPE" value="${1}"/>
		
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table margin_b">

				<tr>
					<td class="td_title" width="15%">
						<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
						<!--姓名-->
					</td>
					<td class="td_type" width="35%">
						<input type="text" name="EMER_NAME" />
					</td>
					<td class="td_title" width="15%">
						<spring:message code="hrm.empinfo.MAIN_CONTACT_AREA" /><!--主要联络处与否-->
					</td>
					<td class="td_type" width='35%'>
						<input type="checkbox" name='MAIN_LIAISON_OFFICE' id='MAIN_LIAISON_OFFICE' value="Y">
					</td>
				</tr>
				<tr>
					<td class="td_title" width="15%">
						<spring:message code="ess.empInfo.relationship" />
						<!-- 关系-->
					</td>
					<td class="td_type" width="35%">
						<ait:SelectSyCodeByCpnyID name="EMER_TYPE_CODE" parentNo="1693"
							cnpyID="${defaultCpny}" />
					</td>
					<td class="td_title" width='15%'>
						<!-- E-Mail --><spring:message code="hrm.empinfo.EMAIL" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_EMAIL" id="EMER_EMAIL" />
					</td>
				</tr>
				<tr>
					<td class="td_title" width='15%'>
						<!--联系电话 --><spring:message code="ess.empInfo.contact_number" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_PHONE" />
					</td>
					<td class="td_title" width='15%'>
						<!--联系电话2 --><spring:message code="hrm.empinfo.LIANXI_DIANHUA_TWO.Z" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_PHONE_SECOND" />
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td class="td_title" width="4%" style="display:none"><spring:message code="ess.empInfo.phone_number" /><!--手机号码--></td>
					<td class="td_type" width="25%" style="display:none">
						<input type="text" id="EMER_CELLPHONE" name="EMER_CELLPHONE" >
					</td>
					<td class="td_title" width='15%'>
						<!--办公电话--><spring:message code="ess.empInfo.office_telephone" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_WORK_PHONE" />
					</td>
					<td class="td_title" width='15%'>
					<spring:message code="ess.empInfo.Country" />
						<!--国家-->
					</td>
					<td class="td_type" width='35%'>
						<ait:SelectSyCodeByCpnyID name="NATIONALITY" parentNo="870"
							cnpyID="${defaultCpny}" />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr style="display:none">
					<td class="td_title" width="4%"><spring:message code="ess.empInfo.phone_number" /><!--手机号码--></td>
					<td class="td_type" width="25%">
						<input type="text" id="EMER_CELLPHONE" name="EMER_CELLPHONE" >
					</td>
					<td class="td_title" width='15%'>
						<!--办公电话--><spring:message code="ess.empInfo.office_telephone" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="EMER_WORK_PHONE" />
					</td>
				</tr>
				<tr>
					<td class="td_title" width='15%'>
					<spring:message code="ess.empInfo.Country" />
						<!--国家-->
					</td>
					<td class="td_type" width='35%'>
						<ait:SelectSyCodeByCpnyID name="NATIONALITY" parentNo="870"
							cnpyID="${defaultCpny}" />
					</td>
					<td class="td_title" width='15%'></td>
					<td class="td_type" width='35%'></td>
				</tr>
				</c:if>
				<tr>
					<td class="td_title" width='15%'>
						<spring:message code="ess.empInfo.address" />
						<!--地址-->
					</td>
					<td class="td_type" width='75%' colspan="3">
						<input type="text" name="EMER_ADDRESS" size="60px">
					</td>
				</tr>
			</table>
			<div id="createTable" width="100%"></div>
			<input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!-- 提交 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
<!-- </div> --></div>