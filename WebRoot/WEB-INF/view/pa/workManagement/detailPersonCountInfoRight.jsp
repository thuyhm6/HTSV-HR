<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$("#detailPersonCountInfoRight_lit", navTab.getCurrentPanel()).dataTable( {
				"bPaginate": false,    //分页
			    "bAutoWidth":false,//表格宽度不自动变化
			    "bProcessing":true,
			    //"lengthMenu": [[20, 50, 100, -1], [20, 50, 100, "所有"]],
				//"bLengthChange": true,  //按多少条记录显示下拉框
				//"iDisplayLength": 20, //默认每页显示的记录数
				"bLengthChange":false,
				"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		         "searching": true,//本地搜索
					"bSort": true,   //排序功能
					"bInfo": false,   //显示datatables的信息（底部的页数，条目数信息）
				//"bScrollInfinite":true,
		         "orderClasses": false,
		         "order":[],//初始化不用自动排序
		 		 "scrollX" : true,
		         "scrollY": $(document.body).height() - 300,
		         "scrollCollapse": true,
		         //"deferRender":true,
		         //"scroller":true,
		         "columnDefs": [//使某个字段不支持快速检索
		                        { "searchable": false, "targets": [0] }
		                      ],
				"oLanguage" : {//多语言配置
					 //正在加载中......
					 "sProcessing": "<spring:message code='ess.message.loading' />",
				     //查询不到相关数据！
				     "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
				     //表中无数据存在！
				     "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
				     //快速筛选
				     "sSearch": "<spring:message code='ess.message.rapid_screening' />",
				     //每页 _MENU_ 条记录
				     "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
				     //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
				     "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
				     //(从 _MAX_ 条记录过滤)
				     "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
				     "oPaginate": {
				         //上一页
				         "sPrevious": "<spring:message code='ess.message.previous_page' />",
				         //下一页
				         "sNext": "<spring:message code='ess.message.next_page' />"
					}
				}
			//多语言配置
					});
			$("#detailPersonCountInfoRight_lit tbody", navTab.getCurrentPanel()).on(
					'click',
					'tr',
					function() {
						$(this).toggleClass('selected');
					});
</script>
<div class="pageContent" style="margin: 0px; padding: 10px;">
	<table class="orderList " id="detailPersonCountInfoRight_lit" width="99%">
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
					<!--适用式--><spring:message code="pa.detailPersonCountInfoRight.SHIYONGSHI.b" />
				</th>
			</tr>
		</thead>
		<c:forEach items="${detailPersonCountInfoList}" var="item"
			varStatus="i">
			<%-- <c:if test="${item.ITEM_TYPE eq ITEM_TYPE}"> --%>
				<tr>
					<td >
						${i.count}
					</td>
					<td >
						${item.ITEM_NAME}
					</td>
					<td >
						<fmt:formatNumber value="${item.ITEM_VALUE}" pattern="#,##0" />
					</td>
					<td >
						${item.ITEM_FORMULAR}
					</td>
				</tr>
			<%-- </c:if> --%>
		</c:forEach>
	</table>
</div>
