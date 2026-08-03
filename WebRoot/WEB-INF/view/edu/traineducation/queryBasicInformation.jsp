<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageContent" layoutH="10">
		<table id="eduTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type"  width="20%" >
		${trainBasicInformationInfo.COURSE_NAME_CODE }
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.PEIXUNFANGSHI.a"/><!--培训方式--></td>
		<td class="td_type"  width="20%" >
		${trainBasicInformationInfo.TRAIN_FORM_CODE_NAME }
		</td>
		<td class="td_title" width="1%"><spring:message code="empsubject.eduRm"/><!--培训地点--></td>
		<td class="td_type"  width="20%" >
		${trainBasicInformationInfo.TRAIN_ADDRESS }
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainArchives.SHISHIKAISHIRIQI.a"/><!--实施开始日期--></td>
		<td class="td_type"  width="20%" >
		${trainBasicInformationInfo.IMPLE_START_DATE }
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainArchives.SHISHIJIESHURIQI.a"/><!--实施结束日期--></td>
		<td class="td_type"  width="20%" >
		${trainBasicInformationInfo.IMPLE_END_DATE }
		</td>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.SHISHIKESHI.a"/><!--实施课时--></td>
		<td class="td_type"  width="20%" >
		${trainBasicInformationInfo.IMPLE_CLASS_HOUR }&nbsp
		<c:if test="${trainBasicInformationInfo.IMPLE_CLASS_UNIT eq '0' }"><spring:message code="display.mutual.month"/><!--月--></c:if>
		<c:if test="${trainBasicInformationInfo.IMPLE_CLASS_UNIT eq '1' }"><spring:message code="display.mutual.day"/><!--天--></c:if>
		<c:if test="${trainBasicInformationInfo.IMPLE_CLASS_UNIT eq '2' }"><spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></c:if>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.BAOMINGJIEZHIRIQII.a"/><!--报名截止日期--></td>
		<td class="td_type"  width="20%" >
		${trainBasicInformationInfo.APPLY_END_DATE }
		</td>
		<td class="td_title" width="1%"></td>
		<td class="td_type"  width="20%" >
		</td>
		<td class="td_title" width="1%"></td>
		<td class="td_type"  width="20%" >
		</td>
		</tr>
		</table>
</div>
