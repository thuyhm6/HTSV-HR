<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/* $(function (){
	var adminempid="${ADMINEMPID}";
	if(adminempid!="11111111"&&adminempid!="11111112"&&adminempid!="30100104"&&adminempid!="40110008"){
		$('#kaopingTitle').attr('style','display:none');
		/* $('#studentEva input[name=TEST_SCORE]').each(function(){
			alert($(this).val());
		}); */
		/*	}
}); */
function studentEvabaocun(){
	var arrayfreeno="";
	/* var arrayallscore=""; */ 
	var arrayevaresult="";
	 $('#studentEva input[name=FREE_NO]').each(function(){
		if($(this).val()!=''){
			arrayfreeno=arrayfreeno+$(this).val()+',';
		}
	});
	 /*$('#studentEva input[name=TEST_SCORE]').each(function(){
		if($(this).val()!=''){
		arrayallscore=arrayallscore+$(this).val()+',';
		}else{
			arrayallscore=arrayallscore+'zero,';
		}
	}); */
	
	$('#studentEva input[name=EVA_RESULT]').each(function(){
		if($(this).val()!=''){
			arrayevaresult=arrayevaresult+$(this).val()+',';
		}else{
			arrayevaresult=arrayevaresult+'zero,';
		}
	});
	 arrayfreeno=arrayfreeno.substring(0,arrayfreeno.length-1);
	 /*arrayallscore=arrayallscore.substring(0,arrayallscore.length-1); */
	 
	arrayevaresult=arrayevaresult.substring(0,arrayevaresult.length-1);
	 $('#arrayfreeno').attr('value',arrayfreeno);
	/* $('#arrayallscore').attr('value',arrayallscore); */ 
	$('#arrayevaresult').attr('value',arrayevaresult);
	if(arrayevaresult!=''){
		$('#studentEvaluateInfo').submit();
	}else{
		alert('<spring:message code="edu.studentEvaluate.QINGXIANTIANJIAKAOSHICHENGJI.a"/>');//请先添加考试成绩,再进行保存!
	}
	
}
function studentEvaImport(){
	$("#importExcelDialogStudentEva").attr('href','/pa/excelImport/importExcelData?importFunName=/importStudentEvaluate?basic_no=${studentEvaluateInfo.BASIC_NO }');
	$("#importExcelDialogStudentEva").click();
	//$.pdialog.reload('/edu/traineducation/studentEvaluateInfo?BASIC_NO=${studentEvaluateInfo.BASIC_NO }','','updateStu'); 
}
function changeEvaResult(freeno){
	var kaopingavg=$('#KP_AVG_SCORE_'+freeno).val();
	var testscore=$('#TEST_SCORE_'+freeno).val();
	if(kaopingavg==''){
		kaopingavg='0';
	}
	if(testscore==''){
		testscore='0';
	}
	var avgresult=parseInt(kaopingavg)*0.4+parseInt(testscore)*0.6;
	$('#eva_result_'+freeno).html(avgresult.toFixed(0));
	
}
</script>

<div class="pageContent" layoutH="10">
	<form method="post" id="studentEvaluateInfo" action="/edu/traineducation/updateStudentEvaluateInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		 <input type="hidden" name="arrayfreeno" id="arrayfreeno" value="">
		 <input type="hidden" name="arrayallscore" id="arrayallscore" value="">
		 <input type="hidden" name="arrayevaresult" id="arrayevaresult" value="">
		 <a id="importExcelDialogStudentEva"  href="" target="dialog" rel="evaimport" mask="true" width="500" height="200"></a>
		 <div class="formBar" id="kaopingTitle" style="">
			<ul>
			        <li>
						<a class="buttonActive" href="#" onclick="studentEvaImport()">
							<span><spring:message code="ar.addempshift.title.excelimport"/><!--Excel导入--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" href="/edu/traineducation/studentEvaImportDemoLoad?flag=load" >
							<span><spring:message code="pa.button.message.specialempimportmodeldown"/><!--导入模板下载--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=157&BASIC_NO=${studentEvaluateInfo.BASIC_NO }">
							<span><spring:message code="ess.infoApply.EXCEL_OUT"/><!--Excel导出--></span>
						</a>
					</li>
					<%--
					<li>
						<a class="buttonActive" href="javascript:;" onclick="$.pdialog.reload('/edu/traineducation/studentEvaluateInfo?BASIC_NO=${studentEvaluateInfo.BASIC_NO }','','updateStu');"><span>刷新</span></a>
					</li>
				<li onclick="$.pdialog.reload('/edu/traineducation/studentEvaluateInfo?BASIC_NO=${studentEvaluateInfo.BASIC_NO }','','updateStu');studentEvabaocun()"><div class="buttonActive"><div class="buttonContent">保存</div></div></li>
				--%>
				<li onclick="studentEvabaocun()"><div class="buttonActive"><div class="buttonContent"><spring:message code="ess.message.save"/><!--保存--></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>     
		
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
			<c:if test="${studentEvaluateInfo.IMPLE_CLASS_UNIT=='0' }">
				<span>${studentEvaluateInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="display.mutual.month"/><!--月--></span>
			</c:if>
			<c:if test="${studentEvaluateInfo.IMPLE_CLASS_UNIT=='1' }">
				<span>${studentEvaluateInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天--></span>
			</c:if>
			<c:if test="${studentEvaluateInfo.IMPLE_CLASS_UNIT=='2' }">
				<span>${studentEvaluateInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时--></span>
			</c:if>
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
		<td class="td_type"  width="5%" >${i.count}</td>
		<td class="td_type"  width="20%" >${s.EMPID }</td>
		<td class="td_type"  width="20%">${s.LOCAL_NAME }</td>
		<td class="td_type"  width="20%">${s.ORG_NAME_LOCAL }</td>
		<td class="td_type"  width="20%">${s.POST_GRADE_NO_NAME }</td>
		<td class="td_type"  width="20%">
		<%-- <c:if test="${ADMINEMPID=='11111111'||ADMINEMPID=='11111112'||ADMINEMPID=='30100104'||ADMINEMPID=='40110008' }"> --%>
		<input type="text" name="EVA_RESULT"  value="${s.EVA_RESULT }" min="0" max="100" style="width:100px;">
		<%-- </c:if> --%>
		<input type="hidden" name="KP_AVG_SCORE" id="KP_AVG_SCORE_${s.FREE_NO }" value="${s.KP_AVG_SCORE }">
		</td>
		<%-- <td class="td_type"  width="40%" nowrap="nowrap">
		<c:if test="${ALLTEACHEREMPID!='11111111'&&ALLTEACHEREMPID!='11111112'&&flag=='1' }">
		<c:if test="${s.TEA_PERSON_ID==null }">
		<a id="weikao_${s.EMPID }" href="/edu/traineducation/studentKaoping?studentempid=${s.EMPID }&studentname=${s.LOCAL_NAME }&BASIC_NO=${s.BASIC_NO}" id="kaoping" rel="kao" target="dialog" mask="true" width="600" height="400" >
		<span id="weikaoping_${s.EMPID }">未考评</span></a>
		</c:if>
		<c:if test="${s.TEA_PERSON_ID!=null }">
		<a style="color:red;" href="/edu/traineducation/studentKaoping?studentempid=${s.EMPID }&studentname=${s.LOCAL_NAME }&BASIC_NO=${s.BASIC_NO}" id="kaoping" rel="kao" target="dialog" mask="true" width="600" height="400" >
		<span>已考评</span></a>
		</c:if>
		</c:if>
		<c:if test="${ADMINEMPID=='11111111'||ADMINEMPID=='11111112'||ADMINEMPID=='30100104'||ADMINEMPID=='40110008' }">
		<a style="color:red;" href="/edu/traineducation/studentChakan?studentempid=${s.EMPID }&studentname=${s.LOCAL_NAME }&BASIC_NO=${s.BASIC_NO}" id="chakan" rel="chakan" target="dialog" mask="true" width="600" height="400" >
		<span>查看</span></a>
		</c:if>
		</td> --%>
		<%-- <td class="td_type"  width="20%" name="EVA_RESULT" id="eva_result_${s.FREE_NO }">
		<c:if test="${ADMINEMPID=='11111111'||ADMINEMPID=='11111112'||ADMINEMPID=='30100104'||ADMINEMPID=='40110008' }">
		${s.EVA_RESULT }
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
