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
			//正在加载中......
	   	    "sProcessing": "<spring:message code='ess.message.loading' />",
	        //查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
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
			//正在加载中......
	   	    "sProcessing": "<spring:message code='ess.message.loading' />",
	        //查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
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
			//正在加载中......
	   	    "sProcessing": "<spring:message code='ess.message.loading' />",
	        //查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
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
					<!--项目--><spring:message code="ess.empInfo.project" />
				</th>
				<th>
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" />
				</th>
				<th>
					<!--年间累加额--><spring:message code="pa.detailmonthCountInfoRight.NIANJIALEIJIAE.b" />
				</th>
			</tr>
		</thead>
		<c:set var="iCount" value="0"></c:set>
		<c:forEach items="${detailMYCountInfoRightList}" var="item" >
			<c:if test="${item.ITEM_TYPE eq '1'}">
				<tr>
					<td class="td_type" width="10%">
						<c:set var="iCount" value="${iCount+1 }"></c:set>
						${iCount }
					</td>

					<td class="td_type" width="40%">
						${item.ITEM_NAME}
					</td>
					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" />
					</td>

					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.ITEM_YEAR_VALUE}" pattern="#,##0" />
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
					<!--项目--><spring:message code="ess.empInfo.project" />
				</th>
				<th>
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" />
				</th>
				<th>
					<!--年间累加额--><spring:message code="pa.detailmonthCountInfoRight.NIANJIALEIJIAE.b" />
				</th>
			</tr>
		</thead>
		<c:set var="iCount" value="0"></c:set>
		<c:forEach items="${detailMYCountInfoRightList}" var="item" >
			<c:if test="${item.ITEM_TYPE eq '2'}">
				<tr>
					<td class="td_type" width="10%">
						<c:set var="iCount" value="${iCount+1 }"></c:set>
						${iCount }
					</td>

					<td class="td_type" width="40%">
						${item.ITEM_NAME}
					</td>
					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" />
					</td>

					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.ITEM_YEAR_VALUE}" pattern="#,##0" />
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
					<!--项目--><spring:message code="ess.empInfo.project" />
				</th>
				<th>
					<!--金额--><spring:message code="ess.empInfo.amount_of_money" />
				</th>
				<th>
					<!--年间累加额--><spring:message code="pa.detailmonthCountInfoRight.NIANJIALEIJIAE.b" />
				</th>
			</tr>
		</thead>
		<c:set var="iCount" value="0"></c:set>
		<c:forEach items="${detailMYCountInfoRightList}" var="item" >
			<c:if test="${item.ITEM_TYPE eq '3'}">
				<tr>
					<td class="td_type" width="10%">
						<c:set var="iCount" value="${iCount+1 }"></c:set>
						${iCount }
					</td>

					<td class="td_type" width="40%">
						${item.ITEM_NAME}
					</td>
					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" />
					</td>

					<td class="td_type" width="25%">
						<fmt:formatNumber value="${item.ITEM_YEAR_VALUE}" pattern="#,##0" />
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</div>