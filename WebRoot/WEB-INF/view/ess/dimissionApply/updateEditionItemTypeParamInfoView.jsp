<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ess/dimissionApply/updateEditionItemParamInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<input type="hidden" name="EDITION_ITEM_NO" value = "${editionItemParamInfo.EDITION_ITEM_NO }"/>
			<ait:SyLanguage1 languageNo="${editionItemParamInfo.EDITION_ITEM_NO}"/>

			<dl style="height:auto">
				<table>
					<tr>
						<td class="td_title" style="width:122px;"><spring:message code="pa.insurance.title.description"/><!--描述-->:</td>
						<td class="td_type">
							<textarea cols="100" rows="4" class="l-textarea" name="REMARK" id="REMARK" style="width:400px" >${editionItemParamInfo.REMARK }</textarea>
						</td>
					</tr>
				</table>
			</dl>
			<dl>
				<dt>
					显示顺序
				</dt>
				<dd>
					<input name="ORDERNO" id="ORDERNO" type="text" value="${editionItemParamInfo.ORDERNO}"/>
				</dd>
			</dl>
			
			<div class="formBar" layoutH="106">
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