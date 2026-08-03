<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updateHomeRelationInfo");

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
}
function submitForm1(type) {
	alert(type);

}
function submitForm2(type) {
	if ($("#SUBMIT_TYPE").val() == 1) {
		$("#SUBMIT_TYPE").val(2);
		var $form = $("#updateHomeRelationInfo").submit();
	}  
}
function submitForm3(type) {
	if ($("#SUBMIT_TYPE").val() == 1) {
			$("#SUBMIT_TYPE").val(3);
			$("#APPLY_TYPE_NUM").val(8);
		var $form = $("#updateHomeRelationInfo").submit();
	}  

}
function submitForm4(type) {
	alert(type);

}
</script>
<%-- <c:if test="${homeRelationList.SUBMIT_TYPE eq 1}"> --%>

	<div class="pageHeader"
		style="width: 300px; margin-left: auto; margin-right: 2px; width: 300px;">

		<div class="searchBar" width="100%" style="border: 0px solid #8aa6d7;">
			<div class="subBar">
				<ul>
					<li>
						<div>
							<a class="buttonActive" onclick="print()"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 印刷 --><spring:message code="hrm.approve.PRINTING" />        </span> </a>
						</div>
					</li>
					<c:if test="${homeRelationList.SUBMIT_TYPE eq 1}">
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm2('submit');"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span> <!--完结 --><spring:message code="hrm.approve.OVER" />     </span> </a>
						</div>
					</li>
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm3('back');" 
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!--退回--> <spring:message code="hrm.approve.RETURN" /> </span>
							</a>
						</div>
					</li>
					</c:if>
					<%-- <li>
						<div>
							<a class="buttonActive" onclick="submitForm4('email');"
								alt="点击上传"><span> <!-- 制定邮件 --> <spring:message code="hrm.approve.MAKE_MAILE" /></span> </a>
						</div>
					</li> --%>
				</ul>
			</div>
		</div>

	</div>
	<div class="pageContent" style="margin: 0px; padding: 0px;" sysLong="printDiv">
		<form id="updateHomeRelationInfo"
			onsubmit="return validateCallbackViewFamilyInfo(this, navTabAjaxDoneWithForm);"
			action="/hrm/approve/updateHomeRelationInfo" method="post">
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
							<!-- 申请后 --> <spring:message code="hrm.approve.APPLY_AFTER" />
						</th>
						<th width="25%">
							<!-- 申请前  --> <spring:message code="hrm.approve.APPLY_FRONT" />
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							1
						</td>
						<td>
							<!-- 关系 --><spring:message code="ess.empInfo.relationship" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.FAM_TYPE_CODE ne homeRelationInfoPro.FAM_TYPE_CODE ?'red':''}">${homeRelationList.FAM_TYPE_CODE}</font>
							
						</td>
						<td>
							${homeRelationInfoPro.FAM_TYPE_CODE}
						</td>
					</tr>
					<tr>
						<td>
							2
						</td>
						<td>
							<!-- 姓名 --><spring:message code="hrm.empinfo.FAM_NAME" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.FAM_NAME ne homeRelationInfoPro.FAM_NAME ?'red':''}">${homeRelationList.FAM_NAME}</font>
							
						</td>
						<td>
							${homeRelationInfoPro.FAM_NAME}
						</td>
					</tr>
					<tr>
						<td>
							3
						</td>
						<td>
							<spring:message code="hr.viewPersonalInfo.title.SEX" /><!--性别-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.GENDER ne homeRelationInfoPro.GENDER?'red':''}">${homeRelationList.GENDER}</font>
							
						</td>
						<td>
							${homeRelationInfoPro.GENDER}
						</td>
					</tr>
					<tr>
						<td>
							4
						</td>
						<td>
							<spring:message code="hr.viewPersonalInfo.title.DOB" />
						<!--出生日期-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.FAM_BORNDATE ne homeRelationInfoPro.FAM_BORNDATE ?'red':''}">${homeRelationList.FAM_BORNDATE}</font>
							
						</td>
						<td>
							${homeRelationInfoPro.FAM_BORNDATE}
						</td>
					</tr>
					<tr>
						<td>
							5
						</td>
						<td>
							<spring:message code="hr.viewHire.title.HOME_PHONE" /><!--家庭电话-->
						</td>
						<td>
							<font style="line-height: 20px;" 
								color="${homeRelationList.FAM_FAMILY_PHONE ne homeRelationInfoPro.FAM_FAMILY_PHONE ?'red':''}">${homeRelationList.FAM_FAMILY_PHONE}</font>
							
						</td>
						<td>
							${homeRelationInfoPro.FAM_FAMILY_PHONE}
						</td>
					</tr>
					<tr>
						<td>
							6
						</td>
						<td>
							<spring:message code="hrm.recruitManage.FAM_EMAIL"/>
						<!--家人电子邮箱-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.FAM_EMAIL ne homeRelationInfoPro.FAM_EMAIL ?'red':''}">${homeRelationList.FAM_EMAIL}</font>
							
						</td>
						<td>
							${homeRelationInfoPro.FAM_EMAIL}
						</td>
					</tr>
					<tr>
						<td>
							7
						</td>
						<td>
							<spring:message code="hr.viewGoAbroad.title.COUNTRY_NAME" /><!--国家-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.NATIONALITY ne homeRelationInfoPro.NATIONALITY?'red':''}">${homeRelationList.NATIONALITY}</font>
							
						</td>
						<td>
							${homeRelationInfoPro.NATIONALITY}
						</td>
					</tr>
					<tr>
						<td>
							8
						</td>
						<td>
							<spring:message code="hrm.recruitManage.EMERGENCY_CONTACT_YN" /><!--紧急联络处-->
					   </td>
						<td>
							<font style="line-height: 20px;" color="${homeRelationList.EMERGENCY_LIAISON_OFFICE ne homeRelationInfoPro.EMERGENCY_LIAISON_OFFICE ?'red':''}" >
							<input type="checkbox" disabled="disabled" <c:if test="${homeRelationList.EMERGENCY_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> ></input></font>
						</td>
						<td>
							<input type="checkbox" disabled="disabled" <c:if test="${homeRelationInfoPro.EMERGENCY_LIAISON_OFFICE eq 'Y'}"> checked="checked" </c:if> ></input>
						</td>
					</tr>
					<tr>
						<td>
							9
						</td>
						<td>
							<spring:message code="hr.viewRelation.title.FAM_ADDRESS" /><!--地址-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.FAM_ADDRESS ne homeRelationInfoPro.FAM_ADDRESS ?'red':''}">${homeRelationList.FAM_ADDRESS}</font>
							
						</td>
						<td>
							${homeRelationInfoPro.FAM_ADDRESS}
						</td>
					</tr>
					<tr>
						<td>
							10
						</td>
						<td>
					 		<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" /><!--手机号码-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.FAM_PHONE ne homeRelationInfoPro.FAM_PHONE ?'red':''}">${homeRelationList.FAM_PHONE}
							
						</td>
						<td>
							${homeRelationInfoPro.FAM_PHONE}
						</td>
					</tr>
					<tr>
						<td>
							11
						</td>
						<td>
					 		<spring:message code="sys.basicMaint.title.companyTelPhoneNo" /><!--工作处电话-->
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.WORK_PHONE ne homeRelationInfoPro.WORK_PHONE ?'red':''}">${homeRelationList.WORK_PHONE}
							
						</td>
						<td>
							${homeRelationInfoPro.WORK_PHONE}
						</td>
					</tr>
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					<tr>
						<td>
							12
						</td>
						<td>
					 		<!-- 工作岗位 --> <spring:message code="hrm.empinfo.WORK_DUTY.Z" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.OCUPATION ne homeRelationInfoPro.OCUPATION ?'red':''}">${homeRelationList.OCUPATION}
							
						</td>
						<td>
							${homeRelationInfoPro.OCUPATION}
						</td>
					</tr>
					<tr>
						<td>
							13
						</td>
						<td>
					 		<!-- 工作单位 --> <spring:message code="hr.viewWorkInfo.title.CPNY_NAME" />
						</td>
						<td>
							<font style="line-height: 20px;"
								color="${homeRelationList.FAM_COMPANY_NAME ne homeRelationInfoPro.FAM_COMPANY_NAME ?'red':''}">${homeRelationList.FAM_COMPANY_NAME}
							
						</td>
						<td>
							${homeRelationInfoPro.FAM_COMPANY_NAME}
						</td>
					</tr>
					</c:if>
		
				
					<input type="hidden" value="${homeRelationList.PERSON_ID}"
						name="PERSON_ID" id="PERSON_ID" />
					<input type="hidden" value="${homeRelationList.FAMILY_NO}"
						id="FAMILY_NO" name="FAMILY_NO" />
					<input type="hidden" value="${homeRelationList.UPDATE_FAMILY_NO}"
						id="UPDATE_FAMILY_NO" name="UPDATE_FAMILY_NO" />
					<input type="hidden" value="${homeRelationList.ESS_TYPE_CODE }"
						id="ESS_TYPE_CODE" name="ESS_TYPE_CODE" />
					<input type="hidden" value="${homeRelationList.APPLY_TYPE }"
						id="APPLY_TYPES" name="APPLY_TYPES" />
					<input type="hidden" value="${homeRelationList.APPLY_TYPE_NUM}"
						name="APPLY_TYPE" id="APPLY_TYPE_NUM" />
					<!-- 1提交 2审批 3退回 4取消 -->
					<input type="hidden" value="${homeRelationList.SUBMIT_TYPE}"
						id="SUBMIT_TYPE" name="ACTIVITY_NUM" />
				</tbody>
			</table>
			<div width="100%">
				<table class="user_table" width="100%">
					<tr>
						<td calss="td_title" width="20%" style="background: #ddd">
							 <!-- 回复 --> <spring:message code="hrm.approve.REPLY" />
						</td>
						<td calss="td_type" width="80%">
							<textarea name="CALLBACK" style="width: 200px; height: 80px">${homeRelationList.CALLBACK}</textarea>
						</td>
					</tr>
				</table>

				<div width="100%">
					<table class="user_table" width="100%">
						<tr>
							<td calss="td_title" width="20%" style="background: #ddd">
								<!-- 错误内容 --> <spring:message code="hrm.approve.ERROR_CONTENT" />
							</td>
							<td calss="td_type" width="80%">
								<textarea name="EARROR" style="width: 200px; height: 80px">${homeRelationList.EARROR}</textarea>
							</td>
						</tr>
					</table>
				</div>
		</form>
	</div>
<%-- </c:if> --%>