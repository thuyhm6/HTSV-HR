<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ess/dimissionApply/updateEditionItemTypeInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		    <dl>
				<dt>
					<spring:message code="ess.dimission.title.editionnumber"/><!--版本号-->
				</dt>
				<dd>
				<select id="EDITION_NO" name="EDITION_NO" disabled="disabled">
					<c:forEach items="${editionList }" var="item">
						<option value="${item.EDITION_NO}"<c:if test="${item.EDITION_NO eq editionItemTypeInfo.EDITION_NO}">selected</c:if>>${item.EDITION_NO}
					</option>
						</c:forEach>
				</select>
				<input name="EDITION_NO" id="EDITION_NO" type="hidden" value="${editionItemTypeInfo.EDITION_NO }"/>
				</dd>
			</dl>
			<input type="hidden" name="EDITION_ITEM_TYPE" value = "${editionItemTypeInfo.EDITION_ITEM_TYPE }"/>
			<ait:SyLanguage1 languageNo="${editionItemTypeInfo.EDITION_ITEM_TYPE}"/>

			<dl style="height:auto">
				<table>
					<tr>
						<td class="td_title" style="width:122px;"><spring:message code="pa.insurance.title.description"/><!--描述-->:</td>
						<td class="td_type">
							<textarea cols="100" rows="4" class="l-textarea" name="REMARK" id="REMARK" style="width:400px" >${editionItemTypeInfo.REMARK }</textarea>
						</td>
					</tr>
				</table>
			</dl>
			<dl>
				<dt>
					显示顺序
				</dt>
				<dd>
					<input name="ORDERNO" id="ORDERNO" type="text" value="${editionItemTypeInfo.ORDERNO}"/>
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