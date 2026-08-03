<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function deleteCourseSyllabus(id){
  var SYLL_NO = id;  
  $('#deleteCourseSyllabus'+SYLL_NO).attr('href','/edu/traineducation/deleteCourseSyllabus?syll_no='+SYLL_NO);
}

</script>
<form id="courseSyllabus" onsubmit="return navTabSearch(this);" action="/edu/traineducation/queryCourseSyllabus?PLAN_NO=${PLAN_NO }" method="post">
</form>
<div class="subBar" style="margin-top: 20px; padding-left: 1050px;">
		<ul>
			<li>
				<a  style="color:blue;" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a"/><!--确定是否删除?-->" id="deleteCourseSyllabus${d.SYLL_NO}" onclick="deleteCourseSyllabus(${d.SYLL_NO});" href="#" callback="dialogAjaxDone" fresh="true" target="ajaxTodo">
							<span><spring:message code="button.delete"/><!--删除--></span>
		  </a>
			</li>
		</ul>
</div>
<div class="pageContent" layoutH="10">
		<table id="tearcherTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<span>Total:${queryCourseSyllabusListCount }</span>
		<tr>
		<td class="td_title" width="1%">NO</td>
		<td class="td_title" style="text-align: center;"  width="15%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
		<td class="td_title" style="text-align: center;"  width="10%"><spring:message code="edu.planManager.KECHENGRIQI.a"/><!--课程日期--></td>
		<td class="td_title" style="text-align: center;"  width="7%"><spring:message code="ess.infoApply.title.startTime"/><!--开始时间--></td>
		<td class="td_title" style="text-align: center;"  width="7%"><spring:message code="ess.infoApply.title.endTime"/><!--结束时间--></td>
		<td class="td_title" style="text-align: center;"  width="5%"><spring:message code="edu.planManager.XIANGXIDIDIAN.a"/><!--搜索--></td>
		<td class="td_title" style="text-align: center;"  width="3%"><spring:message code="button.delete"/><!--删除--></td>
		</tr>
		<c:forEach items="${queryCourseSyllabusList}" var="d" varStatus="i">
		<tr>
		<td class="td_type"  style="text-align: center;"  width="1%" >${i.count }</td>
		<td class="td_type"  style="text-align: center;"  width="15%" >${d.COURSE_NAME_CODE }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >${d.COURSE_DATE }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >${d.COURSE_START_DATE }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >${d.COURSE_END_DATE }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >${d.DETAIL_ADDRESS }</td>
		<td class="td_type"  style="text-align: center;"  width="5%" >
		   <a  style="color:blue;" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a"/><!--确定是否删除?-->" id="deleteCourseSyllabus${d.SYLL_NO}" onclick="deleteCourseSyllabus(${d.SYLL_NO});" href="#" callback="dialogAjaxDone" fresh="true" target="ajaxTodo">
							<span><spring:message code="button.delete"/><!--删除--></span>
		  </a>
		</td>
		</tr>
		</c:forEach>
		</table>
</div>
