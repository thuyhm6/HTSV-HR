<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	 var averageScore=0;
	 var count=0;
	$('#studentChakan td[name=allscore]').each(function(){
		averageScore=averageScore+parseInt($(this).html());
		count=count+1;
	});
	if(averageScore>0&&count>0){
		$('#averageScore').html((averageScore/count).toFixed(0));
	}
});
</script>
		<div class="pageContent" layoutH="10" style="padding-top:20px;">
				<table id="studentChakan" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				<td class="td_title" width="5%"><spring:message code="edu.studentChakan.XUESHENGSHEHAO.a"/><!--学生社号:--></td>
				<td class="td_type" width="5%">${curStudentEmpid }</td>
				<td class="td_title" width="5%"><spring:message code="edu.studentChakan.XUESHENGXINGMING.a"/><!--学生姓名:--></td>
				<td class="td_type" width="5%">${curStudentName }</td>
				<td class="td_title" width="5%" style="color:red"><spring:message code="edu.teacherEvaluate.PINGJUNFEN.a"/><!--平均分:--></td>
				<td class="td_type" width="5%" id="averageScore" colspan="2" style="color:red"></td>
				</tr>
				<tr>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.PINGJIAZHESHEHAO.a"/><!--评价者社号--></td>
				<td class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.PINGJIAZHEXINGMING.a"/><!--评价者姓名--></td>
				<td class="td_title" width="5%"><spring:message code="edu.studentChakan.CHUQINLV.a"/><!--出勤率(30%)--></td>
				<td class="td_title" width="5%"><spring:message code="edu.studentChakan.JIJIXING.a"/><!--积极性(30%)--></td>
				<td class="td_title" width="5%"><spring:message code="edu.studentChakan.KETANGZHANGWO.a"/><!--课堂掌握(40%)--></td>
				<td class="td_title" width="5%"><spring:message code="edu.studentChakan.ZONGFEN.a"/><!--总分--></td>
				<td class="td_title" width="5%"><spring:message code="edu.studentChakan.QITAJIANYI.a"/><!--其它建议--></td>
				</tr>
				<c:forEach items="${studentChakanList}" var="s" varStatus="i">
				<td class="td_type" width="5%">${s.TEA_PERSON_ID }</td>
				<td class="td_type" width="5%">${s.TEA_LOCAL_NAME }</td>
				<td class="td_type" width="5%">${s.ATTEN_RATES }</td>
				<td class="td_type" width="5%">${s.ENTHUSIASM }</td>
				<td class="td_type" width="5%">${s.COURSE_CROL }</td>
				<td class="td_type" width="5%" name="allscore">${s.ALLSCORE }</td>
				<td class="td_type" width="5%" >${s.OTHER_ADVISE }</td>
				</tr>
				</c:forEach>
				
				</table>
		</div>
