<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ess/dimissionApply/addEditionItemTypeParamInfo?seach_EDITION_NO=${EDITION_NO }&seache_EDITION_ITEM_TYPE=${EDITION_ITEM_TYPE}&seach_CPNY_ID=${CPNY_ID}"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="ACTIVITY" value="1"/>
		<input type="hidden" name="EDITION_NO" value="${EDITION_NO}"/>
		<input type="hidden" name="EDITION_ITEM_TYPE" value="${EDITION_ITEM_TYPE }"/>
		<div class="pageFormContent nowrap">
			<ait:SyLanguage />
			<dl style="height:auto">
				<table>
					<tr>
						<td class="td_title" style="width:122px;"><spring:message code="pa.insurance.title.description"/><!--描述-->:</td>
						<td class="td_type">
							<textarea cols="100" rows="4" class="l-textarea" name="REMARK"
						id="REMARK" style="width: 400px"></textarea>
						</td>
					</tr>
				</table>
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