<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">

$(document).ready(function() {
	
	$("#ConfirmBottom").width($(document.body).width()-100);
	$("#viewResultConfirmList2Bottom").css('height',$(document.body).height() - 460);
	$("#ConfirmBottom", navTab.getCurrentPanel()).dataTable( {
		"bPaginate" : false, //关闭分页
		"bAutoWidth" : false,//表格宽度不自动变化
		"bProcessing" : true,
		"bLengthChange" : false, //关闭按多少条记录显示下拉框
		"bFilter" : true, //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort" : true, //关闭排序功能
		"bInfo" : false, //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite" : true,
		"scrollY" : $(document.body).height() - 500,
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


<div class="pageContent" id="viewResultConfirmList2Bottom" style="margin: 0px; padding: 0px;"
	style="width:100%">
	<table id='ConfirmBottom' class='orderList'  >
		<thead>
			<tr>
				<th>
					No.
				</th>
				<th>
					<!--姓名--><spring:message code="ess.infoApply.NAME" />
				</th>
				<th>
					<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
				</th>
				<th>
					<!--日期--><spring:message code="ess.infoApply.date" />
				</th>
				<th>
					<!--班次--><spring:message code="ess.message.work_shift" />
				</th>
				<th>
					<!--类型--><spring:message code="sys.affirm.title.type" />
				</th>
				<th>
					<!--开始--><spring:message code="ar.viewshift.title.start" />
				</th>
				<th>
					<!--结束--><spring:message code="ar.viewshift.title.end" />
				</th>
				<th>
					<!--时长--><spring:message code="ess.infoApply.duration" />
				</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${viewResultConfirmList2Bottom}" var="item"
				varStatus="i">

				<tr>
					<td class="td_type" >
						${i.count}
					</td>
					<td class="td_type" >
						${item.LOCAL_NAME}
					</td>
					<td class="td_type" >
						${item.EMPID}
					</td>
					<td class="td_type" >
						${item.AR_DATE_STR}
					</td>
					<td class="td_type" >
						${item.SHIFT_NAME}
					</td>
					<td class="td_type" >
						${item.ITEM_NAME}
					</td>
					<td class="td_type" >
						${item.FROM_TIME}
					</td>

					<td class="td_type" >
						${item.TO_TIME}
					</td>
					<td class="td_type" >
						${item.QUANTITY}
					</td>
				</tr>
			</c:forEach>
			<tbody>
	</table>
</div>