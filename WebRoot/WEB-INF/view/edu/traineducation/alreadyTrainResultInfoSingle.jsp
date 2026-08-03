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
						<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=161&BASIC_NO=${BASIC_NO }">
							<span><spring:message code="ar.addempshift.title.excelexport"/><!--Excel导出--></span>
						</a>
					</li>
			</ul>
		     </div>   
				<table id="resuTable" class="user_table" width="92%" style="margin-left:32px;" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
				<td class="td_type" width="1%" colspan='3'>${alreadyTrainResultInfo.TRAIN_TYPE_CODE_NAME}</td>
				<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
				<td class="td_type" width="1%" colspan='4'>${alreadyTrainResultInfo.COURSE_NAME_CODE}</td>
				</tr>
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
				<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_HOUR!=''}">
				<td colspan='3' class="td_type" width="1%">
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT=='0' }">
						<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="display.mutual.month"/><!--月--></span>
					</c:if>
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT=='1' }">
						<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天--></span>
					</c:if>
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT=='2' }">
						<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时--></span>
					</c:if>
				</td>
				</c:if>
				<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_HOUR==''}">
				<td colspan='3' class="td_type" width="1%"></td>
				</c:if>
				<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.PEIXUNSHISHIQIJIAN.a"/><!--培训实施期间--></td>
				<td class="td_type" width="1%" colspan='4'>${alreadyTrainResultInfo.IMPLE_START_DATE}~${alreadyTrainResultInfo.IMPLE_END_DATE}</td>
				</tr>
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.trainResult.KECHENGNEIRONG.a"/><!--课程内容--></td>
				<td class="td_type" width="1%" colspan='3'>${alreadyTrainResultInfo.TRAIN_CONTENT}</td>
				<td class="td_title" width="1%"><spring:message code="edu.trainResult.PINGJIAJIEGUO.a"/><!--评价结果(平均值)--></td>
				<td class="td_type" width="1%" colspan='4'><span id="resuAve" style="color:red;"></span></td>
				</tr>
				<tr>
				<td class="td_title" width="1%">Total:</td>
				<td class="td_type" width="1%" colspan='8'>${alreadyTrainResultListCount}</td>
				</tr>
				<tr>
				   <td class="td_title" width="1%"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.PINGJIAZHE.a"/><!--评价者--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNNANYIDU.a"/><!--培训难易程度(20%)--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNNEIRONGCHONGSHI.a"/><!--培训内容充实(20%)--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNSHIYONGXING.a"/><!--培训实用性(40%)--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNSHIJIANSHIZHONG.a"/><!--培训时间适中(20%)--></td>
				   <td class="td_title" width="1%"><spring:message code="hrm.approve.AMOUNT"/><!--合计--></td>
				   <td class="td_title" width="1%"><spring:message code="hr.viewSuggestion.title.Suggestion"/><!--意见--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNBAOGAO.a"/><!--培训报告--></td>
				</tr>
				<c:forEach items="${alreadyTrainResultList}" var="s" varStatus="i">
				<tr>
				   <td class="td_type" width="1%">${s.STU_EMPID }</td>
				   <td class="td_type" width="1%">${s.STU_LOCAL_NAME }</td>
				   <td class="td_type" width="1%">${s.DIFFICULTY }</td>
				   <td class="td_type" width="1%">${s.CONTENT_RICH }</td>
				   <td class="td_type" width="1%">${s.PRACTICABILITY }</td>
				   <td class="td_type" width="1%">${s.TIME_MODERATE }</td>
				   <td class="td_type" width="1%" name="resuAllScore">${s.ALLSCORE }</td>
				   <td class="td_type" width="1%">${s.OTHER_ADVISE }</td>
				   <td class="td_type" width="1%" >
				   <c:forEach items="${s.fileList}" var="item" varStatus="i">
					<span style="color:blue">${i.count }.</span>
					<a style="color:blue" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a><br>
				    </c:forEach>
				   </td>
			    </tr>
				</c:forEach>
				</table>
		</div>
		</form>
