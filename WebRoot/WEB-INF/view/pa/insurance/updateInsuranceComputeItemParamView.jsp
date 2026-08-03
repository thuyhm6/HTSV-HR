<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/insurance/updateInsuranceComputeItemParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt><spring:message code="pa.insurance.title.projectName"/><!--项目名称-->:</dt>
				<label>${isComputeItemParam.ALIAS_NAME }</label>
				<dd><input name="PARAM_NO" type="hidden" value="${isComputeItemParam.PARAM_NO }"/>
					<input name="ITEM_NO" type="hidden" value="${isComputeItemParam.ITEM_NO }"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.insurance.title.companyID"/><!--公司ID-->:</dt>
				<dd><select name="CPNY_ID" id="CPNY_ID">
				 	<c:forEach items="${cpnyList}" var="cpny">
				 		<option value="${cpny.CPNY_ID}" <c:if test="${isComputeItemParam.CPNY_ID==cpny.CPNY_ID }" > selected </c:if> disabled="true"> ${cpny.CONTENT}</option>
					</c:forEach>
				</select>
				</dd>
			</dl>
			<!--<dl>
				<dt><spring:message code="pa.insurance.title.ifRelatedWithBonus"/>是否与奖金有关联:</dt>
				<dd><select name="PA_RELEVANCE_FLAG" id="PA_RELEVANCE_FLAG">
					<option value="1" <c:if test="${isComputeItemParam.PA_RELEVANCE_FLAG==1 }" >selected</c:if> >
					<spring:message code="pa.insurance.title.yes"/><!--是</option>
					<option value="0" <c:if test="${isComputeItemParam.PA_RELEVANCE_FLAG==0 }" >selected</c:if>>
					<spring:message code="pa.insurance.title.no"/><!--否</option>
				</select>
				</dd>
			</dl>
			-->
			<dl>
				<dt><spring:message code="pa.insurance.title.precision"/><!--精度-->:</dt>
				<dd><input type="text" name="PRICISION" class="required digits" value="${isComputeItemParam.PRICISION }"></dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.carry"/><!--进位-->:</dt>
				<dd><input type="text" name="CARRY_BIT" class="required number" value="${isComputeItemParam.CARRY_BIT }"></dd>
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