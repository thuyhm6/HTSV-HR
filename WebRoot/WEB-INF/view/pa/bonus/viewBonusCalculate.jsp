<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#loading-bonusCalculate").hide();
	});

	function f_bonusCalculate() {
		var $form = $("#viewBonusCal");
		var basisMonth = $form.find("#basicYear").val() + $form.find("#basicMonth").val();
		var bonusMonth = $form.find("#bonusYear").val() + $form.find("#bonusMonth").val();
		var bonusTypeId = $form.find("#BONUS_TYPE_ID").val();
		var give_date = $form.find("#GIVE_DATE").val();
		if(give_date == ''){
			//alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			//alert("工资发放日不能为空");
			alertMsg.info('<spring:message code="alert.message.paGiveDateIsNotNull"/>');
			return;
		}
		//alert(give_date);"<spring:message code='pa.viewiscalculate.title.iscal'/>"++"[" + basisMonth + "]"
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.isbasicmonth'/>"+"[" + bonusMonth
								+ "]"+"<spring:message code='pa.title.message.bonusTitle'/>"+"?",
						{
							okCall : function() {
								$("#loading-bonusCalculate").show();
								$("#monthCalculate").hide();
								$
										.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/bonus/bonusCalculate?seach_PA_BASIC_MONTH='
													+ basisMonth
													+ '&seach_BN_MONTH='
													+ bonusMonth
													+ '&seach_BONUS_TYPE_ID='
													+ bonusTypeId
													+'&seach_GIVE_DATE='
													+give_date,
											dataType : "json",

											success : function(responseStr) {
												$("#bonusCalculateResult").html(
														responseStr);
												$("#loading-bonusCalculate").hide();
												$("#bonusCalculate").show();
											}
										});
							}
						});

	}
	//通过选择的保险月 查询出发放日期的list
function getSalaryProvideDateBn(){
	var paMonth = $("#bonusYear",navTab.getCurrentPanel()).val() + $("#bonusMonth",navTab.getCurrentPanel()).val();
	var sel = $("#GIVE_DATE",navTab.getCurrentPanel());//职级
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/pa/bonus/getSalaryProvideDateBn?",
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
	getSalaryProvideDateBn();
	});
</script>

<form name="viewBonusCal" method="post" action="" id="viewBonusCal">
	<div id="loading-bonusCalculate"
		style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
		<img src="/resources/images/loading.gif">
	</div>
	<div id='bonusCalculate' title='<spring:message code="pa.insurance.title.bonusCaculate"/>' style="padding-top: 10px;">
		<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr>
				<td align="left"
					style="font-family: 'Arial', 'simsun'; font-size: 14px; color: #333333; font-weight: bold; background-image: url(/resources/images/title/top_1.gif); background-repeat: no-repeat; padding-right: 0px; padding-left: 18px; padding-top: 3px; padding-bottom: 2px;"
					colspan="10">
					<spring:message code="pa.insurance.title.bonusCaculate"/><!--奖金计算-->
				</td>
			</tr>
		</table>
		<table width="100%" align="center" border="1" cellspacing="0"
			cellpadding="0" bordercolorlight="#E7E7E7" bordercolordark="#FFFFFF"
			style="padding: 2px 2px 2px 2px;">
			<tr>
				<!--<td>
					<spring:message code="pa.insurance.title.salaryBasicMonth"/>工资基础月：
					<ait:date yearName="basicYear" yearSelected="${basicYear}"
						monthName="basicMonth" monthSelected="${basicMonth}"/>
				</td>
				--><td class="td_center">
					<spring:message code="pa.insurance.title.bonusMonth"/><!--奖金月-->:
					<ait:date yearName="bonusYear" yearSelected="${bonusYear}"
						monthName="bonusMonth" monthSelected="${bonusMonth}"  onChange="getSalaryProvideDateBn();"/>
				</td>
				<td class="td_center">
					<spring:message code="pa.insurance.title.bonusType"/><!--奖金类型-->:
					<select name="BONUS_TYPE_ID" id="BONUS_TYPE_ID" style="width:180px; position: static; visibility: inherit;">
						<c:forEach items="${bonusTypeList}" var="cpny">
						    <option value="${cpny.TYPE_ID}">${cpny.TYPE_NAME}</option>
						</c:forEach>
					</select>
				</td>
				
				<td class="td_center">
				    <spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 -->
					<select id="GIVE_DATE" name="GIVE_DATE" class="select" >
						<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>
					</select>
				</td>
								
				<td width="15%" style="padding: 4px;" aclass="td_center">
					<a class="l-button"
						style="width: 60px; float: left; margin-left: 10px;"
						onclick="f_bonusCalculate();"> <spring:message code="pa.insurance.title.bonusCaculate"/><!--奖金计算--> </a>
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
				 <spring:message code="pa.salary.title.caculateStatus"/><!--计算状态--> </td>
			</tr>
		</table>
		</br>
		<table width="100%" align="center" border="1" cellspacing="0" cellpadding="0"
			bordercolorlight="#E7E7E7" bordercolordark="#FFFFFF"
			style="padding: 2px 2px 2px 2px;">
			<tr>
				<td colspan="3" align="center" style="padding: 4px;">
					<div id="bonusCalculateResult" style="color:#F00;"></div>
				</td>
			</tr>
		</table>
	</div>
</form>