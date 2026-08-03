<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/ess/dimissionApply/updateDimissionEditionInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
		    <dl>
				<dt>
					<spring:message code="ess.dimission.title.editionnumber"/><!--版本号-->
				</dt>
				<dd>
					<input type="text" name="EDITION_NO" id = "EDITION_NO" value="${editionInfo.EDITION_NO }" readonly="readonly"/>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="sys.essParam.title.ifEnabled"/><!--是否启用-->
				</dt>
				<dd>
					<input name="ACTIVITY" type="radio" value="1" <c:if test="${editionInfo.ACTIVITY eq 1}">checked</c:if>/><!-- 启用 --><spring:message code="sys.arAffirmPost.title.able"/>
				<input name="ACTIVITY" type="radio" value="0" <c:if test="${editionInfo.ACTIVITY eq 0}">checked</c:if>/><!-- 不启用 --><spring:message code="sys.arAffirmPost.title.enable"/>
				</dd>
			</dl>

			<dl style="height:auto">
				<table>
					<tr>
						<td class="td_title" style="width:122px;"><spring:message code="ar.viewItem.title.shuoming"/><!--说明-->:</td>
						<td class="td_type">
							<textarea cols="100" rows="4" class="l-textarea" name="REMARK" id="REMARK" style="width:400px" >${editionInfo.REMARK}</textarea>
						</td>
					</tr>
				</table>
			</dl>
			
			<dl>
				<table class="table" width="90%" layoutH="100">
					<thead>
						<tr>
							<th width="150"><spring:message
									code="is.company.title.PERSON_TYPE" />
								<!--人员类型-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${empTypeNameList}" var="jobType" varStatus="i">
							<c:if test="${i.count%3 == 1 }">
							<tr target="EMP_TYPE_CODE" rel="${jobType.CODE_NO}">
								<td><input type="checkbox" name="check_EMP_TYPE_CODE"
									value="${jobType.CODE_NO}" 
									<c:forEach items="${editionList}" var="itemlist" varStatus="j">
											<c:if test="${jobType.CODE_NO eq itemlist.EMP_TYPE_CODE}">checked=true</c:if>
											</c:forEach>/>&nbsp;&nbsp;${jobType.EMP_TYPE_NAME}
								</td>
							</c:if>
							<c:if test="${i.count%3 == 2 }">
								<td><input type="checkbox" name="check_EMP_TYPE_CODE"
									value="${jobType.CODE_NO}" 
									<c:forEach items="${editionList}" var="itemlist" varStatus="j">
											<c:if test="${jobType.CODE_NO eq itemlist.EMP_TYPE_CODE}">checked=true</c:if>
											</c:forEach>
									/>&nbsp;&nbsp;${jobType.EMP_TYPE_NAME}
								</td>
							</c:if>
							<c:if test="${i.count%3 == 0 }">
								<td><input type="checkbox" name="check_EMP_TYPE_CODE"
									value="${jobType.CODE_NO}" 
									<c:forEach items="${editionList}" var="itemlist" varStatus="j">
											<c:if test="${jobType.CODE_NO eq itemlist.EMP_TYPE_CODE}">checked=true</c:if>
											</c:forEach>
									/>&nbsp;&nbsp;${jobType.EMP_TYPE_NAME}
								</td>
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</dl>
			
		</div>
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
	</form>
</div>