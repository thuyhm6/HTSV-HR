<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function addBonusInputItemDataInfo(form, callback) {
	
	var $form = $("#addBonusInputItemDataInfoForm");
	
	if (!$form.valid()) {
		return false;
	}

	var startMonth = document.getElementById("START_MONTH").value;

	if (!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(startMonth)){
         alert('<spring:message code="alert.message.pa.insurance.startMonthIsNotCorrect"/>');
         return false;
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
	<form id="addBonusInputItemDataInfoForm"
		name="addBonusInputItemDataInfoForm" method="post" action="/pa/bonus/addBonusInputItemDataInfo?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" class="pageForm required-validate" onsubmit="return addBonusInputItemDataInfo(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<c:if
				test="${bonusInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
				<dl>
					<dt>
						<spring:message code="pa.insurance.title.company"/><!--公司-->
					</dt>
					<dd>
						<select name="CPNY_ID" id="CPNY_ID"
							style="width: 180px; position: static; visibility: inherit;">
							<c:forEach items="${cpnyList}" var="cpny">
								<option value="${cpny.CPNY_ID}"
									<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if>
									disabled="true">
									${cpny.CONTENT}
								</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl>
					<dt>
						<spring:message code="public.title.empId"/><!--工号-->
					</dt>
					<dd>
						<input id="personId" name="dwz.person.personId" value=""
							type="hidden" lookupGroup="person" />
						<input id="empId" name="dwz.person.empId" value="" type="text"
							lookupGroup="person" chass="textInput required" readonly="true" />
						<a class="btnLook"
							href="/pa/insurance/viewAddInsurancePersonalDataList?pageNum=1"
							lookupGroup="person" target="dialog" mask="true" width="800"
							height="400"><hi:text key='<spring:message code="pa.insurance.title.searchAndBringBack"/>' /><!--查找带回-->
						</a>
					</dd>

				</dl>
				<dl>
					<dt>
						<spring:message code="pa.insurance.title.dataValue"/><!--数值-->:
					</dt>
					<dd>
						<input type="text" name="RETURN_VALUE" class="textInput required" min="0" style="text-align:right;">
					</dd>
				</dl>
				<dl>
					<dt>
						<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->:
					</dt>
					<dd>
						<input type="text" name="START_MONTH" id="START_MONTH"
							class="textInput required">
						<font color="red"><spring:message code="pa.insurance.title.exampleMonth"/><!--例:2012年2月---201202(下同)--></font>
					</dd>
				</dl>
				<dl>
					<dt>
						<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
					</dt>
					<dd>
						<input name="REMARK" type="text" maxlength="200"/>
					</dd>
				</dl>
				<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
		</div>
		</c:if>
		<c:if
			test="${bonusInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
			<c:choose>
				<c:when
					test="${bonusInputItemParamInfo.DISTINCT_FIELD_2ND_NAME eq null}">
					<dl>
						<dt>
							<spring:message code="pa.insurance.title.company"/><!--公司-->
						</dt>
						<dd>
							<select name="CPNY_ID" id="CPNY_ID"
								style="width: 180px; position: static; visibility: inherit;">
								<c:forEach items="${cpnyList}" var="cpny">
									<option value="${cpny.CPNY_ID}"
										<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if>
										disabled="true">
										${cpny.CONTENT}
									</option>
								</c:forEach>
							</select>
						</dd>
					</dl>

					<dl>
						<dt>
							${bonusInputItemParamInfo.DISTINCT_FIELD_NAME}:
						</dt>
						<dd>
							<select name="FIELD1_VALUE">
								<c:forEach items="${distinctList}" var="distinct">
									<option value="${distinct.FIELD_VALUE}">
										${distinct.FIELD_NAME}
									</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>
							<spring:message code="pa.insurance.title.dataValue"/><!--数值-->:
						</dt>
						<dd>
							<input type="text" name="RETURN_VALUE" class="textInput required" min="0" style="text-align:right;">
						</dd>
					</dl>
					<dl>
						<dt>
							<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->:
						</dt>
						<dd>
							<input type="text" name="START_MONTH" id="START_MONTH"
								class="textInput required">
							<font color="red"><spring:message code="pa.insurance.title.exampleMonth"/><!--例:2012年2月---201202(下同)--></font>
						</dd>
					</dl>
					<dl>
						<dt>
							<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
						</dt>
						<dd>
							<input name="REMARK" type="text" maxlength="200"/>
						</dd>
					</dl>
					<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
				</c:when>
				<c:otherwise>
					<dl>
						<dt>
							<spring:message code="pa.insurance.title.company"/><!--公司-->
						</dt>
						<dd>
							<select name="CPNY_ID" id="CPNY_ID"
								style="width: 180px; position: static; visibility: inherit;">
								<c:forEach items="${cpnyList}" var="cpny">
									<option value="${cpny.CPNY_ID}"
										<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if>
										disabled="true">
										${cpny.CONTENT}
									</option>
								</c:forEach>
							</select>
						</dd>
					</dl>

					<dl>
						<dt>
							${bonusInputItemParamInfo.DISTINCT_FIELD_NAME}:
						</dt>
						<dd>
							<select name="FIELD1_VALUE">
								<c:forEach items="${distinctList}" var="bonus">
									<option value="${bonus.FIELD_VALUE}">
										${bonus.FIELD_NAME}
									</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>
							${bonusInputItemParamInfo.DISTINCT_FIELD_2ND_NAME}:
						</dt>
						<dd>
							<select name="FIELD2_VALUE">
								<c:forEach items="${distinctList2}" var="bonus">
									<option value="${bonus.FIELD_VALUE}">
										${bonus.FIELD_NAME}
									</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>
							<spring:message code="pa.insurance.title.dataValue"/><!--数值-->:
						</dt>
						<dd>
							<input type="text" name="RETURN_VALUE" class="textInput required" min="0" style="text-align:right;">
						</dd>
					</dl>
					<dl>
						<dt>
							<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->:
						</dt>
						<dd>
							<input type="text" name="START_MONTH" id="START_MONTH"
								class="textInput required">
							<font color="red"><spring:message code="pa.insurance.title.exampleMonth"/><!--例:2012年2月---201202(下同)--></font>
						</dd>
					</dl>
					<dl>
						<dt>
							<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
						</dt>
						<dd>
							<input name="REMARK" type="text" maxlength="200"/>
						</dd>
					</dl>
					<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
				</c:otherwise>
			</c:choose>
		</c:if>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="pa.insurance.title.submit"/><!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle"/><!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>