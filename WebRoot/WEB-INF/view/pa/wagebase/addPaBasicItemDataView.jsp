<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post"
		action="/pa/wagebase/addPaBasicInputItemDataInfo?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<c:if test="${paBasicItemDataInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
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
							href="/pa/wagebase/viewAddPaBasicPersonalDataList?pageNum=1"
							lookupGroup="person" target="dialog" mask="true" width="800"
							height="400"><hi:text key='<spring:message code="pa.insurance.title.lookUpAndBack"/>' />
						</a>
					</dd>

				</dl>
				<dl>
					<dt>
						<spring:message code="pa.insurance.title.dataValue"/><!--数值-->:
					</dt>
					<dd>
						<input type="text" name="RETURN_VALUE" class="textInput required" 
						min="-99999999999999" style="text-align:right;">
					</dd>
				</dl>
				<dl>
					<dt>
						<spring:message code="public.title.startDate"/><!-- 开始日期 -->:
					</dt>
					<dd>
						<input name="START_DATE" type="text" maxlength="200"
							class="date required" />
						<%--<ait:inputText name="START_DATE" id="START_DATE" inputType="date" maxLength="200"/>--%>
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
		<c:if test="${paBasicItemDataInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
			<c:choose>
				<c:when
					test="${paBasicItemDataInfo.DISTINCT_FIELD_2ND_NAME eq null}">
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
							${paBasicItemDataInfo.DISTINCT_FIELD_NAME}:
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
							<input type="text" name="RETURN_VALUE" class="textInput required" min="-99999999999999" style="text-align:right;">
						</dd>
					</dl>
					<dl>
						<dt>
							<spring:message code="public.title.startDate"/><!-- 开始日期 -->:
						</dt>
						<dd>
							<input name="START_DATE" type="text" maxlength="200"
								class="date required" />
							<%--<ait:inputText name="START_DATE" id="START_DATE" inputType="date"/>--%>
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
							${paBasicItemDataInfo.DISTINCT_FIELD_NAME}:
						</dt>
						<dd>
							<select name="FIELD1_VALUE">
								<c:forEach items="${distinctList}" var="paBasic">
									<option value="${paBasic.FIELD_VALUE}">
										${paBasic.FIELD_NAME}
									</option>
								</c:forEach>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>
							${paBasicItemDataInfo.DISTINCT_FIELD_2ND_NAME}:
						</dt>
						<dd>
							<select name="FIELD2_VALUE">
								<c:forEach items="${distinctList2}" var="paBasic">
									<option value="${paBasic.FIELD_VALUE}">
										${paBasic.FIELD_NAME}
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
							<input type="text" name="RETURN_VALUE" class="textInput required" min="-99999999999999" style="text-align:right;">
						</dd>
					</dl>
					<dl>
						<dt>
							<spring:message code="public.title.startDate"/><!-- 开始日期 -->:
						</dt>
						<dd>
							<input name="START_DATE" type="text" maxlength="200"
								class="date required" />
							<%--<ait:inputText name="START_DATE" id="START_DATE" inputType="date" maxLength="200"/>--%>
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
								<spring:message code="public.title.submit"/><!-- 提交 -->
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