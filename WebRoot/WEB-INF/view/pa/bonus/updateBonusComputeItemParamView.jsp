<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/bonus/updateBonusComputeItemParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<dl>
				<dt><spring:message code="pa.insurance.title.computeItem"/><!--计算项目-->:</dt>
				<dd>
				<label>${bonusComputeItemParamInfo.ITEM_NO }</label>
				<input name="PARAM_NO" type="hidden" value="${bonusComputeItemParamInfo.PARAM_NO }"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.insurance.title.companyID"/><!--公司ID-->:</dt>
				<dd>
				<select name="CPNY_ID" id="CPNY_ID" style="width:180px; position: static; visibility: inherit;">
				 	<c:forEach items="${cpnyList}" var="cpny">
				 		<option value="${cpny.CPNY_ID}" <c:if test="${bonusComputeItemParamInfo.CPNY_ID==cpny.CPNY_ID }" > selected </c:if> >${cpny.CONTENT}</option>
					</c:forEach>
				</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.precision"/><!--精度-->:</dt>
				<dd><input type="text" name="PRICISION" class="number" value="${bonusComputeItemParamInfo.PRICISION }"></dd>
			</dl>
			<dl>
				<label><spring:message code="pa.insurance.title.carry"/><!--进位-->:</label>
				<input type="text" name="CARRY_BIT" class="number" value="${bonusComputeItemParamInfo.CARRY_BIT }">
			</dl>
			<dl>
				<label><spring:message code="pa.title.message.bonusTitle.leixing"/><!-- 奖金类型 -->:  </label>
					<select name="BONUS_NO" id="BONUS_NO">
									<option value="">
										<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
										<!-- 请选择 -->
									</option>
									<c:forEach items="${bonusList}" var="bonusList">
										<option value="${bonusList.TYPE_ID}">
											${bonusList.TYPE_NAME}
										</option>
									</c:forEach>
								</select>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.insurance.title.relatedWithSalary"/><!--是否与工资有关联-->:</dt>
				<dd>
					<select name="PA_RELEVANCE_FLAG" id="PA_RELEVANCE_FLAG" style="width:180px; position: static; visibility: inherit;">
						<option value="1" <c:if test="${bonusComputeItemParamInfo.PA_RELEVANCE_FLAG==1 }" >selected</c:if> >
						<spring:message code="pa.insurance.title.yes"/><!--是--></option>
						<option value="0" <c:if test="${bonusComputeItemParamInfo.PA_RELEVANCE_FLAG==0 }" >selected</c:if>>
						<spring:message code="pa.insurance.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
				<dl>
				<dt><spring:message code="pa.title.message.bonusTitle.bonusmonth"/><!--关联工资月-->: </dt>
				<dd>
					<select name="PA_MONTH" id="PA_MONTH" style="width:180px; position: static; visibility: inherit;">
					
						<option value="-1" <c:if test="${bonusComputeItemParamInfo.PA_BONUS_MONTH==-1 }" >selected </c:if> >
						-1</option>
						<option value="-2" <c:if test="${bonusComputeItemParamInfo.PA_BONUS_MONTH==-2 }" >selected </c:if>>
						-2</option>
						<option value="-3" <c:if test="${bonusComputeItemParamInfo.PA_BONUS_MONTH==-3 }" >selected </c:if>>
						-3</option>
						<option value="0" <c:if test="${bonusComputeItemParamInfo.PA_BONUS_MONTH==0 }" >selected </c:if>>
						0</option>
						<option value="1" <c:if test="${bonusComputeItemParamInfo.PA_BONUS_MONTH==1 }" >selected </c:if>>
						1</option>
						<option value="2" <c:if test="${bonusComputeItemParamInfo.PA_BONUS_MONTH==2 }" >selected </c:if>>
						2</option>
						<option value="3" <c:if test="${bonusComputeItemParamInfo.PA_BONUS_MONTH==3 }" >selected </c:if>>
						3</option>
					</select>
				</dd>
			</dl>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">
                    <spring:message code="public.title.cancle"/><!--取消--></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>