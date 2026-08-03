<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/insurance/updateInsuranceInputItemDataInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		        <input name="PARAM_NO" type="hidden" value="${isInputItemDataInfo.PARAM_NO }"/>
				<input name="PARAM_DATA_NO" type="hidden" value="${isInputItemDataInfo.PARAM_DATA_NO }"/>
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt><spring:message code="pa.insurance.title.companyID"/><!--公司ID-->:</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID" style="width:180px; position: static; visibility: inherit;">
				 	<c:forEach items="${cpnyList}" var="cpny">
				 		<option value="${cpny.CPNY_ID}" <c:if test="${isInputItemDataInfo.CPNY_ID eq cpny.CPNY_ID}">selected</c:if>>${cpny.CPNY_NAME_ZH}</option>
					</c:forEach>
				</select>
				</dd>
			
			</dl>
			
			<dl>
				<dt><spring:message code="pa.insurance.title.dataValue"/><!--数值-->:</dt>
				<dd>
					<input type="text" name="RETURN_VALUE" value="${isInputItemDataInfo.RETURN_VALUE }" class="textInput required"  style="text-align:right;"/>
				</dd>
				
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.filed1ChineseName"/><!--字段1中文-->:</dt>
				<dd>
					<input type="text" name="FIELD1_VALUE_ZH" value="${isInputItemDataInfo.FIELD1_VALUE_ZH }" class="textInput required">
				</dd>
				
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.filed2ChineseName"/><!--字段2中文-->:</dt>
				<dd>
					<input type="text" name="FIELD2_VALUE_ZH" value="${isInputItemDataInfo.FIELD2_VALUE_ZH }" class="textInput required">
				</dd>
				
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.filed1EnglishName"/><!--字段1英文-->:</dt>
				<dd>
					<input type="text" name="FIELD1_VALUE_EN" value="${isInputItemDataInfo.FIELD1_VALUE_EN }" class="textInput required">
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.filed2EnglishName"/><!--字段2英文-->:</dt>
				<dd>
				    <input type="text" name="FIELD2_VALUE_EN" value="${isInputItemDataInfo.FIELD2_VALUE_EN }" class="textInput required">
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.filed1KoreaName"/><!--字段1韩文-->:</dt>
				<dd>
					<input type="text" name="FIELD1_VALUE_KR" value="${isInputItemDataInfo.FIELD1_VALUE_KR }" class="textInput required">
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.filed2KoreaName"/><!--字段2韩文-->:</dt>
				<dd>
					<input type="text" name="FIELD2_VALUE_KR" value="${isInputItemDataInfo.FIELD2_VALUE_KR }" class="textInput required">
				</dd>				
			</dl>			
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>