<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/insurance/updateInsuranceInputItemParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input name="PARAM_NO" type="hidden" value="${insuranceInputItemParamInfo.PARAM_NO}" />
		<div class="pageFormContent nowrap" layoutH="97">

			<dl>
				<dt><spring:message code="pa.insurance.title.projectName"/><!--项目名称-->:</dt>
				<dd>
					${insuranceInputItemParamInfo.ALIAS_NAME } 
		            <input name="PARAM_NO" type="hidden" id="PARAM_NO" value="${insuranceInputItemParamInfo.PARAM_NO }"/>
				</dd>
			</dl>
		    
		    <dl>
				<dt><spring:message code="pa.insurance.title.company"/><!--公司-->:</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID">
					 	<c:forEach items="${cpnyList}" var="cpny">
					 		<option value="${cpny.CPNY_ID}" <c:if test="${insuranceInputItemParamInfo.CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">${cpny.CONTENT}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
		    <dl>
				<dt><spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:-->:</dt>
				<dd>
					<select name="DISTINCT_FIELD" id="DISTINCT_FIELD">
<!-- 		           	     <option value=""><spring:message code="pa.insurance.title.pleaseChoose"/>请选择:</option> -->
						 <c:forEach items="${distinctFieleList}" var="item">
						 	<option value="${item.DISTINCT_FIELD}" <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq item.DISTINCT_FIELD}">selected</c:if> >${item.FIELD_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.insurance.title.distinctName2"/><!--区分项目2:-->:</dt>
				<dd>
					<select name="DISTINCT_FIELD_2ND" id="DISTINCT_FIELD_2ND">
		           	     <option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
						 <c:forEach items="${distinctFieleList}" var="item">
						 	<option value="${item.DISTINCT_FIELD}" <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq item.DISTINCT_FIELD}">selected</c:if> >${item.FIELD_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>       
			
			<dl>
				<dt><spring:message code="pa.insurance.title.defaltValue"/><!--默认值:-->:</dt>
				<dd>
					<input name="DEFAULT_VAL" type="text" id="DEFAULT_VAL"  value="${insuranceInputItemParamInfo.DEFAULT_VAL}" 
					       class="required number"/>
				</dd>
			</dl> 
			<dl>
				<dt><spring:message code="pa.insurance.title.applyFlag"/></dt>
				<dd>
					<select name="APPLY_FLAG" id="APPLY_FLAG" class="select">
						<%-- <option><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>--%>
						<option <c:if test="${insuranceInputItemParamInfo.APPLY_FLAG ne 'Y' }">selected</c:if> value="N" >N</option>
						<option <c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' }">selected</c:if> value="Y">Y</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ACTIVITY" id="PA_RELEVANCE_FLAG">
						<option value="1" <c:if test="${insuranceInputItemParamInfo.ACTIVITY eq 1 }">selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" <c:if test="${insuranceInputItemParamInfo.ACTIVITY ne 1 }">selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用-->
						</option>
					</select>
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
