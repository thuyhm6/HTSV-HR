<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div style="text-align:left;">
	<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;width:100%;"><spring:message code="evs.viewPersonalInfoHead_evsProbation.ZHIYUANJIBENXINXI.a"/><!--职员基本信息--></div>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
		<tr>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.LOCAL_NAME}</td>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.DEPTNAME}</td>
		</tr>
		<tr>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></td>
			<td style="border-left:0px;border-bottom:1px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.DATE_STARTED}</td>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hr.enpinfo.title.EMP.PROBATION_END_DATE"/><!--试用期结束日--></td>
			<td style="border-left:0px;border-bottom:1px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.PROBATION_END_DATE}</td>
		</tr>
		<tr>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME"/><!--等级--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.POST_GRADE_NAME}</td>
			<td style="border:0px;text-align:left;" class="td_title" width="20%"><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/><!--主要业务--></td>
			<td style="border-left:0px;border-bottom:0px;border_right:1px;text-align:left;" class="td_title" width="30%">${viewEvsObjectInfo.MAIN_BUSINESS_NAME}</td>
		</tr>
	</table>
</div>

