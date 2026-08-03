<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
  <div class="pageContent">
     <form method="post" action="/pa/bonus/updateBonusTypeParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">

		<div class="pageFormContent nowrap" layoutH="58">        
		   <dl>
				<dt><spring:message code="pa.bonus.title.bonusTypeName"/><!--奖金类型名称 -->:</dt>
				<dd>
				    <label>${bonusTypeParamInfo.ALIAS_NAME}</label>
				    <input name="PARAM_NO" type="hidden" id="PARAM_NO" value="${bonusTypeParamInfo.PARAM_NO }"/>					
				</dd>
			</dl>		    
		    <dl>
				<dt><spring:message code="pa.insurance.title.company"/><!--公司-->:</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID" style="width:180px; position: static; visibility: inherit;">
					 	<c:forEach items="${cpnyList}" var="cpny">
					 		<option value="${cpny.CPNY_ID}" <c:if test="${bonusTypeParamInfo.CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">${cpny.CONTENT}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.relatedWithSalary"/><!--是否与工资有关联-->:</dt>
				<dd>
					<select name="PA_RELEVANCE_FLAG" id="PA_RELEVANCE_FLAG" style="width:180px; position: static; visibility: inherit;">
						<option value="1" <c:if test="${bonusTypeParamInfo.PA_RELEVANCE_FLAG==1 }" >selected</c:if> >
						<spring:message code="pa.insurance.title.yes"/><!--是--></option>
						<option value="0" <c:if test="${bonusTypeParamInfo.PA_RELEVANCE_FLAG==0 }" >selected</c:if>>
						<spring:message code="pa.insurance.title.no"/><!--否--></option>
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