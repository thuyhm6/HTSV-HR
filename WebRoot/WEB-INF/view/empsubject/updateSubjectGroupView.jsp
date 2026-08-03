<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/empsubject/updateSubjectGroup" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt><spring:message code="ar.viewcycleparameter.title.gongsi"/><!-- 公司 --></dt>
				<dd style="width:100px"><input name="SUBSD_CD" value="${subjectGroupInfo.SUBSD_CD}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectGrID"/><!-- 课程组ID --></dt>
				<dd style="width:100px"><input name="SUBJT_GR_ID" value="${subjectGroupInfo.SUBJT_GR_ID}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectGrNm"/><!-- 课程组 --></dt>
				<dd style="width:100px"><input name="SUBJT_GR_NM" value="${subjectGroupInfo.SUBJT_GR_NM}" maxlength="33" class="required textInput"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.useYn"/><!-- 状态 --></dt>
				<dd style="width:100px">
					<select name="USE_YN">
						<option value="Y" <c:if test="${subjectGroupInfo.USE_YN eq 'Y'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="N" <c:if test="${subjectGroupInfo.USE_YN eq 'N'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.updateTime"/><!-- 更新时间 --></dt>
				<dd style="width:100px"><input name="UPDT_DTIME" value="${subjectGroupInfo.UPDT_DTIME}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.empNm"/><!-- 员工 --></dt>
				<dd style="width:100px"><input name="UPDT_USER_NM" value="${personName}" readonly="true"/></dd>
				<input name="UPDT_USER" value="${personId}"  type="hidden" />
			</dl>
						
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>