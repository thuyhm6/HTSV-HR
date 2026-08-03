<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script> 

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewFamilyInfo(form, callback) {
	
		var $form = $("#viewHomeRelationInfo");
		
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
    
    function copyAddress(){
    
    	$("#FAM_ADDRESS").attr("value",$("#addressMsg").attr("value"));
    	
    }
</script>

<div class="pageContent">
	<form id="viewHomeRelationInfo" method="post"
		action="/ess/empinfo/addHomeRelationInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">
		<input type="hidden" id="isEssSystem" name="isEssSystem"
			value="${isEssSystem }" />
		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table margin_b">
				<tr>
					<td class="td_title" width='15%'>
						<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME" />
									<!--关系-->
					</td>
					<td class="td_type" width='35%'>
						<ait:SelectSyCodeByCpnyID name="FAM_TYPE_CODE" parentNo="950"
							cnpyID="${defaultCpny}" />
						<input type="hidden" name="PERSON_ID" class="textInput"
							value="${LoginUser.adminID}" />
					</td>
					<td class="td_title" width='15%'>
						<spring:message code="hr.viewPersonalInfo.title.SEX" /><!--性别-->
					</td>
					<td class="td_type" width='35%'>
						<ait:SelectSyCodeByCpnyID name="GENDER" parentNo="1324"
							cnpyID="${defaultCpny}" />
					<input type="hidden" name="APPLY_TYPE" value="${1}"/>
							
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
						<!--姓名-->
					</td>
					<td class="td_type">
						<input type="text" name="FAM_NAME" class="textInput" />
					</td>
					<td class="td_title"></td>
					<td class="td_type"></td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DOB" />
						<!--出生日期-->
					</td>
					<td class="td_type">
						<input type="text" name="FAM_BORNDATE" class="Wdate required"
							readonly="true" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
					</td>
					 
					<td class="td_title">
						<spring:message code="hr.viewHire.title.HOME_PHONE" /><!--家庭电话-->
					</td>

					<td class="td_type">
						<input type="text" name="FAM_FAMILY_PHONE" class="textInput"
							maxlength="30" />
					</td>
					 
				</tr>
				<tr>
					<td class="td_title" width='15%'>
						<spring:message code="hrm.recruitManage.FAM_EMAIL"/>
						<!--家人电子邮箱-->
					</td>
					<td class="td_type" width='35%'>


						<input type="text" name="FAM_EMAIL" class="textInput"
							maxlength="30" />
					</td>
					<td class="td_title" width='15%'>
					<spring:message code="hr.hrm.empinfo.dizhifuzhi.Z"/>
						<!--地址复制-->
					</td>
					<td class="td_type" width='35%'>
						<input type="button" onclick="javascript:copyAddress()"
							value="<spring:message code="hr.hrm.empinfo.renzhirenyuanbiaoqian.Z"/>" />
						<c:forEach items="${addressList}" var="item">
							<c:if test="${item.ADDRESS_TYPE=='14013841'}">
								<input type="hidden" id='addressMsg'
									value='${item.ADDRESS_CONTENT}' />
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td class="td_title" width='15%'>
						<spring:message code="hr.viewGoAbroad.title.COUNTRY_NAME" /><!--国家-->
					</td>
					<td class="td_type" width='35%'>
						<ait:SelectSyCodeByCpnyID name="NATIONALITY" parentNo="870"
							cnpyID="${defaultCpny}" />

					</td>
					<td class="td_title" width='15%'>
						<spring:message code="hrm.recruitManage.EMERGENCY_CONTACT_YN" /><!--紧急联络处-->
					</td>
					<td class="td_type" width='35%'>
						<input type="checkbox" value="Y" name="EMERGENCY_LIAISON_OFFICE">
					</td>
				</tr>

			</table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table margin_b">

				<tr>
					<td class="td_title" width='15%'>
						<spring:message code="hr.viewRelation.title.FAM_ADDRESS" /><!--地址-->
					</td>
					<td class="td_type" width='75%'>


						<input type="text" name="FAM_ADDRESS" id='FAM_ADDRESS'
							class="textInput" maxlength="300" style="width: 80%" />
					</td>
				</tr>
				 </table>
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table margin_b">

				<tr>
					<td class="td_title" width='15%'>
						<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" /><!--手机号码-->
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="FAM_PHONE" id='FAM_PHONE' class="textInput" maxlength="80"  />
					</td>
					<td class="td_title" width='15%'>
						<spring:message code="sys.basicMaint.title.companyTelPhoneNo" /><!--工作处电话-->
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="WORK_PHONE"  class="textInput" maxlength="80" />
					</td>
				</tr>
				<c:if test="${LoginUser.cpnyId eq 'HAE'}">
				<tr>
					<td class="td_title" width='15%'>
						<!-- 工作岗位 --> <spring:message code="hrm.empinfo.WORK_DUTY.Z" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="OCUPATION" id='OCUPATION'
							class="textInput" maxlength="80"  />
					</td>
					<td class="td_title" width='15%'>
						<!-- 工作单位 --> <spring:message code="hr.viewWorkInfo.title.CPNY_NAME" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="FAM_COMPANY_NAME" 
							class="textInput" maxlength="80" />
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr style="display:none">
					<td class="td_title" width='15%'>
						<!-- 工作岗位 --> <spring:message code="hrm.empinfo.WORK_DUTY.Z" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="OCUPATION" id='OCUPATION'
							class="textInput" maxlength="80"  />
					</td>
					<td class="td_title" width='15%'>
						<!-- 工作单位 --> <spring:message code="hr.viewWorkInfo.title.CPNY_NAME" />
					</td>
					<td class="td_type" width='35%'>
						<input type="text" name="FAM_COMPANY_NAME" 
							class="textInput" maxlength="80" />
					</td>
				</tr>
				</c:if>
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
								<!-- 保存 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
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
</div>