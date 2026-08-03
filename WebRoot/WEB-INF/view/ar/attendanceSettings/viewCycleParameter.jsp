<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewCycleParameter" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 参数别名 --><spring:message code="ar.viewcycleparameter.title.qujianmingcheng"/>：<input type="text" name="seach_ALIAS_NAME" value="${ALIAS_NAME}"/>
					</td>
					<%-- 
					<td>公司</td>
					<td>
						<select name="seach_CPNY_ID" class="combox">
							<option value="">全部</option>
							<c:forEach items="${cpnyList}" var="cpny">
								<option value="${cpny.CPNY_ID}" <c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
							</c:forEach>
						</select>
					</td>
					 --%>
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

	<c:set value="dialog" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addCycleParamView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/ar/attendanceSettings/deleteCycleParamInfo?NO={paramno}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="350" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateCycleParamView?NO={paramno}&PRO_NO=1" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th><!-- 区间NO --><spring:message code="ar.viewcycleparameter.title.qujian"/>NO</th>
				<th><!-- 区间名称 --><spring:message code="ar.viewcycleparameter.title.qujianmingcheng"/></th>
				<th><!-- 公司名称 --><spring:message code="ar.viewcycleparameter.title.gongsimingcheng"/></th>
				<th><!-- 开始日期 --><spring:message code="ar.viewcycleparameter.title.kaishiriqi"/></th>
				<th><!-- 结束日期 --><spring:message code="ar.viewcycleparameter.title.jieshuriqi"/></th>
				<th><!-- 状态 --><spring:message code="ar.viewcycle.title.zhuangtai"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paramList}" var="parameter">
				<tr target="paramno" rel="${parameter.PARAM_NO}&STATISTIC_NO=${parameter.STAT_NO}">
					<td>${parameter.STAT_NO}</td>
					<td>${parameter.STAT_NAME}</td>
					<td>${parameter.CPNY_NAME}</td>
					<td>${parameter.START_DATE}</td>
					<td>${parameter.END_DATE}</td>
					<td><img src="/resources/images/a_${parameter.ACTIVITY}.gif"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceSettings/viewCycleParameter" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>