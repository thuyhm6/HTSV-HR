<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!-- jjy maskedinput -->
<script src="/resources/js/jquery/jquery.maskedinput.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		var time = "${personInfo.AGE}";
		var year = parseInt(time) / 365;
		var day = parseInt(time) % 365;
		year = Math.floor(year);
		if (year < 1) {
			year = 0;
		}
		var month = day / 30;
		month = Math.floor(month);
		if (month < 1) {
			month = 0;
		}
	//	$('#ageYear').html(year);
	//	$('#ageMonth').html(month);

		var str = PersonalTitle();
		$('#personalTitle').html(str);

		if ("${personInfo.IS_MBO}" == "Y") {
			$('#ismbo').attr('checked', 'checked');
			$('#IS_MBO').attr('value', 'Y');
		} else {
			$('#ismbo').removeAttr('checked', 'checked');
			$('#IS_MBO').attr('value', 'N');
		}
		$("#IDCARD_EXPIRE_DATE",navTab.getCurrentPanel()).click(function(){
			transCodeChange();
		});
		$("#final1",navTab.getCurrentPanel()).click(function(){
			transCodeChange();
		});

	});
	function transCodeChange(){
		var IDCARD_EXPIRE_DATE = $("#IDCARD_EXPIRE_DATE",navTab.getCurrentPanel()).val();
		var LONG_TERM = $("#final1",navTab.getCurrentPanel()).val();
		changeDisabled();
		if(IDCARD_EXPIRE_DATE != ''){
			addDisabled("final1");
		} 
		if(LONG_TERM == "Y"){
			addDisabled("IDCARD_EXPIRE_DATE");
		}
	}
	function addDisabled(type){
		if(type == 'final1'){
			$('#final1',navTab.getCurrentPanel()).attr('disabled',true);
		}
		if(type == 'IDCARD_EXPIRE_DATE'){
			$('#IDCARD_EXPIRE_DATE',navTab.getCurrentPanel()).attr('disabled',true);
		}
	}
	function changeDisabled(){
		$('#IDCARD_EXPIRE_DATE',navTab.getCurrentPanel()).attr('disabled',false);
		$('#final1',navTab.getCurrentPanel()).attr('disabled',false);
	}
	function final1degree() {
		var check = $('#final1',navTab.getCurrentPanel()).prop('checked');
		if (check == true) {
			$('#final1',navTab.getCurrentPanel()).attr('value', 'Y');
			$('#LONG_TERM',navTab.getCurrentPanel()).attr('value', 'Y');
		} else {
			$('#final1',navTab.getCurrentPanel()).attr('value', 'N');
			$('#LONG_TERM',navTab.getCurrentPanel()).attr('value', 'N');
		}
	}
<%--页面加载完更新采用路径--%>
	function validateCallbackEditEduPerInfo(form, callback) {

		var $form = $(form);

		if (!$form.valid()) {
			return false;
		}
		alertMsg
				.confirm(
						'<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>',
						{
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
	function fangdajing2(flag) {
		var name = encodeURI(encodeURI($('#seach_KEY2').val()));
		$('#fangda2')
				.attr(
						'href',
						'/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewHrPersonalInfo&seach_KEY='
								+ name);
		if (flag == 'onkeyup')
			$('#fangda2').click();
	}

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

	function changeolddepart() {
		var check = $('#olddepart').prop('checked');
		if (check == true) {
			$('#OLD_DEPARTMENT').attr('value', 'YES');
		} else {
			$('#OLD_DEPARTMENT').attr('value', 'NO');
		}
	}

	function changemarriage() {
		var check = $('#marriage').prop('checked');
		if (check == true) {
			$('#MARRIAGE_SUPPLEMENT').attr('value', 'YES');
		} else {
			$('#MARRIAGE_SUPPLEMENT').attr('value', 'NO');
		}
	}
	function PersonalTitle() {
		var gg = "${personInfo.LOCAL_NAME }";
		var hh = "${personInfo.EMPID }";
		var ll = "${personInfo.POST_GRADE_NO_NAME}";
		var mm = "${personInfo.RANK_STATISTICS_NAME}";
		var nn = "${personInfo.COST_CENTER}";
		var oo = "${personInfo.EMP_OFFICE_NAME }";
		var str = "";
		if (gg != '') {
			str = str + gg;
		}
		if (hh != '') {
			str = str + " / " + hh;
		}
		if (ll != '') {
			str = str + " / " + ll;
		}
		if (mm != '') {
			str = str + "(" + mm + ")";
		}
		if (nn != '') {
			str = str + " / " + nn;
		}
		if (oo != '') {
			str = str + " / " + oo;
		}
		return str;
	}
	function changeIS_MBO() {
		var ismbo = $('#ismbo').prop('checked');
		if (ismbo == true) {
			$('#IS_MBO').attr('value', 'Y');
		} else {
			$('#IS_MBO').attr('value', 'N');
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
	function finalPERSONAL() {
		var check = $('#finalARMY').prop('checked');
		if (check == true) {
			$('#ARMY_OR_NOT').attr('value', 'Y');
		} else {
			$('#ARMY_OR_NOT').attr('value', 'N');
		}
		var check = $('#finalOBSTACLE').prop('checked');
		if (check == true) {
			$('#OBSTACLE_OR_NOT').attr('value', 'Y');
		} else {
			$('#OBSTACLE_OR_NOT').attr('value', 'N');
		}
	}
</script>
<div class="pageContent">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="user_table">
		<tr>
			<td class="td_title" style="width: 10%">
				<!-- 社号/姓名： --> <spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
			</td>
			<td class="td_type" style="width: 10%"><input type="text"
				name="seach_KEY2" id="seach_KEY2" value="${KEY}"
				onkeydown="javascript:if(event.keyCode == 13)fangdajing2('onkeyup');" />
			</td>
			<td class="td_type"><a class="btnLook" id="fangda2"
				onclick="fangdajing2()"
				href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewHrPersonalInfo"
				lookupGroup="person"> </a> <span style="margin-left: 50px;"
				id="personalTitle"></span></td>
		</tr>
	</table>
	<div class="formBar">

		<ul class="toolBar">
			<li><a class="buttonActive"
				onclick="validateAddResumeInfoCallback('editHrPersonInfo',navTabAjaxDone)"
				href="#"><span><spring:message code="button.sys.affirm.save" /><!--保存--></span></a></li>
		</ul>
	</div>
	<form id="editHrPersonInfo" method="post"
		action="/hrm/empinfo/editHrPersonInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackEditEduPerInfo(this,navTab);">
		<input TYPE="hidden" id="PERSON_ID" NAME="PERSON_ID"
			VALUE="${personInfo.PERSON_ID}"> <input TYPE="hidden"
			NAME="isEssSystem" VALUE="${isEssSystem}">
		<div class="panel collapse">
			<h1><spring:message code="hrm.empinfo.Basic_matters" /><!--基本事项--></h1>
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="user_table">
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.LOCAL_NAME" /><!--中文姓名--></td>
									<td class="td_type" width="25%"><input type="text"
										class="required" value="${personInfo.LOCAL_NAME }"
										id="LOCAL_NAME" name="LOCAL_NAME"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.ENGLISH_NAME" /><!--英文姓名--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.ENGLISH_NAME }" id="ENGLISH_NAME"
										name="ENGLISH_NAME"></td>
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class="td_title"><spring:message code="hrm.empinfo.ARMY_OR_NOT.Z" /><!--参军与否--></td>
									<td width="25%" class="td_type">
										<c:if test="${personInfo.ARMY_OR_NOT=='Y' }">
											<input type="checkbox" checked="finalARMY" id="finalARMY" onclick="finalPERSONAL()">
											<input type="hidden" name='ARMY_OR_NOT' id='ARMY_OR_NOT' value="Y">
										</c:if> 
										<c:if test="${personInfo.ARMY_OR_NOT !='Y' }">
											<input type="checkbox" id="finalARMY" onclick="finalPERSONAL()">
											<input type="hidden" id="ARMY_OR_NOT" name="ARMY_OR_NOT" value="N">
										</c:if>
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.OBSTACLE_OR_NOT.Z" /><!--障碍与否--></td>
									<td class="td_type" width="25%">
										<c:if test="${personInfo.OBSTACLE_OR_NOT=='Y' }">
											<input type="checkbox" checked="finalOBSTACLE" id="finalOBSTACLE" onclick="finalPERSONAL()">
											<input type="hidden" name='OBSTACLE_OR_NOT' id='OBSTACLE_OR_NOT' value="Y">
										</c:if> 
										<c:if test="${personInfo.OBSTACLE_OR_NOT !='Y' }">
											<input type="checkbox" id="finalOBSTACLE" onclick="finalPERSONAL()">
											<input type="hidden" id="OBSTACLE_OR_NOT" name="OBSTACLE_OR_NOT" value="N">
										</c:if>
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
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.IDCARD_NO }" id="IDCARD_NO"
										name="IDCARD_NO"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.award_date" /><!--获证日期--></td>
									<td class="td_type" width="25%"><input name="IDCARD_START_DATE" id="IDCARD_START_DATE"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personInfo.IDCARD_START_DATE}"/></td>
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">	
										<td class="td_title"><spring:message code="hrm.empinfo.QIANFA_JIGUAN.Z" /><!-- 签发机构 --></td>
									</c:if>
									<c:if test="${LoginUser.cpnyId eq 'HAE'}">
										<td class="td_title"><spring:message code="hr.viewCredential.title.CREDENTIAL_SOURCE" /><!-- 签发地 --></td>
									</c:if>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.ISSUING_AUTHORITY }" id="ISSUING_AUTHORITY"
										name="ISSUING_AUTHORITY"></td>
									<td class="td_title">
										<spring:message code="hrm.empinfo.CV_update_status.Z" /> <!--CV update status-->
									</td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="CV_UPDATE_STATUS" id="CV_UPDATE_STATUS" parentNo="90000302"
											cnpyID="${defaultCpny}"
											selected="${personInfo.CV_UPDATE_STATUS}" limit="all" /></td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.FAM_BORNDATE" /><!--出生日期--></td>
									<td class="td_type" width="25%"><input name="DOB" id="DOB"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personInfo.DOB}"  onBlur="age()" /></td>
									<td class="td_title"><spring:message code="hrm.empinfo.AGE" /><!--年龄--></td>
									<td class="td_type" width="25%">
										<input type="text" id="AGE" readonly="readonly"
										name="AGE" value="${personInfo.AGE}"/>
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.ORIGIN.Z" /><!--出生地--></td>
									<td class="td_type" width="25%">
										<input type="text" id="ORIGIN" name="ORIGIN" value="${personInfo.ORIGIN}" />
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.SEXCODE" /><!--性别--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="SEXCODE" id="SEXCODE" parentNo="1324"
											cnpyID="${defaultCpny}" selected="${personInfo.SEXCODE }"
											limit="all" /></td>
								</tr>
								<tr>
									<c:if test="${LoginUser.cpnyId eq 'HAE'}">
										<td class="td_title"><spring:message code="hr.viewCondSql.title.ZUIZHONGXUEXIAO" /> <!--最终学校--></td>
										<td class="td_type" width="25%">
											<input type="text" readonly="readonly" value="${personInfo.INSTITUTION_NAME}"/>
										</td>
									</c:if>
									<td class="td_title"><spring:message code="hrm.empinfo.FINALLY_DEGREE_CODE" /> <!--最终学历--></td>
									<td class="td_type" width="25%">
										<input type="text" readonly="readonly" value="${personInfo.DEGREE_CODE}"/>
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.NATIONALITY_CODE" /><!--国籍--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="NATIONALITY_CODE" id="NATIONALITY_CODE" parentNo="870"
											cnpyID="${defaultCpny}"
											selected="${personInfo.NATIONALITY_CODE}" limit="all" /></td>
									<td class="td_title"><spring:message code="hrm.empinfo.NATION_CODE" /><!--民族--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="NATION_CODE" id="NATION_CODE"
											parentNo="210942" cnpyID="${defaultCpny}"
											selected="${personInfo.NATION_CODE}" limit="all" />
									</td>
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class="td_title"><spring:message code="ess.empInfo.religion"/><!-- 宗教 --></td>
									<td class="td_type" width="25%">
										<input type="text" id="RELIGION" name="RELIGION" value="${personInfo.RELIGION}">
									</td>
									</c:if>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.CELLPHONE" /><!--手机--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.CELLPHONE }" id="CELLPHONE"
										name="CELLPHONE"></td>
									<c:if test="${LoginUser.cpnyId eq 'HAE'}">
										<td class="td_title"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></td>
										<td class="td_type" width="25%"><input type="text"
										value="${personInfo.EMAIL_SECOND}" id="EMAIL_SECOND" name="EMAIL_SECOND"></td>
									</c:if>
									<td class="td_title"><spring:message code="hrm.empinfo.MARITAL_STATUS_NAME" /><!--结婚状态--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="MARITAL_STATUS_CODE" id="MARITAL_STATUS_CODE"
											parentNo="1709" cnpyID="${defaultCpny}"
											selected="${personInfo.MARITAL_STATUS_CODE}" limit="all" />
									</td>
									<td class="td_title"><spring:message code="hr.viewCondSql.title.JIEHUNRIQI" /><!--结婚日期--></td>
									<td class="td_type" width="25%"><input name="WEDDING_DATE" id="WEDDING_DATE"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personInfo.WEDDING_DATE}" /></td>
									<c:if test="${LoginUser.cpnyId eq 'HTSV'}">	
									<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.POLITY_NAME" /><!--政治面貌--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="POLITICAL_OUTLOOK" id="POLITICAL_OUTLOOK"
											parentNo="876" cnpyID="${defaultCpny}"
											selected="${personInfo.POLITICAL_OUTLOOK}" limit="all" />
									</td>
									</c:if>
								</tr>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="EXIST_SINGLE" id="EXIST_SINGLE"
											parentNo="14013861" cnpyID="${defaultCpny}"
											selected="${personInfo.EXIST_SINGLE}" limit="all" />
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.SING_ID }" id="SING_ID" name="SING_ID"></td>
									<td class="td_title"><spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.OFFICE_PHONE}" id="OFFICE_PHONE"
										name="OFFICE_PHONE"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.EMAIL}" id="EMAIL" name="EMAIL"></td>	
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.REG_PLACE }" id="REG_PLACE" name="REG_PLACE"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.FILE_LOCATION}" id="FILE_LOCATION" name="FILE_LOCATION"></td>
									<td class="td_title"><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入--></td>
									<td class="td_type" width="25%"><input name="FILE_ENTER" id="FILE_ENTER"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personInfo.FILE_ENTER}" /></td>	
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出--></td>
									<td class="td_type" width="25%"><input name="FILE_OUT" id="FILE_OUT"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personInfo.FILE_OUT}" /></td>	
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.HOME_PHONE}" id="HOME_PHONE" name="HOME_PHONE"></td>
									<td class="td_title"><spring:message code="ess.empInfo.height" /><!--身高--></td>
									<td class="td_type" width="25%">
										<input type="text" value="${personInfo.HEIGHT }" id="HEIGHT" name="HEIGHT">
									</td>
									<td class="td_title"><spring:message code="ess.empInfo.weight" /><!--体重--></td>
									<td class="td_type" width="25%">
										<input type="text" value="${personInfo.WEIGHT }" id="WEIGHT" name="WEIGHT">
									</td>
									<td class="td_title"><spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z" /> <!--住宅区分--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="RESIDENTIAL_DISTINCTION" id="RESIDENTIAL_DISTINCTION"
											parentNo="14013865" cnpyID="${defaultCpny}"
											selected="${personInfo.RESIDENTIAL_DISTINCTION}" limit="all" />
									</td>
								</tr>
								</c:if>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem.Z" /><!--EagLem 与否--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="EXIST_SINGLE" id="EXIST_SINGLE"
											parentNo="14013861" cnpyID="${defaultCpny}"
											selected="${personInfo.EXIST_SINGLE}" limit="all" />
									</td>
									<td class="td_title"><spring:message code="hr.viewHire.title.HOME_PHONE" /> <!-- 家庭电话 --></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.HOME_PHONE}" id="HOME_PHONE"
										name="HOME_PHONE"></td>
									<td class="td_title"><spring:message code="sys.basicMaint.title.companyTelPhoneNo" /> <!--公司电话--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.OFFICE_PHONE}" id="OFFICE_PHONE"
										name="OFFICE_PHONE"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z" /> <!--住宅区分--></td>
									<td class="td_type" width="25%"><ait:SelectSyCodeByCpnyID
											name="RESIDENTIAL_DISTINCTION" id="RESIDENTIAL_DISTINCTION"
											parentNo="14013865" cnpyID="${defaultCpny}"
											selected="${personInfo.RESIDENTIAL_DISTINCTION}" limit="all" />
									</td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EagLem_ID.Z" /><!--EagLem ID--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.SING_ID }" id="SING_ID" name="SING_ID"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.HUJIDIZHI" /><!--户口所在地--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.REG_PLACE }" id="REG_PLACE" name="REG_PLACE"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.GEREN_EMAIL.Z" /><!--个人邮箱--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.EMAIL_SECOND}" id="EMAIL_SECOND" name="EMAIL_SECOND"></td>
									<td class="td_title"><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z" /><!--公司邮箱--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.EMAIL}" id="EMAIL" name="EMAIL"></td>
								</tr>
								<tr style="display:none">
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_LOCATION.Z" /><!--档案所在地--></td>
									<td class="td_type" width="25%"><input type="text"
										value="${personInfo.FILE_LOCATION}" id="FILE_LOCATION" name="FILE_LOCATION"></td>
									<td class="td_title"><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME" /><!--档案转入--></td>
									<td class="td_type" width="25%"><input name="FILE_ENTER" id="FILE_ENTER"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personInfo.FILE_ENTER}" /></td>	
									<td class="td_title"><spring:message code="hrm.empinfo.FILE_OUT.Z" /><!--档案转出--></td>
									<td class="td_type" width="25%"><input name="FILE_OUT" id="FILE_OUT"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
										value="${personInfo.FILE_OUT}" /></td>	
									<td class="td_title"></td>
									<td class="td_type" width="25%"></td>
								</tr>
								</c:if>
				</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="panel collapse">
			<h1></h1>

			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="user_table">
								<tr>
									<td class="td_title" width="15%"><spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者--></td>
									<td class="td_type" width="35%">${personInfo.UPDATED_BY
										}&nbsp&nbsp${personInfo.UPDATED_IP }</td>
									<td class="td_title" width="15%"><spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间--></td>
									<td class="td_type" width="35%">${personInfo.UPDATE_DATE }</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</div>
		</div>
		<!-- ----------------------------------按钮------------------------------------------------ -->
	</form>
</div>
