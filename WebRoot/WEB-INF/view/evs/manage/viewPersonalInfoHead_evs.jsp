<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div style="text-align:left;">
	<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;width:100%;"><spring:message code="hr.viewPersonalInfo.title.PERSONAL_INFORMATION"/></div>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
		<tr><td style="font:bold 12px/20px arial,sans-serif;border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" colspan="2"><spring:message code="evs.viewPersonalInfoHead_evsProbation.ZHIYUANJIBENXINXI.a"/><!--职员基本信息--></td><td style="border-left:0px;border-bottom:0px;border_right:1px;font:bold 12px/20px arial,sans-serif;text-align:left;" class="td_title" colspan="2"><spring:message code="hrm.empinfo.Evaluation_items"/><!--评价事项--></td></tr>
		<tr>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.LOCAL_NAME}</td>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hrm.empinfo.Evaluation_year"/><!--评价年度--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.EVS_YEAR}</td>
		</tr>
		<tr>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME"/><!--等级--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.POST_GRADE_NAME}</td>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hr.viewEvaluate.title.EV_PERIOD"/><!--评价期间--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.EVS_START_DATE}~${viewEvsObjectInfo.EVS_END_DATE}</td>
		</tr>
		<tr>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="ess.infoApply.DEPT"/><!--部门--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.DEPTNAME}</td>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="evs.viewEvsAffirmorSetup.YICIPINGJIAREN.a"/><!--1次评价人--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.LOCAL_NAME1}</td>
		</tr>
		<tr>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></td>
			<td style="border-left:0px;border-bottom:1px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.DATE_STARTED}</td>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="evs.viewEvsAffirmorSetup.LAINGCIPINGJIAREN.a"/><!--2次评价人--></td>
			<td style="border-left:0px;border-bottom:1px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.LOCAL_NAME2}</td>
		</tr>
		<c:if test="${not empty viewEvsObjectInfo.LOCAL_NAME3 }">
		<tr>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/><!--主要业务--></td>
			<td style="border-left:0px;border-bottom:1px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.MAIN_BUSINESS_NAME}</td>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="evs.viewEvsAffirmorSetup.SANCIPINGJIAREN.a"/><!--3次评价人--></td>
			<td style="border-left:0px;border-bottom:1px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.LOCAL_NAME3}</td>
		</tr>
		</c:if>
	</table>
</div>

