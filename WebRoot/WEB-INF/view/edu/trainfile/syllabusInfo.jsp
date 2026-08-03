<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

</script>
<div class="pageContent" layoutH="10" >
		<table class="user_table" width="100%" border="1" cellpadding="2"
		cellspacing="1">
		<tr >
		    <td class="td_title" width="5%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
			<td class="td_title" width="5%"><spring:message code="edu.planManager.KECHENGRIQI.a"/><!--课程日期--></td>
			<td class="td_title" width="5%"><spring:message code="ess.infoApply.title.startTime"/><!--开始时间--></td>
			<td class="td_title" width="5%"><spring:message code="ess.infoApply.title.endTime"/><!--结束时间--></td>
			<td class="td_title" width="5%"><spring:message code="edu.planManager.XIANGXIDIDIAN.a"/><!--详细地点--></td>
		</tr>
		<c:forEach items="${syllabusInfo}" var="c" varStatus="i">
		<tr >
		   <td class="td_type" width="5%" >${c.COURSE_NAME_CODE }</td>
           <td class="td_type" width="5%">${c.COURSE_DATE }</td>
           <td class="td_type" width="5%">${c.TSTART }</td>
           <td class="td_type" width="5%">${c.TEND }</td>
           <td class="td_type" width="5%">${c.DETAIL_ADDRESS }</td>
		</c:forEach>
	</table>
</div>
