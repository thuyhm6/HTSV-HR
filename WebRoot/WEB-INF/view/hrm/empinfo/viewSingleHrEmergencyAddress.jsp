<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	function validateAddResumeInfoCallback(form, callback) {
		var $form = $("#" + form);
		if (!$form.valid()) {
			return false;
		}
		alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>", {//确定要保存吗？
			okCall : function() {
				$.ajax({
					type : form.method || 'POST',
					url : $form.attr("action"),
					data : $form.serializeArray(),
					dataType : "json",
					cache : false,
					success : callback || DWZ.ajaxDone,
					error : DWZ.ajaxError
				});
			}
		});
		return false;
	}

	function validateDeleteResumeInfoCallback(form, callback) {
		var $form = $("#" + form);
		alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete"/>", {//确定要删除吗？
			okCall : function() {
				$.ajax({
					type : form.method || 'POST',
					url : '/hrm/empinfo/deleteHrEmergencyAddress',
					data : $form.serializeArray(),
					dataType : "json",
					cache : false,
					success : callback || DWZ.ajaxDone,
					error : DWZ.ajaxError
				});
			}
		});
		return false;
	}

	function changeMainOffice(index) {
		var check = $('#changeMain').prop('checked');

		if (check == true) {
			$('#MAIN_LIAISON_OFFICE').attr('value', 'Y');
			document.getElementById("cMain_" + index).checked = true;

		} else {
			$('#MAIN_LIAISON_OFFICE').attr('value', 'N');
			document.getElementById("cMain_" + index).checked = false;

		}

	}

	function finalLIAISON() {
		var check = $('#final').prop('checked');
		if (check == true) {
			$('#MAIN_LIAISON_OFFICE').attr('value', 'Y');
		} else {
			$('#MAIN_LIAISON_OFFICE').attr('value', 'N');
		}
		
	}
</script>
<div class="pageContent" style="height: 613px;">
	<div>
		<form id="editHrEmergencyAddress" method="post"
			action="/hrm/empinfo/editHrEmergencyAddress"
			class="pageForm required-validate"
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}">
			<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID"
				VALUE="${PERSON_ID}"> <input TYPE="hidden"
				NAME="isEssSystem" VALUE="${isEssSystem}"> <input
				type="hidden" name="EMERGENCY_NO" id="EMERGENCY_NO"
				value="${personInfo.EMERGENCY_NO}">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2"
					cellspacing="1">
					<tr>
						<td>
							<table class="user_table" width="100%">
								<tr>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.FAM_NAME" /><!--姓名--></td>
									<td class="td_type" width="25%"><input type="text"
										id="EMER_NAME" name="EMER_NAME"
										value=${personInfo.EMER_NAME }></td>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.MAIN_CONTACT_AREA" /><!--主要联络处与否--></td>
									<td width="35%" class="td_type">
										<c:if test="${personInfo.MAIN_LIAISON_OFFICE=='Y' }">
											<input type="checkbox" checked="final" id="final" onclick="finalLIAISON()">
											<input type="hidden" name='MAIN_LIAISON_OFFICE' id='MAIN_LIAISON_OFFICE' value="Y">
										</c:if> 
										<c:if test="${personInfo.MAIN_LIAISON_OFFICE !='Y' }">
											<input type="checkbox" id="final" onclick="finalLIAISON()">
											<input type="hidden" id="MAIN_LIAISON_OFFICE" name="MAIN_LIAISON_OFFICE" value="N">
										</c:if>
									</td>
								</tr>
								<tr>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" /><!--关系--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="EMER_TYPE_CODE" id="EMER_TYPE_CODE" parentNo="1693"
											cnpyID="${defaultCpny}"
											selected="${personInfo.EMER_TYPE_CODE}" /></td>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.EMAIL" /><!--E-Mail--></td>
									<td class="td_type" width="25%">
										<input type="text" id="EMER_EMAIL" name="EMER_EMAIL" value=${personInfo.EMER_EMAIL }>
									</td>
								</tr>
								<tr>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.FAM_PHONE" /><!--联系电话--></td>
									<td class="td_type" width="25%"><input type="text"
										id="EMER_PHONE" name="EMER_PHONE"
										value=${personInfo.EMER_PHONE }></td>
								 	<td class="td_title" width="4%"><spring:message code="hrm.empinfo.LIANXI_DIANHUA_TWO.Z" /><!--联系电话2--></td>
									<td class="td_type" width="25%"><input type="text"
										id="EMER_PHONE_SECOND" name="EMER_PHONE_SECOND"
										value=${personInfo.EMER_PHONE_SECOND }>
										<input type="hidden" id="EMER_CELLPHONE" name="EMER_CELLPHONE" value=${personInfo.EMER_CELLPHONE }>
										<input type="hidden" id="EMER_WORK_PHONE" name="EMER_WORK_PHONE" value=${personInfo.EMER_WORK_PHONE }>
									</td>
								</tr>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
						        <tr>
						        	<td class="td_title" width="4%" style='display:none'><spring:message code="ess.empInfo.phone_number" /><!--手机号码--></td>
									<td class="td_type" width="25%" style='display:none'><input type="text"
										id="EMER_CELLPHONE" name="EMER_CELLPHONE"
										value=${personInfo.EMER_CELLPHONE }></td>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.COMPANY_PHONE" /><!--办公电话--></td>
									<td class="td_type" width="25%"><input type="text"
										id="EMER_WORK_PHONE" name="EMER_WORK_PHONE"
										value=${personInfo.EMER_WORK_PHONE }></td>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.country" /><!--国家--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="NATIONALITY" id="NATIONALITY" parentNo="870"
											cnpyID="${defaultCpny}" selected="${personInfo.NATIONALITY}"
											limit="all" /></td>
						        </tr>
						        </c:if>
						        <c:if test="${LoginUser.cpnyId eq 'HAE'}">
						        <tr>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.country" /><!--国家--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="NATIONALITY" id="NATIONALITY" parentNo="870"
											cnpyID="${defaultCpny}" selected="${personInfo.NATIONALITY}"
											limit="all" /></td>
									<td class="td_title" width="4%"></td>
									<td class="td_type" width="25%"></td>
						        </tr>
						        </c:if>
								<tr>
									<td class="td_title" width="4%"><spring:message code="hrm.empinfo.FAM_ADDRESS" /><!--地址--></td>
									<td class="td_type" width='75%' colspan="3"><input type="text"
										id="EMER_ADDRESS" name="EMER_ADDRESS" size="100px"
										value=${personInfo.EMER_ADDRESS }></td>
								</tr>
								<tr>
									<c:if test="${not empty personInfo.UPDATED_BY}">
										<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者--></td>
										<td class="td_type" width="25%">${personInfo.UPDATED_BY
											}&nbsp&nbsp${personInfo.UPDATED_IP }</td>
									 	<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></td>
										<td class="td_type" width="25%">
											${personInfo.UPDATE_DATE }</td>
									</c:if>
									<c:if test="${empty personInfo.UPDATED_BY}">
										<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者--></td>
										<td class="td_type" width="25%">${personInfo.CREATED_BY
											}&nbsp&nbsp${personInfo.CREATED_IP }</td>
										<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></td>
										<td class="td_type" width="25%">
											${personInfo.CREATE_DATE }</td>
									</c:if>
								</tr>
							 	</table>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>