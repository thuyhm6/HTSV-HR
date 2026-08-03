<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	$('#peixunnanyidu option[value='+"${trainResultInfo.DIFFICULTY}"+']').attr('selected','selected');
	$('#peixunneirongchongshi option[value='+"${trainResultInfo.CONTENT_RICH}"+']').attr('selected','selected');
	$('#peixunshiyongxing option[value='+"${trainResultInfo.PRACTICABILITY}"+']').attr('selected','selected');
	$('#shijianshizhong option[value='+"${trainResultInfo.TIME_MODERATE}"+']').attr('selected','selected');
	 var averageScoreResu=0;
	 var count=0;
	$('#resuTable td[name=resuAllScore]').each(function(){
		averageScoreResu=averageScoreResu+parseInt($(this).html());
		count=count+1;
	});
	if(averageScoreResu>0&&count>0){
		$('#resuAve').html((averageScoreResu/count).toFixed(0));
	}
});
</script>
		<form method="post" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageContent" layoutH="10" >
		    <div class="formBar" style="padding-right: 100px;">
			<ul>
					<li>
						<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=197&BASIC_NO=${BASIC_NO }">
							<span><spring:message code="ar.addempshift.title.excelexport"/><!--Excel导出--></span>
						</a>
					</li>
			</ul>
		     </div>   
				<table id="resuTable" class="user_table" width="92%" style="margin-left:32px;" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
				<td class="td_type" width="1%" colspan='2'>${alreadyTrainResultInfo.TRAIN_TYPE_CODE_NAME}</td>
				<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
				<td class="td_type" width="1%" colspan='3'>${alreadyTrainResultInfo.COURSE_NAME_CODE}</td>
				</tr>
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
				<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_HOUR!=''}">
				<td colspan='2' class="td_type" width="1%">
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT_NAME=='0' }">
						<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="display.mutual.month"/><!--月--></span>
					</c:if>
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT_NAME=='1' }">
						<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天--></span>
					</c:if>
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT_NAME=='2' }">
						<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时--></span>
					</c:if>
				</td>
				</c:if>
				<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_HOUR==''}">
				<td colspan='2' class="td_type" width="1%"></td>
				</c:if>
				<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.PEIXUNSHISHIQIJIAN.a"/><!--培训实施期间--></td>
				<td class="td_type" width="1%" colspan='3'>${alreadyTrainResultInfo.IMPLE_START_DATE}~${alreadyTrainResultInfo.IMPLE_END_DATE}</td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message code="edu.trainResult.KECHENGNEIRONG.a"/><!--课程内容--></td>
					<td class="td_type" width="1%" colspan='6'>${alreadyTrainResultInfo.TRAIN_CONTENT}</td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message code="edu.teacherEvaluate.KAOPINGXUEYUAN.a"/><!--考评学员--></td>
					<td class="td_type" width="1%" colspan='6'>
					    <a style="color:red;" href="/edu/traineducation/checkTrainResultTSTOInfoPer?BASIC_NO=${BASIC_NO}" id="chakan1" rel="chakan1" target="dialog" mask="true" width="700" height="400" >
			            <span><spring:message code="button.sys.view"/><!--查看--></span></a>
					</td>
				</tr>
				<tr>
				   <td  style="text-align:right;" class="td_title" width="7%"><spring:message code="hrm.contract.distinguish"/><!--区分--></td>
				   <td  style="text-align:center;" class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.FEICHANGHAO.a"/><!--非常好--></td>
				   <td  style="text-align:center;" class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.BIJIAOHAO.a"/><!--比较好--></td>
				   <td  style="text-align:center;" class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.YIBAN.a"/><!--一般--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.BIJIAOBUHAO.a"/><!--比较不好--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.FEICHANGBUHAO.a"/><!--非常不好--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.MANYIDU.a"/><!--满意度--></td>
				</tr>
				<c:forEach items="${alreadyTrainResultList}" var="s" varStatus="i">
				<tr>
				   <td style="text-align:right;" class="td_type" width="7%" nowrap="nowrap">${s.TYPE_NAME }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.REV_05 }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.REV_04 }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.REV_03 }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.REV_02 }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.REV_01 }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.REV_TOTAL }</td>
			    </tr>
			    </c:forEach>
				
				<tr>
				  <td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNBAOGAO.a"/><!--培训报告--></td>
				<td class="td_type"  width="20%" colspan='7'>
				   <c:forEach items="${fileList}" var="item" varStatus="i">
					<span style="color:blue">${i.count }.</span>
					<a style="color:blue" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a><br>
				    </c:forEach>
				   </td>
			    </tr>
				</table>
		</div>
		</form>
