<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">


</script>
<div class="pageContent" layoutH="10">
		<table id="tearcherTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<span>Total:${queryCourseSyllabusListCount }</span>
		<tr>
		<td class="td_title" width="1%">NO</td>
		<td class="td_title" style="text-align: center;"  width="15%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_title" style="text-align: center;"  width="5%"><spring:message code="edu.planManager.KECHENGRIQI.a"/><!--课程日期--></td>
		<td class="td_title" style="text-align: center;"  width="5%"><spring:message code="ess.infoApply.title.startTime"/><!--开始时间--></td>
		<td class="td_title" style="text-align: center;"  width="5%"><spring:message code="edu.planManager.JIESHUSHIJIAN.a"/><!--结束时间--></td>
		<td class="td_title" style="text-align: center;"  width="5%"><spring:message code="edu.planManager.XIANGXIDIDIAN.a"/><!--详细地点--></td>
		</tr>
		<c:forEach items="${queryCourseSyllabusList}" var="d" varStatus="i">
		<tr>
		<td class="td_type"  style="text-align: center;"  width="1%" >${i.count }</td>
		<td class="td_type"  style="text-align: center;"  width="15%" >${d.COURSE_NAME_CODE }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >${d.COURSE_DATE }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >${d.COURSE_START_DATE }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >${d.COURSE_END_DATE }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >${d.DETAIL_ADDRESS }</td>
		</tr>
		</c:forEach>
		</table>
</div>
