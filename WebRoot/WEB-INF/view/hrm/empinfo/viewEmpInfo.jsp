<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!-- jjy maskedinput -->
<script src="/resources/js/jquery/jquery.maskedinput.js"
	type="text/javascript">
</script>
<script type="text/javascript">
$(function() {
	var date_start = "${personInfo.DATE_STARTED}";
	var time = "${personInfo.ATTENDANCETIME}";
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
	var totaltime = year + "<spring:message code="hrm.empinfo.YEAR"/>" + month + "<spring:message code="hrm.empinfo.MONTH"/>(from " + date_start + ")";
	$('#ATTENDANCETIME').html(totaltime);
	if ("${personInfo.OURER_EMP_ISNOT }" == "Y") {
		$('#empisnot').attr('checked', 'checked');
		$('#OURER_EMP_ISNOT').attr('value', 'Y');
	} else {
		$('#empisnot').removeAttr('checked');
		$('#OURER_EMP_ISNOT').attr('value', 'N');
	}
	var str = gerenxinxiTitle();
	$('#gerenxinxiTitle').html(str);
});<%--页面加载完更新采用路径--%>
	
function validateCallbackEditEduPerInfo(form, callback) {

	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>',{
			okCall:function(){
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


function fangdajing1(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY1').val()));
	$('#fangda1').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEmpInfo&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda1').click();
}

function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>",//确定要保存吗？
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

function gerenxinxiTitle(){
	var gg="${personInfo.LOCAL_NAME }";
	var hh="${personInfo.EMPID }";
	var ll="${personInfo.POST_GRADE_NO_NAME}";
	var mm="${personInfo.RANK_STATISTICS_NAME}";
	var nn="${personInfo.COST_CENTER}";
	var oo="${personInfo.EMP_OFFICE_NAME }";
	var str="";
	if(gg!=''){
		str=str+gg;
	}
	if(hh!=''){
		str=str+" / "+hh;
	}
	if(ll!=''){
		str=str+" / "+ll;
	}
	if(mm!=''){
		str=str+"("+mm+")";
	}
	if(nn!=''){
		str=str+" / "+nn;
	}
	if(oo!=''){
		str=str+" / "+oo;
	}
	return str;
}
</script>
<div class="pageContent" style="height: 576px; overflow-y: auto">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="user_table">
		<tr>
			<td class="td_title" style="width: 10%">
				<!-- 社号/姓名： -->
				<spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
			</td>
			<td class="td_type" style="width: 10%">
				<input type="text" name="seach_KEY1" id="seach_KEY1" value="${KEY}"
					onkeydown="javascript:if(event.keyCode == 13)fangdajing1('onkeyup');" />
			</td>
			<td class="td_type">
				<a class="btnLook" id="fangda1" onclick="fangdajing1()"
					href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEmpInfo"
					lookupGroup="person"> </a>
				<span style="margin-left: 50px;" id="gerenxinxiTitle"></span>
			</td>

		</tr>
	</table>
	<div class="formBar">

		<ul class="toolBar">
			<li>
				<a class="buttonActive"
					onclick="validateAddResumeInfoCallback('editEmpInfo',navTabAjaxDone)"
					href="#">
						<span><spring:message code="button.sys.affirm.save" /><!--保存--></span> </a>
			</li>
		</ul>
	</div>
	<form id="editEmpInfo" method="post" action="/hrm/empinfo/editEmpInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackEditEduPerInfo(this,navTab);">
		<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}">
		<div class="panel collapse">
			<h1>
				<spring:message
					code="hr.viewPersonalInfo.title.PERSONAL_FOUNDATION_INFORMATION" />
				<!--个人基础信息-->
			</h1>
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="user_table">
								<tr>
									<td class="td_title">
										<spring:message code="hrm.empinfo.FAM_NAME" /><!--姓名-->
									</td>
									<td class="td_type" width="25%">
										${personInfo.LOCAL_NAME}
									</td>
									<td class="td_title">
										<spring:message code="hrm.empinfo.empid" /><!--社号-->
									</td>
									<td class="td_type" width="25%">
										${personInfo.EMPID }
										<input type="hidden" name="PERSON_ID"
											value="${personInfo.PERSON_ID }">
										<a id="onck" name="onck"
											href="/hrm/empinfo/viewEmpIdList?pageNum=1"
											lookupGroup="person" width="950"></a>
									</td>
									<td class="td_title">
										<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
										<!--部门  -->
									</td>
									<td class="td_type" width="25%">
									${personInfo.DEPTNO_NAME }</td>
										
								</tr>
							  	<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!--员工类型--></td>
									<td class="td_type" width="25%">${personInfo.EMP_TYPE_CODE_NAME }</td>
									<td class="td_title"><spring:message code="ess.infoApply.renzhizhuangtai" /> <!--任职状态--></td>
									<td class="td_type" width="25%">${personInfo.EMP_OFFICE_NAME }</td>
									<td class="td_title"><spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL" /><!--成本中心--></td>
									<td class="td_type" width="25%">${personInfo.COST_CENTER }</td>
								</tr>
								<tr>
									<td class="td_title"><spring:message code="hrm.empinfo.DATE_STARTED" /> <!--入社日期--></td>
									<td class="td_type" width="25%">${personInfo.DATE_STARTED }</td>
									<td class="td_title"><spring:message code="hrm.contract.Rank" /><!--职级--></td>
									<td class="td_type" width="25%">${personInfo.POST_GRADE_NO_NAME }</td>
									<td class="td_title"><spring:message code="hr.enpinfo.title.EMP.PROBATION_END_DATE" /><!--试用期结束日期--></td>
									<td class="td_type" width="25%">
										<input name="END_PROBATION_DATE" id="END_PROBATION_DATE"
											class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
											value="${personInfo.END_PROBATION_DATE }" /></input>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!--<div class="panel collapse">
			<h1>
				<spring:message
					code="hr.viewPersonalInfo.title.PERSONAL_INFORMATION" />
				个人信息
				 개인기초정보 
			</h1>

			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="user_table">
								<tr>
								<td class="td_title">
										<spring:message code="hrm.empinfo.Service_time" />在职时间
									</td>
									<td class="td_type" width="35%">${personInfo.INCUMBENCY}</td>
								<td class="td_title">
										<spring:message code="hrm.empinfo.END_PROBATION_DATE" />试用期结束日期
									</td>
									<td class="td_type" width="35%">
										<input name="END_PROBATION_DATE" id="END_PROBATION_DATE"
											class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"
											value="${personInfo.END_PROBATION_DATE }" />
									</td>
								</tr>
								<tr style="display:none">  
									<td class="td_title">
										<spring:message code="hrm.empinfo.START_CONTRACT_DATE" />当前合同日期
									</td>
									<td class="td_type" width="35%">
										${personInfo.START_CONTRACT_DATE }
									</td>
									<td class="td_title">
										<spring:message code="hrm.empinfo.END_CONTRACT_DATE" />合同到期日
									</td>
									<td class="td_type" width="35%">
										<input type="hidden" name="CONTRACT_NO" value="${personInfo.CONTRACT_NO }">
										<input name="END_CONTRACT_DATE" id="END_CONTRACT_DATE"
											class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"
											value="${personInfo.END_CONTRACT_DATE }" />
									</td>
								</tr>
								<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
								<tr>
								<td class="td_title">
									<spring:message code="hrm.recruitManage.DATE_GROUP_ENTRY"/> 集团入职日期 
								</td>
								<td class="td_type" width="35%">
										<input type="hidden" name="CONTRACT_NO" value="${personInfo.CONTRACT_NO }">
										<input name="DATE_GROUP_ENTRY" id="DATE_GROUP_ENTRY"
											class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"
											value="${personInfo.DATE_GROUP_ENTRY}" />
								</td>
								<td class="td_title"></td>
								<td class="td_type" width="35%"></td>
								</tr>
								</c:if>
								
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		--><div class="panel collapse">
			<h1>

			</h1>

			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="user_table">
								<tr>
									<td class="td_title">
										<spring:message code="hrm.empinfo.UPDATED_BY" /><!--变更者-->
									</td>
									<td class="td_type" width="35%">
										${personInfo.UPDATED_BY }&nbsp&nbsp${personInfo.UPDATED_IP }
									</td>
									<td class="td_title">
										<spring:message code="hrm.empinfo.UPDATE_DATE" /><!--变更时间-->
									</td>
									<td class="td_type" width="35%">
										${personInfo.UPDATE_DATE }
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

			</div>
			</td>
			</tr>
			</table>
		</div>
	</form>
</div>
