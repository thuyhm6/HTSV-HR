<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
function navTabSearch_changeUser(form) {
	if ($("#changeUser").attr("action") == ''
			|| $("#changeUser").attr("action") == null) {
		return false;
	}
}

function magnifier_changeUser(flag) {

	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var scheduleNo = $('#PAY_SCHEDULE_NO', navTab.getCurrentPanel()).val();
	var refreshUrl = '/ess/change/changeUser?pawer=1';
	var refreshMenuCode = 'ess3601';
	var refreshMenuName = encodeURI(encodeURI('<spring:message code="ess.title.SHIYONGZHEBIANGENG"/>'));//使用者变更
	//$('#searchPop',navTab.getCurrent())
	$("#magnifier_changeUser", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=all&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#magnifier_changeUser", navTab.getCurrentPanel()).click();
}

function submitForm(){
	$("#changeUser").attr("action","/ess/change/in");
	$("#changeUser").submit();
 	
}
 
</script>
<style type="text/css">
</style>
<div class="pageContent">
	<%--
function(){
		
		location.href="/ess/change/home_partner";
		}

	--%>
	<table class="user_table" width="100%">
		<tr>
			<td class="td_title" width="25%">
				<!-- 姓名 --><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
			</td>
			<td class="td_type" width="25%">
				${personalInfo.LOCAL_NAME}
			</td>
			<td class="td_title" width="25%">
				<!-- 员工号 --><spring:message code="ess.infoApply.EMP_ID" />
			</td>
			<td class="td_type" width="25%">
				${personalInfo.EMPID}
			</td>

		</tr>
		<tr>
			<td class="td_title">
				<!-- 部门 --><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName" />
			</td>
			<td class="td_type">
				${personalInfo.DEPTNAME}
			</td>
			<td class="td_title">
				<!-- 等级名 --><spring:message code="ess.title.DENGJIMING" />
			</td>
			<td class="td_type">
				${personalInfo.POST_GRADE_NO}
			</td>

		</tr>

	</table>
</div>
<div class="pageHeader">

	<form onsubmit=" return navTabSearch_changeUser(this)" id="changeUser"
		name="viewEntryInfoList" onkeydown="false">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">

				<input type="hidden" name="passwords"
					value="${personalInfo.PASSWORD}">
				<input type="hidden" name="usernames"
					value="${personalInfo.USER_NAME}">
				<input type="hidden" name="COMPANY_IDS"
					value="${personalInfo.CPNY_ID}">
					<input type="hidden" name="PEROSN_ID_ID"
					value="${personalInfo.PERSON_ID}">
				<tr>

					
					<td class="td_title" style="width: 10%">
						<!-- 工号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td class="td_type" style="width: 10%">


						<input type="text" name="seach_KEY" id="seach_KEY"
							value="${personalInfo.LOCAL_NAME}"
							onkeydown="javascript:if(event.keyCode == 13)magnifier_changeUser('onkeyup');" />



					</td>

					<td class="td_type">
						<a class="btnLook" id="magnifier_changeUser"
							onclick="magnifier_changeUser()" href="" lookupGroup="person">
						</a>
						<span style="margin-left: 50px;" id="title_changeUser">${LOCAL_TITLE}</span>
					</td>
					<td>
						<input type="hidden" name="empInfoShow" value="${empInfoShow }" />
						${empInfoShow }
					</td>
				</tr>

			</table>



			<div class="subBar">
				<ul>


					<li>
						<a class="button" onclick="submitForm();"><span><!-- 使用者变更 -->
						<spring:message
							code="ess.title.SHIYONGZHEBIANGENG" /></span> </a>
					</li>
				</ul>
			</div>



		</div>
	</form>
</div>
