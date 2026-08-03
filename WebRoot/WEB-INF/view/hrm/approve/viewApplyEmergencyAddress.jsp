<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updateEmergencyInfo");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : callback || DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;
}function submitForm1(type) {
	alert(type);

}
function submitForm2(type) {

	if ($("#SUBMIT_TYPE").val() == 1) {
		$("#SUBMIT_TYPE").val(2);
		var $form = $("#updateEmergencyInfo").submit();
	}
}
function submitForm3(type) {
	if ($("#SUBMIT_TYPE").val() == 1) {
		$("#SUBMIT_TYPE").val(3);
			$("#APPLY_TYPE_NUM").val(8);
		var $form = $("#updateEmergencyInfo").submit();
	}

}
function submitForm4(type) {
	alert(type);

}
</script>
<%-- <c:if test="${EmergencyAddressList.SUBMIT_TYPE eq 1}"> --%>

	<div class="pageHeader"
		style="width: 300px; margin-left: auto; margin-right: 2px; width: 300px;">

		<div class="searchBar" width="100%" style="border: 0px solid #8aa6d7;">
			<div class="subBar">
				<ul>
					<li>
						<div>
							<a class="buttonActive" onclick="print()"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!--印刷 --> <spring:message code="hrm.approve.PRINTING" /> </span> </a>
						</div>
					</li>
					<c:if test="${EmergencyAddressList.SUBMIT_TYPE eq 1}">
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm2('submit');"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span> <!-- 完结 --> <spring:message code="hrm.approve.OVER" />  </span> </a>
						</div>
					</li>
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm3('back');" 
							alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 退回 --><spring:message code="hrm.approve.RETURN"/> </span>
							</a>
						</div>
					</li>
					</c:if>
					<%-- <li>
						<div>
							<a class="buttonActive" onclick="submitForm4('email');"
								alt="点击上传"><span> <!-- 制定邮件  --> <spring:message code="hrm.approve.MAKE_MAILE" /> </span> </a>
						</div>
					</li> --%>
				</ul>
			</div>
		</div>

	</div>
	<div class="pageContent" style="margin: 0px; padding: 0px;" sysLong="printDiv">
		<form id="updateEmergencyInfo"
			onsubmit="return validateCallbackViewFamilyInfo(this,navTabAjaxDoneWithForm);"
			action="/hrm/approve/updateEmergencyInfo" method="post">
			<table class="table" width="100%" 
				style="margin: 0px; padding: 0px;">
				<thead>
					<tr>

						<th width="25%">
							NO
						</th>
						<th width="25%">
							Line
						</th>
						<th width="25%">
							<!-- 申请后 --><spring:message code="hrm.approve.APPLY_AFTER" />
						</th>
						<th width="25%">
							<!--申请前  --><spring:message code="hrm.approve.APPLY_FRONT" />
						</th>
					</tr>
				</thead>
				<tbody>

					<input type="hidden" value="${EmergencyAddressList.PERSON_ID}"
						name="PERSON_ID" id="PERSON_ID" />
					<input type="hidden" value="${EmergencyAddressList.EMERGENCY_NO}"
						id="EMERGENCY_NO" name="EMERGENCY_NO" />
					<input type="hidden"
						value="${EmergencyAddressList.UPDATE_EMERGENCY_NO}"
						id="UPDATE_EMERGENCY_NO" name="UPDATE_EMERGENCY_NO" />

					<input type="hidden" value="${EmergencyAddressList.ESS_TYPE_CODE }"
						id="ESS_TYPE_CODE" name="ESS_TYPE_CODE" />
					<input type="hidden" value="${EmergencyAddressList.APPLY_TYPE }"
						id="APPLY_TYPES" name="APPLY_TYPES" />
					<input type="hidden" value="${EmergencyAddressList.APPLY_TYPE_NUM}"
						name="APPLY_TYPE" id="APPLY_TYPE_NUM" />
					<!-- 1提交 2审批 3退回 4取消 -->
					<input type="hidden" value="${EmergencyAddressList.SUBMIT_TYPE}"
						id="SUBMIT_TYPE" name="ACTIVITY_NUM" />
					<tr>
						<td>
							1
						</td>
						<td>
							<!-- 姓名 --><spring:message code="hrm.empinfo.FAM_NAME" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.EMER_NAME ne EmergencyAddressInfoPro.EMER_NAME?'red':''}">${EmergencyAddressList.EMER_NAME}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.EMER_NAME}
						</td>
					</tr>
					<tr>
						<td>
							2
						</td>
						<td>
							<spring:message code="hrm.empinfo.MAIN_CONTACT_AREA" /><!--主要联络处与否-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.MAIN_LIAISON_OFFICE ne EmergencyAddressInfoPro.MAIN_LIAISON_OFFICE?'red':''}">
								<input type="checkbox" disabled="disabled" <c:if test="${EmergencyAddressList.MAIN_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> ></input></font>
						</td>
						<td>
							<input type="checkbox" disabled="disabled" <c:if test="${EmergencyAddressInfoPro.MAIN_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> ></input>
						</td>
					</tr>
					<tr>
						<td>
							3
						</td>
						<td>
							<!-- 关系 --><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.EMER_TYPE_CODE ne EmergencyAddressInfoPro.EMER_TYPE_CODE?'red':''}">${EmergencyAddressList.EMER_TYPE_CODE}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.EMER_TYPE_CODE}
						</td>
					</tr>
					<tr>
						<td>
							4
						</td>
						<td>
							E-mail
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.EMER_EMAIL ne EmergencyAddressInfoPro.EMER_EMAIL?'red':''}">${EmergencyAddressList.EMER_EMAIL}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.EMER_EMAIL}
						</td>
					</tr>
					<tr>
						<td>
							5
						</td>
						<td>
							<!-- 联系电话 --><spring:message code="ess.empInfo.contact_number" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.EMER_PHONE ne EmergencyAddressInfoPro.EMER_PHONE?'red':''}">
								${EmergencyAddressList.EMER_PHONE}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.EMER_PHONE}
						</td>
					</tr>
					<tr>
						<td>
							6
						</td>
						<td>
							<!-- 联系电话2 --><spring:message code="hrm.empinfo.LIANXI_DIANHUA_TWO.Z" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.EMER_PHONE_SECOND ne EmergencyAddressInfoPro.EMER_PHONE_SECOND?'red':''}">
								${EmergencyAddressList.EMER_PHONE_SECOND}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.EMER_PHONE_SECOND}
						</td>
					</tr>
					<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
					<tr>
						<td>
							7
						</td>
						<td>
							<!-- 办公电话 --><spring:message code="ess.empInfo.office_telephone" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.EMER_WORK_PHONE ne EmergencyAddressInfoPro.EMER_WORK_PHONE ?'red':''}">${EmergencyAddressList.EMER_WORK_PHONE}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.EMER_WORK_PHONE}
						</td>
					</tr>
					<tr>
						<td>
							8
						</td>
						<td>
							<!-- 国家 --><spring:message code="hrm.empinfo.country" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.NATIONALITY ne EmergencyAddressInfoPro.NATIONALITY?'red':''}">${EmergencyAddressList.NATIONALITY}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.NATIONALITY}
						</td>
					</tr>
					<tr>
						<td>
							9
						</td>
						<td>
							<spring:message code="ess.empInfo.address" />
						<!--地址-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.EMER_ADDRESS ne EmergencyAddressInfoPro.EMER_ADDRESS?'red':''}">${EmergencyAddressList.EMER_ADDRESS}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.EMER_ADDRESS}
						</td>
					</tr>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<tr>
						<td>
							7
						</td>
						<td>
							<!-- 国家 --><spring:message code="hrm.empinfo.country" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.NATIONALITY ne EmergencyAddressInfoPro.NATIONALITY?'red':''}">${EmergencyAddressList.NATIONALITY}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.NATIONALITY}
						</td>
					</tr>
					<tr>
						<td>
							8
						</td>
						<td>
							<spring:message code="ess.empInfo.address" />
						<!--地址-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${EmergencyAddressList.EMER_ADDRESS ne EmergencyAddressInfoPro.EMER_ADDRESS?'red':''}">${EmergencyAddressList.EMER_ADDRESS}</font>
						</td>
						<td>
							${EmergencyAddressInfoPro.EMER_ADDRESS}
						</td>
					</tr>
					</c:if>
				</tbody>
			</table>
			<div width="100%">
				<table class="user_table" width="100%">
					<tr>
						<td calss="td_title" width="20%" style="background: #ddd">
							<!-- 回复 --> <spring:message code="hrm.approve.REPLY" />
						</td>
						<td calss="td_type" width="80%">
							<textarea name="CALLBACK" style="width: 200px; height: 80px">${EmergencyAddressList.CALLBACK}</textarea>
						</td>
					</tr>
				</table>

				<div width="100%">
					<table class="user_table" width="100%">
						<tr>
							<td calss="td_title" width="20%" style="background: #ddd">
								<!-- 错误内容  --> <spring:message code="hrm.approve.ERROR_CONTENT" />
							</td>
							<td calss="td_type" width="80%">
								<textarea name="EARROR" style="width: 200px; height: 80px">${EmergencyAddressList.EARROR}</textarea>
							</td>
						</tr>
					</table>
				</div>

			</div>
		</form>
	</div>
<%-- </c:if> --%>