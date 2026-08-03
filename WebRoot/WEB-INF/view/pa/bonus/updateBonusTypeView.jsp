<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/bonus/updateBonusTypeInfo"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="NO" value="${bonusTypeInfo.TYPE_NO}" />
		<div class="pageFormContent" layoutH="56">
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.bonusTypeID" />
					<!--奖金类型ID-->
					:
				</dt>
				<dd>
					<input name="TYPE_ID" type="text" size="30"
						value="${bonusTypeInfo.TYPE_ID }" class="required alphanumeric"
						maxlength="30" />
					<input name="NO" type="hidden" id="ITEM_NO"
						value="${bonusTypeInfo.TYPE_ID }" />
				</dd>
			</dl>
			<ait:SyLanguage languageNo="${bonusTypeInfo.TYPE_NO}" />
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.description" />
					<!--描述-->
					:
				</dt>
				<dd>
					<textarea cols="100" rows="4" class="l-textarea" name="DESCR"
						id="DESCR" style="width: 400px" class="required" maxlength="1000">${bonusTypeInfo.DESCR }</textarea>
					<dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="pa.insurance.title.submit" />
								<!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>