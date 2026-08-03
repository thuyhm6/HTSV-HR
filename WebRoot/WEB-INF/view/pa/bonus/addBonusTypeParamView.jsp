<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageContent">
	<form method="post" action="/pa/bonus/addBonusTypeParamInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="58">
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.bonusType" />
					<!--奖金类型-->
					:
				</dt>
				<dd>
					<select name="TYPE_NO" type="text" id="TYPE_NO">
						<c:forEach items="${bonusTypeList}" var="cpny">
							<option value="${cpny.TYPE_NO}">
								${cpny.TYPE_NAME}
							</option>
						</c:forEach>
					</select>
				</dd>
			</dl>

			<dl>
				<dt>
					<spring:message code="pa.insurance.title.company" />
					<!--公司-->
					:
				</dt>
				<dd>
					<select name="CPNY_ID" id="CPNY_ID">
						<c:forEach items="${cpnyList}" var="cpny">
							<option value="${cpny.CPNY_ID}"
								<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if>
								disabled="true">
								${cpny.CONTENT}
							</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.relatedWithSalary" />
					<!--是否与工资有关联-->
					:
				</dt>
				<dd>
					<select name="PA_RELEVANCE_FLAG" id="PA_RELEVANCE_FLAG"
						style="width: 180px; position: static; visibility: inherit;">
						<option value="1">
							<spring:message code="pa.insurance.title.yes" />
							<!--是-->
						</option>
						<option value="0">
							<spring:message code="pa.insurance.title.no" />
							<!--否-->
						</option>
					</select>
				</dd>
			</dl>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.submit" />
									<!-- 提交 -->
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
