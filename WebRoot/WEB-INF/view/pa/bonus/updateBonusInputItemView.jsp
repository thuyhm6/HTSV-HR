<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">
	<form method="post" action="/pa/bonus/updateBonusInputItemInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.projectID"/><!--项目ID-->:
				</dt>
				<dd>					
					<input id="ITEM_ID" NAME="ITEM_ID" type="text" value="${bonusInputItemInfo.PARAM_ITEM_ID}" class="required alphanumeric" maxlength="30"/>	
					<input name="NO" id="ITEM_NO" type="hidden"
						value="${bonusInputItemInfo.PARAM_ITEM_NO }" />
				</dd>
			</dl>
			<ait:SyLanguage languageNo="${bonusInputItemInfo.PARAM_ITEM_NO}" />
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.dataType"/><!--数据类型-->:
				</dt>
				<dd>					
					<select id="DATA_TYPE" name="DATA_TYPE">
						<option value="NUMBER(14,4)"
							<c:if test="${bonusInputItemInfo.DATA_TYPE eq 'NUMBER(14,4)' }" >selected</c:if>>
							<spring:message code="pa.insurance.title.numberType"/><!--数字类型-->
						</option>
						<option value="VARCHAR(100)"
							<c:if test="${bonusInputItemInfo.DATA_TYPE eq 'VARCHAR(100)' }" >selected</c:if>>
							<spring:message code="pa.insurance.title.varcharType"/><!--字符类型-->
						</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.description"/><!--描述-->:
				</dt>
				<dd>
					<textarea cols="100" rows="4" class="l-textarea" name="DESCR"
						id="DESCR" style="width: 400px">${bonusInputItemInfo.DESCR }</textarea>
				</dd>
			</dl>

			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.submit"/><!-- 提交 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">
									<spring:message code="public.title.cancle"/><!--取消-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>