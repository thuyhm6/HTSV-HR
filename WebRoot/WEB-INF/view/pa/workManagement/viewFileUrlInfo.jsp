<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#pa0132_pageContent_r1").css("height", $(document.body).height() -220);
	$("#pa0132_table_r1").css("width", $("#pa0132_pageContent_r1").width()-20);

	$("#pa0132_table_r1", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : false, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() /5.8,
		"scrollX" : true,
		"orderClasses" : false,
		"oLanguage" : {
			"sProcessing" : "正在加载中......",
			"sZeroRecords" : "查询不到相关数据！",
			"sEmptyTable" : "表中无数据存在！",
			"sSearch" : "快速筛选"
		}
	//多语言配置
			});
	$("#pa0132_table_r2").css("width", $("#pa0132_pageContent_r1").width()-20);

	$("#pa0132_table_r2", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : false, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() /5.8,
		"scrollX" : true,
		"orderClasses" : false,
		"oLanguage" : {
			"sProcessing" : "正在加载中......",
			"sZeroRecords" : "查询不到相关数据！",
			"sEmptyTable" : "表中无数据存在！",
			"sSearch" : "快速筛选"
		}
	//多语言配置
			});
	$("#pa0132_table_r3").css("width", $("#pa0132_pageContent_r1").width()-20);

	$("#pa0132_table_r3", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : false, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height()/5.8,
		"scrollX" : true,
		"orderClasses" : false,
		"oLanguage" : {
			"sProcessing" : "正在加载中......",
			"sZeroRecords" : "查询不到相关数据！",
			"sEmptyTable" : "表中无数据存在！",
			"sSearch" : "快速筛选"
		}
	//多语言配置
			});
});
</script>
<div class="pageContent" width="100%" id='pa0132_pageContent_r1'>
	<table class="orderList" id="pa0132_table_r1" width="100%">
		<thead>
			<tr>
				<th>
					No.
				</th>

				<th>
					津贴
				</th>
				<th>
					实际支付
				</th>
				<th>
					年间累加额
				</th>

			</tr>
		</thead>
		<c:set var="C" value="${0}" />

		<c:forEach items="${detailYearCountInfoRight}" var="item"
			varStatus="i">
			<c:if test="${item.ITEM_TYPE eq '1'}">
				<tr>
					<td class="td_type" width="20%">
						<c:set var="C" value="${C+1}" />
						${C}
					</td>

					<td class="td_type" width="30%">
						${item.ITEM_NAME}
					</td>
					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.MONTH_ALL}" pattern="#,##0" />
					</td>

					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.YEAR_ALL}" pattern="#,##0" />
					</td>
				</tr>
			</c:if>


		</c:forEach>
	</table>

	<table class="orderList" id="pa0132_table_r2">
		<thead>
			<tr>
				<th>
					No.
				</th>

				<th>
					津贴
				</th>
				<th>
					实际支付
				</th>
				<th>
					年间累加额
				</th>

			</tr>
		</thead>
		<c:set var="C" value="${0}" />

		<c:forEach items="${detailYearCountInfoRight}" var="item"
			varStatus="i">
			<c:if test="${item.ITEM_TYPE eq '2'}">
				<tr>
					<td class="td_type" width="20%">
						<c:set var="C" value="${C+1}" />
						${C}
					</td>

					<td class="td_type" width="30%">
						${item.ITEM_NAME}
					</td>
					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.MONTH_ALL}" pattern="#,##0" />
					</td>

					<td class="td_type" width="25%">

						<fmt:formatNumber value="${item.YEAR_ALL}" pattern="#,##0" />
					</td>
				</tr>
			</c:if>


		</c:forEach>
	</table>

	<table class="orderList" id="pa0132_table_r3">
		<thead>
			<tr>
				<th>
					No.
				</th>

				<th>
					津贴
				</th>
				<th>
					实际支付
				</th>
				<th>
					年间累加额
				</th>

			</tr>
		</thead>
		<c:set var="C" value="${0}" />

		<c:forEach items="${detailYearCountInfoRight}" var="item"
			varStatus="i">
			<c:if test="${item.ITEM_TYPE eq '3'}">
				<tr>
					<td class="td_type" width="20%">
						<c:set var="C" value="${C+1}" />
						${C}
					</td>

					<td class="td_type" width="30%">
						${item.ITEM_NAME}
					</td>
					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.MONTH_ALL}" pattern="#,##0" />
					</td>

					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.YEAR_ALL}" pattern="#,##0" />
					</td>
				</tr>
			</c:if>


		</c:forEach>
	</table>
</div>






