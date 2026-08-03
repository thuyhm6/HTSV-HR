<%@ page contentType="text/html; charset=UTF-8" language="java"	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form method="post" action="/pa/wagebase/addPaAllowanceInfo"
		class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt><%--法人--%><spring:message code="sys.essParam.title.legalPerson"/>：</dt>
				<dd>
					<select name="CPNY_NAME">
						<c:forEach items="${cpnyList}" var="cpny">
							<option value="${cpny.CONTENT}" <c:if test="${CPNY_NAME eq cpny.CONTENT}">selected</c:if>>${cpny.CONTENT}
						</c:forEach>
					</select>
					<input type="hidden" name="ALLOWANCE_ID" value="${ALLOWANCE.ALLOWANCE_ID}">
				</dd>
			</dl>
			<dl>
				<dt><%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/>：</dt>
				<dd>
					<ait:deptList name="DEPT_ID" id="addDept_id"/>
					<ait:deptTreeIcon name="DEPT_ID" id="addDept_id" selected="${ALLOWANCE.DEPT_ID}"/></td>
				</dd>
			</dl>
			<dl>
				<dt><%--职责--%><spring:message code="ess.infoApply.title.dutyName"/>：</dt>
				<dd>
					<select name="DUTY_ALLOWANCE">
						<c:forEach items="${positionList}" var="position">
							<option value="${position.POSITION}" <c:if test="${position.POSITION eq ALLOWANCE.DUTY_ALLOWANCE}">selected</c:if>>${position.POSITION}
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><%--人员类型--%><spring:message code="is.company.title.PERSON_TYPE"/>：</dt>
				<dd>
					<select name="TYPE_ALLOWANCE">
						<c:forEach items="${personType}" var="person">
							<option value="${person.CODE_NO}" <c:if test="${person.CODE_NO eq ALLOWANCE.TYPE_ALLOWANCE}">selected</c:if>>${person.CONTENT}
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><%--数值--%><spring:message code="pa.insurance.title.dataValue"/>：</dt>
				<dd>
					<input type="text" name="POSITION_ALLOWANCE"  value="${ALLOWANCE.POSITION_ALLOWANCE}" class="required"/>
				</dd>
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