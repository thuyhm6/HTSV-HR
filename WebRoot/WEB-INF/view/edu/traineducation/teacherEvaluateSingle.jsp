<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function (){
	var adminempid="${ADMINEMPID}";
	if(adminempid!="11111111"&&adminempid!="11111112"&&adminempid!="30100104"&&adminempid!="40110008"){
		$('#teakaopingTitle').attr('style','display:none');
	}
});
</script>

<div class="pageContent" layoutH="10">
	<form method="post" id="teacherEvaluateInfo" action="/edu/traineducation/updateStudentEvaluateInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		 <a id="importExcelDialogStudentEva"  href="" target="dialog" rel="evaimport" mask="true" width="500" height="200"></a>
		 <div class="formBar" id="teakaopingTitle" style="">
			<ul>
					<li>
						<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=157&BASIC_NO=${teacherEvaluateInfo.BASIC_NO }">
							<span><spring:message code="inct.salesman.downloadToExcel"/><!--Excel导出--></span>
						</a>
					</li>
				<li onclick="teacherEvabaocun()"><div class="buttonActive"><div class="buttonContent"><spring:message code="pa.insurance.title.submit"/><!--保存--></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>     
		
		<div class="pageFormContent nowrap">
		<input type="hidden" name="BASIC_NO" id="BASIC_NO" value="${teacherEvaluateInfo.BASIC_NO }">
		<table id="teacherEva" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
		<td class="td_type"  width="20%" colspan='2'>
		<span id="traintype">${teacherEvaluateInfo.TRAIN_TYPE_CODE_NAME }</span>
		</td>
		<td class="td_title" width="1%" colspan='2'><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type"  width="20%" colspan='2'>
		<span id="trainname">${teacherEvaluateInfo.COURSE_NAME_CODE }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%" ><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
		<td class="td_type"  width="20%" colspan='2'>
		<span>${teacherEvaluateInfo.IMPLE_CLASS_HOUR }&nbsp
		<c:if test="${teacherEvaluateInfo.IMPLE_CLASS_UNIT eq '0' }"><spring:message code="display.mutual.month"/><!--月--></c:if>
		<c:if test="${teacherEvaluateInfo.IMPLE_CLASS_UNIT eq '1' }"><spring:message code="display.mutual.day"/><!--天--></c:if>
		<c:if test="${teacherEvaluateInfo.IMPLE_CLASS_UNIT eq '2' }"><spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></c:if>
		</span>
		</td>
		<td class="td_title" width="1%" colspan='2'><spring:message code="edu.planManager.PEIXUNNEIRONG.a"/><!--培训内容--></td>
		<td class="td_type"  width="20%" colspan='2'>
		<span>${teacherEvaluateInfo.TRAIN_CONTENT }</span>
		</td>
		</tr>
		
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></td>
		<td class="td_title" width="1%"><spring:message code="ess.trans.title.postGradeName"/><!--职级--></td>
		<td class="td_title" width="1%"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!--部门--></td>
		<td class="td_title" width="1%"><spring:message code="hr.viewPersonalInfo.title.SEX"/><!--性别--></td>
		<td class="td_title" width="1%"><spring:message code="inct.salesman.position"/><!--职务--></td>
		<td class="td_title" width="2%">&nbsp&nbsp&nbsp<spring:message code="edu.teacherEvaluate.XUEYUANKAOPING.a"/><!--学员考评-->&nbsp&nbsp&nbsp</td>
		
		</tr>
		<c:forEach items="${teacherEvaluateList}" var="s" varStatus="i">
		<input type="hidden" name="FREE_NO" id="FREE_NO" value="${s.FREE_NO }">
		<tr>
		<td class="td_type"  width="20%">${s.TEA_LOCAL_NAME }</td>
		<td class="td_type"  width="20%">${s.POST_GRADE_NO_NAME }</td>
		<td class="td_type"  width="20%">${s.ORG_NAME_LOCAL }</td>
		<td class="td_type"  width="20%">${s.SEXCODE_NAME }</td>
		<td class="td_type"  width="20%">${s.POSITION_NO_NAME }</td>
		<td class="td_type"  width="40%" nowrap="nowrap">
		<c:if test="${ALLTEACHEREMPID!='11111111'&&ALLTEACHEREMPID!='11111112'&&flag=='1' }">
		<c:if test="${s.STU_EMPID==null }">
		<span id="weikaoping_${s.TEA_EMPID }"><spring:message code="edu.teacherEvaluate.WEIKAOPING.a"/><!--未考评--></span>
		</c:if>
		<c:if test="${s.STU_EMPID!=null }">
		<span style="color:red;"><spring:message code="edu.teacherEvaluate.YIKAOPING.a"/><!--已考评--></span>
		</c:if>
		</c:if>
		<c:if test="${ADMINEMPID=='11111111'||ADMINEMPID=='11111112'||ADMINEMPID=='30100104'||ADMINEMPID=='40110008' }">
		<a style="color:red;" href="/edu/traineducation/teacherChakan?teacherempid=${s.TEA_EMPID }&teachername=${s.TEA_LOCAL_NAME }&BASIC_NO=${s.BASIC_NO}" id="chakan" rel="chakan" target="dialog" mask="true" width="700" height="400" >
		<span><spring:message code="button.sys.view"/><!--查看--></span></a>
		</c:if>
		</td>
		
		</tr>
		</c:forEach>
		</table>
		</div>
		
	</form>
</div>
<script type="text/javascript">


</script>
