<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback(form,callback) {	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}


	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.pa.insurance.pleaseInputDataFirst"/>');
		return false;
	}
	if (confirm ('<spring:message code="alert.message.pa.insurance.confirmSubmit"/>')){
	
		/*/*$.each($("input[name='RETURN_VALUE']"),function(i,value){
			alert(i);
			alert(value.value+","+value.itemParamNO);
		$("#c1",$(value.parentNode.parentNode.parentNode)).val(value.value+","+value.itemParamNO);
		});
		var checkbox= document.getElementsByName("c1");
		for(var i=0;i<checkbox.length;i++){
			var per = document.getElementsByName("START_MONTH")+","+document.getElementsByName("END_MONTH")
			+","+document.getElementsByName("RETURN_VALUE")
			alert(per);
		}*/
	
  	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});	
	return false;
	}
	
}
</script>

<div class="pageContent"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
	<form onsubmit="return validateCallback(this, navTabAjaxDone);" action="/pa/bonus/updateBonusInputItemDataPersonInfo" method="post">
	<div class="formBar">
			<tr>
				
				<td align="center">
				  <b><spring:message code="public.title.name"/><!--姓名-->：
				  </b>${bonusPersonInfo.LOCAL_NAME}
				</td>
				<td align="center">
				  <b><spring:message code="public.title.empId"/><!--工号-->：</b>${bonusPersonInfo.PERSON_ID}
				</td>
				<td align="center">
				  <b><spring:message code="public.title.deptName"/><!--部门-->：
				  </b>${bonusPersonInfo.DEPT_NAME}
				</td>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="c1"
								selectType="invert">
								<spring:message code="pa.salary.title.opsiteChecked"/><!--反选-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="pa.insurance.title.submit"/><!--保存-->
							</button>
						</div>
					</div>
				</li>
				</ul>
		</tr>
	</div>
	


	<table class="table" width="99%" layoutH="138">
		<thead>
			<tr>
				<th width="20%"></th>
				<th width="20%"><spring:message code="pa.insurance.title.projectName"/><!--项目名称--></th>
				<th width="20%"><spring:message code="pa.insurance.title.dataValue"/><!--数值--></th>
				<th width="30%"><spring:message code="pa.insurance.title.startMonth"/><!--开始月--></th>
				<th width="30%"><spring:message code="pa.insurance.title.endMonth"/><!--结束月--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bonusPersonList}" var="bonusPerson">
				<tr target="sid" rel="${bonusPerson.PARAM_NO}">
					<input type="hidden" id="PARAM_DATA_NO" name="PARAM_DATA_NO_${bonusPerson.PARAM_NO}" value="${bonusPerson.PARAM_DATA_NO}" />
					<input type="hidden" id="PERSON_ID" name="PERSON_ID_${bonusPerson.PARAM_NO}" value="${bonusPersonInfo.PERSON_ID}" />
					<input type="hidden" id="CPNY_ID" name="CPNY_ID_${bonusPerson.PARAM_NO}" value="${bonusPersonInfo.CPNY_ID}" />
					<td>
						<input type="checkbox" id="c1" name="c1" value="${bonusPerson.PARAM_NO}" />
					</td>
					<td>${bonusPerson.ITEM_NAME}</td>
					<td>88
						<c:if test="${empty bonusPerson.RETURN_VALUE}">
							<input name="RETURN_VALUE_${bonusPerson.PARAM_NO}" type="text" maxlength="200" 
							alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>'
							 style="text-align:right;"	value="0" />
						</c:if>
						<c:if test="${not empty bonusPerson.RETURN_VALUE}">
							<input name="RETURN_VALUE_${bonusPerson.PARAM_NO}" type="text" maxlength="50"
								value="${bonusPerson.RETURN_VALUE}"  style="text-align:right;"/>
						</c:if>
					</td>
					<td>
					<input name="START_MONTH_${bonusPerson.PARAM_NO}" type="text" maxlength="10" size="10"
										value="${bonusPerson.START_MONTH}" />
					</td>
					<td>
					<input name="END_MONTH_${bonusPerson.PARAM_NO}" type="text" maxlength="10" size="10"
										value="${bonusPerson.END_MONTH}" />
					</td>							
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/bonus/updateBonusInputItemDataPersonInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</form>
</div>