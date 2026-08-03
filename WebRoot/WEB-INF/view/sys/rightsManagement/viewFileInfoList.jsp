<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$(document).ready(function() {
	$("#sy0503_pageContent").css("height", $(document.body).height() - 200);
	$("#sy0503_pageContent").css("width", $(document.body).width() - 20);

	$("#sy0503_left").css("width", $(document.body).width() / 3);
	$("#sy0503_table").css("width", $("#sy0503_left").width() - 10);

	$("#sy0503_table", navTab.getCurrentPanel()).dataTable( {
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

function changeUrl_sy0503(HR_FILE_NO) {

	openOnRight(
			'/sys/rightsManagement/viewFileUrlInfo?HR_FILE_NO=' + HR_FILE_NO,
			'sy0503_uitl');
}
</script>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/sys/rightsManagement/viewFileInfoList" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						关键字
					</td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					<td>
						期间
					</td>
					<td>
						<input type="text" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${START_DATE}"/>~
						<input type="text" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${END_DATE}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent" id="sy0503_pageContent">
	<c:set value="dialog" var="add_tab" />
	<c:set value="800" var="add_width" />
	<c:set value="400" var="add_height" />

	<c:set value="/sys/rightsManagement/addFileInfoView" var="add_Url" />
	<c:set value="ajaxTodo" var="delete_tab" />
	<c:set value="/sys/rightsManagement/deleteLoginUser?USER_NO={sid}"
		var="delete_Url" />
	<c:set value="dialog" var="edit_tab" />
	<c:set value="800" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<c:set value="/sys/rightsManagement/updateLoginUserView?USERNO={sid}"
		var="edit_Url" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<div id="sy0503_left" sysLong="printDiv"
		style="float: left; display: block; overflow: auto; border: solid 1px #CCC; line-height: 21px; background: #fff;">
		<div style="font: bold 12px/ 20px arial, sans-serif;">
			Total:${fn:length(viewFileInfoList)}
		</div>
		<table id="sy0503_table" class="orderList">
			<thead>
				<tr>
					<th>
						NO

					</th>
					<th>
						分类
					</th>
					<th>
						题目
					</th>
					<th>
						上传者
					</th>

					<th>
						上传日期
					</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${viewFileInfoList}" var="item" varStatus="i">
					<tr style="cursor: pointer;"
						onclick="changeUrl_sy0503('${item.HR_FILE_NO}')">
						<td>
							${i.count}
						</td>
						<td>
							${item.FILE_FLAG_NAME}
						</td>
						<td>
							${item.THEME_NAME}
						</td>
						<td>
							${item.LOCAL_NAME}
						</td>
						<td>
							${item.CREATE_DATE}
						</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div id="sy0503_uitl" style="display: block;">
	</div>
</div>