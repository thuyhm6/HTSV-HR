<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form method="post" action="/pa/wagebase/updatePaAllowanceSelfInfo"
		class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt>社号：</dt>
				<dd>${PERSON.EMPID}</dd><input type="hidden" name="ALLOWANCE_ID" value="${PERSON.ALLOWANCE_ID}">
			</dl>
			<dl>
				<dt>姓名：</dt>
				<dd>${PERSON.CHINESENAME }</dd>
			</dl>
			<dl>
				<dt>G数值：</dt>
				<dd>${PERSON.POSITION_ALLOWANCE }</dd>
			</dl>
			<dl>
				<dt>C数值：</dt>
				<dd><input type="text" name="POSITION_ALLOWANCE_C" value="${PERSON.POSITION_ALLOWANCE_C }"</dd>
			</dl>
			<dl>
				<dt>支付比例：</dt>
				<dd><input type="text" name="PERCENT_ALLOWANCE" value="${PERSON.PERCENT_ALLOWANCE }"></dd>
			</dl>
			<dl>
				<dt>发令日期：</dt>
				<dd>${PERSON.CHANGE_DATE}</dd>
			</dl>
			<dl>
				<dt>有效期月数：</dt>
				<dd><input type="text" name="VALID_MONTH" value="${PERSON.VALID_MONTH }"></dd>
			</dl>
			<dl>
				<dt>状态：</dt>
				<dd>
					<select name="ACTIVITY" >
						<option value="1" <c:if test="${PERSON.ACTIVITY eq '1'}">selected</c:if>>启用</option>
						<option value="0" <c:if test="${PERSON.ACTIVITY eq '0'}">selected</c:if>>未启用</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>备注：</dt>
				<dd><textarea NAME="DEMO_ALLOWANCE">${PERSON.DEMO_ALLOWANCE }</textarea></dd>
			</dl>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!--提交--><spring:message code="public.title.submit" />
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">
									<!--取消--><spring:message code="public.title.cancle" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>