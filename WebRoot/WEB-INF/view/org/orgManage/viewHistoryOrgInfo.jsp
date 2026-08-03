<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//初始化右边页面
$(function(){
	openOnRight('/org/orgManage/viewHistoryOrgTreeInfo?RESUME_NO=${RESUME_NO}','viewHistoryOrgDetailInfo_orgTree');
});
function viewHistoryOrgInfo(){
	$("#viewHistoryOrgInfoForm").submit();
}
</script>
<div class="pageHeader">
<form id="viewHistoryOrgInfoForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewHistoryOrgInfo" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="org.title.DATE" /><!-- 日期 --></td>
		<td>
			<input type="text" id="search_RESUME_DATE" name="search_RESUME_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" 
					readonly="true" value="${RESUME_DATE}" size="25" onchange="viewHistoryOrgInfo()"  openChange="true"/>
		</td>
		<td><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
		<td>
			${RESUME_NO}
		</td>
		
		<%-- <td>~</td>
		<td>
			<input type="text" id="search_END_DATE" name="search_END_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" 
					readonly="true" value="${END_DATE}" size="25" onchange="viewHistoryOrgInfo()"  openChange="true"/>
		</td> --%>
	</tr>
</table>
</div>
</form>
</div>
<div id="viewHistoryOrgDetailInfo_orgTree" style="width:340px;height:670px;line-height:570px;overflow:auto;overflow-x:hidden;float:left;">
</div>
<div id="viewHistoryOrgDetailInfo_right_unit" style="display:block;margin-top:5px;">
</div>
