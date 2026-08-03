<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">
	<form method="post" action="/pa/bonus/updateBonusInputItemParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input name="PARAM_NO" type="hidden" value="${bonusInputItemParamInfo.PARAM_NO}" />
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt><spring:message code="pa.insurance.title.company"/><!--公司-->:</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID" style="width:180px; position: static; visibility: inherit;">
					 	<c:forEach items="${cpnyList}" var="cpny">
					 		<option value="${cpny.CPNY_ID}" <c:if test="${bonusInputItemParamInfo.CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">${cpny.CONTENT}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:-->：</dt>
				<dd>
					<select name="DISTINCT_FIELD" id="DISTINCT_FIELD" style="width:180px; position: static; visibility: inherit;">
<!-- 		           	     <option value=""><spring:message code="pa.insurance.title.pleaseChoose"/>请选择:</option> -->
						 <c:forEach items="${distinctFileList}" var="item">
						 	<option value="${item.DISTINCT_FIELD}" <c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD eq item.DISTINCT_FIELD}">selected</c:if>>${item.FIELD_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.distinctName2"/><!--区分项目2:-->:</dt>
				<dd>
					<select name="DISTINCT_FIELD_2ND" id="DISTINCT_FIELD_2ND" style="width:180px; position: static; visibility: inherit;">
		           	     <option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
						 <c:forEach items="${distinctFileList}" var="item">
						 	<option value="${item.DISTINCT_FIELD}" <c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD_2ND eq item.DISTINCT_FIELD}">selected</c:if>>${item.FIELD_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.defaltValue"/><!--默认值:-->:</dt>
				<dd>
					<input name="DEFAULT_VAL" type="text" id="DEFAULT_VAL" value="${bonusInputItemParamInfo.DEFAULT_VAL }" value="0"  class="required number"/>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ACTIVITY" id="PA_RELEVANCE_FLAG">
						<option value="1" <c:if test="${bonusInputItemParamInfo.ACTIVITY eq 1 }">selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" <c:if test="${bonusInputItemParamInfo.ACTIVITY ne 1 }">selected</c:if>>
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
