<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updatePersonal");

	if (!$form.valid()) {
		return false;
	}
	if($("#LONG_TERMS").attr("checked")=="checked"){
		$("#LONG_TERM").val('Y');
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
function finalPERSONALESS() {
	var check = $('#finalARMYESS').prop('checked');
	if (check == true) {
		$('#ARMY_OR_NOT').attr('value', 'Y');
	} else {
		$('#ARMY_OR_NOT').attr('value', 'N');
	}
	var check = $('#finalOBSTACLEESS').prop('checked');
	if (check == true) {
		$('#OBSTACLE_OR_NOT').attr('value', 'Y');
	} else {
		$('#OBSTACLE_OR_NOT').attr('value', 'N');
	}
}
function age(){
	var DOB = $("#DOB").val();
	if($("#DOB").val() == null || $("#DOB").val() == ""){
		$("#AGE").val("");
	}else{
		var nowdate = new Date();
		var nowyear = nowdate .getFullYear();
		var birth = parseInt(DOB.substring(6,10));
		$("#AGE").val(nowyear - birth);
	}
}	
</script>

<!-- <div class="pageContent"> -->
<div style="background-color: #fff;">
	<form id="updatePersonal" method="post"
		action="/ess/empinfo/updatePersonal"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">


				<input type="hidden" name="APPLY_TYPE" value="${2}" />


				<input type="hidden" name="PERSON_ID" value="${PERSON_ID}" />
				<input type="hidden" id="picDif" name="picDif" value="1"/>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.LOCAL_NAME" /><!--中文姓名--></td>
									<td class="td_type" width="25%">${personalInfo.LOCAL_NAME }</td>
									<td class="td_title"><spring:message code="hrm.empinfo.ENGLISH_NAME" /><!--英文姓名--></td>
									<td class="td_type" width="25%">${personalInfo.ENGLISH_NAME }</td>
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class="td_title"><spring:message code="hrm.empinfo.ARMY_OR_NOT.Z" /><!--参军与否--></td>
									<td width="25%" class="td_type">
										<c:if test="${personalInfo.ARMY_OR_NOT=='Y' }">
											<input type="checkbox" checked="finalARMYESS" id="finalARMYESS" onclick="finalPERSONALESS()">
											<input type="hidden" name='ARMY_OR_NOT' id='ARMY_OR_NOT' value="Y">
										</c:if> 
										<c:if test="${personalInfo.ARMY_OR_NOT !='Y' }">
											<input type="checkbox" id="finalARMYESS" onclick="finalPERSONALESS()">
											<input type="hidden" id="ARMY_OR_NOT" name="ARMY_OR_NOT" value="N">
										</c:if>
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.OBSTACLE_OR_NOT.Z" /><!--障碍与否--></td>
									<td class="td_type" width="25%">
										<c:if test="${personalInfo.OBSTACLE_OR_NOT=='Y' }">
											<input type="checkbox" checked="finalOBSTACLEESS" id="finalOBSTACLEESS" onclick="finalPERSONALESS()">
											<input type="hidden" name='OBSTACLE_OR_NOT' id='OBSTACLE_OR_NOT' value="Y">
										</c:if> 
										<c:if test="${personalInfo.OBSTACLE_OR_NOT !='Y' }">
											<input type="checkbox" id="finalOBSTACLEESS" onclick="finalPERSONALESS()">
											<input type="hidden" id="OBSTACLE_OR_NOT" name="OBSTACLE_OR_NOT" value="N">
										</c:if>
									</td>
									</c:if>
									<c:if test="${LoginUser.cpnyId eq 'HAE'}">
										<td class="td_title"></td>
										<td class="td_type" width="25%"><input type="hidden" name='ARMY_OR_NOT' id='ARMY_OR_NOT' value=""></td>
										<td class="td_title"></td>
										<td class="td_type" width="25%">
											<input type="hidden" name='OBSTACLE_OR_NOT' id='OBSTACLE_OR_NOT' value="">
											<input type="hidden" id="RELIGION" name="RELIGION" value="">
											<input type="hidden" id="RESIDENTIAL_DISTINCTION" name="RESIDENTIAL_DISTINCTION" value="">
											<input type="hidden" id="POLITICAL_OUTLOOK" name="POLITICAL_OUTLOOK" value="">
										</td>
									</c:if>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" /><!-- 身份证号 --></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.IDCARD_NO }" id="IDCARD_NO"
										name="IDCARD_NO"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.award_date" /><!--获证日期--></td>
									<td class="td_type" width="25%"><input name="IDCARD_START_DATE" id="IDCARD_START_DATE"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personalInfo.IDCARD_START_DATE}"/></td>
									<td class="td_title"><spring:message code="hrm.empinfo.QIANFA_JIGUAN.Z" /><!-- 签发机构 --></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.ISSUING_AUTHORITY }" id="ISSUING_AUTHORITY"
										name="ISSUING_AUTHORITY"></td>
									<td class="td_title">
										<spring:message code="hrm.empinfo.CV_update_status.Z" /> <!--CV update status-->
									</td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="CV_UPDATE_STATUS" id="CV_UPDATE_STATUS" parentNo="90000302"
											cnpyID="${defaultCpny}"
											selected="${personalInfo.CV_UPDATE_STATUS}" limit="all" /></td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.FAM_BORNDATE" /><!--出生日期--></td>
									<td class="td_type" width="25%"><input name="DOB" id="DOB"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personalInfo.DOB}"  onBlur="age()" /></td>
									<td class="td_title"><spring:message code="hrm.empinfo.AGE" /><!--年龄--></td>
									<td class="td_type" width="25%">
										<input type="text" id="AGE" readonly="readonly"
										name="AGE" value="${personalInfo.AGE}"/>
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.ORIGIN.Z" /><!--出生地--></td>
									<td class="td_type" width="25%">
										<input type="text" id="ORIGIN" name="ORIGIN" value="${personalInfo.ORIGIN}" />
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.SEXCODE" /><!--性别--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="SEXCODE" id="SEXCODE" parentNo="1324"
											cnpyID="${defaultCpny}" selected="${personalInfo.SEX_CODE }"
											limit="all" /></td>
								</tr>
								<tr>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
									<td class="td_title"><spring:message code="hr.viewCondSql.title.ZUIZHONGXUEXIAO" /> <!--最终学校--></td>
									<td class="td_type" width="25%">${personalInfo.INSTITUTION_NAME}</td>
								</c:if>
									<td class="td_title"><spring:message code="hrm.empinfo.FINALLY_DEGREE_CODE" /> <!--最终学历--></td>
									<td class="td_type" width="25%">${personalInfo.DEGREE_CODE}</td>
									<td class="td_title"><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="NATIONALITY_CODE" id="NATIONALITY_CODE" parentNo="870"
											cnpyID="${defaultCpny}"
											selected="${personalInfo.NATIONALITY_CODE}" limit="all" /></td>
									<td class="td_title"><spring:message code="hrm.empinfo.NATION_CODE" /><!--民族--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="NATION_CODE" id="NATION_CODE"
											parentNo="210942" cnpyID="${defaultCpny}"
											selected="${personalInfo.NATION_CODE}" limit="all" />
									</td>
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class="td_title"><spring:message code="ess.empInfo.religion"/><!-- 宗教 --></td>
									<td class="td_type" width="25%">
										<input type="text" id="RELIGION" name="RELIGION" value="${personalInfo.RELIGION}">
									</td>
									</c:if>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.CELLPHONE" /><!--手机--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.CELLPHONE }" id="CELLPHONE"
										name="CELLPHONE"></td>
									<c:if test="${LoginUser.cpnyId eq 'HAE'}">
										<td class="td_title"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.EMAIL_SECOND}" id="EMAIL_SECOND" name="EMAIL_SECOND"></td>
									</c:if>
									<td class="td_title"><spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /><!--结婚状态--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="MARITAL_STATUS_CODE" id="MARITAL_STATUS_CODE"
											parentNo="1709" cnpyID="${defaultCpny}"
											selected="${personalInfo.MARITAL_STATUS_CODE}" limit="all" />
									</td>
									<td class="td_title"><spring:message code="hr.viewCondSql.title.JIEHUNRIQI" /><!--结婚日期--></td>
									<td class="td_type" width="25%"><input name="WEDDING_DATE" id="WEDDING_DATE"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personalInfo.WEDDING_DATE}" /></td>	
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">	
									<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.POLITY_NAME" /><!--政治面貌--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="POLITICAL_OUTLOOK" id="POLITICAL_OUTLOOK"
											parentNo="876" cnpyID="${defaultCpny}"
											selected="${personalInfo.POLITICAL_OUTLOOK}" limit="all" />
									</td>
									</c:if>
								</tr>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="EXIST_SINGLE" id="EXIST_SINGLE"
											parentNo="14013861" cnpyID="${defaultCpny}"
											selected="${personalInfo.EXIST_SINGLE}" limit="all" />
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.SING_ID }" id="SING_ID" name="SING_ID"></td>
									<td class="td_title"><spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.OFFICE_PHONE}" id="OFFICE_PHONE"
										name="OFFICE_PHONE"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.EMAIL}" id="EMAIL" name="EMAIL"></td>	
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.REG_PLACE }" id="REG_PLACE" name="REG_PLACE"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.FILE_LOCATION}" id="FILE_LOCATION" name="FILE_LOCATION"></td>
									<td class="td_title"><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入--></td>
									<td class="td_type" width="25%"><input name="FILE_ENTER" id="FILE_ENTER"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personalInfo.FILE_ENTER}" /></td>	
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出--></td>
									<td class="td_type" width="25%"><input name="FILE_OUT" id="FILE_OUT"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personalInfo.FILE_OUT}" /></td>	
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.HOME_PHONE}" id="HOME_PHONE"
										name="HOME_PHONE"></td>
									<td class="td_title"></td>
									<td class="td_type" width="25%"></td>
									<td class="td_title"></td>
									<td class="td_type" width="25%"></td>
									<td class="td_title"></td>
									<td class="td_type" width="25%"></td>
								</tr>
								</c:if>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="EXIST_SINGLE" id="EXIST_SINGLE"
											parentNo="14013861" cnpyID="${defaultCpny}"
											selected="${personalInfo.EXIST_SINGLE}" limit="all" />
									</td>
									<td class="td_title"><spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.HOME_PHONE}" id="HOME_PHONE"
										name="HOME_PHONE"></td>
									<td class="td_title"><spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.OFFICE_PHONE}" id="OFFICE_PHONE"
										name="OFFICE_PHONE"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z" /> <!--住宅区分--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="RESIDENTIAL_DISTINCTION" id="RESIDENTIAL_DISTINCTION"
											parentNo="14013865" cnpyID="${defaultCpny}"
											selected="${personalInfo.RESIDENTIAL_DISTINCTION}" limit="all" />
									</td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.SING_ID }" id="SING_ID" name="SING_ID"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.REG_PLACE }" id="REG_PLACE" name="REG_PLACE"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.EMAIL_SECOND}" id="EMAIL_SECOND" name="EMAIL_SECOND"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.EMAIL}" id="EMAIL" name="EMAIL"></td>
								</tr>
								<tr style="display:none">
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personalInfo.FILE_LOCATION}" id="FILE_LOCATION" name="FILE_LOCATION"></td>
									<td class="td_title"><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入--></td>
									<td class="td_type" width="25%"><input name="FILE_ENTER" id="FILE_ENTER"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personalInfo.FILE_ENTER}" /></td>	
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出--></td>
									<td class="td_type" width="25%"><input name="FILE_OUT" id="FILE_OUT"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personalInfo.FILE_OUT}" /></td>	
									<td class="td_title"></td>
									<td class="td_type" width="25%"></td>
								</tr>
								</c:if>
			</table>
			</br>
			<div id="createTable" width="100%"></div>
			<input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<!--修改--><spring:message code="ess.empInfo.modify" />
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
<!-- </div> -->
</div>