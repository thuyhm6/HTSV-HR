<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	 var averageScoreTea=0;
	 var count=0;
	$('#teacherChakan td[name=allscoretea]').each(function(){
		averageScoreTea=averageScoreTea+parseInt($(this).html());
		count=count+1;
	});
	if(averageScoreTea>0&&count>0){
		$('#averageScoreTea').html((averageScoreTea/count).toFixed(0));
	}
});
</script>
		<div class="pageContent" layoutH="8" style="padding-top:20px;">
				<table id="teacherChakan" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.JIANGSHISHEHAO.a"/><!--讲师社号:--></td>
				<td class="td_type" width="5%">${curteacherempid }</td>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.JIANGSHIXINGMING.a"/><!--讲师姓名:--></td>
				<td class="td_type" width="5%">${curteachername }</td>
				<td class="td_title" width="5%" style="color:red"><spring:message code="edu.teacherEvaluate.PINGJUNFEN.a"/><!--平均分:--></td>
				<td class="td_type" width="5%" id="averageScoreTea" colspan="3" style="color:red"></td>
				</tr>
				<tr>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.PINGJIAZHESHEHAO.a"/><!--评价者社号--></td>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.PINGJIAZHEXINGMING.a"/><!--评价者姓名--></td>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.YIRONGYIBIAO.a"/><!--仪容仪表(20%)--></td>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.JIJIREQING.a"/><!--积极热情(20%)--></td>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.KECHENGNEIRONGZHUNBEICHONGSHI.a"/><!--课程内容准备充实(30%)--></td>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.HUDONGXING.a"/><!--互动性(30%)--></td>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.JIANYI.a"/><!--建议--></td>
				<td class="td_title" width="5%"><spring:message code="ess.viewpersonalpainfo.heji"/><!--合计--></td>
				</tr>
				<c:forEach items="${teacherChakanList}" var="s" varStatus="i">
				<td class="td_type" width="5%">${s.STU_EMPID }</td>
				<td class="td_type" width="5%">${s.STU_LOCAL_NAME }</td>
				<td class="td_type" width="5%">${s.GROOMING }</td>
				<td class="td_type" width="5%">${s.POSITIVE }</td>
				<td class="td_type" width="5%">${s.COURSE_ENRICH }</td>
				<td class="td_type" width="5%">${s.INTERACT }</td>
				<td class="td_type" width="5%" >${s.OTHER_ADVISE }</td>
				<td class="td_type" width="5%" name="allscoretea">${s.ALLSCORE }</td>
				</tr>
				</c:forEach>
				
				</table>
		</div>
