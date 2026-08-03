<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function addInsuranceInputItemDataInfo(form, callback) {
	var $form = $("#addInsuranceInputItemDataInfoForm");
	if (!$form.valid()) {
		return false;
	}
	var startMonth = document.getElementById("START_MONTH").value;
	if (!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(startMonth)){
         alertMsg.error('<spring:message code="alert.message.pa.insurance.startMonthIsNotCorrect"/>');
         return false;
    }
	var endMonth = document.getElementById("END_MONTH").value;
	if(endMonth!=null && endMonth !=""){
		if (!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(endMonth)){
	        alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsNotCorrect"/>');
	        return false;
	    }
		//如果结束月份比开始月早， 请重新填写结束月！
	    if(endMonth < startMonth){
	    	alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
	        return false;
	    }
	}
    
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
</script>

<div class="pageContent">
	<form id="addInsuranceInputItemDataInfoForm" name="addInsuranceInputItemDataInfoForm" method="post"
		action="/pa/insurance/addInsuranceInputItemDataInfo?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}&ITEM_NO=${ITEM_NO}"
		class="pageForm required-validate" onsubmit="return addInsuranceInputItemDataInfo(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="55">
			<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
				<dl>
					<dt><!--公司-->
						<spring:message code="pa.insurance.title.company"/>
					</dt>
					<dd>
						<select name="CPNY_ID" id="CPNY_ID" style="width: 180px; position: static; visibility: inherit;">
							<c:forEach items="${cpnyList}" var="cpny">
								<option value="${cpny.CPNY_ID}"
									<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">
									${cpny.CONTENT}
								</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl>
					<dt><!--工号-->
						<spring:message code="public.title.empId"/>
					</dt>
					<dd>
						<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person" />
						<input id="empId" name="dwz.person.empId" value="" type="text"
							lookupGroup="person" chass="textInput required" readonly="true" />
						<a class="btnLook" href="/pa/insurance/viewAddInsurancePersonalDataList?pageNum=1"
							lookupGroup="person" target="dialog" mask="true" width="800" height="400">
							<hi:text key='<spring:message code="pa.insurance.title.searchAndBringBack"/>' /><!--查找带回-->
						</a>
					</dd>
				</dl>
				<dl>
					<dt><!--数值-->
						<spring:message code="pa.insurance.title.dataValue"/>:
					</dt>
					<dd>
						<input type="text" name="RETURN_VALUE" class="textInput required" min="-99999999999999" style="text-align:right;">
					</dd>
				</dl>
				<dl>
					<dt><!--开始月-->
						<spring:message code="pa.insurance.title.startMonth"/>:
					</dt>
					<dd>
						<input type="text" name="START_MONTH" id="START_MONTH" class="textInput required">
						<font color="red"><!--例:2012年2月---201202(下同)-->
							<spring:message code="pa.insurance.title.exampleMonth"/>
						</font>
					</dd>
				</dl>
				<dl>
					<dt><!--结束月-->
						<spring:message code="pa.insurance.title.endMonth"/>:
					</dt>
					<dd>
						<input type="text" name="END_MONTH" id="END_MONTH" class="textInput">
						<font color="red"><!--例:2012年2月---201202(下同)-->
							<spring:message code="pa.insurance.title.exampleMonth"/>
						</font>
					</dd>
				</dl>
				<dl>
					<dt><!--备注-->
						<spring:message code="hr.viewPromote.title.REMARK"/>
					</dt>
					<dd>
						<input name="REMARK" type="text" maxlength="200"/>
					</dd>
				</dl>
				<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
			</c:if>
			<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
				<c:choose>
					<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME eq null}">
						<dl>
							<dt>
								<spring:message code="pa.insurance.title.company"/><!--公司-->
							</dt>
							<dd>
								<select name="CPNY_ID" id="CPNY_ID" style="width: 180px; position: static; visibility: inherit;">
									<c:forEach items="${cpnyList}" var="cpny">
										<option value="${cpny.CPNY_ID}"
											<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">
											${cpny.CONTENT}
										</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>
								${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}:
							</dt>
							<dd>
								<select name="FIELD1_VALUE">
									<c:forEach items="${distinctList}" var="distinct">
										<option value="${distinct.FIELD_VALUE}">${distinct.FIELD_NAME}</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt><!--数值-->
								<spring:message code="pa.insurance.title.dataValue"/>:
							</dt>
							<dd>
								<input type="text" name="RETURN_VALUE" class="textInput required" min="-99999999999999" style="text-align:right;">
							</dd>
						</dl>
						<dl>
							<dt><!--开始月-->
								<spring:message code="pa.insurance.title.startMonth"/>:
							</dt>
							<dd>
								<input type="text" name="START_MONTH" id="START_MONTH" class="textInput required">
								<font color="red"><!--例:2012年2月---201202(下同)-->
									<spring:message code="pa.insurance.title.exampleMonth"/>
								</font>
							</dd>
						</dl>
						<dl>
							<dt><!--结束月-->
								<spring:message code="pa.insurance.title.endMonth"/>:
							</dt>
							<dd>
								<input type="text" name="END_MONTH" id="END_MONTH" class="textInput">
								<font color="red"><!--例:2012年2月---201202(下同)-->
									<spring:message code="pa.insurance.title.exampleMonth"/>
								</font>
							</dd>
						</dl>
						<dl>
							<dt><!--备注-->
								<spring:message code="hr.viewPromote.title.REMARK"/>
							</dt>
							<dd>
								<input name="REMARK" type="text" maxlength="200"/>
							</dd>
						</dl>
						<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
					</c:when>
					<c:otherwise>
						<dl>
							<dt><!--公司-->
								<spring:message code="pa.insurance.title.company"/>
							</dt>
							<dd>
								<select name="CPNY_ID" id="CPNY_ID" style="width: 180px; position: static; visibility: inherit;">
									<c:forEach items="${cpnyList}" var="cpny">
										<option value="${cpny.CPNY_ID}"
											<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">
											${cpny.CONTENT}
										</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
	
						<dl>
							<dt>${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}:</dt>
							<dd>
								<select name="FIELD1_VALUE">
									<c:forEach items="${distinctList}" var="insurance">
										<option value="${insurance.FIELD_VALUE}">
											${insurance.FIELD_NAME}
										</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME}:</dt>
							<dd>
								<select name="FIELD2_VALUE">
									<c:forEach items="${distinctList2}" var="insurance">
										<option value="${insurance.FIELD_VALUE}">
											${insurance.FIELD_NAME}
										</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt><!--数值-->
								<spring:message code="pa.insurance.title.dataValue"/>:
							</dt>
							<dd>
								<input type="text" name="RETURN_VALUE" class="textInput required" min="-99999999999999" style="text-align:right;">
							</dd>
						</dl>
						<dl>
							<dt><!--开始月-->
								<spring:message code="pa.insurance.title.startMonth"/>:
							</dt>
							<dd>
								<input type="text" name="START_MONTH" id="START_MONTH" class="textInput required">
								<font color="red"><!--例:2012年2月---201202(下同)-->
									<spring:message code="pa.insurance.title.exampleMonth"/>
								</font>
							</dd>
						</dl>
						<dl>
							<dt><!--结束月-->
								<spring:message code="pa.insurance.title.endMonth"/>:
							</dt>
							<dd>
								<input type="text" name="END_MONTH" id="END_MONTH" class="textInput">
								<font color="red"><!--例:2012年2月---201202(下同)-->
									<spring:message code="pa.insurance.title.exampleMonth"/>
								</font>
							</dd>
						</dl>
						<dl>
							<dt><!--备注-->
								<spring:message code="hr.viewPromote.title.REMARK"/>
							</dt>
							<dd>
								<input name="REMARK" type="text" maxlength="200"/>
							</dd>
						</dl>
						<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!--保存-->
								<spring:message code="pa.insurance.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close"><!--取消-->
								<spring:message code="public.title.cancle"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
