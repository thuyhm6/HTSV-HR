<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ess/dimissionApply/addEditionItemTypeInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="ACTIVITY" value="1"/>
		<div class="pageFormContent nowrap">
		    <dl>
				<dt>
					<spring:message code="ess.dimission.title.editionnumber"/><!--版本号-->
				</dt>
				<dd>
					<select id="EDITION_NO" name="EDITION_NO">
						<c:forEach items="${editionList }" var="item">
						    <option value="${item.EDITION_NO}">${item.EDITION_NO}
						</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
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