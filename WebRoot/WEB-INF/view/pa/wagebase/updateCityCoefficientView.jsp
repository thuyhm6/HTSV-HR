<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 
<div class="pageContent">
	<form method="post" action="/pa/wagebase/updateCityCoefficientInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label><spring:message code="pa.wagebase.title.workArea"/><!-- 工作地 -->:</label>
				<ait:SelectSyCodeByCpnyID name="S_WORK_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all" selected="${cityCoefficient.S_WORK_AREA}"/>
				<input name="SS_WORK_AREA" value="${cityCoefficient.S_WORK_AREA}" type="hidden">
			</p>
			<p>
				<label><spring:message code="pa.wagebase.title.socialArea"/><!--社保地-->:</label>
				<ait:SelectSyCodeByCpnyID name="E_WORK_AREA" parentNo="4604" cnpyID="${defaultCpny}" limit="all" selected="${cityCoefficient.E_WORK_AREA}"/>
				<input name="EE_WORK_AREA" value="${cityCoefficient.E_WORK_AREA}" type="hidden">
			</p>
			<p>
				<label><spring:message code="pa.wagebase.title.cityCoefficient"/><!--同城系数-->:</label>
				<select name="CITY_COEFFICIENT">
					<option value="0.5" <c:if test="${cityCoefficient.COEFFICIENT == 0.5}">selected</c:if>>0.5</option>
					<option value="1" <c:if test="${cityCoefficient.COEFFICIENT == 1}">selected</c:if>>1</option>
				</select>
			</p>
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
