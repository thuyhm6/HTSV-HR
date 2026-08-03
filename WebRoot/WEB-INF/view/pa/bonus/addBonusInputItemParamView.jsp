<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function validateCallback_addBonusInputItemParamViewForm(form, callback) {
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	if($("#PARAM_ITEM_NO").val()==null || $("#PARAM_ITEM_NO").val()=="" ){
			alertMsg.error('<spring:message code="liang.hr.alert.message.viewHire.paramItemNotNull"/>');//输入项目为空不能保存
			return false;
		}
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
  <div class="pageContent">
     <form method="post" id="addBonusInputItemParamViewForm" action="/pa/bonus/addBonusInputItemParamInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallback_addBonusInputItemParamViewForm(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">        
		   <dl>
				<dt><spring:message code="pa.insurance.title.inputItem"/><!--输入项目--></dt>
				<dd>
					<select name="PARAM_ITEM_NO" id="PARAM_ITEM_NO">
						<c:forEach items="${bonusInputItemList}" var="cpny">
					 		<option value="${cpny.PARAM_ITEM_NO}">${cpny.PARAM_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
		    
		    <dl>
				<dt><spring:message code="pa.insurance.title.company"/><!--公司-->:</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID">
					 	<c:forEach items="${cpnyList}" var="cpny">
					 		<option value="${cpny.CPNY_ID}" <c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">
					 			${cpny.CONTENT}
							</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
		    <dl>
				<dt><spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:-->:</dt>
				<dd>
					<select name="DISTINCT_FIELD" id="DISTINCT_FIELD">
<!-- 		           	     <option value=""><spring:message code="pa.insurance.title.pleaseChoose"/>请选择:</option> -->
						 <c:forEach items="${distinctFileList}" var="item">
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
						 <c:forEach items="${distinctFileList}" var="item">
						 	<option value="${item.DISTINCT_FIELD}">${item.FIELD_NAME}</option>
						 </c:forEach>
					</select>
				</dd>
			</dl>       
			<dl>
				<dt><spring:message code="pa.insurance.title.defaltValue"/><!--默认值:-->:</dt>
				<dd>
					<input name="DEFAULT_VAL" type="text" id="DEFAULT_VAL" value="0"  class="required"/>
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
					<spring:message code="pa.insurance.title.submit"/></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
                    <spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
			</div> 
			</div>
        </form>
 </div>
