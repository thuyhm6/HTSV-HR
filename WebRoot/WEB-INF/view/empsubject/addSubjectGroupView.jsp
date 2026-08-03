<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/empsubject/addSubjectGroup" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input name="USE_YN" type="hidden" value="" />
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt><spring:message code="ar.viewcycleparameter.title.gongsi"/><!-- 公司 --></dt>
				<dd style="width:100px"><input name="SUBSD_CD" value="${defaultCpny}" readonly/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectGrID"/><!-- 课程组ID --></dt>
				<dd style="width:100px"><input name="SUBJT_GR_ID" value="" maxlength="10" onkeyup="value=value.replace(/[^\w\.\/-]/ig,'')" style="ime-mode:disabled" class="required textInput"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectGrNm"/><!-- 课程组 --></dt>
				<dd style="width:100px"><input name="SUBJT_GR_NM" value="" maxlength="33" class="required textInput"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.empNm"/><!-- 员工 --></dt>
				<dd style="width:100px"><input name="UPDT_USER_NM" value="${personName}"  readonly/></dd>
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