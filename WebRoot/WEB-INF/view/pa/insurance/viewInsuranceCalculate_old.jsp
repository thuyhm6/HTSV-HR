<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
	$(function() {
		$("#loading_viewInsuranceCalculate").hide();
	});
	
	function changeType(no){
		var allTr = new Array(1, 2, 3);
		for (var i = 0; i < allTr.length; i++){
			if (no == allTr[i]){
				$("#trInsuranceCal" + allTr[i]).show();
			} else {
				$("#trInsuranceCal" + allTr[i]).hide();
			}
		}
	}
//新的保险计算
	function f_Calculate_viewIscalculateNew() {
		var $form = $("#form_viewInsuranceCalculate",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO").val();
		var giveDate = $form.find("#GIVE_DATE").val();
		var str = "";
		$("#layout1 input:radio",navTab.getCurrentPanel()).each(function() {
			if (this.checked) {
				str = this.value;
			}
		});
		if(giveDate == ''){
			//alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			alert("工资发放日不能为空");
			return;
		}
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}
		var urlParam = "";
		if (str == "deptType"){//部门别	
			if (document.form_viewInsuranceCalculate.DEPTID.value == "") {
				//请选择部门
				alertMsg.error("<spring:message code='ar.alert.message.addempshift.choosedept'/>");
				return;
			}
			urlParam = 'seach_caltype=dept&seach_deptid=' + document.form_viewInsuranceCalculate.DEPTID.value;
		}else if (str == "empType"){//员工别
			document.form_viewInsuranceCalculate.PERSON_ID.value = document.form_viewInsuranceCalculate.personId.value;
			if (document.form_viewInsuranceCalculate.PERSON_ID.value == "") {
				//请选择员工
				alertMsg.error("<spring:message code='ar.alert.message.viewardetailcaculate.chooseperson'/>");
				return;
			}
			urlParam = 'seach_caltype=emp&seach_empid=' + document.form_viewInsuranceCalculate.PERSON_ID.value;
		}else if (str == "paSurperType"){//工资担当别
			if (document.form_viewInsuranceCalculate.paSupervisorId.value == "") {
				//请选择工资担当
				alertMsg.error("<spring:message code='ar.alert.message.viewardetailcaculate.choosePasupervisior'/>");
				return;
			}
			urlParam = 'seach_caltype=supervisor&seach_supervisorId=' + document.form_viewInsuranceCalculate.paSupervisorId.value;

		}
		
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.isbasicmonth'/>"+"[" + paMonth
				+ "]"+"<spring:message code='pa.viewiscalculate.title.insurancemonth'/>"+"?",
						{
							okCall : function() {
								$("#loading_viewInsuranceCalculate").show();
								$("#InsuranceCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/insurance/insuranceCalculateNew?'+ urlParam + '&seach_paMonth=' +paMonth+'&seach_statNo='+statNo+'&seach_GIVE_DATE='+giveDate,
											dataType : "json",
											
											success : function(responseStr) {
												$("#InsuranceCalculateResult").html(responseStr);
												$("#loading_viewInsuranceCalculate").hide();
												$("#InsuranceCalculate").show();
											}
									});
							}				            
						});

	}
	//辣的保险计算
	function f_Calculate_viewIscalculate() {
		var $form = $("#form_viewInsuranceCalculate",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO").val();
		var str = "";
		$("#layout1 input:radio",navTab.getCurrentPanel()).each(function() {
			if (this.checked) {
				str = this.value;
			}
		});
		
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}
		var urlParam = "";
		if (str == "deptType"){//部门别	
			if (document.form_viewInsuranceCalculate.DEPTID.value == "") {
				//请选择部门
				alertMsg.error("<spring:message code='ar.alert.message.addempshift.choosedept'/>");
				return;
			}
			urlParam = 'seach_caltype=dept&seach_deptid=' + document.form_viewInsuranceCalculate.DEPTID.value;
		}else if (str == "empType"){//员工别
			document.form_viewInsuranceCalculate.PERSON_ID.value = document.form_viewInsuranceCalculate.personId.value;
			if (document.form_viewInsuranceCalculate.PERSON_ID.value == "") {
				//请选择员工
				alertMsg.error("<spring:message code='ar.alert.message.viewardetailcaculate.chooseperson'/>");
				return;
			}
			urlParam = 'seach_caltype=emp&seach_empid=' + document.form_viewInsuranceCalculate.PERSON_ID.value;
		}else if (str == "paSurperType"){//工资担当别
			if (document.form_viewInsuranceCalculate.paSupervisorId.value == "") {
				//请选择工资担当
				alertMsg.error("<spring:message code='ar.alert.message.viewardetailcaculate.choosePasupervisior'/>");
				return;
			}
			urlParam = 'seach_caltype=supervisor&seach_supervisorId=' + document.form_viewInsuranceCalculate.paSupervisorId.value;

		}
		
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.isbasicmonth'/>"+"[" + paMonth
				+ "]"+"<spring:message code='pa.viewiscalculate.title.insurancemonth'/>"+"?",
						{
							okCall : function() {
								$("#loading_viewInsuranceCalculate").show();
								$("#InsuranceCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/insurance/insuranceCalculate?'+ urlParam + '&seach_paMonth=' +paMonth+'&seach_statNo='+statNo,
											dataType : "json",
											
											success : function(responseStr) {
												$("#InsuranceCalculateResult").html(responseStr);
												$("#loading_viewInsuranceCalculate").hide();
												$("#InsuranceCalculate").show();
											}
									});
							}
						});

	}
	
	//通过选择的保险月 查询出发放日期的list
function getSalaryProvideDate(){	
	var paMonth = $("#paYear",navTab.getCurrentPanel()).val() + $("#paMonth",navTab.getCurrentPanel()).val();
	var sel = $("#GIVE_DATE",navTab.getCurrentPanel());//职级
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/pa/insurance/getSalaryProvideDate?",
		 data: 'paMonth=' + paMonth,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
}

$(document).ready(function(){
	getSalaryProvideDate();
	});
</script>
<form name="form_viewInsuranceCalculate" method="post" action="" id="form_viewInsuranceCalculate">
	<div id="layout1">
		<div id="loading_viewInsuranceCalculate"
			style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
		<div id="InsuranceCalculate" title='<spring:message code="pa.salary.title.salarycalculation"/>' style="padding-top: 10px;">
			<table width="100%" border="0" cellpadding="0" cellspacing="1" >
				<tr>
					<td align="left" style="font-family: 'Arial','simsun';
					font-size: 14px;color:#333333;font-weight: bold;
					background-image: url(/resources/images/title/top_1.gif);
					background-repeat: no-repeat;
					padding-right: 0px;
					padding-left: 18px;
					padding-top: 3px;
					padding-bottom: 2px;">
						<spring:message code="pa.insurance.title.insuranceCaculate"/><!-- 保险计算 --></td>
				</tr>
			</table>
			<br/>
			<table width="100%" border="1" cellspacing="0" cellpadding="0"
					bordercolorlight="#E7E7E7" bordercolordark="#FFFFFF"
					style="padding: 2px 2px 2px 2px;">
	
				<tr>
					<td width="10%" style="padding: 4px;">
						<!-- 计算类型 --><spring:message code="ar.viewardetailcaculate.title.caltype"/>
					</td>
					<td width="90%" style="padding: 4px;">
						<table width="100%">
							<tr>
								<td width="90%" style="padding: 4px;">
									<input type="radio" value="deptType" name="group" checked="checked"
										onclick="changeType('1')" />
									&nbsp;<!-- 部门别 --><spring:message code="ar.viewardetailcaculate.title.bydept"/>
									<input type="radio" value="empType" name="group"
										onclick="changeType('2')" />
									&nbsp;<!-- 员工别 --><spring:message code="ar.viewardetailcaculate.title.byperson"/>
									<input type="radio" value="paSurperType" name="group"
										onclick="changeType('3');" />
									&nbsp;<!-- 工资担当别 --><spring:message code="ar.viewardetailcaculate.title.byPasupervisior"/>
								</td>
								<td width="15%" style="padding: 4px;" align="center">
									<a class="l-button"
										style="width: 60px; float: left; margin-left: 10px;"
										onclick="f_Calculate_viewIscalculateNew()">
								    <spring:message code="pa.insurance.title.insuranceCaculate"/><!--保险计算 -->
									</a>
								</td>
								<%-- 原来保险计算 2013-09-13
								<td width="15%" style="padding: 4px;" align="center">
									<a class="l-button"
										style="width: 60px; float: left; margin-left: 10px;"
										onclick="f_Calculate_viewIscalculate()">
								    <spring:message code="pa.insurance.title.insuranceCaculate"/><!--保险计算 -->
									</a>
								</td> --%>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="10%" style="padding: 4px;">
						<!-- 计算条件 --><spring:message code="ar.viewardetailcaculate.title.choose"/>
					</td>
					<td width="90%" style="padding: 4px;">
						<table width="100%" border="1">
					      	<tr>
								<td width="25%" style="padding: 4px;">
								<spring:message code="pa.insurance.title.insuranceMonth"/><!--保险月-->
									<ait:date yearName="paYear" yearSelected="${paYear}"
											  monthName="paMonth" monthSelected="${paMonth}" onChange="getSalaryProvideDate();"/>
								</td>
								<td width="35%" style="padding: 4px;" align="center">
								    <spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 -->
									<select id="GIVE_DATE" name="GIVE_DATE" class="select" >
										<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>
									</select>
								</td>
								<td width="25%" style="padding: 4px;" align="center">
								 <spring:message code="pa.salary.title.attendanceInterval"/><!--考勤区间-->
									<select id="STAT_NO" name="STAT_NO">
										<!--<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>  -->
										<c:forEach items="${statList}" var="stat">
											<option value="${stat.STAT_NO}">${stat.STAT_NAME}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
					      </table>
					<td>
				</tr>
	
				<tr id="trInsuranceCal1" name="trInsuranceCal1" style="display: block;">
					<td style="padding: 4px;">
						<!-- 部门 --><spring:message code="public.title.deptName"/>
					</td>
					<td style="padding: 4px;">
						<ait:deptTree name="DEPTID" limit="pa" selected=""/>
					</td>
				</tr>
				<tr id="trInsuranceCal2" name="trInsuranceCal2" style="display: none;">
					<td style="padding: 4px;">
						<!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					</td>
					<td style="padding: 4px;">
						<table width="30%">
					      	<tr>
					      		<td width="10%" style="padding:4px;">
					      			<input id="PERSON_ID" name="PERSON_ID" type="hidden" value=""/>
									<input id="personId" name="dwz.person.personId" value="" readOnly type="hidden" lookupGroup="person"/>
						 			<input id="empId" name="dwz.person.empId" type="text" readOnly class="required"  readOnly lookupGroup="person"/>
					      		</td>
					      		<td style="padding:4px;">
					      			<a class="btnLook" href="/pa/salary/viewAddPaPersonalDataList?pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					      		</td>
					      	</tr>
					      </table>
					</td>
				</tr>
				<tr id="trInsuranceCal3" name="trInsuranceCal3" style="display: none;">
					<td style="padding: 4px;">
						<!-- 工资担当 --><spring:message code="ar.viewardetailcaculate.title.pasupervisior"/>
					</td>
					<td style="padding: 4px;">
						<select name="paSupervisorId" id="paSupervisorId"
							style="width: 180px; position: static; visibility: inherit;">
							<c:forEach items="${supervisorlist}" var="supervisorlist">
								<c:choose>
									<c:when test="${supervisorId==supervisorlist.SUPERVISOR_ID}">
										<option value="${supervisorlist.SUPERVISOR_ID}"
											selected="selected">
											${supervisorlist.EMPID}- ${supervisorlist.LOCAL_NAME}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${supervisorlist.SUPERVISOR_ID}">
											${supervisorlist.EMPID }-
											${supervisorlist.LOCAL_NAME}
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			</br>
			<table width="100%" border="0" cellpadding="0" cellspacing="1" >
				<tr>
					<td align="left" style="font-family: 'Arial','simsun';
					font-size: 14px;color:#333333;font-weight: bold;
					background-image: url(/resources/images/title/top_1.gif);
					background-repeat: no-repeat;
					padding-right: 0px;
					padding-left: 18px;
					padding-top: 3px;
					padding-bottom: 2px;">
					<spring:message code="pa.salary.title.caculateStatus"/><!--计算状态--></td>
				</tr>
			</table>
			</br>
			<table width="100%" align="center" border="1" cellspacing="0" cellpadding="0"
				bordercolorlight="#E7E7E7" bordercolordark="#FFFFFF"
				style="padding: 2px 2px 2px 2px;">
				<tr>
					<td width="100%" colspan="2" align="center" style="padding: 4px;">
						<div id="InsuranceCalculateResult" style="color:#F00;"></div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>