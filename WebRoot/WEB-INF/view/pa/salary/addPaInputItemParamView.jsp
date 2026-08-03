<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
  <div class="pageContent">
     <form method="post" action="/pa/salary/addPaInputItemParamInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">        
		   <dl>
				<dt><spring:message code="pa.insurance.title.projectName"/><!--项目名称-->:</dt>
				<dd>
					<select name="PARAM_ITEM_NO"  id="PARAM_ITEM_NO">
						<c:forEach items="${itemList}" var="cpny">
					 		<option value="${cpny.PARAM_ITEM_NO}">${cpny.PARAM_ITEM_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
		    
		    <dl>
				<dt><spring:message code="pa.insurance.title.company"/><!--公司-->:</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID">
					 	<c:forEach items="${cpnyList}" var="cpny">
					 		<option value="${cpny.CPNY_ID}" <c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">${cpny.CONTENT}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
		    <dl>
				<dt><spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:-->:</dt>
				<dd>
					<select name="DISTINCT_FIELD" id="DISTINCT_FIELD">
<!-- 						<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/>请选择:</option> -->
						 <c:forEach items="${distinctFieleList}" var="item">
						 	<option value="${item.DISTINCT_FIELD}">${item.FIELD_NAME}</option>
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
						 	<option value="${item.DISTINCT_FIELD}">${item.FIELD_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>       
			<dl>
				<dt><spring:message code="pa.insurance.title.defaltValue"/><!--默认值:-->:</dt>
				<dd>
					<input name="DEFAULT_VAL" type="text" id="DEFAULT_VAL" value="0"  class="required number" 
					       maxlength="6"/>
				</dd>
			</dl> 
			<%--
			<input type="hidden" name="ONLINE_FLAG" id="ONLINE_FLAG" value="N">
			 --%>
			<dl>
				<dt><spring:message code="pa.paParam.title.online"/><!--是否需要线上禀议:-->:</dt>
				<dd>
					<select name="ONLINE_FLAG" id="ONLINE_FLAG">
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.paParam.title.project.fse"/><!--是否为FSE专用项目:-->:</dt>
				<dd>
					<select name="FSE_FLAG" id="FSE_FLAG">
						<option value="N">N</option>
						<option value="Y">Y</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="ACTIVITY" id="PA_RELEVANCE_FLAG">
						<option value="1" selected>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" >
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用-->
						</option>
					</select>
				</dd>
			</dl>
			        
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
			</div> 
			</div>
        </form>
 </div>