<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<div class="panel">
		<h1>
			<spring:message
				code="hr.viewPersonalInfo.title.STAFF_FOUNDATION_INFORMATION" />
			<!--员工基础信息-->
		</h1>
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.close"/><!-- 关闭 --></button></div></div>
			</li>
		</ul>
	</div>
     <div><%@ include file="/WEB-INF/view/ess/empinfo/viewPersonalInfoHead.jsp"%></div></div>
</div>
<div class="pageContent" >

	<table class="table" width="100%" layoutH="138" border="2" cellpadding="2" cellspacing="1">
			<tr>
				<td width="10%" bgcolor='#EBF5FC' style="padding-top: 1px;padding-bottom: 1px;">
                <spring:message code="hr.viewPersonalInfo.title.ENGLISH_NAME" /><!--英文名 --></td>
				<td width="23%">
				    ${personalInfo.ENGLISH_NAME}	
				</td>
				<td width="10%" bgcolor='#EBF5FC' style="padding-top: 1px;padding-bottom: 1px;">
				<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" />
				<!--手机号码-->
				</td>
				<td width="23%">
				    ${personalInfo.CELLPHONE}
				</td>
				<td width="10%" bgcolor='#EBF5FC' style="padding-top: 1px;padding-bottom: 1px;">
				<spring:message code="liang.hr.viewPersonalInfo.title.EMAIL" />
				<!--E-mail-->
				</td>
				<td width="23%">
				    ${personalInfo.EMAIL }
			    </td>
			</tr>

			<tr>
				<td width="15%" bgcolor='#EBF5FC' style="padding-top: 1px;padding-bottom: 1px;" >
				<spring:message code="hr.viewPersonalInfo.title.HOME_ADDRESS" />
				<!--现住址(邮编)--></td>
				<td width="80%" colspan="5">
				    ${personalInfo.HOME_ADDRESS }
				</td>
			</tr>					
	</table>
</div>