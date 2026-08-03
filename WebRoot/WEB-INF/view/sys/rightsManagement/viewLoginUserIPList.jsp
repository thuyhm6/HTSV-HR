<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(document).ready(
		function() {
			$("#sy0125_pageContent").css("height",
					$(document.body).height() - 200);
			$("#sy0125_pageContent")
					.css("width", $(document.body).width() - 20);

			$("#sy0125_table").css("width",
					$("#sy0125_pageContent").width() - 20);

			$("#sy0125_table", navTab.getCurrentPanel()).dataTable( {
				"bPaginate" : false, //关闭分页
				"bAutoWidth" : false,//表格宽度不自动变化
				"bProcessing" : false,
				"bLengthChange" : false, //关闭按多少条记录显示下拉框
				"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
				"bSort" : true, //关闭排序功能
				"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
				"bScrollInfinite" : true,
				"scrollY" : $(document.body).height() - 250,
				"scrollX" : true,
				"orderClasses" : false
			});
		});
</script>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/sys/rightsManagement/viewLoginUserIPList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!-- 检索 -->
								</button>
							</div>
						</div>
					</li>

				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent" id="sy0125_pageContent">
	<table class="orderList" id="sy0125_table">
		<thead>
			<tr>
				<th>
					No.
				</th>
				<th>
					IP地址
				</th>
				<th>
					创建时间
				</th>
				<th>
					创建者
				</th>

			</tr>

		</thead>
		<tbody>
			<c:forEach items="${loginUserIPList}" var="item" varStatus="i">
				<td>
					${i.count}
				</td>
				<td>
					${item.IP_ADDRESS}
				</td>
				<td>
					${item.CREATE_DATE}
				</td>
				<td>
					${item.CREATED_NAME}
				</td>

			</c:forEach>

		</tbody>
	</table>
</div>
