<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/bonus/addBonusComputeItemParamInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.computeItem"/><!--计算项目-->:
				</dt>
				<dd>
					<select name="ITEM_NO" id="ITEM_NO"
						style="width: 180px; position: static; visibility: inherit;">
						<c:forEach items="${bonusComputeItemList}" var="cpny">
							<option value="${cpny.ITEM_NO}">
								${cpny.ITEM_NAME}
							</option>
						</c:forEach>
					</select>
				</dd> 
			</dl>

			<dl>
				<dt>
					<spring:message code="pa.insurance.title.companyID"/><!--公司ID-->:
				</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID"
						style="width: 180px; position: static; visibility: inherit;">
						<c:forEach items="${cpnyList}" var="cpny">
							<option value="${cpny.CPNY_ID}" <c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">${cpny.CONTENT }</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.precision"/><!--精度-->:
				</dt>
				<dd>
					<input name="PRICISION" type="text" size="30" value="2"
						class="required number" />
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.carry"/><!--进位-->:
				</dt>
				<dd>
					<input name="CARRY_BIT" type="text" size="30" value="0.005"
						class="required" />
				</dd>
			</dl>

		<dl>
				<dt><spring:message code="pa.insurance.title.relatedWithSalary"/><!--是否与工资有关联-->:</dt>
				<dd>
					<select name="PA_RELEVANCE_FLAG" id="PA_RELEVANCE_FLAG" style="width:180px; position: static; visibility: inherit;">
						<option value="1">
						<spring:message code="pa.insurance.title.yes"/><!--是--></option>
						<option value="0">
						<spring:message code="pa.insurance.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
				<dl>
				<dt><spring:message code="pa.title.message.bonusTitle.bonusmonth"/><!--关联工资月-->: </dt>
				<dd>
					<select name="PA_MONTH" id="PA_MONTH" style="width:180px; position: static; visibility: inherit;">
					
						<option value="-1"  >
						-1</option>
						<option value="-2"  >
						-2</option>
						<option value="-3"  >
						-3</option>
						<option value="0" selected >
						0</option>
						<option value="1"  >
						1</option>
						<option value="2"  >
						2</option>
						<option value="3"  >
						3</option>
					</select>
				</dd>
			</dl>	
			
		</div>
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