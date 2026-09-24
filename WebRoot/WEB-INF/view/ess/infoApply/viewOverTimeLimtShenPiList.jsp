<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="/resources/js/togglebar.js"></script>
<script type="text/javascript">

//加载样式
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //关闭分页
		"bAutoWidth": false,   //表格宽度不自动变化
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能
		"bSort": true,
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"scrollY": $(document.body).height() - 260,
		"scrollX": true,
        "orderClasses": false,
        "fixedColumns":{leftColumns: 2}
	});
});
</script>

<div id="viewOverTimeLimtShenPiListPage"  class="pageHeader" >
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewOverTimeLimtShenPiList?firstFlag=N"  method="post"
		id="viewOverTimeLimtShenPiList" name="viewOverTimeLimtShenPiList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 年 --><spring:message code="rp.report.title.year"/></td>
					<td>
						<input type="text" id="seach_YEAR" name="seach_YEAR" class="Wdate" style="width: 80px" onClick="WdatePicker({dateFmt:'yyyy',lang:'en'})" value="${YEAR}" />
					</td>
					<td><!-- 社号/姓名 -->
						<spring:message code="hrm.empinfo.nameAndEmpid" />
					</td>
					<td>
						<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
					</td>
				     <td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList limit="manager" name="seach_DEPTNO" cpnyId="${defaultCpny}"  id="viewOverTimeLimtShenPiList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon limit="manager" name="seach_DEPTNO" cpnyId="${defaultCpny}"  id="viewOverTimeLimtShenPiList_seachDept" selected="${DEPTNO}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit">
					       <spring:message code="public.title.search"/>
					    </button>
				        </div>
				        </div>
				    </li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" >
<div class="formBar">
       <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 10px; line-height: 30px;">Total:${fn:length(otYearTrackList)}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
       <ul class="toolBar">
			<li><a class="buttonActive" onclick="downloadExcel('viewOverTimeLimtShenPiList','/ess/infoApply/exportOverTimeLimtShenPiExcel?firstFlag=N','/ess/infoApply/viewOverTimeLimtShenPiList?firstFlag=N')"><span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span></a></li>
       </ul>
</div>
	<table class="orderList" width="4800px"  >
		<thead>
				<tr>
					<th rowspan="2" width="80px"><spring:message code="public.title.empId"/></th>
					<th rowspan="2" width="140px"><spring:message code="public.title.name"/></th>
					<th colspan="5">${YEAR}</th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month01"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month02"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month03"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month04"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month05"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month06"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month07"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month08"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month09"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month10"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month11"/></th>
					<th colspan="5"><spring:message code="ess.infoApply.title.overTimeTrack.month12"/></th>
				</tr>
				<tr>
					<c:forEach begin="1" end="13">
						<th width="70px"><spring:message code="ess.infoApply.title.overTimeTrack.totalOt"/></th>
						<th width="70px"><spring:message code="ess.infoApply.title.overTimeTrack.otApproval"/></th>
						<th width="70px"><spring:message code="ess.infoApply.title.overTimeTrack.satIncentiveApproval"/></th>
						<th width="70px"><spring:message code="ess.infoApply.title.overTimeTrack.otRequest"/></th>
						<th width="70px"><spring:message code="ess.infoApply.title.overTimeTrack.satIncentiveRequest"/></th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otYearTrackList}" var="item" varStatus="i">
					<tr target="sid" rel="${item.PERSON_ID}" >
					    <td style="text-align: center">${item.EMPID}</td>
					    <td>${item.LOCAL_NAME}</td>
					    <td style="text-align: center">${item.TOTAL_YEAR}</td>
					    <td style="text-align: center">${item.APPROVAL_YEAR}</td>
					    <td style="text-align: center">${item.SATAPV_YEAR}</td>
					    <td style="text-align: center">${item.REQUEST_YEAR}</td>
					    <td style="text-align: center">${item.SATREQ_YEAR}</td>
					    <td style="text-align: center">${item.TOTAL_01}</td>
					    <td style="text-align: center">${item.APPROVAL_01}</td>
					    <td style="text-align: center">${item.SATAPV_01}</td>
					    <td style="text-align: center">${item.REQUEST_01}</td>
					    <td style="text-align: center">${item.SATREQ_01}</td>
					    <td style="text-align: center">${item.TOTAL_02}</td>
					    <td style="text-align: center">${item.APPROVAL_02}</td>
					    <td style="text-align: center">${item.SATAPV_02}</td>
					    <td style="text-align: center">${item.REQUEST_02}</td>
					    <td style="text-align: center">${item.SATREQ_02}</td>
					    <td style="text-align: center">${item.TOTAL_03}</td>
					    <td style="text-align: center">${item.APPROVAL_03}</td>
					    <td style="text-align: center">${item.SATAPV_03}</td>
					    <td style="text-align: center">${item.REQUEST_03}</td>
					    <td style="text-align: center">${item.SATREQ_03}</td>
					    <td style="text-align: center">${item.TOTAL_04}</td>
					    <td style="text-align: center">${item.APPROVAL_04}</td>
					    <td style="text-align: center">${item.SATAPV_04}</td>
					    <td style="text-align: center">${item.REQUEST_04}</td>
					    <td style="text-align: center">${item.SATREQ_04}</td>
					    <td style="text-align: center">${item.TOTAL_05}</td>
					    <td style="text-align: center">${item.APPROVAL_05}</td>
					    <td style="text-align: center">${item.SATAPV_05}</td>
					    <td style="text-align: center">${item.REQUEST_05}</td>
					    <td style="text-align: center">${item.SATREQ_05}</td>
					    <td style="text-align: center">${item.TOTAL_06}</td>
					    <td style="text-align: center">${item.APPROVAL_06}</td>
					    <td style="text-align: center">${item.SATAPV_06}</td>
					    <td style="text-align: center">${item.REQUEST_06}</td>
					    <td style="text-align: center">${item.SATREQ_06}</td>
					    <td style="text-align: center">${item.TOTAL_07}</td>
					    <td style="text-align: center">${item.APPROVAL_07}</td>
					    <td style="text-align: center">${item.SATAPV_07}</td>
					    <td style="text-align: center">${item.REQUEST_07}</td>
					    <td style="text-align: center">${item.SATREQ_07}</td>
					    <td style="text-align: center">${item.TOTAL_08}</td>
					    <td style="text-align: center">${item.APPROVAL_08}</td>
					    <td style="text-align: center">${item.SATAPV_08}</td>
					    <td style="text-align: center">${item.REQUEST_08}</td>
					    <td style="text-align: center">${item.SATREQ_08}</td>
					    <td style="text-align: center">${item.TOTAL_09}</td>
					    <td style="text-align: center">${item.APPROVAL_09}</td>
					    <td style="text-align: center">${item.SATAPV_09}</td>
					    <td style="text-align: center">${item.REQUEST_09}</td>
					    <td style="text-align: center">${item.SATREQ_09}</td>
					    <td style="text-align: center">${item.TOTAL_10}</td>
					    <td style="text-align: center">${item.APPROVAL_10}</td>
					    <td style="text-align: center">${item.SATAPV_10}</td>
					    <td style="text-align: center">${item.REQUEST_10}</td>
					    <td style="text-align: center">${item.SATREQ_10}</td>
					    <td style="text-align: center">${item.TOTAL_11}</td>
					    <td style="text-align: center">${item.APPROVAL_11}</td>
					    <td style="text-align: center">${item.SATAPV_11}</td>
					    <td style="text-align: center">${item.REQUEST_11}</td>
					    <td style="text-align: center">${item.SATREQ_11}</td>
					    <td style="text-align: center">${item.TOTAL_12}</td>
					    <td style="text-align: center">${item.APPROVAL_12}</td>
					    <td style="text-align: center">${item.SATAPV_12}</td>
					    <td style="text-align: center">${item.REQUEST_12}</td>
					    <td style="text-align: center">${item.SATREQ_12}</td>
					</tr>
				</c:forEach>
			</tbody>
	</table>
</div>
