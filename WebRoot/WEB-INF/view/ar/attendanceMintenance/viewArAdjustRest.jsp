<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
function view_detail(year, personID){
	
}
</script>
<div class="pageHeader">
	<form id="id1" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArAdjustRest" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>:&nbsp;<input name="seach_KEY" type="text" id="seach_KEY" value="${KEY}"/>
					</td>
					<td>
						<!-- 部门 --><spring:message code="public.title.deptName"/>:&nbsp;<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
					</td>
					<td>
						<!-- 调休年 --><spring:message code="ar.viewArAdjustRest.title.tiaoxiunian"/>:&nbsp;<ait:date yearName="seach_VAC_ID" yearSelected="${VAC_ID}" yearMinus="5" yearPlus="5"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div class="pageContent">

	<table class="table" width="100%" layoutH="115">
		<thead>
			<tr>
				<th width="5"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="25"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="25"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="25"><!-- 调休年 --><spring:message code="ar.viewArAdjustRest.title.tiaoxiunian"/></th>
				<th width="25"><!-- 调休总数 --><spring:message code="ar.viewArAdjustRest.title.tiaoxiuzongshu"/></th>
				<th width="25"><!-- 已用调休数 --><spring:message code="ar.viewArAdjustRest.title.yiyongtiaoxiushu"/></th>
				<th width="25"><!-- 剩余调休数 --><spring:message code="ar.viewArAdjustRest.title.shengyutiaoxiushu"/></th>
				<th width="25"><!-- 查看详细 --><spring:message code="ar.viewArAdjustRest.title.chakanxiangxi"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arAdjustRestList}" var="list" varStatus="i">
			
				<tr>
					<td>${list.EMPID}</td>
					<td>${list.LOCAL_NAME}</td>
					<td>${list.DEPTNAME}</td>
					<td>${list.YEAR}</td>
					<td>${list.YEAR_TOTAL}</td>
					<td>${list.YEAR_USE_TOTAL}</td>
					<td>${list.ADJUST_REST}</td>
					<td><%--<a href="#" onclick="javascript:view_detail('${list.YEAR}','${list.PERSON_ID}')">每月明细</a> --%>
						<a href="/ar/attendanceMintenance/viewArAdjustRestInfo?YEAR=${list.YEAR}&PERSON_ID=${list.PERSON_ID}" target="dialog" mask="true" width="300" height="300">
                           	 <!-- 每月明细 --><spring:message code="ar.viewArAdjustRest.title.meiyuemingxi"/>
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceMintenance/viewArAdjustRest" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
