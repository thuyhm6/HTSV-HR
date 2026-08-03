<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/salary/updatePaInputItemDataInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label><spring:message code="pa.insurance.title.orderNo"/><!--序号--></label>
				<input name="PARAM_NO" type="hidden" value="${paInputItemDataInfo.PARAM_NO }"/>
				<input name="PARAM_DATA_NO" type="hidden" value="${paInputItemDataInfo.PARAM_DATA_NO }"/>
				<label>${PARAM_NO}</label>
			</p>
			
			<p>
				<label><spring:message code="pa.insurance.title.companyID"/><!--公司ID--></label>
				<select name="CPNY_ID" id="CPNY_ID" style="width:180px; position: static; visibility: inherit;">
				 	<c:forEach items="${cpnyList}" var="cpny">
				 		<option value="${cpny.CPNY_ID}" <c:if test="${paInputItemDataInfo.CPNY_ID eq cpny.CPNY_ID}">selected</c:if>>${cpny.CPNY_NAME_ZH}</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.dataValue"/><!--数值--></label>
				<input type="text" name="RETURN_VALUE" value="${paInputItemDataInfo.RETURN_VALUE }" class="textInput required" style="text-align:right;">
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.filed1ChineseName"/><!--字段1中文--></label>
				<input type="text" name="FIELD1_VALUE_ZH" value="${paInputItemDataInfo.FIELD1_VALUE_ZH }" class="textInput required">
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.filed2ChineseName"/><!--字段2中文--></label>
				<input type="text" name="FIELD2_VALUE_ZH" value="${paInputItemDataInfo.FIELD2_VALUE_ZH }" class="textInput required">
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.filed1EnglishName"/><!--字段1英文--></label>
				<input type="text" name="FIELD1_VALUE_EN" value="${paInputItemDataInfo.FIELD1_VALUE_EN }" class="textInput required">
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.filed2EnglishName"/><!--字段2英文--></label>
				<input type="text" name="FIELD2_VALUE_EN" value="${paInputItemDataInfo.FIELD2_VALUE_EN }" class="textInput required">
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.filed1KoreaName"/><!--字段1韩文--></label>
				<input type="text" name="FIELD1_VALUE_KR" value="${paInputItemDataInfo.FIELD1_VALUE_KR }" 
				       class="textInput required">
			</p>
			<p>
				<label><spring:message code="pa.insurance.title.filed2KoreaName"/><!--字段2韩文--></label>
				<input type="text" name="FIELD2_VALUE_KR" value="${paInputItemDataInfo.FIELD2_VALUE_KR }" 
				       class="textInput required">
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