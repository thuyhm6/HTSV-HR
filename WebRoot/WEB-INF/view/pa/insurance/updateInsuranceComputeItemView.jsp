<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>




<div class="pageContent">
	<form method="post" action="/pa/insurance/updateInsuranceComputeItemInfo" class="pageForm required-validate" 
	      enctype="multipart/form-data" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt><spring:message code="pa.insurance.title.projectID"/><!--项目ID-->:</dt>
				<input name="ITEM_ID" type="text" size="30"  value="${insuranceComputeItemInfo.ITEM_ID }" class="required alphanumeric" maxlength="30" />
				<input name="NO" type="hidden" size="30"  value="${insuranceComputeItemInfo.ITEM_NO }"/>
			</dl>
			<ait:SyLanguage languageNo="${insuranceComputeItemInfo.ITEM_NO }"/>
			<dl>
				<dt><spring:message code="pa.insurance.title.dataType"/><!--数据类型-->:</dt>
				<select id="DATATYPE" name="DATATYPE" >
					<option value="NUMBER(14,4)" <c:if test="${insuranceComputeItemInfo.DATATYPE == 'NUMBER(14,4)' }">selected</c:if> >
					<spring:message code="pa.insurance.title.numberType"/><!--数字类型--></option>
					<option value="VARCHAR(100)" <c:if test="${insuranceComputeItemInfo.DATATYPE == 'VARCHAR(100)' }">selected</c:if>>
					<spring:message code="pa.insurance.title.varcharType"/><!--字符类型--></option>
				</select>
			</dl>	            
 			<dl style="height:auto">
				<table>
					<tr>
						<td class="td_title" style="width:122px;"><spring:message code="pa.insurance.title.description"/><!--描述-->:</td>
						<td class="td_type">
							<textarea cols="100" rows="4" class="l-textarea" name="DESCR" id="DESCR" style="width:400px" >
				${insuranceComputeItemInfo.DESCR }</textarea>
						</td>
					</tr>
				</table>
			</dl>	            
		</div>
		<div class="formBar" layoutH="106">
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
