<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/postManagement/updatePositionInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<input type="hidden" name="POSITION_NO" value="${positionInfo.POSITION_NO}"/>
			<input type="hidden" name="NO" value="${positionInfo.POSITION_NO}"/>
		   <ait:SyLanguage languageNo="${positionInfo.POSITION_NO}"/>
		   <dl>
				<dt><spring:message code="sys.postManage.title.postGrade"/><!--职级--></dt>
				<dd>
					<select name="POST_GRADE_NO">
						<option value="">--<spring:message code="sys.affirm.title.choose"/>--</option>
				 		<c:forEach items="${postGradeList}" var="grade">
							<option value="${grade.POST_GRADE_NO}" <c:if test="${grade.POST_GRADE_NO eq positionInfo.POST_GRADE_NO}">selected</c:if>>${grade.GRADENAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			  <dl>
				<dt><spring:message code="sys.postManage.title.postType"/><!--职类--></dt>
				<dd>
					<select name="POST_CATEGORY_NO">
				 		<c:forEach items="${gategoryList}" var="gategory">
							<option value="${gategory.CODE_NO}" <c:if test="${gategory.CODE_NO eq positionInfo.POST_CATEGORY_NO}">selected</c:if>>${gategory.CODE_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.postManage.title.ifUsed"/><!--是否使用--></dt>
				<dd>
				<select name="ACTIVITY">
				  <option VALUE="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
				  <option VALUE="0"><spring:message code="sys.affirm.title.no"/><!--否--></option>
				</select>
				</dd>
			</dl>
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>	
</div>
