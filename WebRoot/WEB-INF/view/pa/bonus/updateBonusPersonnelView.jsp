<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/bonus/updateBonusPersonnelInfo" class="pageForm required-validate" 
	      onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<dl>
				<dt><spring:message code="public.title.empId"/><!--工号-->:</dt>
				<label>${bnPersonnel.PERSON_ID}</label>
				<dd>
					<input name="PERSON_ID" type="hidden" value="${bnPersonnel.PERSON_ID }"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="public.title.name"/><!--姓名-->:</dt>
				<dd>
					<label>${bnPersonnel.LOCAL_NAME}</label>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="public.title.deptName"/><!--部门-->:</dt>
				<dd>
					<label>${bnPersonnel.DEPT_NAME}</label>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.postGrade"/><!--职级-->:</dt>
				<dd>
					<label>${bnPersonnel.POST_GRADE_NAME}</label>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.status"/><!--状态-->:</dt>
				<dd>
					<label>${bnPersonnel.STATUS_NAME}</label>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.ifCaculateBonus"/><!--是否计算奖金-->:</dt>
				<dd><select name="BN_CALC_FLAG" id="BN_CALC_FLAG" style="width:180px; position: static; visibility: inherit;">
					<option value="Y" <c:if test="${bnPersonnel.BN_CALC_FLAG=='Y'}" >selected</c:if>>Y</option>
					<option value="N" <c:if test="${bnPersonnel.BN_CALC_FLAG=='N'}" >selected</c:if>>N</option>
				</select>
				</dd>
			</dl>	
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
                <spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">
                    <spring:message code="public.title.cancle"/><!--取消--></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>