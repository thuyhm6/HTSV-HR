<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
     <form method="post" action="/pa/wagebase/updatePaBasicItemInfo" 
     	class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
     	<div class="pageFormContent nowrap">
     		<dl>
				<dt><spring:message code="pa.insurance.title.projectType"/><!--项目类型-->:</dt>
				<dd>
					<select name="seach_ITEM">
						<option value="基础项目" <c:if test="${ITEM == '基础项目'}">selected</c:if>>基础项目</option>
						<option value="输入项目" <c:if test="${ITEM == '输入项目'}">selected</c:if>>输入项目</option>
						<option value="计算项目" <c:if test="${ITEM == '计算项目'}">selected</c:if>>计算项目</option>
					</select>
				</dd>
			</dl>
     		<dl>
				<dt><spring:message code="pa.insurance.title.projectID"/><!--项目ID-->:</dt>
				<dd>
					<input id="ITEM_ID" name="ITEM_ID" value="${paBasicItemInfo.ITEM_ID }">
					<input name="NO" type="hidden" id="ITEM_NO" value="${paBasicItemInfo.ITEM_NO }"  />
				</dd>
			</dl>
			
			<ait:SyLanguage languageNo="${paBasicItemInfo.ITEM_NO}"/>

			<dl>
				<dt><spring:message code="pa.insurance.title.dataType"/><!--数据类型-->:</dt>
				<dd>
					<select id="DATA_TYPE" name="DATA_TYPE" >
						<option value="NUMBER(14,4)" <c:if test="${paBasicItemInfo.DATA_TYPE eq 'NUMBER(14,4)' }" >selected</c:if> >
						<spring:message code="pa.insurance.title.numberType"/><!--数字类型--></option>
						<option value="VARCHAR(100)" <c:if test="${paBasicItemInfo.DATA_TYPE eq 'VARCHAR(100)' }" >selected</c:if> >
						<spring:message code="pa.insurance.title.varcharType"/><!--字符类型--></option>
					</select>
				</dd>
			</dl>

		    <dl>
				<dt><spring:message code="pa.insurance.title.description"/><!--描述-->:</dt>
				<dd>
					<textarea cols="100" rows="4" class="l-textarea" name="DESCR" id="DESCR" style="width:400px" >${paBasicItemInfo.DESCR }</textarea>
				</dd>
			</dl>
			
			
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
			</div> 
			</div>
        </form>
</div>
