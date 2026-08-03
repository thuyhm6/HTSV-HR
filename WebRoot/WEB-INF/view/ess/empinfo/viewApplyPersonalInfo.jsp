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
/* $(function(){
	var name =$("[name=NATIONALITY_NAME]").val();
	alert(name);
}); */
</script>

<div class="pageContent">
	<form id="updatePersonal" method="post"
		action="/ess/empinfo/updatePersonal"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">


				<input type="hidden" name="APPLY_TYPE" value="${2}" />
				<input type="hidden" name="PERSON_ID" value="${PERSON_ID}" />

								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.LOCAL_NAME" /><!--中文姓名--></td>
									<td class="td_type" width="25%">${personalInfo.LOCAL_NAME }</td>
									<td class="td_title"><spring:message code="hrm.empinfo.ENGLISH_NAME" /><!--英文姓名--></td>
									<td class="td_type" width="25%">${personalInfo.ENGLISH_NAME }</td>
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class="td_title"><spring:message code="hrm.empinfo.ARMY_OR_NOT.Z" /><!--参军与否--></td>
									<td width="25%" class="td_type">
										<input type="checkbox" <c:if test="${personalInfo.ARMY_OR_NOT=='Y' }"> checked="checked"</c:if> >
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.OBSTACLE_OR_NOT.Z" /><!--障碍与否--></td>
									<td class="td_type" width="25%">
										<input type="checkbox" <c:if test="${personalInfo.OBSTACLE_OR_NOT=='Y' }"> checked="checked"</c:if> >
									</td>
									</c:if>
									<c:if test="${LoginUser.cpnyId eq 'HAE'}">
										<td class="td_title"></td>
										<td class="td_type" width="25%"></td>
										<td class="td_title"></td>
										<td class="td_type" width="25%"></td>
									</c:if>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.IDCARD_NO" /><!-- 身份证号 --></td>
									<td class="td_type" width="25%">
										${personalInfo.IDCARD_NO }
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.award_date" /><!--获证日期--></td>
									<td class="td_type" width="25%">
										${personalInfo.IDCARD_START_DATE}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.QIANFA_JIGUAN.Z" /><!-- 签发机构 --></td>
									<td class="td_type" width="25%">
										${personalInfo.ISSUING_AUTHORITY }
									</td>
									<td class="td_title">
										<spring:message code="hrm.empinfo.CV_update_status.Z" /> <!--CV update status-->
									</td>
									<td class="td_type" width="25%">
										${personalInfo.CV_UPDATE_STATUS_NAME}
									</td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.FAM_BORNDATE" /><!--出生日期--></td>
									<td class="td_type" width="25%">
										${personalInfo.DOB}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.AGE" /><!--年龄--></td>
									<td class="td_type" width="25%">
										${personalInfo.AGE}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.ORIGIN.Z" /><!--出生地--></td>
									<td class="td_type" width="25%">
										${personalInfo.ORIGIN}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.SEXCODE" /><!--性别--></td>
									<td class="td_type" width="25%">
										${personalInfo.SEXCODE_NAME }
									</td>
								</tr>
								<tr>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
									<td class="td_title"><spring:message code="hr.viewCondSql.title.ZUIZHONGXUEXIAO" /> <!--最终学校--></td>
									<td class="td_type" width="25%">${personalInfo.INSTITUTION_NAME}</td>
								</c:if>
									<td class="td_title"><spring:message code="hrm.empinfo.FINALLY_DEGREE_CODE" /> <!--最终学历--></td>
									<td class="td_type" width="25%">${personalInfo.DEGREE_CODE}</td>
									<td class="td_title"><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍--></td>
									<td class="td_type" width="25%">
										${personalInfo.NATIONALITY_NAME}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.NATION_CODE" /><!--民族--></td>
									<td class="td_type" width="25%">
										${personalInfo.NATION_NAME}
									</td>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class="td_title"><spring:message code="ess.empInfo.religion"/><!-- 宗教 --></td>
									<td class="td_type" width="25%">
										${personalInfo.RELIGION}
									</td>
								</c:if>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.CELLPHONE" /><!--手机--></td>
									<td class="td_type" width="25%">
										${personalInfo.CELLPHONE }
									</td>
									<c:if test="${LoginUser.cpnyId eq 'HAE'}">
									<td class="td_title"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></td>
									<td class="td_type" width="25%">
										${personalInfo.EMAIL_SECOND}
									</td>
									</c:if>
									<td class="td_title"><spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /><!--结婚状态--></td>
									<td class="td_type" width="25%">
										${personalInfo.MARITAL_STATUS_NAME}
									</td>
									<td class="td_title"><spring:message code="hr.viewCondSql.title.JIEHUNRIQI" /><!--结婚日期--></td>
									<td class="td_type" width="25%">
										${personalInfo.WEDDING_DATE}
									</td>	
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.POLITY_NAME" /><!--政治面貌--></td>
									<td class="td_type" width="25%">
										${personalInfo.POLITICAL_OUTLOOK_NAME}
									</td>
									</c:if>
								</tr>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否--></td>
									<td class="td_type" width="25%">
										${personalInfo.EXIST_SINGLE_NAME}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></td>
									<td class="td_type" width="25%">
										${personalInfo.SING_ID }
									</td>
									<td class="td_title"><spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话--></td>
									<td class="td_type" width="25%">
										${personalInfo.OFFICE_PHONE}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱--></td>
									<td class="td_type" width="25%">
										${personalInfo.EMAIL}
									</td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地--></td>
									<td class="td_type" width="25%">
										${personalInfo.REG_PLACE }
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地--></td>
									<td class="td_type" width="25%">
										${personalInfo.FILE_LOCATION}
									</td>
									<td class="td_title"><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入--></td>
									<td class="td_type" width="25%">
										${personalInfo.FILE_ENTER}
									</td>	
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出--></td>
									<td class="td_type" width="25%">
										${personalInfo.FILE_OUT}
									</td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></td>
									<td class="td_type" width="25%">
										${personalInfo.HOME_PHONE}
									</td>
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
									<td class="td_type" width="25%">
										${personalInfo.EXIST_SINGLE_NAME}
									</td>
									<td class="td_title"><spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></td>
									<td class="td_type" width="25%">
										${personalInfo.HOME_PHONE}
									</td>
									<td class="td_title"><spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话--></td>
									<td class="td_type" width="25%">
										${personalInfo.OFFICE_PHONE}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z" /> <!--住宅区分--></td>
									<td class="td_type" width="25%">
										${personalInfo.RESIDENTIAL_DISTINCTION_NAME}
									</td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></td>
									<td class="td_type" width="25%">
										${personalInfo.SING_ID }
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地--></td>
									<td class="td_type" width="25%">
										${personalInfo.REG_PLACE }
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></td>
									<td class="td_type" width="25%">
										${personalInfo.EMAIL_SECOND}
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱--></td>
									<td class="td_type" width="25%">
										${personalInfo.EMAIL}
									</td>
								</tr>
								<tr style="display:none">
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地--></td>
									<td class="td_type" width="25%">
										${personalInfo.FILE_LOCATION}
									</td>
									<td class="td_title"><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入--></td>
									<td class="td_type" width="25%">
										${personalInfo.FILE_ENTER}
									</td>	
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出--></td>
									<td class="td_type" width="25%">
										${personalInfo.FILE_OUT}
									</td>	
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
		</div>
	</form>
</div>

