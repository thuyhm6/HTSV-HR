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
		<div class="pageContent" layoutH="9" style="padding-top:20px;">
				<table id="teacherChakan" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				<td class="td_title" width="3%" colspan="2"><spring:message code="edu.teacherEvaluate.JIANGSHISHEHAO.a"/><!--讲师社号:--></td>
				<td class="td_type" width="3%">${curteacherempid }</td>
				<td class="td_title" width="3%"><spring:message code="edu.teacherEvaluate.JIANGSHIXINGMING.a"/><!--讲师姓名:--></td>
				<td class="td_type" width="3%">${curteachername }</td>
				</tr>
				<tr>
				<td style="text-align: center;"  class="td_title" width="1%">NO.</td>
				<td style="text-align: center;" class="td_title" width="3%"><spring:message code="edu.teacherEvaluate.PINGJIAZHESHEHAO.a"/><!--评价者社号--></td>
				<td style="text-align: center;" class="td_title" width="3%"><spring:message code="edu.teacherEvaluate.PINGJIAZHEXINGMING.a"/><!--评价者姓名--></td>
				<td style="text-align: center;" class="td_title" width="3%" colspan="2"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>
				</tr>
				<c:forEach items="${teacherChakanList}" var="s" varStatus="i">
				<td style="text-align: center;" class="td_type" width="1%">${i.count }</td>
				<td style="text-align: center;" class="td_type" width="3%">${s.STU_EMPID }</td>
				<td style="text-align: center;" class="td_type" width="3%">${s.STU_LOCAL_NAME }</td>
				<td style="text-align: center;" class="td_type" width="3%" colspan="2">${s.GROOMING }</td>
				</tr>
				</c:forEach>
				
				</table>
		</div>
