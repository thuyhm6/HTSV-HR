<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/*
window.document.onkeydown = SubmitOrHidden;

var SubmitOrHidden = function(evt){
    evt = window.event || evt;
    alert(evt);
    alert(evt.keyCode);
    if(evt.keyCode==13){//如果取到的键值是回车
		return false;	
     }
}*/

function validateCallback(form,callback) {	
	
	var $form = $("#addPaInsPersonalInput");
	if (!$form.valid()){
		return false;
	}
	
	var myDate = new Date();
	var year=myDate.getFullYear(); //当前年
	var month=myDate.getMonth();
	month = month+1;
	if(month<10){
		month = '0'+month;
	}
	var months = year+''+month;
	var months1 = year+'-'+month+'-01';
	//alert(months);
	//alert(months1);
	//alert(year);
	//alert(month);
	//alert(day);
		
	var h3 = document.getElementsByName("h3");
	for(var i=0;i<h3.length;i++){
		//alert(baStartMonths[i].value);
		var baStartMonth = $("#BASIC_START_DATE_" + h3[i].value);
		var baStartMonthOld = $("#BASIC_START_DATE_OLD_" + h3[i].value);
		var baEndMonth = $("#BASIC_END_DATE_" + h3[i].value);

		//如果结束月份比开始月早， 请重新填写结束月！
	    if(baEndMonth && baStartMonth && baEndMonth.val()!='' && baStartMonth.val()!='' && baEndMonth.val() < baStartMonth.val()){
	    	alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
	    	baEndMonth.focus();
	        return false;
	    }
	    /*
	    //结束年月不能小于当前年月.
	    if(baEndMonth && baEndMonth.val()!='' && baEndMonth.val() < months1){
	    	alertMsg.error('<spring:message code="alert.message.mess1"/>');
	    	baEndMonth.focus();
	        return false;
		}
		//开始年月不能小于原来的开始年月
		if(baStartMonthOld && baStartMonth && baStartMonthOld.val()!='' 
			&& baStartMonth.val()!='' && baStartMonth.val() < baStartMonthOld.val()){
	    	alertMsg.error('<spring:message code="alert.message.mess2"/>');
	    	baStartMonth.focus();
	        return false;
		}
		//开始年月不能小于当前年月
		if(baStartMonthOld && baStartMonth && baStartMonthOld.val()=='' 
			&& baStartMonth.val()!='' && baStartMonth.val() < months1){
	    	alertMsg.error('<spring:message code="alert.message.mess3"/>');
	    	baStartMonths[i].focus();
	        return false;
		}*/
	}

	var h2 = document.getElementsByName("h2");
	for(var i = 0 ; i < h2.length ; i++){
		var inpStartMonth = $("#INPUT_START_MONTH_" + h2[i].value);
		var inpStartMonthOld = $("#INPUT_START_MONTH_OLD_" + h2[i].value);
		var inpEndMonth = $("#INPUT_END_MONTH_" + h2[i].value);
		
		//如果结束月份比开始月早， 请重新填写结束月！
	    if(inpEndMonth && inpStartMonth && inpEndMonth.val() != '' 
		    && inpStartMonth.val() != '' && inpEndMonth.val() < inpStartMonth.val()){
		    //alert(1);
	    	alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
	    	inpEndMonth.focus();
	        return false;
	    }
/*
	    //结束年月不能小于当前年月.
	    if(inpEndMonth && inpEndMonth.val() != '' && inpEndMonth.val() < months){
		    //alert(2);
	    	alertMsg.error('<spring:message code="alert.message.mess1"/>');
	    	inpEndMonth.focus();
	        return false;
		}

		//开始年月不能小于原来的开始年月
		if(inpStartMonth && inpStartMonthOld && inpStartMonth.val()!='' 
			&& inpStartMonthOld.val() != '' && inpStartMonth.val() < inpStartMonthOld.val()){
		    //alert(3);
	    	alertMsg.error('<spring:message code="alert.message.mess2"/>');
	    	inpStartMonth.focus();
	        return false;
		}
		//开始年月不能小于当前年月
		if(inpStartMonth && inpStartMonthOld &&  inpStartMonthOld.val() == '' 
			&& inpStartMonth.val() != '' && inpStartMonth.val() < months){
		    //alert(4);
	    	alertMsg.error('<spring:message code="alert.message.mess3"/>');
	    	inpStartMonth.focus();
	        return false;
		}
		*/
	}
	
	var h1 = document.getElementsByName("h1");
	for(var i = 0 ; i < h1.length ; i++){
		var insStartMonth = $("#INS_START_MONTH_" + h1[i].value);
		var insStartMonthOld = $("#INS_START_MONTH_OLD_" + h1[i].value);
		var insEndMonth = $("#INS_END_MONTH_" + h1[i].value);
		//如果结束月份比开始月早， 请重新填写结束月！
	    if(insStartMonth && insEndMonth && insStartMonth.val()!='' 
		    && insEndMonth.val()!='' && insEndMonth.val() < insStartMonth.val()){
		    //alert(5);
	    	alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
	    	insEndMonth.focus();
	        return false;
	    }
	    /*
	    //结束年月不能小于当前年月.
	    if(insEndMonth && insEndMonth.val()!='' && insEndMonth.val() < months){
		    //alert(6);
	    	alertMsg.error('<spring:message code="alert.message.mess1"/>');
	    	insEndMonth.focus();
	        return false;
		}
		//开始年月不能小于原来的开始年月
		if(insStartMonthOld && insStartMonth && insStartMonthOld.val() != '' 
			&& insStartMonth.val() != '' && insStartMonth.val() < insStartMonthOld.val()){
		    //alert(7);
	    	alertMsg.error('<spring:message code="alert.message.mess2"/>');
	    	insStartMonth.focus();
	        return false;
		}
		//开始年月不能小于当前年月
		if(insStartMonthOld && insStartMonth && insStartMonthOld.val() == '' 
			&& insStartMonth.val() != '' && insStartMonth.val() < months){
		    //alert(8);
	    	alertMsg.error('<spring:message code="alert.message.mess3"/>');
	    	insStartMonth.focus();
	        return false;
		}
		*/
	}
	
	/*
	var evt = window.event;
	if(evt.keyCode==13){//如果取到的键值是回车
		return false;	
	}
*/
	if (confirm ('<spring:message code="alert.message.pa.insurance.confirmSubmit"/>')){
	  	$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){
			if(data.statusCode == 200){
				alertMsg.correct(data.message);
			}
			if(data.statusCode == 300){
				alertMsg.error(data.message);
			}
 				$.pdialog.closeCurrent();
			},
			error: DWZ.ajaxError
		});	
		return false;
	}else{
		return false;
	}
}


$(document).ready(function(){
	                           
	
	
	$("input[type='text'][id^='INS_START_MONTH']").each(function(){
		$(this).removeClass("required");
	});
	$("input[type='text'][id^='INS_END_MONTH']").each(function(){
		$(this).removeClass("required");
	});
	$("input[type='text'][id^='INS_RETURN_VALUE']").each(function(){
		$(this).removeClass("required");
	});
	$("input[type='text'][id^='INPUT_START_MONTH']").each(function(){
		$(this).removeClass("required");
	});
	$("input[type='text'][id^='INPUT_END_MONTH']").each(function(){
		$(this).removeClass("required");
	});
	$("input[type='text'][id^='INPUT_RETURN_VALUE']").each(function(){
		$(this).removeClass("required");
	});
	$("input[type='text'][id^='BASIC_RETURN_VALUE']").each(function(){
		$(this).removeClass("required");
	});
}); 
</script>
<form id="addPaInsPersonalInput" action="/pa/salary/updatePaInsPersonalInput" onsubmit="return validateCallback(this,navTabAjaxDone)">
<div class="formBar">
	<tr>
		<td align="center">
			<!--姓名-->
			&nbsp;&nbsp;
			<b><spring:message code="public.title.name" />： </b>${paInputItemPersonInfo.LOCAL_NAME}
		</td>
		<td align="center">
			<!--工号-->
			<b><spring:message code="public.title.empId" />：</b>${paInputItemPersonInfo.EMPID}
		</td>
		<td align="center">
			<!--部门-->
			<b><spring:message code="public.title.deptName" />：</b>
			${paInputItemPersonInfo.DEPT_NAME}
		</td>
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="validateCallback(this,navTabAjaxDone);">
							<spring:message code="pa.insurance.title.submit" />
							<!--保存-->
						</button>
					</div>
				</div>
			</li>
		</ul>
	</tr>
</div>
<div layoutH="50">
	<div class="pageContent">
		<div class="panel">
			<h1>
				<spring:message code="zxc.pa.input.SALARY_BASIC_ITEM_DATA" />
				<!-- 工资基础项目数据 -->
			</h1>
			<div>
				<table class="tablea" width="99%">
					<thead>
						<tr>
							<th width="5%"></th>
							<th width="20%">
								<spring:message code="pa.insurance.title.projectName" />
								<!--项目名称-->
							</th>
							<th width="15%">
								<spring:message code="pa.insurance.title.startMonth" />
								<!--开始月-->
							</th>
							<th width="15%">
								<spring:message code="pa.insurance.title.endMonth" />
								<!--结束月-->
							</th>
							<th width="10%">
								<spring:message code="pa.insurance.title.dataValue" />
								<!--数值-->
							</th>
							<th width="25%">
								<spring:message code="hr.viewPromote.title.REMARK" />
								<!--备注-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paBasicInputItemList}" var="paBasicInputItem" varStatus="basicStatus">
							<c:if test="${empty paBasicInputItem.BASIC_DATA_NO }">
								<tr target="sid" rel="${paBasicInputItem.PARAM_NO}" id="basicInputItem_${paBasicInputItem.PARAM_NO }">
									<input type="hidden" id="BASIC_PERSON_ID"
										name="BASIC_PERSON_ID_${paBasicInputItem.PARAM_NO}"
										value="${paInputItemPersonInfo.PERSON_ID}" />									
									<input type="hidden" id="BASIC_CPNY_ID"
										name="BASIC_CPNY_ID_${paBasicInputItem.PARAM_NO}"
										value="${paInputItemPersonInfo.CPNY_ID}" />
									<input type="hidden" name="BASIC_PARAM_NO_${paBasicInputItem.PARAM_NO }" 
										value="${paBasicInputItem.PARAM_NO }"/>
									<input type="hidden" name="PA_BASIC_FLAG_${paBasicInputItem.PARAM_NO }" value="${paBasicInputItem.FLAG }"/>
									<input type="hidden" id="h3" name="h3" value="${paBasicInputItem.PARAM_NO}" />
									
									<td>${basicStatus.index +1 }</td>
									<td>
										${paBasicInputItem.ITEM_NAME}
									</td>
									<td>
										<input id="BASIC_START_DATE_OLD_${paBasicInputItem.PARAM_NO}" name="BASIC_START_DATE_OLD_${paBasicInputItem.PARAM_NO}" 
										 value="${paBasicInputItem.START_DATE}" type="hidden"/>
										<input name="BASIC_START_DATE_${paBasicInputItem.PARAM_NO}" class="date textInput valid" type="text" 
											id ="BASIC_START_DATE_${paBasicInputItem.PARAM_NO}"	maxLength="200" value="${paBasicInputItem.START_DATE}"/>
									</td>
									<td>
										<input name="BASIC_END_DATE_${paBasicInputItem.PARAM_NO}" class="date textInput" 
											id="BASIC_END_DATE_${paBasicInputItem.PARAM_NO}"	type="text" maxLength="200" value="${paBasicInputItem.END_DATE}"/>
									</td>
									<td>
										<c:if test="${empty paBasicInputItem.RETURN_VALUE}">
											<ait:inputText
												name="BASIC_RETURN_VALUE_${paBasicInputItem.PARAM_NO}"
												id="BASIC_RETURN_VALUE_${paBasicInputItem.PARAM_NO}"
												inputType="number" maxLength="200"
												value="" style="text-align:right;"/>
										</c:if>
										<c:if test="${not empty paBasicInputItem.RETURN_VALUE}">
											<ait:inputText
												name="BASIC_RETURN_VALUE_${paBasicInputItem.PARAM_NO}"
												id="BASIC_RETURN_VALUE_${paBasicInputItem.PARAM_NO}"
												inputType="number" maxLength="50"  style="text-align:right;"
												value="${paBasicInputItem.RETURN_VALUE}" />
										</c:if>
									</td>
									<td>
										<input name="BASIC_REMARK_${paBasicInputItem.PARAM_NO}" type="text"
											maxlength="30" size="30" value="${paBasicInputItem.REMARK}" />
									</td>
								</tr>
							</c:if>
							<c:if test="${not empty paBasicInputItem.BASIC_DATA_NO }">
								<tr target="sid" rel="${paBasicInputItem.BASIC_DATA_NO}" id="basicInputItem_${paBasicInputItem.BASIC_DATA_NO }">
									<input type="hidden" id="BASIC_PERSON_ID"
										name="BASIC_PERSON_ID_${paBasicInputItem.BASIC_DATA_NO}"
										value="${paInputItemPersonInfo.PERSON_ID}" />
									<input type="hidden" id="BASIC_CPNY_ID"
										name="BASIC_CPNY_ID_${paBasicInputItem.BASIC_DATA_NO}"
										value="${paInputItemPersonInfo.CPNY_ID}" />
									<input type="hidden" name="BASIC_PARAM_NO_${paBasicInputItem.BASIC_DATA_NO }" 
										value="${paBasicInputItem.PARAM_NO }"/>
									<input type="hidden" name="PA_BASIC_FLAG_${paBasicInputItem.BASIC_DATA_NO }" value="${paBasicInputItem.FLAG }"/>
									<input type="hidden" id="h3" name="h3" value="${paBasicInputItem.BASIC_DATA_NO}" />
									<input type="hidden" id="BASIC_BASIC_DATA_NO"
										name="BASIC_BASIC_DATA_NO_${paBasicInputItem.BASIC_DATA_NO}"
										value="${paBasicInputItem.BASIC_DATA_NO}" />
									<td>${basicStatus.index +1 }</td>
									<td>
										${paBasicInputItem.ITEM_NAME}
									</td>
									<td>
										<input id="BASIC_START_DATE_OLD_${paBasicInputItem.BASIC_DATA_NO}" value="${paBasicInputItem.START_DATE}" 
											type="hidden"  name="BASIC_START_DATE_OLD_${paBasicInputItem.BASIC_DATA_NO}" />
										<input name="BASIC_START_DATE_${paBasicInputItem.BASIC_DATA_NO}" class="date textInput valid" type="text" 
											id="BASIC_START_DATE_${paBasicInputItem.BASIC_DATA_NO}" 	maxLength="200" value="${paBasicInputItem.START_DATE}"/>
									</td>
									<td>
										<input name="BASIC_END_DATE_${paBasicInputItem.BASIC_DATA_NO}" class="date textInput" 
											id="BASIC_END_DATE_${paBasicInputItem.BASIC_DATA_NO}"	type="text" maxLength="200" value="${paBasicInputItem.END_DATE}"/>
									</td>
									<td>
										<c:if test="${empty paBasicInputItem.RETURN_VALUE}">
											<ait:inputText
												name="BASIC_RETURN_VALUE_${paBasicInputItem.BASIC_DATA_NO}"
												id="BASIC_RETURN_VALUE_${paBasicInputItem.BASIC_DATA_NO}"
												inputType="number" maxLength="200"
												value=""  style="text-align:right;"/>
										</c:if>
										<c:if test="${not empty paBasicInputItem.RETURN_VALUE}">
											<ait:inputText
												name="BASIC_RETURN_VALUE_${paBasicInputItem.BASIC_DATA_NO}"
												id="BASIC_RETURN_VALUE_${paBasicInputItem.BASIC_DATA_NO}"
												inputType="number" maxLength="50" style="text-align:right;"
												value="${paBasicInputItem.RETURN_VALUE}" />
										</c:if>
									</td>
									<td>
										<input name="BASIC_REMARK_${paBasicInputItem.BASIC_DATA_NO}" type="text"
											maxlength="30" size="30" value="${paBasicInputItem.REMARK}" />
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panel">
			<h1>
				<spring:message code="zxc.pa.input.SALARY_INPUT_ITEM_DATA" />
				<!-- 工资输入项目数据 -->
			</h1>
			<div>
				<table class="tablea" width="99%">
					<thead>
						<tr>
							<th width="5%"></th>
							<th width="20%">
								<spring:message code="pa.insurance.title.projectName" />
								<!--项目名称-->
							</th>
							<th width="15%">
								<spring:message code="pa.insurance.title.startMonth" />
								<!--开始月-->
							</th>
							<th width="15%">
								<spring:message code="pa.insurance.title.endMonth" />
								<!--结束月-->
							</th>
							<th width="10%">
								<spring:message code="pa.insurance.title.dataValue" />
								<!--数值-->
							</th>
							<th width="25%">
								<spring:message code="hr.viewPromote.title.REMARK" />
								<!--备注-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paInputItemPersonList}"
							var="paInputItemPerson" varStatus="inputStatus">
							<tr target="sid" rel="${paInputItemPerson.PARAM_NO}">
								<input type="hidden" id="INPUT_PERSON_ID"
									name="INPUT_PERSON_ID_${paInputItemPerson.PARAM_NO}"
									value="${paInputItemPersonInfo.PERSON_ID}" />
								<input type="hidden" id="INPUT_CPNY_ID"
									name="INPUT_CPNY_ID_${paInputItemPerson.PARAM_NO}"
									value="${paInputItemPersonInfo.CPNY_ID}" />
								<input type="hidden" name="PA_INPUT_FLAG_${paInputItemPerson.PARAM_NO }" value="${paInputItemPerson.FLAG }"/>
								<input type="hidden" id="h2" name="h2" value="${paInputItemPerson.PARAM_NO}" />								
								<input type="hidden" id="INPUT_PARAM_DATA_NO"
									name="INPUT_PARAM_DATA_NO_${paInputItemPerson.PARAM_NO}"
									value="${paInputItemPerson.PARAM_DATA_NO}" />
								<td>${inputStatus.index +1 }</td>
								<td>
									${paInputItemPerson.ITEM_NAME}
								</td>
								<td>
									<input id="INPUT_START_MONTH_OLD_${paInputItemPerson.PARAM_NO}" name="INPUT_START_MONTH_OLD_${paInputItemPerson.PARAM_NO}"
										value="${paInputItemPerson.START_MONTH}" type="hidden"/>
									<ait:inputText name="INPUT_START_MONTH_${paInputItemPerson.PARAM_NO}"
										id="INPUT_START_MONTH_${paInputItemPerson.PARAM_NO}"
										inputType="date" maxLength="6" inputSize="10"
										value="${paInputItemPerson.START_MONTH}" />
								</td>
								<td>
									<ait:inputText name="INPUT_END_MONTH_${paInputItemPerson.PARAM_NO}"
										id="INPUT_END_MONTH_${paInputItemPerson.PARAM_NO}" inputType="date"
										maxLength="6" inputSize="10"
										value="${paInputItemPerson.END_MONTH}" />
								</td>
								<td>
									<c:if test="${empty paInputItemPerson.RETURN_VALUE}">
										<ait:inputText
											name="INPUT_RETURN_VALUE_${paInputItemPerson.PARAM_NO}"
											id="INPUT_RETURN_VALUE_${paInputItemPerson.PARAM_NO}"
											inputType="number" maxLength="200" value=""  style="text-align:right;"/>
									</c:if>
									<c:if test="${not empty paInputItemPerson.RETURN_VALUE}">
										<ait:inputText
											name="INPUT_RETURN_VALUE_${paInputItemPerson.PARAM_NO}"
											id="INPUT_RETURN_VALUE_${paInputItemPerson.PARAM_NO}"
											inputType="number" maxLength="50"  style="text-align:right;"
											value="${paInputItemPerson.RETURN_VALUE}" />
									</c:if>
								</td>
								<td>
									<input name="INPUT_REMARK_${paInputItemPerson.PARAM_NO}" type="text"
										maxlength="30" size="30" value="${paInputItemPerson.REMARK}" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panel">
			<h1>
				<spring:message code="zxc.pa.input.INSURANCE_INPUT_DATA" />
				<!-- 社保输入项目数据 -->
			</h1>
			<div>
				<table class="tablea" width="99%">
					<thead>
						<tr>
							<th width="5%"></th>
							<th width="20%">
								<spring:message code="pa.insurance.title.projectName" />
								<!--项目名称-->
							</th>
							<th width="15%">
								<spring:message code="pa.insurance.title.startMonth" />
								<!--开始月-->
							</th>
							<th width="15%">
								<spring:message code="pa.insurance.title.endMonth" />
								<!--结束月-->
							</th>
							<th width="10%">
								<spring:message code="pa.insurance.title.dataValue" />
								<!--数值-->
							</th>
							<th width="25%">
								<spring:message code="hr.viewPromote.title.REMARK" />
								<!--备注-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${insuranceInputItemPersonList}"
							var="insuranceInputItemPerson" varStatus="insStatus">
							<tr target="sid" rel="${insuranceInputItemPerson.PARAM_NO}">
								<input type="hidden" id="INS_PERSON_ID"
									name="INS_PERSON_ID_${insuranceInputItemPerson.PARAM_NO}"
									value="${paInputItemPersonInfo.PERSON_ID}" />
								<input type="hidden" id="INS_CPNY_ID"
									name="INS_CPNY_ID_${insuranceInputItemPerson.PARAM_NO}"
									value="${paInputItemPersonInfo.CPNY_ID}" />
								<input type="hidden" name="INS_FLAG_${insuranceInputItemPerson.PARAM_NO }" value="${insuranceInputItemPerson.FLAG }"/>
								<input type="hidden" id="h1" name="h1" value="${insuranceInputItemPerson.PARAM_NO}" />								
								<input type="hidden" id="INS_PARAM_DATA_NO"
									name="INS_PARAM_DATA_NO_${insuranceInputItemPerson.PARAM_NO}"
									value="${insuranceInputItemPerson.PARAM_DATA_NO}" />
								<td>${insStatus.index +1 }</td>
								<td>
									${insuranceInputItemPerson.ITEM_NAME}
								</td>
								<td>
									<input id="INS_START_MONTH_OLD_${insuranceInputItemPerson.PARAM_NO}" name="INS_START_MONTH_OLD_${insuranceInputItemPerson.PARAM_NO}" 
										value="${insuranceInputItemPerson.START_MONTH}" type="hidden"/>
									<ait:inputText name="INS_START_MONTH_${insuranceInputItemPerson.PARAM_NO}"
										id="INS_START_MONTH_${insuranceInputItemPerson.PARAM_NO}"
										inputType="date" maxLength="6" inputSize="10"
										value="${insuranceInputItemPerson.START_MONTH}" />
								</td>
								<td>
									<ait:inputText name="INS_END_MONTH_${insuranceInputItemPerson.PARAM_NO}"
										id="INS_END_MONTH_${insuranceInputItemPerson.PARAM_NO}"
										inputType="date" maxLength="6" inputSize="10"
										value="${insuranceInputItemPerson.END_MONTH}" />
								</td>
								<td>
									<c:if test="${empty insuranceInputItemPerson.RETURN_VALUE}">
										<ait:inputText
											name="INS_RETURN_VALUE_${insuranceInputItemPerson.PARAM_NO}"
											id="INS_RETURN_VALUE_${insuranceInputItemPerson.PARAM_NO}"
											inputType="number" maxLength="200"  style="text-align:right;"
											value="" />
									</c:if>
									<c:if test="${not empty insuranceInputItemPerson.RETURN_VALUE}">
										<ait:inputText
											name="INS_RETURN_VALUE_${insuranceInputItemPerson.PARAM_NO}"
											id="INS_RETURN_VALUE_${insuranceInputItemPerson.PARAM_NO}"
											inputType="number" maxLength="50"  style="text-align:right;"
											value="${insuranceInputItemPerson.RETURN_VALUE}" />
									</c:if>
								</td>
								<td>
									<input name="INS_REMARK_${insuranceInputItemPerson.PARAM_NO}"
										type="text" maxlength="30" size="30"
										value="${insuranceInputItemPerson.REMARK}" />
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</form>