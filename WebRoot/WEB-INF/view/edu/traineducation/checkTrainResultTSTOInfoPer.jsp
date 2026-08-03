<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
		<div class="pageContent" layoutH="10" >  
				<table id="resuTable" class="user_table" width="92%" style="margin-left:32px;" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
				<td class="td_type" width="1%" colspan='3'>${alreadyTrainResultInfo.TRAIN_TYPE_CODE_NAME}</td>
				<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
				<td class="td_type" width="1%" colspan='2'>${alreadyTrainResultInfo.COURSE_NAME_CODE}</td>
				</tr>
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
				<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_HOUR!=''}">
				<td colspan='3' class="td_type" width="1%">
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
				<td class="td_type" width="1%" colspan='2'>${alreadyTrainResultInfo.IMPLE_START_DATE}~${alreadyTrainResultInfo.IMPLE_END_DATE}</td>
				</tr>
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.trainResult.KECHENGNEIRONG.a"/><!--课程内容--></td>
				<td class="td_type" width="1%" colspan='3'>${alreadyTrainResultInfo.TRAIN_CONTENT}</td>
				<td class="td_title" width="1%"></td>
				<td class="td_type" width="1%" colspan='2'></span></td>
				</tr>
				<tr>
				   <td  style="text-align:right;" class="td_title" width="2%">NO.</td>
				   <td  style="text-align:center;" class="td_title" width="7%"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号--></td>
				   <td  style="text-align:center;" class="td_title" width="7%"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></td>
				   <td  style="text-align:center;" class="td_title" width="5%"><spring:message code="edu.trainResult.KECHENGNEIRONGSHIFOUSHIYONG.a"/><!--课程的内容是否实用--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.trainResult.KECHENGYIZHANGWODECHENGDU.a"/><!--课程易掌握的程度--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.trainResult.KECHENGDESHIJIANCHANGDU.a"/><!--课程的时间长度--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.trainResult.KECHENGDEZHENGTIMANYIDU.a"/><!--课程的整体满意度--></td>
				</tr>
				<c:forEach items="${trainResultTSTOInfoPerList}" var="s" varStatus="i">
				<tr>
				   <td style="text-align:right;" class="td_type" width="2%">${i.index+1}</td>
				   <td style="text-align:center;" class="td_type" width="7%">${s.STU_EMPID }</td>
				   <td style="text-align:center;" class="td_type" width="7%">${s.STU_LOCAL_NAME }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.PRACTICABILITY }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.CONTENT_RICH }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.TIME_MODERATE }</td>
				   <td style="text-align:center;" class="td_type" width="5%">${s.DIFFICULTY }</td>
			    </tr>
			    </c:forEach>
				</table>
		</div>
