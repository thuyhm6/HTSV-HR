<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback(form,callback) {	
	var $form = $("#addPaPersonalInput");
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
	<form id="addPaPersonalInput" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);" 
		action="/pa/salary/updatePaInputItemDataPersonInfo" method="post" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		
		<div class="formBar">
			<tr>
				<td align="center"><!--姓名-->&nbsp;&nbsp;
				  <b><spring:message code="public.title.name"/>：
				  </b>${paInputItemPersonInfo.LOCAL_NAME}
				</td>
				<td align="center"><!--工号-->
				  <b><spring:message code="public.title.empId"/>：</b>${paInputItemPersonInfo.EMPID}
				</td>
				<td align="center"><!--部门-->
				  <b><spring:message code="public.title.deptName"/>：</b>
				  ${paInputItemPersonInfo.DEPT_NAME}
				</td>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="c1" selectType="invert">
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
				<c:forEach items="${paInputItemPersonList}" var="paInputItemPerson">
					<tr target="sid" rel="${paInputItemPerson.PARAM_NO}">
						<input type="hidden" id="PARAM_DATA_NO" name="PARAM_DATA_NO_${paInputItemPerson.PARAM_NO}" value="${paInputItemPerson.PARAM_DATA_NO}" />
						<input type="hidden" id="PERSON_ID" name="PERSON_ID_${paInputItemPerson.PARAM_NO}" value="${paInputItemPersonInfo.PERSON_ID}" />
						<input type="hidden" id="CPNY_ID" name="CPNY_ID_${paInputItemPerson.PARAM_NO}" value="${paInputItemPersonInfo.CPNY_ID}" />
						<td>
							<input type="checkbox" id="c1" name="c1" value="${paInputItemPerson.PARAM_NO}" />
						</td>
						<td>${paInputItemPerson.ITEM_NAME}</td>
						<td>
							<c:if test="${empty paInputItemPerson.RETURN_VALUE}">
								<%--
								<input name="RETURN_VALUE_${paInputItemPerson.PARAM_NO}" type="text" maxlength="200" 
								alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' value="0" />
								--%>
								<ait:inputText name="RETURN_VALUE_${paInputItemPerson.PARAM_NO}" id="RETURN_VALUE_${paInputItemPerson.PARAM_NO}" 
									inputType="number" maxLength="200" alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' value="0"/>
							</c:if>
							<c:if test="${not empty paInputItemPerson.RETURN_VALUE}">
								<%--<input name="RETURN_VALUE_${paInputItemPerson.PARAM_NO}" type="text" maxlength="50" value="${paInputItemPerson.RETURN_VALUE}" />--%>
								<ait:inputText name="RETURN_VALUE_${paInputItemPerson.PARAM_NO}" id="RETURN_VALUE_${paInputItemPerson.PARAM_NO}" 
									inputType="number" maxLength="50" value="${paInputItemPerson.RETURN_VALUE}"/>
							</c:if>
						</td>
						<td>
							<%--<input name="START_MONTH_${paInputItemPerson.PARAM_NO}" type="text" maxlength="10" size="10" value="${paInputItemPerson.START_MONTH}" />--%>
							<ait:inputText name="START_MONTH_${paInputItemPerson.PARAM_NO}" id="START_MONTH_${paInputItemPerson.PARAM_NO}" 
								inputType="date" maxLength="10" inputSize="10" value="${paInputItemPerson.START_MONTH}"/>
						</td>
						<td>
							<%--<input name="END_MONTH_${paInputItemPerson.PARAM_NO}" type="text" maxlength="10" size="10" value="${paInputItemPerson.END_MONTH}" />--%>
							<ait:inputText name="END_MONTH_${paInputItemPerson.PARAM_NO}" id="END_MONTH_${paInputItemPerson.PARAM_NO}" 
								inputType="date" maxLength="10" inputSize="10" value="${paInputItemPerson.END_MONTH}"/>
						</td>
						<td>
							<input name="REMARK_${paInputItemPerson.PARAM_NO}" type="text" maxlength="30"  size="30" value="${paInputItemPerson.REMARK}" />
						</td>						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<form id="pagerForm" method="post" action="/pa/salary/addPaPersonalInputView?seach_CPNY_ID=${cpnyID}&seach_PERSON_ID=${personID}">
		<div class="panelBar">
			<div class="pages">
				<span><spring:message code="public.title.view"/><!--显示--></span>
					<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
						<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
						<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
						<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					</select>
				<span><spring:message code="public.title.tiao"/><!--条-->，<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
			</div>
			<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
		</div>
	</form>
</div>