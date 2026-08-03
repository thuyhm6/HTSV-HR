<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/insurance/updateInsuranceInputItemInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt><spring:message code="pa.insurance.title.projectID"/><!--项目ID-->:</dt>
				<dd>
					<input type="text" name="PARAM_ID" value="${insuranceInputItemInfo.PARAM_ID}" size="30"  class="required alphanumeric" maxlength="30"/>
					<input type="hidden" name="NO" value="${insuranceInputItemInfo.PARAM_NO}" size="30"  />
				</dd>
			
			</dl>
			
			<ait:SyLanguage languageNo="${insuranceInputItemInfo.PARAM_NO}"/>
			<dl>
				<dt><spring:message code="pa.insurance.title.dataType"/><!--数据类型-->:</dt>
				<dd>
					<select id="DATA_TYPE" name="DATA_TYPE">
						<option value="NUMBER(14,4)"
							<c:if test="${insuranceInputItemInfo.DATA_TYPE == 'NUMBER(14,4)' }">selected</c:if>>
							<spring:message code="pa.insurance.title.numberType"/><!--数字类型-->
						</option>
						<option value="VARCHAR(100)"
							<c:if test="${insuranceInputItemInfo.DATA_TYPE == 'VARCHAR(100)' }">selected</c:if>>
							<spring:message code="pa.insurance.title.varcharType"/><!--字符类型-->
						</option>
					</select>
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.description"/><!--描述-->:</dt>
				<dd>
					<input type="text" name="DESCR" value="${insuranceInputItemInfo.DESCR}" size="30"  />
				</dd>				
			</dl>						
		</div>		
		<div class="formBar" layoutH="260">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>	
</div>
