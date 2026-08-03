<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/salary/addPaComputeItemParamInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->:
				</label>
				<label>
					<select name="ITEM_NO" id="ITEM_NO">
						<c:forEach items="${itemList}" var="cpny">
							<option value="${cpny.ITEM_NO}">
								${cpny.ITEM_NAME}
							</option>
						</c:forEach>
					</select>
				</label>
			</p>

			<p>
				<label>
					<spring:message code="pa.insurance.title.companyID"/><!--公司ID-->:
				</label>
				<label>
					<select name="cpny" id="cpny">
						<c:forEach items="${cpnyList}" var="cpny">
							<option value="${cpny.CPNY_ID}"
								<c:if test="${LoginUser.cpnyId==cpny.CPNY_ID }" >selected</c:if>
								disabled="true">
								${cpny.CONTENT}
							</option>
						</c:forEach>
					</select>
				</label>
			</p>
			<input type="hidden" name="APPLY_TYPE" value="21350">
			<%--
			<p>
				<label>
					<spring:message code="pa.salary.title.propertyType"/><!--适用类型-->:
				</label>
				<label>
					<ait:selectSyCode name="APPLY_TYPE" parentNo="21348" />
				</label>
			</p>
			 --%>
			<p>
				<label>
					<spring:message code="pa.insurance.title.precision"/><!--精度-->:
				</label>
				<label>
					<input type="text" name="PRICISION" class="digits">
				</label>
			</p>
			<p>
				<label>
					<spring:message code="pa.insurance.title.carry"/><!--进位-->:
				</label>
				<label>
					<input type="text" name="CARRY_BIT" class="number">
				</label>
			</p>

		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="pa.insurance.title.submit"/><!--保存-->
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