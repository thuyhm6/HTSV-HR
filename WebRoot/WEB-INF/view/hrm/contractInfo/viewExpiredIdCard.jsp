<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":true,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50,100,500], [10,20, 35, 50,100,500]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 35, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 200,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	    "columnDefs": false,
	    "fixedColumns":false,
        "oLanguage": {//多语言配置
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
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [] 
		});
});

</script>
	<div class="formBar">
		<ul class="toolBar">
			<li><a class="buttonActive"
		href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=298&PERSON_ID=${PERSON_ID }"><span><spring:message
		code="hrm.empinfo.EXPORT" /><!--导出到EXECL--></span></a></li>
		</ul>
	</div>
<div class="pageContent">
		<table class="orderList" width="99%">
			<thead>
				<tr>
					<th>NO</td>
					<th><!-- 社号 --><spring:message code="ess.infoApply.EMPID" /></td>
					<th><!-- 姓名 --><spring:message code="ess.infoApply.NAME" /></td>
					<th><!-- 部门 --><spring:message code="ess.infoApply.DEPT" /></td>
					<th><!-- 到期时间 --><spring:message code="hrm.contractInfo.EXPIRATION_DATE.Z" /></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${expiredIdCardList}" var="item" varStatus="i">
					<tr>
						<td>${i.count }</td>
						<td>${item.EMPID }</td>
						<td>${item.LOCAL_NAME }</td>
						<td>${item.DEPT_NAME }</td>
						<td>${item.IDCARD_EXPIRE_DATE }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		</div>
</div>
