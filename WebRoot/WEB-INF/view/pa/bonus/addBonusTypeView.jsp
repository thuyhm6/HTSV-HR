<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/bonus/addBonusTypeInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>
					<spring:message code="pa.insurance.title.bonusTypeID" />
					<!--奖金类型ID-->
					:
				</label>
				<input name="TYPE_ID" type="text" size="30"
					value="${bonusType.TYPE_ID }" class="required alphanumeric"
					maxlength="30" />
			</p>

			<ait:SyLanguage />

			<p>
				<label>
					<spring:message code="pa.insurance.title.description" />
					<!--描述-->
					:
				</label>
				<textarea cols="100" rows="4" class="l-textarea" name="DESCR"
					id="DESCR" style="width: 400px" class="required" maxlength="1000">${bonusType.DESCR }</textarea>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
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
								取消
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
