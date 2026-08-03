<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback(form,callback) {	
	var $form = $("#addInsurancePersonalInputView");
	if (!$form.valid()) {
		return false;
	}
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			break;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.pa.insurance.pleaseInputDataFirst"/>');
		return false;
	}
	if (confirm ('<spring:message code="alert.message.pa.insurance.confirmSubmit"/>')){
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
	<form id="addInsurancePersonalInputView" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" 
	      action="/pa/insurance/updateInsuranceInputItemDataPersonInfo" method="post" rel="">
		<div class="formBar">
			<tr>
				<td align="center">
				  <b><!--姓名-->&nbsp;&nbsp;
				  <spring:message code="public.title.name"/>：</b>
				  ${insuranceInputItemPersonInfo.LOCAL_NAME}
				</td>
				<td align="center"><!--工号-->
				  <b><spring:message code="public.title.empId"/>：</b>${insuranceInputItemPersonInfo.EMPID}
				</td>
				<td align="center">
				  <b><!--部门-->
				  <spring:message code="public.title.deptName"/>：</b>
				  ${insuranceInputItemPersonInfo.DEPT_NAME}
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
	
	<table class="table" width="99%" layoutH="85">
		<thead>
			<tr>
				<th width="10%"></th>
				<th width="20%"><spring:message code="pa.insurance.title.projectName"/><!--项目名称--></th>
				<th width="10%"><spring:message code="pa.insurance.title.dataValue"/><!--数值--></th>
				<th width="15%"><spring:message code="pa.insurance.title.startMonth"/><!--开始月--></th>
				<th width="15%"><spring:message code="pa.insurance.title.endMonth"/><!--结束月--></th>
				<th width="30%"><spring:message code="hr.viewPromote.title.REMARK"/><!--备注--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${insuranceInputItemPersonList}" var="insuranceInputItemPerson">
				<tr target="sid" rel="${insuranceInputItemPerson.PARAM_NO}">
					<input type="hidden" id="PARAM_DATA_NO" name="PARAM_DATA_NO_${insuranceInputItemPerson.PARAM_NO}" value="${insuranceInputItemPerson.PARAM_DATA_NO}" />
					<input type="hidden" id="PERSON_ID" name="PERSON_ID_${insuranceInputItemPerson.PARAM_NO}" value="${insuranceInputItemPersonInfo.PERSON_ID}" />
					<input type="hidden" id="CPNY_ID" name="CPNY_ID_${insuranceInputItemPerson.PARAM_NO}" value="${insuranceInputItemPersonInfo.CPNY_ID}" />
					<td style="text-align:center">
						<input type="checkbox" id="c1" name="c1" value="${insuranceInputItemPerson.PARAM_NO}" />
					</td>
					<td>${insuranceInputItemPerson.ITEM_NAME}</td>
					<td>
						<c:if test="${empty insuranceInputItemPerson.RETURN_VALUE}">
							<input name="RETURN_VALUE_${insuranceInputItemPerson.PARAM_NO}" type="text" maxlength="200" alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' value="0"  style="text-align:right;"/>
						</c:if>
						<c:if test="${not empty insuranceInputItemPerson.RETURN_VALUE}">
							<input name="RETURN_VALUE_${insuranceInputItemPerson.PARAM_NO}" type="text" maxlength="50" value="${insuranceInputItemPerson.RETURN_VALUE}"  style="text-align:right;"/>
						</c:if>
					</td>
					<td>
						<input name="START_MONTH_${insuranceInputItemPerson.PARAM_NO}" type="text" maxlength="10" size="10" value="${insuranceInputItemPerson.START_MONTH}" />
					</td>
					<td>
						<input name="END_MONTH_${insuranceInputItemPerson.PARAM_NO}" type="text" maxlength="10" size="10" value="${insuranceInputItemPerson.END_MONTH}" />
					</td>
					<td>
						<input name="REMARK_${insuranceInputItemPerson.PARAM_NO}" type="text" maxlength="30" size="30" value="${insuranceInputItemPerson.REMARK}" />
					</td>						
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</form>
	<c:set value="/pa/insurance/addInsurancePersonalInputView?seach_CPNY_ID=${cpnyID}&seach_PERSON_ID=${personID}" var="pageUrl"/>
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
</div>