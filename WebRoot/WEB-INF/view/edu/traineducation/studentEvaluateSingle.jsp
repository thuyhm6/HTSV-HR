<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function (){
	var adminempid="${ADMINEMPID}";
	if(adminempid!="11111111"&&adminempid!="11111112"&&adminempid!="30100104"&&adminempid!="40110008"){
		$('#kaopingTitle').attr('style','display:none');
		/* $('#studentEva input[name=TEST_SCORE]').each(function(){
			alert($(this).val());
		}); */
	}
});
function studentEvabaocun(){
	var arrayfreeno="";
	var arrayallscore="";
	var arrayevaresult="";
	$('#studentEva input[name=FREE_NO]').each(function(){
		if($(this).val()!=''){
			arrayfreeno=arrayfreeno+$(this).val()+',';
		}
	});
	$('#studentEva input[name=TEST_SCORE]').each(function(){
		if($(this).val()!=''){
		arrayallscore=arrayallscore+$(this).val()+',';
		}else{
			arrayallscore=arrayallscore+'zero,';
		}
	});
	$('#studentEva td[name=EVA_RESULT]').each(function(){
		if($(this).html()!=''){
			arrayevaresult=arrayevaresult+$(this).html()+',';
		}else{
			arrayevaresult=arrayevaresult+'zero,';
		}
	});
	arrayfreeno=arrayfreeno.substring(0,arrayfreeno.length-1);
	arrayallscore=arrayallscore.substring(0,arrayallscore.length-1);
	arrayevaresult=arrayevaresult.substring(0,arrayevaresult.length-1);
	$('#arrayfreeno').attr('value',arrayfreeno);
	$('#arrayallscore').attr('value',arrayallscore);
	$('#arrayevaresult').attr('value',arrayevaresult);
	if(arrayfreeno!=''&&arrayallscore!=''){
		$('#studentEvaluateInfo').submit();
	}else{
		alert('<spring:message code="edu.studentEvaluate.QINGXIANTIANJIAKAOSHICHENGJI.a"/>');//请先添加考试成绩,再进行保存!
	}
	
}
function studentEvaImport(){
	$("#importExcelDialogStudentEva").attr('href','/pa/excelImport/importExcelData?importFunName=/importStudentEvaluate?basic_no=${studentEvaluateInfo.BASIC_NO }');
	$("#importExcelDialogStudentEva").click();
}
</script>

<div class="pageContent" layoutH="10">
	<form method="post" id="studentEvaluateInfo" action="/edu/traineducation/updateStudentEvaluateInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		 <input type="hidden" name="arrayfreeno" id="arrayfreeno" value="">
		 <input type="hidden" name="arrayallscore" id="arrayallscore" value="">
		 <input type="hidden" name="arrayevaresult" id="arrayevaresult" value="">
		 <a id="importExcelDialogStudentEva"  href="" target="dialog" rel="evaimport" mask="true" width="500" height="200"></a>
		
		<div class="pageFormContent nowrap">
		<input type="hidden" name="BASIC_NO" id="BASIC_NO" value="${studentEvaluateInfo.BASIC_NO }">
		<table id="studentEva" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
		<td class="td_type"  width="20%" colspan='1'>
		<span id="traintype">${studentEvaluateInfo.TRAIN_TYPE_CODE_NAME }</span>
		</td>
		<td class="td_title" width="1%" ><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type"  width="20%" colspan='3'>
		<span id="trainname">${studentEvaluateInfo.COURSE_NAME_CODE }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%" ><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
		<td class="td_type"  width="20%" colspan='1'>
		<span>${studentEvaluateInfo.IMPLE_CLASS_HOUR }&nbsp
		<c:if test="${studentEvaluateInfo.IMPLE_CLASS_UNIT eq '0' }"><spring:message code="display.mutual.month"/><!--月--></c:if>
		<c:if test="${studentEvaluateInfo.IMPLE_CLASS_UNIT eq '1' }"><spring:message code="display.mutual.day"/><!--天--></c:if>
		<c:if test="${studentEvaluateInfo.IMPLE_CLASS_UNIT eq '2' }"><spring:message code="ar.viewitemparameter.title.xiaoshi"/><!--小时--></c:if>
		</span>
		</td>
		<td class="td_title" width="1%" ><spring:message code="edu.trainArchives.PEIXUNNEIRONG.a"/><!--培训内容--></td>
		<td class="td_type"  width="20%" colspan='3'>
		<span>${studentEvaluateInfo.TRAIN_CONTENT }</span>
		</td>
		</tr>
		
		
		<tr>
		<td class="td_title" width="1%">NO.</td>
		<td class="td_title" width="1%"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号--></td>
		<td class="td_title" width="1%"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></td>
		<td class="td_title" width="1%"><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门--></td>
		<td class="td_title" width="1%"><spring:message code="ess.trans.title.postGradeName"/><!--职级--></td>
		<td class="td_title" width="1%"><spring:message code="edu.studentEvaluate.KAOSHICHENGJI.a"/><!--考试成绩--></td>
		<!-- <td class="td_title" width="2%">学员考评</td>
		<td class="td_title" width="1%">评价结果</td> -->
		</tr>
		<c:forEach items="${studentEvaluateList}" var="s" varStatus="i">
		<input type="hidden" name="FREE_NO" id="FREE_NO" value="${s.FREE_NO }">
		<tr>
		<td class="td_type"  width="5%" >${i.count }</td>
		<td class="td_type"  width="20%" >${s.EMPID }</td>
		<td class="td_type"  width="20%">${s.LOCAL_NAME }</td>
		<td class="td_type"  width="20%">${s.ORG_NAME_LOCAL }</td>
		<td class="td_type"  width="20%">${s.POST_GRADE_NO_NAME }</td>
		<td class="td_type"  width="20%">
		<%-- <c:if test="${ADMINEMPID=='11111111'||ADMINEMPID=='11111112'||ADMINEMPID=='30100104'||ADMINEMPID=='40110008' }"> --%>
		<%-- <input type="text" name="TEST_SCORE" id="TEST_SCORE" value="${s.TEST_SCORE }" min="0" max="100" style="width:100px;"> --%>
		<span>${s.EVA_RESULT }</span>
		<%-- </c:if> --%>
		<%-- </td>
		<td class="td_type"  width="40%" nowrap="nowrap">
		<c:if test="${ALLTEACHEREMPID!='11111111'&&ALLTEACHEREMPID!='11111112'&&flag=='1' }">
		<c:if test="${s.TEA_PERSON_ID==null }">
		<span id="weikaoping_${s.EMPID }">未考评</span>
		</c:if>
		<c:if test="${s.TEA_PERSON_ID!=null }">
		<span style="color:red">已考评</span>
		</c:if>
		</c:if>
		<c:if test="${ADMINEMPID=='11111111'||ADMINEMPID=='11111112'||ADMINEMPID=='30100104'||ADMINEMPID=='40110008' }">
		<a style="color:red;" href="/edu/traineducation/studentChakan?studentempid=${s.EMPID }&studentname=${s.LOCAL_NAME }&BASIC_NO=${s.BASIC_NO}" id="chakan" rel="chakan" target="dialog" mask="true" width="600" height="400" >
		<span>查看</span></a>
		</c:if>
		</td>
		<td class="td_type"  width="20%" name="EVA_RESULT">
		<c:if test="${ADMINEMPID=='11111111'||ADMINEMPID=='11111112'||ADMINEMPID=='30100104'||ADMINEMPID=='40110008' }">
		${s.finaResult }
		</c:if>
		</td> --%>
		</tr>
		</c:forEach>
		</table>
		</div>
		
	</form>
</div>
<script type="text/javascript">


</script>
