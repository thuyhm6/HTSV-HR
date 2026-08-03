<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/* $(function (){
	var adminempid="${ADMINEMPID}";
	if(adminempid!="11111111"&&adminempid!="11111112"&&adminempid!="30100104"&&adminempid!="40110008"){
		$('#teakaopingTitle').attr('style','display:none');
	}
}); */

function teacherEvaluateImport(TEA_EMPID,BASIC_NO){
		$("#importExcelDialogteacherEvaluate").attr('href','/pa/excelImport/importExcelData?importFunName=/teacherEvaluateImport?parmater='+TEA_EMPID+'@'+BASIC_NO);
		$("#importExcelDialogteacherEvaluate").attr('height', "100");
		$("#importExcelDialogteacherEvaluate").attr('width', "200");
		$("#importExcelDialogteacherEvaluate").click();
}
</script>

<div class="pageContent" layoutH="10">
	<form method="post" id="teacherEvaluateInfo" action="/edu/traineducation/updateStudentEvaluateInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		 <a id="importExcelDialogStudentEva"  href="" target="dialog" rel="evaimport" mask="true" width="500" height="200"></a>
		 <div class="formBar" id="teakaopingTitle" style="">
			<ul>
			     
					<li>
						<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=160&BASIC_NO=${teacherEvaluateInfo.BASIC_NO }">
							<span><spring:message code="ess.infoApply.EXCEL_OUT"/><!--Excel导出--></span>
						</a>
					</li>
				 <li onclick="teacherEvabaocun()"><div class="buttonActive"><div class="buttonContent"><spring:message code="ess.message.save"/><!--保存--></div></div></li> 
				  <li>
					<a class="buttonActive" href="/edu/traineducation/teacherEvaluateInfoImportDemoLoad?flag=load" ><span><spring:message code="pa.button.message.specialempimportmodeldown"/><!--导入模板下载--></span></a>
				  </li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>     
		<a id="importExcelDialogteacherEvaluate"  href="#" target="dialog" mask="true" width="300" height="200"></a>
		<div class="pageFormContent nowrap">
		<input type="hidden" name="BASIC_NO" id="BASIC_NO" value="${teacherEvaluateInfo.BASIC_NO }">
		<table id="teacherEva" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%" colspan='2'><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
		<td class="td_type"  width="30%" colspan='4'>
		<span id="traintype">${teacherEvaluateInfo.TRAIN_TYPE_CODE_NAME }</span>
		</td>
		<td class="td_title" width="1%" colspan='2'><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_type"  width="20%" colspan='5'>
		<span id="trainname">${teacherEvaluateInfo.COURSE_NAME_CODE }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%" colspan='2'><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
		<td class="td_type"  width="30%" colspan='4'>
		<span>${teacherEvaluateInfo.IMPLE_CLASS_HOUR }${teacherEvaluateInfo.IMPLE_CLASS_UNIT_NAME }</span>
		</td>
		<td class="td_title" width="1%" colspan='2'><spring:message code="edu.planManager.PEIXUNNEIRONG.a"/><!--培训内容--></td>
		<td class="td_type"  width="20%" colspan='5'>
		<span>${teacherEvaluateInfo.TRAIN_CONTENT }</span>
		</td>
		</tr>
		
		
		<tr>
		<td class="td_title" width="10%" rowspan="2"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号--></td>
		<td class="td_title" width="10%" rowspan="2"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></td>
		<td class="td_title" width="10%" rowspan="2"><spring:message code="ess.trans.title.postGradeName"/><!--职级--></td>
		<td class="td_title" width="10%" rowspan="2"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!--部门--></td>
		<%-- <td class="td_title" width="10%" rowspan="2"><spring:message code="inct.salesman.position"/><!--职务--></td> --%>
		<td class="td_title" width="10%" rowspan="2" style="text-align:center"><spring:message code="edu.teacherEvaluate.KAOPINGXUEYUAN.a"/><!--考评学员--></td>
		<td class="td_title" width="10%" colspan="7" style="text-align:center"><spring:message code="inct.salesman.eval.result"/><!--评价结果--></td>
		</tr>
		<tr>
		   <td class="td_title" width="5%" ><spring:message code="edu.teacherEvaluate.FEICHANGHAO.a"/><!--非常好--></td>
		   <td class="td_title" width="5%" ><spring:message code="edu.teacherEvaluate.BIJIAOHAO.a"/><!--比较好--></td>
		   <td class="td_title" width="5%" ><spring:message code="edu.teacherEvaluate.YIBAN.a"/><!--一般--></td>
		   <td class="td_title" width="5%" ><spring:message code="edu.teacherEvaluate.BIJIAOBUHAO.a"/><!--比较不好--></td>
		   <td class="td_title" width="5%" ><spring:message code="edu.teacherEvaluate.FEICHANGBUHAO.a"/><!--非常不好--></td>
		   <td class="td_title" width="5%" ><spring:message code="edu.teacherEvaluate.MANYIDU.a"/><!--满意度--></td>
		   <td class="td_title" width="10%" ><spring:message code="edu.teacherEvaluate.DAORU.a"/><!--导入--></td>
		</tr>
		<c:forEach items="${teacherEvaluateList}" var="s" varStatus="i">
		<tr>
		<td class="td_type"  width="10%" style="text-align:center">${s.TEA_EMPID }</td>
		<td class="td_type"  width="10%">${s.TEA_LOCAL_NAME }</td>
		<td class="td_type"  width="10%" title="${s.POST_GRADE_NO_NAME }">${fn:substring(s.POST_GRADE_NO_NAME,0,6) }</td>
		<td class="td_type"  width="10%" title="${s.ORG_NAME_LOCAL }">${fn:substring(s.ORG_NAME_LOCAL,0,5) }..</td>
		<%-- <td class="td_type"  width="10%">${s.POSITION_NO_NAME }</td> --%>
		<td class="td_type"  width="10%" nowrap="nowrap" style="text-align:center">
		<%-- <c:if test="${ADMINEMPID=='11111111'||ADMINEMPID=='11111112'||ADMINEMPID=='30100104'||ADMINEMPID=='40110008' }"> --%>
		<a style="color:red;" href="/edu/traineducation/teacherTSTOChakan?teacherempid=${s.TEA_EMPID }&teachername=${s.TEA_LOCAL_NAME }&BASIC_NO=${s.BASIC_NO}" id="chakan" rel="chakan" target="dialog" mask="true" width="700" height="600" >
		<span><spring:message code="button.sys.view"/><!--查看--></span></a>
		<%-- </c:if> --%>
		</td>
		<td class="td_type"  width="5%">${s.REV_05 }</td>
		<td class="td_type"  width="5%">${s.REV_04 }</td>
		<td class="td_type"  width="5%">${s.REV_03 }</td>
		<td class="td_type"  width="5%">${s.REV_02 }</td>
		<td class="td_type"  width="5%">${s.REV_01 }</td>
		<td class="td_type"  width="5%">${s.REV_TOTAL }</td>
		<td class="td_type"  width="10%">
		   <a style="color:blue;" href="#" onclick="teacherEvaluateImport(${s.TEA_EMPID },${s.BASIC_NO})"><span><spring:message code="edu.teacherEvaluate.DAORU.a"/><!--导入--></span></a>  
		</td>
		</tr>
		</c:forEach>
		</table>
		</div>
	</form>
</div>
<script type="text/javascript">


</script>
