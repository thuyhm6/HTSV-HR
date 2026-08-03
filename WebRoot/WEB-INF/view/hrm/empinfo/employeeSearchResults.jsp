<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style type="text/css">
th,td {
	white-space: nowrap;
}

div.dataTables_wrapper {
	margin: 0 auto;
}
</style>
<script type="text/javascript">
var employeeSearchHeight = 0;
$(function() {
	var cc = document.body.clientHeight;
	var aa = cc - 256;
	employeeSearchHeight = aa;

});
$("#empSearchResultTable", navTab.getCurrentPanel()).dataTable( {
	"bPaginate" : true, //分页
	"bAutoWidth" : false,//表格宽度不自动变化
	"bProcessing" : true,
	//"lengthMenu": [[20, 50, 100, -1], [20, 50, 100, "所有"]],
	//"bLengthChange": true,  //按多少条记录显示下拉框
	//"iDisplayLength": 20, //默认每页显示的记录数
	"bFilter" : false, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	"searching" : true,//本地搜索
	"bSort" : true, //排序功能
	"bInfo" : true, //显示datatables的信息（底部的页数，条目数信息）
	//"bScrollInfinite":true,
	"orderClasses" : false,
	"order" : [],//初始化不用自动排序
	"scrollY" : $(document.body).height() - 300,
	"scrollX" : true,
	"scrollCollapse" : true,
	"deferRender" : true,
	"scroller" : true,
	"fixedColumns" : {
		leftColumns : 3
	},
	"language" : {
		"sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
	},
	"sDom" : '<"top"rfB>t<"bottom"ip<"clear">>',
	"buttons" : [  ]

});
$("#empSearchResultTable tbody", navTab.getCurrentPanel()).on(
		'click',
		'tr',
		function() {
			$(this).toggleClass('selected');
			$("#empSearchResultTable", navTab.getCurrentPanel()).dataTable()
					.api().fixedColumns().update();
		});
var personid = "";
function loadRenshika(a) {
	navTabNum('/hrm/empinfo/viewPersonalInfo?PERSON_ID=' + a,'pageNum=1&amp;menuNo=125244&amp;navTabId=hr2100','hr2100','<spring:message code="hrm.empinfo.COOMPREHENSIVE_INTRODUCTION.Z" />');//综合简介
	//personid = a;
}
//function downloadRenshi() {
//	if (personid == "") {
//		alert('请先选择一个人!');
//	} else {
//		window.location.href = "/hrm/report/payReport04?checkVal=report6&EMPID_STR="
//				+ personid + "&filename=card";
//	}
//}

/**
 * excel导出带form表单搜索条件
 * @param url
 * @return
 */
function downloadExcelResult(formId,excelUrl,searchUrl){
	$('#' + formId).attr("action",excelUrl);
	$('#' + formId).attr("onsubmit",'');
	$('#' + formId).submit();
	$('#' + formId).attr("action",searchUrl);
	$('#' + formId).attr("onsubmit",'return navTabSearch(this);');
}
</script>
<div class="pageHeader" id="employeeSearch">
	<div class="pageContent">
		<%-- <span style="font-size: 15px;color:#000;padding-top:10px;">Total:${searchResultsListCount }</span> 
		<a class="buttonActive" id="empRenshika" onclick="downloadRenshi()"
			style="float: right;" href="#"><span>人事卡</span> </a>--%>
		<a  class="buttonActive" style="float: right;" href="#" onclick="downloadExcelResult('employeeSearchResults','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=27','/hrm/empinfo/employeeSearchResults')">
			<span><!-- 导出到Excel --><spring:message code="ess.infoApply.export_to_Excel" /></span></a>
	</div>
	<div>
		<c:if test="${fn:length(searchnamelist) >0}">
			<table id="empSearchResultTable" class="orderList" width="100%">
				<thead>
					<tr>

						<c:if test="${flag=='1' }">
						
							<th>
								NO.
							</th>

							<c:forEach items="${searchnamelist }" var="s" varStatus="i">
								<th>
									${s.SEARCH_NAME}
								</th>
							</c:forEach>



						</c:if>
						<c:if test="${flag=='2' }">
							<th>
								NO.
							</th>
							<th>
								<!-- 姓名 --><spring:message code="hrm.empinfo.FAM_NAME" />
							</th>
							<th>
								<spring:message code="hrm.empinfo.empid" />
							</th>
						</c:if>
					</tr>
				</thead>
				<tbody>

					${searchResultsList}

				</tbody>
			</table>
		</c:if>
		<c:if test="${fn:length(searchnamelist)==0}">
		
		<!-- 查询结果无数据！ --><spring:message code="hrm.empinfo.QUERY_RESULT_NO_DATA" />
		</c:if>

	</div>
</div>
